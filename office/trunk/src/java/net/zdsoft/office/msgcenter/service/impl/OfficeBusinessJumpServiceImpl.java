package net.zdsoft.office.msgcenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.push.client.WeikePushClient;
import net.zdsoft.eis.component.push.entity.WKPushParm;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.enums.WeikeAppUrlEnum;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.msgcenter.dao.OfficeBusinessJumpDao;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.service.OfficeBusinessJumpService;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.seal.entity.OfficeSeal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
/**
 * office_business_jump
 * @author 
 * 
 */
public class OfficeBusinessJumpServiceImpl implements OfficeBusinessJumpService{
	private OfficeBusinessJumpDao officeBusinessJumpDao;
	private OfficeSubsystemService officeSubsystemService;
	private OfficeGoodsReqService officeGoodsReqService;
	private OfficeRepaireService officeRepaireService;

	@Override
	public OfficeBusinessJump save(OfficeBusinessJump officeBusinessJump){
		return officeBusinessJumpDao.save(officeBusinessJump);
	}
	
	@Override
	public void batchSave(List<OfficeBusinessJump> list){
		officeBusinessJumpDao.batchSave(list);
	}

	@Override
	public Integer delete(String[] ids){
		return officeBusinessJumpDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBusinessJump officeBusinessJump){
		return officeBusinessJumpDao.update(officeBusinessJump);
	}

	@Override
	public OfficeBusinessJump getOfficeBusinessJumpById(String id){
		return officeBusinessJumpDao.getOfficeBusinessJumpById(id);
	}

	@Override
	public Map<String, OfficeBusinessJump> getOfficeBusinessJumpMapByIds(String[] ids){
		return officeBusinessJumpDao.getOfficeBusinessJumpMapByIds(ids);
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpList(){
		return officeBusinessJumpDao.getOfficeBusinessJumpList();
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpPage(Pagination page){
		return officeBusinessJumpDao.getOfficeBusinessJumpPage(page);
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdList(String unitId){
		return officeBusinessJumpDao.getOfficeBusinessJumpByUnitIdList(unitId);
	}

	@Override
	public List<OfficeBusinessJump> getOfficeBusinessJumpByUnitIdPage(String unitId, Pagination page){
		return officeBusinessJumpDao.getOfficeBusinessJumpByUnitIdPage(unitId, page);
	}
	
	@Override
	public OfficeBusinessJump getOfficeBusinessJumpByMsgId(String msgId){
		List<OfficeBusinessJump> list = officeBusinessJumpDao.getOfficeBusinessJumpByMsgId(msgId);
		if(CollectionUtils.isNotEmpty(list))
			return list.get(0);
		else
			return null;
	}
	
	public void pushMsgUrlToMobile(Object obj, Integer type, String[] userIds, int jumpState){
		if(OfficeBusinessJump.OFFICE_REPAIRE == type){//报修管理
			OfficeRepaire officeRepaire = (OfficeRepaire) obj;
			
			WKPushParm parm = new WKPushParm();
			List<String> rows = new ArrayList<String>();
			int dataType = 0;//0:我发起的,1:待我处理 
			if(WeikeAppConstant.DETAILE_URL ==  jumpState){
				parm.setMsgTitle("报修提醒");
				parm.setBodyTitle("报修提醒");
				rows.add("设备地点：" + officeRepaire.getGoodsPlace());
				rows.add("设备名称：" + officeRepaire.getGoodsName());
				rows.add("故障详情：" + officeRepaire.getRemark());
				rows.add("维修备注：" + officeRepaire.getRepaireRemark());
			}else{
				dataType = 1;
				parm.setMsgTitle("报修申请");
				parm.setHeadContent(officeRepaire.getUserName());
				parm.setBodyTitle("报修申请");
				rows.add("设备地点：" + officeRepaire.getGoodsPlace());
				rows.add("设备名称：" + officeRepaire.getGoodsName());
				rows.add("故障详情：" + officeRepaire.getRemark());
			}
			parm.setRowsContent(rows.toArray(new String[0]));
			parm.setFootContent("详情");
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			if(StringUtils.isNotBlank(domain)){
				parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
				String url = WeikeAppUrlEnum.getWeikeUrl(WeikeAppConstant.REPAIR, jumpState)+"&id="+officeRepaire.getId()+"&dataType="+dataType;
				parm.setUrl(domain + url);
			}
			WeikePushClient.getInstance().pushMessage("", userIds, parm);
		}
	}
	
	
	@Override
	public void pushMsgUrl(Object obj, Integer type, String msgId){
		OfficeMsgSendingDto msgSendingDto = officeSubsystemService.getSendMsgById(msgId);
		if(msgSendingDto != null){
			String contextPath = ServletActionContext.getRequest().getContextPath();
			OfficeBusinessJump businessJump = new OfficeBusinessJump();
				String objId="";
			
			if(OfficeBusinessJump.OFFICE_GOODS == type){//物品管理
				OfficeGoodsReq ent = (OfficeGoodsReq) obj;
				objId = ent.getId();
				businessJump.setUnitId(msgSendingDto.getUnitId());
				businessJump.setMsgId(msgId);
				businessJump.setModules("70022,70522");
				businessJump.setReceivers(msgSendingDto.getUserIds());
				businessJump.setReceiverType("2");
				businessJump.setCreateTime(new Date());
				
				JSONObject json = new JSONObject();
				json.put("index", "2");
				json.put("loadObject", "#adminDiv");
				json.put("url", contextPath+"/office/goodmanage/goodmanage-goodsAudit.action");
				json.put("roleCode", OfficeGoodsConstants.OFFICE_GOODS_AUDIT);
				json.put("sysOption", "GOODS.AUDIT.MODEL_1");
				json.put("checkState", "1");
				json.put("objId", objId);
				json.put("objType", type);
				businessJump.setContent(json.toString());
				
				this.save(businessJump);
			}
			else if(OfficeBusinessJump.OFFICE_REPAIRE == type){//报修管理
				OfficeRepaire ent = (OfficeRepaire) obj;
				objId = ent.getId();
				businessJump.setUnitId(msgSendingDto.getUnitId());
				businessJump.setMsgId(msgId);
				businessJump.setModules("71006,71506");
				businessJump.setReceivers(msgSendingDto.getUserIds());
				businessJump.setReceiverType("2");
				businessJump.setCreateTime(new Date());
				
				JSONObject json = new JSONObject();
				json.put("index", "2");
				json.put("loadObject", "#repaireDiv");
				json.put("url", contextPath+"/office/repaire/repaire-manage.action");
				json.put("roleCode", "repaire_manage");
				json.put("checkState", "1");
				json.put("objId", objId);
				json.put("objType", type);
				json.put("endHandler", "openDiv(\\\'#classLayer3\\\',\\\'\\\',\\\'"+contextPath+"/office/repaire/repaire-mangeEdit.action?id="+objId+"\\\',"+null+","+null+",\\\'300px\\\')");
				businessJump.setContent(json.toString());
				
				this.save(businessJump);
			}
			else if(OfficeBusinessJump.OFFICE_SEAL_MANAGE == type){//用印管理
				OfficeSeal officeSeal=(OfficeSeal) obj;
				businessJump.setUnitId(msgSendingDto.getUnitId());
				businessJump.setMsgId(msgId);
				businessJump.setModules("70030,70530");
				businessJump.setReceivers(msgSendingDto.getUserIds());
				businessJump.setReceiverType("2");
				businessJump.setCreateTime(new Date());
				
				objId = officeSeal.getId();
				 
				JSONObject json = new JSONObject();
				json.put("index", "1");
				json.put("loadObject", "#sealDiv");
				json.put("state", officeSeal.getState());
				//json.put("url", contextPath+"/office/sealmanage/sealmanage-sealManageAdmin.action");
				if(StringUtils.equals(officeSeal.getState(), "2")){
					//json.put("endHandler", "openDiv(\\\'"+"#sealAddLayer"+"\\\',\\\'#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset"+"\\\',\\\'"+contextPath+"/office/sealmanage/sealmanage-sealManageAudit.action?officeSealId="+objId+"\\\',"+null+","+null+",\\\'900px"+"\\\')");
					json.put("url", contextPath+"/office/sealmanage/sealmanage-sealManageAudit.action?officeSealId="+objId);
				}
				if(StringUtils.equals(officeSeal.getState(), "3")){
					//json.put("endHandler", "openDiv(\\\'"+"#sealAddLayer"+"\\\',\\\'#sealAddLayer .close,#sealAddLayer .submit,#sealAddLayer .reset"+"\\\',\\\'"+contextPath+"/office/sealmanage/sealmanage-sealManageView.action?officeSealId="+objId+"\\\',"+null+","+null+",\\\'900px"+"\\\')");
					json.put("url", contextPath+"/office/sealmanage/sealmanage-sealManageView.action?officeSealId="+objId);
				}
				json.put("officeSealId", objId);
				businessJump.setContent(json.toString());
				if(StringUtils.isNotBlank(objId)){
					officeBusinessJumpDao.save(businessJump);
				}
				}else if(OfficeBusinessJump.OFFICE_BULLETIN==type){//通知公告
					OfficeBulletin officeBulletin=(OfficeBulletin) obj;
					
					businessJump.setUnitId(officeBulletin.getUnitId());
					businessJump.setMsgId(msgId);
					businessJump.setModules("70002,70502");
					//businessJump.setReceivers(msgSendingDto.getUserIds());
					businessJump.setCreateTime(new Date());
					businessJump.setReceiverType("2");
					
					objId = officeBulletin.getId();
					
					JSONObject json = new JSONObject();
					json.put("objType", type);
					json.put("index", "0");
					json.put("bulletinId",officeBulletin.getId());
					json.put("url", contextPath+"office/bulletin/bulletin-viewDetail.action?bulletinType="+officeBulletin.getType()+"&bulletinId="+officeBulletin.getId());
					businessJump.setContent(json.toString());
					if(StringUtils.isNotBlank(objId)){
						officeBusinessJumpDao.save(businessJump);
					}
				}
				else{
					return;
				}
		}
	}
	
	public boolean checkState(String objId, String objType){
		boolean flag = false;
		if(objType.equals(OfficeBusinessJump.OFFICE_GOODS+"")){//物品管理
			OfficeGoodsReq ent = officeGoodsReqService.getOfficeGoodsReqById(objId);
			if(ent != null && ent.getState() == OfficeGoodsConstants.GOODS_NOT_AUDIT){
				flag = true;
			}
		}
		else if(objType.equals(OfficeBusinessJump.OFFICE_REPAIRE+"")){//报修管理
			OfficeRepaire ent = officeRepaireService.getOfficeRepaireById(objId);
			if(ent != null && OfficeRepaire.STATE_ONE.equals(ent.getState())){
				flag = true;
			}
		}else{
			return false;
		}
		return flag;
	}

	public void setOfficeBusinessJumpDao(OfficeBusinessJumpDao officeBusinessJumpDao){
		this.officeBusinessJumpDao = officeBusinessJumpDao;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public void setOfficeGoodsReqService(OfficeGoodsReqService officeGoodsReqService) {
		this.officeGoodsReqService = officeGoodsReqService;
	}

	public void setOfficeRepaireService(OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}

}
