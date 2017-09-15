<div class="user-inner-list-scroll" style="height:${height?default(370)-30}px;">
    <ul class="more-list">
		<#if depts?exists && depts?size gt 0>
			<#list depts as dept>
				<li>
					<p class="tit" data-state="close">
						<input type="checkbox" class="chk" id="${dept.id!}" name="input_deptIds" value="${dept.id!}" onclick="checkDeptUsers('${dept.id!}');">
						<img src="${request.contextPath}/static/css/zTreeStyle/img/diy/02.png" alt="部门">
						<span class="name" style="cursor:pointer;">${dept.deptname!}</span>
					</p>
		            <div class="dd-list fn-clear">
		            	<#assign users = deptUsersMap.get(dept.id)/>
		            	<#if users?exists && users?size gt 0>
							<#list users as user>
				            	<p>
			            			<input type="checkbox" class="chk" parentDeptId="${dept.id!}" id="${user.id!}" name="input_userIds" onclick="selectUser(this,'${user.id!}','${user.realname!}','${user.realname!}(${user.unitName!}-${user.deptName!})',3);" value="${user.id!}" txt="${user.realname!}" detail="${user.realname!}(${user.unitName!}-${user.deptName!})">
			            			<img src="${request.contextPath}/static/css/zTreeStyle/img/diy/03.png" alt="个人">
			            			<span class="name" style="cursor:pointer;" onclick="checkedUser('${user.id!}','${user.realname!}','${user.realname!}(${user.unitName!}-${user.deptName!})',3);">${user.realname!}</span>
				            	</p>
				            </#list>
			        	</#if>
		            </div>
				</li>
			</#list>
		</#if>
	</ul>
</div>
<p class="bt">
	<span class="fn-left mt-5 ml-5"><input type="checkbox" class="chk" id="checkAllInput">全选</span>
</p>
<script>
function closeInnerMore(){
	$('.user-inner-more').hide();
}

function checkedUser(id,name,detail,type){
	if($('#'+id).attr("checked") == "checked"){
		$('#'+id).attr("checked",false);
	}else{
		$('#'+id).attr("checked",true);
	}
	var obj = document.getElementById(id);
	selectUser(obj,id,name,detail,type);
}

$(function(){
	$('.more-list .tit .name').click(function(e){
		var $chk=$(this).parent('p')
		var state=$chk.attr('data-state');
		if(state=='open'){
			$chk.attr('data-state','close').parents('li').removeClass('open');
		}else if(state=='close'){
			$chk.attr('data-state','open').parents('li').addClass('open');
		};
	});
	$("input[name='input_userIds']").each(function(){
		for(var index=0,len=userSelection.length;index<len;index++){
			var item = userSelection[index];
			if($(this).val()==item.id){
				$(this).attr("checked",true);
				break;
			}
		}
	});
	$("#checkAllInput").click(function(){
		if($(this).attr("checked")=='checked'){
			$("input[name='input_userIds']").not("input:checked").each(function(){
				var param=new paramObject($(this).val(),$(this).attr("txt"),$(this).attr("detail"),3);
				$(this).attr("checked",true);
				userSelection.push(param);
			});
			$("input[name='input_deptIds']").attr("checked",true);
			setNameListHtml(userSelection);
		}else{
			$("input[name='input_userIds']:checked").each(function(){
				$(this).attr("checked",false);
				for(var index=0,len=userSelection.length;index<len;index++){
					var item = userSelection[index];
					if($(this).val()==item.id){
						userSelection.splice(index,1);
						break;
					}
				}
			});
			$("input[name='input_deptIds']").attr("checked",false);
			setNameListHtml(userSelection);
		}
	});
});

function checkDeptUsers(deptId){
	if($("#"+deptId).attr("checked")=='checked'){
		$("input[name='input_userIds'][parentDeptId='"+deptId+"']").not("input:checked").each(function(){
			var param=new paramObject($(this).val(),$(this).attr("txt"),$(this).attr("detail"),3);
			$(this).attr("checked",true);
			userSelection.push(param);
		});
		setNameListHtml(userSelection);
	}else{
		$("input[name='input_userIds'][parentDeptId='"+deptId+"']:checked").each(function(){
			$(this).attr("checked",false);
			for(var index=0,len=userSelection.length;index<len;index++){
				var item = userSelection[index];
				if($(this).val()==item.id){
					userSelection.splice(index,1);
					break;
				}
			}
		});
		setNameListHtml(userSelection);
	}
}
</script>