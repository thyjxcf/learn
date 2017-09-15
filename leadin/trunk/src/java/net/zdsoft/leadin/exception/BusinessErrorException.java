/* 
 * @(#)BusinessErrorException.java    Created on Dec 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.exception;

/**
 * 业务错误异常，主要用于service层
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 22, 2010 12:45:21 PM $
 */
public class BusinessErrorException extends RuntimeException {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2577942338083210861L;

    public BusinessErrorException(String message, Throwable cause) {
        super(message, cause);

    }

    public BusinessErrorException(String message) {
        super(message);
    }

}
