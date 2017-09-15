<#import "titlemacro.ftl" as titlemacro>
<#assign loginInfo=session.getAttribute("${appsetting.loginSessionName}")>
<#--<#assign preurl='http://'+request.serverName+':'+request.serverPort+request.contextPath+'/' />-->
<#assign preurl=request.contextPath+'/' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#if subSystem?exists>
<title>${subSystem.name}</title>
<#else>
<title>${appsetting.getWebTitle(appId)}</title>
</#if>
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<link href="${request.contextPath}/static/system/homepage/css/subsystem.css" type="text/css" rel="stylesheet">
<SCRIPT type="text/javascript" src="${request.contextPath}/static/js/click.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${request.contextPath}/static/js/cookie.js"></SCRIPT>
<SCRIPT type="text/javascript" src="${request.contextPath}/static/js/validate.js"></SCRIPT>
<script language="JavaScript" src="${request.contextPath}/static/js/util.js"></script>
</head>
<body>
<SCRIPT language=javascript>
linkset[1]=new Array();
linkset[1][0]='<div class="menuitems1" onMouseover=this.className="menuitems11" onMouseout=this.className="menuitems1"><a href="${request.contextPath}/${action.getText('eis.favorite.postfix')}" target="mainframe">我的收藏夹</a></div>';
<#switch appId?default(10)>
	<#case 50>
		linkset[1][1]='<div class="menuitems3" onMouseover=this.className="menuitems33" onMouseout=this.className="menuitems3"><a href="${request.contextPath}/downfolder/env.exe">环境下载</a></div>';
		<#break>
	<#case 51>
		linkset[1][1]='<div class="menuitems3" onMouseover=this.className="menuitems33" onMouseout=this.className="menuitems3"><a href="${request.contextPath}/downfolder/env.exe">环境下载</a></div>';
		<#break>
</#switch>
//linkset[1][1]='<div class="menuitems2" onMouseover=this.className="menuitems22" onMouseout=this.className="menuitems2"><a href="#">操作向导</a></div>'
//linkset[1][2]='<div class="menuitems3" onMouseover=this.className="menuitems33" onMouseout=this.className="menuitems3"><a href="#">退出选项</a></div>'
</SCRIPT>
<div class=menuskin id=popmenu onMouseOver="clearhidemenu();highlightmenu(event,'on')" style="z-index: 100" onMouseOut="highlightmenu(event,'off');dynamichide(event)"></div>
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td height="74"><@titlemacro.titleDiv/></td>
  </tr>
  <tr>
    <td><table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    
        <td width="144" valign="top" height="100%" id="leftframe"><iframe src="smallindexleft.action?appId=${appId}&moduleID=${moduleID}&parentId=${(curTab)?default('0')}" scrolling="no" name="frameleft" width="144" height="100%" frameborder="0"></iframe></td> 
		<td width="4" class="YecSpec13"><img src="${request.contextPath}/static/system/homepage/images/left_icon.gif" name="Image2" width="4" height="50" id="Image2" style="cursor:pointer" onClick="switchSysBar()"/></td>
        <td valign="top" width="100%">
        <table width="100%" height="100%" cellpadding="0" cellspacing="0" class="YecSpec7">
	        <tr><td>
				<table width="100%" height="20" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="fontblue2" align="left">
                  	<table border="0" cellspacing="0" cellpadding="0"><tr>
                  	  <td>
	                  	  <table id="navpic" height="100%" width="143px" style="display:none;padding-left:2px;" border="0" cellspacing="0" cellpadding="0"><tr>
						  <td><a href="#" onclick="displaySysBar()"><img src="${request.contextPath}/static/images/btn_back.gif" width="72" height="20" border="0"></a></td>
						  <td width="61"><!--<img src="${request.contextPath}/static/images/btn_ss.gif" width="61" height="20" border="0">--></td>
						  </tr>
						  </table>
					  </td>					  
                      <td><DIV id="navDiv" style="padding-left:8px;">您当前所在位置：导航</div></td>
                    </tr></table>
                  </td>
                  <td align="right">
                  	<table align="right" width="280"  height="20" border="0" cellspacing="0" cellpadding="0" id="displayFavorite">
		              	<tr>
		              	<td width="90">
		              	<table id="chinaExcelPrintPreview" style="display:none"  border="0" cellspacing="0" cellpadding="0" ><tr><td>
							<#if showCompanyLogo>
							<a href="#" onclick="javascript:chinaExcelPreview();"><img src="${request.contextPath}/static/system/homepage/images/icon_print.gif" width="22" height="20" style="border-style:none"></a>
							<#else>
							<a href="#" onclick="javascript:chinaExcelPreview1();"><img src="${request.contextPath}/static/system/homepage/images/icon_print.gif" width="22" height="20" style="border-style:none"></a>
							</#if>	
							</td><td>
							<#if showCompanyLogo>
							<span onclick="javascript:chinaExcelPreview();" onmouseover="this.style.cursor='hand'">打印预览</span>
							<#else>
							<span onclick="javascript:chinaExcelPreview1();" onmouseover="this.style.cursor='hand'">打印预览</span>
							</#if>							
							</td></tr>
						</table>
		              	</td>
		              	<td width="80">
		              	<table id="chinaExcelSave" style="display:none"  border="0" cellspacing="0" cellpadding="0" ><tr><td>
							<a href="#" onclick="javascript:chinaExcelSave();"><img src="${request.contextPath}/static/system/homepage/images/icon_execl.gif" width="22" height="20" style="border-style:none"></a>
							</td><td>
							<span onclick="javascript:chinaExcelSave();" onmouseover="this.style.cursor='hand'">导出...</span>
							</td></tr>
						</table>
		              	</td> 
        		        <td width="90">	
		              	<table style="display:none;" id="fav" border="0" cellspacing="0" cellpadding="0" ><tr><td>
							<a href="#" onclick="javascript:addFavorite();"><img src="${request.contextPath}/static/system/homepage/images/icon1.gif" width="22" height="20" style="border-style:none"></a>
							</td><td>
							<span onclick="javascript:addFavorite();" onmouseover="this.style.cursor='hand'">放入收藏夹</span>
							</td></tr>
						</table>       
                		</td>
                     			</tr>
		            </table>	        
		            </td>
                </tr>
              </table>
	        </td></tr>
	        <tr height="100%"><td>
<#if (moduleID > 0) && module?exists>
			<iframe src="${preurl}${module.url}?moduleID=${moduleID}&parentId=${module.parentid}&state=${state}" scrolling="yes" name="mainframe" width="100%" height="100%" frameborder="0"></iframe>
<#else>
        	<#if subSystem?exists>
				<iframe src="nav.action?appId=${subSystem.id}" scrolling="yes" name="mainframe" width="100%" height="100%" frameborder="0"></iframe>
			<#else>
				<iframe src="nav.action?appId=10" scrolling="yes" name="mainframe" width="100%" height="100%" frameborder="0"></iframe>
			</#if>
</#if>
          </td></tr>
        </table>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
<input type="hidden" name="url" vlaue="">
<input type="hidden" name="subSystem" vlaue="">
<input type="hidden" name="moduleId" vlaue="">
<input type="hidden" name="moduleName" vlaue="">
<input type="hidden" name="picture" vlaue="">
<script>
<#-- 如果curTab不为空，则记录到cookie中 -->
<#if curTab?default(0) != 0>
setCookie("tabindex${appId}${loginInfo.user.name}", ${curTab}, cookieSaveDate, "/");	
</#if>

var tabIndex=0;
tabIndex=getCookie("tabindex${appId}${loginInfo.user.name}");
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function switchSysBar(){
	with(document.all.leftframe.style){
		if(display){
			display=''
			MM_swapImage('Image2','','${request.contextPath}/static/system/homepage/images/left_icon.gif',1)
			document.getElementById('navpic').style.display='none';			
			}
		else{
			display='none'
			MM_swapImage('Image2','','${request.contextPath}/static/system/homepage/images/right_icon.gif',1)
			document.getElementById('navpic').style.display='';
			}
	}
}
<!-- 2007-04-10 zhaosf 当前模块id-->
var currentID = ${curTab};
function onClickHelp(obj){	
	var parms = "";	
	if(null == currentID || "" == currentID|| "undefined" == currentID){
		parms = "";			
	}else{
		parms = "&moduleID="+currentID;
	}
	obj.href = "${request.contextPath}/${action.getText('eis.help.postfix')}?appId=${appId?default(-1)}"+parms;
}
<!--    end   -->

function tabSelected(obj,id){
	currentID = id;
		
	var divObj;
	var i=0;
	//如果左边栏关闭，则展开左边栏
	if(document.getElementById('leftframe').style.display){
		switchSysBar();
	}
	
	//如果是点击当前tab，则返回
	if(obj.getAttribute('index')==tabIndex) return;
	var moduletab = document.getElementsByTagName("DIV");	
	for(var i = 0; i < moduletab.length; i ++){
		if (moduletab[i].getAttribute('index') == null){
			continue;
		}
		if (moduletab[i].getAttribute('index') != id){
			if (moduletab[i].className != "cc2"){
				moduletab[i].className = "cc2";
				moduletab[i].setAttribute("orginclass", "cc2");
			}
		}
	}
//	do{
//		divObj=document.getElementById("tab"+i);
//		if(divObj!=null){
//			if(divObj.className != "cc2"){
//	    		divObj.className="cc2";
//	    		divObj.setAttribute("orginclass","cc2");
 //   		}
//		}
//		i++;
//	}while(divObj!=null);
	//alert(this==null);
	obj.className="cc1";
	obj.setAttribute("orginclass","cc1");
	tabIndex=obj.getAttribute('index');
	setCookie("tabindex${appId}${loginInfo.user.name}", tabIndex, cookieSaveDate, "/");	
	frameleft.location="indexleft.action?parentId="+id + "&appId=${appId?default(10)}"
}
function tabSelected2(id){
	var divObj;
	var obj = document.getElementById("tab" + id);
	if (obj == null){
		return false;
	}
	var moduletab = document.getElementsByTagName("DIV");	
	for(var i = 0; i < moduletab.length; i ++){
		if (moduletab[i].getAttribute('index') == null){
			continue;
		}
		if (moduletab[i].getAttribute('index') != id){
			if (moduletab[i].className != "cc2"){
				moduletab[i].className = "cc2";
				moduletab[i].setAttribute("orginclass", "cc2");
			}
		}
	}
	obj.className="cc1";
	obj.setAttribute("orginclass","cc1");
	setCookie("tabindex${appId}${loginInfo.user.name}", id, cookieSaveDate, "/");
	tabIndex=getCookie("tabindex${appId}${loginInfo.user.name}");
}
function changeNavigation(moduleName){
	var navDiv=document.getElementById("navDiv");
	if(tabIndex == null){
		tabIndex = "${curTab}";
	}
	var tabDiv=document.getElementById("tab"+tabIndex);

	if (tabDiv == null){
		navDiv.innerHTML="";
	}
	else{
		navDiv.innerHTML="您当前所在位置："+tabDiv.innerText+" &gt;&gt; "+moduleName;
	}
	enablePrintSave(null);
}

function changeNavigation2(sectionName, moduleName){
	var navDiv=document.getElementById("navDiv");
	navDiv.innerHTML = "您当前所在位置："+sectionName+" &gt;&gt; "+moduleName;
	enablePrintSave(null);
}

function displaySysBar(){
	if(document.all.leftframe.style.display){
		switchSysBar();
	}	
}

var chinaExcelContainer;
var pageObj;
function enablePrintSave(obj,page){
	if(obj!=null){
		chinaExcelContainer=obj;
		document.getElementById('chinaExcelPrintPreview').style.display="";
		document.getElementById('chinaExcelSave').style.display="";
	}else{
		chinaExcelContainer=null;
		document.getElementById('chinaExcelPrintPreview').style.display="none";
		document.getElementById('chinaExcelSave').style.display="none";	
	}
	pageObj = page;	
}
function enablePrint(obj,page){
	if(obj!=null){
		chinaExcelContainer=obj;
		document.getElementById('chinaExcelPrintPreview').style.display="none";
		document.getElementById('chinaExcelSave').style.display="none";
	}else{
		chinaExcelContainer=null;
		document.getElementById('chinaExcelPrintPreview').style.display="none";
		document.getElementById('chinaExcelSave').style.display="none";	
	}
	pageObj = page;	
}
function chinaExcelPreview(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPreview();
	}else{
		alert("未找到超级报表控件！");
	}
}

function chinaExcelPreview1(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPreview1();
	}else{
		alert("未找到超级报表控件！");
	}
}

function chinaExcelPrint(obj){
	chinaExcelContainer = obj;
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPrint();
	}else{
		alert("未找到超级报表控件！");
	}
}

function chinaExcelSave(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelSave();
	}else{
		alert("未找到超级报表控件！");
	}
}

function addUrl(url, id, name, subSystem, picture){
	<#if openFavorite>
	document.getElementById("fav").style.display = "";
	</#if>
	document.getElementById("url").value = url;
	document.getElementById("subSystem").value = subSystem;
	document.getElementById("moduleId").value = id;
	document.getElementById("moduleName").value = name;	
	document.getElementById("picture").value = picture;	
}
function addFavorite(){
	var url = document.getElementById("url").value;
	if(url.indexOf('/') == 0)
		url = url.substring(1, url.length - 1);		
	var subSystem = document.getElementById("subSystem").value;
	var moduleId = document.getElementById("moduleId").value;
	var moduleName = document.getElementById("moduleName").value;
	var picUrl = document.getElementById("picture").value;
	picUrl = "stusys/" + picUrl;
	//url = encodeURIComponent(url);
	openwindow('${request.contextPath}/${action.getText('eis.addfavorite.postfix')}?operationType=4&picUrl=' + picUrl + '&moduleName=' + moduleName + '&moduleId=' + moduleId + '&subSystem=' + subSystem + '&url=' + url, '加入收藏', 540,400, "no");	
}
</script>
</body>
</html>