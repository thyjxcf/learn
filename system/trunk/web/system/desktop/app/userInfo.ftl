<script>
function sign(){
	var timeSetUrl = "${request.contextPath}/office/signmanage/signmanage-signmanageSave.action";
	var options = {
      url : timeSetUrl,
      success : showSuccess,
      type : 'post'
    };
  	$.ajax(options);
	
}
      
  //操作提示
  function showSuccess(data) {
    if (data!=null && data!=''){
      showMsgError(data);
      return;
    }else{
    	$("#countDiv").addClass("sign-in sign-in1");
    	$("#countDiv").text("已签到");
  }
}
</script>
<style type="text/css"> 
.sign-in1{position: absolute;top: 38px;right: 0;display: block;width: 60px;height: 31px;background: url(../../../static/images/sign_in.png) 0 0 no-repeat;padding-left: 10px;text-align: center;font: 100 16px/31px "Microsoft YaHei";color: #fcfcfd;}
.sign-in{position: absolute;top: 38px;right: 0;display: block;width: 60px;height: 31px;background: url(../images/sign_in.png) 0 0 no-repeat;padding-left: 10px;text-align: center;font: 100 16px/31px "Microsoft YaHei";color: #fcfcfd;}
.sign-in2{background: url(../images/sign_in2.png) 0 0 no-repeat;color: #898989;}
</style>
<script>
</script>
<div class="avatar"><img src="<#if userSet.downloadPath?default('') !=''>${userSet.downloadPath?default('')}<#else>${request.contextPath}/static/images/<#if loginInfo.user.sex?default('1')=='2'>avatar_woman.png<#else>avatar_man.png</#if></#if>"></div>
<p class="name">
      <#if loginInfo.user.realname?length lt 10>
			${loginInfo.user.realname!}
			<#else>
			${loginInfo.user.realname?default('')[0..9]}...
			</#if>

<a href="javascript:void(0);" onclick="userInfoSet();return false;" class="set"></a></p>
<p><a href="javascript:void(0);" onclick="userPhotoSet();return false;">修改头像</a><#if showPwdModify?default(true)><a href="javascript:void(0);" onclick="userInfoSet();return false;" class="ml-30">修改密码</a></#if>
<#if modelEsist>
<#if registOff>
<#if canSignedIn&&!signedIn><div id="countDiv"><a href="javascript:void(0);" onclick="sign();return false;" class="sign-in" >签到</a></div>
<#elseif signedIn><span class="sign-in sign-in1">已签到</span>
<#else><span class="sign-in sign-in2">签到</span>
</#if>
</#if>
</#if>
</p>
<p class="mt-10"><#if latestLoginTime?exists>上次登录时间：${latestLoginTime?string('yyyy-MM-dd HH:mm')}<#else>好久没见您了，欢迎回来！</#if></p>
