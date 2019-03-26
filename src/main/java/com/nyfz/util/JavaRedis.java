package com.nyfz.util;

import redis.clients.jedis.Jedis;

public class JavaRedis {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		System.out.println("success");
		jedis.set("111", "success_mykey_vale");
		System.out.println(jedis.get("111"));
	}
}
