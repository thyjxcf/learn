<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#import "/common/xtree/treelib.ftl" as treelib>
</head>
<@treelib.treecite />
<script>
	var stuid = ""; //学生ID
	var classid = ""; //班级ID
    function treeItemClick(id,type,name,classId){
    	if(parent != self){
    		parent.treeItemClick(id,type,name,classId);
    	}
    }
	//取班级ID
	function getClassid(){
		return classid;
	}
	    
	//设置班级ID
	function setClassid(id){
		classid = id;
	}
	    
	//取学号
	function getStuid(){
		return stuid;
	}
	
	//设置学号
	function setStuid(id){
		stuid = id;
	}
</script>
<body class="dtree-bg">
<@treelib.tree />
</body>
</html>