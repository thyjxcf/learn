package net.zdsoft.office.asset.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.asset.entity.OfficeAssetPurchaseOpinion;
import net.zdsoft.office.asset.dao.OfficeAssetPurchaseOpinionDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * 采购意见信息表
 * @author 
 * 
 */
public class OfficeAssetPurchaseOpinionDaoImpl extends BaseDao<OfficeAssetPurchaseOpinion> implements OfficeAssetPurchaseOpinionDao{

	@Override
	public OfficeAssetPurchaseOpinion setField(ResultSet rs) throws SQLException{
		OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion = new OfficeAssetPurchaseOpinion();
		officeAssetPurchaseOpinion.setId(rs.getString("id"));
		officeAssetPurchaseOpinion.setUnitId(rs.getString("unit_id"));
		officeAssetPurchaseOpinion.setContent(rs.getString("content"));
		officeAssetPurchaseOpinion.setType(rs.getString("type"));
		return officeAssetPurchaseOpinion;
	}

	@Override
	public OfficeAssetPurchaseOpinion save(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion){
		String sql = "insert into office_asset_purchase_opinion(id, unit_id, content, type) values(?,?,?,?)";
		if (StringUtils.isBlank(officeAssetPurchaseOpinion.getId())){
			officeAssetPurchaseOpinion.setId(createId());
		}
		Object[] args = new Object[]{
			officeAssetPurchaseOpinion.getId(), officeAssetPurchaseOpinion.getUnitId(), 
			officeAssetPurchaseOpinion.getContent(), officeAssetPurchaseOpinion.getType()
		};
		update(sql, args);
		return officeAssetPurchaseOpinion;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_asset_purchase_opinion where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion){
		String sql = "update office_asset_purchase_opinion set unit_id = ?, content = ?, type = ? where id = ?";
		Object[] args = new Object[]{
			officeAssetPurchaseOpinion.getUnitId(), officeAssetPurchaseOpinion.getContent(), 
			officeAssetPurchaseOpinion.getType(), officeAssetPurchaseOpinion.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAssetPurchaseOpinion getOfficeAssetPurchaseOpinionById(String id){
		String sql = "select * from office_asset_purchase_opinion where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionMapByIds(String[] ids){
		String sql = "select * from office_asset_purchase_opinion where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList(){
		String sql = "select * from office_asset_purchase_opinion";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionPage(Pagination page){
		String sql = "select * from office_asset_purchase_opinion";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdList(String unitId){
		String sql = "select * from office_asset_purchase_opinion where unit_id = ? order by type";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_asset_purchase_opinion where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList(String unitId, String type, String content){
		String sql = "select * from office_asset_purchase_opinion where unit_id = ? and type = ? and content = ?";
		return query(sql, new Object[]{unitId, type, content}, new MultiRow());
	}
	
	@Override
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionListByType(String unitId, String type){
		String sql = "select * from office_asset_purchase_opinion where unit_id = ? and type = ?";
		return query(sql, new Object[]{unitId, type}, new MultiRow());
	}
}
