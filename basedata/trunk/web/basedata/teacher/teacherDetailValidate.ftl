<#--教职工信息验证JS-->
<#macro teacherCommonVialidate>
function checkInvalidChar1(obj, msg){
	if(obj){
		var val = obj.value;
		if(val.indexOf("\·")!=-1){
			addFieldErrorWithObject(obj,msg+" 不能有·字符");
			return false;
		}
		return true;
	}else{
		return true;
	}
}
<#--数据验证-->
function formvalidate(){
	clearMessages();
	if(!checkAllValidate("#teacherEditDiv")){
		return false;
	}	
	if(!checkInvalidChar1(document.getElementById('teacher.pname'), '曾用名')){
		return false;
	}
	if(!checkInvalidChar1(document.getElementById('teacher.perNative'), '籍贯')){
		return false;
	}
	if(!checkInvalidChar1(document.getElementById('teacher.register'), '户口所在地')){
		return false;
	}
	if(!checkInvalidChar1(document.getElementById('teacher.fsschool'), '毕业院校')){
		return false;
	}
	if(!checkInvalidChar1(document.getElementById('teacher.remark'), '备注')){
		return false;
	}
	if(!checkInvalidChar1(document.getElementById('teacher.cardNumber'), '点到卡号')){
		return false;
	}
	if(!checkIdentityCard(document.getElementById('teacher.idcard'))){
		return false;
	}else if(!checkMobilePhone(document.getElementById('teacher.personTel'))){
		return false;
	}else if(!checkPhone(document.getElementById('teacher.officeTel'),'办公电话')){
		return false;
	}
	//if(!checkInteger(document.getElementById('teacher.displayOrder'),"排序号")){
	//	return false;
	//}
	<#nested>
	return true;
}

<#--合同时间判断
function checkContractDate(){
	var startDate = document.getElementById('contractStartDate');
	var endDate = document.getElementById('contractEndDate');
	if (!checkAfterDate(startDate, endDate)){
		return false;
	}
	
	var arr=["first","second","third","fourth"];
	for (var i = 0; i < arr.length; i++){
		startDate = document.getElementById(arr[i] + 'ContractStart');
		endDate = document.getElementById(arr[i] + 'ContractEnd');
		if (!checkAfterDate(startDate, endDate)){
			return false;
		}
	}
	return true;
}-->

<#--验证职务-->
function checkDuty(){
	var duty = document.getElementById('teacher.duty').value;
	if (duty != ''){
		if (duty.indexOf('999') >= 0){
			document.getElementById('otherDutySpan').style.display = "";		
		}else{
			if (document.getElementById('otherDutySpan').style.display != "none"){
				document.getElementById('teacher.otherDuty').value = "";
				document.getElementById('otherDutySpan').style.display = "none";
			}	
		}
	}else{
		if (document.getElementById('otherDutySpan').style.display != "none"){
			document.getElementById('teacher.otherDuty').value = "";
			document.getElementById('otherDutySpan').style.display = "none";
		}
	}
}

<#--下拉列表切换 显示其他隐藏域-->
function changeElement(eleId, hiddenSpanId, hiddenId, eleValue){
	var ele = document.getElementById(eleId).value;
	if (ele != ""){
		if (ele.indexOf(eleValue) >= 0){
			document.getElementById(hiddenSpanId).style.display = "";
		}else{
			if (document.getElementById(hiddenSpanId).style.display != "none"){
				document.getElementById(hiddenId).value = "";
				document.getElementById(hiddenSpanId).style.display = "none";
			}
		}
	}else{
		if (document.getElementById(hiddenSpanId).style.display != "none"){
			document.getElementById(hiddenId).value = "";
			document.getElementById(hiddenSpanId).style.display = "none";
		}
	}
}

<#--工作岗位切换-->

var jobLevelSpan = "jobLevelSpan" + "${teacher.job?default('')}";
var jobLevelSelect = "jobLevelSelect" + "${teacher.job?default('')}";
function changeJob(){
	var job = document.getElementById("teacher.job").value;
	if (job != ""){
		//if (job == "01") 
		document.getElementById("jobLevel").value="";
		if (document.getElementById(jobLevelSpan) && document.getElementById(jobLevelSpan).style.display != "none"){
			if (document.getElementById("otherJobLevelSpan").style.display != "none"){
				document.getElementById("teacher.otherJobLevel").value = "";
				document.getElementById("otherJobLevelSpan").style.display = "none";
			}
			if (document.getElementById(jobLevelSelect)) 
				document.getElementById(jobLevelSelect).selectedIndex = 0;
			document.getElementById(jobLevelSpan).style.display = "none";
		}
		jobLevelSpan = "jobLevelSpan" + job;
		jobLevelSelect = "jobLevelSelect" + job;
		
		document.getElementById(jobLevelSpan).style.display = "";
		if (document.getElementById(jobLevelSelect)){ 
			document.getElementById(jobLevelSelect).selectedIndex = 0;
		}
	}else{
		if (document.getElementById(jobLevelSpan) && document.getElementById(jobLevelSpan).style.display != "none"){
			if (document.getElementById("otherJobLevelSpan").style.display != "none"){
				document.getElementById("teacher.otherJobLevel").value = "";
				document.getElementById("otherJobLevelSpan").style.display = "none";
			}
			if (document.getElementById(jobLevelSelect)) document.getElementById(jobLevelSelect).selectedIndex = 0;
			document.getElementById(jobLevelSpan).style.display = "none";
		}
	}
}
<#--验证身份证-->
function checkoutIdcard(elem){
	if(elem.value == ""){
		return true;
	}
	var birth=checkoutBirthday(elem);
	if(birth){
		document.getElementById('teacher.birthday').value=birth;
	}else{
		return false;
	}
	var sex=checkoutSex(elem);
	if(sex){
		var sexselect=document.getElementById('teacher.sex');
		for(var i=0;i<sexselect.options.length;i++){
			if(sexselect.options[i].value==sex){
				sexselect.selectedIndex=i;
				return ;
			}
		}
	}else{
		return false;
	}
}
</#macro>
<#--检查图片格式-->
<#macro checkFileType>
function checkFileType(elem){
	if (elem.value.length > 0){
		var pos = elem.value.lastIndexOf(".")
		if (pos <0 || pos == elem.value.length - 1 ){
			addActionError("手写签名图片，系统只支持【GIF】、【BMP】、【JPG】、【PNG】及【JPEG】格式");
			return false;
		}
		else{
			var fileExt = elem.value.substring(pos + 1, elem.value.length);
			if (fileExt.toLowerCase() != "gif" && fileExt.toLowerCase() !="png" && fileExt.toLowerCase() != "bmp" && fileExt.toLowerCase() != "jpg" && fileExt.toLowerCase() != "jpeg"){
				addActionError("手写签名图片，系统只支持【GIF】、【BMP】、【JPG】、【PNG】及【JPEG】格式");
				return false;
			}
		}
	}
	return true;
}
</#macro>
<#--鼠标控制事件-->
<#macro mouseEvent>
var divobj;
function mousedown(obj)
{
	divobj = obj;
	obj.onmousemove = mousemove;
	obj.onmouseup = mouseup;
	
	oEvent = window.event ? window.event : event;
	x = oEvent.clientX;
	y = oEvent.clientY;
}
function mousemove()
{
	oEvent = window.event ? window.event : event;
	var _top = oEvent.clientY - y + "px";
	var _left = oEvent.clientX - x +"px";
	divobj.style.top = _top;
	divobj.style.left = _left;
	x = oEvent.clientX;
	y = oEvent.clientY
}
function mouseup(){
	divobj.onmousemove = null;
	divobj.onmouseup = null;
}
</#macro>