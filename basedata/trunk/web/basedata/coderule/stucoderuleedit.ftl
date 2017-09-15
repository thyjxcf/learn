<#import "/common/htmlcomponent.ftl" as htmlmacro> 
<@htmlmacro.moduleDiv titleName="学号规则新增"> 

<form action="codeRuleSetAdmin-stuCodeRuleDoSave.action" method="post" name="editform" id="editform">
<input type="hidden" name="ruleposition" value="${ruleposition?default("")}">
<input type="hidden" name="ruleid" value="${ruleid?default("")}">
<input type="hidden" name="rulelistid" value="${rulelistid?default("")}">
<input type="hidden" name="constant" value="${constant?default("")}">
<input type="hidden" name="schid" value="${schid?default("")}">
<input type="hidden" name="initCodeRule" value="${initCodeRule?string}">
<input type="hidden" name="codeType" value="${codeType!}">

<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span><#if rulelistid?exists && rulelistid?default("")!="">编辑<#else>新增</#if><#if ruleposition?exists && ruleposition==99> 末尾后缀 <#else> 第${ruleposition?default("")}位 </#if>号码规则</span></p>
<div class="wrap pa-10">
	<div id="form">
	<table id="eidtTable" border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
			<th width="110">号码位置：</th>
			<td width="180">
				<input name="rulepositionview" type="text" class="input-txt input-readonly" style="width:140px;"
				value="<#if ruleposition?exists && ruleposition==99>末尾后缀<#else>${ruleposition?default("")}</#if>" >
				<span class="c-orange mt-5 ml-10">*</span>
			</td>
			<td id="td-remark" valign="top" rowspan="6">
				<div  class="explain-text">
					<p class="b">号码规范设置说明：</p>
					<p>1、号码位置：维护要设置号码的第几位；<br />
					<p>2、类型：维护对应的号码位取自哪一个字段；<br />
					<p>3、取值位：如果该位从该类型字段中取，则需要输入该位在这种类型中的所在位；<br />
					<p>4、类型长度：指该项类型的总长度，取值位不能大于这个长度<br/>
					<p>5、固定值：若该位是固定值，则需要输入此固定值；<br />
					<p>6、类型为“性别”时，“1”为男生，“2”为女生；<br />
					<p>7、是否计入流水号：计入流水号，即表示此字段的值不同时，流水号重新计算，否则表示流水号连续下去。
				</div>
			</td>
		</tr>
			<th>数据类型：</th>
			<td>
				<div class="ui-select-box fn-left" style="width:150px;">
					<input type="text" class="ui-select-txt" value="" />
					<input name="datatype" id="datatype" type="hidden" value="" class="ui-select-value" />
					<a class="ui-select-close"></a>
					<div class="ui-option" myfunchange="doChange">
					<div class="a-wrap">
					${datatypeHtml?default(" <a val=''><span>没有对应数据</span></a>")}
					</div>
					</div>
				</div>
				 <span class="fn-left c-orange mt-5 ml-10">*</span>
			</td>
		<tr id="rulenumberLine" name="rulenumberLine">
			<th id="rulenumlabel" name="rulenumlabel">取值位：</th>
			<td>
				<input name="rulenumber" id="rulenumber" type="text" value="${rulenumber?default("")?trim}"  class="input-txt" style="width:140px;" fieldtip="请输入少于类型长度的整数值" />
				<span class="c-orange mt-5 ml-10">*</span>
			</td>
		</tr>
		<tr id="lengthLine" name="lengthLine">
			<th>类型长度：</th>
			<td>
				<input name="datatype_length" id="datatype_length" type="text" class="input-txt input-readonly"  style="width:140px;" value="${datatypeLengthMap[datatype?default("")]?default("")}"  />
				<span class="c-orange mt-5 ml-10">*</span>
			</td>
		</tr>
		<tr id="cnstntLine" name="cnstntLine">
			<th>固定值：</th>
			<td>
				<input name="cnstnt" type="text" class="input-txt" style="width:140px;" value="${constant?if_exists?trim}"  maxlength="5"/>
				<span class=" c-orange mt-5 ml-10">*</span>
			</td>
		</tr>
		<tr>
			<th>是否计入流水号：</th>
			<td>
				<span class="ui-radio <#if inSerialNumber>ui-radio-current</#if>" data-name="num"><input type="radio" name="inSerialNumber" value="true" <#if inSerialNumber>checked</#if>   class="radio">是</span>
		        <span class="ui-radio <#if !inSerialNumber>ui-radio-current</#if>" data-name="num"><input type="radio" name="inSerialNumber" value="false" <#if !inSerialNumber>checked</#if>  class="radio">否</span>
			</td>
		</tr>
		<tr>
			<th>备注：</th>
			<td>
				<input name="remark" type="text" value="${remark?default("")?trim}" class="input-txt" style="width:140px;" maxlength="10"/></td>
		</tr>
	</table>
	</div>
</div>
<p class="dd">
	<a class="abtn-blue submit" href="javascript:void(0);" onclick="doSave()">确定</a>
	<a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
</form>
<script>
//数据类型对应的长度数组
var datatypeLengthMap = "${datatypeLengthMap?if_exists}"; 
var dtLength = "";
if(datatypeLengthMap != null && datatypeLengthMap!= ""){
	//格式为{stusourceplace=6, stusourcetype=1, enteracadyear=, currentacadyear=9}，对其进行拆分
	var temp = datatypeLengthMap.replace("}","");
	temp = temp.replace("{","");
	dtLength = temp.split(",");
}
//根据初始的数据类型，初始化显示页面
displayForm('${datatype?default("")}');

function doChange(value){
	var datatype = value;
	if(datatype == "" || datatype == null){
		$("#editform").find("input[name='rulenumber']").val("");
		$("#editform").find("input[name='datatype_length']").val("");
		$("#editform").find("input[name='cnstnt']").val("");
		$("#editform").find("input[name='remark']").val("");
		$("#editform").find("input[name='rulenumber']").attr("disabled",true);
		$("#editform").find("input[name='cnstnt']").attr("disabled",true);
		$("#editform").find("input[name='remark']").attr("disabled",true);
	}else{
		if(datatype == "fixedvalue"){
			$("#editform").find("input[name='rulenumber']").attr("disabled",true);
			$("#editform").find('#rulenumberLine').hide();
			$("#editform").find('#lengthLine').hide();
			$("#editform").find("input[name='cnstnt']").attr("disabled",false);
			$("#editform").find('#cnstntLine').show();
			$("#editform").find('#td-remark').attr("rowSpan","6");
			$("#editform").find("input[name='ruleposition']").val('${ruleposition?default("")}');
			$("#editform").find("input[name='rulepositionview']").val('${ruleposition?default("")}');
		}else if(datatype == "serialno"){
			$("#editform").find("input[name='rulenumber']").attr("disabled",false);
			$("#editform").find('#rulenumberLine').show();
			$("#editform").find('#lengthLine').hide();
			$("#editform").find("input[name='cnstnt']").attr("disabled",true);
			$("#editform").find('#cnstntLine').hide();
			$("#editform").find("th[name='rulenumlabel']").text("流水号长度：");
			$("#editform").find("input[name='ruleposition']").val('99');
			$("#editform").find("input[name='rulepositionview']").val('末尾后缀');
			$("#editform").find('#td-remark').attr("rowSpan","6");
		}else{
			$("#editform").find("input[name='rulenumber']").attr("disabled",false);
			$("#editform").find('#rulenumberLine').show();
			$("#editform").find('#lengthLine').show();
			$("#editform").find("input[name='cnstnt']").attr("disabled",true);
			$("#editform").find('#cnstntLine').hide();
			$("#editform").find("th[name='rulenumlabel']").text("取值位：");
			$("#editform").find("input[name='ruleposition']").val('${ruleposition?default("")}');
			$("#editform").find("input[name='rulepositionview']").val('${ruleposition?default("")}');
			$("#editform").find('#td-remark').attr("rowSpan","6");
		}
		var length = "";
		var len = dtLength.length;
		for(var i=0; i<len; i++){
			var tempdtlength = dtLength[i].split("=");
			if(datatype == $.trim(tempdtlength[0])){
				length = $.trim(tempdtlength[1]);
				break;
			}
		}
		$("#editform").find("input[name='datatype_length']").val(length);
	}
	return;
}
var isSubmitting = false;
function doSave(){
	//为空判断
	if(!checkElement($("#editform").find('#datatype').get(0),"数据类型")) return;
	//“备注”长度判断
	if(!checkOverLen($("#editform").find("input[name='remark']").get(0),10,"备注")) return;

	var dt = $("#editform").find('#datatype').val();
	if(dt == "fixedvalue"){
		//“固定值”为空判断
		if(!checkElement($("#editform").find("input[name='cnstnt']").get(0),"固定值")) return;
		//“固定值”长度判断
		if(!checkOverLen($("#editform").find("input[name='cnstnt']").get(0),5,"固定值")) return;
		$("#editform").find("input[name='constant']").val($("#editform").find("input[name='cnstnt']").val());
	}else if(dt == "serialno"){
		if(!checkElement($("#editform").find("input[name='rulenumber']").get(0),"流水号长度")) return;
		//流水号长度数据类型判断
		if(!checkInteger($("#editform").find("input[name='rulenumber']").get(0),"流水号长度")) return;
		//流水号长度在0-10之间
		var serialno = parseInt($("#editform").find("input[name='rulenumber']").val());
		if(serialno <= 0 || serialno>10){
			showMsgError("流水号长度应在1-10之间，请更改！");
			return;
		}
		$("#editform").find("input[name='constant']").val('');
	}else{
		if(!checkElement($("#editform").find("input[name='rulenumber']").get(0),"取值位")) return;
		//数据类型判断
		if(!checkPlusInt($("#editform").find("input[name='rulenumber']").get(0),"取值位")) return;
		//判断大小
		var rulenumber = parseInt($("#editform").find("input[name='rulenumber']").val());
		var length = parseInt($("#editform").find("input[name='datatype_length']").val());
		if(rulenumber>length){
			showMsgError("取值位应小于类型长度，请更改！");
			return;
		}
		$("#editform").find("input[name='constant']").val('');
	}
	if(isSubmitting){
    	return;
	}
	isSubmitting = true;
	var options = {
		url : "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoSave.action",
		success : showSuccess,
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
  	$("#editform").ajaxSubmit(options);
}
function showSuccess(data){
	if(data.jsonError != null && data.jsonError != ""){
		showMsgError(data.jsonError);
		$("#btnAdd").attr("class", "abtn-orange-new");
		return;
	}else{
		$("#btnAdd").attr("class", "abtn-orange-new");
		showMsgSuccess("保存成功","",function(){$(".ui-radio-current").find("input[name='radiobutton']").trigger("onclick");});
	}
}


//数据类型改变时，变化相应的输入框
function displayForm(datatype){
	doChange(datatype);
}
vselect();
</script>
</@htmlmacro.moduleDiv>