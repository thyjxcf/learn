/* 
 * @(#)ConversionErrorFieldValidator.java    Created on Dec 7, 2006
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/validator/ConversionErrorFieldValidator.java,v 1.2 2006/12/11 09:11:36 linqz Exp $
 */
package net.zdsoft.leadin.validator;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * ConversionErrorFieldValidator
 * 
 * @author Jason Carreira Date: Nov 28, 2003 1:58:49 PM
 */

/**
 * @author linqz
 * @version $Revision: 1.2 $, $Date: 2006/12/11 09:11:36 $
 */
public class ConversionErrorFieldValidator extends FieldValidatorSupport {
    // ~ Methods
    // ////////////////////////////////////////////////////////////////

    /**
     * 只判断有内容的，如果没有内容，则不做任何处理
     * 
     * @param object
     * @throws ValidationException
     */   
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String fullFieldName = getValidatorContext()
                .getFullFieldName(fieldName);
        
        ActionContext context = ActionContext.getContext();
        Map<String, Object> conversionErrors = context.getConversionErrors();

        if (conversionErrors.containsKey(fullFieldName)) {
            if ((defaultMessage == null) || (defaultMessage.trim().equals(""))) {
                defaultMessage = XWorkConverter.getConversionErrorMessage(
                        fullFieldName, context.getValueStack());
            }
            addFieldError(fieldName, object);
        }
    }
}
