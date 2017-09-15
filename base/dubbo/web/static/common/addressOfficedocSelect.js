var userSelection_ = new Array();

function loadAddressBookOfficedocDetail(idObjectId,nameObjectId,detailObjectId,selectUserIds,height,url,params,callback,preset){
	if (preset != null && preset != "") {
		if(eval(preset+"()") == false){
			return false;
		}
	}
	var userIds = getObjectValue_(selectUserIds);
	if(userIds == null){
		userIds = "";
	}
	url=url+(url.indexOf('?') > -1 ? '&':'?')+"idObjectId="+idObjectId+"&nameObjectId="+nameObjectId+"&detailObjectId="+detailObjectId+"&userIds="+userIds+"&height="+height+"&callback="+callback;
	if(params != ""){
		url+="&"+params;
	}
	load('#addressBookOfficedocDetail',url);
}

function initSelected_(idObjectId,nameObjectId,detailObjectId,userSelection_){
	var objectIds = getObjectValue_(idObjectId);
	var objectNames = getObjectValue_(nameObjectId);
	var objectDetails = getObjectValue_(detailObjectId);
	if(objectIds == null || objectIds == ""){
		userSelection_.length = 0;
		setNameListHtml_(userSelection_);
		return;
	}
	var idArr = objectIds.split(",");
	var nameArr = objectNames.split(",");
	var detailArr = objectDetails.split(",");
	for (var index = 0, len = idArr.length; index < len; ++index) {
		var param=new paramObject_(idArr[index],nameArr[index],detailArr[index],3);
		userSelection_.push(param);
	}
	setNameListHtml_(userSelection_);
}

//needGroupId为true时调用

function selectUser_(obj,id,name,detail,type){
	if(obj.checked){
		//判断是否已经存在
		var flag = false;
		for(var index=0,len=userSelection_.length;index<len;index++){
			var item = userSelection_[index];
			if(id==item.id){
				flag = true;
				break;
			}
		}
		if(!flag){
			var param=new paramObject_(id,name,detail,type);
			userSelection_.push(param);
			setNameListHtml_(userSelection_);
		}
	}else{
		for(var index=0,len=userSelection_.length;index<len;index++){
			var item = userSelection_[index];
			if(id==item.id){
				userSelection_.splice(index,1);
				setNameListHtml_(userSelection_);
				break;
			}
		}
	}
}

function setNameListHtml_(userSelection_){
	var nameStr="";
	for (var index = 0, len = userSelection_.length; index < len; ++index) {
		var item = userSelection_[index];
		var detailName = item.detail;
		if(item.detail.length > 18){
			detailName = item.detail.substring(0,18);
		}
		if(item.type == 2){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/02.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject_('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject_('"+item.id+"');\"></i></li>";
		}else if(item.type == 3){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/03.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject_('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject_('"+item.id+"');\"></i></li>";
		}else if(item.type == 4){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/04.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject_('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject_('"+item.id+"');\"></i></li>";
		}else if(item.type == 5){
			nameStr=nameStr+"<li title="+item.detail+"><img src="+_contextPath+"/static/css/zTreeStyle/img/diy/05.png><a href=\"javascript:void(0);\" ondblclick=\"deleteObject_('"+item.id+"');\">"+detailName+"</a><i onclick=\"deleteObject_('"+item.id+"');\"></i></li>";
		}
	}
	$("#userNameList_").html(nameStr);
	$("#selectCount_").html(userSelection_.length);
	return false;
}

//双击删除某一个已经被选择的对象
function deleteObject_(id){
	for(var index=0,len=userSelection_.length;index<len;index++){
		var item = userSelection_[index];
		if(id==item.id){
			userSelection_.splice(index,1);
			setNameListHtml_(userSelection_);
			break;
		}
	}
}

function setObjectValue_(id, value){
	if(value == undefined) return;
	
	var obj = getMainWindowElementById_(id);
	if(obj){
		if(obj.tagName == 'INPUT' || obj.tagName == 'TEXTAREA'){
			obj.value = value;
		}else{
			$(obj).html(value);
		}
	}
}

function getObjectValue_(id){
	var obj = getMainWindowElementById_(id);
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

function getMainWindowElementById_(id){
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

function saveSelected_(idObjectId,nameObjectId,detailObjectId,callback){
	var idObjectValue = "";
	var nameObjectValue = "";
	var detailObjectValue = "";
	for(var index=0,len=userSelection_.length;index<len;index++){
		var item = userSelection_[index];
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
	setObjectValue_(idObjectId, idObjectValue);
	setObjectValue_(nameObjectId, nameObjectValue);
	setObjectValue_(detailObjectId, detailObjectValue);
	userSelection_ = new Array();
	closeAddressBookLayerDiv_(callback);
}

function closeAddressBookLayerDiv_(callback) {
	  closeDiv('#addressBookLayer');
	  if (callback != null && callback != "") {
		  eval(callback+"()");
      }
}

function clearAllInfo_(){
	userSelection_ = new Array();
	setNameListHtml_(userSelection_);
}

//传递给父页面的参数类
//2:部门组，3：人员组，4：部门领导组，5：自定义组
function paramObject_(id,name,detail,type) {
	this.id=id;
	this.name=name;
	this.detail=detail;
	this.type=type;
}