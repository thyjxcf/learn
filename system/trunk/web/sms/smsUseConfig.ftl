<title>${webAppTitle}--</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<SCRIPT language=javascript src="${request.contextPath}/static/js/click.js" type=text/javascript></SCRIPT>
<SCRIPT language="javascript" src="${request.contextPath}/static/js/prototype.js" type="text/javascript"></SCRIPT>
<script language="javascript" src="${request.contextPath}/static/js/buffalo.js" type="text/JavaScript"></script>
<script>
function displayArchive(id){
	if(id==2){
		document.all.tab1.style.display = "";
		if(document.form1.smsNotUsed.checked){
			document.form1.smsNotUsed.checked = false;
		}
	}
	else{
		document.all.tab1.style.display = "none";
		if(document.form1.smsUsed.checked){
			document.form1.smsUsed.checked = false;
		}
	}
}

function clearValue(obj){
	if(obj){
		obj.value = "";
		obj.focus();
	}
}

var isSubmitting = false;
function onSubmit(){
	if(isSubmitting){
		return;
	}
	isSubmitting = true;
	var isSmsUsed;
	if($("smsUsed").checked){
		isSmsUsed = 1;
	}else{
		isSmsUsed = 0;
	}
	
	var clientId = $("clientId").value;
	if(clientId == "请输入短信客户帐号"){
		clientId = "";
	}
	
	var buffalo = new Buffalo("${request.contextPath}/sms");
	var promptMessages = [];
	promptMessages["clientId"] = "";
	
	buffalo.remoteActionCall( "smsUseConfig-remote.action","saveUseConfig",
		[isSmsUsed,clientId],
		function(reply){
			drawMessages(reply,function(){
				if(!reply.getResult().actionErrors && !reply.getResult().fieldErrors){			
					myRefresh();			
				}else{
					isSubmitting = false;
				}
			});
		} );
}

function myRefresh(){
	document.location.reload();
}
</script>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
  <tr>
    <td></td>
  </tr>
  <tr>
    <td height="10" class="padding_left"></td>
  </tr>
  <tr>
    <td class="padding_top2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="27" background="${request.contextPath}/static/images/stylemenu_zbg.gif" valign="bottom"><table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="9">&nbsp;</td>
              <td class="style_menu2" id="menu1" onClick="javascript:document.location.reload();">单位短信启用</td>
            <td >&nbsp;</td>
            <td>&nbsp;</td>
            <td width="10"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="30" bgcolor="#AABBFF" class="padding_left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="100%" valign="middle">
<form name="form1">
			<table width="80%" border="0" align="center" cellpadding="1" cellspacing="1" class="table_style">

              <tr>
                <td width="90" height="80" align="right" class="fontblue_bold">
			    <input type="radio" name="smsUsed" id="smsUsed" value="1" onclick="displayArchive(2);" <#if isSmsUsed! =='true'>checked</#if>/><label for="smsUsed">是</label></td>
                <td height="80" style="padding-top:0px;"><table border="0" cellpadding="0" cellspacing="0" name="tab1" id="tab1" style='display:<#if isSmsUsed! !='true'>none</#if>'>
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td width="20" height="26"></td>
                    <td rowspan="2"><#if isSmsUsed! =='true'>您的短信客户帐号：<#else>短信客户帐号验证：</#if>
                      <input name="textfield" id="clientId" type="text" value="<#if isSmsUsed! =='true'>${clientId!}<#else>请输入短信客户帐号</#if>"
					   style="width:150px;" class="input" onFocus="clearValue(this)" <#if isSmsUsed! =='true'>disabled</#if>><span id="clientIdError"></span></td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td height="26"></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="80" align="right" class="fontblue_bold"><input name="smsNotUsed" id="smsNotUsed" value="0" type="radio"  onclick="displayArchive(1);" value="radiobutton" <#if isSmsUsed! !='true'>checked</#if>/><label for="smsNotUsed">否</label></td>
                <td class="padding_left2">&nbsp;</td>
              </tr>
        </table>
</form>	
	</td>
  </tr>
  <tr>
    <td bgcolor="#C2CDF7" height="1"></td>
  </tr>
  <tr>
    <td bgcolor="#ffffff" height="1"></td>
  </tr>
  <tr>
    <td bgcolor="#C2CDF7" height="32" class="padding_left"><table width="99%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><table width="120"  border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
              <td align="right" id="checkedbutton"><div class="comm_button1" onmouseover  =   "this.className   =   'comm_button11';" onmousedown="this.className  =   'comm_button111';" onmouseout="this.className  =   'comm_button1';" onClick="onSubmit();">确定</div></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>