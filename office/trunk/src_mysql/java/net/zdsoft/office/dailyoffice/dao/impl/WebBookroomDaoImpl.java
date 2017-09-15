package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.WebBookroomDao;
import net.zdsoft.office.dailyoffice.entity.WebBookroom;
/**
 * web_bookroom1
 * @author 
 * 
 */
public class WebBookroomDaoImpl extends BaseDao<WebBookroom> implements WebBookroomDao{

	private static final String SQL_INSERT = "insert into web_bookroom1(staffid, controllerid, doorno, begindatetime, enddatetime, sendflag) values(?,?,?,?,?,?)";
	
	@Override
	public WebBookroom setField(ResultSet rs) throws SQLException{
		WebBookroom webBookroom = new WebBookroom();
		webBookroom.setStaffid(rs.getString("staffid"));
		webBookroom.setControllerid(rs.getString("controllerid"));
		webBookroom.setDoorno(rs.getInt("doorno"));
		webBookroom.setBegindatetime(rs.getString("begindatetime"));
		webBookroom.setEnddatetime(rs.getString("enddatetime"));
		webBookroom.setSendflag(rs.getInt("sendflag"));
		return webBookroom;
	}

	@Override
	public WebBookroom save(WebBookroom webBookroom){
		
		Object[] args = new Object[]{
			webBookroom.getStaffid(), webBookroom.getControllerid(), 
			webBookroom.getDoorno(), webBookroom.getBegindatetime(), 
			webBookroom.getEnddatetime(), webBookroom.getSendflag()
		};
		update(SQL_INSERT, args);
		return webBookroom;
	}
	
	@Override
	public void batchSave(List<WebBookroom> webBookrooms) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (WebBookroom webBookroom:webBookrooms) {
			Object[] args = new Object[]{
					webBookroom.getStaffid(), webBookroom.getControllerid(), 
					webBookroom.getDoorno(), webBookroom.getBegindatetime(), 
					webBookroom.getEnddatetime(), webBookroom.getSendflag()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.NVARCHAR, Types.NVARCHAR, Types.SMALLINT,
				Types.NVARCHAR, Types.NVARCHAR, Types.SMALLINT
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
	}
	
	@Override
	public void batchUpdate(List<WebBookroom> webBookrooms) {
		String sql = "update web_bookroom1 set sendflag = ? where staffid = ? and controllerid = ? and doorno = ? and begindatetime = ? and enddatetime = ? ";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (WebBookroom webBookroom:webBookrooms) {
			Object[] args = new Object[]{
					webBookroom.getSendflag(), webBookroom.getStaffid(), 
					webBookroom.getControllerid(), webBookroom.getDoorno(),
					webBookroom.getBegindatetime(), webBookroom.getEnddatetime()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.SMALLINT, Types.NVARCHAR, Types.NVARCHAR, 
				Types.SMALLINT, Types.NVARCHAR, Types.NVARCHAR, 
				};
		batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from web_bookroom1 where staffid in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(WebBookroom webBookroom){
		String sql = "update web_bookroom1 set controllerid = ?, doorno = ?, begindatetime = ?, enddatetime = ?, sendflag = ? where staffid = ?";
		Object[] args = new Object[]{
			webBookroom.getControllerid(), webBookroom.getDoorno(), 
			webBookroom.getBegindatetime(), webBookroom.getEnddatetime(), 
			webBookroom.getSendflag(), webBookroom.getStaffid()
		};
		return update(sql, args);
	}

	@Override
	public WebBookroom getWebBookroomById(String id){
		String sql = "select * from web_bookroom1 where staffid = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, WebBookroom> getWebBookroomMapByIds(String[] ids){
		String sql = "select * from web_bookroom1 where staffid in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<WebBookroom> getWebBookroomList(){
		String sql = "select * from web_bookroom1";
		return query(sql, new MultiRow());
	}

	@Override
	public List<WebBookroom> getWebBookroomPage(Pagination page){
		String sql = "select * from web_bookroom1";
		return query(sql, new MultiRow(), page);
	}
}