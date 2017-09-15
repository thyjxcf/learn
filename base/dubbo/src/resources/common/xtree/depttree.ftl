<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite />
<script>
  function treeItemClick(id,name,unitId){
  	if(parent.document.getElementById('choose_deptId')){
  		parent.document.getElementById('choose_deptId').value=id;
  	}
  	if(parent.document.getElementById('choose_deptName')){
  		parent.document.getElementById('choose_deptName').value=name;  		
  	}
  	//调用父页面的回调接口
  	if(parent.treeItemClick){
  		parent.treeItemClick(id,name,unitId);
  	}
  }
function init(){
	var id= "00000000000000000000000000000000";
	var unitId = "${unitId?default('00000000000000000000000000000000')}";
	treeItemClick(id,null,unitId);
	//tree.select();
}
function selectRefresh(){
	var curNode=tree.getSelected();
	if(curNode){		
		curNode.reload();
	}	
}
</script>
</head>
<body class="dtree-bg" onload="init();">
<@treelib.tree />
</body>
</html>