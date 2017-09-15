<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function changeDay(value){
	var deptId=$("#deptId").val();
	load("#boardRoomDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderAdmin.action?officeBoardroomXjId="+'${officeBoardroomXj.id!}'+"&prev="+value+"&applyStartDate="+'${applyStartDate!}');
}
function doOrder(){
	var deptId=$("#deptId").val();
	load("#orderApplyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderList.action?officeBoardroomXjId="+'${officeBoardroomXj.id!}'+"&deptId="+deptId+"&prev="+'${prev!}'+"&applyStartDate="+'${applyStartDate!}'+"&applyEndDate="+'${applyEndDate!}');
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<p class="table-dt">会议室预约</p>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10">
					<!--<span class="fn-left">${officeBoardroomXj.name!}</span>-->
					<span style="margin-right:30px;font-size: 14px;color: #444444;">${officeBoardroomXj.name!}</span>
					<input type="hidden" name="officeApplyDetailsXj.roomId" id="roomId" value="${officeBoardroomXj.id!}"/>
				</div>
			</div>
    	</div>
    </div>
</div>
<div id="container">
	<div class="fn-clear">
		
    	<p class="typical-ttt fn-left">
    	    <span>&nbsp;&nbsp;&nbsp;</span>
    		<span class="block green"></span>
    		<span>申请</span>
    		<span class="block gray ml-20"></span>
    		<span>已约</span>
    		<span class="block orange ml-20"></span>
    		<span>审核中</span>
    		<!--<span class="c-red">&nbsp;过去的时间段不能申请</span>-->
    	</p>
        
    	<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="margin-top: -10px;">  
    		<div class="query-part">
				<div class="query-tt ml-10" style="margin-top:-10px;">
					<span class="fn-left">申请使用的部门：</span>
				</div>
				<div class="fn-left" style="margin-top:-10px;">
					<@common.select style="width:170px;" valName="deptId" valId="deptId" myfunchange="doOrder">
						<a val="">请选择</a>
						<#list deptList as item>
						<a val="${item.id!}" <#if deptId?exists&&deptId==item.id> class="selected" </#if>>${item.deptname!}</a>
						</#list>
					</@common.select>
				</div>
			</div>
    	</div>
    </div>
    <p class="typical-time typical-time2 fn-right" style="margin-right:48px;margin-top:-11px;">
        	<a href="javascript:void(0);" onclick="changeDay(1);" class="prev"></a>
        	<span class="date">${applyStartDate!}——${applyEndDate!}</span>
        	<a href="javascript:void(0);" onclick="changeDay(2);" class="next"></a>
        </p>
</div>
</div>
	<div id="orderApplyListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>

$(document).ready(function(){
	$("#deptId").val('${deptId!}');
	doOrder();
	//load("#orderApplyListDiv","${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderList.action?officeBoardroomXjId="+'${officeBoardroomXj.id!}'+"&deptId="+'${deptId!}');
});
</script>
</@common.moduleDiv>