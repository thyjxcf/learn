
<script>
function classHoliday() {
	var url = "${request.contextPath}/office/check/backSchoolAdmin-holidayList.action?pageIndex="+'${page.pageIndex?default(1)}'+"&pageSize="+'${page.pageSize?default(20)}';
    load("#containerDiv", url);
}
function classStuBackList() {
	var url = "${request.contextPath}/office/check/backSchoolAdmin-head.action";
    load("#containerDiv", url);
}
classHoliday();

</script>
<div class="pub-tab" id="test">
	<ul class="pub-tab-list">
	<li <#if actionName?default('')=="backSchoolAdmin"> class="current" </#if> onclick="javascript:classHoliday()">节假日设置</li>
	<li  <#if actionName?default('')=="backSchoolAdmin-head"> class="current" </#if> onclick="javascript:classStuBackList()">学生返校查询</li>
	</ul>
</div>
<div id="containerDiv"></div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>