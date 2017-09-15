<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<#if module?exists>
	<div class="dt fn-clear">
		<a class="fn-right mr-5" href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多</a>
		<span class="item-name fn-left">${module.name!}</span>
	</div>
	<#if officeBulletinXjList?exists && officeBulletinXjList?size gt 0>
	    <ul class="re-list">
	         <#list officeBulletinXjList as ent>
	          	<#if ent_index lt 5>
	                <li title="${ent.title!}">
	                	<a href="javascript:void(0);" onclick="viewContentXj('${ent.id!}');">
	                    	<span>${(ent.createTime?string("yy-MM-dd"))!}</span>
							<@htmlmacro.cutOff str="${ent.title!}" length=20 />
						</a>
					</li>
	              </#if>
	         </#list>    
	    </ul>
	<#else>
	    <div class="no_data">一有消息，我们将及时为您送达！</div>
	</#if>
<#else>
	<div class="no_data">暂无模块信息</div>
</#if>
<script>
function viewContentXj(id){
	window.open("${request.contextPath}/office/desktop/app/info-viewDetailXj.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}
</script>