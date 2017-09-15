package net.zdsoft.eis.base.tree;

import java.util.HashMap;
import java.util.Map;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author lilj
 * @since 1.0
 * @version $Id: TreeConstant.java,v 1.5 2007/03/06 12:33:09 zhaosf Exp $
 */

public class TreeConstant {

    public static final String TREE_ICON_PATH_PREFIX = "/static/common/xtree/images/";
    
    public static final String ZTREE_ICON_PATH_PREFIX = "/static/css/zTreeStyle/img/diy/";

    // ============30-50的ITEMTYPE供eisu使用==========
    /**
     * 树节点类型：学校节点
     */
    public static final int ITEMTYPE_SCHOOL = 1;

    /**
     * 树节点类型：分校区节点
     */
    public static final int ITEMTYPE_SUBSCHOOL = 2;

    /**
     * 树节点类型：年级节点
     */
    public static final int ITEMTYPE_GRADE = 3;

    /**
     * 树节点类型：班级节点
     */
    public static final int ITEMTYPE_CLASS = 4;

    /**
     * 树节点类型：学生节点
     */
    public static final int ITEMTYPE_STUDENT = 5;

    /**
     * 树节点类型：教师节点
     */
    public static final int ITEMTYPE_TEACHER = 6;

    /**
     * 树节点类型：同类班级节点
     */
    public static final int ITEMTYPE_KINCLASS = 7;

    /**
     * 树节点类型：教育局单位节点
     */
    public static final int ITEMTYPE_EDUCATION = 11;

    /**
     * 树节点类型：部门节点
     */
    public static final int ITEMTYPE_DEPARTMENT = 12;
    
    /**
     * 树节点类型：教研组节点
     */
    public static final int ITEMTYPE_TR_GROUP = 13;

    /**
     * 树节点类型：职工节点
     */
    public static final int ITEMTYPE_EMPLOYEE = 14;

    /**
     * 树节点类型：用户节点
     */
    public static final int ITEMTYPE_USER = 15;

    /**
     * 树节点类型：学区节点
     */
    public static final int ITEMTYPE_XQ = 16;

    /**
     * 树类型：共用
     */
    public static final int TREE_TYPE_COMMON = 0;

    /**
     * 树类型：学生
     */
    public static final int TREE_TYPE_STUDENT = 1;

    /**
     * 树类型：教师
     */
    public static final int TREE_TYPE_TEACHER = 2;
    /**
     * 树类型：用户
     */
    public static final int TREE_TYPE_TEACHER_USER = 3;

    //----------------院系放在这里，是由于部门、教职工共用----------------------
    /**
     * 树节点类型：院系
     */
    public static final int ITEMTYPE_INSTITUTE = 31;
    
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
     * 学生树：教育局 -> 学区 -> 学校 -> 分校 -> 年级 -> 同类班级 -> 班级 -> 学生<br>
     * 教师树：教育局 -> 学区 -> 学校 -> 分校 -> 部门 -> 职工（用户）
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
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_GRADE), Integer.valueOf(12));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_KINCLASS), Integer.valueOf(13));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_CLASS), Integer.valueOf(14));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_STUDENT), Integer.valueOf(15));

        // 与教师相关：21-29
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_DEPARTMENT), Integer.valueOf(21));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TR_GROUP), Integer.valueOf(22));
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_TEACHER), Integer.valueOf(23));

        // 与教师用户相关：31-39
        treeDepthMap.put(Integer.valueOf(ITEMTYPE_USER), Integer.valueOf(31));
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

    /**
     * 树节点点击触发的方法名
     */
    public static final String TREE_CLICK_METHOD = "javascript:treeItemClick";

    /**
     * 学生树类别：学生列表取正规的学生
     */
    public static final int TREETYPE_STU_NORMAL = 1;

    /**
     * 学生树类别：学生列表取异动业务操作需要使用的学生
     */
    public static final int TREETYPE_STU_FOR_ABNORMAL = 2;

    /**
     * 学生树类别：学生列表取当前学年下所有的学生(包括：在校、离校、已毕业)
     */
    public static final int TREETYPE_STU_FOR_ALL = 3;

    /**
     * 学生树类别：学生列表取当前教学班的学生
     */
    public static final int TREETYPE_STU_FOR_TEACH_CLASS = 4;

    /**
     * 学生树类别：学生列表取毕业学生
     */
    public static final int TREETYPE_STU_GRADUATE = 9;

    /**
     * 学生树类别：学生列表取将要毕业的学生
     */
    public static final int TREETYPE_STU_GRADUATING = 8;

    /**
     * 班级树类别：未毕业的班级列表（跟学年无关，正常的班级列表）
     */
    public static final int TREETYPE_CLASS_NORMAL = 1;

    /**
     * 班级树类别：毕业班列表，（跟学年有关，即指定学年内的毕业班列表，不论毕业标志是0还是1）
     */
    public static final int TREETYPE_CLASS_GRADUATING = 2;

    /**
     * 班级类型
     */
    public static final int CLASS_STATE_NORMAL = 1;// 正常
    public static final int CLASS_STATE_GRADUATING = 2;// 将要毕业
    public static final int CLASS_STATE_GRADUATED = 3;// 已毕业

    /**
     * 学生树
     * 
     * @param treeType
     * @return
     */
    public static int getClassState(int treeType) {
        int state = CLASS_STATE_NORMAL;

        switch (treeType) {
        case TREETYPE_STU_GRADUATING:
            state = CLASS_STATE_GRADUATING;
            break;

        case TREETYPE_STU_GRADUATE:
            state = CLASS_STATE_GRADUATED;
            break;

        default:
            state = CLASS_STATE_NORMAL;
            break;
        }

        return state;
    }

    /**
     * 班级树
     * 
     * @param treeType
     * @param graduateSign
     * @return
     */
    public static int getClassState(int treeType, String graduateSign) {
        int state = CLASS_STATE_NORMAL;
        switch (treeType) {
        case TREETYPE_CLASS_GRADUATING:
            if ("1".equals(graduateSign)) {
                state = CLASS_STATE_GRADUATED;
            } else {
                state = CLASS_STATE_GRADUATING;
            }
            break;
        default:
            state = CLASS_STATE_NORMAL;
            break;
        }
        return state;
    }
    
    public static final String TREE_USER = "2";// 人员
    public static final String TREE_GROUP = "3";// 用户组
    public static final String TREE_DEPT = "4";// 部门
    public static final String TREE_UNIT = "5";// 单位

}
