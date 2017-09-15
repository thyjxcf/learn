<div id="importDiv">
<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script language="javascript">
var oInterval = "";
var replyId = "";
var imported = false;

function dataimport() {
	if(imported){
		return;
	}
	imported = true;
	var content = $("#uploadFilePath").html();
	if(content.length==0) {
		showMsgWarn("请先选择导入的数据文件！");
		imported = false;
		return false;
	}
	var options = {
		url:"${request.contextPath}/office/dataAnalysis/dataAnalysis-importDataAnalysis.action?"+jQuery("#form1").serialize(),
		type:"post",
		async:false,
		clearForm : false,
	    resetForm : false,
		dataType:"json",
		success:function(data){
			if(data.operateSuccess){
				showMsgSuccess("导入成功！","" ,function(){
				$("#downLoadFile").show();
				$("#fileName").text(data.promptMessage);
				});
				leftframe.document.location.href = "${request.contextPath}/office/dataAnalysis/dataAnalysis-getSchoolStatistics.action?schoolId=${schoolId!}";
			}else{
				showMsgError(data.errorMessage);
			}
			imported=false;
		}
	}

	 try{
		 $('#form1').ajaxSubmit(options);
		 }catch(e){
		 	showMsgError('上报失败！');
		 	imported =false;
		 }

}



function fnStartInterval(){
	$("#import_panelWindow_tip").html("<b>导入处理：</b>"+"<p class='c-blue'>==>开始导入数据，请等待······</p>");
	oInterval = window.setTimeout("fnRecycle()",500);
}


function drawReplyMsg(result){
	var oTd = $("#import_panelWindow_tip");
	oTd.html("<b>导入处理：</b><br>"+"<p class='c-blue'>==>开始导入数据，请等待······</p>");
	if (result.actionMessages && result.actionMessages.length > 0) {
		for(var i = 0; i < result.actionMessages.length; i ++){
			oTd.html(oTd.html() + "<p class='c-blue'>" + result.actionMessages[i] + "</p>");
		}
    }
    if (result.actionErrors && result.actionErrors.length > 0) {
    	for(var i = 0; i < result.actionErrors.length; i ++){
    		oTd.html(oTd.html() + "<p class='c-blue'>" + result.actionErrors[i] + "</p>");
		}
     }
}


</script>


<form  name="form1" id="form1" target="hiddenIframe" action="" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="unitid" value="${unitid?default("")}">
<input type="hidden" name="replyCacheId" id="replyCacheId" value="${replyCacheId?default("")}" >
<div class="query-builder">

  <div class="query-part">
	<!--input、a、span是内链元素，同行对齐不需要左浮动和有浮动-->
	    <span>文件导入：</span>
	    <a href="#" class="acc-link upfile-name" style="display:none;" id="uploadFilePath"></a>
	    <span class="upload-span"><a href="#" class="abtn-blue upfile-btn">选择文件</a></span>       
	    <input style="display:none" id="uploadfile" name="uploadfile" hidefocus type="file" onchange="uploadFile();" >
	    </div>
		
		<div id="downLoadFile"  >
		
		    <div class="query-part" >
		    <span>已导入文件：<span id="fileName">${officeDataReportAnalysis.filename!}</span></span>
		   </div>

	  		<div class="query-part"  >
	
		    <a href="#" class="abtn-blue " style="" onclick="getInfo('kindergartenInfo')" >幼儿园信息</a>
		    <a href="#" class="abtn-blue " style="" onclick="getInfo('teacherInfo')" >教师信息</a>
		    <a href="#" class="abtn-blue " style="" onclick="getInfo('enjoybenefits')" >享受补助</a>
		    <a href="#" class="abtn-blue " style="" onclick="getInfo('paySocialSecurityBenefits')" >缴纳社保补助</a>
		     <a href="#" class="abtn-blue " style="" onclick="getInfo('supportStaffBenefits')" >后勤人员补助</a>
		   </div>
		</div>
		   <div class="query-part"  >
		    <a href="#" class="abtn-blue " style="" onclick="downloadFile()" >下载模版</a>
		    <p class="mt-20"><b>导入文件格式说明：</b></p>
            <p>1、导入文件只支持Excel文件(*.xls)<br /></p>
		    <p class="t-center pt-30">
		     <a href="javascript:void(0);" class="abtn-blue-big" onclick="dataimport();">开始导入</a>
		    </P>
		   </div>
	 
</div>
</form>
</div>
<p class="t-center pt-30">


    <a href="javascript:void(0);" class="abtn-gray-big" id="errorbtn" onclick="downloadErrorDatas();" style="display:<#if errorDataPath?default("") == "">none</#if>;">错误数据</a>
</p> 
    <iframe id="leftframe" name="leftframe" class="dtreeCFrame" marginwidth=0 allowTransparency="true"
		              marginheight=0 src="" frameborder=="0"
		              frameborder=0 width="100%" height="100%"  SCROLLING = "no" >
		              <style type="text/css">

                         </style>
		              </iframe>
<!-- 
<div class="import-explain">
	<p class="mt-20" id="import_panelWindow_tip"></p>
	
    <p class="mt-20"><b>导入文件格式说明：</b></p>
    <p>1、导入文件只支持Excel文件(*.xls)<br /></p>
</div>
-->
<script language="javascript">
$(function(){
	if($("#fileName").text() == ''){
		$("#downLoadFile").hide();
	}
	vselect();
	$('.upfile-name').click(function(){
		$('.upfile-btn').text('上传文件');
		$(this).hide();
		$("#uploadFilePath").html('');
		$("#uploadfile").val('');
		resetFilePos();
	});
	$(".upload-span").mouseover(function(){
		$("#uploadfile").offset({"top":$(".upload-span").offset().top });
	});
	resetFilePos();
})

function uploadFile(){
	$("#uploadFilePath").html($("#uploadfile").val());
	$('.upfile-name').show();
	$('.upfile-btn').text('重新上传');	
	resetFilePos();
}

function resetFilePos(){
	$("#uploadfile").css({"position":"absolute","-moz-opacity":"0","opacity":"0","filter":"alpha(opacity=0)","width":$(".upload-span a").width() + 27,"height":$(".upload-span").height(),"cursor":"pointer"});
	$("#uploadfile").offset({"left":$(".upload-span").offset().left});		
	$("#uploadfile").css({"display":""});
}
function downloadFile(){
	location.href="${request.contextPath}/office/dataAnalysis/dataAnalysis-downloadDataAnalysisFile.action";
}
function getInfo(data){
		window.open('${request.contextPath}/office/dataAnalysis/dataAnalysis-getInfomation.action?shName='+data);
}
jQuery(document).ready(function(){
		jQuery("#leftframe").height($("#container").height()-$("#importDiv").height() + 150);
		if($("#fileName").text() != ''){
		leftframe.document.location.href = "${request.contextPath}/office/dataAnalysis/dataAnalysis-getSchoolStatistics.action?schoolId=${schoolId!}";
		}
});
</script>

</@htmlmacro.moduleDiv >