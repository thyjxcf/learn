package net.zdsoft.eis.base.tree.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;

public interface CommonClassTreeService {

    /**
     * 
     * @param schid
     * @param contextPath
     * @param treeType
     * @param allLinkOpen
     * @param treeLevel
     * @param classList
     * @return
     */
    public String getXTree(String schid, String contextPath, String treeType, boolean allLinkOpen,
            String treeLevel, List<BasicClass> classList) throws Exception;
}
