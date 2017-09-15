<#import "/common/htmlcomponent.ftl" as common />
<#import "/common/commonmacro.ftl" as commonmacro>
<@common.moduleDiv titleName="">
<script>
$(function(){
	<#if officeBulletinSet.needAudit?default('')=='0'>
	$("#userDisplay").hide();
	<#else>
	$("#userDisplay").show();
	</#if>
})
function boardRoomManage(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAdmin.action");
}
function boardRoomOrderAudit(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomAuditAdmin.action");
}
function boardRoomOrderManage(){
	load("#boardRoomDiv", "${request.contextPath}/office/boardroommanage/boardroommanage-boardRoomOrderManageAdmin.action");
}
function toAuditSet(){
	load("#container","${request.contextPath}/office/boardroommanage/boardroommanage-auditSet.action")
}

var isSubmit =false;
function doSave(){
	if(isSubmit){
		return;
	}
	var options = {
	   url:'${request.contextPath}/office/boardroommanage/boardroommanage-auditSetSave.action', 
	   success : showReply,
	   dataType : 'json',
	   clearForm : false,
	   resetForm : false,
	   type : 'post'
	};
	isSubmit = true;
	$('#timeSetForm').ajaxSubmit(options);
}


function showReply(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				  	load("#container","${request.contextPath}/office/boardroommanage/boardroommanage-auditSet.action");
			}
		}
		
function toAudit1(){
	$("#userDisplay").hide();
	$("#needAudit").val(0);
	$("#auditId").val("");
}

function toAudit2(){
	$("#userDisplay").show();
	$("#needAudit").val(1);
	$("#auditId").val('${officeBulletinSet.auditId!}');
}
</script>
<div class="popUp-layer" id="bulletinLayer" style="display:none;width:700px;580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
		<li onclick="boardRoomOrderManage();">预约记录查询</li>
		<#if shheAdmin>
		<li onclick="boardRoomOrderAudit();">预约审核</li>
		</#if>
		<#if megAdmin>
		<li onclick="boardRoomManage();">会议室管理</li>
		<li class="current" onclick="toAuditSet();">审核权限设置</li>
		</#if>
	</ul>
</div>
<div id="boardRoomDiv">
<form method="post" name="timeSetForm" id="timeSetForm">
<input type="hidden" id="id" name="officeBulletinSet.id" value="${officeBulletinSet.id?default('')}"/>
<input type="hidden" id="id" name="bulletinType" value="${bulletinType?default('')}"/>
<@common.tableList addClass="mt-15">
  	<tr>
  		<th style="width:20%;text-align:right;"><span class="c-red">*</span>审核是否开启:</th>
                <td class="tit" width="20">
                <span onclick="toAudit1();"  class="ui-radio ui-radio-noTxt <#if officeBulletinSet.needAudit?default('')=='0'>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeBulletinSet.needAudit?default('')=='0'>checked="checked"</#if> name="officeBulletinSet.needAudit" value="0">&nbsp不开启</span>&nbsp&nbsp
                <span onclick="toAudit2();" class="ui-radio ui-radio-noTxt <#if officeBulletinSet.needAudit?default('')=='1'>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeBulletinSet.needAudit?default('')=='1'>checked="checked"</#if> name="officeBulletinSet.needAudit" value="1">&nbsp开启</span></td>
    </tr>
  	<tr id="userDisplay">
  	 	<th style="width:20%;text-align:right;"><span class="c-red">*</span>人员权限设置:</th>
  	  	<td colspan="3" >
		    <@commonmacro.selectMoreUser idObjectId="auditId" nameObjectId="auditNames" width=400 height=300>
				<input type="hidden" id="auditId" name="officeBulletinSet.auditId" value="${officeBulletinSet.auditId!}"/>
				<textarea id="auditNames" name="officeBulletinSet.auditNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="人员设置" maxLength="100">${officeBulletinSet.auditNames!}</textarea>
		    </@commonmacro.selectMoreUser>
		</td>
  	</tr>
</@common.tableList>
</form>
<div class="t-center mt-10">
		<a href="javascript:void(0);" id="btnSaveAll" class="abtn-blue-big" onclick="javascript:doSave();">保存</a>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@common.moduleDiv>