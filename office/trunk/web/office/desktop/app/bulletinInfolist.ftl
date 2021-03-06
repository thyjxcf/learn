<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<#if module?exists>
	<#if officeBulletinList?exists && (officeBulletinList?size>0)>
     	<#assign len=officeBulletinList?size>
         <ul class="news-list" >
             <#list officeBulletinList as ent>
              	<#if ent_index lt bulletinNum>
                    <li onclick="viewContent('${ent.id!}');" style="cursor:pointer;"><span class="date">${(ent.createTime?string("MM-dd"))!}</span>
                    <span id="${ent.id!}_span" style="font-weight:<#if ent.isRead>normal<#else>bold</#if>;">
                    <#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	        			【${appsetting.getMcode("DM-ZCWJ").get(ent.scope?default('1')?string!)}】<@htmlmacro.cutOff4List str="${ent.title!}" length=13 />
	        		<#else>
                    	【${ent.areaName!}】<@htmlmacro.cutOff4List str="${ent.title!}" length=13 />
					</#if>
					<#if ent.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
					</span>
					</li>
                  </#if>
             </#list>    
         </ul>
             <#if len gt bulletinNum>
           		<p class="more"><a href="javascript:void(0);" onclick="go2Module('${module.url}','${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">查看更多 >></a></p>
             </#if>
	<#else>
        <div class="no_data">一有消息，我们将及时为您送达！</div>
	</#if>
<#else>
	<div class="no_data">暂无模块信息</div>
</#if>