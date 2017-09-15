
	/**
	 * UI层
	 * 主要是一些弹出组件，交互组件及宽高计算函数
	 * Dialog组件使用了插件artDialog
	 * Tip组件使用了插件jGrowl
	 * @class Ui
	 * @static
	 */
	(function(){
		var Ui = {};
		// Ui.themes = [ "3497DB", "A6C82F", "F4C73B", "EE8C0C", "E76F6F", "AD85CC", "98B2D1", "82939E"];

		(function(j){
			if(!j){ 
				//$.error("Ui.tip: 使用tip需要加载$.fn.jGrowl");
				return false;
			}
			/**
			 * 全局提示，基于jGrowl
			 * @method tip
			 * @param  {String} msg   提示的文本
			 * @param  {String} [theme=success] 主题(success|danger|warning|default|normal|info);
			 * @return {}
			 */
			Ui.tip = function(msg, theme) {
				msg = msg || "";
				if(msg.indexOf("@") == 0) {
					msg = Zdsoft.l(msg.substr(1)) || msg;
				}
				return $.jGrowl(msg, {theme: theme||'success'});
			};
		})($.jGrowl||top.$.jGrowl);

		(function(d){
			if(d){
				$.extend(Ui, {
					/**
					 * 全局对话框，基于artDialog
					 * @method dialog
					 * @param {Object} options 配置
					 * @return {Object} artDialog实例
					 */
					dialog: d,
					/**
					 * 全局警告框，基于artDialog，模态
					 * @method alert
					 * @param  {String}  msg 提示文本
					 * @param  {Function}  ok 确定后的回调
					 * @return {Object} artDialog实例
					 */
					alert: d.alert,
					/**
					 * 全局确定框，基于artDialog，模态
					 * @method confirm
					 * @param  {String} msg 提示文本
					 * @param  {Function}  ok 确定后的回调
					 * @param  {Function}  cancel 取消后的回调
					 * @return {Object} artDialog实例
					 */
					confirm: d.confirm,
					/**
					 * 全局信息接收框，基于artDialog，模态
					 * @method prompt
					 * @param  {String} msg 提示文本
					 * @param  {Function}  ok 确定后的回调， 输入的文本会作为首参数传入
					 * @param  {Function}  cancel 取消后的回调
					 * @return {Object} artDialog实例
					 */
					prompt: d.prompt,
					tips: d.tips,
					/**
					 * ajax对话框，基于artDialog
					 * @method ajaxDialog
					 * @param  {String} url ajax地址
					 * @param  {Object}  options 配置，与Ui.dialog相同
					 * @return {Object} artDialog实例
					 */
					ajaxDialog: d.load,
					/**
					 * 框架的对话框，基于artDialog
					 * @method openFrame
					 * @param  {String} url 框架页地址
					 * @param  {Object}  options 配置，与Ui.dialog相同
					 * @return {Object} artDialog实例
					 */
					openFrame: d.open,
					/**
					 * 获取Dialog实例
					 * @method getDialog
					 * @param  {String} [id] dialog的自定义id, 为空时获取所有对话框实例
					 * @return {Object} artDialog实例
					 */
					getDialog: d.get,
					/**
					 * 关闭对话框
					 * @method closeDialog
					 * @param  {String} [id] dialog的自定义id, 为空时关闭所有对话框实例
					 * @return {Object} artDialog实例
					 */
					closeDialog: function(id) {
						// 没有传参时，关闭所有弹窗
						if(typeof id === "undefined") {
							for(var i in d.list){
								if(d.list.hasOwnProperty(i)){
									d.list[i].close();
								}
							}
						} else {
							var dl = this.getDialog(id);
							dl && dl.close();
						}
					}
				})
			}
		})($.artDialog||top.$.artDialog);


		/**
		 * 通用菜单类
		 * @class Menu
		 * @todo 扩展出子类AjaxMenu
		 * @todo 是否添加opiton.width跟option.height;
		 * @constructor
		 * @param {Jquery}  $menu                            作为菜单的节点
		 * @param {Object}  [options]                        配置项
		 *     @param {String}  [options.id]                 标识符，作为获取实例的依据
		 *     @param {Object}  [options.position]           定位方位，使用jqui的position方法, 参数一致
		 *     @param {String}  [options.content]            初始化时的内容
		 *     @param {Number}  [options.autoHide]           自动隐藏的延迟时间数，默认不自动隐藏
		 *     @param {Boolean} [options.clickToHide=false]  点击菜单以外的地方时隐藏
		 *     @_param {Number} [options.zIndex = 1001]      菜单z-index值
		 * @return {Menu}                                    菜单实例
		 */
		Ui.Menu = (function(){
			var menuid = 0;
			var instance = {};

			var Menu = function(menu, options){
				var me = this;
				this.$menu = Dom.getElem(menu, true);
				var opt = this.options = $.extend({}, Menu.defaults, options);

				// 添加新实例的引用
				if(opt.id){
					if(instance[opt.id]){
						return instance[opt.id];
					} else {
						instance[opt.id] = this;
						this.id = opt.id;
					}
				// 没有设置 id 时，使用自动menuid
				} else {
					this.id = "menu" + menuid++;
					instance[this.id] = this;	
				}

				this.$menu.css("position", "absolute").hide();
				this.showing = false;
				this.zIndex(opt.zIndex);

				// 悬停时中止自动隐藏
				if(opt.autoHide) {
					this.$menu.on({
						"mouseenter": function(){
							me._stopDelay("hide");
						},
						"mouseleave": function(){
							me.hide(me.options.autoHide);
						}
					})
				}

				// 有设置option.content属性时，初始化内容
				if(!U.isUnd(opt.content) && (typeof opt.content === "string" || opt.content.nodeType === 1)) {
					this.$menu.html(opt.content);
				}

				if(opt.clickToHide) {
					$(document).on("mousedown", function(evt){
						if(me.$menu[0] !== evt.target && !$.contains(me.$menu[0], evt.target)) {
							me.hide();
						}
					})
				}

				opt.addClass && this.$menu.addClass(opt.addClass)
			}
			Menu.defaults = {
				animate: false,
				zIndex: 1000
			}
			Menu._access = function(id, callback){
				if(U.isUnd(id)){
					for(var i in instance) {
						if(instance.hasOwnProperty(i)) {
							callback.call(instance[i])
						}
					}
					return instance;
				} else {
					if(id in instance) {
						callback.call(instance[id]);
						return instance[id];
					}
					return null;
				}
			}
			/**
			 * 显示指定菜单
			 * @show
			 * @static
			 * @param  {String} id 要显示菜单的id，为空时显示所有菜单
			 * @return {}
			 */
			Menu.show = function(id){
				this._access(id, function(){
					this.show();
				})
			}
			/**
			 * 隐藏指定菜单
			 * @hide
			 * @static
			 * @param  {String} id 要隐藏菜单的id，为空时隐藏所有菜单
			 * @return {}    
			 */
			Menu.hide = function(id){
				this._access(id, function(){
					this.hide();
				})
			}
			/**
			 * 获取指定id的菜单实例
			 * @param  {String} id  菜单实例的id
			 * @return {Menu}       菜单实例
			 */
			Menu.getIns = function(id){
				return id ? instance[id] : instance
			}
			Menu.prototype = {
				constructor: Menu,

				/**
				 * 定位菜单，直接使用了jqUi的position函数
				 * @method position
				 * @chainable
				 * @param  {Object} [opt] 定位配置项，详见jqUi position函数
				 * @return {Menu}       菜单实例
				 */
				position: function(opt){
					this.$menu.position($.extend({
						of: document.body
					}, opt));
					return this;
				},

				/**
				 * 设置菜单z-index值
				 * @method zIndex
				 * @chainable
				 * @param  {Number|String} z z-index值
				 * @return {Menu}       菜单实例
				 */
				zIndex: function(z){
					this.$menu.css("z-index", z);
					return this;
				},
				
				/**
				 * 显示菜单
				 * @method show
				 * @chainable
				 * @param  {Number} [delay] 延时显示
				 * @return {Menu}           菜单实例
				 */
				show: function(delay){
					var that = this,
						opt = this.options;
					if(this.showing){
						return this;
					}

					// 有设置option.content属性时，初始化内容
					// 如果options.content是一个函数，则在beforeshow执行
					if(!U.isUnd(opt.content) && typeof this.options.content === "function"){
						this.$menu.html(this.options.content.call(this) || "");
					}

					this.showing = true;
					// 设置了animate属性时，给予渐显过渡
					if(opt.animate) {
						this.$menu.fadeTo(0, 0).show().trigger("show")
						.fadeTo(200, 1);
						opt.show && opt.show.call(this);
					} else {
						this.$menu.show().trigger("show");
						opt.show && opt.show.call(this);
					}
					// 定位需要在$menu出现在页面之后才执行，否则会有定位误差
					this.position(opt.position);

					// 自动隐藏
					if(opt.autoHide) {
						setTimeout(function(){
							that.hide();
						}, opt.autoHide)
					}
					return this;
				},

				/**
				 * 隐藏菜单
				 * @method hide
				 * @chainable
				 * @param  {Number} [delay] 延时隐藏
				 * @return {Menu}           菜单实例
				 */
				hide: function(delay){
					this.showing = false;
					if(this.options.animate) {
						this.$menu.fadeOut(200).trigger("hide");
						this.options.hide && this.options.hide.call(this);
					} else {
						this.$menu.hide().trigger("hide");
						this.options.hide && this.options.hide.call(this);
					}
					return this;
				},

				/**
				 * 设置菜单内容
				 * @method setContent
				 * @chainable
				 * @param  {String|HTMLElement|Jquery} [content] 内容
				 * @return {Menu}           菜单实例
				 */
				setContent: function(content){
					this.$menu.html(content);
					this.position(this.options.position);
					return this;
				}

				// destory: function(){
				// 	delete this.constructor.instance[this.id];
				// }
			}

			return Menu;
		})();

		/**
		 * 通用弹出菜单类
		 * @class PopMenu
		 * @constructor
		 * @extends {Menu}
		 * @param {Jquery} $ctrl        作为触发器的节点
		 * @param {Jquery} $menu        作为菜单的节点
		 * @param {Object} [options]    配置项，详见Menu类
		 *     @param {String} [options.trigger]  触发方式，目前支持hover、click
		 * @return {Menu}               菜单实例
		 */
		Ui.PopMenu = function($ctrl, $menu, options){
			if(!$ctrl || !$ctrl.length) {
				return false
			}
			if(options.trigger === "click") {
				options.hideDelay = options.hideDelay ? options.hideDelay : 0;
				options.showDelay = options.showDelay ? options.showDelay : 0;
			}
			this._super.call(this, $menu, $.extend(true, {}, Ui.PopMenu.defaults, options));
			this.$ctrl = $ctrl;

			var that = this;
			var timer;

			if(!this.options.position.of){
				this.options.position.of = this.$ctrl;
			}

			var _show = function(){
				clearTimeout(timer)
				timer = setTimeout(function(){
					that.show();
				}, that.options.showDelay)
			};
			var _hide = function(e){
				clearTimeout(timer);
				if(that.options.trigger == "hover") {
					// 当离开的瞬间重新进入菜单或菜单开关时，不隐藏
					if(e.toElement === $ctrl[0] 
						|| e.toElement === $menu[0] 
						|| $.contains($ctrl[0], e.toElement)
						|| $.contains($menu[0], e.toElement)
					){
						return false;
					}
				}
				timer = setTimeout(function(){
					that.hide();
				}, that.options.hideDelay);
			}

			if(this.options.trigger === "hover") {
				this.$ctrl.on({
					"mouseenter.popmenu": _show,
					"mouseleave.popmenu": _hide
				});
				this.$menu.on({
					"mouseenter.popmenu": function(){
						clearTimeout(timer);
					},
					"mouseleave.popmenu": _hide
				});
			} else if(this.options.trigger === "click") {
				this.$ctrl.on("click.popmenu", function(){
					that.showing ? _hide() : _show();
				})
			}
		};
		Zdsoft.core.inherits(Ui.PopMenu, Ui.Menu);
		Ui.PopMenu.defaults = {
			position: {
				at: "left bottom",
				my: "left top"
			},
			trigger: "hover", // hover, click
			showDelay: 500,
			hideDelay: 500
		};

		$.extend(Ui, {
			modal: {
				show: function(options){
					var opt = $.extend({
						backgroundColor: '#FFF',
						opacity: '.7',
						zIndex: '2000'
					}, options)
					if(!this.$modal || !this.$modal.length){
						this.$modal = $("<div style='position:fixed; top:0; left:0; width:100%; height:100%; overflow:hidden'></div>").css("z-index", opt.zIndex).hide().appendTo(document.body);
						$("<div style='height:100%;'></div>").css(opt).appendTo(this.$modal);

						this.$modal.fadeIn(200)
						.on("mousedown", function(evt){ 
							evt.stopPropagation();
							evt.preventDefault();
						});
					}
				},
				
				hide: function(){
					var $modal = this.$modal;
					if($modal){
						$modal.fadeOut(200, function(){
							$modal.remove();
							Ui.modal.$modal = null
						});
					}
				},

				toggle: function(){
					if(this.$modal){
						this.hide();
					}else{
						this.show();
					}
				}
			},

			/**
			 * 聚集于指定上下文环境中的第一个指定控件上
			 * @method focusForm 
			 * @param  {Jquery}  [$context = $(document.body)] 			 上下文环境
			 * @param  {String}  [selector = 'input, textarea, select']  起作用的选择器
			 * @param  {Boolean} [cls = true]      						 是否为父节点添加has-focus类
			 * @return {[type]}          [description]
			 */
			focusForm: function($context, selector, cls){
				var $first;

				selector = selector || "input, textarea, select";
				cls = cls === false ? false : true;

				$first = $(selector, $context).eq(0).focus();

				cls && $first.parent().addClass("has-focus");
			},

			/**
			 * popover, 基于bootstrap $.fn.popover, 样式不同
			 */
			popover: function($elem, options) {
				$elem.popover($.extend({
					template: '<div class="popover popover-w"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
					placement: "bottom"
				}, options));
			},

			/**
			 * 滚动至指定元素的头部
			 * @method scrollYTo
			 * @for  Ui
			 * @param {HTMLElement|Jquery} elem 指定元素
			 * @param {Number} offset           位置偏移量调整
			 * @param {Function} [callback]     滚动完成后的回调函数
			 * @return {}
			 */
			scrollYTo: function(elem, offset, callback, time){
				var $body = (window.opera) ? (document.compatMode === "CSS1Compat" ? $('html') : $('body')) : $('html,body'),
					$elem = Dom.getElem(elem, true),
					top = $elem.offset().top + (offset || 0);
				time = time || 500;
				$body.stop().animate({ scrollTop: top }, time, callback);
			},
			/**
			 * 页面回到顶部
			 * @method scrollToTop
			 * @return {} 
			 */
			scrollToTop: function (callback) {
				this.scrollYTo($(document.body), 0, callback);
			},

			selectOne: function($elem, tag){
				tag = tag || $elem[0].nodeName;
				$elem.addClass("active").siblings(tag).removeClass("active");
			},

			/**
			 * 动态计算节点高度，让其相对某祖先节点撑开高度
			 * @method fillHeight
			 * @example
			 * @todo 完善
			 * <div  id='a' style='height: 100px;'>
			 * 		<div id='b'></div>
			 * </div>
			 * fillHeight('b', 'a') ==> <div id='b' style='height: 100px;'></div>
			 * @param  {Jquery} $elem   元素
			 * @param  {Jquery} $target 相对元素
			 * @return {}         
			 */
			fillHeight: function($elem, $target){
				var height,
					$child,
					$parent;
				$elem = $child = Dom.getElem($elem, true);
				$target = Dom.getElem($target, true);
				$parent = $elem.parent();
				height = $target.height();

				while($parent && $parent.length && ($.isWindow($target[0]) ? $parent[0].nodeName.toLowerCase() !== "html" : $parent[0] !== $target[0])) {
					height -= $parent.outerHeight() - $child.outerHeight();
					$child = $parent;
					$parent = $parent.parent();
				}
				// if(height > $elem.outerHeight()) {
					// 这里要考虑padding 跟 border
					$elem.css("min-height", height);
				// }
			},

			/**
			 * 新消息标题闪动提醒
			 * @method blinkTitle 
			 * @param  {String} text     提醒内容
			 * @param  {Number} [interval=1000] 闪动的时间间隔
			 * @param  {Number} [timeout]  超时
			 * @return {}
			 */
			blinkTitle: (function(){
				var timer,
					oldTitle = document.title,
					reset = function(){
						clearInterval(timer);
						document.title = oldTitle;
					}

				return function(text, interval, timeout){
					var _in = false;
					interval = interval || 1000;
					if(text === false) {
						reset();
						return false;
					}

					timer = setInterval(function(){
						document.title = _in ? oldTitle : text;
						_in = !_in
					}, interval);

					if(timeout) {
						setTimeout(function() {
							reset();
						}, timeout);
					}
				}
			})(),

			/**
			 * 设置复选框联动
			 * @method linkCheckbox
			 * @param  {String|Element|Jquery} main 主复选框
			 * @param  {String|Element|Jquery} sub  从复选框
			 * @return 
			 */
			linkCheckbox: function(main, sub){
				var $mainCheckbox = $(main),
					$subCheckboxs;

				if($mainCheckbox && $mainCheckbox.length) {
					$subCheckboxs = $(sub);
					// 当主复选框状态变化时，其它复选框跟随其状态变化
					$mainCheckbox.on("change", function(){
						var isChecked = this.checked;
						$subCheckboxs.each(function(){
							var labelIns;
							this.checked = isChecked;
							// 如果复选框是Label实例（即复选框），则调用实例刷新方法
							labelIns = $(this).data("label");
							labelIns && labelIns.refresh();
						})
					});

					$subCheckboxs.on("change", function(){
						if(!this.checked){
							var labelIns;
							$mainCheckbox.prop("checked", false);
							labelIns = $mainCheckbox.data("label");
							labelIns && labelIns.refresh();
						}
					});
				}
			}
		})
		/**
		 * 弹出提示框
		 * @method showPrompt
		 * @param  {String} content 提示内容
		 * @param  {Number} time    显示时间，秒数
		 * @return {ArtDialog}      弹窗实例
		 */
		Ui.showPrompt = function(content, time){
			return Ui.dialog({
				id: "prompt",
				padding: 0,
				title: false,
				cancel: false,
				zIndex: 9999,
				skin: "prompt-dialog"
			})
			.content("<div class='prompt'>" + (content||"") + "</div>")
			.time(time||1.5);
		}	

		Ui.submenu = function(menu){
			var $menu = $(menu);

			$('[data-node="submenuToggle"]', $menu).each(function(){
				var $elem = $(this);
					$menu = $elem.find('[data-node="submenu"]');

				if($menu.length){
					var menuIns = new Ui.PopMenu($elem, $menu, {
						position: {
							at: "right top",
							my: "left top"
						},
						hideDelay: 0,
						showDelay: 0
					});
					// 阻止点击冒泡导致父菜单被关闭
					$menu.on("click", function(evt){
						evt.stopPropagation();
					})
				}
			});
		}

		window.Ui = $.extend(window.Ui, Ui);
	})();
