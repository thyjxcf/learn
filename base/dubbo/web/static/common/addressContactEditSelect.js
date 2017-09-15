var userSelection = new Array();
var allUserSelection = new Array();

function selectUser(id,name,unit,phone,flag){
	var obj = document.getElementById(id);
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
			var param=new paramObject(id,name,unit,phone,flag);
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

function selectAllUser(){
	var chk = document.getElementById("allSelect").checked;
	for (var index = 0, len = allUserSelection.length; index < len; ++index) {
		var item = allUserSelection[index];
		document.getElementById(item.id).checked = chk;
		selectUser(item.id, item.name, item.unit, item.phone, item.flag);
	}
		
}

function setNameListHtml(userSelection){
	var nameStr="";
	nameStr=nameStr+"<tr><td colspan='5' class='data-total'>已选中联系人： "+userSelection.length+" 人</td></tr>";
	nameStr=nameStr+"<tr><th width='2%'></th><th width='20%'>姓名</th><th width='38%'>单位</th><th width='30%'>手机</th><th width='10%'></th></tr>";
	for (var index = 0, len = userSelection.length; index < len; ++index) {
		nameStr=nameStr+"<tr>";
		var item = userSelection[index];
		nameStr=nameStr+"<td></td><td>"+item.name+"</td>"+"<td>"+item.unit+"</td>"+"<td>"+item.phone+"</td>";
		nameStr=nameStr+"<td><a href=\"javascript:void(0);\" onclick=\"deleteObject('"+item.id+"');\"><img src="+_contextPath+"/static/images/icon/del5.png></a></td>";
		nameStr=nameStr+"</tr>";
	}
	$("#contactNameList").html(nameStr);
	return false;
}

function setAllNameListHtml(allUserSelection){
	var nameStr="";
	nameStr=nameStr+"<tr><th width='10%' class='t-center'><p><span><input type='checkbox' class='chk' id='allSelect' onclick=\"selectAllUser();\"></span></p></th><th width='20%'>姓名</th><th width='40%'>单位</th><th width='30%'>手机</th></tr>";
	for (var index = 0, len = allUserSelection.length; index < len; ++index) {
		var item = allUserSelection[index];
		nameStr=nameStr+"<tr>";
		if(item.flag == "true"){
			nameStr=nameStr+"<td class='t-center'><p><span><input type='checkbox' class='chk' checked='checked' id='"+item.id+"' name='input_userIds' onclick=\"selectUser('"+item.id+"', '"+item.name+"', '"+item.unit+"', '"+item.phone+"', '"+item.flag+"');\" value='"+item.id+"' txt='"+item.name+"'></span></p></td>";
		}else{
			nameStr=nameStr+"<td class='t-center'><p><span><input type='checkbox' class='chk' id='"+item.id+"' name='input_userIds' onclick=\"selectUser('"+item.id+"', '"+item.name+"', '"+item.unit+"', '"+item.phone+"', '"+item.flag+"');\" value='"+item.id+"' txt='"+item.name+"'></span></p></td>";
		}
		nameStr=nameStr+"<td>"+item.name+"</td>";
		nameStr=nameStr+"<td>"+item.unit+"</td>";
		nameStr=nameStr+"<td>"+item.phone+"</td>";
		nameStr=nameStr+"</tr>";
	}
	$("#allContactNameList").html(nameStr);
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
	var newArr = new Array();
	for(var index=0,len=allUserSelection.length;index<len;index++){
		var item = allUserSelection[index];
		var flag = "false";
		for(var i=0,l=userSelection.length;i<l;i++){
			var selectItem = userSelection[i];
			if(item.id==selectItem.id){
				flag = "true";
				break;
			}
		}
		newArr.push(new paramObject(item.id,item.name,item.unit,item.phone,flag));
	}
	setAllNameListHtml(newArr);
}

function clearAllInfo(){
	userSelection = new Array();
	setNameListHtml(userSelection);
	var newArr = new Array();
	for(var index=0,len=allUserSelection.length;index<len;index++){
		var item = allUserSelection[index];
		newArr.push(new paramObject(item.id,item.name,item.unit,item.phone,"false"));
	}
	setAllNameListHtml(newArr);
}

//传递给父页面的参数类
function paramObject(id,name,unit,phone,flag) {
	this.id=id;
	this.name=name;
	this.unit=unit;
	this.phone=phone;
	this.flag=flag;
}