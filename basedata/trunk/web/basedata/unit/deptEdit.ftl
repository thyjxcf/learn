<html>
<head>
<title>${webAppTitle}--部门编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<SCRIPT language=javascript src="${request.contextPath}/static/js/validate.js" type=text/javascript></SCRIPT>
<script>
var isSubmited = false;
function saveDept(){
	if(isSubmited) return;
	
	//表单验证
	if(formValidate()){
		var f=document.deptForm;
        <#if (loginInfo.unitClass==1)||(hasSubDept==true) || dept.getDeptname()?default('').equals("学校管理员组")>
			f.action="${request.contextPath}/basedata/unit/deptAdmin-update.action?jymark=${dept.jymark?default('')}&&modID=${modID?default('')}";	
        <#else>
			f.action="deptAdmin-update.action?modID=${modID?default('')}";		
		</#if>
		//alert(f.action);
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
	window.location.href="deptAdmin-list.action?modID=${modID?default('')}&&deptId=${dept.parentid?default('0000000-0000-0000-0000-000000000000')}&&unitId=${unitId?default('0000000-0000-0000-0000-000000000000')}";
}
function chooseParentDept(event){

var elem = Event.element(event);
var offset = elem.positionedOffset();
 
var url = "${request.contextPath}/common/xtree/depttree.action?unitId=${unitId?default('')}";
ymPrompt.win({message:url,width:200,height:350,title:'部门选择',iframe:true,winPos:[offset.left+elem.getWidth(),offset.top],handler:handler,btn:[['确定','ok'],['取消','cancel']],showMask:false,dragOut:false});

}
function changeParentDept(){
	var parentdeptname=document.getElementById('parentName');
	
	if(parentdeptname.value==''){
		parentdeptname.value='${topDeptName?default('')?trim}';
	}	
}
function handler(tp){
	if(tp=='ok'){
		deptChoose();
	}
	if(tp=='cancel'){
		ymPrompt.close();
	}
}
function deptChoose(){
	document.getElementById('parentid').value=document.getElementById('choose_deptId').value;
	document.getElementById('parentName').value=document.getElementById('choose_deptName').value;
}
</script>
</head>
<body>
<form name="deptForm" method="POST">
<input name="unitId" type="hidden" value="${dept.getUnitId()?default('00000000000000000000000000000000')}">
<input name="id" type="hidden" value="${dept.id?default('')}">
<table align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>		
	<td valign="top">
	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tr>
		  <td height="100%" valign="top">
		  	<table  width="100%"  border="0" cellpadding="0" cellspacing="0">
		  	  <tr>
		  	  	<td>
		  	  	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  	  	  	<tr><td class="send_font_title">部门编辑</td></tr>
		  	  	  	<tr><td bgcolor="#FCFFFF">
		  	  	  	  <table width="100%" height="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width" width="100"><font color="red">*</font>部门名称：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="deptname" id="deptname" type="text" value="${dept.getDeptname()?default('')?trim}"
		  	  	  	  	  	<#if dept.getDeptname()?default('').equals("学校管理员组") && (loginInfo.unitClass==2)> class="input_readonly"<#else> class="input"</#if> ></td>
		  	  	  	  	  <td class="send_font_no_width">部门编号：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="deptCode" id="deptCode" type="text" class="input" value="${dept.getDeptCode()?default('')?trim}"></td>		  	  	  	  	  
  	  	  				</tr>
  	  	  				<tr>
  	  	  				  <td class="send_font_no_width"><font color="red">*</font>上级部门：</td>
  	  	  				  <td class="send_padding_no_width">
									<input name="choose_deptId" id="choose_groupId" type="hidden" value="">
									<input name="choose_deptName" id="choose_groupName" type="hidden" value="">
  	  	  				  	<input name="preParentId" type="hidden" value="${dept.preParentId?default('')}">	  	  				  	
  	  	  				  	<input name="parentid" type="hidden" id="parentid" value="${dept.parentid?default('')}">	  	  	  				
  	  	  				  	<input name="parentName" type="text" class="input_readonly" style="width:128px;" readonly="true" value="${dept.parentName?default('')?trim}">
	  	  	  				<#if !dept.getDeptname()?default('').equals("学校管理员组") || (loginInfo.unitClass==1)> 
	  	  	  					<img src="${request.contextPath}/static/images/tree_icon.gif" width="15" height="14" onclick="chooseParentDept(event);" style="cursor:pointer;display:none">
	  	  	  				</#if>
  	  	  				  </td>
  	  	  				  <td class="send_font_no_width">教研组标识：</td>
  	  	  				  <td class="send_padding_no_width">
  	  	  				  	<select name="jymark" id="jymark" <#if (loginInfo.unitClass==1)> disabled </#if> <#if (hasSubDept==true)> disabled </#if>  
  	  	  				  		<#if dept.getDeptname()?default('').equals("学校管理员组")> disabled </#if> style="width:150px">
  	  	  				  	${appsetting.getMcode("DM-JYBZ").getHtmlTag(dept.jymark?default('')?string)}  	  	  					
  	  	  				  	</select>
  	  	  				  </td>  	  	  				    
  	  	  				</tr>
  	  	  				<tr>  	  	  	
  	  	  				  <td class="send_font_no_width"><font color="red">*</font>部门排序号：</td>
  	  	  				  <td class="send_padding_no_width"><input name="orderid" id="orderid" type="text" class="input" value="${dept.getOrderid()?default('')}"></td>
  	  	  				  <td class="send_font_no_width">负责人：</td>
  	  	  				  <td class="send_padding_no_width"><select name="principan" id="principan" style="width:150px"><option value="">--请选择--</option>
  	  	  					<#list userList as user>
  	  	  	  				<option value="${user.getId()?default('')}" <#if dept.principan?exists&&dept.principan==user.id>selected</#if>>${user.getName()?default('')?trim}<#if user.realname?exists>(${user.realname})</#if></option>
  	  	  					</#list>
  	  	  					</select>
  	  	  				  </td>
  	  	  				</tr>
  	  	 				<tr>
  	  	  				  <td class="send_font_no_width">电话号码：</td>
  	  	  				  <td class="send_padding_no_width"><input name="depttel" id="depttel" type="text" class="input" value="${dept.getDepttel()?default('')?trim}" maxlength="20"></td>
  	  	  				  <td class="send_font_no_width" >分管领导：</td>
  	  	  				  <td class="send_padding_no_width"><select name="leaderId" id="leaderId" style="width:150px"><option value="">--请选择--</option>
  	  	  					<#list userList as user>
  	  	  	  				<option value="${user.getId()?default('')}" <#if dept.leaderId?exists&&dept.leaderId==user.id>selected</#if>>${user.getName()?default('')?trim}<#if user.realname?exists>(${user.realname})</#if></option>
  	  	  					</#list>
  	  	  					</select></td>
  	  	  				</tr>
  	  	  				<tr>
  	  	  				  <td class="send_font_no_width" valign="top">描述信息：</td><td class="send_padding_no_width"colspan="3"><textarea name="about" id="about" class="input" cols="69" rows="3">${dept.getAbout()?default('')?trim}</textarea></td>
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
</script>
</body>
</html>