<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<script language="javascript">
function back(){
	location.href='schoolGeneralInfoQuery-queryBasicSchool.action?name='+encodeURIComponent("${name?default('')}")+'&searchType=schoolInfo';
}	
</script>
</head>
<body >

<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec16" height="100%">
 
	<tr>
	<td valign="top">
		<div class="content_div">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr><td bgcolor="#FCFFFF" id="content">
        	<table width="100%" border="0"  cellspacing="1" cellpadding="1">
			  <tr>
			    <td  class="send_font"  width="20%">学校名称：</td>
			    <td class="send_padding" width="30%">${schoolinfo.name?default('')}</td>
			    <td  class="send_font"  width="20%">学校英文名称：</td><#--width="120" -->
			    <td class="send_padding" width="30%">${schoolinfo.englishname?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">主管单位代码：</td>
			    <td class="send_padding">${schoolinfo.educode?default('')}</td>
			    <td class="send_font">学校代码：</td>
			    <td class="send_padding">${schoolinfo.code?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">所在学区：</td>
			    <td class="send_padding">${schoolinfo.schdistrictname?default('')}</td>
			    <td class="send_font">学校地址：</td>
			    <td class="send_padding">${schoolinfo.address?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">学校类别：</td>
			    <td class="send_padding">
			    ${appsetting.getMcode("DM-XXLB").get(schoolinfo.type?default(""))}</td>
			    <td class="send_font">所在地行政区：</td>
			    <td class="send_padding">       
			    ${schoolinfo.regionname?default('')}</td>
			  </tr>
			   <tr>
			    <td class="send_font">学校校长：</td>
			    <td class="send_padding">${schoolinfo.shoolmaster?default('')}</td>
				
			    <td class="send_font">党组织负责人：</td>
			    <td class="send_padding">${schoolinfo.partymaster?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">建校年月：</td>
			    <td class="send_padding">
				<#if schoolinfo.datecreated?exists>${schoolinfo.datecreated?string("yyyy-MM-dd")}</#if></td>
			    <td class="send_font">校庆日：</td>
			    <td class="send_padding">
			     ${schoolinfo.anniversary?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">学校主管部门：</td>
			    <td class="send_padding">${schoolinfo.governor?default('')}</td>
			    <td class="send_font">招生区域：</td>
			    <td class="send_padding">
			     ${schoolinfo.recruitregion?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">学校办别：</td>
			    <td  class="send_padding" >			        
			    ${appsetting.getMcode("DM-XXBB").get(schoolinfo.runschtype?default(""))}</td>
			    <td class="send_font">所在地区类别：</td>
			    <td class="send_padding">
			    ${appsetting.getMcode("DM-SZDQLB").get(schoolinfo.regiontype?default(''))}</td>
			  </tr> 
			  
			  <tr>
			    <td class="send_font">当地经济属性：</td>
			    <td class="send_padding">
				${appsetting.getMcode("DM-SZDJJSX").get(schoolinfo.regioneconomy?default(''))}</td>
			    <td class="send_font">当地民族属性：</td>
			    <td class="send_padding">
			     ${appsetting.getMcode("DM-XDMZ").get(schoolinfo.regionnation?default(''))}</td>
			  </tr>
			  <tr>
			    <td class="send_font">小学规定年制：</td>
			    <td class="send_padding">
				${schoolinfo.gradeyear?default('')}</td>
			    <td class="send_font">小学入学年龄：</td>
			    <td class="send_padding">
			    ${schoolinfo.gradeage?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">初中规定年制：</td>
			    <td class="send_padding">
				${schoolinfo.junioryear?default('')}</td>
			    <td class="send_font">初中入学年龄：</td>
			    <td class="send_padding">
			    ${schoolinfo.juniorage?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">高中规定年制：</td>
			    <td class="send_padding">
				${schoolinfo.senioryear?default('')}</td>
			    <td class="send_font">占地面积：</td>
			    <td class="send_padding">
			    ${schoolinfo.area?default("")}
			    </td>
			  </tr>
			  <tr>
			    <td class="send_font">主教学语言：</td>
			    <td class="send_padding">
				${appsetting.getMcode("DM-ZGYZ").get(schoolinfo.primarylang?default(''))}</td>
			    <td class="send_font">辅教学语言：</td>
			    <td class="send_padding">
			    ${appsetting.getMcode("DM-ZGYZ").get(schoolinfo.secondarylang?default(''))}</td>
			  </tr>
			  <tr>
			    <td class="send_font">邮政编码：</td>
			    <td class="send_padding">
				${schoolinfo.postalcode?default('')}</td>
			    <td class="send_font">联系电话：</td>
			    <td class="send_padding">
			    ${schoolinfo.linkphone?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">传真电话：</td>
			    <td class="send_padding">
				${schoolinfo.fax?default('')}</td>
			    <td class="send_font">电子信箱：</td>
			    <td class="send_padding">
			    ${schoolinfo.email?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">主页地址：</td>
			    <td class="send_padding">
				${schoolinfo.homepage?default('')}</td>
			    <td class="send_font">组织机构代码：</td>
			    <td class="send_padding">
			    ${schoolinfo.organizationcode?default('')}</td>
			  </tr>
			  <tr>
                 <td class="send_font">学校简历：</td>
                 <td class="send_padding" colspan="3"> 
                 <textarea name="remark"  class="input_readonly" rows="6" cols="80">${schoolinfo.introduction?default('')?trim}</textarea> </td>
              </tr>     
			  </table>
           </td></tr>
            
      </table>
	  </div>
	 </td>
  </tr>
  <tr>
    <td bgcolor="#C2CDF7" height="1"></td>
  </tr>
  <tr>
    <td bgcolor="#ffffff" height="1"></td>
  </tr>
  <tr>
    <td bgcolor="#C2CDF7" height="32" class="padding_left4">
	<table width="70%"  border="0" cellspacing="0" cellpadding="0">
		
	<input type="submit" name="Submit2" value="返回" 
									class="del_button1"
									onmouseover="this.className = 'del_button3';"
									onmousedown="this.className = 'del_button2';"
									onmouseout="this.className = 'del_button1';"									
									onClick="back();"
									 />
			
	</td></tr></table>
	
	</td>
  </tr>
</table>
</body>
</html>
