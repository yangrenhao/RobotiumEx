package com.xracoon.rato;

import java.net.InetSocketAddress;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XMemcacheClientHelper {
	private String serverAddress = "";

	public XMemcacheClientHelper(String serverAddress, Integer port) {
		this.serverAddress = (serverAddress + ":" + String.valueOf(port));
	}

	public Object getValue(String key) throws Exception {
		MemcachedClient memcachedClient = null;
		Object result = null;
		try {
			memcachedClient = getClient(this.serverAddress);
			result = memcachedClient.get(key);
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
		return result;
	}

	public void setValue(String key, Integer expireTime, Object value)
			throws Exception {
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = getClient(this.serverAddress);
			memcachedClient.set(key, expireTime.intValue(), value);
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}

	public void delete(String key) {
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = getClient(this.serverAddress);
			memcachedClient.delete(key);
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}

	public void flushServer(String serverAddress, Integer port) {
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = getClient(serverAddress + ":"
					+ String.valueOf(port));
			InetSocketAddress address = new InetSocketAddress(serverAddress,
					port.intValue());
			memcachedClient.flushAll(address);
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}

	public void replace(String key, Integer expireTime, Object value) {
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = getClient(this.serverAddress);
			memcachedClient.replace(key, expireTime.intValue(), value);
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}

	public void touch(String key, Integer expireTime) {
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = getClient(this.serverAddress);
			memcachedClient.touch(key, expireTime.intValue());
			shutDown(memcachedClient);
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}

	private MemcachedClient getClient(String serverAddress) throws Exception {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses(serverAddress));
		MemcachedClient memcachedClient = builder.build();
		return memcachedClient;
	}

	private void shutDown(MemcachedClient memcachedClient) {
		try {
			memcachedClient.shutdown();
		} catch (Exception e) {
			//Logger.getRootLogger().error(e.getMessage());
		}
	}
}
