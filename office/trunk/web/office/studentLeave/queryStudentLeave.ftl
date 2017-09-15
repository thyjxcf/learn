<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="" >
	<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">请假时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:90px;" value="${startTime!}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:90px;" value="${endTime!}"/>
				<div class="query-tt ml-10">
					<span class="fn-left">审核状态：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:110px;" valName="queryState" valId="queryState" myfunchange="doSearch">
						<a val=0>请选择</a>
						<a val=3>通过</a>
						<a val=4>未通过</a>
						<a val=8>已作废</a>
					</@common.select>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">请假原因：</span>
				</div>
				<input name="remarks" id="remarks" value="${remark!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
				<div class="query-tt ml-10">
					<span class="fn-left">年级：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:100px;" valName="gradeId" valId="gradeId" myfunchange="test">
						<a val="">请选择</a>
						<#list grades as item>
						<a val="${item.id!}">${item.gradename!}</a>
						</#list>
					</@common.select>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">班级：</span>
				</div>
				<div class="fn-left" id="chanceId">
					<@common.select style="width:170px;" valName="classId" valId="classId" myfunchange="doSearch">
						<a val="">请选择</a>
						<#list eisuClasss as item>
						<a val="${item.id!}">${item.classname!}</a>
						</#list>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="studentLeaveQuery"></div>
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
		var remark=$("#remarks").val();
		var gradeId=$("#gradeId").val();
		var classId=$("#classId").val();
		var auditState=$("#queryState").val();
		var str="?startTime="+startTime+"&endTime="+endTime+"&leaveStatus="+auditState+"&gradeId="+gradeId+"&classId="+classId+"&remark="+encodeURIComponent(remark);
		var url="${request.contextPath}/office/studentLeave/studentLeave-queryList.action"+str;
		load("#studentLeaveQuery",url);
	}
	
	function test(){
		var gradeId=$("#gradeId").val();
		load("#chanceId",'${request.contextPath}/office/studentLeave/studentLeave-leaveClassQuery.action?gradeId='+gradeId,function(){
			doSearch();
		});
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>