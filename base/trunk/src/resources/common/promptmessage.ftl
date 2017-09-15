<script language="javascript">
function formSubmit(theform,actionUrl,formTargert){
	if(actionUrl == "") return;
	theform.action = actionUrl;
	if(formTargert) {
		theform.target = formTargert;
	}
	theform.submit();
}

<#if promptMessageDto?exists>
	<#-- 页面打开时须执行的js操作 -->
	${promptMessageDto.javaScript?default("")}
</#if>
</script>
<body>
<input type="hidden" id="_promptMsgOption" value = "0">
<input type="hidden" id="_promptMsgOptionResult" value = "1">
<form action="" method="post" name="_promptmainform" id="_promptmainform">
<#if promptMessageDto?exists>
	<div class="recommend-tips-wrap">
    <#if promptMessageDto.operateSuccess?exists && promptMessageDto.operateSuccess>
    	<script>$("#_promptMsgOptionResult").val("0");</script>
		<div class="recommend-tips recommend-success" style="background-position:5px 40px;padding:40px 5px 10px 50px;">
	<#else>
		<div class="recommend-tips recommend-warn" style="background-position:5px 40px;padding:40px 5px 10px 50px;">
	</#if>
	<div class="tt">
	<#-- 提示信息 -->
	<#if promptMessageDto.promptMessage?exists && promptMessageDto.promptMessage?default("") != "">
		${promptMessageDto.promptMessage?default("")}
	</#if>
	<#-- 错误信息 -->
	<#if promptMessageDto.errorMessage?exists && promptMessageDto.errorMessage?default("") != "">
		${promptMessageDto.errorMessage?default("")}
	</#if>
	</div>
	<#if promptMessageDto.operations?exists>
	<script>$("#_promptMsgOption").val("1");</script>
	<div class="dd">
	<#assign count = 0>
	<#list promptMessageDto.operations as op>
	<#if count != 0>&nbsp;|</#if>&nbsp;
	<a href="javascript:void(0);" onClick="<#if op[2]?exists>${op[2]};</#if>formSubmit(_promptmainform,'${op[1]?default("")}'<#if op[3]?exists>,'${op[3]}'</#if>);return false;">${op[0]?default("")}</a>
	<#assign count = 1>
	</#list>
	</div>
	</#if>
	<#if promptMessageDto.hiddenText?exists>
		<#list promptMessageDto.hiddenText as text>
			<input name="${text[0]?default("")}" type="hidden" value="${text[1]?default("")}"/>
		</#list>
	</#if>
	</div>
</#if>
</form>
</body>