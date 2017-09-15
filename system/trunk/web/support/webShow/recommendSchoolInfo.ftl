<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>推荐学校详细信息</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
		<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
	<style type="text/css">
	.tinput{
		border-width:1px;
		border-style:solid;
		border-color:0;
	}
	</style>
	</head>
	<body>
		<table width="776" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td align="center" valign="top" bgcolor="#F7F6F6"><br>
      <table width="500" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center">
	<table width="1%" border="0" cellpadding="3" cellspacing="1" bgcolor="#d7d6d6">
          <tr>
            <td bgcolor="#FFFFFF" width="350" height="232"><img src="${recommendSchool.schoolPictureUrl?default("")}" ></td>
          </tr>
        </table>
	</td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="24"><strong>学校名称</strong>：${recommendSchool.schoolName?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>学校主页</strong>：${recommendSchool.schoolHomepage?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>学校博客</strong>：${recommendSchool.schoolBlog?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>学校类别</strong>：${recommendSchool.schoolTypeText?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>学校地址</strong>：${recommendSchool.schoolAddress?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>邮政编码</strong>：${recommendSchool.schoolPostcode?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>联系电话</strong>：${recommendSchool.schoolPhone?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>传　　真</strong>：${recommendSchool.schoolFax?default("")}</td>
          </tr>
          <tr>
            <td height="24"><strong>学校介绍</strong>：</td>
          </tr>
          <tr>
            <td height="30">${recommendSchool.schoolIntroduction?default("")}</td>
          </tr>
        </table>
	</td>
      </tr>
    </table>
	</body>
</html>