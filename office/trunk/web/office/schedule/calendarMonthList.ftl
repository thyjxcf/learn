<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
$(document).ready(function(){
	$('.schedule-table td').hover(function(){
		$(this).addClass('td-hover');
		$(this).prev('th,td').addClass('td-left');
		$(this).parent('tr').prev('tr').children('th:eq('+$(this).index()+'),td:eq('+$(this).index()+')').addClass('td-top');
	},function(){
		$(this).removeClass('td-hover');
		$(this).prev('th,td').removeClass('td-left');
		$(this).parent('tr').prev('tr').children('th:eq('+$(this).index()+'),td:eq('+$(this).index()+')').removeClass('td-top');
	});
});
</script>
<@common.tableList id="tablelist1" name="tablelist1" class="schedule-table schedule-table-mon mt-5">
<tr class="tit">
	<th>星期一</th>
	<th>星期二</th>
	<th>星期三</th>
	<th>星期四</th>
	<th>星期五</th>
	<th>星期六</th>
	<th>星期日</th>
</tr>
<#assign dayWeeks=[2,3,4,5,6,7,1] />
<#assign monWeeks=[1..calDto.weekNum] />
<#assign mon = calDto.calendarTime?string('yyyy-MM') />
<#assign days = 0 />
<#list 1..calDto.weekNum as mw>
<tr>
	<#list dayWeeks as we>
		<#if (mw==1 && calDto.firstWeek != we && days==0) || days==calDto.dayNum>
				<td>&nbsp;</td>
		<#else>
			<#assign days = days+1 />
			<#assign keys = calMap?keys>
			<#assign hasDto = false />
			<#assign daystr = days+'' />
			<#if days lt 10>
			<#assign daystr = '0'+daystr />
			</#if>
			<#list keys as calKey>
				<#if calKey?index_of(mon+'-'+daystr+'-0') != -1>
				<#assign dto = calMap[calKey] />
				<#assign hasDto = true />
				<#break />
				</#if>
			</#list>
			<#if dto?exists && hasDto>
			<td class="has">
	        	<p class="day">${days}</p>
	            <span class="num" onclick="toDetails('${mon!}-${days}','-1');">${dto.num?default(0)}</span>
	            <#if calDto.creatorType?default('1')=='2'>
	            	<#if hasUnitAuth><span class="add-schedule" onclick="toAdd('${mon+'-'+daystr}','0');"></span></#if>
	            <#else>
	            <span class="add-schedule" onclick="toAdd('${mon+'-'+daystr}','0');"></span>
	            </#if>
	        </td>
	        <#else>
	        <td>
	        	<p class="day">${days}</p>
	            <#if calDto.creatorType?default('1')=='3'>
	            	<#if hasUnitAuth><span class="add-schedule" onclick="toAdd('${mon+'-'+daystr}','0');"></span></#if>
	            <#else>
	            <span class="add-schedule" onclick="toAdd('${mon+'-'+daystr}','0');"></span>
	            </#if>
	        </td>
	        </#if>
		</#if>
    </#list>
</tr>
</#list>
</@common.tableList>
</@common.moduleDiv>