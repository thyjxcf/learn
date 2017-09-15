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
<@common.tableList id="tablelist1" name="tablelist1" class="schedule-table schedule-table-day mt-5">
<tr class="tit">
	<th width="30"></th>
	<th>工作日志</th>
</tr>
<#assign hasOcm = false />
<#if calMap?exists && calMap?size gt 0>
<#assign hasOcm = true />
<#assign keys = calMap?keys>
</#if>
<#assign pestr=['上午','中午','下午','晚上'] />
<#assign nowday = calDto.calendarTime?string('yyyy-MM-dd') />
<#list 1..4 as pe>
<tr <#if pe_index%2==1>class="even"</#if>>
	<td class="tt" style="vertical-align:middle;">${pestr[pe-1]}</td>
	<td>
        <ul>
        	<#if hasOcm>
				<#assign hasDto = false />
				<#list keys as calKey>
					<#if calKey == (nowday?default('')+'-'+pe)>
						<#assign dto = calMap[calKey] />
						<#assign hasDto = true />
						<#break />
					</#if>
				</#list>
				<#if dto?exists && hasDto>
					<span class="num" onclick="toDetails('${nowday!}','${pe}');">${dto.num?default(0)}</span>
					<#if dto.cals?exists && dto.cals?size gt 0>
					<#list dto.cals as cal>
					<li>
                    <div class="li-name" style="width:165px;">${cal_index+1}、<@common.cutOff4List str='${cal.creatorName!}' length=10 />：</div>
                    <div><@common.cutOff4List str='${cal.place!}' length=40 /></div>
					</li>
					</#list>
					</#if>
				</#if>
				<#assign hasDto = false />
			<#else>
				&nbsp;
			</#if>
        </ul>
    </td>
</tr>
</#list>
</@common.tableList>
</@common.moduleDiv>