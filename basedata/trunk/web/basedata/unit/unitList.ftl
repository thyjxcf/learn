<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<#assign objectName = stack.findValue("@net.zdsoft.eis.base.data.action.UnitImportAction@UNIT_IMPORT") >
<@htmlmacro.moduleDiv titleName="单位管理列表">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>

<div id="container1">    
    <form name="form1" id="form1" action="" method="post">
	<div class="query-builder mt-10" style="height:30px;">
        <div class="query-part">
            <div class="query-tt">单位名称：</div>
            <input type="text" name="unitName" id="unitName" value="${unitName?default('')}" class="input-txt fn-left">
            <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="searchUnit();">查询</a>
            <div class="fn-right">
            	
                <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="addNewUnit();">新增</a>
                <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="importUnit();">导入</a>
                <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="excueUnit();">导出</a>
            </div>
            <div class="fn-clear"></div>
        </div>
    </div>
    
	<@htmlmacro.tableList id="tablelist" class="public-table table-list mt-15">
	<tr>
	    <#if maintenanceUnit>
		<th width="7%">选择</th>
		</#if>
		<th width="">单位名称</th>
		<th width="15%">单位管理员</th>
		<th width="15%">单位分类</th>
		<th width="15%">注册日期</th>
		<!--th width="15%">当前状态</th-->
		<th width="15%">统一编号</th>
		<th width="7%" class="t-center">操作</th>
	</tr>
	<#if listOfUnitDto?exists&&(listOfUnitDto?size>0)>
	<#list listOfUnitDto as x>
	<tr>
		<#if maintenanceUnit>
 		<td class="t-center">
	 		<#if unitId?default('-1')==x.id?default('0')>
      			<p><span class="ui-checkbox ui-checkbox-disabled"><input type="checkbox" name="noArrayIds" disabled value="${x.id?default('')}" class="chk"></span></p>
	      	<#else>			         	
	        	<p><span class="ui-checkbox"><input type="checkbox" name="ids" value="${x.id?default('')}" class="chk"></span></p>
	      	</#if>
		</td>
		</#if>
		<td >
			${x.name?default("")}<#if unitId?default('-1')==x.id?default('0')><font color="red">（本单位）</font></#if>
		</td>
		<td >
			<#assign userDto = userMap[x.id?default("")]?default("")>
		  	<#if userDto!="">
		  		${userDto.name?default("")}
		  	</#if>
		</td>
		<td >
			${appsetting.getMcode("DM-DWFL").get(x.unitclass?default('')?string)}
		</td>
		<td >
			<#if x.creationTime?exists>${x.creationTime?string("yyyy-MM-dd")}</#if>
		</td>
		<!--td>
			<#if x.mark==unitMarkWithOutAutidt><font color="red"></#if>${appsetting.getMcode("DM-DWZT").get(x.mark?default('')?string)}<#if x.mark==unitMarkWithOutAutidt></font></#if>
		</td-->
		<td >
			${x.unionid?default("")}
		</td>
		<td class="t-center">
		<a href="javascript:void(0);" onclick="editUnit('${x.id?default("")}','${unitId?default('')}');"><img src="${request.contextPath}/static/images/icon/edit.png" alt="编辑"></a></td>
	</tr>
	</#list>	
	<#else>
	  <tr><td colspan="88"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
	</#if>
	
	</@htmlmacro.tableList>
	<#if listOfUnitDto?exists && listOfUnitDto?size gt 0>
	<@htmlmacro.Toolbar container="#container1">
		<p class="opt">
	    	<!--<span class="ui-checkbox ui-checkbox-all" data-all="no"><input type="checkbox" class="chk">全选</span>-->
	        <a class="abtn-blue" href="javascript:doDelete();">删除</a>
	        <input type="hidden" name="currUnitId" id="currUnitId" value="${unitId?default('')}"/>
	    </p>
	</@htmlmacro.Toolbar>
	</#if> 
  </form>
</div>
<div class="popUp-layer" id="addDiv" style="display:none;width:600px;"></div>
<script type="text/javascript">
$('#unitName').keydown(function(event){
    if(event.keyCode==13){
    	searchUnit();
    }
});

function editUnit(unitId,parentUnitId){
 	var unitName=$.trim($('#unitName').val());
    unitName=encodeURIComponent(unitName);
	openDiv("#addDiv", "#addDiv .close,#addDiv .reset", "${request.contextPath}/basedata/unit/unitAdmin-edit.action?modID=${modID?default('')}&&unitId=" + unitId + "&unitName="+unitName+"&pageIndex=${pageIndex!}"+"&parentUnitId="+parentUnitId, null, null, 400, vselect);
}

function searchUnit(){
	var unitName=$.trim($("#unitName").val());
	if(unitName==''){
		return ;
	}
	if(unitName.indexOf('\'')>-1||unitName.indexOf('%')>-1){
		showMsgError("请确认欲查询的单位名称不包含单引号、百分号等特殊符号！");
		return ;
	}	
    var str = "unitName="+encodeURI(unitName);
    load("#container1","${request.contextPath}/basedata/unit/unitAdmin-list.action?"+str);
}

function addNewUnit() {
	<#if notSch>
		showMsgError("非教育局单位不允许新增下级单位");
		return ;
	</#if>
	var currUnitId = $('#currUnitId').val();
	openDiv("#addDiv", "#addDiv .close,#addDiv .reset", "${request.contextPath}/basedata/unit/unitAdmin-new.action?unitId="+currUnitId+"&modID=${modID?default('')}", null, null, 400, vselect);
}

function edit(id) {
	openDiv("#classLayer", "#classLayer .close,#classLayer .submit,#classLayer .reset", "${request.contextPath}/basedata/basedept/baseDeptAdmin-edit.action?id="+id, null, null, "500px");
}
  	
function doDelete() {;
	var ids =[];
	var i = 0;
	$("input[name='ids']:checked").each(function(){
           ids[i] = $(this).val();
           i++;
	});
	if (ids.length == 0) {
		showMsgError("没有选要操作的行，请先选择！");
		return;
	}
	if (ids.length >1) {
		showMsgError("请确认选择了要删除的单位并且只选择了一个！");
		return;
	}
	var currUnitId = $('#currUnitId').val();
	var message = "删除单位，将删除该单位下所有重要信息，且不可恢复；请确认单独删除！";
	if(showConfirm(message)){
		$.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/unit/unitAdmin-unitDelete.action",
			data: $.param({unitIds:ids},true),
			dataType: "json",
			success: function(data){
				if(data.operateSuccess){
					showMsgSuccess(data.promptMessage,"提示",function(){
						//load("#container","${request.contextPath}/basedata/unit/unitAdmin.action?unitId=${unitId?default('')}");
						load("#container1","${request.contextPath}/basedata/unit/unitAdmin-list.action?unitId="+currUnitId);
						load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${loginInfo.getUnitID()?default('')}");
					});
				}else{
					showMsgError(data.errorMessage);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
		});
	}
}
function importUnit(){
	var url = "${request.contextPath}/basedata/unit/unitAdmin-importMain.action?objectName=${objectName!}";
	load('#container',url);
}

function excueUnit(){
	location.href="${request.contextPath}/basedata/unit/unitAdmin-export.action";
}
</script>
</@htmlmacro.moduleDiv>