package net.zdsoft.eis.frame.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.frame.entity.RemoteJsonCode;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.util.ZipUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class RemoteBaseAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String remoteParam;
    private JSONObject json;
    private JSONObject jsonParam;
    private String encode;
    
    /**
     * 打印显示信息，默认是加密打印的内容
     * @param s
     * @throws IOException
     */
    protected String print(String s) throws IOException {
        if (StringUtils.equals("1", encode)) {
            s = ZipUtils.zipEncode(s);
        }
        ServletUtils.print(getResponse(), s);
        return NONE;
    }

    
    /**
     * 发送结果
     * 
     * @param value
     * @throws IOException
     */
    protected void sendResult(String value) throws IOException {
        String remoteZip = getParamValue(RemoteCallUtils.REMOTE_ZIP);
        if (StringUtils.isBlank(remoteZip) || StringUtils.equals(remoteZip, "1")) {
            ServletUtils.print(getResponse(), RemoteCallUtils.encode(value));
        }
        else
            ServletUtils.print(getResponse(), value);
    }
    
    protected void addJsonData(JSONObject json, JSONArray data) {
        json.put("data", data);
    }

    /**
     * 取得参数值
     * 
     * @param name
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String getParamValue(String name) {
        if(remoteParam == null) {
            Map map = getRequest().getParameterMap();
            Object v = map.get(name);
            if(v == null)
                return getRequest().getHeader(name);
            else
                return ((Object[])v)[0].toString();
        }
        else {
            if (getJsonParam().containsKey(name)) {
                return getJsonParam().getString(name);
            }
            else {
                return "";
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected Set<String> getParameterNames(){
        return getRequest().getParameterMap().keySet();
    }
    
    public int getParamValueInt(String name) {
        return NumberUtils.toInt(getParamValue(name));
    }
    
    public Date getParamValueDate(String name, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(getParamValue(name));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得返回的原始参数
     * @return
     */
    public JSONObject getJson() {
        if (json != null)
            return json;
        String param = getRemoteParam();
        param = RemoteCallUtils.decode(remoteParam);
        if (StringUtils.isBlank(param)) {
            return new JSONObject();
        }
        try {
            json = JSONObject.fromObject(param);
            return json;
        }
        catch (Exception e) {
            return new JSONObject();
        }
    }
    
    protected String createSuccess(JSONObject json) {
        if (!json.containsKey("result")) {
            json.put("result", RemoteJsonCode.SUCCESS);
            json.put("message", "操作成功！");
        }
        String s = json.toString();
        if (StringUtils.equals("1", encode)) {
            s = ZipUtils.zipEncode(s);
        }
        return s;
    }
    
    protected String printSuccess(JSONObject json) {
        if (!json.containsKey("result")) {
            json.put("result", RemoteJsonCode.SUCCESS);
            json.put("message", "操作成功！");
        }
        String s = json.toString();
        if (StringUtils.equals("1", encode)) {
            s = ZipUtils.zipEncode(s);
        }
        try {
            ServletUtils.print(getResponse(), s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
    
    protected String printSuccess(String s) {
        if (StringUtils.equals("1", encode)) {
            s = ZipUtils.zipEncode(s);
        }
        try {
            ServletUtils.print(getResponse(), s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    protected String printError(JSONObject json) {
        if (!json.containsKey("result")) {
            json.put("result", RemoteJsonCode.OTHER_ERROR);
            json.put("message", "其他错误！");
        }
        if (!json.containsKey("data")) {
            json.put("data", new String[] {});
        }
        String s = json.toString();
        if (StringUtils.equals("1", encode)) {
            s = ZipUtils.zipEncode(s);
        }
        try {
            ServletUtils.print(getResponse(), s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 取得经过解析后的返回参数
     * @return
     */
    public JSONObject getJsonParam() {
        if (jsonParam != null)
            return jsonParam;
        JSONObject jsonv = getJson();
        if (jsonv.containsKey(RemoteCallUtils.JSON_PARAM)) {
            jsonParam = jsonv.getJSONObject(RemoteCallUtils.JSON_PARAM);
        }
        else {
            jsonParam = new JSONObject();
        }
        return jsonParam;
    }

    public String getRemoteParam() {
        return remoteParam;
    }

    public void setRemoteParam(String remoteParam) {
        this.remoteParam = remoteParam;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

}
