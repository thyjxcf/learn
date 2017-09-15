/* 
 * @(#)ModuleTreeService.java    Created on 2006-10-10
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/usermanager/service/ModuleTreeService.java,v 1.10 2007/01/16 10:12:44 luxm Exp $
 */
package net.zdsoft.eis.system.frame.service;

import java.util.Set;

import net.zdsoft.eis.base.tree.service.FastTreeNode;
import net.zdsoft.leadin.tree.WebTree;

/**
 * @author luxingmu
 * @version $Revision: 1.10 $, $Date: 2007/01/16 10:12:44 $
 */
public interface ModuleTreeService {


	/**
	 * 根据单位分类获取相应的所有有效模块树对象
	 * 
	 * @param unitId
	 * @param unitClass
	 * @param ownerType 
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @param selectExtraSubSystemSet
	 * @return
	 */
	public WebTree getModuleTree(String unitId, int unitClass, int ownerType,
			int unitType, Set<Long> selectedModSet,
			Set<Long> selectedOperSet, Set<String> selectExtraSubSystemSet);



	/**
	 * 根据获取相应的所有有效模块树对象
	 * 
	 * @param unitId
	 * @param unitClass
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @param selectExtraSubSystemSet
	 * @param ownerType 
	 * @return
	 */
	public WebTree getUserPermModuleTree(String unitId, int unitClass,
			int unitType, Set<Long> selectedModSet, Set<Long> selectedOperSet,
			Set<String> selectExtraSubSystemSet, int ownerType);

	/**
	 * 根据获取相应的所有有效模块树对象
	 * 
	 * @param unitId
	 * @param unitClass
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @param selectExtraSubSystemSet
	 * @param treeText
	 * @param ownerType 
	 * @return
	 */
	public WebTree getUserPermModuleTree(String unitId, int unitClass,
			int unitType, Set<Long> selectedModSet, Set<Long> selectedOperSet,
			Set<String> selectExtraSubSystemSet, String treeText, int ownerType);
	
	public FastTreeNode getUserPermModuleTree2(int unitClass, int unitType, Set<Integer> selectedModSet,
            Set<String> selectedOperSet);

}
