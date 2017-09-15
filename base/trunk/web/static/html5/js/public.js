
//常量
var Constants = {
  // 用户id
  UNIT_ID : 'login_unit_id',
  USER_ID : 'login_user_id',
  OWNER_ID : 'login_owner_id',
  USER_REALNAME : 'login_user_realname',
  MOBILE_CONTEXT_PATH : 'mobile_context_path',
  SUCCESS : 1,
  FAILURE : 0,
  
  //上下文
  CONTEXTPATH: 'CONTEXTPATH',
  
  //通讯录参数
  ADDRESS_TYPE : 'ADDRESS_TYPE',//1所有单位,2本单位
  ADDRESS_SELECT_TYPE : 'ADDRESS_SELECT_TYPE',//1多选,2单选
  ADDRESS_SELECTED_USERIDS: 'ADDRESS_SELECTED_USERIDS',// 已选择人员ids字符串
  ADDRESS_SELECTED_USERNAMES: 'ADDRESS_SELECTED_USERNAMES',// 已选择人员names字符串
  ADDRESS_RETURN_FUNCTION: 'ADDRESS_RETURN_FUNCTION',// 通讯录回调--若通讯录用的ifream
  ADDRESS_RETURN_URL: 'ADDRESS_RETURN_URL',// 回调页面--若通讯录用的location.href
  
  ADDRESS_SELECTED_USERS: 'ADDRESS_SELECTED_USERS',// 已选择人员
  ADDRESS_UNIT_ID : 'ADDRESS_UNIT_ID',// 传入的单位id
  ADDRESS_TOP_UNIT_ID : 'ADDRESS_TOP_UNIT_ID',// 顶级单位id
  ADDRESS_PARENT_ID: 'ADDRESS_PARENT_ID',// 上级单位id
  ADDRESS_OTHER_UNIT_ID : 'ADDRESS_OTHER_UNIT_ID',// 传入的其它单位id
  
  //
  TYPE_1 : 1,
  TYPE_2 : 2,
  
  //单位分类
  UNIT_CLASS_EDU : 1, // 教育局
  UNIT_CLASS_SCHOOL : 2, // 学校
  
  //参数
  PATE_SIZE : 15,
  
  //审核参数
  APPLY_STATUS_0 : 0,
  APPLY_STATUS_1 : 1,//待提交
  APPLY_STATUS_2 : 2,//审核中
  APPLY_STATUS_3 : 3,//通过
  APPLY_STATUS_4 : 4,//未通过
  
};

var OfficeConstants = {
  OFFICE_SOURCE_TYPE : 'office_source_type',
  OFFICE_MODULE_AUTH : 'office_module_auth',
  OFFICE_MODULE_LIST : 'office_module_list',
  
  OFFICE_MODULE_AUTH_TRUE : 'true',
  OFFICE_MODULE_AUTH_FALSE : 'false',
  
  // 用户id
  SOURCE_TYPE_APP : '1',
  SOURCE_TYPE_HTML : '2',
}

//page
var WapPage = {
  curRowNum : -1,
  desc : false,
  id : "",
  maxPageIndex : 1,
  maxRowCount : 1,
  pageIndex : 1,
  pageSize : Constants.PATE_SIZE,
  sort : "",
  useCursor : false
};

/**
 * 取地址
 */
// 取地址参数
var UrlSearch = function() {
  var name, value;
  var str = location.href; // 取得整个地址栏
  var num = str.indexOf("?");
  str = str.substr(num + 1); // 取得所有参数
  var arr = str.split("&"); // 各个参数放到数组里
  for ( var i = 0; i < arr.length; i++) {
    num = arr[i].indexOf("=");
    if (num > 0) {
      name = arr[i].substring(0, num);
      value = arr[i].substr(num + 1);
      this[name] = value;
    }
  }
};

function convertArray(o) { // 主要是推荐这个函数。它将jquery系列化后的值转为name:value的形式。
    var v = {};
    for (var i in o) {
        if (typeof (v[o[i].name]) == 'undefined') v[o[i].name] = o[i].value;
        else v[o[i].name] += "," + o[i].value;
    }
    return v;
};

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};


function paramString2obj(serializedParams) {
	var obj = {};
	function evalThem(str) {
		var attributeName = str.split("=")[0];
		var attributeValue = str.split("=")[1];
		if (!attributeValue) {
			return;
		}
		var array = attributeName.split(".");
		for ( var i = 1; i < array.length; i++) {
			var tmpArray = Array();
			tmpArray.push("obj");
			for ( var j = 0; j < i; j++) {
				tmpArray.push(array[j]);
			}

			var evalString = tmpArray.join(".");
			// alert(evalString);
			if (!eval(evalString)) {
				eval(evalString + "={};");
			}
		}

		eval("obj." + attributeName + "='" + attributeValue + "';");
	}
	var properties = serializedParams.split("&");
	for ( var i = 0; i < properties.length; i++) {
		evalThem(properties[i]);
	}

	return obj;
};

$.fn.form2json = function() {
	var serializedParams = this.serialize();
	var obj = paramString2obj(serializedParams);
	return JSON.stringify(obj);
};

service = {
    _doError : function(data) {
        if (data != null && data.result != undefined && data.result == Constants.FAILURE) {
          view.toast(data.msg, function() {
             if (data.callback) {
                location.href = data.url;
             }
          });
          return true;
        }
        return false;
      }
};

officeAjax = {
		doAjax : function(url, data, callback, type, async) {
			if(async== undefined){
				async = true;
			}
			var newUrl = storage.get(Constants.MOBILE_CONTEXT_PATH) + url;
			return jQuery.ajax({
				type : "POST",
				async: async,
				url : newUrl,
				data : data,
				success : callback,
				dataType : type
			});
	}
};


//footer
function initFooter(type){
	var officeauth = storage.get(OfficeConstants.OFFICE_MODULE_AUTH);
	if(OfficeConstants.OFFICE_MODULE_AUTH_TRUE == officeauth){
		$('footer .abtn-office').show();
	}else{
		$('footer .abtn-office').hide();
	}

	//底部tab切换
	if("1" != type){
		$('footer .abtn-message').on('click', function(e){
			var contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
			location.href = contextPath + "/office/mobile/message/messageList.html";
		});
	}
	if("2" != type){
		$('footer .abtn-note').on('click', function(e){
			var contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
			location.href = contextPath + "/officedoc/mobile/officedocList.html";
		});
	}
	
	if("3" != type){
		$('footer .abtn-office').on('click', function(e){
			var contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
			location.href = contextPath + "/office/mobile/officeMain.html";
		});
	}
}

function isAndroid(){
	 var u = navigator.userAgent;
	 if(u.indexOf('Android') > -1 || u.indexOf('Adr') > -1){
	 	return true;
	 }else{
	 	return false;
	 }
}

function isIOS(){
	 var u = navigator.userAgent;
	 if(!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
	 	return true;
	 }else{
	 	return false;
	 }
}

//获取两个日期之间的年月
function getDateYearMouth(a,b){
	var arrA = a.split("-"),
	arrB = b.split("-"),
	yearA = arrA[0],
	yearB = arrB[0],
	monthA = +arrA[1],
	monthB = (yearB-(+yearA))*12+parseInt(arrB[1]),
	rA = [],
	rB = [];
	do{
		do{
				rA.push(yearA+""+(+monthA > 9 ? monthA : "0"+monthA));
				//rB.push(yearA+"年"+monthA+"月");
				if(monthA == 12){
				monthA=1;
				monthB -= 12;
				break;
		   }
		}while(monthB > monthA++)
	}while(yearB > yearA++)
	return rA;
}