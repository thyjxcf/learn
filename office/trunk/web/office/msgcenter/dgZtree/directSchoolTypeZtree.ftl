<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>用户组ztree</title>
<script type="text/javascript" src="${request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="${request.contextPath}/static/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${request.contextPath}/static/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/static/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<SCRIPT type="text/javascript">
	var setting = {
		check:{
			enable: false,
			chkStyle: "checkbox"
		},
		data:{
			simpleData: {
				idKey: "id",
				pIdKey: "parentId",
				enable: true
			}
		},
		callback: {
			onClick:zTreeOnClick,//节点点击
			onCheck:zTreeOnCheck,//选中复选框
			onDblClick:zTreeOnDblClick//双击节点
		}
	};
	
	//点击树节点
	function zTreeOnClick(event, treeId, treeNode) {
		 //相关页面需实现该方法
		var cascade = $("#cascade").attr("checked");
	 	//treeItemClick(treeNode.id,treeNode.name, treeNode, cascade)
	};
	
	//选择树节点复选框
	function zTreeOnCheck(event, treeId, treeNode) {
		//相关页面需实现该方法
		var cascade = $("#cascade").attr("checked");
		treeItemCheck(treeNode,cascade);
	    //treeItemCheck(treeNode.id,treeNode.name,treeNode.checked);
	};
	
	function treeItemCheck(treeNode,cascade){
		if(cascade){
			var zTree = $.fn.zTree.getZTreeObj("zTree");
			var childNodes = zTree.transformToArray(treeNode);
        	var nodes = new Array();
        	for(i = 0; i < childNodes.length; i++) {
            	setTreeNode(childNodes[i]);
        	}
		}else{
			setTreeNode(treeNode);
		}
	}
	
	function setTreeNode(treeNode){
		//没用的父节点，直接返回
		if(treeNode.type == 3){
			return;
		}
		if(treeNode.checked){
			var flag = true;
			for(var index=0,len=parent.userSelection.length;index<len;index++){
				var item = parent.userSelection[index];
				if(treeNode.id==item.id){
					flag = false;
					break;
				}
			}
			if(flag){
				var param=new parent.paramObject(treeNode.id,treeNode.detailName,treeNode.type);
				parent.userSelection.push(param);
			}
		}else{
			for(var index=0,len=parent.userSelection.length;index<len;index++){
				var item = parent.userSelection[index];
				if(treeNode.id==item.id){
					parent.userSelection.splice(index,1);
					break;
				}
			}
		}
		parent.setNameListHtml(parent.userSelection);
	}
	
	//双击树节点
	function zTreeOnDblClick(event, treeId, treeNode) {
		//相关页面需实现该方法
	    //treeItemDblClick(treeNode.id,treeNode.name);
	};
	
	//设置复选框选项
	function setCheckOption() {
		var zTree = $.fn.zTree.getZTreeObj("zTree");
		var cascade = $("#cascade").attr("checked")? "s":"";
		zTree.setting.check.chkboxType = {"Y":cascade, "N":cascade};
	}
	var lastTime;
	
	function searchNodeWithDelay(event) {
		//延时500毫秒加载
		setTimeout(function(){
			if(lastTime-event.timeStamp==0)
				searchNode();
		},500); 
	}
	
	//搜索节点/
	function searchNode(){
		var zTree = $.fn.zTree.getZTreeObj("zTree");
		var searchForName = $("#searchForName").val();
     	if (!searchForName || searchForName=="") {
     		init();
        	return;
      	}
		var nodeList = zTree.getNodesByParamFuzzy("name", searchForName);
		
		zTree.checkAllNodes(false);

	    var hiddenNodes = zTree.getNodesByParam("isHidden", true);
	    if (hiddenNodes) {
	    	zTree.showNodes(hiddenNodes);
	    }
      	
        var nodes = zTree.getNodesByFilter(function (node) {
        	if (node.name.indexOf(searchForName)==-1) {
            	return true;
       		}
          	return false;
      	});
      	zTree.hideNodes(nodes); //hide child node
      	
      	var noChildShowNodes = zTree.getNodesByFilter(function (node) {
      		if (node.isParent && (!node.children || node.children.length==0)) {
	        	return true;
	      	}
	      	if (node.isParent) {
	      		var showNodes=[];
	      		showNodes=checkNode(node,showNodes)
				if(showNodes.length > 0)
					return true;
			}
		    return false;
      	});
      	zTree.showNodes(noChildShowNodes); //hide no show child node
      	//展开所有的节点
      	zTree.expandAll(true);
	}	

	//根据父节点获取有显示的节点数组
	function checkNode(treeNode,showNodes){
        var childrenNodes = treeNode.children;
        if (childrenNodes) {
            for (var i = 0; i < childrenNodes.length; i++) {
                if(!childrenNodes[i].isHidden){
                	showNodes.push(childrenNodes[i]);
                }
                showNodes=checkNode(childrenNodes[i],showNodes);
            }
        }
        return showNodes;
	}
	//转化为json格式
	var zNodes=eval(${treeJsonCode!});
	
	function init(){
		//显示复选框
		setting.check.enable=true;
		$.fn.zTree.init($("#zTree"), setting, zNodes);
		//复选框 是否级联
		setCheckOption();
		$("#cascade").unbind("change").bind("change", setCheckOption);
		//搜索
		$("#searchForName").unbind("keyup").bind("keyup", function(event){
			lastTime = event.timeStamp;
			searchNodeWithDelay(event);
		});
	}
	
	$(document).ready(function(){
		init();
	});
	
	jQuery(document).keypress(function(event){
		if (event.which == '13') {
			// 在搜索框时回车会导致页面刷新
			event.preventDefault();
		}
	});
</SCRIPT>
<body>
<div class="search" style="white-space:nowrap;"><input type="text" style="width:80px;"  id="searchForName" placeholder="输入搜索值"><span style="font-size: 12px">&nbsp;是否级联:</span><input type="checkbox" id="cascade" class="checkbox first" checked /></div>
<div class="ztree-inner"><ul id="zTree" class="ztree"></ul></div>
</body>
</html>