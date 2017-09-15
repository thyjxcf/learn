
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/layout.css"/>
<#if loginInfo.user.ownerType ==3>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/green.css"/>
<#elseif loginInfo.user.ownerType==1>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/yellow.css"/>
<#else>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/default.css"/>
</#if> 

<script>
_contextPath = "${request.contextPath}";
</script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jscroll.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript-chkRadio.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/jwindow.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/js/LodopFuncs.js"></script>

<input type="hidden" name="unitid" id="schoolId" value="${schoolId?default("")}">
	<!-- 
	<div class="t-center data-total"><p class="table-dt fb18">${unitName!}</p></div>
	-->
<div class="query-builder">
 	<div class="query-part"  >
			    <a href="#" class="abtn-blue " style="" onclick="getInfo('kindergartenInfo')" >幼儿园信息</a>
			    <a href="#" class="abtn-blue " style="" onclick="getInfo('teacherInfo')" >教师信息</a>
			    <a href="#" class="abtn-blue " style="" onclick="getInfo('enjoybenefits')" >享受补助</a>
			    <a href="#" class="abtn-blue " style="" onclick="getInfo('paySocialSecurityBenefits')" >缴纳社保补助</a>
			    <a href="#" class="abtn-blue " style="" onclick="getInfo('supportStaffBenefits')" >后勤人员补助</a>
	</div>
</div>
    <iframe id="leftframe" name="leftframe" class="dtreeCFrame" marginwidth=0 allowTransparency="true"
		              marginheight=0 src="" frameborder=="0"
		              frameborder=0 width="100%" height="100%"  SCROLLING = "yes" >
		              </iframe>
<script>

function getInfo(data ){
		var schoolId = document.getElementById("schoolId").value;
		//window.location.href = '${request.contextPath}/office/dataAnalysis/edu/eduDataAnalysis-getSchoolDataInfo.action?shName='+data+"&schoolId="+schoolId;
		leftframe.document.location.href = '${request.contextPath}/office/dataAnalysis/edu/eduDataAnalysis-getSchoolDataInfo.action?shName='+data+"&schoolId="+schoolId;
}
</script>