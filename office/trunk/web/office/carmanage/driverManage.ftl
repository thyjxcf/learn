<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<script>
var isSubmit = false;
function saveDriver(){
if (isSubmit){
	return;
}
isSubmit = true;
if(!checkAllValidate("#driverManageForm")){
	isSubmit=false;
	return;
}

	var options = {
		url : "${request.contextPath}/office/carmanage/carmanage-saveDriver.action",
		success : function(data){
			var error = data;
			if(error && error != ''){
				showMsgError(data);
				isSubmit = false;
			} else {
				showMsgSuccess('保存成功!','提示',driverManage);
			}
		},
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		type : 'post',
		error:function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}//请求出错 
	};
  	$("#driverManageForm").ajaxSubmit(options);
}
</script>

<form id="driverManageForm" name="driverManageForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner mt-30">
        <div class="table-fen1-wrap" id="driverManageDiv">
            <table class="public-table table-list table-list-edit table-fen1" id="groupTable">
                <thead>
                <tr>
                    <th width="40%">姓名</th>
                    <th width="40%">手机号</th>
                    <th class="t-center" width="20%">操作</th>
                </tr>
                </thead>
				<tbody>
				<#assign index = 0>
                <#if officeCarDrivers?exists && officeCarDrivers?size gt 0>
                <#list officeCarDrivers as driver>
                <tr>
                    <td>
                    	<input type="hidden" id="id${index}" name="officeCarDrivers[${index}].id" value="${driver.id!}"/>
                    	<input type="hidden" id="unitId${index}" name="officeCarDrivers[${index}].unitId" value="${driver.unitId!}"/>
                    	<input type="hidden" id="driverId${index}" name="officeCarDrivers[${index}].driverId" value="${driver.driverId!}"/>
                    	<input type="hidden" id="name${index}" name="officeCarDrivers[${index}].name" value="${driver.name!}"/>
                    	${driver.driverName!}
                    </td>
                    <td>
						<input class="edit-txt input-txt" style="width:340px;" id="mobilePhone${index}" name="officeCarDrivers[${index}].mobilePhone" maxLength="20" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的手机号,并且不能超过20个数字" msgName="手机号" value="${driver.mobilePhone!}"/>
					</td>
                    <td class="t-center"><a href="javascript:void(0)" onclick="doDelete(event);"><img alt="删除" src="${request.contextPath}/static/images/icon/del2.png"></a></td>
                </tr>
                <#assign index=index+1>
                </#list>
                </#if>
            </tbody>
		    <tfoot>
			<tr>
				<td colspan="5" class="t-center"><a href="javascript:void(0);" class="bottom-add-new bottom-add-new-gray" id="addDriver">+ 新增驾驶员</a></td>
			</tr>
			</tfoot>
            </table>
        </div>
        <p class="t-center pt-30">
            <a href="javascript:void(0);" class="abtn-blue-big" onclick="saveDriver();">保存</a>
        </p>
    </div>
</div>
</form>
<@commonmacro.selectOneUser idObjectId="driverId" nameObjectId="driverName" width=400 height=300 callback="doDriverSave">
<input type="hidden" name="driverIndex" id="driverIndex" value="">
<input type="hidden" name="driverId" id="driverId" value=""> 
<input type="hidden" name="driverName" id="driverName" value="">
<a id="pop-class"></a>
</@commonmacro.selectOneUser>
<script>
vselect();
function changeDriver(index){
	$("#driverIndex").val(index);
	$("#driverId").val($("#driverId"+index).val());
	$("#driverName").val($("#name"+index).val());
	$("#pop-class").click();
}

function doDriverSave(){
	var index = $("#driverIndex").val();
	$("#driverId"+index).val($("#driverId").val());
	$("#name"+index).val($("#driverName").val());
}

var trIndex  = $('#addDriver').parents('table').find('tbody tr').size();
$(function(){
	$('#addDriver').click(function(e){
		e.preventDefault();
		var chr="<tr>";
		chr=chr+"<input type='hidden' id='id"+trIndex+"' name='officeCarDrivers["+trIndex+"].id' value=''/>";
		chr=chr+"<td><input type='hidden' name='officeCarDrivers["+trIndex+"].driverId' id='driverId"+trIndex+"' value='' notNull='true' msgName='驾驶员'/><input type='text' id='name"+trIndex+"' name='officeCarDrivers["+trIndex+"].name' value='' onclick='changeDriver("+trIndex+");' class='input-txt input-readonly' style='width:190px;' readonly='readonly'/></td>";
		chr=chr+"<td><input name='officeCarDrivers["+trIndex+"].mobilePhone' id='mobilePhone"+trIndex+"' class='input-txt' style='width:340px;' value='' notNull='true' msgName='手机号' maxLength='20' regex='/^[0-9]{1,20}$/' regexMsg='请输入正确的手机号,并且不能超过20个数字'/></td>";
		chr=chr+"<td class='t-center'><a href='javascript:void(0)' onclick='doDelete(event);'><img alt='删除' src='${request.contextPath}/static/images/icon/del2.png'></a></td>";
		chr=chr+"</tr>";
		$(chr).appendTo($(this).parents('table').find('tbody'));
		$('.public-table tr:odd').addClass('odd');
		
		trIndex++;
	});
});
function doDelete(event){
	var obj = event.srcElement ? event.srcElement : event.target;
	var driverId = $(obj).parents("tr").find('input[name*="id"]').val();
	if(!driverId){
		$(obj).parents("tr").remove();
	}
	else{
		if(showConfirm("确定要删除该驾驶员信息？")){
			$.getJSON("${request.contextPath}/office/carmanage/carmanage-deleteDriver.action",{"driverId":driverId},function(data){
				if(data && data != ""){
					showMsgError(data);
				}else{
					showMsgSuccess('删除成功!','提示');
					driverManage();
				}
			}).error(function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);});
		}
	}
	$('.public-table tr').removeClass('odd');
	$('.public-table tr:odd').addClass('odd');
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>