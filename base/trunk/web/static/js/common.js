var Zdsoft = Zdsoft || {};
var L = L || {};

/**
 * 与应用相关的数据及操作
 * @class Zdsoft.app
 * @static
 */
Zdsoft.app = (function(){

	var app = {};
	var _pageParam = {};

	/**
	 * 设置全局参数，等效于setPageParam函数
	 * @method s
	 * @param {String} key  参数名
	 * @param {Any} value   参数值
	 * @example
	 * 		Zdsoft.app.s("name", "ink");
	 * 		Zdsoft.app.s({ "name": "ink" });
	 * @return {} 
	 */
	app.s = app.setPageParam = function(key, value){
		if(typeof key === "string") {
			_pageParam[key] = value;
		} else {
			$.extend(_pageParam, key);
		}
	};

	/**
	 * 获取全局参数，等效于getPageParam函数
	 * @method g
	 * @param {String} key       参数名
	 * @param {Any}    [defVal]  默认值，当key获取不到值时使用
	 * @example
	 * 		Zdsoft.app.g("name");
	 * 		// ==> "ink"
	 * 		
	 * @return {Any}             全局参数中存储的值
	 */
	app.g = app.getPageParam = function(key, defVal) {
		// G 为页面上定义的全局变量
		return typeof _pageParam[key] !== "undefined" ? 
			_pageParam[key] :
			typeof G !== "undefined" && typeof G[key] !== "undefined" ?
			G[key] :
			defVal ? defVal : null;
	};

	/**
	 * 获取路由
	 * @method url
	 * @param  {String} route   由三个子参数组成的字符： 模块/控制器/动作
	 * @param {Object} [param]  作为url参数的对象，{a: 1, b: 1}将解析为 a=1&b=1的格式
	 * @example 
	 *  	Zdsoft.app.url('main/default/index');
	 *  	// ==> localhost/main/default/index
	 *  	Zdsoft.app.url('main/default/index', { op: "add" });
	 *  	// ==> localhost/main/default/index?op=add
	 *  	
	 * @return {String}          Url地址
	 */
	app.url = function(route, param){
		route += "";
		param = param ? '?' + $.param(param) : '';
		var url= _contextPath + "/" + route;
		if(param !=='')
			url+=param;
		return url;
	};

	/**
	 * 获取事件参数，即节点上data-param属性解析后的对象
	 * @method getEvtParams
	 * @param  {HTMLElement} elem  元素节点
	 * @example
	 * 		**HTML**
	 * 		<div id="a" data-param='{ "b": 1, "c": "HEHE"}'></div>
	 * 		**Script**
	 * 		app.getEvtParams(document.getElementById("a"));
	 * 		// ==> { b: 1, c: "HEHE" }
	 * 		
	 * @return {Object}            解析后的数据
	 */
	app.getEvtParams = function(elem) {
		var param = $.attr(elem, "data-param");
		return param ? $.parseJSON(param) : undefined;
	};
	
	return app;
})();

/**
 * 语言包入口
 * 读取语言包, 语言包定义于全局变量 L
 * 当data参数存在时，会调用模板
 * @method l
 * @for Zdsoft
 * @param {String} langNS 语言包命名空间
 * @param {Object} data  模板数据，当data === true 时，调用 L 对象
 */ 
Zdsoft.l = function(langNS, data) {
	var _langNs = langNS,
		lang;

	if(typeof L == "undefined"){
		return langNS;
	}

	lang = L;

	langNS = (langNS || "").split(".");

	for (var i = 0, ns; ns = langNS[i++];) {
		lang = lang[ns];
		if (!lang) {
			break;
		}
	}
	return data ? (data === true ? $.template(lang, L) : $.template(lang, data)) : lang || _langNs;
}

//@deprecated 
Zdsoft.Event = function($ctx, type, flag){
	var that = this;
	type = type || "click";
	flag = flag || type;

	$ctx = $ctx && $ctx.length ? $ctx : $(document);
	$ctx.on(type, "[data-" + flag + "]", function(){
		var evtName = $.attr(this, "data-" + flag),
			params = Zdsoft.app.getEvtParams(this);
		that.fire(evtName, params, $(this));
	})
	this._evts = {};
}

Zdsoft.Event.prototype = {
	constructor: Zdsoft.Event,
	// 查询是否存在指定标识的事件处理器，存在且为函数时返回true
	has: function(name){
		return name in this._evts && $.isFunction(this._evts[name]);
	},
	add: function(name, evts){
		// 若首选项为字符串，则作为模块标识符
		if(typeof name === "string"){
			this._evts[name] = evts;
		} else {
			evts = name;
			$.extend(this._evts, evts);
		}
	},
	remove: function(name){
		delete this._evts[name];
	},

	fire: function(name, params, $elem){
		if(this.has(name)){
			this._evts[name].call(this._evts.click, params, $elem);
		}
	}
}

Zdsoft.events = new Zdsoft.Event();

(function(window, $){
	var _slice = Array.prototype.slice,
	_call = function(func){
		var args;
		if($.isFunction(func)){
			args = _slice.call(arguments, 1);
			return func.apply(this, args);
		}
	}

/**
 * 节点处理函数集
 * @class Dom
 * @static
 */
window.Dom = Zdsoft.Dom = (function(){
	var Dom = {
		/**
		 * 通过 id 获取节点，等同于document.getElementById(id);
		 * @method byId
		 * @param  {String} id     节点id
		 * @return {HTMLElment}    节点，没有时为null;
		 */
		byId: function(id){
			return document.getElementById(id) || null;
		},
		/**
		 * 获取节点或节点对应的jq对象
		 * @method getElem
		 * @param  {String|HTMLElement|Jquery} id   节点id或节点本身或对应jquery对象
		 * @param  {Boolean} toJq                   当此值为true时，返回jq对象，否则返回节点
		 * @return {HTMLElement|Jquery}             
		 */
		getElem: function(id, toJq){
			var node, isJq = false;
			if(typeof id === "string"){
				node = document.getElementById(id)
			} else {
				node = id;
				if(id && !id.nodeType) {
					isJq = true;
				}
			}
			return toJq ? (isJq ? node : $(node)) : (isJq ? node[0] : node);
		}
	}

	return Dom;
})();	

/**
 * 全局核心函数集
 * @class Zdsoft.core
 * @static
 */
Zdsoft.core = {
	/**
	 * 用于继承父类
	 * @method inherits
	 * @param  {Function} _subClass   父类
	 * @param  {Function} _superClass 子类
	 * @example
	 * 		var A = function(){};
	 * 		A.prototype.a = 1;
	 * 		
	 * 		var B = function(){};
	 * 		B.prototype.b = 2;
	 * 		
	 * 		Zdsoft.core.inherits(B, A);
	 * 		
	 * 		var ins = new B
	 * 		ins.a; // ==> 1
	 * @return {Function}             子类
	 */
	inherits: function(_subClass, _superClass){
		var _F = function(){};
		_F.prototype = _superClass.prototype;
		_subClass.prototype = $.extend(new _F(), { _super: _superClass, constructor: _subClass }, _subClass.prototype);
		_F.prototype = null;
		return _subClass;
	}
};

/**
 * 显示模态窗口
 * @method showModal
 * @for jQuery
 * @param  {Object} [options] 配置项，一组css属性
 * @return {Jquery}         
 */
$.fn.showModal = function(options){
	var opt = $.extend($.fn.showModal.defaults, options);
	return this.each(function(){
		var $elem = $(this),
			$modal = $elem.data("modal"),
			posVal;
		if(!$modal){
			posVal = $elem.css("position");
			if(posVal !== "fixed" && posVal !== "absolute" && posVal !== "relative") {
				$elem.css("position", "relative");
			}
			$modal = $('<div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: #FFF; opacity: .7; filter: Alpha(opacity=70)"></div>').hide();;
			$modal.css(opt).appendTo($elem).fadeIn(200).on("mousedown", function(e){
				e.stopPropagation();
				e.preventDefault();
			});
			$elem.data("modal", $modal);
		}
	});
};
$.fn.showModal.defaults = {
	backgroundColor: "#FFF",
	opacity: ".7",
	zIndex: 2000
}
/**
 * 隐藏模态窗口
 * @method hideModal
 * @for jQuery
 * @return {Jquery}         
 */
$.fn.hideModal = function(){
	return this.each(function(){
		var $elem = $(this),
			$modal = $elem.data("modal");
		if($modal) {
			$modal.fadeOut(200, function(){
				$modal.remove();
			});
			$elem.removeData("modal");
		}
	});
};


/**
 * 显示或关闭等待中提示
 * @method waiting
 * @for  jQuery
 * @param  {String|False} [content]        提示文字或HTML，值为false时关闭等待提示
 * @param  {String}       [mode="normal"]  提示类型， normal、small、mini分别对应三种大小的图标
 * @param  {Boolean}      [lock=false] 	   是否锁定
 * @return {Jquery}         
 */
$.fn.waiting = function(content, mode, lock){
	if(content === false) {
		return this.stopWaiting();
	}
	content = content || "";
	mode = mode || "normal";
	var tpl = '<div><img src="<%= path %>" width="<%= size %>" height="<%= size %>" />' +
		'<span style="margin-left: 10px; font-size: <%= fontSize %>px;"><%= content %></span>' + 
		'</div>';

	var modeSet = {
		normal: $.template(tpl, { path: _contextPath +"/static/jbmp/editor/images/workflow/loading.gif", size: 60, fontSize: 16, content: content }),
		small: $.template(tpl, { path: _contextPath +"/static/jbmp/editor/images/workflow/loading_small.gif", size: 24, fontSize: 14, content: content }),
		mini: $.template(tpl, { path: _contextPath +"/static/jbmp/editor/images/workflow/loading_mini.gif", size: 16, fontSize: 12, content: content })
	}
	content = modeSet[mode] || content || "Loading...";

	return this.each(function(){
		var $ctx = $(this),
			$wait = $ctx.data("waiting");

		if(lock) {
			$ctx.showModal();
		}

		if($wait) {
			$wait.html(content).position({ of: $ctx });
		} else {
			$wait = $("<div class='waiting' style='position: absolute; z-index: 2001; width: 300px; text-align: center; overflow: hidden;'></div>")
			.html(content)
			.appendTo($ctx.parent())
			.position({ of: $ctx });

			$ctx.data("waiting", $wait);
		}
	});
};

//@deprecated 统一使用 $.fn.waiting 入口
$.fn.waitingC = function(content, lock){
	return this.waiting(content, 'normal', lock);
};

// @deprecated 统一使用 $.fn.waiting 入口
$.fn.stopWaiting = function(){
	return this.each(function(){
		var $ctx = $(this),
			$wait = $ctx.data("waiting");

		$ctx.hideModal();

		if($wait) {
			$wait.remove();
			$ctx.removeData("waiting");
		}
	});
};

/**
 * 工具类，包括一些工具函数 
 * @class U
 * @static
 */
(function(){
	var U = {
		/**
		 * 判断值是否未定义， 未定义时返回true
		 * @method isUnd
		 * @param {Any} any  
		 * @return {Boolean}
		 */
		isUnd: function(prop){
			return prop === void 0;
			//return typeof prop === "undefined";
		},

		/**
		 * 内置正则集
		 * @attribute reg
		 * @type {Object}
		 */
		reg: {
			'int': /^-?\d+$/,  
			positiveInt: /^[1-9]\d*$/,  // 正整数
			decimals: /^-?(([1-9]\d*(\.\d+))|(0\.\d+))$/, // 小数（不包含整数）
			positiveDecimals: /^(([1-9]\d*(\.\d+))|(0\.\d+))$/,  // 正小数
			url: /^(http[s]?:\/\/)?([\w-]+\.)+[\w-]+([\w-.\/?%&=]*)?$/,
			email: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
			zipcode: /^\d{6}$/,
			notempty: /\S+/,
			mobile: /^1\d{10}$/,
			tel: /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/,	//电话号码的函数(包括验证国内区号,国际区号,分机号),
			currency: /^-?(\d{1,3})((\,\d{3})+)?(\.\d+)?$/ 			// 货币格式(包括千分号)
		},

		/**
		 * 判断字符串是否符合某一类型正则
		 * @method regex
		 * @param  {String} str  要验证的字符串
		 * @param  {String} type 正则类型，参考 U.reg
		 * @see   U.reg
		 * @return {[type]}      [description]
		 */
		regex: function(str, type) {
			if(type && this.reg[type] && this.reg[type].test(str)){
				return true;
			}
			return false;
		},

		/**
		 * 判断值是否正整数
		 * @method isPositiveInt
		 * @param  {Number|String}  value 
		 * @return {Boolean}
		 */
		isPositiveInt: function(value) {
			return this.regex("" + value, 'positiveInt')
		},

		/**
		 * 基于属性对比两个对象是否相等
		 * @isEqualObject 
		 * @param  {Object}  a 源对象
		 * @param  {Object}  b 作为比较对象
		 * @return {Boolean}   
		 */
		isEqualObject: function(a, b){
			var ret = true;
			if(typeof a !== "object" || typeof b !== "object") {
				return false;
			}
			for(var i in a) {
				if(a.hasOwnProperty(i)){
					if(typeof a[i] === "object") {
						ret = this.isEqualObject(a[i], b[i]);
						if(!ret) {
							return false;
						}
					} else if(a[i] !== b[i]) {
						return false;
					}
				}
			}
			return ret;
		},

		/**
		 * 获取url中参数段
		 * @method getUrlParam
		 * @param  {String} [url=window.location.href] url地址
		 * @return {Object}                            参数集
		 */
		getUrlParam: function(url){
			var ret = {},
				index,
				lastIndex,
				str;

			url = url || window.location.href;
			index = url.indexOf("?") + 1;
			lastIndex = url.indexOf("#");
			lastIndex = (lastIndex == -1 ? url.length : lastIndex);

			str = url.substring(index, lastIndex);

			$.each(str.split("&"), function(index, s){
				var kv = s.split("=");
				if(kv.length > 1) {
					ret[kv[0]] = kv[1]
				}
			});

			return ret;
		},

		/**
		 * 格式文件大小，将数字转为合理格式
		 * formatFileSize(1024) => 1.00KB
		 * @method formatFileSize
		 * @param  {Number} size 		文件大小
		 * @param  {Number} [dec=0]  	小数位，即默认保留多少位小数
		 * @return {String}      		格式化的文件大小
		 */
		formatFileSize: function(size, dec){
			size = parseInt(size, 10);
			dec = dec || 0;
			if(isNaN(size)){
				return '0B';
			} else {
				var unit = ["B", "KB", "MB", "GB"];
				var i = 0;
				while(size >= 1024){
					size = size/1024;
					if(++i == 3) break;
				}
				return size.toFixed(dec) + unit[i];
			}
		},
		/**
		 * 为表单控件添加值(主要作用于text,hidden,textarea)，格式为 "a,b,c"
		 * @method addValue 
		 * @param {String|Element|Jquery} selector  选择器
		 * @param {String} value    				目标值
		 */
		addValue: function(selector, value){
			var oldVal = $(selector).val();
			$(selector).val(oldVal ? oldVal + "," + value : value);
		},
		/**
		 * 从表单控件中移除值(主要作用于text,hidden,textarea)，格式为 "a,b,c"
		 * @method removeValue
		 * @param {String|Element|Jquery} selector  选择器
		 * @param {String} value    				目标值
		 */
		removeValue: function(selector, value){
			var oldVal = $(selector).val();
			if($.trim(oldVal) !== ""){
				oldVal = ("," + oldVal + ",").replace(new RegExp("," + value + ",", "g"), ",");
				$(selector).val(oldVal.slice(1, -1));
			}
		},
		hasValue: function(selector, value){
			var oldVal = $(selector).val();
			return ("," + oldVal + ",").indexOf("," + value + ",") !== -1;
		}
	};


	/**
	 * 设置cookie
	 * @method setCookie
	 * @param  {String} name    cookie标识符
	 * @param  {String} value   cookie值，为空时删除该cookie
	 * @param  {Number} [seconds] cookie有效周期，单位为秒，当传入值小于0时，会删除该cookie。
	 * @return {[type]}         [description]
	 */
	U.setCookie = function(name, value, seconds, path, domain, secure) {
		var SECOND_PER_MONTH = 2592000,
			expires = new Date();
		if(value === '' || seconds < 0) {
			seconds = -SECOND_PER_MONTH;
		}
		seconds = this.isUnd(seconds) ? SECOND_PER_MONTH : seconds;
		path = path || Zdsoft.app.g('cookiePath') || "";
		domain = domain || Zdsoft.app.g('cookieDomain') || "";
		expires.setTime(expires.getTime() + seconds * 1000);
		document.cookie = escape((Zdsoft.app.g('cookiePre') || "") + name) + '=' + escape(value) + 
		(expires ? '; expires=' + expires.toGMTString() : '') +
		(path ? '; path=' + path : '/') +
		(domain ? '; domain=' + domain : '') +
		(secure ? '; secure': '');
	},

	/**
	 * 获取cookie值
	 * @method getCookie
	 * @param  {String}  name                 cookie标识符
	 * @param  {Boolean} [nounescape = false] 是否不使用unescape解码
	 * @return {String}                       cookie值
	 */
	U.getCookie = function(name, nounescape) {
		name = (Zdsoft.app.g('cookiePre') || "") + name;
		var cookieStart = document.cookie.indexOf(name),
			cookieEnd = document.cookie.indexOf(";", cookieStart);
		if(cookieStart == -1) {
			return '';
		} else {
			var v = document.cookie.substring(cookieStart + name.length + 1, (cookieEnd > cookieStart ? cookieEnd : document.cookie.length));
			return !nounescape ? unescape(v) : v;
		}
	},

	/**
	 * 清除所有cookie值
	 * @method clearCookie
	 * @return {[type]} [description]
	 */
	U.clearCookie = function(){
		var reg = /[^ =;]+(?=\=)/g,
			keys = document.cookie.match(reg);
		if(keys) {
			for(var i = keys.length; i--;){
				document.cookie=keys[i]+'=0;expires=' + new Date(0).toUTCString();
			}
		}
	}

	/**
	 * 枚举出对象上所有可枚举的属性名
	 * @method keys
	 * @param  {Object} obj  要进行枚举的对象
	 * @return {Array}       属性名组成的数组
	 */
	U.keys = Object.keys || function(obj) {
	    obj = Object(obj)
	    var arr = []    
	    for (var a in obj) arr.push(a)
	    return arr
	};

	/**
	 * 对调对象的属性名与属性值
	 * @method invertKeys
	 * @param  {Object} obj  要进行key-value对调的对象
	 * @return {Object}      对调完成后的新对象，不指向原对象
	 */
	U.invertKeys = function(obj) {
	    obj = Object(obj)
	    var result = {}
	    for (var a in obj) result[obj[a]] = a
	    return result
	}

	/**
	 * 获取字符串长度
	 * 字母长度为0.5，中文长度为1，对url会做特殊处理
	 * @method getCharLength
	 * @param String str 要计算长度的字符串
	 * @return {Number}  经过计算的长度值
	 */
	U.getCharLength = (function() {
		var byteLength = function(str) {
			if (typeof str == "undefined") {
				return 0
			}
			
			var sChar = str.match(/[^\x00-\x80]/g);
			// 匹配出中文字符的数目并再次加上，这样子每个中文字符占位为2
			return (str.length + (!sChar ? 0 : sChar.length))
		};

		return function(str, opt) {
			opt = opt || {};
			opt.max = opt.max || 140;
			// opt.min = opt.min || 41;
			opt.surl = opt.surl || 20;
			var p = $.trim(str).length;
			if (p > 0) {
				// 下面这段正则用于匹配URL
				var result = str.match(/(http|https):\/\/[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+([-A-Z0-9a-z\$\.\+\!\_\*\(\)\/\,\:;@&=\?~#%]*)*/gi) || [];
				var total = 0;
				for (var m = 0,
						len = result.length; m < len; m++) {
					var o = byteLength(result[m]);

					total += o <= opt.max ? opt.surl : (o - opt.max + opt.surl)

					str = str.replace(result[m], "")
				}
				return Math.ceil((total + byteLength(str)) / 2)
			} else {
				return 0
			}
		}
	})();

	/**
	 * 对HTML进行实体转义处理的函数集
	 */
	U.entity = (function(){
		var entityMap = {
		    escape: {
		      '&': '&amp;',
		      '<': '&lt;',
		      '>': '&gt;',
		      '"': '&quot;',
		      "'": "&apos;"
		    }
		}
		entityMap.unescape = U.invertKeys(entityMap.escape);
		var entityReg = {
		    escape: RegExp('[' + U.keys(entityMap.escape).join('') + ']', 'g'),
		    unescape: RegExp('(' + U.keys(entityMap.unescape).join('|') + ')', 'g')
		}
		 
		/**
		 * 将HTML特殊字符转义为实体
		 * @method entity.escape
		 * @param  {String} html html文本
		 * @return {String}      转义后的文本
		 */
		function escape(html) {
		    if (typeof html !== 'string') return ''
		    return html.replace(entityReg.escape, function(match) {
		        return entityMap.escape[match]
		    })
		}
		/**
		 * 将HTML实体转义为特殊字符
		 * @method entity.unescape
		 * @param  {String} html 转义后的html文本
		 * @return {String}      html文本
		 */
		function unescape(str) {
		    if (typeof str !== 'string') return ''
		    return str.replace(entityReg.unescape, function(match) {
		        return entityMap.unescape[match]
		    })    
		}
		return {
			escape: escape,
			unescape: escape
		}
	})();

	/**
	 * 预读取图片
	 * @method loadImage
	 * @param  {String}   url      文件地址
	 * @param  {Function} load     读取成功回调
	 * @param  {Function} error    读取失败回调
	 * @return {[type]}            [description]
	 */
	U.loadImage = function(url, load, error){
		var img = new Image(),
			loaded = false;
		img.onload = function(){
			img.onload = img.onerr = null;
			!loaded && _call(load, img);
			loaded = true;
		}
		// 加载错误
		img.onerror = function () {
			img.onload = img.onerror = null;
			_call(error, img);
		};
		img.src = url;
		if(img.complete){
			loaded = true;
			_call(load, img);
		}
	};

	/**
	 * 用于动态加载JS、CSS文件
	 * @method loadFile
	 * @param {DocumentElement} 文档对象
	 * @param {Object} 文件配置信息 {src|href, tag, id, *}
	 * @param {Function} 加载成功回调
	 * @return {[type]} [description]
	 */
	U.loadFile = (function () {
        var tmpList = [];
        function getItem(doc,obj){
            try{
                for(var i= 0,ci;ci=tmpList[i++];){
                    if(ci.doc === doc && ci.url == (obj.src || obj.href)){
                        return ci;
                    }
                }
            }catch(e){
                return null;
            }

        }
        return function (doc, obj, fn) {
            var item = getItem(doc,obj);
            if (item) {
                if(item.ready){
                    fn && fn();
                }else{
                    item.funs.push(fn)
                }
                return;
            }
            tmpList.push({
                doc:doc,
                url:obj.src||obj.href,
                funs:[fn]
            });
            if (!doc.body) {
                var html = [];
                for(var p in obj){
                    if(p == 'tag')continue;
                    html.push(p + '="' + obj[p] + '"')
                }
                doc.write('<' + obj.tag + ' ' + html.join(' ') + ' ></'+obj.tag+'>');
                return;
            }
            if (obj.id && doc.getElementById(obj.id)) {
                return;
            }
            var element = doc.createElement(obj.tag);
            delete obj.tag;
            for (var p in obj) {
                element.setAttribute(p, obj[p]);
            }
            element.onload = element.onreadystatechange = function () {
                if (!this.readyState || /loaded|complete/.test(this.readyState)) {
                    item = getItem(doc,obj);
                    if (item.funs.length > 0) {
                        item.ready = 1;
                        for (var fi; fi = item.funs.pop();) {
                            fi();
                        }
                    }
                    element.onload = element.onreadystatechange = null;
                }
            };
            element.onerror = function(){
                throw Error('The load '+(obj.href||obj.src)+' fails')
            };
            doc.getElementsByTagName("head")[0].appendChild(element);
        }
    })();

    U.loadCss = function(href, callback){
    	if(document.getElementById(href)){
    		return false;
    	}
    	U.loadFile(document, {
    		tag: "link",
    		rel: "stylesheet",
    		href: href,
    		id: href
    	}, callback)
    };

	/**
	 * 用于将经过Jquery 系列化的表单数组转化为对象
	 * @method serializedToObject
	 * @deprecated            使用 $.fn.serializeObject 代替
	 * @param  {Array} array  系列化后得到的数组
	 * @return {Object}       解析后的对象
	 */
	U.serializedToObject = function(serialized) {
		var data = {},
			name,
			val;

		if(serialized && serialized.length) {

			for (var i = 0; i < serialized.length; i++) {
				name = serialized[i].name;
				val = serialized[i].value;

				// 如果已经存在同名键，则将值存为数组
				if(data[name]) {
					if(!$.isArray(data[name])) {
						data[name] = [data[name]];
					}
					data[name].push(val);
				} else {
					data[name] = val;
				}
			}

		}
		return data;
	};

	/**
	 * 根据name属性获取选中的复选框
	 * @method getChecked
	 * @param  {String} name 表单控件的name
	 * @param  {Jquery} [$ctx] 上下文
	 * @return {Jquery}      复选框的jq对象数组
	 */
	U.getChecked = function(name, $ctx){
		return $("input[type='checkbox'][name='" + name + "']:checked", $ctx);
	};

	/**
	 * 根据name属性获取选中的复选框值，返回以“,”分隔的字符串
	 * @method getCheckedValue 
	 * @param  {String} name 表单控件的name
	 * @param  {Jquery} $ctx 上下文
	 * @return {String}      复选框的值字符串
	 */
	U.getCheckedValue = function(name, $context){
		var $checkeds = this.getChecked(name, $context);

		return $checkeds.map(function(){
			return this.value;
		}).get().join(",");
	}

	U.lang = Zdsoft.l;

	/**
	 * 获取唯一随机值
	 * @method uniqid
	 * @param  {String} [prefix]       值前缀
	 * @param  {Boolean} [more_entropy] 当此参数为true时，会加长id以增强随机性
	 * @return {String}                随机值
	 */
	U.uniqid = function(prefix, more_entropy) {
		// + 	 original by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
		// +     revised by: Kankrelune (http://www.webfaktory.info/)
		// %     note 1: Uses an internal counter (in php_js global) to avoid collision
		if (typeof prefix === 'undefined') {
			prefix = "";
		}

		var retId;
		var formatSeed = function(seed, reqWidth) {
			seed = parseInt(seed, 10).toString(16); // to hex str
			if (reqWidth < seed.length) { // so long we split
				return seed.slice(seed.length - reqWidth);
			}
			if (reqWidth > seed.length) { // so short we pad
				return Array(1 + (reqWidth - seed.length)).join('0') + seed;
			}
			return seed;
		};

		// BEGIN REDUNDANT
		// END REDUNDANT
		if (!this.uniqidSeed) { // init seed with big random int
			this.uniqidSeed = Math.floor(Math.random() * 0x75bcd15);
		}
		this.uniqidSeed++;

		retId = prefix; // start with prefix, add current milliseconds hex string
		retId += formatSeed(parseInt(new Date().getTime() / 1000, 10), 8);
		retId += formatSeed(this.uniqidSeed, 5); // add seed hex string
		if (more_entropy) {
			// for more entropy we add a float lower to 10
			retId += (Math.random() * 10).toFixed(8).toString();
		}

		return retId;
	};

	window.U = Zdsoft.Util = U;
})();
})(window, window.jQuery);