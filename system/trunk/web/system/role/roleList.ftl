<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="角色管理">
<script>
	var url = "${request.contextPath}/system/role/";
   var deleteMessage = "删除角色将导致关联到该角色的用户失去该角色提供的权限！您确认要删除角色吗？";
   function add() {
   		load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?operation=add");
   }
   
   function doEdit(id) {
   		load("#containerRole", url +  "roleAdmin.action?operation=modify&id="+id);
	}
   
   //角色委派
   function doAccredit(){
	   	var roleids = getSelectedRoleIds();
		if(!roleids){
			showMsgWarn("请选择角色!", "", function(){});
			return;
		}
		load("#containerRole", url +  "roleAdmin-accreditFrame.action?operation=index&roleids="+roleids);
   }
   
   function lock() {
	   	var roleids = getSelectedRoleIds();
		if(!roleids){
			showMsgWarn("请选择角色!", "", function(){});
			return;
		}
		$.getJSON("${request.contextPath}/system/role/roleAdmin.action?operation=lock",{roleids:roleids},function(data){
				if(!data.operateSuccess){
					showMsgError(data.promptMessage);
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
					});	
					return;
				}
		}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);
		});
   }
   function activate() {
	   	var roleids = getSelectedRoleIds();
		if(!roleids){
			showMsgWarn("请选择角色!", "", function(){});
			return;
		}
		$.getJSON("${request.contextPath}/system/role/roleAdmin.action?operation=activate",{roleids:roleids},function(data){
				if(!data.operateSuccess){
					showMsgError(data.promptMessage);
					return;
				}else{
					showMsgSuccess(data.promptMessage, "提示", function(){
						load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
					});	
					return;
				}
		}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);
		});
	   }
   
   function getSelectedRoleIds(){
	   	var roleids = "";
	   	var ids1 = [];
   		var idi1 = [];
	   	var i = 0;
	   	$("[name='ids'][checked='checked']:input").each(function(){
	   		ids1[i] = $(this).val();
			roleids = roleids + $(this).val() + ",";
			i++;
		});
		var j = 0;
	   	$("[name='idi'][checked='checked']:input").each(function(){
	   		idi1[i] = $(this).val();
			roleids = roleids + $(this).val() + ",";
			j++;
		});
		if(ids1.length == 0 && idi1.length == 0){
			return false;
		}
		return roleids;
   }
   
   function roleDelete(){
   		var ids1 = [];
   		var idi1 = [];
  		var i = 0;
  		var j = 0;
  		$("[name='ids'][checked='checked']:input").each(function(){
			ids1[i] = $(this).val();
			i++;
		});
		$("[name='idi'][checked='checked']:input").each(function(){
			idi1[j] = $(this).val();
			j++;
		});
		if(ids1.length == 0 && idi1.length == 0){
			showMsgWarn("没有选要删除的行，请先选择！");
			return;
		}
		if(showConfirm('您确认要删除信息吗？')){	
			$.ajax({
				type: "POST",
				url: "${request.contextPath}/system/role/roleAdmin.action?operation=delete",
				data: $.param({ids:ids1, idiJson:idi1}, true),
				success: function(data){
						if(!data.operateSuccess){
							showMsgError(data.promptMessage);
							return;
						}else{
							showMsgSuccess(data.promptMessage, "提示", function(){
								load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
							});	
							return;
						}
				},
				dataType: "json",
				error: function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);}
			});
		}else{
			return;
		}
   }
</script>
<div id="containerRole">
<p class="pub-operation">
	<a href="javascript:add();" class="abtn-orange-new" id="add">新增</a>
</p>
<form name="form1" id="form1" action="" method="post">
<table border="0" cellspacing="0" cellpadding="0" class="public-table table-list">
	<tr>
		<th width="30">选择</th>
		<th width="25%">角色名称</th>
		<th width="15%">角色类型</th>
		<th width="10%">是否激活</th>
		<th width="35%">描述</th>
		<th width="10%" class="t-center">操作</th>
	</tr>
<#list roleList as role>
	<tr>
		<td class="t-center"><p><span class="ui-checkbox">
			<input type="checkbox" class="chk" <#if role.identifier?exists> name="idi" id="idi" <#else>name="ids" id="ids" </#if>value="${role.id?default("")}" />
		</span></p></td>	
		<td >
			 ${role.name?default('')}<#if role.identifier?exists><font color="red">（系统内置）</font></#if>
		</td>
		<td >
			${role.roletypeString}
		</td>
		<td >
			${role.isactiveString}
		</td>
		<td >
			${role.description}
		</td>
		<td class="t-center"><a href="javascript:doEdit('${role.id!}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
	</tr>
</#list>
</table>
<div class="base-operation" >
        	<p class="opt" style="width:500px">
            	<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>
                <a href="javascript:roleDelete();" id="roleUser" class="abtn-blue">删除</a>
                <a href="javascript:doAccredit();" id="roleUser" class="abtn-blue">委派</a>
                <a href="javascript:lock();" id="lockUser" class="abtn-blue">锁定</a>
                <a href="javascript:activate();" id="unlockUser" class="abtn-blue">激活</a>
            </p>
</div>            
</form>
</div>
<div  id="classLayer" class="popUp-layer" style="display:none;width:720px"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>vselect();</script>
</@htmlmacro.moduleDiv>
