<#import "/common/htmlcomponent.ftl" as common>
<#import "/common/commonmacro.ftl" as commonmacro>
<#assign NEEDAUDIT = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NEED_AUDIT") >
<#assign PASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_PASS") >
<#assign UNPASS = stack.findValue("@net.zdsoft.office.util.Constants@APPLY_STATE_NOPASS") >
<#assign SECTION = stack.findValue("@net.zdsoft.office.util.Constants@OFFICE_ROOM_ORDER_USER_TYPE_SECTION") >
<div class="typical-table-wrap pt-15" style="overflow-x:auto;">
    <table class="typical-table">
        <#if teachPlaceList?exists && teachPlaceList?size gt 0>
	        <tr>
        		<th class="tt" style="min-width:70px;">
        			<#if officeRoomOrderSet.useType! != SECTION>
        				间隔${officeTimeSet.timeInterval!}分钟
        			<#else>
        				&nbsp;
        			</#if>
        		</th>
	        	<#list teachPlaceList as tpl>
	        		<th style="word-break:break-all; word-wrap:break-word;min-width:65px;">${tpl.placeName!}</th>
	        	</#list>
	        </tr>
        	<#if officeRoomOrderSet.useType! != SECTION>
    			<#list periodList as x>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
		        			${timeMap.get(x)!}
		        		</td>
			        	<#list teachPlaceList as tpl>
			        		<#if applyMap.get('${tpl.id!}_${x!}')?exists>
			        			<#assign applyInfo = applyMap.get('${tpl.id!}_${x!}')/>
			        			<#-- 本人申请 -->
			        			<#if loginInfo.user.id == applyInfo.userId>
				        			<#if applyInfo.state == NEEDAUDIT>
					        			<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="orange"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == PASS>
				        				<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="green"><span title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)</span><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == UNPASS>
				        				<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
						        			<span class="red"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			</#if>
			        			<#else>
			        				<#-- 他人申请 -->
			        				<td>
					        			<span class="gray" title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str="${applyInfo.purpose!}" length=10/>)<i></i></span>
				        			</td>
			        			</#if>
			        		<#else>
				        		<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
				        			<span class="empty"><i></i></span>
				        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
			        			</td>
			        		</#if>
			        	</#list>
		        	</tr>
	        	</#list>
    		<#else>
        		<#list amPeriodList as x>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
			        		第${x!}节
		        		</td>
			        	<#list teachPlaceList as tpl>
			        		<#if applyMap.get('${tpl.id!}_${x!}')?exists>
			        			<#assign applyInfo = applyMap.get('${tpl.id!}_${x!}')/>
			        			<#-- 本人申请 -->
			        			<#if loginInfo.user.id == applyInfo.userId>
				        			<#if applyInfo.state == NEEDAUDIT>
					        			<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="orange"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == PASS>
				        				<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="green"><span title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)</span><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == UNPASS>
				        				<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
						        			<span class="red"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			</#if>
			        			<#else>
			        				<#-- 他人申请 -->
			        				<td>
					        			<span class="gray" title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)<i></i></span>
				        			</td>
			        			</#if>
			        		<#else>
				        		<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
				        			<span class="empty"><i></i></span>
				        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
			        			</td>
			        		</#if>
			        	</#list>
		        	</tr>
	        	</#list>
	        	<#-- 机房和实训室有中午时间段设置，该时间段在时段设置那边取 -->
	        	<tr>
		        	<td class="tt" style="text-align:center;">
		        		中午
		        		<#assign x = 99/>
	        		</td>
		        	<#list teachPlaceList as tpl>
		        		<#if applyMap.get('${tpl.id!}_${x!}')?exists>
		        			<#assign applyInfo = applyMap.get('${tpl.id!}_${x!}')/>
		        			<#-- 本人申请 -->
		        			<#if loginInfo.user.id == applyInfo.userId>
			        			<#if applyInfo.state == NEEDAUDIT>
				        			<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
					        			<span class="orange"><i></i></span>
					        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
				        			</td>
			        			<#elseif applyInfo.state == PASS>
			        				<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
					        			<span class="green"><span title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)</span><i></i></span>
					        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
				        			</td>
			        			<#elseif applyInfo.state == UNPASS>
			        				<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
					        			<span class="red"><i></i></span>
					        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
				        			</td>
			        			</#if>
		        			<#else>
		        				<#-- 他人申请 -->
		        				<td>
				        			<span class="gray" title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)<i></i></span>
			        			</td>
		        			</#if>
		        		<#else>
			        		<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');" class="edit-class">
			        			<span class="empty"><i></i></span>
			        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
		        			</td>
		        		</#if>
		        	</#list>
	        	</tr>
	        	<#list pmPeriodList as x>
		        	<tr>
			        	<td class="tt" style="text-align:center;">
			        		第${x!}节
		        		</td>
			        	<#list teachPlaceList as tpl>
			        		<#if applyMap.get('${tpl.id!}_${x!}')?exists>
			        			<#assign applyInfo = applyMap.get('${tpl.id!}_${x!}')/>
			        			<#-- 本人申请 -->
			        			<#if loginInfo.user.id == applyInfo.userId>
				        			<#if applyInfo.state == NEEDAUDIT>
					        			<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="orange"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == PASS>
				        				<td onclick="changeCancelValue('myBookState${tpl.id!}${x!}');">
						        			<span class="green"><span title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)</span><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			<#elseif applyInfo.state == UNPASS>
				        				<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
						        			<span class="red"><i></i></span>
						        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
					        			</td>
				        			</#if>
			        			<#else>
			        				<#-- 他人申请 -->
			        				<td>
					        			<span class="gray" title="${applyInfo.userName!}(${applyInfo.purpose!})">${applyInfo.userName!}(<@common.cutOff str='${applyInfo.purpose!}' length=10/>)<i></i></span>
				        			</td>
			        			</#if>
			        		<#else>
				        		<td onclick="changeApplyValue('myBookState${tpl.id!}${x!}');">
				        			<span class="empty"><i></i></span>
				        			<input type="hidden" id="myBookState${tpl.id!}${x!}" name="myBookState" value="${tpl.id!}_${x!}"/>
			        			</td>
			        		</#if>
			        	</#list>
		        	</tr>
	        	</#list>
    		</#if>
        <#else>
        	<tr>
		    	<td>
		    		<p class="no-data mt-50 mb-50">
	        				请先维护${officeRoomOrderSet.name?default('场所')}！
	    			</p>
		    	</td>
		    </tr>
        </#if>
    </table>
</div>
<#if canEdit>
<#if teachPlaceList?exists && teachPlaceList?size gt 0>
<p class="typical-tips">
	提示：点击选中您需要使用的时间段，再提交申请或撤销
    <span>
        <a href="javascript:void(0);" class="abtn-blue-big" onclick="applyRoomInfo();">申请</a>
        <a href="javascript:void(0);" class="abtn-blue-big ml-15" onclick="cancelRoomInfo();">撤销</a>
        <#if roomType=="11">
        <a class="abtn-blue-big ml-15" href="javascript:void(0);" onclick="applybxdRoomInfo();">不限定场地实验申请</a>
        </#if>
    </span>
</p>
</#if>
<input type="hidden" id="applyState" name="applyState"/>
<div class="popUp-layer popUp-layer-tips" id="layer3" style="display:none;z-index:9999;">
    <p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span><span style="color:red;">*</span>用途</span></p>
    <div class="wrap">
    <@common.tableDetail>
        <#if zhenghSchool?default(false) && roomType == '3'>
        <tr id="courseDiv">
            <th>课程：</th>
            <td>
            	<@common.select style="width:180px;" valName="courseId" valId="courseId" myfunchange="displayDiv">
					<a val="">请选择</a>
					<#list eduadmCourseDtoList as course>
					<a val="${course.id!}">${course.courseNameClassName!}</a>
					</#list>
				</@common.select>
        	</td>
        </tr>
        </#if>
        <tr id="purposeDiv">
            <th>用途：</th>
            <td>
           		<textarea id="purpose" name="purpose" style="width:250px;height:60px;"></textarea>
            </td>
        </tr>
    </@common.tableDetail>
    </div>
        <p class="t-center pb-10">
            <a class="abtn-blue" href="javascript:void(0);" onclick="saveApplyInfo()">提交</a>
            <a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
        </p>
</div>

<#-- 会议室申请专用,带附件提交 -->
<form action="" method="post" name="roomApplyForm" id="roomApplyForm" enctype="multipart/form-data">
<input type="hidden" name="officeApplyNumber.applyDate" id="currentTimeInput"/>
<input type="hidden" name="officeApplyNumber.type" id="roomTypeInput"/>
<div id="applyRoomsDiv"></div>
<div class="popUp-layer" id="layer4" style="display:none;width:700px;z-index:9999;">
		<p class="table-dt">会议申请</p>
	    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
	    <tr>
	        <th width="15%"><span class="c-orange mr-5">*</span>会议主题：</th>
	        <td width="35%">
	        	<input type="text" name="officeApplyNumber.meetingTheme" class="input-txt fn-left" style="width:200px;" notNull="true" msgName="会议主题" maxlength="60" />
	        </td>
	        <th width="15%">主持人：</th>
	        <td width="35%">
				<@commonmacro.selectOneUser idObjectId="hostUserId" nameObjectId="hostUserName" width=400 height=300>
					<input type="hidden" id="hostUserId" name="officeApplyNumber.hostUserId"/> 
					<input type="text" name="hostUserName" id="hostUserName" notNull="false" msgName="主持人"  class="input-txt fn-left" style="width:200px;" readonly="readonly"/>
		  		</@commonmacro.selectOneUser>
	        </td>
	    </tr> 
	   	<tr>
	   		<th><span class="c-orange mr-5">*</span>主办部门：</th>
	   		<td colspan="3">
	       		<@commonmacro.selectMoreTree idObjectId="deptIds" nameObjectId="deptNames"  preset="" treeUrl=request.contextPath+"/common/xtree/deptTree.action?allLinkOpen=false" >
		  			<input type="hidden" id="deptIds" name="officeApplyNumber.deptIds"/> 
		  	   		<textarea name="deptNames" id="deptNames" notNull="true" msgName="主办部门"  class="text-area my-10" rows="4" cols="69" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly"></textarea>
				</@commonmacro.selectMoreTree>
			</td>
	   	</tr>
		<tr>
			<th><span class="c-orange mr-5">*</span>与会人员：</th>
	   		<td colspan="3">
				<@commonmacro.selectMoreUser idObjectId="meetingUserIds" nameObjectId="meetingUserNames" width=400 height=300>
					<input type="hidden" id="meetingUserIds" name="officeApplyNumber.meetingUserIds"/> 
					<textarea name="meetingUserNames" id="meetingUserNames" notNull="true" msgName="与会人员"  class="text-area my-10" rows="4" cols="69" style="width:80%;padding:5px 1%;height:50px;" readonly="readonly"></textarea>
		  		</@commonmacro.selectMoreUser>
			</td>
		</tr>
		<tr>
			<th>备注：</th>
	   		<td colspan="3">
				<textarea name="officeApplyNumber.purpose" msgName="备注" class="text-area my-10" rows="4" cols="69" maxLength="500" style="width:80%;padding:5px 1%;height:50px;"></textarea>
				<br/>
				对于多媒体、音响、话筒的需求
			</td>
		</tr>
		<tr>
			<th>附件：</th>
	        <td colspan="3">
				<div class="fn-left">
					<input id="uploadFilePath" name="" type="text" class="input-txt input-readonly" readonly="readonly" style="width:200px;" value="" maxLength="125"/>&nbsp;&nbsp;
				</div>
		 		<div class="fn-rel fn-left" style="width:60px;overflow:hidden">	
		 			<span class="upload-span"><a href="javascript:void(0);">选择文件</a></span>
					<input  id="fileInput" name="fileInput" hidefocus='' type="file" onchange="doChange();"/>
		 		</div>
		 		<div id="cleanFile" style="display:none">
		 			<span class="upload-span"><a href="javascript:deleteFile();" class="">清空</a></span>
		 		</div>
			</td>
		</tr>
	    </table>
	<p class="t-center pt-15">
	    <a class="abtn-blue" href="javascript:void(0);" onclick="saveMeetingRoomInfo();">提交</a>
		<a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
	</p>
	<br/>
</div>
</form>

<#-- 实验室申请专用  -->
<form action="" method="post" name="labApplyForm" id="labApplyForm" >
<input type="hidden" name="officeApplyNumber.applyDate" id="labCurrentTimeInput"/>
<input type="hidden" name="officeApplyNumber.type" id="labRoomTypeInput"/>
<input type="hidden" name="officeApplyNumber.useType" id="useType"/>
<div id="labApplyDiv"></div>
<div class="popUp-layer" id="layer5" style="display:none;width:750px;z-index:9999;">
		<p class="table-dt">实验室申请</p>
	    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
	   	<tr>
	        <th><span class="c-orange mr-5">*</span>学科：</th>
            <td <#if !hasGrade>colspan="3"</#if>>
            	<@common.select style="width:210px;" valName="officeLabInfo.subject" valId="subject" notNull="true" msgName="学科" myfunchange="changeSearchInfo" optionDivName="selectSubject">
					<a val=""><span>请选择</span></a>
		        	<#list appsetting.getMcode('DM-SYSLX').getMcodeDetailList() as item>
	            		<a val="${item.thisId}" <#if officeLabInfo.subject?default('') == item.thisId>class="selected"</#if>><span>${item.content}</span></a>
	            	</#list>
				</@common.select>
        	</td>
        	<#if hasGrade>
        	<th>年级：</th>
	    	<td>
	    		<@common.select style="width:210px;" valId="grade" valName="grade"  msgName="年级" myfunchange="changeSearchInfo" optionDivName="selectGrade">
					<a val=""><span>----请选择----</span></a>
					<#if gradeList?exists && gradeList?size gt 0>
		        	<#list gradeList as item>
	            		<a val="${item}"><span>${item}</span></a>
	            	</#list>
	            	</#if>
				</@common.select>
	    	</td>
	    	<#else>
				<input type="hidden" msgName="年级"  id="grade" />
			</#if>
        </tr>
	   	<tr>	
			<th><span class="c-orange mr-5">*</span>实验种类：</th>
	   		<td id="labSetTD">
	   			<@common.select style="width:210px;" valName="officeLabInfo.labSetId" valId="labSetId" notNull="true" msgName="实验种类" myfunchange="changeLabTypeSet">
					<a val="">请选择</a>
				</@common.select>
			</td>
			<th>教材页面：</th>
			<td>
				<input type="text" msgName="教材页面" class="input-txt fn-left input-readonly" id="courseBook" readonly="true" style="width:200px;">
			</td>
	   	</tr>
		<tr>
		  	<@common.tdt msgName="所需仪器" id="apparatus" colspan="3" class="text-area input-readonly" readonly="true" style="width:470px;" value="" />
		</tr>
		<tr>
		  	<@common.tdt msgName="所需药品" id="reagent" colspan="3" class="text-area input-readonly" readonly="true" style="width:470px;" value="" />
		</tr>
	    <tr>
	        <th width="15%"><span class="c-orange mr-5">*</span>上课班级：</th>
	        <td width="35%">
	        	<input type="text" name="officeLabInfo.className" class="input-txt fn-left" style="width:200px;" notNull="true" msgName="上课班级" maxlength="100" />
	        </td>
		  	<th width="15%">学生人数：</th>
	        <td width="35%">
	       		 <input type="text" id="studentNum" name="officeLabInfo.studentNum" class="input-txt fn-left" style="width:200px;" datatype='integer' maxLength="4" integerLength="4" /> 
	        </td>
		</tr> 
   		<tr>
	        <th><span class="c-orange mr-5">*</span>任课教师：</th>
	        <td>
				<@commonmacro.selectOneUser idObjectId="teacherId" nameObjectId="teacherName" width=400 height=300>
					<input type="hidden" id="teacherId" name="officeLabInfo.teacherId" value="${officeLabInfo.teacherId!}"/> 
					<input type="text" id="teacherName" name="officeLabInfo.teacherName" notNull="true" msgName="任课教师"  class="input-txt fn-left" style="width:200px;" readonly="readonly" value="${officeLabInfo.teacherName!}"/>
		  		</@commonmacro.selectOneUser>
	        </td>
	   		<th><span class="c-orange mr-5">*</span>实验形式：</th>
	   		<td id="radioApp">
			</td>
	    </tr>
	    <tr id="userTime" style="display:none;">
		  	  <th style="width:20%;text-align:right;"><span class="c-red">*</span>申请使用时间:</th>
		  	  <td>
		  	  	<@common.datepicker class="input-txt" style="width:100px;" id="useStartTime" name="officeApplyNumber.useStartTime" value="" dateFmt="HH:mm"/>
		  	  	至
		  	  	<@common.datepicker class="input-txt" style="width:100px;" id="useEndTime" name="officeApplyNumber.useEndTime" value="" dateFmt="HH:mm"/>
		  	  </td>
  		</tr>
	    <tr>
			<th>用途：</th>
	   		<td colspan="3">
				<textarea name="officeApplyNumber.purpose" msgName="用途" class="text-area my-10" rows="4" cols="69" maxLength="500" style="width:470px;padding:5px 1%;height:50px;"></textarea>
			</td>
		</tr> 
	    </table>
	<p class="t-center pt-15">
	    <a class="abtn-blue" id="bxd" style="display:none;" href="javascript:void(0);" onclick="saveLabRoomInfo(2);">提交</a>
	    <a class="abtn-blue" id="xd" style="display:none;"href="javascript:void(0);" onclick="saveLabRoomInfo(1);">提交</a>
		<a class="abtn-blue reset ml-5" href="javascript:void(0);">取消</a>
	</p>
	<br/>
</div>
</form>

</#if>
<script type="text/javascript">
	
function displayDiv() {
    var courseId=$("#courseId").val();
    if(courseId=='') {
    	$("#purposeDiv").show();
    }else{
        $("#purposeDiv").hide();
    } 
}	
	
function changeApplyValue(id){
	if($("#"+id).attr("needApply") == 1){
		$("#"+id).removeAttr("needApply","1");
		//$("#classId"+id).val("");
		//$("#className"+id).text("");
	}else{
        //initChooseClass(id);
        $("#"+id).attr("needApply","1");
	}
}

function initChooseClass(id) {
	$("#classId").val($("#classId"+id).val());
	$("#className").val($("#className"+id).text());
	$("#nowObjectId").val(id);
	$("#pop-user").click();
}

function saveClass() {
    var id = $("#nowObjectId").val();
	$("#classId"+id).val($("#classId").val());
	$("#className"+id).text($("#className").val());
	if($("#classId").val() != "") {
		$("#"+id).attr("needApply","1");
	}
}

function changeCancelValue(id){
	if($("#"+id).attr("needCancel") == 2){
		$("#"+id).removeAttr("needCancel","2");
	}else{
		$("#"+id).attr("needCancel","2");
	}
}

function applybxdRoomInfo(){
	$("#applyState").val("1");
		$('#layer5').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer5 .close,#layer5 .submit,#layer5 .reset'
		});
	//$("#radioCs").css("display","");
	//$("#radioCs2").css("display","none");
	$("#radioApp").html('');
	$("#radioApp").append("<span id='radioCs' style='' class='ui-radio ui-radio-current' data-name='a'><input name='officeLabInfo.labMode' type='radio' class='radio' value='1' checked>教师演示实验</span>");
	$("#userTime").css("display","");
	$("#xd").css("display","none");
	$("#bxd").css("display","");
}

function applyRoomInfo(){
	<#if roomType == '70'>
		if($("input[name='myBookState'][needApply='1']").length > 0){
			$("#applyState").val("1");
			$('#layer4').jWindowOpen({
				modal:true,
				center:true,
				close:'#layer4 .close,#layer4 .submit,#layer4 .reset'
			});
		}else{
			showMsgWarn("请先选择要申请的会议室");
		}
	<#elseif roomType == '11'>
		if($("input[name='myBookState'][needApply='1']").length > 0){
			$("#applyState").val("1");
			$('#layer5').jWindowOpen({
				modal:true,
				center:true,
				close:'#layer5 .close,#layer5 .submit,#layer5 .reset'
			});
			$("#radioApp").html('');
			$("#radioApp").append("<span style='' class='ui-radio ui-radio-current' data-name='a'><input name='officeLabInfo.labMode' type='radio' class='radio' value='2' checked>学生操作实验</span>");
			$("#userTime").css("display","none");
			$("#xd").css("display","");
			$("#bxd").css("display","none");
		}else{
			showMsgWarn("请先选择要申请的实验室");
		}
	<#else>
		if($("input[name='myBookState'][needApply='1']").length > 0){
		    <#if zhenghSchool?default(false) && roomType == '3'>
				$("#courseDiv").show();
	    	</#if>
			$("#applyState").val("1");
			$('#layer3').jWindowOpen({
				modal:true,
				center:true,
				close:'#layer3 .close,#layer3 .submit,#layer3 .reset'
			});
		}else{
			showMsgWarn("请先选择要申请的${officeRoomOrderSet.name?default('场所')}");
		}
	</#if>
}

function cancelRoomInfo(){
	if($("input[name='myBookState'][needCancel='2']").length > 0){
	    <#if zhenghSchool?default(false) && roomType == '3'>
			$("#courseDiv").hide();
	    </#if>
		$("#applyState").val("2");
		$('#layer3').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer3 .close,#layer3 .reset'
		});
	}else{
		showMsgWarn("请先选择要撤销的${officeRoomOrderSet.name?default('场所')}");
	}
}

var isSubmit = false;
function saveApplyInfo(){
	if(isSubmit){
		return;
	}
	var purpose = $("#purpose").val();
	var courseId = '';
	<#if zhenghSchool?default(false) && roomType == '3'>
		courseId = $("#courseId").val();
		if(courseId == '' && purpose == ''){
			showMsgWarn("没有选择课程时用途不能为空");
			return;
		}
	<#else>
		if(purpose == ''){
			showMsgWarn("用途不能为空");
			return;
		}
	</#if>
	if(_getLength(purpose) > 500){
		showMsgError('用途不能超过500个字符');
		return;
	}
	var applyState = $("#applyState").val();
	var promptInfo;
	var applyRooms = [];
	var i = 0;
	if(applyState == 1){
		promptInfo = "提交申请成功";
		$("input[name='myBookState'][needApply='1']").each(function(){
			applyRooms[i] = $(this).val();
			i++;
		});
		if(applyRooms.length == 0){
			showMsgWarn("请先选择要申请的${officeRoomOrderSet.name?default('场所')}");
			return;
		}
	}else{
		promptInfo = "撤销成功";
		$("input[name='myBookState'][needCancel='2']").each(function(){
			applyRooms[i] = $(this).val();
			i++;
		});
		if(applyRooms.length == 0){
			showMsgWarn("请先选择要撤销的${officeRoomOrderSet.name?default('场所')}");
			return;
		}
	}
	var roomType = $("#roomType").val();
	if(roomType == ''){
		showMsgWarn("类型不能为空");
		return;
	}
	var currentTime = $("#currentTime").val();
	if(currentTime == ''){
		showMsgWarn("日期不能为空");
		return;
	}
	isSubmit = true;
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/roomorder/roomorder-saveApplyInfo.action",
		data: $.param( {"applyRooms":applyRooms,"courseId":courseId,"purpose":purpose,"roomType":roomType,"currentTime":currentTime,"applyState":applyState},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				isSubmit = false;
				return;
			}else{
				showMsgSuccess(promptInfo, "提示", function(){
					searchOrder();
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function saveMeetingRoomInfo(){
	if(isSubmit){
		return;
	}
	var length = $("input[name='myBookState'][needApply='1']").length;
	if(length == 0){
		showMsgWarn("请先选择要申请的会议室");
		return;
	}
	var roomType = $("#roomType").val();
	if(roomType == ''){
		showMsgWarn("类型不能为空");
		return;
	}
	var currentTime = $("#currentTime").val();
	if(currentTime == ''){
		showMsgWarn("日期不能为空");
		return;
	}
	if(!checkAllValidate("#layer4")){
		return;
	}
	$("#roomTypeInput").val(roomType);
	$("#currentTimeInput").val(currentTime);
	
	$("input[name='officeApplyNumber.applyRooms']").each(function(){
		$(this).remove();
	});
	var applyRoomsHtml = "";
	$("input[name='myBookState'][needApply='1']").each(function(){
		applyRoomsHtml += '<input type="hidden" name="officeApplyNumber.applyRooms" value="'+$(this).val()+'"/>';
	});
	$("#applyRoomsDiv").append(applyRoomsHtml);
	isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/roomorder/roomorder-saveMeetingRoomInfo.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#roomApplyForm').ajaxSubmit(options);
}

function showReply(data){
	if(data != null && data!=''){
		showMsgError(data);
		isSubmit = false;
		return;
	}else{
		showMsgSuccess("提交申请成功", "提示", function(){
			searchOrder();
		});
		return;
	}
}

function saveLabRoomInfo(obj){
	if(isSubmit){
		return;
	}
	if(obj=="1"){
		var length = $("input[name='myBookState'][needApply='1']").length;
		if(length == 0){
			showMsgWarn("请先选择要申请的实验室");
			return;
		}
	}
	if(obj=="2"){
		var useStartTime=$("#useStartTime").val();
		var useEndTime=$("#useEndTime").val();
		if(useStartTime==''||useEndTime==''){
			showMsgWarn("请选择使用时间");
			return;
		}
	var noonStartTimes=useStartTime.split(':');  
	var noonEndTimes=useEndTime.split(':');
	var noonStartStr = "1"+noonStartTimes[0]+noonStartTimes[1];
	var noonEndStr = "1"+noonEndTimes[0]+noonEndTimes[1];
	var noonStart = parseInt(noonStartStr);
	var noonEnd = parseInt(noonEndStr);
	if(noonEnd<=noonStart){
		showMsgWarn("开始时间必须小于结束时间！");
		return;
	}
	}
	if(obj=="1"){
		$("#useStartTime").val("");
		$("#useEndTime").val("");
	}
	var roomType = $("#roomType").val();
	if(roomType == ''){
		showMsgWarn("类型不能为空");
		return;
	}
	var currentTime = $("#currentTime").val();
	if(currentTime == ''){
		showMsgWarn("日期不能为空");
		return;
	}
	if(!checkAllValidate("#layer5")){
		return;
	}
	$("#labRoomTypeInput").val(roomType);
	$("#labCurrentTimeInput").val(currentTime);
	
	if(obj=="1"){
		$("input[name='officeApplyNumber.applyRooms']").each(function(){
			$(this).remove();
		});
	
		var applyRoomsHtml = "";
		$("input[name='myBookState'][needApply='1']").each(function(){
			applyRoomsHtml += '<input type="hidden" name="officeApplyNumber.applyRooms" value="'+$(this).val()+'"/>';
		});
		$("#labApplyDiv").append(applyRoomsHtml);
	}

	$("#useType").val(obj);
	isSubmit = true;
	var options = {
       url:'${request.contextPath}/office/roomorder/roomorder-saveLabRoomInfo.action', 
       success : showReply,
       dataType : 'json',
       clearForm : false,
       resetForm : false,
       type : 'post'
    };
    $('#labApplyForm').ajaxSubmit(options);
}

function changeSearchInfo(){
	var subject = $("#subject").val();
	var grade = $("#grade").val();
	
	var url="${request.contextPath}/office/roomorder/roomorder-getSubjectSelect.action?officeLabInfo.subject="+subject+"&searchGrade="+grade;
	load("#labSetTD", url);
	jQuery("#courseBook").val("");
	jQuery("#apparatus").val("");
	jQuery("#reagent").val("");
}

function changeLabTypeSet(){
	var labSetId = $("#labSetId").val();
	
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/roomorder/roomorder-getLabSetInfo.action?officeLabInfo.labSetId="+labSetId,
		success: function(data){
			jQuery("#courseBook").val(data.courseBook);
			jQuery("#apparatus").val(data.apparatus);
			jQuery("#reagent").val(data.reagent);
			jQuery("#subject").val(data.subject);
			jQuery("#grade").val(data.grade);
			
			jQuery("#selectSubject a").attr("class","");
			jQuery("#selectSubject a[val="+data.subject+"]").attr("class","selected");
			jQuery("#selectGrade a").attr("class","");
			jQuery("#selectGrade a[val="+data.grade+"]").attr("class","selected");
			vselect();
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

$(function(){
	//初始化
	var tpW=$('.typical-table-wrap').width();
	var tdLen=$('.typical-table th').length;
	var tdW=(tpW-75)/tdLen;
	$('.typical-table th:not(".tt")').width(tdW);
	<#if canEdit>
	//选中
	$('.typical-table td span:not(".gray")').click(function(){
		var $td=$(this).parent('td');
		if(!$td.hasClass('current')){
			$td.addClass('current');
		}else{
			$td.removeClass('current');
		}
	});
	$('#btn3').click(function(e){
		e.preventDefault();
		$('#layer3').jWindowOpen({
			modal:true,
			center:true,
			close:'#layer3 .close,#layer3 .submit,#layer3 .reset'
		});
	});
	</#if>
	vselect();
	$(".upload-span").mouseover(function(){
		$("#fileInput").offset({"top":$(".upload-span").offset().top });
	});
	resetFilePos();
});
function resetFilePos(){
	$("#fileInput").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","cursor":"pointer","font-size":"30px","left":"-350px"});
	$("#fileInput").css({"display":""});
}
function doChange(){
	$('#uploadFilePath').val($('#fileInput').val());
	$('#cleanFile').attr("style","display:block");
}
function deleteFile(){
	$('#uploadFilePath').val('');
	var file = $("#fileInput")
	file.after(file.clone().val(""));
	file.remove();
	$('#cleanFile').attr("style","display:none");
}
</script>