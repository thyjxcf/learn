<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="新建备忘录">
<!--=S 备忘录 (默认显示最近三天的备忘信息)Start-->
<form>
<div class="typical-table-wrap pt-15" style="height:284px;overflow-y:auto;width:450px;">
<p class="dt"></p>
<ul>
	<#if memoDtoList?exists && memoDtoList?size gt 0>
    	<#list memoDtoList as memoDto>
			<#if memoDto.getType() == "memo">
				<#assign memo = memoDto.getMemo()>
				<li>
					<div class="s-tit" style="word-break:break-all; word-wrap:break-word;">
						<span>
							<#if memo.getTime()?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gte baseDate?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")&&memo.send?default("0")!="1"> 
								<a href="javascript:void(0);" onclick="update('${memo.getId()!}')"> 
									<img src="${request.getContextPath()}/static/images/icon/edit.png" />
								</a>
							</#if>
							<a href="javascript:void(0);" onclick="removeMemo('${memo.getMemoExId()!}')">
								<img src="${request.getContextPath()}/static/images/icon/del2.png" />
							</a>
						</span>
						<span class="ui-checkbox"><input type="checkbox" class="chk" name="memoIds" value="${memo.getMemoExId()!}"></span>
						<label title="（${(memo.getTime()?string("HH:mm"))!}）${appsetting.htmlFilter(memo.getContent())}">
							<#if memo.send?default("0")=="1"><font color="#026db7">（${(memo.getTime()?string("HH:mm"))!}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=30/></font><#else>
							（${(memo.getTime()?string("HH:mm"))!}）<@common.cutOff str='${appsetting.htmlFilter(memo.getContent())}' length=30/></#if>
						</label>
					</div>
					<div class="s-con">
						<#if memo.getTime()?exists>
						周${Dayary[appsetting.getDayOfWeek(memo.getTime())-1]}
						${memo.getTimeString()!}
						</#if>
					</div>
				</li>
	<#--
	<%}else if("calendarDayInfo".equals(memoDto.getType())){ 
		CalendarDayInfo calendar = memoDto.getCalendarDayInfo();
		%>
		<li>
		<div class="s-tit" style="word-break:break-all; word-wrap:break-word;">
		<span> </span> 
		<label	title="（<%=DateUtils.date2String(calendar.getRestDate(),"yyMMdd") %>） 内容:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(calendar.getContent()))%>">
			（<%=DateUtils.date2String(calendar.getRestDate(),"yyMMdd") %> ）内容:<%=StringUtils.htmlFilter(StringUtils.cutOut(StringUtils.ignoreNull(calendar.getContent()),30,"..."))%>
		</label>
		</div>
		<div class="s-con">
			
		</div>
		</li>
	<%}else if("schedule".equals(memoDto.getType())){ 
		Schedule schedule = memoDto.getSchedule();
		%>
		<li>
		<div class="s-tit" style="word-break:break-all; word-wrap:break-word;">
		<span> </span> 
		<label	title="（<%=DateUtils.date2String(schedule.getCalendarTime(),"yyMMdd HH:mm") %>--<%=DateUtils.date2String(schedule.getEndTime(),"yyMMdd HH:mm") %>） 地点:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(schedule.getPlace()))%> 创建人:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(action.getUserById(schedule.getOperatorId()).getRealname()))%> 参与人:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(schedule.getAttendeeNames()))%> 内容:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(schedule.getContent()))%>">
			（<%=DateUtils.date2String(schedule.getCalendarTime(),"yyMMdd HH:mm") %>--<%=DateUtils.date2String(schedule.getEndTime(),"yyMMdd HH:mm") %>） 地点:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(schedule.getPlace()))%> 创建人:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(action.getUserById(schedule.getOperatorId()).getRealname()))%> 参与人:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(schedule.getAttendeeNames()))%> 内容:<%=StringUtils.htmlFilter(StringUtils.cutOut(StringUtils.ignoreNull(schedule.getContent()),30,"..."))%>
		</label>
		</div>
		<div class="s-con">
			
		</div>
		</li>
	<%}else if("workPlan".equals(memoDto.getType())){ 
		WorkPlanItem workPlan = memoDto.getWorkPlanItem();
		%>
		<li>
		<div class="s-tit" style="word-break:break-all; word-wrap:break-word;">
		<span> </span> 
		<label	title="（<%=DateUtils.date2String(workPlan.getDate(),"yyMMdd") %>） 工作内容:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(workPlan.getContent()))%> 工作地点:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(workPlan.getPlace()))%>">
		（<%=DateUtils.date2String(workPlan.getDate(),"yyMMdd") %>）工作内容:<%=StringUtils.htmlFilter(StringUtils.cutOut(StringUtils.ignoreNull(workPlan.getContent()),30,"...")) %>工作地点:<%=StringUtils.htmlFilter(StringUtils.ignoreNull(workPlan.getPlace()))%>
		</label>
		</div>
		<div class="s-con">
			<%if(null!=workPlan.getDate()){ %>
			周<%= Dayary[DateUtils.getDayOfWeek(workPlan.getDate())-1] %>
			<%=DateUtils.date2StringByDay(workPlan.getDate())%>
			<%} %>
		</div>
		</li>
	<%}}}%>
	 -->
	 		</#if>
	 	</#list>
	 </#if>
</ul>

<div class="bt">
	<span class="s-page">${htmlOfPaginationDiv!}</span>
    <p class="opt">
    	<span class="ui-checkbox ui-checkbox-all fn-left" data-all="no" style="width:3px;"><input type="checkbox" class="chk"></span>
    	<a href="javascript:void(0);" onclick="removeMemo()">删除</a>
    </p>
</div>
</div>
</form>
<script>
vselect();
function update(id) {
  $("#memorandum").hide();
  $('#memoLayer').show().addClass('new-memoLayer');
  $('#memoLayer').load('${request.getContextPath()}/office/desktop/memo/memo-viewMemo.action?memoId=' + id);
}
/*备忘记录的删除：单个删除和批量删除*/
function removeMemo(id){
  var memoIds = new Array();
  if(id==null){
    $('input[name="memoIds"]:checkbox:checked').each(function(i, e) {
      memoIds.push(e.value);
    });
  }else{
    memoIds.push(id);
  }
  if(memoIds==null||memoIds.length==0){
    showMsgWarn("请选择要删除的备忘录信息！");
    return;
  }
  if (!confirm("您确定要删除备忘录信息？")) {
    return;
  }
  $.getJSON("${request.contextPath}/office/desktop/memo/memo-removeMemo.action?t=" + new Date().getTime(),
    $.param( {"ids" : memoIds},true)
  , function(data) {
	    if (data!=null && data!='') {//删除失败，显示失败信息
			showMsgError(data);
	    } else {//删除成功，重新加载备忘内容
	      //showMsgSuccess("删除成功！", "提示", function(){
		        frushMyMemo();
			//});
			return;
	    }
  });
  
}

var type="${timeType!}";
memoUrlw = '${request.contextPath}/office/desktop/memo/memo-addMemo.action';
// 初使化时选中的TAB页(如最近三天)
type = parseInt(type - 1);
if (type > -1) {
	$(".schedule-slider li:eq(" + type + ")").addClass("current").siblings(
			"li").removeClass("current");
	var text = $(".schedule-slider li:eq(" + type + ")").html();
	$(".schedule-content .dt").html(text);
}

</script>
</@common.moduleDiv>