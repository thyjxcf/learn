/**
 * 1. 1/2/3数据验证
 * 2. 1或1,2或1,2,3,4,5,6数据格式验证（即：用“,”号分隔的1——6数字验证）
 * 3、1/2数据验证
 * 4、是否小于100000（十万）
 * 
 */


/*
 * 1、1/2/3数据验证
 */
function check123(elem,field){
	var val=trim(elem.value);
	var pattern=/^(1|2|3)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入1或2或3');
		elem.focus();
		return false;
	}
	return true;	
}


/*
 * 2、1或1,2或1,2,3,4,5,6数据格式验证（即：用“,”号分隔的1——6数字验证）
 */
function check123456CommaSplit(elem,field){
	var val=trim(elem.value);
	var len = val.length;
	var index = len;
	var value, flag=true;
	while(index>0){
		value = val.substring(index -1, index);
		//寄数位置是数字，偶数位置是“,”分隔符（如：1,2,3,4,5,6）
		if(index%2==1){
			if(value!='1' && value!='2' && value!='3' && value!='4' && value!='5'&& value!='6'){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的1——6数字');
				elem.focus();
				return false;
			}
		}else{
			if(value!=','){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的1——6数字');
				elem.focus();
				return false;
			}
		}

		index = index -1;	
	}
	return true;

}

/*
 * 2、1或1,2或1,2,数据格式验证（即：用“,”号分隔的1——2数字验证）
 */
function check12CommaSplit(elem,field){
	var val=trim(elem.value);
	var len = val.length;
	var index = len;
	var value, flag=true;
	while(index>0){
		value = val.substring(index -1, index);
		//奇数位置是数字，偶数位置是“,”分隔符（如：1,2,）
		if(index%2==1){
			if(value!='1' && value!='2'){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的1——2数字');
				elem.focus();
				return false;
			}
		}else{
			if(value!=','){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的1——2数字');
				elem.focus();
				return false;
			}
		}

		index = index -1;	
	}
	return true;

}

/*
 *3、1/2数据验证
 */
function check12(elem,field){
	var val=trim(elem.value);
	var pattern=/^(1|2)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入1或2');
		elem.focus();
		return false;
	}
	return true;	
}
/*
 *4、是否小于100000（十万）
 */
function checklt100thousand(elem,field){
	if(!checkPlusInt(elem,field))
		return false;
	val=elem.value;
	var thousand100=100000;
	if(val<thousand100){
		addFieldError(elem.id,field+'不能小于'+thousand100);
		elem.focus();
		return false;
	}
	return true;
}

/*
 *5、0/1数据验证
 */
function check01(elem,field){
	var val=trim(elem.value);
	var pattern=/^(0|1)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入0或1');
		elem.focus();
		return false;
	}
	return true;	
}


/**
 * 校验日期格式(MM-DD格式) addFieldError 返回boolean
 *	elem：日期输入框
 *  field：提示名称
 */
function checkDate_(elem,field){
	if(isBlank(elem.value))
		return true;
	var DateValue = "";
    var seperator = "-";
    var day;
    var month;
    var leap = 0;
    var err = 0;
    err = 0;
    DateValue = elem.value;
    var re = /[^0-9\-]+/gi;
    DateValue =  DateValue.replace(re, "");   //去除所有数字和'-'以外的字符
    var parts = DateValue.split('-');

    VALIDATION: {
        var len = parts.length;
        if(len != 2) {
            err = 19;
            break VALIDATION;
        }
        /* Validation of month*/
        month = parseInt(jsTrimLeadingZero(trim(parts[0])), 10);
        if ((isNaN(month))|| (month < 1) || (month > 12)) {
            err = 21;
            break VALIDATION;
        }
        /* Validation of day*/
        day = parseInt(jsTrimLeadingZero(trim(parts[1])), 10);
        if ((isNaN(day))|| (day < 1)) {
            err = 22;
            break VALIDATION;

        }
        if ((month == 2) && (leap == 1) && (day > 29)) {
            err = 23;
            break VALIDATION;
        }
        if ((month == 2) && (leap != 1) && (day > 28)) {
            err = 24;
            break VALIDATION;
        }
        if ((day > 31) && ((month == "01") || (month == "03") || (month == "05") || (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
            err = 25;
            break VALIDATION;
        }
        if ((day > 30) && ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
            err = 26;
            break VALIDATION;
        }
        if ((day == 0) && (month == 0) && (year == 00)) {
            err = 0; day = ""; month = ""; year = ""; seperator = "";
        }
    }
    if (err!= 0) {
    	addFieldError(elem, '要求'+ field + '的格式为mm-dd，并请注意月份与日期的有效范围');   
	return false;
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

/*
 * 1、1/2/3/4数据验证
 */
function check1234(elem,field){
	var val=trim(elem.value);
	var pattern=/^(1|2|3|4)$/;
	if(!pattern.test(val)){
		addFieldError(elem.id,field+'只能输入1或2或3或4');
		elem.focus();
		return false;
	}
	return true;	
}

/*
 *3、用“,”号分隔的数字1、2验证
 */
function check12CommaSplit(elem,field){
	var val=trim(elem.value);
	var len = val.length;
	var index = len;
	var value, flag=true;
	while(index>0){
		value = val.substring(index -1, index);
		//寄数位置是数字，偶数位置是“,”分隔符（如：1,2,3,4,5,6）
		if(index%2==1){
			if(value!='1' && value!='2'){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的数字1、2');
				elem.focus();
				return false;
			}
		}else{
			if(value!=','){
				addFieldError(elem.id,field+'只能输入用“,”号分隔的数字1、2');
				elem.focus();
				return false;
			}
		}

		index = index -1;	
	}
	return true;
}