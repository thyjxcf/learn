<#import "/common/htmlcomponent.ftl" as htmlmacro> 
<@htmlmacro.moduleDiv titleName="学号规则设置"> 
<input type="hidden" name="rulelistid" id="rulelistid" value="${rulelistid?default("")}">
<input type="hidden" name="initCodeRule" value="${initCodeRule?string}">
<input type="hidden" name="stucoderuleid" value="${stucoderuleid?if_exists}">
<input type="hidden" name="schid" value="${schid?if_exists}">
<div class="pub-operation fn-clear">
           <#if initCodeRule>
           		<span class="fn-left mt-5">是否启用：</span>
           		<div class="ui-select-box fn-left" style="width:100px;">
	                <input type="text" class="ui-select-txt" value="" />
	                <input name="automatism" id="automatism" type="hidden" value="" class="ui-select-value" />
	                <a class="ui-select-close"></a>
	                <div class="ui-option" myfunchange="onChange">
	                	<div class="a-wrap">
	                    <a val="0" <#if automatism?default('0')=='0'>class="selected"</#if>><span>否</span></a>
	                    <a val="1" <#if automatism?default('0')=='1'>class="selected"</#if>><span>是</span></a>
	                	</div>
	                </div>
            	</div>    
         	<#else>
	          <#if (codeType =='21' || codeType =='22')>
	            <#if existSchool?exists && existSchool=="true">	
	           		<span class="fn-left mt-5">是否启用：</span>            	
		            <div class="ui-select-box fn-left" style="width:100px;" <#if codeType == "2">style="display:none"</#if>>
		                <input type="text" class="ui-select-txt" value="" />
		                <input name="automatism" id="automatism" type="hidden" value="" class="ui-select-value" />
		                <a class="ui-select-close"></a>
		                <div class="ui-option" myfunchange="onChange">
		                	<div class="a-wrap">
		                    <a val="0" <#if automatism?default('0')=='0'>class="selected"</#if>><span>否</span></a>
		                    <a val="1" <#if automatism?default('0')=='1'>class="selected"</#if>><span>是</span></a>
		                	</div>
		                </div>
            		</div>   
		        <#else>
		        	<font color="#FF0000">学校信息还未添加，请先添加学校！</font>
		        </#if>
		      </#if>
		       <#if codeType =='11' || codeType =='12'>
		       		<span class="fn-left mt-5">是否启用：</span>
		       		<div class="ui-select-box fn-left" style="width:100px;" <#if !topEdu> disabled</#if>>
		                <input type="text" class="ui-select-txt" value="" />
		                <input name="automatism" id="automatism" type="hidden" value="" class="ui-select-value" />
		                <a class="ui-select-close"></a>
		                <div class="ui-option" myfunchange="onChange">
		                	<div class="a-wrap">
		                    <a val="0" <#if automatism?default('0')=='0'>class="selected"</#if>><span>否</span></a>
		                    <a val="1" <#if automatism?default('0')=='1'>class="selected"</#if>><span>是</span></a>
		                	</div>
		                </div>
            		</div>   
		      </#if>  
	      </#if>
	      <span class="fn-right">
	      <#if initCodeRule || ( (existSchool?exists && existSchool=="true"&& (codeType =='21' || codeType =='22')) || (codeType =='11' || codeType =='12')&& topEdu)>
			<a href="javascript:void(0);" id="btnAdd" onClick="doAdd();" class="abtn-orange-new">新增</a>
			<a href="javascript:void(0);" id="btnSort" onClick="doSort();" class="abtn-blue-big">调整位置</a>
		  </#if>
		  </span>
</div>
	<table id="detailtable" border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
		        <tr>
				  <th width="10%">号码位置</td>
                  <th width="15%">类　　型</td>
                  <th width="15%">取值位（流水号长度）</td>
                  <th width="10%">类型长度</td>
                  <th width="8%">固定值</td>
                  <th width="10%">是否计入流水号</td>
                  <th width="11%">备   注</td>
                  <th class="t-center" width="8%">操  作</th>
                </tr>
                <#if stuCodeRuleList?exists>
				  <#list stuCodeRuleList as item>
	              <tr >
	                <td><#if item.rulePosition?exists && item.rulePosition==99>末尾后缀<#else>${item.rulePosition?default("")}</#if></td>
	                <td>${item.dataType?default("")}</td>
	                <td><#if (item.ruleNumber?exists && item.ruleNumber &gt; 0) || !item_has_next>${item.ruleNumber?default("")}</#if></td>
	                <td><#if item.rulePosition?exists && item.rulePosition != 99>${item.length?default("")}</#if></td>
	                <td>${item.constant?default("")}</td>
	                <td><#if item.inSerialNumber>是<#else>否</#if></td>
	                <td>${item.remark?default("")}</td>
	                <td name="deletecloumn">
	                 <#if initCodeRule || ( (existSchool?exists && existSchool=="true"&& (codeType =='21' || codeType =='22')) || (topEdu && (codeType =='11' || codeType =='12')) )>
	                	<#if item.rulePosition?exists && item.rulePosition != 99>
	                	<a name="deletebutton" href="javascript:void(0);" onClick="doDel('${item.id?default("")}')" class="ml-15"><img name="deleteimg" src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
	                	</#if>
                      </#if>
                      <a  href="javascript:void(0);" onClick="doEdit('${item.id?default("")}');" class="ml-15"><img name="deleteimg" src="${request.contextPath}/static/images/icon/edit.png" alt="修改"></a>
	                </td>
	              </tr>
				  </#list>
				</#if>
            </table>
<div class="explain-text">
	<p class="b">号码规范设置说明：</p>
	<p>对号码的每一位分别进行维护<br/>
	<p>1、号码位置：维护要设置号码的第几位；<br />
	<p>2、类型：维护对应的号码位取自哪一个字段；<br />
    <p>3、取值位：如果该位从该类型字段中取，则需要输入该位在这种类型中的所在位；<br />
    <p>4、类型长度：指该项类型的总长度，取值位不能大于这个长度；<br/>
    <p>5、固定值：若该位是固定值，则需要输入此固定值；<br />
    <p>6、类型为“性别”时，“1”为男生，“2”为女生；<br />
    <p>7、类型为“流水号”时，取值位则表示的是流水号的长度；<br />
    <p>8、“流水号”行会自动添加且不能删除，其值必须是在1-10之间的一个整数;<br />
    <p>9、是否计入流水号：计入流水号，即表示此字段的值不同时，流水号重新计算，否则表示流水号连续下去。
</div>
<div class="popUp-layer" id="edDiv" style="display:none;width:720px;"></div>
<div class="popUp-layer" id="sortdDiv" style="display:none;width:720px;"></div>
<script>
vselect();
function onChange(val,txt){
	load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoUpdate.action?codeType=${codeType!}&initCodeRule=${initCodeRule?string}&stucoderuleid="+$("input[name='stucoderuleid']").val()+"&automatism="+$("input[name='automatism']").val());
}


function doDel(rulelistid){
	if(showConfirm("确定要删除该行规则？")){
		$.getJSON("${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoDelete.action",{codeType:${codeType},ruleListId:rulelistid},function(data){
	   		if(data.jsonError != null && data.jsonError != ""){
				showMsgError(data.jsonError);
				//按钮变回样式
				return;
			}else{
				showMsgSuccess("删除成功","",function(){load("#dataDiv", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRule.action?codeType=${codeType}");});
			}
	   }).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
	}
}

function doEdit(rulelistid){
 	<#if !initCodeRule && !topEdu && (codeType =='11' || codeType =='12')>
 		return;
 	</#if>
 	openDiv("#edDiv",  "#edDiv .close,#edDiv .reset", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoEdit.action?rulelistid=" + rulelistid+"&codeType=${codeType}&initCodeRule=${initCodeRule?string}");
}

function doAdd(){
	if($('#detailtable').find('tr').size()>=21){
		showMsgError("已达到最大的规则设置数量,无法新增");
		return;
	}

	$("#btnAdd").attr("class", "abtn-unable-big");
	var stucoderuleid = $("input[name='stucoderuleid']").val();
	$.getJSON("${request.contextPath}/basedata/coderule/codeRuleSetAdmin-remoteStuCodeRule.action", {
	schId:"${schid!}", stuCodeRuleId:stucoderuleid}, function(data){
		if(data.jsonError != null && data.jsonError != ""){
			showMsgError(data.jsonError);
			$("#btnAdd").attr("class", "abtn-orange-new");
			return;
		}
		else{
			openAdd();		
			return;
		}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);
	});
		
}

function openAdd(){
	var stucoderuleid = $("input[name='stucoderuleid']").val();
	openDiv("#edDiv", "#edDiv .close,#edDiv .reset", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoAdd.action?codeType=${codeType!}"+"&schid=${schid!}&stucoderuleid="+stucoderuleid+"&initCodeRule=${initCodeRule?string}","","","",vselect,function(){$("#btnAdd").attr("class", "abtn-orange-new");});
}

//排序
function doSort(){
	$("#btnSort").attr("class", "abtn-unable-big");
	var detailtable = $("#detailtable tr").size();
	if(detailtable<=3){
		showMsgError("可调整位置的行数小于2行，不能排序！","", null);
		return false;
	}
	openDiv("#sortdDiv","#sortdDiv .close,#sortdDiv .reset", "${request.contextPath}/basedata/coderule/codeRuleSetAdmin-stuCodeRuleDoSort.action?codeType=${codeType!}&schid=${schid!}&initCodeRule=${initCodeRule?string}","","","",vselect,function(){$("#btnSort").attr("class", "abtn-orange-big");});
}

</script>
</@htmlmacro.moduleDiv>