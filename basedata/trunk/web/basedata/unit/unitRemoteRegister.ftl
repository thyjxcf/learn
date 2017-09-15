<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/commonmacro.ftl" as common>
<#import "../../common/xtree/treelib.ftl" as treelib>
<html>
<head>
<title>${webAppTitle}--单位注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/prototype.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script>
function saveRemoteRegister(){
	document.getElementById('remoteProcess').style.display="";
	document.getElementById('remoteTable').style.display="none";

	var rf=document.getElementById('remoteForm');
	rf.action="unitAdmin-remoteRegisterSave.action";
	rf.submit();
}
function treeExpandAll(){
	if(${treeName}){
		${treeName}.expandAll();
	}
}
function treeCollapseAll(){
	if(${treeName}){
		${treeName}.collapseAll();
		${treeName}.expand();
	}
}

function extraCheck(treeNode){
	var divEl = document.getElementById(treeNode.id);
	var inputEl = divEl.getElementsByTagName("INPUT")[0];
	if(inputEl == null){
		return;
	}
	if(inputEl.checked && treeNode.parentNode.getChecked != null && !treeNode.parentNode.getChecked()){
		//treeNode.parentNode.setChecked(true);
		var divE2 = document.getElementById(treeNode.parentNode.id);
		var inputE2 = divE2.getElementsByTagName("INPUT")[0];
		inputE2.checked = 'checked';
	}
	extraCheck(treeNode.parentNode);
}
function remoteTest(){
	var buffalo=new Buffalo('');
	var testdiv=document.getElementById('testdiv');
	testdiv.style.display='none';
	
	testdiv.className='actiontip_common';
	testdiv.innerHTML='远程服务测试中，请稍等...';
	testdiv.style.display='';
	
	buffalo.remoteActionCall("unitAdmin-remoteUnitService.action","remoteTest",[],function(reply){
		var result=reply.getResult();
		
		if(result==true){
			testdiv.className='tip_common';
			testdiv.innerHTML='远程服务测试成功！';			
		}
		else{
			testdiv.className='tip_error';
			testdiv.innerHTML='远程服务测试失败，请检查远程服务设置及确认远程服务是否开启！';
		}
		testdiv.innerHTML+="<img src='${request.contextPath}/static/images/close.gif' border='0' style='cursor:pointer;' onclick=\"document.getElementById('testdiv').style.display='none'\">";
		testdiv.style.display='';
	});
}
function exportOffline(){
	var unitIds=getCheckUnit();
	var testdiv=document.getElementById('testdiv');
	testdiv.style.display='none';
	
	if(unitIds&&unitIds.length>0){
		var buffalo=new Buffalo('');
		
		buffalo.remoteActionCall("unitAdmin-remoteUnitService.action","exportTest",[unitIds],function(reply){
			var result=reply.getResult();
			
			if(result==true){
				remoteForm.action="unitAdmin-unit2OfflineRegister.action";
				remoteForm.target="hiddenIframe";
				remoteForm.method="POST";
				remoteForm.submit();
				
				testdiv.innerHTML="该文件包含单位的注册信息，<font color='red'>请勿修改</font>";		
				testdiv.className="tip_common";
				testdiv.style.display='';
			}
			else{
				testdiv.innerHTML="对不起，您选择的单位信息不完整或不存在，导出注册信息暂时不可用！";
				testdiv.className="tip_error";
				testdiv.style.display='';
			}
		});	
	}
	else{
		testdiv.innerHTML="请先选择欲导出注册信息的单位";
		testdiv.className="tip_error";
		testdiv.style.display='';
	}
}
function getCheckUnit(){
	var arrayIds=new Array();
	var unitcheck=document.getElementsByName('arrayIds');
	var j=0;
	for(var i=0;i<unitcheck.length;i++){
		if(unitcheck[i].checked==true){
			arrayIds[j]=unitcheck[i].value;
			j++;
		}
	}
	return arrayIds;
}
function importOffline(){
	var importdiv=document.getElementById('import_div');
	if(importdiv){
		importdiv.style.display='';
	}
}
function doImport(){
	var testdiv=document.getElementById('testdiv');
	if(testdiv){
		testdiv.innerHTML="请稍等，导入文件处理中...";
		testdiv.style.display='';
		
		importForm.action="unitAdmin-unit4OfflineRegister.action";
		importForm.method="POST";
		importForm.submit();
	}
}
</script>
<@treelib.treecite />
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/webfxcheckboxtreeitem.js"></script>
</head>
<body>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/wz_dragdrop.js"></script>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none"></iframe>
<div id="import_div" style="position:absolute;width:350px;height:100px;z-index:99;border:2px solid #6C85CE;display:none;margin-left:10px;">
  <form name="importForm" method="POST" enctype="multipart/form-data">
  	<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16" >
  	  <tr>
  	  	<td>
  	  	  <table width="90%" height="100%" cellspacing="0" cellpadding="0" border="0" style="margin-left:5px;">
	  	  <tr>
	  	  	<td class="ct">请选择导入文件：</td>
	  	  </tr>
	  	  <tr>
	  	  	<td style="margin-left:5px;"><input name="importFile" type="file"></td>
	  	  </tr>
	  	  <tr>
	  	  	<td align="right">
	  	  	  <input name="importbutton" type="button" value="确认" class="del_button1" onclick="doImport();">
	  	  	  <input name="cancelbutton" type="button" value="取消" class="del_button1" onclick="document.getElementById('import_div').style.display='none';">
	  	  	</td>
	  	  </tr>
  	  	  </table>
  	  	</td>
  	  </tr>
  	</table>
  </form>
</div>
<div id="remoteProcess" style="display:none;">
  <table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  	<tr>
  	  <td align="center" valign="middle">
  	  	<table>
  	  	  <tr>
  	  	  	<td style="color:#29248A;">远程注册信息保存中，请稍等</td>
  	  	  </tr>
  	  	  <tr>
  	  	  	<td><img src="${request.contextPath}/static/images/progressbar.gif" border="0"></td></td>
  	  	  </tr>
  	  	</table>
  	  </td>
  	</tr>
  </table>
</div>
<form name="remoteForm" action="" method="POST">
<table id="remoteTable" align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>
  	<td>
  	  <table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0">
  	  	<tr>
  	  	  <td style="color:#29248A" width="120px" class="send_font_title">单位远程注册</td>
  	  	  <td width="50%">
  	  	  	<div id="testdiv" style="display:none;"></div>
  	  	  </td>
  	  	  <td align="right" width="350px" style="padding-right:5px;">
  	  	  	<span class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="remoteTest();">服务测试</span>
  	  	  	<#if false>
  	  	  	  <#if !action.isEISSUnit()><span class="comm_button1" onClick="importOffline();">导入注册信息</span></#if>
  	  	  	  <span class="comm_button1" onClick="exportOffline();">导出注册信息</span>
  	  	  	</#if>
  	  	  </td>
  	  	</tr>
  	  	<tr>
  	  	  <td colspan="3">
  	  	  	<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0">
  	  	  	  <tr>
  	  	  	  	<td height="400" width="50%">
  	  	  	  	  <div style="width: 100%; top: 0px; left: 0px; height: 100%; padding: 5px; overflow: auto;">
					<script>
					  ${xtreeScript}
					  document.write(${treeName});
					</script>
				  </div>
  	  	  	  	</td>
  	  	  	  	<td>
  	  	  	  	</td>
  	  	  	  </tr>
  	  	  	</table> 
  	  	  </td>
  	  	</td>
  	  </table>
  	</td>
  </tr>
  <tr style="height:0px;"><td id="actionTip"></td></tr>
  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
  <tr><td bgcolor="#ffffff" height="1"></td></tr>
  <tr>
  	<td bgcolor="#C2CDF7" height="32" class="padding_left4"><table width="219" border="0" cellspacing="0" cellpadding="0">
      <tr>
		<td width="60" align="center"><label>
		  <input type="button" name="Submit" value="注册"class="del_button1" tabindex="16" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="saveRemoteRegister();"/>
        </label></td>
		<td width="53"><label>
		  <input type="button" name="Submit2" value="展开"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="treeExpandAll();"/>
		</label></td>
		<td width="53"><label>
		  <input type="button" name="Submit2" value="收缩"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="treeCollapseAll();"/>
		</label></td>
	  </tr>
	</table></td>
  </tr>
</table>
</form>
<script>
treeExpandAll();
</script>
<script type="text/javascript" language="javascript">
<!--
SET_DHTML(CURSOR_MOVE, "import_div");
//-->
</script>  
</body>
</html>