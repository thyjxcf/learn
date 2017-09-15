<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function doTypeEdit(typeId){
	if(!isActionable("#btnSave")){
		return;
	}
	var typeName = $("#input_"+typeId).val();
	if(trim(typeName)==""){
		showMsgWarn("请填写项次！");
		salaryManageType();
		return;
	}else{
		var typeYear=typeName.substring(0,4);
		var typeMonth=typeName.substring(5,6);
		if(!/^\d{4}$/.test(typeYear)||!/^\d{1}$/.test(typeMonth)){
      		showMsgWarn("请按照xxxx年x月...格式填写！");
      		salaryManageType();
       		return false;
  		 }	
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	var options = {
       url:'${request.contextPath}/office/salarymanage/salarymanage-salaryTypeSave.action?officeSalaryImport.id='+typeId+'&officeSalaryImport.monthtime='+encodeURIComponent(typeName), 
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
		showMsgWarn("没有选要删除的项次，请先选择!");
		return;
	}
	
	if(!showConfirm('您确认要删除项次')){
		return;
	}
	$("#btnDelete").attr("class", "abtn-unable");	
	var options = {
       url:'${request.contextPath}/office/salarymanage/salarymanage-salaryTypeDelete.action', 
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
				   salaryManageType();
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
			   	   salaryManageType();
				   return;
			   }
	}else{
		   		showMsgSuccess(data.promptMessage,"",function(){
		   			salaryManageType();
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
        	<p style="color:#fa7305;margin-top:6px;">
			提示：点击选中您需要删除的项次，会把跟项次关联的数据全部删除，请谨慎使用该功能！(注：修改名应为xxxx年xx月....否则数据无效)
			</p>
            <div class="fn-clear"></div>
        </div>
    </div>
	<ul class="goods-list fn-clear mt-20">
	<#if officeSalaryImports?exists && officeSalaryImports?size gt 0>
		<#list officeSalaryImports as item>
		<li>
        	<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${item.id?default('')}"></span>
    		<div class="show-wrap"><span class="txt">${item.monthtime!}</span><a href="#" class="edit"></a></div>
            <div class="edit-wrap"><a href="javascript:void(0)" onclick="doTypeEdit('${item.id!}');" class="yes"></a><p class="txt"><input type="text" id="input_${item.id!}" maxlength="18"></p></div>
    	</li>
		</#list>
	</#if>
	</ul>
	<@common.Toolbar container="#salaryDiv"></@common.Toolbar>
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