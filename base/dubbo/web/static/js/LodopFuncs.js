var CreatedOKLodop7766=null;

function docType() {
        var standard = " Trasitional";
        var dtd = "loose";
        return '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01' + standard + '//EN" "http://www.w3.org/TR/html4/' + dtd + '.dtd">';
}
    
function getHead() {
        var head = "<head><title></title>";
        jQuery(document).find("link")
            .filter(function () {
                return jQuery(this).attr("rel").toLowerCase() == "stylesheet";
            })
            .filter(function () { 
                var media = jQuery(this).attr("media");
                if (media == undefined) {
                    return true;
                }
                else {
                    return (media.toLowerCase() == "" || media.toLowerCase() == "all" || media.toLowerCase() == "print");
                }
            })
            .each(function () {
                head += '<link type="text/css" rel="stylesheet" href="' + jQuery(this).attr("href") + '" >';
            });
        head += "<style>.noprint{display:none;}</style></head>";
        return head;
    }

    function getBody(printElement, dClass) {
    	//一些对打印有影响的样式，需要去除掉
    	var ss = "";
    	if(dClass){
    		for(c = 0; c < dClass.length; c ++){
    			var objs = jQuery("." + dClass[c]);
    			objs.removeClass(dClass[c]);
    			ss = printElement.html();
    			objs.addClass(dClass[c]);
    		}
    	}
    	if(ss == ""){
    		ss = printElement.html();
    	}
        ss = '<body>' + ss + '</body>';
        return ss;
    }
    
function getPrintContent(printElement, disableclasses){
	return docType() + "<html>" + getHead() + getBody(printElement, disableclasses) + "</html>";
}

function getLodop(oOBJECT,oEMBED,contextPath){
/**************************
  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
  IE系列、IE内核系列的浏览器采用oOBJECT，
  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
  64位浏览器指向64位的安装程序install_lodop64.exe。
**************************/
        var contextPath = contextPath === undefined ? _contextPath : contextPath;//需要重构contextPath，由方法参数传递
        var strHtmInstall="<font color='#FF00FF'>打印控件未安装!点击这里 <a href='" + contextPath + "/static/downfolder/install_print32.exe' target='_self'>执行安装(install_print32)</a>, 安装后请刷新页面或重新进入。</font>";
        var strHtmUpdate="<font color='#FF00FF'>打印控件需要升级!点击这里 <a href='" + contextPath + "/static/downfolder/install_print32.exe' target='_self'>执行升级(install_print32)</a>, 升级后请重新进入。</font>";
        var strHtm64_Install="<font color='#FF00FF'>打印控件未安装!点击这里 <a href='" + contextPath + "/static/downfolder/install_print64.exe' target='_self'>执行安装(install_print64)</a>, 安装后请刷新页面或重新进入。</font>";
        var strHtm64_Update="<font color='#FF00FF'>打印控件需要升级!点击这里 <a href='" + contextPath + "/static/downfolder/install_print64.exe' target='_self'>执行升级(install_print64)</a>, 升级后请重新进入。</font>";
        var strHtmFireFox="<font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
        var strHtmChrome="<font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
        var LODOP;		
	try{	
	     //=====判断浏览器类型:===============
	     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
	     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
	     //=====如果页面有Lodop就直接使用，没有则新建:==========
	     if (oOBJECT!=undefined || oEMBED!=undefined) { 
               	 if (isIE) 
	             LODOP=oOBJECT; 
	         else 
	             LODOP=oEMBED;
	     } else { 
		 if (CreatedOKLodop7766==null){
          	     LODOP=document.createElement("object"); 
		     LODOP.setAttribute("width",0); 
                     LODOP.setAttribute("height",0); 
		     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
		     else LODOP.setAttribute("type","application/x-print-lodop");
		     document.documentElement.appendChild(LODOP); 
	             CreatedOKLodop7766=LODOP;		     
 	         } else 
                     LODOP=CreatedOKLodop7766;
	     };
	     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
	     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
	             if (navigator.userAgent.indexOf('Chrome')>=0)
	             	showMsgWarn(strHtmChrome, null, null, -1);
	             if (navigator.userAgent.indexOf('Firefox')>=0)
	             	showMsgWarn(strHtmFireFox, null, null, -1);
	             if (is64IE) 
	             	showMsgWarn(strHtm64_Install, null, null, -1); 
	             else if (isIE)   
	             	showMsgWarn(strHtmInstall, null, null, -1);  
	             else
	                 showMsgWarn(strHtmInstall, null, null, -1);
	             return LODOP;
	     } else 
	     if (LODOP.VERSION<"6.1.9.4") {
	             if (is64IE) document.write(strHtm64_Update); else
	             if (isIE) document.write(strHtmUpdate); else
	             document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
	    	     return LODOP;
	     };
	     //=====如下空白位置适合调用统一功能(如注册码、语言选择等):====	     


	     //============================================================	     
	     return LODOP; 
	} catch(err) {
	     if (is64IE)	
            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
	     return LODOP; 
	};
}
