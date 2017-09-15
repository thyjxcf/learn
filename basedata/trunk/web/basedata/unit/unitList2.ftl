<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<#import "../../common/htmlcomponent.ftl" as common>
<html>
<head>
<title>${webAppTitle}--单位管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/validate.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script>
	function doEdit(unitId){
		var f=document.getElementById('ec');
		if(event){
			if(event.srcElement.name=='arrayIds'||event.srcElement.name=='checkbox'){
				return false;
			}
		}
		f.action = "unitAdmin-edit.action?modID=${modID?default('')}&&unitId=" + unitId + "&unitName="+
			document.getElementById('unitName').value;
		f.submit();
	}
	function insertUnit(){
		var f=document.getElementById('ec');
		f.action="unitAdmin-new.action?unitId=${unitId}&&modID=${modID?default('')}";
		f.submit();
	}
	function searchUnit(){
		var buffalo=new Buffalo('');
		var elem=document.getElementById('unitName');
		var unitName=elem.value;
		
		var div=document.getElementById('unitDiv');
		var noresultdiv=document.getElementById('noResultDiv');
		var resultdiv=document.getElementById('resultdiv');
		var noresultSpan=document.getElementById('noResultSpan');		
		
		//if(!checkElement(elem,'单位名称')){
		//	return false;
		//}
		
		if(unitName.indexOf('\'')>-1||unitName.indexOf('%')>-1){
			noresultSpan.innerHTML="请确认欲查询的单位名称不包含单引号、百分号等特殊符号！";
			noresultdiv.style.display='';
			return false;
		}		
		
		//直接对列表操作
		var f=document.getElementById('ec');
		f.action=encodeURI("unitAdmin.action?unitName="+unitName+"&modID=${modID?default('')}");
		f.submit();
		
		/**
		buffalo.remoteActionCall("unitAdmin-remoteUnitService.action","getUnitFaintness",[unitName],function(reply){
			var result=reply.getResult();
			if(result==null){
				noresultSpan.innerHTML="只有顶级单位管理员才能使用该功能！";
				noresultdiv.style.display='';
				div.style.display='none';
			}
			else if(result.length==0){
				noresultSpan.innerHTML="名称符合："+unitName+"的单位未搜索到！";
				noresultdiv.style.display='';
				div.style.display='none';
			}
			else{
				document.getElementById('searchUnitName').innerText=unitName;	
				var trobjlength=resultdiv.getElementsByTagName("tr").length;
				for(var i=0;i<trobjlength;i++){
					resultdiv.deleteRow();
				}	
				for(var i=0;i<result.length;i++){
					var mytr=resultdiv.insertRow();
					var mytd=mytr.insertCell(0);
					var html="<a href=\"javascript:doEdit('"+result[i].id+"');\">"+result[i].name+"</a>";
					mytd.innerHTML=html;
				}
				resultdiv.style.display='';
				noresultdiv.style.display='none';
				div.style.display='';
			}
			
		});
		*/
	}
	function deleteUnit(){		 
		var arrayIds=document.getElementsByName('arrayIds');
		var nums=0;
		var id='';
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i].checked){
				nums++;
				id=arrayIds[i].value;
			}
		}
		if(nums==0){
			alert('请选择欲删除的单位');
			return;
		}
		if(nums>1){
			alert('删除单位，将删除该单位下所有重要信息，且不可恢复；请确认单独删除！');
			return;
		}
		if(nums==1){
		 	if(!window.confirm('请确认确实要删除单位信息？')) return;
			document.forms.ec.action="unitAdmin-try2DeleteUnit.action";
			document.forms.ec.method="get";			
			document.forms.ec.submit();
		}
	}	
	function remoteRegister(){
		var f=document.getElementById('ec');
		f.action="unitAdmin-remoteRegister.action";
		f.submit();
	}
	function exportUnit(){
		document.getElementById('hiddenIframe').src="${request.contextPath}/basedata/unit/unitAdmin-export.action";
	}
	function auditUnit(){
		var f=document.getElementById('ec');
		f.action="unitAdmin-audit.action";
		f.submit();
	}
	
	function recommendUnit(){
		var f=document.getElementById('ec');
		f.action="unitAdmin-recommend.action";
		f.submit();
	}
	
	function createSmsAccounts(){
	  if(window.confirm('请确认要创建短信账号的单位？')){
		var arrayIds=document.getElementsByName('arrayIds');
		var nums=0;
		var ids='';
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i].checked){
				nums++;
				if(ids.length<=0){
  				  ids=arrayIds[i].value;
  				}else{
  				  ids+=","+arrayIds[i].value;
  				}
				
			}
		}
		if(nums==0){
			alert('请选择要创建短信账号的单位');
		}else if(nums>=1){
			document.forms.ec.action="unitAdmin-smsAccounts.action";
			document.forms.ec.method="get";			
			document.forms.ec.submit();
			
		}
	  }
	  return ;
	}
</script>
</head>
<body bgcolor="transparent"> 
<iframe id="hiddenIframe" style="display:none"></iframe>
<script type="text/javascript" language="javascript" src="${request.contextPath}/static/js/wz_dragdrop.js"></script>
<div id="unitDiv" style="position:absolute;width:250px;height:145px;z-index:99;border:2px solid #6C85CE;display:none;overflow:auto;">
  <table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  	<tr>
  	  <td>  	  	
  	  	<table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0">
  	  	  <tr><td class="send_titlefont">名称符合：<span id="searchUnitName"></span>的单位有：</td></tr>
  	  	  <tr>
  	  	  	<td><table id="resultDiv" height="100%" width="100%" cellspacing="0" cellpadding="0" border="0"></table></td>
  	  	  </tr>
  	  	</table>
  	  </td>
  	</tr>
  	<tr>
  	  <td align="right"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="document.getElementById('unitDiv').style.display='none';">返回</div></td>
  	</tr>
  </table>
</div>
<div id="noResultDiv" style="position:absolute;width:250px;height:145px;z-index:99;border:2px solid #6C85CE;display:none;overflow:auto;">
  <table height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec16">
  	<tr><td id="noResultSpan" style="color:#29248A;"></td></tr>
  	<tr><td align="right"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="document.getElementById('noResultDiv').style.display='none';">返回</div></td></tr>
  </table>
</div> 	  
<#assign htmlaction=request.contextPath+"/basedata/unit/unitAdmin.action?unitName="+unitName?default("") />
<table id="normalTable" height="100%" width="100%" cellspacing="0" cellpadding="0" border="0" class="YecSpec">
  <tr><td height="30" class="padding_left">
  	<table width="99%" border="0" cellpadding="0" cellspacing="0">  	  
  	  <tr>
  	  	<td align="right">
  	  	  <table height="100%" border="0" cellpadding="0" cellspacing="0">
  	  	  	<tr> 
  	  	  	<#--如果是产品部署 -->
  	  	  	<#if maintenanceUnit> 	  	  	  
  	  			<td align="right">
	  		  		<div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onClick="insertUnit();">新增</div>
  		  		</td>
  	  	  	</#if>
	  		 <#-- <td>
	  		  	<div class="comm_button1" onClick="recommendUnit();">单位推荐</div>
	  	  	  </td>-->
	  		  <td>
	  		  	<div class="comm_button1" onClick="auditUnit();">下属单位审核</div>
	  	  	  </td>
	  	  	  <#if useSmsCenter>
	  	  	  <td align="right">
	  		  	<div class="comm_button1" onClick="createSmsAccounts();">批量建短信账号</div>
	  	  	  </td>
	  	  	  </#if>
	  	  	  <td align="right">
	  		  	<div class="excel_btn" onClick="exportUnit();">导出excel</div>
	  	  	  </td>
	  	  	</tr>
	  	  </table>
	  	</td>
  	  </tr>
  	</table>
  </td></tr>
  <tr>
	<td height="100%" valign="top">
	  <div class="content_div">
		<form id="ec" action="${action}"  method="post" >
			<input type="hidden"  name="pageIndex"  value="${pageIndex!}" />
			<table  width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr>
				    <#if maintenanceUnit>
					<td class="tdtitle" width="5%">选择</th>
					</#if>
					<td class="tdtitle" width="20%">单位名称</th>
					<td class="tdtitle" width="15%">单位管理员</th>
					<td class="tdtitle" width="15%">单位分类</th>
					<td class="tdtitle" width="10%">注册日期</th>
					<td class="tdtitle" width="15%">当前状态</th>
					<td class="tdtitle" >统一编号</th>
				</tr>
				<#list listOfUnitDto as x>
				<tr height="20px;" valign="top" onMouseover="this.className='trmouseover';" 
					<#if maintenanceUnit>
					onclick="doEdit('${x.id!}');"
					</#if>
					onMouseout="this.className='<#if x_index % 2 == 1>trdual<#else>trsingle</#if>';" 
					class="<#if x_index % 2 == 1>trdual<#else>trsingle</#if>">
					<#if maintenanceUnit>
			 		<td class="tddata">
				 		<#if unitId?default('-1')==x.id?default('0')>
			      			<input type="checkbox" name="noArrayIds" disabled value="${x.id?default('')}" />	
				      	<#else>			         	
				        	<input type="checkbox" name="arrayIds" value="${x.id?default('')}" />				      
				      	</#if>
					</td>
					</#if>
					<td class="tddata" >
						${x.name?default("")}<#if unitId?default('-1')==x.id?default('0')><font color="red">（本单位）</font></#if>
					</td>
					<td class="tddata" >
						<#assign userDto = userMap[x.id?default("")]?default("")>
					  	<#if userDto!="">
					  		${userDto.name?default("")}
					  	</#if>
					</td>
					<td class="tddata" >
						${appsetting.getMcode("DM-DWFL").get(x.unitclass?default('')?string)}
					</td>
					<td class="tddata" >
						<#if x.creationTime?exists>${x.creationTime?string("yyyy-MM-dd")}</#if>
					</td>
					<td class="tddata" >
						<#if x.mark==unitMarkWithOutAutidt><font color="red"></#if>${appsetting.getMcode("DM-DWZT").get(x.mark?default('')?string)}<#if x.mark==unitMarkWithOutAutidt></font></#if>
					</td>
					<td class="tddata" >
						${x.unionid?default("")}
					</td>
				</tr>
				</#list>	
			</table>
		</form>	  	
	  </div>		
	  </td>
	</tr>
	<tr>
		<td bgcolor="#C2CDF7" height="1"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff" height="1"></td>
	</tr>
	<tr style="height:0px;"><td id="actionTip"></td></tr>
	<tr>
	  <td bgcolor="#C2CDF7" height="32">
	  	<table width="100%" >
			<tr>
				<td align="left" >	
				<#if maintenanceUnit>
					<input type='button' name='delete' onClick="deleteUnit();" value='删除' class='del_button1' onmouseover = "this.className = 'del_button3';" onmousedown= "this.className = 'del_button2';" onmouseout = "this.className = 'del_button1';">
				</#if>
						&nbsp;下属单位查询（根据单位名称查询）：<input name='unitName' id='unitName' type='text' value='${unitName?default("")}' onkeyup='if(event.keyCode==13) searchUnit();'>
						&nbsp;<input name='searchUnit' id='searchUnit' type='button' value='搜索' class='del_button1' onclick='searchUnit();'>
				</td>
				<#-- -->
				<td align="right"  >
					<div id="rightBar">${htmlOfPagination}</div>
				</td>
				
			</tr>
		</table>
	  </td>
	</tr>
</table>
<script type="text/javascript" language="javascript">
<!--
SET_DHTML(CURSOR_MOVE, "unitDiv");
//-->
</script>
</body>
</html>


