package net.zdsoft.eis.system.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.data.entity.UnitChannel;
import net.zdsoft.eis.system.data.service.UnitChannelService;

public class UnitIniAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int MAXCHANEELNUM = 10;	//可设置的单位频道数量
	private final static int MAXNAMELENGTH = 6;		//频道名称的长度
	private final static int MAXORDERIDLENGTH = 2;	//频道排序的长度
	private final static int MAXCODELENGTH = 12;	//频道代码的长度
	private final static int MAXURLLENGTH = 100;	//频道链接的长度
	
	private UnitChannelService unitChannelService;
	private List<UnitChannel> unitChannelList;
	private String promptMessage;
	
//	private String unitid;			//单位id
	private String names;			//频道名称
	private String urls;			//频道地址
	private String marks;			//类型标志，0 表示系统默认，1 表示用户设置
	private String codes;			//频道代码，如博客为blog等，用于特殊处理
	private String orderids;		//排序号， 1 优先级最高，在频道栏最左边
	private String displays;		//是否显示，0 表示不显示，1 表示显示
	
	public String unitIniConfig(){
		unitChannelList = unitChannelService.getUnitChannels(getLoginInfo().getUnitID(), getLoginInfo().getUnitClass());
        if (unitChannelList == null || unitChannelList.size() ==  0){
            unitChannelService.initUnitChannel(getLoginInfo().getUnitID(), getLoginInfo().getUnitClass());
            unitChannelList = unitChannelService.getUnitChannels(getLoginInfo().getUnitID(), getLoginInfo().getUnitClass());
        }
		return SUCCESS;
	}
	
	public String saveUnitIniConfig(){
		List<UnitChannel> tempList = productionData();
		if(tempList.size() == 0){
			promptMessage = "单位频道设置保存失败，你填写的数据可能有误，请重新设置！";
			return INPUT;
		}
		unitChannelService.saveUnitChannels(tempList);
		promptMessage = "单位频道设置保存成功！";
		return SUCCESS;
	}
	
	public String showUnitChannel(){
	    unitChannelService.getUnitChannels(getLoginInfo().getUnitID(), getLoginInfo().getUnitClass(),0);
		return SUCCESS;
	}
	
	public List<UnitChannel> productionData(){
		List<UnitChannel> channelList = new ArrayList<UnitChannel>();
		try{
			String name[] = names.trim().split(",");
			String orderid[] = orderids.trim().split(",");
			String url[] = urls.trim().split(",");
			String code[] = codes.trim().split(",");
			String display[] = displays.trim().split(",");
			String mark[] = marks.trim().split(",");
			//后台检验数据正确性（只是匹配个数）
			if(name.length == orderid.length && name.length == url.length && name.length == orderid.length){
				for(int i = 0 ; i < display.length; i ++){
					if(!checkNumber(orderid[i])){
						continue;
					}
					UnitChannel channelIni = new UnitChannel();
					channelIni.setUnitid(getLoginInfo().getUser().getId());
					channelIni.setDisplay(Integer.parseInt( display[i].trim() ));
					channelIni.setName(name[i].trim());
					channelIni.setCode(code[i].trim());
					channelIni.setMark(Integer.parseInt( mark[i].trim() ));
					channelIni.setOrderid(Integer.parseInt( orderid[i].trim() ));
					channelIni.setUrl(url[i].trim());
					
					channelList.add(channelIni);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<UnitChannel>();
			}
		return channelList;
	}
	
	public boolean checkNumber(String str){
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) > 9 && str.charAt(i) < 0){
				return false;
			}
		}
		return true;
	}

	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getDisplays() {
		return displays;
	}
	public void setDisplays(String displays) {
		this.displays = displays;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getOrderids() {
		return orderids;
	}
	public void setOrderids(String orderids) {
		this.orderids = orderids;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	public static int getMAXCHANEELNUM() {
		return MAXCHANEELNUM;
	}
	public List<UnitChannel> getUnitChannelList() {
		return unitChannelList;
	}
	public void setUnitChannelList(List<UnitChannel> unitChannelLixt) {
		this.unitChannelList = unitChannelLixt;
	}

	public static int getMAXCODELENGTH() {
		return MAXCODELENGTH;
	}

	public static int getMAXNAMELENGTH() {
		return MAXNAMELENGTH;
	}

	public static int getMAXURLLENGTH() {
		return MAXURLLENGTH;
	}

	public static int getMAXORDERIDLENGTH() {
		return MAXORDERIDLENGTH;
	}

	public String getPromptMessage() {
		return promptMessage;
	}

	public void setPromptMessage(String promptMessage) {
		this.promptMessage = promptMessage;
	}

    public void setUnitChannelService(UnitChannelService unitChannelService) {
        this.unitChannelService = unitChannelService;
    }
}
