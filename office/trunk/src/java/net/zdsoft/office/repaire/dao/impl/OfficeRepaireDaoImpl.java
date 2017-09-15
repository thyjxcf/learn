package net.zdsoft.office.repaire.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ecs.storage.Array;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.repaire.dao.OfficeRepaireDao;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
/**
 * office_repaire
 * @author 
 * 
 */
public class OfficeRepaireDaoImpl extends BaseDao<OfficeRepaire> implements OfficeRepaireDao{

	@Override
	public OfficeRepaire setField(ResultSet rs) throws SQLException{
		OfficeRepaire officeRepaire = new OfficeRepaire();
		officeRepaire.setId(rs.getString("id"));
		officeRepaire.setUnitId(rs.getString("unit_id"));
		officeRepaire.setUserId(rs.getString("user_id"));
		officeRepaire.setTeachAreaId(rs.getString("teach_area_id"));
		officeRepaire.setType(rs.getString("type"));
		officeRepaire.setPhone(rs.getString("phone"));
		officeRepaire.setGoodsName(rs.getString("goods_name"));
		officeRepaire.setGoodsPlace(rs.getString("goods_place"));
		officeRepaire.setCreateTime(rs.getTimestamp("create_time"));
		officeRepaire.setDetailTime(rs.getTimestamp("detail_time"));
		officeRepaire.setRemark(rs.getString("remark"));
		officeRepaire.setState(rs.getString("state"));
		officeRepaire.setRepaireUserId(rs.getString("repaire_user_id"));
		officeRepaire.setRepaireTime(rs.getTimestamp("repaire_time"));
		officeRepaire.setRepaireRemark(rs.getString("repaire_remark"));
		officeRepaire.setFeedback(rs.getString("feedback"));
		officeRepaire.setIsFeedback(rs.getBoolean("is_feedback"));
		officeRepaire.setIsDeleted(rs.getBoolean("is_deleted"));
		officeRepaire.setRepaireTypeId(rs.getString("repaire_type_id"));
		officeRepaire.setEquipmentType(rs.getString("equipment_type"));
		officeRepaire.setClassId(rs.getString("class_id"));
		officeRepaire.setMark(rs.getString("mark"));
		return officeRepaire;
	}
	
	@Override
	public OfficeRepaire save(OfficeRepaire officeRepaire){
		String sql = "insert into office_repaire(id, repaire_type_id,unit_id, user_id, teach_area_id, type, phone, goods_name, goods_place, create_time, detail_time, remark, state, repaire_user_id, repaire_time, repaire_remark, feedback, is_feedback, is_deleted,equipment_type,mark, class_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeRepaire.getId())){
			officeRepaire.setId(createId());
		}
		Object[] args = new Object[]{
			officeRepaire.getId(), officeRepaire.getRepaireTypeId(),officeRepaire.getUnitId(), 
			officeRepaire.getUserId(), officeRepaire.getTeachAreaId(), 
			officeRepaire.getType(), officeRepaire.getPhone(), 
			officeRepaire.getGoodsName(), officeRepaire.getGoodsPlace(), 
			officeRepaire.getCreateTime(), officeRepaire.getDetailTime(), 
			officeRepaire.getRemark(), officeRepaire.getState(), 
			officeRepaire.getRepaireUserId(), officeRepaire.getRepaireTime(), 
			officeRepaire.getRepaireRemark(), officeRepaire.getFeedback(), 
			officeRepaire.getIsFeedback(), officeRepaire.getIsDeleted(),
			officeRepaire.getEquipmentType(),officeRepaire.getMark(),
			officeRepaire.getClassId()
		};
		update(sql, args);
		return officeRepaire;
	}

	@Override
	public void updateState(String[] ids, String state,String userId) {
		if(StringUtils.isNotBlank(state)&&StringUtils.equals(state, "2")){
			String sql="update office_repaire set repaire_user_id=?,state=?, repaire_time=? where id in";
			updateForInSQL(sql, new Object[]{userId,state,new Date()}, ids);
		}
		if(StringUtils.isNotBlank(state)&&StringUtils.equals(state, "3")){
			String sql="update office_repaire set repaire_user_id=?,state=?, repaire_time=? where id in";
			updateForInSQL(sql, new Object[]{userId,state,new Date()}, ids);
		}
		
		//String sql="update office_repaire set state=?, repaire_time=? where id in";
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_repaire set is_deleted = 1 where id in ";
		return updateForInSQL(sql, null,ids);
	}

	@Override
	public Integer update(OfficeRepaire officeRepaire){
		String sql = "update office_repaire set repaire_type_id = ?, teach_area_id = ?, type = ?, phone = ?, goods_name = ?, goods_place = ?,  detail_time = ?, remark = ?,equipment_type = ?, class_id = ?,mark=? where id = ?";
		Object[] args = new Object[]{
			officeRepaire.getRepaireTypeId(), officeRepaire.getTeachAreaId(),
			officeRepaire.getType(), officeRepaire.getPhone(),
			officeRepaire.getGoodsName(), officeRepaire.getGoodsPlace(), 
			officeRepaire.getDetailTime(), officeRepaire.getRemark(), 
			officeRepaire.getEquipmentType(),officeRepaire.getClassId(),officeRepaire.getMark(),
			officeRepaire.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateState(OfficeRepaire officeRepaire) {
		String sql = "update office_repaire set state = ?, repaire_user_id = ?, repaire_time = ?, repaire_remark = ? where id = ?";
		Object[] args = new Object[]{
			officeRepaire.getState(), officeRepaire.getRepaireUserId(), 
			officeRepaire.getRepaireTime(), officeRepaire.getRepaireRemark(),
			officeRepaire.getId()
		};
		update(sql, args);
	}
	
	@Override
	public void updateFeedBack(OfficeRepaire officeRepaire) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update office_repaire set mark =? ");
		args.add(officeRepaire.getMark());
		if(StringUtils.isNotBlank(officeRepaire.getFeedback())){
			sql.append(", feedback =? ");
			args.add(officeRepaire.getFeedback());
		}
		sql.append(", is_feedback = 1 where id = ?");
		args.add(officeRepaire.getId());
		update(sql.toString(), args.toArray());
	}
	
	@Override
	public List<OfficeRepaire> getOfficeRepaireList(String userId,
			String unitID, String areaId, String type, String state,
			Date startTime, Date endTime, String searchContent, Pagination page) {
		StringBuilder sql = new StringBuilder("select * from office_repaire where unit_id = ? and is_deleted = 0");
		List<Object> args = new ArrayList<Object>();
		args.add(unitID);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and user_id = ? ");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(areaId) && !StringUtils.equals(BaseConstant.ZERO_GUID, areaId)){
			sql.append(" and teach_area_id = ? ");
			args.add(areaId);
		}
		if(StringUtils.isNotBlank(type)){
			sql.append(" and type = ? ");
			args.add(type);
		}
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ? ");
			args.add(state);
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (goods_name like ? or goods_place like ?)");
			args.add("%"+searchContent+"%");
			args.add("%"+searchContent+"%");
		}
		if(startTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			args.add(endTime);
		}
		sql.append(" order by state asc ,detail_time desc");
		if(page != null)
			return query(sql.toString(), args.toArray(), new MultiRow(),page);
		else
			return query(sql.toString(), args.toArray(), new MultiRow());
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId,
			String typeId) {
		String sql = "select * from office_repaire where unit_id = ? and is_deleted = 0 and repaire_type_id = ? ";
		return query(sql, new Object[]{unitId,typeId}, new MultiRow());
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireList(String unitID,
			String[] areaIds, String[] types, String state, Date startTime,
			Date endTime, String searchContent, Pagination page) {
		StringBuilder sql = new StringBuilder("select * from office_repaire where unit_id = ? and is_deleted = 0");
		List<Object> args = new ArrayList<Object>();
		args.add(unitID);
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ? ");
			args.add(state);
		}
		if(startTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			args.add(endTime);
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (goods_name like ? or goods_place like ?)");
			args.add("%"+searchContent+"%");
			args.add("%"+searchContent+"%");
		}
		sql.append(" and teach_area_id in" + SQLUtils.toSQLInString(areaIds));
		sql.append(" and type in");
		return queryForInSQL(sql.toString(), args.toArray(), types, new MultiRow(), "  order by state asc ,detail_time desc",page);
	}
	@Override
	public List<OfficeRepaire> getOfficeRepaireListH5(String unitID,
			String[] areaIds, String[] types, String[] state, Date startTime,
			Date endTime, String searchContent, Pagination page) {
		StringBuilder sql = new StringBuilder("select * from office_repaire where unit_id = ? and is_deleted = 0");
		List<Object> args = new ArrayList<Object>();
		args.add(unitID);
		if(state != null){
			sql.append(" and state in"+SQLUtils.toSQLInString(state));
		}
		if(startTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			args.add(endTime);
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (goods_name like ? or goods_place like ?)");
			args.add("%"+searchContent+"%");
			args.add("%"+searchContent+"%");
		}
		sql.append(" and teach_area_id in" + SQLUtils.toSQLInString(areaIds));
		sql.append(" and type in");
		return queryForInSQL(sql.toString(), args.toArray(), types, new MultiRow(), "  order by state asc ,detail_time desc",page);
	}
	@Override
	public int getOfficeRepaireListH5Count(String unitID,
			String[] areaIds, String[] types, String[] state, Date startTime,
			Date endTime, String searchContent) {
		StringBuilder sql = new StringBuilder("select count(*) from office_repaire where unit_id = ? and is_deleted = 0");
		List<Object> args = new ArrayList<Object>();
		args.add(unitID);
		if(state != null){
			sql.append(" and state in"+SQLUtils.toSQLInString(state));
		}
		if(startTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			args.add(startTime);
		}
		if(endTime != null){
			sql.append(" and to_date(to_char(detail_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			args.add(endTime);
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (goods_name like ? or goods_place like ?)");
			args.add("%"+searchContent+"%");
			args.add("%"+searchContent+"%");
		}
		sql.append(" and teach_area_id in" + SQLUtils.toSQLInString(areaIds));
		sql.append(" and type in");
		return queryForIntInSQL(sql.toString(), args.toArray(), types);
	}
	@Override
	public OfficeRepaire getOfficeRepaireById(String id){
		String sql = "select * from office_repaire where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeRepaire> getOfficeRepaireMapByIds(String[] ids){
		String sql = "select * from office_repaire where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireList(){
		String sql = "select * from office_repaire";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeRepaire> getOfficeRepairePage(Pagination page){
		String sql = "select * from office_repaire";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdList(String unitId){
		String sql = "select * from office_repaire where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeRepaire> getOfficeRepaireByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_repaire where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public OfficeRepaire getOfficeRepaireByUserIdLastTime(String userId) {
		String sql = "select * from office_repaire where user_id = ?  and create_time = (select max(create_time) from office_repaire where user_id = ?)";
		return query(sql, new Object[]{userId,userId}, new SingleRow());
	}

	@Override
	public Map<String, String> getOfficeRepaireByUnitIdAndYearsMap(
			String unitId, String years, String change) {
		if(StringUtils.isBlank(change)||StringUtils.equals(change, "1")){
			List<Object> args=new ArrayList<Object>();
			StringBuffer sb=new StringBuffer("select type, TO_NUMBER(to_char(create_time, 'mm')) month, count(*) total from office_repaire");
			sb.append(" where to_char(create_time, 'yyyy')=? and unit_id=?");
			args.add(years);
			args.add(unitId);
			sb.append(" group by to_char(create_time, 'mm'), type order by to_char(create_time, 'mm')");
			return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, String>(){
				
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("month")+"_"+rs.getString("type");
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("total");
				}
				
			});
		}
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "2")){
			List<Object> args=new ArrayList<Object>();
			StringBuffer sb=new StringBuffer("select TO_NUMBER(to_char(create_time, 'mm')) month, round(avg(mark),1) mark,repaire_user_id from office_repaire");
			sb.append(" where to_char(create_time, 'yyyy') = ? and unit_id=?");
			args.add(years);
			args.add(unitId);
			sb.append(" group by to_char(create_time, 'mm'),repaire_user_id order by to_char(create_time, 'mm')");
			return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, String>(){

				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("month")+"_"+rs.getString("repaire_user_id");
				}

				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("mark");
				}
				
			});
		}
		return null;
	}

	@Override
	public Map<String, String> getOfficeRepaireByUnitIdAndYearsTalMap(
			String unitId, String years, String change) {
		if(StringUtils.isBlank(change)||StringUtils.equals(change, "1")){
			List<Object> args=new ArrayList<Object>();
			StringBuffer sb=new StringBuffer("select type, count(*) total from office_repaire");
			sb.append(" where to_char(create_time, 'yyyy')=? and unit_id=?");
			args.add(years);
			args.add(unitId);
			sb.append(" group by type");
			return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, String>(){
				
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("type");
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("total");
				}
				
			});
		}
		if(StringUtils.isNotBlank(change)&&StringUtils.equals(change, "2")){
			List<Object> args=new ArrayList<Object>();
			StringBuffer sb=new StringBuffer("select round(avg(mark),1) mark,repaire_user_id from office_repaire");
			sb.append(" where to_char(create_time, 'yyyy') = ? and unit_id=?");
			args.add(years);
			args.add(unitId);
			sb.append(" group by repaire_user_id");
			return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, String>(){

				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("repaire_user_id");
				}

				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("mark");
				}
				
			});
		}
		return null;
	}

	@Override
	public Map<String, String> getOfficeRepaireByUnitIdAndYearsCountMap(
			String unitId, String years) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select to_number(to_char(create_time, 'mm')) month, count(*) mark, repaire_user_id from office_repaire");
		sb.append(" where to_char(create_time, 'yyyy') =? and unit_id =? and repaire_user_id is not null and mark is not null");
		args.add(years);
		args.add(unitId);
		sb.append(" group by to_char(create_time, 'mm'),repaire_user_id order by to_char(create_time, 'mm')");
		return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("month")+"_"+rs.getString("repaire_user_id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("mark");
			}
			
		});
	}
	
}