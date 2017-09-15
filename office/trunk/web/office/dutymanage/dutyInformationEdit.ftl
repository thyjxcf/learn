<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../jtgooutmanage/archiveWebuploader.ftl" as archiveWebuploader>
<@htmlmacro.moduleDiv titleName="出差申请">
	<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span style="text-align:center;">值班信息设置</span></p>
	<div class="wrap pa-10">
	<form name="officeDutyInformationSetForm" id="officeDutyInformationSetForm" method="post">
		<input id="id" name="officeDutyInformationSet.id" value="${officeDutyInformationSet.id!}" type="hidden" />
		<input id="unitId" name="officeDutyInformationSet.unitId" value="${officeDutyInformationSet.unitId!}" type="hidden" />
		<input id="createUserId" name="officeDutyInformationSet.createUserId" value="${officeDutyInformationSet.createUserId!}" type="hidden" />
		<input id="createTime" name="officeDutyInformationSet.createTime" value="${officeDutyInformationSet.createTime!}" type="hidden" />
		<input id="year" name="officeDutyInformationSet.year" value="${officeDutyInformationSet.year!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>值班名称：</th>
		       <td style="width:40%"  colspan="3">
		        	<input name="officeDutyInformationSet.dutyName" id="dutyName" type="text" class="input-txt" style="width:35%;" maxlength="50"  value="${officeDutyInformationSet.dutyName!}" msgName="值班名称 " notNull="true" />
		       </td>
		    </tr>
		    <tr>
		        <th><span class="c-orange mr-5">*</span>值班日期：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.dutyStartTime" id="dutyStartTime" class="input-txt" style="width:25%;" msgName="值班开始日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyInformationSet.dutyStartTime)?string('yyyy-MM-dd'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.dutyEndTime" id="dutyEndTime" class="input-txt" style="width:25%;" msgName="值班结束日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyInformationSet.dutyEndTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		        <th  ><span class="c-orange mr-5">*</span>报名日期：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.registrationStartTime" id="registrationStartTime" class="input-txt" style="width:25%;" msgName="报名开始日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyInformationSet.registrationStartTime)?string('yyyy-MM-dd'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeDutyInformationSet.registrationEndTime" id="registrationEndTime" class="input-txt" style="width:25%;" msgName="报名结束日期" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd" value="${((officeDutyInformationSet.registrationEndTime)?string('yyyy-MM-dd'))?if_exists}" />
		        </td>
		    </tr>
		    
		    <tr>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>类型：</th>
		        <td style="width:40%" colspan="3">
                <span class="ui-radio ui-radio-noTxt <#if officeDutyInformationSet.type?default('0')=='0'>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if officeDutyInformationSet.type?default('0')=='0'>checked="checked"</#if> name="officeDutyInformationSet.type" value="0">&nbsp上下午</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if officeDutyInformationSet.type?default('0')=='1'>ui-radio-current</#if>" data-name="a" style="margin-left:13px;">
                <input type="radio" class="radio" <#if officeDutyInformationSet.type?default('0')=='1'>checked="checked"</#if> name="officeDutyInformationSet.type" value="1">&nbsp一天</span></td>
		    </tr>
		    <tr>
		        <th>说明：</th>
		        <td colspan="3">
		        	<textarea name="officeDutyInformationSet.instruction" id="instruction" cols="70" rows="4" class="text-area my-5" style="width:97%;padding:5px 1%;height:75px;" maxLength="500">${officeDutyInformationSet.instruction!}</textarea>
		        </td>
		    </tr>
		    <tr>
		       <th><span class="c-orange mr-5">*</span>人员设置：</th>
		        <td colspan="3">
		        	<@commonmacro.selectAddressBookAllLayer userObjectId="userIds" deptObjectId="" unitObjectId="" detailObjectId="userNames" sendToOtherUnit="false" otherParam="" callback="" preset="" height="430">
			        	<input type="hidden" id="userIds" name="officeDutyInformationSet.userIds" value="${officeDutyInformationSet.userIds!}"/>
			        	<textarea id="userNames" name="officeDutyInformationSet.userNames" cols="70" rows="4" class="text-area my-5" style="width:97%;padding:5px 1%;height:100px;line-height:20px;" msgName="人员设置">${officeDutyInformationSet.userNames!}</textarea>
            		</@commonmacro.selectAddressBookAllLayer>
		        <span id="noneUser" class="field_tip input-txt-warn-tip"></span>
		        </td>
		    </tr>
		    <tr>
		    	<td colspan="4" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave()">保存</a>
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
	</form>
	</div>
	<script>
		function back(){
			jtGoOut();
		}
		
		function checkNoneUser(){
			$("#noneUser").text("");
			var userIds=$("#userIds").val();
			if(userIds==null||userIds==''){
				$("#noneUser").text("人员设置 不能为空！");
				return false;
			}
			$("#noneUser").text("");
			return true;
		}
		
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#officeDutyInformationSetForm")){
				if(!checkNoneUser()){
					return;
				}
				return;
			}
			if(!checkNoneUser()){
					return;
			}
			if(!checkAfterDate($("#dutyStartTime").get(0),$("#dutyEndTime").get(0))){
				return;
			}
			if(!checkAfterDate($("#registrationStartTime").get(0),$("#registrationEndTime").get(0))){
				return;
			}
			
			if(!(compareDate($("#registrationEndTime").get(0),$("#dutyStartTime").get(0))<0)){
				showMsgError("报名结束日期不能大于等于值班开始日期！");
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/dutymanage/dutymanage-dutyInformationSave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#officeDutyInformationSetForm').ajaxSubmit(options);
		}
		
		function showReply(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
				  	dutySet();
				});
				return;
			}
		}
		
		
	</script>
</@htmlmacro.moduleDiv >