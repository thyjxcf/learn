/* 
 * @(#)UUIDUtils.java    Created on 2006-12-15
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/UUIDUtils.java,v 1.3 2007/01/11 01:17:24 liangxiao Exp $
 */
package net.zdsoft.keel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import net.zdsoft.keel.dao.UUIDGenerator;

/**
 * UUID工具类
 * 
 * @author liangxiao
 * @version $Revision: 1.3 $, $Date: 2007/01/11 01:17:24 $
 */
public class UUIDUtils {
    private static final Logger log = LoggerFactory.getLogger(UUIDUtils.class);
    
    private static UUIDGenerator uuid = new UUIDGenerator();

    private UUIDUtils() {
    }

    /**
     * 产生新的UUID
     * 
     * @return 32位长的UUID字符串
     */
    public static String newId() {
        return uuid.generateHex().toUpperCase();
    }

    public static void main(String[] args) {
    }

    private static Pattern pattern;

    private static final PatternMatcher matcher = new Perl5Matcher();

    static {
        try {
            pattern = new Perl5Compiler()
                    .compile("[A-Fa-f0-9]{32}");
        } catch (MalformedPatternException e) {
            log.error(e.toString());
        }
    }

    /**
     * 校验字符串是否符合GUID的规则
     * 
     * @param guid
     *            要校验的字符串
     * @return
     */
    public static boolean isUUID(String guid) {
        if (guid != null && matcher.matches(guid, pattern)) {
            return true;
        } else {
            return false;
        }
    }
}
