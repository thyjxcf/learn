<html>
<head>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/util.js"></script>
<script src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script src="${request.contextPath}/static/js/prototype.js"></script>
<script src="${request.contextPath}/static/js/buffalo.js"></script>
<script>

//验证表单数据
function checkform(){
   
  // if(!checkElement(form1.name,"学校名称")||!checkOverLen(form1.name,60,"学校名称")){
  //    return;
  // }
   if(!checkElement(form1.code,"学校代码")){
      return;
   }
   if(!checkElement(form1.regionname,"所在地行政区")){
      return;
  }
   if(!checkElement(form1._type,"学校类别")){
      return;
   }
   if(!checkElement(form1.runschtype,"学校办别")){
      return;
   }
      
   //学校代码只能是12位
   var codeVal = form1.code.value;
   var codeLen = getLength(codeVal);
   if(codeLen != 12){
   		addFieldError(form1.code,"学校代码长度必须为12，目前的长度为"+codeLen+"位，请更改！");
   		form1.code.focus();
   		return;
   }   
   
   if(!checkOverLen(form1.shortName,32,"学校简称")){
      return;
   }
   if(!checkOverLen(form1.englishname,180,"学校英文名称")){
      return;
   }
   if(!checkOverLen(form1.address,60,"学校地址")){
      return;
   }
   if(!checkOverLen(form1.anniversary,60,"校庆日")){
      return;
   }
   if(!checkOverLen(form1.shoolmaster,30,"学校校长")){
      return;
   }
   if(!checkOverLen(form1.partymaster,30,"党组织负责人")){
      return;
   }
   if(!checkDate(form1.datecreated,"建校年月")){
      return;
   }
   if(!checkOverLen(form1.governor,60,"学校主管部门")){
      return;
   }
   if(!checkOverLen(form1.recruitregion,30,"招生区域")){
      return;
   }
   if(!checkInteger(form1.infantyear,"幼儿园规定年制")){
      return;
   }
   if(!checkNumRange2(form1.infantyear,"幼儿园规定年制",1,9)){
      return;
   }
   if(!checkInteger(form1.gradeyear,"小学规定年制")){
      return;
   }
   if(!checkNumRange2(form1.gradeyear,"小学规定年制",1,9)){
      return;
   }
   if(!checkInteger(form1.junioryear,"初中规定年制")){
      return;
   }
   if(!checkNumRange2(form1.junioryear,"初中规定年制",1,9)){
      return;
   }
   if(!checkInteger(form1.senioryear,"高中规定年制")){
      return;
   }
   if(!checkNumRange2(form1.senioryear,"高中规定年制",1,9)){
      return;
   }
   if(!checkInteger(form1.gradeage,"小学入学年龄")){
      return;
   }
   if(!checkNumRange2(form1.gradeage,"小学入学年龄",1,9)){
      return;
   }
   if(!checkInteger(form1.infantage,"幼儿园入学年龄")){
      return;
   }
   if(!checkNumRange2(form1.infantage,"幼儿园入学年龄",1,9)){
      return;
   }
   if(!checkInteger(form1.juniorage,"初中入学年龄")){
      return;
   }
   if(!checkNumRange2(form1.juniorage,"初中入学年龄",1,18)){
      return;
   }
   if(!checkOverLen(form1.linkphone,30,"联系电话")){
      return;
   }
   if(!checkPhone(form1.fax,"传真电话")){
      return;
   }
   if(!checkEmail(form1.email)){
      return;
   }
//   if(form1.homepage.value!=""&&!checkipport(form1.homepage,"主页地址")){
//      return;
//   }
	
   if(!checkOverLen(form1.homepage,60,"主页地址")){
      return;
   }
   
   if(!checkwebaddress(form1.homepage,"主页地址")){
      return;
   }
   
   if(!checkPostalCode(form1.postalcode)){
      return;
   }
   

   
   //组织机构代码,只能为0位或9位
   var elem = form1.organizationcode;
   if(!isBlank(elem.value)){
	   var pattern = /^[0-9]{8}[0-9xX]{1}$/;
	   if(!pattern.test(elem.value)) {
	   	 	addFieldError(elem.name,"组织机构代码长度必须为9位数字，请更改！");
	        elem.focus();
	        return;
	   }
   }
   
   if(!checkOverLen(form1.introduction,4000,"学校简历")){
      return;
   }
   
   //if(!checkOverLen(form1.introduction,4,"历史沿革")){
      //return;
   //}
   
   
   
   
    form1.type.value = form1._type.value;
    //var code = document.getElementById("code").value;  
    var id = document.getElementById("id").value;
    var newSchDistriId = document.getElementById("schdistrictid").value;
    if(originCode != null && originCode != "") {
		if(originSchDistriId != newSchDistriId) { //即学区有变化
			//if(window.confirm("您修改了学区设置，您想同步学生的应服务学区吗？")){
				document.getElementById("synchroSchDistrict").value = "synchroSchDistrict";
				form1.submit();	
			//} else {
			//	form1.submit();
			//} 		
		}else {
			form1.submit();
		}
	}else{	//表示新建立学校时，此时学校无编号。  
		form1.submit();
	}
   
}

//定义buffalo
var endPoint="${request.contextPath}/basedata/sch/";	
var buffalo = new Buffalo(endPoint);
function doCheckCode(){
	var code = document.form1.code.value;
	buffalo.remoteActionCall("schoolinfo-remoteCheckCode.action", "checkRemoteCode", [code], function(reply) {
		var rtnStr = reply.getResult();
		if(rtnStr == "true"){
			alert("学校代码已经存在，请更改！");
			document.form1.code.focus();
		}else if(rtnStr == "false"){
			alert("该学校代码可以使用！");
		}else{
			alert("错误信息："+rtnStr);
		}
	});	
}

var originSchDistriId;
var originCode;  
function getSchDistrictId() {
	//保存原始的学区id和学校代码
	originSchDistriId = document.getElementById("schdistrictid").value;
	originCode = document.getElementById("code").value; 
}

</script>
</head>
<body onload="getSchDistrictId();">
<iframe width="174" height="172" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${request.contextPath}/static/js/calendar/ipopeng.html?begin=2000-01-01" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;"></iframe>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec7" height="100%">
    <!--tr>
        <td height="28" class="send_title">	
        </td>
	</tr-->
	<tr>
		<td height="100%" valign="top" class="YecSpec8" style="padding-left:30px;">
			<div class="content_div">
			<form name="form1" action="schoolinfo.action"  method="post">
			<input type="hidden" name="schoolId" value="${schoolinfo.schoolId?default("")}">
			<input type="hidden" name="synchroSchDistrict" value="">
			<input type="hidden" name="id" value="${schoolinfo.id?default('')}">		
			<input type="hidden" name="operation" value="save">			
			<table width="100%" border="0" cellpadding="0" cellspacing="4">
								<tr>
								  <td width="140" align="right" 　align="right"><font color="red">*</font>学校名称：</td>
								  <td ><input name="name" type="text" class="input_readonly"
										maxlength="64" value="${schoolinfo.name?default('')?trim}" readonly></td>
								  <#-- 学校编号不显示 -->
								  <td width="120" align="right" style="display:none">学校编号：</td>
									<td  style="display:none"><input name="etohSchoolIdDisplay" type="text" title="系统内部编号"
									<#assign etohschoolIdWeb = schoolinfo.etohSchoolId?default('')?trim>
										class="input_readonly" size="19" maxlength="30" value="<#if etohschoolIdWeb != "">${etohschoolIdWeb}<#else>系统内部编号</#if>">
										
										<input name="etohSchoolId" type="hidden" value="${schoolinfo.etohSchoolId?default('')?trim}">
									</td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right"><font color="red">*</font>学校代码：</td>
									<td ><input name="code" type="text" class="input"
										maxlength="12" value="${schoolinfo.code?default('')?trim}" fieldtip="学校代码必须为12位">&nbsp;
										<#if selfDeploy><input type="button" name="checkcode" value="远程校验" onClick="doCheckCode()"></#if></td>
									<td  align="right">所在学区：</td>
									<td><select name="schdistrictid" style="width:150px;">
									    <option value="">--请选择--</option>
										<#list schDistriList as listItem>
											<option value="${listItem.id?default('')}" <#if listItem.id == schoolinfo.schdistrictid?default("")>selected</#if> >
												${listItem.name?default('')}
											</option>
										</#list>      
										
									</select></td>										
								</tr>
								<tr>
								  <td width="140" align="right" 　align="right">主管单位代码：</td>
								  <td ><input type="text" class="input_readonly"
										maxlength="60" value="${schoolinfo.educode?default('')?trim}" readonly></td>
									<td width="120" align="right">学校简称：</td>
									<td ><input name="shortName" type="text"
										class="input" size="19" maxlength="30" value="${schoolinfo.shortName?default('')?trim}"></td>
								</tr>
								<tr>
									<td align="right"><font color="red">*</font>学校类别：</td>
									<td colspan="3">									
									<select name="_type" style="width:200px;"  <#if typecontrol>disabled</#if> fieldtip="这个属性会决定系统可选择的学段范围。">									
										${appsetting.getMcode("DM-XXLB").getHtmlTag(schoolinfo.type?default(""))}
									</select> <#if typecontrol><font color="red">${typemessage?default('')}</font></#if>
									<input name="type" type="hidden" value="{${schoolinfo.type?default('')}}"/>
									<input name="typecontrol" type="hidden" value="${typecontrol.toString()}"/>
									<input name="typemessage" type="hidden" value="${typemessage?default('')}"/>
									</td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">学校英文名称：</td>
									<td colspan="3"><input name="englishname" type="text"
										class="input" size="59" maxlength="180" value="${schoolinfo.englishname?default('')?trim}"></td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">学校地址：</td>
									<td colspan="3"><input name="address" type="text" class="input"
										size="59" maxlength="60" value="${schoolinfo.address?default('')?trim}"></td>
								</tr>												
								<tr >
									<td width="140"  align="right"><font color="red">*</font>所在地行政区：</td>
									<td ><input size="20" name="regionname" type="text"
										class="input_readonly" value="${schoolinfo.regionname?default(regionName?default(""))?trim}"> 
										<input name="region" type="hidden" class="input" value="${schoolinfo.region?default(regionId?default(""))}"> 
										<#--<input type="button" name="btn2" class="i" value=".." onClick="openwindow('${request.contextPath}/common/xtree/regiontree.action?codeField=region&valueField=regionname','region',300,400)">-->
										</td>
									<td width="120" align="right">学校校长：</td>
									<td ><input name="shoolmaster" type="text"
										class="input" size="19" maxlength="30" value="${schoolinfo.shoolmaster?default('')?trim}"></td>
								</tr>
								<tr >
									<td width="140"  align="right">党组织负责人：</td>
									<td ><input name="partymaster" type="text" class="input" maxlength="30" value="${schoolinfo.partymaster?default('')?trim}" >									</td>
									<td width="120" align="right">建校年月：</td>
									<td ><input name="datecreated" type="text" 
										class="input" size="14" maxlength="30" value="<#if schoolinfo.datecreated?exists>${schoolinfo.datecreated?string("yyyy-MM-dd")}</#if>">
										<a href="javascript:void(0)" onClick="gfPop.fPopCalendar(document.getElementById('datecreated'));return false;" hidefocus><img name="popcal" align="absmiddle" src="${request.contextPath}/static/js/calendar/calbtn.gif"  border="0" alt=""></a></td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">校庆日：</td>
									<td ><input name="anniversary" type="text" class="input"
										maxlength="60" value="${schoolinfo.anniversary?default('')?trim}"></td>
									<td  align="right">重点级别：</td>
									<td><select name="emphases" style="width:150px;">										
										${appsetting.getMcode("DM-ZDJB").getHtmlTag(schoolinfo.emphases?default(''))}
									</select></td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">学校主管部门：</td>
									<td ><input name="governor" type="text" class="input"
										maxlength="60" value="${schoolinfo.governor?default('')?trim}"></td>
									<td width="120" align="right">农民工子女定点学校：</td>
									<td ><input type="radio" name="boorish" value="0" <#if schoolinfo.boorish?default(0) == 0>checked</#if>>否
										<input type="radio" name="boorish" value="1" <#if schoolinfo.boorish?default(0) == 1>checked</#if>>是 
										</td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">招生区域：</td>
									<td ><input name="recruitregion" type="text" class="input"
										maxlength="30" value="${schoolinfo.recruitregion?default('')?trim}"></td>
									<td  align="right">示范级别：</td>
									<td><select name="demonstration" style="width:150px;">										
										${appsetting.getMcode("DM-SFJB").getHtmlTag(schoolinfo.demonstration?default('9'))}
									</select></td>
								</tr>														
								<tr>
									<td width="140" align="right"><font color="red">*</font>学校办别：</td>
									<td width="150"><select name="runschtype" id="runschtype" style="width:150px;" class="">
										${appsetting.getMcode("DM-XXBB").getHtmlTag(schoolinfo.runschtype?default(""))}
									</select></td>
									<td  align="right">所在地区类别：</td>
									<td><select name="regiontype" style="width:150px;">
										${appsetting.getMcode("DM-SZDQLB").getHtmlTag(schoolinfo.regiontype?default(''))}
									</select></td>
								</tr>
								<tr>
									<td width="140" align="right">当地经济属性：</td>
									<td width="100"><select name="regioneconomy" style="width:150px;">
										
										${appsetting.getMcode("DM-SZDJJSX").getHtmlTag(schoolinfo.regioneconomy?default(''))}
									</select></td>
									<td  align="right">当地民族属性：</td>
									<td><select name="regionnation" style="width:150px;">
										
										${appsetting.getMcode("DM-XDMZ").getHtmlTag(schoolinfo.regionnation?default(''))}
									</select></td>
								</tr>
								<tr>
									<td width="140" align="right">幼儿园规定年制：</td>

									<td ><input name="infantyear" id="infantyear" type="text" value="<#if schoolinfo.infantyear==0>3<#else>${schoolinfo.infantyear}</#if>" fieldtip="规定幼儿园的年级数，一般为3，在班级设置中按照该值设定年级。" <#-- if infantYearReadonly>readonly class="input_readonly"<#else> class="input" </#if> --><input name="infantyear_readonly" type="hidden" value=""/></td><#--${infantYearReadonly.toString()} -->

									<td  align="right">幼儿园入学年龄：</td>

									<td ><input name="infantage" id="infantage" type="text" class="input" value="<#if schoolinfo.infantage==0>5<#else>${schoolinfo.infantage?default(0)}</#if>" ></td>

								</tr>
								<tr>
									<td width="140" align="right">小学规定年制：</td>
									<td ><input name="gradeyear" id="gradeyear" type="text" value="${schoolinfo.gradeyear}" fieldtip="规定小学的年级数，一般为6，在班级设置中按照该值设定年级。" <#if gradeyear_readonly>readonly class="input_readonly"<#else> class="input" </#if>><input name="gradeyear_readonly" type="hidden" value="${gradeyear_readonly.toString()}"/></td>
									<td  align="right">小学入学年龄：</td>
									<td ><input name="gradeage" id="gradeage" type="text" class="input" value="${schoolinfo.gradeage}" ></td>
								</tr>
								<tr>
									<td width="140" align="right">初中规定年制：</td>
									<td ><input name="junioryear" id="junioryear" type="text" value="${schoolinfo.junioryear}" fieldtip="规定初中的年级数，一般为3，在班级设置中按照该值设定年级。" <#if junioryear_readonly>readonly class="input_readonly"<#else> class="input" </#if>><input name="junioryear_readonly" type="hidden" value="${junioryear_readonly.toString()}"/></td>
									<td  align="right">初中入学年龄：</td>
									<td ><input name="juniorage" id="juniorage" type="text" class="input" value="${schoolinfo.juniorage}" ></td>
								</tr>
								<tr>
									<td width="140" align="right">高中规定年制：</td>
									<td >									
									<input name="senioryear" id="senioryear" type="text"  value="${schoolinfo.senioryear}" fieldtip="规定高中的年级数，一般为3，在班级设置中按照该值设定年级。" <#if senioryear_readonly>readonly class="input_readonly"<#else> class="input" </#if>><input name="senioryear_readonly" type="hidden" value="${senioryear_readonly.toString()}"/></td>
									<td width="120" align="right">占地面积：</td>
									<td ><input name="area" type="text" class="input" value="${schoolinfo.area?default("")}" maxlength="20" ></td>
								</tr>
								<tr>
									<td width="140" align="right">主教学语言：</td>
									<td width="100"><select name="primarylang" style="width:150px;">										
										${appsetting.getMcode("DM-ZGYZ").getHtmlTag(schoolinfo.primarylang?default(''))}
									</select></td>
									<td  align="right">辅教学语言：</td>
									<td><select name="secondarylang" style="width:150px;">
									
										${appsetting.getMcode("DM-ZGYZ").getHtmlTag(schoolinfo.secondarylang?default(''))}
									</select></td>
								</tr>
								<tr>
									<td width="140" align="right">邮政编码：</td>
									<td ><input name="postalcode" type="text" class="input" maxlength="6" value="${schoolinfo.postalcode?default('')?trim}" fieldtip="6位数字"></td>
									<td  align="right">联系电话：</td>
									<td ><input name="linkphone" type="text" class="input" maxlength="30" value="${schoolinfo.linkphone?default('')?trim}" ></td>
								</tr>
								<tr>
									<td width="140" align="right">传真电话：</td>
									<td ><input name="fax" type="text" class="input" maxlength="30" value="${schoolinfo.fax?default('')?trim}" ></td>
									<td  align="right">电子信箱：</td>
									<td ><input name="email" type="text" class="input" maxlength="40" value="${schoolinfo.email?default('')?trim}" ></td>
								</tr>
								<tr>
									<td width="140" align="right">主页地址：</td>
									<td ><input name="homepage" id="homepage" type="text" class="input" maxlength="60" value="${schoolinfo.homepage?default('')?trim}" ></td>
									<td  align="right">组织机构代码：</td>
									<td ><input name="organizationcode" type="text" class="input" maxlength="9" value="${schoolinfo.organizationcode?default('')?trim}" fieldtip="9位数字"></td>
								</tr>
								<tr>
									<td width="140" align="right" 　align="right">学校简历：</td>
									<td colspan="3"><textarea name="introduction" class="input" rows="6" cols="58"  >${schoolinfo.introduction?default('')?trim}</textarea></td>
								</tr>
							</table>
					
			</form>
			</div>
		</td>
	</tr>

	  <tr style="height:0px;"><td id="actionTip"></td></tr>       
	  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	  <tr><td bgcolor="#ffffff" height="1"></td></tr>
	  <tr>
	    <td bgcolor="#C2CDF7" height="32" class="padding_left4">
		<table border="0" cellspacing="0" cellpadding="0">   	    
          <tr>
            <td width="10%" align="center"><label>
            　
                <input type="submit" name="Submit" value="保存" class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';"
                onclick="checkform()"/>
            </label></td>
            <td width="10%" align="cneter"><label>
            <input type="button" name="Submit2" value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';"
                onclick="location.href = location.href"/>
            </label></td>
          </tr>
        </table>
		</td>
	  </tr>
</table>
</body>
</html>
