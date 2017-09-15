<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "/common/ztree/ztree.ftl" as aa>
<@htmlmacro.moduleDiv titleName="">
	<div class="wrap pa-10">
		<div class="fn-clear px-10  mt-5">
			<#if stepDto.stepType?default("0")=="0">
				<@commonmacro.selectAddressBook height="200" idObjectId="selTeacherId"  deptObjectId="deptObjectId" deptLeaderObjectId="deptLeaderObjectId" groupObjectId="groupObjectId" nameObjectId="selTeacherName" detailObjectId="selTeacherDetailName" changeHeight="changeAddress" needGroupId="true">
					<input type="hidden" name="selTeacherId" id="selTeacherId" value="">
					<input type="hidden" name="deptObjectId" id="deptObjectId" value="">
					<input type="hidden" name="deptLeaderObjectId" id="deptLeaderObjectId" value="">
					<input type="hidden" name="groupObjectId" id="groupObjectId" value="">
					<input type="hidden" name="selTeacherName" id="selTeacherDetailName" value=""> 
					<input type="hidden" name="selTeacherName" id="selTeacherName" value="" >
				</@commonmacro.selectAddressBook>
			<#else>
				<@commonmacro.selectAddressBookOfficedoc idObjectId="selTeacherId" nameObjectId="selTeacherName" detailObjectId="selTeacherDetailName" selectUserIds="selectUserIds" height="200" otherParam="" callback="" preset="">
					<input type="hidden" name="selTeacherId" id="selTeacherId" value="">
					<input type="hidden" name="deptObjectId" id="deptObjectId" value="">
					<input type="hidden" name="deptLeaderObjectId" id="deptLeaderObjectId" value="">
					<input type="hidden" name="groupObjectId" id="groupObjectId" value="">
					<input type="hidden" name="selTeacherName" id="selTeacherDetailName" value=""> 
					<input type="hidden" name="selTeacherName" id="selTeacherName" value="" >
					<input type="hidden" name="selTeacherIds" id="selectUserIds" value="${stepDto.taskUserId!}">
				</@commonmacro.selectAddressBookOfficedoc>
			</#if>
		</div> 
	</div>
	<#if halfSelectUsers?exists && halfSelectUsers?size gt 0>
	    <div class="user-inner-other fn-clear" id="otherUserSelect">
            <span>其他人员：</span>
            <#list halfSelectUsers as user>
           		<span class="ui-checkbox <#if checkHalfUsers?exists && checkHalfUsers.indexOf(user.halfId)!=-1>ui-checkbox-current </#if> " dataVal="${user.halfId!}" dataName="${user.halfName!}"><input type="checkbox" class="chk" <#if checkHalfUsers?exists && checkHalfUsers.indexOf(user.halfId)!=-1>checked="checked"</#if>>${user.halfName!}</span>
            </#list>
        </div>
	</#if>
	
	<p class="dd">
		<a class="abtn-blue submit" href="javascript:void(0)" onclick="step_checkDiv()"><span>确定</span></a>
		<a class="abtn-blue reset ml-5" href="javascript:void(0)"   onclick="onDivClose()"><span>取消</span></a>
	</p>
</@htmlmacro.moduleDiv>
<script>
	

	$(function(){
		vselect();
		$('.user-sList-radio span').click(function(){
			$(this).addClass('current').siblings('span').removeClass('current');
		});
		$("#selTeacherId").val($(".${currentStepId!}").find("a[class='change']").attr("dataUserIdVal"));
		$("#selTeacherDetailName").val($(".${currentStepId!}").find("a[class='change']").attr("dataUserDetailNameVal"));
		$("#selTeacherName").val($(".${currentStepId!}").find("a[class='change']").attr("dataUserNameVal"));
		//loadAddressBookDetail('selTeacherId','deptObjectId','deptLeaderObjectId','groupObjectId','selTeacherName','selTeacherDetailName','200',false,'${request.contextPath}/component/addressbook/addressBook-userAddressBook.action','','changeAddress','','',true);
	});
	
	
	function step_checkDiv(){
		<#if stepDto.stepType?default("0")=="0">
			saveSelectedNew("selTeacherId","deptObjectId","deptLeaderObjectId","groupObjectId","selTeacherName","selTeacherDetailName");
		<#else>
			saveSelected_("selTeacherId","selTeacherName","selTeacherDetailName",'');
		</#if>
		
		remoteUser(false);
	}
	
	
	function remoteUser(isGroup){
		var selTeacherIds = $("#selTeacherId").val();
		var selTeacherNames = $("#selTeacherName").val();
		var selTeacherDetailNames = $("#selTeacherDetailName").val();
		if(selTeacherIds!=''){
			var selTeacherIdArray = selTeacherIds.split(',');
			var selTeacherNameArray = selTeacherNames.split(',');
			var selTeacherDetailNames =selTeacherDetailNames.split(',');
			if(isGroup){
				var splitChar = ",";
			}else{
				var splitChar = ",";
			}
			var selTeacherIdStr='';
			var selTeacherNameStr='';
			var selTeacherDetailNamesStr ="";
			for(var i=0;i<selTeacherIdArray.length;i++){
				if(i==0){
					selTeacherIdStr +=selTeacherIdArray[i];
					selTeacherNameStr +=selTeacherNameArray[i];
					selTeacherDetailNamesStr += selTeacherDetailNames[i];
				}else{
					selTeacherIdStr +=splitChar+selTeacherIdArray[i];
					selTeacherNameStr +=splitChar+selTeacherNameArray[i];
					selTeacherDetailNamesStr +=splitChar+ selTeacherDetailNames[i];
				}
			}
			$("#selTeacherId").val(selTeacherIdStr);
			$("#selTeacherName").val(selTeacherNameStr);
			$("#selTeacherDetailName").val(selTeacherDetailNamesStr);
		}else{
			$("#selTeacherName").val('');
			$("#selTeacherDetailName").val('');	
		}
		
		$.post("${request.contextPath}/component/addressbook/addressBook-remoteUser.action", {deptIds:$("#deptObjectId").val(),deptLeaderIds:$("#deptLeaderObjectId").val(),groupIds:$("#groupObjectId").val()}, function(data){
			var userIdStr = '';
			var userNameStr='';
			var userDetailNameStr ='';
			if(isGroup){
				var splitChar = ",";
			}else{
				var splitChar = ",";
			}
			var user1= data['dept'];
			var user2= data['deptLeader'];
			var user3= data['group'];
			if(user1&&user1.length>0){
				var tempDeptId = '';
				for(var i=0;i<user1.length;i++){
					var tempUser = user1[i];
					if(tempDeptId==''){
						tempDeptId = tempUser.deptid;
						userIdStr += tempUser.id;
						userNameStr += tempUser.realname;
						userDetailNameStr +=tempUser.markName;
					}else if(tempDeptId==tempUser.deptid){
						userIdStr += ","+tempUser.id;
						userNameStr += ","+tempUser.realname;
						userDetailNameStr +=tempUser.markName;
					}else{
						tempDeptId = tempUser.deptid;
						userIdStr += splitChar +tempUser.id;
						userNameStr += splitChar+tempUser.realname;
						userDetailNameStr +=splitChar+tempUser.markName;
					}
				}
			}
			var tempUser = '';
			if(user2&&user2.length>0){
				var tempDeptId = '';
				for(var i=0;i<user2.length;i++){
					var tempUser = user2[i];
					if(tempDeptId==''){
						if(userIdStr!=''){
							userIdStr +=splitChar;
						}
						if(userNameStr!=''){
							userNameStr +=splitChar;
						}
						if(userDetailNameStr!=''){
							userDetailNameStr +=splitChar;
						}
						tempDeptId = tempUser.deptid;
						userIdStr += tempUser.id;
						userNameStr += tempUser.realname;
						userDetailNameStr +=tempUser.markName;
					}else if(tempDeptId==tempUser.deptid){
						userIdStr += ","+tempUser.id;
						userNameStr += ","+tempUser.realname;
						userDetailNameStr +=","+tempUser.markName;
					}else{
						tempDeptId = tempUser.deptid;
						userIdStr += splitChar +tempUser.id;
						userNameStr += splitChar+tempUser.realname;
						userDetailNameStr +=splitChar+tempUser.markName;
					}
				}
			}
			var tempUser = '';
			if(user3&&user3.length>0){
				var tempGroupId = '';
				for(var i=0;i<user3.length;i++){
					var tempUser = user3[i];
					if(tempGroupId==''){
						if(userIdStr!=''){
							userIdStr +=splitChar;
						}
						if(userNameStr!=''){
							userNameStr +=splitChar;
						}
						if(userDetailNameStr!=''){
							userDetailNameStr +=splitChar;
						}
						tempGroupId = tempUser.groupid;
						userIdStr += tempUser.id;
						userNameStr += tempUser.realname;
						userDetailNameStr +=tempUser.markName;
					}else if(tempGroupId==tempUser.groupid){
						userIdStr += ","+tempUser.id;
						userNameStr += ","+tempUser.realname;
						userDetailNameStr +=","+tempUser.markName;
					}else{
						tempGroupId = tempUser.groupid;
						userIdStr += splitChar +tempUser.id;
						userNameStr += splitChar+tempUser.realname;
						userDetailNameStr +=splitChar+tempUser.markName;
					}
				}
			}
			if(userIdStr!=''){
				if($("#selTeacherId").val()!=''){
					$("#selTeacherId").val($("#selTeacherId").val()+splitChar+userIdStr);
				}else{
					$("#selTeacherId").val(userIdStr);
				}
				
			}
			if(userNameStr!=''){
				if($("#selTeacherName").val()!=''){
					$("#selTeacherName").val($("#selTeacherName").val()+splitChar+userNameStr);	
				}else{
					$("#selTeacherName").val(userNameStr);
				}
			}
			if(userDetailNameStr!=''){
				if($("#selTeacherDetailName").val()!=''){
					$("#selTeacherDetailName").val($("#selTeacherDetailName").val()+splitChar+userDetailNameStr);	
				}else{
					$("#selTeacherDetailName").val(userDetailNameStr);
				}
			}
			
			if($("#selTeacherId").val()!=""){
				var resultId = $("#selTeacherId").val();
				var resultName = $("#selTeacherName").val();
			}else{
				var resultId = "";
				var resultName = "";
			}
			
			var resultCheckHalfUsers ="";
			//处理TODO
			$("#otherUserSelect .ui-checkbox-current").each(function(){
				if($("#selTeacherId").val()==""){
					
					
					if(resultCheckHalfUsers==""){
						resultCheckHalfUsers +=$(this).attr("dataVal");
						resultId += $(this).attr("dataVal");
						resultName += $(this).attr("dataName");
					}else{
						resultCheckHalfUsers +=splitChar+$(this).attr("dataVal");
						resultId +=splitChar+ $(this).attr("dataVal");
						resultName += splitChar+ $(this).attr("dataName");
					}
				}else{
					resultId += splitChar+$(this).attr("dataVal");
					resultName +=splitChar+$(this).attr("dataName");
					if(resultCheckHalfUsers==""){
						resultCheckHalfUsers +=$(this).attr("dataVal");
					}else{
						resultCheckHalfUsers +=splitChar+$(this).attr("dataVal");
					}
					
				}
			});
			
			if(resultId==""){
				showMsgError('下一步骤未选中负责人');
				return;
			}
			$(".${currentStepId!}").find("a[class='change']").attr("dataUserIdVal",$("#selTeacherId").val());
			$(".${currentStepId!}").find("a[class='change']").attr("dataUserNameVal",$("#selTeacherName").val());
			$(".${currentStepId!}").find("a[class='change']").attr("dataUserDetailNameVal",$("#selTeacherDetailName").val());
			$(".${currentStepId!}").find("a[class='change']").attr("changeNameStr",resultName);
			$(".${currentStepId!}").find(".changeNameStr").html(resultName);
			$(".${currentStepId!}").find("a[class='change']").attr("dataCheckHalfUsers",resultCheckHalfUsers);
			
			
			var nextStepChangeUserId = '';
	    	var nextStepChangeTaskId = '';
	    	var nextStepChangeStepName = '';
	    	var nextStepChangeStepType = '';
			var markIndex = 0;
			$("a[class='change']").each(function(){
		  		if(markIndex==0){
	  				if($(this).attr("dataCheckHalfUsers")==""){
	  					nextStepChangeUserId +=$(this).attr("dataUserIdVal");
	  				}else{
	  					if($(this).attr("dataUserIdVal")==""){
			  				nextStepChangeUserId +=$(this).attr("dataCheckHalfUsers");
			  			}else{
			  				nextStepChangeUserId +=$(this).attr("dataUserIdVal")+","+$(this).attr("dataCheckHalfUsers");
			  			}
	  				}
		  			
		  			nextStepChangeTaskId +=$(this).attr("dataStep");
		  			nextStepChangeStepName +=$(this).attr("dataStepName");
		  			nextStepChangeStepType +=$(this).attr("dataTaskType");
		  		}else{
		  			if($(this).attr("dataCheckHalfUsers")==""){
	  					nextStepChangeUserId +="#"+$(this).attr("dataUserIdVal");
	  				}else{
	  					if($(this).attr("dataUserIdVal")==""){
			  				nextStepChangeUserId +="#"+$(this).attr("dataCheckHalfUsers");
			  			}else{
			  				nextStepChangeUserId +="#"+$(this).attr("dataUserIdVal")+","+$(this).attr("dataCheckHalfUsers");
			  			}
	  				}
		  			nextStepChangeTaskId +="#"+$(this).attr("dataStep");
		  			nextStepChangeStepName +="#"+$(this).attr("dataStepName");
		  			nextStepChangeStepType +="#"+$(this).attr("dataTaskType");
		  		}
		  		markIndex++;
		  	});
		  	
		  	$("#nextStepChangeUserId").val(nextStepChangeUserId);
		  	$("#nextStepChangeTaskId").val(nextStepChangeTaskId);
		  	$("#nextStepChangeStepName").val(nextStepChangeStepName);
		  	$("#nextStepChangeStepType").val(nextStepChangeStepType);
			
			onDivClose();
		});
	}
	
	function changeAddress(){
		$("#addressBookDetail_div").attr("style","height:200px");
	}
	
</script>
