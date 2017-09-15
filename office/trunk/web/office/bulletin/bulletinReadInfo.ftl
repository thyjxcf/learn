<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="查看信息">
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#msgLayer');return false;" class="close">关闭</a><span>未读人员</span></p>
<div class="wrap userList-layer" id="memoEditDiv">
        <#list readInfoDtos as x>
	  <h3>${x.unitName!}<span>   ${x.userDeptNameList.size()!}人</span><span style="float:right;"><#if megAdmin><a href="javascript:void(0);" class="abtn-blue" onclick="sendMsg('${users!}','${bulletinId!}');">一键消息提醒</a></#if></span></h3>
	  <ul class="fn-clear">
		<#list x.userDeptNameList as str>
	        	<li>${str!}</li>
		</#list>
        </ul>
    	</#list>
</div>
            <p class="dd">
            	<a class="abtn-blue reset" onclick="closeDiv('#msgLayer');return false;" href="javascript:void(0);"><span>关闭</span></a>
            </p>
<script type="text/javascript">
function sendMsg(obj,bulletinId){
	$.ajax({
	        url:"${request.contextPath}/office/bulletin/bulletin-sendMsg.action",
	        dataType:"json",
	        data:{"users":obj,"bulletinId":bulletinId},
	        type:"post",
	        success:function(data){
		        if(!data.operateSuccess){
			  		 if(data.errorMessage!=null&&data.errorMessage!=""){
				  		 showMsgError(data.errorMessage);
				   		 return;
			  		 }
				}else{
					if(data.promptMessage != null && data.promptMessage != ""){
						showMsgSuccess("操作成功！", "提示", function(){
					});
				}
				return;
				} 
	        },
	        dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	    });


	//$.getJSON("${request.contextPath}/office/bulletin/bulletin-sendMsg.action", {
	//	"users":obj,"bulletinId":bulletinId
	//	}, function(data){
	//		if (!data.operateSuccess){
	//			if(data.errorMessage!=null&&data.errorMessage!=""){
	//			   showMsgError(data.errorMessage);
	//			   return;
	//		    }
	//		}else{
	//			if(data.promptMessage != null && data.promptMessage != ""){
	//				showMsgSuccess("操作成功！", "提示", function(){
	//			});
	//			}
	//			return;
	//		}
	//}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(textStatus);alert(XMLHttpRequest.status);
	//});
}
</script>
</@common.moduleDiv>