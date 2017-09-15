<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>推荐学校详细信息</title>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
</head>
<body> 
<script>
function querySchoolByName(){
	var schoolName = document.getElementById("textfield").value;
	if (schoolName == ""){
		alert("请输入学校名称！");
		document.getElementById("textfield").focus();
		return false;
	}
	location.href = "getSchoolsBySchName.action?schoolName=" + schoolName;
}
</script>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <#--<tr>
    <td width="632" height="25" background="images/top_bg.gif"><img src="images/sm_green_pic.gif" width="11" height="11" hspace="5" align="absmiddle"><a href="#">登录</a> | <a href="#">注册新用户</a></td>
    <td width="144" align="center" background="images/top_bg.gif"><a href="#">设为首页</a> | <a href="#">加入收藏</a></td>
  </tr>-->
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images/banner_pic.jpg" width="776" height="93"></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="3" bgcolor="#EFEBEC"></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="60"><img src="images/dh_left_pic.gif" width="60" height="33"></td>
    <td width="74"><a href="${homepage?default("#")}"><img src="images/index_pic.gif" width="74" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="90"><a href="${etoh?default("#")}"><img src="images/school_message_pic.gif" width="90" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="75"><a href="${blog?default("#")}"><img src="images/blog_pic.gif" width="75" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="77"><a href="${sbar?default("#")}"><img src="images/study_pic.gif" width="77" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="97"><a href="${game?default("#")}"><img src="images/game_pic.gif" width="97" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="98"><a href="${resource?default("#")}"><img src="images/educate_pic.gif" width="98" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="98"><a href="${forum?default("#")}"><img src="images/bbs_pic.gif" width="74" height="33" border="0"></a></td>
    <td width="7"><img src="images/line_pic.gif" width="7" height="33"></td>
    <td width="58"><img src="images/dh_right_pic.gif" width="82" height="33"></td>
  </tr>
</table> 
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="3" bgcolor="#EFEBEC"></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="30"><img src="images/sm_pic2.gif" width="7" height="7" hspace="4"><span style="padding-left:5px">当前位置：首页 &gt;&gt; 学校推荐 &gt;&gt; ${recommendSchool.schoolName?default("")}</span></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#8CD261">
  <tr>
    <td height="33" align="left" bgcolor="#F1F9E6" style="padding-left:5px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="51%">请输入学校名称：
          <input name="textfield" type="text"  class="input_1px" id="textfield">
          <a href="#"><img onclick="querySchoolByName();" src="images/search_button.gif" width="60" height="22" border="0" align="absmiddle"></a></td>
        <td width="49%" align="right"></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="776" height="2" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#EBEBEB">
  <tr>
    <td></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="4" bgcolor="#FFFFFF"></td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td align="center" valign="top" bgcolor="#F7F6F6"><br>
      <table width="500" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center"><table width="1%" border="0" cellpadding="3" cellspacing="1" bgcolor="#d7d6d6">
          <tr>
          	<td bgcolor="#FFFFFF"><img src="${recommendSchool.schoolPictureUrl?default("")}" ></td>
          </tr>
        </table></td>
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
        </table></td>
      </tr>
    </table>
<br></td>
    <td width="221" valign="top" class="padding_4px line_green_1px">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/pic4.gif"></td>
      </tr>
    </table>        
   <table width="221" border="0" height="520" cellpadding="0" cellspacing="0">
    <tr height="100%">
      <td bgcolor="#FFFFFF">
      <iframe FRAMEBORDER="no" scrolling="no" width="100%" height="100%" name="regionSchIFrame" src="getRecommendSchoolList.action?schoolRegionCode=${schoolRegionCode?default("65")}"></iframe>
      </td>
    </tr>
  </table>
  </td>
  </tr>
</table>
<br>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="41" background="images/buttom_pic.gif">&nbsp;</td>
  </tr>
</table>
<table width="776" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <TD style="BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; BORDER-BOTTOM: medium none" align=middle>中国移动通信集团新疆有限公司 All Right Reserved 咨询服务热线：<FONT color=#ff0000>10086</FONT></TD>
	<TD style="BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; BORDER-BOTTOM: medium none" align=left width=131><A href="http://www.hangzhou.cyberpolice.cn/"><IMG height=54 src="http://xj.edu88.com/Images/pageItem/winupon/police.gif" width=54 align=absMiddle vspace=1 border=0></A></TD>
  </tr>
</table>
</body>
</html>
