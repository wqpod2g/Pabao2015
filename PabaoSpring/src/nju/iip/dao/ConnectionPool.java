package nju.iip.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import nju.iip.util.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接池类
 * 
 * @author wangqiang
 * 
 */
public class ConnectionPool {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

	private int max_connection = Integer.valueOf(Config.getValue("max_connection"));// 最大连接数

	private int min_connection = Integer.valueOf(Config.getValue("min_connection"));// 最小连接数

	private Map<Connection,String> connection_map;// 存放连接池的容器

	private static ConnectionPool connectPool;// 单例
	
	private ConnectionPool() {
		logger.info("创建连接池....");
		intializePool();
		logger.info("创建连接池成功....共"+connection_map.size()+"个连接");
	}

	/**
	 * 获取连接池单例
	 * 
	 * @return connectPool
	 */
	public static ConnectionPool getInstance() {
		if (connectPool == null) {
			synchronized (ConnectionPool.class) {
				if (connectPool == null) {
					connectPool = new ConnectionPool();
				}
			}
		}
		return connectPool;
	}

	public void intializePool() {
		if (connection_map != null) {
			return;
		}
		connection_map = new HashMap<Connection,String>();
		try {
			for (int i = 0; i < min_connection; i++) {
				connection_map.put(getNewConnection(),"free");
			}
		} catch (Exception e) {
			logger.info("intializePool error", e);
		}
	}

	public Connection getNewConnection() {
		Connection conn = null;
		try {
			Class.forName(Config.getValue("DBDRIVER"));
			conn = DriverManager.getConnection(Config.getValue("DBURL"), Config.getValue("DBUSER"),Config.getValue("DBPASSWORD"));
		} catch (Exception e) {
			logger.info("getNewConnection error", e);
		}
		return conn;
	}

	/**
	 * 获取一个连接
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection conn = null;
		for (Entry<Connection, String> entry : connection_map.entrySet()) {
			if (entry.getValue().equals("free")) {
				conn = entry.getKey();
				connection_map.put(conn,"busy");
				break;
			}
		}
		if (conn == null) {
			if (connection_map.size() <max_connection) {
				conn = getNewConnection();//新建一个连接
				connection_map.put(conn,"busy");
				logger.info("no free connection,add new connection ok!");
			} 
			else {
				logger.info("reach max_connction!start watting...");
				try{
					this.wait();
				}catch(Exception e){
					e.printStackTrace();
				}
                conn = getConnection();
            }
		}
		return conn;
	}
	

	/**
	 * 释放连接
	 * @param myconnection
	 */
	public synchronized void releaseConnection(Connection conn) {
		if(conn == null) {
            return;
        }
		 try{
			 if(connection_map.containsKey(conn)) {
				 if(conn.isClosed()) {
					 connection_map.remove(connectPool);
				 }
				 else{
	                 connection_map.put(conn,"free");   
	                 logger.info("releaseConnection ok...");
	                 this.notify();
				 }
	         } 
			 else {
				 conn.close();
	         }
		 }catch(Exception e){
			 logger.info("releaseConnection error", e);
		 }
	 }

}
