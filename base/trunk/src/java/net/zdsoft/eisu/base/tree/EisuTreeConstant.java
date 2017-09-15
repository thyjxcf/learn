/* 
 * @(#)EisuTreeConstant.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree;

import java.util.HashMap;
import java.util.Map;

import net.zdsoft.eis.base.tree.TreeConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 11:24:40 AM $
 */
public final class EisuTreeConstant extends TreeConstant {

    /**
     * 树节点类型：专业
     */
    public static final int ITEMTYPE_SPECIALTY = 32;
    
    /**
     * 树节点类型：专业方向
     */
    public static final int ITEMTYPE_SPECIALTY_POINT = 36;

    /**
     * 树节点类型：教学区
     */
    public static final int ITEMTYPE_TEACH_AREA = 33;

    /**
     * 树节点类型：教学场地
     */
    public static final int ITEMTYPE_TEACH_PLACE = 34;

    /**
     * 树节点类型：教学场地资源
     */
    public static final int ITEMTYPE_TEACH_PLACE_RES = 35;
    
    /**
     * 树节点类型：专业类别
     */
    public static final int ITEMTYPE_SPECIALTY_TYPE = 37;
    /**
     * 树节点类型：专业类别
     */
    public static final int ITEMTYPE_SPECIALTY_CATALOG = 38;

    public static int getTreeType(int itemType) {
        int depth = getTreeDepth(itemType);
        if (String.valueOf(depth).length() == 2) {
            int type = Integer.parseInt(String.valueOf(depth).substring(0, 1));
            return type;
        } else {
            return TREE_TYPE_COMMON;
        }
    }

    /**
     * 树层次关系，key=树节点类型,value=层次关系<br>
     * 学生树：教育局 -> 学区 -> 学校 -> 分校 -> 院系 -> 专业 -> 专业方向 -> 年级 -> 同类班级 -> 班级 -> 学生<br>
     * 教师树：教育局 -> 学区 -> 学校 -> 分校 -> 院系 -> 部门 -> 职工（用户）
     */
    private static final Map<Integer, Integer> treeDepthMap;
    static {
        treeDepthMap = new HashMap<Integer, Integer>();

        // 共用：1-9
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_EDUCATION), Integer.valueOf(1));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_XQ), Integer.valueOf(2));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SCHOOL), Integer.valueOf(3));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SUBSCHOOL), Integer.valueOf(4));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_INSTITUTE), Integer.valueOf(5));

        // 与学生相关：11-19
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SPECIALTY), Integer.valueOf(11));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SPECIALTY_POINT), Integer.valueOf(12));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_GRADE), Integer.valueOf(13));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_KINCLASS), Integer.valueOf(14));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_CLASS), Integer.valueOf(15));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_STUDENT), Integer.valueOf(16));

        // 与教师相关：21-29
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_DEPARTMENT), Integer.valueOf(21));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TR_GROUP), Integer.valueOf(22));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TEACHER), Integer.valueOf(23));

        // 与用户相关：31-39
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_USER), Integer.valueOf(31));

        // 与教学场地资源相关：41-49
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TEACH_AREA), Integer.valueOf(41));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TEACH_PLACE), Integer.valueOf(42));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TEACH_PLACE_RES), Integer.valueOf(43));
        
        //与专业目录相关 51-59
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SPECIALTY_TYPE), Integer.valueOf(51));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_SPECIALTY_CATALOG), Integer.valueOf(52));
        
    }

    /**
     * 取树的深度
     * 
     * @param itemType 树节点类型
     * @return
     */
    public static int getTreeDepth(int itemType) {
        return treeDepthMap.get(Integer.valueOf(itemType)).intValue();
    }

}
