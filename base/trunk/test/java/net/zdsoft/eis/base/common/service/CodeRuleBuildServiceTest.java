/* 
 * @(#)CodeRuleBuildServiceTest.java    Created on Jun 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.observe.CodeRuleObserveParam;
import net.zdsoft.leadin.cache.CacheManager;
import net.zdsoft.leadin.cache.WrappedCache;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 29, 2010 4:45:12 PM $
 */
public class CodeRuleBuildServiceTest extends BaseServiceTestCase {
    @Autowired
    private CodeRuleBuildService codeRuleBuildService;
    
    @Autowired
    private WrappedCache wrappedCache;
    
    /**
     * 测试辅助类
     */
    private class CodeRule extends Observable{
        public CodeRule() {
            this.addObserver(codeRuleBuildService);
        }
        public void updateCodeRule(){
            this.setChanged();
            this.notifyObservers(new CodeRuleObserveParam(CodeRuleObserveParam.CODETYPE_ALL, null));
        }
    }
    
    public void testUpdate(){
        //放入缓存
        codeRuleBuildService.getCodeRuleDetails("1", 11);
        
        CacheManager cacheManager = (CacheManager)codeRuleBuildService;
        boolean status = wrappedCache.containsKey(cacheManager.fetchCacheIndexKey());
        assertEquals(status, true);
        
        //清除缓存
        CodeRule codeRule = new CodeRule();
        codeRule.updateCodeRule();
        
        //判断缓存是否为空        
        status = wrappedCache.containsKey(cacheManager.fetchCacheIndexKey());
        assertEquals(status, false);
        
    }
}
