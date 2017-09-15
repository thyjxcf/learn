<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#if officeMsgReceivings?exists && officeMsgReceivings?size gt 0>
    <ul class="news-list">
    	<#list officeMsgReceivings as x>
        	<#if x_index lt 10>
	        	<li style="cursor:pointer;" title="${x.title!}" <#if x.isRead==0>class="unread"</#if> onclick="go2Module(<#if module.id==69002||module.id==69052>'${module.url}?desktopIn=2&officeMsgReceiving.id=${x.id!}'<#else>'${module.url}'</#if>,'${module.subsystem}','${module.id}','${module.parentid}','${module.name}','desktop');return false;">
	        		<span class="date">${x.dateStr!}</span>
	        		<#if x.msgType?default(0)==1>
	        			<span <#if x.isRead==0>style="color:red;"</#if>>
							<#if x.receiverType==4>【部门<#elseif x.receiverType==5>【单位<#else>【个人</#if><#if switchName>邮件】<#else>消息】</#if>
						</span>
	        		<#elseif x.msgType?default(0)==2>
	        			<span <#if x.isRead==0>style="color:red;"</#if>>【公文<#if switchName>邮件】<#else>消息】</#if></span>
	        		<#elseif x.msgType?default(0) gt 2>
	        			<span <#if x.isRead==0>style="color:red;"</#if>>【办公<#if switchName>邮件】<#else>消息】</#if></span>
	        		<#else>
	        			<span <#if x.isRead==0>style="color:red;"</#if>>【其他<#if switchName>邮件】<#else>消息】</#if></span>
	        		</#if>
	        		<@htmlmacro.cutOff str='${x.title!}' length=15/>
	        	</li>
        	</#if>
    	</#list>
    </ul>
<#else>
	<div class="no_data">一有消息，我们将及时为您送达！</div>
</#if>
