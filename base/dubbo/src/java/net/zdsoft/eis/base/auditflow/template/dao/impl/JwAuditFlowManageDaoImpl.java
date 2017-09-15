package net.zdsoft.eis.base.auditflow.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.dao.JwAuditFlowManageDao;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwAuditFlowManageDaoImpl extends BasicDAO implements JwAuditFlowManageDao {
	private static final Logger log = LoggerFactory.getLogger(JwAuditFlowManageDaoImpl.class);
	/**
	 * 清除数据
	 */
	public void deleteAuditFlow(String auditType,String section,String businessType){
		String[] auditTypes = auditType.split("-");
		String sql = "SELECT flow_id FROM base_flow_template";
		List<String> list = null;
		if(auditTypes[1].equals("0")){
			//除去转学其他的异动情况
			sql += " WHERE target_region_level IS null AND source_region_level=? AND business_type=?";
			if(StringUtils.isBlank(section)){
				sql += " AND section is null";
			}else{
				sql += " AND section ="+Integer.parseInt(section);
			}
			list = query(sql, new Object[] {Integer.parseInt(auditTypes[0]), Integer.parseInt(businessType)},new MultiRowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("id");
				}
				
			});
		}else{
			//异动转学
			sql += " WHERE source_region_level=? AND business_type=? AND target_region_level=?";
			if(StringUtils.isBlank(section)){
				sql += " AND section is null";
			}else{
				sql += " AND section ="+Integer.parseInt(section);
			}
			list = query(sql, new Object[] {Integer.parseInt(auditTypes[0]), Integer.parseInt(businessType),Integer.parseInt(auditTypes[1])},new MultiRowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("id");
				}
				
			});
		}
		
		if(list != null && list.size()>0){
			//BusinessFlowTemplate businessFlowTemplate = (String)list.get(0);
			String flowId = (String)list.get(0);
			
			sql = "DELETE FROM base_flow_template WHERE id=?";
			update(sql, flowId);
			
			sql = "DELETE FROM base_flow_step WHERE id=?";
			update(sql, flowId);
		}
	}
	
	/**
	 * 保存数据
	 */
	public void addAuditFlow(String auditType,String section,String businessType,List auditFlowList){
		String[] auditTypes = auditType.split("-");
		//保存流程模板
		BusinessFlowTemplate businessFlowTemplate = new BusinessFlowTemplate();
		businessFlowTemplate.setId(UUIDGenerator.getUUID());
		if(StringUtils.isNotBlank(section)){
			businessFlowTemplate.setSection(Integer.parseInt(section));
		}
		businessFlowTemplate.setBusinessType(Integer.parseInt(businessType));
		businessFlowTemplate.setSourceRegionLevel(Integer.parseInt(auditTypes[0]));
		if(!auditTypes[1].equals("0")){
			//除去区域内部移动情况
			businessFlowTemplate.setTargetRegionLevel(Integer.parseInt(auditTypes[1]));
		}
		
		update("insert into base_flow_template(id,business_type,source_region_level,section,target_region_level) values(?,?,?,?,?)", new Object[]{
			businessFlowTemplate.getId(),businessFlowTemplate.getBusinessType(),
			businessFlowTemplate.getSourceRegionLevel(),businessFlowTemplate.getSection(),
			businessFlowTemplate.getTargetRegionLevel()
		});
		
		
		//保存流程步骤
		//flow_id，流程模板id；
		//audit_unit_type，审核单位类型：1教育局，2学校；
		//region_level，审核单位级别；
		//step_value:步骤，-1表示最后一步，其他的从0开始；
		//io_type，单位来源类型，如果是0表示转出方，1表示转入方
		
		/*保存审核每个层次的信息，
		 * 0-序号
		 * 1-级别
		 * 2-起止点标示（0起点，-1止点，1为中间单位）
		 * 3-转出、转入（0转出，1转入,-1最高级别）
		 * 4-审核单位名称
		 * 5-单位类型 （1教育局，2学校）*/
		for (int i = 0; i < auditFlowList.size(); i++) {
			String[] str = (String[])auditFlowList.get(i);
			FlowStep flowStep = new FlowStep();
			flowStep.setFlowId(businessFlowTemplate.getId());
			flowStep.setRegionLevel(Integer.parseInt(str[1]));
			if(i == (auditFlowList.size()-1)){
				//最后一步时
				flowStep.setStepValue(-1);
			}
			else{
				flowStep.setStepValue(i);
			}
			flowStep.setAuditUnitType(Integer.parseInt(str[5]));
			flowStep.setIoType(Integer.parseInt(str[3]));
			log.info("开始设置异动流程");
			log.info("异动单位审核类型：" + flowStep.getAuditUnitType());
			log.info("异动转入/转出类型：" + flowStep.getIoType());
			log.info("步骤：" + flowStep.getStepValue());
			update("insert into base_flow_step(id,flow_id,audit_unit_type,region_level,step_value,io_type)values(?,?,?,?,?,?)", new Object[]{
					UUIDGenerator.getUUID(),flowStep.getFlowId(),
					flowStep.getAuditUnitType(),flowStep.getRegionLevel(),
					flowStep.getStepValue(),flowStep.getIoType()
			});
		}
		
	}
	
	/**
	 * 获得所有异动类型
	 * 
	 * @return 所有异动类型
	 */
//	public List getFlowTypeList(){
//		String hql = "SELECT id FROM "+Mcodedetail.class.getName()
//		+ " WHERE mcodeId = 'DM-YDLB'";
//		List list = getHibernateTemplate().find(hql);
//		return list;
//	}
	
	/**
	 * 获得已经存在的审核步骤
	 * 
	 * @return 所有异动类型
	 */
	public List<FlowStep> getFlowTypeNoteData(String businessType,String auditType,String section){
		String[] auditTypes = auditType.split("-");
		String sql = "SELECT id FROM base_flow_template";
		List<String> list = null;
		if(auditTypes[1].equals("0")){
			//除去转学其他的异动情况
			sql += " WHERE target_region_level IS null AND source_region_level=? AND business_type=?";
			if(StringUtils.isBlank(section)){
				sql += " AND section is null";
			}else{
				sql += " AND section ="+Integer.parseInt(section);
			}
			list = query(sql, new Object[] {Integer.parseInt(auditTypes[0]), Integer.parseInt(businessType)},new MultiRowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("id");
				}
				
			});
		}else{
			//异动转学
			sql += " WHERE source_region_level=? AND business_type=? AND target_region_level=?";
			if(StringUtils.isBlank(section)){
				sql += " AND section is null";
			}else{
				sql += " AND section ="+Integer.parseInt(section);
			}
			list = query(sql, new Object[] { Integer.parseInt(auditTypes[0]), Integer.parseInt(businessType),Integer.parseInt(auditTypes[1])},new MultiRowMapper<String>(){

				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("id");
				}
				
			});
		}
		if(list != null && list.size()>0){
			String flowId = (String)list.get(0);
			sql = "SELECT * FROM base_flow_step";
			sql += " WHERE id=?";
			return query(sql, flowId,new MultiRowMapper<FlowStep>() {

				@Override
				public FlowStep mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					FlowStep flowStep = new FlowStep();
					flowStep.setId(rs.getString("id"));
					flowStep.setFlowId(rs.getString("flow_id"));
					flowStep.setRegionLevel(rs.getInt("region_level"));
					flowStep.setStepValue(rs.getInt("step_value"));
					flowStep.setAuditUnitType(rs.getInt("audit_unit_type"));
					flowStep.setIoType(rs.getInt("io_type"));
					return flowStep;
				}
				
			});
		}else{
			return new ArrayList<FlowStep>();
		}
	}
}
