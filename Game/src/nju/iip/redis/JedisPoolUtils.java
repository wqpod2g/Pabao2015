package nju.iip.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nju.iip.util.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池
 * 
 * @author wangqiang
 * 
 */
public class JedisPoolUtils {

	private static final Logger logger = LoggerFactory.getLogger(JedisPoolUtils.class);

	private static JedisPoolUtils jedisPoolUtils;// 单例

	private JedisPool pool;// redis连接池

	private JedisPoolUtils() {
		logger.info("开始初始化redis连接池...");
		initialJedisPool();
		logger.info("初始化redis连接池完毕...");
	}
	
	
	/**
	 * 获得单例
	 * @return
	 */
	public static JedisPoolUtils getInstance() {
		if (jedisPoolUtils == null) {
			synchronized(JedisPoolUtils.class) {
				if(jedisPoolUtils == null) {
					jedisPoolUtils = new JedisPoolUtils();
				}
			}
		}
		return jedisPoolUtils;
	}

	public void initialJedisPool() {
		if (pool == null) {
			// 建立连接池配置参数
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置最大连接数
			config.setMaxActive(30);
			// 设置最大阻塞时间，记住是毫秒数milliseconds
			config.setMaxWait(1000);
			// 设置空间连接
			config.setMaxIdle(10);
			// 创建连接池
			pool = new JedisPool(config, Config.getValue("redis_ip"), 6379);
		}
	}


	/**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 */
	public Jedis getJedis() {
		try{
			return pool.getResource();
		}catch(Exception e) {
			logger.info("getJedis error",e);
		}
		return null;
	}
	
	 /**
     * 归还一个连接
     * 
     * @param jedis
     */
    public void returnRes(Jedis jedis) {
    	try{
    		pool.returnResource(jedis);
    	    logger.info("release redis connection ok");
    	}catch(Exception e) {
			logger.info("returnRes error",e);
		}
    }

}
