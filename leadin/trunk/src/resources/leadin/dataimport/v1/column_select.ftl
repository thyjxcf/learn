<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "/common/css.ftl">
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/switch"></script>

<script language="JavaScript" src="${request.contextPath}/static/js/prototype.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
</head>
<script language="javascript">
jQuery.noConflict();

function cboxall(yn){
	for(var i=0;i<document.forms.form1.length;i++){
		if(document.forms.form1.elements[i].type=="checkbox"&&!document.forms.form1.elements[i].disabled){
			document.forms.form1.elements[i].checked=yn;
		}
	}
}
function submitform() {
	var form1=document.getElementById('form1');
	if(form1){
		form1.action="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-gettemplate.action";
		form1.target="hiddenIframe";
		form1.submit();
	}
}
function goback() {
	var params = "";
	<#list paramsList as x>
		params += "&${x[0]?default("")}=${x[1]?default("")}";
	</#list>
	document.location.href="${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}.action?objectName=${objectName}"+params;
}

var buffalo=new Buffalo('');
buffalo.async = false; //同步执行 
function saveTemplate(){
	var cols = new Array();
	var row=0;
	var cks = document.getElementsByName("ckb");
	for(var i=0;i<cks.length;i++){
		if(cks[i].type=="checkbox" && cks[i].checked  && cks[i].disabled == false){
			cols[row++] = cks[i].value;
		}
	}
	
	var args = null;
	if(cols.length == 0){
		args = ["${viewParam.importFile!}","${objectName}"];
	}else{
		args = ["${viewParam.importFile!}","${objectName}",cols];
	}
	buffalo.remoteActionCall("${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}-remoteTemplateConfig.action","saveTemplates",args,function(reply){
		var result=reply.getResult();		
		drawMessages(reply);
	}); 
}

jQuery(document).ready(function(){
	jQuery(".table-content").height(jQuery(".tabFrame", window.parent.document).height()-jQuery('.head-tt').height()-jQuery('.table-header').height()-jQuery('.table1-bt').height());
})
</script>
<body>
<form id="form1" name="form1" method="post">
<#list paramsList as x>
<input type="hidden" name="${x[0]?default("")}" value="${x[1]?default("")}">
</#list>
<div class="head-tt">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  	  <tr>
	  	  	<td height="30"><b>&nbsp;请选择模板中需要的数据项</b>&nbsp;&nbsp;&nbsp;&nbsp;${templateRemark?default("")}</td> 
	  	  </tr>
	  	</table>
</div>
<div class="table-all">
    <div class="table-header">
        <!--[if lte IE 6]>
    	<div style="position:absolute;z-index:-1;width:851px;height:30px;">  
        	<iframe style="width:851px;height:30px;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
        </div> 
        <![endif]-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1">
            <tr id="copy_tr">
            </tr>
        </table>
    </div>
    <div class="table-content">
    	<!--[if lte ie 8]> <div style="+zoom:1"><![endif]-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table1">
            <tr id="pri_tr">
                <th width="10%">选择</th>
                <th width="15%">字段名称</th>
                <th width="25%">字段说明</th>
                <th width="50%">字段范例</th>
            </tr>
           <#list listOfNode as column>		    
			<tr >
				<td>
					<#assign disabled = column.disabled>
					<#assign color = "">
					<#if !templateConfig?default(false) && column.checked && !disabled><#-- 下载模板时，只要checked则就不能取消 -->
						<#assign disabled = true>
						<#assign color = "blue">
					</#if>
                	<#if disabled>
						<input type="checkbox" <#if column.checked>checked</#if> <#if column.defaultChecked>checked</#if> disabled name="ckb" >	
						<input type="hidden" name="ckb" value="${column.name}">
					<#else>						
						<input type="checkbox" <#if column.checked>checked</#if> <#if column.defaultChecked>checked</#if> name="ckb" value="${column.name}">						
					</#if>
                </td>
                <td><span style="color:${color}">${column.define}</span></td>
                <td>
                	<#if ((column.type)!"") =="Select">
                		<select style="width:150:px;">
            				<option>--请选择--</option>
                			<#list column.selectItems as constrains>
                				<option <#if ((column.defaultValue)!"") == (constrains!"")>selected</#if>>${constrains!""}</option>
                			</#list>
                		</select>
                	<#elseif column.getMcode()!= "" && column.getMcode()!= "Region">
						<select style="width:150px;" >${appsetting.getMcode( column.getMcode() ).getHtmlTag(column.defaultValue?default(''))}</select>
					<#else>
						<#if column.type=="String">
							字符型，限长 ${column.strLength?default("")} 个字符
						<#elseif column.type=="Datetime" || column.type=="Date" || column.type=="Timestamp" || column.type=="YearMonth">
							日期型
						<#else>
							数值型，${column.precision?default("")}位整数 <#if column.decimal?default(0) !=0>${column.decimal?default("")}位小数</#if>
						</#if>
					</#if>
                </td>
                <td>${column.example?default("")}</td>
			</tr>
		</#list>		
        </table>
        <!--[if lte ie 8]></div><![endif]-->
    </div>
</div>
<div class="table1-bt t-center">
    <!--[if lte IE 6]>
    <div style="position:absolute;z-index:-1;width:100%;height:100%;">  
        <iframe style="width:100%;height:100%;border:0;filter:alpha(opacity=0);-moz-opacity:0"></iframe>  
    </div> 
    <![endif]-->
    <#if !templateConfig?default(false)>
	<span>请选择模板的数据格式：
		<select name="filetype">
			<option value="1" selected>EXCEL文件</option>				
		</select>
		<input type="hidden" name="objectName" value="${objectName}">
	</span>
	</#if>
    <span class="input-btn2" onclick="cboxall(true);"><button type="button">全选</button></span>
    <span class="input-btn2" onclick="cboxall(false);"><button type="button">取消全选</button></span>
    <#if templateConfig?default(false)>
    <span class="input-btn2" onclick="saveTemplate();"><button type="button">保存模板</button></span>
    </#if>
    <span class="input-btn2" onclick="submitform();"><button type="button">导出模板</button></span>
    <span class="input-btn2" onclick="goback();"><button type="button">返回</button></span>
</div>
<script>
jQuery('.table-content').height(jQuery(".mainFrame", window.parent.parent.document).height() - jQuery(".tab-bg", window.parent.document).height() - jQuery('.table1-bt').height() - jQuery('.head-tt').height() - 5);
</script>
</form>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none">
</body>
</html>