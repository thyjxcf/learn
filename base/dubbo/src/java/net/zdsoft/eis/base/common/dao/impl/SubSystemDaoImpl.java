package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.SubSystemDao;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;

public class SubSystemDaoImpl extends BaseDao<SubSystem> implements
		SubSystemDao {

	private static final String SQL_INSERT_SUBSYSTEM = "INSERT INTO sys_subsystem(id,name,code,"
			+ "markbit,rolegroup,index_url,mark,image,iscontrolbyperm,build,"
			+ "curversion,orderid,type,server_id,server_type,introduction_page,tab_short_page,"
			+ "description,can_distributed,init_unit_open,multi_screen,parentid,source,sort_type) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_UPDATE_SUBSYSTEM = "UPDATE sys_subsystem SET name=?,code=?,"
			+ "markbit=?,rolegroup=?,index_url=?,mark=?,image=?,iscontrolbyperm=?,build=?,"
			+ "curversion=?,orderid=?,type=?,server_id=?,server_type=?,introduction_page=?,tab_short_page=?,"
			+ "description=?,can_distributed=?,init_unit_open=?,multi_screen=?,parentid=?,source=?,sort_type=? WHERE id=?";

	private static final String SQL_FIND_SUBSYSTEMS_BY_NAME = "SELECT * FROM sys_subsystem WHERE name = ? ";

	private static final String SQL_FIND_SUBSYSTEM_BY_IDS = "SELECT * FROM sys_subsystem WHERE id IN ";

	private static final String SQL_FIND_SUBSYSTEMS = "SELECT * FROM sys_subsystem ORDER BY orderid ASC";
	
	private static final String SQL_FIND_SUBSYSTEMS_BY_CODE = "SELECT * FROM sys_subsystem WHERE code = ? ";

	private static final String SQL_FIND_SPECIAL_SUBSYSTEM_BY_IDS = "SELECT * FROM sys_subsystem WHERE can_distributed =0 and id IN ";

	private static final String SQL_FIND_THIRD_PART_SUBSYSTEMS = "SELECT * FROM sys_subsystem WHERE source = ? order by orderid";

	private static final String SQL_DELETE_SUBSYSTEM = "delete from sys_subsystem where source = 2 and id = ?";
	
	public SubSystem setField(ResultSet rs) throws SQLException {
		SubSystem subSystem = new SubSystem();
		subSystem.setId(rs.getLong("id"));
		subSystem.setName(rs.getString("name"));
		subSystem.setCode(rs.getString("code"));
		subSystem.setMarkbit(rs.getInt("markbit"));
		subSystem.setRolegroup(rs.getString("rolegroup"));
		subSystem.setIndexUrl(rs.getString("index_url"));
		subSystem.setMark(rs.getInt("mark"));
		subSystem.setImage(rs.getString("image"));
		subSystem.setIsControlByPerm(rs.getString("iscontrolbyperm"));
		subSystem.setBuild(rs.getString("build"));
		subSystem.setCurVersion(rs.getString("curversion"));
		subSystem.setOrderid(rs.getInt("orderid"));
		subSystem.setType(rs.getInt("type"));
		subSystem.setServerId(rs.getInt("server_id"));
		subSystem.setServerType(rs.getInt("server_type"));
		subSystem.setIntroductionPage(rs.getString("introduction_page"));
		subSystem.setTabShortPage(rs.getString("tab_short_page"));
		subSystem.setDescription(rs.getString("description"));
		subSystem.setCanDistributed(rs.getInt("can_distributed"));
		subSystem.setInitUnitOpen(rs.getInt("init_unit_open"));
		subSystem.setMultiScreen(rs.getInt("multi_screen"));
		subSystem.setParentId(rs.getInt("parentid"));
		subSystem.setSource(rs.getString("source"));
		subSystem.setSortType(rs.getString("sort_type"));
		return subSystem;
	}

	public void insertSubSystem(SubSystem subSystem) {
		update(SQL_INSERT_SUBSYSTEM,
				new Object[] { subSystem.getId(), subSystem.getName(),
						subSystem.getCode(), subSystem.getMarkbit(),
						subSystem.getRolegroup(), subSystem.getIndexUrl(),
						subSystem.getMark(), subSystem.getImage(),
						subSystem.getIsControlByPerm(), subSystem.getBuild(),
						subSystem.getCurVersion(), subSystem.getOrderid(),
						subSystem.getType(), subSystem.getServerId(),
						subSystem.getServerType(),
						subSystem.getIntroductionPage(),
						subSystem.getTabShortPage(),
						subSystem.getDescription(),
						subSystem.getCanDistributed(),
						subSystem.getInitUnitOpen(),
						subSystem.getMultiScreen(), subSystem.getParentId(),
						subSystem.getSource(),subSystem.getSortType() },
				new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
						Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
						Types.INTEGER, Types.VARCHAR, Types.CHAR,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.INTEGER, Types.CHAR, Types.CHAR });
	}

	public void updateSubSystem(SubSystem subSystem) {
		update(SQL_UPDATE_SUBSYSTEM,
				new Object[] { subSystem.getName(), subSystem.getCode(),
						subSystem.getMarkbit(), subSystem.getRolegroup(),
						subSystem.getIndexUrl(), subSystem.getMark(),
						subSystem.getImage(), subSystem.getIsControlByPerm(),
						subSystem.getBuild(), subSystem.getCurVersion(),
						subSystem.getOrderid(), subSystem.getType(),
						subSystem.getServerId(), subSystem.getServerType(),
						subSystem.getIntroductionPage(),
						subSystem.getTabShortPage(),
						subSystem.getDescription(),
						subSystem.getCanDistributed(),
						subSystem.getInitUnitOpen(),
						subSystem.getMultiScreen(), subSystem.getParentId(),
						subSystem.getSource(),subSystem.getSortType(), subSystem.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.VARCHAR, Types.CHAR, Types.VARCHAR,
						Types.VARCHAR, Types.INTEGER, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.CHAR, Types.CHAR, Types.CHAR });
	}

	public SubSystem getSubSystemByCode(String code) {
		return query(SQL_FIND_SUBSYSTEMS_BY_CODE, code, new SingleRow());
	}

	public boolean isRepeatSubSystemName(String name) {
		return query(SQL_FIND_SUBSYSTEMS_BY_NAME, name, new MultiRow()).size() > 0 ? true
				: false;
	}

	public List<SubSystem> getSubSystems() {
		return query(SQL_FIND_SUBSYSTEMS, new MultiRow());
	}

	public List<SubSystem> getSubSystems(Integer... ids) {
		return queryForInSQL(SQL_FIND_SUBSYSTEM_BY_IDS, null, ids,
				new MultiRow());
	}

	public List<SubSystem> getSpecialSubSystems(Integer... ids) {
		return queryForInSQL(SQL_FIND_SPECIAL_SUBSYSTEM_BY_IDS, null, ids,
				new MultiRow());
	}

	public List<SubSystem> getThirdPartAppSubSystems(String source) {
		return query(SQL_FIND_THIRD_PART_SUBSYSTEMS, new Object[] { source },
				new MultiRow());
	}
	
	public List<SubSystem> getSubSystemsByConditions(String searchName, String searchCode, String searchSortType, String searchSource, Pagination page){
		StringBuffer sql = new StringBuffer("select * from sys_subsystem where 1=1 ");
		List<Object> objs = new ArrayList<Object>();
		if(StringUtils.isNotBlank(searchName)){
			sql.append(" and name like ?");
			objs.add("%" + searchName + "%");
		}
		if(StringUtils.isNotBlank(searchCode)){
			sql.append(" and code like ?");
			objs.add("%" + searchCode + "%");
		}
		if(StringUtils.isNotBlank(searchSortType)){
			sql.append(" and sort_type = ?");
			objs.add(searchSortType);
		}
		if(StringUtils.isNotBlank(searchSource)){
			sql.append(" and source = ?");
			objs.add(searchSource);
		}
		sql.append(" order by orderid asc");
		if(page != null){
			return query(sql.toString(), objs.toArray(), new MultiRow(), page);
		}else{
			return query(sql.toString(), objs.toArray(), new MultiRow());
		}
	}

    public void deleteSubSystem(Integer subSystemId){
		update(SQL_DELETE_SUBSYSTEM, new Object[]{subSystemId});
    }
    
    public int countMaxId(){
    	String sql = "select max(id) from sys_subsystem"; 
    	return queryForInt(sql);
    }
}
