// JavaScript Document
function Utility(){
}
// 检查某个页面复选框是否有被选中
// 适用环境 ---- 如某个页面的大工匹量删除, 在提交时需做是否有 CheckBox 被选中
// 参数 ---- 复选框对象
Utility.prototype.isCheckBoxSelect = function( checkbox ){
	if(checkbox){
		var selected = false;
		var _len = checkbox.length;
		for(var i = 0; i < _len; i++){
			if(checkbox[i].checked){
				selected = true;
				break;
			}
		}
		if(!selected && checkbox.checked)
			selected = true;
		return selected;
	}
}
/*
	设置所有复选框的选取状态
	checkbox --- 所操作的 checkbox 对象;
	checked --- true: 为选中;  false: 为不选中;
	return --- true: 操作成功;  false: 对象 checkbox 不存在
*/
Utility.prototype.selectAllCheckBox = function( checkbox , checked){
	if(checkbox){
		var _len = checkbox.length;
		for(var i = 0; i < _len; i++){
			if(!checkbox[i].disabled)
				checkbox[i].checked = checked;
		}
		if(!checkbox.length && !checkbox.disabled){
			checkbox.checked = checked;
		}
		return true;
	}else{
		return false;
	}
}

/**
 * 通常用于把列表页面checkbox已经选中的值，转换成带分隔符的字符串
 * @param checkboxObj 复选框对象
 * @param separator 分隔符，比如逗号
 */
Utility.prototype.CBValueToSpecifyStr = function(checkboxObj, separator){
	var str = "";
	if(checkboxObj){
		var _len = checkboxObj.length;
		if(_len > 1){
			for(var i = 0; i < _len; i++){
				if(checkboxObj[i].checked){
					str += checkboxObj[i].value + separator;
				}
			}
			str = str.substr(0, str.lastIndexOf(separator));
		}else if(checkboxObj.checked){
			str += checkboxObj.value;
		}
	}
	return str;
}
/*
		将 array 转换成特定的字符串;
	array --- 所操作的 array 数组对象, 应在存储字符的数组;
	separator --- 每一数组单元间的分隔符;
	return --- 返回字符串
*/
Utility.prototype.arrayToSpecifyStr = function( array, separator){
	var str = "";
	var _len = array.length-1;
	for(var i = 0; i < _len; i++){
		str += array[i] + separator;
	}
	str += array[_len];
	return str;
}
/*
		将 array 转换成特定的字符串;
	array --- 所操作的 array 数组对象, 应在存储字符的数组;
	separator --- 每一数组单元间的分隔符;
	return --- 返回字符串
*/
Utility.prototype.stringToArray = function( str, token){
	
	var result = new Array();
	return str;
}
/*
		将 array 转换成请求服务器的字符串;
	array --- 所操作的 array 数组对象, 应在存储字符的数组;
	reqName --- 请求时所使用的参数名;
	return --- 返回请求字符串
*/
Utility.prototype.arrayToRequestStr = function( array, reqName){
	if(!array || array.length == 0)
		return "";
	var str = reqName + "=" + array[0];
	var _len = array.length;
	for(var i = 1; i < _len; i++){
		if(array[i]){
			str += "&" + reqName + "=" + array[i];
		}
	}
	return str;
}
/*
		将 array 转换成表单的隐藏表单域;
	array --- 所操作的 array 数组对象, 应在存储字符的数组;
	form --- 请求的表单;
	hiddenName --- 隐藏表单域的名字;
	idName --- 隐藏表单域的ID值(可选);
*/
Utility.prototype.arrayToFormHidden = function(array, form, hiddenName, idName, docinput){
	var doc;
	if(docinput){
		doc = docinput;
	}else{
		doc = document;
	}
	var _len = array.length;
	for(var i = 0; i < _len; i++){
		if(array[i]){
			var input = doc.createElement('INPUT');
			input.type = 'hidden';
			input.id = idName;
			input.name = hiddenName;
			input.value = array[i];
			form.appendChild(input);
		}
	}
}
/*
		完成两个标准格式 (YYYY-MM-DD) 的日期大小判断
	dateOne --- 日期1;
	dateTwo --- 日期2;
	return ---  1: 若日期1 > 日期2;
				0: 若日期1 == 日期2;
			   -1: 若日期1 < 日期2;
*/
Utility.prototype.CompareDate = function(dateOne, dateTwo){
	if(null == dateOne || null == dateTwo)
		return NaN;
    var partsOne = dateOne.split('-');
    var partsTwo = dateTwo.split('-');

    if((partsOne.length != 3) || (partsTwo.length != 3)) {
        return NaN;
    }

    if ((parseInt(this.TrimString(partsOne[0],0), 10) - parseInt(this.TrimString(partsTwo[0],0), 10)) > 0) return 1;
    if ((parseInt(this.TrimString(partsOne[0],0), 10) - parseInt(this.TrimString(partsTwo[0],0), 10)) < 0) return -1;

    if ((parseInt(this.TrimString(partsOne[1],0), 10) - parseInt(this.TrimString(partsTwo[1],0), 10)) > 0) return 1;
    if ((parseInt(this.TrimString(partsOne[1],0), 10) - parseInt(this.TrimString(partsTwo[1],0), 10)) < 0) return -1;

    if ((parseInt(this.TrimString(partsOne[2],0), 10) - parseInt(this.TrimString(partsTwo[2],0), 10)) > 0) return 1;
    if ((parseInt(this.TrimString(partsOne[2],0), 10) - parseInt(this.TrimString(partsTwo[2],0), 10)) < 0) return -1;

    return 0;
}
/*
	根据要求，消除字符串头或尾的空格
	input --- 待处理的字符串
	type ---  处理类型： -1 -- 左边空格； 0 -- 两边空格；  1 -- 右边空格；
*/
Utility.prototype.TrimString = function(input, type){
	if(null == type || null == input){
		alert("TrimString(String, int) 参数不对");
		return null;
	}
	if(-1 == type){
		input = input.replace(/^\s+/g, "");
	}else if(1 == type){
		input = input.replace(/\s+$/g, "");
	}else if( 0 == type ){
		input = input.replace(/(^\s+)|(\s+$)/g, "");
//		input = input.replace(/^ */g, "");
//		input = input.replace(/ *$/g, "");
	}else{
		alert("输入的处理类型必须为：-1、0、1");
		return null;
	}
	return input;
}
/*
	将表单的数据转换成请求的字符串形式
	form --- 表单对象
*/
Utility.prototype.getReqparamFromForm = function(form){
	var reqparam = '';
	var _len = form.elements.length;
	for(var i = 0; i < _len; i ++){
		var child = form.elements[i];
		if(child && child.value != ""){
			if(child.tagName == 'INPUT') {
				var type = child.type.toLowerCase();
				if(type != 'checkbox' && type != 'radio'){
					reqparam += form.elements[i].name + "=" + form1.elements[i].value + "&";
				}else if(child.checked){
					reqparam += form.elements[i].name + "=" + form1.elements[i].value + "&";
				}
			}else{
				reqparam += form.elements[i].name + "=" + form1.elements[i].value + "&";
			}
		}
	}
	return reqparam.replace(/\&+$/g,'');
}
/*
	将表单的请求数据转换成 Map 形式的数组
	form --- 表单对象
*/
Utility.prototype.setFormToArrayMap = function(form){//, nameArray, valueArray){
	var formMap = new Array();
	var _len = form.elements.length;
	for(var i = 0; i < _len; i ++){
		var child = form.elements[i];
		if(child && child.value != ""){
			if(child.tagName == 'INPUT') {
				var type = child.type.toLowerCase();
				if(type != 'checkbox' && type != 'radio'){
					var map = new UtilityMap();
					map.key = form.elements[i].name;
					map.value = form1.elements[i].value;
					formMap[formMap.length] = map;
				}else if(child.checked){
					var map = new UtilityMap();
					map.key = form.elements[i].name;
					map.value = form1.elements[i].value;
					formMap[formMap.length] = map;
				}
			}else{
				var map = new UtilityMap();
				map.key = form.elements[i].name;
				map.value = form1.elements[i].value;
				formMap[formMap.length] = map;
			}
		}
	}
	return formMap;
}
function UtilityMap(){}
UtilityMap.prototype.key;
UtilityMap.prototype.value;

Utility.prototype.setArrayMapToForm = function(arrayMap, doc, form){//, nameArray, valueArray){
	var _len = arrayMap.length;
	for(var i = 0; i < _len; i++){
		var input = doc.createElement('INPUT');
		input.type = 'hidden';
		input.name = arrayMap[i].key;
		input.value = arrayMap[i].value;
		form.appendChild(input);
	}
	
}
Utility.prototype.abbreviate = function(str, length){
	if(str.length > length){
		return str.substring(0,length) + '...';
	}else{
		return str;
	}
}
Utility.prototype.escapeFileName = function(value){
	//var reg = /[^\u4e00-\u9fa5]/g;
	//return s.replace(reg, function($1){return escape($1)} );
    var reg = /\\/g;
    value = value.replace(reg, "＼");
    reg = /\//g;
    value = value.replace(reg, "／");
    reg = /\:/g;
    value = value.replace(reg, "：");
    reg = /\*/g;
    value = value.replace(reg, "＊");
    reg = /\?/g;
    value = value.replace(reg, "？");
    reg = /\"/g;
    value = value.replace(reg, "“");
    reg = /\</g;
    value = value.replace(reg, "＜");
    reg = /\>/g;
    value = value.replace(reg, "＞");
    reg = /\|/g;
    value = value.replace(reg, "｜");
    reg = /\'/g;
    value = value.replace(reg, "‘");
    reg = /%/g;
    value = value.replace(reg, "％");

    return value;
}
//黄小东专用
function myEscape(value) {
    //return value;
	//	replace %, &, /, ?, +,  , # 
	var re1 = /%/g;
	value = value.replace(re1, "%25");
	var re2 = /\&/g;
	value = value.replace(re2, "%26");
	var re4 = /\//g;
	value = value.replace(re4, "%2F");
	var re5 = /\?/g;
	value = value.replace(re5, "%3F");
	var re6 = /\+/g;
	value = value.replace(re6, "%2B");
	var re7 = / /g;
	value = value.replace(re7, "%20");
	var re8 = /#/g;
	value = value.replace(re8, "%23");
	return value;
}
function getRadioValue(field) {
    if (field.length) {
        for (var i = 0; i < field.length; i++) {
            if (field[i].checked) {
                return field[i].value;
            }
        }
    }
    else {
        if (field.checked) {
            return field.value;
        }
    }
    return null;
}

function getCheckboxValues(field, uncheckedValue) {
    var valueArray = new Array();
    if (field.length) {
        for (var i = 0; i < field.length; i++) {
            if (field[i].checked) {
                valueArray[i] = field[i].value;
            }
            else {
                valueArray[i] = uncheckedValue;
            }
        }
    }
    else {
        if (field.checked) {
            valueArray[0] = field.value;
        }
        else {
            valueArray[0] = uncheckedValue;
        }
    }
    return valueArray;
}

function getValueArray(fields) {
  var valueArray = new Array();
  
  if (!fields) {
    return valueArray;
  }

  if (!fields.length) {
    if (fields.value) {
      valueArray[0] = fields.value;
    }
    return valueArray;
  }

  if ("checkbox" == fields[0].type || "radio" == fields[0].type) {
    var index = 0;
    for (var i = 0; i < fields.length; i++) {
      if (fields[i].checked) {
        valueArray[index++] = fields[i].value;
      }
    }
  }
  else {
    for (var i = 0; i < fields.length; i++) {
      valueArray[i] = fields[i].value;
    }
  }
  return valueArray;
}

function parseDate(str) {
    var date = new Date();
    date.setTime(Date.parse(str.replace("-", "/")));
    return date;
}