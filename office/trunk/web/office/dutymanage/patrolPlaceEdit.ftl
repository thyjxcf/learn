<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="巡查地点">
<#include "/common/handlefielderror.ftl">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<script language="javascript">
	var isSubmit = false;
	function save(){
		if(isSubmit){
			return false;
		}
		if(!checkAllValidate('#setAddForm')){
			return false;
		}
		var placeName = $("#placeName").val();
		if(trim(placeName).indexOf("\.") != -1){
			addFieldError("placeName", "巡查地点 不能输入特殊字符.");
			return;
		}
		isSubmit = true;
		var options = {
	       url:'${request.contextPath}/office/dutymanage/dutymanage-patrolPlaceSave.action', 
	       dataType : 'json',
	       clearForm : false,
	       resetForm : false,
	       type : 'post',
	       timeout : 3000,
	       success : showReply
	    };
		try{
			$('#setAddForm').ajaxSubmit(options);
		}catch(e){
			showMsgError('保存失败！');
		}
	}
	
	function showReply(data){
		if(!data.operateSuccess){
		   if(data.errorMessage!=null&&data.errorMessage!=""){
			   showMsgError(data.errorMessage);
			   isSubmit = false;
			   return;
		   }
		}else{
			showMsgSuccess(data.promptMessage,"",function(){
			  	patrolPlace();
			});
			return;
		}
	}
	
</script>
	<div class="popUp-layer" id="classLayer" style="display:none;width:380px;"></div>
	<form id="setAddForm" name="setAddForm">
    <p class="tt"><a href="javascript:void(0);" class="close" onclick="closeDiv('#positionLayerAdd');">关闭</a><span><#if officePatrolPlace?exists&&officePatrolPlace.id?default('')==''>新增<#else>编辑</#if>巡查地点</span></p>
    <div class="wrap pa-10">
        <input type="hidden" id="id" name="officePatrolPlace.id" value="${officePatrolPlace.id!}"/>
        <input type="hidden" id="unitId" name="officePatrolPlace.unitId" value="${officePatrolPlace.unitId!}"/>
        <table border="0" cellspacing="0" cellpadding="0" class="table-edit">
            <tr>
                <th><span class="c-orange ml-10">*</span>巡查地点：</th>
                <td><input type="text" class="input-txt" style="width:150px" name="officePatrolPlace.placeName" id="placeName" msgName="巡查地点" size="20"  maxlength='50' notNull="true" value="${officePatrolPlace.placeName?default('')}"/></td>
            <tr>
            <tr>
            	<td colspan="2" align="center"><a href="javascript:void(0);" class="abtn-blue" onclick="save();">保存</a>
            	<a href="javascript:void(0);" class="abtn-blue reset ml-15" onclick="closeDiv('#positionLayerAdd');">取消</a></td>
            </tr>
        </table>
    </div>
    </form>
</@htmlmacro.moduleDiv>