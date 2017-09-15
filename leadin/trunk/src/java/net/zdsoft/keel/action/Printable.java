/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.action;

/**
 * 实现这个接口的Action，可以直接在http响应里输出内容，也可以直接使用ServletUtils.print输出内容
 * 
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/15 02:09:20 $
 */
public interface Printable {

    /**
     * 判断是否需要直接输出内容
     * 
     * @return 是true，否则false
     */
    public boolean isPrintEnabled();

    /**
     * 取得需要直接输出的内容
     * 
     * @return 需要直接输出的内容
     */
    public String getPrintContent();

}