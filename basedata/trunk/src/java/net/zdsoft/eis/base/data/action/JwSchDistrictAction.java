
package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.service.BaseSchoolDistrictService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

import com.opensymphony.xwork2.ModelDriven;

/**
 * <p>项目：学籍二期(stusys)
 * 
 * 学区基本信息--控制流转类
 * 
 * @author Kobe Su,2007-5-17
 */
public class JwSchDistrictAction extends BaseAction implements ModelDriven<SchoolDistrict> {

	private static final long serialVersionUID = 1L;
	
	private SchoolDistrict schoolDistrict = new SchoolDistrict();
	private BaseSchoolDistrictService baseSchoolDistrictService;
	private StudentService studentService;
	private UnitService unitService;
	
	private List<SchoolDistrict> schDistrictList;   //学区信息列表
	
	public SchoolDistrict getModel() {
		return schoolDistrict;
	}

	/**
	 * 跳转
	 */
	public String forward() throws Exception {
		//设置根教育局id
		schoolDistrict.setEduid(getLoginInfo().getUnitID());

		return SUCCESS;
	}	
	
	/**
	 * 进入学区信息主管理界面
	 */
	public String admin() throws Exception {
	
		schDistrictList = baseSchoolDistrictService.getSchoolDistricts(schoolDistrict.getEduid());
        String eduName = unitService.getUnit(schoolDistrict.getEduid()).getName();
        for (SchoolDistrict dto : schDistrictList) {
            dto.setEduName(eduName);
        }
		
		return SUCCESS;
	}
	
	/**
	 * 新增
	 */
	public String add() throws Exception {
		
		String code = baseSchoolDistrictService.getAutoIncreaseCode(schoolDistrict.getEduid());
		schoolDistrict.setCode(code);
		
		return SUCCESS;
	}	
	
	/**
	 * 编辑
	 */
	public String edit() throws Exception {
		schoolDistrict = baseSchoolDistrictService.getSchoolDistrict(schoolDistrict.getId());
		return SUCCESS;
	}		
	
	/**
	 * 保存
	 */
	public String save() throws Exception {
		
		//校验学区名称是否唯一
		boolean isExist = baseSchoolDistrictService.isExistsName(schoolDistrict.getEduid(), schoolDistrict.getName(), schoolDistrict.getId());
		if(isExist) {
			
			addFieldError("name", "已经存在相同学区名称，请重新填写！");
//			setPromptMessageDto(new PromptMessageDto());
//			promptMessageDto.setPromptMessage("已经存在相同学区名称，请重新填写！");
//			promptMessageDto.setOperateSuccess(false);
//			String url = (dto.getId()== null || dto.getId().equals(""))
//				? ("add.action?eduid="+dto.getEduid()) : ("edit.action?id=" + dto.getId());
//			promptMessageDto.addOperation(new String[]{"返回",url});				
//			return PROMPTMSG;
		}
		
		if(hasFieldErrors() || hasErrors()) return INPUT;
		if(null!=schoolDistrict.getId()&&!"".equals(schoolDistrict.getId())){
		    baseSchoolDistrictService.updateSchoolDistrict(schoolDistrict);
		}else{
		    baseSchoolDistrictService.insertSchoolDistrict(schoolDistrict);
		}
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setPromptMessage("保存成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[]{"返回","schDistrict-Admin.action?eduid="+schoolDistrict.getEduid()});	
		
		if(schoolDistrict.getId() == null || schoolDistrict.getId().equals(""))  //新增
			promptMessageDto.addOperation(new String[]{"继续新增","schDistrict-add.action?eduid="+schoolDistrict.getEduid()});	
		
		return PROMPTMSG;
	}	
	
	/**
	 * 删除
	 */
	public String remove() throws Exception {
		
		List<SchoolDistrict> list = baseSchoolDistrictService.getSchoolDistricts(schoolDistrict.getCheckid());
		boolean flag = false;
		String name = "";
		outer:
		for(int i=0;i<list.size();i++) {
			SchoolDistrict distri = list.get(i);
			flag = baseSchoolDistrictService.isExistSchoolDistrict(distri.getId());
			if(flag) {
				name = distri.getName();
				break outer;
			}
			flag = studentService.isExistsStuByDistrict(distri.getId());
			if(flag) {
				name = distri.getName();
				break outer;
			}
		}
		
		if(flag) {  //这里首先校验该学区是否已被学校引用
			setPromptMessageDto(new PromptMessageDto());
			promptMessageDto.setPromptMessage("学区：\"" + name +"\"已被有关学校或学生引用，不能删除！");
			promptMessageDto.setOperateSuccess(false);			
		}else {
		    baseSchoolDistrictService.deleteSchoolDistrict(schoolDistrict.getCheckid());
			setPromptMessageDto(new PromptMessageDto());
			promptMessageDto.setPromptMessage("删除成功！");
			promptMessageDto.setOperateSuccess(true);
			
		}
		String url = "schDistrict-Admin.action?eduid="+schoolDistrict.getEduid();
		promptMessageDto.addOperation(new String[]{"返回",url});			
		return PROMPTMSG;
	}	
	
	//以下为相关参数的set,get方法

    public void setBaseSchoolDistrictService(BaseSchoolDistrictService baseSchoolDistrictService) {
        this.baseSchoolDistrictService = baseSchoolDistrictService;
    }

    public List<SchoolDistrict> getSchDistrictList() {
		return schDistrictList;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public SchoolDistrict getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(SchoolDistrict schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }



}
