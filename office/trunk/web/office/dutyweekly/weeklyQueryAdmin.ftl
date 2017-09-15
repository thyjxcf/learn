<#import "/common/htmlcomponent.ftl" as common>
<script>

function searchOrder(){
	var dutyStartTime=$("#dutyStartTime").val();
	var dutyEndTime=$("#dutyEndTime").val();
	if(dutyStartTime!=''&&dutyEndTime!=''){
		var re = compareDate(dutyStartTime,dutyEndTime);
		if(re==1){
			showMsgError("结束时间不能早于开始时间，请重新选择！");
			return;
		}
	}
	var week=$("#week").val();
	var str="?dutyStartTime="+dutyStartTime+"&dutyEndTime="+dutyEndTime+"&week="+week;
	load("#wirkReportDiv","${request.contextPath}/office/dutyweekly/dutyweekly-weeklyQueryList.action"+str);
}

$(function(){
	searchOrder();
});



</script>
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">日期：</span></div>
	    	<@common.datepicker name="dutyStartTime" id="dutyStartTime" style="width:120px;" value="${((dutyStartTime)?string('yyyy-MM-dd'))?if_exists}"/>
	   		<div class="query-tt">&nbsp;-&nbsp;</div>
	    	<@common.datepicker name="dutyEndTime" id="dutyEndTime" style="width:120px;" value="${((dutyEndTime)?string('yyyy-MM-dd'))?if_exists}"/>
			<div class="query-tt ml-10" style="margin-left:30px;"><span class="fn-left">周次：</span></div>
				<div class="select_box fn-left">
				<@common.select style="width:100px;" valName="week" valId="week" myfunchange="searchOrder">
					<a val=""><span>请选择</span></a>
					<#list weekTimeList as item>
					<a val="${item!}" <#if item==week?default('')>class="selected"</#if>><span>第${item!}周</span></a>
					</#list>
				</@common.select>
				</div>
			
			
			&nbsp;&nbsp;<a href="javascript:void(0);" onclick="searchOrder();" class="abtn-blue">查找</a>
    </div>
</div>
<div id="wirkReportDiv"></div>