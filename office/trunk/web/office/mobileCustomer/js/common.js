var _msgTipLayer = '#msgTipLayer';
var _msgTipContent = '#msgTipContent';
var _msgTipHidden = '#msgTipHidden';
var _msgTipBtn = '#msgTipBtn';
var _layerSubmit = '#layerSubmit';
var _layerCancel = '#layerCancel';

var _toastAutoLayer = '#toastAutoLayer';
var _toastTipHidden = '#toastTipHidden';
var _toastTipContent = '#toastTipContent';

$(function(){
	if($("#page")){
		//对话框
		var htmlStr = '<div id="msgTipLayer" class="ui-layer" style="display:none;">';
		htmlStr += '<div id="msgTipContent" class="wrap f-14"></div>';
		htmlStr += '<div id="msgTipBtn">';
		htmlStr += '<p class="fn-flex fn-flex-row dd f-14">';
		htmlStr += '<a href="javascript:void(0)" id="layerSubmit" class="fn-flex-auto close">确定</a>';
		htmlStr += '<a href="javascript:void(0)" id="layerCancel" class="fn-flex-auto close">取消</a>';
		htmlStr += '</p></div></div>';
		htmlStr += '<input type="hidden" id="msgTipHidden"/>';
		
		//吐司
		htmlStr += '<div class="ui-layer-auto" id="toastAutoLayer">';
		htmlStr += '<p class="txt f-16"><span id="toastTipContent"></span></p>';
		htmlStr += '</div>';
		htmlStr += '<input type="hidden" id="toastTipHidden"/>';
		$("#page").after(htmlStr);
		
		layer(_msgTipHidden, _msgTipLayer,'.close',"提示");
		//吐司
		autoLayer(_toastTipHidden, _toastAutoLayer, 0, '.close', '');
	}
});

//确定取消提示框，msg：提示信息，submitEvent：确定事件，cancelEvent取消事件（可不传）
function showMsg(msg, submitEvent, cancelEvent){
	$(_msgTipContent).html(msg);
	$(_msgTipHidden).trigger('touchstart');
	//解绑
	$(_layerSubmit).unbind();
	$(_layerCancel).unbind();
	$(_msgTipBtn).show();
	$(_layerSubmit).show();
	$(_layerCancel).show();
	//绑定事件
	$(_layerSubmit).on('click', function(e){
		closeMsgTip();
		if(submitEvent){
			e.preventDefault();
			submitEvent();
		}
	});
	
	$(_layerCancel).on('click', function(e) {
		closeMsgTip();
		if(cancelEvent){
			e.preventDefault();
			cancelEvent();
		}
	});
}

//确定提示框，msg：提示信息，submitEvent：确定事件(可不传)
function showMsgTip(msg, submitEvent){
	$(_msgTipContent).html(msg);
	$(_msgTipHidden).trigger('touchstart');
	
	$(_layerSubmit).unbind();
	$(_layerCancel).unbind();
	$(_msgTipBtn).show();
	$(_layerSubmit).show();
	$(_layerCancel).hide();

	//绑定事件
	$(_layerSubmit).on('click', function(e){
		closeMsgTip();
		if(submitEvent){
			e.preventDefault();
			submitEvent();
		}
	});
}

//加载中...
function showLoading(msg){
	$(_msgTipContent).html(msg);
	$(_msgTipHidden).trigger('touchstart');
	$(_msgTipBtn).hide();
}

//关闭弹框
function closeMsgTip(){
	$(_msgTipLayer).hide();
	$('.ui-layer-mask').remove();
}

//页面土司
function viewToast(msg, event){
	$(_toastTipContent).html(msg);
	$(_toastTipHidden).click();//('click');
	
	if(event){
		setTimeout(event, 2000);
	}
}

//校验是否可以提交  ture--校验通过 可以提交
function isActionable(obj) {
	if($(_msgTipLayer).is(":hidden") && $(_toastAutoLayer).is(":hidden")){
		return true;
	}else{
		return false;
	}
}