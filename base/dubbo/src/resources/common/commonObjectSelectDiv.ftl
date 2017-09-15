<#if objects?exists && 0<objects?size>
<#if defaultItem =='Y'>
<li id="" objectId="${idObjectId!}">不限</li>
</#if>
<#list objects as row>
<li id="${row.id!}" objectId="${idObjectId!}" >${row.objectName!}</li>
</#list>
<#else>
<p id="nodataId" class="no-data mt-50 mb-50">${tipMsg?default('还没有任何记录哦')}</p>
</#if>
<script>
$(document).ready(function(){
//树级菜单3	
	//div模拟select下拉框select_box
	//mouseover/mouseout展开或关闭下拉菜单
	$(".select_box").unbind('click').click(function(){
		
		//如果有依赖的参数并且自身的值为空的话 才去加载数据 否则不需要加载数据
		if($(this).attr("objectId") ==undefined){
			return;
		}	
		if($('#'+$(this).attr("objectId")+"Dependson").val() !=""){
			var isChanged=false;
			var dependsonElements=$('#'+$(this).attr("objectId")+"Dependson").val().split(",");
			for (i=0;i<dependsonElements.length;i++){
				var state =$('#'+dependsonElements[i]+"DependState").val();
				if(state=="1"){
					isChanged=true;
					$('#'+dependsonElements[i]+"DependState").val("0");
					break;
				}
			}
			if(isChanged){
				dynamicLoadDivData($(this).attr("objectId"));
			}
		}
		$(this).children(".select_current").addClass("select_current_hover").end().find(".select_list,#ovLayer").fadeIn(function(){
			var myW=$(this).width()+5;
	        var myH=$(this).height()+30;
			$(this).siblings('#ovLayer').css({'width':myW,'height':myH});
		},0);	

	});
	
	$(".select_box").mouseleave(function(){
	     $(this).children(".select_current").removeClass("select_current_hover").end().find(".select_list,#ovLayer").hide();	
	});
	
	//获取选中的值
	$(".select_list li").unbind('click').click(function(event){
		//$(this).parents(".select_box").find('#ovLayer').height(0);
		$(this).parents(".select_box").find('.select_list,#ovLayer').hide().end().find('.select_current').attr("value",$(this).text());
		$("#"+$(this).attr("objectId")+"SelectedName").removeClass("select_current_hover");
		var clickMethod=$("#"+$(this).attr("objectId")+"Onclick").val();
		clickOnSelectDiv(clickMethod,$(this).attr('id'),$(this).attr('objectId'));
		$("#"+$(this).attr("objectId")+"DependState").val("1");
		return false;
	});
	//下拉列表hover
	$(".select_list li").mouseenter(function(){
	    $(this).addClass("select_hover");
	})
	$(".select_list li").mouseleave(function(){
	    $(this).removeClass("select_hover");
	})
	
	//focus blur
	$(".select_current").focus(function(){
		//var txt_value = $(this).val();
		//if(txt_value == this.defaultValue){$(this).val("");}     
	});
	$(".select_current").blur(function(){
		//var txt_value = $(this).val();
		//if(txt_value == ""){$(this).val(this.defaultValue);}  
	});
	<#if selectedValue! !="">
	<#list objects as row>
		<#if selectedValue =row.id>
			$('#${idObjectId!}SelectedName').val('${row.objectName!}');
		</#if>
	</#list>
	</#if>
	var isSingleSelect=$('#${idObjectId!}singleSelect').val();
 	if(isSingleSelect =="true"){
		$("#nodataId").hide();
	}
});

function clickOnSelectDiv(clickMethod,id,objectId){
	$('#'+$('#'+objectId+'Id').val()).val(id);
	if(id == ""){
		$('#'+objectId+'SelectedName').val('请选择'+$('#'+objectId+'DivName').val());
		
	}
	//适用于的的元素 全部清空
	var referto =$('#'+objectId+'Referto').val();
	if(referto != ""){
		var refertoElements=referto.split(",");
		for (i=0;i<refertoElements.length;i++){
			$('#'+refertoElements[i]+"DependState").val('1');
			if($('#'+refertoElements[i]+'Option')){
				$('#'+refertoElements[i]).val('');
				$('#'+refertoElements[i]+'SelectedName').val('请选择'+$('#'+refertoElements[i]+'DivName').val());
				$('#'+refertoElements[i]+'Option').html('');
			}
		}
	}
	if(clickMethod !=""){
		if (window[clickMethod] != undefined) {
	         window[clickMethod](id);
	         return;
	  	}
	}
}
</script>
