<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<#if module?exists>
	<div class="dt fn-clear">
		<a class="fn-right mr-5" href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">更多</a>
		<span class="item-name fn-left">${module.name!}</span>
	</div>
	<#if officeBulletinList?exists && (officeBulletinList?size>0)>
	 	<#assign len=officeBulletinList?size>
	     <ul class="news-list" >
	         <#list officeBulletinList as ent>
	          	<#if ent_index lt 5>
	                <li style="cursor:pointer;" id="${ent.id!}_li" <#if !ent.isRead>class="unread"</#if> onclick="viewContent('${ent.id!}');">
	                <span class="date">${(ent.createTime?string("MM-dd"))!}</span>
                	<@htmlmacro.cutOff4List str="${ent.title!}" length=13 />
					<#if ent.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
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
function viewContent(id){
	$("#"+id+"_li").removeClass("unread");
	window.open("${request.contextPath}/office/desktop/app/info-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}
</script>