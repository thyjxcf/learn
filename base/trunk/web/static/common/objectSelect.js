
var objectWin;
function showObjects(curObj,idObjectId,nameObjectId,url,useCheckbox,params,callback,preset,dynamicParam,width,height){
	if (preset != null && preset != "") {
		if(eval(preset+"()") == false){
			return;
		}
	}
	
	//关闭原弹出框
	var popupObj;
//	var parentObj = $(curObj);
//	while((popupObj == null || popupObj.length==0) && parentObj[0].nodeName.toLowerCase() != 'body'){
//		popupObj = parentObj.parent('.popUp-layer');
//		parentObj = parentObj.parent();
//	}
//	if(null != popupObj && popupObj.length!=0){
//		popupObj.hide();
//	}
	
	var o = getMainWindowElementById(idObjectId);
	if(o){
		var objectIds=o.value;
	}
 	url=url+"?nameObjectId="+nameObjectId+"&idObjectId="+idObjectId+"&objectIds="+objectIds+"&useCheckbox="+useCheckbox+"&callback="+callback;
 	if(params != ""){
 		url+="&"+params;
 	} 	
 	
	if (dynamicParam != null && dynamicParam != "") {
		url+="&"+eval(dynamicParam+"()");
	}
	var ol = $("#_______overlayer").css("display"); 
	var haveLayer = 1;
	if(ol && ol != "none"){
		haveLayer = 1;
	}
	else{
		haveLayer = 0;
	}
	openDiv("#_panel-pulic-window", "#_panel-pulic-window .close,#_panel-pulic-window .submit,#_panel-pulic-window .reset", url, false, "#_panel-pulic-window", null,function(){vselect();}, function(){if(null != popupObj && popupObj.length!=0) popupObj.show();});
	$("#_______overlayer").css({"zIndex":(++divZIndex + 300)});
	$("#_panel-pulic-window").css({"zIndex":(++divZIndex + 300)});
}
//关闭时调用的方法
function closeObjectDivHandler(haveLayer){
	if (haveLayer == 1){
		jWindow.showOverlayer();
	}
	else{
		jWindow.removeOverlayer();
	}
	$("#_______overlayer").css({"zIndex":(++divZIndex)});
}

function closeObjectDivs(callback) {
	
  closeDiv();
  if (callback != null && callback != "") {
         callback();
     }
}

function onSelectAllDiv(allObj,idsName){
	if( allObj.checked){
		checkAllByStatus(idsName,'checked');
	}
	else{
		checkAllByStatus(idsName,'');
	}
}

function submitObjects(idObjectId,nameObjectId,ids,names,callback){
    var objectIds="";
    var objectNames = "";
    var ii=0;
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
		   ii=ii+1;
		   if(ii == 1){
		      objectIds += ids[i].value ;
		      objectNames+=names[i].value;
		   }else{
		      objectIds += ","+ids[i].value ;
		      objectNames+=","+names[i].value;
		   }
		}						
	}
	if(objectNames!=""){
	  	getMainWindowElementById(idObjectId).value = objectIds;
	  	getMainWindowElementById(nameObjectId).value = objectNames;
  	}
  	closeObjectDivs(callback);
}

function submitObject(idObjectId,nameObjectId,id,name,callback){
	getMainWindowElementById(idObjectId).value = id;
	getMainWindowElementById(nameObjectId).value = name;
	closeObjectDivs(callback);
}

function cancelObjects(idObjectId,nameObjectId,callback) {
	var o = getMainWindowElementById(idObjectId);
	if(o)
		o.value = "";
	o = getMainWindowElementById(nameObjectId);
	if(o)
		o.value = "";
  closeObjectDivs(callback);
}

function callbackObjects(callback,params) {
	return function(){
	  	if (window[callback] != undefined) {
	         window[callback](params);
	         return;
	  	  }
	  	  if (parent[callback] != undefined) {
	         parent[callback]();
	         return;
	  	  }
	  	  if (parent.parent[callback] != undefined) {
	         parent.parent[callback]();
	         return;
	  	  }
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

//传递给父页面的参数类
function paramObject(id,name) {
	this.id=id;
	this.name=name;
}
var selection=new Array();
//初始化
function initSelection(objectIds,objectNames){
	var idArr = objectIds.split(",");
	var nameArr = objectNames.split(",");
	for (var index = 0, len = idArr.length; index < len; ++index) {
		var param=new paramObject(idArr[index],nameArr[index]);
		selection.push(param);
	}
	setNameListHtml();
}

//设置对象名称列表的HTML内容
function setNameListHtml(){
	var idStr="";
	var nameStr="";
	for (var index = 0, len = selection.length; index < len; ++index) {
		var item = selection[index];
		if (index==0) {
			idStr = item.id;
			nameStr="<a href=\"javascript:void(0)\" ondblclick=\"deleteObject('"+item.id+"')\">"+item.name+"</a>"; 
		} else {
			idStr += ","+item.id;
			nameStr=nameStr+"&nbsp;&nbsp;"+"<a href=\"javascript:void(0)\" ondblclick=\"deleteObject('"+item.id+"')\">"+item.name+"</a>";
		}
	}
	var nameListSpan=$("nameList");
	nameListSpan.innerHTML=nameStr;
	
	document.getElementById("objectIds").value = idStr;
}

	
//双击删除某一个已经被选择的对象
function deleteObject(id){
	for(var index=0,len=selection.length;index<len;index++){
		var item = selection[index];
		if(id==item.id){
			selection.splice(index,1);
			setNameListHtml();
			setObjectUnchecked(id);
			mainframe.document.getElementById("selectAllDiv").checked=false;
			break;
		}
	}
}

//删除某个对象后设置对象列表中的该对象为unchecked
function setObjectUnchecked(id){
	var checkBoxes = mainframe.document.getElementsByName("idss");
	for(var i=0,len=checkBoxes.length;i<len;i++){
		if(id==checkBoxes[i].value){
			checkBoxes[i].checked=false;
			break;
		}
	}
}
//点击某个对象
function onSelection(id,isAdd){	
	var name = document.getElementById(id).value;
	var param=new paramObject(id,name);
	if(isAdd){
		var index = 0;
		for(len=selection.length;index<len;++index){
			var item = selection[index];
			if(item.id==param.id)
				break;
		}
		if(index>=selection.length)
			selection.push(param);
		setNameListHtml();
	}else{
		 deleteObject(id);
	}
}
//点击全选按钮
function onSelectAllClick(checked,idsName){
	if(checked){
		selectAll(idsName);
	}else{
		clearAllOnObjectList(idsName);
	}
}

//选择所有
function selectAll(idsName){
	var checkBoxes = mainframe.document.getElementsByName(idsName);
	checkBoxes = Array.from(checkBoxes);
	checkBoxes.each(function iterate(value,index){
		if(!value.checked){
			value.checked=true;
			onSelection(value.value,true);
		}	
	});
}

//清空页面中对象列表中的所有
function clearAllOnObjectList(idsName){ 
	var checkBoxes = mainframe.document.getElementsByName(idsName);
	checkBoxes = Array.from(checkBoxes);
	checkBoxes.each(function iterate(value,index){
		if(value.checked){
			deleteObject(value.value);
		}
	});
}
//清空所有
function clearAll(){
	for(var index=selection.length-1;index>=0;--index){
		deleteObject(selection[index].id);
	}
}
//确定
function submitObjects2(idObjectId,nameObjectId,selection,callback){
	var objectIds="";
    var objectNames = "";
	for(var i=0;i<selection.length;i++){
	   var item = selection[i];
	   if(i == 0){
	      objectIds += item.id ;
	      objectNames+=item.name;
	   }else{
	      objectIds += ","+item.id ;
	      objectNames+=","+item.name;
	   }
					
	}

  	getMainWindowElementById(idObjectId).value = objectIds;
  	getMainWindowElementById(nameObjectId).value = objectNames;

  	closeObjectDivs(callback);
}