package net.zdsoft.eis.base.form;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

public class FieldDaoImpl extends BaseDao<Field> implements FieldDao {
	//查询信息
	private static final String LIST_QUERY_FIELD = "select * from base_form_field where business_type = ? order by order_id";
	
	private static final String ENTITY_QUERY_FIELD = "select * from base_form_field where business_type = ? and name = ? and parent_value = ?";
	
	@Override
	public List<Field> getFieldList(int businessType){
		return query(LIST_QUERY_FIELD, new Object[]{businessType}, new MultiRow());
	}
	
	@Override
	public Map<String, Field> getFieldMap(int businessType){
		return queryForMap(LIST_QUERY_FIELD, new Object[]{businessType}, new MapRowMapper<String, Field>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("name");
			}

			@Override
			public Field mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}
	
	@Override
	public Field getFieldEntity(int businessType, String name, String parentValue){
		return query(ENTITY_QUERY_FIELD, new Object[]{businessType, name, parentValue}, new SingleRow());
	}
	
	@Override
	public Field setField(ResultSet rs) throws SQLException {
		Field field = new Field();
		field.setId(rs.getString("id"));
		field.setBusinessType(rs.getString("business_type"));
		field.setName(rs.getString("name"));
		field.setDefine(rs.getString("define"));
		field.setMcode(rs.getString("mcode"));
		field.setType(rs.getString("type"));
		field.setDefaultValue(rs.getString("default_value"));
		field.setInputType(rs.getInt("input_type"));
		field.setOrder(rs.getInt("order_id"));
		if (rs.getString("min_value") == null){
			field.setMinValue(null);
		}else{
			field.setMinValue(rs.getFloat("min_value"));
		}
		if (rs.getString("max_value") == null){
			field.setMaxValue(null);
		}else{
			field.setMaxValue(rs.getFloat("max_value"));
		}
		field.setChildName(rs.getString("child_name"));
		field.setParentValue(rs.getString("parent_value"));
		field.setAlone(rs.getBoolean("alone"));
		field.setListShow(rs.getBoolean("list_show"));
		field.setRequire(rs.getBoolean("require"));
		return field;
	}

}
