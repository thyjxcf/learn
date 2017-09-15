<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="${webAppTitle}--用户角色">
<link href="${request.contextPath}/static/common/fasttree/TreePanel.css" rel="stylesheet" type="text/css">
<script type=text/javascript src="${request.contextPath}/static/common/fasttree/common-min.js"></script>
<script type=text/javascript src="${request.contextPath}/static/common/fasttree/TreePanel.js"></script>

<script type=text/javascript>
	var root = ${treeData};
	var cookKey = "regionCode";
  	var regionCode;
  	var regionName;

  	
function init(){
	tree = new TreePanel({
		renderTo:'tree-div',
		iconPath:'${request.contextPath}/static/common/fasttree/img/',
		'root' : root
	});
	tree.render();
	var treeNode = tree.findChild('id','A1');
	tree.expand(treeNode);
}
</script>
<p class="permission-tt">查看${user.name?default('')}用户权限 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a class="abtn-blue" id="expandAll" href="javascript:void(0);" onclick="tree.expandAll();">展开</a>
<a class="abtn-blue  ml-5" id="collapseAll"  href="javascript:void(0);" onclick="tree.collapseAll();">收缩</a>
</p>
<div class="permission-wrap" id="tree-div" style="overflow-y:auto;height:320px;">
</div>
<script>
init()
</script>
</@htmlmacro.moduleDiv>