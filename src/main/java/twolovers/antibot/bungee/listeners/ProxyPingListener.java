package twolovers.antibot.bungee.listeners;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import twolovers.antibot.bungee.instanceables.BotPlayer;
import twolovers.antibot.bungee.instanceables.Punish;
import twolovers.antibot.bungee.module.BlacklistModule;
import twolovers.antibot.bungee.module.ModuleManager;
import twolovers.antibot.bungee.module.PlayerModule;
import twolovers.antibot.bungee.module.RateLimitModule;

public class ProxyPingListener implements Listener {
	private final Plugin plugin;
	private final ModuleManager moduleManager;

	public ProxyPingListener(final Plugin plugin, final ModuleManager moduleManager) {
		this.plugin = plugin;
		this.moduleManager = moduleManager;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onProxyPing(final ProxyPingEvent event) {
		if (event.getResponse() != null) {
			final BlacklistModule blacklistModule = moduleManager.getBlacklistModule();
			final RateLimitModule rateLimitModule = moduleManager.getRateLimitModule();
			final PlayerModule playerModule = moduleManager.getPlayerModule();
			final Connection connection = event.getConnection();
			final String ip = connection.getAddress().getHostString();
			final long currentTimeMillis = System.currentTimeMillis();
			final BotPlayer botPlayer = playerModule.get(ip);

			botPlayer.setPPS(botPlayer.getPPS() + 1);

			final int currentPPS = moduleManager.getCurrentPPS() + 1;
			final int currentCPS = moduleManager.getCurrentCPS();
			final int currentJPS = moduleManager.getCurrentJPS();

			moduleManager.setCurrentPPS(currentPPS);

			if (rateLimitModule.meet(currentPPS, currentCPS, currentJPS) && rateLimitModule.check(connection)) {
				new Punish(plugin, moduleManager, "en", rateLimitModule, connection, event);

				blacklistModule.setBlacklisted(ip, true);
			} else if (blacklistModule.meet(currentPPS, currentCPS, currentJPS) && blacklistModule.check(connection)) {
				new Punish(plugin, moduleManager, "en", blacklistModule, connection, event);
			} else {
				if (botPlayer.getPlayers().size() < 1) {
					playerModule.setOffline(botPlayer);
				}
			}

			botPlayer.setRepings(botPlayer.getRepings() + 1);
			botPlayer.setLastPing(currentTimeMillis);
		}
	}
}
