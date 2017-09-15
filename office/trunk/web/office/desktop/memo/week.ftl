<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="周视图">
<style>
.more:hover{
cursor:pointer;
}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-kb table-kb-wek">
	<#if weekMemoDto?exists && weekMemoDto?size gt 0>
		<#assign index = 0/>
		<#list weekMemoDto as memoDtoList>
		<tr>
			<th style="width:60px;font-weight: normal;<#if now  == weekDate.get(index)?string('yyyy-MM-dd')>color:red</#if>">${weekDate.get(index)?string("MM/dd")}周${Dayary[appsetting.getDayOfWeek(weekDate.get(index))-1]}</th>
			<td class="<#if weekDate.get(index)?string('yyyyMMdd HH:mm:ss')?date('yyyyMMdd HH:mm:ss') lt nowTime?string('yyyyMMdd HH:mm:ss')?date('yyyyMMdd HH:mm:ss')>_blank</#if>" value="${weekDate.get(index)?string('yyyy-MM-dd')}">
			<#if memoDtoList?exists && memoDtoList?size gt 0>
				<#if memoDtoList?size gt 2>
					<#list 0..1 as i>
						<#assign memoDto = memoDtoList.get(i)/>
						<#if "memo" == memoDto.getType()>
							<#assign memo = memoDto.getMemo()/>
							<p class="details" value="${memo.getId()}"  style="word-break:break-all; word-wrap:break-word;"><#if memo.send?default("0")=="1"><font color="#026db7">（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=56/></font><#else>（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=56/></#if>
							<img style="margin-left:5px;margin-right:10px;visibility: hidden;" src="${request.getContextPath()}/static/images/icon/del2.png"
					            class="del-img simple_del" title="删除" value="${memo.getMemoExId()!}" /></p>
				        </#if>
				        <div class="clr"></div>
				    </#list>
				 <#else>
				 	<#list memoDtoList as memoDto>
						<#if "memo" == memoDto.getType()>
							<#assign memo = memoDto.getMemo()/>
							<p class="details" value="${memo.getId()}"  style="word-break:break-all; word-wrap:break-word;"><#if memo.send?default("0")=="1"><font color="#026db7">（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=56/></font><#else>（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=56/></#if>
							<img style="margin-left:5px;margin-right:10px;visibility: hidden;" src="${request.getContextPath()}/static/images/icon/del2.png"
					            class="del-img simple_del" title="删除" value="${memo.getMemoExId()}" /></p>
				        </#if>
				        <#if memoDtoList.size() gt 1>
				        	<div class="clr"></div>
				        </#if>
				    </#list>
				 </#if>
		    	<p class="more" value="${weekDate.get(index)?string('yyyy-MM-dd')}">展开</p>
		 	</#if>
			</td>
			</tr>
			<#assign index= index+1/>
		 </#list>
	 </#if>
		<#-- 
				<%	} else if ("calendarDayInfo".equals(memoDto.getType())) {
					CalendarDayInfo calendar = memoDto.getCalendarDayInfo();%>
					<p class="details" value="" dateValue="<%=DateUtils.date2StringByDay(weekDates.get(index)) %>"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(calendar.getRestDate(),"yyMMdd") %>）内容:<%=StringUtils.cutOut(StringUtils.ignoreNull(calendar.getContent()),30,"...") %></p>
				<%	} else if ("schedule".equals(memoDto.getType())) {
					Schedule schedule = memoDto.getSchedule();%>
					<p class="details" value="" dateValue="<%=DateUtils.date2StringByDay(weekDates.get(index)) %>"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(schedule.getCalendarTime(),"yyMMdd HH:mm") %>--<%=DateUtils.date2String(schedule.getEndTime(),"yyMMdd HH:mm") %>） 地点:<%=StringUtils.ignoreNull(schedule.getPlace())%> 创建人:<%=StringUtils.ignoreNull(action.getUserById(schedule.getOperatorId()).getRealname())%>...</p>
					<%-- 参与人:<%=StringUtils.ignoreNull(schedule.getAttendeeNames())%> 内容:<%=StringUtils.cutOut(StringUtils.ignoreNull(schedule.getContent()),30,"...")%> --%>
				<%	} else if ("workPlan".equals(memoDto.getType())) {
					WorkPlanItem workPlan = memoDto.getWorkPlanItem();%>
					<p class="details" value="" dateValue="<%=DateUtils.date2StringByDay(weekDates.get(index)) %>"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(workPlan.getDate(),"yyMMdd") %>）工作内容:<%=StringUtils.cutOut(StringUtils.ignoreNull(workPlan.getContent()),30,"...") %></p>
					<%--工作地点:<%=StringUtils.ignoreNull(workPlan.getPlace())%> --%>
				<%  }
		-->
</table>
<script type="text/javascript">
$(document).ready(function() {
  
  $(".table-kb-wek td").mouseover(function(){
    $(".table-kb td").removeClass("hover cur hover_1");
    if($(this).hasClass("_blank")){
      $(this).addClass("hover_1");
    }else{
      $(this).addClass("hover");
    }
  });
  $(".table-kb-wek td").click(function(){
    $(".table-kb-wek td").removeClass("hover cur hover_1")
    $(this).addClass("hover_1 cur");
    $("#addWek,#modelLayer").show();
    var date = $(this).attr("value");
    $("#addWek").load(_contextPath+"/office/desktop/memo/memo-addMon.action?date="+date);
  });
  $(".table-kb-wek td .details").mouseover(function(){
    $("img",this).css("visibility","visible");
    
  }).mouseout(function(e){
    $("img",this).css("visibility","hidden");
    
  });
  $(".table-kb-wek td .details").click(function(e){
    e.stopPropagation();
    var id = $(this).attr("value");
    $(".table-kb-wek td").removeClass("hover cur hover_1");
    $(this).addClass("hover_1 cur");
    $("#listWek,#modelLayer").hide();
    $("#addWek,#modelLayer").show();
    //var date = $(this).attr("dateValue");
    //+"&date="+date
    $("#addWek").load(_contextPath+"/office/desktop/memo/memo-addMon.action?memoId="+id);
  });
  $(".table-kb-wek td .more").click(function(e){
    e.stopPropagation();
    $(".table-kb-wek td").removeClass("hover cur hover_1")
    $(this).parents("td").addClass("hover_1 cur");
    $("#addWek,#modelLayer").hide();
    $("#listWek,#modelLayer").show();
    var date = $(this).attr("value");
    /**
    var schedule = jQuery("#schedule");
    var scheduleValue = "" ;
    if(schedule.attr("checked")){
    	scheduleValue = schedule.val();
    }
    var workweek = jQuery("#workweek");
    var workweekValue = "";
    if(workweek.attr("checked")){
    	workweekValue = workweek.val();
    }
    +"&schedule="+scheduleValue+"&workweek="+workweekValue
    **/
    $("#listWek").load(_contextPath+"/office/desktop/memo/memo-listMon.action?date="+date);
  });
  $(".simple_del").click(function(e) {
    e.stopPropagation();
    if (!confirm("您确定要删除备忘录信息？")) {
      return;
    }
    var id = $(this).attr("value");
    //var memoIds = new Array();
    //memoIds.push(id);
    $.getJSON(_contextPath + "/office/desktop/memo/memo-removeMemo.action?t=" + new Date().getTime(), {
      "ids" : id
    }, function(data) {
      if (data!=null && data != '') {// 删除失败，显示失败信息
        showMsgError(data);
      } else {//删除成功，从新加载备忘内容
      	frushMyMemo();
      }
    });
  });
  $("._blank").each(function(e,i){
    $(this).removeClass("hover cur hover_1").unbind("click");
    // $(this).css("cursor","text");
    $(this).find(".details").each(function(e,i){
      $(this).unbind("click");
    });
  });
});
</script>
</@common.moduleDiv>