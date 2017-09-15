
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#assign studentType= stack.findValue("@net.zdsoft.eis.base.common.entity.User@STUDENT_LOGIN") >
<#assign teacherType= stack.findValue("@net.zdsoft.eis.base.common.entity.User@TEACHER_LOGIN") >
<@htmlmacro.moduleDiv titleName="学生教师列表">
<script>
function treeItemClick(id,name){
	jQuery.ajax({
		url: "${request.contextPath}/basedata/query/stuTeaQuery-schOrEdu.action?unitid="+id,
		type:"post",
		dataType:"JSON",
		async:false,
		success:function(data){
				if(data.operateSuccess){
					$("#unitid").val(id);
					doSearch();
				}else {
					$("#unitid").val("");
					$("#stuTeaList").empty();
				}
		}
	});
}

$(document).ready(function(){
	load("#ztreeDiv","${request.contextPath}/common/xtree/unitztree.action?useCheckbox=false"+"&unitId=${unitId?default('')}");
});

function doSearch(){
	var queryTchName=$("#queryTchName").val().replace(/\ +/g,"");
	var userType=$("#userType").val();
	var queryTchUserName=$("#queryTchUserName").val().replace(/\ +/g,"");
	var unitid=$("#unitid").val();
	if(unitid == ""){
		showMsgWarn("请先选择学校");
		return;
	}
	if(userType=="1"){		
		load("#stuTeaList","${request.contextPath}/basedata/query/stuTeaQuery-list.action?queryTchName="+queryTchName+"&userType="+userType+"&queryTchUserName="+queryTchUserName+"&unitid="+unitid);
	}
	if(userType=="2"){
		load("#stuTeaList","${request.contextPath}/basedata/query/stuTeaQuery-teacherList.action?queryTchName="+queryTchName+"&userType="+userType+"&queryTchUserName="+queryTchUserName+"&unitId="+unitid);		
	}
}

</script>	
<div class="pub-table-wrap">
    <div class="pub-table-inner">
        <div class="user-group fn-left">
            <div class="page-list page-list-scroll">
            	<div id="ztreeDiv" class="ztree">
                </div>
            </div>
        </div>
        
        <div class="tel-book"  style="width:81.5%;">
        		<div class="query-builder mt-10" style="height:30px;">
			    <div class="query-part">
			    		<input type="hidden" name="unitid" id="unitid" value="" >
			    		
			              <div class="query-tt ml-10">人员类别：</div>
					            <div class="ui-select-box fn-left" style="width:60px;">
					                <input type="text" class="ui-select-txt" value="" />
					                <input id="userType" name="userType" type="hidden" value="${userType!}" class="ui-select-value" />
					                <a class="ui-select-close"></a>
					                <div class="ui-option" myfunchange="doSearch" >
					                	<div class="a-wrap">
					                		<a val="${studentType!}" ><span>学生</span></a>
					                		<a val="${teacherType!}" ><span>教师</span></a>
					                    </div>
					                </div>
					            </div>
					            
					      <div class="query-tt ml-10">姓名：</div>
			    	   		<input id="queryTchName" name="queryTchName" onChange="" value="${queryTchName!}" class="input-txt fn-left" style="width:160px;" type="text">
					      <div class="query-tt ml-10">账号：</div>
			    	   		<input id="queryTchUserName" name="queryTchUserName" onChange="" value="${queryTchUserName!}" class="input-txt fn-left" style="width:160px;" type="text">
			              <a href="javascript:void(0);" class="abtn-blue fn-left ml-10" onclick="doSearch();">查询</a>
			        
			            <div class="fn-clear"></div>
			        </div>
			    </div>
        	<div id="stuTeaList" ></div>
        </div>
    </div>
</div>
<div class="fn-clear"></div>
</@htmlmacro.moduleDiv>