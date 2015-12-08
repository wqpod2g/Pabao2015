package nju.iip.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nju.iip.dto.UserLocation;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LocationDao extends DAO {
	
	private static final Logger logger = LoggerFactory.getLogger(LocationDao.class);
	

	/**
	 * 判断用户位置是否被记录过
	 * 
	 * @param openId
	 * @return
	 */
	public boolean isLocated(String openId) {
		boolean flag = false;
		try{
			begin();
			getSession().flush();
			getSession().clear();
			Query query = getSession().createQuery("from UserLocation where openId=:openId");
			query.setString("openId", openId);
			UserLocation location = (UserLocation)query.uniqueResult();
			if(location!=null) {
				flag = true;
			}
		}catch (HibernateException e) {
			rollback();
			logger.info("LocationDao-->isLocated",e);
		}
		return flag;
	}
	
	/**
	 * 更新用户的坐标
	 * @param Latitude
	 * @param Longitude
	 * @param openId
	 * @return
	 */
	public void updateUserLocation(UserLocation location) {
		Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
    	String time = dateFormat.format(now);
    	location.setTime(time);
    	try{
    		begin();
    		getSession().flush();
			getSession().clear();
    		getSession().saveOrUpdate(location);
    		commit();
    	}catch (HibernateException e) {
			rollback();
			logger.info("LocationDao-->updateUserLocation",e);
		}
	}
	
	/**
	 * 获取所有用户的坐标信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserLocation> getAllUserLocation() {
		List<UserLocation> list = null;
		try{
			begin();
			getSession().flush();
			getSession().clear();
			Query query = getSession().createQuery("from UserLocation");
			list = query.list();
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("LocationDao-->getAllUserLocation",e);
		}
		return list;
	}
	
	public static void main(String[] args) {
		LocationDao ld = new LocationDao();
		System.out.println(ld.getAllUserLocation().size());
	}

}
