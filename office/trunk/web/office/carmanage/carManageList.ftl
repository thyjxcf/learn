<#import "/common/htmlcomponent.ftl" as common />
<form>
<@common.moduleDiv titleName="">
<@common.tableList id="tablelist">
	<tr>
		<th width="20%" >车牌号码</th>
		<th width="20%" >车辆型号</th>
		<th width="10%" >车辆容量</th>
		<th width="40%" >备注</th>
		<th width="10%" style="text-align:center;">操作</th>
	</tr>
	<#if officeCarInfos?exists && officeCarInfos?size gt 0>
		<#list officeCarInfos as x>
			<tr>
				<td >${x.carNumber!}</td>
				<td >${x.carType!}</td>
				<td >${x.seating!}</td>
				<td>
				<@common.cutOff4List str="${x.remark!}" length=32 />
				</td>
				<td style="text-align:center;">
					<a href="javascript:void(0)" onclick="editCar('${x.id!}')"><img src="${request.contextPath}/static/images/icon/edit.png" title="编辑"></a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="deleteCar('${x.id!}')"><img src="${request.contextPath}/static/images/icon/del2.png" title="删除"></a>
				</td>
			</tr>
		</#list>
	<#else>
		<tr>
	        <td colspan="5"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
	   	</tr>
	</#if>
</@common.tableList>
<@common.Toolbar container="#carManageListDiv"/>
</form>
<script>

function editCar(id){
	var url="${request.contextPath}/office/carmanage/carmanage-carManageEdit.action?carId="+id;
	load("#carManageListDiv", url);
}

function deleteCar(id){
	if(confirm("确定要删除吗？")){
	    $.getJSON("${request.contextPath}/office/carmanage/carmanage-deleteCar.action?carId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("操作成功！", "提示", function(){
				doSearch();
			});
	      }
	    });
	}
}

</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>