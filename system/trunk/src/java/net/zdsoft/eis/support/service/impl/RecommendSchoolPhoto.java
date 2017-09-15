/* 
 * @(#)RecommendSchoolPhoto.java    Created on 2007-9-1
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.support.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import net.zdsoft.keel.util.ImageUtils;

public class RecommendSchoolPhoto {

    public void saveRecSchPhoto(String filePath, String unitunionid,
            String photoid, File photoFile, String fileRealname,
            ServletContext context) throws IOException {
        String photopath = filePath + File.separator + unitunionid;
        String _name = photoFile.getName();
        if (fileRealname != null)
            _name = fileRealname;
        if (_name.lastIndexOf(".") > -1)
            photopath += _name.substring(_name.lastIndexOf("."));
        // 有照片上传

        if (photoFile.length() > 0) {
            ImageUtils.changeOppositeSize(photoFile.getAbsolutePath(),
                    photopath, 110, 110);
            // FileUtils.copyFile(photoFile, new File(photopath));
        }

    }

    public void saveRecSchPhoto(String filePath, String unitunionid,
            String photoid, File photoFile, String fileRealname,
            ServletContext context, int width, int height) throws IOException {
        String photopath = filePath + File.separator + unitunionid;
        String _name = photoFile.getName();
        if (fileRealname != null)
            _name = fileRealname;
        if (_name.lastIndexOf(".") > -1)
            photopath += _name.substring(_name.lastIndexOf("."));
        // 有照片上传

        if (photoFile.length() > 0) {
            ImageUtils.changeOppositeSize(photoFile.getAbsolutePath(),
                    photopath, width, height);
            // FileUtils.copyFile(photoFile, new File(photopath));
        }

    }

}
