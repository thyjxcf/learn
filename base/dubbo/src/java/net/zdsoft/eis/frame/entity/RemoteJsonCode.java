package net.zdsoft.eis.frame.entity;

import java.util.HashMap;
import java.util.Map;

public class RemoteJsonCode {
    /**
     * 成功
     */
    final static public String SUCCESS = "1";
    /**
     * 其他错误
     */
    final static public String OTHER_ERROR = "0";
    /**
     * ip验证失败
     */
    final static public String IP_INVALIDATION = "-1";
    /**
     * apcode验证失败
     */
    final static public String TICKET_KEY_INVALIDATION = "-2";
    /**
     * 参数不对
     */
    final static public String PARAM_INVALIDATION = "-3";

    /**
     * 找不到对象/结果错误（应该找到，但是因为参数等其他错误，导致找不到）
     */
    final static public String RESULT_INVALIDATION = "-4";

    static private Map<String, String> map = new HashMap<String, String>();
    
    static public String getMsg(String code) {
        return map.get(code);
    }
    
    static {
        map.put(SUCCESS, "操作成功！");
        map.put(OTHER_ERROR, "错误！");
        map.put(IP_INVALIDATION, "IP认证失败！");
        map.put(TICKET_KEY_INVALIDATION, "Ticket Key认证失败！");
        map.put(PARAM_INVALIDATION, "参数错误！");
        map.put(RESULT_INVALIDATION, "结果错误！");
    }
    
}
