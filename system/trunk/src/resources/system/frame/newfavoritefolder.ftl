<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${webAppTitle}--新增目录</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/calendarDlg.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<#assign defaultId = "00000000000000000000000000000000">
</head>

<body style="overflow:visible">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="YecSpec">
  <tr> 
    <td height="28" class="title_bg1" style="padding-left:6px;">
	<img src="/eissweb/images/dot2.gif" width="10" height="11" hspace="3" align="absmiddle">
	<strong><span class="white12">新建文件夹</span></strong></td>
  </tr>
  <tr> 
    <td height="100%" valign="top"  class="win_bg" style="padding-top:10px;">
			<form name="form1" method="post">
		<table width="440" border="0" align="center" cellpadding="3" cellspacing="0">
        <tr> 
          <td width="12%" align="right" nowrap class="font_blue" >位置序号：</td>
          <td class="blue"><font class="font_blue">
            <input name="orderNum" type="text" class="input_search"  size="15">
					</font></td>
            <td align="right" class="font_blue"></td>
          <td class="blue"><font class="font_blue">&nbsp;</font></td>
        </tr>
        <tr> 
          <td align="right" nowrap class="font_blue" style="">名称：</td>
          <td class="blue"><font class="font_blue">
          
            <input name="moduleName" type="text" class="input_search" size="15">
            </font></td>
          <td width="39%" align="right" class="font_blue">类别： </td>
            <td width="110" class="blue"><font class="font_blue"> 
              <input name="type1" type="text" class="input_search" value="自定义文件夹" size="13" readonly=true>
              <input name="type" value="2" type="hidden">
              </font></td>
        </tr>
        <tr> 
          <td align="right" nowrap class="font_blue" style="">所处目录：</td>
          <td colspan="3" class="blue"><font class="font_blue">
             <select name="parentId" style="width:120px;" id="floderSelect">
					<option value="${defaultId}">根目录</option>
<#list favoriteFloderList as ele>
					<option value="${ele.id}" <#if (ele.id == nowId?default(defaultId))>selected</#if>>${ele.moduleName}</option>
</#list>
				</select>
			</td>
        </tr>
        <tr> 
          <td align="right" nowrap class="font_blue" style="padding-top:5px;">描述：</td>
          <td colspan="3" class="blue" style="padding-top:5px;"><textarea name="moduleDescription" cols="57" class="input_search1" style="height:215px;"></textarea> 
          </td>
        </tr>
			
      </table>
				</form>
			</td>
  </tr>
  <tr> 
    <td height="100%" align="center" valign="top" class="window_bg"> <table border="0" cellspacing="0" cellpadding="8">
        <tr> 
          <td width="100" align="center"><label>
		  <input type="button" name="Submit" value="保存"class="del_button1" tabindex="16" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveInfo();"/>
        </label></td>
		<td width="53"><label>
		  <input type="button" name="Submit2" value="取消"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancelInsert();"/>
		</label></td>
        </tr>
      </table></td>
  </tr>
</table>
<script>
function saveInfo(){
	var saveForm = document.getElementById("form1");
	saveForm.action = "favoriteSave.action";
	saveForm.submit();
//	if(validateform()){
//		saveForm.submit();
//	}	
}
function cancelInsert(){
	window.location.href="favorite.action?nowId=${nowId?default(defaultId)}";
}
</script>
</body>
</html>



