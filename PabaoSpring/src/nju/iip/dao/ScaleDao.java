package nju.iip.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nju.iip.dto.Options;
import nju.iip.dto.Questions;
import nju.iip.dto.Scale;
import nju.iip.dto.ScaleRecord;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScaleDao extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(ScaleDao.class);
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	@SuppressWarnings("unchecked")
	public List<Scale> getScaleList() {
		List<Scale> list = null;
		try{
			begin();
			Query query = getSession().createQuery("from Scale");
			list = query.list();
		}catch (HibernateException e) {
			rollback();
			logger.info("ScaleDao-->getScaleList",e);
		}
		return list;
	}
	
	/**
	 * 根据totalScaleId 从parkinsontotalscale表中取出对应量表的信息
	 * @param totalScaleId
	 * @return scale
	 */
	public Scale getScale(int totalScaleId) {
		Scale scale = null;
		try{
			begin();
			Query query = getSession().createQuery("from Scale where id=:id");
			query.setInteger("id", totalScaleId);
			scale = (Scale)query.uniqueResult();
		}catch (HibernateException e) {
			rollback();
			logger.info("ScaleDao-->getScale",e);
		}
		return scale;
	}
	
	
	/**
	 * 根据totalScaleId 从parkinsonscale表中取出所有题目的questionId
	 * @param totalScaleId
	 * @return
	 */
	public  List<Integer> getQuestionId(int totalScaleId) {
		List<Integer> questionId_list = new ArrayList<Integer>();
		String sql = "select * from parkinsonscale where totalScaleId='"+totalScaleId+"'";
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				int questionId = rs.getInt("questionId");//取出questionId
				questionId_list.add(questionId);
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		finally {
			closeDB();
		}
		return questionId_list;
	}
	
	
	/**
	 * 根据questionId_list取出所有对应题目和选项
	 * @param questionId_list
	 * @return List<Questions> question_list
	 */
	public  List<Questions> getQuestions(List<Integer> questionId_list) {
		List<Questions> question_list = new ArrayList<Questions>();
		conn = ConnectionPool.getInstance().getConnection();
		try {
			for(Integer questionId:questionId_list) {
				Questions question = new Questions();
				String sql = "select * from scalequestion where id='"+questionId+"'";
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				//取出题目的内容
				if(rs.next()) {
					String showType = rs.getString("showType");
					int indexParent = rs.getInt("indexParent");
					if(rs.getString("showType").equals("cut")||(showType.equals("matrix")&&indexParent==0)) continue;
					question.setQuestionContent(rs.getString("questionContent"));
					question.setShowType(showType);
					
				}
				List<Options> options = new ArrayList<Options>();
				sql = "select * from questionoption where questionId='"+questionId+"'";
				ps=conn.prepareStatement(sql);
				rs=ps.executeQuery();
				//取出题目对应的选项
				while(rs.next()) {
					Options option = new Options();
					option.setOptionContent(rs.getString("optionContent"));
					option.setOptionValue(rs.getString("optionValue"));
					options.add(option);
				}
				question.setAnswers(options);
				question.setQuestionId(questionId);
				question_list.add(question);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			closeDB();
		}
		return question_list;
	}
	
	
	/**
	 * 将用户量表填写结果存入数据库表
	 * @param openId
	 * @param scale
	 * @param score
	 * @return
	 */
	public  boolean storeResult(String openId,Scale scale,String score) {
		Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
    	String time = dateFormat.format(now);
		String sql = "insert into weixin_scaleresult(openId,scaleId,scaleName,score,time) values(?,?,?,?,?)";
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			ps.setInt(2, scale.getId());
			ps.setString(3, scale.getScaleName());
			ps.setString(4, score);
			ps.setString(5, time);
			return ps.executeUpdate() == 1 ? true : false;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}
	
	
	/**
	 * 根据openId取出用户填表信息
	 * @param openId
	 * @return
	 */
	public  List<ScaleRecord> getScaleRecord(String openId) {
		List<ScaleRecord> record_listList = new ArrayList<ScaleRecord>();
		String sql = "select * from weixin_scaleresult where openId='"+openId+"'";
		try {
			conn = ConnectionPool.getInstance().getConnection();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				ScaleRecord record = new ScaleRecord();
				record.setScaleName(rs.getString("scaleName"));
				record.setScore(rs.getString("score"));
				record.setTime(rs.getString("time"));
				record_listList.add(record);
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		finally {
			closeDB();
		}
		return record_listList;
	}
	
	
	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ConnectionPool.getInstance().releaseConnection(conn);
	}
	
	
	public static void main(String[] args) {
		ScaleDao sd = new ScaleDao();
		logger.info(sd.getQuestions(sd.getQuestionId(Integer.valueOf(96))).size()+"");
	}

}
