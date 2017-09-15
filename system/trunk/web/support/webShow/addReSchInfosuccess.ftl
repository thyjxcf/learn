<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
</head>

<script language="javascript">
function formSubmit(theform,actionUrl){
	theform.action = actionUrl;
	theform.submit();
}

	
</script>


<body>
<form action="" method="post" name="mainform">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" class="YecSpec10">
	<tr><td align="center"><table width="70%" height="100%">
	<tr height="60"><td colspan="2">&nbsp;</td></tr>
	<tr valign="top">
    <td align="center">
    	<img src="${request.contextPath}/static/images/wind_icon.gif">
    </td>
    
    <td><table width="90%"  border="0" align="center" cellpadding="0" cellspacing="4">
    		<tr>
	       		<td class="fontblue">
	       		<div class="error" style="font-weight: bold;background: transparent;color: blue;margin-right: 0; 
				margin-bottom: 3px; margin-top: 3px">
	       		<img src="${request.contextPath}/static/images/set_tips1.gif">
	       		${saveMessage?default("")}
	       		</div></td>
	       	</tr>
   			<tr><td align="center">
   			<table><tr>
				<td align="left">
				<div class="comm_button1" onMouseover = "this.className = 'comm_button11';" 
            	onMousedown= "this.className = 'comm_button111';"onMouseout= "this.className = 'comm_button1';"
	            onClick=";formSubmit(mainform,'addRecommendSchoolPage.action');">返回</div>
	          	</td>
			</tr></table>
			</td></tr>
        </table>
        </td></tr></table>
        </td>
     </tr>
</table>


</form>
</body>
</html>