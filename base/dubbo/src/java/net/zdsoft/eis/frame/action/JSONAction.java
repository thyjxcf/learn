package net.zdsoft.eis.frame.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.keel.util.ServletUtils;
import org.json.simple.JSONValue;

public class JSONAction extends PageAction {
    private static final long serialVersionUID = 4123958528540218129L;

    protected Map<String, Object> jsonMap = new HashMap<String, Object>();

    protected void responseJSON(Map<String, Object> jsonMap) {
        JSONObject jsonObject = new JSONObject();
        for (String key : jsonMap.keySet()) {
            jsonObject.put(key, jsonMap.get(key));
        }
        try {
            ServletUtils.print(this.getResponse(), jsonObject.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void responseErrorJson(String errorCode) {
        jsonMap.put("result", BaseConstant.INT_NO);
        jsonMap.put("code", errorCode);
        responseJSON(jsonMap);
    }

    protected void responseSuccessJson(String errorCode) {
        jsonMap.put("result", BaseConstant.SUCCESS);
        jsonMap.put("code", errorCode);
        responseJSON(jsonMap);
    }

    @SuppressWarnings("unused")
    private void responseAllJSON(Object allList) {// 比较复杂的数据结构如map里又有List
        try {
            HttpServletResponse response = this.getResponse();
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JSONValue.toJSONString(allList);
            writer.print(json);
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void responseJSON(Object obj) {
        JSONArray jsonarray = new JSONArray();
        jsonarray = JSONArray.fromObject(obj);
        try {
            ServletUtils.print(this.getResponse(), jsonarray.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
