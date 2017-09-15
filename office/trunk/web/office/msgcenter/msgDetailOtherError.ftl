<#import "/common/htmlcomponent.ftl" as common>
<div class="msg-content">
	<div class="msg-opt fn-clear">
    	<div class="fn-left">
            <a href="javascript:void(0);" class="abtn-blue" onclick="loadMsgReceiveDiv(${receiveType!});">返回</a>
        </div>
    </div>
</div>
<script>
$(function(){
addActionError("信息缺失！");
});
</script>