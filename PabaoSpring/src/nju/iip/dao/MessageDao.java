package nju.iip.dao;

import java.util.List;
import nju.iip.dto.Message;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 私信消息DAO
 * 
 * @author wangqiang
 * 
 */
@Service
public class MessageDao extends DAO {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageDao.class);

	// 将新的私信消息插入数据库
	public boolean addMessage(Message message) {
		boolean flag = false;
		try {
			begin();
			getSession().save(message);
			commit();
			flag = true;
		} catch (HibernateException e) {
			rollback();
			logger.info("MessageDao-->addMessage failed", e);
		}
		return flag;
	}

	// 读取用户的私信消息
	@SuppressWarnings("unchecked")
	public List<Message> getMessage(String openId) {
		List<Message> list = null;
		try {
			begin();
			getSession().flush();
			getSession().clear();
			Query query = getSession().createQuery(
					"from Message where toOpenId=:toOpenId");
			query.setString("toOpenId", openId);
			list = query.list();
		} catch (HibernateException e) {
			rollback();
			logger.info("MessageDao-->getMessage failed", e);
		}
		return list;
	}

	/**
	 * 标记私信消息已读过
	 * 
	 * @return
	 */
	public boolean updateIsRead(int id) {
		boolean flag = false;
		try {
			begin();
			Query query = getSession().createQuery("update Message m set m.isRead=1 where m.id=:id");
			query.setInteger("id", id);
			if(query.executeUpdate()==1) {
				flag = true;
			}
			commit();
		} catch (HibernateException e) {
			rollback();
			logger.info("MessageDao-->updateIsRead failed", e);
		}
		return flag;
	}

	public static void main(String[] args) {
		MessageDao md = new MessageDao();
		logger.info(md.updateIsRead(4) + "");
	}

}
