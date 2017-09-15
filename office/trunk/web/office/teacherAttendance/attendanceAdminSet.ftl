 <#import "/common/htmlcomponent.ftl" as htmlmacro>
 <#import "/common/commonmacro.ftl" as commonmacro>
 <#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
        <ul class="pub-two-tab">
            <li class="current" onclick="onClick1('1');">考勤地点设置</li>
            <li onclick="onClick1('2');">考勤时段设置</li>
            <li onclick="onClick1('3');">考勤组设置</li>
        </ul>
        <div id="showListDiv"></div>
    </div>
</div>
<script>
$(function(){
	onClick1("1");
});

function onClick1(type){
	if("1"==type){
		load("#showListDiv","${contextPath!}/office/teacherAttendance/teacherAttendance-place.action");
	}else if("2"==type){
		load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-setList.action");
	}else if("3"==type){
		load("#showListDiv","${request.contextPath}/office/teacherAttendance/teacherAttendance-groupMain.action");
	}
}
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>