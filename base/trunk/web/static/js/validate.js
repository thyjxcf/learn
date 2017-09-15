
/**
*  1、检查输入框中是否为空
*  elem 输入框
*  field 输入框的中文名称
*/
function checkElement(elem,field){
	if(trim(elem.value)==''){
		addFieldError(elem,field+"不能为空，请输入！");
		return false;
	}
	return true;
}


/**
*  2、检查表单内所有字段是否为空
*/
function checkAllForm(frm){
 var len=frm.elements.length;
  for(var i=0;i<len;i++)
   {
     if(frm.elements[i].alt=='key'){
	     if(trim(frm.elements[i].value)==''){ 
	    	 if(frm.elements[i].type=='text'){
			 alert("请输入"+frm.elements[i].id);
			 	frm.elements[i].select();
			}else{
			alert("请选择"+frm.elements[i].id);
				frm.elements[i].focus();
			   }
			 return false;
		    }
	 }
  }
  return true;
}


/**
*  3、判断输入框中只能输入正整数（不包括零）
*/
function checkPlusInt(elem,field){
	if(!checkElement(elem,field)) return false;
	val=elem.value;
	//var pattern=/[^1-9]/;
	var pattern=/[^0-9]/;
	if(pattern.test(val) || val.slice(0,1)=="0"){
		addFieldError(elem,field+"只能输入非零的整数！");
		elem.value = '';
		return false;
	}
	return true;
}

/**
*  4、判断输入框中只能输入非负整数（包括零）
*/
function checkInteger(elem,field){
	if(!checkElement(elem,field)) return false;

	val=elem.value;
	var pattern=/[^0-9]/;
	if(pattern.test(val)){
		addFieldError(elem,field+"只能输入非负整数！");
		return false;
	}
	return true;
}


/**
*  5、判断输入框中只能输入非负数数值型数据(包括小数)
*/
function checkNum(elem,field){
	if(!checkElement(elem,field)) return false;
	val=elem.value;
	var pattern=/^([0]|[1-9]\d*|\d+\.\d+)$/;
	if(!pattern.test(val)){
		addFieldError(elem,field+"只能输入非负数值型数据！");
		elem.value = '';
		return false;
	}
	return true;
}

/**
*  6、判断输入框中的数值是否在指定范围内（如：0-100），不包括上下界值
*  min：最小值
*  max：最大值
*/
function checkNumRange(elem,field,min,max){
	val = parseInt(elem.value);
	if(val<=min || val>=max){
		addFieldError(elem,field+"只能是"+min+"~"+max+"范围内的数值！");
		return false;
	}
	return true;
}

/**
*  7、判断输入框中只能输入字母
*/
function checkCharacters(elem,field){
 	var flag=false;
    var str=/[a-z]|[A-Z]/;
    var str1=trim(elem.value).length;
 	for(var i=0;i<str1;i++){
    	if(!str.test(trim(elem.value).charAt(i))){
	    	flag=true;
			break;
		}
	}
	if(flag==true){
	    addFieldError(elem,field+"只能为字母！");
	   return false;
     }else{
     	return true;
     }  
 }

/**
*  8、判定指定的字段长度是否超过了指定的最大长度，主要是校验汉字的长度
*  element　指定字段
*  maxlen  允许最大长度
*　field   标题（字段名）
*/
function checkOverLen(elem,maxlen,field){
	var len;
	var i;
	len = 0;
	var val = trim(elem.value);
	var maxlength = parseInt(maxlen);
	var length = val.length;
	for (i=0;i<length;i++){
		if (val.charCodeAt(i)>255) 
			len+=2;
		else 
			len++;
	}
	if(len>maxlength){
		addFieldError(elem,field+"长度超过范围,允许最大长度为 "+maxlength+" 字符(一个汉字占2个字符)");
		return false;
	}
	return true;
}


/**
*  9、验证日期的前后关系
*  elem1 前面的日期
*  elem2 后面的日期
*/
function checkAfterDate(elem1,elem2){
	if(elem1.value!="" && elem2.value!=""){
		 var date1 ;
		 var date2 ;
		 try{
			date1 = elem1.value.split('-');
			date2 =elem2.value.split('-');
		 }catch(e){
			date1 = elem1.split('-');
			date2 = elem2.split('-');
		 }
	
		 for(var i=0;i<Math.min(date1.length,date2.length);i++){
			if(date1[i].length==1)
				date1[i]='0'+date1[i];
			if(date2[i].length==1)
				date2[i]='0'+date2[i];
		 }
		 
		 for(var i=0;i<date1.length;i++){
			if(date1[i]>date2[i]){
				if(elem1.getAttribute("type") != 'hidden'){
					addFieldError(elem1.getAttribute("id") ,elem1.getAttribute("msgName") + "与" + elem2.getAttribute("msgName") + "之间 前后时间不合逻辑，请更正！");
					if (!elem1.getAttribute("dataType") || elem1.getAttribute("dataType") != "date")
						elem1.focus();
				} else {
					addFieldError(elem2.getAttribute("id") ,elem1.getAttribute("msgName") + "与" + elem2.getAttribute("msgName") + "之间 前后时间不合逻辑，请更正！");
					try{
						if (!elem2.getAttribute("dataType") || elem2.getAttribute("dataType") != "date")
							elem2.focus();
					}catch(e){}
				}
				return false;
			}
			if(date1[i]<date2[i]){
				i=date1.length;
			}
		 }
	}
	
	return true;
}


/**
*  9-1、验证日期的前后关系（传入提示信息）
*  elem1 前面的日期
*  elem2 后面的日期
*  msg  验证通不过时的提示信息
*/
function checkAfterDateWithMsg(elem1,elem2,msg){
	if(elem1.value!="" && elem2.value!=""){
		 var date1 ;
		 var date2 ;
		 try{
			date1 = elem1.value.split('-');
			date2 =elem2.value.split('-');
		 }catch(e){
			date1 = elem1.split('-');
			date2 = elem2.split('-');
		 }
	
		 for(var i=0;i<Math.min(date1.length,date2.length);i++){
			if(date1[i].length==1)
				date1[i]='0'+date1[i];
			if(date2[i].length==1)
				date2[i]='0'+date2[i];
		 }
		 
		 for(var i=0;i<date1.length;i++){
			if(date1[i]>date2[i]){
				//alert(msg);
				addFieldError(elem1,msg);
				if(elem1.type!='hidden')
					if (!elem1.getAttribute("dataType") || elem1.getAttribute("dataType") != "date")
						elem1.focus();
				else {
					try{
						if (!elem2.getAttribute("dataType") || elem2.getAttribute("dataType") != "date")
							elem2.focus();
					}catch(e){}
				}
				return false;
			}
			if(date1[i]<date2[i]){
				i=date1.length;
			}
		 }
	}
	
	return true;
}


/**
*  9-2、比较日期的大小
*  elem1 前面的日期（string）
*  elem2 后面的日期（string）
*  elem1>elem2 return 1;elem1==elem2 return 0;elem1<elem2 return -1
*/
function compareDate(elem1,elem2){
	if(elem1.value!="" && elem2.value!=""){
		 var date1 ;
		 var date2 ;
		 try{
			date1 = elem1.value.split('-');
			date2 =elem2.value.split('-');
		 }catch(e){
			date1 = elem1.split('-');
			date2 = elem2.split('-');
		 }
		 if(eval(date1[0])>eval(date2[0])){
		 	return 1;
		 }else if(eval(date1[0])==eval(date2[0])){
		 	if(eval(date1[1])>eval(date2[1])){
		 		return 1;
		 	}else if(eval(date1[1])==eval(date2[1])){
		 	    if(eval(date1[2])>eval(date2[2])){
			 		return 1;
			 	}else if(eval(date1[2])==eval(date2[2])){
			 		return 0;
			 	}else{
			 	    return -1;
			 	}	
		 	}else{
		 		return -1;
		 	}
		 }else{
		 	return -1;
		 }
	}
}



/**
*  10、判断学年的格式（格式为：2005-2006）
*/
function checkTermyear(elem){
	var termyear = trim(elem.value);
	if(termyear.length!=9){
		alert("学年必须为9位数据");
		elem.select();
		return false;
	}
	if(termyear.charAt(4)!='-'){
		alert("格式错误,正确格式示例：2003-2004");
		elem.select();
		 return false;
	}
	if(parseInt(termyear.substring(0,4))!=(parseInt(termyear.substring(5,9))-1)){
		alert("学年信息错误！");
		elem.select();
		return false;
 	}
	return true;
}


/**
 *  11、去除前后的空格
 */
function trim(str) {
	if(str !='' && str != null){
		str= str.replace(/(^\s+)|(\s+$)/g, "");
	}
	return str;
}

/**
 * 12. 判断是否有选择框被选中，true 表示至少有一个选中，false表示一个也没选中
 */
function isCheckBoxSelect( checkbox ){
	if(checkbox){
		var selected = false;
		for(var i = 0; i < checkbox.length; i++){
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


/**
*  13-1、checkbox全选
*  frm 包含删除checkbox的表单
*  elemCheckboxAll 全选的checkbox
*  elemCheckbox 每行的checkbox的name值，字符串
*/
function selectAllCheckbox(frm,elemCheckboxAll,elemCheckbox){
	var length = frm.elements.length;
	if (elemCheckboxAll.checked==true){
		for(i=0;i<length;i++){
			if((frm.elements[i].name ==elemCheckbox) && (frm.elements[i].disabled==false)){
		   		frm.elements[i].checked=true;
			}
		}
	}else{
		for(i=0;i<length;i++){
			if((frm.elements[i].name ==elemCheckbox) && (frm.elements[i].disabled==false)){
		   		frm.elements[i].checked=false;
		   	}
		}
	} 
}

/**
*  13-2、是否有checkbox被选择，用于有选择的删除
*  frm 包含删除checkbox的表单
* 	elemCheckbox 每行的checkbox的name值，字符串
*/
function checkSelectCheckbox(frm,elemCheckbox){
	var flag = false;
	var length = frm.elements.length;
	for(i=0;i<length;i++){
		if(frm.elements[i].name == elemCheckbox && frm.elements[i].checked){
			flag = true;
			break;
		}
	}
	
	if(!flag){
		showMsgWarn("没有行被选择，请先选择！","","");
	}
	return flag;
}


 /**
 * 16、校验日期格式(YYYY-MM-DD格式) addFieldError 返回boolean
 *	elem：日期输入框
 *  field：提示名称
 */
function checkDate(elem,field){
  var pattern=/\s+/;
  if (pattern.test(elem.value)) {
		  addFieldError(elem,field+"不能是空格串");
      return false;
  }
	if(isBlank(elem.value))
		return true;
	var DateValue = "";
    var seperator = "-";
    var day;
    var month;
    var year;
    var leap = 0;
    var err = 0;
    err = 0;
    DateValue = elem.value;

    var re = /[^0-9\-]+/gi;

    DateValue =  DateValue.replace(re, "");   //去除所有数字和'-'以外的字符

    var parts = DateValue.split('-');

    VALIDATION: {
        var len = parts.length;
        if(len != 3) {
            err = 19;
            break VALIDATION;
        }

      
        /*if(parts[0].length == 2) {
            parts[0] = '20' + parts[0];
        }*/
		if(parts[0].length != 4) {
            err = 100;
            break VALIDATION;
        }        
        year = parseInt(jsTrimLeadingZero(trim(parts[0])), 10);

        if ((isNaN(year))|| (year == 0)) {
            err = 20;
            break VALIDATION;
        }
        /* Validation of month*/
        month = parseInt(jsTrimLeadingZero(trim(parts[1])), 10);
        if ((isNaN(month))|| (month < 1) || (month > 12)) {
            err = 21;
            break VALIDATION;
        }
        /* Validation of day*/
        day = parseInt(jsTrimLeadingZero(trim(parts[2])), 10);
        if ((isNaN(day))|| (day < 1)) {
            err = 22;
            break VALIDATION;

        }

        /* Validation leap-year / february / day */
        if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0)) {
            leap = 1;
        }
        if ((month == 2) && (leap == 1) && (day > 29)) {
            err = 23;
            break VALIDATION;
        }
        if ((month == 2) && (leap != 1) && (day > 28)) {
            err = 24;
            break VALIDATION;
        }
        /* Validation of other months */
        if ((day > 31) && ((month == "01") || (month == "03") || (month == "05") || (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
            err = 25;
            break VALIDATION;
        }
        if ((day > 30) && ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
            err = 26;
            break VALIDATION;
        }
        /* if 00 ist entered, no error, deleting the entry */
        if ((day == 0) && (month == 0) && (year == 00)) {
            err = 0; day = ""; month = ""; year = ""; seperator = "";
        }
    }
    /* if no error, write the completed date to Input-elem (e.g. 13.12.2001) */
    if (err == 0) {    	
    	if (compareDate(year + seperator + month + seperator + day, "1900" + seperator + "1" + seperator + "1") < 0){
//    		alert(field + '的值不能小于1900-1-1');			
			addFieldError(elem, field + "的值必须在1900-1-1到2038-12-31之间");			
    		elem.select();
			return false;
    	}
    	if (compareDate("2038" + seperator + "12" + seperator + "31", year + seperator + month + seperator + day) < 0){
//    		alert(field + '的值不能大于2038-12-31');
			addFieldError(elem, field + "的值必须在1900-1-1到2038-12-31之间");
    		elem.select();
			return false;
    	}
        elem.value = year + seperator + month + seperator + day;
	
    }  else {
    	addFieldError(elem, '要求'+ field + '的格式为yyyy-mm-dd，并请注意月份与日期的有效范围');   
//        alert(field + '为日期型\n日期的格式为yyyy-mm-dd\n并请注意月份与日期的有效范围');    
        elem.select();
		return false;
    }
    return true;
}

function jsTrimLeadingZero(s) {
    return s.replace(/(^0+)/g, "");
}

 /**
 * 16-1、校验日期格式(YYYY-MM-DD格式) 返回提示信息
 *	elem：日期输入框
 *  field：提示名称
 */
function checkDate2(value,field){
	if(isBlank(value))
		return "";
	var DateValue = "";
    var seperator = "-";
    var day;
    var month;
    var year;
    var leap = 0;
    var err = 0;
    err = 0;
    DateValue = value;

    var re = /[^0-9\-]+/gi;

    DateValue =  DateValue.replace(re, "");   //去除所有数字和'-'以外的字符

    var parts = DateValue.split('-');

    VALIDATION: {
        var len = parts.length;
        if(len != 3) {
            err = 19;
            break VALIDATION;
        }

      
        /*if(parts[0].length == 2) {
            parts[0] = '20' + parts[0];
        }*/
		if(parts[0].length != 4) {
            err = 100;
            break VALIDATION;
        }        
        year = parseInt(jsTrimLeadingZero(trim(parts[0])), 10);

        if ((isNaN(year))|| (year == 0)) {
            err = 20;
            break VALIDATION;
        }
        /* Validation of month*/
        month = parseInt(jsTrimLeadingZero(trim(parts[1])), 10);
        if ((isNaN(month))|| (month < 1) || (month > 12)) {
            err = 21;
            break VALIDATION;
        }
        /* Validation of day*/
        day = parseInt(jsTrimLeadingZero(trim(parts[2])), 10);
        if ((isNaN(day))|| (day < 1)) {
            err = 22;
            break VALIDATION;

        }

        /* Validation leap-year / february / day */
        if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0)) {
            leap = 1;
        }
        if ((month == 2) && (leap == 1) && (day > 29)) {
            err = 23;
            break VALIDATION;
        }
        if ((month == 2) && (leap != 1) && (day > 28)) {
            err = 24;
            break VALIDATION;
        }
        /* Validation of other months */
        if ((day > 31) && ((month == "01") || (month == "03") || (month == "05") || (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
            err = 25;
            break VALIDATION;
        }
        if ((day > 30) && ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
            err = 26;
            break VALIDATION;
        }
        /* if 00 ist entered, no error, deleting the entry */
        if ((day == 0) && (month == 0) && (year == 00)) {
            err = 0; day = ""; month = ""; year = ""; seperator = "";
        }
    }
    /* if no error, write the completed date to Input-elem (e.g. 13.12.2001) */
    if (err == 0) {    	
    	if (compareDate(year + seperator + month + seperator + day, "1900" + seperator + "1" + seperator + "1") < 0){
			return field + "的值必须在1900-1-1到2038-12-31之间";
    	}
    	if (compareDate("2038" + seperator + "1" + seperator + "1", year + seperator + month + seperator + day) < 0){
			return field + "的值必须在1900-1-1到2038-12-31之间";
    	}
        value = year + seperator + month + seperator + day;
	
    }  else {
		return '要求'+ field + '的格式为yyyy-mm-dd，并请注意月份与日期的有效范围';
    }
    return "";
}


 /**
 * 17、校验身份证号是否有效
 */
function checkIdentityCard(elem,showPrompt){	
    if(isBlank(elem.value)) return true;
    
    var reOld = /^[0-9]{15}$/gi;
    var reNew = /^[0-9]{17}[0-9x]{1}$/gi;
    
    var err = 0;

    if(reOld.test(elem.value)) {
        err = 0;
    }    
    else if(reNew.test(elem.value)){
        err = 0;
    }
    else {
    	err = 1;
    	if(showPrompt == undefined || showPrompt)
        	addFieldError(elem,'请输入正确的身份证号！');
        return false;
    }
    
    var year;
    var month;
    var day;
    var date;
    if (err == 0){
    	if (elem.value.length == 15){
    		year = "19" + elem.value.substring(6, 8);
    		month = elem.value.substring(8, 10);
    		day = elem.value.substring(10, 12)
    		date = year + "-" + month + "-" + day;
    	}
    	else if (elem.value.length == 18){
    		year = elem.value.substring(6, 10);
    		month = elem.value.substring(10, 12);
    		day = elem.value.substring(12, 14)
    		date = year + "-" + month + "-" + day;
    	}
    	var checkdate = checkDate2(date, "身份证号中出生日期");
    	if (checkdate == ""){
    		return true;
    	}
    	else{
    		if(showPrompt == undefined || showPrompt)
    			addFieldError(elem, checkdate);
    		return false;
    	}
    }
}

/**
 * 根据身份证获取生日
 */
function checkoutBirthday(elem,showPrompt){
	var birth='';
	if(!checkIdentityCard(elem,showPrompt)){
		return birth;
	}
	
	val=trim(elem.value);
	if(val.length==0){
		return birth;
	}
	
	if(val.length==15){
		birth='19'+val.substring(6,8);
		birth=birth+'-'+val.substring(8,10);
		birth=birth+'-'+val.substring(10,12);
	}
	else if(val.length=18){
		birth=val.substring(6,10);
		birth=birth+'-'+val.substring(10,12);
		birth=birth+'-'+val.substring(12,14);
	}
	return birth;
}

 /**
 * 根据身份证获取性别
 */
function checkoutSex(elem,showPrompt){
	var sex='';
	if(!checkIdentityCard(elem,showPrompt)){
		return sex;
	}

	val=trim(elem.value);
	if(val.length==0){
		return sex;
	}
	
	var sexindex=-1;
	if(val.length==15){
		sexindex=14;
	}
	else{
		sexindex=16;
	}
	sex=val.substring(sexindex,sexindex+1);
	if(sex%2==0){
		return '2';
	}
	else{
		return '1';
	}
}

 /**
 * 18、判断是否为空
 *	s：值
 */
function isBlank(s) {
    var re = /^\s*$/g;
    return re.test(s);
}


/**
* 19、判断输入框中只能输入字母数字
*  
*/
function verifyLetterAndNum(elem,field){
 	var flag=false;
    var str=/[a-z]|[A-Z]|[0-9]/;
    var str1=trim(elem.value).length;
 	for(var i=0;i<str1;i++){
    	if(!str.test(trim(elem.value).charAt(i))){
	    	flag=true;
			break;
		}
	}
	if(flag==true){
	    addFieldError(elem,field+"只能为数字和字母！");
	   return false;
    }else {
    	return true;
    }  
 }

/*
 *21、手机号码验证
 */
function checkMobilePhone(elem){
	val=elem.value;
	if(trim(val)!=''){		
		var pattern=/^[0-9]{1,20}$/;
		//alert(val);
		if(!pattern.test(val)){
			addFieldError(elem,"请输入正确的号码！并且不能超过20个数字");
			return false;
		}
	}
	return true;
}

/*
 *23、Y/N数据验证
 */
function checkYN(elem,field){	
	var val=trim(elem.value);
	var pattern=/^(Y|N|y|n)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入Y或N');
		return false;
	}
	else{
		elem.value=val.toUpperCase();
	}
	return true;
}

/*
 *24、0/1数据验证
 */
function check01(elem,field){
	var val=trim(elem.value);
	var pattern=/^(0|1)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入0或1');
		return false;
	}
	return true;	
}

/*
 *25、http://IP:PORT格式地址验证,可以为空
 */
function checkipport(elem,field){
	var val=trim(elem.value);
	if(val==''){
		return true;
	}
	var pattern=/^http:\/\/\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\:\d{2,10}$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'请确认符合http://IP:PORT格式');
		return false;
	}
	return true;
}

/*
 *26、MO/TO数据验证
 */
function checkmoto(elem,field){
	var val=trim(elem.value);
	var pattern=/^(M|T)O$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入MO或TO');
		return false;
	}
	return true;
}

/*
 *27、固定电话号码验证
 */
function checkPhone(elem,field){
	val=elem.value;
	if(trim(val).length==0){
		return true;
	}
	if(val.indexOf('-')==-1){
		var pattern=/^[0-9]{1,12}$/;
		if(!pattern.test(val)){
			addFieldError(elem,'请输入正确的'+field+",并且不能超过12个数字");
			return false;
		}
		return true;		
	}
	else{
		pattern=/^[0-9]{3,4}-[0-9]{6,8}$/;
		if(!pattern.test(val)){
			addFieldError(elem,'请输入正确的'+field);
			return false;
		}
		return true;		
	}		
}

 /**
 * 29、校验Email地址是否有效
 *	elem：Email地址输入框
 */
function checkEmail(elem){	
	return checkEmailByField(elem,"Email");
}

function checkEmailByField(elem,field){	
    if(isBlank(elem.value)) return true;
    var reEmail =/[\w-.]+@{1}[\w-]+\.{1}\w{2,4}(\.{0,1}\w{2}){0,1}/ig;
    if(reEmail.test(elem.value)) {
        return true;
    }
    else {
        addFieldError(elem.name,field+'格式不对，请输入正确的'+field+'地址！');
        return false;
    }
}


/**
*  32、判断输入框中的数值是否在指定范围内（如：0-100），包含最大值，最小值，会先做为空判断
*  min：最小值
*  max：最大值
*/
function checkNumRange2(elem,field,min,max){
	if(!checkNum(elem,field)){
		return false;
	}

	val = elem.value;
	if(val<min || val>max){
		addFieldError(elem,field+"只能是"+min+"~"+max+"范围内的数值！");
		return false;
	}
	return true;
}

/**
*  33、判断输入框中只能输入数字
*/
function checkNumber(elem,field){
	val=elem.value;
	var pattern=/^[0-9]/;
	if(!pattern.test(val)){
		addFieldError(elem.name,field+"只能输入数字！");
		elem.value = '';
		return false;
	}
	return true;
}

 /**
 * 37、校验邮政编码是否有效
 *	elem：邮政编码输入框
 */
function checkPostalCode(elem){	
    if(isBlank(elem.value)) return true;
    var pattern = /^[0-9]{6}$/;
    if(pattern.test(elem.value)) {
        return true;
    }else {
   	 	addFieldError(elem.name,'邮政编码格式不对，请输入正确的邮政编码！');
        return false;
    }
}


/*
 *38、web地址格式验证（http://IP格式 或 http://域名格式）
 */
function checkwebaddress(elem,field){
	var val=trim(elem.value);
	if(val==''){
		return true;
	}
	var pattern=/^http:\/\/$/;
	if(!pattern.test(val.substring(0,7))){
		addFieldError(elem.id,field+'请确认符合http://IP格式或http://域名格式');
		return false;
	}
	return true;
}

/*
 *39、是否为有效字符
 */
function checkInvalidChar(obj){
	var pattern = /[\<\>\&~!@#$%|]/;
	if(pattern.test(obj.value)){
		addFieldErrorWithObject(obj,"不能有<>~!@#$%&|等字符");
		return false;
	}
	return true;
}

function checkPicFile(elem, picMaxSize){
	var src = elem.value;
	if(src=="" || src==null) return true;
	
	//var fso = new ActiveXObject("Scripting.FileSystemObject");
	var fso = new Image();
	var picfile;
	try{
		fso.src = src;
//		picfile = fso.getfile(src);
	}catch(e){
		addActionError("打开文件 "+src+" 错误，请检查是否存在！");
		elem.value = "";
		return false;
	}
	
	clearMessages();	
	var filesize = (fso.fileSize)/1024;
	if(filesize>parseInt(picMaxSize)){
		addActionError("您选择的文件大小超过了最大允许上传值" + picMaxSize +"K，无法上传！");
		elem.value = "";
		return false;
	}
	
	clearMessages();
	return true;
}

String.prototype.trim=function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim=function(){
        return this.replace(/(^\s*)/g,"");
}
String.prototype.rtrim=function(){
        return this.replace(/(\s*$)/g,"");
}


/**
*41、表单中所有输入框的内容去掉前后空格
*/
function trimAllElementsToSave(form){
	var formMap = new Array();
	var _len = form.elements.length;
	for(var i = 0; i < _len; i ++){
		var child = form.elements[i];
		if(!child)
			continue;
		if(child.tagName == 'INPUT'){
			var type = child.type.toLowerCase();
			if(type == 'text' || type == 'hidden')
				child.value = trim(child.value);
		}
		else if(child.tagName == 'TEXTAREA')
			child.value = trim(child.value);
	}
}

function checkParsePlusInt(v){
	v = v.trim();
	if(v.length > 0){
		var p = /(^\+?[0-9]+$)|(^\-[0-9]+$)/;
		return p.test(v);
	}
	else{
		return false;
	}
}

function checkParseFloat(v, dl){
	v = v.trim();
	if(v.length > 0){
		if(!dl){
			var p = /(^\+?\d+\.\d+$)|(^\+?\d+$)|(^\-\d+\.*\d+$)|(^\-\d+$)/;
			return p.test(v);
		}
		else{
			var p_ = "(^\\+?\\d+\\.\\d{1," + dl + "}$)|(^\\+?\\d+$)|(^\\-\\d+\\.*\\d{1," + dl + "}$)|(^\\-\\d+$)";
			var rg = new RegExp(p_);
			return rg.test(v);
		}
	}
	return false;
}

/**
 * 验证查询条件规则
 * @param elem
 * @param elemName
 * @return
 */
function checkQuery(elem, elemName){
	if(!elemName){
		elemName = "";
	}
	var p = /['%_^|\/\]\[*]/;
	if (p.test(elem.value)){
		addFieldError(elem, elemName + "内容不能包含以下任何字符: '%_^|/][*");	
		return false;
	}
	return true;
}
/**
 * 获取一个数字的小数位长度
 * param n 数字
 */
function precision(n) {
	var nArr = (n + "").split(".");
 	return nArr && nArr[1] ? nArr[1].length : 0;
}
/**
 * 验证各子项数据相加后是否小于等于总计,可传入多个参数
 * 参数1是总计的值
 * 参数2是运算符 > >= < <= =
 * 参数3是子项的数值
**/
function checkNumCount(){
	if(arguments && arguments.length >= 3){
		var $one = jQuery(arguments[0]);
		var $operator = arguments[1];
		var otherLen = arguments.length - 2;
		if($one && !isNaN($one.val()) && /^(<=?|>=?|==)$/.test($operator)){
			var msgNames = [];
			var leftV = parseFloat($one.val());
			var rightV = [];
			var maxPrecision = precision(leftV);
			for(var i=2;i<arguments.length;i++){
				var $other = jQuery(arguments[i]);
				var otherV = parseFloat($other.val());
				if($other && !isNaN(otherV)){
					msgNames.push($other.attr("msgName"));
					rightV.push(otherV);
					maxPrecision = precision(otherV) > maxPrecision ? precision(otherV) : maxPrecision;
				}
			}
			var expression = (Math.pow(10, maxPrecision) * leftV).toFixed(0) + "" + $operator;;
			for(var i=0;i<rightV.length;i++){
				expression += (Math.pow(10, maxPrecision) * rightV[i]).toFixed(0) + "+";
			}
			expression = expression.replace(/\+$/, "");
			var result = eval(expression);
			if(!result){
				addInterLockFieldError($one[0], $one.attr("msgName") + " 必须" + $operator.replace(/>/, "大于").replace(/</, "小于").replace(/==?/, "等于") + " " + msgNames.join("、 ") + (msgNames.length > 1 ? " 之和！" : "") );
				return false;
			}else{
				return true;
			}
		}
	}
	throw("内部错误");
}
//
function checkCompare(one, operator, others){
	var $one = jQuery(one);
	var $others = jQuery(others);
	//{gt : ">"; gte : ">="; lt : "<"; lte : "<="; eq : "="}
	var $operator = operator.replace(/^eq$/, "==").replace(/^(gt)(e?)$/, ">$2").replace(/^(lt)(e?)$/, "<$2").replace(/^(>?|<?)e$/, "$1=");
	//存在 比较1、比较符号、比较2...才能进行比较
	if($one && $one.length > 0 && $others && $others.length > 0 && /^(<=?|>=?|==)$/.test($operator)){
		var argumentsArr = [$one, $operator];
		for(var i=0;i<$others.length;i++){
			argumentsArr.push($others[i]);
		}
		return checkNumCount.apply(this || window, argumentsArr);
	}
}

//检查相互关联的项
function checkAllInterLock(containerName){
	var tgs = ["INPUT"];
	var len = tgs.length;
	for(var j = 0; j < len; j ++){
		if(containerName){
			if(typeof(containerName) ==  "string"){
				var os = jQuery(containerName + " " + tgs[j]);
			}else{
				len = 1;//针对单一确定元素
				var os = jQuery(containerName);
			}
		}
		else{
			var os = jQuery(tgs[j]);
		}
		if(os){
			for(var i = 0; i < os.length; i ++){
				var o = os[i];
				if(o.value.trim().length > 0){
					var osName = jQuery(o).attr("msgName");
					if(!osName){
						osName = "";
					}
					var dataType = jQuery(o).attr("dataType");
					if (!dataType){
						dataType = jQuery(o).attr("datatype");
					}
					if(dataType && (dataType == "integer" || dataType == "float")){
						//比较大小
						var gt = jQuery(o).attr("gt");
						var gte = jQuery(o).attr("gte");
						var lt = jQuery(o).attr("lt");
						var lte = jQuery(o).attr("lte");
						var eq = jQuery(o).attr("eq");
						var eq1 = jQuery(o).attr("eq1");
						var eq2 = jQuery(o).attr("eq2");
						if(gt && !checkCompare(o, "gt", gt)){
							return false;
						}
						if(gte && !checkCompare(o, "gte", gte)){
							return false;
						}
						if(lt && !checkCompare(o, "lt", lt)){
							return false;
						}
						if(lte && !checkCompare(o, "lte", lte)){
							return false;
						}
						if(eq && !checkCompare(o, "eq", eq)){
							return false;
						}
						if(eq1 && !checkCompare(o, "eq", eq1)){
							return false;
						}
						if(eq2 && !checkCompare(o, "eq", eq2)){
							return false;
						}
					}
					//radio不能为空
					var notNull = jQuery(o).attr("notnull");
					if(jQuery(o).is(":radio") && notNull && (notNull.toLowerCase() == "yes" || notNull == "1" || notNull.toLowerCase() == "true" || notNull.toLowerCase() == "notnull")) {
						
						var name = jQuery(o).attr("name");
						var checked = false;
						jQuery(":radio[name='"+name+"']").each(function(i,e){
							checked = checked || jQuery(e).prop("checked");
						});
						if(!checked){
							addInterLockFieldError(o, osName + "不能为空！请选择");
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}

/**
* 统一校验输入内容长度以及是否为空，是否可以为空，是否超出最大长度
* (如果输入的全部是空格，那么将内容设置为空)
* input、select、textarea三种类型进行判断，如果需要控制最大长度，需要设置 maxLength或者maxlength的值，一个汉字以两个字符算，如最大可以输入 10个汉字的，直接设置 maxLength="20"即可；
如果不能为空的，设置属性 notNull="true"即可；
如果输入的内容都是空格，那么调用这个方法后，会将内容调整为空串。
 * containerName 检测的选择器 focus 是否焦点 scrollContainerName 出现滚动条的div(默认是整个页面) 
 * @return
 */
function checkAllValidate(containerName,focus,scrollContainerName){
	if(typeof(focus) == "undefined"){
		focus=true;
	}
	var notValidate = false;
	var offsetTop=0;
	var isFirst=false;
	var tgs = ["INPUT:not(:file)", "SELECT", "TEXTAREA"];
	var len = tgs.length;
	for(var j = 0; j < len; j ++){
		if(containerName){
			if(typeof(containerName) ==  "string"){
				var os = jQuery(containerName + " " + tgs[j]);
			}else{
				len = 1;//针对单一确定元素
				var os = jQuery(containerName);
			}
		}
		else{
			var os = jQuery(tgs[j]);
		}
		if(os){
			for(var i = 0; i < os.length; i ++){
				var o = os[i];
				var osName = o.getAttribute("msgName");
				if(!osName){
					osName = o.getAttribute("msgname");
				}
				if(!osName){
					osName = "";
				}
				//清除focus框
				if (o.tagName == "SELECT") {
					o.style.color = "";
				} else {
					if($(o).hasClass('ui-select-txt')){
						$(o).parent()[0].style.borderColor = "";
					}else{
						o.style.borderColor = "";
					}
				}
						
				//if(o.value.trim() == ""){
					o.value = trim(o.value);
				//}
				
				var disabled = jQuery(o).prop("disabled");
				if(disabled){
					continue;
				}
				var notNull = o.getAttribute("notNull");
				if(!notNull){
					notNull = o.getAttribute("notnull");
				}
				if(notNull && (notNull.toLowerCase() == "yes" || notNull == "1" || notNull.toLowerCase() == "true" || notNull.toLowerCase() == "notnull")){
					var v = o.value;
					if(!v || v.trim() == ""){						
						addFieldError(o, osName + " 不能为空！");
						notValidate = true;
					}else{
						//模拟下拉框
						if($(o).hasClass('ui-select-txt') && $(o).siblings(".ui-select-value").val() == ""){
							addFieldError(o, osName + " 不能为空！");
							notValidate = true;
						}
					}
				}
				
				if(o.value.trim().length > 0){
					var maxLength = o.getAttribute("maxLength");
					if(!maxLength){
						maxLength = o.getAttribute("maxlength");
					}
					if(maxLength && maxLength > 0 && maxLength < 100000){
						var valueLength = _getLength(o.value);
						if(valueLength > maxLength){
							addFieldError(o, osName + " 长度为" + valueLength + "个字符，超出了最大长度限制：" + maxLength + "个字符");
							notValidate = true;
						}
					}
					
					var regex = o.getAttribute("regex");
					if(regex){
						var msg = o.getAttribute("regexMsg");
						if (regex.indexOf("/") == 0){
							regex = regex.substring(1);
						}
						if(regex.indexOf("/") == regex.length - 1){
							regex = regex.substring(0, regex.length - 1);
						}
						var re = new RegExp(regex);
						if (!re.test(o.value)){
							if(msg){
								addFieldError(o, msg);
								notValidate = true;
							}
							else{
								addFieldError(o, osName + " 不正确！");
								notValidate = true;
							}
						}
					}
					
					var validateScript = o.getAttribute("validateScript");
					if(!validateScript){
						validateScript = o.getAttribute("validatescript");
					}
					if(validateScript){
						var ret = true;
						try{
							ret = eval(validateScript)(o.value.trim());
						}
						catch(e){
							try{
								ret = eval(validateScript + "(" + o.value.trim() + ");");
							}
							catch(ee){
							}
						}
						
						
						if(!ret || ret == false){
							//方法中自定义错误消息
							//addFieldError(o, osName + " 不正确！");
							notValidate = true;
						}
					}
					
					var dataType = o.getAttribute("dataType");
					if (!dataType){
						dataType = o.getAttribute("datatype");
					}
					if(dataType && (dataType == "integer" || dataType == "float")){
						var max = o.getAttribute("maxValue");
						var min = o.getAttribute("minValue");
						var integerLength = o.getAttribute("integerLength");
						var nonnegative = o.getAttribute("nonnegative");
						var ch = false;
						if(dataType == "integer"){
							if(dl) dl=null;
							if(checkParsePlusInt(o.value) == true){
								ch = true;
								if(!max || !checkParsePlusInt(max)){
									max = null;
								}
								if(!min || !checkParsePlusInt(min)){
									min = null;
								}
							}
						}
						else if(dataType == "float"){
							var dl = o.getAttribute("decimalLength");
							if(!dl){
								dl= o.getAttribute("decimallength");
							}
							
							if(checkParseFloat(o.value, dl)== true){
								ch = true;
								if(!max || !checkParseFloat(max)){
									max = null;
								}
								if(!min || !checkParseFloat(min)){
									min = null;
								}
							}
						}
						if(ch){
							o.value = parseFloat(o.value);
							if(max){
								if(o.value > parseFloat(max)){
									addFieldError(o, osName + " 值不能超过" + max);
									notValidate = true;
								}
							}
							if(min){
								if(o.value < parseFloat(min)){
									addFieldError(o, osName + " 值不能小于" + min);
									notValidate = true;
								}
							}
							if(integerLength){
								var beginIndex=0;
								if(o.value.indexOf("+")>=0 || o.value.indexOf("-")>=0){
									beginIndex=1;
								}
								var v;
								if (o.value.indexOf(".") >= 0){
									v = o.value.substring(beginIndex, o.value.indexOf("."));
								}
								else{
									v = o.value.substring(beginIndex, o.value.length);
								}
								if(v.length > integerLength){
									if (dl){
										addFieldError(o, osName + " 输入格式不对，要求是不超过" + integerLength + "位整数，" + dl + "位小数的数值");
										notValidate = true;
									}
									addFieldError(o, osName + " 输入格式不对，要求是不超过" + integerLength + "位整数位的数值");
									notValidate = true;
								}
							}
							if(nonnegative){
								if(nonnegative && (nonnegative.toLowerCase() == "yes" || nonnegative == "1" || nonnegative.toLowerCase() == "true" )){
									if(o.value.indexOf("-")>=0){
										addFieldError(o, osName + " 输入格式不对，只能输入非负数值型数据");
										notValidate = true;
									}
								}
							}
						}
						else{
							if (dl)
								addFieldError(o, osName + " 输入格式不对，要求是不超过" + dl + "位小数的数值");
							else if(dataType == "integer")
								addFieldError(o, osName + " 输入格式不对，只能输入整数");
							else 
								addFieldError(o, osName + " 输入格式不对");
							notValidate = true;
						}
					}
				}
				if(notValidate && !isFirst){
					isFirst=true;
					offsetTop = $(o).offset().top;
				}
			}
		}
	}
	if(notValidate && focus){
		if(scrollContainerName){
			$(scrollContainerName).scrollTop(offsetTop);
		}else{
			$(window).scrollTop(offsetTop);
		}
	}
	return !notValidate;
}
	
	
	
	