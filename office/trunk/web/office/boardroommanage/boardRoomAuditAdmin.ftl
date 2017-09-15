<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>

function searchOrder(){
	var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
	var roomId = document.getElementById("roomId").value;
	var deptId = document.getElementById("deptId").value;
	var url="${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditList.action";
	url += "?show="+status;
	url += "&officeBoardroomXjId="+roomId;
	url += "&deptId="+deptId;
	load("#myOrderListDiv", url);
}

$(document).ready(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
			searchOrder();
		});
		searchOrder();
	});

</script>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >

<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt" style="margin-top:-3px;">
    				<input type="hidden" name="show" id="show" value="${show!}">
			    	<span id="resourceofficedocTypeList" class="user-sList user-sList-radio">
				        <span <#if show?default("0")=="0">class="current"</#if> data-select="0" key="0">未审核</span>
				        <span <#if show?default("0")=="1">class="current"</#if> data-select="1" key="1">已审核</span>
			    	</span>
    			</div>
				<div class="query-tt ml-10">
					<span class="fn-left">会议室：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:170px;" valName="roomId" valId="roomId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeBoardroomXjList as item>
						<a val="${item.id}">${item.name!}</a>
						</#list>
					</@common.select>
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">部门：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:170px;" valName="deptId" valId="deptId" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list deptList as item>
						<a val="${item.id!}">${item.deptname!}</a>
						</#list>
					</@common.select>
				</div>
				&nbsp;<a href="javascript:void(0);" onclick="searchOrder();" class="abtn-blue">查找</a>
			</div>
    	</div>
    </div>
</div>
<div id="myOrderListDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>