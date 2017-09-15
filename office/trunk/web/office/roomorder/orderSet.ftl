<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myOrder(){
	var url="${request.contextPath}/office/roomorder/roomorder.action";
	load("#container", url);
}


//$('#orderSetForm').find('input[type=checkbox]').find("input[name='checkidd']").bind('click', function(){
//alert(1);
//$('#orderSetForm').find('input[type=checkbox]').find("input[name='checkidd']").not(this).attr("checked", false);
//});


function orderApply(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApply.action";
	load("#container", url);
}

function orderAudit(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAudit.action";
	load("#container", url);
}

function timeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
	load("#container", url);
}

function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}

function labTypeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-labTypeSet.action";
	load("#container", url);
}

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}

function test(obj){
	if($("#checkidd"+obj).attr("checked")){
		$($("#checkidd"+obj)).parent('span').removeClass("ui-checkbox ui-checkbox-current");
		$($("#checkidd"+obj)).parent('span').addClass("ui-checkbox");
		//$(this).addClass("ui-checkbox"); 
		$($("#checkidd"+obj)).removeAttr("checked");
		$($("#checkidd"+obj)).val("0");
		}
	if($("#useType_"+obj).val()==null||$("#useType_"+obj).val()==''){
		if($("#checkid"+obj).attr('checked') != 'checked'){
			showMsgWarn("你已选择使用，请选择使用类型!");
			return;
			}
		}
	if($("#needAudit_"+obj).val()==null||$("#needAudit_"+obj).val()==''){
		if($("#checkid"+obj).attr('checked') != 'checked'){
			showMsgWarn("你已选择使用，请选择是否审核!");
			return;
			}
		}
}

function isSelect(obj){
	$("input[name='checkidd'][checked='checked']").each(function(){
		if($("#checkidd"+obj).attr("checked")){
			$("#checkidd"+obj).val("0");
			return;
		}
		if ($(this).attr("checked")) {
		$(this).parent('span').removeClass("ui-checkbox ui-checkbox-current");
		$(this).parent('span').addClass("ui-checkbox");
		//$(this).addClass("ui-checkbox"); 
		$(this).removeAttr("checked");
		$(this).val("0");
	} 
		//$(this).prop("checked",'');
	});
	if($("#checkidd"+obj).attr("checked")){
			return;
		}
	if($("#checkid"+obj).attr("checked")==undefined){
			showMsgWarn("请选择是否使用!");
			return;
		}
	$("#checkidd"+obj).val("1");
}
   
function save(){
	var ids = [];
	var disableIds=[];
	var needAudit=[];
	var useType=[];
	var isDefault=[];
	if(!confirm("确定保存吗？")){
		return;
	}
	
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	jQuery("#selected_ids").val(ids);
	var j = 0;
	$("input[name='checkid'][checked!='checked']").each(function(){
		disableIds[j] = $(this).val();
		j++;
	});
	jQuery("#unSelected_ids").val(disableIds);
	var selected_ids=$("#selected_ids").val();
	if(selected_ids != ""){
		var selected_id=selected_ids.split(",");
		for(i=0;i<selected_id.length;i++){
			if($("#useType_"+selected_id[i]).val()==null||$("#useType_"+selected_id[i]).val()==''){
					var thisId=$("#"+selected_id[i]).val();
					showMsgWarn("你已选择"+thisId+"使用，请选择使用类型!");
					return;
				}	
			if($("#needAudit_"+selected_id[i]).val()==null||$("#needAudit_"+selected_id[i]).val()==''){
					var thisId=$("#"+selected_id[i]).val();
					showMsgWarn("你已选择"+thisId+"使用，请选择是否审核!");
					return;
				}	
		}
	}
	var selected_idss=$("#selected_ids").val();
	var selected=selected_idss.split(",");
	for(j=0;j<selected.length;j++){
		useType[j]=$("#useType_"+selected[j]).val();
		needAudit[j]=$("#needAudit_"+selected[j]).val();
		isDefault[j]=$("#checkidd"+selected[j]).val();
	}
	jQuery("#useTypes").val(useType); 
	jQuery("#needAudits").val(needAudit); 
	jQuery("#isDefaults").val(isDefault); 
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/roomorder/roomorder-orderSetSave.action",
		data: $('#orderSetForm').serialize(),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("操作成功！", "提示", function(){
					var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
		    		load("#container",url);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
      
}
      
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="myOrder();">我的预约</li>
	<li onclick="orderApply();">预约申请</li>
	<#if auditAdmin>
	<li onclick="orderAudit();">预约审核</li>
	<li onclick="timeSet();">时段设置</li>
	<li class="current" onclick="orderSet();">类型信息设置</li>
	<#if !edu>
	<li onclick="labTypeSet();">实验种类设置</li>
	</#if>
	</#if>
	<#if !edu>
	<li onclick="labApplyCount();">实验申请统计</li>
	</#if>
	</ul>
</div>
<form method="post" name="orderSetForm" id="orderSetForm">
<input type="hidden" value="" name="selected_ids" id="selected_ids"/>
<input type="hidden" value="" name="unSelected_ids" id="unSelected_ids"/>
<input type="hidden" value="" name="useTypes" id="useTypes"/>
<input type="hidden" value="" name="needAudits" id="needAudits"/>
<input type="hidden" value="" name="isDefaults" id="isDefaults"/>
<div class="pub-table-wrap">
	<div class="pub-table-inner mt-30">
<@common.tableList id="tablelist">
	<tr>
		<th width="20%">类型名称</th>
		<th width="20%">使用类型</th>
		<th width="20%">是否审核</th>
		<th width="20%">是否使用</th>
		<th width="20%">是否默认</th>
	</tr>
	<#if officeRoomOrderSetList?exists && officeRoomOrderSetList?size gt 0>
    		<#list officeRoomOrderSetList as roomOrderSet>
    		<tr>
	        <td>
	        <input type="hidden" id="${roomOrderSet.thisId?default('')}" name="thisIds" value="${appsetting.getMcode('DM-CDLX').get(roomOrderSet.thisId?default(''))}"/>
				${appsetting.getMcode('DM-CDLX').get(roomOrderSet.thisId?default(''))}
			</td>
			<td>
				<@common.select style="width:100px;" valName="useType" valId="useType_${roomOrderSet.thisId?default('')}">
							<a val="">请选择</a>
							<a val="1"  <#if (roomOrderSet.useType?default("")=="1")>class="selected"</#if>><span>节次</span></a>
							<a val="2"  <#if (roomOrderSet.useType?default("")=="2")>class="selected"</#if>><span>时间段</span></a>
				</@common.select>
			</td>
	        <td>
        		<@common.select style="width:100px;" valName="needAudit" valId="needAudit_${roomOrderSet.thisId?default('')}">
        					<a val="">请选择</a>
							<a val="0"  <#if (roomOrderSet.needAudit?default("")=="0")>class="selected"</#if>><span>不审核</span></a>
							<a val="1"  <#if (roomOrderSet.needAudit?default("")=="1")>class="selected"</#if>><span>审核</span></a>
				</@common.select>
            </td>
	        <td>
	        	<#if roomOrderSet.id?default('')!=''>
				<span class="ui-checkbox ui-checkbox-current" onclick="test('${roomOrderSet.thisId?default('')}');"><input type="checkbox" class="chk" checked="checked" id="checkid${roomOrderSet.thisId?default('')}" name="checkid" value="${roomOrderSet.thisId?default('')}"></span>
				<#else>
					<span class="ui-checkbox" onclick="test('${roomOrderSet.thisId?default('')}');"><input type="checkbox" class="chk" id="checkid${roomOrderSet.thisId?default('')}" name="checkid" value="${roomOrderSet.thisId?default('')}"></span>
				</#if>
			</td>
	        <td>
	        	<#if roomOrderSet.isSelected?default('0')!='0'>
				<span class="ui-checkbox ui-checkbox-current" onclick="isSelect('${roomOrderSet.thisId?default('')}');"><input type="checkbox" class="chk" checked="checked" id="checkidd${roomOrderSet.thisId?default('')}" name="checkidd" value="${roomOrderSet.isSelected?default(0)}"></span>
				<#else>
					<span class="ui-checkbox" onclick="isSelect('${roomOrderSet.thisId?default('')}');"><input type="checkbox" class="chk" id="checkidd${roomOrderSet.thisId?default('')}" name="checkidd" value="${roomOrderSet.isSelected?default('0')}"></span>
				</#if>
			</td>
	        </tr>
            </#list>
        <#else>
        <tr>
        	<td colspan="8">
	        	<p class="no-data mt-50 mb-50">还没有数据哦！</p>
	        </td>
        </tr>
        </#if>
</@common.tableList>
<div class="t-center mt-10">
		<a href="javascript:void(0);" id="btnSaveAll" class="abtn-blue-big" onclick="javascript:save();">保存</a>
</div>
	</div>
</div>
</form>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>