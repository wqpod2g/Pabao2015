package nju.iip.dao;

import java.util.List;

import nju.iip.dto.GameScore;
import nju.iip.redis.JedisPoolUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class ScoreDAO extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreDAO.class);
	
	/**
	 * 根据openId判断用户是否玩过游戏
	 * @param openId
	 * @return
	 */
	public boolean checkIsExist(String openId,String game) {
		boolean flag = false;
		try{
			begin();
			getSession().flush();
			getSession().clear();
			Query q = getSession().createQuery("from GameScore where openId = :openId and game=:game");
			q.setString("openId", openId);
			q.setString("game", game);
			GameScore gs = (GameScore)q.uniqueResult();
			if(gs!=null) {
				flag = true;
			}
		}catch (HibernateException e) {
			rollback();
			logger.info("ScoreDAO-->checkIsExist",e);
		}
		return flag;
	}
	/**
	 * 增加新用户得分
	 * @param gs
	 * @return
	 */
	public boolean addScore(GameScore gs) {
		try{
			begin();
			getSession().save(gs);
    		commit();
    		return true;
		}catch (HibernateException e) {
			rollback();
			logger.info("ScoreDAO-->addScore",e);
		}
		return false;
	}
	
	public int getScore(String openId,String game) {
		int score = 0;
		try{
			begin();
			getSession().flush();
			getSession().clear();
			Query q = getSession().createQuery("from GameScore where openId = :openId and game=:game");
			q.setString("openId", openId);
			q.setString("game", game);
			GameScore gs = (GameScore)q.uniqueResult();
			commit();
			score = gs.getScore();
		}catch(HibernateException e) {
			rollback();
			logger.info("ScoreDAO-->getScore",e);
		}
		return score;
	}
	
	public void updateScore(GameScore gs) {
		try{
			begin();
			Query query = getSession().createQuery("update GameScore g set g.score = :score where openId= :openId and game=:game");
			query.setInteger("score", gs.getScore());
			query.setString("openId", gs.getOpenId());
			query.setString("game",gs.getGame());
			query.executeUpdate();
			commit();
		}catch(HibernateException e) {
			rollback();
			logger.info("ScoreDAO-->updateScore",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GameScore> getAllScore(String game) {
		List<GameScore> list = null;
		try{
			begin();
			getSession().flush();
			getSession().clear();
			Query query = getSession().createQuery("from GameScore where game=:game order by score desc");
			query.setString("game", game);
			query.setMaxResults(8); 
			list = query.list();
		}catch(HibernateException e) {
			rollback();
			logger.info("getAllScore-->updateScore",e);
		}
		return list;
	}
	
	public static void main(String[] args) {
		ScoreDAO sd = new ScoreDAO();
		Jedis jedis = JedisPoolUtils.getInstance().getJedis();//jedis实例
		logger.info(jedis.ping());
		List<GameScore> list = sd.getAllScore("CrazyFinger2");
		for(GameScore gamescore:list) {
			jedis.zadd(gamescore.getGame(), gamescore.getScore(),gamescore.getOpenId());
		}
		JedisPoolUtils.getInstance().returnRes(jedis);//释放redis连接
	}
}
