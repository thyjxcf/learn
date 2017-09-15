//需要先引用weike.js storage.js
//用了判断是否是从微课接入
var WeikeConstants = {
	WEIKE_FLAG_KEY : "weike_flag_key",
	WEIKE_FLAG_VALUE_TRUE : "true",
	WEIKE_FLAG_VALUE_FALSE : "false",
}

$(function(){
	var flag = storage.get(WeikeConstants.WEIKE_FLAG_KEY); 
	if(WeikeConstants.WEIKE_FLAG_VALUE_TRUE == flag){
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

//移动OA返回方法
function back(){
	if($(".html-window-back").length > 0){//有返回事件  则触发返回事件
		$(".html-window-back").click();
	}
}
