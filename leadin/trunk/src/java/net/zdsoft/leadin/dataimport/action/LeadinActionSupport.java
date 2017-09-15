/* 
 * @(#)LeadinActionSupport.java    Created on Aug 19, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 19, 2010 4:08:24 PM $
 */
public class LeadinActionSupport extends ActionSupport {

    private static final long serialVersionUID = -8804073614798673225L;

    // 得到request
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    
    public LoginUser getLoginUser() {
        return (LoginUser) ActionContext.getContext().getSession().get("LOGINUSER");
    }
    
    public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAILURE = 0;
	
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
		return NONE;
	}

	protected String responseArrayJSON(Object obj) {
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(obj);
		try {
			ServletUtils.print(getResponse(), jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	protected String responseObjectJSON(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	protected String responseReplyJSON(Reply reply) {
		jsonMap.put("reply", reply);
		return responseJSON(jsonMap);
	}
	
	protected String responseFailureJSON(String msg) {
		jsonMap.put("result", RESULT_FAILURE);
		jsonMap.put("msg", msg);
		return responseJSON(jsonMap);
	}

	protected String responseSuccessJSON(String msg) {
		jsonMap.put("result", RESULT_SUCCESS);
		jsonMap.put("msg", msg);
		return responseJSON(jsonMap);
	}
}
