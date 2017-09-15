package net.zdsoft.eis.system.desktop.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.util.RedisUtils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 图表相关
 * @author
 *
 */
public class EchartsChartsAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private UnitService unitService;
	private McodedetailService mcodedetailService;
	
	private String jsonStringCharts[][];
	private StudentService studentService;
	private GradeService gradeService;
	private BasicClassService basicClassService;
	private TeacherService teacherService;
	
	/**
	 * 学生年级分布统计图
	 * 柱状图
	 * @return
	 */
	public String studentGradeHistogram() {
		jsonStringCharts=new String[1][1];
		String unitId = this.getLoginInfo().getUnitID();
		String jsonStr = RedisUtils.get("schstugrade_"+unitId);
		jsonStr = null;
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			JSONObject jsonObject=new JSONObject();
			String[] xAxisDatas=null;
			Integer[][] loadingDataInt=null;
			String[] legendDatas=null;
			legendDatas=new String[]{"人数"};
			List<Grade> gradelist = gradeService.getGrades(unitId);
			xAxisDatas = new String[gradelist.size()];
			Map<Integer,String> gradeWZmap = new HashMap<Integer, String>();
			int i=0;
			for(Grade g : gradelist){
				xAxisDatas[i] = g.getGradename();
				gradeWZmap.put(i, g.getId());
				i++;
			}
			loadingDataInt=new Integer[1][xAxisDatas.length];
			int con = 0;
			Map<String,BasicClass> clsmap = basicClassService.getClassMap(unitId);
			Map<String,Integer> countMap = new HashMap<String, Integer>();
			List<Student> stulist = studentService.getStudentsByFaintnessStudentCode(unitId, null, null);
			String gradeId  = null;
			for(Student stu : stulist){
				if(BaseConstant.LEAVESIGN_OUT==stu.getLeavesign()){
					//去掉离校的
					continue;
				}
				if(!clsmap.containsKey(stu.getClassid())){
					//没有班级的学生不算
					continue;
				}
//				con++;
				gradeId = clsmap.get(stu.getClassid()).getGradeId();
				if(countMap.containsKey(gradeId)){
					countMap.put(gradeId,countMap.get(gradeId)+1);
				}else{
					countMap.put(gradeId,1);
				}
			}
			for(int j=0;j<xAxisDatas.length;j++){
				gradeId = gradeWZmap.get(j);
				loadingDataInt[0][j]=countMap.containsKey(gradeId)?countMap.get(gradeId):0;
				con = con + loadingDataInt[0][j];
			}
//			jsonObject.put("text", "学生年级分布统计图");//主标题	可不填写
			jsonObject.put("subtext", "共"+con+"人");//副标题	可不填写
			jsonObject.put("xAxisData", xAxisDatas);
			jsonObject.put("legendData", legendDatas);
			//loadingData数据顺序和xAxisData相对
			jsonObject.put("loadingData", loadingDataInt);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("schstugrade_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
		}
		return SUCCESS;
	}
	
	/**
	 * 学生综合统计
	 * 包括学生户口类别分布图、学生类别分布图、学生性别分布图
	 * @return
	 */
	public String studentMultiStatisticPie() {
		//学生综合统计--饼图
		String unitId = getUnitId();
		jsonStringCharts=new String[3][1];
		String jsonStr1 = RedisUtils.get("studentmultistatisticpie1_"+unitId);
		String jsonStr2 = RedisUtils.get("studentmultistatisticpie2_"+unitId);
		String jsonStr3 = RedisUtils.get("studentmultistatisticpie3_"+unitId);
		if(StringUtils.isBlank(jsonStr1) || StringUtils.isBlank(jsonStr2) || StringUtils.isBlank(jsonStr3)){
			List<Mcodedetail> mlist1 = mcodedetailService.getAllMcodeDetails("DM-HKXZ");
			List<Mcodedetail> mlist2 = mcodedetailService.getAllMcodeDetails("DM-XSLB");
			List<Mcodedetail> mlist3 = mcodedetailService.getAllMcodeDetails("DM-XB");
			Map<String,String> mStrmap = new HashMap<String, String>();
			String[] xAxisDatas1=new String[mlist1.size()+1];
			String[] xAxisDatas2=new String[mlist2.size()+1];
			String[] xAxisDatas3=new String[mlist3.size()];
			JSONArray jsonArr22=new JSONArray();
			JSONObject json22=new JSONObject();
			int i =0;
			for(Mcodedetail m : mlist1){
				xAxisDatas1[i] = m.getContent();
				mStrmap.put("DM-HKXZ"+m.getThisId(), m.getContent());
				i++;
			}
			mStrmap.put("DM-HKXZ0", "未维护");
			xAxisDatas1[xAxisDatas1.length-1] = "未维护";
			i = 0;
			for(Mcodedetail m : mlist2){
				xAxisDatas2[i] = m.getContent();
				mStrmap.put("DM-XSLB"+m.getThisId(), m.getContent());
				i++;
			}
			mStrmap.put("DM-XSLB"+null, "未维护");
			xAxisDatas2[xAxisDatas2.length-1] = "未维护";
			i = 0;
			for(Mcodedetail m : mlist3){
				xAxisDatas3[i] = m.getContent();
				mStrmap.put("DM-XB"+m.getThisId(), m.getContent());
				i++;
			}
			int con1 = 0;
			int con2 = 0;
			int con3 = 0;
			JSONObject jsonObject=new JSONObject();
			Integer[][] loadingDataInt =null;//2C91808B4FF0F55601501539A17104A5
			List<Student> stulist = studentService.getStudentsByFaintnessStudentCode(unitId, null, null);
			Map<String,Integer> xNummap1 = new HashMap<String, Integer>();
			Map<String,Integer> xNummap2 = new HashMap<String, Integer>();
			Map<String,Integer> xNummap3 = new HashMap<String, Integer>();
			for(Student stu : stulist){
				if(BaseConstant.LEAVESIGN_OUT==stu.getLeavesign()){
					//去掉离校的
					continue;
				}
				if(mStrmap.containsKey("DM-HKXZ"+stu.getRegisterType())){
					String content = mStrmap.get("DM-HKXZ"+stu.getRegisterType());
					if(xNummap1.containsKey(content)){
						xNummap1.put(content, xNummap1.get(content)+1);
					}else{
						xNummap1.put(content, 1);
					}
					con1++;
				}
				if(StringUtils.isBlank(stu.getStudentType())){
					if(xNummap2.containsKey("未维护")){
						xNummap2.put("未维护", xNummap2.get("未维护")+1);
					}else{
						xNummap2.put("未维护", 1);
					}
					con2++;
				}else{
					if(mStrmap.containsKey("DM-XSLB"+stu.getStudentType())){
						String content = mStrmap.get("DM-XSLB"+stu.getStudentType());
						if(xNummap2.containsKey(content)){
							xNummap2.put(content, xNummap2.get(content)+1);
						}else{
							xNummap2.put(content, 1);
						}
						con2++;
					}
				}
				if(mStrmap.containsKey("DM-XB"+stu.getSex())){
					String content = mStrmap.get("DM-XB"+stu.getSex());
					if(xNummap3.containsKey(content)){
						xNummap3.put(content, xNummap3.get(content)+1);
					}else{
						xNummap3.put(content, 1);
					}
					con3++;
				}
			}
			//==============================
			loadingDataInt=new Integer[1][xAxisDatas1.length];
			for(i=0;i<xAxisDatas1.length;i++){
				loadingDataInt[0][i]=xNummap1.containsKey(xAxisDatas1[i])?xNummap1.get(xAxisDatas1[i]):0;
			}
			jsonObject.put("text", "学生户口类别分布图"+"（共"+con1+"人）");//主标题	可不填写
			jsonObject.put("legendData", xAxisDatas1);
			jsonArr22=new JSONArray();
			json22=new JSONObject();
			for(i=0;i<xAxisDatas1.length;i++){
				json22=new JSONObject();
				json22.put("value", loadingDataInt[0][i]);
				json22.put("name", xAxisDatas1[i]);
				jsonArr22.add(json22);
			}
			jsonObject.put("loadingData", jsonArr22);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("studentmultistatisticpie1_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
			//==========================================
			jsonObject.clear();
			loadingDataInt=new Integer[1][xAxisDatas2.length];
			for(i=0;i<xAxisDatas2.length;i++){
				loadingDataInt[0][i]=xNummap2.containsKey(xAxisDatas2[i])?xNummap2.get(xAxisDatas2[i]):0;
			}
			jsonObject.put("text", "学生类别分布图"+"（共"+con2+"人）");//主标题	可不填写
			jsonObject.put("legendData", xAxisDatas2);
			jsonArr22=new JSONArray();
			json22=new JSONObject();
			for(i=0;i<xAxisDatas2.length;i++){
				json22=new JSONObject();
				json22.put("value", loadingDataInt[0][i]);
				json22.put("name", xAxisDatas2[i]);
				jsonArr22.add(json22);
			}
			jsonObject.put("loadingData", jsonArr22);
			jsonStringCharts[1][0]=jsonObject.toJSONString();
			RedisUtils.set("studentmultistatisticpie2_"+unitId, jsonStringCharts[1][0], 60*60*12);//保存时间12小时
			//============================================
			jsonObject.clear();
			loadingDataInt=new Integer[1][xAxisDatas3.length];
			for(i=0;i<xAxisDatas3.length;i++){
				loadingDataInt[0][i]=xNummap3.containsKey(xAxisDatas3[i])?xNummap3.get(xAxisDatas3[i]):0;
			}
			jsonObject.put("text", "学生性别分布图"+"（共"+con3+"人）");//主标题	可不填写
			jsonObject.put("legendData", xAxisDatas3);
			jsonArr22=new JSONArray();
			json22=new JSONObject();
			for(i=0;i<xAxisDatas3.length;i++){
				json22=new JSONObject();
				json22.put("value", loadingDataInt[0][i]);
				json22.put("name", xAxisDatas3[i]);
				jsonArr22.add(json22);
			}
			jsonObject.put("loadingData", jsonArr22);
			jsonStringCharts[2][0]=jsonObject.toJSONString();
			RedisUtils.set("studentmultistatisticpie3_"+unitId, jsonStringCharts[2][0], 60*60*12);//保存时间12小时
		}else{
			jsonStringCharts[0][0] = jsonStr1;
			jsonStringCharts[1][0] = jsonStr2;
			jsonStringCharts[2][0] = jsonStr3;
		}
		
		
		return SUCCESS;
	}

	/**
	 * 教师性别分布图
	 * @return
	 */
	public String teacherSexPie() {
		String unitId = getUnitId();
		jsonStringCharts=new String[1][1];
		String jsonStr = RedisUtils.get("teachersexpie_"+unitId);
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			List<Teacher> tealist =teacherService.getTeachers(unitId);
			List<Mcodedetail> mlist = mcodedetailService.getAllMcodeDetails("DM-XB");
			Map<String,String> mStrmap = new HashMap<String, String>();
			String[] xAxisDatas=new String[mlist.size()];
			JSONArray jsonArr=new JSONArray();
			JSONObject json=new JSONObject();
			int i =0;
			for(Mcodedetail m : mlist){
				xAxisDatas[i] = m.getContent();
				mStrmap.put("DM-XB"+m.getThisId(), m.getContent());
				i++;
			}
			int con = 0;
			JSONObject jsonObject=new JSONObject();
			Integer[][] loadingDataInt =null;
			Map<String,Integer> xNummap = new HashMap<String, Integer>();
			for(Teacher tea : tealist){
				if(mStrmap.containsKey("DM-XB"+tea.getSex())){
					String content = mStrmap.get("DM-XB"+tea.getSex());
					if(xNummap.containsKey(content)){
						xNummap.put(content, xNummap.get(content)+1);
					}else{
						xNummap.put(content, 1);
					}
					con++;
				}
			}
			loadingDataInt=new Integer[1][xAxisDatas.length];
			for(i=0;i<xAxisDatas.length;i++){
				loadingDataInt[0][i]=xNummap.containsKey(xAxisDatas[i])?xNummap.get(xAxisDatas[i]):0;
			}
			jsonObject.put("text", "共"+con+"人");//主标题	可不填写
			jsonObject.put("legendData", xAxisDatas);
			for(i=0;i<xAxisDatas.length;i++){
				json=new JSONObject();
				json.put("value", loadingDataInt[0][i]);
				json.put("name", xAxisDatas[i]);
				jsonArr.add(json);
			}
			jsonObject.put("loadingData", jsonArr);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("teachersexpie_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
		}
		return SUCCESS;
	}

	/**
	 * 下属全部学校数，按分类统计
	 * @return
	 */
	public String subSchoolSetTypeHistogram() {
		String unitId = getUnitId();
		jsonStringCharts=new String[1][1];
		String jsonStr = RedisUtils.get("subSchoolSetTypeHistogram_"+unitId);
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			JSONObject jsonObject=new JSONObject();
			String[] legendDatas=new String[]{"数量"};
			List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-UNITUSETYPE"); 
			String[] xAxisDatas=new String[mcodeList.size()];
			Integer[][] loadingDataInt=new Integer[1][mcodeList.size()];
			Map<String, Integer> unitCountMap = unitService.getUnderUnitMapByUnitUseType(getLoginInfo().getUnitID(), false);
			int i = 0;
			for(Mcodedetail mcodedetail : mcodeList){
				xAxisDatas[i] = mcodedetail.getContent();
				if(unitCountMap.containsKey(mcodedetail.getThisId())){
					//把未维护的数据归为其他教育机构
					if("91".equals(mcodedetail.getThisId())){
						loadingDataInt[0][i] = unitCountMap.get(mcodedetail.getThisId())+(unitCountMap.get(null)==null?0:unitCountMap.get(null));
					}else{
						loadingDataInt[0][i] = unitCountMap.get(mcodedetail.getThisId());
					}
				}else{
					//把未维护的数据归为其他教育机构
					if("91".equals(mcodedetail.getThisId())){
						loadingDataInt[0][i] = unitCountMap.get(null)==null?0:unitCountMap.get(null);
					}else{
						loadingDataInt[0][i] = 0;
					}
				}
				i++;
			}
			Integer con = 0;
			for(Integer num: unitCountMap.values()){
				con += num;
			}
//			jsonObject.put("text", "下属学校统计图");//主标题
			jsonObject.put("subtext", "共"+con+"所");//副标题	可不填写
			jsonObject.put("xAxisData", xAxisDatas);
			jsonObject.put("legendData", legendDatas);
			//loadingData数据顺序和xAxisData相对
			jsonObject.put("loadingData", loadingDataInt);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("subSchoolSetTypeHistogram_"+unitId, jsonStringCharts[0][0], 60*60*24);//保存时间24小时
		}
		return SUCCESS;
	}

	/**
	 * 下属全部学校学生数，按学段，性别统计
	 * @return
	 */
	public String subSchoolStudentSexHistogram() {
		String unitId = getUnitId();
		jsonStringCharts=new String[1][1];
		String jsonStr = RedisUtils.get("subSchoolStudentSexHistogram_"+unitId);
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			JSONObject jsonObject=new JSONObject();
			List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-UNITUSETYPE");
			List<Mcodedetail> sexList = mcodedetailService.getMcodeDetails("DM-XB");
			String[] xAxisDatas=new String[mcodeList.size()];
			String[] legendDatas = new String[sexList.size()+1];
			Integer[][] loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
			Unit unit = unitService.getUnit(unitId);
			Map<String,Integer> stuNum = studentService.getUnderSchoolNumUseTypeMap(unitId, unit.getUnionid());
			int i = 0;
			int d1 = 0;
			int sum = 0;
			for(Mcodedetail mcodedetail : mcodeList){
				if(StringUtils.equals(mcodedetail.getThisId(),"01")){
					 xAxisDatas=new String[mcodeList.size() - 1];
					 loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
					continue;
				}
				xAxisDatas[i] = mcodedetail.getContent();
				int j = 0;
				for(Mcodedetail m : sexList){
					if(StringUtils.isBlank(legendDatas[j])){
						legendDatas[j] = m.getContent();
					}
					if(stuNum.containsKey(mcodedetail.getThisId() + ","+m.getThisId())){
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = stuNum.get(mcodedetail.getThisId() + ","+m.getThisId())+(stuNum.get(null+","+m.getThisId())==null?0:stuNum.get(null+","+m.getThisId()));
						}else{
							d1 = stuNum.get(mcodedetail.getThisId() + ","+m.getThisId());
						}
					}else{
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = stuNum.get(null+","+m.getThisId())==null?0:stuNum.get(null+","+m.getThisId());
						}else{
							d1 = 0;
						}
					}
					loadingDataDou[j][i]=d1;
					sum = d1 + sum;
					j++;
				}
				legendDatas[j] = "总人数";
				loadingDataDou[j][i] = sum;
				sum =0;
				i++;
			}
			jsonObject.put("xAxisData", xAxisDatas);
			jsonObject.put("legendData", legendDatas);
			//loadingData数据顺序和xAxisData相对
			jsonObject.put("loadingData", loadingDataDou);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("subSchoolStudentSexHistogram_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
			
//			JSONObject jsonObject=new JSONObject();
//			List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-UNITUSETYPE");
//			List<Mcodedetail> sexList = mcodedetailService.getMcodeDetails("DM-XB");
//			//TODO
//			String[] xAxisDatas=new String[mcodeList.size()];
//			String[] legendDatas1 = new String[sexList.size()+1];
//			String[] legendDatas = new String[]{"男生数","女生数","总人数"};
//			Integer[][] loadingDataDou=new Integer[legendDatas1.length][xAxisDatas.length];
//			Unit unit = unitService.getUnit(unitId);
//			Map<String,Integer> stuNum = studentService.getUnderSchoolNumUseTypeMap(unitId, unit.getUnionid());
//			int i = 0;
//			int d1 = 0;//男生数
//			int d2 = 0;//女生数
//			for(Mcodedetail mcodedetail : mcodeList){
//				if(StringUtils.equals(mcodedetail.getThisId(),"01")){
//					 xAxisDatas=new String[mcodeList.size() - 1];
//					 loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
//					continue;
//				}
//				xAxisDatas[i] = mcodedetail.getContent();
//				if(stuNum.containsKey(mcodedetail.getThisId() + ",1")){
//					//把未维护的数据归为其他教育机构
//					if("91".equals(mcodedetail.getThisId())){
//						d1 = stuNum.get(mcodedetail.getThisId()+",1")+(stuNum.get(null+",1")==null?0:stuNum.get(null+",1"));
//					}else{
//						d1 = stuNum.get(mcodedetail.getThisId()+",1");
//					}
//				}else{
//					//把未维护的数据归为其他教育机构
//					if("91".equals(mcodedetail.getThisId())){
//						d1 = stuNum.get(null+",1")==null?0:stuNum.get(null+",1");
//					}else{
//						d1 = 0;
//					}
//				}
//				if(stuNum.containsKey(mcodedetail.getThisId() + ",2")){
//					//把未维护的数据归为其他教育机构
//					if("91".equals(mcodedetail.getThisId())){
//						d2 = stuNum.get(mcodedetail.getThisId()+",2")+(stuNum.get(null+",2")==null?0:stuNum.get(null+",2"));
//					}else{
//						d2 = stuNum.get(mcodedetail.getThisId()+",2");
//					}
//				}else{
//					//把未维护的数据归为其他教育机构
//					if("91".equals(mcodedetail.getThisId())){
//						d2 = stuNum.get(null+",2")==null?0:stuNum.get(null+",2");
//					}else{
//						d2 = 0;
//					}
//				}
//				loadingDataDou[0][i]=d1;
//				loadingDataDou[1][i]=d2;
//				loadingDataDou[2][i]=d1+d2;
//				d1 = 0;
//				d2 = 0;
//				i++;
//			}
////			jsonObject.put("text", "学生性别统计图");//主标题	可不填写
//			jsonObject.put("xAxisData", xAxisDatas);
//			jsonObject.put("legendData", legendDatas);
//			//loadingData数据顺序和xAxisData相对
//			jsonObject.put("loadingData", loadingDataDou);
//			jsonStringCharts[0][0]=jsonObject.toJSONString();
//			RedisUtils.set("subSchoolStudentSexHistogram_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
		}
		return SUCCESS;
	}

	/**
	 * 下属全部学校学生数，按学段，户口类型统计
	 * @return
	 */
	public String subSchoolStudentHuKouHistogram() {
		String unitId = getUnitId();
		jsonStringCharts=new String[1][1];
		String jsonStr = RedisUtils.get("subSchoolStudentHuKouHistogram_"+unitId);
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			JSONObject jsonObject=new JSONObject();
			List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-UNITUSETYPE");
			List<Mcodedetail> hkList = mcodedetailService.getMcodeDetails("DM-HKXZ");
			Mcodedetail m1 = new Mcodedetail();
			m1.setContent("未维护");
			m1.setThisId(null);
			hkList.add(m1);
			String[] xAxisDatas=new String[mcodeList.size()];
			String[] legendDatas = new String[hkList.size()+1];
			Integer[][] loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
			Unit unit = unitService.getUnit(unitId);
			Map<String,Integer> stuNum = studentService.getUnderSchoolNumHkMap(unitId, unit.getUnionid());
			int i = 0;
			int d1 = 0;//男生数
			int b2 = 0;
			for(Mcodedetail mcodedetail : mcodeList){
				if(StringUtils.equals(mcodedetail.getThisId(),"01")){
					 xAxisDatas=new String[mcodeList.size() - 1];
					 loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
					continue;
				}
				xAxisDatas[i] = mcodedetail.getContent();
				int j = 0;
				for(Mcodedetail m : hkList){
					if(StringUtils.isBlank(legendDatas[j])){
						legendDatas[j] = m.getContent();
					}
					if(stuNum.containsKey(mcodedetail.getThisId() + ","+m.getThisId())){
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = stuNum.get(mcodedetail.getThisId() + ","+m.getThisId())+(stuNum.get(null+","+m.getThisId())==null?0:stuNum.get(null+","+m.getThisId()));
						}else{
							d1 = stuNum.get(mcodedetail.getThisId() + ","+m.getThisId());
						}
					}else{
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = stuNum.get(null+","+m.getThisId())==null?0:stuNum.get(null+","+m.getThisId());
						}else{
							d1 = 0;
						}
					}
					loadingDataDou[j][i]=d1;
					b2 = d1 + b2;
					j++;
				}
				legendDatas[j] = "总人数";
				loadingDataDou[j][i] = b2;
				b2 =0;
				i++;
			}
//			jsonObject.put("text", "学生性别统计图");//主标题	可不填写
			jsonObject.put("xAxisData", xAxisDatas);
			jsonObject.put("legendData", legendDatas);
			//loadingData数据顺序和xAxisData相对
			jsonObject.put("loadingData", loadingDataDou);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("subSchoolStudentHuKouHistogram_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
		}
		return SUCCESS;
	}

	/**
	 * 下属全部学校教师数，按学段统计总人数及性别
	 * @return
	 */
	public String subSchoolTeacherSexHistogram() {
		String unitId = getUnitId();
		jsonStringCharts=new String[1][1];
		String jsonStr = RedisUtils.get("subSchoolTeacherSexHistogram_"+unitId);
		if(StringUtils.isNotBlank(jsonStr)){
			jsonStringCharts[0][0] = jsonStr;
		}else{
			Unit unit = unitService.getUnit(unitId);
			Map<String,Integer> teaCountMap = teacherService.getUnionSectionSexCount(unitId,unit.getUnionid()); 
			JSONObject jsonObject=new JSONObject();
			List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-UNITUSETYPE");
			List<Mcodedetail> xbList = mcodedetailService.getMcodeDetails("DM-XB");
			String[] xAxisDatas=new String[mcodeList.size()];
			String[] legendDatas = new String[xbList.size()+1];
			Integer[][] loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
			int i = 0;
			int d1 = 0;//男数
			int b1 = 0;
			int b2 =0;
			for(Mcodedetail mcodedetail : mcodeList){
				if(StringUtils.equals(mcodedetail.getThisId(),"01")){
					 xAxisDatas=new String[mcodeList.size() - 1];
					 loadingDataDou=new Integer[legendDatas.length][xAxisDatas.length];
					continue;
				}
				xAxisDatas[i] = mcodedetail.getContent();
				int j = 0;
				for(Mcodedetail m : xbList){
					if(StringUtils.isBlank(legendDatas[j])){
						legendDatas[j] = m.getContent();
					}
					if(teaCountMap.containsKey(mcodedetail.getThisId() + ","+m.getThisId())){
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = teaCountMap.get(mcodedetail.getThisId() + ","+m.getThisId())+(teaCountMap.get(null+","+m.getThisId())==null?0:teaCountMap.get(null+","+m.getThisId()));
						}else{
							d1 = teaCountMap.get(mcodedetail.getThisId() + ","+m.getThisId());
						}
					}else{
						//把未维护的数据归为其他教育机构
						if("91".equals(mcodedetail.getThisId())){
							d1 = teaCountMap.get(null+","+m.getThisId())==null?0:teaCountMap.get(null+","+m.getThisId());
						}else{
							d1 = 0;
						}
					}
					loadingDataDou[j][i]=d1;
					b1 = d1;
					b2 = b1+b2;
					j++;
				}
				legendDatas[j] = "总人数";
				loadingDataDou[j][i] = b2;
				b1 = 0;
				b2 = 0;
				i++;
			}
//			jsonObject.put("text", "学生性别统计图");//主标题	可不填写
			jsonObject.put("xAxisData", xAxisDatas);
			jsonObject.put("legendData", legendDatas);
			//loadingData数据顺序和xAxisData相对
			jsonObject.put("loadingData", loadingDataDou);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("subSchoolTeacherSexHistogram_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
		}
		return SUCCESS;
	}

	/**
	 * 下属全部学校学生数，学生类别统计
	 * @return
	 */
	public String subSchoolStudentCategoryPie() {
		//学生综合统计--饼图
		String unitId = getUnitId();
		jsonStringCharts=new String[4][1];
		String jsonStr = RedisUtils.get("subSchoolStudentCategoryPie_"+unitId);
		String jsonStr1 = RedisUtils.get("subSchoolStudentCategoryPie1_"+unitId);//小学
		String jsonStr2 = RedisUtils.get("subSchoolStudentCategoryPie2_"+unitId);//初中
		String jsonStr3 = RedisUtils.get("subSchoolStudentCategoryPie3_"+unitId);//高中
		
		if(StringUtils.isBlank(jsonStr) || StringUtils.isBlank(jsonStr1) || StringUtils.isBlank(jsonStr2) || StringUtils.isBlank(jsonStr3)){
			List<Mcodedetail> mlist = mcodedetailService.getAllMcodeDetails("DM-XSLB");
			Mcodedetail m1 = new Mcodedetail();
			m1.setContent("未维护");
			m1.setThisId(null);
			mlist.add(m1);
			String[] xAxisDatas=new String[mlist.size()];
			JSONArray jsonArr22=new JSONArray();
			JSONObject json22=new JSONObject();
			JSONObject jsonObject=new JSONObject();
			Integer[][] loadingDataDou =new Integer[1][xAxisDatas.length];
			int con = 0;
			Unit unit = unitService.getUnit(unitId);
			Map<String,Integer> stuNumMap = studentService.getUnderSchoolNumXSLBMap(unitId, unit.getUnionid());
			int i =0;
			int d = 0;
			for(Mcodedetail m : mlist){
				xAxisDatas[i] = m.getContent();
				if(stuNumMap.containsKey(m.getThisId())){
					d = stuNumMap.get(m.getThisId());
					loadingDataDou[0][i]=d;
					con = (int) (con + d);
				}else{
					d = 0;
					loadingDataDou[0][i] = d;
				}
				i++;
			}
			jsonObject.put("text", "共"+con+"人");//主标题	可不填写
			jsonObject.put("legendData", xAxisDatas);
			for(i=0;i<xAxisDatas.length;i++){
				json22=new JSONObject();
				json22.put("value", loadingDataDou[0][i]);
				json22.put("name", xAxisDatas[i]);
				jsonArr22.add(json22);
			}
			jsonObject.put("loadingData", jsonArr22);
			jsonStringCharts[0][0]=jsonObject.toJSONString();
			RedisUtils.set("subSchoolStudentCategoryPie_"+unitId, jsonStringCharts[0][0], 60*60*12);//保存时间12小时
			Map<String,Integer> stuNumGradeMap = studentService.getUnderSchoolNumGradeCodeMap(unit.getUnionid());
			String[] xAxisDatas1=new String[]{"小一","小二","小三","小四","小五","小六"};
			String[] gradeCodes1 = new String[]{"11","12","13","14","15","16"};
			JSONObject jsonObject1=new JSONObject();
			JSONObject json1=new JSONObject();
			JSONArray jsonArr1=new JSONArray();
			con = 0;
			jsonObject1.put("legendData", xAxisDatas1);
			for(i=0;i<xAxisDatas1.length;i++){
				json1=new JSONObject();
				json1.put("value", stuNumGradeMap.containsKey(gradeCodes1[i])?stuNumGradeMap.get(gradeCodes1[i]):0);
				con = con + (stuNumGradeMap.containsKey(gradeCodes1[i])?stuNumGradeMap.get(gradeCodes1[i]):0);
				json1.put("name", xAxisDatas1[i]);
				jsonArr1.add(json1);
			}
			jsonObject1.put("text", "共"+con+"人");//主标题	可不填写
			jsonObject1.put("loadingData", jsonArr1);
			jsonStringCharts[1][0]=jsonObject1.toJSONString();
			RedisUtils.set("subSchoolStudentCategoryPie1_"+unitId, jsonStringCharts[1][0], 60*60*12);//保存时间12小时
			
			String[] xAxisDatas2=new String[]{"初一","初二","初三"};
			String[] gradeCodes2 = new String[]{"21","22","23"};
			JSONObject jsonObject2=new JSONObject();
			JSONObject json2=new JSONObject();
			JSONArray jsonArr2=new JSONArray();
			con = 0;
			jsonObject2.put("legendData", xAxisDatas2);
			for(i=0;i<xAxisDatas2.length;i++){
				json2=new JSONObject();
				json2.put("value", stuNumGradeMap.containsKey(gradeCodes2[i])?stuNumGradeMap.get(gradeCodes2[i]):0);
				con = con + (stuNumGradeMap.containsKey(gradeCodes2[i])?stuNumGradeMap.get(gradeCodes2[i]):0);
				json2.put("name", xAxisDatas2[i]);
				jsonArr2.add(json2);
			}
			jsonObject2.put("text", "共"+con+"人");//主标题	可不填写
			jsonObject2.put("loadingData", jsonArr2);
			jsonStringCharts[2][0]=jsonObject2.toJSONString();
			RedisUtils.set("subSchoolStudentCategoryPie2_"+unitId, jsonStringCharts[2][0], 60*60*12);//保存时间12小时
			
			String[] xAxisDatas3=new String[]{"高一","高二","高三"};
			String[] gradeCodes3 = new String[]{"31","32","33"};
			JSONObject jsonObject3=new JSONObject();
			JSONObject json3=new JSONObject();
			JSONArray jsonArr3=new JSONArray();
			con = 0;
			jsonObject3.put("legendData", xAxisDatas3);
			for(i=0;i<xAxisDatas3.length;i++){
				json3=new JSONObject();
				json3.put("value", stuNumGradeMap.containsKey(gradeCodes3[i])?stuNumGradeMap.get(gradeCodes3[i]):0);
				con = con + (stuNumGradeMap.containsKey(gradeCodes3[i])?stuNumGradeMap.get(gradeCodes3[i]):0);
				json3.put("name", xAxisDatas3[i]);
				jsonArr3.add(json3);
			}
			jsonObject3.put("text", "共"+con+"人");//主标题	可不填写
			jsonObject3.put("loadingData", jsonArr3);
			jsonStringCharts[3][0]=jsonObject3.toJSONString();
			RedisUtils.set("subSchoolStudentCategoryPie3_"+unitId, jsonStringCharts[3][0], 60*60*12);//保存时间12小时
			
			
			
			
			
			
		}else{
			jsonStringCharts[0][0] = jsonStr;
			jsonStringCharts[1][0] = jsonStr1;
			jsonStringCharts[2][0] = jsonStr2;
			jsonStringCharts[3][0] = jsonStr3;
		}
		return SUCCESS;
	}
	
	
	public String[][] getJsonStringCharts() {
		return jsonStringCharts;
	}

	public void setJsonStringCharts(String[][] jsonStringCharts) {
		this.jsonStringCharts = jsonStringCharts;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
	
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
}