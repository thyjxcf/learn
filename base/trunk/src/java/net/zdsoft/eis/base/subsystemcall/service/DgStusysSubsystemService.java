package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.subsystemcall.entity.DgBaseStudent;
import net.zdsoft.eis.base.subsystemcall.entity.StudentInformalFamilyTemp;
import net.zdsoft.eis.base.subsystemcall.entity.StudentInformalTemp;
import net.zdsoft.keelcnet.action.UploadFile;

public interface DgStusysSubsystemService {
	//根据家长id找学生临时表数据 返回stuId
	public String getStuInfoByFamily(String ownerId);
	//根据学生id找临时表中家长 key家长id
	public Map<String,String> getStuFamilyByStuId(String stuId);
	//根据学生id找资料上传 附件
	public List<Attachment> getStuInfoAttachments(String stuId);
	//根据学生id找学生临时表审核状态
	public String getStuAuditStatus(String stuId);
	//学生资料上传
	public void saveStuFiles(List<UploadFile> files, String linId);
	
	
	public JSONObject getInfo(String stuId, String type) throws Exception;
	
	public JSONObject findFamilyInfoMsg(String familyId) throws Exception;
	
	public JSONObject saveStudentInfo(JSONObject jsonPar) throws Exception;
	
	public JSONObject deleteAtt(String attId) throws Exception;
	
	/**
	 * 获取学生家长信息
	 * @param stuId
	 * @return
	 */
	public List<StudentInformalFamilyTemp> getStudentInformalFamilyTempListByStuId(String stuId);
	
	/**
	 * 根据id获取STUDENT_INFORMAL_FAMILY_TEMP
	 * @param id
	 * @return
	 */
	public StudentInformalFamilyTemp getStudentInformalFamilyTempById(String id);
	
	/**
	 * 根据家长id找学生
	 * @param familyId
	 * @return
	 */
	public StudentInformalTemp getStudentInformalTempByFamilyId(String familyId);
	
	/**
	 * 根据id获取STUDENT_INFORMAL_TEMP
	 * @param id
	 * @return
	 */
	public StudentInformalTemp getStudentInformalTempById(String id);
	
	/**
	 * 获取正式表学生数据
	 */
	public DgBaseStudent getStudentById(String id);
}
