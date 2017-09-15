var userSelection = new Array();

function loadAddressBookDetail(idObjectId,deptObjectId,deptLeaderObjectId,groupObjectId,nameObjectId,detailObjectId,height,currentUnit,url,params,changeHeight,callback,preset,needGroupId){
	if (preset != null && preset != "") {
		if(eval(preset+"()") == false){
			return false;
		}
	}
	url=url+(url.indexOf('?') > -1 ? '&':'?')+"idObjectId="+idObjectId+"&deptObjectId="+deptObjectId+"&deptLeaderObjectId="+deptLeaderObjectId+"&groupObjectId="+groupObjectId+"&nameObjectId="+nameObjectId+"&detailObjectId="+detailObjectId+"&height="+height+"&currentUnit="+currentUnit+"&changeHeight="+changeHeight+"&callback="+callback+"&needGroupId="+needGroupId;
	if(params != ""){
		url+="&"+params;
	} 	
	load('#addressBookDetail',url);
	
}

function initSelected(idObjectId,nameObjectId,detailObjectId,userSelection){
	var objectIds = getObjectValue(idObjectId);
	var objectNames = getObjectValue(nameObjectId);
	var objectDetails = getObjectValue(detailObjectId);
	if(objectIds == null || objectIds == ""){
		userSelection.length = 0;
		setNameListHtml(userSelection);
		return;
	}
	var idArr = objectIds.split(",");
	var nameArr = objectNames.split(",");
	var detailArr = objectDetails.split(",");
	for (var index = 0, len = idArr.length; index < len; ++index) {
		var param=new paramObject(idArr[index],nameArr[index],detailArr[index],3);
		userSelection.push(param);
	}
	setNameListHtml(userSelection);
}

//needGroupId为true时调用
function initSelectedNew(idObjectId,deptObjectId,deptLeaderObjectId,groupObjectId,nameObjectId,detailObjectId,userSelection){
	var objectIds = getObjectValue(idObjectId);
	var objectDepts = getObjectValue(deptObjectId);
	var objectDeptLeaders = getObjectValue(deptLeaderObjectId);
	var objectGroups = getObjectValue(groupObjectId);
	var objectNames = getObjectValue(nameObjectId);
	var objectDetails = getObjectValue(detailObjectId);
	if(objectDetails == null || objectDetails == ""){
		userSelection.length = 0;
		setNameListHtml(userSelection);
		return;
	}
	var idArr = objectIds.split(",");
	var deptArr = objectDepts.split(",");
	var deptLeaderArr = objectDeptLeaders.split(",");
	var groupArr = objectGroups.split(",");
	var nameArr = objectNames.split(",");
	var detailArr = objectDetails.split(",");
	var num = 0;
	if(objectIds && objectIds!=''){
		for (var index = 0, len = idArr.length; index < len; ++index) {
			var param=new paramObject(idArr[index],nameArr[num],detailArr[num],3);
			userSelection.push(param);
			num++;
		}
	}
	if(objectDepts && objectDepts!=''){
		for (var index = 0, len = deptArr.length; index < len; ++index) {
			var param=new paramObject(deptArr[index],nameArr[num],detailArr[num],2);
			userSelection.push(param);
			num++;
		}
	}
	if(objectDeptLeaders && objectDeptLeaders!=''){
		for (var index = 0, len = deptLeaderArr.length; index < len; ++index) {
			var param=new paramObject(deptLeaderArr[index],nameArr[num],detailArr[num],4);
			userSelection.push(param);
			num++;
		}
	}
	if(objectGroups && objectGroups!=''){
		for (var index = 0, len = groupArr.length; index < len; ++index) {
			var param=new paramObject(groupArr[index],nameArr[num],detailArr[num],5);
			userSelection.push(param);
			num++;
		}
	}
	setNameListHtml(userSelection);
}

function selectUser(obj,id,name,detail,type){
	if(obj.checked){
		//判断是否已经存在
		var flag = false;
		for(var index=0,len=userSelection.length;index<len;index++){
			var item = userSelection[index];
			if(id==item.id){
				flag = true;
				break;
			}
		}
		if(!flag){
			var param=new paramObject(id,name,detail,type);
			userSelection.push(param);
			setNameListHtml(userSelection);
		}
	}else{
		for(var index=0,len=userSelection.length;index<len;index++){
			var item = userSelection[index];
			if(id==item.id){
				userSelection.splice(index,1);
				setNameListHtml(userSelection);
				break;
			}
		}
	}
}

function setNameListHtml(userSelection){
	var nameStr="";
	for (var index = 0, len = userSelection.length; index < len; ++index) {
		var item = userSelection[index];
		var detailName = item.detail;
		if(item.type == 2){
			nameStr=nameStr+"<li><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/02.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}else if(item.type == 3){
			nameStr=nameStr+"<li><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/03.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}else if(item.type == 4){
			nameStr=nameStr+"<li><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/04.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}else if(item.type == 5){
			nameStr=nameStr+"<li><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/05.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject('"+item.id+"');\"></i></li>";
		}
	}
	$("#userNameList").html(nameStr);
	return false;
}

//双击删除某一个已经被选择的对象
function deleteObject(id){
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(id==item.id){
			userSelection.splice(index,1);
			setNameListHtml(userSelection);
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
}

function saveSelected(idObjectId,nameObjectId,detailObjectId,callback){
	var idObjectValue = "";
	var nameObjectValue = "";
	var detailObjectValue = "";
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(index==0){
			idObjectValue += item.id;
			nameObjectValue += item.name;
			detailObjectValue += item.detail;
		}else{
			idObjectValue += "," + item.id;
			nameObjectValue += "," + item.name;
			detailObjectValue += "," + item.detail;
		}
	}
	setObjectValue(idObjectId, idObjectValue);
	setObjectValue(nameObjectId, nameObjectValue);
	setObjectValue(detailObjectId, detailObjectValue);
	userSelection = new Array();
	
	closeAddressBookLayerDiv(callback);
}

function saveSelectedNew(idObjectId,deptObjectId,deptLeaderObjectId,groupObjectId,nameObjectId,detailObjectId,callback){
	var idObjectValue = "";
	var deptObjectValue = "";
	var deptLeaderObjectValue = "";
	var groupObjectValue = "";
	var nameObjectValue = "";
	var detailObjectValue = "";
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(item.type == 3){
			if(idObjectValue==''){
				idObjectValue += item.id;
				nameObjectValue += item.name;
				detailObjectValue += item.detail;
			}else{
				idObjectValue += "," + item.id;
				nameObjectValue += "," + item.name;
				detailObjectValue += "," + item.detail;
			}
		}
	}
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(item.type == 2){
			if(deptObjectValue==''){
				deptObjectValue += item.id;
				
			}else{
				deptObjectValue += "," + item.id;
			}
			if(nameObjectValue==''){
				nameObjectValue += item.name;
				detailObjectValue += item.detail;
			}else{
				nameObjectValue += "," + item.name;
				detailObjectValue += "," + item.detail;
			}
		}
	}
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(item.type == 4){
			if(deptLeaderObjectValue==''){
				deptLeaderObjectValue += item.id;
			}else{
				deptLeaderObjectValue += "," + item.id;
			}
			if(nameObjectValue==''){
				nameObjectValue += item.name;
				detailObjectValue += item.detail;
			}else{
				nameObjectValue += "," + item.name;
				detailObjectValue += "," + item.detail;
			}
		}
	}
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(item.type == 5){
			if(groupObjectValue==''){
				groupObjectValue += item.id;
			}else{
				groupObjectValue += "," + item.id;
			}
			if(nameObjectValue==''){
				nameObjectValue += item.name;
				detailObjectValue += item.detail;
			}else{
				nameObjectValue += "," + item.name;
				detailObjectValue += "," + item.detail;
			}
		}
	}
	setObjectValue(idObjectId, idObjectValue);
	setObjectValue(deptObjectId, deptObjectValue);
	setObjectValue(deptLeaderObjectId, deptLeaderObjectValue);
	setObjectValue(groupObjectId, groupObjectValue);
	setObjectValue(nameObjectId, nameObjectValue);
	setObjectValue(detailObjectId, detailObjectValue);
	userSelection = new Array();
	
	closeAddressBookLayerDiv(callback);
}

function closeAddressBookLayerDiv(callback) {
	  closeDiv('#addressBookLayer');
	  if (callback != null && callback != "") {
		  eval(callback+"()");
      }
}

function clearAllInfo(){
	userSelection = new Array();
	setNameListHtml(userSelection);
}

//传递给父页面的参数类
//2:部门组，3：人员组，4：部门领导组，5：自定义组
function paramObject(id,name,detail,type) {
	this.id=id;
	this.name=name;
	this.detail=detail;
	this.type=type;
}