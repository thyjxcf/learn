<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="编辑角色">
<script>
var prefix = "${request.contextPath}/static/common/xtree/";
function saveRole(){
	var isValid = false;
	if(!isActionable("#btnSaveAll")){
		return;
	}
	
	if(!checkElement(document.getElementById("name"), "角色名称")){
		$("#btnSaveAll").attr("class", "abtn-blue-big");
		return;
	}
	if(!checkOverLen(document.getElementById("name"),50,"角色名称")) {
		$("#btnSaveAll").attr("class", "abtn-blue-big");
		return;	
	}
	if(!(trim($("description").val())=='')){
		if(!checkOverLen(document.getElementById("description"),255,"角色描述")) {
			$("#btnSaveAll").attr("class", "abtn-blue-big");
			return;
		}
	}		
	var hasModChecked = false;
	var hasModExChecked = false;
	var mods = document.getElementsByName('modids');
	//var mods = $("[name='modids']");
    for (i = 0; i < mods.length; i++) {
    	//alert(i+"----"+mods[i].checked);
    	if(mods[i].checked){
    		hasModChecked = true;
    		break;
    	}
    }
    var modsEx = document.getElementsByName('extraids');
    //var modsEx = $("[name='extraids']");
    for (i = 0; i < modsEx.length; i++) {
    	if(modsEx[i].checked){
    		hasModExChecked = true;
    		break;
    	}
    }
    if(!hasModChecked && !hasModExChecked){
    	showMsgWarn('请选择相应的模块权限');
    	$("#btnSaveAll").attr("class", "abtn-blue-big");
		return;
    }
    for (i = 0; i < mods.length; i++) {
    	if(mods[i].disabled){
    		mods[i].disabled = '';
    	}
    }
    for (i = 0; i < modsEx.length; i++) {
    	if(modsEx[i].disabled){
    		modsEx[i].disabled = '';
    	}
    }
    $("#btnSaveAll").attr("class", "abtn-unable-big");
	var roleId = '${roleDto.id?default('')}';
	var roleName = trim($("#name").val());
	var description = trim($("#description").val());
	$.getJSON("${request.contextPath}/system/role/roleAdmin-remote.action", {
		"roleId":roleId, "roleName":roleName, "description":description
		}, function(data){
			if(data != null && data != ""){
				showMsgError(data);
				$("#btnSaveAll").attr("class", "abtn-blue-big");
				return;
			}
			else{
				saveRoleSubmit();
				return;
			}
	}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);
	});
}

function saveRoleSubmit(){
		var operation = $("#operation").val();
	 	var modids = [];
   		var extraids = [];
   		var operids = [];
	   	var i = 0;
	   	$("[name='modids']:checked").each(function(){
	   			modids[i] = $(this).val();
				i++;
		});
		var j = 0;
	   	$("[name='extraids']:checked").each(function(){
		   		extraids[i] = $(this).val();
				j++;
		});
		var k = 0;
	   	$("[name='operids']:checked").each(function(){
		   		operids[k] = $(this).val();
				k++;
		});
		var jsonString = getJsonStr("#containerRoleEdit");
		$.ajax({
				type: "POST",
				url: "${request.contextPath}/system/role/roleAdmin.action",
				data: $.param({jsonString:jsonString, operation:operation, modids:modids, extraids:extraids, operids:operids}, true),
				success: function(data){
						if(!data.operateSuccess){
							showMsgError(data.promptMessage);
							$("#btnSaveAll").attr("class", "abtn-blue-big");
							return;
						}else{
							$("#btnSaveAll").attr("class", "abtn-blue-big");
								showMsgSuccess(data.promptMessage, "提示", function(){
								load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action?moduleID=${moduleID!}");
							});	
							return;
						}
				},
				dataType: "json",
				error: function(XMLHttpRequest, textStatus, errorThrown){alert(errorThrown);}
		});
		
}

function extraCheck(treeNode){
	var divEl = document.getElementById(treeNode.id);
	var inputEl = divEl.getElementsByTagName("INPUT")[0];
	if(inputEl == null){
		return;
	}
	if(inputEl.checked && treeNode.parentNode.getChecked != null && !treeNode.parentNode.getChecked()){
		var divE2 = document.getElementById(treeNode.parentNode.id);
		var inputE2 = divE2.getElementsByTagName("INPUT")[0];
		if(inputE2.name != null && inputE2.name != '' && inputE2.name != 'subsys'){
			inputE2.checked = 'checked';
		}
	}
	
	if(!inputEl.checked && treeNode.parentNode.getChecked != null && treeNode.parentNode.getChecked()){
		var divE2 = document.getElementById(treeNode.parentNode.id);
		var inputE2 = divE2.getElementsByTagName("INPUT")[0];
		if(inputE2.name == 'subsys'){
			inputE2.checked = '';
		}
	}
	
	extraCheck(treeNode.parentNode);
}

function cancel(){
	load("#containerRole", "${request.contextPath}/system/role/roleAdmin.action");
}

function treeExpandAll(){
	if(${treeName}){
		${treeName}.expandAll();
	}
}
function treeCollapseAll(){
	if(${treeName}){
		${treeName}.collapseAll();
		${treeName}.expand();
	}
}

function checkOverLen1(elem,maxlen,field){
	var len;
	var i;
	len = 0;
	var val = trim(elem.value);
	var maxlength = parseInt(maxlen);
	var length = val.length;
	for (i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}
	if(len>maxlength){
		addActionError(field+"长度超过范围,允许范围为0-"+maxlength);
		elem.focus();
		return false;
	}
	return true;
}
function onKeyDown(){
	if(event.keyCode==13 ){
		return false;
	}
}
</script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
<script language="JavaScript" src="${request.contextPath}/static/common/xtree/webfxcheckboxtreeitem.js"></script>
<div id="containerRoleEdit">
<form action="roleAdmin.action" method="post" name="roleform">
<input type="hidden" name="roletype" id="roletype" value="0">
<input type="hidden" name="id" id="id" value="${roleDto.id?default('')}">
<input type="hidden" name="identifier" id="identifier" value="${roleDto.getIdentifier()!}">
<input type="hidden" name="operation" id="operation" value="save">
<div class="pub-table-wrap">
<div class="pub-table-inner">
		<div class="fn-left">
		<p class="table-dt">${operationName?default('')?trim}</p>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-form">
		<tr>
		    <th width="80">角色名称：</th>
		    <td><input id="name" name="name" type="text" class="input-txt fn-left" style="width:140px;" value="${roleDto.getName()?default('')?trim}" title="角色名称不能超过50个字符或25个汉字！" onKeyDown="return onKeyDown();">
		    <span class="fn-left c-orange mt-5 ml-10">*</span>
		    </td>
		</tr>
		<tr>
		    <th width="80">是否激活：</th>
		    <td>
		    	<div class="ui-select-box fn-left" style="width:150px;">
		                <input type="text" class="ui-select-txt" value=""/>
		                <input type="hidden" name="isactive" id="isactive" value="" class="ui-select-value" />
		                <a class="ui-select-close"></a>
		                <div class="ui-option">
		                	<div class="a-wrap">
		                    <a val="true" <#if roleDto.isactive?exists && roleDto.isactive> class="selected"</#if>><span>是</span></a>
		                    <a val="false" <#if roleDto.isactive?exists && !roleDto.isactive> class="selected"</#if>><span>否</span></a>
		                    </div>
		                </div>
			    </div>
			    <span class="fn-left c-orange mt-5 ml-10">*</span>
		    </td>
		</tr>
		<tr>
		    <th width="80">角色概述：</th>
		    <td><textarea class="text-area my-10" id="description" name="description" title="角色描述不能超过255个字符或127个汉字！">${roleDto.description?default('')?trim}</textarea></td>
		</tr>
</table>
</div>		
<div class="fn-right" style="width:50%;overflow-y:auto;height:320px;" id="roleTree">
	<script>
		${xtreeScript}
		//$("#roleTree").html(${treeName});
		document.getElementById("roleTree").innerHTML=${treeName};
	</script>
</div>
<div class="fn-clear"></div>
<p style="text-align:center;padding:50px 0;">
            	<a id="btnSaveAll" href="javascript:saveRole();" class="abtn-blue-big">保存</a>
                <a href="javascript:cancel();" class="abtn-blue-big">返回</a>
                <a href="javascript:treeExpandAll();" class="abtn-blue-big">展开</a>
                <a href="javascript:treeCollapseAll();" class="abtn-blue-big">收缩</a>
</p>
</div>
</div>
</form>
</div>
<script>
	var treeChild = ${treeName}.childNodes;
	if(treeChild){
		for(var i = 0; i<treeChild.length;i++){
			treeChild[i].expand();
		}
	}
	vselect();
</script>
</@htmlmacro.moduleDiv>