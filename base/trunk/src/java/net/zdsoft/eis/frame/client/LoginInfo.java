package net.zdsoft.eis.frame.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginInfo extends BaseLoginInfo {

    private static final long serialVersionUID = 7849872357667900030L;

    private static final Logger log = LoggerFactory.getLogger(LoginInfo.class);

    /** 当前登陆用户帐号对象 */
    private User user;

    /** 客户端的IP */
    private String clientIP;

    /**
     * 所属单位分类
     * 
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_EDU
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_SCHOOL
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_SCHDISTRICT
     * 
     */
    private Integer unitClass;

    /**
     * 所属单位类型
     * 
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_EDU_TOP
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_EDU_SUB
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_ASP
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_COLLEGE
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_KINDERGARTEN
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_EISS
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_EISV
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_NOTEDU_NOTSCH
     */
    private Integer unitType;

    /** 所属单位名称 */
    private String unitName;

    /** 角色列表 */
    private List<Role> roleList;

    /** 当前用户拥有的模块集合,放module.getId() */
    private Set<Integer> allModSet;

    /** 当前用户拥有的操作集合 */
    private Set<String> allOperSet;

    /** 当前用户拥有的子系统集合 */
    private Set<Integer> allSubSystemSet;

    /** 当前用户拥有的额外子系统集合 */
    private Set<String> allExtraSubSystemSet;

    /** 当前用户拥有的真正订购子系统集合map(id,code) */
    private Set<Integer> allActiveSubSytem;

    private Long unitPartitionNum;// 分区号

    /**
     * 学生或家长用户
     * 
     * @param user
     */
    public LoginInfo(User user, Unit unit,Set<Integer> activeSubSytem) {
        if (null == user) {
            throw new IllegalArgumentException("The User should not be NULL");
        }
        if (null == unit) {
            throw new IllegalArgumentException("The Unit should not be NULL");
        }

        this.user = user;
        this.unitClass = unit.getUnitclass();
        this.unitType = unit.getUnittype();
        this.unitName = unit.getName();
        this.unitPartitionNum = unit.getUnitPartitionNum();
        allSubSystemSet = (null == activeSubSytem) ? Collections.<Integer> emptySet() : activeSubSytem;
    }
    
    /**
     * Constructor
     * 
     * @param user
     *            登录用户 the login <code>User</code>, not <tt>NULL</tt>
     * @param unit
     *            登录用户所在的单位 the <code>Unit</code> of the login user, not
     *            <tt>NULL</tt>
     * @param roleList
     *            登录用户的角色列表 the <code>Role</code>s that user have
     * @param commonRoleList
     *            公共权限角色，这个意义还需明确 the <code>Role</code>s in common
     * @param allSubSystem
     *            所有子系统 all the SubSystem
     * @param activeSubSytem
     *            根据单位的id和用户类型取可用的子系统 all the active SubSystem
     */
    public LoginInfo(User user, Unit unit, List<Role> roleList, List<Role> commonRoleList,
            Map<Integer, SubSystem> allSubSystem, Set<Integer> activeSubSytem,
            boolean isHaveLastData,boolean isPreEdu) {
        this(user, unit, null);
        
        Set<String> setOfExAppFromRole = new HashSet<String>();
        this.roleList = (null == roleList) ? Collections.<Role> emptyList() : roleList;

        allModSet = new HashSet<Integer>();
        allOperSet = new HashSet<String>();
        allSubSystemSet = new HashSet<Integer>();
        allExtraSubSystemSet = new HashSet<String>();
        allActiveSubSytem = (null == activeSubSytem) ? Collections.<Integer> emptySet() : activeSubSytem;

        if (user.getOwnerType() == User.TEACHER_EDU_LOGIN || user.getOwnerType() == User.TEACHER_LOGIN) {
            for (Role role : this.roleList) {
                if (role.getModSet() != null){
                    for (Module m : role.getModSet()) {
                    	//过滤  非7.0版本 模块 
                    	if(StringUtils.equals("7", StringUtils.trim(m.getVersion()))){
                    		continue;
                    	}
                        Integer subId = m.getSubsystem();
                        if (activeSubSytem.contains(subId)) {
                            allModSet.add(m.getId().intValue());
                        }
                    }
                }
                if (null != role.getOperSet()) {
                    allOperSet.addAll(role.getOperSet());
                }
                if (role.getSubSystemSet() != null) {
                    allSubSystemSet.addAll(role.getSubSystemSet());
                }
                if (null != role.getExtraSubSystemSet()) {
                    setOfExAppFromRole.addAll(role.getExtraSubSystemSet());
                }
            }
            //子系统【数据中心】中的数据变动模块、上年度数据结转模块互斥，有上年度结转数据则屏蔽上年度数据结转模块，否则屏蔽数据变动模块
            if(isHaveLastData){
    			//allModSet.remove(Integer.valueOf(Module.DATA_CENTER_MODULE_SNDSJJZ));
        	}else{
        		allModSet.remove(Integer.valueOf(Module.DATA_CENTER_MODULE_SJBD));
        	}
            
            if (CollectionUtils.isNotEmpty(commonRoleList)) {
                for (Role role : commonRoleList) {
                    if (role.getModSet() != null){
                        for (Module m : role.getModSet()) {
                        	//非7.0版本过滤
                        	if(StringUtils.equals("7", StringUtils.trim(m.getVersion()))){
                        		continue;
                        	}
                            Integer subId = m.getSubsystem();
                            if (activeSubSytem.contains(subId)) {
                                allModSet.add(m.getId().intValue());
                            }
                        }
                    }
                    allOperSet.addAll(role.getOperSet());
                    allSubSystemSet.addAll(role.getSubSystemSet());
                    setOfExAppFromRole.addAll(role.getExtraSubSystemSet());
                }

                if (log.isDebugEnabled()) {
                    for (Iterator<Integer> subsysIteror =   allSubSystemSet.iterator();subsysIteror.hasNext();) {
                        log.debug("[系统显示]从角色中取得的内部子系统:" + subsysIteror.next());
                    }
                    for (String s : setOfExAppFromRole) {
                        log.debug("[系统显示]从角色中取得的附属子系统：" + s);
                    }
                }
            }
        }

        if (CollectionUtils.isNotEmpty(allSubSystemSet)) {
            allSubSystem = (null == allSubSystem) ? Collections.<Integer, SubSystem> emptyMap() : allSubSystem;
            activeSubSytem = (null == activeSubSytem) ? Collections.<Integer> emptySet() : activeSubSytem;

            // 如果是管理员，默认就有系统管理权限
            if (user.getType().intValue() == User.USER_TYPE_TOPADMIN
                    || user.getType().intValue() == User.USER_TYPE_SUBADMIN) {
                activeSubSytem.add(SubSystem.SUBSYSTEM_SYSTEM);
            }

            for (Iterator<Integer> sysids = allSubSystemSet.iterator();sysids.hasNext();) {
                Integer sysid = sysids.next();
                // 系统管理不过滤
                if (SubSystem.SUBSYSTEM_SYSTEM == sysid)
                    continue;

                // 根据启用模块所属子系统进行过滤
                if (!activeSubSytem.contains(sysid)) {
                    log.debug("[系统显示]根据启动模块过滤内部子系统，去掉：" + sysid);
                    sysids.remove();
                }
            }
        }
        allExtraSubSystemSet.addAll(setOfExAppFromRole);
    }

    /**
     * 验证用户是否有指定模块的权限(所有角色)
     * 
     * @param id
     *            指定模块
     * @return <tt>true</tt>:有, <tt>false</tt>没有.
     */
    public boolean validateAllModel(int id) {
        return allModSet.contains(id);
    }

    /**
     * 验证用户是否有指定模块以及指定操作点(功能点)的权限(所有角色)
     * 
     * @param modId
     *            指定模块
     * @param operaName
     *            指定操作点(功能点)
     * @return <tt>true</tt>:有, <tt>false</tt>没有.
     */
    public boolean validateAllModelOpera(int id, String operaName) {
        if (StringUtils.isEmpty(operaName)) {
            return false;
        }
        return validateAllModel(id) && allOperSet.contains(operaName);
    }

    /**
     * 获取登录用户的单位<tt>id</tt>
     * 
     * @return 登录用户的单位<tt>id</tt>
     */
    public String getUnitID() {
        if (user.getOwnerType() == null) {
            user.setOwnerType(User.TEACHER_LOGIN);
        }

        return user.getUnitid();
    }

    /**
     * 取得用户对象
     * 
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * 所属单位类型
     * 
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_EDU_TOP
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_EDU_SUB
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_ASP
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_COLLEGE
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_KINDERGARTEN
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_EISS
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_SCHOOL_EISV
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_NOTEDU_NOTSCH
     * @return
     */
    public Integer getUnitType() {
        return unitType;
    }

    /**
     * 所属单位分类
     * 
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_EDU
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_SCHOOL
     * @see net.zdsoft.eis.base.common.entity.Unit#UNIT_CLASS_SCHDISTRICT
     * @return
     */
    public Integer getUnitClass() {
        return unitClass;
    }

    /**
     * 所属单位名称
     * 
     * @return
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * 取得用户所具有的角色列表
     * 
     * @return
     */

    public List<Role> getRoleList() {
        return roleList;
    }

    /**
     * 取得客户端的ip
     * 
     * @return
     */
    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Set<Integer> getAllSubSystemSet() {
        return allSubSystemSet;
    }

    public Set<String> getAllExtraSubSystemSet() {
        return allExtraSubSystemSet;
    }

    /**
     * 返回当前用户拥有的模块集合，存放module.getId()的值。
     * 
     * @return
     */
    public Set<Integer> getAllModSet() {
        return this.allModSet;
    }

    /**
     * 当前用户拥有的操作集合
     * 
     * @return
     */
    public Set<String> getAllOperSet() {
        return allOperSet;
    }

    /**
     * 分区表的分区号
     * 
     * @return
     */
    public Long getUnitPartitionNum() {
        return unitPartitionNum;
    }

    /**
     * 当前用户拥有的真正订购子系统集合map(id,code)
     * 
     * @return
     */
    public Set<Integer> getAllActiveSubSytem() {
        return allActiveSubSytem;
    }
}
