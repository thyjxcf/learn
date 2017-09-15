<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--应用平台注册信息</title>   
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script language="JavaScript">
 var endPoint="${request.contextPath}/system/appregistry/";	
var buffalo = new Buffalo(endPoint);
	
<!-- 验证form -->
function checkForm(){
    var frm = document.form1;							
    frm.submit();
}	

<!-- 取消 -->
function checkCancel(){
	document.location.href = "appRegistryAdmin.action";
}

<!-- 改变应用名称 -->
function onChangeSysid(){
	var sysCom = document.getElementById("sysid"); 
	var sysid = sysCom.value;
	var appcode = sysid + "-${dto.unitid?default("")}";
	document.getElementById("appcodeShow").value = appcode;
	document.getElementById("appcode").value = appcode;
	document.getElementById("appname").value = sysCom.options[sysCom.selectedIndex].text;
	getApplication(sysid);
}

<!-- 取应用信息 -->
function getApplication(sysid){	
	//取url样例
	buffalo.remoteActionCall("appRegistryAdmin-remote.action","remoteGetApplication",[sysid], function(reply) {
		var appDto = reply.getResult();
		var td = document.getElementById("urlExampleTd");
		if(appDto != null){
			td.innerHTML = appDto.urlExample;	
		}
	});
}
</script>
</head>  
<body>
<form name="form1" method="post" action="appRegistryAdmin-save.action">
<#-- 分页数据 -->
<input type="hidden" name="ec_p" value="${ec_p?default("")}" />
<input type="hidden" name="ec_crd" value="${ec_crd?default("")}" />

<input type="hidden" name="id" value="${dto.id?default("")}">
<input type="hidden" name="unitid" value="${dto.unitid?default("")}">
<input type="hidden" name="displayorder" value="${dto.displayorder!}">
<input type="hidden" name="appintid" value="${dto.appintid!}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" class="YecSpec">
  <tr>
    <td height="100%" valign="top">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr>
          <td height="28" class="send_title"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="send_titlefont">应用注册信息</td>              
            </tr>
          </table></td>
        </tr>
	    <tr>
		  <td>		
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
				<td bgcolor="#FCFFFF" id="content" colspan="2">
				  <table width="100%" border="0" cellpadding="1" cellspacing="1">
				    <tr>
					  <td class="send_font_no_width" width="20%"><font color="red">*</font>是否启用：</td>
					  <td class="send_padding_no_width"><input name="isusing" type="checkbox" value="1" <#if dto.isusing?default("") == "1">checked</#if>></td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red">*</font>是否同步：</td>
					  <td class="send_padding_no_width"><input name="issync" type="checkbox" value="1" <#if dto.issync?default("") == "1">checked</#if>></td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red">*</font>是否供下属单位使用：</td>
					  <td class="send_padding_no_width"><input name="underlingUnitUse" type="checkbox" value="1" <#if dto.underlingUnitUse?default("") == "1">checked</#if>></td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red">*</font>应用名称：</td>
					  <td class="send_padding_no_width"><select name="sysid" style="width:153px;" onchange="onChangeSysid();">
					  		<option value="">--请选择--</option>
				        	<#list appList as x>
							 	<option value="${x[0]}" <#if x[0] == ((dto.sysid)?default(""))>selected</#if>>
									${x[1]}
								</option>
					         </#list>
					      </select>
					  </td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red">*</font>注册名称：</td>
					  <td class="send_padding_no_width"><input name="appname" class="input" size="50" maxlength="100" value="${dto.appname?default('')}"></td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red">*</font>注&nbsp;册&nbsp;码：</td>
					  <td class="send_padding_no_width"><input name="appcodeShow" class="input" readonly size="50" maxlength="50" value="${dto.appcode?default('')}">
					  						   <input name="appcode" type="hidden" size="50" maxlength="50" value="${dto.appcode?default('')}"></td>
					</tr>
					<tr>
					  <td class="send_font_no_width"><font color="red"></font>验&nbsp;证&nbsp;码：</td>
					  <td class="send_padding_no_width"><input name="verifyKey" class="input" readonly size="50" maxlength="50" value="${dto.verifyKey?default('')}"></td>
					</tr>					
					<tr> 
					  <td class="send_font_no_width"><font color="red">*</font>应用主页：</td>
					  <td class="send_padding_no_width" ><input name="url" class="input" value="${dto.url?default("")}" maxlength="255" size="50"></td>
					</tr>
					<tr> 
					  <td class="send_font_no_width">主页样例：</td>
					  <td class="send_padding_no_width" id="urlExampleTd">${dto.urlExample?default("")}</td>
					</tr>
				  </table>
				</td>
			  </tr>
			  <tr><td height="20">&nbsp;</td></tr>
			   <tr>
			      <td width="50"></td>
                  <td bgcolor="#EAEDFE" class="padding_top">
                    <table width="600" height="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="5"><img src="${request.contextPath}/static/images/frame/tl_tips.jpg" width="5" height="5" /></td>
                        <td height="5" background="${request.contextPath}/static/images/frame/t_bg.jpg"></td>
                        <td width="5"><img src="${request.contextPath}/static/images/frame/rt_tips.jpg" width="5" height="5" /></td>
                      </tr>
                      <tr>
                        <td width="5" background="${request.contextPath}/static/images/frame/l_bg.jpg"></td>
                        <td valign="top" class="right_titlebg">说明：<br />
                          1、是否同步：如果注册的多个应用系统对应同一个数据库，只需同步其中一个应用系统即可；<br />
                          2、注 册 码：与应用系统配置中的注册码相对应；<br />
                          3、验 证 码：与应用系统配置中的验证码相对应；<br />
                          4、应用主页：应用系统的根目录地址，如http://127.0.0.1:8080/webroot；<br /></td>
                        <td width="5" background="${request.contextPath}/static/images/frame/r_bg.jpg"></td>
                      </tr>
                      <tr>
                        <td width="5"><img src="${request.contextPath}/static/images/frame/bl_tips.jpg" width="5" height="5" /></td>
                        <td height="5" background="${request.contextPath}/static/images/frame/b_bg.jpg"></td>
                        <td width="5"><img src="${request.contextPath}/static/images/frame/rb_tips.jpg" width="5" height="5" /></td>
                      </tr>
                  </table></td>
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
		  <td width="100" align="center">
			<label><input type="button" value="保存" class="del_button1" onmouseover="this.className='del_button3';"
				onmousedown="this.className='del_button2';" onmouseout="this.className='del_button1';" onClick="checkForm();"/> </label>
		  </td>
		  <td width="53">
			<label><input type="button" value="取消" class="del_button1" onmouseover="this.className='del_button3';"
			    onmousedown="this.className='del_button2';" onmouseout="this.className='del_button1';"  onclick="checkCancel();"/></label>
		  </td>
		</tr>
	  </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>
