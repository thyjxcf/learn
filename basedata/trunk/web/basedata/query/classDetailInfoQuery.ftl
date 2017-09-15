<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<script language="javascript">
function back(){
	location.href='schoolGeneralInfoQuery-queryBasicClass.action?name='+encodeURIComponent("${name?default('')}")+'&searchType=classInfo'
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
			    <td  class="send_font">班级编码：</td>
			    <td class="send_padding">${classDto.classcode?default('')}</td>
			    <td  class="send_font">班级名称：</td>
			    <td width="555"  class="send_padding">${classDto.classnamedynamic?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">入学学年：</td>
			    <td class="send_padding">${classDto.acadyear?default('')}</td>
			    <td class="send_font">学制：</td>
			    <td colspan="3" class="send_padding">${classDto.schoolinglen?if_exists}</td>
			  </tr>
			  <tr>
			    <td width="112" class="send_font">建班年月：</td>
			    <td width="131" class="send_padding">
			    <#if classDto.datecreated?exists>${classDto.datecreated?string("yyyy-MM-dd")}</#if></td>
			    <td class="send_font">班级荣誉称号：</td>
			    <td class="send_padding">       
			    ${classDto.honor?default('')}</td>
			  </tr>
			   <tr>
			    <td class="send_font">班级类型：</td>
			    <td class="send_padding">
			    ${appsetting.getMcode("DM-BJLX").get(classDto.classtype?default(""))}</td>
			    <td class="send_font">文理类型：</td>
			    <td class="send_padding">
			    ${appsetting.getMcode("DM-BJWLLX").get(classDto.artsciencetype?string?default(""))}</td>
			  </tr>
			  <tr>
			    <td class="send_font">班主任：</td>
			    <td class="send_padding">
			     ${classDto.teachername?default('')}</td>
			    <td width="137" class="send_font">副班主任：</td>
			    <td colspan="3" class="send_padding">
			     ${classDto.viceTeacherName?default('')}</td>
			  </tr>
			  <tr>
			    <td class="send_font">班长：</td>
			    <td class="send_padding">
				${classDto.monitor?default('')}</td>
			    <td width="137" class="send_font"></td>
			    <td colspan="3" class="send_padding"></td>
			  </tr>
			  <#--<tr>
			    <td class="send_font">是否毕业：</td>
			    <td class="send_padding">${classDto.graduatesign?default('0')}</td>
			    <td width="137" class="send_font">毕业日期：</td>
			    <td colspan="3" class="send_padding">
			     <#if classDto.graduatedate?exists>${classDto.graduatedate?string("yyyy-MM-dd")}</#if></td>
			  </tr>-->

			  
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
