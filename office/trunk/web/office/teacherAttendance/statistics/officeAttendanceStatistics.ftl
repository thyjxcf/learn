<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="">
<script>
	jQuery(document).ready(function(){
		<#if teacherAttenceAdmin>
		statisticsData("","");
		<#else>
			statisticsData("","${deptList[0].id!}");
		</#if>
		jQuery(".pub-two-tab li").click(function(){
			var type = $(this).attr("type");
			if(type == "1"){
				$("#group").hide();
				$("#department").hide();
				statisticsData("","");
			}
			if(type == "2"){
				$("#department").hide();
				$("#group").show();
				if($("#groupId").val() != ""){
				statisticsData($("#groupId").val(),"");
				}
			}
			if(type == "3"){
				$("#group").hide();
				$("#department").show();
				if($("#deptId").val() !=""){
				statisticsData("",$("#deptId").val());
				}
				
			}
			$(this).siblings().removeAttr("class");
			$(this).attr("class","current");
		});
	
	});
	function query(){
			var type = jQuery(".pub-two-tab li.current").attr("type");
			if( type == "2"){
				var groupId = $("#groupId").val();
				if(groupId == ""){
					return;
				}
				statisticsData(groupId,"");
			}else if (type == "3" ){
				var deptId = $("#deptId").val();
				if(deptId == ""){
					return;
				}
				statisticsData("",deptId);
			}
	}
	function statisticsData(groupId,deptId){
		var queryDate = $("#queryDate").val();
		var str1 = "groupId="+groupId+"&deptId="+deptId+"&queryDate="+queryDate;
		load("#checkingbar" ,"${request.contextPath}/office/teacherAttendance/teacherAttendance-statisticsBar.action?" + str1);
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var str2 = "groupId="+groupId+"&deptId="+deptId+"&startDate="+startDate+"&endDate="+endDate;
		load("#showList","${request.contextPath}/office/teacherAttendance/teacherAttendance-statisticsList.action?" + str2);
	}
	function statisticBar(){
		var type = jQuery(".pub-two-tab li.current").attr("type");
		var groupId="";
		var deptId="";
			if( type == "2"){
				 groupId = $("#groupId").val();
				if(groupId == ""){
					return;
				}
			}else if (type == "3" ){
				 deptId= $("#deptId").val();
				if(deptId == ""){
					return;
				}
			}
		
		var queryDate = $("#queryDate").val();
		var str1 = "groupId="+groupId+"&deptId="+deptId+"&queryDate="+queryDate;
		load("#checkingbar" ,"${request.contextPath}/office/teacherAttendance/teacherAttendance-statisticsBar.action?" + str1);
	}
	function stattisticList(){
		if(!validateDate()){
			return;
		}
		var type = jQuery(".pub-two-tab li.current").attr("type");
		var groupId="";
		var deptId="";
			if( type == "2"){
				 groupId = $("#groupId").val();
				if(groupId == ""){
					return;
				}
			}else if (type == "3" ){
				 deptId= $("#deptId").val();
				if(deptId == ""){
					return;
				}
			}
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var str2 = "groupId="+groupId+"&deptId="+deptId+"&startDate="+startDate+"&endDate="+endDate;
		load("#showList","${request.contextPath}/office/teacherAttendance/teacherAttendance-statisticsList.action?" + str2);
		
	}
	//校验日期
	
	function validateDate(){
		
		var startDate = document.getElementById("startDate");
		var endDate = document.getElementById("endDate");
		
		if(!(checkAfterDateWithMsg(startDate,endDate,"起始日期要小于结束日期！"))){
			var errorText = $("#error").text();
			if(errorText != "起始日期要小于结束日期！"){
			$("#endDate").after("<span id=\"error\" class=\"field_tip input-txt-warn-tip\">起始日期要小于结束日期！</span>");
			}
			$("#timediv span").last().remove();
			return false;
		}else {
			$("#error").remove();
			//width: 151px; border-color: rgb(227, 64, 7);
			$("#startDate").css("border-color","");
			return true;
		}
		
	}
	function doExport(){
	    var type = jQuery(".pub-two-tab li.current").attr("type");
		var groupId="";
		var deptId="";
			if( type == "2"){
				 groupId = $("#groupId").val();
				if(groupId == ""){
					return;
				}
			}else if (type == "3" ){
				 deptId= $("#deptId").val();
				if(deptId == ""){
					return;
				}
			}
	    var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var str2 = "groupId="+groupId+"&deptId="+deptId+"&startDate="+startDate+"&endDate="+endDate;
		if(confirm("确认导出信息？")){
			location.href = "${request.contextPath}/office/teacherAttendance/teacherAttendance-ExportStatisticsList.action?"+ str2;
		}
	}
</script>
<div class="pub-table-wrap">
    	<div class="pub-table-inner">
    		
            <ul class="pub-two-tab">
	            <#if  teacherAttenceAdmin>
                 <li type="1" class="current">按单位统计</li>
                <li type="2">按考勤组统计</li>
                <li type="3" >按部门统计</li>
                <#else>
                <li type="3" class="current">按部门统计</li>
	    		</#if>
            </ul>
            		<div class="mt-10" >
            		<#if  teacherAttenceAdmin>
            			<div id="group" style="display:none"  class="mt-10" > 
		            		 <div class="query-tt mt-5 ml-10 fn-left">考勤组：</div>
			                 <div class="ui-select-box fn-left" style="width:95px;">
				                <input type="text" id="groutText" class="ui-select-txt" value="" />
				                <input id="groupId" name="" type="hidden" value="" class="ui-select-value" />
				                <a class="ui-select-close"></a>
				                <div class="ui-option"  myfunchange="query" >
				                	<div class="a-wrap">
				                		<#list groupList as group>
				                			<a val="${group.id!}"><sapn>${group.name}</span></a>
				                		</#list>
				                    </div>
				                </div>
	                       </div>	
                       </div>
                    </#if>
                       <div id="department" <#if teacherAttenceAdmin > style="display:none " </#if> class="mt-10"  >
                        <div class="query-tt mt-5 ml-10 fn-left">部门：</div>
		                 <div class="ui-select-box fn-left" style="width:95px;">
			                <input type="text" id="deptText" class="ui-select-txt" value="" />
			                <input id="deptId" name="" type="hidden" value="" class="ui-select-value" />
			                <a class="ui-select-close"></a>
			                <div class="ui-option" myfunchange="query">
			                	<div class="a-wrap">
			                		<#list deptList as dept>
			                			<a val="${dept.id!}" ><span>${dept.deptname!}</span></a>
			                		</#list>
			                    </div>
			                </div>
                        </div>
                      </div>                    
            			<div class="query-tt mt-5 ml-10 fn-left">时间：</div>
            			<@htmlmacro.datepicker msgName="时间" id="queryDate" maxDate="${(maxDate)?string('yyyy-MM-dd')}" value="${(queryDate?string('yyyy-MM-dd'))?if_exists}" onpicked="statisticBar"  maxlength="19"  style="width:140px;" dateFmt="yyyy-MM-dd"/>
            		</div>
            		
            	<div style="height:50px;"></div>	
              <div class="pub-two-tab-inner">
            	<div class="checking-sta mt-20" style="height: 500px;">
            		 <div class="checking-sta-pie mt-40" id="checkingbar" style="height: 400px;">
            		</div>
            	</div>
            	<div class="query-builder" >
	            	<div class="query-part fn-clear" id="timediv" >
		            		<div class="query-tt fn-left ml-10"><span>日期：</span></div>
		            		<@htmlmacro.datepicker msgName="时间"  maxDate="${(maxDate)?string('yyyy-MM-dd')}"  onpicked="stattisticList" id="startDate" value="${(startDate?string('yyyy-MM-dd'))?if_exists}"  maxlength="19"   style="width:140px;" dateFmt="yyyy-MM-dd"/>
		            		<div class="query-tt fn-left ml-10"><span>至</span></div>
		            		<@htmlmacro.datepicker msgName="时间"  maxDate="${(maxDate)?string('yyyy-MM-dd')}"  onpicked="stattisticList"  id="endDate" value="${(endDate?string('yyyy-MM-dd'))?if_exists}" maxlength="19"   style="width:140px;"  dateFmt="yyyy-MM-dd"/>
	            			 
	            			 <a href="javascript:void(0)" onclick="doExport();" class="abtn-blue fn-right ml-20">导出</a>
	            			 <div class="fn-clear"></div>
	            	</div>
            	</div>
            	<div  id="showList" class="mt-30">
					
            	</div>
            </div>
        </div>
        
    </div>
 



</@htmlmacro.moduleDiv>