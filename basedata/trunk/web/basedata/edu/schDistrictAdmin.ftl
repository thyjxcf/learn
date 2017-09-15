<#import "../../common/htmlcomponent.ftl" as common />

<#assign ec=JspTaglibs["/WEB-INF/tld/extremecomponents.tld"]>
<html>
<head><title>学区基本信息管理</title>
<link rel="stylesheet" href="${request.contextPath}/static/css/extremecomponents.css" type="text/css">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css" type="text/css">
<script type="text/javascript">
function doEdit(id){
 	if (event.srcElement.name =="checkid"||event.srcElement.name =="checkbox1"){ 
 		return false;
 	}
 
	document.getElementById("ec").action="schDistrict-edit.action?id="+id;
	document.getElementById("ec").submit();
}

function add() {
	var eduid = document.getElementById("eduid").value;
	var url = "schDistrict-add.action?eduid=" + eduid;
	document.location.href=url;
}
   </script>
</head>
<body bgcolor="transparent">

<input type="hidden" name="eduid" value="${eduid?default('')}" >
<table width="100%"  border="0" cellspacing="0" cellpadding="0"  height="100%">
  <tr>
  	<td height="5"></td>
  </tr>
  <tr>
	<td height="20" align="right" class="padding_right">
	  <table width="80" align="right" border="0" cellpadding="0" cellspacing="0">
		<tr>
		  <td width="80" style="padding-left:150px;"><div class="comm_button21" onMouseover = "this.className = 'comm_button21';" onMousedown= "this.className = 'comm_button22';"onMouseout= "this.className = 'comm_button21';" onclick="add();" >新增</div></td>
		</tr>
	  </table>
	</td>
  </tr> 
      
      <tr>
        <td height="100%" valign="top">
    <div class="content_div">
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr>
	      <td width="45%" valign="top">	         
        
    <#assign action=request.contextPath+"/basedata/edu/schDistrict-Admin.action?eduid="+eduid />
	<@ec.table 
		items="schDistrictList"
		var="item"
		action="${action}"
		width="100%"
		view="custom"
		locale="zh_CN"
		>
		 
			 <@ec.row  highlightRow="true" interceptor="doEdit"> 	
			 
		       <@ec.column property="check"  title="选择" filterable="false" sortable="false" width="30">
				         <table width="100%" border="0" cellspacing="0" cellpadding="0">
				         	<tr><td name="checkbox" width="100%">
				         	<input type="checkbox" name="checkid" value="${item.id}" />
				         	</td></tr>
				         </table>
		      </@ec.column>

			  <@ec.column property="code" title="学区编号" filterable="false" width="15%"></@ec.column>
		      <@ec.column property="name" title="学区名称" filterable="false" width="30%"></@ec.column>
		      <@ec.column property="eduName" title="所属教育局" filterable="false" width="20%"> </@ec.column>
		      <@ec.column property="region"     title="区域范围" filterable="false" width=""> 
			     <#if item.region?exists>
			        <#if item.region.length() gt 20>
			         	${item.region?substring(0,20)}...
			      	<#else>
			      		${item.region?default("")}
			      	</#if>
			     </#if>	      
		      </@ec.column>
		      
		</@ec.row>
	</@ec.table>
		  </td>
	    </tr>
      </table>
      </div>	
	
       </td>
      </tr>
	  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	  <tr><td bgcolor="#ffffff" height="1"></td></tr>
	  <tr><td>
	    <@common.ECtoolbarWithDelete data=schDistrictList action=action checkboxname="checkid" deleteaction="schDistrict-remove.action?eduid=${eduid?default('')}" />
	  </td></tr>
	  	
	
	  
</table>

</html>
