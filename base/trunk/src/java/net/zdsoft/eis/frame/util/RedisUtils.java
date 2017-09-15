package net.zdsoft.eis.frame.util;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.exceptions.JedisException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisUtils {

	private static Logger logger = Logger.getLogger(RedisUtils.class);

	private static JedisPool pool;
	
	public static int TIME_FOREEVER = 0;
    public static int TIME_FIVE_SECONDS = 5;
    public static int TIME_HALF_MINUTE = 30;
    public static int TIME_ONE_MINUTE = 60;
    public static int TIME_THREE_MINUTE = 180;
    public static int TIME_FIVE_MINUTES = 300;
    public static int TIME_TEN_MINUTES = 600;
    public static int TIME_HALF_HOUR = 1800;
    public static int TIME_ONE_HOUR = 3600;
    public static int TIME_ONE_DAY = 86400;
    public static int TIME_ONE_WEEK = 604800;
    public static int TIME_ONE_MONTH = 2592000;
    public static int TIME_MAX_TIME = 25920000;

	static {

		// 加载redis配置文件
		ResourceBundle bundle = ResourceBundle.getBundle("conf/redis");
		if (bundle == null) {
			throw new IllegalArgumentException(
					"[redis.properties] is not found!");
		}
		// 创建jedis池配置实例
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置池配置项值
		config.setMaxTotal(100);
		config.setMaxWaitMillis(Integer.valueOf(bundle
				.getString("redis.maxWait")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.maxIdle")));
		config.setTestOnBorrow(Boolean.valueOf(bundle
				.getString("redis.testOnBorrow")));
		// 根据配置实例化jedis池
		pool = new JedisPool(config, bundle.getString("redis.ip"),
				Integer.valueOf(bundle.getString("redis.port")));

		// List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		// shards.add(new JedisShardInfo(bundle.getString("redis.ip"),
		// Integer.valueOf(bundle.getString("redis.port")),
		// Integer.valueOf(bundle.getString("redis.maxWait"))));
		// shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public static JedisPool getPool() {
		return pool;
	}

	public static <T> void setObject(String key, T t) {
		setObject(key, t, 0);
	}

	public static <T> void setObject(String key, T t, int seconds) {
		byte[] bytes = SerializationUtils.serialize(t);
		if (seconds > 0)
			set(key.getBytes(), bytes, seconds);
		else
			set(key.getBytes(), bytes);
	}

	public static <T> void setObject(Jedis jedis, String key, T t) {
		byte[] bytes = SerializationUtils.serialize(t);
		set(jedis, key.getBytes(), bytes);
	}

	public static <T> void setObject(Jedis jedis, String key, T t, int seconds) {
		byte[] bytes = SerializationUtils.serialize(t);
		if (seconds > 0)
			set(jedis, key.getBytes(), bytes, seconds);
		else
			set(jedis, key.getBytes(), bytes);
	}

	public static void set(byte[] key, byte[] value) {
		set(key, value, 0);
	}

	public static void set(Jedis jedis, byte[] key, byte[] value) {
		set(jedis, key, value, 0);
	}

	public static void set(byte[] key, byte[] value, int seconds) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			if (seconds > 0)
				jedis.setex(key, seconds, value);
			else
				jedis.set(key, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
	}

	public static void set(Jedis jedis, byte[] key, byte[] value, int seconds) {
		try {
			if (seconds > 0)
				jedis.setex(key, seconds, value);
			else
				jedis.set(key, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
		}
	}

	public static <T> T getObject(String key) {
		byte[] bytes = get(key.getBytes());
		return (T)SerializationUtils.deserialize(bytes);
	}

	public static byte[] get(byte[] key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
		return null;
	}

	public static void returnResource(Jedis jedis, boolean success) {
		if (!success) {
			pool.returnBrokenResource(jedis);
		} else {
			pool.returnResource(jedis);
		}
	}

	public static <T> T getJsonFromCache(String key, int time,
			TypeReference<T> typeReference, RedisInterface<T> ri) {
		String json = get(key);
		ObjectMapper mapper = new ObjectMapper();
		if (StringUtils.isBlank(json)) {
			T t = ri.queryData();
			try {
				json = mapper.writeValueAsString(t);
				set(key, json, time);
				return t;
			} catch (JsonProcessingException e) {
				return t;
			}
		} else {
			try {
				@SuppressWarnings("unchecked")
				T t = (T) mapper.readValue(json, typeReference);
				return t;
			} catch (Exception e) {
				return ri.queryData();
			}
		}
	}

	/**
	 * 查找
	 * 
	 * @param key
	 * @param value
	 */
	public static Set<String> keySearch(String key) {
		Jedis jedis = pool.getResource();
		Set<String> result = jedis.keys("*" + key + "*");
		// 释放对象池，即获取jedis实例使用后要将对象还回去
		pool.returnResource(jedis);
		return result;
	}

	/**
	 * 查找，右匹配
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> keySearchRight(String key) {
		Jedis jedis = pool.getResource();
		Set<String> result = jedis.keys(key + "*");
		// 释放对象池，即获取jedis实例使用后要将对象还回去
		pool.returnResource(jedis);
		return result;
	}

	/**
	 * 查找，左匹配
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> keySearchLeft(String key) {
		Jedis jedis = pool.getResource();
		Set<String> result = jedis.keys("*" + key);
		// 释放对象池，即获取jedis实例使用后要将对象还回去
		pool.returnResource(jedis);
		return result;
	}

	/**
	 * 分布式锁
	 * 获取锁状态
	 * 轮循（每0.1秒）判断锁状态
	 * 返回false说明获取锁状态时发送异常
	 * @return
	 */
	public static boolean hasLocked(String key){
		Jedis jedis = null;
		boolean success = true;
		boolean isPass = true;
		try {
			jedis = pool.getResource();
			while(jedis.setnx(key, key) == 0){
				Thread.sleep(100);//0.1秒减少性能消耗
			}
			//设置5分钟过期时间，防止死锁（这里有个弊端如果一个方法执行了5分钟还没好还是会释放锁）
			jedis.expire(key, TIME_FIVE_MINUTES);
		} catch (Exception e) {
			logger.error("jedis set error", e);
			success = false;
			isPass = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
		if(success && !isPass){
			unLock(key);
		}
		return isPass;
	}
	
	/**
	 * 解锁
	 * 实际调用删除缓存方法
	 */
	public static void unLock(String key){
		remove(key);
	}
	
	/**
	 * 单次存储
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            保存时间，单位：秒
	 */
	public static void set(String key, String value, int seconds) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加hash对象
	 * 
	 * @param key
	 * @param hash
	 */
	public static void setHash(String key, Map<String, String> hash) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.hmset(key, hash);
		} catch (JedisException e) {
			logger.error("jedis setHash error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置hash中某个域的值(已存在的会覆盖)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void setHashField(String key, String field, String value) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.hset(key, field, value);
		} catch (JedisException e) {
			logger.error("jedis setHashField error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 批量删除hash域中多个值
	 * 
	 * @param key
	 * @param fields
	 */
	public static void removeHashField(String key, String... fields) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.hdel(key, fields);
		} catch (JedisException e) {
			logger.error("jedis batchRemoveHashField error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 往zset中添加元素每次score增加1
	 * 
	 * @param key
	 * @param member
	 */
	public static void zIncre(String key, String member) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.zincrby(key, 1, member);
		} catch (JedisException e) {
			logger.error("jedis batchRemoveHashField error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}

	}

	/**
	 * 获取hash中某个域的值
	 * 
	 * @param key
	 * @param field
	 */
	public static String getHashField(String key, String field) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.hget(key, field);
		} catch (JedisException e) {
			logger.error("jedis getHashField error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 判断hash中的某个域是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean checkHExists(String key, String field) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.hexists(key, field);
		} catch (JedisException e) {
			logger.error("jedis checkHExists error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return false;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取hash中指定域的值(返回值顺序同field顺序)
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static List<String> getHashFields(String key, String... fields) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.hmget(key, fields);
		} catch (JedisException e) {
			logger.error("jedis getHashFields error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 将名称为key的hash中field的value增加integer
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hincrby(String key, String field, int value) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.hincrBy(key, field, value);
		} catch (JedisException e) {
			logger.error("jedis hincrby error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 批量设置key,value
	 * 
	 * @param map
	 */
	public static void setBatch(Map<String, String> map) {
		Jedis jedis = null;

		boolean success = true;
		try {
			jedis = pool.getResource();
			for (String key : map.keySet()) {
				jedis.set(key, map.get(key));
			}
		} catch (JedisException e) {
			e.printStackTrace();
			logger.error("jedis setBatch error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 从redis获取key对应的value值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (JedisException e) {
			logger.error("jedis get error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 批量删除key对应的value值
	 * 
	 * @param keys
	 * @return
	 */
	public static long remove(String... keys) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.del(keys);
		} catch (JedisException e) {
			logger.error("jedis remove error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return 0;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 数值增加value
	 * 
	 * @param key
	 * @param value
	 */
	public static void incrby(String key, int value) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.incrBy(key, value);
		} catch (JedisException e) {
			logger.error("jedis incrby error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public static void expire(String key, int seconds) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.expire(key, seconds);
		} catch (JedisException e) {
			logger.error("jedis incrby error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 数值减少value
	 * 
	 * @param key
	 * @param value
	 */
	public static void decrby(String key, int value) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.decrBy(key, value);
		} catch (JedisException e) {
			logger.error("jedis incrby error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public static Set<String> getSet(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.smembers(key);
		} catch (JedisException e) {
			logger.error("jedis get error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public static long llen(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.llen(key);
		} catch (JedisException e) {
			logger.error("jedis lpush error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return 0;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 在list的头部添加数据
	 * 
	 * @param key
	 * @param values
	 */
	public static void lpush(String key, String... values) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.lpush(key, values);
		} catch (JedisException e) {
			logger.error("jedis lpush error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回list头部首个元素并从list中删除
	 * 
	 * @param key
	 * @return
	 */
	public static String lpop(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.lpop(key);
		} catch (JedisException e) {
			logger.error("jedis lpop error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 在list的尾部添加数据
	 * 
	 * @param key
	 * @param values
	 */
	public static void rpush(String key, String... values) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.rpush(key, values);
		} catch (JedisException e) {
			logger.error("jedis rpush error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回list尾部首个元素并从list中删除
	 * 
	 * @param key
	 * @return
	 */
	public static String rpop(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.rpop(key);
		} catch (JedisException e) {
			logger.error("jedis rpop error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * list按首字母排序
	 * 
	 * @param key
	 * @param limit
	 *            小于等于0时返回全部
	 * @return
	 */
	public static List<String> sortListByAlpha(String key, int limit) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			SortingParams sortingParameters = new SortingParams();
			sortingParameters.alpha();
			if (limit > 0) {
				sortingParameters.limit(0, limit);
			}
			return jedis.sort(key, sortingParameters);
		} catch (JedisException e) {
			logger.error("jedis sortListByAlpha error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public static void pipeSet(Map<String, String> map) {
		Jedis jedis = pool.getResource();
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for (String key : map.keySet()) {
			pipeline.set(key, map.get(key));
		}

		pipeline.exec();
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		pool.returnResource(jedis);
	}

	public static List<String> lrange(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.lrange(key, 0, -1);
		} catch (JedisException e) {
			logger.error("jedis get error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加元素member
	 * 
	 * @param key
	 * @param member
	 */
	public static void sadd(String key, String... members) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.sadd(key, members);
		} catch (JedisException e) {
			logger.error("jedis sadd error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public static void srem(String key, String... members) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			jedis.srem(key, members);
		} catch (JedisException e) {
			logger.error("jedis sadd error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取key对应的所有元素
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.smembers(key);
		} catch (JedisException e) {
			logger.error("jedis smembers error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回key中所对应的所有键及对应值
	 * 
	 * @param key
	 */
	public static Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = pool.getResource();
			return jedis.hgetAll(key);
		} catch (JedisException e) {
			logger.error("jedis hgetall error", e);
			success = false;
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			return null;
		} finally {
			if (success && jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public static Jedis getJedis() {
		return pool.getResource();
	}

	public static void closeJedis(Jedis jedis) {
		pool.returnResource(jedis);
	}

	public static void closeBrokenJedis(Jedis jedis) {
		pool.returnBrokenResource(jedis);
	}

}
