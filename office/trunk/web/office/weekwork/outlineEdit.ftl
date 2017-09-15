<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="大纲编辑">
<div id="outlineEditContainer">
<form name="form1" id="form1" method="POST" action="">
<input type="hidden" name="officeWorkArrangeOutline.id" value="${officeWorkArrangeOutline.id!}">
<p class="table-dt fb18">周工作大纲</p>
<@htmlmacro.tableDetail divClass="table-form">
        <tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;开始日期：</th>
            <td>
                <@htmlmacro.datepicker name="officeWorkArrangeOutline.startTime" id="startTime" value="${((officeWorkArrangeOutline.startTime)?string('yyyy-MM-dd'))?if_exists}" size="20" msgName="开始日期" dateFmt='yyyy-MM-dd' notNull="true"/>           
            </td>
        </tr>
        <tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;结束日期：</th>
            <td>
                <@htmlmacro.datepicker name="officeWorkArrangeOutline.endTime" id="endTime" value="${((officeWorkArrangeOutline.endTime)?string('yyyy-MM-dd'))?if_exists}" size="20" msgName="结束日期" dateFmt='yyyy-MM-dd' notNull="true"/>           
            </td>
        </tr>
        <tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;工作大纲名称：</th>
            <td>
            	<input name="officeWorkArrangeOutline.name" id="name" type="text" class="input-txt" style="width:300px;" maxlength="60" value="${officeWorkArrangeOutline.name?default("")}" msgName="工作大纲名称" notNull="true" />
            </td>
        </tr>
                     
        <tr>
            <th>工作重点：</th>
            <td colspan="3">
				<textarea name="officeWorkArrangeOutline.workContent" id="workContent" cols="70" rows="5" msgName="工作重点" maxLength="255">${officeWorkArrangeOutline.workContent?default("")}</textarea>
			</td>
        </tr>
        <tr>
        	<td colspan="2" class="td-opt">
        	    <#--<#if !officeWorkArrangeOutline.use>-->
			    <a class="abtn-blue doSave" id="btnSave" href="javascript:void(0);" onclick="save()">保存</a>
			    <#--</#if>-->
			    <a class="abtn-blue reset ml-5" href="javascript:void(0);" onclick="back();">返回</a>
            </td>
        </tr> 
</@htmlmacro.tableDetail>
</form>
</div>

<script type="text/javascript">
var isSubmit = false;
     
function save(){
    if(isSubmit) {
       return;
    }
	if(!isActionable("#btnSave")){
		return;
	}
	if(!checkAllValidate("#outlineEditContainer")){
		return;
	}
	
	//开始时间不能大于结束时间
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	if((startTime != null && startTime != "")||(endTime != null && endTime != "")){
		if(compareDate(endTime,startTime)<=0){
			addFieldError("startTime","开始日期必须小于结束日期!");
			return false;
		}
	}
	var name = $("#name").val();
	if(name.indexOf("第周") > 0){
		showMsgWarn("请填写工作大纲名称为第几周");
		return;
	}
	$("#btnSave").attr("class", "abtn-unable");
	$.getJSON("${request.contextPath}/office/weekwork/weekwork-outlineSave.action", jQuery('#form1').serialize(), function(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
	   		   $("#btnSave").attr("class", "abtn-blue");
			   return;
		   }
		}else{
		    var msg="保存成功!";
			showMsgSuccess(msg,"",function(){
			  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork.action");
			});
			return;
		}
	});
}

function back(){
  load("#weekworkContainer","${request.contextPath}/office/weekwork/weekwork-outlineList.action");
}
vselect();
</script>
</@htmlmacro.moduleDiv>