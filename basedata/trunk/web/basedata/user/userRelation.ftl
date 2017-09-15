<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--${operationName?default('')}">
<script>
function validateform(){
	jQuery.ajax({
	   		url:"${request.contextPath}/basedata/user/userAdmin-roleSave.action?modID=${modID?default('')}",
	   		type:"POST",
	   		data:jQuery('#roleform').serialize(),
	   		dataType:"JSON",
	   		async:false,
	   		success:function(data){
	   			if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   					load("#container","${request.contextPath}/basedata/user/userAdmin.action");
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
  		 });
}

function cancelUser(){
	load("#container","${request.contextPath}/basedata/user/userAdmin.action");
}

function selectAll(){
    var checkbox = document.getElementsByName('roleids');
    var chk = document.getElementById("allSelect").checked;
    if(chk){
		for(var i=0;i<checkbox.length;i++){				 
			  checkbox[i].checked=true;			
		}
	}else{
		for(var i=0;i<checkbox.length;i++){	
			  checkbox[i].checked=false;			
		}
	}
}

</script>
<form action="" method="POST" name="roleform" id="roleform">
	<div class="query-builder">
	  	  	<span>
	  	  		<#assign maxsize=10 />
	  	  		<#list userList as user><input name="userids" type="hidden" value="${user.id?default('')}"></#list>
	  			为以下用户进行角色设定：<#list userList as user>${user.name?default('')}<#if user_has_next&&(user_index+1<maxsize)>、</#if><#if (user_index+1 >= maxsize)>等<#break></#if></#list>
	  	  	</span>
    </div>
<@htmlmacro.tableList id="tablelist">
	<tr>
		<th class="t-center">选择</th>
		<th width="20%">角色名称</th>
		<th width="15%">角色类型</th>
		<th width="15%">是否激活</th>
		<th width="45%">角色描述</th>
	</tr>

	<#list roleList as x>
		<tr>	
			<td class="t-center">
				<span class="ui-checkbox <#if x.checked?exists && x.checked>ui-checkbox-current</#if>">
					<input type="checkbox" class="chk" name="roleids" value="${x.id?default("")}" <#if x.checked?exists && x.checked>checked</#if>/>
				</span>
			</td>
			<td >
				${x.name?default('')}
			</td>
			<td >
				${x.roletypeString?default('')}
			</td>
			<td >
				<#if x.isactive>
					${x.isactiveString?default('')}
				<#else>
					<font color="red">${x.isactiveString?default('')}</font>
				</#if>
			</td>
			<td >
				${x.description?default('')}
			</td>
		</tr>
	</#list>		
</@htmlmacro.tableList>
<div class="base-operation">
	  <p class="opt">
	<span class="ui-checkbox ui-checkbox-all" data-all="no">
	<input type="checkbox" class="chk" id="selectAll">全选</span>
	<a href="javascript:void(0);" onclick="validateform();" class="abtn-blue"> 委派</a>
	<a href="javascript:void(0);" onclick="cancelUser();" class="abtn-blue"> 取消</a>
      </p>	
</div>
</form>
<script>
vselect();
</script>
</@htmlmacro.moduleDiv>
