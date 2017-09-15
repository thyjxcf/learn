<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>行政区选择窗口</title>
<link href="${request.contextPath}/static/common/fasttree/TreePanel.css" rel="stylesheet" type="text/css">
<script type=text/javascript src="${request.contextPath}/static/js/suggest/yahoo-dom-event.js"></script>
<script type=text/javascript src="${request.contextPath}/static/js/suggest/ks-core.js"></script>
<script type=text/javascript src="${request.contextPath}/static/js/suggest/suggest.js"></script>
<script type=text/javascript src="${request.contextPath}/static/common/fasttree/common-min.js"></script>
<script type=text/javascript src="${request.contextPath}/static/common/fasttree/TreePanel.js"></script>
<script type=text/javascript src="${request.contextPath}/static/js/cookie.js"></script>
</head>
<body>

<table height="100%" width="100%" border="0">
<tr height="20"><td align="middle">
<input type="text" id="search" value="" style="width:100px"/>
<span class="input-btn2" onclick="self.close();"><button type="button">取 消</button></span>&nbsp;
<span class="input-btn2" onclick="ok()"><button type="button">确 定</button></span>
</td></tr>
<tr><td>
<div id="tree-div" class="content_div"></div>
</td></tr>
</table>
</body>
<script type=text/javascript>
	var root = ${regionStr};
	var cookKey = "regionCode";
  	var regionCode;
  	var regionName;
  	
	//单击树触发事件,可以在构造函数中用handler传入或用tree.on(selectItem)来设置;
	
	tree = new TreePanel({
		renderTo:'tree-div',
		iconPath:'${request.contextPath}/static/common/fasttree/img/',
		'root' : root,
		handler : selectItem
	});
	
	tree.render();
	
	//根据cookies中的值定位上次选中项
	var prevRegionCode = getCookie(cookKey);
	if(prevRegionCode) {
		var preNode = tree.findChild('id',prevRegionCode);
		if(preNode){
			preNode.expandSelf();
			tree.setFocusNode(preNode);
			selectItem(preNode);
		}
	}
	

	//点击树调用的函数
	function selectItem(node){
		//为根目录时，返回空，相当于清空行政区框
		if(node.attributes.id == '000000'){
			regionName='';
			regionCode='';
			return;
		}
		regionCode = node.attributes.id;
		regionName = node.attributes.fulltext;
	}
	

	
  
  	function ok(){
		if (null == regionCode)	regionCode = "";
		if (null == regionName)	regionName = "未知";
		if(window.opener){
		    var rname=window.opener.document.getElementById("${valueField?default('regionname')}");
			var rcode=window.opener.document.getElementById("${codeField?default('region')}");
		 	rname.value=regionName;
		 	rcode.value=regionCode;
		 	
	 	}
		//24 * 60 * 60 * 1000表示一天
		var cookieSaveDate = new Date(now.getTime() + 24 * 60 * 60 * 1000);
	 	setCookie(cookKey,regionCode,cookieSaveDate);
	 	self.close();
  	}  	
  	
	//给树添加查找功能。
 KISSY.ready(function(S) {
		
		function filterData(inputValue){
			return tree.findChilds('text',inputValue);
		}

        var sug8 = new S.Suggest('#search', filterData,
		{ autoFocus: true,
            //resultFormat: '是%result%',
			containerWidth: '300px'
        });

		//把数据format成Suggest能识别的格式
        sug8.on('dataReturn', function() {
            var data = this.returnedData || [];
            var result = [];

            for (var i = 0, len = data.length; i < len; ++i) {
                result.push([data[i].attributes['text'], data[i].attributes['fulltext'],data[i].attributes['id']]);
            }

            this.returnedData = result;
        });

		//Suggest选中一个项目的时候触发该事件
		sug8.on('itemSelect',function(){
			var data =this.getSelectItemData("id");
			var treeNode = tree.findChild('id',data);
			tree.expand(treeNode);
			tree.setFocusNode(treeNode);
			selectItem(treeNode);
		});
});
</script>
</html>