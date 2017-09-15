//需要先引用weike.js storage.js
//用了判断是否是从微课接入
var WeikeConstants = {
	WEIKE_FLAG_KEY : "weike_flag_key",
	WEIKE_FLAG_VALUE_TYPE_1 : "1",//旧版对接、微办公
	WEIKE_FLAG_VALUE_TYPE_2 : "2",//新版对接，微课工作界面
}

$(function(){
	//contextPath会在html请求服务器时添加， 过滤器
	var Request = new UrlSearch();
	var _mobileContextPath = Request.contextPath;
	var _unitId = Request.unitId;
	var _userId = Request.userId;
	var _userName = Request.userName;
	var _weike = Request.weike;
	
	if(null==_mobileContextPath || ""==_mobileContextPath || undefined==_mobileContextPath){
		_mobileContextPath = "";
	}
	storage.set(Constants.MOBILE_CONTEXT_PATH, _mobileContextPath);
	
	if(null!=_unitId && ""!=_unitId && undefined!=_unitId){
		storage.set(Constants.UNIT_ID, _unitId);
	}
	if(null!=_userId && ""!=_userId && undefined!=_userId){
		storage.set(Constants.USER_ID, _userId);
	}
	if(null!=_userName && ""!=_userName && undefined!=_userName){
		var _userName = decodeURI(_userName);
		storage.set(Constants.USER_REALNAME, _userName);
	}
	if(2==_weike){//新版微课模块首页   此页面返回需要返回到微课工作界面
		storage.set(WeikeConstants.WEIKE_FLAG_KEY, WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2);
		initWieikeGoBack();
	}
	
	
	var flag = storage.get(WeikeConstants.WEIKE_FLAG_KEY); 
	if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_1 == flag 
			|| WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == flag){
		if (weikeJsBridge) {
			weikeJsBridge.windowClose(".html-window-close");//绑定关闭事件
		}
//		$(".abtn-mail").hide();//邮件隐藏
	}else{
		$(".html-window-close").hide();
	}
});

//微课返回方法
function wkGoBack(){
	if($(".html-window-back").length > 0){//有返回事件  则触发返回事件
		$(".html-window-back").click();
	}else{//否则触发关闭事件
		if($(".html-window-close").length > 0){
			$(".html-window-close").click();
		}
	}
}

//微课新版工作返回方法--旧版微办公返回到微办公界面而新版工作上的办公模块  则需要返回到微课界面
function initWieikeGoBack(){
	var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
	if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接 返回则退回到微课界面
		if($("#cancelId")){
			$("#cancelId").removeClass("html-window-back");
		}
		if($("#back")){
			$("#back").removeClass("html-window-back");
		}
	}else{
		if(!$("#cancelId").hasClass("html-window-back")){
			$("#cancelId").addClass("html-window-back");
		}
		if(!$("#back").hasClass("html-window-back")){
			$("#back").addClass("html-window-back");
		}
	}
}

//移动OA返回方法
function back(){
	if($(".html-window-back").length > 0){//有返回事件  则触发返回事件
		$(".html-window-back").click();
	}
}
