<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--新增模块</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<#assign defaultId = "00000000000000000000000000000000">
</head>

<body style="overflow:visible" class="YecSpec">

<table width="100%" height="%" border="0" cellpadding="0" cellspacing="0" class="YecSpec">
  <tr> 
    <td height="28" class="title_bg1" style="padding-left:6px;">
	<strong><span class="white12"><#if favoriteId?default('') == "">加入收藏夹<#else>修改收藏夹</#if></span></strong></td>
  </tr>
  <tr height="90%"> 
    <td  valign="top"  class="win_bg" style="padding-top:10px;">
    <table width="440" border="0" align="center" cellpadding="3" cellspacing="0">
    <form name="form1" method="post">
    	<input type="hidden" name="subSystem" id="subSystem" value="${favorite.subSystem?default(0)}">
        <tr> 
          <td width="12%" align="right" nowrap class="font_blue" ><font color="red">*</font>位置序号：</td>
          <td class="blue">
			<input name="orderNum" type="text" class="input2" size="15" maxLength="3" value="${favorite.orderNum?default('')}">
			    <input type="hidden" id="id" name="id" value="${favorite.id?default('')}">
			    <input type="hidden" id="picUrl" name="picUrl" value="${favorite.picUrl?default('')}">
			    <input type="hidden" id="moduleId" name="moduleId" value="${favorite.moduleId?default('')}">
          </td>
          <td align="right" class="font_blue">&nbsp;</td>
          <td class="blue"><font class="font_blue">&nbsp;</font></td>
        </tr>
        <tr> 
          <td align="right" nowrap class="font_blue"><font color="red">*</font>名称：</td>
          <td class="blue"><font class="font_blue">
            <input name="moduleName" id="moduleName" maxLength="20" type="text" class="input2" size="15" value="${favorite.moduleName?default('')?trim}">
            </font></td>
          <td width="39%" align="right" class="font_blue">类别： </td>

          <td width="110" class="blue">
<#if (operationType == 2)>
			<label>自定义目录</label>
			<input type="hidden" name="type" id="type" value="2">
<#else>
	<#if (favorite.type == 3)>
		<input type="hidden" name="type" id="type" value="3">
		<label>外部链接</lable>
	<#else>
		<input type="hidden" name="type" id="type" value="1">
		<label>系统模块</lable>
	</#if>
</#if>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap class="font_blue" style=""><font color="red">*</font>所处目录：</td>
          <td colspan="3" class="blue">
				<select name="parentId" id="parentId" style="width:120px;" id="floderSelect">
					<option value="${defaultId}">根目录</option>
<#list favoriteFloderList as ele>
					<option value="${ele.id}" <#if (ele.id == favorite.parentId?default(nowId)?default(defaultId))>selected</#if>>${ele.moduleName}</option>
</#list>
				</select>
          </td>
        </tr>
<#if operationType != 2>
        <tr> 
          <td align="right" nowrap class="font_blue" style="padding-top:5px;"><font color="red">*</font><span name=
		  "spanUrl" id="spanUrl">链接地址：</span></td>
          <td colspan="3" class="blue" style="padding-top:5px;">
<#if operationType == 3>
	<input name="url" id="url" maxLength="200" type="text" class="input2" size="59" value="${favorite.url?default('')?trim}">
<#else>
	<input name="url_display" id="url_display" maxLength="200" type="text" class="input_readonly" size="59" value="${favorite.url?default('')?trim}">
	<input name="url" id="url" maxLength="200" type="hidden" class="input2" size="59" value="${favorite.url?default('')?trim}">	
</#if>          
            
            
            </td>
        </tr>
</#if>
        <tr> 
          <td align="right" nowrap class="font_blue" style="padding-top:5px;" valign="top">描述：</td>
          <td colspan="3" class="blue" style="padding-top:5px;"><textarea name="moduleDescription" id="moduleDescription" cols="57" class="input_search1" style="height:190px;" >${favorite.moduleDescription?default('')?trim}</textarea></td>
        </tr>
        </form>
      </table></td>
  </tr>

  <tr>
  <td id="actionTip">&nbsp;</td>
  </tr>
  <tr height="10%"> 
    <td align="center" valign="top" class="window_bg"> <table border="0" cellspacing="0" cellpadding="8">
        <tr> 
          <td width="100" align="center"><label>
		  <input type="button" name="Submit" value="保存"class="del_button1" tabindex="16" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveInfo();"/>
        </label></td>
		<td width="53"><label>
		  <input type="button" name="Submit2" value="<#if operationType == 4>关闭<#else>取消</#if>"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancelInsert();"/>
		</label></td>
        </tr>
      </table></td>
  </tr>
</table>
<script>

function checkOverLen(elem,maxlen,field){
	var ResultStr = "";
	Temp=elem.value.split(" "); //双引号之间是个空格；
	for(i = 0; i < Temp.length; i++){
	   ResultStr +=Temp[i];
	}
	var len;
	var i;
	len = 0;
	var val = ResultStr;
	var maxlength = parseInt(maxlen);
	var length = val.length;
	for (i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}
	if(len>maxlength){
		addFieldError(elem,field+"长度超过范围,允许范围为0-"+maxlength+"个字符（一个汉字占2个字符）");
		elem.focus();
		return false;
	}
	return true;
}

function saveInfo(){	
	if(validateform()){
		var buffalo = new Buffalo("");
		buffalo.async=false;
		var moduleName = document.getElementById("moduleName").value;
		var id = document.getElementById("id").value;		
		var type = document.getElementById("type").value;
		var parentId = document.getElementById("parentId").value;
		var subSystem = parseInt(document.getElementById("subSystem").value);
		buffalo.remoteActionCall("favoriteBurlap.action", "isExistsName", 
		    [id, moduleName,type, parentId,subSystem], function(reply) {
			if (reply.getResult() == true){
				<!-- 2007-04-10 zhaosf  新增时如果存在相同名称，则提示用户是否覆盖-->	
				if(null == id || "" == id){
					if(window.confirm("已经存在名称为“" + moduleName + "”的收藏内容。是否覆盖?")){
						updateData();
					}else{
						return false;
					}
				}else{
					addActionError("已经存在名称为“" + moduleName + "”的收藏内容，请修改！" );
					return false;
				}
			}
			else{
				updateData();
			}
		});
	}	
}

function updateData(){
	var saveForm = document.getElementById("form1");
	saveForm.action = "favoriteSave.action?listType=1&operationType=${operationType}";	
	saveForm.submit();	
}

function validateform(){
	var orderNum = document.getElementById("orderNum");
	var moduleName = document.getElementById("moduleName");
	var url;
	<#if operationType != 2>
		url = document.getElementById("url");
	</#if>

	if(orderNum.value.length == 0){
		addFieldError("orderNum","位置序号不能为空，请输入！");
		orderNum.focus();
		return false;
	}
	if(trim(moduleName.value) == ""){
		addFieldError("moduleName","名称不能为空，请输入！");
		moduleName.focus();
		return false;
	}
	<#if operationType != 2>
	if(url.value.length == 0){
		url.focus();
		addFieldError("url","链接不能为空，请输入！");
		return false;
	}	
	</#if>
	if(!checkOverLen($("moduleDescription"),255,"描述")) return false;	
	return true;
}

function cancelInsert(){
	if(${operationType?default(-1)} == 4){
		window.close();
	}
	else{
		window.location.href="favorite.action?nowId=${favorite.parentId?default(nowId)?default(defaultId)}&listType=1";
	}
}
function changeApp(){
	var appType = document.getElementById("type").value;
	if (appType == 0){
		document.getElementById("spanUrl").innerHTML = "模块编号：";
	}else{
		document.getElementById("spanUrl").innerHTML = "链接：";
	}
}
</script>
</body>
</html>
