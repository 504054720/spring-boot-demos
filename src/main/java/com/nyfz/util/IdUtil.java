package com.nyfz.util;

import org.apache.commons.lang3.RandomUtils;

import com.relops.snowflake.Snowflake;

public class IdUtil {

	private static Snowflake s = new Snowflake(RandomUtils.nextInt(0, 1024));
	
	public static long next(){
		return s.next();
	}
}
