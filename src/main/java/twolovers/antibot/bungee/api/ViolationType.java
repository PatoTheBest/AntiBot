package twolovers.antibot.bungee.api;

/**
 * The violation type a user triggered
 */
public enum ViolationType {

    /**
     * The whitelist adds real players to a list on disconnect so they can bypass the checks.
     * If conditions are achieved Lockout will activate and only Whitelisted will be able to join.
     * Lockout is made for security measures and its not intended to be used normally.
     * The violation would be trying to join the proxy without being whitelisted
     */
    WHITELIST,

    /**
     * The IP trying to connect is inside a blacklist
     */
    BLACKLIST,

    /**
     * The connection has been rate limited
     */
    RATE_LIMIT,

    /**
     * The nickname used contained a blacklisted word
     */
    NICKNAME,

    /**
     * The player that connected didn't ping the server first
     */
    RECONNECT,

    /**
     * The player didn't send the settings packet (vanilla clients always sends the settings packet)
     * This can be a false positive if Minechat is used or any other client of the same type.
     */
    SETTINGS,

    /**
     * Connections that chat too fast or in invalid moments.
     */
    FAST_CHAT,

    /**
     * The player tried to register with the same password another player used to register
     */
    REGISTER,

    /**
     * If there are too many accounts connected to the server with the same ip
     */
    ACCOUNTS

}
