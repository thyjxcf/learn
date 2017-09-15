<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite />
<script language="javascript">
function treeItemClick(id,type,name,parentId){
	parent.treeItemClick(id,type,name,parentId);
}
</script>
<body class="dtree-bg">
<@treelib.tree />
</body>
</html>
