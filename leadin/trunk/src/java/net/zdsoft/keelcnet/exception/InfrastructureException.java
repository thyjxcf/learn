/* 
 * @(#)InfrastructureException.java    Created on 2006-12-11
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keelcnet/exception/InfrastructureException.java,v 1.2 2006/12/30 07:15:39 jiangl Exp $
 */
package net.zdsoft.keelcnet.exception;

public class InfrastructureException extends RuntimeException {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7283485001321196111L;

    public InfrastructureException(Throwable cause) {
        super(cause);
    }

    public InfrastructureException(String message) {
        super(message);
    }

    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

}
