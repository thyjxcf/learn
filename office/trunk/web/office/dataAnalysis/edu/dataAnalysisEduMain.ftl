<form  name="form1" id="form1" target="hiddenIframe" action="" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" name="unitid" value="${unitid?default("")}">
<div class="query-builder">

  <div class="query-part">
	<!--input、a、span是内链元素，同行对齐不需要左浮动和有浮动-->
	  		<div class="query-part"  >
		    <a href="#" class="abtn-blue " style="" onclick="analysis();" >统计汇总</a>
		    <a href="#" id="analysisData" class="abtn-blue " style=""
		    	<#if statistics>
		    	<#else>
		    		style="display:none;"
		    	</#if>
		     onclick="showAnalysisResult();" >查看汇总信息</a>
		   </div>
   </div>
</div>
</form>
<div id="schoolListDiv">
	
</div>
<script>
	var flag=false;
	function analysis(){
		if(flag){
			return;
		}
		flag=true;
		jQuery.ajax({
			url:"${request.contextPath}/office/dataAnalysis/edu/eduDataAnalysis-dataAnalysis.action",
			async:false,
			type:"post",
			dataType:"json",
			success:function(data){
					if(data.operateSuccess){
						showMsgSuccess(data.promptMessage);
						$("#analysisData").show();
					}else{
						showMsgError(data.errorMessage);
					}
					flag=false;
			}
		});
	}
	
	function showAnalysisResult(){
		window.open('${request.contextPath}/office/dataAnalysis/edu/eduDataAnalysis-dataAnalysisShow.action');
	}
</script>