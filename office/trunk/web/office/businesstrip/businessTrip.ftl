<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">
<script>
$(function(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripList.action");
});

function myBusinessTrip(){
	var states = $("#states").val();
	if(states==''||states==null){
		states="";
	}
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripList.action?states="+states);
}

function myBusinessTrip2(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-myBusinessTripList.action");
}

function BusinessTripSearch(){
	var states = $("#states").val();
	if(states==''||states==null){
		states="0";
	}
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-businessTripList.action?states="+states);
}
function BusinessTripSearch2(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-businessTripList.action?states="+"0");
}
function BusinessTripQuery(){
	load("#businessTripeDiv", "${request.contextPath}/office/businesstrip/businessTrip-businessTripQueryList.action");
}
</script>
<div class="pub-tab mb-15">
	<ul class="pub-tab-list">
		<li class="current" onclick="myBusinessTrip2();">我的出差</li>
		<li onclick="BusinessTripSearch2();">出差审核</li>
		<#if businessTripQuery>
		<li onclick="BusinessTripQuery();">出差查询</li>
		</#if>
	</ul>
</div>
<div id="businessTripeDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>