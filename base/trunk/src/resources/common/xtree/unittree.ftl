<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite />
<script>
function treeItemClick(id,type,name){
  	if(parent.document.getElementById('unitId')){
  		parent.document.getElementById('unitId').value=id;  		
  	}
  	if(parent.document.getElementById('unitName')){
  		parent.document.getElementById('unitName').value=name;
  	}
  	if(parent.document.getElementById('choose_unitName')){
  		parent.document.getElementById('choose_unitName').value=name;
  	}
  	if(parent.document.getElementById('choose_unitId')){
  		parent.document.getElementById('choose_unitId').value=id;
  	}
  	
  	if(parent.document.getElementById('choose_unitClass')){
  		parent.document.getElementById('choose_unitClass').value=type;
  	}
  	
  	//调用父页面的回调接口
  	if(parent.treeItemClick){
		parent.treeItemClick(id,type,name);
  	}
}
</script>
</head>
<body class="dtree-bg">
<@treelib.tree />
</body>
</html>