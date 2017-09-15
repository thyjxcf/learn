<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="" >
	<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">请假时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${startTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${endTime!}"/>
				<div class="query-tt ml-10">
					<span class="fn-left">审核状态：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="queryState" valId="queryState" myfunchange="doSearch">
						<a val=0>请选择</a>
						<a val=3>通过</a>
						<a val=4>未通过</a>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="teacherLeaveQuery"></div>
<script>
	$(function(){
		vselect();
		doSearch();
	});
	
	function doSearch(){
		var startTime=$("#queryStartTime").val();
		var endTime=$("#queryEndTime").val();
		if(startTime!=''&&endTime!=''){
			var re = compareDate(startTime,endTime);
			if(re==1){
				showMsgError("结束时间不能早于开始时间，请重新选择！");
				return;
			}
		}
		var auditState=$("#queryState").val();
		var str="?startTime="+startTime+"&endTime="+endTime+"&queryState="+auditState;
		var url="${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-queryList.action"+str;
		load("#teacherLeaveQuery",url);
	}
	
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>