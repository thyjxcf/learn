<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite checkbox=checkbox/>
<script>
    function treeItemClick(id,type,name,parentId,param5){
		//alert('注意：需要自己写方法覆盖 treeItemClick(id,type,name)\nid:'+id+'\nname:'+name+'\ntype:'+type+'\nparentId:'+parentId+'\nparam5:'+param5);
		parent.treeItemClick(id,type,name,parentId,param5);
    }  
</script>
</head>
<body class="dtree-bg">
<@treelib.tree />
</body>
<script>
<#if checkbox?default(false)>
	if(tree){
		tree.expandAll();
	}
</#if>
</script>
</html>