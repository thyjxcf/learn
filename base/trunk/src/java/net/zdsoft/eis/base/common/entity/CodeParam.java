package net.zdsoft.eis.base.common.entity;

/**
 * <p>
 * ZDSoft学籍系统(stusys)V3.5
 * </p>
 * <p>
 * 存放生成学号学籍号所需的参数
 * </p>
 * 
 * @author zhongh
 * @since 1.0
 * @version $Id: CodeParam.java,v 1.6 2006/11/03 11:21:33 dongzk Exp $
 */
public class CodeParam {

    private String schid; // 学校ID
    private String schregion; // 学校所在地行政区（BasicSchoolinfo.region）
    private String schcode; // 学校代码（BasicSchoolinfo.code）
    private String currentacadyear; // 当前学年（格式为：2005-2006）
    private String enteracadyear; // 入学学年（格式为：2005-2006）
    private String section; // 所属学段（微代码：DM-RKXD）
    private String stusourcetype; // 生源类别（微代码：DM-XSLY，StudentInfoex.sourcetype）
    private String stuislocalsource; // 是否本地生源
    private String stusex; // 性别（微代码：M男，F女）
    private String specialtyCode;//专业代码
    private String graduateyear; // 毕业学年
    private String runschtype; // 学校性质（办别）
    private String identitycard; // 身份证
    private String classcode;  //班级
    private String classorderid; //班内编码
    private String credentialcode; // 证件号

    public CodeParam() {
    }

    public String getCurrentacadyear() {
        return currentacadyear;
    }

    public void setCurrentacadyear(String currentacadyear) {
        this.currentacadyear = currentacadyear;
    }

    public String getEnteracadyear() {
        return enteracadyear;
    }

    public void setEnteracadyear(String enteracadyear) {
        this.enteracadyear = enteracadyear;
    }

    public String getSchcode() {
        return schcode;
    }

    public void setSchcode(String schcode) {
        this.schcode = schcode;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getSchregion() {
        return schregion;
    }

    public void setSchregion(String schregion) {
        this.schregion = schregion;
    }

    public String getStusourcetype() {
        return stusourcetype;
    }

    public void setStusourcetype(String stusourcetype) {
        this.stusourcetype = stusourcetype;
    }

    public String getGraduateyear() {
        return graduateyear;
    }

    public void setGraduateyear(String graduateyear) {
        this.graduateyear = graduateyear;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStuislocalsource() {
        return stuislocalsource;
    }

    public void setStuislocalsource(String stuislocalsource) {
        this.stuislocalsource = stuislocalsource;
    }

    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    public String getRunschtype() {
        return runschtype;
    }

    public void setRunschtype(String runschtype) {
        this.runschtype = runschtype;
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
    }

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

	public String getClassorderid() {
		return classorderid;
	}

	public void setClassorderid(String classorderid) {
		this.classorderid = classorderid;
	}

	public String getCredentialcode() {
		return credentialcode;
	}

	public void setCredentialcode(String credentialcode) {
		this.credentialcode = credentialcode;
	}

	public String getSpecialtyCode() {
		return specialtyCode;
	}

	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}

}
