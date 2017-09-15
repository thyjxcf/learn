/* 
 * @(#)CutomConfigurationProvider.java    Created on Aug 24, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.struts;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import com.opensymphony.util.FileManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 24, 2010 11:14:47 AM $
 */
public class CutomConfigurationProvider extends XmlConfigurationProvider {
    private static final String FILE_PATTERN = "classpath*:/conf/xwork/ww-*.xml";

    private Set<String> loadedFileUrls = new HashSet<String>();
    
    public CutomConfigurationProvider() {
        Map<String, String> mappings = new HashMap<String, String>();
        mappings.put("-//OpenSymphony Group//XWork 2.1.3//EN", "xwork-2.1.3.dtd");
        mappings.put("-//OpenSymphony Group//XWork 2.1//EN", "xwork-2.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 2.0//EN", "xwork-2.0.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.1.1//EN", "xwork-1.1.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.1//EN", "xwork-1.1.dtd");
        mappings.put("-//OpenSymphony Group//XWork 1.0//EN", "xwork-1.0.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.0//EN",
                "struts-2.0.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1//EN",
                "struts-2.1.dtd");
        mappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN",
                "struts-2.1.7.dtd");
        setDtdMappings(mappings);
    }
    
    /**
     * XmlConfigurationProvider 有bug(第951行),loadConfigurationFiles方法中的loadedFileUrls.add(url.toString());应该在循环里面
     */
    @Override
    public boolean needsReload() {
        for (String url : loadedFileUrls) {
            if (FileManager.fileNeedsReloading(url)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected Iterator<URL> getConfigurationUrls(String fileName) throws IOException {
        List<URL> urls = new ArrayList<URL>();
        Resource[] resources = getAllResourcesUrl();
        for (Resource resource : resources) {
            urls.add(resource.getURL());
            loadedFileUrls.add(resource.getURL().toString());
        }

        return urls.iterator();
    }

    /**
     * 获取系统中需要搜寻的struts的配置
     * 
     * @return
     * @throws IOException
     */
    private Resource[] getAllResourcesUrl() {
        ResourcePatternResolver resoler = new PathMatchingResourcePatternResolver();
        try {
            return resoler.getResources(FILE_PATTERN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Resource[0];
    }

}
