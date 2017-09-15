/* 
 * @(#)MonitoredOutputStream.java    Created on Jul 8, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: /project/blog/src/net/zdsoft/blog/upload/MonitoredOutputStream.java,v 1.3 2006/10/20 09:23:12 yangm Exp $
 */
package net.zdsoft.leadin.upload;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2006/10/20 09:23:12 $
 */
public class MonitoredOutputStream extends OutputStream {
    private OutputStream target;
    private OutputStreamListener listener;

    public MonitoredOutputStream(OutputStream target, OutputStreamListener listener)
    {
        this.target = target;
        this.listener = listener;
        this.listener.start();
    }

    public void write(byte b[], int off, int len) throws IOException
    {
        target.write(b,off,len);
        listener.bytesRead(len - off);
    }

    public void write(byte b[]) throws IOException
    {
        target.write(b);
        listener.bytesRead(b.length);
    }

    public void write(int b) throws IOException
    {
        target.write(b);
        listener.bytesRead(1);
    }

    public void close() throws IOException
    {
        target.close();
        listener.done();
    }

    public void flush() throws IOException
    {
        target.flush();
    }
}
