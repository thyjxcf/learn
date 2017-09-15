<#import "/common/htmlcomponent.ftl" as common>
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
	
	function toAdd(dateStr,pe){
		var url="${request.contextPath}/office/schedule/schedule-workOutlineAdd.action?"
		+"officeCalendarDto.calendarTime="+dateStr+"&officeWorkOutline.period="+pe;
		openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
		});
	}
	
	function toWeekLineDetails(datestr,pe){
		var unitordept=$("#unitordept").val();
		var url="${request.contextPath}/office/schedule/schedule-workOutlineDayperiod.action?"
		+"&officeCalendarDto.calendarTime="+datestr+"&officeWorkOutline.period="+pe+"&unitordept="+unitordept+"&isWeek=true&isMonth=false&isDay=false";
		openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
		});
	}
	
	function showtips(){
		showMsgError("您没有操作的权限！");
	}
</script>
<#assign hasWorkline = false />
<#if workOutlineMap?exists && workOutlineMap?size gt 0>
<#assign hasWorkline = true />
</#if>
<@common.tableList id="tableList1" name="tableList1" class="schedule-table schedule-table-week mt-5">
	<tr class="tit">
    	<th class="tt">&nbsp;</th>
    	<#list dates as da>
    		<th>${da.dateStr!}(${da.dayOfWeekStr!})</th>
    	</#list>
    </tr>
    <#assign pestr=['上午','中午','下午','晚上']/>  
    <#list 1..4 as pe>
    	<tr>
    		<td class="tt" style="vertical-align:middle;">${pestr[pe-1]}</td>
    		<#list dates as da>
    			<td id="${da.strByDay!},${pe}">
    				<#if isDeptAuth><span onclick="javascript:toAdd('${da.strByDay!}','${pe}');" class="add-schedule"></span></#if>
    				<ul>
    					<#if hasWorkline>
							<#assign weekList = (workOutlineMap.get(da.getStrByDay()+pe))?if_exists />
							<#if weekList?exists && weekList?size gt 0>
							<span class="num" onclick="toWeekLineDetails('${da.strByDay!}','${pe}');">${(weekList?size)?default(0)}</span>
							<#assign num=0>
							<#list weekList as wl>
							<#assign num=num+1>
							<#if (num>3)><#break></#if>
							<li>
							<#--第几条-->
                        	<div class="li-name">${wl_index+1}.${wl.deptName!}:</div>
                        	<#--地点 内容-->
                        	<div class="li-address"><@common.cutOff4List str='${wl.place!}' length=7 /></div>
							</li>
							</#list>
							</#if>
						<#else>
							&nbsp;
						</#if>
    				</ul>
    			</td>
    		</#list>
    	</tr>
    </#list>
</@common.tableList>
<div id="tableContent"/>
</@common.moduleDiv>