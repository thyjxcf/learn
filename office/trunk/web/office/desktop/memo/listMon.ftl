<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="月视图">
<input type="hidden" id="memoSize" value="<#if memoDtoList?exists>${memoDtoList?size}<#else>0</#if>"/>
<div class="dt">
	<span class="close-top" id="closeListMon">X</span>${action.getDate()?string("M月d号（E）")}
</div>
<div class="sc-list">
	<table width="236" border="0" cellspacing="0" cellpadding="0" id="listMonTable">
		<#if memoDtoList?exists && memoDtoList?size gt 0>
			<#assign index=1/>
			<#list memoDtoList as memoDto> 
				<#if "memo" == memoDto.getType()>
					<#assign memo = memoDto.getMemo()/>
					<tr>
						<td class="num" width="5%">${index}</td>
						<td style="word-break:break-all; word-wrap:break-word;" width="75%"><#if memo.send?default("0")=="1"><font color="#026db7">（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=30/></font><#else>
						（${memo.getTime()?string("HH:mm")}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=30/></#if>
						</td>
						<td width="20%">
							<img src="${request.getContextPath()}/static/images/icon/del2.png" class="del-img simple_del list_del" title="删除" value="${memo.getMemoExId()}" date="${memo.getTime()?string("yyyy-MM-dd")}"/>
							 <#if memo.getTime()?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gte baseDate?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")&&memo.send?default("0")!="1">
								<img src="${request.getContextPath()}/static/images/icon/edit.png" class="add-img" title="编辑" value="${memo.getId()}"/>
							</#if>
						</td>
					</tr>
				<#-- 
					<%}else if("schedule".equals(memoDto.getType())){ 
						Schedule schedule = memoDto.getSchedule();%>
							<tr>
								<td class="num" width="5%"><%=index %></td>
								<td colspan="2"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(schedule.getCalendarTime(),"yyMMdd HH:mm")%>--<%=DateUtils.date2String(schedule.getEndTime(),"yyMMdd HH:mm") %> ）地点:<%=StringUtils.ignoreNull(schedule.getPlace())%> 创建人:<%=StringUtils.ignoreNull(action.getUserById(schedule.getOperatorId()).getRealname())%> 参与人:<%=StringUtils.ignoreNull(schedule.getAttendeeNames())%> 内容:<%=StringUtils.ignoreNull(schedule.getContent())%></td>
							</tr>
					<%}else if("calendarDayInfo".equals(memoDto.getType())){ 
						CalendarDayInfo calendar = memoDto.getCalendarDayInfo();%>
							<tr>
								<td class="num" width="5%"><%=index %></td>
								<td colspan="2"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(calendar.getRestDate(),"yyMMdd")%> ） 内容:<%=StringUtils.ignoreNull(calendar.getContent())%></td>
							</tr>
					<%}else if("workPlan".equals(memoDto.getType())){ 
						WorkPlanItem workPlan = memoDto.getWorkPlanItem();%>
							<tr>
								<td class="num" width="5%"><%=index %></td>
								<td colspan="2"  style="word-break:break-all; word-wrap:break-word;">（<%=DateUtils.date2String(workPlan.getDate(),"yyMMdd") %>） 工作内容:<%=StringUtils.ignoreNull(workPlan.getContent())%> 工作地点:<%=StringUtils.ignoreNull(workPlan.getPlace())%></td>
							</tr>
					<%}
				-->
				<#assign index = index + 1/>
				</#if>
			</#list>
		</#if>	
	</table>
</div>
<script type="text/javascript">
  $(document).ready(function() {
    $("#listMonTable tr").mouseover(function() {
      $(this).addClass("current").siblings("tr").removeClass("current");
    });
    $(".add-img").click(function() {
      var id = $(this).attr("value");
      var tabType = $("#memo_menu_tab").attr("value");
      if(tabType=="monthmemo"){
        $("#addMon").load(_contextPath+"/office/desktop/memo/memo-addMon.action?parent="+tabType+"&memoId=" + id,function(){
          $("#addMon,#modelLayer").show();
        });
        $("#listMon").hide();
      }else if(tabType=="weekmemo"){
        $("#addWek").load(_contextPath+"/office/desktop/memo/memo-addMon.action?parent="+tabType+"&memoId=" + id,function(){
          $("#addWek,#modelLayer").show();
        });
        $("#listWek").hide();
      }
    });
    
    $(".list_del").click(function() {
      if (!confirm("您确定要删除备忘录信息？")) {
        return;
      }
      var id = $(this).attr("value");
      var date = $(this).attr("date");
      //var memoIds = new Array();
      var size = $("#memoSize").val();
      //memoIds.push(id);
      $.getJSON(_contextPath + "/office/desktop/memo/memo-removeMemo.action?t=" + new Date().getTime(), {
        "ids":id
      }, function(data) {
        if (data!=null && data!='') {// 删除失败，显示失败信息
          showMsgError(data.code);
        } else {//删除成功，从新加载备忘内容
          frushMyMemo("showList",date,(size-1));
        }
      });
    });
    $('.close-top').click(function(){
		$(this).parents('.top-layer').hide();
		$('.table-kb td').removeClass('current')
	});
  });
</script>
</@common.moduleDiv>