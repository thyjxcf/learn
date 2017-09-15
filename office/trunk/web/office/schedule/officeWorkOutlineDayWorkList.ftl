<#import "/common/htmlcomponent.ftl" as common>
<#assign allday=stack.findValue("@net.zdsoft.office.schedule.util.ScheduleConstant@PERIOD_ALLDAY")>
<#assign am=stack.findValue("@net.zdsoft.office.schedule.util.ScheduleConstant@PERIOD_AM")>
<#assign noon=stack.findValue("@net.zdsoft.office.schedule.util.ScheduleConstant@PERIOD_NOON")>
<#assign pm=stack.findValue("@net.zdsoft.office.schedule.util.ScheduleConstant@PERIOD_PM")>
<#assign night=stack.findValue("@net.zdsoft.office.schedule.util.ScheduleConstant@PERIOD_NIGHT")>
<@common.moduleDiv titleName="">
<input type="hidden" id="" name="" value="">
<table class="schedule-table schedule-table-day mt-5">
    	<tr class="tit">
    		<th width="30"></th>
    		<th>日程安排</th>
    	</tr>
        <tr>
        <#assign amlist=workOutlineListByPeriodMap.get(am)>
        <#assign num=0>
        	<td class="tt" style="vertical-align:middle;">上午</td>
        	<td>
                <ul>
                	<#if amlist?exists && amlist?size gt 0><span class="num" onclick="showDayLineDetail('${am!}');">${(amlist?size)?default(0)}</span></#if>
                	<#list amlist as al>
                	<#assign num=num+1>
                	<#if (num>3)><#break></#if>
                    <li>
                        <div class="li-name" style="width:165px;">${num!}、${al.deptName!}：</div>
                        <div><@common.cutOff4List str='${al.place!}' length=40/></div>
                        <!--<div class="li-des"><@common.cutOff4List str='${al.content!}' length=50/></div>-->
                    </li>
                    </#list>
                </ul>
            </td>
        </tr>
        <tr class="even">
        <#assign noonlist=workOutlineListByPeriodMap.get(noon)>
        <#assign num=0>
        	<td class="tt" style="vertical-align:middle;">中午</td>
        	<td>
                <ul>
                	<#if noonlist?exists && noonlist?size gt 0><span class="num"  onclick="showDayLineDetail('${noon!}');">${(noonlist?size)?default(0)}</span></#if>
                	<#list noonlist as noon>
                	<#assign num=num+1>
                	<#if (num>3)><#break></#if>
                    <li>
                        <div class="li-name" style="width:165px;">${num!}、${noon.deptName!}：</div>
                        <div><@common.cutOff4List str='${noon.place!}' length=40/></div>
                        <!--<div class="li-des"><@common.cutOff4List str='${noon.content!}' length=50/></div>-->
                    </li>
                    </#list>
                </ul>
            </td>
        </tr>
        <tr>
        	<td class="tt" style="vertical-align:middle;">下午</td>
        	<td>
        		<#assign pmlist=workOutlineListByPeriodMap.get(pm)>
        		<#assign num=0>
                <ul>
                	<#if pmlist?exists && pmlist?size gt 0><span class="num"  onclick="showDayLineDetail('${pm!}');">${(pmlist?size)?default(0)}</span></#if>
                	<#list pmlist as pl>
                	<#assign num=num+1>
                	<#if (num>3)><#break></#if>
                    <li>
                       	<div class="li-name" style="width:165px;">${num!}、${pl.deptName!}：</div>
                        <div><@common.cutOff4List str='${pl.place!}' length=40/></div>
                       <!-- <div class="li-des"><@common.cutOff4List str='${pl.content!}' length=50/></div>-->
                    </li>
                    </#list>
                </ul>
            </td>
        </tr>
        <tr class="even">
        	<td class="tt" style="vertical-align:middle;">晚上</td>
        	<#assign nightlist=workOutlineListByPeriodMap.get(night)>
        	<#assign num=0>
        	<td>
                <ul>
                	<#if nightlist?exists && nightlist?size gt 0><span class="num"  onclick="showDayLineDetail('${night}');">${(nightlist?size)?default(0)}</span></#if>
                	<#list nightlist as nl>
                	<#assign num=num+1>
                	<#if (num>3)><#break></#if>
                    <li>
                       	<div class="li-name" style="width:165px;">${num!}、${nl.deptName!}：</div>
                        <div><@common.cutOff4List str='${nl.place!}' length=40/></div>
                        <!--<div class="li-des"><@common.cutOff4List str='${nl.content!}' length=50/></div>-->
                    </li>
                    </#list>
                </ul>
            </td>
        </tr>
    </table>
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
	//详细页面
	function showDayLineDetail(tim){
		var url="${request.contextPath}/office/schedule/schedule-workOutlineDayperiod.action?officeWorkOutline.period="+tim
		+"&officeCalendarDto.calendarTime=${(officeCalendarDto.calendarTime?string('yyyy-MM-dd'))?if_exists}"+"&isWeek=false&isMonth=false&isDay=true";
		openDiv("#scheduleLayer","#scheduleLayer .close,#scheduleLayer .submit,#scheduleLayer .reset",url,null,null,"600px",function(){
			
		});
	}
	
</script>
</@common.moduleDiv>