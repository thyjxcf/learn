<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
$(document).ready(function(){
	$('.schedule-table-week td ul li:last').addClass('last');
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
<#assign hasOcm = false />
<#if calMap?exists && calMap?size gt 0>
<#assign hasOcm = true />
<#assign keys = calMap?keys>
</#if>
<@common.tableList id="tablelist1" name="tablelist1" class="schedule-table schedule-table-week mt-5">
<#if dates?size gt 1>
	<tr class="tit">
		<th class="tt">&nbsp;</th>
		<#list dates as da>
			<th>${da.dateStr!}(${da.dayOfWeekStr!})</th>
		</#list>
	</tr>
	<#assign pestr=['上午','中午','下午','晚上'] />
	<#list 1..4 as pe>
	<tr>
		<td class="tt" style="vertical-align:middle;">${pestr[pe-1]}</td>
		<#list dates as da>
		<td id="${da.strByDay!},${pe}">
			<#if calDto.creatorType?default('1')=='3'>
				<#if hasUnitAuth>
				<span class="add-schedule" onclick="toAdd('${da.strByDay!}','${pe}');"></span>
				</#if>
			<#else>
				<span class="add-schedule" onclick="toAdd('${da.strByDay!}','${pe}');"></span>
			</#if>
			<ul>
				<#if hasOcm>
					<#assign hasDto = false />
					<#list keys as calKey>
						<#if calKey == (da.strByDay?default('')+'-'+pe)>
						<#assign dto = calMap[calKey] />
						<#assign hasDto = true />
						<#break />
						</#if>
					</#list>
					<#if dto?exists && hasDto>
						<span class="num" onclick="toDetails('${da.strByDay!}','${pe}');">${dto.num?default(0)}</span>
						<#if dto.cals?exists && dto.cals?size gt 0>
						<#list dto.cals as cal>
						<li>
                        <div class="li-name">${cal_index+1}.${cal.creatorName!}</div>
                        <div class="li-address"><@common.cutOff4List str='${cal.place!}' length=7 /></div>
						</li>
						</#list>
						</#if>
					</#if>
				<#else>
					&nbsp;
				</#if>
			</ul>
		</td>
		</#list>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="1"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	<tr>
</#if>

</@common.tableList>
</@common.moduleDiv>