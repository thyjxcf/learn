(function(window, undefined) {

var popupObject = (function() {
	
// Define a local copy of popupObject
var popupObject = function(popup, idObjectId, nameObjectId, callback, useCheckbox) {
	return new popupObject.fn.init(popup, idObjectId, nameObjectId, callback, useCheckbox);
};


popupObject.fn = popupObject.prototype = {
	popup : null,
	idObjectId : "", 
	nameObjectId : "", 
	callback : null, 
	useCheckbox : false,
	selection : new Array(),
		
	constructor: popupObject,
	init: function( popup, idObjectId, nameObjectId, callbackFun, useCheckbox ) {
		this.popup = popup;
		this.idObjectId = idObjectId;
		this.nameObjectId = nameObjectId;
		this.useCheckbox = useCheckbox;
		
		if (callbackFun != null && callbackFun != ''){
			this.callback = callbackObjects(callbackFun,idObjectId);
		}
		this.selection = new Array();
		
		this.initPopup = function(){
			popupObject.div.initPopup(this, popup, idObjectId, nameObjectId, this.callback, useCheckbox,this.selection);
		};
		
		this.resetSelection = function(){
			this.selection.length = 0;
			popupObject.div.initPopupCheck(this.popup, this.idObjectId, this.nameObjectId, this.useCheckbox, this.selection);
		};
		
		return this;
	}
};



return popupObject;
})();



//-------------------tab页-------------------------
popupObject.tabs = {

	//浮动选择tab切换
	switchTab : function(popup, li){
		$(li).addClass("active").siblings().removeClass("active");

		var index=popup.find(".tab_menu > ul > li").index(li);
		popup.find(".tab_box > div").eq(index).show().siblings().hide();
	},

	//清空上次被选中节点的所有子节点
	clearNodes : function(popup, clickNode){
		popup.find(".tab_box > div").each(function(i){
			if(!($(this).is(":hidden"))){
				$(this).nextAll().remove();
				popup.find(".tab_menu > ul > li").eq(i).nextAll().remove();
			}
		});
	},

	//点击：所先节点为current（替换当前tab名称）、追加tab页，加载下级内容、切换到新的tab页
	setClickNode2Tab : function(popup, clickNode){
		var currentTab = popup.find(".tab_menu > ul > li :last >a");
		currentTab.attr('val',clickNode.attr('val'));
		currentTab.attr('txt',clickNode.attr('txt'));
		currentTab.html(clickNode.html());
	},

	//加载节点
	loadNodes : function(popup, clickNode,idObjectId,nameObjectId,callback,useCheckbox,selection){		
		//点击：所先节点为current（替换当前tab名称）、追加tab页，加载下级内容、切换到新的tab页
		this.setClickNode2Tab(popup, clickNode);
		
		var url = clickNode.attr('xmlSrc');
		if(url == null || url == "") return;
		
		var newDiv = $('<div></div>');
		url += (url.indexOf('?') > -1 ? '&':'?') + 'useCheckbox=' + useCheckbox;
		load(newDiv[0],url,function(){
			if(newDiv.find('.tree-menu-select').children().length == 0 && newDiv.find('.my_node_filter').length == 0) return;
			
			popup.find('#content').append(newDiv);
					
			var li = $('<li class="mainlevel active"><a class="mainlevel_a" val="" txt="">请选择</a></li>');
			popup.find('#title').append(li);
			li.click(function(){
				popupObject.tabs.switchTab(popup,this);
				return false;
			});
			li.click();

			popupObject.tabs.initTab(popup, newDiv,idObjectId,nameObjectId,callback,useCheckbox,selection);
			
			//计算高度
			computePopupHeight(popup, newDiv);
		},null,true);		
		
	},
		
	//初始化tab
	initTab : function(popup, tabDiv,idObjectId,nameObjectId,callback,useCheckbox,selection){
		//字母索引
		var divIndex = idObjectId + '_' + popup.find(".tab_box > div").index(tabDiv);
		tabDiv.find('.letter-filter a').click(function(){
			$(this).addClass("current").siblings("a").removeClass("current");		
			
			var letter = $(this).attr('val');
			var newId = divIndex + letter;
			
			if(tabDiv.find('.jscroll-e').size() > 0){
				var scrollHeight = -(tabDiv.find('.jscroll-c').position().top) + ($(document.getElementById(newId)).offset().top - tabDiv.find('.jscroll-e').offset().top);
				var event = jQuery.Event('my_event_scroll');
				tabDiv.find('.jscroll-c').trigger(event,scrollHeight);
			}
		});
		
		//字母锚点
		tabDiv.find('.letter-filter a').each(function(){
			var letter = $(this).attr('val');
			var newId = divIndex + letter;
			
			$(this).attr('href','javascript:void(0);');
//			$(this).attr('href','#'+newId);
			tabDiv.find('#'+letter).attr('id',newId);
		});
		
		//过滤节点样式
		tabDiv.find('.my_node_filter').click(function(){
			$(this).addClass("current").siblings("a").removeClass("current");
			return false;
		});
		
		//点击可加载节点：先清除选中节点的下级节点，再加载本次选中节点的下级节 点
		tabDiv.find('.my_node_stop,.my_node_filter').click(function(){
			popupObject.tabs.clearNodes(popup, $(this));
			popupObject.tabs.loadNodes(popup, $(this),idObjectId,nameObjectId,callback,useCheckbox,selection);
			return false;
		});
		
		//复选框
		tabDiv.find('.user-sList-checkbox span').click(function(){
			if(!$(this).hasClass('current')){
				$(this).addClass('current');
			}else{
				$(this).removeClass('current');	
			};
		
			popupObject.more.onSelection(popup, selection,$(this).attr("val"),$(this).attr("txt"),$(this).hasClass('current'));
			
			return false;
		});
		
		//单选框(只可点击选择、不加载下级节 点)
		tabDiv.find('.user-sList-radio .my_node').click(function(){
			tabDiv.find('.my_node_root').removeClass('current');
			tabDiv.find('.tree-menu-select').find('.user-sList-radio span').removeClass('current');
			$(this).addClass('current');
			
			popupObject.tabs.setClickNode2Tab(popup, $(this));
			
			var id = $(this).attr("val");
			var name = $(this).attr("txt");
			popupObject.data.submitObject(popup,idObjectId,nameObjectId,id,name,callback);

			return false;
		});
		
		//反选
		tabDiv.find('#selectAllDiv').click(function(){
			//性能有点慢
			//tabDiv.find('.user-sList-checkbox span').click();
			
			tabDiv.find('.user-sList-checkbox span').each(function(){
				var id = $(this).attr("val");
				var name = $(this).attr("txt");
				
				if(!$(this).hasClass('current')){
					$(this).addClass('current');
					
					var param=new paramObject(id,name);
					for(var index=0,len=selection.length;index<len;++index){
						var item = selection[index];
						if(item.id==param.id)
							break;
					}
					if(index>=selection.length)
						selection.push(param);
			
					
				}else{
					$(this).removeClass('current');
					
					for(var index=0,len=selection.length;index<len;index++){
						var item = selection[index];
						if(id==item.id){
							selection.splice(index,1);
							break;
						}
					}
				}
			});
			
			popupObject.more.setNameListHtml(popup,selection);
			return false;
		});
		
			
		//复选状态
		if(useCheckbox){
			popup.find('.user-sList-checkbox span').each(function(){
				for(var index=0,len=selection.length;index<len;index++){
					var item = selection[index];
					if($(this).attr("val")==item.id){
						$(this).addClass('current');
					}
				}
			});
		}
	}
	
};

//-------------------主层-------------------------
popupObject.div = {
	initPopup : function(self, popup,idObjectId,nameObjectId,callback,useCheckbox,selection){
		//绑定切换功能
		var switchSelector = popup.attr('val_switchSelector');
		if("" != switchSelector){
			$(switchSelector).live("click",function(){
				self.resetSelection();
			});
		}
		
		var tabDiv = popup.find('#content > div');
		
		//根节点：样式随 一级子节点 变化；子节点类型分为：可加载子节点、不可加载子节点
		var nodeRoot = tabDiv.find('.my_node_root'); 
		if(nodeRoot.size() > 0){
			if(tabDiv.find('.my_node_stop').length > 0){
				nodeRoot.addClass('my_node_stop');
			}else{
				nodeRoot.addClass('my_node');
			}
		}
		
		//初始化tab内容
		popupObject.tabs.initTab(popup, tabDiv, idObjectId,nameObjectId,callback,useCheckbox,selection);
		
		//确定按钮（复选）
		popup.find('#comfirmCheckbox').click(function(){
			popupObject.data.submitObjects2(popup,idObjectId,nameObjectId,selection,callback);
			
			return false;
		});
		
		//确定按钮（单选中停）：将已选择tab页上的内容置到input中
		var comfirmTreeStop = popup.find('#comfirmTreeStop'); 
		if(tabDiv.find('.my_node_stop').length >0){
			comfirmTreeStop.show();
			
			comfirmTreeStop.click(function(){
				//如果是可加载节点，有可能最后一个tab页是“请选择”
				var item = popup.find(".tab_menu > ul > li").last().find("a");
				while(item.length > 0){
					if(item.attr("val") != ""){
						break;
					}else{
						item = item.parent().prev().find("a");
					}
				}
				var id = item.attr("val");
				var name = item.attr("txt");
				popupObject.data.submitObject(popup,idObjectId,nameObjectId,id,name,callback);
				
				return false;
			});
		}else{
			comfirmTreeStop.hide();
		}
		
		//清除
		popup.find('#cancel').click(function(){
			if(useCheckbox){
				popupObject.more.clearAll(popup,selection);
			}else{//TODO
				var currentTab = popup.find(".tab_menu > ul > li :last > a");
				var id = "";
				if(currentTab.attr('val')==""){
					id = $(".my_node.current").attr('val');
				}else{
					id = currentTab.attr('val');
				}
				//清空选中状态
				setObjectUnchecked(popup,id);
				
				currentTab.attr('val','');
				currentTab.attr('txt','');
				currentTab.html('请选择');
			}
			
			popupObject.data.cancelObjects(popup,idObjectId,nameObjectId,callback);
			
			return false;
		});
		
		
		//初始化选择项
		this.initPopupCheck(popup, idObjectId, nameObjectId, useCheckbox, selection);		
			
		//已选择项
		popup.find('.address-selected').on('click','.level-box .show-part',function(){
			$(this).removeClass('show-part').addClass('show-all').text('展开');
			$(this).siblings('.level-list').css('height','20px');
		});
		popup.find('.address-selected').on('click','.level-box .show-all',function(){
			$(this).removeClass('show-all').addClass('show-part').text('收起');
			$(this).siblings('.level-list').css('height','auto');
		});
			
		//浮动选择tab切换	
		popup.find(".tab_menu > ul > li").click(function(){
			popupObject.tabs.switchTab(popup, this);
		});
				
		//div模拟select下拉框select_box02
		//mouseover/mouseout展开或关闭下拉菜单
		popup.click(function(event,sign){
			//如果同一个页面中有多个层，则先关闭已打开的。如果是同一个对象先关闭再打开，IE下时的滚动条会滚动到页面的初始位置
			$(".select_box02").each(function(){
				if(this != popup[0]){
					$(this).children(".select_current02").removeClass("select_current02_hover").end().children(".select_list02_container").hide();
				}else{
					$(this).children(".select_current02").addClass("select_current02_hover").end().children(".select_list02_container").show();
				}
			});			
			if(sign == null || sign == 'undefined') sign = true;
			return sign;
		});
		
		//关闭
		popup.find('.select_list02_container .close').click(function(e){
			popupObject.div.closeObjectDivs(popup,null);
			return false;
		});
		
		//计算高度
		computePopupHeight(popup, tabDiv);
		
		//两种样式
		var nestedDiv = $(document.getElementById(idObjectId+'_nestedDiv'));
		if(nestedDiv.find('.select_current02').size() > 0){
			nestedDiv.children().insertBefore(popup.children(".select_list02_container"));
		
		}else{
			//点击嵌入div
			nestedDiv.click(function(){
				var popupDiv = $(document.getElementById(idObjectId+'_popup'));
				popupDiv.find(".select_list02_container").show();
				popupDiv.jWindowOpen({
					modal:true,
					center:true
				});
				return false;
			});
			
			//构造弹出层
			var width = popup.find(".select_list02_container").width();
			var title = nestedDiv.children().text();
			var newDiv = $('<div class="popUp-layer" id="'+idObjectId+'_popup" style="display:none;width:'+width+'px;"></div>');
			newDiv.append('<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>'+title+'</span></p>');

			popup.find('.select_list02_container .close').hide();
			popup.find('.select_list02_container').css('position','relative');
			newDiv.append(popup);
			
			$('#'+idObjectId+'_popup').remove();
			$('#container').append(newDiv);
			
			//点击关闭
			newDiv.find('.close').click(function(e){
				popupObject.div.closeObjectDivs(popup,null);
				return false;
			});
		}
		
		var loadCallbackStr = popup.attr('val_loadCallback');
		if(loadCallbackStr != ''){
			eval(loadCallbackStr+'()');
		}
		//是否显示清空按钮
		var showCancelButtonStr = popup.attr('val_showCancelButton');
		if(showCancelButtonStr=="false"){
			popup.find('#cancel').hide();
		}else{
			popup.find('#cancel').show();
		}
	},
	
	//初始化选择项
	initPopupCheck : function(popup,idObjectId,nameObjectId,useCheckbox,selection){
		var objectIds = popupObject.data.getObjectValue(idObjectId);
		var objectNames = popupObject.data.getObjectValue(nameObjectId);
		if(useCheckbox){
			popupObject.more.initSelection(popup,objectIds,objectNames,selection);
			
			//复选状态
			popup.find('.user-sList-checkbox span').each(function(){
				if(objectIds.indexOf($(this).attr("val")) > -1){
					$(this).addClass('current');
				}else{
					$(this).removeClass('current');
				}
			});
		}else{
			popup.find('.user-sList-radio .my_node').each(function(){
				if(objectIds.indexOf($(this).attr("val")) > -1){
					$(this).addClass('current');
				}else{
					$(this).removeClass('current');
				}
			});
		}
	},
	
	closeObjectDivs : function(popup,callback) {
	  if(popup.find('.select_current02').size() > 0){
		  popup.children(".select_current02").removeClass("select_current02_hover").end().children(".select_list02_container").hide();
	  }else{
		  popup.parent().jWindowClose();
	  }
	  
	  if (callback != null && callback != "") {
		  callback();
	  }
	}

};


//-------------------操作数据-------------------------
popupObject.data = {
	setObjectValue: function(id, value){
		if(value == undefined) return;
		
		var obj = this.getMainWindowElementById(id);
		if(obj){
			if(obj.tagName == 'INPUT' || obj.tagName == 'TEXTAREA'){
				obj.value = value;
			}else{
				$(obj).html(value);
			}
		}
	},
	
	getObjectValue: function(id){
		var obj = this.getMainWindowElementById(id);
		if(obj){
			if(obj.tagName == 'INPUT' || obj.tagName == 'TEXTAREA'){
				return obj.value;
			}else{
				return $(obj).html();
			}
		}else{
			return "";
		}
	},
	
	getMainWindowElementById : function(id){
		var obj = document.getElementById(id);
		if(null != obj){
			return obj;
		}
		
		obj = parent.document.getElementById(id);
		if(null != obj){
			return obj;
		}
		
		obj = parent.parent.document.getElementById(id);			
		if(null != obj){
			return obj;
		}
		return null;
	},
	
	submitObject : function(popup,idObjectId,nameObjectId,id,name,callback){
		this.setObjectValue(idObjectId, id);
		this.setObjectValue(nameObjectId, name);
		popupObject.div.closeObjectDivs(popup,callback);
	},
	
	//确定
	submitObjects2 : function(popup,idObjectId,nameObjectId,selection,callback){
		var objectIds="";
	    var objectNames = "";
		for(var i=0;i<selection.length;i++){
		   var item = selection[i];
		   if(i == 0){
		      objectIds += item.id ;
		      objectNames+=item.name;
		   }else{
		      objectIds += ","+item.id ;
		      objectNames+=","+item.name;
		   }
						
		}

	  	this.setObjectValue(idObjectId, objectIds);
	  	this.setObjectValue(nameObjectId, objectNames);
	  	
	  	popupObject.div.closeObjectDivs(popup,callback);
	},
	
	cancelObjects : function(popup,idObjectId,nameObjectId,callback) {		
		this.setObjectValue(idObjectId, "");		
		this.setObjectValue(nameObjectId, "");
		
		popupObject.div.closeObjectDivs(popup,callback);
	}
	
};

//-------------------多选-------------------------
popupObject.more =  {
	
	//初始化
	initSelection : function(popup,objectIds,objectNames,selection){
		if(objectIds == null || objectIds == ""){
			selection.length = 0;
			this.setNameListHtml(popup,selection);
			return;
		}
		
		var idArr = objectIds.split(",");
		var nameArr = objectNames.split(",");
		for (var index = 0, len = idArr.length; index < len; ++index) {
			var param=new paramObject(idArr[index],nameArr[index]);
			selection.push(param);
		}
		this.setNameListHtml(popup,selection);
	},

	//设置对象名称列表的HTML内容
	setNameListHtml : function(popup,selection){
		var nameStr="";
		for (var index = 0, len = selection.length; index < len; ++index) {
			var item = selection[index];			
			nameStr=nameStr+"<a href=\"javascript:void(0);\" val=\""+item.id+"\">"+item.name+"</a>";
		}
		popup.find("#nameList").html(nameStr);
		
		popup.find("#nameList a").click(function(){
			popupObject.more.deleteObject(popup,selection,$(this).attr('val'));
			return false;
		});
	},

		
	//双击删除某一个已经被选择的对象
	deleteObject : function(popup,selection, id){
		for(var index=0,len=selection.length;index<len;index++){
			var item = selection[index];
			if(id==item.id){
				selection.splice(index,1);
				this.setNameListHtml(popup,selection);
				setObjectUnchecked(popup,id);
				//document.getElementById("selectAllDiv").checked=false;
				break;
			}
		}
	},


	//点击某个对象
	onSelection : function(popup,selection,id,name,isAdd){
		var param=new paramObject(id,name);
		if(isAdd){
			var index = 0;
			for(len=selection.length;index<len;++index){
				var item = selection[index];
				if(item.id==param.id)
					break;
			}
			if(index>=selection.length)
				selection.push(param);
	
			this.setNameListHtml(popup,selection);
		}else{
			 this.deleteObject(popup,selection,id);
		}
	},
	
	//点击全选按钮
	onSelectAllClick : function(popup,selection,checked,idsName){
		if(checked){
			this.selectAll(popup,selection,idsName);
		}else{
			this.clearAllOnObjectList(popup,selection,idsName);
		}
	},

	//选择所有
	selectAll : function(popup,selection,idsName){
		var checkBoxes = document.getElementsByName(idsName);
		checkBoxes = Array.from(checkBoxes);
		checkBoxes.each(function iterate(value,index){
			if(!value.checked){
				value.checked=true;
				this.onSelection(popup,selection,value.value,true);
			}	
		});
	},

	//清空页面中对象列表中的所有
	clearAllOnObjectList : function(popup,selection,idsName){ 
		var checkBoxes = document.getElementsByName(idsName);
		checkBoxes = Array.from(checkBoxes);
		checkBoxes.each(function iterate(value,index){
			if(value.checked){
				this.deleteObject(popup,selection,value.value);
			}
		});
	},
	
	//清空所有
	clearAll : function(popup,selection){
		for(var index=selection.length-1;index>=0;--index){
			this.deleteObject(popup,selection,selection[index].id);
		}
	}
	
};

//加载内容：
//初始：请选择（current为空）、一级内容
//点击：所先节点为current（替换上级节点的tab名称）、下级内容
//node: id、code、name、type（部门、教师等）、url、action（点击时的动作）、checkbox（是否复选：叶子节点提供复选功能）、
function loadContent(current,parent,children){
	
	
	
}

function callbackObjects(callback,params) {
	return function(){
	  	if (window[callback] != undefined) {
	         window[callback](params);
	         return;
	  	  }
	  	  if (parent[callback] != undefined) {
	         parent[callback]();
	         return;
	  	  }
	  	  if (parent.parent[callback] != undefined) {
	         parent.parent[callback]();
	         return;
	  	  }
	  	}
}
     

//删除某个对象后设置对象列表中的该对象为unchecked
function setObjectUnchecked(popup,id){
	popup.find('#span_'+id).removeClass('current');	
}

//传递给父页面的参数类
function paramObject(id,name) {
	this.id=id;
	this.name=name;
}


//Expose popupObject to the global object
window.popupObject = popupObject;

})(window);


function computePopupHeight(popup, tabDiv){
	//计算高度：隐藏的必须先显示
	var containerDiv = popup.children(".select_list02_container");
	var hidden = containerDiv.is(":hidden");
	if(hidden){
		containerDiv.show();
	}
	
	var height = popup.attr('val_height');
	var contentNodeDiv = tabDiv.find('#contentNodeDiv');
	var real = contentNodeDiv.height();
	
	//判断外部元素是否隐藏
	var outEle = popup.parent();
	var outEleHidden = false;
	if(real == 0){
		while(outEle.css('display') != 'none'){			
			outEle = outEle.parent();
		}
		outEleHidden = outEle.is(":hidden");
		if(outEleHidden){
			outEle.show();
		}
		real = contentNodeDiv.height();
	}
	var fixEleHeight = popup.find('.select_list02_container').height() - real;	//固定元素高度
	
	var max = height - fixEleHeight;
	var min = 300;
	//至少保证最大高度
	if(max < min){
		max = min;
	}
	if(real > max){
		contentNodeDiv.css({'height':max+'px'});
		contentNodeDiv.jscroll({ W:"5px"//设置滚动条宽度
			,Bar:{  Pos:""//设置滚动条初始化位置在底部
					,Bd:{Out:"#999fa5",Hover:"#5b5c5d"}//设置滚动滚轴边框颜色：鼠标离开(默认)，经过
					,Bg:{Out:"#999fa5",Hover:"#67686a",Focus:"#67686a"}}//设置滚动条滚轴背景：鼠标离开(默认)，经过，点击
			,Btn:{btn:false}//是否显示上下按钮 false为不显示
		});	
	}
	
	if(hidden){
		containerDiv.hide();
	}	
	if(outEleHidden){
		outEle.hide();
	}
}

function showPopupObjects(idObjectId,nameObjectId,url,useCheckbox,params,callback,preset,dynamicParam,width,height){
	var popup = $(document.getElementById(idObjectId +"_div"));
	popup.click(function(){
		if (preset != null && preset != "") {
			if(eval(preset+"()") == false){
				return false;
			}
		}
	});
		
 	url=url+(url.indexOf('?') > -1 ? '&':'?')+"nameObjectId="+nameObjectId+"&idObjectId="+idObjectId+"&useCheckbox="+useCheckbox+"&callback="+callback;
 	if(params != ""){
 		url+="&"+params;
 	} 	
 	
	if (dynamicParam != null && dynamicParam != "") {
		url+="&"+eval(dynamicParam+"()");
	}
	//加载数据
	load(popup.find('.select_list02_container')[0], url);
}
