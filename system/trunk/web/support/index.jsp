<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="images/style.css">

<script language="javascript" src="" ></script>
</head>
<body >
	<a href="<%=request.getContextPath()%>\support\getRecommendSchoolList.action?schoolRegionCode=data">list</a>
	<a href="<%=request.getContextPath()%>\support\getAllRecommendSchoolList.action">allList</a>
	<a href="<%=request.getContextPath()%>\support\showUpdateRecSchInfo.action?schoolId={AE3A0836-2B62-4794-B558-DF147E565D36}">updateInfo</a>
	<a href="<%=request.getContextPath()%>\support\showAllSchoolInfoList.action">showAllSchool</a>
	<a href="<%=request.getContextPath()%>\support\addRecommendSchoolPage.action">add</a>
	<a href="<%=request.getContextPath()%>\support\getSchoolsBySchName.action?schoolName=安">add</a>
	<table >
		<tr valign="top"></tr>
	</table>
</body>
</html>