package twolovers.antibot.shared.interfaces;

import net.md_5.bungee.api.connection.Connection;
import twolovers.antibot.bungee.api.ViolationType;

import java.util.Collection;

public interface PunishModule extends Module {
	boolean meet(final int pps, final int cps, final int jps);

	boolean check(final Connection connection);

	Collection<String> getPunishCommands();

	ViolationType getViolationType();
}
