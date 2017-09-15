<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

$(function(){
load("#contectDiv", "${request.contextPath}/office/meetingmanage/receptionmanage-receptionManageList.action");
});
</script>
 
<div id="contectDiv"></div>
</@common.moduleDiv>