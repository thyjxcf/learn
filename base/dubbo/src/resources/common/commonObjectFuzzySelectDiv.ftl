<#if objects?exists && 0<objects?size>
<#if defaultItem =='Y'>
<a id="" objectId="${idObjectId!}">不限</li>
</#if>
<#list objects as row>
<a id="${row.id!}" objectId="${idObjectId!}" href="javascript:void(0);">${row.objectName!}</a>
</#list>
<#else>
<p id="nodataId" class="no-data mt-50 mb-50">${tipMsg?default('还没有任何记录哦')}</p>
</#if>
<script>
$(document).ready(function(){
//树级菜单3	
	//div模拟select下拉框select_box
	//mouseover/mouseout展开或关闭下拉菜单
	$(".search-box").unbind('click').click(function(){
		dynamicLoadFuzzyDivData($(this).attr("objectId"),'');
	    
	    $(this).find(".search-list-wrap,#ovLayer").fadeIn(function(){
			var myW=$(this).width()+5;
	        var myH=$(this).height()+30;
			$(this).siblings('#ovLayer').css({'width':myW,'height':myH});
		},0);
	});
	
	$('.search-box').mouseleave(function(){
		$(this).find('.search-list-wrap,#ovLayer').hide();
	});
	
	//获取选中的值
	$('.search-list-wrap a').unbind('click').click(function(e){
		e.stopPropagation();
		e.preventDefault();
		$(this).parent('.search-list-wrap').hide().siblings('.txt').val($(this).text());
		$(this).parents(".search-box").find('#ovLayer').hide();
		dynamicLoadFuzzyDivData($(this).attr("objectId"),'');
		var clickMethod=$("#"+$(this).attr("objectId")+"Onclick").val();
		clickOnSelectDiv(clickMethod,$(this).attr('id'),$(this).attr('objectId'));
		return false;
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
