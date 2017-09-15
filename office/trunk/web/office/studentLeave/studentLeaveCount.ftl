<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as com>
<@common.moduleDiv titleName="" >
	<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt ml-10"><span class="fn-left">请假时间：</span></div>
			    <@common.datepicker name="queryStartTime" id="queryStartTime" style="width:120px;" value="${(startTime?string('yyyy-MM-dd'))?if_exists}"/>
			   	<div class="query-tt">&nbsp;-&nbsp;</div>
			    <@common.datepicker name="queryEndTime" id="queryEndTime" style="width:120px;" value="${(endTime?string('yyyy-MM-dd'))?if_exists}"/>
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
				<!--<div class="query-tt ml-10">
					<span class="fn-left">班级：</span>
				</div>
				<div class="fn-left">
					<@com.selectObject useCheckbox="false" idObjectId="classId" nameObjectId="className" url="${request.contextPath}/common/getClassDataPopup.action?preGraduateInclude=true" otherParam="showLetterIndex=true" width=800 callback="doSearch">
						<input type="hidden" name="classId" id="classId" value="">
						<input type="text" class="input-txt" name="className" id="className" value="">
					</@com.selectObject>
				</div>-->
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="studentLeaveCount"></div>
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
		var gradeId=$("#gradeId").val();
		var classId=$("#classId").val();
		var str="?startTime="+startTime+"&endTime="+endTime+"&classId="+classId+"&gradeId="+gradeId;
		var url="${request.contextPath}/office/studentLeave/studentLeave-countList.action"+str;
		load("#studentLeaveCount",url);
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