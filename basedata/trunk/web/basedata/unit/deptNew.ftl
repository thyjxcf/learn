<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/htmlcomponent.ftl" as common>
<html>
<head>
<title>${webAppTitle}--部门新增</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<SCRIPT language=javascript src="${request.contextPath}/static/js/validate.js" type=text/javascript></SCRIPT>
<SCRIPT language=javascript src="${request.contextPath}/static/js/wz_dragdrop.js" type=text/javascript></SCRIPT>
<script>
var isSubmited = false;
function saveDept(){
	if(isSubmited) return;

	//表单验证
//	alert(document.getElementById('jymark').value);
	if(formValidate()){
		var f=document.deptForm;
		<#if (loginInfo.unitClass==1)> 
			f.action="deptAdmin-update.action?jymark=1&&modID=${modID?default('')}&&ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}";	
		<#else>
			f.action="deptAdmin-update.action?modID=${modID?default('')}&&ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}";		
		</#if>

		f.submit();
		isSubmited = true;
	}
}
function formValidate(){
	var f=document.deptForm;
	if(trim(document.getElementById('deptname').value)==''){
		addFieldError('deptname','请输入部门名称');
		return false;
	}
	else if(document.getElementById('jymark').value==''){
		addFieldError('jymark','请选择教研组标识');
		return false;
	}
	else if(document.getElementById('parentid').value==''){
		addFieldError('parentid','请选择上级部门');
		return false;
	}
	else if(!checkInteger(document.getElementById('orderid'),'部门排序号')){
		return false;
	}
		
	return true;
}
function cancelDept(){
	window.location.href="deptAdmin-list.action?modID=${modID?default('')}&&deptId=${deptId?default('0000000-0000-0000-0000-000000000000')}&&unitId=${unitId?default('0000000-0000-0000-0000-000000000000')}&&ec_p=${ec_p?default('')}&&ec_crd=${ec_crd?default('')}";
}
function chooseParentDept(){
	document.getElementById('deptDiv').style.display='';
}
function changeParentDept(){
	var parentdeptname=document.getElementById('parentName');
	
	if(parentdeptname.value==''){
		parentdeptname.value='${topDeptName!}';
	}	
}
function initialParentName(){
	var parentdeptname=document.getElementById('parentName');
	var parentdeptid=document.getElementById('parentid');
	
	if(parentdeptid.value=='${topDeptId!}'){
		parentdeptname='${topDeptName!}';
	}
}
function deptChoose(){
	document.getElementById('parentid').value=document.getElementById('choose_deptId').value;
	document.getElementById('parentName').value=document.getElementById('choose_deptName').value;
	document.getElementById('deptDiv').style.display='none';
}
</script>
</head>
<body>
<form name="deptForm" method="POST">
<input name="unitId" type="hidden" value="${unitId?default('00000000000000000000000000000000')}">
<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>		
	<td valign="top">
	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		  <td height="100%" valign="top">
		  	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
		  	  <tr>
		  	  	<td>
		  	  	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  	  	  	<tr><td class="send_font_title">部门新增</td></tr>
		  	  	  	<tr><td bgcolor="#FCFFFF">
		  	  	  	  <table width="100%" height="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width" width="100"><font color="red">*</font>部门名称：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="deptname" id="deptname" type="text" class="input" value="${dept.deptname?default('')}" fieldtip="请输入部门名称，不超过50个字符(一个中文算两个字符)"></td>
		  	  	  	  	  <td class="send_font_no_width" width="100">部门编号：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="deptCode" id="deptCode" type="text" class="input" value="${dept.deptCode?default('')}" fieldtip="请输入部门编号，不超过6位"></td>  	  	  				  				  		  	  	  	  	  
  	  	  				</tr>
  	  	  				<tr>
  	  	  				  <td class="send_font_no_width"><font color="red">*</font>上级部门：</td>
  	  	  				  <td class="send_padding_no_width">
							<div id="deptDiv" style="position:absolute;width:150px;height:200px;z-index:99;border:2px solid #6C85CE;display:none;overflow:auto;">
							  <table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec">
							  	<tr><td class="send_font_title">请选择上级部门：</td></tr>
							  	<tr>
								  <td height="95%">
								  	<iframe name="deptTreeFrame" marginwidth="0" marginheight="0" src="${request.contextPath}/common/xtree/depttree.action?unitId=${unitId?default('')}"
										frameborder="0" width="100%" height="100%" allowTransparency="true">
									</iframe>
									<input name="choose_deptId" id="choose_deptId" type="hidden" value="">
									<input name="choose_deptName" id="choose_deptName" type="hidden" value="">
								  </td>
							    </tr>
							    <tr>
								  <td align="middle"><input name="confirm" type="button" class="del_button1" value="确定" onclick="deptChoose();">
								  <input name="cancel" type="button" value="取消" class="del_button1" onclick="document.getElementById('deptDiv').style.display='none';">
								  </td>
							  	</tr>
							  </table>
							</div>
  	  	  				  	<input name="preParentId" type="hidden" value="${dept.preParentId?default('')}">	  	  				  	  	  	  				  	
  	  	  				  	<input name="parentid" type="hidden" id="parentid" value="${dept.parentid?default('')}">	  	  	  				
  	  	  				  	<input name="parentName" type="text" class="input" readonly="true" style="width:128px;" value="${dept.parentName?default('')}">
	  	  	  				<img src="${request.contextPath}/static/images/tree_icon.gif" width="15" height="14" onclick="chooseParentDept();" style="cursor:pointer;">
	  	  	  			  </td>
  	  	  				  <td class="send_font_no_width"><font color="red">*</font>教研组标识：</td>
  	  	  				  <td class="send_padding_no_width">
  	  	  				     <select <#if (loginInfo.unitClass==1)> disabled </#if> name="jymark" id="jymark" style="width:150px">
  	  	  				  	  ${appsetting.getMcode("DM-JYBZ").getHtmlTag(dept.jymark?default('')?string)}
  	  	  					  </select>
  	  	  				  </td>
  	  	  				</tr>
  	  	  				<tr>  	  	  	
  	  	  				  <td class="send_font_no_width"><font color="red">*</font>部门排序号：</td>
  	  	  				  <td class="send_padding_no_width"><input name="orderid" id="orderid" type="text" class="input" value="${dept.getOrderid()?default('')}" fieldtip="请输入部门排序号"></td>
  	  	  				  <td class="send_font_no_width">负责人：</td>
  	  	  				  <td class="send_padding_no_width"><select name="principan" id="principan" style="width:150px"><option value="">--请选择--</option>
  	  	  					<#list userList as user>
  	  	  	  				<option value="${user.getId()?default('')}" <#if principan?exists&&principan==user.id>selected</#if>>${user.getName()?default('')}<#if user.realname?exists>(${user.realname})</#if></option>
  	  	  					</#list>
  	  	  					</select>
  	  	  				  </td>
  	  	  				</tr>
  	  	 				<tr>
  	  	  				  <td class="send_font_no_width">电话号码：</td>
  	  	  				  <td class="send_padding_no_width"><input name="depttel" id="depttel" type="text" class="input" value="${dept.getDepttel()?default('')}" fieldtip="请输入电话号码" maxlength="20"></td>
  	  	  				  <td class="send_font_no_width" >分管领导：</td>
  	  	  				  <td class="send_padding_no_width"><select name="leaderId" id="leaderId" style="width:150px"><option value="">--请选择--</option>
  	  	  					<#list userList as user>
  	  	  	  				<option value="${user.getId()?default('')}" <#if dept.leaderId?exists&&dept.leaderId==user.id>selected</#if>>${user.getName()?default('')?trim}<#if user.realname?exists>(${user.realname})</#if></option>
  	  	  					</#list>
  	  	  					</select></td>
  	  	  				</tr>
  	  	  				<tr>
  	  	  				  <td class="send_font_no_width" valign="top">描述信息：</td><td colspan="3" class="send_padding_no_width"><textarea name="about" id="about" class="input" cols="69" rows="5">${dept.getAbout()?default('')}</textarea></td>
  	  	  				</tr>
					  </table>
		  	  	  	</td></tr>
		  	  	  </table>
		  	  	</td>
		  	  </tr>
		  	</table>
		  </td>
		</tr>				
	  </table>			
	</td>
  </tr>
  <tr style="height:0px;"><td id="actionTip"></td></tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4">
	  	<table width="153" border="0" cellspacing="0" cellpadding="0">
	      <tr>
			<td width="100" align="center"><label>
			  <input type="button" name="Submit" value="保存"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveDept();"/>
	        </label></td>
			<td width="53"><label>
			  <input type="button" name="Submit2" value="取消"class="del_button1" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancelDept();"/>
			</label></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>
</form>
<script>
if ('${loginInfo.unitClass}'=='1'){
  document.getElementById('jymark').value='1';
}
changeParentDept();
</script>
</body>
</html>