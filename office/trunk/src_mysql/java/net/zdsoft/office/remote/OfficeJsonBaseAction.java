package net.zdsoft.office.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.ServletUtils;

public abstract class OfficeJsonBaseAction extends PageSemesterAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3181042475063871449L;
	protected Map<String, Object> jsonMap = new HashMap<String, Object>();

	protected String responseJSON(Map<String, Object> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	protected String responseArrayJSON(Object obj) {
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(obj);
		try {
			ServletUtils.print(getResponse(), jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	protected String responseObjectJSON(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	protected String responseFailureJSON(String msg) {
		jsonMap.put("result", "failue");
		jsonMap.put("msg", msg);
		return responseJSON(jsonMap);
	}

	protected String responseSuccessJSON(String msg) {
		jsonMap.put("result", "success");
		jsonMap.put("msg", msg);
		return responseJSON(jsonMap);
	}

	/**
	 * 列表分页
	 * 
	 * @param datas
	 * @return
	 */
	protected String responseListJSON(List<?> datas) {
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(datas);

		jsonMap.put(getListObjectName(), jsonArray);
		jsonMap.put("page", getPage());
		return responseJSON(jsonMap);
	}

	/**
	 * 单个明细对象
	 * 
	 * @param obj
	 * @return
	 */
	protected String responseDetailJSON(Object obj) {
		jsonMap.put("result", "success");
		jsonMap.put(getDetailObjectName(), JSONObject.fromObject(obj));
		return responseJSON(jsonMap);
	}

	/**
	 * 取列表对象名称
	 * 
	 * @return
	 */
	protected abstract String getListObjectName();

	/**
	 * 取明细对象名称
	 * 
	 * @return
	 */
	protected abstract String getDetailObjectName();

	/**
	 * 文本数据
	 * 
	 * @param obj
	 * @return
	 */
	protected String responseText(String txt) {
		try {
			ServletUtils.print(getResponse(), txt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
