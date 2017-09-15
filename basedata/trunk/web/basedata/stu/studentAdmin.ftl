<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="学生数据导入">
<script>
function setObjectName(objectName){
	var url ="${request.contextPath}/basedata/stu/studentImportAdmin-importMain.action?objectName="+objectName;
	load("#importDiv",url);
}
</script>
<body>
	<div id="importDiv"></div>
<script>
$(document).ready(function(){
	setObjectName("${objectName}");
});
</script>
</@htmlmacro.moduleDiv>