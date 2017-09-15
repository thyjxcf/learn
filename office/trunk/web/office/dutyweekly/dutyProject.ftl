<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function doTypeAdd(){
	if(!isActionable("#btnSave")){
		return;
	}
	var projectName = $("#projectName").val();
	if(trim(projectName)==""){
		showMsgWarn("请填写检查项目名称！");
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/dutyweekly/dutyweekly-dutyprojectAdd.action?officeDutyProject.projectName='+encodeURIComponent(projectName), 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#goodsTypeform').ajaxSubmit(options);
}

function doTypeEdit(typeId){
	if(!isActionable("#btnSave")){
		return;
	}
	var projectName = $("#input_"+typeId).val();
	if(trim(projectName)==""){
		showMsgWarn("请填写检查项目名称！");
		dutyproject();
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/dutyweekly/dutyweekly-dutyprojectEdit.action?officeDutyProject.id='+typeId+'&officeDutyProject.projectName='+encodeURIComponent(projectName), 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#goodsTypeform').ajaxSubmit(options);
}

function doTypeDel(){
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("没有选要删除的检查项目，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除检查项目')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/dutyweekly/dutyweekly-dutyprojectDelete.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#goodsTypeform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   dutyproject();
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
			   	   dutyproject();
				   return;
			   }
	}else{
		   		showMsgSuccess(data.promptMessage,"",function(){
		   			dutyproject();
				});
	}
}
</script>
<div id="goodsTypeDiv">
	<form id="goodsTypeform" action="" method="post">
	<div class="query-builder">
    	<div class="query-part">
        	<span class="fn-right"><a href="javascript:void(0)" onclick="doTypeDel();" class="abtn-blue fn-right">删除</a>
        	<span class="ui-checkbox ui-checkbox-all fn-right mt-5" data-all="no"><input type="checkbox" class="chk">全选</span></span>
        	<div class="query-tt">检查项目名称：</div>
            <input type="text" class="input-txt" style="width:200px;" id="projectName" maxlength="6">
            <a href="javascript:void(0)" onclick="doTypeAdd();" id="btnSave" class="abtn-blue ml-10">添加</a>
            <div class="fn-clear"></div>
        </div>
    </div>
	<ul class="goods-list fn-clear mt-20">
	<#if officeDutyProjects?exists && officeDutyProjects?size gt 0>
		<#list officeDutyProjects as item>
		<li>
        	<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}"></span>
    		<div class="show-wrap"><span class="txt">${item.projectName!}</span><a href="#" class="edit"></a></div>
            <div class="edit-wrap"><a href="javascript:void(0)" onclick="doTypeEdit('${item.id}');" class="yes"></a><p class="txt"><input type="text" id="input_${item.id}" maxlength="6"></p></div>
    	</li>
		</#list>
	</#if>
	</ul>
	</form>
</div>
<script type="text/javascript">
$(function(){
	$('.goods-list li').hover(function(){
		$(this).addClass('hover');
	},function(){
		$(this).removeClass('hover');
	});
	$('.goods-list li .edit').click(function(e){
		var txt=$(this).siblings('.txt').text();
		$(this).parents('li').children('.show-wrap').hide().siblings('.edit-wrap').show().find('.txt input').val(txt);
	});
	$('.goods-list li .yes').click(function(e){
		var txt=$(this).siblings('.txt').children('input').val();
		$(this).parents('li').children('.edit-wrap').hide().siblings('.show-wrap').show().find('.txt').text(txt);
	});
	
});
</script>
</@common.moduleDiv>