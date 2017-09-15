<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<#import "../jtgooutmanage/archiveWebuploader.ftl" as archiveWebuploader>
<script type="text/javascript" src="${request.contextPath}/static/jbmp/editor/js/flow.js"></script>
<script>
function test(obj){
	//var type=$("#type").val();
	if(obj=='1'){
		$.each($("tr[class='teacherhid']"), function(i){
			 this.style.display = 'none';  
		});
		$.each($("tr[class='studenthid']"), function(i){
			 this.style.display = '';  
		});
		
		var isOrganization = $("input[name='gooutStudentEx.isOrganization']:checked").val();
		//var isOrganization=$("#isOrganization").val();
		if(isOrganization=='false'){
			$.each($(".lxsunit"), function(i){
			      this.style.display = 'none';  
			  }); 
			$.each($(".lxsunit"), function(i){
			      $(this).find("input.input-txt").removeAttr("notNull");   
			  }); 
		}else{
			$.each($(".lxsunit"), function(i){
			      this.style.display = '';  
			  }); 
			$.each($(".lxsunit"), function(i){
			     $(this).find("input.input-txt").attr("notNull","true");   
			  }); 
		}
		
		
		$.each($("tr[class='studenthid']"), function(i){
			 $(this).find("input.input-txt").attr("notNull","true");  
		});
		$("#content").attr("notNull","true");
		$("#contents").removeAttr("notNull");
	}else{
		$.each($("tr[class='teacherhid']"), function(i){
			 this.style.display = '';  
		});
		$.each($(".studenthid"), function(i){
			 this.style.display = 'none';  
		});
		$.each($("tr[class='studenthid']"), function(i){
			 $(this).find("input.input-txt").removeAttr("notNull");  
		});
		
		$.each($(".lxsunit"), function(i){
			      $(this).find("input.input-txt").removeAttr("notNull");   
			  });
		
		$.each($("tr[class='teacherhid']"), function(i){
			 $(this).find("input.input-txt").attr("notNull","true");  
		});
		$("#content").removeAttr("notNull");
		$("#contents").attr("notNull","true");
	}
}
function isdis(obj){
	if(obj=='0'){
		$.each($(".lxsunit"), function(i){
			      this.style.display = 'none';  
			  }); 
		$.each($(".lxsunit"), function(i){
			      $(this).find("input.input-txt").removeAttr("notNull");   
			  }); 
	}else{
		$.each($(".lxsunit"), function(i){
			      this.style.display = '';  
			  }); 
		$.each($(".lxsunit"), function(i){
			     $(this).find("input.input-txt").attr("notNull","true");   
			  }); 
	}
}

</script>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/boxy/boxy.css"/>
<script type="text/javascript" src="${request.contextPath}/static/boxy/jquery.boxy.js"></script>
<@htmlmacro.moduleDiv titleName="出差申请">
	<form name="jtGoOutForm" id="jtGoOutForm" method="post">
		<input id="id" name="officeJtGoout.id" value="${officeJtGoout.id!}" type="hidden" />
		<input id="unitId" name="officeJtGoout.unitId" value="${officeJtGoout.unitId!}" type="hidden" />
		<input id="applyUserId" name="officeJtGoout.applyUserId" value="${officeJtGoout.applyUserId!}" type="hidden" />
		<input id="createTime" name="officeJtGoout.createTime" value="${officeJtGoout.createTime!}" type="hidden" />
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
		        <th colspan="4" style="text-align:center;">集体外出申请</th>
		    </tr>
		    <tr>
		        <th  ><span class="c-orange mr-5">*</span>外出时间：</th>
		        <td colspan="3" >
		        	<@htmlmacro.datepicker name="officeJtGoout.startTime" id="startTime" class="input-txt" style="width:17%;" msgName="开始时间" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeJtGoout.startTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        	至
		        	<@htmlmacro.datepicker name="officeJtGoout.endTime" id="endTime" class="input-txt" style="width:17%;" msgName="结束时间" notNull="true" size="20" maxlength="19" dateFmt="yyyy-MM-dd HH:mm:00" value="${((officeJtGoout.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}" />
		        </td>
		    </tr>
		    <tr>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>外出类型：</th>
		       <td colspan="3">
		        	<!--<@htmlmacro.select style="width:120px;" valName="officeJtGoout.type" valId="type"  msgName="外出类型" myfunchange="test">
					<a val="1" <#if officeJtGoout.type?default('')=='1'>class="selected"</#if> ><span>学生集体活动</span></a>
					<a val="2" <#if officeJtGoout.type?default('')=='2'>class="selected"</#if> ><span>教师培训</span></a>
					</@htmlmacro.select>-->
					<span class="ui-radio ui-radio-noTxt <#if officeJtGoout.type?default('')=='1'>ui-radio-current</#if>" data-name="u" onclick="test('1');">
                <input type="radio" class="radio" <#if officeJtGoout.type?default('')=='1'>checked="checked"</#if> name="officeJtGoout.type" value="1">&nbsp学生集体活动</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if officeJtGoout.type?default('')=='2'>ui-radio-current</#if>" data-name="u" style="margin-left:13px;" onclick="test('2');">
                <input type="radio" class="radio" <#if officeJtGoout.type?default('')=='2'>checked="checked"</#if> name="officeJtGoout.type" value="2">&nbsp教师集体培训</span></td>
		       </td>
		    </tr>
		    
		    <#--学生类型-->
		    <tr class="studenthid">
		        <th style="width:20%"><span class="c-orange mr-5">*</span>组织活动的年级或班级：</th>
		        <td style="width:30%">
		        	<input name="gooutStudentEx.organize" id="organize" type="text" class="input-txt" style="width:81%;" maxlength="20"  value="${gooutStudentEx.organize!}" msgName="活动年级或班级" notNull="true" />
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>活动人数：</th>
		        <td style="width:30%">
		        	<input name="gooutStudentEx.activityNumber" id="activityNumber" type="text" class="input-txt" style="width:81%;" maxlength="4"  value="${gooutStudentEx.activityNumber!}" msgName="活动人数" notNull="true" regex="/^[0-9]*[1-9][0-9]*$/" regexMsg="只能输入正整数"/>
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:20%"><span class="c-orange mr-5">*</span>活动地点：</th>
		       <td style="width:30%"  colspan="3">
		        	<input name="gooutStudentEx.place" id="place" type="text" class="input-txt" style="width:35%;" maxlength="50"  value="${gooutStudentEx.place!}" msgName="活动地点 " notNull="true" />
		       </td>
		    </tr>
		    <tr class="studenthid">
		        <th><span class="c-orange mr-5">*</span>活动内容：</th>
		        <td colspan="3">
		        	<textarea name="gooutStudentEx.content" id="content" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="活动内容" notNull="true" maxLength="1000">${gooutStudentEx.content!}</textarea>
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:20%"><span class="c-orange mr-5">*</span>交通工具：</th>
		       <td style="width:30%">
		        	<input name="gooutStudentEx.vehicle" id="vehicle" type="text" class="input-txt" style="width:81%;" maxlength="50"  value="${gooutStudentEx.vehicle!}" msgName="交通工具" notNull="true" />
		       </td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否有营运证：</th>
		        <td style="width:40%">
                <span class="ui-radio ui-radio-noTxt <#if !gooutStudentEx.isDrivinglicence?default(false)>ui-radio-current</#if>" data-name="a">
                <input type="radio" class="radio" <#if !gooutStudentEx.isDrivinglicence?default(false)>checked="checked"</#if> name="gooutStudentEx.isDrivinglicence" value="flase">&nbsp无营运证</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if gooutStudentEx.isDrivinglicence?default(false)>ui-radio-current</#if>" data-name="a" style="margin-left:13px;">
                <input type="radio" class="radio" <#if gooutStudentEx.isDrivinglicence?default(false)>checked="checked"</#if> name="gooutStudentEx.isDrivinglicence" value="true">&nbsp有营运证</span></td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否由旅行社组织：</th>
		        <td style="width:40%">
                <span class="ui-radio ui-radio-noTxt <#if !gooutStudentEx.isOrganization?default(false)>ui-radio-current</#if>" data-name="b"  onclick="isdis('0');">
                <input type="radio" class="radio" <#if !gooutStudentEx.isOrganization?default(false)>checked="checked"</#if> name="gooutStudentEx.isOrganization" id="isOrganization" value="false">&nbsp否</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if gooutStudentEx.isOrganization?default(false)>ui-radio-current</#if>" data-name="b" style="margin-left:13px;" onclick="isdis('1')">
                <input type="radio" class="radio" <#if gooutStudentEx.isOrganization?default(false)>checked="checked"</#if> name="gooutStudentEx.isOrganization" id="isOrganization" value="true">&nbsp是</span></td>
		       <th style="width:20%" class="lxsunit"><span class="c-orange mr-5">*</span>旅行社单位：</th>
		       <td style="width:30%" colspan="3" class="lxsunit">
		        	<input name="gooutStudentEx.traveUnit" id="traveUnit" type="text" class="input-txt" style="width:81%;" maxlength="40"  value="${gooutStudentEx.traveUnit!}" msgName="旅行社单位"/>
		       </td>
		    </tr>
		    <tr class="studenthid lxsunit">
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人：</th>
		       <td style="width:30%">
		        	<input name="gooutStudentEx.traveLinkPerson" id="traveLinkPerson" type="text" class="input-txt" style="width:81%;" maxlength="20"  value="${gooutStudentEx.traveLinkPerson!}" msgName="旅行社联系人"/>
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人手机号：</th>
		       <td style="width:30%">
		        	<input name="gooutStudentEx.traveLinkPhone" id="traveLinkPhone" type="text" class="input-txt" style="width:81%;" maxlength="13"  value="${gooutStudentEx.traveLinkPhone!}" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的手机号,并且不能超过20个数字" msgName="手机号"/>
		       </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否购买人身保险和意外伤害保险：</th>
		        <td style="width:40%" colspan="3">
                <span class="ui-radio ui-radio-noTxt <#if !gooutStudentEx.isInsurance?default(false)>ui-radio-current</#if>" data-name="c">
                <input type="radio" class="radio" <#if !gooutStudentEx.isInsurance?default(false)>checked="checked"</#if> name="gooutStudentEx.isInsurance" value="false">&nbsp未入</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if gooutStudentEx.isInsurance?default(false)>ui-radio-current</#if>" data-name="c" style="margin-left:13px;">
                <input type="radio" class="radio" <#if gooutStudentEx.isInsurance?default(false)>checked="checked"</#if> name="gooutStudentEx.isInsurance" value="true">&nbsp已入</span></td>
                
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人：</th>
		        <td style="width:30%">
		        	<@commonmacro.selectOneUser idObjectId="activityLeaderId" nameObjectId="activityLeaderName" width=400 height=300>
						<input type="hidden" id="activityLeaderId" name="gooutStudentEx.activityLeaderId" value="${gooutStudentEx.activityLeaderId!}"/> 
						<input type="text" id="activityLeaderName" name="gooutStudentEx.activityLeaderName" notNull="true" msgName="活动负责人" value="${gooutStudentEx.activityLeaderName!}" class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
			  		</@commonmacro.selectOneUser>
		        </td>
                <th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人手机号：</th>
		        <td style="width:30%">
		        	<input name="gooutStudentEx.activityLeaderPhone" id="activityLeaderPhone" type="text" class="input-txt" style="width:50%;" maxlength="13"  value="${gooutStudentEx.activityLeaderPhone!}" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的手机号,并且不能超过20个数字" msgName="手机号"/>
		        </td>
                
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>带队老师：</th>
		        <td style="width:30%">
		        	<@commonmacro.selectOneUser idObjectId="leadGroupId" nameObjectId="leadGroupName" width=400 height=300>
						<input type="hidden" id="leadGroupId" name="gooutStudentEx.leadGroupId" value="${gooutStudentEx.leadGroupId!}"/> 
						<input type="text" id="leadGroupName" name="gooutStudentEx.leadGroupName" notNull="true" msgName="带队老师" value="${gooutStudentEx.leadGroupName!}" class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
			  		</@commonmacro.selectOneUser>
		        </td>
                <th style="width:20%"><span class="c-orange mr-5">*</span>带队老师手机号：</th>
		        <td style="width:30%">
		        	<input name="gooutStudentEx.leadGroupPhone" id="leadGroupPhone" type="text" class="input-txt" style="width:50%;" maxlength="13"  value="${gooutStudentEx.leadGroupPhone!}" regex="/^[0-9]{1,20}$/" regexMsg="请输入正确的手机号,并且不能超过20个数字" msgName="手机号"/>
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th>其他老师：</th>
		        <td colspan="3">
		        	<@commonmacro.selectAddressBookAllLayer userObjectId="otherTeacherId" deptObjectId="" unitObjectId="" detailObjectId="otherTeacherNames" sendToOtherUnit="false" otherParam="" callback="" preset="" height="430">
			        	<input type="hidden" id="otherTeacherId" name="gooutStudentEx.otherTeacherId" value="${gooutStudentEx.otherTeacherId!}"/>
			        	<textarea id="otherTeacherNames" name="gooutStudentEx.otherTeacherNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="其他老师" maxLength="3000">${gooutStudentEx.otherTeacherNames!}</textarea>
            		</@commonmacro.selectAddressBookAllLayer>
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>活动方案：</th>
		        <td style="width:40%">
                <span class="ui-radio ui-radio-noTxt <#if !gooutStudentEx.activityIdeal?default(false)>ui-radio-current</#if>" data-name="d">
                <input type="radio" class="radio" <#if !gooutStudentEx.activityIdeal?default(false)>checked="checked"</#if> name="gooutStudentEx.activityIdeal" value="false">&nbsp无</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if gooutStudentEx.activityIdeal?default(false)>ui-radio-current</#if>" data-name="d" style="margin-left:13px;">
                <input type="radio" class="radio" <#if gooutStudentEx.activityIdeal?default(false)>checked="checked"</#if> name="gooutStudentEx.activityIdeal" value="true">&nbsp有</span></td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>安全方案：</th>
		        <td style="width:40%">
                <span class="ui-radio ui-radio-noTxt <#if !gooutStudentEx.saftIdeal?default(false)>ui-radio-current</#if>" data-name="e">
                <input type="radio" class="radio" <#if !gooutStudentEx.saftIdeal?default(false)>checked="checked"</#if> name="gooutStudentEx.saftIdeal" value="false">&nbsp无</span>&nbsp;&nbsp;
                <span class="ui-radio ui-radio-noTxt <#if gooutStudentEx.saftIdeal?default(false)>ui-radio-current</#if>" data-name="e" style="margin-left:13px;">
                <input type="radio" class="radio" <#if gooutStudentEx.saftIdeal?default(false)>checked="checked"</#if> name="gooutStudentEx.saftIdeal" value="true">&nbsp有</span></td>
		    </tr>

		    
		    <#--教师内容-->
		    <tr class="teacherhid">
		        <th><span class="c-orange mr-5">*</span>外出内容：</th>
		        <td colspan="3">
		        	<textarea name="gooutTeacherEx.content" id="contents" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="外出内容" maxLength="1000">${gooutTeacherEx.content!}</textarea>
		        </td>
		    </tr>
		    <tr class="teacherhid">
		       <th>参与人员：</th>
		        <td colspan="3">
		        	<!--<@commonmacro.selectMoreUser idObjectId="partakePersonId" nameObjectId="partakePersonNames" width=400 height=300>
			        	<input type="hidden" id="partakePersonId" name="gooutTeacherEx.partakePersonId" value="${gooutTeacherEx.partakePersonId!}"/>
			        	<textarea id="partakePersonNames" name="gooutTeacherEx.partakePersonNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="其他老师" maxLength="800">${gooutTeacherEx.partakePersonNames!}</textarea>
		        	</@commonmacro.selectMoreUser>-->
		        	<@commonmacro.selectAddressBookAllLayer userObjectId="partakePersonId" deptObjectId="" unitObjectId="" detailObjectId="partakePersonNames" sendToOtherUnit="false" otherParam="" callback="" preset="" height="430">
			        	<input type="hidden" id="partakePersonId" name="gooutTeacherEx.partakePersonId" value="${gooutTeacherEx.partakePersonId!}"/>
			        	<textarea id="partakePersonNames" name="gooutTeacherEx.partakePersonNames" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="参与人员" maxLength="3000">${gooutTeacherEx.partakePersonNames!}</textarea>
            		</@commonmacro.selectAddressBookAllLayer>
		        </td>
		    </tr>
		    
		    
		    <tr>
	    	   <th colspan="1" >
	    	   <span class="c-orange mr-5">*</span>
	    	   		外出流程选择：
	    	   </th>
	    	   <td colspan="3">
				<div class="query-part fn-rel fn-clear promt-div  flowDiv">
		        	<@htmlmacro.select style="width:260px;" valName="officeJtGoout.flowId" valId="flowId" myfunchange="flowChange" msgName="流程选择"notNull='true'>
						<a val="" ><span>--请选择--</span></a>
						<#if flowList?exists && flowList?size gt 0>
							<#list flowList as flow>
								<a val="${flow.flowId!}" <#if officeJtGoout.flowId?exists && officeJtGoout.flowId==flow.flowId>class="selected"</#if> dataEasyLevel="${flow.easyLevel?default('0')}" flowUnitId="${flow.ownerId!}" flowOwnerType="${flow.ownerType!}"><span>${flow.flowName!}</span></a>
							</#list>
						</#if>
					</@htmlmacro.select>
		        </div>
	    	   </td>
		    </tr>   
		    <@archiveWebuploader.archiveWebuploaderEditViewer canEdit=true showAttachmentDivId='showAttDiv' editContentDivId='editContentDiv' isSend=true loadDiv=false />
		    <p class="pt-15 t-center">
	        	<a id="changeFlowDiv" class="abtn-blue-big" href="javascript:void(0);" onclick="doChangeFlow() ">修改流程</a>
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSave() ">保存</a>
		    	    <a class="abtn-blue-big" href="javascript:void(0);" onclick="doSubmit()">提交审核</a>
				    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="back();">返回</a>
        	</p>
		</@htmlmacro.tableDetail>
	</form>
	<div id="flowShow"  class="docReader my-20" style="height:660px;">
		<p >请选择流程</p>
	</div>
	<input id="wf-actionUrl" value="" type="hidden" />
	<input id="wf-taskHandlerSaveJson" value="" type="hidden" />
	<script>
		//流程选择
		function flowChange(){
			var flowId= $("#flowId").val();
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			
			var gooutUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(gooutUnitId == flowUnitId){
					$("#changeFlowDiv").show();
				}else{
					$("#changeFlowDiv").hide();
				}
			}
			else{
				$("#changeFlowDiv").show();
			}
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId+"&easyLevel="+easyLevel);
		}
		
		$(document).ready(function(){
			vselect();
			var flowId= $("#flowId").val();
			
			<#if officeJtGoout.type?default('')=='1'>
			$.each($("tr[class='teacherhid']"), function(i){
			      this.style.display = 'none';  
			  });
			    
			$.each($("tr[class='studenthid']"), function(i){
			      this.style.display = '';  
			  });
			  
			  $.each($("tr[class='teacherhid']"), function(i){
			 		this.style.display = 'none';  
				});
			$.each($("tr[class='studenthid']"), function(i){
			 	this.style.display = '';  
			});
			$.each($("tr[class='studenthid']"), function(i){
			 $(this).find("input.input-txt").attr("notNull","true");  
			});
			
			<#if !gooutStudentEx.isOrganization?default(false)>  
			    $.each($(".lxsunit"), function(i){
			      this.style.display = 'none';  
			  }); 
				$.each($(".lxsunit"), function(i){
			      $(this).find("input.input-txt").removeAttr("notNull");   
			  });
			  <#else>
			  $.each($(".lxsunit"), function(i){
			      this.style.display = '';  
			    });
				$.each($(".lxsunit"), function(i){
			     	$(this).find("input.input-txt").attr("notNull","true");   
			    }); 
			  </#if> 
			
			$("#content").attr("notNull","true");
			$("#contents").removeAttr("notNull"); 
			</#if> 
			<#if officeJtGoout.type?default('')=='2'>
			$.each($("tr[class='studenthid']"), function(i){
			      this.style.display = 'none';  
			  }); 
			$.each($(".lxsunit"), function(i){
			      this.style.display = 'none';  
			  });
			$.each($("tr[class='teacherhid']"), function(i){
			      this.style.display = '';  
			  });
			  
				$.each($("tr[class='studenthid']"), function(i){
					 this.style.display = 'none';  
				});
				$.each($("tr[class='studenthid']"), function(i){
					 $(this).find("input.input-txt").removeAttr("notNull");  
				});
				$.each($("tr[class='teacherhid']"), function(i){
					 $(this).find("input.input-txt").attr("notNull","true");  
				});
				$("#content").removeAttr("notNull");
				$("#contents").attr("notNull","true"); 
			</#if> 
			
			var gooutUnitId = $("#unitId").val();
			var flowUnitId = $(".flowDiv .selected").attr("flowUnitId");
			var flowOwnerType = $(".flowDiv .selected").attr("flowOwnerType");
			if(flowOwnerType == "0"){
				if(gooutUnitId == flowUnitId){
					$("#changeFlowDiv").show();
				}else{
					$("#changeFlowDiv").hide();
				}
			}
			else{
				$("#changeFlowDiv").show();
			}
			
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id="+flowId);
		});
		
		function back(){
			//var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
			//var str = "?applyStatus="+status;
			//load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutList.action"+str);
			jtGoOut();
		}
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#jtGoOutForm")){
				return;
			}
			if(!checkAfterDate($("#startTime").get(0),$("#endTime").get(0))){
				return;
			}
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("请选择一个合适的审批流程");
				 return;
			}
			
			var options = {
		       url:'${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutSave.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#jtGoOutForm').ajaxSubmit(options);
		}
		
		function doSubmit(){
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("要提交审核,必须选择一个流程");
				 return;
			}
			if(!checkAfterDate($("#startTime").get(0),$("#endTime").get(0))){
				return;
			};
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#jtGoOutForm")){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutSubmit.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#jtGoOutForm').ajaxSubmit(options);
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
					//var status=$('#resourceofficedocTypeList.user-sList-radio span.current').attr("key");
					//var str = "?applyStatus="+status;
				  	//load("#jtGoOutListDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutList.action"+str);
				  	jtGoOut();
				});
				return;
			}
		}
		
		function doChangeFlow(){
			if(showConfirm("确定保存信息并进入流程修改？")){
				if(isSubmit){
					return;
				}
				if(!checkAllValidate("#jtGoOutForm")){
					return;
				}
				if(!checkAfterDate($("#startTime").get(0),$("#endTime").get(0))){
					return;
				}
				var flowId= $("#flowId").val();
				if(!flowId||flowId==""){
					 showMsgError("请选择一个合适的审批流程");
					 return;
				}
				var options = {
			       url:'${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutSave.action', 
			       success : showReply1,
			       dataType : 'json',
			       clearForm : false,
			       resetForm : false,
			       type : 'post'
			    };
			    isSubmit = true;
			    $('#jtGoOutForm').ajaxSubmit(options);
			}
		}
		
		function showReply1(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",function(){
					var gooutId = data.errorMessage;
					$("#id").val(gooutId);
				  	openNew(gooutId);
				});
				return;
			}
		}
		
		function openNew(gooutId){
			var flowId = $("#flowId").val();
			var easyLevel = $(".flowDiv .selected").attr("dataEasyLevel");
			formUrl = '${request.contextPath}/jbmp/editor/wfEdit.action';
			businessType = '7008';
			operation = 'start';
			instanceType ='model';
			actionUrl ='${request.contextPath}/office/jtgooutmanage/jtgooutmanage-changeFlow.action?jtGoOutId='+gooutId;
			callBackJs = 'flowSuccess'; 
			taskHandlerSaveJson = '';
			currentStepId='';
			develop="false";
			subsystemId = 70;
			openOfficeWin(formUrl,businessType,operation,instanceType,flowId,actionUrl,callBackJs,taskHandlerSaveJson,currentStepId,develop,subsystemId,easyLevel);
		}
		
		function flowSuccess(){
			closeTip();
			showMsgSuccess("设计完成","",back);
		}	
		
	</script>
</@htmlmacro.moduleDiv >