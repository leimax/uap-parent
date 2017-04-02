package com.deppon.uap.framework.cache;

import io.codis.jodis.JedisResourcePool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CacheHelper<V> {

    private final Logger logger = LoggerFactory.getLogger(CacheHelper.class);

    private JedisResourcePool codisPool;


    public boolean set(String key, V value) {
        try (Jedis jedis = codisPool.getResource()) {
            String jsonValue = CacheUtils.objectFormatJson(value);
            // 通过jedis往缓存放值
            jedis.set(key, jsonValue);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean set(String key, V value, int expire) {
        try (Jedis jedis = codisPool.getResource()) {
            String jsonValue = CacheUtils.objectFormatJson(value);
            // 通过jedis往缓存放值 有过期时间的值
            jedis.setex(key, expire, jsonValue);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public V get(String key) {
        try (Jedis jedis = codisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (StringUtils.equalsIgnoreCase(jsonValue, "null")) {
                if ("nil".equalsIgnoreCase(jsonValue)) {
                    throw new IllegalArgumentException("key is not found");
                } else {
                    CacheUtils.jsonParseObject(jsonValue);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void remove(String... keys) {
        try (Jedis jedis = codisPool.getResource()) {
            jedis.del(keys);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public boolean set(Map<String, V> values, int expire) {
        try (Jedis jedis = codisPool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (Map.Entry<String, V> entry : values.entrySet()) {
                if (StringUtils.isNotBlank(entry.getKey())) {
                    String jsonValue = CacheUtils.objectFormatJson(entry.getValue());
                    if (expire >= 0) {
                        pipeline.setex(entry.getKey(), expire, jsonValue);
                    } else {
                        pipeline.set(entry.getKey(), jsonValue);
                    }
                }
            }
            pipeline.sync();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public boolean set(Map<String, V> values) {
        return set(values, -1);
    }

    public long ttl(String key) {
        try (Jedis jedis = codisPool.getResource()) {
            return jedis.ttl(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1l;
    }

    public long lpush(final String key, final String... strings) {
        try (Jedis jedis = codisPool.getResource()) {
            return jedis.lpush(key, strings);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0l;
    }

    public List<String> lrange(final String key, final long start, final long end) {
        try (Jedis jedis = codisPool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public long expire(final String key, final int seconds) {
        try (Jedis jedis = codisPool.getResource()) {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0l;
    }

    public Jedis getJedis() {
        return codisPool.getResource();
    }

    public void setCodisPool(JedisResourcePool codisPool) {
        this.codisPool = codisPool;
    }
}
