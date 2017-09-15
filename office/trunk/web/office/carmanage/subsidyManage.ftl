<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">

<form id="driverManageForm" name="driverManageForm">
<div class="pub-table-wrap">
	<div class="pub-table-inner mt-30">
        <div class="table-fen1-wrap" id="driverManageDiv">
            <table class="public-table table-list table-list-edit table-fen1" id="groupTable">
                <tr>
                    <th width="40%">车辆范围</th>
                    <th width="40%">补贴金额</th>
                    <th class="t-center" width="20%">操作</th>
                </tr>
                <#if officeSubsidys?exists && officeSubsidys?size gt 0>
                <#list officeSubsidys as x>
                <tr>
                    <td class="edit-td">
                    	<input type="hidden" id="subsidyIds" name="subsidyIds" value="${x.id!}"/>
                    	<input type="text" class="edit-txt input-txt" id="scopeNames" name="scopeNames" maxLength="20" notNull="true" msgName="车辆范围" value="${x.scope!}"/>
                    </td>
                    <td class="edit-td">
				<input type="text" class="edit-txt input-txt" id="subsidys" name="subsidys" maxLength="7" notNull="true" dataType="float" maxValue="9999" minValue="0" msgName="补贴金额" value="${x.subsidy?string('0.##')}"/>
			</td>
                    <td class="t-center"><a href="javascript:void(0);" class="del delGroupTr" id="${x.id!}"><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a></td>
                </tr>
                </#list>
                </#if>
            </table>
            <a href="javascript:void(0);" class="bottom-add-new bottom-add-new-gray" id="addDriver">+ 新增车辆补贴</a>
        </div>
        <p class="t-center pt-30">
            <a href="javascript:void(0);" class="abtn-blue-big" onclick="saveDriver();">保存</a>
        </p>
    </div>
</div>
</form>
<table id="copyTr" style="display:none;">
	<tr>
        <td class="t-center edit-td">
        	<input type="text" class="edit-txt input-txt" id="scopeNames" name="scopeNames" msgName="车辆范围" maxLength="20" notNull="true" value=""/>
    	</td>
        <td class="t-center edit-td">
			<input type="text" class="edit-txt input-txt" id="subsidys" notNull="true" name="subsidys" maxLength="7" dataType="float" maxValue="9999" minValue="0" msgName="补贴金额" value=""/>
		</td>
        <td class="t-center"><a href="javascript:void(0);" class="del delGroupTr" id=""><img src="${request.contextPath}/static/images/icon/del2.png" alt="删除"></a></td>
    </tr>
</table>
<script>
$(document).ready(function(){
	$('#addDriver').click(function(e){
		e.preventDefault();
		$($('#copyTr tr').clone(false)).appendTo('#groupTable');
		doFalsh();
	});
	function doFalsh(){
		$(".delGroupTr").unbind("click").click(function(e){
			e.preventDefault();
			var id = $(this).attr("id");
			var flag = false;
			var index = $(this).parents("tr").index();
			if(id && id!=''){
				if(confirm("确定要删除吗？")){			
					$.ajax({
						type: "POST",
						url: "${request.contextPath}/office/carmanage/carmanage-deleteSubsidy.action",
						data: $.param({driverId : id},true),
						success: function(data){
							if(data && data!=''){
								showMsgError(data);
							}else{
								showMsgSuccess("删除成功！");
								flag = true;
							}
						},
						dataType: "json",
						error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
					});
				}
			}else{
				flag = true;
			}
			setTimeout(function(){
				if(flag){
					$(".table-fen1 tr:eq("+index+")").remove();
				}
			},200);
		});
	}
	doFalsh();
});

var isSubmit = false;
function saveDriver(){
	var table =document.getElementById("groupTable");
	var rows = table.rows.length;
	if(rows<2){
   		showMsgWarn("没有数据需要保存!");
   		return;
	}
	if(!checkAllValidate("#driverManageDiv")){
		return false;
	}
	var driverNames_ = document.getElementsByName("scopeNames");
	var flag = false;
	for(var m=0;m<driverNames_.length;m++){
		for(var n=m+1;n<driverNames_.length;n++){
			if(driverNames_[m].value == driverNames_[n].value){
				flag = true;
				break;
			}
		}
		if(flag){
			break;
		}
	}
	if(flag){
		showMsgWarn("出车范围不能相同!");
		return false;
	}
	showSaveTip();
	isSubmit = true;
	var options = {
	       url:'${request.contextPath}/office/carmanage/carmanage-saveSubsidy.action',
		   success : showReply,
	       dataType : 'json',
           clearForm : false,
           resetForm : false,
           type : 'post'
    };
	$('#driverManageForm').ajaxSubmit(options);
}
	
function showReply(data){
    closeTip();
	if(data && data != ''){
		showMsgError(data);
		isSubmit = false;
	} else {
		showMsgSuccess('保存成功','',subsidyManage);
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>