// 表单数据条件
var FormCondition = (function() {

	var isNotNullString = function(str) {
		return $.trim(str) !== "";
	};

	function FormCondition() {

	}

	FormCondition.prototype = {
		constructor: FormCondition,
		_valid: function(text) {
			//检查公式
			if (text.indexOf("(") >= 0) {
				var nNum1 = text.split("(").length;
				var nNum2 = text.split(")").length;
				if (nNum1 !== nNum2) {
					return false;
				}
			}
			return true;
		},
		_getCurrentIndex: function() {
			var node=document.getElementById("condition_select");
			var current, opts = node.children;
			if (node.children && node.children.length>0) {
				current = opts.length - 1;
			}
			return current;
		},
		addLeftParenthesis: function(logic) {
			var node=document.getElementById("condition_select");
			var opts = node.children,
					len = opts.length,
					currentIndex,
					ptOpt,
					text;

			if (len > 0) {
				//检查是否有条件
				currentIndex = this._getCurrentIndex();
			} else {
				//有条件才能添加左括号表达式
				Ui.tip(L.WF.CONDITION_NEED, "warning");
				return;
			}

			text = opts[currentIndex].innerText;

			//无法编辑已经“发生关系”的条件 O(∩_∩)O~
			if (($.trim(text).substr(-3, 3) === '&&') || ($.trim(text).substr(-2, 2) === '||')) {
				Ui.tip(L.WF.CONDITION_CANNOT_EDIT, "warning");
				return;
			}
			if (text.indexOf('(') >= 0) { //检查括号匹配
				if (!this._valid(text)) {
					Ui.tip(L.WF.CONDITION_FORMAT_ERROR, "warning");
					return;
				} else {
					text += " " + logic;
				}
			} else {
				text = text + " " + logic;
			}
			node.children[currentIndex].innerHTML="<span><i></i>"+text+"</span>"
			ptOpt = document.createElement('p');
			ptOpt.innerHTML ="<span><i></i>"+"( "+"</span>";
			node.appendChild(ptOpt);
			
			//绑定事件
			conditions.bindEvents();
		},
		/**
		 * 增加右括号表达式
		 */
		addRightParenthesis: function(logic) {
			var node=document.getElementById("condition_select");
			var opts = node.children,
					len = opts.length,
					currentIndex,
					text;

			if (len > 0) {
				currentIndex = this._getCurrentIndex();
			} else {
				Ui.tip(L.WF.CONDITION_NEED, "warning");
				return;
			}

			text = node.children[currentIndex].innerText;
			if (($.trim(text).substr(-3, 3) === '&&') || ($.trim(text).substr(-2, 2) === '||')) {
				Ui.tip(L.WF.CONDITION_CANNOT_EDIT, "warning");
				return;
			}
			if (($.trim(text).substr(-1, 1) === '(')) {
				Ui.tip(L.WF.CONDITION_NEED, "warning");
				return;
			}

			if (!this._valid(text)) {
				text = text + ")";
			}
			opts[currentIndex].innerHTML="<span><i></i>"+text+"</span>"
			//绑定事件
			conditions.bindEvents();
		},
		addCondition: function(field, operator, value, logic) {
			var node=document.getElementById("condition_select");
			var toAdd = true,
					len = node.children.length,
					inBracket = false,
					text,
					newText,
					optionNode;
			logic = logic || "&&";
			if (isNotNullString(field) && isNotNullString(operator) && isNotNullString(value)) {
				if (len > 0) {
					text = node.children[len - 1].innerText;

					if (!this._valid(text)) {
						toAdd = false;
					}
				}
				if (value.indexOf("'") >= 0) {
					Ui.tip(L.WF.CONDITION_INVAILD, "warning");
					return;
				}

				//newText = "'" + field + "' " + operator + " '" + value + "'";
				newText =  field + " " + operator + " " + value ;

				for (var i = 0; i < len; i++) {
					if (node.children[i].innerText.indexOf(newText) >= 0) {
						Ui.tip(L.WF.CONDITION_REPEAT, "warning");
						return;
					}
				}

				if (toAdd) {
					optionNode = document.createElement('p');
					optionNode.innerHTML ="<span><i></i>"+newText+"</span>";
					node.appendChild(optionNode);

					if (len > 0) {
						node.children[len - 1].innerHTML="<span><i></i>"+node.children[len - 1].innerText+"  " + logic+"</span>"
						//this.node.children[len - 1].innerText += "  " + logic;
					}
				} else {
					inBracket = $.trim(node.children[len - 1].innerText).substr(-1, 1) === "(";
					node.children[len - 1].innerHTML="<span><i></i>"+node.children[len - 1].innerText + (inBracket ? newText : " " + logic + " " + newText)+"</span>";
				}
			} else {
				Ui.tip(L.WF.CONDITION_INCOMPLETE, "warning");
				return;
			}
			//绑定事件
			conditions.bindEvents();
		},
		getConditions: function() {
			var node=document.getElementById("condition_select"),result = "";
			for (var i = 0; i < node.children.length; i++) {
				if(i==0){
					result +="${";
				}
				result += node.children[i].innerText+"";
				if(i==node.children.length-1){
					result +="}";
				}
			}
			return result;
		},
		removeCondition: function(i) {
			var node=document.getElementById("condition_select")
			if (typeof node.children[i + 1] === "undefined" && typeof node.children[i - 1] !== "undefined") {
				node.children[i - 1].innerHTML="<span><i></i>"+node.children[i - 1].innerText.replace(/(&&|\|\|)$/, '')+"</span>";
				//绑定事件
				conditions.bindEvents();
			}
		}
	};
	return FormCondition;
}());

//表单数据条件
var conditions = {
	bindEvents: function() {
		$('.ui-condition-wrap span').unbind('hover').hover(
			function(){
				$(this).parents('.ui-condition-wrap').find('span').removeClass('hover');
				$(this).addClass('hover');
			},function(){
				$(this).removeClass('hover');
			}
		);
		$('.ui-condition-wrap span i').unbind('click').click(function(){
			formCondition.removeCondition($(this).parent('span').parent("p").index());
			$(this).parent('span').parent("p").remove();
		});
	}
};

//表单数据条件
var formCondition = new FormCondition();