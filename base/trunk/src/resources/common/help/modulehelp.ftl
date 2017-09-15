<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite />
<script>
function treeItemClick(action){
	parent.document.getElementById("help_btn1").style.display="block";
	parent.loadHelp(action);
}
    
function init(){
	var modArr = new Array();
	var cnt = 0;
	<#list searchModuleList as x>
		modArr[cnt] = "${x.name}";
		cnt++;
	</#list>	
	var index = 0;
	var text;
	<#if "" != subSystem?default("")>
		text = "${subSystem.name}";
	<#else>
		text = "${systemVersion.name}";
	</#if>	
	var node = tree.findNodeByText(text,0,true);
	if(node != null){
		node.expand();
		for(var i=modArr.length -1;i>=0;i--){
			var moduleName = modArr[i];	
			//目前只支持树结点文字连续相同的情况，如果结点1和结点2不同，结点3又和结点1同，则不能准确定位。		
			if(node.text == moduleName){
				index++;
			}else{
				index = 0;
			}
			node = node.findNodeByText(moduleName,index,true);
			if(node != null){
				if(node.childNodes.length > 0){
					node.expand();
				}
			}
		}
		if(null != node.action && "" != node.action){
			eval(node.action);			
		}
		node.select();		
	}
}
</script>
<body class="dtree-bg" onload="init();">
<@treelib.tree />
</body>
</html>