<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutList.action");
});

function myGoOut(){
	var states = $("#states").val();
	if(states==''||states==null){
		states="";
	}
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutList.action?states="+states);
}

function myGoOut2(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-myGoOutList.action");
}


function jtGoOut(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-jtGoOut.action");
}

function goOutSearch(){
	var states = $("#states").val();
	if(states==''||states==null){
		states="0";
	}
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-goOutList.action?states="+states);
}
function goOutSearch2(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-goOutList.action?states="+0);
}
function goOutStatistics(){
	load("#goOutDiv", "${request.contextPath}/office/goout/goout-statistics.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="myGoOut2();">我的外出</li>
		<#--<#if loginInfo.unitClass==2&&schoolMaster>
		<li onclick="jtGoOut();">集体外出</li>
		</#if>-->
		<li onclick="goOutSearch2();">外出审核</li>
		<li onclick="goOutStatistics();">外出统计</li>
	</ul>
</div>
<div id="goOutDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>