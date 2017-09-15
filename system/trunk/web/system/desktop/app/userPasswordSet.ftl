<!--
<script type="text/javascript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
-->
<script>
function saveUserInfo(){

}
var isSubmit =false;
function updatePassword(){
	if(isSubmit){
		return;
	}
	isSubmit =true;
	var oldPassword=$("#oldPassword").val();
	var newPassword=$("#newPassword").val();
	var confirmPassword=$("#confirmPassword").val();
	getJSON("${request.contextPath}/system/desktop/app/userInfo-remote!updateEasyPassword.action",{"oldPassword":oldPassword,"newPassword":newPassword,"confirmPassword":confirmPassword},"showResultMsg(data)");
}

function showResultMsg(data){
	var type=data.message.split("&")[0];
	var msg =data.message.split("&")[1];
	if(type == "success"){
		showMsgSuccess(msg,"",function(){closeDiv('#setLayer');});
	}else{
		isSubmit =false;
		jQuery("#passwordError").html("<img src='${request.contextPath}/static/images/icon3.gif' />"+msg);
		//showMsgError(msg);
	}
}


function changePwdRank() {
	var rank = PwdIntensity($("#newPassword").val());
	printIntensity(rank);
}

$(document).ready(function(){
	//个人设置
	$('#setLayer .set-tab li').click(function(){
		$(this).addClass('current').siblings().removeClass();
		$('#setLayer .set-wrap:eq('+$(this).index()+')').show().siblings('.set-wrap').hide();
	});
})

function onChangeTab(type){
	if("2" == type){
		$('#modifyPassword').hide();
		$('#modifyPersonnelInfo').show();
		load("#modifyPersonnelInfo","${request.contextPath}/basedata/teacher/common/teacherAdmin-person-edit.action?id=${teacherId!}&isDesktop=true");
	}else{
		$('#modifyPassword').show();
		$('#modifyPersonnelInfo').hide();
	}
}
</script>
<p class="tt">密码修改</p>
<div class="wrap">
	<ul class="set-tab">
    	<li class="current" onclick="onChangeTab('1');">修改密码</li>
    </ul>
    <div id="modifyPassword">
	    <div class="set-wrap" >
	    	<div class="t-center"><span style="font-size:20px" >${unitName!}</span></div> 
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt-30">
	    		<tr>
	                <td colspan="2"><span style="color:red"id="passwordError">密码规则：${pwdRuleStr!}</span></td>
	            </tr>
	            <tr>
	                <th>原密码：</th>
	                <td><input id="oldPassword" type="password" class="txt"><span style="color:red"id="passwordError"></span></td>
	                
	            </tr>
	            <tr>
	                <th>新密码：</th>
	                <td><input id="newPassword" type="password" class="txt" ></td>
	            </tr>
	            <#--
	            <tr class="password-verify">
	                <th>&nbsp;</th>
	                <td>
	                	<div class="tips">密码安全程度：</div>
	                    <div id="safetyRange" class="acc-safety acc-safety-low fn-left">
	                    	<span class="low">弱</span>
	                    	<span class="center">中</span>
	                    	<span class="high">强</span>
	                    </div>
	                </td>
	            </tr>
	            -->
	            <tr>
	                <th>确认密码：</th>
	                <td><input id="confirmPassword" type="password" class="txt"></td>
	            </tr>
	        </table>
	    </div>
	    <p class="dd">
		    	<a href="javascript:void(0);" onclick="updatePassword();return false;" class="abtn-blue submit">保存</a>
		</p>
	</div>	
    <div id="modifyPersonnelInfo" class="set-wrap" style="display:none;">
    	
    </div>
</div>
