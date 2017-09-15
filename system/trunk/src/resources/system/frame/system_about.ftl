<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#assign productParamMap = productParamMap>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>关于我们</title>
<style>
td{
    color:#fff;
	font-size:12px;
}
.font_blue{
    color:#16145D;
}
.font_white{
    color:#fff;
}
.border{
    border:1px solid #16145D;
}

a{text-decoration: none;} /* 链接无下划线,有为underline */ 
a:link {color: #fff;} /* 未访问的链接 */
a:visited {color: #16145D;} /* 已访问的链接 */
a:hover{color: #16145D;} /* 鼠标在链接上 */ 
a:active {color: #16145D;} /* 点击激活链接 */
</style>
</head>

<body topmargin="0" leftmargin="0">
<table width="462" border="0" cellpadding="0" cellspacing="0" class="border">
  <tr>
    <td height="79" background="${request.contextPath}/system/images/about/system_top.jpg" width="460" height="79" >
      <table height="100%"  width="100%" border="0" cellspacing="0" cellpadding="0">
      	<tr>
      	  <td align="left"><font color="#000066" style="font-family:黑体;font-size:40px;padding-left:10px;"><strong><em><#if subSystemDto?exists>${subSystemDto.name?default('未知子系统')}<#else>未知子系统</#if></em></strong></font></td>
      	</tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="240" valign="top" background="${request.contextPath}/system/images/about/wins_bg.jpg" style="padding-top:20px;"><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="99%" border="0" cellspacing="1" cellpadding="1">
        <tr>
            <td>${subSystemDto.name?default("产品")}版本：<span class="font_blue"><#if subSystemDto?exists>V${subSystemDto.curVersion?default('未知版本号')}<#else>未知版本号</#if></span>
           &nbsp;&nbsp;build：<span class="font_blue"><#if subSystemDto?exists>${subSystemDto.build?default('未知BUILD版本号')}<#else>未知BUILD版本号</#if>
            </span></td>
          </tr>
          <tr>
            <td width="40%">${systemVersionDto.name?default("子系统")}版本：<span class="font_blue"><#if systemVersionDto?exists>V${systemVersionDto.curversion?default('未知版本号')}<#else>未知版本号</#if></span>
            &nbsp;&nbsp;build：<span class="font_blue"><#if systemVersionDto?exists>${systemVersionDto.build?default('未知BUILD版本号')}<#else>未知BUILD版本号</#if></span>
            </td>                    
        </table></td>
      </tr>
      <tr>
        <td height="2" background="${request.contextPath}/system/images/about/line_bg.jpg"></td>
      </tr>
      <tr>
        <td style="padding-top:15px;"><table width="99%" border="0" cellspacing="1" cellpadding="1">
          <#assign commerceTelephone = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@COMMERCE_TELEPHONE"))?default("")>
          <#if commerceTelephone!="">
          <tr>
            <td colspan="2">商务热线：${commerceTelephone}</td>
          </tr>
          </#if>
          <#assign supportTelephone = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@SUPPORT_TELEPHONE"))?default("")>
          <#if supportTelephone!="">
          <tr>
            <td colspan="2">技术热线：${supportTelephone}</td>
          </tr>
          </#if>
          <#assign customServiceTelephone = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@CUSTOM_SERVICE_TELEPHONE"))?default("")>
          <#if customServiceTelephone!="">
          <tr>
            <td colspan="2">客服热线：${customServiceTelephone}</td>
          </tr>
          </#if>
          <#assign companyUrl = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@COMPANY_URL"))?default("")>
          <#if companyUrl!="">
          <tr>
            <td colspan="2"><a href="${companyUrl}" target="_blank"><span class="font_white">${companyUrl}</span></a></td>
          </tr>
          </#if>
          <#assign email = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@EMAIL"))?default("")>
          <#if email!="">
          <tr>
            <td width="59%">E-mail:<a href="mailto:zdsoft@zdsoft.net">${email}</a></td>
            <td width="41%"></td>
          </tr>         
          </#if>
          <tr>
            <td>&nbsp;</td>
            <td><img src="${request.contextPath}/system/images/about/wins_close.jpg" width="103" height="23" onclick="window.close()" style="cursor:pointer;"/></td>
          </tr>
          <#assign chineseName = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@COMPANY_CHINESE_NAME"))?default("")>
          <#if chineseName!="">
          <tr>
            <td colspan="2">${chineseName}</td>
          </tr>
          </#if>
          <#assign englishName = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@COMPANY_ENGLISH_NAME"))?default("")>
          <#if englishName!="">
          <tr>
            <td colspan="2">${englishName}</td>
          </tr>
          </#if>
          <#assign copyright = productParamMap.get(stack.findValue("@net.zdsoft.eis.base.common.entity.ProductParam@COMPANY_COPYRIGHT"))?default("")>
          <#if copyright!="">
          <tr>
            <td colspan="2">${copyright?replace('currentYear',currentYear)}</td>
          </tr>
          </#if>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

