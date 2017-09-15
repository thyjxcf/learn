var userSelection = new Array();

function initSelected(objectIds,objectDepts,objectDetails,userSelection){
	if(objectDetails == null || objectDetails == ""){
		userSelection.length = 0;
		setNameListHtml(userSelection);
		return;
	}
	var idArr = objectIds.split(",");
	var deptArr = objectDepts.split(",");
	var detailArr = objectDetails.split(",");
	var num = 0;
	if(objectDepts && objectDepts!=''){
		for (var index = 0, len = deptArr.length; index < len; ++index) {
			var param=new paramObject(deptArr[index],detailArr[num],4);
			userSelection.push(param);
			num++;
		}
	}
	if(objectIds && objectIds!=''){
		for (var index = 0, len = idArr.length; index < len; ++index) {
			var param=new paramObject(idArr[index],detailArr[num],2);
			userSelection.push(param);
			num++;
		}
	}
	setNameListHtml(userSelection);
}

function checkedUser(id,detail,type){
	if($('#_'+id).attr("checked") == "checked"){
		$('#_'+id).attr("checked",false);
	}else{
		$('#_'+id).attr("checked",true);
	}
	var obj = document.getElementById("_"+id);
	selectUser(obj,id,detail,type);
}

function selectUser(obj,id,detail,type){
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
	$("#userSpanDiv").html(nameStr);
	if(nameStr==""){
		document.getElementById("userSpanDiv").style.height="18px";
	}else{
		document.getElementById("userSpanDiv").style.height="";
	}
	setUsers();
	resetFileInit();
	return false;
}

//根据显示的内容，初始化input隐藏域的值
function setUsers(){
	var userIds = "";
	var deptIds = "";
	var detailNames = "";
	
	var userNamesTemp = "";
	var deptNamesTemp = "";
	
	var i = 0;
	$('#userSpanDiv span').each(function(fn){
		var spanId=$(this).attr('id');
		var type=$(this).attr('type');
		var spanDetailName=$(this).attr('detailName');
		if(type==2){
			if(userIds == ""){
				userIds += spanId;
				userNamesTemp += spanDetailName;
			}else{
				userIds += "," + spanId;
				userNamesTemp += "," + spanDetailName;
			}
		}else if(type==4){
			if(deptIds == ""){
				deptIds += spanId;
				deptNamesTemp += spanDetailName;
			}else{
				deptIds += "," + spanId;
				deptNamesTemp += "," + spanDetailName;
			}
		}
	});
	$("#userIds").val(userIds);
	$("#deptIds").val(deptIds);
	
	//将用户名称，部门名称，单位名称拼接一起
	detailNames += deptNamesTemp;
	if(userNamesTemp != ""){
		if(detailNames != ""){
			detailNames += "," + userNamesTemp;
		}else{
			detailNames += userNamesTemp;
		}
	}
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
//2:用户，4：部门，5：单位
function paramObject(id,detail,type) {
	this.id=id;
	this.detail=detail;
	this.type=type;
}