function isNotBlank(str){
	if(typeof(str)!='undefined' && str!='undefined' && str != null && str != 'null' && str!=''){
		if("" != $.trim(str)){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}


//正整数  校验通过返回true
function validateInteger(num){
	var pattern = /^([1-9]|[1-9][\d]+)$/;
	return pattern.test(num);
}

//一位小数  校验通过返回true
function validateFloat(num){
	var pattern = /^((0|([1-9]|[1-9][\d]+))(\.\d)?)$/;
	return pattern.test(num);
}

//日期关系判断  1--参数1比参数2日期大
function validateDate(elem1,elem2){
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

//校验日期时间 -- true校验通过
function validateDatetime(elem1, elem2){
	var startTime=elem1.value;
	var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	var endTime=elem2.value;
	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	if(end<start){
	 	return false;
	}
	return true;
}
/** 
 * 计算两日期时间差 
 * @param   interval 计算类型：D是按照天、H是按照小时、M是按照分钟、S是按照秒、T是按照毫秒 
 * @param  date1 起始日期  格式为年月格式 为2012-06-20 
 * @param  date2 结束日期 
 * @return  
 */  
function countTimeLength(interval, date1, date2) {  
    var objInterval = {'D' : 1000 * 60 * 60 * 24, 'H' : 1000 * 60 * 60, 'M' : 1000 * 60, 'S' : 1000, 'T' : 1};  
    interval = interval.toUpperCase();  
    var dt1 = Date.parse(date1.replace(/-/g, "/"));  
    var dt2 = Date.parse(date2.replace(/-/g, "/"));  
    try{  
        return ((dt2 - dt1) / objInterval[interval]).toFixed(2);//保留两位小数点  
    }catch (e){  
        return e.message;  
    }  
} 
/**
 * 计算字符数(一个汉子占两个字符)
 * @param Str
 * @returns
 */
function  getStringLen(Str){  
	var i, len, code;
	if (Str == null || Str == "")
		return 0;
	len = Str.length;
	for (i = 0; i < Str.length; i++) {
		code = Str.charCodeAt(i);
		if (code > 255) {
			len++;
		}
	}
	return len;   
}
/**
 * b转mb
 * @param fileSize
 */
function getFileSize(fileSize){
	return (Math.round(fileSize * 100 / (1024 * 1024)) / 100).toString() + 'MB';
}

//中间截取
function subFileNameMiddle(str){
   var slength = str.length;
   if(slength>12){
	  var newstr = str.substring(0,4)+"..."+str.substring(slength-8); 
	  return newstr;
   }
   return str;
}

function getFileSize2(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
   return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
/**
 * 文件图标
 */
function getFilePic(extName){
	var htmlStr='';
	if(extName=='doc'||extName=='docx'){
		htmlStr='<span class="icon-img icon-class icon-word"></span>';
	}else if(extName=='csv'){
		htmlStr='<span class="icon-img icon-class icon-csv"></span>';
	}else if(extName=='xls'||extName=='xlsx'||extName=='xlsm'||extName=='xlsb'){
		htmlStr='<span class="icon-img icon-class icon-excel"></span>';
	}else if(extName=='jpg'||extName=='jpeg'||extName=='png'||extName=='bmp'){
		htmlStr='<span class="icon-img icon-class icon-jpg"></span>';
	}else if(extName=='mp3'||extName=='wma'||extName=='wav'){
		htmlStr='<span class="icon-img icon-class icon-music"></span>';
	}else if(extName=='pdf'){
		htmlStr='<span class="icon-img icon-class icon-pdf"></span>';
	}else if(extName=='ppt'||extName=='pptx'||extName=='pptm'){
		htmlStr='<span class="icon-img icon-class icon-ppt"></span>';
	}else if(extName=='rtf'){
		htmlStr='<span class="icon-img icon-class icon-rtf"></span>';
	}else if(extName=='txt'){
		htmlStr='<span class="icon-img icon-class icon-txt"></span>';
	}else if(extName=='avi'||extName=='rmvb'||extName=='wmv'||extName=='rm'||extName=='asf'||extName=='mpeg'){
		htmlStr='<span class="icon-img icon-class icon-zmove"></span>';
	}else{
		htmlStr='<span class="icon-img icon-class icon-other"></span>';
	}
	return htmlStr;
}