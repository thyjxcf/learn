package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import net.zdsoft.eis.base.common.dao.SchoolBuildingDao;
import net.zdsoft.eis.base.common.entity.SchoolBuilding;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.basedata.remote.service.SchoolBuildingRemoteService;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 校舍 
 * @author 
 * 
 */
public class SchoolBuildingDaoImpl extends BaseDao<SchoolBuilding> implements SchoolBuildingDao{
	
	@Autowired
	private SchoolBuildingRemoteService schoolBuildingRemoteService;
	
	
	@Override
	public SchoolBuilding setField(ResultSet rs) throws SQLException{
		SchoolBuilding schoolBuilding = new SchoolBuilding();
		schoolBuilding.setId(rs.getString("id"));
		schoolBuilding.setSchoolId(rs.getString("school_id"));
		schoolBuilding.setSection(rs.getString("section"));
		schoolBuilding.setSerialNumber(rs.getLong("serial_number"));
		schoolBuilding.setName(rs.getString("name"));
		schoolBuilding.setMainUse(rs.getString("main_use"));
		schoolBuilding.setBuildingType(rs.getString("building_type"));
		schoolBuilding.setFloors(rs.getInt("floors"));
		schoolBuilding.setCompleteYear(rs.getInt("complete_year"));
		schoolBuilding.setIsDilapidatedBuilding(rs.getString("is_dilapidated_building"));
		schoolBuilding.setDilapidatedBuildingCode(rs.getString("dilapidated_building_code"));
		schoolBuilding.setDilapidatedBuildingLevel(rs.getString("dilapidated_building_level"));
		schoolBuilding.setIsNewCurrentYear(rs.getString("is_new_current_year"));
		schoolBuilding.setCost(rs.getDouble("cost"));
		schoolBuilding.setIsRental(rs.getString("is_rental"));
		schoolBuilding.setRentalArea(rs.getDouble("rental_area"));
		schoolBuilding.setRentalAreaPublic(rs.getDouble("rental_area_public"));
		schoolBuilding.setRoomAmount(rs.getInt("room_amount"));
		schoolBuilding.setNormalRoomAmount(rs.getInt("normal_room_amount"));
		schoolBuilding.setMultimediaRoomAmount(rs.getInt("multimedia_room_amount"));
		schoolBuilding.setMutilmediaNormalRoomAmount(rs.getInt("mutilmedia_normal_room_amount"));
		schoolBuilding.setTotalArea(rs.getDouble("total_area"));
		schoolBuilding.setIsLent(rs.getString("is_lent"));
		schoolBuilding.setOwnership(rs.getString("ownership"));
		schoolBuilding.setUsageSituation(rs.getString("usage_situation"));
		schoolBuilding.setInfoGatherer(rs.getString("info_gatherer"));
		schoolBuilding.setInfoGathererMode(rs.getString("info_gatherer_mode"));
		return schoolBuilding;
	}
	
	public SchoolBuilding save(SchoolBuilding schoolBuilding){
//		if (StringUtils.isBlank(schoolBuilding.getId())){
//			schoolBuilding.setId(createId());
//		}
//		schoolBuildingRemoteService.saveOne(schoolBuilding);
		
		
		String sql = "insert into base_school_building(id, school_id, section, serial_number, name, main_use, building_type, floors, complete_year, is_dilapidated_building, dilapidated_building_code, dilapidated_building_level, is_new_current_year, cost, is_rental, rental_area, rental_area_public, room_amount, normal_room_amount, multimedia_room_amount, mutilmedia_normal_room_amount, total_area, is_lent, ownership,usage_situation,info_gatherer,info_gatherer_mode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(schoolBuilding.getId())){
			schoolBuilding.setId(createId());
		}
		Object[] args = new Object[]{
			schoolBuilding.getId(), schoolBuilding.getSchoolId(), 
			schoolBuilding.getSection(),
			schoolBuilding.getSerialNumber(), schoolBuilding.getName(), 
			schoolBuilding.getMainUse(), schoolBuilding.getBuildingType(), 
			schoolBuilding.getFloors(), schoolBuilding.getCompleteYear(), 
			schoolBuilding.getIsDilapidatedBuilding(), schoolBuilding.getDilapidatedBuildingCode(), 
			schoolBuilding.getDilapidatedBuildingLevel(), schoolBuilding.getIsNewCurrentYear(), 
			schoolBuilding.getCost(), schoolBuilding.getIsRental(), 
			schoolBuilding.getRentalArea(), schoolBuilding.getRentalAreaPublic(), 
			schoolBuilding.getRoomAmount(), schoolBuilding.getNormalRoomAmount(), 
			schoolBuilding.getMultimediaRoomAmount(), schoolBuilding.getMutilmediaNormalRoomAmount(),
			schoolBuilding.getTotalArea(), schoolBuilding.getIsLent(),
			schoolBuilding.getOwnership(), schoolBuilding.getUsageSituation(),
			schoolBuilding.getInfoGatherer(), schoolBuilding.getInfoGathererMode()
		};
		update(sql, args);
		return schoolBuilding;
	}
	
	@Override
	public Integer delete(String[] ids){
		
		//TODO  可能ids有id重复或id不存在，不能删除  
		if(ids.length==0){
			return 0;
		}else{
			List<SchoolBuilding> schoolBuildings = SchoolBuilding.dt(schoolBuildingRemoteService.findByIds(ids));
			schoolBuildingRemoteService.deleteByInIds(ids);
			return schoolBuildings.size();
		}
		
		
//		String sql = "delete from base_school_building where id in";
//		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SchoolBuilding schoolBuilding){
	//	schoolBuildingRemoteService.updateOne(schoolBuilding);
		
		String sql = "update base_school_building set school_id = ?, section = ?, serial_number = ?, name = ?, main_use = ?, building_type = ?, floors = ?, complete_year = ?, is_dilapidated_building = ?, dilapidated_building_code = ?, dilapidated_building_level = ?, is_new_current_year = ?, cost = ?, is_rental = ?, rental_area = ?, rental_area_public = ?, room_amount = ?, normal_room_amount = ?, multimedia_room_amount = ?, mutilmedia_normal_room_amount = ?, total_area = ?, is_lent = ?, ownership = ?, usage_situation = ?,info_gatherer = ?,info_gatherer_mode = ? where id = ?";
		Object[] args = new Object[]{
			schoolBuilding.getSchoolId(), schoolBuilding.getSection(), schoolBuilding.getSerialNumber(), 
			schoolBuilding.getName(), schoolBuilding.getMainUse(), 
			schoolBuilding.getBuildingType(), schoolBuilding.getFloors(), 
			schoolBuilding.getCompleteYear(), schoolBuilding.getIsDilapidatedBuilding(), 
			schoolBuilding.getDilapidatedBuildingCode(), schoolBuilding.getDilapidatedBuildingLevel(), 
			schoolBuilding.getIsNewCurrentYear(), schoolBuilding.getCost(), 
			schoolBuilding.getIsRental(), schoolBuilding.getRentalArea(), 
			schoolBuilding.getRentalAreaPublic(), schoolBuilding.getRoomAmount(), 
			schoolBuilding.getNormalRoomAmount(), schoolBuilding.getMultimediaRoomAmount(), 
			schoolBuilding.getMutilmediaNormalRoomAmount(), schoolBuilding.getTotalArea(),
			schoolBuilding.getIsLent(), schoolBuilding.getOwnership(),
			schoolBuilding.getUsageSituation(),	schoolBuilding.getInfoGatherer(), 
			schoolBuilding.getInfoGathererMode(),
			schoolBuilding.getId()
		};
		return update(sql, args);
	}

	@Override
	public SchoolBuilding getSchoolBuildingById(String id){
		
	return 	SchoolBuilding.dc(schoolBuildingRemoteService.findById(id));
		
//		String sql = "select * from base_school_building where id = ?";
//		return query(sql, new Object[]{id }, new SingleRow());
	}
}