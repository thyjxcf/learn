/**
 * 
 */
package net.zdsoft.eis.frame.client;

import java.io.Serializable;

import net.zdsoft.eis.base.common.entity.Student;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: loginStuInfo.java, v 1.0 2007-11-9 下午03:03:03 zhaosf Exp $
 */

public class StuLoginInfo implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8339121162650418019L;

    private Student studentInfo;
    private String unitName;// 所属单位名称
    private Long unitPartitionNum;// 分区号

    /**
     * 站点类型 对应loginType
     */
    public static final int SITE_SIGN_MAIN = 0;

    public StuLoginInfo(Student studentInfo, String unitName, Long unitPartitionNum) {
        this.studentInfo = studentInfo;
        this.unitName = unitName;
        this.unitPartitionNum = unitPartitionNum;
    }

    public Student getStudentInfo() {
        return studentInfo;
    }

    public String getUnitName() {
        return unitName;
    }

    public Long getUnitPartitionNum() {
        return unitPartitionNum;
    }

}
