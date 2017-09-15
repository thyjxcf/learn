<#if appId == stack.findValue("@net.zdsoft.eis.base.common.entity.SubSystem@SUBSYSTEM_SYSTEM") || platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_STUPLATFORM")>
	<#assign loginInfo=session.getAttribute("${appsetting.loginSessionName}")>
	<#assign showName=loginInfo.unitName>
	<#assign username=loginInfo.user.realname>
<#else>
	<#assign loginInfo=session.getAttribute(stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@SESSION_BACKGROUND_LOGININFO"))>
	<#assign showName=stack.findValue("@net.zdsoft.eis.base.common.entity.SubSystem@getTitle("+appId+")")>
	<#assign username=loginInfo.username>
</#if>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${appsetting.getWebTitle(appId)}</title>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<link href="${request.contextPath}/static/system/homepage/css/subsystem.css" type="text/css" rel="stylesheet">
<SCRIPT type="text/javascript" src="${request.contextPath}/static/system/js/click.js"></SCRIPT>
<script src="${request.contextPath}/static/js/msn_message.js" language="javascript" type="text/javascript"></script>
<script>
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}
<!-- 交换图片 -->
function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function init(){
	var modID = "${modID?default("")}";
	if (modID != ""){
		for(j=1;j<25;j++){		
			var tdn=document.getElementById("td"+j);				
			if (tdn){		
				if(tdn.modID == modID){	
					tdn.className = "left_menuonmouse";
					changeCurrentName(tdn.modName);
				}
				else{
					tdn.className="left_menucomm";		
				}
			}
		}
	}
}

<!-- bar开关 -->
function switchSysBar(my){
	with(document.all.leftframe.style){
		if(display){
			display=''
			MM_swapImage('Image2','','${request.contextPath}/static/system/homepage/images/left_icon.gif',1)
			}
		else{
			display='none'
			MM_swapImage('Image2','','${request.contextPath}/static/system/homepage/images/right_icon.gif',1)
			}
	}
}

//去除字符串两边空格
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

<!-- 最大日志提醒 -->
var MSG;
function remindLog(){
	var	userLogMsg = "${userLogMsg?default('')}";
	if(userLogMsg == null || userLogMsg.Trim() == ""){
		return;
	}
	var msgstr = "<font color='#FF0000'>提醒：</font><br>"+userLogMsg+" <br>为了保证系统高效运行，请您清理相关日志!";
	MSG = new CLASS_MSN_MESSAGE("msg",220,120,"日志总数超过最大预设条数提醒",msgstr);  
    MSG.rect(null,null,null,screen.height-50); 
    MSG.speed = 10; 
    MSG.step = 5; 
    MSG.show();  	
}

<!-- 2007-04-10 zhaosf 当前模块id-->
var currentID ;
function onClickHelp(obj){
	var parms = "";	
	if(null == currentID || "" == currentID|| "undefined" == currentID){
		parms = "";			
	}else{
		parms = "&moduleID="+currentID;
	}
	<#if platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_TEACHER") >
		obj.href = "${request.contextPath}/${action.getText('eis.help.postfix')}?appId=${appId?default(0)}"+parms;
	<#else>
		obj.href = "${request.contextPath}/${action.getText('eis.common.help.postfix')}?appId=${appId?default(-1)}&platform=${platform}"+parms;
	</#if>
}


function changePassword(obj){
	var furl = "${request.contextPath}/stuplatform/login/stuPwdChange.action";
	openwin(furl,'',400,200);
}

</script>
</head>

<body onload="init();">
<div class="menuskin" id=popmenu onMouseOver="clearhidemenu();highlightmenu(event,'on')" style="z-index: 100" onMouseOut="highlightmenu(event,'off');dynamichide(event)"></div>
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
  <#if showHead?default(true)>
  <tr>
    <td height="61"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="64" background="${request.contextPath}/static/images/head_bg.jpg" valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="21" class="padding_left"><table height="24"  border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="5"></td>
                    <td width="81" height="23" align="center" onmouseover  =   "this.className   =   'top_bg2';" onMouseOut="this.className= 'top2_bg';"><img src="${request.contextPath}/static/system/homepage/images/icon_aboutus.gif" width="18" height="18" align="absmiddle" />
                      <a href="#" onclick="javascript:window.open('${request.contextPath}/${action.getText('eis.about.postfix')}?subSystem=${appId}','_blank','height=340,width=450,top=100,left=250')" > 关于我们</a> 
                    </td>                    
                    <td width="5"></td>
                    <td width="55" height="23" align="center" onmouseover  =   "this.className   =   'top_bg';" onMouseOut="this.className= 'top2_bg';"><img src="${request.contextPath}/static/system/homepage/images/icon_help.gif" width="18" height="18" align="absmiddle" />
                      <a href="#" onclick="javascript:onClickHelp(this);" target="_blank">帮助</a></td>
                    <td width="5"></td>
                    <#if appId == stack.findValue("@net.zdsoft.eis.base.common.entity.SubSystem@SUBSYSTEM_SYSTEM") || platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_STUPLATFORM")>
                    <td width="81" height="23" align="center" onmouseover  =   "this.className   =   'top_bg2';" onMouseOut="this.className= 'top2_bg';"><img src="${request.contextPath}/static/system/homepage/images/icon_back.gif" width="18" height="18" align="absmiddle" /> <a href="${request.contextPath}/${action.getText('eis.backhome.postfix')}">返回中心</a> </td>
                    </#if>
                    <#if platform == stack.findValue("@net.zdsoft.eis.base.constant.BaseConstant@PLATFORM_STUPLATFORM")>
                    <td width="81" height="23" align="center" onmouseover  =   "this.className   =   'top_bg2';" onMouseOut="this.className= 'top2_bg';" style="display:none"><img src="images/icon_tool.gif" width="18" height="18" align="absmiddle" border=0/> <a href="javascript:changePassword(this);">修改密码</a></td><td width="5"></td>
                    </#if>
                    </tr>
              </table></td>
            </tr>
            <tr>
              <td valign="bottom"><table width="100%" height="40"  border="0" cellpadding="0" cellspacing="0">
                  <tr>
                  	<!-- 2007-04-13 zhaosf 长度动态改变 182-400之间 --> 
                  	<#assign unitNameLen = showName.length()>
                  	<#if unitNameLen gt 10 >
                  		<#assign unitNameLen = unitNameLen * 15 + 30>
                  	<#else>  
                  		<#assign unitNameLen = 182>
                  	</#if>               	
                    <td width="${unitNameLen?default("182")}" class="title"><p>${showName}</p></td>
                    <td width="51"><img src="${request.contextPath}/static/system/homepage/images/headtitle_right.jpg" width="51" height="40" /></td>
                    <a href="nav.action?appId=${appId}" onclick="selectdogTab(0,'导航')" target="mainframe"><td width="50" valign="bottom" class="title_white" style="cursor:pointer"><img src="${request.contextPath}/static/system/homepage/images/icon_home.gif" width="18" height="18" border="0" align="absmiddle">导航</td></a>
                    <td valign="bottom" class="title_white">我的位置：<span id="currentposition">导航</span></td>
                  </tr>
              </table></td>
            </tr>
        </table></td>
        <td background="${request.contextPath}/static/images/head_bg.jpg" valign="bottom"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="40" class="padding_right"><img src="${request.contextPath}/static/system/homepage/images/logo.jpg" width="75" height="25" /></td>
            </tr>
            <tr>
              <td height="21" class="title_white2"><span class="title_white">${username?default('')}</span>&nbsp;您好！<a href="${request.contextPath}/<#if appId != stack.findValue("@net.zdsoft.eis.base.common.entity.SubSystem@SUBSYSTEM_BACKGROUND") >${action.getText('eis.logout.postfix')}<#else>${action.getText('eis.background.logout.postfix')}</#if>" ><span class="title_saffron">退出</span></a></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  </#if>
  <tr>
    <td valign="top"><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr bgcolor="#ebf8ff">
        <td width="155" height="100%" valign="top" id="leftframe" class="left_bttbg"><table width="155" border="0" cellspacing="0" cellpadding="0" height="100%">
            <tr>
              <td valign="top" height="100%">
				<table cellSpacing=0 cellPadding=0 width=155 border=0>
			  <#if topModules?default("") == "" || topModules.size() == 0>
				  <tbody id=tabs></tbody>
			  <#else>
				<#assign j = 1>
				<#list topModules as module>
				  <tbody>
				    <tr>
				      <td class="left_menutitle" id="headtitle" onclick="menu_display(${module_index});currentID=${module.id}">${module.name}</td>
				    </tr>
				    <#assign secondModuleList = secondModules.get(module.getId())>
				    <#if secondModuleList?default("") != "" >
				    <tbody id="tabs">
				    <tr>
				      <td class="left_contentbg"><table cellSpacing="0" cellPadding="0" width="90%" align="right" border="0">
				       <tbody>
				         <#list secondModuleList as secModule>
				            <tr>
				              <td><table width="90%" border="0" cellspacing="0" cellpadding="0" class="left_menutab">
				                <tr>
				                  <td width="22"><img  src="${request.contextPath}/${secModule.picture}" width="18" height="18"></td>
				                  <td id="td${j}" modName="${secModule.name?default("")}" modID="${secModule.mid?default("")}" class="left_menucomm" onClick="javascript:selectdogTab(${j},'${secModule.name}');currentID=${secModule.id}">
				                  	<a href="${request.contextPath}/${secModule.url}?appId=${secModule.subsystem}&modelId=${secModule.modelId?default('')}" target="mainframe">${secModule.name}</a>
				                  </td>
				                </tr>
				              </table></td>
				            </tr>
				            <#assign j = j+1>
				         </#list>
				         </tbody>          
				        </table></td>
				    </tr>
				    </#if>
				  </tbody>
				</#list>
			  </#if>
				</table>
				<SCRIPT language="javascript">
				<!--
					function initHide() {
						var tabslength = tabs.length;
						for (var i = 0; i < tabslength; i++) {
							tabs[i].style.display="none";
						}
					}
					initHide();
					menu_display(0);
				//-->
				</SCRIPT>
              </td>
            </tr>
        </table></td>
        <td class="YecSpec13"><img src="${request.contextPath}/static/system/homepage/images/left_icon.gif" name="Image2" width="4" height="50" id="Image1" style="cursor:pointer" onClick="switchSysBar(this)"/></td>
        <td width="100%" valign="top">
        	<iframe src="nav.action?appId=${appId}" scrolling="auto" name="mainframe" width="100%" height="100%" frameborder="0"></iframe>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<script language="javascript">
<#if appId == stack.findValue("@net.zdsoft.eis.base.common.entity.SubSystem@SUBSYSTEM_SYSTEM")>
<!-- 日志提醒-->
setTimeout("remindLog()",2000);
</#if>
</script>
</body>
</html>
