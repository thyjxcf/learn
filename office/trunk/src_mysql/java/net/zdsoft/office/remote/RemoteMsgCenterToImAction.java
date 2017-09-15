package net.zdsoft.office.remote;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.SecurityUtils;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONValue;

/**
 * @author chens
 * @version 创建时间：2016-4-25 上午10:23:19
 * 
 */
public class RemoteMsgCenterToImAction{

	private UserService userService;
	private SystemIniService systemIniService;
	
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	
	private String auth;//"IMSERVER"+tick  用md5+sha1加密过的
	private String tick;//时间串获取的时间(1970年至今的秒数)
	
	/**
	 * 推送消息总条数
	 */
	public void pushMessageNumber() {
		try {
			if(StringUtils.isBlank(tick)){
				JSONObject json = new JSONObject();
				json.put("error", "传入的tick为空");
				writeJsonMessage(json);
				return;
			}else if(StringUtils.isBlank(auth)){
				JSONObject json = new JSONObject();
				json.put("error", "传入的auth为空");
				writeJsonMessage(json);
				return;
			}else{
				String authStr = "IMSERVER"+tick;
				String checkAuth = SecurityUtils.encodeByMD5(authStr)
			            + SecurityUtils.encodeBySHA1(authStr);
				if(checkAuth.equals(auth)){
					long currentTime = calLastedTime();
					long tickToInt = Integer.parseInt(tick);
					long hours;
					if(tickToInt<currentTime) {  
						hours = currentTime - tickToInt;
			        } else {  
			        	hours = tickToInt - currentTime;
			        }  
					hours = hours / (60 * 60);
					if(hours>1){
						JSONObject json = new JSONObject();
						json.put("error", "服务器时间差超过1小时");
						writeJsonMessage(json);
						return;
					}
				}else{
					JSONObject json = new JSONObject();
					json.put("error", "传入的auth值不对");
					writeJsonMessage(json);
					return;
				}
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = "http://" + request.getServerName() //服务器地址  
                    + ":"
                    + request.getServerPort()           //端口号 
                    + request.getContextPath();
			if (StringUtils.isNotBlank(url)) {
				if (!url.endsWith("/")) {
					url += "/";
				}
				StringBuffer detailUrl = new StringBuffer(url);
				detailUrl.append("fpf/im/open/verify.action?url=").append(url)
				.append("fpf/homepage/subsystem.action&appId=69&moduleIDStr=69052-69002");
				JSONObject json = new JSONObject();
				json.put("apid", 1);
				json.put("msgtype", 1);
				json.put("apurl", detailUrl.toString());
				json.put("iscomplete", 1);
				//获取im已经获取过的时间
				String imTime = systemIniService.getValue(BaseConstant.IM_TIME);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//修改被获取数据之后的时间
				systemIniService.updateNowValue(BaseConstant.IM_TIME, sdf.format(date));
				//获取这个时间段以后发生过变化的数据userIds
				String[] userIds = officeMsgReceivingService.getChangedReceivingUserIds(imTime);
				JSONArray jsonArray = new JSONArray();
				if(ArrayUtils.isNotEmpty(userIds)){
					Map<String, User> userMap = userService.getUsersMap(userIds);
					Map<String, Integer> msgMap = officeMsgReceivingService.getMsgReceivingMap(userIds);
					long time = calLastedTime();
					for(String userId:userIds){
						if(!userMap.containsKey(userId)){
							continue;
						}
						User user = userMap.get(userId);
						JSONObject item = new JSONObject();
						item.put("toid", user.getSequence());
						item.put("time", time);
						if(msgMap.get(userId)==null){
							item.put("totalnum", 0);
						}else{
							item.put("totalnum", msgMap.get(userId));
						}
						jsonArray.add(item);
					}
				}
				json.put("message", jsonArray);
				writeJsonMessage(json);
			} else {
				System.out.println("首页地址未维护");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("error", "获取消息条数接口出错");
			writeJsonMessage(json);
			return;
		}
	}

	/**
	 * 推送消息到im
	 */
	public void pushAllMessage() throws Exception {
		try {
			if(StringUtils.isBlank(tick)){
				JSONObject json = new JSONObject();
				json.put("error", "传入的tick为空");
				writeJsonMessage(json);
				return;
			}else if(StringUtils.isBlank(auth)){
				JSONObject json = new JSONObject();
				json.put("error", "传入的auth为空");
				writeJsonMessage(json);
				return;
			}else{
				String authStr = "IMSERVER"+tick;
				String checkAuth = SecurityUtils.encodeByMD5(authStr)
			            + SecurityUtils.encodeBySHA1(authStr);
				if(checkAuth.equals(auth)){
					long currentTime = calLastedTime();
					long tickToInt = Integer.parseInt(tick);
					long hours;
					if(tickToInt<currentTime) {  
						hours = currentTime - tickToInt;
			        } else {  
			        	hours = tickToInt - currentTime;
			        }  
					hours = hours / (60 * 60);
					if(hours>1){
						JSONObject json = new JSONObject();
						json.put("error", "服务器时间差超过1小时");
						writeJsonMessage(json);
						return;
					}
				}else{
					JSONObject json = new JSONObject();
					json.put("error", "传入的auth值不对");
					writeJsonMessage(json);
					return;
				}
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = "http://" + request.getServerName() //服务器地址  
                    + ":"
                    + request.getServerPort()           //端口号 
                    + request.getContextPath();
			if (StringUtils.isNotBlank(url)) {
				if (!url.endsWith("/")) {
					url += "/";
				}
				StringBuffer detailUrl = new StringBuffer(url);
				detailUrl.append("fpf/im/open/verify.action?url=").append(url)
				.append("fpf/homepage/subsystem.action&appId=69&moduleIDStr=69052-69002");
				Pagination page = new Pagination(1001, false);
				List<OfficeMsgReceiving> msgReceivings= officeMsgReceivingService.getMsgUnPushed(page);
				JSONObject json = new JSONObject();
				json.put("apid", 1);
				json.put("msgtype", 1);
				json.put("apurl", detailUrl.toString());
				// 表示是否已经获取所有数据
				if (msgReceivings.size() > 1000) {
					json.put("iscomplete", 2);
					msgReceivings.remove(1);
				} else {
					json.put("iscomplete", 1);
				}
				JSONArray jsonArray = new JSONArray();
				long time = calLastedTime();
				
				Set<String> userIdSet = new HashSet<String>();
				List<String> sendMsgIdlList = new ArrayList<String>();
				List<String> msgIdList = new ArrayList<String>();
				for (OfficeMsgReceiving msg : msgReceivings) {
					userIdSet.add(msg.getReceiveUserId());
					userIdSet.add(msg.getSendUserId());
					sendMsgIdlList.add(msg.getMessageId());
					msgIdList.add(msg.getId());
				}
				Map<String, User> userMap = userService.getUsersMap(userIdSet.toArray(new String[0]));
				Map<String, OfficeMsgSending> msgSendingMap = officeMsgSendingService.getOfficeMsgSendingMapByIds(sendMsgIdlList.toArray(new String[0]));
				for (OfficeMsgReceiving msg : msgReceivings) {
					if(!userMap.containsKey(msg.getReceiveUserId())){
						continue;
					}
					User user = userMap.get(msg.getReceiveUserId());
					JSONObject item = new JSONObject();
					item.put("toid", user.getSequence());
					User user1 = userMap.get(msg.getSendUserId());
					if(user1!=null){
						item.put("fromid", user1.getSequence());
						item.put("fromuser", user1.getRealname());
					}
					item.put("title", msg.getTitle());
					if (MapUtils.isNotEmpty(msgSendingMap) 
							&& msgSendingMap.containsKey(msg.getMessageId())) {
						OfficeMsgSending os = msgSendingMap.get(msg.getMessageId());
						if (StringUtils.isNotBlank(os.getContent()) 
								&& os.getContent().length() > 100) {
							item.put("content",
									os.getContent().substring(0, 100));
						} else {
							item.put("content", os.getContent());
						}
					}
					item.put("time", time);
					jsonArray.add(item);
				}
				json.put("message", jsonArray);
				writeJsonMessage(json);
				//更新推送状态
				if(msgReceivings.size() > 0){
					officeMsgReceivingService.updatePushed(msgIdList.toArray(new String[0]));
				}
			} else {
				System.out.println("没有设置code为office的ap");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("error", "获取消息接口出错");
			writeJsonMessage(json);
			return;
		}
	}
	
	/**
	 * 推送公告到im
	 */

	private void writeJsonMessage(JSONObject jsonObject) {
		try {
			String json = JSONValue.toJSONString(jsonObject);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print(json);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算两个时间直接相差的秒数
	 * 
	 * @return
	 */
	public long calLastedTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date date = cal.getTime();
		long a = new Date().getTime();
		long b = date.getTime();
		long c = (a - b) / 1000;
		return c;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getTick() {
		return tick;
	}

	public void setTick(String tick) {
		this.tick = tick;
	}
	
	public static void main(String[] args) {
		long tick = new RemoteMsgCenterToImAction().calLastedTime();
		String authStr = "IMSERVER"+tick;
		String checkAuth = SecurityUtils.encodeByMD5(authStr)
	            + SecurityUtils.encodeBySHA1(authStr);
		System.out.println(tick);
		System.out.println(checkAuth);
	}
	
}