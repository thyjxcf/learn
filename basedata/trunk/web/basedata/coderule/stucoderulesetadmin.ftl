<#import "/common/htmlcomponent.ftl" as htmlmacro> 
<@htmlmacro.moduleDiv titleName="学号规则设置"> 
	<div class="ui-radio-box">
        <#if initCodeRule>
        <span class="ui-radio ui-radio-current" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=21&initCodeRule=true');" class="radio" checked>学号</span>
	    <span class="ui-radio" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=22&initCodeRule=true');" class="radio">毕业证号</span>
	    <#--<span class="ui-radio" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=11&initCodeRule=true');" class="radio">学籍号</span>-->
		<span class="ui-radio" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=12&initCodeRule=true');" class="radio">高中会考证号规则</span>
        <#else>
	        <#if schoolUserType >
	         <span class="ui-radio ui-radio-current" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=21');" class="radio" checked>学号</span>
	         <#--<span class="ui-radio" data-name="c"><input type="radio" name="radiobutton" value="radiobutton"  onclick="doRad('codeRuleSetAdmin-stuCodeRule.action?codeType=11&sections=1');" class="radio">学籍号</span>-->
	        </#if>
        </#if>
	</div>
<div name="dataDiv" id="dataDiv"  />
<script>
	<#if codeType?default("") != "">
		load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRule.action?codeType=${codeType}");
	</#if>
	//如果直接加载就直接定义就行
	$(document).ready(function(){
		if ("${codeType!}" == "22" || "${codeType!}" == "11"){
			load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRule.action?codeType=${codeType!}");
		}
		else{
			load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRule.action?<#if initCodeRule>initCodeRule=true&codeType=21<#else><#if schoolUserType >codeType=21<#else>codeType=11</#if></#if>");
		}
	})
	function doRad(url){
		load("#dataDiv","${request.contextPath}/basedata/coderule/"+url);
	}
	vselect();
</script>
</@htmlmacro.moduleDiv>