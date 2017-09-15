<span class="level-tt">${divName!}：</span>
<input type="hidden" name="${idObjectName!(idObjectId!)}" id="${idObjectId!}">
<div class="level-list my-search-condition">
    <span class="user-sList <#if mode == 's'>user-sList-radio<#else>user-sList-checkbox</#if>"><!--多选：user-sList-checkbox；单选：user-sList-radio-->
        <#if defaultItem =="Y">
            <span <#if selectedValue! =="">class="current"</#if> onclick="clickOnSelect('${onclick!}','','${params!}')">全部</span>
        </#if>
        <#list objects as row>
        <span <#if selectedValue! == row.id>class="current"</#if> onclick="clickOnSelect('${onclick!}','${row.id}','${params!}')">${row.objectName}</span>
        </#list>
    </span>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
$(document).ready(function(){
	//用户选择
	$('.my-search-condition .user-sList-checkbox span').click(function(){
		$(this).addClass('current');
	});
	$('.my-search-condition .user-sList-radio span').click(function(){
		$(this).addClass('current').siblings('span').removeClass('current');
	});
});

function clickOnSelect(clickMethod,id,params){
	$('#${idObjectId!}').val(id);
	var methodParam ="";
	if(params != ""){
		var param=params.split(",");
		for (i=0;i<param.length ;i++){
			if( i == param.length-1)
				methodParam +="'"+$('#'+param[i]).val()+"'";
			else
				methodParam +="'"+$('#'+param[i]).val()+"',";
		}
	}else{
		methodParam = "'"+id+"'";
	}
	var assembledMethod =clickMethod+"("+methodParam+")";
	if(clickMethod !=""){
	  	if(assembledMethod instanceof Function){
			eval(assembledMethod)();
		}
		else{
			eval(assembledMethod);
		}
	}
}
</script>