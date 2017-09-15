<#-- 待办事项 -->
<#if toDoDtos?exists && toDoDtos?size gt 0>
    <ul class="news-list">
    	<#list toDoDtos as x>
    		<#if x.openUrl?exists && x.openUrl!=''>
        		<li style="cursor:pointer;" onclick="window.open('${x.openUrl}');">
    		<#else>
        		<#assign module = x.module/>
        		<li style="cursor:pointer;" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">
    		</#if>
    		【${x.moduleSimpleName!}】${x.moduleContent!}
        	</li>
    	</#list>
    </ul>
<#else>
	<div class="no_data">一有消息，我们将及时为您送达！</div>
</#if>