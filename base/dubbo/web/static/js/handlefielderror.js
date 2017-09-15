$.ajaxSetup({
	cache : false
		// 关闭AJAX相应的缓存
	});
/** ********************弹出窗口的行为模式列表***************** */
var haveOpenTip = 00001; // 显示外部提示框，不采用内嵌方式
var haveOverlayer = 00100; // 显示遮罩层
var noOverlayer = 00010; // 不显示遮罩层
var openWindowAfterLoaded = 01000; // 必须是载入完毕后，才弹出窗口
var followCursor = 10000; // 弹出窗口初始位置跟随鼠标
/** ********************************************************* */

function isActionable(obj) {
	var cn = $(obj).attr("class");
	if (cn == "abtn-unable" || cn == "abtn-unable-big")
		return false;
	else
		return true;
}
var defaultZIndex = 10100;

if (!divZIndex || divZIndex < 10100)
	var divZIndex = defaultZIndex;
var defaultJWindowName = "#_panel-pulic-window";
try {
	if (_contextPath) {
		var __contextPath = _contextPath;
	} else {
		var __contextPath = "";
	}
} catch (e) {
	var __contextPath = "";
}

/**
 * 把错误信息加到字段上； 如果是多行列表的形式，需要传入rowNum，表示哪一行。
 * 
 * @param fieldName
 *            可以传入id或是对象，比如fid或$(fid)都可以
 * @param msg
 * @param rowNum
 *            只有列表形式的时候需要传入，一般的表单不需要。
 * @return
 */
function addFieldError(fieldName, msg, rowNum) {
	msg=msg.toString();
	var obj;
	if (rowNum || rowNum == 0) {// 第一行按照从0开始计算
		obj = document.getElementsByName(fieldName)[rowNum];
	} else {
		if (typeof(fieldName) == "string") {
			obj = document.getElementById(fieldName);
		} else {
			obj = fieldName;
		}
	}

	if (obj.tagName == "SELECT") {
		obj.style.color = "#e34007";
	} else {
		if ($(obj).hasClass('ui-select-txt')) {
			$(obj).parent()[0].style.borderColor = "#e34007";
		} else {
			obj.style.borderColor = "#e34007";
		}
	}

	// showMsgError(msg, "", function(){
	// if (obj.getAttribute("type") != "hidden" &&
	// (!obj.getAttribute("dataType") || obj.getAttribute("dataType") !=
	// "date"))
	// obj.focus();
	// });

	var parent;
	if ($(obj).hasClass('ui-select-txt')) {
		parent = $(obj).parent().parent();
	} else {
		parent = $(obj).parent();
	}
	if (parent.find(".field_tip").length == 0) {
		parent.append("<span class='field_tip input-txt-warn-tip'>" + msg
				+ "</span>");

		var funname;// 一直找到顶级元素或checkAllValidate为止
		var fun = addFieldError.caller;
		try {
			while (fun) {
				funname = fun.name
						|| fun.toString().match(/function\s*([^(]*)\(/)[1];
				if (funname == "checkAllValidate") {
					break;
				} else {
					fun = fun.caller;
				}
			}
		} catch (e) {
			// js strict模式下 不能调用caller
		}
		if (funname == "checkAllValidate") {
			$(obj).delegate($(obj), "blur", function() {
						if (checkAllValidate(obj,false)) {// 再次检验
							parent.find(".field_tip").hide();
						}
					});

			// 模拟框
			$(obj).siblings(".ui-option").bind("myeventchange",
					function(event, val, txt) {
						if (checkAllValidate(obj,false)) {// 再次检验
							parent.find(".field_tip").hide();
						}
					});
		}

	} else {
		parent.find(".field_tip").removeClass("input-txt-prompt")
				.addClass("input-txt-warn-tip").html(msg).show();
	}

}

/**
 * 把错误信息加到字段上； 如果是多行列表的形式，需要传入rowNum，表示哪一行。
 * 
 * @param fieldName
 *            可以传入id或是对象，比如fid或$(fid)都可以
 * @param msg
 * @param rowNum
 *            只有列表形式的时候需要传入，一般的表单不需要。
 * @return
 */
function addInterLockFieldError(fieldName, msg, rowNum) {
	msg=msg.toString();
	var obj;
	if (rowNum || rowNum == 0) {// 第一行按照从0开始计算
		obj = document.getElementsByName(fieldName)[rowNum];
	} else {
		if (typeof(fieldName) == "string") {
			obj = document.getElementById(fieldName);
		} else {
			obj = fieldName;
		}
	}

	if (obj.tagName == "SELECT") {
		obj.style.color = "#e34007";
	} else {
		if ($(obj).hasClass('ui-select-txt')) {
			$(obj).parent()[0].style.borderColor = "#e34007";
		} else {
			obj.style.borderColor = "#e34007";
		}
	}

	// showMsgError(msg, "", function(){
	// if (obj.getAttribute("type") != "hidden" &&
	// (!obj.getAttribute("dataType") || obj.getAttribute("dataType") !=
	// "date"))
	// obj.focus();
	// });

	var parent;
	if ($(obj).hasClass('ui-select-txt')) {
		parent = $(obj).parent().parent();
	} else {
		parent = $(obj).parent();
	}
	if (parent.find(".field_tip").length == 0) {
		parent.append("<span class='field_tip input-txt-warn-tip'>" + msg
				+ "</span>");

		var funname;// 一直找到顶级元素或checkAllValidate为止
		var fun = addInterLockFieldError.caller;
		try {
			while (fun) {
				funname = fun.name
						|| fun.toString().match(/function\s*([^(]*)\(/)[1];
				if (funname == "checkAllInterLock") {
					break;
				} else {
					fun = fun.caller;
				}
			}
		} catch (e) {
			// js strict模式下 不能调用caller
		}
		if (funname == "checkAllInterLock") {
			$(obj).delegate($(obj), "blur", function() {
						if (checkAllInterLock(obj)) {// 再次检验
							parent.find(".field_tip").hide();
						}
					});

			// 模拟框
			$(obj).siblings(".ui-option").bind("myeventchange",
					function(event, val, txt) {
						if (checkAllInterLock(obj)) {// 再次检验
							parent.find(".field_tip").hide();
						}
					});
		}

	} else {
		parent.find(".field_tip").removeClass("input-txt-prompt")
				.addClass("input-txt-warn-tip").html(msg).show();
	}

}

function _getLength(text) {
	var len;
	var i;
	len = 0;
	var val = text;
	var length = val.length;
	for (i = 0; i < length; i++) {
		if (val.charCodeAt(i) > 255)
			len += 2;
		else
			len++;
	}
	return len;
}

function _substring(text, si) {
	var _si = 0;
	for (var i = 0; i < text.length; i++) {
		var v = text.substring(i, i + 1);
		if (_getLength(v) == 2) {
			_si += 2;
		} else {
			_si += 1;
		}
		if (_si >= si) {
			return text.substring(0, i);
		}
	}
}

function showConfirm(msg) {
	return confirm(msg);
}

function showSaveTip(behavior) {
	showTip("正在保存数据，请稍候……", behavior);
}

function showDeleteTip(behavior) {
	showTip("正在删除数据，请稍候……", behavior);
}

function showLoadTip(behavior) {
	showTip("正在加载数据，请稍候……", behavior);
}

function showTip(msg, handler) {
	$('#panelWindow_tip_msg').html(msg);

	openDiv("#panelWindow_tip", "", null, null, null, null, null, handler);
	$("#panelWindow_tip").css({
				"zIndex" : (++divZIndex + 300)
			});
	return;
}

function closeTip(remainOverLayer) {
	if (remainOverLayer && remainOverLayer == 1) {
		$("#panelWindow_tip").hide();
		$("#panelWindow_tip").DraggableDestroy();
	} else {
		closeDiv("#panelWindow_tip");
	}
}

//autoCloseTime = -1，不自动关闭； 不传此参数，默认3秒后关闭
function showMsgSuccess(msg, title, handler, autoCloseTime) {
	$('#panelWindow_success_msg').html(msg);
	if (!title) {
		title = "";
	}
	if (title != "")
		$('#panelWindow_success_title').html(title);
	openDiv(
			"#panelWindow_success",
			"#panelWindow_success .close,#panelWindow_success .submit,#panelWindow_success .reset",
			null, null, null, null, null, handler);
	$("#panelWindow_success").css({
				"zIndex" : (++divZIndex + 300)
			});
	//统一改成不自动关闭
	autoCloseTime =-1;
	if(!autoCloseTime){
		autoCloseTime = 3000;
	}			
	if(autoCloseTime != -1){		
		setTimeout(function() {
				closeDiv('#panelWindow_success', handler)
			}, 3000); // 3秒钟后自动关闭
	}
	return;
}

//autoCloseTime = -1，不自动关闭； 不传此参数，默认3秒后关闭
function showMsgError(msg, title, handler, autoCloseTime) {
	$('#panelWindow_error_msg').html(msg);
	if (!title) {
		title = "";
	}
	if (title != "")
		$('#panelWindow_error_title').html(title);
	openDiv(
			"#panelWindow_error",
			"#panelWindow_error .close,#panelWindow_error .submit,#panelWindow_error .reset",
			null, null, null, null, null, handler);
	$("#panelWindow_error").css({
				"zIndex" : (++divZIndex + 300)
			});
	//统一改成不自动关闭
	autoCloseTime =-1;		
	if(!autoCloseTime){
		autoCloseTime = 3000;
	}			
	if(autoCloseTime != -1){			
		setTimeout(function() {
				closeDiv('#panelWindow_error', handler)
			}, 3000); // 3秒钟后自动关闭
	}		
	return;
}

//autoCloseTime = -1，不自动关闭； 不传此参数，默认3秒后关闭
function showMsgWarn(msg, title, handler, autoCloseTime) {
	$('#panelWindow_warning_msg').html(msg);
	if (!title) {
		title = "";
	}
	if (title != "")
		$('#panelWindow_warning_title').html(title);
	openDiv(
			"#panelWindow_warning",
			"#panelWindow_warning .close,#panelWindow_warning .submit,#panelWindow_warning .reset",
			null, null, null, null, null, handler);
	$("#panelWindow_warning").css({
				"zIndex" : (++divZIndex + 300)
			});
	//统一改成不自动关闭
	autoCloseTime =-1;		
	if(!autoCloseTime){
		autoCloseTime = 3000;
	}			
	if(autoCloseTime != -1){
		setTimeout(function() {
				closeDiv('#panelWindow_warning', handler)
			}, 3000); // 3秒钟后自动关闭
	}
	return;
}

function closeMsg(divObject, handler, haveLayer) {
	if (haveLayer == 1) {
		jWindow.showOverlayer();
	} else {
		jWindow.removeOverlayer();
	}
	$("#_______overlayer").css({
				"zIndex" : (++divZIndex)
			});

	if (handler instanceof Function) {
		eval(handler)();
	} else {
		eval(handler);
	}
}

/**
 * 封装好的getJSON 如果session超时 跳转登录页面 data 按照ajax的数据格式
 */
function getJSON(url, data, handler,type) {
	if(type && type == "post"){
		$.post(url, data, function(data, textStatus, xhr) {
			var sessionstatus = xhr.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") { // 如果超时就处理 ，指定要跳转的页面
				top.location.href = __contextPath
						+ "/fpf/homepage/back.action";
			}
			if (handler && handler != "") {
				if (handler instanceof Function) {
					eval(handler)(data);
				} else {
					eval(handler);
				}
			}
		});
		
	}else{
		$.getJSON(url, data, function(data, textStatus, xhr) {
			var sessionstatus = xhr.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") { // 如果超时就处理 ，指定要跳转的页面
				top.location.href = __contextPath
						+ "/fpf/homepage/back.action";
			}
			if (handler && handler != "") {
				if (handler instanceof Function) {
					eval(handler)(data);
				} else {
					eval(handler);
				}
			}
		});
	}
	
}

/**
 * @param loadObject
 *            显示的DIV在jQuery的selector名字，如id=openDiv的，则传入 #openDiv 不能空
 * @param url
 *            加载的url 可以空
 * @param endHandler
 *            加载前回调函数
 * @param beforeHandle
 *            加载后回调函数
 * @param noLoadTip
 *            是否显示加载数据中 否则显示
 * @return
 */
function load(loadObject, url, endHandler, beforeHandle, noLoadTip, data) {
	$.ajaxSetup({
		async : true
	});// 异步
	var length = url.length;
	var u = "";
	for (i = 0; i < length; i++) {
		var v = url.substring(i, i + 1);
		if (url.charCodeAt(i) > 255) {
			u += encodeURI(v);
		} else {
			u += v;
		}
	}
	url = u;
	if (beforeHandle && beforeHandle != "") {
		if (beforeHandle instanceof Function) {
			eval(beforeHandle)();
		} else {
			eval(beforeHandle);
		}
	} else {
		if (!noLoadTip) {
			noLoadTip = false;
		}
		if (!noLoadTip) {
			var padding = $(loadObject).height();
			$(loadObject)
					.html("<table height='"
							+ padding
							+ "' width='100%'><tr><td width='50%' align='right'><img src='"
							+ __contextPath
							+ "/static/images/loading.gif' /></td><td width='50%' align='left'><span>&nbsp;正在加载数据……</span></td></tr></table>");
		}
	}
	$(loadObject).load(url, data, function(response, status, xhr) {
				var sessionstatus = xhr.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
				if (sessionstatus == "timeout") { // 如果超时就处理 ，指定要跳转的页面
					top.location.href = __contextPath
							+ "/fpf/homepage/back.action";
				}
				if (endHandler && endHandler != "") {
					if (endHandler instanceof Function) {
						eval(endHandler)();
					} else {
						eval(endHandler);
					}
				}
				vselect();
			});

}

function closeDiv(divObject, handler) {
	
	if (!divObject || divObject == "") {
		divObject = defaultJWindowName;
	}
	if ($(divObject).is(":visible") == true) {
		if(!$(divObject).jWindowClose()){
			if (handler instanceof Function) {
				eval(handler)();
			} else {
				eval(handler);
			}
		}
	}
}

function getJsonStr(obj) {
	var tgs = ["INPUT", "SELECT", "TEXTAREA"];
	var dataMap = {};
	for (var j = 0; j < tgs.length; j++) {
		if (obj) {
			var os = jQuery(obj + " " + tgs[j]);
		} else {
			var os = jQuery(tgs[j]);
		}
		if (os) {
			for (var i = 0; i < os.length; i++) {
				var o = os[i];
				var jo = $(o);
				var id = jo.attr("id");
				if (typeof(id) == "undefined" || id == "") {
					id = jo.attr("name");
				}
				if (id && id != null && id != "") {
					if (jo.attr("type") == "checkbox") {
						if (jo.attr("checked") == true
								|| jo.attr("checked") == "checked") {
							dataMap[id] = "1";
						} else {
							dataMap[id] = "0";
						}
					} else if (jo.attr("type") == "radio") {
						var val = $('input:radio[name=' + id + ']:checked')
								.val();
						if (val == 'undefined') {
							dataMap[id] = '0';
						} else {
							dataMap[id] = val;
						}
					} else {
						dataMap[id] = jo.val();
					}
				}
			}
		}
	}
	return JSON.stringify(dataMap);
}

function getJsonStrRe(obj) {
	var tgs = ["INPUT", "SELECT", "TEXTAREA"];
	var dataMap = {};
	for (var j = 0; j < tgs.length; j++) {
		if (obj) {
			var os = jQuery(obj + " " + tgs[j]);
		} else {
			var os = jQuery(tgs[j]);
		}
		if (os) {
			for (var i = 0; i < os.length; i++) {
				var o = os[i];
				var jo = $(o);
				//先找name，找不到再取id
				var id = jo.attr("name");
				if (typeof(id) == "undefined" || id == "") {
					id = jo.attr("id");
				}
				if (id && id != null && id != "") {
					if (jo.attr("type") == "checkbox") {
						if (jo.attr("checked") == true
								|| jo.attr("checked") == "checked") {
							if(typeof(dataMap[id]) == "undefined"){
								dataMap[id] = "1";
							}else{
								dataMap[id] += ",1";
							}
						} else {
							if(typeof(dataMap[id]) == "undefined"){
								dataMap[id] = "0";
							}else{
								dataMap[id] += ",0";
							}
						}
					} else if (jo.attr("type") == "radio") {
						var val = $('input:radio[name=' + id + ']:checked')
						.val();
						if (val == 'undefined') {
							if(typeof(dataMap[id]) == "undefined"){
								dataMap[id] = "0";
							}else{
								dataMap[id] += ",0";
							}
						} else {
							if(typeof(dataMap[id]) == "undefined"){
								dataMap[id] = val;
							}else{
								dataMap[id] += ","+val;
							}
						}
					} else {
						if(typeof(dataMap[id]) == "undefined"){
							dataMap[id] = jo.val();
						}else{
							dataMap[id] += ","+jo.val();
						}
					}
				}
			}
		}
	}
	return JSON.stringify(dataMap);
}

/**
 * @param divObject
 *            显示的DIV在jQuery的selector名字，如id=openDiv的，则传入 #openDiv 不能空
 * @param closeObject
 *            关闭的控件在jQuery的selector名字，如id=closeButton的，则传入 #closeButton 可以空
 * @param url
 *            加载的url 可以空
 * @param scroll
 *            是否需要滚动
 * @param className
 *            内部的className 如果需要设置滚动 则必须填写
 * @param height
 *            内部的高度超过此高度则需要 否则不需要
 * @param urlLoadedHandler
 *            url加载完毕后的回调函数
 * @param closeHandler
 *            弹出窗口关闭后的回调函数（包括消息窗口）
 * @param container
 *            是否需要容器 默认需要 不需要的false
 * @return
 */
function openDiv(divObject, closeObject, url, scroll, className, height,
		urlLoadedHandler, closeHandler) {
	if (url) {
		load(divObject, url, function() {
					dealDiv(divObject, closeObject, scroll, className, height,
							closeHandler);
					if (urlLoadedHandler) {
						if (urlLoadedHandler instanceof Function) {
							eval(urlLoadedHandler)();
						} else {
							eval(urlLoadedHandler);
						}
					}
				});
	} else {
		dealDiv(divObject, closeObject, scroll, className, height, closeHandler);
	}
}

function dealDiv(divObject, closeObject, scroll, className, height,
		closeHandler) {
	// --成功时关闭原弹出框，再弹出成功提示框
	var handler = closeHandler;
	if (divObject == '#panelWindow_success') {
		var popupObj = $('#container .popUp-layer:not(".popUp-layer-tips,.keep-div")');
		if (null != popupObj && popupObj.length != 0) {
			popupObj.hide();
		}
		// 特殊处理一些不在container里面的弹出层
		popupObj = $('.specialDiv');
		if (null != popupObj && popupObj.length != 0) {
			popupObj.hide();
		}
		var handler = function() {
			if (closeHandler != null) {
				closeHandler();
			}
		}
	}

	$(divObject).jWindowOpen({ // 弹出层的id
		modal : true,
		center : true,
		close : closeObject,
		closeHandler : handler
	});
	$(divObject).css({
				"zIndex" : (++divZIndex + 300)
			});
	if (scroll) {
		var myHeight = $(className).height();
		if (height) {
			if (myHeight > height) {
				$(className).css({
							'height' : height + 'px'
						});
				$(className).jscroll({
							W : "5px"// 设置滚动条宽度
							,
							Bar : {
								Pos : ""// 设置滚动条初始化位置在底部
								,
								Bd : {
									Out : "#999fa5",
									Hover : "#5b5c5d"
								}// 设置滚动滚轴边框颜色：鼠标离开(默认)，经过
								,
								Bg : {
									Out : "#999fa5",
									Hover : "#67686a",
									Focus : "#67686a"
								}
							}// 设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
							,
							Btn : {
								btn : false
							}// 是否显示上下按钮 false为不显示
						});
			}
		} else {
			$(className).jscroll({
						W : "5px"// 设置滚动条宽度
						,
						Bar : {
							Pos : ""// 设置滚动条初始化位置在底部
							,
							Bd : {
								Out : "#999fa5",
								Hover : "#5b5c5d"
							}// 设置滚动滚轴边框颜色：鼠标离开(默认)，经过
							,
							Bg : {
								Out : "#999fa5",
								Hover : "#67686a",
								Focus : "#67686a"
							}
						}// 设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
						,
						Btn : {
							btn : false
						}// 是否显示上下按钮 false为不显示
					});
		}
	}
}

function bindEvent(divObject, behavior, handler) {
	$(divObject).unbind(behavior);
	$(divObject).bind(behavior, handler);
}

/**
 * 把成功的信息加到字段上； 如果是多行列表的形式，需要传入rowNum，表示哪一行。
 */
function addFieldSuccess(fieldName, msg, rowNum) {
	var obj;
	if (rowNum || rowNum == 0) {// 第一行按照从0开始计算
		obj = document.getElementsByName(fieldName)[rowNum];
	} else {
		if (typeof(fieldName) == "string") {
			obj = document.getElementById(fieldName);
		} else {
			obj = fieldName;
		}
	}

	if (obj.tagName == "SELECT") {
		obj.style.color = "#026db7";
	} else {
		obj.style.borderColor = "#026db7";
	}
	// showMsgSuccess(msg, "", function(){obj.focus();})

	var parent;
	if ($(obj).hasClass('ui-select-txt')) {
		parent = $(obj).parent().parent();
	} else {
		parent = $(obj).parent();
	}
	if (parent.find(".field_tip").length == 0) {
		parent.append("<span class='field_tip input-txt-prompt'>" + msg
				+ "</span>");
	} else {
		parent.find(".field_tip").removeClass("input-txt-warn-tip")
				.addClass("input-txt-prompt").html(msg).show();
	}

}

/**
 * 将把错误信息加到指定的一系列字段上
 * 
 * @param fields
 *            对应的一组id值
 * @param objId
 *            id前缀
 * @param msg
 *            提示信息
 * 
 */
function addFieldErrors(fields, objId, msg) {
	var objclear = document.all;
	if (objclear != null) {
		for (var i = 0; i < objclear.length; i++) {
			if (objclear[i].fielderror != '') {
				if (objclear[i].tagName == "SELECT") {
					objclear[i].style.color = "";
				} else {
					objclear[i].style.borderColor = "";
				}
			}
		}
	}
	for (var i = 0, len = fields.length; i < len; i++) {
		fieldName = objId + fields[i];
		obj = document.getElementById(fieldName);
		obj.fielderror = msg;
		if (obj.tagName == "SELECT") {
			obj.style.color = "red";
		} else {
			obj.style.borderColor = "red";
		}
		if (i == 0) {
			showMsgError(msg, "", function() {
						if (obj.getAttribute("type") != "hidden"
								&& (!obj.getAttribute("dataType") || obj
										.getAttribute("dataType") != "date"))
							obj.focus();
					});
			return;
		}
	}
}

/**
 * 清除原来显示的所有提示信息
 * 
 * @param tipId
 *            可以不传入
 */
function clearMessages(containerName) {
	var tgs = ["INPUT:not(:file)", "SELECT", "TEXTAREA"];
	var len = tgs.length;
	for (var j = 0; j < len; j++) {
		if (containerName) {
			if (typeof(containerName) == "string") {
				var os = jQuery(containerName + " " + tgs[j]);
			} else {
				len = 1;// 针对单一确定元素
				var os = jQuery(containerName);
			}
		} else {
			var os = jQuery(tgs[j]);
		}
		if (os) {
			for (var i = 0; i < os.length; i++) {
				var o = os[i];

				// 清除focus框
				if (o.tagName == "SELECT") {
					o.style.color = "";
				} else {
					if ($(o).hasClass('ui-select-txt')) {
						$(o).parent()[0].style.borderColor = "";
					} else {
						o.style.borderColor = "";
					}
				}

				var parent;
				if ($(o).hasClass('ui-select-txt')) {
					parent = $(o).parent().parent();
				} else {
					parent = $(o).parent();
				}
				parent.find(".field_tip").hide();

			}
		}
	}
}

/**
 * 可以对buffalo返回的消息对象进行页面显示。
 * 
 * @param result
 * @param handler
 *            回调函数function(returnValue){...}
 *            其中returnValue的值：-2:fieldErrors,-1:actionErrors,1:actionMessages,2:表示正确，但没有错误和正确提示。
 *            一般使用方法，(returnValue>0)表示正确；(returnValue<0)表示有错误。
 * @return
 * 
 */
function drawMessages(result, handler) {
	if (result.script) {
		eval(result.script);
	}

	if (result.fieldErrors) {
		var errorFlag = false;
		for (var i in result.fieldErrors) {
			if (i == Buffalo.BOCLASS) {
				continue;
			}
			var errorField = $(i);
			if (errorField) {
				errorFlag = true;
				addFieldError(errorField, result.fieldErrors[i]);
			}
		}
		if (errorFlag) {
			if (handler)
				eval(handler)(-2);
			return false;
		}
	}

	if (result.actionErrors) {
		addActionError(result.actionErrors, handler);
		return false;
	} else if (result.actionMessages) {
		addActionMessage(result.actionMessages, handler);
		return true;
	} else {// 凡是没有actionErrors和actionMessages，就会调用ok的回调函数
		if (handler)
			eval(handler)(2);
	}

	return true;
}

/**
 * 支持两种提示方式，有tipId时在页面上显示，没有时用弹出窗口模式
 * 
 * @param msg
 *            要显示的内容
 * @param tipId/callHandler
 *            如果传入的是字符串tipid，表示要显示msg的html对象的id
 *            如果传入的是函数callHandler，表示用弹出窗口，并且在点击弹出窗口的“确定”按钮后调用该回调函数
 * 
 */
function addActionError(msg, tipId) {
	if (typeof(tipId) == "function" || typeof(tipId) == "undefined") {
		showMsgError(msg, "", tipId);
	} else if (typeof(tipId) == "string") {
		showMsgError(msg, "", "");
	} else {
		alert("错误，参数不是预知的类型。");
	}
}

/**
 * 支持两种提示方式，有tipId时在页面上显示，没有时用弹出窗口模式
 * 
 * @param msg
 *            要显示的内容
 * @param tipId/callHandler
 *            如果传入的是字符串tipid，表示要显示msg的html对象的id
 *            如果传入的是函数callHandler，表示用弹出窗口，并且在点击弹出窗口的“确定”按钮后调用该回调函数
 */
function addActionMessage(msg, tipId) {
	if (typeof(tipId) == "function" || typeof(tipId) == "undefined") {
		showMsgSuccess(msg, "", tipId);
	} else if (typeof(tipId) == "string") {
		showMsgSuccess(msg, "", "");
	} else {
		alert("错误，参数不是预知的类型。");
	}

}

// 68
/**
 * @deprecated
 */
function addFieldErrorWithObject(field, msg) {
	addFieldError(field, msg);
}

// 8
/**
 * @deprecated
 */
function addFieldSuccessMsgWithObject(field, msg) {
	addFieldSuccess(field, msg);
}

/**
 * @deprecated
 */
function addFieldErrorWithObjects(fields, objId, msg) {
	addFieldErrors(fields, objId, msg);
}

/**
 * @deprecated
 */
function addFieldErrorWithReply(result) {
	drawMessages(result);
}

// 55
/**
 * @deprecated
 */
function drawPageMessages(result, tipId) {
	drawMessages(result);
}

/*
 * 这几个方法不需要使用，可以用 if(result.hasErrors) if(result.actionErrors)
 * if(result.actionMessages) 方法来代替
 */
function hasErrors(result) {
	return result
			&& ((result.actionErrors && result.actionErrors.length > 0) || result.fieldErrors
					&& result.fieldErrors.length > 0);
}
