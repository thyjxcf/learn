<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>修改推荐状态</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
	<style type="text/css">
	.tinput{
		border-width:1px;
		border-style:solid;
		border-color:0;
	}
	</style>
	<script language="javascript">
		 function sel(a){ 
		  o=document.getElementsByName(a) 
		  for(i=0;i<o.length;i++) 
		  o[i].checked=event.srcElement.checked 
		 }
	</script>
	</head>
	<body class="YecSpec">
        <table width="500" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td><input name="CheckedAllschool" value="" type="checkbox" onclick="sel('schoolId')"></td>
            <td height="24" algin="center"><strong>学校名称</strong></td>
            <td height="24" width="80"><strong>学校类别</strong></td>										 
            <td height="24" width="80"><strong>区域编码</strong></td>
          </tr>
          <form name="form1" action="updateRecommendState.action" method="post">
           <#list listOfRecommendSchool as tList>
          <tr>
          	<td><input name="schoolId" value="${tList.schoolId?default("")}" <#if tList.schoolRecommend == 1> checked </#if> type="checkbox"></td>
          	<td>${tList.schoolName?default("")}</td>
          	<td>${tList.schoolTypeText?default("")}</td>
          	<td>${tList.schoolRegionCode?default("")}</td>
          </tr>
          </#list>
          <tr><td colspan="3"></td><tr/>
          <tr>
          	<td></td><td><input type="submit" value="确 认" class="del_button1"></td>
          </tr>
          </form>
        </table>
	</body>
</html>