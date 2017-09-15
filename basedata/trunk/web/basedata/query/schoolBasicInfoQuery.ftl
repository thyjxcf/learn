<#import "/common/htmlcomponent.ftl" as common />
<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<html>
<head>
<title>${webAppTitle}--学校基本信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css"	type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<script language="javascript">
//显示详细信息
function doEdit(schid){			
	formSearch.action	= "schoolGeneralInfoQuery-queryDetailSchool.action?schid=" + schid ;
	formSearch.submit();
}
//查询
function search(){
	
	var searchType = formSearch.searchType.value;
	if(searchType == "schoolInfo") {
		formSearch.action="schoolGeneralInfoQuery-queryBasicSchool.action";		
	}else if(searchType == "semesterInfo") {
		removeOrder();
		formSearch.action="schoolGeneralInfoQuery-queryBasicSemester.action";
	}else {
	    removeOrder();
		formSearch.action="schoolGeneralInfoQuery-queryBasicClass.action";
	}
		
	formSearch.submit();
}

function removeOrder() {

	var elems = document.formSearch.elements;
	for(var i=0;i<elems.length;i++) {
		if(elems[i].type == "hidden") {
			if(elems[i].value == "asc" || elems[i].value == "desc")
				elems[i].value = "";
		}
	}	
}



function onKeyDown(){
	if(event.keyCode!=13 ){
		return false;
	}
	else{
		search();
	}
}	
	
	
</script>
</head>
<body>

<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="YecSpec" height="100%">
	<form name="formSearch" id="formSearch"  method="post">
	
	<tr>
		<td height="45" class="padding_left">
			<table width="99%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td>				
						<table width="100%" cellpadding="0" cellspacing="0" border="0" >
							<tr>
								<td width="10">
								</td>
								<td width="100" align="right">
									请选择查询条件:
								</td>
								<td width="120">
									<select name="searchType" size="1" id="searchName">
									  <option value="schoolInfo" <#if searchType="schoolInfo">selected</#if>>学校基本信息</option>
									  <option value="semesterInfo" <#if searchType="semesterInfo">selected</#if>>学校学年学期信息</option>
									  <option value="classInfo" <#if searchType="classInfo">selected</#if>>学校班级信息</option>
							      </select>
								</td>
								<td width="100" align="right">
									请输入学校名称:
								</td>
								<td width="100">
									<input name="name" type="text" class="input" size="20" value="<#if name?exists>${name?default('')}</#if>" onKeyDown="onKeyDown();">
								</td>
								<td width="80">
									<div class="comm_button21"
										onMouseover="this.className = 'comm_button21';"
										onMousedown="this.className = 'comm_button22';"
										onMouseout="this.className = 'comm_button21';"
										onClick="search()">
										查询
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					
				  </td>
					
				</tr>
			</table>
		</td>
	</tr>
  <tr>
  <td height="100%" valign="top">	
		<div class="content_div">	
		
		<@common.tableList id="tableList">
		<tr>
		<th>学校名称</th>
		<th>学校代码</th>
		<th>学校类别</th>
		<th>学校校长</th>
		<th>联系电话</th>
		</tr>
		<#list basicSchoolInfo as sch>
		<tr style="cursor:pointer;" onclick="doEdit('${sch.id?default('')}', event);">
		<td>${sch.name?default('')}</td>
		<td>${sch.code?default('')}</td>
		<td>${appsetting.getMcode("DM-XXLB").get(sch.type?default(''))}</td>
		<td>${sch.shoolmaster?default('')}</td>
		<td>${sch.linkphone?default('')}</td>
		</tr>
		</#list>
		</@common.tableList>
		  </div>
   </td>

  
  
  </tr>
  </form>
	<tr><td>
 	  <@common.ToolbarBlank/>
 	  </td></tr>
</table>
</body>
</html>
