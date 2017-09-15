/* 
 * @(#)OutputStreamListener.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/OutputStreamListener.java,v 1.3 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2006/10/20 09:23:12 $
 */
public interface OutputStreamListener {
    public void start();
    public void bytesRead(int bytesRead);
    public void error(String message);
    public void done();
}
