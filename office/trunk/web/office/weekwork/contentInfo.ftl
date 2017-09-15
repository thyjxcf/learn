<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="修改工作内容">
<style type="text/css">
	.main th{text-align:right;margin-right:10px;}
	.main th,td{height:30px;}
</style>
<p class="tt"><a href="javascript:void(0);" onclick="closeDiv('#contentLayer');return false;" class="close">关闭</a><span>修改工作内容</span></p>
<form name="form1" id="form1" method="POST" action="">
<div class="wrap" id="contentInfoDiv">
    <input type="hidden" name="officeWorkArrangeContent.id" value="${officeWorkArrangeContent.id!}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-form">
        <tr>
            <th width="15%"><span class="c-orange ml-10">*</span>&nbsp;日期：</th>
            <td width="85%">
            	<@common.datepicker name="officeWorkArrangeContent.workDate" id="workDate" value="${officeWorkArrangeContent.workDate?string('yyyy-MM-dd')!}" size="20" dateFmt='yyyy-MM-dd' notNull="true"/>
            </td>
        </tr>
        <#if useNewFields>
        <tr>
            <th>时间：</th>
            <td>
            	<@common.datepicker class="input-txt input-readonly" style="width:100px" id="workStartTime" name="officeWorkArrangeContent.workStartTime" dateFmt="HH:mm" value="${officeWorkArrangeContent.workStartTime!}"/>
	            &nbsp;-&nbsp;
	            <@common.datepicker class="input-txt input-readonly" style="width:100px" id="workEndTime" name="officeWorkArrangeContent.workEndTime" dateFmt="HH:mm" value="${officeWorkArrangeContent.workEndTime!}"/>
        	</td>
        </tr>
        </#if>
    	<tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;工作内容：</th>
            <td>
            	<textarea name="officeWorkArrangeContent.content" id="content" cols="82" rows="5" notNull="true" maxLength="255">${officeWorkArrangeContent.content!}</textarea>
        	</td>
        </tr>
        
        <tr>
            <th>&nbsp;具体要求、安排：</th>
            <td>
            	<textarea name="officeWorkArrangeContent.arrangContent" id="arrangContent" cols="82" rows="5" maxLength="255">${officeWorkArrangeContent.arrangContent!}</textarea>
        	</td>
        </tr>
        
    	<tr>
            <th><span class="c-orange ml-10">*</span>&nbsp;责任部门：</th>
            <td>
	  			<@commonmacro.selectMoreTree idObjectId="deptIds" nameObjectId="deptNames"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" callback="doDeptsSave"  switchSelector=".edit-class">
	  			<input type="hidden" id="deptIds" name="officeWorkArrangeContent.deptIds" value="${officeWorkArrangeContent.deptIds!}"/> 
	  	   		<input id="deptNames" name="officeWorkArrangeContent.deptNames" value="${officeWorkArrangeContent.deptNames!}" notNull="true" type="text" class="input-txt edit-class" style="width:500px;" readonly="readonly"/>
        		</@commonmacro.selectMoreTree>
        	</td>
        </tr>
        <#if useNewFields>
        <tr>
            <th>参与人员：</th>
            <td>
            	<input name="officeWorkArrangeContent.attendees" id="attendees" type="text" class="input-txt" style="width:500px;" maxlength="500" value="${officeWorkArrangeContent.attendees!}"/>
        	</td>
        </tr>
        </#if>
    	<tr>
            <th>地点：</th>
            <td>
            	<input name="officeWorkArrangeContent.place" id="place" type="text" class="input-txt" style="width:500px;" maxlength="60" value="${officeWorkArrangeContent.place!}"/>
        	</td>
        </tr>
    </table>
	<p class="dd">
		<a href="javascript:void(0);" class="abtn-blue submit" onclick="saveContentInfo();">保存</a>
	    <a href="javascript:void(0);" class="abtn-blue reset" onclick="closeDiv('#contentLayer');return false;">取消</a>
	</p>
</div>
</form>
<script type="text/javascript">
function compareTimes(elem1, elem2) {
	if (elem1 != "" && elem2 != "") {
    	var date1;
      	var date2;
    	date1 = elem1.split(':');
    	date2 = elem2.split(':');
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
function saveContentInfo(){
	if(isSubmit){
		return;
	}
	var workDate = document.getElementById("workDate");
	if(compareDate(startTime,workDate.value)>0 || compareDate(endTime,workDate.value)<0){
		showMsgWarn("日期必须在工作大纲时间范围内");
    	return;
	}
	if(!checkAllValidate("#contentInfoDiv")){
		return;
	}
	<#if useNewFields>
		var workStartTime = $("#workStartTime").val();
		var workEndTime = $("#workEndTime").val();
		
		if(workStartTime == "" && workEndTime == ""){}
		else if(workStartTime != "" && workEndTime != ""){
			if(compareTimes(workStartTime,workEndTime)>-1){
	    		showMsgWarn("时间开始时间必须小于结束时间");
	    		return;
			}
		}
		else{
			showMsgWarn("时间信息未填写完整");
			return;
		}
	</#if>
	isSubmit = true;
	var noteUrl = "${request.contextPath}/office/weekwork/weekwork-modifyContent.action";
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
};

//操作提示
function showSuccess(data) {
    if (!data.operateSuccess){
		if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"提示",function(){
			closeDiv('#contentLayer');
			getAuditList();
		});
		return;
	}
}
</script>
</@common.moduleDiv>