package de.htw.saar.smartcity.aggregator.lib.base;

/**
 * The type Constants.
 */
public final class Constants {

    /**
     * The constant GROUP_EXCHANGE. - Specifies the name of the exchange used for all groups
     */
    public static final String GROUP_EXCHANGE = "group_exchange";

    /**
     * The constant MEMCACHED_EXPIRATION. - Time for key-value-pair expiration
     */
    public static final int MEMCACHED_EXPIRATION = 60*60*12; //12 hours

    /**
     * The constant MEMCACHED_MEASUREMENT_PREFIX. - Prefix for the measurements stored in the cache
     */
    public static final String MEMCACHED_MEASUREMENT_PREFIX = "M_";

    /**
     * The constant MEMCACHED_TEMPORARY_PREFIX. - Prefix for the temporary aggregates stored in the cache
     */
    public static final String MEMCACHED_TEMPORARY_PREFIX = "T_";

    /**
     * The constant PREFETCH_COUNT. - The number of messages that are prefetched at once
     */
    public static final int PREFETCH_COUNT = 10;

    /**
     * the constant  MAX_LOG_MESSAGE_SIZE. - Maximal size of a single logged message
     */
    public static final Integer MAX_LOG_MESSAGE_SIZE = 250;

    /**
     * The constant COLLECTOR_KEYWORD_ALL.- Used for including all producers or aggregators
     */
    public static final String COLLECTOR_KEYWORD_ALL = "ALL";

    /**
     * The constant COLLECTOR_KEYWORD_NONE. - Used for excluding all producers or aggregators
     */
    public static final String COLLECTOR_KEYWORD_NONE = "NONE";

    /**
     * The constant MEMCACHED_UPPER_LIMIT_GET_OBJECTS. - Upper limit for the number of objects read in one call
     */
    public static final double MEMCACHED_UPPER_LIMIT_GET_OBJECTS = 100_000.0;
}
