package com.nyfz.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.nyfz.util.Constants.SYSTEM_PROP_CONFIG;

public class JedisUtil {
	Logger LOGGER = LoggerFactory.getLogger(getClass());
	//private static final  Logger LOGGER = LoggerFactory.getUtilLog(JedisUtil.class);

	private static Set<HostAndPort> jedisClusterNodes = new HashSet<>();
	private static JedisCluster jedisCluster = null;
	private static BinaryJedisCluster binaryCluster = null;
	
	
	private JedisUtil(){}
	/**
	 * 获取JedisCluster对象
	 * @return
	 * @throws Exception
	 */
	public static JedisCluster getCluster() throws Exception {
		if (jedisCluster == null) {
			synchronized (JedisUtil.class) {
				GenericObjectPoolConfig config = new GenericObjectPoolConfig();
				config.setMaxTotal(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_TOTAL));
				config.setMaxIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_IDLE));
				config.setMinIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MIN_IDLE));
				
				jedisCluster = new JedisCluster(getHostNodes(),
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_CONN_TIMEOUT), 
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_SOCK_TIMEOUT), 
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_MAX_REDIRECT),  config);
			}
		}
		return jedisCluster;
	}

	/**
	 * 获取BinaryJedisCluster对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public static BinaryJedisCluster getBinaryCluster() throws Exception{
		if (binaryCluster == null) {
			synchronized (JedisUtil.class) {
				GenericObjectPoolConfig config = new GenericObjectPoolConfig();
				config.setMaxTotal(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_TOTAL));
				config.setMaxIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MAX_IDLE));
				config.setMinIdle(PropertiesUtil.getInt(SYSTEM_PROP_CONFIG.REDIS_MIN_IDLE));
				// 借用接口测试开关,测试环境redis配置添加
				//if(CacheUtil.exists(RedisKey.REDIS_INTERFACE_TEST)){
				//	config.setTestOnBorrow(true);
				//}
				
				binaryCluster = new BinaryJedisCluster(getHostNodes(),
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_CONN_TIMEOUT), 
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_SOCK_TIMEOUT), 
						PropertiesUtil.getInt(Constants.SYSTEM_PROP_CONFIG.REDIS_MAX_REDIRECT), config);
			}
		}
		return binaryCluster;
	}
	
	@SuppressWarnings("deprecation")
	public static Set<HostAndPort> getHostNodes() throws Exception {
		//LOGGER.info("getHostNodes","初始化RedisNodes");
		if (!jedisClusterNodes.isEmpty()) {
			return jedisClusterNodes;
		}
		String redisAddrCfg = PropertiesUtil.getString(SYSTEM_PROP_CONFIG.REDIS_ADDR_CFG);
		if (StringUtil.isEmpty(redisAddrCfg) || redisAddrCfg.split(SYSTEM_PROP_CONFIG.REDIS_CFG_SPLIT).length == 0) {
			throw new Exception("System.properties中REDIS_ADDR_CFG属性为空");
		}
		String[] addrs = redisAddrCfg.split(SYSTEM_PROP_CONFIG.REDIS_CFG_SPLIT);
		for (String addr : addrs) {
			String regex="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))):(\\d+)";
			if(!addr.matches(regex)){
				throw new Exception("System.properties中REDIS_ADDR_CFG属性配置错误");
			}
			String[] ipAndPort = addr.split(":");
			
			jedisClusterNodes.add(new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1])));
		}
		
		return jedisClusterNodes;
	}
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100000; i++) {
			new Thread(
				new Runnable() {
					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						try {
							for (int j = 0; j < 10; j++) {
							 long id = getCluster().incr("nihaoa");
							//LOGGER.info("main",Thread.currentThread().getName() + " " + id);
							}
						} catch (Exception e) {
							//LOGGER.error("main", e.getMessage(), e);
						}
					}
				}
			,Integer.toString(i)).start();
			
		}
}
}
