<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="资产类别维护">
<script>
function doSave(){
	if(!isActionable("#btnSave")){
		return;
	}
	
	if(!checkAllValidate("#contentDiv")){
		return;
	}
	
	$("#btnSave").attr("class", "abtn-unable");
	
	var options = {
       url:'${request.contextPath}/office/asset/assetAdmin-saveCategory.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
	
    $('#mainform').ajaxSubmit(options);
}

function showReply(data){
	$("#btnSave").attr("class", "abtn-blue");
	if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   return;
			   }else{
			   	   showMsgError(data.promptMessage);
				   return;
			   }
	}else{
		   		showMsgSuccess("保存成功！","",function(){
				   load("#adminDiv", "${request.contextPath}/office/asset/assetAdmin-category.action");
				});
				return;
	}
}
</script>
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>资产类别维护</span></p>
<div class="wrap pa-10" id="contentDiv">
<form id="mainform" action="" method="post">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
    	<input type="hidden" id="id" name="id" value="${category.id?default('')}">  
	    <input type="hidden" id="unitId" name="unitId" value="${category.unitId?default('')}">  
	    <tr>
	      <th width="30%"><span class="c-orange mr-10">*</span>类别：</th>
	      <td width="70%">
	        <input name="assetName" id="assetName" value="${category.assetName?default('')}" 
	            type="text" class="input-txt" style="width:150px;" msgName="类别"  notNull="true" maxlength="30">
	      </td>
	    </tr>
	    <tr>
	      <th><span class="c-orange mr-10"></span>处室负责人：</th>
	      <td>
	        <@commonmacro.selectMoreUser idObjectId="deptLeaderId" nameObjectId="deptLeaderName" width=600>
			<input id="deptLeaderName" value="${deptLeaderName?default('')}"  class="select_current02"  style="width:150px;" readonly="readonly" msgName="处室负责人">
            <input id="deptLeaderId" name="deptLeaderId"   value="${category.deptLeaderId?default('')}" type="hidden" >
            </@commonmacro.selectMoreUser>
	      </td>
	    </tr>
	    <tr>
	      <th><span class="c-orange mr-10"></span>分管领导人：</th>
	      <td>
	        <@commonmacro.selectOneUser idObjectId="leaderId" nameObjectId="leaderName" width=600>
			<input id="leaderName" value="${leaderName?default('')}"  class="select_current02"  style="width:150px;" readonly="readonly" msgName="分管领导人">
            <input id="leaderId" name="leaderId"   value="${category.leaderId?default('')}" type="hidden" >
            </@commonmacro.selectOneUser>
	      </td>
	    </tr>
	    
	    <#if auditModel>
	    	<tr>
        		<span style="color:#fa7305;">
				提示：该模式走金额审核模式，处室负责人负责金额<=200，200<分管校领导负责金额<=2000,2000<校长审核金额<10000,会议讨论负责人负责金额>=10000
				</span>
	    	</tr>
	    </#if>
	    
	    <#if !auditModel>
	    <tr>
	    	<td style="width:100%" colSpan="2">
		    	<#if category.is_DeptLeader?default(false)!=false><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current">
		    	<input type="checkbox" id="is_DeptLeader" name="is_DeptLeader" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10">
        			<input type="checkbox" id="is_DeptLeader" name="is_DeptLeader" value="true" class="chk"/>
		       	</#if>
        			处室不审核
        		</span>
        		
		    	<#if category.is_Leader?default(false)!=false><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current">
		    	<input type="checkbox" id="is_Leader" name="is_Leader" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10">
        			<input type="checkbox" id="is_Leader" name="is_Leader" value="true" class="chk"/>
		       	</#if>
        			分管领导不审核
        		</span>
        		
		    	<#if category.is_master?default(false)!=false><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current">
		    	<input type="checkbox" id="is_master" name="is_master" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10">
        			<input type="checkbox" id="is_master" name="is_master" value="true" class="chk"/>
		       	</#if>
        			校长不审核
        		</span>
        		
		    	<#if category.is_meeting?default(false)!=false><span class="ui-checkbox fn-left mt-5 ml-10 ui-checkbox-current">
		    	<input type="checkbox" id="is_meeting" name="is_meeting" value="true" class="chk" checked="checked"/>
		    	<#else>
		       	<span class="ui-checkbox fn-left mt-5 ml-10" style="margin-top:10px;">
        			<input type="checkbox" id="is_meeting" name="is_meeting" value="true" class="chk"/>
		       	</#if>
        			会议不审核
        		</span>
        	</td>
	    </tr>
		</#if>
		    
    </table>
</form>    
</div>
<p class="dd">
    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave();" id="btnSave">保存</a>
    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
</p>
<script>vselect();</script>
</@htmlmacro.moduleDiv>