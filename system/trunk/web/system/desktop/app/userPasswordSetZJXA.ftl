<!--
<script type="text/javascript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
-->
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/validate.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/handlefielderror.js"></script>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/strspell.js"></script>
<script type="text/javascript" language="JavaScript" src="${request.contextPath}/static/js/pwdintensity.js"></script>
<script>
function saveUserInfo(){

}
var isSubmit =false;
function updatePassword(){
	if(!checkAllValidate()){
		return ;
	}
	if(!checkMobilePhone(document.getElementById("mobilePhone"))){
		return false;
	}
	if(isSubmit){
		return;
	}
	isSubmit =true;
	var oldPassword=$("#oldPassword").val();
	var newPassword=$("#newPassword").val();
	var confirmPassword=$("#confirmPassword").val();
	var teachName=$("#teachName").val();
	var mobilePhone=$("#mobilePhone").val();
	var identityCard=$("#identityCard").val();
	var loginName = '';
	for(var i=0;i<teachName.length;i++){
			loginName += teachName.charAt(i).toSpell().charAt(0);
	}
	var username=loginName+identityCard.substring(identityCard.length-6,identityCard.length);
	getJSON("${request.contextPath}/system/desktop/app/userInfo-remote!updateEasyPasswordZJXA.action",{"oldPassword":oldPassword,"newPassword":newPassword,"confirmPassword":confirmPassword,"username":username,"teacherName":teachName,"mobilePhone":mobilePhone,"identityCard":identityCard},"showResultMsg(data)");
}

function showResultMsg(data){
    $(".field_tip").hide();
	var type=data.message.split("&")[0];
	var msg =data.message.split("&")[1];
	var fieldId =data.message.split("&")[2];
	if(type == "success"){
		showMsgSuccess(msg,"",function(){
		closeDiv('#setLayer');
		window.location.href="${request.contextPath}/${action.getText('eis.logout.postfix')}";
		});
	}else{
		isSubmit =false;
		//jQuery("#passwordError").html("<img src='${request.contextPath}/static/images/icon3.gif' />"+msg);
		//showMsgError(msg);
		addFieldError($("#"+fieldId)[0],msg);
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
<p class="tt">信息修改</p>
<div class="wrap">
	<ul class="set-tab">
    	<li class="current" onclick="onChangeTab('1');">修改信息</li>
    </ul>
    <div id="modifyPassword">
	    <div class="set-wrap" >
	    	<div class="t-center"><span style="font-size:20px" >${unitName!}</span></div> 
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mt-30">
	    		<tr>
	                <td colspan="2"><span style="color:red"id="passwordError" notNull="true">密码规则：${pwdRuleStr!}</span></td>
	            </tr>
	            <tr>
	                <th>原密码：</th>
	                <td>
		                <input id="oldPassword" type="password" msgName="原密码" class="txt" notNull="true" maxlength="18">
		                <span style="color:red"id="passwordError"></span>
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
	            </tr>
	            <tr>
	                <th>新密码：</th>
	                <td>
		                <input id="newPassword" type="password" msgName="新密码" class="txt" notNull="true" maxlength="18">
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
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
	                <td>
		                <input id="confirmPassword" msgName="确认密码" type="password" class="txt" notNull="true" maxlength="18">
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
	            </tr>
	            <tr>
	                <th>姓名：</th>
	                <td>
		                <input id="teachName" type="text" msgName="姓名" class="txt" notNull="true" maxlength="10">
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
	            </tr>
	            <tr>
	                <th>身份证号：</th>
	                <td>
		                <input id="identityCard" type="text" msgName="身份证号" class="txt" notNull="true" maxlength="20">
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
	            </tr>
	            <tr>
	                <th>手机号：</th>
	                <td>
		                <input id="mobilePhone" type="text" msgName="手机号" class="txt" notNull="true" maxlength="20">
		                <span class="c-orange mt-5 ml-10">*</span>
	                </td>
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
