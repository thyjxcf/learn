<#-- 待办工作 -->
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
<div class="dt fn-clear">
	<span class="item-name fn-left">待办工作</span>
</div>
<#if toDoDtos?exists && toDoDtos?size gt 0>
    <ul class="re-list-item">
    	<#list toDoDtos as x>
        	<li>
        		<#assign module = x.module/>
        		<a href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;" class="view">查看明细</a>
        		<span class="c-orange">【${x.moduleSimpleName!}】</span>
        		${x.moduleContent!}
        	</li>
    	</#list>
    </ul>
<#else>
	<div class="no_data">一有消息，我们将及时为您送达！</div>
</#if>
</@htmlmacro.moduleDiv>