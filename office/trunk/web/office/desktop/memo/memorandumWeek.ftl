<!--=S 备忘录 Start-->
<div class="grid">
	<div class="option option-wek">
		<span class="prev" id="preWeek" title="上一周"></span>
		<span id="span1"></span>
		<span class="next" id="nextWeek" title="下一周"></span>
	</div>
	<div class="table-scroll">
	    <div id="weekListContent"></div>
        <div class="top-layer" id="addWek"></div>
        <div class="top-layer" id="listWek"></div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
  //下一周
  $("#nextWeek").click(function() {
    memo_month.addWeek('+');
    var startDate = $("#weekmemo").attr("startdate");
    var endDate = $("#weekmemo").attr("endDate");
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
    jQuery("#schedule").attr("happen","2");
    jQuery("#workweek").attr("happen","2");
     **/
    //+"&schedule="+scheduleValue+"&workweek="+workweekValue
    $("#weekListContent").load(_contextPath+"/office/desktop/memo/memo-week.action?startDate="+startDate+"&endDate="+endDate);
  });
  // 上一周
  $("#preWeek").click(function() {
    memo_month.addWeek('-');
    var startDate = $("#weekmemo").attr("startdate");
    var endDate = $("#weekmemo").attr("endDate");
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
    jQuery("#schedule").attr("happen","2");
    jQuery("#workweek").attr("happen","2");
    +"&schedule="+scheduleValue+"&workweek="+workweekValue
    **/
    $("#weekListContent").load(_contextPath+"/office/desktop/memo/memo-week.action?startDate="+startDate+"&endDate="+endDate);
  });
  
});
</script>
