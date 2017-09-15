package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.RegionService;

import org.apache.commons.lang.StringUtils;

public class RemoteRegionDataAction extends OfficeJsonBaseAction{
	
	private RegionService regionService;
	
	public void getRegionData(){
		
		List<Region> list = regionService.getRegions();
		List<Region> plist = new ArrayList<Region>();//省
		Map<String, List<Region>> cmap = new HashMap<String,List<Region>>();//省市
		Map<String, List<Region>> dmap = new HashMap<String,List<Region>>();//市区
		for(Region r : list){
			String regionCode = r.getRegionCode();
			if(StringUtils.isBlank(regionCode))
				continue;
			if("00".equals(regionCode))//屏蔽未知
				continue;
			if(regionCode.length()==2){//省
				plist.add(r);
			}else if(regionCode.length()==4){//市
				String pcode = regionCode.substring(0, 2);
				if(cmap.containsKey(pcode)){
					cmap.get(pcode).add(r);
				}else{
					List<Region> l = new ArrayList<Region>();
					l.add(r);
					cmap.put(pcode, l);
				}
			}else if(regionCode.length()==6){//区
				String ccode = regionCode.substring(0, 4);
				if(dmap.containsKey(ccode)){
					dmap.get(ccode).add(r);
				}else{
					List<Region> l = new ArrayList<Region>();
					l.add(r);
					dmap.put(ccode, l);
				}
			}
			
		}
		
		JSONArray array = new JSONArray();
		for(Region r : plist){
			//省
			JSONObject pjson = new JSONObject();
			pjson.put("name", r.getRegionName());
			pjson.put("code", r.getRegionCode());
			pjson.put("fullCode", r.getFullCode());
			pjson.put("fullName", r.getFullName());
			//市
			JSONArray carray = new JSONArray();
			if(cmap.containsKey(r.getRegionCode())){
				List<Region> clist = cmap.get(r.getRegionCode());
				for(Region re : clist){
					JSONObject cjson = new JSONObject();
					cjson.put("name", re.getRegionName());
					cjson.put("code", re.getRegionCode().substring(2, 4));
					cjson.put("fullCode", re.getFullCode());
					cjson.put("fullName", re.getFullName());
					//区
					JSONArray darray = new JSONArray();
					if(dmap.containsKey(re.getRegionCode())){
						List<Region> dlist = dmap.get(re.getRegionCode());
						for(Region reg : dlist){
							JSONObject djson = new JSONObject();
							djson.put("name", reg.getRegionName());
							djson.put("code", reg.getRegionCode().substring(4));
							djson.put("fullCode", reg.getFullCode());
							djson.put("fullName", reg.getFullName());
							darray.add(djson);
						}
					}
					cjson.put("district", darray);
					carray.add(cjson);
				}
			}
			
			pjson.put("city", carray);
			array.add(pjson);
		}
		
		responseArrayJSON(array);
	}
	

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

}
