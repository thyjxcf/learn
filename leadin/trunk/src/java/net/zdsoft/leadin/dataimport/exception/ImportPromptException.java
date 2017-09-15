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
public class ImportPromptException  extends Exception {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5228368165124344987L;

    public ImportPromptException() {
        super();
    }

    public ImportPromptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ImportPromptException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ImportPromptException(Throwable cause) {
        super(cause);
    }
}
