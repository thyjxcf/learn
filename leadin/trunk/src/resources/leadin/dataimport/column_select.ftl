<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script language="javascript">
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
	var url = "${request.contextPath}${pageParam.namespace}/${pageParam.mainActionName}.action?objectName=${objectName}"+params;
	load('#importDiv',url);
}

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
		args = {'importFile':"${viewParam.importFile!}",'objectName':"${objectName}"};
	}else{
		args = {'importFile':"${viewParam.importFile!}",'objectName':"${objectName}",'cols':cols};
	}
	$.post("${request.contextPath}/leadin/import/jsonSaveTemplates",args,function(data){
		drawMessages(data.reply);
	}, 'json').error(function() {
		showMsgError("jsonSaveTemplates error");
    });  
}
</script>
<form id="form1" name="form1" method="post">
<#list paramsList as x>
<input type="hidden" name="${x[0]?default("")}" value="${x[1]?default("")}">
</#list>
<p class="pb-15">请选择模板中需要的数据项：&nbsp;&nbsp;&nbsp;&nbsp;${templateRemark?default("")}</p>
<div class="base-operation">
	<p class="opt fn-left">
    	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
    </p>
	<#if !templateConfig?default(false)>
    <p class="opt fn-left">
        <span class="fn-left">请选择模板的数据格式：</span>
	 	<div class="ui-select-box fn-left mt-15" style="width:100px;">
            <input type="text" class="ui-select-txt" value="" />
            <input type="hidden" value="" class="ui-select-value" name="filetype"/>
            <a class="ui-select-close"></a>
            <div class="ui-option">
            	<div class="a-wrap">
                <a val="1" class='selected'><span>EXCEL文件</span></a>
                </div>
            </div>
        </div>
		<input type="hidden" name="objectName" value="${objectName}">
    </p>
	</#if>
    <p class="opt fn-left">
    	<#if templateConfig?default(false)>
		<a href="javascript:void(0);" class="abtn-blue-big" onclick="saveTemplate();">保存模板</a>
	    </#if>
	    <a href="javascript:void(0);" class="abtn-blue-big" onclick="submitform();">模板下载</a>
	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="goback();">返回</a>
    </p>
</div>
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
    <tr>
        <th width="30">选择</th>
        <th>字段名称</th>
        <th>字段说明</th>
        <th>字段范例</th>
    </tr>
   <#list listOfNode as column>		    
	<tr >
		<td class="t-center"><p>
			<#assign disabled = column.disabled>
			<#assign color = "">
			<#if !templateConfig?default(false) && column.checked && !disabled><#-- 下载模板时，只要checked则就不能取消 -->
				<#assign disabled = true>
				<#assign color = "blue">
			</#if>
			<span class="ui-checkbox <#if column.checked || column.defaultChecked><#if disabled>ui-checkbox-disabled-checked<#else>ui-checkbox-current</#if></#if>">
        	<#if disabled>
				<input type="checkbox" class="chk" <#if column.checked>checked</#if> <#if column.defaultChecked>checked</#if> disabled name="ckb" >	
				<input type="hidden" name="ckb" value="${column.name}">
			<#else>						
				<input type="checkbox" class="chk" <#if column.checked>checked</#if> <#if column.defaultChecked>checked</#if> name="ckb" value="${column.name}">						
			</#if>
			</span></p>
        </td>
        <td><span style="word-break:break-all;color:${color};"><@htmlmacro.cutOff4List str="${column.define}" length=10/> </span></td>
        <td>
        	<#if ((column.type)!"") =="Select">
        		<div class="ui-select-box fn-left" style="width:150px;">
                    <input type="text" class="ui-select-txt" value="" />
                    <input type="hidden" value="" class="ui-select-value" />
                    <a class="ui-select-close"></a>
                    <div class="ui-option">
                    	<div class="a-wrap">
                    	<a val=""><span>--请选择--</span></a>
                    	<#list column.selectItems as constrains>
                    		<a val="" <#if ((column.defaultValue)!"") == (constrains!"")>class='selected'</#if>><span>${constrains!""}</span></a>
        				</#list>
        				</div>
                    </div>
                </div>
        	<#elseif column.getMcode()!= "" && column.getMcode()!= "Region">
        		 <div class="ui-select-box fn-left" style="width:150px;">
                    <input type="text" class="ui-select-txt" value="" />
                    <input type="hidden" value="" class="ui-select-value" />
                    <a class="ui-select-close"></a>
                    <div class="ui-option">
                    	<div class="a-wrap">
                        ${appsetting.getMcode( column.getMcode() ).getHtmlTag(column.defaultValue?default(''))}
                        </div>
                    </div>
                </div>
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
</form>
<iframe id="hiddenIframe" name="hiddenIframe" style="display:none" />
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script language="javascript">
$(function(){
	vselect();
})
</script>