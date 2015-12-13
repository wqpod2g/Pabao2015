package nju.iip.redis;

import redis.clients.jedis.Jedis;

public class test {

	public static void main(String[] args) {
		// 连接本地的 Redis 服务
		Jedis jedis = JedisPoolUtils.getInstance().getJedis();
		System.out.println("Connection to server sucessfully");
		// 设置 redis 字符串数据
		// 获取存储的数据并输出
		System.out.println(jedis.zrank("game1","wcj"));
		JedisPoolUtils.getInstance().returnRes(jedis);
	}

}
