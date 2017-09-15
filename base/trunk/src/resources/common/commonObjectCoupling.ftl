<#if objects?exists && 0<objects?size >
<span class="level-tt">${divName!}：</span>
<ul class="level-list fn-clear">
 	<#list objects as row>
	<li id="${row.id!}" onclick="clickOnCoupling('${onclick!}','${row.id!}','${row.groupId!}')">${row.objectName!}</li>
	</#list>
</ul>
<script>
	$(document).ready(function(){
		$('.tree-menu-search').on('click','.show-part',function(){
			$(this).removeClass('show-part').addClass('show-all').text('展开');
			$(this).parents('.tree-menu-search').find('.tree-menu-box').hide();
		});
		$('.tree-menu-search').on('click','.show-all',function(){
			$(this).removeClass('show-all').addClass('show-part').text('收起');
			$(this).parents('.tree-menu-search').find('.tree-menu-box').show();
		});
	})

function clickOnCoupling(clickMethod,id,parentId){
	addSelectdItem(id,parentId);
	loadCoupling(clickMethod,id);
}

function loadCoupling(clickMethod,id){
	onLoadInnerSelect('${idObjectId!}','${nameObjectId!}','${url!}','${divName!}','${onclick!}','${callback!}',id);
	if(clickMethod !=""){
		if (window[clickMethod] != undefined) {
	         window[clickMethod](id);
	         return;
	  	}
	}
}

function addSelectdItem(id,parentId){
	$('#${idObjectId!}').val(id);
	var $text=$('#'+id).text();
	var $order=$('.tree-menu-selected .level-selected').length+1;
	$('.tree-menu-search b').text('您已选择：');
	//$('.tree-menu-selected .level-selected').removeClass('level-current');
	if($order ==1){
		$("<span id='"+parentId+"' onClick=\"removeSelectdItem('"+parentId+"');\" class='level-selected level-current' data-order='"+$order+"'><span>"+$text+"<\/span><\/span>").appendTo('.tree-menu-selected');
	}else{
		$("<span id='"+parentId+"' onClick=\"removeSelectdItem('"+parentId+"');\" class='level-selected level-current' data-order='"+$order+"'><span>"+$text+"<\/span><\/span>").appendTo('.tree-menu-selected');
	}
}

function removeSelectdItem(id){
	//$('#'+id).prev('.level-selected').addClass('level-current');
	$('#'+id).next('.level-selected').remove();
	$('#'+id).remove();
	$('#${idObjectId!}').val(id);
	loadCoupling('${onclick!}',id);
}
</script>
</#if>
