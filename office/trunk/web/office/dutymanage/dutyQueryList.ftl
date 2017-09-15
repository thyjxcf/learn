<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>

</script>
<div class="pub-table-inner">

<div class="pub-table-wrap" id="myWorkReportDiv">
<form name="form1" action="" method="post">
<div class="typical-table-wrap pt-15" <#if officePatrolPlaces?exists && (officePatrolPlaces?size>6)>style="width:1200px;overflow-x:auto;"</#if>>
	<@htmlmacro.tableList id="tablelist">
	<#if (officePatrolPlaces?exists && (officePatrolPlaces?size>0))&&(officeDutyPlaceMap?exists&&(officeDutyPlaceMap?size>0))>
	<tr>
		<th class="tt" style="width:30px;">
        	
        </th>
	    <#list officePatrolPlaces as tpl>
	    	<th <#if officePatrolPlaces?exists && (officePatrolPlaces?size>6)>width="100px;"<#else>width="${(1200/officePatrolPlaces?size)!}px;" </#if>style="word-break:break-all; word-wrap:break-word;text-align:center;">${tpl.placeName!}</th>
	    </#list>
	</tr>
	</#if>
	<#if (officePatrolPlaces?exists && (officePatrolPlaces?size>0))&&(officeDutyPlaceMap?exists&&(officeDutyPlaceMap?size>0))>
		<#list xdateList as date>
		    	<#if timeMap.get('${date!}')?exists>
		        	<#assign applyInfos = timeMap.get('${date!}')/>
		        	<#list applyInfos as ss>
		        	<#assign user1 = userMap.get('${ss!}')/>
		    <tr>
                	<td class="tt" style="text-align:center;"${ss!}>
		        		${date!}<br>(${user1.realname!})
		        	</td>
		        	
		        <#list officePatrolPlaces as tpl>
		        	<#if officeDutyPlaceMap.get('${date!}_${tpl.id!}_${user1.id!}')?exists>
		        	<#assign applyInfos = officeDutyPlaceMap.get('${date!}_${tpl.id!}_${user1.id!}')/>
		        		<td class="tt" style="">${applyInfos.patrolContent!}</td>
		        	<#else>
		        		<td class="tt" style="text-align:center;"></td>
		        	</#if>
		        </#list>
		        
			</tr>
		      </#list>
		      
		      <#else>
		      
		      <tr>
                	<td class="tt" style="text-align:center;">
		        		${date!}
		        	</td>
		        	
		        <#list officePatrolPlaces as tpl>
		        	<#if officeDutyPlaceMap.get('${date!}_${tpl.id!}')?exists>
		        	<#assign applyInfo = officeDutyPlaceMap.get('${date!}_${tpl.id!}')/>
		        	<#assign user = userMap.get('${applyInfo.dutyUserId!}')/>
		        		<td class="tt" style="">${applyInfo.patrolContent!}</td>
		        	<#else>
		        		<td class="tt" style="text-align:center;"></td>
		        	</#if>
		        </#list>
			</tr>
		        
		     </#if>
		</#list>
	<#else>
	   <tr><td colspan="50"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	</@htmlmacro.tableList>
</div>	
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>