<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<html>
<head>
<title>${webAppTitle}--单位推荐</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script>
var prefix = "${request.contextPath}/common/xtree/";
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
function recommend(type){
	var mark=document.getElementById('mark');
	mark.value=type;
	submitForm();
}
function lock(){
	var mark=document.getElementById('mark');
	mark.value='${unitmark_lock}';
	
	submitForm();
}
function unlock(){
	var mark=document.getElementById('mark');
	mark.value='${unitmark_normal}';
	submitForm();
}
function submitForm(){
	var auditForm=document.getElementById('auditForm');
	
	auditForm.action="unitAdmin-saveRecommend.action";
	auditForm.method="POST";
	auditForm.submit();
}
function cancel(){
	window.location.href="unitAdmin.action?ec_p=${ec_p?default(1)}&&ec_crd=${ec_crd?default(20)}";
}
</script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/webfxcheckboxtreeitem.js"></script>
</head>
<body>
<form name="auditForm" action="" method="POST">
<input id="mark" name="mark" type="hidden" value="0">
<table id="remoteTable" align="left" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  <tr>
  	<td>
  	  <table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0">
  	  	<tr>
  	  	  <td style="color:#29248A" width="120px" class="send_font_title">单位推荐</td>
  	  	  <td width="50%">
  	  	  	<div id="testdiv" style="display:none;"></div>
  	  	  </td>
  	  	  <td align="right" width="350px" style="padding-right:5px;">
  	  	  	<span class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="recommend(1);">推荐</span>
  	  	  	<span class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="recommend(0);">取消推荐</span>
  	  	  	<span class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="unlock();">解锁</span>  	  	  	
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
      	<td width="53"><label>
		  <input type="button" name="Submit2" value="返回"class="del_button1" tabindex="17" onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';" onClick="cancel();"/>
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
</body>
</html>