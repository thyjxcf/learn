/* 
 * @(#)AttachmentServiceTest.java    Created on Sep 21, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.attachment.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.test.EisTestCase;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 21, 2010 4:04:05 PM $
 */
public class AttachmentServiceTest extends EisTestCase {
    @Autowired
    private AttachmentService attachmentService;

    @Override
    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/baseAttachmentContext.xml" };
    }

    public void testSaveAttachment() {
        Attachment preparePlanAttachment = new Attachment();
        preparePlanAttachment.setFileName("tsete");
        preparePlanAttachment.setContentType("xls");
        preparePlanAttachment.setFileSize(200);
        preparePlanAttachment.setUnitId("38CA848667334B86A7F313D5815A0C66");
        preparePlanAttachment.setObjectId(BaseConstant.ZERO_GUID);
        attachmentService.saveAttachment(preparePlanAttachment);
    }
}
