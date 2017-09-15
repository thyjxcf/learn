<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../weekwork/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="周工作上报">
<div id="detailEditContainer">
<form name="form1" id="form1" method="POST" action="">
<input type="hidden" name="officeWorkArrangeDetail.id" value="${officeWorkArrangeDetail.id!}">
<input type="hidden" id="state" name="officeWorkArrangeDetail.state" value="${officeWorkArrangeDetail.state!}">
<input type="hidden" name="officeWorkArrangeDetail.outlineId" value="${officeWorkArrangeOutline.id!}">
<p class="table-dt fb18">周工作上报</p>
<@htmlmacro.tableDetail divClass="table-form" id="auditTable">
        <tr>
            <th>&nbsp;开始日期：</th>
            <td <#if useNewFields>colspan="3"</#if>>
                ${officeWorkArrangeOutline.startTime?string('yyyy-MM-dd')}
            </td>
            <th>&nbsp;结束日期：</th>
            <td <#if useNewFields>colspan="3"<#else>colspan="2"</#if>>
                ${officeWorkArrangeOutline.endTime?string('yyyy-MM-dd')}
            </td>
        </tr>
        <tr>
            <th>&nbsp;工作大纲名称：</th>
            <td <#if useNewFields>colspan="6"<#else>colspan="4"</#if>>
                ${officeWorkArrangeOutline.name?default('')}
            </td>
        </tr>
                     
        <tr>
            <th>工作重点：</th>
            <td <#if useNewFields>colspan="6"<#else>colspan="4"</#if> style="word-break:break-all; word-wrap:break-word;">
			    ${officeWorkArrangeOutline.workContent?default('')}
			</td>
        </tr>
        <tr>
            <th style="text-align:center;">日期</th>
            <#if useNewFields>
            <th style="text-align:center;">时间</th>
            </#if>
            <th style="text-align:center;">工作内容</th>
            <th style="text-align:center;">具体要求、安排</th>
            <th style="text-align:center;">责任部门</th>
            <#if useNewFields>
            <th style="text-align:center;">参与人员</th>
            </#if>
            <th style="text-align:center;">地点</th>
        </tr>
        <#if officeWorkArrangeDetail.officeWorkArrangeContents?exists && officeWorkArrangeDetail.officeWorkArrangeContents?size gt 0>
        	<#list officeWorkArrangeDetail.officeWorkArrangeContents as owac>
        	<tr>
	            <td style="text-align:center;">
	            	<@htmlmacro.datepicker name="workDates" id="workDates" style="width:120px;" value="${owac.workDate?string('yyyy-MM-dd')!}" size="20" dateFmt='yyyy-MM-dd' />
	            </td>
	            <#if useNewFields>
	            <td style="text-align:center;">
	            <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workStartTime" name="workStartTime" dateFmt="HH:mm" value="${owac.workStartTime!}"/>
	            &nbsp;-&nbsp;
	            <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workEndTime" name="workEndTime" dateFmt="HH:mm" value="${owac.workEndTime!}"/>
	            </td>
	            <td style="text-align:center;"><textarea name="contents" id="contents" cols="20" rows="3" maxLength="255">${owac.content!}</textarea></td>
	            <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="20" rows="3" maxLength="255">${owac.arrangContent!}</textarea></td>
	            <#else>
	            <td style="text-align:center;"><textarea name="contents" id="contents" cols="35" rows="3" maxLength="255">${owac.content!}</textarea></td>
	            <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="35" rows="3" maxLength="255">${owac.arrangContent!}</textarea></td>
	            </#if>
	            <td style="text-align:center;">
			  			<input type="hidden" id="deptIds${owac_index!}" name="deptIds" value="${owac.deptIds!}"/> 
			  	   		<input id="deptNames${owac_index!}" name="deptNames" value="${owac.deptNames!}" inputNumber="${owac_index!}" onclick="changeDept(this);" type="text" class="input-txt edit-class" style="width:150px;" readonly="readonly"/>
	        	</td>
	        	<#if useNewFields>
	        	<td style="text-align:center;"><input name="attendees" id="attendees" type="text" class="input-txt" style="width:100px;" maxlength="500" value="${owac.attendees!}"/></td>
	        	</#if>
	            <td style="text-align:center;">
	            	<input name="places" id="places" type="text" class="input-txt" <#if useNewFields>style="width:120px;"<#else>style="width:180px;"</#if> maxlength="60" value="${owac.place!}"/>
            		<#if owac_index gt 0>
            			<a href="javascript:void(0);" class="del delGroupTr"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
            		</#if>
            	</td>
	        </tr>
	        </#list>
        <#else>
	        <tr>
	            <td style="text-align:center;">
	            	<@htmlmacro.datepicker name="workDates" id="workDates" style="width:120px;" value="" size="20" dateFmt='yyyy-MM-dd' />
	            </td>
	            <#if useNewFields>
	            <td style="text-align:center;">
	            <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workStartTime" name="workStartTime" dateFmt="HH:mm" value=""/>
	            &nbsp;-&nbsp;
	            <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workEndTime" name="workEndTime" dateFmt="HH:mm" value=""/>
	            </td>
	            <td style="text-align:center;"><textarea name="contents" id="contents" cols="20" rows="3" maxLength="255"></textarea></td>
	            <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="20" rows="3" maxLength="255"></textarea></td>
	            <#else>
	            <td style="text-align:center;"><textarea name="contents" id="contents" cols="35" rows="3" maxLength="255"></textarea></td>
	            <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="35" rows="3" maxLength="255"></textarea></td>
	            </#if>
	            <td style="text-align:center;">
		  			<input type="hidden" id="deptIds0" name="deptIds" value=""/> 
		  	   		<input id="deptNames0" name="deptNames" value="" inputNumber="0" onclick="changeDept(this);" type="text" class="input-txt edit-class" style="width:150px;" readonly="readonly"/>
	        	</td>
	        	<#if useNewFields>
	        	<td style="text-align:center;"><input name="attendees" id="attendees" type="text" class="input-txt" style="width:100px;" maxlength="500" value=""/></td>
	        	</#if>
	            <td style="text-align:center;"><input name="places" id="places" type="text" class="input-txt" <#if useNewFields>style="width:120px;"<#else>style="width:180px;"</#if> maxlength="60" value=""/></td>
	        </tr>
        </#if>
        <tr id="needAddTr">
            <th>&nbsp;备注：</th>
            <td <#if useNewFields>colspan="6"<#else>colspan="4"</#if> style="word-break:break-all; word-wrap:break-word;">
			    <textarea name="officeWorkArrangeDetail.remark" id="remark" cols="150" rows="5" msgName="备注" maxLength="500">${officeWorkArrangeDetail.remark?default('')}</textarea>
			</td>
        </tr>
        <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
         <p class="pt-15 t-center">
        	<td <#if useNewFields>colspan="6"<#else>colspan="4"</#if> class="td-opt" >
		       <#-- 未提交可以修改 -->
		       <a class="abtn-blue center" href="javascript:void(0);" id="addTr">+ 追加工作内容</a>
		       <#if officeWorkArrangeDetail.state?default('1') == '1'>
		       <a class="abtn-blue"  href="javascript:void(0);" onclick="save('1')">保存</a>
		       <a class="abtn-blue"  href="javascript:void(0);" onclick="save('2')">提交</a>
		       <#else>
		       <a class="abtn-blue"  href="javascript:void(0);" onclick="save('2')">保存</a>
		       </#if>
			   <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </p>
</@htmlmacro.tableDetail>
</form>
</div>
<table id="copyTr" style="display:none;">
	<tr>
        <td style="text-align:center;">
        	<@htmlmacro.datepicker name="workDates" id="workDates" style="width:120px;" value="" size="20" dateFmt='yyyy-MM-dd' />
        </td>
        <#if useNewFields>
        <td style="text-align:center;">
        <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workStartTime" name="workStartTime" dateFmt="HH:mm" value=""/>
        &nbsp;-&nbsp;
        <@htmlmacro.datepicker class="input-txt input-readonly" style="width:40px" id="workEndTime" name="workEndTime" dateFmt="HH:mm" value=""/>
        </td>
        <td style="text-align:center;"><textarea name="contents" id="contents" cols="20" rows="3" maxLength="255"></textarea></td>
	    <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="20" rows="3" maxLength="255"></textarea></td>
        <#else>
        <td style="text-align:center;"><textarea name="contents" id="contents" cols="35" rows="3" maxLength="255"></textarea></td>
	    <td style="text-align:center;"><textarea name="arrangContents" id="arrangContents" cols="35" rows="3" maxLength="255"></textarea></td>
        </#if>
        <td style="text-align:center;">
    		<input type="hidden" id="deptIdsTemp" name="deptIds" value=""/>
			<input id="deptNamesTemp" name="deptNames" value="" inputNumber="inputNumberTemp" onclick="changeDept(this);" type="text" class="input-txt edit-class" style="width:150px;" readonly="readonly"/>
    	</td>
    	<#if useNewFields>
    	<td style="text-align:center;"><input name="attendees" id="attendees" type="text" class="input-txt" style="width:100px;" maxlength="500" value=""/></td>
    	</#if>
        <td style="text-align:center;">
        	<input name="places" id="places" type="text" class="input-txt" <#if useNewFields>style="width:120px;"<#else>style="width:180px;"</#if> maxlength="60" value=""/>
        	<a href="javascript:void(0);" class="del delGroupTr"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a>
        </td>
    </tr>
</table>

<@commonmacro.selectMoreTree idObjectId="deptIdsSelect" nameObjectId="deptNamesSelect"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" callback="doDeptsSave"  switchSelector=".edit-class">
	<input type="hidden" name="deptNumber" id="deptNumber">
	<input type="hidden" id="deptIdsSelect" name="deptIdsSelect"/>
	<input type="hidden" id="deptNamesSelect" name="deptNamesSelect"/>
	<a id="pop-class"></a>
</@commonmacro.selectMoreTree>

<script type="text/javascript">
<#if officeWorkArrangeDetail.officeWorkArrangeContents?exists && officeWorkArrangeDetail.officeWorkArrangeContents?size gt 0>
var currentNumber = ${officeWorkArrangeDetail.officeWorkArrangeContents?size};
<#else>
var currentNumber = 1;
</#if>
$(document).ready(function(){
	$('#addTr').click(function(e){
		e.preventDefault();
		var trContent = $('#copyTr tr').html();
		var detailContent = "<tr>"+trContent.replaceAll("deptIdsTemp","deptIds"+currentNumber).replaceAll("deptNamesTemp","deptNames"+currentNumber).replace("inputNumberTemp",currentNumber)+"</tr>";
		$("#needAddTr").before(detailContent);
		currentNumber = currentNumber+1;
		doFalsh();
	});
	String.prototype.replaceAll = function(s1,s2) {
	    return this.replace(new RegExp(s1,"gm"),s2);
	}
	function doFalsh(){
		$(".delGroupTr").unbind("click").click(function(e){
			e.preventDefault();
			var index = $(this).parents("tr").index();
			$("#auditTable tr:eq("+index+")").remove();
		});
	}
	doFalsh();
});

function changeDept(obj){
	var inputNumber = $(obj).attr("inputNumber");
	$("#deptNumber").val(inputNumber);
	$("#deptIdsSelect").val($("#deptIds"+inputNumber).val());
	$("#deptNamesSelect").val($("#deptNames"+inputNumber).val());
	$("#pop-class").click();
}

function doDeptsSave(){
	var inputNumber = $("#deptNumber").val();
	$("#deptIds"+inputNumber).val($("#deptIdsSelect").val());
	$("#deptNames"+inputNumber).val($("#deptNamesSelect").val());
}

function compareTimes(elem1, elem2) {
	if (elem1.value != "" && elem2.value != "") {
    	var date1;
      	var date2;
      	try {
        	date1 = elem1.value.split(':');
        	date2 = elem2.value.split(':');
      	} catch (e) {
        	date1 = elem1.split(':');
        	date2 = elem2.split(':');
      	}
      	if (eval(date1[0]) > eval(date2[0])) {
        	return 1;
      	} else if (eval(date1[0]) == eval(date2[0])) {
        	if (eval(date1[1]) > eval(date2[1])) {
          		return 1;
        	} else if (eval(date1[1]) == eval(date2[1])) {
            	return 0;
        	} else {
          		return -1;
        	}
      	} else {
        	return -1;
      	}
    }
}

var isSubmit = false;
var startTime = '${officeWorkArrangeOutline.startTime?string('yyyy-MM-dd')}';
var endTime = '${officeWorkArrangeOutline.endTime?string('yyyy-MM-dd')}';
function save(state){
    if(isSubmit) {
       return;
    }
    $("#state").val(state);
	if(!checkAllValidate("#detailEditContainer")){
		return;
	}
    var workDates = document.getElementsByName("workDates");
    var contents = document.getElementsByName("contents");
    var deptIds = document.getElementsByName("deptIds");
    var remark = $("#remark").val();
    
    var msg = "";
    for(var i=0;i<workDates.length-1;i++){
    	var i_msg = "第" + (i+1) + "行";
    	if(workDates[i].value == "" &&　contents[i].value == ""){
    		if(remark == ""){
    			msg = "若无工作内容，请填写备注说明";
    			break;
    		}
    	}
    	else if(workDates[i].value != "" &&　contents[i].value != ""){
	    	if(compareDate(startTime,workDates[i].value)>0 || compareDate(endTime,workDates[i].value)<0){
	    		msg = i_msg + "日期必须在工作大纲时间范围内";
	    		break;
	    	}
	    	if(deptIds[i].value == ""){
	    		msg = "请确保" + i_msg + "信息填写完整";
				break;
	    	}
    	}
    	else{
    		msg = "请确保" + i_msg + "信息填写完整";
			break;
    	}
    }
    if(msg != ""){
    	showMsgWarn(msg);
    	return;
    }
    
    var workStartTime = document.getElementsByName("workStartTime");
    var workEndTime = document.getElementsByName("workEndTime");
    for(var j=0;j<workStartTime.length-1;j++){
    	var j_msg = "第" + (j+1) + "行";
    	if(workStartTime[j].value =="" && workEndTime[j].value ==""){
    		continue;
    	}
    	else if(workStartTime[j].value !="" && workEndTime[j].value !=""){
    		if(compareTimes(workStartTime[j].value,workEndTime[j].value)>-1){
	    		msg = j_msg + "时间开始时间必须小于结束时间";
	    		break;
    		}
    	}
    	else{
    		msg = j_msg + "时间信息未填写完整";
    		break;
    	}
    }
    if(msg != ""){
    	showMsgWarn(msg);
    	return;
    }
    
	isSubmit = true;
	var noteUrl = "${request.contextPath}/office/weekwork/weekwork-arrangeSave.action";
	var options = {
      target : '#form1',
      url : noteUrl,
      success : showSuccess,
      dataType : 'json',
      clearForm : false,
      resetForm : false,
      type : 'post'
    };
  	$("#form1").ajaxSubmit(options);
}

//操作提示
function showSuccess(data) {
    var searchYear = '${searchYear!}';
    if (!data.operateSuccess){
		if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"提示",function(){
		  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork-arrangeList.action?searchYear="+searchYear);
		});
		return;
	}
}

function back(){
  var searchYear = '${searchYear!}';
  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork-arrangeList.action?searchYear="+searchYear);
}
vselect();
</script>
</@htmlmacro.moduleDiv>