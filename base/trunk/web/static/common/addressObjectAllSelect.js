var userSelectionAll = new Array();
var selectReady = true;

function loadAddressBookAllDetail(userObjectId,deptObjectId,unitObjectId,detailObjectId,height,url,params,callback,preset){
	if (preset != null && preset != "") {
		if(eval(preset+"()") == false){
			return false;
		}
	}
	url=url+(url.indexOf('?') > -1 ? '&':'?')+"userObjectId="+userObjectId+"&deptObjectId="+deptObjectId+"&unitObjectId="+unitObjectId+"&detailObjectId="+detailObjectId+"&height="+height+"&callback="+callback;
	if(params != ""){
		url+="&"+params;
	} 	
	load('#addressBookDetailAll',url);
	
}

function initAllSelected(userObjectId,deptObjectId,unitObjectId,detailObjectId,userSelectionAll){
	var objectUserIds = getObjectValue(userObjectId);
	var objectDeptIds = getObjectValue(deptObjectId);
	var objectUnitIds = getObjectValue(unitObjectId);
	var objectDetails = getObjectValue(detailObjectId);
	if(objectDetails == null || objectDetails == ""){
		userSelectionAll.length = 0;
		setNameListHtml(userSelectionAll);
		return;
	}
	var userIdArr = objectUserIds.split(",");
	var deptIdArr = objectDeptIds.split(",");
	var unitIdArr = objectUnitIds.split(",");
	var detailArr = objectDetails.split(",");
	var num = 0;
	//1单位，2部门，3人员
	if(objectUnitIds && objectUnitIds!=''){
		for (var index = 0, len = unitIdArr.length; index < len; ++index) {
			var param=new paramObjectAll(unitIdArr[index],detailArr[num],1);
			userSelectionAll.push(param);
			num++;
		}
	}
	if(objectDeptIds && objectDeptIds!=''){
		for (var index = 0, len = deptIdArr.length; index < len; ++index) {
			var param=new paramObjectAll(deptIdArr[index],detailArr[num],2);
			userSelectionAll.push(param);
			num++;
		}
	}
	if(objectUserIds && objectUserIds!=''){
		for (var index = 0, len = userIdArr.length; index < len; ++index) {
			var param=new paramObjectAll(userIdArr[index],detailArr[num],3);
			userSelectionAll.push(param);
			num++;
		}
	}
	setNameListHtml(userSelectionAll);
}

function selectUser(obj,id,detail,type){
	if(obj.checked){
		//判断是否已经存在
		var flag = false;
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(id==item.id){
				flag = true;
				break;
			}
		}
		if(!flag){
			var param=new paramObjectAll(id,detail,type);
			userSelectionAll.push(param);
			setNameListHtml(userSelectionAll);
		}
	}else{
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(id==item.id){
				userSelectionAll.splice(index,1);
				setNameListHtml(userSelectionAll);
				break;
			}
		}
	}
}

function setTreeNode(treeNode){
	if(treeNode.checked){
		var flag = true;
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(treeNode.id==item.id){
				flag = false;
				break;
			}
		}
		if(flag){
			var param=new paramObjectAll(treeNode.id,treeNode.name,1);
			userSelectionAll.push(param);
		}
	}else{
		for(var index=0,len=userSelectionAll.length;index<len;index++){
			var item = userSelectionAll[index];
			if(treeNode.id==item.id){
				userSelectionAll.splice(index,1);
				break;
			}
		}
	}
}

function removeTreeNode(id){
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(id==item.id){
			userSelectionAll.splice(index,1);
			setNameListHtml(userSelectionAll);
			break;
		}
	}
	var treeObj = $.fn.zTree.getZTreeObj("zTree");
	var node = treeObj.getNodeByParam("id", id, null);
	treeObj.checkNode(node, false, false, false);
}

function setTreeNodeChecked(){
	var treeObj = $.fn.zTree.getZTreeObj("zTree");
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(item.type == 1){
			var node = treeObj.getNodeByParam("id", item.id, null);
			treeObj.checkNode(node, true, false, false);
		}
	}
}

function setNameListHtml(userSelectionAll){
	selectReady = false;
	var nameStr="";
	for (var index = 0, len = userSelectionAll.length; index < len; ++index) {
		var item = userSelectionAll[index];
		var detailName = item.detail;
		if(item.detail.length > 18){
			detailName = item.detail.substring(0,18);
		}
		if(item.type == 1){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/01.png><a href=\"javascript:void(0);\" ondblclick=\"removeTreeNode('"+item.id+"');\">"+detailName+"</a><i onclick=\"removeTreeNode('"+item.id+"');\"></i></li>";
		}else if(item.type == 2){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/02.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}else if(item.type == 3){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/03.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}
	}
	$("#userNameList").html(nameStr);
	$("#selectCount").html(userSelectionAll.length);
	selectReady = true;
	return false;
}

//双击删除某一个已经被选择的对象
function deleteObject(id){
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(id==item.id){
			userSelectionAll.splice(index,1);
			setNameListHtml(userSelectionAll);
			break;
		}
	}
}

function setObjectValue(id, value){
	if(value == undefined) return;
	
	var obj = getMainWindowElementById(id);
	if(obj){
		if(obj.tagName == 'INPUT' || obj.tagName == 'TEXTAREA'){
			obj.value = value;
		}else{
			$(obj).html(value);
		}
	}
}

function getObjectValue(id){
	var obj = getMainWindowElementById(id);
	if(obj){
		if(obj.tagName == 'INPUT' || obj.tagName == 'TEXTAREA'){
			return obj.value;
		}else{
			return $(obj).html();
		}
	}else{
		return "";
	}
}

function getMainWindowElementById(id){
	try{
		var obj = document.getElementById(id);
		if(null != obj){
			return obj;
		}
		
		obj = parent.document.getElementById(id);
		if(null != obj){
			return obj;
		}
		
		obj = parent.parent.document.getElementById(id);			
		if(null != obj){
			return obj;
		}
		return null;
		
	}catch(e){
		return null;
	}
}

function saveSelected(userObjectId,deptObjectId,unitObjectId,detailObjectId,callback){
	var userIdObjectValue = "";
	var deptIdObjectValue = "";
	var unitIdObjectValue = "";
	var detailObjectValue = "";
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(item.type == 1){
			if(unitIdObjectValue==''){
				unitIdObjectValue += item.id;
				detailObjectValue += item.detail;
			}else{
				unitIdObjectValue += "," + item.id;
				detailObjectValue += "," + item.detail;
			}
		}
	}
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(item.type == 2){
			if(deptIdObjectValue==''){
				deptIdObjectValue += item.id;
			}else{
				deptIdObjectValue += "," + item.id;
			}
			if(detailObjectValue==''){
				detailObjectValue += item.detail;
			}else{
				detailObjectValue += "," + item.detail;
			}
		}
	}
	for(var index=0,len=userSelectionAll.length;index<len;index++){
		var item = userSelectionAll[index];
		if(item.type == 3){
			if(userIdObjectValue==''){
				userIdObjectValue += item.id;
			}else{
				userIdObjectValue += "," + item.id;
			}
			if(detailObjectValue==''){
				detailObjectValue += item.detail;
			}else{
				detailObjectValue += "," + item.detail;
			}
		}
	}
	setObjectValue(userObjectId, userIdObjectValue);
	setObjectValue(deptObjectId, deptIdObjectValue);
	setObjectValue(unitObjectId, unitIdObjectValue);
	setObjectValue(detailObjectId, detailObjectValue);
	userSelectionAll = new Array();
	
//	closeDiv('#addressBookLayer');
	closeAddressBookLayerDiv(callback);
}

function closeAddressBookLayerDiv(callback) {
	  closeDiv('#addressBookLayer');
	  if (callback != null && callback != "") {
		  eval(callback+"()");
      }
}

function clearAllInfo(){
	userSelectionAll = new Array();
	setNameListHtml(userSelectionAll);
}

//传递给父页面的参数类
//type:1单位2部门3人员
function paramObjectAll(id,detail,type) {
	this.id=id;
	this.detail=detail;
	this.type=type;
}