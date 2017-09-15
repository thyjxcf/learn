var userSelection = new Array();

function initSelected(objectIds,objectDetails,userSelection){
	if(objectDetails == null || objectDetails == ""){
		userSelection.length = 0;
		setNameListHtml(userSelection);
		return;
	}
	var idArr = objectIds.split(",");
	var detailArr = objectDetails.split(",");
	var num = 0;
	if(objectIds && objectIds!=''){
		for (var index = 0, len = idArr.length; index < len; ++index) {
			var param=new paramObject(idArr[index],detailArr[num],5);
			userSelection.push(param);
			num++;
		}
	}
	setNameListHtml(userSelection);
}

function checkedUnit(id,detail,type){
	if($('#_'+id).attr("checked") == "checked"){
		$('#_'+id).attr("checked",false);
	}else{
		$('#_'+id).attr("checked",true);
	}
	var obj = document.getElementById("_"+id);
	selectUnit(obj,id,detail,type);
}

function selectUnit(obj,id,detail,type){
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
			var param=new paramObject(id,detail,type);
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
		nameStr += "<span id="+item.id+" type="+item.type+" detailName="+item.detail+">"+item.detail+"</span>";
	}
	$("#unitSpanDiv").html(nameStr);
	if(nameStr==""){
		document.getElementById("unitSpanDiv").style.height="18px";
	}else{
		document.getElementById("unitSpanDiv").style.height="";
	}
	setUnits();
	return false;
}

//根据显示的内容，初始化input隐藏域的值
function setUnits(){
	var unitIds = "";
	var detailNames = "";
	var i = 0;
	$('#unitSpanDiv span').each(function(fn){
		var spanId=$(this).attr('id');
		var type=$(this).attr('type');
		var spanDetailName=$(this).attr('detailName');
		if(unitIds == ""){
			unitIds += spanId;
			detailNames += spanDetailName;
		}else{
			unitIds += "," + spanId;
			detailNames += "," + spanDetailName;
		}
	});
	$("#unitIds").val(unitIds);
	$("#detailNames").val(detailNames);
}

//双击删除某一个已经被选择的对象
function deleteObject(id){
	for(var index=0,len=userSelection.length;index<len;index++){
		var item = userSelection[index];
		if(id==item.id){
			userSelection.splice(index,1);
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

function clearAllInfo(){
	userSelection = new Array();
	setNameListHtml(userSelection);
}

//传递给父页面的参数类
//5：单位
function paramObject(id,detail,type) {
	this.id=id;
	this.detail=detail;
	this.type=type;
}