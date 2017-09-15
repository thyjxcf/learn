<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<script language="javascript">
function back(){
	location.href='schoolGeneralInfoQuery-queryBasicSemester.action?name='+encodeURIComponent("${name?default('')}")+'&searchType=semesterInfo';
}	
</script>
</head>
<body >

<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec16" height="100%">
 
	<tr>
	<td valign="top">
		<div class="content_div">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr><td bgcolor="#FCFFFF" id="content"><table width="100%" border="0" cellspacing="1" cellpadding="1">
			  <tr>
			    <td class="send_padding" align="right" width="200">学年名称：</td>
			    <td class="send_padding_no_width" width="">${semesterDto.acadyear?default('')}</td>
			    <!--
			    <td class="send_font">学期名称：</td>
			    <td width="555"  class="send_padding">${appsetting.getMcode("DM-XQ").get(semesterDto.semester?default(''))}</td>
			    -->
			  </tr>
			  <tr>
			  	<td class="send_padding" align="right">学期名称：</td>
			    <td class="send_padding_no_width">${appsetting.getMcode("DM-XQ").get(semesterDto.semester?default(''))}</td>
			  </tr>
			  <tr>
			    <td class="send_padding" align="right">工作开始日期：</td>
			    <td class="send_padding_no_width">${semesterDto.workbegin?string("yyyy-MM-dd")}</td>
			    <!--
			    <td class="send_font">周天数：</td>
			    <td colspan="3" class="send_padding">${semesterDto.edudays?if_exists}</td>
			    -->
			  </tr>
			  <tr>
			    <td class="send_padding" align="right">学习开始日期：</td>
			    <td class="send_padding_no_width">
			    ${semesterDto.semesterbegin?string("yyyy-MM-dd")}</td>
			    <!--
			    <td class="send_font">课长：</td>
			    <td class="send_padding">       
			    ${semesterDto.classhour?if_exists}</td>
			    -->
			  </tr>
			   <tr>
			    <td class="send_padding" align="right">注册日期：</td>
			    <td class="send_padding_no_width">${semesterDto.registerdate?string("yyyy-MM-dd")}</td>
				<!--
			    <td class="send_font">上午课节数：</td>
			    <td class="send_padding">${semesterDto.amperiods?if_exists}</td>
			    -->
			  </tr>
			  <tr>
			    <td class="send_padding" align="right">学习结束日期：</td>
			    <td class="send_padding_no_width">
				${semesterDto.semesterend?string("yyyy-MM-dd")}</td>
				<!--
			    <td width="137" class="send_font">下午课节数：</td>
			    <td colspan="3" class="send_padding">
			    ${semesterDto.pmperiods?if_exists}</td>
			    -->
			  </tr>
			  <tr>
			    <td class="send_padding" align="right">工作结束日期：</td>
			    <td class="send_padding_no_width">${semesterDto.workend?string("yyyy-MM-dd")}</td>
			    <!--
			    <td width="137" class="send_font">晚上课节数：</td>
			    <td colspan="3" class="send_padding">
			    ${semesterDto.nightperiods?if_exists}</td>
			    -->
			  </tr>
			  
			  </table>
           </td></tr>
            
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
	<table width="70%"  border="0" cellspacing="0" cellpadding="0">
		
	<input type="submit" name="Submit2" value="返回"
									class="del_button1"
									onmouseover="this.className = 'del_button3';"
									onmousedown="this.className = 'del_button2';"
									onmouseout="this.className = 'del_button1';"									
									onClick="back();"
									 />
			
	</td></tr></table>
	
	</td>
  </tr>
</table>
</body>
</html>
