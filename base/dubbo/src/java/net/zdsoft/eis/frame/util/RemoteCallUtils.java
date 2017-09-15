package net.zdsoft.eis.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.entity.RemoteJsonCode;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.leadin.util.ZipUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class RemoteCallUtils {
    private static final int JSON_STATUS_SUCCESS = 1;
    private static final int JSON_STATUS_ERROR = -1;
    private static final String PARAM_NAME = "remoteParam";

    public static final String REMOTE_ZIP = "remoteZip";
    public static final String JSON_STATUS = "status";
    public static final String JSON_TASK_ID = "taskId";
    public static final String JSON_USERNAME = "username";
    public static final String JSON_PASSWORD = "password";
    public static final String JSON_AUTH = "auth";
    public static final String JSON_RESULT = "result";
    public static final String JSON_RESULT_STATUS = "result_status";
    public static final String JSON_RESULT_STR = "result_str";
    public static final String JSON_RESULT_JSON = "result_json";
    public static final String JSON_RESULT_ARRAY = "result_array";
    public static final String JSON_PARAM = "params";
    public static final String JSON_ACTION = "actionName";
    public static final String JSON_DATA_TIMIE = "dataTime";

    /**
     * 组装发送的json
     * 
     * @param json
     * @return
     */
    public static JSONObject convertJson(JSONObject json) {
        JSONObject jsonj = new JSONObject();
        jsonj.put(JSON_RESULT_JSON, json);
        jsonj.put(JSON_RESULT_STATUS, 1);
        return jsonj;
    }

    /**
     * 是否成功调用
     * 
     * @param result
     * @return
     */
    public static boolean isSuccess(JSONObject result) {
        if (result.getInt(JSON_STATUS) == 1
                && result.getJSONObject(JSON_RESULT).getInt(JSON_RESULT_STATUS) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 结果返回的是json数组
     * 
     * @param result
     * @return
     */
    public static JSONArray getResultArray(JSONObject result) {
        JSONObject json = result.getJSONObject(JSON_RESULT);
        if (json.containsKey(JSON_RESULT_ARRAY))
            return json.getJSONArray(JSON_RESULT_ARRAY);
        else
            return null;
    }

    /**
     * 结果返回的是一个对象
     * 
     * @param result
     * @return
     */
    public static JSONObject getResultObject(JSONObject result) {
        JSONObject json = result.getJSONObject(JSON_RESULT);
        if (json.containsKey(JSON_RESULT_JSON))
            return json.getJSONObject(JSON_RESULT_JSON);
        else
            return null;
    }

    /**
     * 结果返回的是一个字符串
     * 
     * @param result
     * @return
     */
    public static String getResultStr(JSONObject result) {
        JSONObject json = result.getJSONObject(JSON_RESULT);
        if (json.containsKey(JSON_RESULT_STR))
            return json.getString(JSON_RESULT_STR);
        else
            return null;
    }

    /**
     * 将一个数组，或者集合对象转换为json格式（用于action返回）
     * 
     * @param os
     * @return
     */
    public static <T> JSONObject convertJsons(Object os) {
        JSONObject json = new JSONObject();
        JsonConfig config = new JsonConfig();
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.setIgnoreDefaultExcludes(false);
        config.setIgnoreJPATransient(true);
        config.setIgnorePublicFields(true);
        config.setIgnoreTransientFields(true);

        json.put(JSON_RESULT_ARRAY, JSONArray.fromObject(os, config));
        json.put(JSON_RESULT_STATUS, 1);
        return json;
    }

    /**
     * 返回业务上的错误信息，譬如参数不对等（用于action返回）
     * 
     * @param msg
     * @return
     * @throws Exception
     */
    public static <T> JSONObject convertError(String msg) throws Exception {
        JSONObject json = new JSONObject();
        json.put(JSON_RESULT_STATUS, -1);
        json.put(JSON_RESULT_STR, msg);
        return json;
    }

    /**
     * 将一个对象或者字符串转为json格式（用于action返回）
     * 
     * @param o
     * @return
     */
    public static <T> JSONObject convertJson(T o) {
        JSONObject json = new JSONObject();
        if (o instanceof String) {
            json.put(JSON_RESULT_STR, o.toString());
            json.put(JSON_RESULT_STATUS, 1);
        }
        else {
            JsonConfig config = new JsonConfig();
            config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            config.setIgnoreDefaultExcludes(false);
            config.setIgnoreJPATransient(true);
            config.setIgnorePublicFields(true);
            config.setIgnoreTransientFields(true);

            json.put(JSON_RESULT_JSON, JSONObject.fromObject(o, config));
            json.put(JSON_RESULT_STATUS, 1);
        }
        return json;
    }

    /**
     * 调用远程访问
     * 
     * @param url
     * @param params
     * @return
     */
    public static String sendUrl(String url, JSONObject params) {
        return sendUrl(url, params, true);
    }

    /**
     * 调用远程访问
     * 
     * @param url
     * @param params
     *            json格式的参数
     * @param sync
     *            是否同步，1 = 同步，0 = 异步（异步暂时用不到，没有去实现）
     * @return
     */
    public static String sendUrl(String url, JSONObject params, boolean sync) {
        Set<String> set = new HashSet<String>();
        for (Object keyo : params.keySet()) {
            String key = keyo.toString();
            String value = params.getString(key);
            set.add(key + "=" + value);
        }
        if (StringUtils.contains(url, "?")) {
            url += "&";
        }
        else {
            url += "?";
        }
        url += StringUtils.join(set.toArray(new String[0]), "&");
        return sendUrl(url, sync);
    }

    /**
     * 调用远程访问
     * 
     * @param url
     * @return
     */
    public static String sendUrl(String url) {
        return sendUrl(url, true);
    }

    /**
     * 调用远程访问
     * 
     * @param url
     * @param params
     *            json格式的参数
     * @param sync
     *            是否同步，1 = 同步，0 = 异步（异步暂时用不到，没有去实现）
     * @return
     */
    public static String sendUrlDirect(String url, JSONObject params) {
        Set<String> set = new HashSet<String>();
        for (Object keyo : params.keySet()) {
            String key = keyo.toString();
            String value = params.getString(key);
            set.add(key + "=" + value);
        }
        if (StringUtils.contains(url, "?")) {
            url += "&";
        }
        else {
            url += "?";
        }
        url += StringUtils.join(set.toArray(new String[0]), "&");
        return sendUrlDirect(url);
    }

    public static String sendUrl(String url, Map<String, String> headerMap,
            Map<String, String> formMap) throws IOException {
        if (!StringUtils.startsWith(url.toLowerCase(), "http")) {
            String domain = RedisUtils.get("EIS.BASE.PATH.V6");
            if(StringUtils.isBlank(domain)) {
                HttpServletRequest req = ServletActionContext.getRequest();
                if(req != null) {
                    domain = BootstrapManager.getBaseUrl();
                }
            }
            if(StringUtils.isNotBlank(domain)) {
                url = domain + url;
            }
        }

        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if (headerMap != null)
            for (String key : headerMap.keySet()) {
                connection.addRequestProperty(key, headerMap.get(key));
            }

        if (formMap != null) {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String[] ss = new String[formMap.size()];
            int count = 0;
            for (String key : formMap.keySet()) {
                ss[count] = key + "=" + formMap.get(key);
                count++;
            }

            out.write(StringUtils.join(ss, "&").getBytes("utf-8"));
            out.flush();
            out.close();
        }

        connection.connect();
        InputStream is = connection.getInputStream();
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length;
        while ((length = is.read(buffer)) != -1) {
            outs.write(buffer, 0, length);
        }
        return new String(outs.toByteArray(), "utf-8");
    }

    /**
     * 远程调用，不封装结果，直接返回url的结果内容
     * 
     * @param url
     * @return
     */
    public static String sendUrlDirect(String url) {

        if (!StringUtils.startsWith(url.toLowerCase(), "http")) {
            url = BootstrapManager.getBaseUrl() + url;
        }
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(url)) {
            json.put(JSON_STATUS, RemoteJsonCode.OTHER_ERROR);
            return json.toString();
        }
        String actionName = "";
        String param = "";
        if (StringUtils.contains(url, "?")) {
            actionName = StringUtils.substringBefore(url, "?");
            param = StringUtils.substringAfter(url, "?");
        }
        else {
            actionName = url;
        }

        JSONObject jsonParam = new JSONObject();

        if (StringUtils.isNotBlank(param)) {
            String[] paramFulls = StringUtils.split(param, "&");
            for (String paramFull : paramFulls) {
                String[] pps = StringUtils.split(paramFull, "=");
                String name = StringUtils.trimToEmpty(pps[0]);
                String value;
                if (pps.length == 2)
                    value = StringUtils.trimToEmpty(pps[1]);
                else
                    value = "";
                jsonParam.put(name, value);
            }
        }
        JSONObject result = new JSONObject();
        json.put(JSON_PARAM, jsonParam);
        String s = json.toString();
        // json.put(JSON_ACTION, actionName);
        try {
            String content = URLUtils.readContent(actionName, PARAM_NAME, encode(s), true);
            if (!content.startsWith("{"))
                content = decode(content);
            return content;
        }
        catch (Exception e) {
            result.put(JSON_STATUS, JSON_STATUS_ERROR);
            result.put(JSON_RESULT, e.getMessage());
            return result.toString();
        }
    }

    /**
     * 调用远程访问
     * 
     * @param url
     * @param sync
     *            是否同步，1 = 同步，0 = 异步（异步暂时用不到，没有去实现）
     * @return
     */
    public static String sendUrl(String url, boolean sync) {
        if (!StringUtils.startsWith(url.toLowerCase(), "http")) {
            url = BootstrapManager.getBaseUrl() + url;
        }
        JSONObject json = new JSONObject();
        // json.put(JSON_TASK_ID, UUIDUtils.newId());
        if (StringUtils.isBlank(url)) {
            json.put(JSON_STATUS, -1);
            return json.toString();
        }
        String actionName = "";
        String param = "";
        if (StringUtils.contains(url, "?")) {
            actionName = StringUtils.substringBefore(url, "?");
            param = StringUtils.substringAfter(url, "?");
        }
        else {
            actionName = url;
        }

        JSONObject jsonParam = new JSONObject();

        if (StringUtils.isNotBlank(param)) {
            String[] paramFulls = StringUtils.split(param, "&");
            for (String paramFull : paramFulls) {
                String[] pps = StringUtils.split(paramFull, "=");
                String name = StringUtils.trimToEmpty(pps[0]);
                String value;
                if (pps.length == 2)
                    value = StringUtils.trimToEmpty(pps[1]);
                else
                    value = "";
                jsonParam.put(name, value);
            }
        }
        json.put(JSON_PARAM, jsonParam);
        String s = json.toString();
        // String sendUrl = actionName + "?" + PARAM_NAME + "=" + encode(s);
        json.put(JSON_ACTION, actionName);
        if (sync)
            try {
                String content = URLUtils.readContent(actionName, PARAM_NAME, encode(s), true);
                // String content = URLUtils.readContent(sendUrl);
                if (!content.startsWith("{"))
                    content = decode(content);
                json.put(JSON_STATUS, JSON_STATUS_SUCCESS);
                json.put(JSON_RESULT, content);
            }
            catch (Exception e) {
                json.put(JSON_STATUS, JSON_STATUS_ERROR);
                json.put(JSON_RESULT, e.getMessage());
                e.printStackTrace();
                return json.toString();
            }
        else
            return "";

        return json.toString();
    }

    /**
     * 内容进行加密以及编码压缩
     * 
     * @param s
     * @return
     */
    public static String encode(String s) {
        // return SecurityUtils.encodeByMD5(SecurityUtils.encodeByMD5(s) + s) +
        // ZipUtils.zipEncode(s);
        return BaseConstant.ZERO_GUID + ZipUtils.zipEncode(s);
    }

    /**
     * 内容进行解密以及反编码压缩
     * 
     * @param s
     * @return
     */
    public static String decode(String s) {
        // MD5加密先去掉
        // String md5 = StringUtils.substring(s, 0, 32);
        String zips = ZipUtils.unzipDecode(StringUtils.substring(s, 32));
        // String checkMd5 =
        // SecurityUtils.encodeByMD5(SecurityUtils.encodeByMD5(zips) + zips);
        // if (StringUtils.equals(md5, checkMd5)) {
        // return zips;
        // }
        // else {
        // return "";
        // }
        return zips;
    }

    public static void main(String[] args) throws Exception, IOException {
        String regex = "^/remote-biz/([v.0-9]+)/([0-9a-zA-Z]+)[?]*(?!.*\\/).*$";
        String regex2 = "^/remote-biz/([v.0-9]+)/([0-9a-zA-Z]+)/([0-9a-zA-Z]{32})([?]*)([^/.]*)$";
        String regex3 = "^/remote-biz/([v.0-9]+)/([0-9a-zA-Z]+)/([0-9a-zA-Z]{32})/([0-9a-zA-Z]+)([?]*)([^/.]*)$";
        String s = "/remote-biz/v1.1/teacher";
        System.out.println(Pattern.matches(regex, s));
        System.out.println(Pattern.matches(regex2, s));
        System.out.println(Pattern.matches(regex3, s));
        String s2 = "/remote-biz/v1.1/teacher/00000000000000000000000000000000";
        System.out.println(Pattern.matches(regex, s2));
        System.out.println(Pattern.matches(regex2, s2));
        System.out.println(Pattern.matches(regex3, s2));
        String s3 = "/remote-biz/v1.1/teacher/00000000000000000000000000000000/school";
        System.out.println(Pattern.matches(regex, s3));
        System.out.println(Pattern.matches(regex2, s3));
        System.out.println(Pattern.matches(regex3, s3));
        // String s = Request.Post("").addHeader("",
        // "").execute().returnContent().asString();
        // System.out.println(s);
        // JSONObject json = new JSONObject();
        // json.put("unitId", "402880944393B2C3014393BAC0580000");
        // String result =
        // sendUrl("http://localhost/common/open/system-getRoles.action", json);
        // JSONObject j = JSONObject.fromObject(result);
        // if (isSuccess(j)) {
        // // 直接对返回结果进行操作
        // System.out.println(j.toString());
        // // 如果返回的是一个字符串，则调用
        // String r = getResultStr(j);
        // // 如果返回的是一个对象，譬如是一个Unit对象，则调用，
        // Role role = (Role) JSONObject.toBean(getResultObject(j), Role.class);
        // // 如果返回的是一个对象数组，譬如List<Unit>，则调用
        // if (getResultArray(j) != null) {
        // System.out.println(getResultArray(j).toString());
        // Role[] roels = (Role[]) JSONArray.toArray(getResultArray(j),
        // Role.class);
        // }
        // }
    }
}
