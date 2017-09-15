<html>
<head>
<title>${webAppTitle}--单位频道设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript">

	<!-- ####### 设置显示的最大频道数量  ####### -->
	var MAXROWLENGTH = ${MAXCHANEELNUM?default('5')};
	
	function addRow(){
		var rowLength = form1.all("display").length;
		if(rowLength == MAXROWLENGTH){
			addActionError("&nbsp;能设置频道最多为" +MAXROWLENGTH+ "个，当前数目已达到，不能再继续添加!","addRowMessage");
		}
		if(rowLength < MAXROWLENGTH){
			NewRow = table_1.insertRow();
			for(var i = 0 ;i < 6;i++)
				NewRow.insertCell(i);
			NewRow.cells(0).style.background='#E8EBFE';
			NewRow.cells(0).align='center';
			NewRow.cells(0).innerHTML = '<input class="input" name="display" id="display" type="checkbox" value="" >';
			NewRow.cells(1).style.background='#E8EBFE';
			NewRow.cells(1).innerHTML = '<input onfocus="onFocusFun();" onblur="onBlurFun();" name="name" id="name" class="input" type="text" value="" size="12" maxlength="${MAXNAMELENGTH?default(5)}">';
			NewRow.cells(2).style.background='#E8EBFE';
			NewRow.cells(2).innerHTML='<input name="code" onfocus="onFocusFun();" onblur="onBlurFun();" id="code" class="input" type="text" value="" size="12" maxlength="${MAXCODELENGTH?default(5)}">';
			NewRow.cells(3).style.background='#E8EBFE';
			NewRow.cells(3).innerHTML = '<input name="orderid" onfocus="onFocusFun();" onblur="onBlurFun();" id="orderid" class="input" type="text" value="" size="4" maxlength="${MAXORDERIDLENGTH?default(1)}">';
			NewRow.cells(4).style.background='#E8EBFE';
			NewRow.cells(4).innerHTML = '<input name="url" onfocus="onFocusFun();" onblur="onBlurFun();" id="url" class="input" type="text" value="" size="40" maxlength="${MAXURLLENGTH?default(100)}"><input name="mark" id="mark" type="hidden" value="1">';
			NewRow.cells(5).style.background='#E8EBFE';
			NewRow.cells(5).innerHTML = '<input name="Submit" type="button" class="del_button1" value="取 消" onClick=delrow(this)> &nbsp;&nbsp;自定义频道，频道信息请填完整';
		}
	}
	 
	function delrow(delId){
		var RowIndex = delId.parentElement.parentElement.rowIndex
		table_1.deleteRow(RowIndex);
	}
	function getDisplayValues(){
		var displaysValue = "";
		var displays = document.getElementsByName('display');
		for(var i =0 ; i < displays.length; i ++){
			if(displays[i].checked == true){
				if(displaysValue.length > 0){
					displaysValue = displaysValue + "," + "1";
				}else{
					displaysValue = "1";
				}
			}else{
				if(displaysValue.length > 0){
					displaysValue = displaysValue + "," + "0";
				}else{
					displaysValue = "0";
				}	
			}
		}
		document.getElementById('displays').value = displaysValue;
	}
	function getNamesValues(){
		var namesValue = "";
		var names = document.getElementsByName('name');
		for(var i =0 ; i < names.length; i ++){
				if(namesValue.length > 0){
					namesValue = namesValue + "," + names[i].value;
				}else{
					namesValue = names[i].value;
				}
		}
		var n = document.getElementById('names').value = namesValue;
	}
	function getOrderidsValues(){
		var orderidsValue = "";
		var orderids = document.getElementsByName('orderid');
		for(var i =0 ; i < orderids.length; i ++){
				if(orderidsValue.length > 0){
					orderidsValue = orderidsValue + "," + orderids[i].value;
				}else{
					orderidsValue = orderids[i].value;
				}
		}
		document.getElementById('orderids').value = orderidsValue;
	}
	function getUrlsValues(){
		var urlsValue = "";
		var urls = document.getElementsByName('url');
		for(var i =0 ; i < urls.length; i ++){
				if(urlsValue.length > 0){
					urlsValue = urlsValue + "," + urls[i].value;
				}else{
					urlsValue = urls[i].value;
				}
		}
		document.getElementById('urls').value = urlsValue;
	}
	function getMarksValues(){
		var marksValue = "";
		var marks = document.getElementsByName('mark');
		for(var i =0 ; i < marks.length; i ++){
				if(marksValue.length > 0){
					marksValue = marksValue + "," + marks[i].value;
				}else{
					marksValue = marks[i].value;
				}
		}
		document.getElementById('marks').value = marksValue;
	}
	function getCodesValues(){
		var codesValue = "";
		var codes = document.getElementsByName('code');
		for(var i =0 ; i < codes.length; i ++){
				if(codesValue.length > 0){
					if(codes[i].value == ""){
						codesValue = codesValue + "," + 'noCode';
					}else{
						codesValue = codesValue + "," + codes[i].value;
					}
				}else{
					if(codes[i].value == ""){
							codesValue = codesValue + "," + 'noCode';
						}else{
							codesValue = codes[i].value;
						}
				}
		}
		document.getElementById('codes').value = codesValue;
	}
	function trim(str){
		return str.replace(/^\s+|\s+$/, '');
		}
	
	function checkData(){
	
		var names = document.getElementsByName('name');
		var codes = document.getElementsByName('code');
		var urls = document.getElementsByName('url');
		var orderids = document.getElementsByName('orderid');
		
		var displayes = document.getElementsByName('display');
		
		for(var i =0 ; i < names.length; i ++){
				if(trim(names[i].value) == ""){
					addFieldError('name','频道名称不能为空，请填写或取消相应行', i);
					return false;
				}
				if(trim(codes[i].value) == ""){
					addFieldError('code','频道排序不能为空，请填写或取消相应行', i);
					return false;
				}
				if(trim(orderids[i].value) == ""){
					addFieldError('orderid','频道排序不能为空，请填写或取消相应行', i);
					return false;
				}
				if(trim(urls[i].value) == ""){
					addFieldError('url','频道链接不能为空，请填写或取消相应行', i);
					return false;
				}
				if( isNaN( trim(orderids[i].value) ) == true ){
					addFieldError('orderid','类型错误，应为不能重复的数字,请修改', i);
					return false;
				}else{
					for(var j = 0;j < orderids.length; j++){
						for(var k = j + 1; k < orderids.length; k ++){
							if( trim(orderids[j].value) == trim(orderids[k].value)){
								if(trim(orderids[j].value) != ""){
									addFieldError('orderid','频道排序数值为不能重复,请修改', j);
									return false;
								}
							}
						}						
					}
				}
		}
		
		var t = 0;
		for(var i = 0; i < displayes.length; i ++){			
			if (displayes[i].checked == true){
				t = 1;
			}
		}
		if (t == 0){
			addActionError("&nbsp;必须至少选择一个频道页!","addRowMessage");
			return false;
		}
		
		return true;
	}
	function save(){
		getDisplayValues();
		getNamesValues();
		getOrderidsValues();
		getUrlsValues();
		getCodesValues();
		getMarksValues();
		if(checkData()){
			document.form1.submit();
		}
	}
</script>
</head>
<body>
  <table align="center" width="100%" class="YecSpec_background" height="100%" cellspacing="0" cellpadding="0" border="0">
  	<tr>
  	  <td height="30" bgcolor="#AABBFF" class="padding_right2">&nbsp;</td>
  	</tr>
  	<form action="saveUnitIniConfig.action" name="form1" method="post">
	  	<input name="names" id ="names" type="hidden" value="">
	  	<input name="orderids" id ="orderids" type="hidden" value="">
	  	<input name="displays" id ="displays" type="hidden" value="">
	  	<input name="urls" id ="urls" type="hidden" value="">
	  	<input name="codes" id ="codes" type="hidden" value="">
	  	<input name="marks" id ="marks" type="hidden" value="">
  	<tr><td bgcolor="#FCFFFF">
  	  <table align="center" width="100%" height="100%" cellspacing="1" cellpadding="1" border="0" class="content_div" id="table_1">
  	  	<tr align="center" height="26">
  	  	  <td bgcolor="#E8EBFE" width="60"><font color="#29248A">是否显示</font></td>
  	  	  <td bgcolor="#E8EBFE"><font color="#29248A">频道名称</font></td>
  	  	  <td bgcolor="#E8EBFE"><font color="#29248A">频道代码</font></td>
  	  	  <td bgcolor="#E8EBFE"><font color="#29248A">频道排序</font></td>
  	  	  <td bgcolor="#E8EBFE"><font color="#29248A">频道链接</font></td>
  	  	  <td bgcolor="#E8EBFE"><font color="#29248A">说    明</font></td>
  	  	</tr>
  	  	<#list unitChannelList as uList>
	  	  	<tr height="26">
	  	  	  <input name="mark" id="mark" type="hidden" value="${uList.mark?default(1)}">
	  	  	  <td align='center' bgcolor="#E8EBFE">
	  	  	    <input class="input" name="display" id="display" type="checkbox" <#if uList.display?default(0) == 1>checked="true"</#if> value="" <#if uList.mark?default(0) == 2> disabled </#if>>
	  	  	  </td>
	  	  	  <td align="left" bgcolor="#E8EBFE">
	  	  	    <input name="name" id="name"  type="text" value="${uList.name?default("无")}" size="12" <#if uList.mark?default(0) == 0 || uList.mark?default(0) == 2> class="input_readonly" </#if><#if uList.mark?default(0) == 1> class="input" </#if> maxlength="${MAXNAMELENGTH?default(5)}">
	  	  	  </td>
	  	  	  <td align="left" bgcolor="#E8EBFE">
	  	  	  	<input name="code" id="code"  type="text" value="<#if uList.code?default("noCode") != "noCode">${uList.code?default("无")}</#if>" size="12" <#if uList.mark?default(0) == 0||uList.mark?default(0) == 2> class="input_readonly" </#if><#if uList.mark?default(0) == 1> class="input" </#if> maxlength="${MAXCODELENGTH?default(5)}">
	  	  	  </td>
	  	  	  <td bgcolor="#E8EBFE">
	  	  	    <input name="orderid" id="orderid" class="input" type="text" value="${uList.orderid?default("0")}" size="4" maxlength="${MAXORDERIDLENGTH?default(1)}">
	  	  	  </td>
	  	  	  <td bgcolor="#E8EBFE">
	  	  	    <input name="url" id="url" type="text" value="${uList.url?default("无")}" size="40" <#if uList.mark?default(0) == 0||uList.mark?default(0) == 2> class="input_readonly" </#if><#if uList.mark?default(0) == 1> class="input" </#if> maxlength="100">
	  	  	  </td>
	  	  	  <td bgcolor="#E8EBFE">
	  	  	  	<#if uList.mark?default(0) == 0>系统默认频道，只能修改是否显示和频道排序</#if>
	  	  	  	<#if uList.mark?default(0) == 2>系统默认显示频道，只能修改频道排序</#if>
	  	  	  	<#if uList.mark?default(0) == 1>
				  <input name="Submit" type="button" class="del_button1" value="删 除" onClick=delrow(this)> &nbsp;&nbsp;自定义频道，频道信息请填完整
				</#if>
	  	  	  </td>
	  	  	</tr>
  	  	</#list>
  	  </table>
  	  <tr height="100%" bgcolor="#E8EBFE" valign="bottom">
  	  	  <td colspan="5" bgcolor="#E8EBFE" height="100%" valign="bottom">
  	  	  </td>
  	  </tr>
  	  <tr bgcolor="#E8EBFE" valign="bottom">
  	  	  <td colspan="5" bgcolor="#E8EBFE" valign="bottom">
  	  	   <div id="addRowMessage"></div>
  	  	  </td>
  	  </tr>
  	</td>
  	</tr>
  	</form>
  	<tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	<tr><td bgcolor="#ffffff" height="1"></td></tr>
  	<tr> 	
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4" valign="bottom">
  	  <table width="170" border="0">
  	  	<tr>
  	  	  <td><input name="Submit" type="Submit" class="del_button1" value="添加" size="50" onclick="addRow()"></td>
  	  	  <td><input name="Submit" type="button" class="del_button1" value="保存" onclick="save()"></td>
  	  	  <td><input name="cancel" type="reset" class="del_button1" value="重置" onclick="javascript:location.href='unitIniConfig.action';"></td>  	  	  
  	  	</tr>
  	  </table>
  	</td>
    </tr>
  </table>
</body>
</html>