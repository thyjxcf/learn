<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<html>
<head>
<title>${webAppTitle}--单位新增</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script language="javascript">
function changetype(elem){
	clearMessages();
	
	var regiondiv=document.getElementById('regiondiv');
	var classinput=document.getElementById('unitclass');
	if(elem.value=='${unittype_subedu}'){
		classinput.value='${unitclass_edu}';//unitclass设置为教育局
		regiondiv.style.display='';//显示下属教育局的信息
		
		document.getElementById('unitusetype_sel').value = '01';  //只有当单位类型为“教育局”时，才设置单位使用类别为“教育局”，其它情况下设置为“请选择”
		document.getElementById('unitusetype_sel').disabled = true;
		document.getElementById('unitusetype').value = '01'; 
	}
	else if(elem.value=='${unitclass_nonedu}'){
		classinput.value='${unitclass_edu}';//unitclass设置为非教育局单位
		regiondiv.style.display='none';	
		document.getElementById('unionid').value='';//对于学校单位，清除unionid	
		
		document.getElementById('unitusetype_sel').value = '';
		document.getElementById('unitusetype_sel').disabled = false;
		document.getElementById('unitusetype').value = ''; 
	}
	else{	
		regiondiv.style.display='none';
		classinput.value='${unitclass_school}';//unitclass设置为学校
		document.getElementById('unionid').value='';//对于学校单位，清除unionid	
		
		document.getElementById('unitusetype_sel').value = '';
		document.getElementById('unitusetype_sel').disabled = false;
		document.getElementById('unitusetype').value = ''; 
	}
	if(document.getElementById('parentid').value!=''){
		parentclick(document.getElementById('parentid'));
	}
}
function parentclick(elem){
	var unittype=document.getElementById('unittype');
	var unitid=elem.value;
	var provinceSelect=document.getElementById('province');
		provinceSelect.options.length=0;
		provinceSelect.options[0]=new Option('--请选择--');
		provinceSelect.disabled='true';
	var citySelect=document.getElementById('city');
		citySelect.options.length=0;
		citySelect.options[0]=new Option('--请选择--');
		citySelect.disabled='true';
	var sectionSelect=document.getElementById('section');
		sectionSelect.options.length=0;
		sectionSelect.options[0]=new Option('--请选择--');
		sectionSelect.disabled='true';
	var countryspan=document.getElementById('countryspan');
	var countrycheck=document.getElementById('country');		
	
	var buffalo=new Buffalo('');
	if(unittype.value=='${unittype_subedu}'){
	  if(unitid!=''){	
		buffalo.async=false;
		buffalo.remoteActionCall('unitAdmin-remoteUnitService.action','getRegion',[unitid],function(reply){
			var result=reply.getResult();
			if(result!=null){				
				var obj;
				for(var i=0;i<result.length-3;i++){
					if(i==0){
						obj=provinceSelect;
					}
					else if(i==1){
						obj=citySelect;
					}
					else if(i==2){
						obj=sectionSelect;						
					}
					obj.options.length=0;					
				  	  if(result[i]!=null){				  	  	
						if(result[i].length>1){
							// obj.options[0]=new Option('--请选择--','');
							obj.disabled=false;							
							for(var j=0;j<result[i].length;j++){
								obj.options[j]=new Option(result[i][j].regionName,result[i][j].regionCode);
								if(result[i][j].id=='${unionid?default('-1')}'){
									obj.options.selectedIndex=j;
									break;
								}
							}
						}
						else{
							for(var j=0;j<result[i].length;j++){						
								obj.options[j]=new Option(result[i][j].regionName,result[i][j].regionCode);
								
							}
							if(i==2){
								countryspan.style.display='';
								countrycheck.checked=true;
								countrycheck.disabled=true;
							}
						}
				  	  }										
				}
				document.getElementById('orderid').value=result[3][0];	
				document.getElementById('regionlevel').value=result[4][0];
				
				if(result[5][0]==true){
					villiageEdu();
				}			
			}
		});
	  }
	}
	else {
		buffalo.remoteActionCall("unitAdmin-remoteUnitService.action","getOrder",[unitid],function(reply){
			var result=reply.getResult();
			if(result!=null){
				document.getElementById('orderid').value=result[0];
				document.getElementById('regionlevel').value=result[1];
				
				if(result[2]==true){
					villiageEdu();
				}
			}			
		});
		
	}
}
//乡镇级教育局过滤单位类型中的下属教育局
function villiageEdu(){
	var unittype=document.getElementById('unittype');
	var selectedIndex=unittype.selectedIndex;
	
	var unitTypeArray=new Array();
	<#if unitClassList?exists>
		<#list unitClassList as uc>
			unitTypeArray[${uc_index}]=new Array(2);
			unitTypeArray[${uc_index}][0]='${uc.thisId?default('')}';
			unitTypeArray[${uc_index}][1]='${uc.content?default('')}';
		</#list>
	</#if>
	unittype.options.length=0;
	unittype.options[0]=new Option('--请选择--','');
	var j=1;
	for(var i=0;i<unitTypeArray.length;i++){
		if(unitTypeArray[i][1]!='下属教育局'){
			unittype.options[j]=new Option(unitTypeArray[i][1],unitTypeArray[i][0]);
			j++;
		}
	}
	unittype.selectedIndex=selectedIndex;
	
	document.getElementById('regiondiv').style.display='none';
}
//保存信息
function saveInfo(){
	var saveForm = document.getElementById("saveForm");	
	getUnionid();
	if(validateform()){
		//alert("来了！");
		saveForm.action = "unitAdmin-save.action?modID=${modID?default('')}";
		saveForm.submit();
	}
	else{
		document.getElementById('userdtopassword').value='';
		document.getElementById('confirmPassword').value='';
	}	
}
function validateform(){	
	if(trim(document.getElementById('name').value)==''){
		addFieldError('name','请输入单位名称');
		return false;
	}
	else if(document.getElementById('unittype').value==''){
		addFieldError('unittype','请选择单位类型');
		return false;
	}
	else if(document.getElementById('parentid').value==''){
		addFieldError('parentid','请选择上级单位');
		return false;
	}
	else if(trim(document.getElementById('userDtoname').value)==''){
		addFieldError('userDtoname','请输入单位管理员账号');
		return false;		
	}	
	else if(!checkInteger(document.getElementById('orderid'),'排序编号')){
		return false;
	}
	else if(document.getElementById('userDtopassword').value!=document.getElementById('confirmPassword').value){
		addFieldError('confirmPassword','请确认单位管理员用户的登录密码填写一致');	
		addFieldError('userDtopassword','请确认单位管理员用户的登录密码填写一致');
		return false;
	}
	
	var unitType = document.getElementById("unittype").value;
	if(unitType != '${unittype_subedu}' && unitType !='${unitclass_nonedu}'){
		if(document.getElementById('unitusetype').value == "01"){
			addFieldError('unitusetype_sel','单位使用类别不能为教育局');
			return false;
		}
	}
	return true;
}
function cancelInsert(){
	window.location.href="unitAdmin.action?modID=${modID?default('')}&&unitId=${unitDto.parentid?default("")}";
}
function getUnionid(){
	var provinceSelect=document.getElementById('province');
	var citySelect=document.getElementById('city');
	var sectionSelect=document.getElementById('section');
	var countrycheck=document.getElementById('country');	
	
	var unionidinput=document.getElementById('unionid');	
	
	if(countrycheck.checked){//如果是新增乡镇级教育局则也需要生成unionid
		unionidinput.value='';
	}
	else if(sectionSelect.value!=''){
		unionidinput.value=sectionSelect.value;
	}
	else if(citySelect.value!=''){
		unionidinput.value=citySelect.value;
	}
	else if(provinceSelect.value!=''){
		unionidinput.value=provinceSelect.value;
	}
	else if(document.getElementById('unitclass')=='${unitclass_edu}'){
		alert('请选择所在地行政区');
		return false;
	}
	return true;
}
function sysUserLoginName(){
	if(document.getElementById('name').value!=''){
		document.getElementById('userDtoname').value=document.getElementById('name').value.toInitial()+'_admin';
	}
}
function checkUserName(){
	var name=document.getElementById('userdtoname').value;
	if(trim(name)==''){
		addFieldError('userdtoname','请输入账号');
		return ;
	}
	
	var buffalo=new Buffalo('${request.contextPath}/basedata/user/');
	buffalo.remoteActionCall("userAdmin-service.action","validateUserNameAvaliable",[name],function(reply){
		var result=reply.getResult();		
		if(result != ""){
			addFieldError('userdtoname',result);
		}
		else{
			document.getElementById('userdtoname').removeAttribute('fielderror');
			addFieldSuccess(document.getElementById('userdtoname'),'恭喜您,'+name+'账号尚可使用');
		}
	});
	return ;
}

function unitusetypeChange(elem){
	document.getElementById("unitusetype").value = elem.value;
}
</script>
</head>
<body>
<form name="saveForm" id="saveForm" method="POST" action="" >
<input name="mark" type="hidden" value="${mark?default('')}"/>
<input name="regcode" type="hidden" value="${regcode?default('')}"/>
<#--
<input name="createdate" type="hidden" value='<#if createdate?exists>${createdate?string('yyyy-MM-dd')}</#if>'/>
-->

<input name="regionlevel" type="hidden" value="${regionlevel?default('')}"/>
<input name="usetype" type="hidden" value="${usetype?default('')}"/>
<input name="authorized" type="hidden" value="${authorized?default('')}"/>
<input name="unionid" type="hidden" value="${unionid?default('')}"/>
<input name="userDto.orderid" type="hidden" value="${userDto.orderid?default('')}"/>
<input name="userDto.mark" type="hidden" value="${userDto.mark?default('')}"/>
<input name="userDto.type" type="hidden" value="${userDto.type?default('')}"/>
<input name="userDto.deptid" type="hidden" value="${userDto.deptid?default('')}"/>
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
		  	  	  	<tr><td class="send_font_title">新增下级单位</td></tr>
		  	  	  	<tr><td bgcolor="#FCFFFF">
		  	  	  	  <table width="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	<tr>
		  	  	  	  	  <td class="send_font_no_width" width="100"><font color="red">*</font>单位名称：</td>
		  	  	  	  	  <td class="send_padding_no_width"><input name="name" id="name" type="input" class="input" tabindex="1" value="${unitDto.name?default('')}" onkeyup="sysUserLoginName();" fieldtip="请输入单位名称，字符数不超过150个(一个中文算2个字符)" maxlength="75"></td>
		  	  	  	  	  <td class="send_font_no_width" width="100"><font color="red">*</font>单位类型：</td>
						  <td class="send_padding_no_width">
					 		
					 		<select name="unittype" id="unittype" style="width:150px;" tabindex="3" onchange="changetype(this);">
					 		  <option value=''>--请选择--</option>		
					 		  <#list unitClassList! as uc>
					 		  	<option value='${uc.thisId?default('')}' <#if unittype?exists&&uc.thisId==unittype?string>selected</#if>>${uc.content?default('')}</option>
					 		  </#list>
				  	  		</select>				  	  		
				  	  		<input name="unitclass" id="unitclass" type="hidden" value="${unitclass?default('')}">
						  </td>		  	  	  	  	  
		  	  	  	  	</tr>
		  	  	  	  	<tr>		  	  	  	 
		  	  	  	  		<input type="hidden" name="etohSchoolId" value="${unitDto.etohSchoolId?default("")}"> 	  
						  <td class="send_font_no_width" width="100"><font color="red">*</font>上级单位：</td>
						  <td class="send_padding_no_width"><select name="parentid" id="parentid" style="width:150px;" tabindex="9" onchange="parentclick(this);">
						  	  <#list listOfUnitDto! as unit>
						  	  	<option value='${unit.id?default('')}' 
						  	  	<#if !parentid?exists>
						  	  	  <#if unit.id?default('') == unitId?default("")>selected</#if>
						  	  	<#else>
						  	  	  <#if parentid.equals(unit.id)>selected</#if>
						  	  	</#if>>${unit.name?default('')}</option>
						  	  </#list>
				  	  		</select>
						  </td>
						  <td class="send_font_no_width" width="100">单位使用类别：</td>
						  <td class="send_padding_no_width"><select  name="unitusetype_sel" id="unitusetype_sel" style="width:150px;" tabindex="9" onchange="unitusetypeChange(this);">
						  		${appsetting.getMcode("DM-UNITUSETYPE").getHtmlTag(unitDto.getUnitusetype()?default(''))}
				  	  		</select>
				  	  		<input type="hidden" name="unitusetype" id="unitusetype" value="">
						  </td>								  					  					  
						</tr>						
		  	  	  	  	<tr style="display:none">
		  	  	  	  	  <td class="send_font_no_width">排序编号：</td>
						  <td class="send_padding_no_width"><input name="orderid" id="orderid" type="text" class="input" tabindex="8" value="${unitDto.orderid?default('')}" fieldtip="请输入排序编号，长度不超过4位"></td>	  	  	  	  	  
		  	  	  	  	  <td colspan="2" class="send_font_no_width">&nbsp;</td>		  
						</tr>
						<tr>
						  <td class="send_font_no_width">所在地行政区：</td>					
						  <td colspan="3" class="send_padding_no_width">
						  	<table id="regiondiv" style="display:none;" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
						  	  <tr>
						  	  	<td style="color:#29248A">
							    &nbsp;省：<select name="province" style="width:150px;padding-right:10px;" disabled>
							      <option value="">--请选择--</option>
							    </select>
							    &nbsp;市：<select name="city" style="width:150px;padding-right:10px;" disabled>
							      <option value="">--请选择--</option>
							    </select>
							    &nbsp;区/县：<select name="section" style="width:150px;padding-right:10px;" disabled>
							      <option value="">--请选择--</option>
							    </select>
						    	<span id="countryspan" style="display:none"><input name="country" type="checkbox" value="${unitCountry}" ><label for="country">乡镇教育局</label></span>
						    	</td>
						      </tr>
						    </table>
						  </td>						  					  
						</tr>											
		  	  	  	  </table>
		  	  	  	</td></tr>
		  	  	  </table>
		  	  	</td>
		  	  </tr>
		  	  <tr>
		  	  	<td>
		  	  	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		  	  	  	<tr>
		  	  	  	  <td><img src="${request.contextPath}/static/images/head_icon2.gif" width="17" height="17" /><font class="send_font_title">同步新增该单位管理员</font>(管理员账号,一经创建即不能删除)</td>		  	  	  	  
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td bgcolor="#FCFFFF">
		  	  	  	  	<table width="100%" border="0" cellpadding="1" cellspacing="1">
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_font_no_width" width="100"><font color="red">*</font>账号：</td>
		  	  	  	  	  	<td class="send_padding_no_width">
		  	  	  	  	  	  <input name="userDto.name" id="userDtoname" type="text" class="input" tabindex="12" value="${userDto.name?default('')}" fieldtip="${userNameFieldTip!}">
		  	  	  	  	  	  <img src="${request.contextPath}/static/images/toolmenu_tips2.gif" border="0" style="cursor:pointer;" onclick="checkUserName();" alt="验证该账号是否可用">
		  	  	  	  	  	</td>
		  	  	  	  	  	<td class="send_padding_no_width" colspan="2"></td>		  	  	  	  	  	  
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_font_no_width"><font color="red">*</font>登录密码：</td>
		  	  	  	  	  	<td class="send_padding_no_width">
		  	  	  	  	  	  <input name="userDto.password" id="userDtopassword" type="password" tabindex="13" class="input" style="width:150px" onkeyup="changePwdRank(this);" fieldtip="${userPasswordFieldTip!}">
		  	  	  	  	  	</td>
		  	  	  	  	  	<td class="send_font_no_width" width="100">密码安全度：</td>
		  	  	  	  	  	<td class="send_padding_no_width">
		  	  	  	  	  	  <DIV id=PasswordCheck style="BACKGROUND-POSITION: 0px 0px; BACKGROUND-IMAGE: url(${request.contextPath}/static/images/password_check_bk.gif); WIDTH: 150px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 15px;" />
		  	  	  	  	  	</td>
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_font_no_width"><font color="red">*</font>密码确认：</td>
		  	  	  	  	  	<td class="send_padding_no_width"><input name="userDto.confirmPassword" id="confirmPassword" type="password" tabindex="14" class="input" style="width:150px"></td>
		  	  	  	  	  	<td class="send_padding_no_width" colspan="2"></td>
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_font_no_width">电子邮件地址：</td>
		  	  	  	  	  	<td class="send_padding_no_width" colspan="3"><input name="userDto.email" id="userdtoemail" type="text" class="input" size="80" tabindex="15" value="${userDto.email?default('')}" fieldtip="请输入用户联系电子邮件地址"></td>		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	</table>
		  	  	  	  </td>
		  	  	  	</tr>
		  	  	  	<!--start smscenter-->
		  	  	  	<#if useSmsCenter>
		  	  	  	<tr>
		  	  	  	  <td><img src="${request.contextPath}/static/images/icon_smsadmin.gif" width="17" height="17" /><font class="send_font_title">同步在短信接入平台里创建账号</font></td>		  	  	  	  
		  	  	  	</tr>
		  	  	  	<tr>
		  	  	  	  <td bgcolor="#FCFFFF"><input type="hidden" name="syncSmsCenter" value="0">
		  	  	  	  	<table width="100%" border="0" cellpadding="1" cellspacing="1">		  	  	  	  	  
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter1" name="isSmsCenter" value="1" onclick="syncSmsCenter.value=this.value;" ><label for="isSmsCenter1">是，同步创建账号</label></td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	  <tr>
		  	  	  	  	  	<td class="send_padding_no_width">&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="isSmsCenter2" name="isSmsCenter" value="0" onclick="syncSmsCenter.value=this.value;" checked><label for="isSmsCenter2">否，不同步创建账号</label></td>		  	  	  	  	  		  	  	  	  	  	
		  	  	  	  	  </tr>
		  	  	  	  	</table>
		  	  	  	  </td>
		  	  	  	</tr>
		  	  	  	</#if>
		  	  	  	<!--end smscenter-->
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
			  <input type="button" name="Submit" value="保存"class="del_button1" tabindex="16" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveInfo();"/>
	        </label></td>
			<td width="53"><label>
			  <input type="button" name="Submit2" value="取消"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancelInsert();"/>
			</label></td>
		  </tr>
		</table>
	</td>
  </tr>
</table>
</form>
<script>
changetype(document.getElementById('unittype'));
</script>
</body>
</html>