<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<@common.tableList id="tableList1" name="tableList1" class="schedule-table schedule-table-mon mt-5">
    	<tr class="tit">
    		<th>星期一</th>
    		<th>星期二</th>
    		<th>星期三</th>
    		<th>星期四</th>
    		<th>星期五</th>
    		<th>星期六</th>
    		<th>星期日</th>
    	</tr>
    	<#list 1..weeksOfMonth as x>
    		<tr>
    		<#list 1..7 as y>
    			<#if monthMaptoDay?exists && monthMaptoDay?size gt 0>
    			<#assign dayList=(monthMaptoDay.get(x?string+y?string))?if_exists>
    			<td class="<#if dayList?exists && dayList?size gt 0>has<#else></#if>">
    				<#assign keys=dateMap?keys>
    				<#list keys as key>
    					<#if key==(x?string+y?string)>
    					<#assign dats=dateMap[key]>
    					<#break />
    					</#if>
    				</#list>
    				<#if (dateMap.get(x?string+y?string))?exists>
    				<p class="day">${dats?string("dd")}</p>
    				</#if>
    				<#if dayList?exists && dayList?size gt 0>
    					<#assign dateToView=dateMap.get(x?string+y?string) />
    					<span class="num" onclick="toDetailOfMonth('${dateToView?string('yyyy-MM-dd')}')">${dayList?size?default('')}</span>
    				</#if>
    				<#if (dateMap.get(x?string+y?string))?exists>
    				<#assign dat=dateMap.get(x?string+y?string) />
    				<#if isDeptAuth>
                	<span class="add-schedule" onclick="toOutlineAdd('${dat?string('yyyy-MM-dd')}')"></span>
                	</#if>
                	</#if>
    			</td>
    			</#if>
    		</#list>
    		</tr>
    	</#list>
</@common.tableList>
<script>
$(function(){
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

function toOutlineAdd(date){
	var url="${request.contextPath}/office/schedule/schedule-workOutlineAdd.action?"
		+"officeCalendarDto.calendarTime="+date+"&isWeek=false&isMonth=true&isDay=false";
	openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
	});
}

function toDetailOfMonth(date){
	var unitordept=$("#unitordept").val();
	var url="${request.contextPath}/office/schedule/schedule-workOutlineDayperiod.action?"
		+"officeCalendarDto.calendarTime="+date+"&unitordept="+unitordept+"&isWeek=false&isMonth=true&isDay=false";
	openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
	});
}

</script>
</@common.moduleDiv>