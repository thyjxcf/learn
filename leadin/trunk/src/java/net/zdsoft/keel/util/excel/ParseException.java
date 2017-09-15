/* 
 * @(#)ParseException.java    Created on 2007-4-28
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ParseException.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

/**
 * 表示解析XLS文件发生错误的异常类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public class ParseException extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6160701972085949528L;

    public ParseException() {
        super();
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

}
