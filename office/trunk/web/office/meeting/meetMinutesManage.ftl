<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script>
	$(function(){
		vselect();
		doSearch();
	});
	function doSearch(){
		var meetName=$("#meetingName").val();
		var str="?meetingName="+encodeURIComponent(meetName);
		var url="${request.contextPath}/office/meeting/workmeeting-meetingMinutesManageList.action"+str;
		load("#meetMinutesManageListDiv",url);
	}
</script>

<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10">
					<span class="fn-left">会议名称：</span>
				</div>
				<div class="fn-left">
					<input type="text" class="input-txt" id="meetingName" name="meetingName" value="${meetingName!}"></input>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue ml-20">查找</a>
			</div>
    	</div>
    </div>
</div>

<div id="meetMinutesManageListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>