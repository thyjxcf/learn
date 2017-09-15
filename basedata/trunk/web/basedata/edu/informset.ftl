<html>
<head>
<title>${webAppTitle}--教育局基本信息设置</title>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${request.contextPath}/static/js/validate.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/util.js"></script>
<script src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script src="${request.contextPath}/static/js/prototype.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script LANGUAGE="JavaScript">
var submitted = false;
//验证表单数据
function checkform(){
   
   if(!checkElement(form1.name,"单位名称")){
      return;
   }
   if(!checkElement(form1.eduCode,"单位机构代码")){
      return;
   }
   if(!checkElement(form1.principal,"联系人")){
      return;
   }
   if(!checkInteger(form1.postalcode,"邮政编码") ){
   	return ;
   }
   if(form1.postalcode.value.length!=0 && form1.postalcode.value.length!=6){
   	alert("邮政编码必须为6位");
	form1.postalcode.focus();
    return;
   }
   if(!checkOverLen(form1.principal,15,"联系人")){
      return;
   }
   if(!checkOverLen(form1.telephone,30,"联系电话")){
      return;
   }
    
   // var regexp = new RegExp("^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})+(\-[0-9]{1,4})?$");
   //  if(!regexp.test(form1.telephone.value)){
   //   alert("请输入正确的电话号码");
   //   form1.telephone.focus();
   //    return;
   //   }
   
   if(!checkOverLen(form1.fax,20,"传真电话")){
      return;
   }
   
   //  if(!regexp.test(form1.fax.value)){
   //   alert("请输入正确的传真电话");
   //   form1.telephone.focus();
   //    return;
   //   }
      
   if(!checkOverLen(form1.address,60,"地址")){
      return;
   }
   
   if(!checkOverLen(form1.director,16,"主管负责人")){
      return;
   }
  
  if(!checkOverLen(form1.statistician,16,"统计负责人")){
      return;
   }
  
   if(!checkOverLen(form1.manager,16,"局负责人")){
      return;
   }
   
   if(!checkEmail(form1.email)){
      return;
   }
   
    if (!checkNetroot(form1.homepage)){
   		return;
   }
   
   if (!checkNetroot(form1.domainUrl)){
   		return;
   }
  
   
   //自治区
   if(form1.isAutonomy.checked){
   		form1.isAutonomy.value="true"
   }
   //边疆
   if(form1.isFrontier.checked){
   		form1.isFrontier.value="true"
   }
   
   if (form1.useDomain.checked){
   		form1.useDomain.value = "true";
   }
   
   if(submitted) {
		return;
   } 
   submitted = true; 
   
    //保存
   form1.action="eduinfo-save.action";
      
   form1.submit();
  
}
//检索
function doDetail(){
	var	url	= "eduinfo.action?eduid="+underlingEduSelect.value;	
	window.location=url;	
	return true;
	//form1.eduid = underlingEduSelect.value;
	//window.location = "eduinfo.action";
}

function checkNetroot(elem) 
{ 	
	if(isBlank(elem.value)) return true;
	//var   regex=/^(http:\/\/)?([\w-]+\.)+[\w-]+(\/[\w-   \.\/?%&=]*)?$/i; 
	var result=elem.value.match(/^(http:\/\/)?([\w-]+\.)+[\w-]+(\/[\w-   \.\/?%&=]*)?$/i); 
	if(result==null) {
		alert("主页格式不正确！");    
   	    elem.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="YecSpec7" height="100%">	
	<tr>
		<td height="28" class="send_title">	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="120" align="right">
						请选择下属教育局：
					</td>
					<td  >
						<select name="underlingEduSelect" id="underlingEduSelect"
							onchange="doDetail()">
							<#--option value="${eduInfo.id}" selected >${eduInfo.name!}</option-->
							<#list underlingEduList as edu>
							<option value="${edu.id}"
								<#if eduid==edu.id>selected</#if>>${edu.name!}
							</option>
							</#list>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="100%" valign="top">
			<div class="content_div">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td >
						<form name="form1" action="" method="post">
							<input type="hidden" name="eduid" value="${eduid}">
							<input type="hidden" name="name" value="${eduInfo.name!}">
							<input type="hidden" name="unionid" value="${eduInfo.unionid!}">
							<input name="region" type="hidden" id="region" />
							<table width="700" border="0" cellspacing="4" cellpadding="0">
								<tr>
									<td align="right" width="114">
										单位名称：
									</td>
									<td colspan="3">
										<input name="name_" type="text" disabled="disabled" id="name_"
											value="${eduInfo.name?default('')?trim}" size="55" maxlength="150"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td align="right" width="114" >
										统一编号：
									</td>
									<td colspan="3">
										<input name="unionid_" type="text" disabled id="unionid_"
											value="${eduInfo.unionid?default('')}" size="15"
											maxlength="12" />
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										<span class="STYLE1">*</span>单位机构代码：
									</td>
									<td width="125">
										<input type="text" id="educode_" name="eduCode"
											value="${eduInfo.eduCode?default('')}" size="20" maxlength="10" />
									</td>
									<td align="right" width="146">
										<span class="STYLE1">*</span>联&nbsp;系&nbsp;人：
									</td>
									<td width="293">
										<input name="principal" type="text" class="input2"
											id="principal" value="${eduInfo.principal?default('')}"
											size="15" maxlength="15" />
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										联系电话：
									</td>
									<td>
										<input name="telephone" type="text" class="input2"
											id="telephone" value="${eduInfo.telephone?default('')?trim}"
											size="15" maxlength="20" />
									</td>
									<td align="right">
										<span class="STYLE1">*</span>邮政编码：
									</td>
									<td>
										<input name="postalcode" type="text" class="input2"
											id="postalcode" value="${eduInfo.postalcode?default('')?trim}"
											size="15" maxlength="6" />
									</td>
								</tr>
								<#--
								<tr>
									<td align="right" width="114">
										所在地行政区：
									</td>
									<td colspan="3">
										<input name="regionname" type="text" class="input2"
											id="regionname" value="${eduInfo.regionname?default('')}"
											size="50" maxlength="200" readonly />
										<input type="button" name="btn2" class="i" value=".."
											onclick="openwindow('${request.contextPath}/common/xtree/regiontree.action','regionname',300,400)">
									</td>
								</tr>
								-->

								<tr>
									<td align="right" width="114">
										地 址：
									</td>
									<td colspan="3">
										<input name="address" type="text" class="input2" id="address"
											value="${eduInfo.address?default('')?trim}" size="55"
											maxlength="60" />
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										是否贫困县：
									</td>
									<td height="30" colspan="3">
										<select id="nationpoverty" name="nationPoverty">
											${appsetting.getMcode("DM-PKX").getHtmlTag(eduInfo.nationPoverty?string?default('0'),false)}
										</select>	
									
										&nbsp;
										<input name="isAutonomy" type="checkbox" id="isAutonomy"
										<#if eduInfo.isAutonomy> checked </#if> />
									
										<label for="isAutonomy">是民族自治区</label>
									
										&nbsp;
										<input name="isFrontier" type="checkbox" id="isFrontier"
										<#if eduInfo.isFrontier> checked </#if> >
									
										<label for="isFrontier">是边疆县</label>
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										传真电话：
									</td>
									<td width="125">
										<input name="fax" type="text"  id="fax"
											value="${eduInfo.fax?default('')}" size="15"
											maxlength="20" />
									</td>
									<td align="right" width="146">
										电子信箱：
									</td>
									<td width="293">
										<input name="email" type="text" class="input2"
											id="email" value="${eduInfo.email?default('')}"
											size="15" maxlength="100" />
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										主管负责人：
									</td>
									<td width="125">
										<input name="director" type="text" id="director"
											value="${eduInfo.director?default('')}" size="15"
											maxlength="16" />
									</td>
									<td align="right" width="146">
										统计负责人：
									</td>
									<td width="293">
										<input name="statistician" type="text" class="input2"
											id="statistician" value="${eduInfo.statistician?default('')}"
											size="15" maxlength="16" />
									</td>
								</tr>
								<tr>
									<td align="right" width="114">
										主页地址：
									</td>
									<td width="125">
										<input name="homepage" type="text" id="homepage"
											value="${eduInfo.homepage?default('')}" size="20"
											maxlength="100" />
									</td>
									<td align="right" width="146">
										局负责人：
									</td>
									<td width="293">
										<input name="manager" type="text" class="input2"
											id="manager" value="${eduInfo.manager?default('')}"
											size="15" maxlength="16" />
									</td>
								</tr>
								<tr style="display:none">
									<td align="right" width="114">
										域名地址：
									</td>
									<td width="125" colspan="2">
										<input name="domainUrl" type="text" id="domainUrl"
											value="${eduInfo.domainUrl?default('')}" size="30"
											maxlength="100" />
									</td>
									<td align="left" width="146">
										<input name="useDomain" type="checkbox" id="useDomain"
										<#if eduInfo.isUseDomain> checked </#if> />									
										<label for="useDomain">是否采用域名地址</label>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
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
			<table width="243" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="126" align="left">
						<label>

							<div align="right">
								<input type="submit" name="Submit" value="保存"
									class="del_button1"
									onmouseover="this.className = 'del_button3';"
									onmousedown="this.className = 'del_button2';"
									onmouseout="this.className = 'del_button1';"
									onclick="checkform()" />
							</div>
						</label>
					</td>
					<td width="117">
						<label>
							<div align="center">
								<input type="submit" name="Submit23" value="取消"
									class="del_button1"
									onmouseover="this.className = 'del_button3';"
									onmousedown="this.className = 'del_button2';"
									onmouseout="this.className = 'del_button1';"
									onclick="location.href=location.href" />
							</div>
						</label>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
