<script src="${request.contextPath}/static/js/login/jquery.cookies.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/md5.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/sha1.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/constants.js" type="text/javascript"></script>
<script src="${request.contextPath}/static/js/login/login.js" type="text/javascript"></script>
<script>
function identitySwitch(){
	var checkUserId = $("[name='choseUserName'][checked='checked']:input").val();
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/fpf/homepage/redIdentitie.action?checkUserId="+checkUserId,
		success: function(data){
			
			if(data.isconPassport){
				jQuery("#username").val(data.username);
				jQuery("#password").val(data.password);
				login.doLogin('${passportUrl}','${serverId}','${request.contextPath}/fpf/homepage/loginForEisOnly.action',<#if request.contextPath?default('') == ''>'1'<#else>'0'</#if>);
				return;
			}else{
				jQuery("#uid").val(data.username);
				jQuery("#pwd").val(data.password);
				login.saveCookie4EisLogin();
	 			$('#loginForm1').attr("action","loginForEisOnly.action").submit() ;
			}
			
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
</script>
<form id="loginForm" name="loginForm" method="post">
<input type="hidden" value="1" id="loginMode" name="loginMode"/>
<input type="hidden" id="verifyCode1" name="verifyCode1" class="txt" maxlength="4" />
<input type="hidden" id="username" name="username" value=""/>
<input type="hidden" id="password" name="password" value=""/>
</form>
<form id="loginForm1" name="loginForm1" method="post">
<input type="hidden" id="uid" name="uid" value=""/>
<input type="hidden" id="pwd" name="pwd" value=""/>
</form>
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#setLayer');return false;" class="close">关闭</a><span>身份切换</span></p>
<div class="wrap">
	<div class="ma-10">
    	<table border="0" cellspacing="0" cellpadding="0" class="table-list">
    	<#if showus?exists && showus?size gt 0>
			<#list showus as item>
            <tr>
                <td class="tit" width="20"><span class="ui-radio ui-radio-noTxt <#if checkUserId! == item.id>ui-radio-current</#if>" data-name="a"><input type="radio" class="radio" <#if checkUserId! == item.id>checked="checked"</#if> name="choseUserName" value="${item.id}"></span></td>
                <td class="tit t-center" width="50">
	                <#if item.ownerType?default(2) ==1>学生
	            	<#elseif item.ownerType?default(2) ==2>教师
	            	<#elseif item.ownerType?default(2) ==3>家长
	            	<#elseif item.ownerType?default(2) ==4>教育局职工
	            	<#else>其他</#if>
	            </td>
                <td>${item.realname!}</td>
            </tr>
        	</#list>
    	</#if>
        </table>
    </div>
</div>
<p class="dd">
    <a href="javascript:void(0);" class="abtn-blue submit" onclick="identitySwitch();return false;">确定</a>
</p>
