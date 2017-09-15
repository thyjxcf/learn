package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;


public class Module extends BasicModule {
    private static final long serialVersionUID = 6675740451692599610L;

    private Integer width;
    private Integer height;
    private String pbcommon;
    private String version;
    private String filelist;
    private String reldir;
    private String mainfile;
    private String parm;
    private String modelId;

    // =================辅助字段===================
    private String subSysName; // 所属子系统名称
    private String parentModName; // 所属父级模块名称
    private List<Module> subModules = new ArrayList<Module>();
	public static final String DATA_CENTER_MODULE_SNDSJJZ = "9672";//上年度数据结转模块
	public static final String DATA_CENTER_MODULE_SJBD = "9607";//数据变动模块
	public static final String DATA_CENTER_MODULE_JSWH = "9641";//教师维护模块
	public static final String DATA_CENTER_MODULE_XSWH = "9653";//学生维护模块
	public static final String DATA_CENTER_MODULE_NJWH = "9651";//年级维护模块
	public static final String DATA_CENTER_MODULE_BJWH = "9652";//班级维护模块
	public static final String DATA_CENTER_MODULE_ZYWH = "9673";//专业维护模块
	public static final int SCHSECURITY_MODULE_FZCSWSB = 97002;//非正常死亡上报 教育局端
	public static final int SCHSECURITY_MODULE_SCHFZCSWSB = 97102;//非正常死亡上报 学校端
	public static final int SCHSECURITY_MODULE_CSXXSB = 97003;//猝死信息上报
	public static final int SCHSECURITY_MODULE_SGSB = 97004;//事故上报
	
    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFilelist() {
        return filelist;
    }

    public void setFilelist(String filelist) {
        this.filelist = filelist;
    }

    public String getMainfile() {
        return mainfile;
    }

    public void setMainfile(String mainfile) {
        this.mainfile = mainfile;
    }

    public String getParm() {
        return parm;
    }

    public void setParm(String parm) {
        this.parm = parm;
    }

    public String getPbcommon() {
        return pbcommon;
    }

    public void setPbcommon(String pbcommon) {
        this.pbcommon = pbcommon;
    }

    public String getReldir() {
        return reldir;
    }

    public void setReldir(String reldir) {
        this.reldir = reldir;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getSubSysName() {
        return subSysName;
    }

    public void setSubSysName(String subSysName) {
        this.subSysName = subSysName;
    }

    public String getParentModName() {
        return parentModName;
    }

    public void setParentModName(String parentModName) {
        this.parentModName = parentModName;
    }

    public List<Module> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<Module> subModules) {
        this.subModules = subModules;
    }

    
}
