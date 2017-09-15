var wfDesigner = (function(){
	var Wp = {
		sourceOptions: { 
			filter: "i" 
		},
		
		//设置目标的属性 
		targetOptions: {
			dropOptions: { hoverClass: "wf-step-hover"}
		},
		addStep: function($steps, options){
			options = options || {};
			// 初始化拖拽				
			if(options.draggable !== false) {
				jsPlumb.draggable($steps, {
				  containment:"parent"
				});
			}
			// 初始化连接源
			if(options.isSource !== false) {
				// 如果需要控制最大连接数
				var _sourceOptions={};
				if(options.maxConnection != -1){
					var maxConnection=options.maxConnection;
					var source_option_for_max_connection= { 
						maxConnections:maxConnection,//最大连接数
						onMaxConnections:function(info) {//绑定一个函数，当到达最大连接个数时弹出提示框
							Ui.tip(U.lang('WF.MAX_CONNECTION'), "warning");
						}	
					};
					_sourceOptions = $.extend(true,{},this.sourceOptions, source_option_for_max_connection);
				}else{
					_sourceOptions=this.sourceOptions;
				}
				jsPlumb.makeSource($steps,_sourceOptions);	
			}

			// 初始化连接目标
			if(options.isTarget !== false) {
				// 如果需要控制最大连接数
				var _targetOptions={};
				if(options.maxConnection != -1){
					var maxConnection=options.maxConnection;
					var target_option_for_max_connection= { 
						maxConnections:maxConnection,//最大连接数
						onMaxConnections:function(info) {//绑定一个函数，当到达最大连接个数时弹出提示框
							if(info.element.id =='StartNoneEvent_-1'){
								Ui.tip(U.lang('WF.NOT_TO_START'), "warning");
							}else{
								Ui.tip(U.lang('WF.MAX_CONNECTION'), "warning");
							}
						}	
					};
					_targetOptions = $.extend(true,{},this.targetOptions, target_option_for_max_connection);
				}else{
					_targetOptions=this.targetOptions;
				}
		    	jsPlumb.makeTarget($steps, _targetOptions);
			}
		    
			//绑定tip事件
			$('.mouseHover-from').hover(
				function(){
					var divWidth=$(this).parent().position().left+$(this).children('.mouseHover-tips').width()+75;
					var divHeight=$(this).parent().position().top+$(this).children('.mouseHover-tips').height()+45;
					var canvasWidth=$("#wf_designer_canvas").width();
					var canvasHeight=$("#wf_designer_canvas").height();
					var tipsWidth=divWidth-canvasWidth>=0?canvasWidth-divWidth:0;
					var tipsHeight=divHeight-canvasHeight>=0?canvasHeight-divHeight:0;
					var css={
							top:tipsHeight,
							left:tipsWidth
						};
					$(this).children('.mouseHover-tips').css(css).show();
				},function(){
					$(this).children('.mouseHover-tips').hide();
				}
			);
			return $steps;
		}
	}
	var defaults = {
		offset:  80, //自动步骤间距
		autoNamePrefix:L.WF.ADD_STEP,
		fixedStepTpl: "<div class='<%=cls%>' id='<%=id%>' type='<%=type%>' style='top: <%=top%>px; left: <%=left%>px;'><i></i><p class='tt'><%=name%></p></div>",
		stepTpl: "<div class='<%=cls%>' id='<%=id%>' type='<%=type%>' style='top: <%=top%>px; left: <%=left%>px;'><i></i><p class='tt'><%=subname%></p><p><%=taskUser%></p></div>",
		stepTpl4Tip: "<div class='<%=cls%>' id='<%=id%>' type='<%=type%>' style='top: <%=top%>px; left: <%=left%>px;'><i></i><p class='tt'><%=subname%></p><p class='mouseHover-from wf-tips'><%=subTaskUser%><span class='mouseHover-tips'><%=taskUser%></span></p></div>",
		gateTpl:"<div class='<%=cls%>' id='<%=id%>' type='<%=type%>'  style='top: <%=top%>px; left: <%=left%>px;'><i class='top'></i><i class='right'></i><i class='bottom'></i><i class='left'></i><p class='tt'></p></div>",
		cls: "wf-step",
		activeCls: "wf-step-active",
		operation: {
			draggable: false,//设置不能拖拽
			isSource: false,
			isTarget: false,
			detach: true,
			maxConnection:1
		}
	}
	
	var W = function($container, plumb, options){
		var options = $.extend(true, {}, defaults, options),
			that = this,
			currentIndex = 0, // 当前使用序号，新建时无序号时，序号按currentIndex + 1,
			currentStep,//当前处理的步骤对象
			prevId = -1, // 用于储存上一步骤Id, 由于初始化时，第一个步骤为“开始”, 其Id 为 -1
			selectedId,
			stepCache = {}, // 用于储存步骤对应的数据
			stepElemCache = {},
			connectCache = {},
			stepMap = new Map(),//用于存放步骤数据
			stateClassMap= new Map(),//用于存放节点状态与对应cls的数据
			stepStateMap=new Map(),//步骤状态map
			targetConnectionMap=new Map(),//key:targetId;value:sourceId
			sourceConnectionMap=new Map(),//key:sourceId;value:targetId
			connectionMap=new Map(),//key:sourceId+targetId value:connectionType
			sequenceFlowMap=new Map(),//用于存放连接的数据
			targetEnabledMap=new Map(),//用来存放是否可作为target
			sourceEnabledMap=new Map(),//用来存放是否可作为source
			previewConnectTypeMap=new Map(),//用于存放预览 连线类型的数据
			conditionMap = new Map(),//用于存放条件结点的条件
			permissionConnectTypeMap=new Map(),//用于存放权限 连线类型的数据
			connectTypeMap=new Map();//用于存放连线类型的数据
			
		var _validateParam = function(params){
				if(!params || typeof params.id === "undefined") {
					// $.error("参数错误，未接收到ID");
					return false;
				}
				if(that.hasId(params.id)){
					// $.error("ID重复");
					return false;
				}
				if(that.hasIndex(params.index)){
					// $.error("index重复");
					return false;
				}			

				return true;
			},

			_addNode = function(params){
				var nodes;
				//判断是步骤还是网关还是开始结束节点
				if(params.type =="UserTask"){
					if(params.name.length > 3){
						params.subname=params.name.substring(0,3)+"...";
					}else{
						params.subname=params.name;
					}
					var taskUser=params.taskUser;
					if(taskUser.length > 3){
						var subTaskUser=taskUser.substring(0,3)+"...";
						params.subTaskUser=subTaskUser;
						//params.cls=params.cls+" ";
						nodes=$.tmpl(options.stepTpl4Tip, params);
						
					}else{
						nodes=$.tmpl(options.stepTpl, params);
					}
				}else if(params.type == "ParallelGateway"){
					nodes=$.tmpl(options.gateTpl, params);
				}else if(params.type == "ExclusiveGateway"){
					nodes=$.tmpl(options.gateTpl, params);
				}else if(params.type =="StartNoneEvent"||params.type =="EndNoneEvent"){
					nodes=$.tmpl(options.fixedStepTpl, params);
				}
				nodes.appendTo("#wf_designer_canvas");
				return nodes;
			},

			_getIdByIndex = function(index){
				if(index) {
					for(var id in stepCache){
						if(stepCache[id].index === index){
							return id;
						}
					}
				}
			},

			// 用于获取对应的jq对象
			// param: id 
			_getElem = function(id){ 
				return stepElemCache[id] || null;
			},


			// 获取jq对象的宽高，返回格式{width: n, height: n}
			_getOffset = function($node){
				return {
					width: $node.outerWidth(),
					height: $node.outerHeight()
				}
			},
			// 当新建步骤时，会自动计算其出现的位置，此函数用于计算出该位置
			// return  { left: n, top: n }
			_reverse = false,
			_getCalculatedPosition = function($step){
				var $prevStepElem,
					prevStepOffset,
					prevStepData,
					prevStepPosition,
					containerOffset,
					stepOffset,
					stepPosition = {
						left: options.offset,
						top: options.offset
					};

				$prevStepElem = _getElem(prevId);
				
				if($prevStepElem && $prevStepElem.length){
					// 上一步骤的数据
					prevStepData = that.getData(prevId);
					// 上一步骤的宽高值
					prevStepOffset = _getOffset($prevStepElem);
		
					// 上一步骤的位置
					prevStepPosition = {
						left: parseInt($("#"+prevStepData.id).position().left, 10),
						top: parseInt($("#"+prevStepData.id).position().top, 10)
					};
					// 窗口的宽高值
					containerOffset = _getOffset($("#wf_designer_canvas"));
					// 当前(即新建)步骤的宽高值
					stepOffset = _getOffset($step)

					// 让当前步骤出现在上一步骤的右边 20px(offset) 处 (反转情况下则是左边20px处)
					stepPosition.left = prevStepPosition.left + (_reverse ? -(prevStepOffset.width + options.offset) : (prevStepOffset.width + options.offset));
					stepPosition.top = prevStepPosition.top;

					// 当新建步骤超出容器右边时，向下延伸, 反转水平方向
					if(stepPosition.left + stepOffset.width > containerOffset.width){
						_reverse = true;

						stepPosition.left = prevStepPosition.left;
						stepPosition.top = prevStepPosition.top + prevStepOffset.height + options.offset;
						// 当新建步骤超出容器高度时，停留在底部
						if(stepPosition.top + stepOffset.height > containerOffset.height) {
							stepPosition.top = containerOffset.height - stepOffset.height;
						}
					}
					// 当新建步骤超出容器左边时，向下延伸, 反转水平方向
					if(stepPosition.left < 0) {
						_reverse = false;

						stepPosition.left = prevStepPosition.left;
						stepPosition.top = prevStepPosition.top + prevStepOffset.height + options.offset;
						// 当新建步骤超出容器高度时，停留在底部
						if(stepPosition.top + stepOffset.height > containerOffset.height) {
							stepPosition.top = containerOffset.height - stepOffset.height;
						}
					}
				}

				return stepPosition
			},
				
			_getOriginalId = function(id){
				if(typeof id === "string") {
					return id.substring(id.lastIndexOf('_')+1)
				}
			},
			// 增加普通步骤，isNew用来标识此步骤是否手动新增，true为新增，false从数据库中还原
			_addStep = function(param, opt, isNew) { 
				var $step,
					calculatedPosition;

				// 当新增步骤时，预期param参数只包含id字段 -- { id: n }
				param = $.extend({
					top: options.offset,
					left: options.offset,
					cls: options.cls
				}, param);
				if(param.id=="EndNoneEvent_0"){
					opt = $.extend(true,{},options.operation, opt,{maxConnection:-1})
				}else{
					opt = $.extend(true,{},options.operation, opt)
				}
				
				if(typeof param.index === "undefined"){
					param.index = that.getMaxIndex() + 1;
				}
				if(typeof param.name === "undefined") {
					param.name = options.autoNamePrefix + " " + param.index;
				}

				$step = _addNode(param);
				prevId = _getIdByIndex(that.getMaxIndex())
				// 新增时自动计算生成位置
				if(isNew) {
					var _left=param.left;
					//特殊处理 重新计算
					if(param.left == -1){
						var $container = $("#wf_designer_canvas");
						_left=($container.outerWidth() - $step.outerWidth())/2;
						if(param.inParallelGateway){
							var beforeStepWidth=$("#"+param.beforeStepId).outerWidth();
							_left=param.beforeStepLeft-($step.outerWidth()-beforeStepWidth)/2;
						}
					}
					calculatedPosition = {
						left: _left,
						top: $step.top
					};
					//_getCalculatedPosition($step);
					$step.css(calculatedPosition);
					$.extend(param, calculatedPosition);
				}

				//prevId = param.index;

				stepCache[param.index] = param;
				stepElemCache[param.index] = $step;
				// 作为流程图的一部分初始化(使其可连接)
				Wp.addStep($step, opt);
				return $step;
			},

			_bindEvent = function(){
				// 连线事件
				plumb.bind("connection", function(con) {
					//判断如果soureceId为条件结点的话 增加
					var connection =con.connection;
					var id = connection.id,
					sourceId = con.sourceId,
					targetId = con.targetId;
					
					//设置的类型有edit,uncomplete,complete
					if(connection.getType() =="")
						connection.setType("edit");
					
					if(sourceId.indexOf("ExclusiveGateway_") !== -1){
						//connection.getOverlay("label").setLabel("设置条件");
						if(conditionMap.getValue(sourceId+targetId)){
							connection.setLabel({label:conditionMap.getValue(sourceId+targetId).name,labelStyle:"",location:0.5,cssClass:"aLabel"});
						}else{
							connection.setLabel({label:"条件设置",labelStyle:"",location:0.5,cssClass:"aLabel"});
						}
					}

					connectionMap.put(sourceId+targetId,connection.getType()[0]);
					sourceConnectionMap.put(sourceId+"NEW",targetId);
					targetConnectionMap.put(targetId+"NEW",sourceId);
					if(id && sourceId && targetId) {
						connectCache[id] = sourceId + "," + targetId;
					}

				});

				if(options.operation.detach) {
					// 连接器移除时，同步移除缓存数据
					plumb.bind("connectionDetached", function(con){ 
						if(con && con.connection){
							delete connectCache[con.connection.id];
						}
					})
					// 点击连接器时，将连接器删除
					plumb.bind("click", function(con,event){
						var nodeName;
						return;//取消点击删除线
						if(event.srcElement){
							nodeName=event.srcElement.nodeName;
						}else{
							nodeName=event.target.nodeName;
						}
						if(nodeName !== "DIV"){
							//判断是否可以删除
							if(con.getType()[0]=== "edit"||con.getType()[0]=== "start"){
								var sourceId = con.sourceId,
								targetId = con.targetId;
								connectionMap.remove(sourceId+targetId);
								conditionMap.remove(sourceId+targetId);
								sourceConnectionMap.remove(sourceId+"NEW");
								targetConnectionMap.remove(targetId+"NEW");
								sourceConnectionMap.remove(sourceId);
								targetConnectionMap.remove(targetId);
								plumb.detach(con);
							}
						}
					})
					// 双击条件 弹出
					plumb.bind("dblclick", function(con,event){
						var nodeName;
						if(event.srcElement){
							nodeName=event.srcElement.nodeName;
						}else{
							nodeName=event.target.nodeName;
						}
						if(con.getType()[0]=== "edit" || con.getType()[0]=== "start"){
							if(nodeName === "DIV"){
								wfControll.editCondition(con,con.sourceId,con.targetId)
							}
						}
					});
				}

				if(options.operation.draggable) {
					// 移动结束时，更新数据
					$container.on( "dragstop",  ".wf-step", function( event, ui ) { 
					} );
				}
			}

		this.getMaxIndex = function(){
			var max = 0;
			for(var i in stepCache) {
				max = Math.max(max, stepCache[i].index);
			}
			return max;
		};

		this.hasIndex = function(index){
			return typeof index !== "undefined" && stepCache.hasOwnProperty(index);
		};

		this.hasId = function(id) {
			if(typeof id !== "undefined"){			
				for(var i in stepCache){
					if(stepCache[i].id === id){
						return true;
					}	
				}
			}
			return false;
		};

		// 用于获取缓存的数据，
		this.getData = function(id){
			return stepCache[id] || null;
		};
			
		this.updateData = function(id, newData){
			var data = that.getData(id);
			if(newData && data){
				delete newData.id;
				delete newData.index;
				$.extend(data, newData);
			}	
			return data;
		};
		
		this.updateStep = function(id, newData){
			var that = this;
			var data = that.getData(id);
			if(newData && data){
				delete newData.id;
				delete newData.index;
				$.extend(data, newData);
			}
			//先删除 在新建
			that.removeStep(id,false);
			that.addStep(data,{},false);
			if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify"){//非编辑才控制
				var permission=0;
				var currentStep=this.getCurrentStep();
				if(currentStep){
					permission=currentStep.properties.permission;
				}
				
				plumb.setTargetEnabled(data.id, true);
				plumb.setSourceEnabled(data.id, true);
				var _nextStepIds = that.getSourceAndTargetRelation(data.id);
				if(typeof(_nextStepIds) == "undefined"){
					_nextStepIds = that.getSourceAndTargetRelation(data.id+"NEW");
				}
				if(_nextStepIds){
					var _nextStepIdArray=_nextStepIds.split(",");
					for (var i = 0; i < _nextStepIdArray.length; i++) {
						var targetEnabled =plumb.isTargetEnabled(_nextStepIdArray[i]);
						//先开放可以放线的权限，完成后再设置为原来的值
						plumb.setTargetEnabled(_nextStepIdArray[i], true);
						that.addConnectByStt(data.id,_nextStepIdArray[i],that.getConnectionMap(data.id+_nextStepIdArray[i]));
						plumb.setTargetEnabled(_nextStepIdArray[i], targetEnabled);
					}
				}
				var _previewStepIds = that.getTargetAndSourceRelation(data.id);
				if(typeof(_previewStepIds) =="undefined"){
					_previewStepIds = that.getTargetAndSourceRelation(data.id+"NEW");
				}
				if(_previewStepIds){
					var _previewStepIdArray=_previewStepIds.split(",");
					for (var i = 0; i < _previewStepIdArray.length; i++) {
						var sourceEnabled = plumb.isSourceEnabled(_previewStepIdArray[i]);
						//先开放可以拉线的权限，完成后再设置为原来的值
						plumb.setSourceEnabled(_previewStepIdArray[i], true);
						that.addConnectByStt(_previewStepIdArray[i],data.id,that.getConnectionMap(_previewStepIdArray[i]+data.id));
						plumb.setSourceEnabled(_previewStepIdArray[i], sourceEnabled);
					}
				}
				var _state =this.getStepStateMap(data.id);
				if(_state){
					if(permission===-1){
						plumb.setTargetEnabled(data.id, that.getTargetEnabledMap(_state));
						plumb.setSourceEnabled(data.id, that.getSourceEnabledMap(_state));
						$(uncompleteStepSelector).contextMenu("step_context_menu", stepContextMenuSettings);
					}else{
						//当前步骤
						var permissionArray = that.tranfserPermission(permission);
						//选中步骤
		 				var _permissionArray = that.tranfserPermission(data.permission);
		 				if(_permissionArray[0])//remove
		 					$("#"+data.id).addClass("wf-step-next-remove");
		 				if(_permissionArray[1])//modify
		 					$("#"+data.id).addClass("wf-step-next-modify");	
		 				if(_permissionArray[0] && _permissionArray[1])
		 					$("#"+data.id).addClass("wf-step-next-remove-modify");	
		 				
		 				if(permissionArray[2]){//insert
		 					plumb.setTargetEnabled(data.id, true);
						}else{
							plumb.setTargetEnabled(data.id, false);
						}
		 				plumb.setSourceEnabled(data.id, false);
		 				
		 				$(nextModifyStepSelector).contextMenu("step_context_menu_modify", stepContextMenuSettings);
		 				$(nextRemoveStepSelector).contextMenu("step_context_menu_remove", stepContextMenuSettings);
		 				$(nextRMStepSelector).contextMenu("step_context_menu", stepContextMenuSettings);	
					}
				}else{
					plumb.setTargetEnabled(data.id, true);
					plumb.setSourceEnabled(data.id, true);
					$(normalStepSelector).contextMenu("step_context_menu", stepContextMenuSettings);
				}
			}else{
				var _nextStepIds = that.getSourceAndTargetRelation(data.id);
				if(typeof(_nextStepIds) == "undefined"){
					_nextStepIds = that.getSourceAndTargetRelation(data.id+"NEW");
				}
				if(_nextStepIds){
					var _nextStepIdArray=_nextStepIds.split(",");
					for (var i = 0; i < _nextStepIdArray.length; i++) {
						that.addConnectByStt(data.id,_nextStepIdArray[i],that.getConnectionMap(data.id+_nextStepIdArray[i]));
					}
				}
				var _previewStepIds = that.getTargetAndSourceRelation(data.id);
				if(typeof(_previewStepIds) =="undefined"){
					_previewStepIds = that.getTargetAndSourceRelation(data.id+"NEW");
				}
				if(_previewStepIds){
					var _previewStepIdArray=_previewStepIds.split(",");
					for (var i = 0; i < _previewStepIdArray.length; i++) {
						that.addConnectByStt(_previewStepIdArray[i],data.id,that.getConnectionMap(_previewStepIdArray[i]+data.id));
					}
				}
				$(normalStepSelector).contextMenu("step_context_menu", stepContextMenuSettings);	
			}
			return data;
		};

		this.addStep = function(param, opt, isNew) {
			if(_validateParam(param)) {
				return _addStep(param, opt, isNew)
			}
		};

		this.addSteps = function(params, opt, isNew) {
			var that = this,
				$result = $();

			if($.isArray(params)) {
				params.sort(function(a, b){
					return a.index > b.index;
				})
				$.each(params, function(index, param){
					//网关节点不受连接数限制
					if(param.type ==='ParallelGateway' ||param.type ==='ExclusiveGateway' ){
						var gateOpt = opt || {};
						$.extend(gateOpt,{maxConnection:-1});
						//gateOpt={maxConnection:-1};//不限连接数
						$result = $result.add(that.addStep(param,gateOpt, isNew));
					}else{
						$result = $result.add(that.addStep(param,opt, isNew));
					}
				})
			} else {
				$result = that.addStep(params, opt, isNew)
			}
			return $result;
		};
		
		this.addConnectByStt = function(source,target,type){
			return plumb.connect({
				source: source,
				target: target,
				type: type
			})
		},

		this.addConnect = function(connect){
			var con;
			if(typeof connect === "string") {
				con = connect.split(",", 3);
				return plumb.connect({
					source: con[0],
					target: con[1],
					type: con[2]
				})
			}
		},

		this.addConnects = function(connects) {
			var that = this,
				ret = [];
			if($.isArray(connects)) {
				$.each(connects, function(index, connect){
					ret.push(that.addConnect(connect));
				})
			} else {
				ret.push(that.addConnect(connects));
			}
			return ret;
		};

		this.clearConnect = function(){
			plumb.detachEveryConnection();
			connectionMap=new Map();
			sourceConnectionMap=new Map();
			targetConnectionMap=new Map();
			connectCache = {};
		};
		
		this.detachEveryConnection = function(){
			plumb.detachEveryConnection();
		};
		
		this.removeConnect=function(con){
			var sourceId = con.sourceId,
			targetId = con.targetId;
			connectionMap.remove(sourceId+targetId);
			sourceConnectionMap.remove(sourceId+"NEW");
			targetConnectionMap.remove(targetId+"NEW");
			sourceConnectionMap.remove(sourceId);
			targetConnectionMap.remove(targetId);
			plumb.detach(con);
		}

		this.removeStep = function(id,isdeleted){
			var $elem = _getElem(id);
			$elem && plumb.removeAllEndpoints($elem);
			//$elem && plumb.remove($elem);
			//如果是操作是真的删除 删除对应的记录
			if(isdeleted){
				if(sourceConnectionMap.containsKey($elem[0].id)){
					targetConnectionMap.remove(sourceConnectionMap.getValue($elem[0].id));
					targetConnectionMap.remove(sourceConnectionMap.getValue($elem[0].id)+"NEW");
					sourceConnectionMap.remove($elem[0].id);
					sourceConnectionMap.remove($elem[0].id+"NEW");
				}
				if(targetConnectionMap.containsKey($elem[0].id)){
					sourceConnectionMap.remove(targetConnectionMap.getValue($elem[0].id));
					sourceConnectionMap.remove(targetConnectionMap.getValue($elem[0].id)+"NEW");
					targetConnectionMap.remove($elem[0].id);
					targetConnectionMap.remove($elem[0].id+"NEW");
				}
			}
			$elem && $elem.remove();
			delete stepCache[id]
			delete stepElemCache[id];
			selectedId = void(0);
			if(prevId === id) {
				prevId = _getIdByIndex(this.getMaxIndex());
			}
		};	

		this.getConnects = function(){
			return connectCache;
		};

		this.getSteps = function(){
			return stepCache;
		};

		this.unselect = function(){
			$container.find(options.activeCls).removeClass(options.activeCls);
			selectedId = void(0);
		}

		this.select = function(id){
			if(typeof id !== "undefined"){
				for(var i in stepCache) {
					if(stepCache[i].index === id) {
						stepElemCache[i].addClass(options.activeCls);
						selectedId = stepCache[i].id;
					} else {
						stepElemCache[i].removeClass(options.activeCls);
					}
				}
			}
		};

		this.getSelect = function(){
			return selectedId;
		};

		this.render = function(params){
			params = params || {};
			// 还原步骤
			if(params.steps && params.steps.length) {
				this.addSteps(params.steps);
				// 还原连接器
				if(params.connects) {
					this.addConnects(params.connects)
				}
			}
		};

		this.clear = function(){
			// plumb.reset();
			this.clearConnect();
			plumb.deleteEveryEndpoint();
			$("#wf_designer_canvas").empty();
			stepCache = {};
			selectedId = void(0);
		};

		this.repaint = function(){
			var steps = [],
				connects = [];
			for(var i in stepCache) {
				steps.push(stepCache[i])
			}
			for(i in connectCache) {
				connects.push(connectCache[i])
			}
			this.clear();

			this.render({
				steps: steps,
				connects: connects
			});
			selectedId = void(0);
		};
	
		this.tranfserPermission=function(permission){
			var permissionStr=parseInt(permission).toString(2);
			if(permissionStr.length == 1){
				permissionStr="00"+permissionStr;
			}else if(permissionStr.length === 2){
				permissionStr="0"+permissionStr;
			}
			var arrayObj = new Array(3);
			var permissionArray = permissionStr.split("");
			var remove=false,modify=false,add=false;
			if(permissionArray[0]=="1") remove=true;
			if(permissionArray[1]=="1") modify=true;
			if(permissionArray[2]=="1") add=true;
			arrayObj[0]=remove;
			arrayObj[1]=modify;
			arrayObj[2]=add;
			return arrayObj;
		};
		
		this.setCurrentStep = function(step){
			currentStep=step;
		};
		
		this.getCurrentStep = function(){
			return currentStep;
		};
		
		this.setStepMap = function(step){
			stepMap.put(step.resourceId,step);
		};
		
		this.getStepMap = function(id){
			return stepMap.getValue(id);
		};
		
		this.getStateClass = function(state){
			return stateClassMap.getValue(state);
		};
		
		this.getConnectType = function(state){
			return connectTypeMap.getValue(state);
		};
		
		this.getPreviewConnectType = function(state){
			return previewConnectTypeMap.getValue(state);
		};
		
		this.getPermissionConnectType = function(state){
			return permissionConnectTypeMap.getValue(state);
		};
		
		this.getTargetEnabledMap = function(state){
			return targetEnabledMap.getValue(state);
		};
		
		this.getSourceEnabledMap = function(state){
			return sourceEnabledMap.getValue(state);
		};
		
		this.getStepStateMap = function(id){
			return stepStateMap.getValue(id);
		};
		
		this.putStepStateMap = function(id,state){
			stepStateMap.put(id,state);
		};
		
		this.putConditionMap = function(id,condition){
			conditionMap.put(id,condition);
		};

		this.putSequenceFlow= function(sequenceFlow){
			if(sequenceFlow.outgoing.length ==1){
				sequenceFlowMap.put(sequenceFlow.resourceId,sequenceFlow);
			}
		};
		
		this.getSequenceFlow= function(sourceId){
			return sequenceFlowMap.getValue(sourceId);
		};
		
		this.putSourceAndTargetRelation= function(sourceId,targetId){
			sourceConnectionMap.put(sourceId,targetId);
		};
		
		this.putTargetAndSourceRelation= function(targetId,sourceId){
			targetConnectionMap.put(targetId,sourceId);
		};
		
		this.removeTargetAndSourceRelation= function(targetId){
			targetConnectionMap.remove(targetId);
			targetConnectionMap.remove(targetId+"NEW");
		};
		
		this.removeSourceAndTargetRelation= function(sourceId){
			sourceConnectionMap.remove(sourceId);
			sourceConnectionMap.remove(sourceId+"NEW");
		};
		
		this.putConnectionMap= function(key,value){
			connectionMap.put(key,value);
		};
		
		this.getConnectionMap= function(key){
			return connectionMap.getValue(key);
		};
		
		this.getConditionMap = function(id){
			return conditionMap.getValue(id);
		};
		
		this.getSourceAndTargetRelation= function(sourceId){
			return sourceConnectionMap.getValue(sourceId);
		};
		
		this.getTargetAndSourceRelation= function(targetId){
			return targetConnectionMap.getValue(targetId);
		};
		
		_setStateClass=function(){
			//预先定义好的状态class 
			stateClassMap.put('0','wf-step ');
			stateClassMap.put('1','wf-step wf-step-gray wf-step-uncomplete');
			stateClassMap.put('2','wf-step wf-step-orange wf-step-ing');
			stateClassMap.put('3','wf-step wf-step-green wf-step-complete');
			stateClassMap.put('4','wf-step wf-step-darkgray');
			stateClassMap.put('5','wf-step wf-step-blue');
		};
		
		_setConnectType=function(){
			//根据状态预先定义好的连线类型
			connectTypeMap.put('1','uncomplete');
			connectTypeMap.put('2','uncomplete');
			connectTypeMap.put('3','complete');
			connectTypeMap.put('4','uncomplete');
			
			permissionConnectTypeMap.put('1','edit');
			permissionConnectTypeMap.put('2','edit');
			permissionConnectTypeMap.put('3','complete');
			permissionConnectTypeMap.put('4','uncomplete');
			
			//根据状态预先定义好的预览连线类型
			previewConnectTypeMap.put('1','uncomplete');
			previewConnectTypeMap.put('2','uncomplete');
			previewConnectTypeMap.put('3','complete');
			previewConnectTypeMap.put('4','uncomplete');
		};
		
		_setTargetAndSourceEnabled=function(){
			//当前步骤 可以拖出 不可以拖进；已完成步骤 不可以拖出 不可以拖进；未完成步骤 可以拖进 不可以拖出
			targetEnabledMap.put('1',true);
			targetEnabledMap.put('2',false);
			targetEnabledMap.put('3',false);
			targetEnabledMap.put('4',false);
			
			sourceEnabledMap.put('1',true);
			sourceEnabledMap.put('2',true);
			sourceEnabledMap.put('3',false);
			sourceEnabledMap.put('4',false);
		};
		

		_setStateClass();
		_setConnectType();
		_setTargetAndSourceEnabled();
		_bindEvent();
	}

	return W;
})()



