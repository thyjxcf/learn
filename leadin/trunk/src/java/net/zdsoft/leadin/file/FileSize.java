/* 
 * @(#)FileSize.java    Created on Jul 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.file;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 9, 2010 3:50:25 PM $
 */

public class FileSize {
    private static final float KB_SIZE = 1024.0F;
    private static final float MB_SIZE = 1048576.0F;
    private static final String KB = " kb";
    private static final String MB = " Mb";

    public static String format(Long filesize) {
        return format(filesize.longValue());
    }

    public static String format(long filesize) {
        if ((float) filesize > 1048576.0F) {
            return formatMB(filesize);
        }
        if ((float) filesize > 1024.0F) {
            return formatKB(filesize);
        }

        return formatBytes(filesize);
    }

    private static String formatMB(long filesize) {
        NumberFormat mbFormat = new DecimalFormat();
        mbFormat.setMinimumIntegerDigits(1);
        mbFormat.setMaximumFractionDigits(2);
        mbFormat.setMinimumFractionDigits(2);
        float mbsize = (float) filesize / MB_SIZE;
        return mbFormat.format(mbsize) + MB;
    }

    private static String formatKB(long filesize) {
        long kbsize = Math.round((float) filesize / KB_SIZE);
        return String.valueOf(kbsize) + KB;
    }

    private static String formatBytes(long filesize) {
        NumberFormat bFormat = new DecimalFormat();
        bFormat.setMinimumIntegerDigits(1);
        bFormat.setMaximumFractionDigits(1);
        bFormat.setMinimumFractionDigits(1);
        float mbsize = (float) filesize / 1024.0F;
        return bFormat.format(mbsize) + KB;
    }
}
