/**
 * 通用js说明
 * 本文件存储 通用非校验 的js工具方法（校验相关的在validate.js）
 * 1. 批量选中或者取消
 * 2. 去除前后的空格
 * 3. 去掉字符串前面的0，为转化成int类型准备
 * 4、使输入域的值在提交之前重定向到其它输入域
 * 5、打开新的窗口
 * 6、打开模态窗口
 * 7、计算输入字符数
 * 8、获取指定字符串的长度，1个汉字为2个字节
 * 9、设置img的路径
 * 
 */

/**
 * 1. 批量选中或者取消
 * singleCheckboxName: checkbox的name
 * checkStatus: 'checked' 选中,''取消选中
 */ 
function checkAllByStatus(singleCheckboxId, checkStatus) {
	$("#"+singleCheckboxId).each(function(){
		if(checkStatus == 'checked')
    		$(this).addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
    	else
    		$(this).removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
    });
}

/**
 * 2.去除前后的空格
 */
function trim(s) {
	if(s !='' && s != null){
    	s= s.replace(/(^\s+)|(\s+$)/g, "");
	}
	return s;
}

/**
 * 3. 去掉字符串前面的0，为转化成int类型准备
 */
function jsTrimLeadingZero(s) {
    return s.replace(/(^0+)/g, "");
}

/**
 * 4、使输入域的值在提交之前重定向到其它输入域
 * 一般用于在表单提交之前，把列表控件中checkbox选中的值，传递到隐藏域字段（也可以是非隐藏的input域）
 * 通过隐藏域字段，把选中的值提交到action。提交到action的值形式：id,id,
 * @param formObj: 包含列表checkbox的表单对象
 * @param checkboxName: 列表checkbox的name值
 * @param hiddenObj: 隐藏域字段对象（也可以是非隐藏的input域字段对象）
 * 具体调用该方法的示例：checkboxValueToHidden (document.form,'userid',document.form1.selectedUserids)
 */
function checkboxValueToHidden(formObj,checkboxName,hiddenObj){
	hiddenObj.value = "";
	var length = formObj.elements.length;
	for(var i=0;i<length;i++){
		if((formObj.elements[i].type == "checkbox") && (formObj.elements[i].name == checkboxName) 
			&& (formObj.elements[i].checked)){
			hiddenObj.value = hiddenObj.value + formObj.elements[i].value + ",";
		}
	}
}


/**
 * 5、打开新的窗口
 * url：窗口路径
 * winName： 窗口的名字
 * width：窗口的宽度
 * height: 窗口高度
 */
function openwindow( url, winName, width, height, resizable) {
//	openDiv(null, null, null, url, width, height);
//	return;
	
	
	
	xposition=0; yposition=0;
	if ((parseInt(navigator.appVersion) >= 4 ))
	{
	xposition = (screen.width - width) / 2;
	yposition = (screen.height - height) / 2;
	}
	theproperty= "width=" + width + "," 
	+ "height=" + height + "," 
	+ "location=0," 
	+ "menubar=0,";
	if (resizable){
		theproperty +="resizable=" + resizable + ",";
	}
	else{
		theproperty +="resizable=1";
	}
	theproperty = theproperty
	+ "scrollbars=0,"
	+ "status=0," 
	+ "titlebar=0,"
	+ "toolbar=0,"
	+ "hotkeys=0,"
	+ "screenx=" + xposition + "," //Netscape
	+ "screeny=" + yposition + "," //Netscape
	+ "left=" + xposition + "," //IE
	+ "top=" + yposition; //IE 
	newwin=window.open( url,winName,theproperty );
	
	if(newwin.closed){
	   window.location.reload();
	}
}

function getXY(width, height){
	var posX = event.clientX;
	var posY = event.clientY;
	if(document.body.clientWidth - event.clientX < width){
		posX = document.body.clientWidth - width;
	}
	if(document.body.clientHeight - event.clientY < height){
		posY = document.body.clientHeight - height;
	}
	return [posX, posY];
}

/**
 * 6、打开模态窗口 
 *	url：地址
 *  arguments：参数
 *	twidth：宽度
 *  theight：高度
 */
function openModalDialog(url,arguments,twidth,theight){
    var sRet = window.showModalDialog(url,arguments,"dialogWidth=" + twidth + "px;dialogHeight=" + theight + "px;status=no;help=no;resizable=no;");
    if(sRet != null){
	    if("refresh" == sRet.kind){
	    	window.location.reload();
	    }
	 }
	 return sRet;
}

/*
 *7、计算输入字符数
 */
function computeInputLength(elem){
	var len;
	len = 0;
	var val = trim(elem.value);
	var length = val.length;
	for (var i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}
	showMsgWarn("您已经输入了"+len+"个字符！");
}

/**
*  8、获取指定字符串的长度，1个汉字为2个字节
*  text　指定字符串
*/
function getLength(text){
	var len;
	var i;
	len = 0;
	var val = text;
	var length = val.length;
	for (i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}	
	return len;
}

/**
 * 9、设置img的路径
 */
function initImagePath(_imagePath){	
	fieldErrorImage = " <img src=\"" + _imagePath + "icon_red.gif\" width=\"14\" height=\"14\" align=\"absmiddle\" /> ";
	errorImage = " <img src=\"" + _imagePath + "icon_red.gif\" width=\"14\" height=\"14\" align=\"absmiddle\" /> ";
	messageImage = " <img src=\"" + _imagePath + "icon_green.gif\" width=\"14\" height=\"14\" align=\"absmiddle\" /> ";
	promptImage = " <img src=\"" + _imagePath + "icon_blue.gif\" width=\"14\" height=\"14\" align=\"absmiddle\" /> ";
}

function getIEVersion(){
	var browser=navigator.appName ;
	var b_version=navigator.appVersion ;
	
	var b_version=b_version.replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && b_version.indexOf("MSIE6.0") >= 0) 
	{ 
		return 6;
	} 
	else if(browser=="Microsoft Internet Explorer" && b_version.indexOf("MSIE7.0") >= 0) 
	{ 
		return 7; 
	} 
	else if(browser=="Microsoft Internet Explorer" && b_version.indexOf("MSIE8.0") >= 0) 
	{ 
		return 8; 
	} 
	else if(browser=="Microsoft Internet Explorer" && b_version.indexOf("MSIE9.0") >= 0) 
	{ 
		return 9; 
	} 
	return 99;
}
