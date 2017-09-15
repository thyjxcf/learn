/* 
 * @(#)Reply.java    Created on 2005-9-14
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/action/Reply.java,v 1.5 2007/01/11 09:15:13 liangxiao Exp $
 */
package net.zdsoft.keel.action;

/**
 * 支持ajax方式的消息回显机制的实现
 * 
 * @author liangxiao
 * @version $Revision: 1.5 $, $Date: 2007/01/11 09:15:13 $
 */
public class Reply extends MessageSupport {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3955180119970274602L;

    private static final String REFRESH_SCRIPT = "location.href = location.href;";

    private Object value;
    private String script;

    /**
     * 构造方法
     */
    public Reply() {
    }

    /**
     * 取得附加的值
     * 
     * @return 附加的值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置附加的值，不宜是非常复杂的对象
     * 
     * @param value
     *            附加的值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 取得要执行的脚本
     * 
     * @return 脚本
     */
    public String getScript() {
        return script;
    }

    /**
     * 设置要执行的脚本
     * 
     * @param script
     *            脚本
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * 设置刷新页面的脚本
     */
    public void setRefreshScript() {
        setScript(REFRESH_SCRIPT);
    }

}
