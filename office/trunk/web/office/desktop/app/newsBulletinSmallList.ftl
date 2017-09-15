<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<@htmlmacro.moduleDiv titleName="">
<script>
function viewContent(id){
	$("#"+id+"_span").css("font-weight","normal");
	window.open("${request.contextPath}/office/desktop/app/info-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}
function queryBulletin(type){
	var url="${request.contextPath}/office/desktop/app/newsBulletinSmallList.action?bulletinType="+type;
	load("#bulletinSmallListDiv", url);
}
</script>
<div id="bulletinSmallListDiv">
<!--=S 通知 Start-->
<#if module?exists>
	<#if officeBulletinTypelist?exists && (officeBulletinTypelist?size>0)>
    	<div class="dt dt-no fn-clear">
        	<#if officeBulletinList?exists && (officeBulletinList?size>0)>
        		<a class="desk-item-more" href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">
    			 	更多 &gt;
			 	</a>
        	</#if>
            <span class="desk-item-tab">
        		<#list officeBulletinTypelist as typ>
        			<span <#if typ.type == bulletinType>class="current first"</#if> onclick="queryBulletin('${typ.type!}');">${typ.name!}</span>
        		</#list>
            </span>
        </div>
        </#if>
        <#if officeBulletinList?exists && (officeBulletinList?size>0)>
        <#assign len=officeBulletinList?size>
        <ul class="re-list desk-item-list" >
        <#list officeBulletinList as ent>
        	<#if ent_index lt 7>
            <li onclick="viewContent('${ent.id!}');" style="cursor:pointer;"><span>${(ent.createTime?string("yy-MM-dd"))!}</span>
        	<span style="float:left;font-weight:<#if ent.isRead>normal<#else>bold</#if>;" id="${ent.id!}_span">
        	<#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
    			【${appsetting.getMcode("DM-ZCWJ").get(ent.scope?default('1')?string!)}】<@htmlmacro.cutOff str="${ent.title!}" length=15 />
    		<#else>
        		【${ent.areaName!}】<@htmlmacro.cutOff str="${ent.title!}" length=15 />
    		</#if>
    		<#if ent.isNew>
				<img src="${request.contextPath}/static/images/icon/new.png">
			</#if>
    		</span>
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
</div>
<!--=E 通知 End-->
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>            
</@htmlmacro.moduleDiv>