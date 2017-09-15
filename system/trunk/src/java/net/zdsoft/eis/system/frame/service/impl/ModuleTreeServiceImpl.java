/* 
 * @(#)ModuleTreeServiceImpl.java    Created on 2006-10-10
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/usermanager/service/impl/ModuleTreeServiceImpl.java,v 1.24 2007/01/19 02:02:56 luxm Exp $
 */
package net.zdsoft.eis.system.frame.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.AppRegistryService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.tree.service.FastTreeNode;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.frame.entity.ModelOperator;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;
import net.zdsoft.eis.system.frame.service.ModuleTreeService;
import net.zdsoft.leadin.tree.TreeNode;
import net.zdsoft.leadin.tree.WebCheckBoxTreeNode;
import net.zdsoft.leadin.tree.WebTree;
import net.zdsoft.leadin.tree.WebTreeNode;

/**
 * @author luxingmu
 * @version $Revision: 1.24 $, $Date: 2007/01/19 02:02:56 $
 */
public class ModuleTreeServiceImpl implements ModuleTreeService {
	private Logger log = LoggerFactory.getLogger(ModuleTreeServiceImpl.class);

	private ModuleService moduleService;

	private ModelOperatorService modelOperatorService;

	private AppRegistryService appRegistryService;

	private SubSystemService subSystemService;
    private SystemDeployService systemDeployService;
    
    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }
    
	public void setAppRegistryService(AppRegistryService appRegistryService) {
		this.appRegistryService = appRegistryService;
	}

	/**
	 * 根据单位分类获取相应的所有有效模块树对象
	 * 
	 * @param unitId
	 * @param unitClass
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @param selectExtraSubSystemSet
	 * @return
	 */
	public WebTree getModuleTree(String unitId, int unitClass, int ownerType,
			int unitType, Set<Long> selectedModSet, Set<Long> selectedOperSet,
			Set<String> selectExtraSubSystemSet) {
		if (selectExtraSubSystemSet == null) {
			selectExtraSubSystemSet = new HashSet<String>();
		}
		WebTree tree = new WebTree("权限设置");
		WebTreeNode root2 = new WebCheckBoxTreeNode("内置子系统",
				"prefix + 'images/department.gif'",
				"prefix + 'images/department.gif'"); // 增加全选节点
		tree.addChildNode(root2);
		List<Module> modules = moduleService.getEnabledModules(unitClass,
				unitType);

		LoginInfo loginInfo = (LoginInfo) ActionContext.getContext()
				.getSession().get(BaseConstant.SESSION_LOGININFO);
		Set<Integer> activeSubsys = loginInfo.getAllActiveSubSytem();

		// List<SubSystemDto> listOfSubsystemUnit = new
		// ArrayList<SubSystemDto>();
		// try {
		// listOfSubsystemUnit =
		// subSystemService.getListOfSubsystem(unitId,ownerType);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// Map<Integer, Integer> mapOfSubsystemUnit = new HashMap<Integer,
		// Integer>();
		// for (SubSystemDto subsystemUnit : listOfSubsystemUnit) {
		// mapOfSubsystemUnit.put(subsystemUnit.getId().intValue(),subsystemUnit.getId().intValue());
		// }
		List<ModelOperator> operations = modelOperatorService
				.getModuleOperationList();
		Map<Integer, WebTreeNode> tempSubSystemMap = new HashMap<Integer, WebTreeNode>();
		Map<Long, WebTreeNode> tempNodeMap = new HashMap<Long, WebTreeNode>();
		List<Module> tempModuleList = new LinkedList<Module>();
		List<SubSystem> seqenceSubSystemOderidList = new ArrayList<SubSystem>();
		Map<String, WebTreeNode> tempSeqenceSubSystemNodeMap = new HashMap<String, WebTreeNode>();
		int nonameSubSystemOrderid = 1000;
		boolean isOrderMode = systemDeployService.isOrderMode();
		for (Module module : modules) {
			if (isOrderMode) {
				// 系统管理不过滤
				if (SubSystem.SUBSYSTEM_SYSTEM != module.getSubsystem()) {
					if (!activeSubsys.contains(module.getSubsystem())) {
						continue;
					}
				}
			}
			WebTreeNode node = new WebCheckBoxTreeNode(module.getName(),
					"modids", String.valueOf(module.getId()), null,
					((selectedModSet != null ? selectedModSet.contains(module
							.getId()) : false) || module.isCommon()), module
							.isCommon(), null, "prefix + 'images/module.gif'",
					"prefix + 'images/module.gif'"); // 由于涉及web路径问题，传入JS
			// web路径参数 prefix
			tempNodeMap.put(module.getId(), node);
			if (module.getParentid() != -1) {
				if (tempNodeMap.get(module.getParentid()) != null) {
					tempNodeMap.get(module.getParentid()).addChildNode(node);
				} else {
					tempModuleList.add(module);
				}
			} else {
				// System.out.println(">>>parentid:"+module.getParentid());
				// 判断用户是否安装了该子系统
				SubSystem subSystem = subSystemService.getSubSystem(module
						.getSubsystem());
				if (null == subSystem) {
					log.error("根据子系统id找不到子系统信息：id=" + module.getSubsystem());
				} 

				WebTreeNode subSystemNode = tempSubSystemMap.get(module
						.getSubsystem());
				if (subSystemNode != null) {
					subSystemNode.addChildNode(node);
				} else {
					// SubSystem subSystem =
					// subSystemDao.getCacheSubSystemById(module.getSubsystem());
					subSystemNode = new WebCheckBoxTreeNode(
							subSystem != null ? subSystem.getName() : "未命名子系统",
							"subsys", "", null, false, false, null,
							"prefix + 'images/grade.gif'",
							"prefix + 'images/grade.gif'");// 由于涉及web路径问题，传入JS
					// web路径参数 prefix
					tempSubSystemMap.put(module.getSubsystem(), subSystemNode);

					if (subSystem != null
							&& subSystem.getOrderid().intValue() != 0) {
					} else {
						nonameSubSystemOrderid++;
						subSystem = new SubSystem();
						subSystem.setCode("subsys");
						subSystem.setOrderid(nonameSubSystemOrderid);
					}
					seqenceSubSystemOderidList.add(subSystem);
					tempSeqenceSubSystemNodeMap.put(subSystem.getCode(),
							subSystemNode);

					// tree.addChildNode(subSystemNode);
					subSystemNode.addChildNode(node);
				}

			}

		}
		Collections.sort(seqenceSubSystemOderidList,
				new Comparator<SubSystem>() {
					public int compare(SubSystem o1, SubSystem o2) {
						return o1.getOrderid().compareTo(o2.getOrderid());
					}
				});

		for (SubSystem subSystem : seqenceSubSystemOderidList) {
			WebTreeNode subSystemNode = tempSeqenceSubSystemNodeMap
					.get(subSystem.getCode());
			root2.addChildNode(subSystemNode);
		}
		for (Module module : tempModuleList) {
			if (tempNodeMap.get(module.getParentid()) != null) {
				tempNodeMap.get(module.getParentid()).addChildNode(
						tempNodeMap.get(module.getId()));
			}
		}
		for (Iterator<ModelOperator> iter = operations.iterator(); iter
				.hasNext();) {
			ModelOperator modelOperator = iter.next();
			WebTreeNode node = new WebCheckBoxTreeNode(modelOperator
					.getOperatorname(), "operids", String.valueOf(modelOperator
					.getId()), null, selectedOperSet != null ? selectedOperSet
					.contains(modelOperator.getId()) : false, false, null,
					"prefix + 'images/function.gif'",
					"prefix + 'images/function.gif'");// 由于涉及web路径问题，传入JS
			// web路径参数 prefix
			if (tempNodeMap.get(modelOperator.getModuleid()) != null) {
				tempNodeMap.get(modelOperator.getModuleid()).addChildNode(node);
			}
		}

		// 设置子系统的节点的选中状态
		for (Iterator<WebTreeNode> iter = tempSubSystemMap.values().iterator(); iter
				.hasNext();) {
			WebCheckBoxTreeNode subNode = (WebCheckBoxTreeNode) iter.next();
			boolean allChildChecked = true;
			for (Iterator<TreeNode> iterator = subNode.getChildrenList()
					.iterator(); iterator.hasNext();) {
				WebCheckBoxTreeNode subChildNode = (WebCheckBoxTreeNode) iterator
						.next();
				if (!subChildNode.isChecked()) {
					allChildChecked = false;
				}
			}
			if (allChildChecked) {
				subNode.setChecked(allChildChecked);
			}
		}
		if (unitId != null) {
			WebTreeNode outerSubSystemRootNode = new WebCheckBoxTreeNode(
					"附属子系统", "prefix + 'images/department.gif'",
					"prefix + 'images/department.gif'"); // 增加全选节点
			tree.addChildNode(outerSubSystemRootNode);
			List<AppRegistry> extraSubSystemList = appRegistryService
					.getAppRegistriesDefaultPerm(unitId);
			for (Iterator<AppRegistry> iter = extraSubSystemList.iterator(); iter
					.hasNext();) {
				AppRegistry appRegistry = iter.next();
				WebTreeNode extraSubNode = new WebCheckBoxTreeNode(
						appRegistry.getSysname(),
						"extraids",
						appRegistry.getId(),
						null,
						selectExtraSubSystemSet != null ? selectExtraSubSystemSet
								.contains(appRegistry.getId())
								: false, false, null,
						"prefix + 'images/grade.gif'",
						"prefix + 'images/grade.gif'");
				outerSubSystemRootNode.addChildNode(extraSubNode);
			}
		}
		return tree;
	}

	/**
	 * 根据获取相应的所有有效模块树对象
	 * 
	 * @param unitId
	 * @param unitClass
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @param selectExtraSubSystemSet
	 * @return
	 */
	public WebTree getUserPermModuleTree(String unitId, int unitClass,
			int unitType, Set<Long> selectedModSet, Set<Long> selectedOperSet,
			Set<String> selectExtraSubSystemSet, int ownerType) {
		return getUserPermModuleTree(unitId, unitClass, unitType,
				selectedModSet, selectedOperSet, selectExtraSubSystemSet,
				"用户权限", ownerType);

	}

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
	 * @return
	 */
	public WebTree getUserPermModuleTree(String unitId, int unitClass,
			int unitType, Set<Long> selectedModSet, Set<Long> selectedOperSet,
			Set<String> selectExtraSubSystemSet, String treeText, int ownerType) {
		WebTree tree = new WebTree(treeText);
		WebTreeNode innerSubSystemRootNode = new WebTreeNode("内置子系统", null,
				null, "prefix + 'images/department.gif'",
				"prefix + 'images/department.gif'"); // 增加全选节点
		tree.addChildNode(innerSubSystemRootNode);

		List<Module> modules = moduleService.getEnabledModules(unitClass,
				unitType);
		List<ModelOperator> operations = modelOperatorService
				.getModuleOperationList();

		Map<Integer, WebTreeNode> tempSubSystemMap = new HashMap<Integer, WebTreeNode>();
		Map<Long, WebTreeNode> tempNodeMap = new HashMap<Long, WebTreeNode>();
		List<Module> tempModuleList = new LinkedList<Module>();
		List<Integer> seqenceSubSystemOderidList = new ArrayList<Integer>();
		Map<Integer, WebTreeNode> tempSeqenceSubSystemNodeMap = new HashMap<Integer, WebTreeNode>();
		int nonameSubSystemOrderid = 1000;
		Integer orderid = null;
		// Map<Long, Long> mapOfSubsystemUnit = new HashMap<Long, Long>();
		// List<SubSystem> listOfSubsystemUnit = new ArrayList<SubSystem>();

		LoginInfo loginInfo = (LoginInfo) ActionContext.getContext()
				.getSession().get(BaseConstant.SESSION_LOGININFO);
		Set<Integer> activeSubsys = loginInfo.getAllActiveSubSytem();

		// try {
		// listOfSubsystemUnit =
		// subSystemService.getListOfSubsystem(unitId,ownerType);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// for (SubSystem subsystemUnit : listOfSubsystemUnit) {
		// mapOfSubsystemUnit
		// .put(subsystemUnit.getId(), subsystemUnit.getId());
		// }
		for (Iterator<Module> iter = modules.iterator(); iter.hasNext();) {
			Module module = iter.next();

			if (systemDeployService.isOrderMode()) {
				// 系统管理不过滤
				if (SubSystem.SUBSYSTEM_SYSTEM != module.getSubsystem()) {
					if (!activeSubsys.contains(module.getSubsystem())) {
						continue;
					}
				}
			}

			WebTreeNode node = null;
			if (selectedModSet.contains(module.getId())) {
				node = new WebTreeNode(module.getName(), null, null,
						"prefix + 'images/module.gif'",
						"prefix + 'images/module.gif'");// 由于涉及web路径问题，传入JS
				// web路径参数 prefix
				tempNodeMap.put(module.getId(), node);
			}

			if (node != null) {
				if (module.getParentid() != -1) {
					if (tempNodeMap.get(module.getParentid()) != null) {
						tempNodeMap.get(module.getParentid())
								.addChildNode(node);
					} else {
						tempModuleList.add(module);
					}
				} else {
					WebTreeNode subSystemNode = tempSubSystemMap.get(module
							.getSubsystem());
					if (subSystemNode != null) {
						subSystemNode.addChildNode(node);
					} else {
						SubSystem subSystem = subSystemService
								.getSubSystem(module.getSubsystem());
						subSystemNode = new WebTreeNode(
								subSystem != null ? subSystem.getName()
										: "未命名子系统", null, null,
								"prefix + 'images/grade.gif'",
								"prefix + 'images/grade.gif'");// 由于涉及web路径问题，传入JS
						// web路径参数
						// prefix
						tempSubSystemMap.put(module.getSubsystem(),
								subSystemNode);
						if (subSystem != null) {
							orderid = subSystem.getOrderid();
						} else {
							orderid = new Integer(nonameSubSystemOrderid);
							nonameSubSystemOrderid++;
						}
						seqenceSubSystemOderidList.add(orderid);
						tempSeqenceSubSystemNodeMap.put(orderid, subSystemNode);
						// tree.addChildNode(subSystemNode);
						subSystemNode.addChildNode(node);
					}

				}
			}

		}
		Collections.sort(seqenceSubSystemOderidList);
		for (Integer integer : seqenceSubSystemOderidList) {
			WebTreeNode subSystemNode = tempSeqenceSubSystemNodeMap
					.get(integer);
			innerSubSystemRootNode.addChildNode(subSystemNode);
		}
		for (Module module : tempModuleList) {
			if (tempNodeMap.get(module.getParentid()) != null) {
				tempNodeMap.get(module.getParentid()).addChildNode(
						tempNodeMap.get(module.getId()));
			}
		}
		for (Iterator<ModelOperator> iter = operations.iterator(); iter
				.hasNext();) {
			ModelOperator modelOperator = iter.next();
			if (selectedOperSet.contains(modelOperator.getId())) {
				WebTreeNode node = new WebTreeNode(modelOperator
						.getOperatorname(), null, null,
						"prefix + 'images/function.gif'",
						"prefix + 'images/function.gif'");// 由于涉及web路径问题，传入JS
				// web路径参数 prefix
				if (tempNodeMap.get(modelOperator.getModuleid()) != null) {
					tempNodeMap.get(modelOperator.getModuleid()).addChildNode(
							node);
				}
			}
		}

		if (unitId != null && selectExtraSubSystemSet != null
				&& !selectExtraSubSystemSet.isEmpty()) {
			WebTreeNode outerSubSystemRootNode = new WebTreeNode("附属子系统", null,
					null, "prefix + 'images/department.gif'",
					"prefix + 'images/department.gif'"); // 增加全选节点
			tree.addChildNode(outerSubSystemRootNode);
			List<AppRegistry> extraSubSystemList = appRegistryService
					.getAppRegistriesDefaultPerm(unitId);
			for (Iterator<AppRegistry> iter = extraSubSystemList.iterator(); iter
					.hasNext();) {
				AppRegistry appRegistry = iter.next();
				if (selectExtraSubSystemSet.contains(appRegistry.getId())) {
					WebTreeNode extraSubNode = new WebTreeNode(appRegistry
							.getSysname(), null, null,
							"prefix + 'images/grade.gif'",
							"prefix + 'images/grade.gif'");// 由于涉及web路径问题，传入JS
					outerSubSystemRootNode.addChildNode(extraSubNode);
				}
			}
		}
		return tree;
	}

	/**
	 * 
	 * @param unitClass
	 * @param unitType
	 * @param selectedModSet
	 * @param selectedOperSet
	 * @return FastTreeNode
	 */
	public FastTreeNode getUserPermModuleTree2(int unitClass, int unitType,
			Set<Integer> selectedModSet, Set<String> selectedOperSet) {

		FastTreeNode root = new FastTreeNode("A", "权限树");
		FastTreeNode root2 = new FastTreeNode("A1", "内置子系统");// "prefix +
		// 'images/department.gif'"
		root.addChild(root2);

		List<Module> modules = moduleService.getEnabledModules(unitClass,
				unitType);
		List<ModelOperator> operations = modelOperatorService
				.getModuleOperationList();

		List<Module> onlyModules = new ArrayList<Module>();// 去掉目录后的模块列表
		List<Module> dir2Modules = new ArrayList<Module>();// 第二级目录列表
		FastTreeNode node = null;
		for (Module module : modules) {
			if (systemDeployService.isOrderMode()) {
				// 系统管理不过滤
				if (SubSystem.SUBSYSTEM_SYSTEM != module.getSubsystem()) {
					if (!selectedModSet.contains(Integer.valueOf(module.getId().intValue())))
						continue;
				}
			}

			if (selectedModSet.contains(module.getId().intValue())) {
				// 目前这里加上根节点，支持三级目录
				if (module.getType().trim().equalsIgnoreCase("dir")) {
					if (module.getParentid() == -1) {
						FastTreeNode sysNode = root2.getChild("sys"
								+ module.getSubsystem());
						if (sysNode == null) {
							SubSystem subSystem = subSystemService
									.getSubSystem(module.getSubsystem());
							sysNode = new FastTreeNode("sys"
									+ subSystem.getId(), subSystem.getName());
							root2.addChild(sysNode);
						}
						node = new FastTreeNode(String.valueOf(module.getId()),
								module.getName());
						sysNode.addChild(node);
					} else {
						dir2Modules.add(module);
					}
				} else {
					if (module.getParentid() == -1) {
						FastTreeNode sysNode = root2.getChild("sys"
								+ module.getSubsystem());
						if (sysNode == null) {
							SubSystem subSystem = subSystemService
									.getSubSystem(module.getSubsystem());
							sysNode = new FastTreeNode("sys"
									+ subSystem.getId(), subSystem.getName());
							root2.addChild(sysNode);
						}
					}
					onlyModules.add(module);
				}
			} else {
				continue;
			}
		}

		for (Module dir : dir2Modules) {
			node = new FastTreeNode(String.valueOf(dir.getId()), dir.getName());
			// 目前这里加上根节点，支持三级目录
			FastTreeNode tmpNode = root2.getChild(String.valueOf(dir
					.getParentid()));
			if (tmpNode != null) {
				tmpNode.addChild(node);
			} else {
				log
						.debug("模块" + dir.getId() + "--" + dir.getName()
								+ "找不到父节点。");
			}
		}

		for (Module module : onlyModules) {
			String json = "'type':'module'";
			node = new FastTreeNode(String.valueOf(module.getId()), module
					.getName(), json);
			FastTreeNode tmpNode;
			if (module.getParentid() == -1) {
				tmpNode = root.getChild("sys" + module.getSubsystem());
			} else {
				tmpNode = root.getChild(String.valueOf(module.getParentid()));
			}
			if (tmpNode != null) {
				tmpNode.addChild(node);
			} else {
				log.error("模块" + module.getId() + "--" + module.getName()
						+ "找不到父节点");
			}
		}

		for (ModelOperator oper : operations) {
			if (!selectedOperSet.contains(oper.getOperheading()))
				continue;

			String json = "'type':'dept'";
			node = new FastTreeNode("oper" + oper.getId(), oper
					.getOperatorname(), json);
			// 目前这里加上根节点，支持三级目录
			FastTreeNode tmpNode = root2.getChild(String.valueOf(oper
					.getModuleid()));
			if (tmpNode != null) {
				tmpNode.addChild(node);
			} else {
				// log.error("模块的功能点" + oper.getId() + "--" +
				// oper.getOperheading() + "--" + oper.getOperatorname()
				// + "找不到相应的模块");
			}
		}

		return root;
	}

	public void setModelOperatorService(
			ModelOperatorService modelOperatorService) {
		this.modelOperatorService = modelOperatorService;
	}

	public SubSystemService getSubSystemService() {
		return subSystemService;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

}
