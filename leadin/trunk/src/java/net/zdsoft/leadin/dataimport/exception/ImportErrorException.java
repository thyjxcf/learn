/* 
 * @(#)TaskErrorException.java    Created on Sep 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.exception;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: Sep 20, 2009 12:57:40 PM $
 */
public class ImportErrorException extends RuntimeException {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5228368165124344987L;

    public ImportErrorException() {
        super();
    }

    public ImportErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ImportErrorException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ImportErrorException(Throwable cause) {
        super(cause);
    }

}
