package net.zdsoft.basedata.remote.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.SecurityUtils;
import net.zdsoft.keel.util.ServletUtils;

import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;

@SuppressWarnings("serial")
public class RemoteUserAction extends BaseAction {

    private String param;
    private BaseUserService baseUserService;
    private BaseTeacherService baseTeacherService;
    private StudentFamilyService studentFamilyService;
    private StudentService studentService;

    /**
     * 解析传递过来的参数
     * 
     * @param param
     * @return
     */
    private JSONObject synParam(String param) {
        String md5Twice = StringUtils.left(param, 32);
        String encode = StringUtils.substring(param, 32);
        BASE64Decoder decode = new BASE64Decoder();
        JSONObject json = new JSONObject();
        try {
            byte[] bs = decode.decodeBuffer(encode);
            Inflater decompressor = new Inflater();
            decompressor.setInput(bs);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(bs.length);
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
            bos.close();
            byte[] decompressedData = bos.toByteArray();
            String decodeResult = new String(decompressedData);
            json = JSONObject.fromObject(decodeResult);
            if (!StringUtils.equals(md5Twice, SecurityUtils.encodeByMD5(SecurityUtils.encodeByMD5(json.toString())))) {
                json.put("code", "01");
                json.put("msg", "MD5信息完整性验证失败");
            }
        }
        catch (Exception e) {
            json.put("code", "01");
            json.put("msg", StringUtils.isBlank(e.getMessage()) ? "其他错误" : e.getMessage());
        }
        return json;
    }

    /**
     * 修改密码
     * 
     * @return
     */
    public String changeUser() {
        String[] changeAttribute = {"phone", "password", "email", "realName", "deptId"};
        String[] refAttribute = {"MobilePhone,Mobile", "Password", "Email", "Realname", "Deptid"};
        String[] refTeaAttribute = {"PersonTel", null, "Email", "Name", "Deptid"};
        String[] refStuAttribute = {"MobilePhone", null, "Email", "Stuname", null};
        String[] refFamAttribute = {"MobilePhone", null, "Email", "Name", null};
        JSONObject json = new JSONObject();
        try {
            json = synParam(param);
            String code = json.containsKey("code") ? json.getString("code") : "00";
            if ("00".equals(code)) {
                String accountId = json.containsKey("accountId") ? json.getString("accountId") : "";
                User user = baseUserService.getUserByAccountId(accountId);
                if (user == null) {
                    json.put("code", "01");
                    json.put("msg", "系统中无此用户!");
                } else {
                	BaseTeacher tea = null;
                    Student stu = null;
                    Family fam = null;
                    if (user.getOwnerType() == User.TEACHER_LOGIN) {
    					tea = baseTeacherService.getBaseTeacher(user.getTeacherid());
    				} else if(user.getOwnerType() == User.STUDENT_LOGIN){
    					stu = studentService.getStudent(user.getTeacherid());
    				} else if(user.getOwnerType() == User.FAMILY_LOGIN){
    					fam = studentFamilyService.getFamily(user.getTeacherid());
    				}
                    
                    int i = 0;
                    for(String attri : changeAttribute) {
                        if (json.containsKey(attri)) {
                            String newAttri = json.getString(attri);
                            String refAttrio = refAttribute[i];
                            String[] refAttris = refAttrio.split(",");
                            for(String refAttri : refAttris) {
                                MethodUtils.invokeMethod(user, "set" + refAttri , new Object[] {newAttri});
                            }
                            
                            if (user.getOwnerType() == User.TEACHER_LOGIN && tea != null) {
                            	String refTea = refTeaAttribute[i];
                                if(StringUtils.isNotBlank(refTea)){
                                	MethodUtils.invokeMethod(tea, "set" + refTea , new Object[] {newAttri});
                                }
            				} else if(user.getOwnerType() == User.STUDENT_LOGIN && stu != null){
            					String refStu = refStuAttribute[i];
                                if(StringUtils.isNotBlank(refStu)){
                                	MethodUtils.invokeMethod(stu, "set" + refStu , new Object[] {newAttri});
                                }
            				} else if(user.getOwnerType() == User.FAMILY_LOGIN && fam != null){
            					String reffam = refFamAttribute[i];
                                if(StringUtils.isNotBlank(reffam)){
                                	MethodUtils.invokeMethod(fam, "set" + reffam , new Object[] {newAttri});
                                }
            				}
                            
                        }
                        i ++;
                    }
                    baseUserService.updateUserWithTea(user, tea, stu, fam, json.containsKey("phone"));
                    json.put("code", "00");
                    json.put("msg", "修改成功");
                }
            }
        }
        catch (Exception e) {
        	log.error(e.getMessage(), e);
            json.put("code", "01");
            json.put("msg", e.getMessage());
        }

        try {
            ServletUtils.print(getResponse(), json.toString());
        }
        catch (IOException e) {
        }
        return NONE;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setBaseUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

}
