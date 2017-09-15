<#import "/common/htmlcomponent.ftl" as htmlmacro>
<script type="text/javascript" src="${request.contextPath}/static/js/printarea.js"></script>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    <div class="query-part">
    	<div class="query-tt ml-10"><span class="fn-left">开始时间：</span></div>
	    <@htmlmacro.datepicker name="startTime" id="startTime" style="width:120px;" value="${(startTime)?if_exists}"/>
	   	<div class="query-tt">&nbsp;结束时间：&nbsp;</div>
	    <@htmlmacro.datepicker name="endTime" id="endTime" style="width:120px;" value="${(endTime)?if_exists}"/>
    	<div class="query-tt ml-10"><span class="fn-left">类型：</span></div>
    	<div class="ui-select-box fn-left" style="width:120px;">
            <input type="text" class="ui-select-txt" value=""  readonly/>
            <input name="type" id="type" type="hidden" value=""  class="ui-select-value" />
            <a class="ui-select-close"></a>
            <div class="ui-option" myfunchange="doSearch">
        		<div class="a-wrap">
            	<a val=""><span>全部</span></a>
            	<a val="1" <#if type?default("") == "1">class="selected"</#if>><span>学生集体活动</span></a>
                <a val="2" <#if type?default("") == "2">class="selected"</#if>><span>教师集体培训</span></a>
                </div>
            </div>
	    </div>
	    <#if loginInfo.unitClass==1>
    	<div class="query-tt ml-10"><span class="fn-left">单位：</span></div>
        <input name="unitName" id="unitName" value="${unitName!}" maxLength="30" class="input-txt fn-left" style="width:120px;"/>
        </#if>
	    <a href="javascript:void(0);" onclick="doSearch();" class="abtn-blue fn-left ml-20">查找</a>
        <div class="fn-clear"></div>
    </div>
</div>
<div id="wirkReportDiv"></div>
<@htmlmacro.tableList class="public-table table-list table-list-edit mt-5">
  	<tr>
    	<th width="10%">序号</th>
    	<th width="10%">申请单位</th>
    	<th width="30%">起止时间</th>
    	<th width="20%">类型</th>
    	<th width="20%">活动内容</th>
    	<th class="t-center" width="10%">详细操作</th>
    </tr>
    <#if officeJtGoOutList?exists && officeJtGoOutList?size gt 0>
    	<#list officeJtGoOutList as officeJtgoOut>
    		<tr>
    			<td >${officeJtgoOut_index+1}</td>
		    	<td >${officeJtgoOut.unitName!}</td>
		    	<td >${((officeJtgoOut.startTime)?string('yyyy-MM-dd'))?if_exists}至${((officeJtgoOut.endTime)?string('yyyy-MM-dd'))?if_exists}</td>
		    	<td>
		    		<#if officeJtgoOut.type=='1'>
		    			学生集体活动
		    		<#elseif officeJtgoOut.type=='2'>
		    			教师集体培训
		    		</#if>
		    	</td>
		    	<td>
		    		<@htmlmacro.cutOff4List str="${officeJtgoOut.content!}" length=28 />
		    	</td>
		    	<td class="t-center">
		    		<a href="javascript:void(0);" onclick="doInfo('${officeJtgoOut.id!}');">查看</a>
		    		<a href="javascript:void(0);" onclick="printDiv('${officeJtgoOut.id!}');">打印</a>
		    	</td>
    		</tr>
    	</#list>
    <#else>
    	<tr>
    		<td colspan='6'><p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	 	</tr>
    </#if>
</@htmlmacro.tableList>
<#if officeJtGoOutList?exists && officeJtGoOutList?size gt 0>
<@htmlmacro.Toolbar container="#goOutDiv">
</@htmlmacro.Toolbar>
</#if>
<div id="printDiv" style="display:none">
	 <@htmlmacro.tableDetail divClass="table-form">
		<tr>
	        <th colspan="4" style="text-align:center;">集体外出</th>
	    </tr>
	    <tr>
	        <th  ><span class="c-orange mr-5">*</span>请假时间：</th>
	        <td colspan="3" id="time">
	        	
	        </td>
	    </tr>
	    <tr>
	       <th style="width:20%"><span class="c-orange mr-5">*</span>外出类型：</th>
	       <td id="types" colspan="3">
		   </td>
	    </tr>
	    
	    
	     <tr class="studenthid">
	        <th style="width:20%"><span class="c-orange mr-5">*</span>组织活动的年级或班级：</th>
		        <td style="width:30%" id="organize">
		        </td>
		        <th style="width:20%"><span class="c-orange mr-5">*</span>活动人数：</th>
		        <td style="width:30%" id="activityNumber">
		        </td>
	    </tr>
	     <tr class="studenthid">
	        <th style="width:20%"><span class="c-orange mr-5">*</span>活动地点：</th>
		       <td style="width:30%"  colspan="3" id="place">
		      </td>
	    </tr>
	    <tr class="studenthid">
	        <th><span class="c-orange mr-5">*</span>活动内容：</th>
		    <td colspan="3" id="content">
		    </td>
	    </tr>
	     <tr class="studenthid">
	        <th style="width:20%"><span class="c-orange mr-5">*</span>交通工具：</th>
		       <td style="width:30%" id="vehicle">
		       </td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否有营运证：</th>
		        <td style="width:40%" id="drivinglicence">
                </td>
	    </tr>
	    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否由旅行社组织：</th>
		        <td style="width:40%" id="organization">
                </td>
                
		       <th style="width:20%" class="lxsunit"><span class="c-orange mr-5">*</span>旅行社单位：</th>
		       <td style="width:30%" colspan="3" class="" id="traveUnit">
		       </td>
		       
		    </tr>
		    
		    <tr class="studenthid lxsunit">
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人：</th>
		       <td style="width:30%" id="traveLinkPerson">
		       </td>
		       <th style="width:20%"><span class="c-orange mr-5">*</span>旅行社联系人手机号：</th>
		       <td style="width:30%" id="traveLinkPhone">
		       </td>
		    </tr>
		    
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>是否购买人身保险和意外伤害保险：</th>
		        <td style="width:40%" id="insurance" colspan="3">
                </td>
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人：</th>
		        <td style="width:30%" id="activityLeaderName">
		        </td>
                <th style="width:20%"><span class="c-orange mr-5">*</span>活动负责人手机号：</th>
		        <td style="width:30%" id="activityLeaderPhone">
		        </td>
		    </tr>
		    <tr class="studenthid">
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>带队老师：</th>
		        <td style="width:30%" id="leadGroupName">
		        </td>
		    	<th style="width:20%"><span class="c-orange mr-5">*</span>带队老师手机号：</th>
		        <td style="width:30%" id="leadGroupPhone">
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th>其他老师：</th>
		        <td colspan="3" id="otherTeacherNames">
		        </td>
		    </tr>
		    <tr class="studenthid">
		       <th style="width:10%"><span class="c-orange mr-5">*</span>活动方案：</th>
		        <td style="width:40%" id="activity">
                </td>
		       <th style="width:10%"><span class="c-orange mr-5">*</span>安全方案：</th>
		        <td style="width:40%" id="saft">
                </td>
		    </tr>
	    	
	    	
	    	<tr class="teacherhid">
		        <th><span class="c-orange mr-5">*</span>外出内容：</th>
		        <td colspan="3" id="contents">
		        </td>
		    </tr>
		    <tr class="teacherhid">
		       <th>参与人员：</th>
		        <td colspan="3" id="partakePersonNames">
		        </td>
		    </tr>	
		    
	    <tr>
	        <th>附件：</th>
	        <td colspan="3" id="fileName">
	        </td>
	    </tr>
	</@htmlmacro.tableDetail>
    <br>
	<div class="fw-edit mt-10">
    	<p class="tt">流程意见</p>
	        <div id="printFlow" class="fw-item-wrap">
        </div>
    </div>
</div> 
<script>
	function doInfo(id){
		load("#goOutDiv","${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutView.action?jtGoOutId="+id+"&fromTab="+3);
	}
	function doSearch(){
		var type =$("#type").val();
		var unitName = $("#unitName").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		
		if(compareDate(startTime, endTime) > 0 ){
			showMsgWarn("开始时间不能大于结束时间，请重新选择！");
			return;
		}
	load("#goOutDiv", "${request.contextPath}/office/jtgooutmanage/jtgooutmanage-jtGoOutQueryList.action?unitName="+encodeURIComponent(unitName)+"&startTime="+startTime+"&endTime="+endTime+"&type="+type);
}

function printDiv(id){
		jQuery.ajax({
			url:"${request.contextPath}/office/jtgooutmanage/jtgooutmanage-printJtGoOut.action",
		   	type:"POST",
		   	dataType:"json",
		   	data:{"jtGoOutId":id},
		   	async:false,
		   	error:function(){
		   		showMsgError("打印出错！");
		   	},
		   	success:function(data){
		   		printValueDiv(data);
		   	}
		});
	}
	function printValueDiv(data){
		var officeJtGoout=data["officeJtGoout"];
		var gooutStudentEx=data["gooutStudentEx"];
		var gooutTeacherEx=data["gooutTeacherEx"];
		if(officeJtGoout!=null){
			document.getElementById("time").innerHTML=officeJtGoout.startTimeStr+"至"+officeJtGoout.endTimeStr;
			document.getElementById("types").innerHTML=officeJtGoout.typeName;
			if(officeJtGoout.type=="1"){
				$.each($("tr[class='teacherhid']"), function(i){
			 		this.style.display = 'none';  
				});
				$.each($("tr[class='studenthid']"), function(i){
					 this.style.display = '';  
				});
				document.getElementById("organize").innerHTML=gooutStudentEx.organize;
				document.getElementById("activityNumber").innerHTML=gooutStudentEx.activityNumber;
				document.getElementById("place").innerHTML=gooutStudentEx.place;
				document.getElementById("content").innerHTML=gooutStudentEx.content;
				document.getElementById("vehicle").innerHTML=gooutStudentEx.vehicle;
				document.getElementById("drivinglicence").innerHTML=gooutStudentEx.drivinglicence;
				document.getElementById("organization").innerHTML=gooutStudentEx.organization;
				
				if(gooutStudentEx.isOrganization){
					$.each($(".lxsunit"), function(i){
			      		this.style.display = '';  
			  		});
			  		document.getElementById("traveUnit").innerHTML=gooutStudentEx.traveUnit;
			  		document.getElementById("traveLinkPerson").innerHTML=gooutStudentEx.traveLinkPerson;
			  		document.getElementById("traveLinkPhone").innerHTML=gooutStudentEx.traveLinkPhone;
				}else{
					$.each($(".lxsunit"), function(i){
			      		this.style.display = 'none';  
			  		});
			  		$("#traveUnit").html("");
				}
				
				document.getElementById("insurance").innerHTML=gooutStudentEx.insurance;
				document.getElementById("activityLeaderName").innerHTML=gooutStudentEx.activityLeaderName;
				document.getElementById("leadGroupName").innerHTML=gooutStudentEx.leadGroupName;
				document.getElementById("otherTeacherNames").innerHTML=gooutStudentEx.otherTeacherNames;
				document.getElementById("activityLeaderPhone").innerHTML=gooutStudentEx.activityLeaderPhone;
				document.getElementById("leadGroupPhone").innerHTML=gooutStudentEx.leadGroupPhone;
				document.getElementById("activity").innerHTML=gooutStudentEx.activity;
				document.getElementById("saft").innerHTML=gooutStudentEx.saft;
			}else{
				$.each($(".teacherhid"), function(i){
			 		this.style.display = '';  
				});
				$.each($(".studenthid"), function(i){
					 this.style.display = 'none';  
				});
			
				document.getElementById("contents").innerHTML=gooutTeacherEx.content;
				document.getElementById("partakePersonNames").innerHTML=gooutTeacherEx.partakePersonNames;
			}
			
			if(officeJtGoout.fileNames!=null){
				var fileName="";
				var lengthLeave=officeJtGoout.fileNames.length;
				for(var j=0;j<lengthLeave;j++){
					if(j==lengthLeave-1){
						fileName+=officeJtGoout.fileNames[j];
					}else{
						fileName+=officeJtGoout.fileNames[j]+"    ";
					}
				}
				document.getElementById("fileName").innerHTML=fileName;
			}
			if(officeJtGoout.flowId!=null && officeJtGoout.flowId!='1'){
				var flowHtml="";
				for(var i=0;i<officeJtGoout.hisTaskList.length;i++){
					var task=officeJtGoout.hisTaskList[i];
					flowHtml+='<div class="fw-item fn-clear"> <p class="tit fn-clear"><span class="num">';
					flowHtml+=(i+1);
					flowHtml+='</span><span class="pl-5">';
					flowHtml+=task.taskName;
					flowHtml+='</span></p><p class="name">负责人：';
					flowHtml+=task.assigneeName;
					flowHtml+='</p> <div class="fn-clear"></div><div class="des" >';
					if(task.comment.commentType==1){
						flowHtml+=task.comment.textComment;
					}
					flowHtml+='</div><p class="date">';
					flowHtml+='';
					flowHtml+='</p></div>';
				}
				$("#printFlow").html(flowHtml);
			}
		}
		LODOP=getLodop();
		LODOP.ADD_PRINT_HTM("10mm","5mm","RightMargin:5mm","BottomMargin:10mm",getPrintContent(jQuery('#printDiv')));
	  	LODOP.PREVIEW();
	}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>