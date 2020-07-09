package twolovers.antibot.bungee.api;

import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;
import twolovers.antibot.bungee.instanceables.BotPlayer;

/**
 * Event triggered when a violation occurs.
 * This can be cancelled and the user won't receive any punishment.
 */
public class ModuleViolationEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private final ViolationType violationType;
    private final Connection connection;
    private final BotPlayer botPlayer;

    public ModuleViolationEvent(ViolationType violationType, Connection connection, BotPlayer botPlayer) {
        this.violationType = violationType;
        this.connection = connection;
        this.botPlayer = botPlayer;
    }

    /**
     * Gets the violation type that was thrown
     *
     * @return the violation type
     */
    public ViolationType getViolationType() {
        return violationType;
    }

    /**
     * Gets the user connection that got denied/disconnected
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Gets the {@link BotPlayer} object bounded with the ip.
     * This can be useful to get CPS, PPS and JPS among other things
     *
     * @return the {@link BotPlayer} object
     */
    public BotPlayer getBotPlayer() {
        return botPlayer;
    }

    /**
     * Get whether or not this event is cancelled.
     *
     * @return the cancelled state of this event
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancelled state of this event.
     *
     * @param cancel the state to set
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
