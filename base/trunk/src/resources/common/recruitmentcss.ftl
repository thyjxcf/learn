<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/recruitment/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/<#if loginUser?exists>${loginUser.skin?default('default')}.css<#else>default.css</#if>"/>
<#if statUrl?default('') !="" && reload>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%${statUrl?default('')}' type='text/javascript'%3E%3C/script%3E"));
</script>
</#if>
