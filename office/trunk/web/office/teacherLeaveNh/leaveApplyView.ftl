<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="教师请假查看">
	<input id="id" name="officeTeacherLeaveNh.id" value="${officeTeacherLeaveNh.id!}" type="hidden" />
	<input id="flowType" name="officeTeacherLeaveNh.flowType" value="${officeTeacherLeaveNh.flowType!}" type="hidden" />
	<@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">教师请假查看</th>
	    </tr>
	    <tr>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>请假时间：</th>
	        <td colspan="3" >
	        	${((officeTeacherLeaveNh.beginTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
	        	至
	        	${((officeTeacherLeaveNh.endTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}
	        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>共计天数：</th>
	       <td style="width:30%">
	        	${(officeTeacherLeaveNh.days?string('0.#'))?if_exists}
	       </td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>申请人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.userName!}
	        </td>
	    </tr>
	     <tr>
	        <th style="width:20%">请假类型：</th>
	        <td colspan="3" style="width:80%">
        	 	${officeTeacherLeaveNh.leaveTypeName!}
	        </td>
	    </tr>
	    <tr>
	        <th style="width:20%">早自修调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.morningChange!}
	        </td>
	        <th style="width:20%">晚自修调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.nightChange!}
	        </td>
	    </tr>
	    <tr>
	        <th style="width:20%">值周调换人：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.weekChange!}
	        </td>
	        <th style="width:20%">代理班主任：</th>
	        <td style="width:30%">
	        	${officeTeacherLeaveNh.actChargeTeacher!}
	        </td>
	    </tr>
	    <tr>
	        <th>备注：</th>
	        <td colspan="3">
	        	<textarea name="officeTeacherLeaveNh.remark" id="remark" cols="70" rows="4" class="text-area my-5" style="width:80%;padding:5px 1%;height:50px;" msgName="备注" maxLength="200" disabled="disabled">${officeTeacherLeaveNh.remark!}</textarea>
	        </td>
	    </tr>
	    <tr>
	    	<td colspan="4" class="td-opt">
	    		<#if officeTeacherLeaveNh.state==3>
				<a href="javaScript:void(0);" onclick="onprint();" class="abtn-blue-big">打印</a>
				</#if>
			    <a class="abtn-blue-big ml-5" href="javascript:void(0);" onclick="doSearch();">返回</a>
	        </td>
	    </tr>
	</@htmlmacro.tableDetail>
	<#if officeTeacherLeaveNh.flowId?default('')!='1'>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
        <div class="fw-item-wrap">
        	<#if (officeTeacherLeaveNh.hisTaskList?size>0)>
        	<#list officeTeacherLeaveNh.hisTaskList as item>
        		<div class="fw-item fn-clear">
	        		<div class="tit">
	                	<p class="num">${item_index+1}</p>
	                	<p>${item.taskName!}</p>
	                </div>
	                <div class="des fn-clear">
	                    <p class="name"><span>${((item.comment.operateTime)?string('yyyy-MM-dd HH:mm:ss'))?if_exists}</span>${item.assigneeName!}</p>
	                    <p ><#if item.comment.commentType==1>${item.comment.textComment!}<#else></#if></p>
	                </div>
	        	</div>
        	</#list>
        	<#else>
        	<p class="no-data mt-50 mb-50">还没有数据哦！</p>
        	</#if>
        </div>
    </div>
    </#if>
	<p class="pt-20 t-center">
    	<a href="javascript:void(0)" class="abtn-blue-big" onclick="doSearch()">返回</a>
	</p>
	<div id="flowShow"  class="docReader my-20" style="height:660px;">
		<p >请选择流程</p>
	</div>
	<div id="teachPrint" style="display:none;"></div>
	<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>	
	<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
	<script>
		$(document).ready(function(){
			vselect();
			load("#flowShow","${request.contextPath}/jbmp/editor/wfPreview.action?subsystemId=70&id=${officeTeacherLeaveNh.flowId!}&instanceType=instance");
			<#if officeTeacherLeaveNh.state==3>
			load("#teachPrint","${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-infoPrint.action?teacherLeaveNhId=${officeTeacherLeaveNh.id!}");
			</#if>
		});
		
		var isSubmit =false;
		function doSave(){
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveNhForm")){
				return;
			}
			if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
				return;
			};
		    isSubmit = true;
			var options = {
		       target : '#teacherLeaveNhForm',
		       url:'${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-saveTeacherLeaveNh.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    $('#teacherLeaveNhForm').ajaxSubmit(options);
		}
		
		function doSubmit(){
			var flowId= $("#flowId").val();
			if(!flowId||flowId==""){
				 showMsgError("要提交审核,必须选择一个流程");
				 return;
			}
			if(!checkAfterDate($("#beginTime").get(0),$("#endTime").get(0))){
				return;
			};
			if(isSubmit){
				return;
			}
			if(!checkAllValidate("#teacherLeaveNhForm")){
				return;
			}
			var options = {
		       url:'${request.contextPath}/office/teacherLeaveNh/teacherLeaveNh-submitTeacherLeaveNh.action', 
		       success : showReply,
		       dataType : 'json',
		       clearForm : false,
		       resetForm : false,
		       type : 'post'
		    };
		    isSubmit = true;
		    $('#teacherLeaveNhForm').ajaxSubmit(options);
		}
		
		function showReply(data){
			if(!data.operateSuccess){
			   if(data.errorMessage!=null&&data.errorMessage!=""){
				   showMsgError(data.errorMessage);
				   isSubmit = false;
				   return;
			   }
			}else{
				showMsgSuccess(data.promptMessage,"",doSearch);
				return;
			}
		}
		
		function onprint(){
			LODOP=getLodop();
			LODOP.ADD_PRINT_HTM("25mm","15mm","RightMargin:15mm","BottomMargin:15mm", getPrintContent(jQuery('#printArea')));
			LODOP.PREVIEW();
		}
		
	</script>
</@htmlmacro.moduleDiv>