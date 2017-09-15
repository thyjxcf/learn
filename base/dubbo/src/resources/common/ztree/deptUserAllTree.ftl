<div class="user-inner-list-scroll" style="height:${height?default(370)-30}px;">
	<ul class="more-list">
		<#if depts?exists && depts?size gt 0>
			<#list depts as dept>
				<li>
					<p class="tit" data-state="close">
						<input type="checkbox" class="chk" id="${dept.id!}" name="input_deptIds" value="${dept.id!}" detail="${dept.deptname!}(${dept.unitName!})" onclick="checkDeptUsers('${dept.id!}');">
						<img src="${request.contextPath}/static/css/zTreeStyle/img/diy/02.png" alt="部门">
						<span class="name" style="cursor:pointer;">${dept.deptname!}</span>
					</p>
		            <div class="dd-list fn-clear">
		            	<#assign users = deptUsersMap.get(dept.id)/>
		            	<#if users?exists && users?size gt 0>
							<#list users as user>
				            	<p>
			            			<input type="checkbox" class="chk" parentDeptId="${dept.id!}" id="${user.id!}" name="input_userIds" onclick="selectUser(this,'${user.id!}','${user.realname!}(${user.unitName!}-${user.deptName!})',3);" value="${user.id!}" detail="${user.realname!}(${user.unitName!}-${user.deptName!})">
			            			<img src="${request.contextPath}/static/css/zTreeStyle/img/diy/03.png" alt="个人">
			            			<span class="name" style="cursor:pointer;" onclick="checkedUser('${user.id!}','${user.realname!}(${user.unitName!}-${user.deptName!})',3);">${user.realname!}</span>
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
	<span class="fn-left mt-5 ml-5"><input type="checkbox" class="chk" id="containUsers">关联人员</span>
</p>
<script>
function closeInnerMore(){
	$('.user-inner-more').hide();
}

function checkedUser(id,detail,type){
	if($('#'+id).attr("checked") == "checked"){
		$('#'+id).attr("checked",false);
	}else{
		$('#'+id).attr("checked",true);
	}
	var obj = document.getElementById(id);
	selectUser(obj,id,detail,type);
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
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if($(this).val()==item.id){
				$(this).attr("checked",true);
				break;
			}
		}
	});
	$("input[name='input_deptIds']").each(function(){
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if($(this).val()==item.id){
				$(this).attr("checked",true);
				break;
			}
		}
	});
	$("#checkAllInput").click(function(){
		if($(this).attr("checked")=='checked'){
			$("input[name='input_deptIds']").not("input:checked").each(function(){
				var param=new paramObjectAll($(this).val(),$(this).attr("detail"),2);
				$(this).attr("checked",true);
				userSelectionAll.push(param);
			});
			if($("#containUsers").attr("checked")=='checked'){
				$("input[name='input_userIds']").not("input:checked").each(function(){
					var param=new paramObjectAll($(this).val(),$(this).attr("detail"),3);
					$(this).attr("checked",true);
					userSelectionAll.push(param);
				});
			}
			setNameListHtml(userSelectionAll);
		}else{
			$("input[name='input_deptIds']:checked").each(function(){
				$(this).attr("checked",false);
				for(var index=0,len=userSelectionAll.length;index<len;index++){
					var item = userSelectionAll[index];
					if($(this).val()==item.id){
						userSelectionAll.splice(index,1);
						break;
					}
				}
			});
			if($("#containUsers").attr("checked")=='checked'){
				$("input[name='input_userIds']:checked").each(function(){
					$(this).attr("checked",false);
					for(var index=0,len=userSelectionAll.length;index<len;index++){
						var item = userSelectionAll[index];
						if($(this).val()==item.id){
							userSelectionAll.splice(index,1);
							break;
						}
					}
				});
			}
			setNameListHtml(userSelectionAll);
		}
	});
});

function checkDeptUsers(deptId){
	if($("#"+deptId).attr("checked")=='checked'){
		var flag = true;
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(deptId==item.id){
				flag = false;
				break;
			}
		}
		if(flag){
			var param=new paramObjectAll($("#"+deptId).val(),$("#"+deptId).attr("detail"),2);
			userSelectionAll.push(param);
		}
		if($("#containUsers").attr("checked")=='checked'){
			$("input[name='input_userIds'][parentDeptId='"+deptId+"']").not("input:checked").each(function(){
				var param=new paramObjectAll($(this).val(),$(this).attr("detail"),3);
				$(this).attr("checked",true);
				userSelectionAll.push(param);
			});
		}
		setNameListHtml(userSelectionAll);
	}else{
		if($("#containUsers").attr("checked")=='checked'){
			$("input[name='input_userIds'][parentDeptId='"+deptId+"']:checked").each(function(){
				$(this).attr("checked",false);
				for(var index=0,len=userSelectionAll.length;index<len;index++){
					var item = userSelectionAll[index];
					if($(this).val()==item.id){
						userSelectionAll.splice(index,1);
						break;
					}
				}
			});
		}
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(deptId==item.id){
				userSelectionAll.splice(index,1);
				break;
			}
		}
		setNameListHtml(userSelectionAll);
	}
}
</script>