package net.zdsoft.office.dataAnalysis.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StorageDirService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eisu.base.common.util.SortUtils;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.MathUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.office.dataAnalysis.constants.BaseConstants;
import net.zdsoft.office.dataAnalysis.entity.*;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportAnalysisService;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStreetService;
import net.zdsoft.office.dataAnalysis.service.OfficeDataReportStrmanagerService;
import net.zdsoft.office.util.EntityConvertUtils;
import net.zdsoft.office.util.POIUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import org.apache.ecs.html.Base;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.struts2.ServletActionContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;


/** 
 * @author  lufeng 
 * @version 创建时间：2017-3-29 下午03:44:06 
 * 类说明 
 */
public class OfficeDataAnalysisAction extends PageAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfficeDataReportAnalysisService officeDataReportAnalysisService;
	private OfficeDataReportAnalysis officeDataReportAnalysis = new OfficeDataReportAnalysis();
	private List<OfficeDataReportAnalysis>  officeDataAnalysisList = new ArrayList<OfficeDataReportAnalysis>();
	private String dataAnalysisId;
	private StorageDirService storageDirService;
	private BaseUnitService baseUnitService;
	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;
	private String shName;//表名
	private String sheetFileName;
	private Map<String,String> sheetNamesMap = new HashMap<String,String>();
	private String dataHtml;
	private boolean statistics;
	private String schoolId;
	private String schoolName;

	private String streetId;//街道Id

	private OfficeDataReportStreetService officeDataReportStreetService;
	private OfficeDataReportStrmanager officeDataReportStrmanager;
	private OfficeDataReportStrmanagerService officeDataReportStrmanagerService;

	private List<OfficeDataReportStreet> streetList;//街道list
	public String execute(){
//	    officeDataReportAnalysis = officeDataReportAnalysisService.getOfficeDataReportAnalysisByUnitId(getUnitId());
		if(officeDataReportAnalysis == null){
			officeDataReportAnalysis = new OfficeDataReportAnalysis();
		}
		officeDataReportAnalysis.setUnitId(getUnitId());
		officeDataReportAnalysis.setYear(getCurrentYear());
		officeDataReportAnalysis.setTimeFrame("1");
		schoolId = getUnitId();

		return "success";
	}
	public String getSchoolInfoMain(){
		if(StringUtils.isNotEmpty(schoolId)){
			schoolName = baseUnitService.getUnit(schoolId).getName();
		}
		return "success";
	}

	/**
	 * 进入 判断是否具有访问权限 是否是管理员
	 * @return
	 */
	public String eduAnalysisLink(){
		if(StringUtils.equals((String)getSession("jbdataAnalysis"),"success")){
			statistics = true;
		}else{
			statistics = false;
		}
		LoginInfo loginInfo = getLoginInfo();
		User user = loginInfo.getUser();
		List<OfficeDataReportStrmanager> strmanagerList = officeDataReportStrmanagerService.getOfficeDataReportStrmanagerByTeacherId(user.getTeacherid());
		Set<String> streetIds = new HashSet<String>();
		for(OfficeDataReportStrmanager manager : strmanagerList){
			String ids = manager.getStreetIds();
			String[] arrs = ids.split(",");
			for(String arr : arrs){
				streetIds.add(arr);
			}
		}
		streetList = officeDataReportStreetService.getOfficeDataReportStreetListByIds(streetIds.toArray(new String[0]));
		if(CollectionUtils.isEmpty(streetList)){
			return "input";
		}else{
			return "success";
		}

	}
	public String uploadDataFile(){
		try{
			UploadFile file = StorageFileUtils.handleFile(new String[]{"xls"}, 0);
//			OfficeDataReportAnalysis data = new OfficeDataReportAnalysis();
			if(officeDataReportAnalysis == null){
				officeDataReportAnalysis = new OfficeDataReportAnalysis();
			}
			officeDataReportAnalysis.setUnitId(getUnitId());
			officeDataReportAnalysis.setTeacherId(getLoginInfo().getUser().getTeacherid());
			officeDataReportAnalysis.setUserId(getLoginInfo().getUser().getId());
			officeDataReportAnalysis = officeDataReportAnalysisService.save(officeDataReportAnalysis, file);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage(officeDataReportAnalysis.getFilename());

			StorageDir storageDir = storageDirService.getActiveStorageDir(StorageDirType.ATTACHMENT);
			String pathPrefix = storageDir.getDir();
			//初始化统计信息
			resolveSingleFile(officeDataReportAnalysis, pathPrefix ,"统计失败 ,请使用当前模板填写之后重新导入");
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("上传失败:"+e.getMessage());
			return "success";
		}
		
		return "success";
	}
	
	public String downLoadFile(){
		
		response = getResponse();
		try{
			OfficeDataReportAnalysis data = officeDataReportAnalysisService.getOfficeDataReportAnalysisByUnitId("00000000000000000000000000000000");
//			OfficeDataReportAnalysis data = dataReportAnalysisList.get(0);
			if(data == null){
				promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + dataAnalysisId + "]");
                return PROMPTMSG;
			}
			StorageDir storageDir = storageDirService.getActiveStorageDir(data
	                .getStorageDirType());
			data.setDirPath(storageDir.getDir());
			InputStream  analysisData = data.getContentsAsStream();
			if(analysisData != null){
				reGenerateExcel(analysisData,officeDataReportAnalysis);
			}
			else{
				promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + dataAnalysisId + "]");
				return PROMPTMSG;
			}
		}catch(Exception e){
			e.printStackTrace();
			 promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage() + "]");
	         return PROMPTMSG;
		}
		
		return NONE;
	}

	/**
	 * 利用模板 改变下拉框的值 和批注信息 导出
	 * @param analysisData
	 * @param analysis
	 */
	private void reGenerateExcel(InputStream  analysisData ,OfficeDataReportAnalysis analysis){
		POIFSFileSystem poifsFileSystem = null;
		try {
			poifsFileSystem = new POIFSFileSystem(analysisData);
			HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
			HSSFSheet sheet = workbook.getSheetAt(1);
			String suffix = ".10之前";
			String[] strs = new String[5];
			String year = analysis.getYear();
			int start = 0;
			if(BaseConstants.DATA_ANALYSIS_TIME_FRAME_FIRST_HALF_YEAR.equals(analysis.getTimeFrame())){
				start = Integer.valueOf(year) -2 ;
			}else{
				start = Integer.valueOf(year) -1;
			}
			for(int i=4;i>=0;i--){
				if(i == 4){
					strs[i] = start  + ".10之后";
				}else{
					strs[i] = start  + suffix;
					start--;
				}
			}
			CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(3,83,8,8);

			DVConstraint constraint = DVConstraint.createExplicitListConstraint(strs);
			HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList,constraint);
			sheet.addValidationData(dataValidation);
			HSSFRow row = sheet.getRow(2);
			HSSFCell cell = row.getCell(17);
			Comment comment = cell.getCellComment();
			//根据江北区注册时间列自动计算，计算方法，2013.10之前为200元，2014.10之前为150元，2015.10之前为100元，2016.10之前为50元，2016.10之后为0（每年往后退一年）
			StringBuffer sb = new StringBuffer("根据江北区注册时间列自动计算，计算方法，");
			for(int i=0;i<=4;i++){
				sb.append(strs[i] + "为" + (200 -i*50) + "元，");
			}
			sb.append("（每年往后退一年）");
			comment.setString(new HSSFRichTextString(sb.toString()));
			ExportUtil.outputData(workbook,"教育局统计表");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void getFile( OfficeDataReportAnalysis analysis ){
		
		OfficeDataReportAnalysis data = officeDataReportAnalysisService.getOfficeDataReportAnalysis(analysis);
		if(data == null){
			promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + dataAnalysisId + "]");
			return;
   
		}
		StorageDir storageDir = storageDirService.getActiveStorageDir(data
                .getStorageDirType());
		data.setDirPath(storageDir.getDir());
		File file = null;
		try {
			file = data.getFile();
			resolveFile(file.getAbsolutePath(), analysis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		InputStream analysisData;
	}

	/**
	 * 解析 单个excel
	 * @param filePath
	 * @param analysis
	 */
	 private void resolveFile(String filePath ,OfficeDataReportAnalysis analysis){
		request = ServletActionContext.getRequest();
		 session = request.getSession();
			try {

				// ----------------------解析并保存模板-----------------------
				String htmlStr = POIUtils.excelToHtml(filePath, null);
//				String htmlStr = POIUtils.excelToHtml("F:\\fileDocument\\1.xls", null);
				String head = StringUtils.substring(htmlStr, 0, htmlStr.indexOf("<h2>"));
//				StringBuffer sb= new StringBuffer(head);
//				sb.append(" <p style=\"text-align:right;font-size:18px;color:blue;\"><a href=\"#\" onclick=\"doPrint();\" class=\"abtn-blue-big\">打印</a></p>");
//				head = sb.toString();
//				htmlStr = sb.toString();
				List<String> sheetNames = Arrays.asList("幼儿园信息", "教师信息", "享受补助", "缴纳社保补助", "后勤人员补助");
				
				sheetNamesMap.put("幼儿园信息", "kindergartenInfo");
				sheetNamesMap.put("教师信息", "teacherInfo");
				sheetNamesMap.put("享受补助", "enjoybenefits");
				sheetNamesMap.put("缴纳社保补助", "paySocialSecurityBenefits");
				sheetNamesMap.put("后勤人员补助", "supportStaffBenefits");
				int sheetIndex = 0;
				Map<String, String> map = new HashMap<String, String>();
				map.put("year" ,analysis.getYear() );
				map.put("timeFrame",analysis.getTimeFrame());
				map.put("unitId" ,analysis.getUnitId());
				Map<String, String> registerMap = new HashMap<String, String>();
				for (String sheetName : sheetNames) {
					sheetIndex++;
					String outFileName = sheetName + ".html";
					String id = DigestUtils.md5Hex("2017江北区幼儿园" + sheetName);
					// TODO get the projet from db
					// TODO if not exist, create it
					DcProject project = new DcProject();
					project.setId(id);
					project.setDataStartIndex(4);
					project.setTitleIndex(3);
					project.setTemplatePath("");
					project.setProjectName("江北区" + sheetName);
					project.setDataEndIndex(4);
					// TODO save to db.
					// TODO --creation end.
					syncExcel(registerMap, map, project, htmlStr, head, outFileName, sheetIndex,analysis.getUnitId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public String getInfo(){
		if(StringUtils.isEmpty(schoolId)){
			schoolId = getUnitId();
		}
//		dataHtml = (String) getSession(shName + "_"+schoolId);
		dataHtml = RedisUtils.get(shName+"_"+officeDataReportAnalysis.getUnitId() +"_" + officeDataReportAnalysis.getYear() + "_" + officeDataReportAnalysis.getTimeFrame());
//		System.out.println(dataHtml);
		if(dataHtml == null){
			getFile(officeDataReportAnalysis);
			dataHtml = RedisUtils.get(shName+"_"+officeDataReportAnalysis.getUnitId() +"_" + officeDataReportAnalysis.getYear() + "_" + officeDataReportAnalysis.getTimeFrame());
		}
		if(dataHtml == null){
			dataHtml = "信息不存在,请选择正确文件";
		}
//		setSession(shName + "_"+schoolId,null);
		return "success";
	}
	private float round(String value){
		double f = NumberUtils.toFloat(value);
		long l = Math.round(f * 10);
		return l * 0.1f;
	}
	
	private float round(float f){
		long l = Math.round(f * 10);
		return l * 0.1f;
	}
	public String initStatExcel() {

		try {
			LoginInfo loginInfo = getLoginInfo();
			String unitId = loginInfo.getUnitID();
			String teaId = loginInfo.getUser().getTeacherid();

			OfficeDataReportAnalysis data = officeDataReportAnalysisService.getOfficeDataReportAnalysisByUnitId(BaseConstants.ONE_GUID);
			if(data == null){
				promptMessageDto.setErrorMessage("附件没有找到！\n附件 ID [" + dataAnalysisId + "]");
				return "success";
			}
			StorageDir storageDir = storageDirService.getActiveStorageDir(data
	                .getStorageDirType());
			data.setDirPath(storageDir.getDir());
			File file = null;
			file =data.getFile();
			String htmlStr = POIUtils.excelToHtml(file.getAbsolutePath(), null);
			String head = StringUtils.substring(htmlStr, 0, htmlStr.indexOf("<h2>"));

			JSONArray jsons = new JSONArray();
			//如果没有统计过 则 进行统计 然后模版展示
			if(!statistics){
				initExcel();
			}
			List<OfficeDataReportAnalysis> jbdrs = new ArrayList<OfficeDataReportAnalysis>();
			if(StringUtils.isNotEmpty(schoolId)){
				OfficeDataReportAnalysis schoolStatistics = officeDataReportAnalysisService.getOfficeDataReportAnalysis(officeDataReportAnalysis);
				if(schoolStatistics != null) jbdrs.add(schoolStatistics);
			}else if(StringUtils.isNotEmpty(streetId)){
				OfficeDataReportStreet street = officeDataReportStreetService.getOfficeDataReportStreetById(streetId);
				String[] unitIds = street.getSchoolIds().split(",");
				jbdrs = officeDataReportAnalysisService.getOfficeDataReportAnalysisList(unitIds ,officeDataReportAnalysis.getYear(),officeDataReportAnalysis.getTimeFrame());

			}else{

				Set<String> setStreetId = getStreetId(unitId , teaId);
				Map<String ,OfficeDataReportStreet>  streetMap = officeDataReportStreetService.getOfficeDataReportStreetMapByIds(setStreetId.toArray(new String[0]));

				Set<String> schoolIdSet = new HashSet<String>();
				for(String streetId : setStreetId){
					OfficeDataReportStreet street = streetMap.get(streetId);
					if( street != null){
						String schoolIds = street.getSchoolIds();
						String[] ids = schoolIds.split(",");
						for(String id : ids){
							schoolIdSet.add(id);
						}
					}

				}

				jbdrs = officeDataReportAnalysisService.getOfficeDataReportAnalysisList(schoolIdSet.toArray(new String[0]) ,officeDataReportAnalysis.getYear(),officeDataReportAnalysis.getTimeFrame());


			}
			//如果存储文件 没有 则 退出
			if(CollectionUtils.isEmpty(jbdrs)){
				if(StringUtils.isEmpty(dataHtml)){
					dataHtml = "统计信息不存在";
				}
				return "success";
			}
//			jbdrs.addAll(jbdrs.subList(0, jbdrs.size()));
			Map<String,JSONObject> dataMap = new HashMap<String, JSONObject>();
			for (OfficeDataReportAnalysis jbdr : jbdrs) {
				if (StringUtils.equals(BaseConstants.ZERO_GUID, jbdr.getUnitId()) || StringUtils.equals(BaseConstants.ONE_GUID, jbdr.getUnitId()))
					continue;

				JSONObject json = new JSONObject();
				String id = DigestUtils.md5Hex("2016浙江宁波江北区上半年幼儿园-幼儿园信息");
//				Object o = RedisUtils.getObject("jbreport.dcprojectcolumn." + id + "_" + jbdr.getUnitId());
				
				List<DcProjectColumn> projectColumns = (List<DcProjectColumn>)RedisUtils.getObject("jbreport.dcprojectcolumn." + id + "_" + jbdr.getUnitId() +"_" + jbdr.getYear() + "_" + jbdr.getTimeFrame() );
//				List<DcProjectColumn> projectColumns = dcProjectColumnService
//						.findListBy(new String[] { "projectId", "unitId" }, new String[] { id, jbdr.getUnitId() });

				if(CollectionUtils.isNotEmpty(projectColumns)){
					SortUtils.ASC(projectColumns, "rowIndex");
					for (DcProjectColumn dpc : projectColumns) {
						Integer isData = dpc.getIsDataRow();
						if (isData == 1) {
							json.put("column3", dpc.getColumn3());
							json.put("column4", dpc.getColumn7());
							json.put("column5", dpc.getColumn8());
							json.put("column6", dpc.getColumn9());
						}
					}
				}


				id = DigestUtils.md5Hex("2016浙江宁波江北区上半年幼儿园-享受补助");
				projectColumns = (List<DcProjectColumn>)RedisUtils.getObject("jbreport.dcprojectcolumn." + id + "_" + jbdr.getUnitId()+"_" + jbdr.getYear() + "_" + jbdr.getTimeFrame());
//				projectColumns = dcProjectColumnService.findListBy(new String[] { "projectId", "unitId" },
//						new String[] { id, jbdr.getUnitId() });
				SortUtils.ASC(projectColumns, "rowIndex");
				int gwCount = 0, zcCount = 0, qlCount = 0;
				float gwSum = 0, zcSum = 0, qlSum = 0;
				if(CollectionUtils.isNotEmpty(projectColumns)){
					for (DcProjectColumn p : projectColumns) {
						float gw = round(p.getColumn12());
						if (gw > 0) {
							gwCount++;
							gwSum += gw * round(p.getColumn13());
						}
						float zc = round(p.getColumn14());
						if (zc > 0) {
							zcCount++;
							zcSum += zc * round(p.getColumn15());
						}
						float ql = round(p.getColumn16());
						if (ql > 0) {
							qlCount++;
							qlSum += ql * round(p.getColumn17());
						}
					}
				}

				json.put("column7", gwCount);
				json.put("column8", round(gwSum));
				json.put("column9", zcCount);
				json.put("column10", round(zcSum));
				json.put("column11", qlCount);
				json.put("column12", round(qlSum));

				id = DigestUtils.md5Hex("2016浙江宁波江北区上半年幼儿园-缴纳社保补助");
//				projectColumns = dcProjectColumnService.findListBy(new String[] { "projectId", "unitId" },
//						new String[] { id, jbdr.getUnitId() });
				projectColumns = (List<DcProjectColumn>)RedisUtils.getObject("jbreport.dcprojectcolumn." + id + "_" + jbdr.getUnitId()+"_" + jbdr.getYear() + "_" + jbdr.getTimeFrame());

				SortUtils.ASC(projectColumns, "rowIndex");
				int sbCount = 0;
				float sbSum = 0;
				for (DcProjectColumn p : projectColumns) {
					float sb = round(p.getColumn11());
					if (sb > 0) {
						sbCount++;
						sbSum += sb;
					}
				}
				json.put("column13", sbCount);
				json.put("column14", String.format("%.2f",round(sbSum)));

				id = DigestUtils.md5Hex("2016浙江宁波江北区上半年幼儿园-后勤人员补助");
//				projectColumns = dcProjectColumnService.findListBy(new String[] { "projectId", "unitId" },
//						new String[] { id, jbdr.getUnitId() });
				projectColumns = (List<DcProjectColumn>)RedisUtils.getObject("jbreport.dcprojectcolumn." + id + "_" + jbdr.getUnitId()+"_" + jbdr.getYear() + "_" + jbdr.getTimeFrame());

				SortUtils.ASC(projectColumns, "rowIndex");
				int hqCount = 0;
				float hqSum = 0;
				for (DcProjectColumn p : projectColumns) {
					float hq = round(p.getColumn10());
					if (hq > 0) {
						hqCount++;
						hqSum += hq;
					}
				}
				json.put("column15", hqCount);
				json.put("column16", hqSum);

				json.put("column17",
						round(json.getString("column8")) + round(json.getString("column10"))
								+ round(json.getString("column12")) + round(json.getString("column14"))
								+ round(json.getString("column16")));
				json.put("unitId", jbdr.getUnitId());
				System.out.println(json.toJSONString());
				dataMap.put(jbdr.getUnitId()+"_"+jbdr.getYear()+"_"+jbdr.getTimeFrame(),json);
				jsons.add(json);
			}
			StringBuffer titleBuffer = new StringBuffer(officeDataReportAnalysis.getYear());
			titleBuffer.append(BaseConstants.timeFrameMap.get(officeDataReportAnalysis.getTimeFrame()));
			titleBuffer.append("江北区");
			JSONArray jsonArray = new JSONArray();
			if(StringUtils.isNotEmpty(streetId)){
				OfficeDataReportStreet officeDataReportStreet =officeDataReportStreetService.getOfficeDataReportStreetById(streetId);
				String[] schoolIds = getSchoolIdsByStreetId(streetId);


				for(String id:schoolIds){
						JSONObject jsonObject = dataMap.get(id+"_" + officeDataReportAnalysis.getYear() +"_"+officeDataReportAnalysis.getTimeFrame());
						if(jsonObject != null){
							jsonArray.add(jsonObject);
						}

				}

				DcProject project = new DcProject();
				project.setDataEndIndex(2 + jsonArray.size());
				project.setDataStartIndex(3);
				project.setTitleIndex(2);

				String title = titleBuffer.toString() + officeDataReportStreet.getStreetName() +"幼儿园非在编教职工补助汇总";//String headStr = officeDataReportAnalysis.getYear()+ BaseConstants.timeFrameMap.get(officeDataReportAnalysis.getTimeFrame())+"江北区" +officeDataReportStreet.getStreetName()+ "幼儿园非在编教职工补助汇总";
				syncStatExcel(jsonArray, project, htmlStr, head, title,"hz.html");
//				return "success";
			}else if(StringUtils.isNotEmpty(schoolId)){
				JSONObject jsonObject = dataMap.get(schoolId+"_" + officeDataReportAnalysis.getYear() +"_"+officeDataReportAnalysis.getTimeFrame());
				if(jsonObject != null )jsonArray.add(jsonObject);
				DcProject project = new DcProject();
				project.setDataEndIndex(2 + jsonArray.size());
				project.setDataStartIndex(3);
				project.setTitleIndex(2);

				String title = titleBuffer.toString() + "幼儿园非在编教职工补助汇总";
				syncStatExcel(jsonArray, project, htmlStr, head,title, "hz.html");

			}else{

				Set<String> setStreetId = getStreetId(unitId , teaId);
				Map<String ,OfficeDataReportStreet>  streetMap = officeDataReportStreetService.getOfficeDataReportStreetMapByIds(setStreetId.toArray(new String[0]));

				Set<String> schoolIdSet = new HashSet<String>();


				StringBuffer sb = new StringBuffer();
				Set<String> setSchoolId = new HashSet<String>();

				for(String streetId : setStreetId){
					jsonArray = new JSONArray();
					OfficeDataReportStreet street = streetMap.get(streetId);
					String schoolIds = street.getSchoolIds();
					String[] ids = schoolIds.split(",");
					for(String id : ids){
						JSONObject jsonObject = dataMap.get(id+"_" + officeDataReportAnalysis.getYear() +"_"+officeDataReportAnalysis.getTimeFrame());
						if(jsonObject != null){
							jsonArray.add(jsonObject);
							setSchoolId.add(id);
						}

					}
					DcProject project = new DcProject();
					project.setDataEndIndex(2 + jsonArray.size());
					project.setDataStartIndex(3);
					project.setTitleIndex(2);

					String title = titleBuffer.toString() +street.getStreetName() + "幼儿园非在编教职工补助汇总";
					String html = syncStatExcel(jsonArray, project, htmlStr, head,title, "hz.html");
					sb.append(html);

				}

				jsonArray = new JSONArray();
				for(String id : setSchoolId){
					JSONObject jsonObject = dataMap.get(id+"_" + officeDataReportAnalysis.getYear() +"_"+officeDataReportAnalysis.getTimeFrame());
					if(jsonObject != null){
						jsonArray.add(jsonObject);
					}

				}
				DcProject project = new DcProject();
				project.setDataEndIndex(2 + jsonArray.size());
				project.setDataStartIndex(3);
				project.setTitleIndex(2);

				String title = titleBuffer.toString() + "幼儿园非在编教职工补助汇总";
				String html = syncStatExcel(jsonArray, project, htmlStr, head, title,"hz.html");
				sb.append(html);

				dataHtml = sb.toString();

			}
			if(StringUtils.isEmpty(dataHtml)){
				dataHtml = "统计信息不存在";
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private Set<String> getStreetId(String unitId , String teacherId){
		Unit unit = baseUnitService.getBaseUnit(unitId);
		String eduUnitId = unit.getId();
		if(Unit.UNIT_CLASS_SCHOOL == unit.getUnitclass()){
			eduUnitId = unit.getParentid();
		}
		List<OfficeDataReportStrmanager> strmanagerList = officeDataReportStrmanagerService.getOfficeDataReportStrmanagerByUnitIdTeaId(eduUnitId,teacherId);
		Set<String> setStreetId = new HashSet<String>();
		for(OfficeDataReportStrmanager manager : strmanagerList){
			String streetIds = manager.getStreetIds();
			String[] ids = streetIds.split(",");
			for(String id : ids){
				setStreetId.add(id);
			}
		}

		return setStreetId;
	}
	private String syncStatExcel(JSONArray jsons, DcProject project, String htmlStr, String head,String title,
			String outFileName) throws IOException, UnsupportedEncodingException {

		htmlStr = StringUtils.substringAfter(htmlStr, "</h2>");
		htmlStr = head + StringUtils.substringBefore(htmlStr, "<h2>");
		org.jsoup.nodes.Document doc = Jsoup.parse(htmlStr);
		doc.select("tbody tr td").get(0).text(title);
		Elements trs = doc.select("tbody tr");
		List<DcProjectColumn> dctrs = new ArrayList<DcProjectColumn>();
		
//		project.setDataEndIndex(project.getDataStartIndex());

		int columnSize = 0;
		int rowIndex = 0;
		for (Element tr : trs) {
			rowIndex++;
			DcProjectColumn dctr = new DcProjectColumn();
			dctr.setIsTemplate(1);
//			dctr.setId(UuidUtils.generateUuid());
			if (rowIndex >= project.getDataStartIndex() && rowIndex <= project.getDataEndIndex())
				dctr.setIsDataRow(1);
			dctr.setProjectId(project.getId());
			dctr.setRowIndex(tr.elementSiblingIndex());
			if (tr.childNodeSize() > 1 && tr.childNode(1).childNodeSize() > 0) {
				String s = tr.childNode(1).childNode(0).outerHtml();
				if (StringUtils.isNotBlank(s) && NumberUtils.isNumber(s)) {
					dctr.setIsDataRow(1);
					if (project.getDataEndIndex() < rowIndex) {
						project.setDataEndIndex(rowIndex);
					}
				}
			}
			boolean allBlank = true;
			for (Node td : tr.childNodes()) {
				if (td.siblingIndex() > columnSize)
					columnSize = td.siblingIndex();
				if (td.childNodeSize() > 0) {
					String s = td.childNode(0).outerHtml();
					if (StringUtils.isNotBlank(s) && !"&nbsp;".equals(s))
						allBlank = false;
					EntityConvertUtils.setValue(dctr, "column" + (td.siblingIndex() + 1), s);
				}
			}
			if (!allBlank)
				dctrs.add(dctr);
		}
		project.setColumnSize(columnSize);
		// dcProjectService.save(project);
		// ------------------------------模板保存完毕----------------------

		// 读取有权限填报的项目
		org.jsoup.nodes.Document doc2 = Jsoup.parse(htmlStr);
		List<DcProjectColumn> colss = dctrs;
		Element firstTR = doc2.select("tbody tr:eq(0)").get(0);
		Elements es = doc2.select("tbody tr:lt(" + project.getDataStartIndex() + ")");
		doc2.select("tbody tr:gt(0)").remove();

		int index = 1;
		ExcelStat sm = new ExcelStat();

		for (int i = 0; i < jsons.size(); i++) {
			JSONObject json = jsons.getJSONObject(i);

				DcProjectColumn column = JSONObject.parseObject(json.toJSONString(), DcProjectColumn.class);
				column.setColumn2(i + 1 + "");
				colss.add(colss.size() - 1, column);


		}

		for (DcProjectColumn cols : colss) {
			if (index < project.getDataStartIndex() || index > project.getDataEndIndex()) {
				Element tr;
				if (index == 1)
					tr = firstTR;
				else {
					int indexES = index < project.getDataStartIndex() ? index
							: index - project.getDataEndIndex() + project.getDataStartIndex() - 1;
					tr = es.get(indexES - 1).clone();
				}
				int colspanTotal = 0;
				for (int i = 0; i < project.getColumnSize(); i++) {
					String v = EntityConvertUtils.getValue(cols, "column" + (i + 1));
					if (v != null && tr.childNodeSize() > i) {
						int colspan = NumberUtils.toInt(tr.childNode(i).attr("colspan"));
						if (colspan == 0) {
							colspan = 1;
						}
						colspanTotal += colspan;
						if (Arrays.asList("{求和}", "｛求和｝", "{sum}", "｛sum｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(sm.getSum("column_" + (colspanTotal)));
							EntityConvertUtils.setValue(cols, "column" + (i + 1), round(sm.getSum("column_" + (colspanTotal))) );
						} else if (Arrays.asList("{sumInt}", "｛sumInt｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html("" + Math.round(NumberUtils.toDouble(sm.getSum("column_" + (colspanTotal)))));
							EntityConvertUtils.setValue(cols, "column" + (i + 1), Math.round(NumberUtils.toDouble(sm.getSum("column_" + (colspanTotal)))));
						} else if (Arrays.asList("{计数}", "｛计数｝", "{count}", "｛count｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(sm.getCount("column_" + (colspanTotal)));
							EntityConvertUtils.setValue(cols, "column" + (i + 1), sm.getCount("column_" + (colspanTotal)));
						} else if (Arrays.asList("{教师姓名}", "｛教师姓名｝", "{teacherName}", "｛teacherName｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html("XXXX 教师");
						} else if (Arrays.asList("{时间}", "｛时间｝", "{time}", "｛time｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
						} else if (Arrays.asList("{日期}", "｛日期｝", "{date}", "｛date｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
						} else if (Arrays.asList("{单位}", "｛单位｝", "{unitName}", "｛unitName｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html("XXXX单位");
						} else {
							Node node = tr.childNode(i);
							try {
								((Element) node).html(v);
							} catch (Exception e) {
								((TextNode) node).text(v);
							}
						}
					}
				}
				if (index > 1) {
					firstTR.parent().append(tr.toString());
				}
			} else if (index <= project.getDataEndIndex()) {
				Element tr = es.get(project.getDataStartIndex() - 1).clone();
				for (int ii = 0; ii < project.getColumnSize(); ii++) {
					int i = ii;
					String v = EntityConvertUtils.getValue(cols, "column" + (i + 1));
					if (v != null) {
						sm.sum("column_" + i, v);
						sm.count("column_" + i);
						if (tr.childNodeSize() <= i)
							continue;
						Node node = tr.childNode(i);
						EntityConvertUtils.setValue(cols, "column" + (i + 1), v);
						if (node instanceof Element){
							if(i == 2){
								String unitId = EntityConvertUtils.getValue(cols, "unitId");
								//学校端查看汇总信息
								if(StringUtils.isNotEmpty(schoolId)){
									((Element) node).html( v );
								//教育局端查看汇总信息
								}else{
									((Element) node).html("<a onclick=showInfo('" + unitId +"'); href='javascript:void(0);'>" + v + "</a>");
								}
							}
							else
								((Element) node).html(v);
						}
						else if (node instanceof TextNode)
							((TextNode) node).text(v);
					}
				}
				firstTR.parent().append(tr.toString());
			}
			index++;
		}

		// DcProjectColumn[] dcs = colss.toArray(new DcProjectColumn[0]);
		// dcProjectColumnService.saveAll(dcs);

//		org.apache.commons.io.FileUtils.write(new File("c:\\doc\\" + outFileName),
//				new String(doc2.toString().getBytes("UTF-8"), "UTF-8"));
		dataHtml = new String(doc2.toString().getBytes("UTF-8"), "UTF-8");

		return dataHtml;
	}

	/**
	 *
	 * @param id 街道id
	 * @return
	 */
	private String[] getSchoolIdsByStreetId(String id){
		OfficeDataReportStreet officeDataReportStreet =officeDataReportStreetService.getOfficeDataReportStreetById(id);
		String schoolIds = officeDataReportStreet.getSchoolIds();
		String[] ids = new String[]{};
		if(StringUtils.isNotEmpty(schoolIds)){
			ids = schoolIds.split(",");

		}

		return ids;
	}
	public String initExcel() {
		
		StorageDir storageDir = storageDirService.getActiveStorageDir(StorageDirType.ATTACHMENT);
		String pathPrefix = storageDir.getDir();
		try {
			List<OfficeDataReportAnalysis> jbdrs = officeDataReportAnalysisService.getOfficeDataReportAnalysisByYearTimeFrame(officeDataReportAnalysis.getYear(),officeDataReportAnalysis.getTimeFrame());

			for (OfficeDataReportAnalysis jbdr : jbdrs) {
				if (StringUtils.equals(BaseConstants.ZERO_GUID, jbdr.getUnitId()) || StringUtils.equals(BaseConstants.ONE_GUID, jbdr.getUnitId()))
					continue;
				// ----------------------解析并保存模板-----------------------
//				String filePath = pathPrefix +File.separator+ jbdr.getFilePath();
				try{
					resolveSingleFile(jbdr, pathPrefix, "统计失败");
				}catch (Exception e){
					continue;
				}

			}

			setSession("jbdataAnalysis","success");
			promptMessageDto.setOperateSuccess(true);
			
			promptMessageDto.setPromptMessage("统计成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("统计失败 ");
		}
		return "success";
	}
	public void resolveSingleFile(OfficeDataReportAnalysis jbdr ,String pathPrefix ,String errorMsg){
		try{
			jbdr.setDirPath(pathPrefix);
			File jbdrFile = jbdr.getFile();

			String htmlStr ="";
			try{
				htmlStr = POIUtils.excelToHtml(jbdrFile.getAbsolutePath(), null);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("该文件 解析 失败 " + jbdr.getFilePath());
				officeDataReportAnalysisService.delete(new String[]{jbdr.getId()});
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage(errorMsg );

			}

			String head = StringUtils.substring(htmlStr, 0, htmlStr.indexOf("<h2>"));
			List<String> sheetNames = Arrays.asList("幼儿园信息", "教师信息", "享受补助", "缴纳社保补助", "后勤人员补助");
			sheetNamesMap.put("幼儿园信息", "kindergartenInfo");
			sheetNamesMap.put("教师信息", "teacherInfo");
			sheetNamesMap.put("享受补助", "enjoybenefits");
			sheetNamesMap.put("缴纳社保补助", "paySocialSecurityBenefits");
			sheetNamesMap.put("后勤人员补助", "supportStaffBenefits");
			int sheetIndex = 0;
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> registerMap = new HashMap<String, String>();
			map.put("unitId", jbdr.getUnitId());
			map.put("userId", jbdr.getUserId());
			map.put("year", jbdr.getYear());
			map.put("timeFrame",jbdr.getTimeFrame());
			for (String sheetName : sheetNames) {
				sheetIndex++;
				String outFileName = sheetName + ".html";
				String id = DigestUtils.md5Hex("2016浙江宁波江北区上半年幼儿园-" + sheetName);
//					DcProject project = dcProjectService.findOne(id);
				DcProject project = RedisUtils.getObject("jbreport.dcproject." + id);
				if (project == null) {
					project = new DcProject();
					project.setId(id);
					project.setDataStartIndex(4);
					project.setTitleIndex(3);
					project.setTemplatePath("");
					project.setProjectName("2016浙江宁波江北区上半年幼儿园-" + sheetName);
					project.setDataEndIndex(4);
				}
				RedisUtils.remove("jbreport.dcprojectcolumn." + project.getId() + "_" + jbdr.getUnitId() + "_" + map.get("year") + "_" + map.get("timeFrame"));
//					dcProjectColumnService.delete(project.getId(), jbdr.getUnitId());
				syncExcel(registerMap, map, project, htmlStr, head, outFileName, sheetIndex, jbdr.getUnitId());
			}
		}catch(Exception  e){
			e.printStackTrace();
		}
	}


	
	private  Map<String, String> syncExcel(Map<String, String> registerValueMap, Map<String, String> map,
			DcProject project, String htmlStr, String head, String outFileName, int sheetIndex ,String unitId)
			throws IOException, UnsupportedEncodingException {
//		System.out.println(htmlStr);
		String htmldddName = StringUtils.substringBefore(outFileName, ".html");
		for (int i = 0; i < sheetIndex; i++) {
			htmlStr = StringUtils.substringAfter(htmlStr, "</h2>");
		}
		htmlStr = head + StringUtils.substringBefore(htmlStr, "<h2>");

		org.jsoup.nodes.Document doc = Jsoup.parse(htmlStr);
		Elements trs = doc.select("tbody tr");
		project.setDataEndIndex(project.getDataStartIndex());
		List<DcProjectColumn> dctrs = new ArrayList<DcProjectColumn>();
		int columnSize = 0;
		int rowIndex = 0;
		for (Element tr : trs) {
			rowIndex++;
			DcProjectColumn dctr = new DcProjectColumn();
			dctr.setIsTemplate(1);
//			dctr.setId(UuidUtils.generateUuid());
			if (rowIndex >= project.getDataStartIndex() && rowIndex <= project.getDataEndIndex())
				dctr.setIsDataRow(1);
			dctr.setUserId("");
			dctr.setUnitId("");
			dctr.setProjectId(project.getId());
			dctr.setRowIndex(tr.elementSiblingIndex());
			if (tr.childNodeSize() > 1 && tr.childNode(1).childNodeSize() > 0) {
				String s = tr.childNode(1).childNode(0).outerHtml();
				if (StringUtils.isNotBlank(s) && NumberUtils.isNumber(s)) {
					dctr.setIsDataRow(1);
					if (project.getDataEndIndex() < rowIndex) {
						project.setDataEndIndex(rowIndex);
					}
				}
			}
			for (Node td : tr.childNodes()) {
				if (td.siblingIndex() > columnSize)
					columnSize = td.siblingIndex();
				if (td.childNodeSize() > 0) {
					String s = td.childNode(0).outerHtml();
					if (rowIndex == project.getTitleIndex()) {
						map.put(sheetIndex + "-" + s, td.siblingIndex() + 1 + "");
					}
					EntityConvertUtils.setValue(dctr, "column" + (td.siblingIndex() + 1), s);
				}
			}
			dctrs.add(dctr);
		}
		project.setColumnSize(columnSize);
		RedisUtils.setObject("jbreport.dcproject." + project.getId(), project);
		
		// ------------------------------模板保存完毕----------------------
		
		// 读取有权限填报的项目
		org.jsoup.nodes.Document doc2 = Jsoup.parse(htmlStr);
		List<DcProjectColumn> colss = dctrs;
		Element firstTR =null;
		if(doc2.select("tbody tr:eq(0)").size() > 0){
			firstTR = doc2.select("tbody tr:eq(0)").get(0);
		}
		Elements es = doc2.select(
				"tbody tr:lt(" + project.getDataStartIndex() + "),tr:gt(" + (project.getDataEndIndex() - 1) + ")");
		doc2.select("tbody tr:gt(0)").remove();
	
		int index = 1;
		ExcelStat sm = new ExcelStat();
		for (DcProjectColumn cols : colss) {
			if (index < project.getDataStartIndex() || index > project.getDataEndIndex()) {
				Element tr;
				if (index == 1)
					tr = firstTR;
				else {
					int indexES = index < project.getDataStartIndex() ? index
							: index - project.getDataEndIndex() + project.getDataStartIndex();
					tr = es.get(indexES - 1).clone();
				}
				int colspanTotal = 0;
				for (int i = 0; i < project.getColumnSize(); i++) {
					String v = EntityConvertUtils.getValue(cols, "column" + (i + 1));
					if (v != null && tr.childNodeSize() > i) {
						int colspan = NumberUtils.toInt(tr.childNode(i).attr("colspan"));
						if (colspan == 0) {
							colspan = 1;
						}
						colspanTotal += colspan;
						if (Arrays.asList("{求和}", "｛求和｝", "{sum}", "｛sum｝").contains(v)) {
							Node node = tr.childNode(i);
							
							float value = round(sm.getSum("column_" + (colspanTotal)));
							((Element) node).html(String.format("%.1f",value));
						} else if (Arrays.asList("{计数}", "｛计数｝", "{count}", "｛count｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(sm.getCount("column_" + (colspanTotal)));
						} else if (Arrays.asList("{姓名}", "｛姓名｝", "{name}", "｛name｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html("XXX老师");
						} else if (Arrays.asList("{时间}", "｛时间｝", "{time}", "｛time｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
						} else if (Arrays.asList("{日期}", "｛日期｝", "{date}", "｛date｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
						} else if (Arrays.asList("{单位}", "｛单位｝", "{unitName}", "｛unitName｝").contains(v)) {
							Node node = tr.childNode(i);
							((Element) node).html("XX单位");
						} else {
							Node node = tr.childNode(i);
							try {
								((Element) node).html(v);
							} catch (Exception e) {
								((TextNode) node).text(v);
							}
						}
					}
				}
				if (index > 1) {
					firstTR.parent().append(tr.toString());
				}
			} else if (index <= project.getDataEndIndex()) {
				Element tr = es.get(project.getDataStartIndex() - 1).clone();
				for (int ii = 0; ii < project.getColumnSize(); ii++) {
					int i = ii;
					String v = EntityConvertUtils.getValue(cols, "column" + (i + 1));

					if (sheetIndex == 1) {
						int sIndex = NumberUtils.toInt(map.get(1 + "-" + "办园性质（自填）")) - 1;
						if (i == sIndex) {
							registerValueMap.put("map.yeyxz", v);
						}
					}

					if (sheetIndex == 5) {
						if (i == 7) {
							String gender = EntityConvertUtils.getValue(cols, "column4");
							String age = EntityConvertUtils.getValue(cols, "column5");
							if ((StringUtils.equals("女", gender) && NumberUtils.toInt(age) > 55)
									|| StringUtils.equals("男", gender) && NumberUtils.toInt(age) > 60) {
								v = "0";
								String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
								EntityConvertUtils.setValue(cols, "column8", v);
								if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
										&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
									v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
								}
							}
						} else if (i == 9) {
							String v1 = EntityConvertUtils.getValue(cols, "column8");
							String v2 = EntityConvertUtils.getValue(cols, "column9");
							v = "" + NumberUtils.toDouble(v1) * NumberUtils.toDouble(v2);
						}
					}

					if (sheetIndex == 4) {
						if (i == 9) {
							int nameIndex = NumberUtils.toInt(map.get(sheetIndex + "-" + "教师姓名(自动生成)"));
							if (nameIndex <= 0)
								nameIndex = 2;
							String gwbz = registerValueMap
									.get("gwbz." + EntityConvertUtils.getValue(cols, "column" + nameIndex));
							if (StringUtils.isBlank(gwbz) || "0".equals(gwbz)) {
								v = "0";
							} else {

								double f = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column7"))
										* NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column9"));
								f = MathUtils.round(f, 2);
								v = "" + f;
							}
							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("&nbsp;", v2) && StringUtils.isNotBlank(v2)
									&& !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
						} else if (i == 10) {
							int nameIndex = NumberUtils.toInt(map.get(sheetIndex + "-" + "教师姓名(自动生成)"));
							if (nameIndex <= 0)
								nameIndex = 2;
							String gwbz = registerValueMap
									.get("gwbz." + EntityConvertUtils.getValue(cols, "column" + nameIndex));
							if (StringUtils.isBlank(gwbz) || "0".equals(gwbz)) {
								v = "0";
							} else {

								double f = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column7"))
										* NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column9")) / 2;
								f = MathUtils.round(f, 2);
								v = "" + f;
							}
							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
									&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
						}
					}

					if (sheetIndex == 2) {
						int sIndex = NumberUtils.toInt(map.get(2 + "-" + "江北区注册时间（自填）")) - 1;
						if (sIndex == -1)
							sIndex = 9;
						if (i == sIndex) {
							int sNameIndex = NumberUtils.toInt(map.get(2 + "-" + "姓名（自填）"));
							registerValueMap.put("" + EntityConvertUtils.getValue(cols, "column" + sNameIndex), v);
						}
					}

					if (sheetIndex == 3 || sheetIndex == 2) {
						int czbt = NumberUtils.toInt(map.get(sheetIndex + "-" + "职业技术职称(自动生成)"));
						if (czbt <= 0)
							czbt = 11;
						int dyxlIndex = NumberUtils.toInt(map.get(sheetIndex + "-" + "第一学历（全日制）(自动生成)"));
						if (dyxlIndex <= 0)
							dyxlIndex = 13;

						int dexlIndex = NumberUtils.toInt(map.get(sheetIndex + "-" + "进修学历(自动生成)"));
						if (dexlIndex <= 0)
							dexlIndex = 16;

						int gwbz = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月岗位补助(自动生成)")) - 1;
						if (gwbz < 0)
							gwbz = 19;

						int zcbz = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月职称补助(自动生成)")) - 1;
						if (zcbz < 0)
							zcbz = 20;

						int qlbz = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月区龄补助(自动生成)")) - 1;
						if (qlbz < 0)
							qlbz = 18;

						int hjbz = NumberUtils.toInt(
								map.get(sheetIndex + "-" + "合计补助（自动计算：每月岗位补助*补助月数+每月职称补助*职称补助月数+每月区龄补助*区龄补助月数）")) - 1;

						if (sheetIndex == 2) {
							if (qlbz < gwbz) {
								if (i == qlbz) {
									i = i + 1;
								} else if (i == gwbz) {
									i = i - 1;
								}
							}
						}

						// 职称补贴
						if (i == zcbz) {
							if ("选择性民办".equals(registerValueMap.get("map.yeyxz"))) {
								v = "0";
							} else {

								int nameIndex = NumberUtils.toInt(map.get(sheetIndex + "-" + "姓名(自动生成)"));
								if (nameIndex <= 0) {
									nameIndex = 3;
								}

								String gwbz2 = registerValueMap
										.get("gwbz." + EntityConvertUtils.getValue(cols, "column" + nameIndex));
								if (StringUtils.isBlank(gwbz2) || "0".equals(gwbz2)) {
									v = "0";
								} else {
									String s = EntityConvertUtils.getValue(cols, "column" + czbt);
									if (StringUtils.equals("中级", s)) {
										v = "500";
									} else if (StringUtils.equals("初级", s)) {
										v = "100";
									} else {
										v = "0";
									}
								}
							}
							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
									&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
							// 岗位补贴
						} else if (i == gwbz) {
							if ("选择性民办".equals(registerValueMap.get("map.yeyxz"))) {
								v = "0";
							} else {
								String dyxl = EntityConvertUtils.getValue(cols, "column" + dyxlIndex);
								String dyzy = EntityConvertUtils.getValue(cols,
										"column" + (dyxlIndex + (sheetIndex == 2 ? 2 : 1)));
								String dexl = EntityConvertUtils.getValue(cols, "column" + dexlIndex);
								String dezy = EntityConvertUtils.getValue(cols,
										"column" + (dexlIndex + (sheetIndex == 2 ? 2 : 1)));
								String zc = EntityConvertUtils.getValue(cols, "column" + czbt);

								if (StringUtils.equals("中专或高中", dyxl)) {
									if (Arrays.asList("学前教育", "师范教育类", "医护类").contains(dyzy)) {
										// 1.第一学历中专、学前教育、师范类、医护类、没有第二学历，无职称200。初级职称300。
										if (StringUtils.isBlank(dexl) || StringUtils.equals(dexl, "0") || StringUtils.equals(dexl, "&nbsp;")) {
											if (StringUtils.equals("未定级", zc)) {
												v = "200";
											} else if (StringUtils.equals("初级", zc)) {
												v = "300";
											}
										}
										// 2.第一学历中专、学前教育，师范类、医护类，第二学历大专、学前、师范、艺术类、医护类、无职称、初级职称300，
										// 最高学历本科，学前、师范、艺术类、医护类，无职称、初级职称500，中级800。
										else if (StringUtils.equals("大专", dexl)) {
											if (Arrays.asList("学前教育", "师范教育类", "医护类", "艺术院校类").contains(dezy)) {
												if (Arrays.asList("未定级", "初级").contains(zc)) {
													v = "300";
												} else if (StringUtils.equals(zc, "中级")) {
													v = "800";
												}
											}
											else if("其他".equals(dezy)){
												if (Arrays.asList("未定级").contains(zc)) {
													v = "200";
												}
												else if (Arrays.asList("初级").contains(zc)) {
													v = "300";
												} else if (StringUtils.equals(zc, "中级")) {
													v = "800";
												}
											}
										} else if (StringUtils.equals("本科", dexl)) {
											if (Arrays.asList("学前教育", "师范教育类", "医护类", "艺术院校类").contains(dezy)) {
												if (Arrays.asList("未定级", "初级").contains(zc)) {
													v = "500";
												} else if (StringUtils.equals("中级", zc))
													v = "800";
											}
										} else if (StringUtils.equals("研究生", dexl)) {
											if(Arrays.asList("学前教育").contains(dezy)){
												v = "800";
											}else{
												if (Arrays.asList( "未定级","初级").contains(zc)) {
													v = "500";
												} else if (StringUtils.equals("中级", zc))
													v = "800";
											}
										}
									} else if (StringUtils.equals("其他", dyzy)) {
										if (StringUtils.equals("未定级", zc)) {
											v = "0";
										} else if (StringUtils.equals("初级", zc)) {
											v = "300";
										} else if (StringUtils.equals("中级", zc)) {
											v = "800";
										}
									}
								}

								else if (StringUtils.equals("大专", dyxl)) {
									//
//									if (!StringUtils.equals("其他", dyzy)) {
									if (!Arrays.asList("其他").contains(dyzy)) {  
										// 第一学历大专除其他专业外，第二学历本科，除其他专业外，无职称、初级职称500，中级职称800。
//										a、第二学历是本科，除其他专业外未定级和初级 ---> 500, 中级 ---> 800，第二学历研究生，除其他专业外，未定级、初级、中级都是800.
										//专业符合  起点 500  开始 研究生 第二专业符合 为 800
										if(StringUtils.equals("研究生", dexl)){
											if (Arrays.asList("学前教育", "师范教育类", "医护类", "艺术院校类").contains(dezy)) {
												v = "800";
											}else{
												if (Arrays.asList("未定级", "初级").contains(zc)) {
													v = "500";
												} else if (StringUtils.equals("中级", zc))
													v = "800";
												
												// 第一学历大专，除其他专业外、无职称、初级职称500.中级职称800。
											}
										}else{
											if (Arrays.asList("未定级", "初级").contains(zc)) {
												v = "500";
											} else if (StringUtils.equals("中级", zc))
												v = "800";
										}
									}
									// 第一学历大专，其他专业，无职称200。初级职称300。中级职称800。
									else {
										if (StringUtils.equals("未定级", zc)) {
											v = "200";
										} else if (StringUtils.equals("初级", zc))
											v = "300";
										else if (StringUtils.equals("中级", zc))
											v = "800";

										// 第一学历大专，其他专业，第二学历本科、学前、医护类专业，初级职称500，中级职称800.
//										第一学历是大专，且专业 其他，无第二学历，岗位补助200.
//										a、第二学历本科，且专业 学前、教育、艺术、医护， 未定级 ---> 200, 初级 ---> 300， 中级 ---> 800
//										b、第二学历本科，专业其他，未定级 ---> 200， 初级 ---> 300， 中级 ---> 800
										if (StringUtils.equals("本科", dexl)) {
											if (StringUtils.equals("初级", zc))
												v = "300";
											else if (StringUtils.equals("中级", zc))
												v = "800";
											else if (StringUtils.equals("未定级", zc))
												v = "200";
										}
									}
								}

								else if (StringUtils.equals("本科", dyxl)) {
									// 第一学历本科，除其他专业外800。无职称、初级、中级职称800。第二学历研究生，学前、医护类、艺术类，有无职称都800
									if (!StringUtils.equals("其他", dyzy)) {
										v = "800";
									} else {
										// 第一学历本科，其他专业，无职称、初级职称500，中级职称800。第二学历研究生，学前、医护类，有无职称都800
										if (StringUtils.equals("未定级", zc)) {
											v = "500";
										} else if (StringUtils.equals("初级", zc))
											v = "500";
										else if (StringUtils.equals("中级", zc))
											v = "800";

										if (StringUtils.equals("研究生", dexl)
												&& Arrays.asList("学前教育", "医护类").contains(dezy)) {
											v = "800";
										}
									}
								}
								// 第一学历研究生，除其他专业外800，有无职称都1000。
								else if (StringUtils.equals("研究生", dyxl)) {
									if (StringUtils.equals("其他", dyzy)) {
										v = "800";
									} else {
										v = "1000";
									}
								}
							}
							registerValueMap.put("gwbz." + EntityConvertUtils.getValue(cols, "column3"), v);

							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
									&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
						}
						// 区龄补贴
						else if (i == qlbz) {
							if ("选择性民办".equals(registerValueMap.get("map.yeyxz"))) {
								v = "0";

								String dyxl = EntityConvertUtils.getValue(cols, "column" + dyxlIndex);
								String dyzy = EntityConvertUtils.getValue(cols,
										"column" + (dyxlIndex + (sheetIndex == 2 ? 2 : 1)));
								String dexl = EntityConvertUtils.getValue(cols, "column" + dexlIndex);
								String zc = EntityConvertUtils.getValue(cols, "column" + czbt);
								
								boolean pass = false;
								if (StringUtils.equals("中专或高中", dyxl)) {
									if (Arrays.asList("学前教育", "师范教育类", "医护类").contains(dyzy)) {
										pass = true;
									}
								} 
								else{
									pass = true;
								}
								
								if(!"未定级".equals(zc))
									pass = true;
								
								if (!pass) {
									v = "0";
								} else {
									String registerTime = registerValueMap.get(EntityConvertUtils.getValue(cols, "column3"));
									String timeFrame = map.get("timeFrame");
									int year =0;
									//上半年度 向前推两年 就是今年是2017年 就是以2015年10月为界，下半年度 向前推1年 就是以2016年10月为界
									// 比如今年的时间设定是：2016年9月之后为0,2016年9月之前为50，2015年9月之前为100，2014年9月之前为150，2013年9月之前为200，
									if(timeFrame == "0"){
										year = Calendar.getInstance().get(Calendar.YEAR) - 2;
									}else{
										year = Calendar.getInstance().get(Calendar.YEAR) - 1;
									}

									if (StringUtils.equals(year + ".10之后", registerTime))
										v = "0";
									else {
										for (int k = 0; k < 4; k++) {
											if (StringUtils.equals((year - k) + ".10之前", registerTime)) {
												v = 50 * (k + 1) + "";
												break;
											}
										}
									}
								}

							} else {
								String gwbz2 = registerValueMap.get("gwbz." + EntityConvertUtils.getValue(cols, "column3"));
								if (StringUtils.isBlank(gwbz2) || "0".equals(gwbz2)) {
									v = "0";
								} else {
									String registerTime = registerValueMap.get(EntityConvertUtils.getValue(cols, "column3"));
									String timeFrame = map.get("timeFrame");
									int year =0;
//									int year = Calendar.getInstance().get(Calendar.YEAR) - 2;
									if(timeFrame == "0"){
										year = Calendar.getInstance().get(Calendar.YEAR) - 2;
									}else{
										year = Calendar.getInstance().get(Calendar.YEAR) - 1;
									}
									if (StringUtils.equals(year + ".10之后", registerTime))
										v = "0";
									else {
										for (int k = 0; k < 4; k++) {
											if (StringUtils.equals((year - k) + ".10之前", registerTime)) {
												v = 50 * (k + 1) + "";
												break;
											}
										}
									}
								}

							}

							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
									&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
						} else if (i == hjbz) {
							int s1Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月岗位补助(自动生成)"));
							int s11Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "岗位补助月数（自填）"));
							int s2Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月职称补助(自动生成)"));
							int s21Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "职称补助月数（自填）"));
							int s3Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "每月区龄补助(自动生成)"));
							int s31Index = NumberUtils.toInt(map.get(sheetIndex + "-" + "区龄补助月数（自填）"));

							float s1 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s1Index));
							float s11 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s11Index));
							float s2 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s2Index));
							float s21 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s21Index));
							float s3 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s3Index));
							float s31 = NumberUtils.toFloat("" + EntityConvertUtils.getValue(cols, "column" + s31Index));
							v = s1 * s11 + s2 * s21 + s3 * s31 + "";
							String v2 = EntityConvertUtils.getValue(cols, "column" + (i + 1));
							if (!StringUtils.equals("0", v2) && !StringUtils.equals("&nbsp;", v2)
									&& StringUtils.isNotBlank(v2) && !StringUtils.equals(v, v2)) {
								v = "<font color='red'>" + v + "(原:" + v2 + ")</font>";
							}
						}
					}
					if (v != null) {
						sm.sum("column_" + i, v);
						sm.count("column_" + i);
						if (tr.childNodeSize() <= i)
							continue;
						Node node = tr.childNode(i);
						EntityConvertUtils.setValue(cols, "column" + (i + 1), v);
						((Element) node).html(v);
					}
				}
				firstTR.parent().append(tr.toString());
			}
			index++;
		}
		
//		Element dody = doc2.select("body").first();
//		Element script = doc2.select("head").first();
//		script.append("<script type=\"text/javascript\" src=\"${request.contextPath}/static/js/jquery-1.7.2.min.js\"></script>");
//		
//		script.append("<script>function doPrint(){jQuery(\"#titleDiv\").show();LODOP=getLodop(null,null,\"${request.contextPath}\");LODOP.ADD_PRINT_HTM(\"25mm\",\"15mm\",\"RightMargin:15mm\",\"BottomMargin:15mm\",getPrintContent(jQuery(\'#listtableDiv\')));LODOP.PREVIEW();$(\"#titleDiv\").hide();}</script>");
//		dody.prepend(" <p style=\"text-align:right;font-size:18px;color:blue;\"><a href=\"#\" onclick=\"doPrint();\" class=\"abtn-blue-big\">打印</a></p>");
		
		DcProjectColumn[] dcs = colss.toArray(new DcProjectColumn[0]);
//		dcProjectColumnService.saveAll(dcs);
		RedisUtils.setObject("jbreport.dcprojectcolumn." + project.getId() + "_" + map.get("unitId")+"_" + map.get("year") + "_" + map.get("timeFrame") ,Lists.newArrayList(dcs));
		String htmlName = StringUtils.substringBefore(outFileName, ".html");
		//new String(doc2.toString().getBytes("UTF-8"), "UTF-8")
		RedisUtils.set(sheetNamesMap.get(htmlName)+"_"+unitId +"_" + map.get("year") + "_" + map.get("timeFrame") ,doc2.outerHtml());
		return map;
	}
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	public String getSheetFileName() {
		return sheetFileName;
	}
	public void setSheetFileName(String sheetFileName) {
		this.sheetFileName = sheetFileName;
	}
	public String getDataHtml() {
		return dataHtml;
	}
	public void setDataHtml(String dataHtml) {
		this.dataHtml = dataHtml;
	}
	public boolean isStatistics() {
		return statistics;
	}
	public void setStatistics(boolean statistics) {
		this.statistics = statistics;
	}
	public List<OfficeDataReportAnalysis> getOfficeDataAnalysisList() {
		return officeDataAnalysisList;
	}
	public void setOfficeDataAnalysisList(
			List<OfficeDataReportAnalysis> officeDataAnalysisList) {
		this.officeDataAnalysisList = officeDataAnalysisList;
	}
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}
	public String getUnitName() {
		return schoolName;
	}
	public void setUnitName(String unitName) {
		this.schoolName = unitName;
	}

	public String getStreetId() {
		return streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	public void setOfficeDataReportStreetService(OfficeDataReportStreetService officeDataReportStreetService) {
		this.officeDataReportStreetService = officeDataReportStreetService;
	}

	public void setOfficeDataReportStrmanager(OfficeDataReportStrmanager officeDataReportStrmanager) {
		this.officeDataReportStrmanager = officeDataReportStrmanager;
	}

	public void setOfficeDataReportStrmanagerService(OfficeDataReportStrmanagerService officeDataReportStrmanagerService) {
		this.officeDataReportStrmanagerService = officeDataReportStrmanagerService;
	}

	public List<OfficeDataReportStreet> getStreetList() {
		return streetList;
	}

	public void setStreetList(List<OfficeDataReportStreet> streetList) {
		this.streetList = streetList;
	}
	public OfficeDataReportAnalysis getOfficeDataReportAnalysis() {
		return officeDataReportAnalysis;
	}
	public void setOfficeDataReportAnalysis(
			OfficeDataReportAnalysis officeDataReportAnalysis) {
		this.officeDataReportAnalysis = officeDataReportAnalysis;
	}
	public void setOfficeDataReportAnalysisService(
			OfficeDataReportAnalysisService officeDataReportAnalysisService) {
		this.officeDataReportAnalysisService = officeDataReportAnalysisService;
	}
	public String getDataAnalysisId() {
		return dataAnalysisId;
	}
	public void setDataAnalysisId(String dataAnalysisId) {
		this.dataAnalysisId = dataAnalysisId;
	}
	public void setStorageDirService(StorageDirService storageDirService) {
		this.storageDirService = storageDirService;
	}

	public static void main(String[] args) {
//		POIFSFileSystem poifsFileSystem = null;
//		try {
//			 poifsFileSystem = new POIFSFileSystem(new File("D:\\store\\dataReportAnalysis\\model\\201702.xls"));
//			 HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
//			HSSFSheet sheet = workbook.getSheetAt(1);
//			String[] strs = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii"};
//			CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(3,26,8,8);
//
//			DVConstraint constraint = DVConstraint.createExplicitListConstraint(strs);
//			HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList,constraint);
//			sheet.addValidationData(dataValidation);
//
//			ExportUtil.outputData(workbook,"201702");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


	}
}
