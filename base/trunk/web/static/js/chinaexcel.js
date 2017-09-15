var chinaExcelContainer;
var pageObj;
function enablePrintSave(obj,page){
	if(obj!=null){
		chinaExcelContainer=obj;
		jQuery("#chinaExcelPrintPreview").show();
		jQuery("#chinaExcelSave").show();
	}else{
		chinaExcelContainer=null;
		jQuery("#chinaExcelPrintPreview").hide();
		jQuery("#chinaExcelSave").hide();
	}
	pageObj = page;	
}
function enablePrint(obj,page){
	if(obj!=null){
		chinaExcelContainer=obj;
		jQuery("#chinaExcelPrintPreview").hide();
		jQuery("#chinaExcelSave").hide();
	}else{
		chinaExcelContainer=null;
		jQuery("#chinaExcelPrintPreview").hide();
		jQuery("#chinaExcelSave").hide();
	}
	pageObj = page;	
}
function chinaExcelPreview(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPreview();
	}else{
		showMsgWarn("未找到超级报表控件！");
	}
}

function chinaExcelPreview1(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPreview1();
	}else{
		showMsgWarn("未找到超级报表控件！");
	}
}

function chinaExcelPrint(obj){
	chinaExcelContainer = obj;
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelPrint();
	}else{
		showMsgWarn("未找到超级报表控件！");
	}
}

function chinaExcelSave(){
	if(pageObj != null){
		pageObj.queryChinaExcelData();
	}
	if(chinaExcelContainer!=null){
		chinaExcelContainer.chinaExcelSave();
	}else{
		showMsgWarn("未找到超级报表控件！");
	}
}