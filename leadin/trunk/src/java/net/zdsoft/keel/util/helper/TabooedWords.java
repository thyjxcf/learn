/* 
 * @(#)TabooedWords.java    Created on 2006-10-11
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/helper/TabooedWords.java,v 1.3 2007/01/12 02:31:38 liangxiao Exp $
 */
package net.zdsoft.keel.util.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 敏感词汇识别工具类，敏感词汇定义在tabooed.words里
 * 
 * @author liangxiao
 * @version $Revision: 1.3 $, $Date: 2007/01/12 02:31:38 $
 */
public class TabooedWords {

    private static Logger logger = LoggerFactory.getLogger(TabooedWords.class);

    private Map<String, List<String>> tabooedWords;

    /**
     * 构造方法
     */
    public TabooedWords() {
    }

    /**
     * 初始化，读取敏感词汇表
     */
    public synchronized void initialize() {
        if (tabooedWords == null) {
            Map<String, List<String>> _tabooedWords = new HashMap<String, List<String>>();

            InputStream in = null;
            try {
                in = new Nothing().getClass().getClassLoader()
                        .getResourceAsStream("tabooed.words");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));
                String line = null;

                int index = 0;
                while ((line = reader.readLine()) != null) {
                    int separatorIndex = line.indexOf("|");
                    String key = null;
                    String value = null;

                    if (separatorIndex != -1) {
                        key = line.substring(separatorIndex + 1);
                        value = line.substring(0, separatorIndex);
                    }
                    else {
                        key = "";
                        value = line;
                    }

                    List<String> list = _tabooedWords.get(key);
                    if (list == null) {
                        list = new ArrayList<String>();
                        _tabooedWords.put(key, list);
                    }
                    list.add(value);

                    logger.debug("tabooedWords[" + ++index + "] " + line);
                }

                tabooedWords = _tabooedWords;
            }
            catch (Exception e) {
                logger.error("Could not read tabooed.words", e);
            }
            finally {
                FileUtils.close(in);
            }
        }
    }

    /**
     * 判断是否包含敏感词汇
     * 
     * @param str
     *            字符串
     * @return 是true，否则false
     */
    public boolean isTabooed(String str) {
        if (str == null) {
            return false;
        }

        if (tabooedWords == null) {
            initialize();
        }

        if (tabooedWords == null) {
            return false;
        }

        Iterator<Map.Entry<String, List<String>>> iterator = tabooedWords.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();

            String key = (String) entry.getKey();
            if ("".equals(key) || str.indexOf(key) == -1) {
                continue;
            }

            List<String> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i);
                if (str.indexOf(value) != -1) {
                    return true;
                }
            }
        }

        List<String> list = tabooedWords.get("");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String value = (String) list.get(i);
                if (str.indexOf(value) != -1) {
                    return true;
                }
            }
        }

        return false;
    }

}
