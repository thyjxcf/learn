<#macro treecite checkbox=false>
<link type="text/css" rel="stylesheet" href="${request.contextPath}/static/common/xtree/xtree.css" />
<script language="javascript">
var prefix = "${request.contextPath}/static/common/xtree/";
</script>	
	<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xtree.js"></script>
	<#if checkbox>
		<script language="JavaScript" src="${request.contextPath}/static/common/xtree/webfxcheckboxtreeitem.js"></script>	
	<#else>
		<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xloadtree.js"></script>
		<script language="JavaScript" src="${request.contextPath}/static/common/xtree/xmlextras.js"></script>
	</#if>
</#macro>

<#macro tree>
	<script>
		${treeJSCode!}
		document.write(tree);
	</script>
</#macro>

<#macro treejs>
	<script>
		function treeExpandAll(){
			if(tree){
				tree.expandAll();
			}
		}
		function treeCollapseAll(){
			if(tree){
				tree.collapseAll();
				tree.expand();
			}
		}
	</script>
</#macro>

<#--
地区树
-->
<#macro regionTree  treeContainer="treeID">
<SCRIPT language="javascript">	
	var tree;
	var treebfl = new Buffalo("${request.contextPath}");
	function buildTreeRoot(){
		//点击根目录传进去的参数是空
		var action = "javascript:treeItemClick('0','');";
		var treeRoot = new WebFXTree("行政区域", action);
		//var treeRoot = new WebFXTree("行政区域", "#");
		treeRoot.icon = webFXTreeConfig.rootIcon;		
		//取消最后一次选择节点的状态，否则网页会报错
		//treeRoot.deSelected();
		return treeRoot;
	}
	
	function initTree(){			
		document.getElementById("${treeContainer}").innerHTML = buildTreeRoot().toString();
	}
	
	tree = buildTreeRoot();	
	document.getElementById("${treeContainer}").innerHTML = tree.toString();
	
	function buildTree(){
		treebfl.remoteActionCall("/common/xtree/regionprovince.action","getProvinces",[],function(reply){
			if(reply.isFault()){
				Buffalo.showError(reply.getResult());
				return;
			}
			var retList = reply.getResult();
			tree = null;
			tree = buildTreeRoot();
			for(i=0; i < retList.length;i++){
				treeItem = new WebFXLoadTreeItem(retList[i].regionName,
									"${request.contextPath}/common/xtree/regiontreexml.action?code="+retList[i].regionCode + "&namePre=" + retList[i].cname,
									"javascript:treeItemClick("+retList[i].regionCode+",tree.getSelected())");
				tree.add(treeItem);
			}
			
			document.getElementById("${treeContainer}").innerHTML = tree.toString();
		});	
	}
</SCRIPT>
</#macro>
