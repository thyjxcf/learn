<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${request.contextPath}/static/css/layout.css" type="text/css" rel="stylesheet">
<link href="${request.contextPath}/system/css/favorite.css" type="text/css" rel="stylesheet">
<SCRIPT language=javascript src="${request.contextPath}/static/js/click.js" type=text/javascript></SCRIPT>
<#assign defaultId = "00000000000000000000000000000000">
<#assign space="">
</head>
<body onload="init();">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="YecSpec" height="100%">
	<tr>
		<td></td>
	</tr>      
	<tr>
		<td height="30" class="padding_left">
		<table width="99%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="70">当前目录：</td>
				<td width="80">
				<select name="select3" style="width:120px;" onchange="javascript:selectChanged();" id="floderSelect">
					<option value="${defaultId}">根目录</option>
<#list favoriteFloderList as ele>
					<option value="${ele.id?default('')}" <#if (ele.id == nowId?default(defaultId))>selected</#if>>${ele.moduleName?default('')?trim}</option>
</#list>
				</select>
				</td>
				<td width="50">
				<div class="comm_button1" onclick="returnUp();">返回上级目录</div>
				</td>
				<td align="right">
				<div class="comm_button1" onclick="<#if listType ==0 >doEdit();<#else>doReturn();" </#if>><#if listType ==0 >管理<#else>返回</#if></div>	
				</td>
			</tr>
		</table>
		</td>
	</tr>
    <tr>
		<td height="100%" valign="top" class="padding_left">
		<table width="90%" border="0">
			<tr>
				<td class="padding_left">
<#if (listType == 1)>
				<table width="250" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><div class="fav1_btn" onclick="javascript:addFavorite(3);">新增外部链接</div></td>
						<td><div class="fav2_btn" onclick="javascript:addFavorite(2);">新建文件夹</div></td>
					</tr>
				</table>
<#else>
				&nbsp;
</#if>
				</td>
			</tr>
			<tr>
				<td class="padding_left">
				<table width="90%" border="0" cellpadding="0" cellspacing="0">
<#assign columns = 4>
<#list favoriteList as ele>
	<#if ele_index % columns == 0>
					<tr>
	</#if>
						<td width="25%" align="center">
						<table width="80%" border="0" align="center" cellspacing="0" cellpadding="0">
							<tr>
<#if (listType == 1)>							
								<td align="center" onmouseover="this.style.cursor='hand';Layer${ele_index}.style.visibility=''" onMouseOut="Layer${ele_index}.style.visibility='hidden'">
<#else>
								<td align="center">
</#if>								
								<table border="0" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td align="center" >
										<img <#if listType == 0> onmouseover="this.style.cursor='hand'"</#if> onclick="javascript:gotoModule('${request.contextPath}/${ele.url?default(space)}', '${ele.type?default(space)}', '${ele.id}', '${ele.moduleId}','${ele.subSystem?default(10)}')" src="${request.contextPath}/${ele.picUrl}" width="32" height="32" border="0">
										</td>
									</tr>
<#if listType == 1>
									<tr>
										<td  align="left" >
											<div id="Layer${ele_index}" style="visibility:hidden;position:absolute; width:123px; height:35px; z-index:1; " onmouseover="this.style.visibility=''" onMouseOut="this.style.visibility='hidden'" >
												<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
													<tr> 					  	
														<td valign="top" class="folder_layer" style="padding-left:5px;">
														<img src="${request.contextPath}/static/images/space.gif" width="1" height="1" border="0">								
														<table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td style="cursor:pointer;"onClick="javascript:updateFavorite('${ele.id}');">
																<img src="${request.contextPath}/static/images/ico_1.gif" width="13" height="15" hspace="5" border="0" align="absmiddle">
																<span class="font_blue">修改</span>
																</td>
																<td style="cursor:pointer;padding-left:5px;" onClick="javascript:deleteFavorite('${ele.id}');"><img src="${request.contextPath}/static/images/ico_2.gif" width="15" height="15" hspace="5" align="absmiddle" >
																<span class="font_blue">删除</span>
																</td>
															</tr>
														</table>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
</#if>
									<tr>
										<td align="center" class="fontblue">${ele.moduleName}</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
	<#if (ele_index + 1) % columns == 0>
					</tr>
					<tr height="10">
						<td>
						&nbsp;
						</td>
					</tr>
	</#if>
	
</#list>
<#if (favoriteList.size()%columns > 0)>
						<td colspan="${columns - favoriteList.size()%columns }">&nbsp;</td>
					</tr>	
</#if>
				</table>
			</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<script>
function init(){
	//top.changeNavigation2("${sectionName?default('')}", "${mName?default('')}");
	//top.document.getElementById("displayFavorite").style.display = "none";
}
function gotoModule(url, type, id, moduleID,subSystem){
	//0 pb模块，1 java模块， 2 目录， 3 外部链接
	if(type == 2){ 
		location.href = "favorite.action?nowId=" + id + "&listType=${listType}";
	}else if(type == 1){
		if (moduleID != "CUSTOM_MODULE_ID"){
			parent.parent.redirectUrl(url,subSystem);
		}
	}else if(type == 0){
		if(moduleID == null){
			moduleID = url;
		}
		openRunWindow(moduleID);
	}else{
		var tempUrl = url.toLowerCase();
		if (tempUrl.indexOf("http://") == 0){
			window.open(url);
		}else{
			window.open("http://" + url);
		}
	}
}
function selectChanged(){
	var id = document.getElementById("floderSelect").value;
	location.href = "favorite.action?nowId=" + id + "&listType=${listType}";
}
function returnUp(){
	location.href = "favorite.action?nowId=${mParentId?default(defaultId)}" + "&listType=${listType}";
}
function newFavoriteFloder(){
	var id = document.getElementById("floderSelect").value;
	location.href = "newFavoriteFloder.action?nowId=" + id + "&listType=${listType}";
}
function addFavorite(type){
	var id = document.getElementById("floderSelect").value;
	location.href = "addFavorite.action?nowId=" + id + "&operationType=" + type + "&listType=${listType}";
}
function updateFavorite(id, type){
	location.href="addFavorite.action?favoriteId=" + id + "&listType=${listType}";
}
function deleteFavorite(id){
	if(confirm("确定要删除吗？")){
		location.href="deleteFavorite.action?favoriteId=" + id + "&listType=${listType}";
	}
}
function doEdit(){
	location.href="favorite.action?nowId=${nowId}&listType=${1-listType}";
}
function doReturn(){
	location.href="${request.contextPath}/desktop/common/favorite/favorite.action";
}
function openRunWindow(moduleId, contextPath) {
  var width = 200;
  var height = 50;
  var left = (screen.width - width) / 2;
  var top = (screen.height - height) / 2;
	
	if (contextPath != null) {
		window.open(contextPath + "run.action?moduleID=" + moduleId, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,dependent=yes,alwaysRaised=yes,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top);
	}
	else {
  		window.open("../stusys/sch/run.action?moduleID=" + moduleId, "", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,dependent=yes,alwaysRaised=yes,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top);
	}
}
</script>
</body>
</html>
