/**
 * 全局播放器
 * @type {null}
 */
var app_global_player = null;
var app_iframe_arr = [];

var app_global_resLibMgr = null;
var app_global_bookLibMgr = null;
var app_global_searchMgr = null;
var app_global_classPresentationMgr=null;

var app_global_bookContainer = null;

var app_global_loginMgr=null;

/**
 * 注册全局函数
 */
InjectGlobal();

function InjectGlobal() {
    _injectStringMethod();
}



function setGlobalContextResLibMgr(resLibMgr) {
    app_global_resLibMgr = resLibMgr;
}

function setGlobalContextBookLibMgr(bookLibMgr) {
    app_global_bookLibMgr = bookLibMgr;
}


function setGlobalContextSearchMgr(searchMgr) {
    app_global_searchMgr = searchMgr;
}

function setGlobalContextClassPresentaionMgr(classPresentaionMgr) {
    app_global_classPresentationMgr = classPresentaionMgr;
}

function setGloabalContextPlayer(player) {
    app_global_player = player;
}

function getGlobalContextPlayer() {
    return app_global_player;
}

function getGlobalBookContainer(){
    return app_global_bookContainer;
}

function setGlobalBookContainer(bookContainer){
    app_global_bookContainer = bookContainer;
}


function setGlobalLoginMgr(loginMgr){
    app_global_loginMgr= loginMgr;
}

/**
 * 注册客户端消息发送的对象(iframe)
 * @param {string} iframeId
 * @param targetUrl :postmessage的 target 域名
 */
function registerIframe(iframeId, targetUrl) {
    app_iframe_arr.push([iframeId, targetUrl]);
}

/**
 * 反注册客户端消息发送的对象
 * @param iframeId
 */
function unregisterIframe(iframeId) {
    app_iframe_arr.filter(function (ele) {
        return ele[0] != iframeId;
    });
}

function _injectStringMethod() {
    //添加format方法
    if (!String.prototype.format) {
        String.prototype.format = function () {
            var args = arguments;
            return this.replace(/{(\d+)}/g, function (match, number) {
                return typeof args[number] != 'undefined'
                    ? args[number]
                    : match
                    ;
            });
        };
    }
    // 添加startWith 方法
    if (typeof String.prototype.startsWith != 'function') {
        // see below for better implementation!
        String.prototype.startsWith = function (str) {
            return this.indexOf(str) == 0;
        };
    }
    //添加endWith 方法
    if (typeof String.prototype.endsWith != 'function') {
        String.prototype.endsWith = function (str) {
            return this.slice(-str.length) == str;
        };
    }
}

/**
 * 调用客户端命令
 * @param {string} cmdRegion 模块名称
 * @param {string} cmdName 命令名
 * @param {string} cmdArgs 多个参数，参数间使用|分隔
 * @param {string=} cmdType 命令类别
 * @returns {string}
 */
function callClient(cmdRegion, cmdName, cmdArgs, cmdType, callback) {
    var funcName = cmdRegion + '.' + cmdName;
    if (callback) {
        var wrapper=function(name,args){
            //删除注册的函数
            //cef.message.removeMessageCallback(funcName, this);
            callback(args[0]);
            //console.log('name:',name,'args:',args);
        };
        cef.message.sendMessage(funcName, [cmdArgs, cmdType.toString()]);
        cef.message.setMessageCallback(funcName, wrapper);
    } else {
        //无回调
        cef.message.sendMessage(funcName, [cmdArgs, cmdType.toString()]);
    }
}

/**
 * 客户端调用页面函数名
 * @param msg是个json 结构为 {evt:eventname,data:somedata}
 */
function onClientInvoked(msg) {
    //console.log('onClientInvoked:'+ msg);

    if (!msg) return;
    var obj = JSON.parse(msg);

    if (obj) {
        if (obj.evt.startsWith('play_') && app_global_player) {
            app_global_player.onMessageReceived(obj);
        }
        if (obj.evt.startsWith('file_download')) {
            if (obj.data.sourcepage == 'reslib' && app_global_resLibMgr) {
                app_global_resLibMgr.onMessageReceived(obj);
            } else if ((obj.data.sourcepage == 'booklib' ||obj.data.sourcepage == 'booklibres')&&app_global_bookLibMgr) {
                app_global_bookLibMgr.onMessageReceived(obj);
            } else if (obj.data.sourcepage == 'searchresult'&&app_global_searchMgr ) {
                app_global_searchMgr.onMessageReceived(obj);
            }else if(obj.data.sourcepage=='classpresentation' && app_global_classPresentationMgr){
                app_global_classPresentationMgr.onMessageReceived(obj);
            }

        }
        if(obj.evt.startsWith('change_pageIndex')){
            if(typeof obj.data.index =="number"){
                app_global_bookContainer.onMessageReceived(obj);
            }
        }
        if(obj.evt.startsWith('get_pageIndex')){
            app_global_bookContainer.onMessageReceived(obj);
        }
        if(obj.evt.startsWith('get_openedWidgetId')){
            app_global_bookContainer.onMessageReceived(obj);
        }
        if(obj.evt=="checknetaccess" || obj.evt=="tryonlinelogin") {
            app_global_loginMgr.onMessageReceived(obj);
        }
        if(obj.evt.startsWith('saveResAndPaint')){
           app_global_bookContainer.onMessageReceived(obj);
        }

        if(obj.evt.startsWith('videocap')) {
           app_global_bookContainer.onMessageReceived(obj);
        }

    }
    dispatchClientMsg(msg);
}

/**
 *
 */
function dispatchClientMsg(msg) {
    for (var i = 0; i < app_iframe_arr.length; i++) {
        try {
            var iframe = document.getElementById(app_iframe_arr[i][0]);
            iframe.contentWindow.postMessage(msg, app_iframe_arr[i][1]);
        }
        catch (e) {
            app_iframe_arr.splice(i--, 1);    //  删除出错的iframe
        }
    }
}

//是否在壳内运行，而不是在真实的浏览器中运行
function isShellRunning() {
    return typeof cef !== 'undefined';
}

/**
 *
 * @param object
 * @returns {boolean}
 * @constructor
 */
function isString(object) {
    if (!object)
        return false;
    return object.constructor === String;
}
/**
 * 用以显示语音卡片中的Flash对象
 */
function showFlash() {
    dispatchClientMsg({evt: 'showFlash'});
}

/**
 *  默认的基准值分辨率
 */
var base_resolution={
    WIDTH:1440,
    HEIGHT:900
}


/**
 *  * 参照一个基准分辨率进行放缩，基准值是  1440,900
 * @param {object} obj
 * @param {boolean=} scaleFromCentre  是否从中心点缩放
 * @returns {Array}  放缩矩阵
 */
function correctToBaseResolution(obj,scaleFromCentre)
{
    if(!obj) return null;
    var scale=[1,0,0,1,0,0];

    scaleFromCentre=scaleFromCentre==undefined?true:scaleFromCentre;
    var objArray= $.makeArray(obj);
    scale[0]= screen.width/base_resolution.WIDTH*1.2;
    scale[3]= screen.height/base_resolution.HEIGHT*1.2;

    scale[0]=scale[0]>=1?1:scale[0];
    scale[3]=scale[3]>=1?1:scale[3];

    $.each(objArray,function(i,obj){
        obj=$(obj);
        if(!scaleFromCentre)
        {
           scale[4]=-(1-scale[0])*obj.outerWidth()/2;
           scale[5]=-(1-scale[3])*obj.outerHeight()/2;
        }
        obj.css('-webkit-transform','matrix(' + scale.join(',') + ')');
    });

    return scale;
}

/**
 * 加载提示对话框
 * @param {object=} options  {tips:提示内容，parent:对话框父元素}
 */

 function showLoading(options)
 {
       hideLoading();
       options=options||{};

       var loadLeft= 0,loadTop= 0,loadWidth=$(window).width() ,loadHeight=$(window).height();
       if(!options.tips) options.tips="正在加载，请稍侯……";
       if(options.parent)
       {
           if(!(options.parent instanceof jQuery)) options.parent=$(options.parent);
           var offset=options.parent.offset();
           loadLeft =offset.left;
           loadTop=offset.top;
           loadWidth=options.parent.outerWidth();
           loadHeight=options.parent.outerHeight();
       }

       var loadingContent=$('<div style="position: absolute;opacity: 0.9;z-index: 10000;" id="loading_tip"> ').
           css({left:loadLeft,top:loadTop,width:loadWidth,height:loadHeight});
       var loadingBox=$('<div class="loading_box" style="position: absolute"> ' +
            ' <div class="pic"><img src="assert/images/loading.gif"></div> '+
            ' <div class="zi">' +
                options.tips+
            '</div>  '  +
             '</div></div>');
        loadingContent.append(loadingBox);
        $('body').append(loadingContent);
         loadingBox.css({
         position: 'absolute',
         left: (loadWidth - loadingBox.outerWidth()) / 2,
         top: (loadHeight - loadingBox.outerHeight()) / 2 + $(document).scrollTop()
         });
 }

 function hideLoading()
 {
     $("#loading_tip").remove();
 }



