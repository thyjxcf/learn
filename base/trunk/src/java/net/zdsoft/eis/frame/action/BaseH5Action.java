package net.zdsoft.eis.frame.action;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.keel.util.ServletUtils;

public abstract class BaseH5Action extends PageSemesterAction{
	
	public static final String KEY_RESULT = "result";
	public static final String KEY_MSG = "msg";
	public static final String KEY_PAGE = "page";
	public static final String KEY_RESULT_ARRAY = "result_array";
	public static final String KEY_RESULT_OBJECT = "result_object";
	private static final long serialVersionUID = 3181042475063871449L;
	protected JSONObject jsonObject = new JSONObject();

	protected void responseJSON() {
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void responseJSON(JSONObject jsObj) {
		try {
			ServletUtils.print(getResponse(), jsObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void returnMsg(String code,String msg) {
		jsonObject.put(KEY_RESULT, code);
		jsonObject.put(KEY_MSG, msg);
		responseJSON();
	}

	protected void returnErrorMsg(String msg) {
		jsonObject = new JSONObject();
		jsonObject.put(KEY_RESULT, "0");
		jsonObject.put(KEY_MSG, msg);
		responseJSON();
	}

	protected void returnSuccessMsg(String msg) {
		jsonObject.put(KEY_RESULT, "1");
		jsonObject.put(KEY_MSG, msg);
		responseJSON();
	}
	
	/**
	 * 
	 * @param jsArray
	 * @param isPage 是否分页
	 */
	protected void setListObject(JSONArray jsArray,boolean isPage){
		jsonObject.put(KEY_RESULT_ARRAY, jsArray);
		if(isPage)
			jsonObject.put(KEY_PAGE, getPage());
	}
	
	protected void setDetailObject(JSONObject jsObj){
		jsonObject.put(KEY_RESULT_OBJECT, jsObj);
	}

}
