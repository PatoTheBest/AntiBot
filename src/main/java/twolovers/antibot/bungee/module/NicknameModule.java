package twolovers.antibot.bungee.module;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import twolovers.antibot.bungee.api.ViolationType;
import twolovers.antibot.bungee.instanceables.Conditions;
import twolovers.antibot.bungee.utils.ConfigUtil;
import twolovers.antibot.shared.interfaces.PunishModule;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

public class NicknameModule implements PunishModule {
	private final String name = "nickname";
	private final ModuleManager moduleManager;
	private Collection<String> punishCommands = new HashSet<>();
	private Collection<String> blacklist = new HashSet<>();
	private Conditions conditions;
	private Pattern pattern = Pattern.compile(
			"^(Craft|Beach|Actor|Games|Tower|Elder|Mine|Nitro|Worms|Build|Plays|Hyper|Crazy|Super|_Itz|Slime)(Craft|Beach|Actor|Games|Tower|Elder|Mine|Nitro|Worms|Build|Plays|Hyper|Crazy|Super|_Itz|Slime)(11|50|69|99|88|HD|LP|XD|YT)");
	private String lastNickname = "AAAAAAAAAAAAAAAA";
	private boolean enabled = true;

	public NicknameModule(final ModuleManager moduleManager) {
		this.moduleManager = moduleManager;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public final void reload(final ConfigUtil configUtil) {
		final Configuration configYml = configUtil.getConfiguration("%datafolder%/config.yml");

		if (configYml != null) {
			final int pps = configYml.getInt(name + ".conditions.pps", 0);
			final int cps = configYml.getInt(name + ".conditions.cps", 0);
			final int jps = configYml.getInt(name + ".conditions.jps", 0);

			this.enabled = configYml.getBoolean(name + ".enabled");
			this.punishCommands.clear();
			this.punishCommands.addAll(configYml.getStringList(name + ".commands"));
			this.conditions = new Conditions(pps, cps, jps, false);
			this.blacklist.clear();
			this.blacklist.addAll(configYml.getStringList(name + ".blacklist"));
		}
	}

	@Override
	public boolean meet(int pps, int cps, int jps) {
		return this.enabled && conditions.meet(pps, cps, jps, moduleManager.getLastPPS(), moduleManager.getLastCPS(),
				moduleManager.getLastJPS());
	}

	@Override
	public boolean check(final Connection connection) {
		if (connection instanceof ProxiedPlayer) {
			final String name = ((ProxiedPlayer) connection).getName();

			if (!name.equals(lastNickname) && name.length() == lastNickname.length()) {
				return true;
			} else {
				final String lowerName = name.toLowerCase();

				for (final String blacklisted : blacklist) {
					if (lowerName.contains(blacklisted)) {
						return true;
					}
				}

				return pattern.matcher(name).find();
			}
		}

		return false;
	}

	@Override
	public Collection<String> getPunishCommands() {
		return punishCommands;
	}

	public final String getLastNickname() {
		return lastNickname;
	}

	public final void setLastNickname(String nickname) {
		lastNickname = nickname;
	}

	@Override
	public ViolationType getViolationType() {
		return ViolationType.NICKNAME;
	}
}
