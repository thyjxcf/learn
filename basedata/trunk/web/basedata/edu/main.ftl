<html>
<head>
<title>${webAppTitle}--学区基本信息管理</title>
<META http-equiv=Content-Type content="text/html; charset=UTF-8"> 
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="javascript">
function treeItemClick(id,type,name){
	if(type == 11 ){ //教育局
		window.open("${request.contextPath}/basedata/edu/schDistrict-Admin.action?eduid="+id, "mainFrame");	
	}
		
}

</script>
</head>

<body leftmargin="0" topmargin="0">
<table width="100%" height="100%" border="0" align="left" cellpadding="0" cellspacing="1"  class="YecSpec">
	<tr>
		<td valign="top">
			<table width="100%" height="100%" border="0" align="left" cellpadding="0" cellspacing="0">				
				<tr>    
					<td width="144" height="100%" class="YecSpec14" valign="top">
						<iframe id="leftFrame" name="leftFrame" marginwidth=0 allowTransparency="true"
							marginheight=0 src="${request.contextPath}/common/xtree/eduToSubEduTree.action?eduid=${eduid}" 
							frameborder=0 width="100%" height="100%" SCROLLING = "yes">
						</iframe>
					</td>
					<td height="100%">										
						 <iframe id="mainFrame" name="mainFrame" marginwidth=0 allowTransparency="true"
							marginheight=0 src="${request.contextPath}/static/common/eduPromptPage.html" 
							frameborder=0 width=100% height=100% SCROLLING = "no">
						</iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>

</html> 
