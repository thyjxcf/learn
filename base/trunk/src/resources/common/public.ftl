<#--手机号码-->
<#assign _REGEX_MOBILE_PHONE = "/^[0-9]{1,20}$/">
<#--身份证-->
<#assign _REGEX_IDENTITY_CARD = "/(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}[x|X]$)/">
<#--电话号码-->
<#assign _REGEX_PHONE = "/(^[0-9]{3,4}-[0-9]{6,8}$)|(^[0-9]{1,12}$)/">
<#--身份证（包含临时身份证）-->
<#assign _REGEX_IDENTITY_CARD_T = "/(^[0-9]{15}$)|(^[0-9]{18}$)|(^[0-9]{17}[x|X]$)|(^[0-9]{17}T$)/" >
<script>
_contextPath = "${request.contextPath}";
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jscroll.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jwindow.js"></script>
<!--[if IE 6]>
<script src="${request.contextPath}/static/js/letskillie6.zh_CN.pack.js"></script>
<![endif]-->
<script type="text/javascript" src="${request.contextPath}/static/js/handlefielderror.js"></script>
<!--校验脚本-->
<script type="text/javascript" src="${request.contextPath}/static/js/validate.js"></script>
<!--日期控制脚本-->
<script type="text/javascript" src="${request.contextPath}/static/js/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.ba-resize.min.js"></script>