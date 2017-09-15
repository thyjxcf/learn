(function() {
	var dashstyle = "0";
	if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
		dashstyle = "-1";
	}
	var jsPlumbDefaults = {
		Endpoint : "Blank",

		Connector : "Straight",
		// Connector: ["Bezier", { curviness:63 } ],//设置连线为贝塞尔曲线
		// Connector:[ "Flowchart", { stub:[40, 60], gap:10, cornerRadius:5,
		// alwaysRespectStubs:true } ],
		ConnectionOverlays : [["Arrow", {
							location : 1,
							id : "arrow",
							length : 14,
							foldback : 0.6
						}], ["Label", {
							location : 0.1,
							id : "label",
							cssClass : "aLabel"
						}]],

		ConnectionsDetachable : false,
		ReattachConnections : false,

		// LogEnabled: true, // 调试模式
		// 锚点自动调整位置
		Anchor : "Continuous",
		// Anchor: [[0, 0.5, 1, 0], [1, 0.5, 1, 0]],
		// 连接器样式
		PaintStyle : {
			strokeStyle : "#EE8C0C",
			lineWidth : 3,
			dashstyle : dashstyle,
			outlineWidth : 4,
			outlineColor : "transparent"
		}
	};

	// 判断连接器是否指向了自己
	var isConnectToSelf = function(param) {
		return param.sourceId === param.targetId
	}, isFromStartToEnd = function(param) {
		return param.sourceId === "StartNoneEvent_-1"
				&& param.targetId === "EndNoneEvent_0"
	},
	// 判断连接步骤是否重复
	stepIsRepeat = function(param) {
		// 获取同一scope下的链接器，判断当前链接是否有重复
		var cnts = jsPlumb.getConnections(param.scope);
		if (cnts.length > 0) {
			for (var i = 0, len = cnts.length; i < len; i++) {
				// 如果有链接器 sourceId 及 targetId 都相等，那大概也许可能就相等了。
				if (param.sourceId === cnts[i].sourceId
						&& param.targetId === cnts[i].targetId) {
					return true;
				}
			}
		}
		return false;
	};

	// 连接前的事件，
	var beforeDropHandler = function(param) {
		if (isConnectToSelf(param)) {
			return false;
		}
		if (stepIsRepeat(param)) {
			Ui.tip(U.lang('WF.REPEAT_STEP'), "warning");
			return false;
		}
		if (isFromStartToEnd(param)) {
			Ui.tip(U.lang('WF.NOT_FROM_START_TO_END'), 'warning');
			return false;
		}
		return true;
	};

	// 连接时, 判断步骤是否重复
	jsPlumb.bind("beforeDrop", beforeDropHandler);

	// 注册连线类型
	jsPlumb.registerConnectionTypes({
				edit : {
					paintStyle : {
						strokeStyle : "#EE8C0C",
						lineWidth : 3,
						dashstyle : dashstyle,
						outlineWidth : 4,
						outlineColor : "transparent"
					},
					overlays : [["Arrow", {
								location : 1,
								id : "arrow",
								length : 14,
								foldback : .6
							}]]
				},
				complete : {
					paintStyle : {
						strokeStyle : "#84b955",
						lineWidth : 3,
						dashstyle : dashstyle,
						outlineWidth : 4,
						outlineColor : "transparent"
					},
					overlays : [["Arrow", {
								location : 1,
								id : "arrow",
								length : 14,
								foldback : .6
							}]]
				},
				uncomplete : {
					paintStyle : {
						strokeStyle : "#B2C0D1",
						lineWidth : 3,
						outlineWidth : 4,
						outlineColor : "transparent",
						dashstyle : "4 1"
					},
					overlays : [["Arrow", {
								location : 1,
								id : "arrow",
								length : 14,
								foldback : .6
							}]]
				}
			});
	jsPlumb.importDefaults(jsPlumbDefaults);

})();

var processDesign = {
	instance : new wfDesigner($("#wf_designer_canvas"), jsPlumb),
	// 解析数据
	parseData : function(data) {
		var currentStepId, model = data.model, // 模型定义数据
		modelExtend = data.modelExtend;// 模型业务数据
		var that = this;

		var taskUserMap = new Map();// 操作人map
		var ret = {
			steps : [],
			connects : []
		};
		var pushConnect = function(sourceId, targetIds) {
			var d;
			if (typeof sourceId === "undefined" || targetIds.length === 0) {
				return false;
			}
			var sourceStep = that.instance.getStepMap(sourceId);
			// 根据target判断连线状态 edit/uncomplete/complete
			var _connectType = "edit";// 默认是可编辑的
			if (Zdsoft.app.g('operation') === "process"
					|| Zdsoft.app.g('operation') === "modify") {// 非编辑才控制
				var permission = 0;
				var currentStep = that.instance.getCurrentStep();
				if (currentStep)
					permission = currentStep.properties.permission;
				for (var i = 0; i < targetIds.length; i++) {
					// 判断是否是连线 如果是，需要找到连线的outgoing
					var _resourceId = targetIds[i].resourceId;
					var sequenceFlow = that.instance
							.getSequenceFlow(_resourceId);
					var outgoing_resourceId = (sequenceFlow.outgoing)[0].resourceId;
					if (!outgoing_resourceId)
						outgoing_resourceId = _resourceId;
					if (permission === -1) {
						if (that.instance.getStepStateMap(sourceId)) {
							_connectType = that.instance
									.getPermissionConnectType(that.instance
											.getStepStateMap(sourceId));
						}
					} else {
						var permissionArray = that.instance
								.tranfserPermission(permission);
						if (permissionArray[2]) {// insert
							if (that.instance.getStepStateMap(sourceId)) {
								// 对于当前节点 特殊处理
								if (currentStep.resourceId !== sourceId) {
									_connectType = that.instance
											.getConnectType(that.instance
													.getStepStateMap(sourceId));
								}
							}
						} else {
							if (that.instance.getStepStateMap(sourceId)) {
								_connectType = that.instance
										.getConnectType(that.instance
												.getStepStateMap(sourceId));
							}
						}
					}
					ret.connects.push(sourceId + "," + outgoing_resourceId
							+ "," + _connectType);
					that.instance.putConnectionMap(sourceId
									+ outgoing_resourceId, _connectType);
					// 保存条件信息
					if (sourceStep.stencil.id === "ExclusiveGateway") {
						that.instance.putConditionMap(sourceId
										+ outgoing_resourceId,
								sequenceFlow.properties);
					}
					var _targetId = "";
					if (that.instance.getSourceAndTargetRelation(sourceId)) {
						_targetId = that.instance
								.getSourceAndTargetRelation(sourceId);
						_targetId += "," + outgoing_resourceId
					} else {
						_targetId = outgoing_resourceId;
					}
					that.instance.putSourceAndTargetRelation(sourceId,
							_targetId);
					var _sourceId = "";
					if (that.instance
							.getTargetAndSourceRelation(outgoing_resourceId)) {
						_sourceId = that.instance
								.getTargetAndSourceRelation(outgoing_resourceId);
						_sourceId += "," + sourceId
					} else {
						_sourceId = sourceId;
					}
					that.instance.putTargetAndSourceRelation(
							outgoing_resourceId, _sourceId);
				}
			} else {
				for (var i = 0; i < targetIds.length; i++) {
					// 判断是否是连线 如果是，需要找到连线的outgoing
					var _resourceId = targetIds[i].resourceId;
					var sequenceFlow = that.instance
							.getSequenceFlow(_resourceId);
					var outgoing_resourceId = (sequenceFlow.outgoing)[0].resourceId;
					if (!outgoing_resourceId)
						outgoing_resourceId = _resourceId;
					ret.connects.push(sourceId + "," + outgoing_resourceId
							+ "," + _connectType);
					// 保存条件信息
					if (sourceStep.stencil.id === "ExclusiveGateway") {
						that.instance.putConditionMap(sourceId
										+ outgoing_resourceId,
								sequenceFlow.properties);
					}
					var _targetId = "";
					if (that.instance.getSourceAndTargetRelation(sourceId)) {
						_targetId = that.instance
								.getSourceAndTargetRelation(sourceId);
						_targetId += "," + outgoing_resourceId
					} else {
						_targetId = outgoing_resourceId;
					}
					that.instance.putSourceAndTargetRelation(sourceId,
							_targetId);
					var _sourceId = "";
					if (that.instance
							.getTargetAndSourceRelation(outgoing_resourceId)) {
						_sourceId = that.instance
								.getTargetAndSourceRelation(outgoing_resourceId);
						_sourceId += "," + sourceId
					} else {
						_sourceId = sourceId;
					}
					that.instance.putTargetAndSourceRelation(
							outgoing_resourceId, _sourceId);
				}
			}
		};
		// 业务数据组装
		if (modelExtend && modelExtend.childShapes) {
			$.each(modelExtend.childShapes, function(i, item) {
				if (Zdsoft.app.g('currentStepId') === "") {
					if (item.status && item.status === 2) {
						currentStepId = item.id;
					}
					that.instance.putStepStateMap(item.id, item.status);
				} else {
					if (item.status && item.status === 2) {
						if (item.id === Zdsoft.app.g('currentStepId')) {
							currentStepId = item.id;
							that.instance.putStepStateMap(item.id, item.status);
						} else {
							// 并行 设置其它的为4 特殊
							that.instance.putStepStateMap(item.id, 4);
						}
					} else {
						that.instance.putStepStateMap(item.id, item.status);
					}
				}
				if (item.usertaskprincipalusernames)
					taskUserMap.put(item.id, item.usertaskprincipalusernames);
			});
		}
		
		Zdsoft.app.s("currentStepId", currentStepId);
		if (model && model.childShapes) {
			// StartNoneEvent(开始节点),UserTask(步骤节点),ParallelGateway(网关节点),SequenceFlow(连线节点),EndNoneEvent(结束节点)

			// 先将连线的信息放入map
			$.each(model.childShapes, function(i, item) {
						// 将当前步骤存入，供后面权限判断使用
						if (currentStepId && currentStepId === item.resourceId) {
							/*
							 * if(Zdsoft.app.g('operation') ==="start"){
							 * item.properties.permission=0; }
							 */
							// 修改赋予当前步骤自由流的权限
							if (Zdsoft.app.g('operation') === "modify") {
								item.properties.permission = -1;
							}
							that.instance.setCurrentStep(item);
						}
						if (item.stencil.id == "SequenceFlow") {
							that.instance.putSequenceFlow(item);
						}
					});

			$.each(model.childShapes, function(i, item) {
				var _id = item.resourceId;
				// 将步骤存入Map
				that.instance.setStepMap(item);
				if (item.stencil.id == "SequenceFlow") {
					return true;
				}

				var _index = parseInt(_id.substring(_id.lastIndexOf('_') + 1),
						10);

				var taskUser = "", taskUserId = "";
				if (item.stencil.id === "UserTask") {
					taskUser = taskUserMap.getValue(_id) ? taskUserMap
							.getValue(_id) : "";
					if (item.properties.customidentitylinks)
						taskUserId = item.properties.customidentitylinks.items[0].identitylinkexpr;
				}

				// 只接收需要的属性
				d = {
					id : _id,
					index : _index,
					type : item.stencil.id,
					taskUser : taskUser,
					taskUserId : taskUserId,
					top : item.bounds.upperLeft.y,
					left : item.bounds.upperLeft.x,
					name : item.properties.name,
					permission : item.properties.permission,
					to : item.outgoing,
					step : item.properties.step
				};
				// 特殊步骤处理, (开始结束)
				if (d.type === "StartNoneEvent") {
					d.cls = "wf-step wf-step-start";
				} else if (d.type === "EndNoneEvent") {
					d.cls = "wf-step wf-step-end";
				} else if (d.type === "ExclusiveGateway") {
					d.cls = "wf-node wf-condition";
				} else if (d.type === "ParallelGateway") {
					d.cls = "wf-node wf-gate";
				} else if (d.type === "UserTask") {
					// 根据状态获取样式
					if (Zdsoft.app.g('operation') === "process"
							|| Zdsoft.app.g('operation') === "modify") {
						if (that.instance.getStepStateMap(d.id)) {
							d.cls = that.instance.getStateClass(that.instance
									.getStepStateMap(d.id));
						}
					} else {
						d.cls = that.instance.getStateClass('0');
					}
				}
				ret.steps.push(d);
				if (d.to) {
					pushConnect(d.id, d.to);
				}

			});
		}
		return ret;
	},
	set : function(data) {
		var that = this, _data;
		addStartStep = function() {
			var $startStep = that.instance.addStep({
						id : 'StartNoneEvent_-1',
						name : U.lang('WF.START_NAME'),// L.START,
						taskUser : '',
						cls : "wf-step wf-step-start",
						index : -1,
						type : 'StartNoneEvent'
					});
			var $container = $("#wf_designer_canvas");
			var position = {
				left : ($container.outerWidth() - $startStep.outerWidth()) / 2,
				top : 40
			};
			$startStep.css(position);
			that.instance.updateData('-1', position);
		}, addEndStep = function() {
			var $endStep = that.instance.addStep({
						id : 'EndNoneEvent_0',
						name : U.lang('WF.END_NAME'),// L.END,
						taskUser : '',
						cls : "wf-step wf-step-end",
						index : 0,
						type : 'EndNoneEvent'
					});
			var $container = $("#wf_designer_canvas");
			var position = {
				left : ($container.outerWidth() - $endStep.outerWidth()) / 2,
				top : 140
			};

			$endStep.css(position);
			that.instance.updateData('0', position);
		};
		sourceAndTargetSet = function(steps) {
			if (Zdsoft.app.g('operation') === "process"
					|| Zdsoft.app.g('operation') === "modify") {// 过程性修改才按照状态去控制
				var permission = 0;
				var currentStep = that.instance.getCurrentStep();
				if (currentStep)
					permission = currentStep.properties.permission;

				if (permission === -1) {
					$.each(steps, function(index, step) {
						// 当前步骤 可以拖出 不可以拖进；已完成步骤 不可以拖出 不可以拖进；未完成步骤 可以拖进 不可以拖出
						// 还要根据当前步骤的权限来判断
						if (that.instance.getStepStateMap(step.id)) {
							var _state = that.instance.getStepStateMap(step.id);
							if (_state === 1) {
								if (step.type === 'ParallelGateway') {
									$("#" + step.id).addClass("wf-gate-next");
								}
							}
							jsPlumb.setTargetEnabled(step.id, that.instance
											.getTargetEnabledMap(_state));
							jsPlumb.setSourceEnabled(step.id, that.instance
											.getSourceEnabledMap(_state));
						}
					});
				} else {
					$.each(steps, function(index, step) {
								// 当前步骤 可以拖出 不可以拖进；已完成步骤 不可以拖出 不可以拖进；未完成步骤 可以拖进
								// 不可以拖出
								// 还要根据当前步骤的权限来判断
								jsPlumb.setTargetEnabled(step.id, false);
								jsPlumb.setSourceEnabled(step.id, false);
							});
					var permissionArray = that.instance
							.tranfserPermission(permission);
					var _nextStepIds = that.instance
							.getSourceAndTargetRelation(currentStep.resourceId);
					if (_nextStepIds) {
						var _nextStepIdArray = _nextStepIds.split(",");
						for (var i = 0; i < _nextStepIdArray.length; i++) {
							var step = that.instance
									.getStepMap(_nextStepIdArray[i]);
							// 如果下一步是网关 继续找步骤 增加网关和步骤特殊样式
							if (step.stencil.id === "ParallelGateway") {
								$("#" + step.resourceId)
										.addClass("wf-gate-next");
								var __nextStepIds = that.instance
										.getSourceAndTargetRelation(step.resourceId);
								var __nextStepIdArray = __nextStepIds
										.split(",");
								for (var j = 0; j < __nextStepIdArray.length; j++) {
									var _step = that.instance
											.getStepMap(__nextStepIdArray[j]);
									if (_step.stencil.id === "EndNoneEvent_0") {
										return true;
									}
									var _permission = _step.properties.permission;
									var _permissionArray = that.instance
											.tranfserPermission(_permission);
									if (_permissionArray[0])// remove
										$("#" + _step.resourceId)
												.addClass("wf-step-next-remove");
									if (_permissionArray[1])// modify
										$("#" + _step.resourceId)
												.addClass("wf-step-next-modify");
									if (_permissionArray[0]
											&& _permissionArray[1])
										$("#" + _step.resourceId)
												.addClass("wf-step-next-remove-modify");
								}
							} else if (step.stencil.id !== "EndNoneEvent_0") {
								var _permission = step.properties.permission;
								var _permissionArray = that.instance
										.tranfserPermission(_permission);
								if (_permissionArray[0])// remove
									$("#" + step.resourceId)
											.addClass("wf-step-next-remove");
								if (_permissionArray[1])// modify
									$("#" + step.resourceId)
											.addClass("wf-step-next-modify");
								if (_permissionArray[0] && _permissionArray[1])
									$("#" + step.resourceId)
											.addClass("wf-step-next-remove-modify");
							}
							if (permissionArray[2]) {// insert
								jsPlumb.setTargetEnabled(_nextStepIdArray[i],
										true);
							}
						}
					}
					if (permissionArray[2]) {// insert
						jsPlumb.setSourceEnabled(currentStep.resourceId, true);
					}
				}
			}
		};
		var wfData = eval("(" + data + ")"); // 包数据解析为json 格式
		$("#workflow_name").val(wfData.name);
		parent.setWfTitle("流程设计器-" + wfData.name);
		$("#key").val(wfData.key);
		if (wfData.model.childShapes) {
			_data = this.parseData(wfData);
			that.instance.addSteps(_data.steps);
			// 在步骤都加载完成后才连线
			that.instance.addConnects(_data.connects);
			// 控制是否可以作为source和target
			sourceAndTargetSet(_data.steps);
		} else {
			// 增加开始和结束步骤
			addEndStep();
			addStartStep();

		}
		// 开始和结束节点的一些限制
		jsPlumb.setTargetEnabled("StartNoneEvent_-1", false);
		jsPlumb.setSourceEnabled("EndNoneEvent_0", false);
	},

	add : function(data, options) {
		return this.instance.addSteps(data, options, true);
	},

	getData : function() {
		var steps = [], sequenceFlowMap = new Map(), connectionTargetMap = new Map(), connectionSourceMap = new Map(), stepMap = new Map(), stepData = this.instance
				.getSteps(), connectData = this.instance.getConnects();

		for (var i in stepData) {
			if (stepData.hasOwnProperty(i)) {
				stepMap.put(stepData[i].id, stepData[i]);
			}
		}
		// 解析链接数据 一条线就相当于 source节点－链接－target节点
		for (var i in connectData) {
			if (connectData.hasOwnProperty(i)) {
				var sequenceFlowId = 'SequenceFlow_' + i;
				var _connects = connectData[i].split(",");
				// 将该虚拟节点作为source节点的target 同时将target节点作为虚拟节点的target
				var _targetIds = sequenceFlowMap.getValue(_connects[0]);
				if(_connects[0] ==="StartNoneEvent_-1" && _connects[1] ==="EndNoneEvent_0"){
					Ui.tip(L.WF.NOT_FROM_START_TO_END, "warning");
					return false;
				}
				connectionSourceMap.put(_connects[0], _connects[1]);
				connectionTargetMap.put(_connects[1], _connects[0]);
				if (!(_targetIds)) {
					_targetIds = [];
				}
				_targetIds.push({
							"resourceId" : sequenceFlowId
						});
				sequenceFlowMap.put(_connects[0], _targetIds);

				var sourceStep = stepMap.getValue(_connects[0]);
				var targetStep = stepMap.getValue(_connects[1]);
				// 构造出一个SequenceFlow
				var sequenceProperties;
				// 如果sourceStep为条件结点
				if (sourceStep.type === "ExclusiveGateway") {
					var sequenceProperties = this.instance
							.getConditionMap(_connects[0] + _connects[1])
					if (sequenceProperties) {
						sequenceProperties.overrideid = sequenceFlowId;
					} else {
						Ui.tip(L.WF.CONDITION_NEED, "warning");
						return false;
					}
				} else {
					sequenceProperties = {// 自定义属性
						"overrideid" : sequenceFlowId,
						"name" : '',
						"documentation" : '',
						"conditionsequenceflow" : '',
						"defaultflow" : 'None',
						"conditionalflow" : 'None',
						"showdiamondmarker" : false
					};
				}
				var sequenceFlowData = {
					"resourceId" : sequenceFlowId,
					"stencil" : {
						"id" : 'SequenceFlow'
					},
					"childShapes" : [],
					"outgoing" : [{
								"resourceId" : _connects[1]
							}],
					"bounds" : {
						"lowerRight" : {
							"x" : $("#" + targetStep.id).position().left,
							"y" : $("#" + targetStep.id).position().top
						},
						"upperLeft" : {
							"x" : $("#" + sourceStep.id).position().left,
							"y" : $("#" + sourceStep.id).position().top
						}
					},
					"dockers" : [{
								"x" : $("#" + sourceStep.id).width() / 2,
								"y" : $("#" + sourceStep.id).height() / 2
							}, {
								"x" : $("#" + targetStep.id).width() / 2,
								"y" : $("#" + targetStep.id).height() / 2
							}],
					"target" : {
						"resourceId" : _connects[1]
					}
				};
				steps.push(sequenceFlowData);
			}
			sequenceFlowData.properties = sequenceProperties;
		}
		var stepUsers = []
		// 解析步骤数据
		for (var i in stepData) {
			if (stepData.hasOwnProperty(i)) {
				if (stepData[i].type !== 'EndNoneEvent') {
					if (!connectionSourceMap.containsKey(stepData[i].id)) {
						return;
					}
				}
				if (stepData[i].type !== 'StartNoneEvent') {
					if (!connectionTargetMap.containsKey(stepData[i].id)) {
						return;
					}
				}
				// StartNoneEvent(开始节点),UserTask(步骤节点),ParallelGateway(网关节点),SequenceFlow(连线节点),EndNoneEvent(结束节点)
				var _properties = {};
				if (stepData[i].type == 'StartNoneEvent') {
					_properties = {
						"overrideid" : stepData[i].id,
						"name" : stepData[i].name
					};
				} else if (stepData[i].type == 'UserTask') {
					var _status =this.instance.getStepStateMap(stepData[i].id);
					if(!_status){
						_status="1";
					}
					var users={
		                "id": stepData[i].id,
		                "usertaskprincipalusernames": stepData[i].taskUser,
		                "status":_status
		            };
		            stepUsers.push(users);
					_properties = {
						"overrideid" : stepData[i].id,
						"name" : stepData[i].name,
						"step" : stepData[i].step,// 自定义属性 步骤
						"permission" : stepData[i].permission
						// 自定义属性 权限控制 -1 不控制；1、可插入步骤；2、可修改负责人；4、可删除步骤
					};
					// 如果没有指定人 就不加载该属性
					if (stepData[i].taskUserId != "") {
						var customidentitylinks = {// 自定义属性 人员
							"items" : [{
								"identity_type" : 'user',
								"identity_link_type" : 'principal',
								"identitylinkexpr" : stepData[i].taskUserId
									// stepData[i].taskUserId
								}]
						};
						_properties.customidentitylinks = customidentitylinks;
					}
				} else if (stepData[i].type == 'ParallelGateway'
						|| stepData[i].type == 'ExclusiveGateway') {
					_properties = {
						"overrideid" : stepData[i].id,
						"name" : stepData[i].name
					};
				} else if (stepData[i].type == 'EndNoneEvent') {
					_properties = {
						"overrideid" : stepData[i].id,
						"name" : stepData[i].name
					};
				}

				var _outgoing = sequenceFlowMap.getValue(stepData[i].id);
				if (!_outgoing) {
					_outgoing = [];
				}
				var _stepData = {
					"resourceId" : stepData[i].id,
					"properties" : _properties,
					"stencil" : {
						"id" : stepData[i].type
					},
					"childShapes" : [],
					"outgoing" : _outgoing,
					"bounds" : {
						"lowerRight" : {
							"x" : $("#" + stepData[i].id).position().left
									+ ($("#" + stepData[i].id).width()),
							"y" : $("#" + stepData[i].id).position().top
									+ ($("#" + stepData[i].id).height())
						},
						"upperLeft" : {
							"x" : $("#" + stepData[i].id).position().left,
							"y" : $("#" + stepData[i].id).position().top
						}
					},
					"dockers" : []
				};
				steps.push(_stepData);
				
			}
		}
		
		var modelData = {
			"resourceId" : 'canvas',
			"properties" : {
				"name" : $("#workflow_name").val(),
				"process_id" : $("#key").val()
				// 需要保存相对应的值
			},
			"stencil" : {
				"id" : 'BPMNDiagram'
			},
			"childShapes" : steps,
			"bounds" : {
				"lowerRight" : {
					"x" : 1000,
					"y" : 500
				},
				"upperLeft" : {
					"x" : 0,
					"y" : 0
				}
			},
			"stencilset" : {
				"namespace" : 'http://b3mn.org/stencilset/bpmn2.0#',
				"url" : '../editor/stencilsets/bpmn2.0/bpmn2.0.json'
			}
		};
		var modelExtendData = {
			 "childShapes":stepUsers
		}
		var wfdata ={
			"name" : $("#workflow_name").val(),
			"key": $("#key").val(),
			"model" : modelData,
			"modelExtend":modelExtendData
		};
		return wfdata;
	}
};

/**
 * 设计器容器内普通步骤选择器
 * 
 * @param {String}
 *            normalStepSelector
 */
var normalStepSelector = ".wf-step:not(.wf-step-start,.wf-step-end,.wf-tips)";

var normalGateSelector = ".wf-gate";

var normalConditionSelector = ".wf-condition";

var nextGateSelector = ".wf-gate-next";

var nextRemoveStepSelector = ".wf-step-next-remove";
var nextModifyStepSelector = ".wf-step-next-modify";
var nextRMStepSelector = ".wf-step-next-remove-modify";
var uncompleteStepSelector = ".wf-step-uncomplete";
var saveFlag = false;
var addFlag = false;
var addDialog,editDialog;

/**
 * 控制器
 * 
 * @param object
 *            wfControll
 */
var wfControll = {
	/**
	 * 编辑步骤
	 * 
	 * @param {integer}
	 *            processId 步骤ID
	 * @param {string}
	 *            op 编辑类型
	 * @returns {undefined}
	 */
	editStep : function(id, op) {
		var isLoad = false, ins = processDesign.instance;
		if (id) {
			var index = id.substring(id.lastIndexOf('_') + 1);
			var selectStep = ins.getData(index);
			Ui.closeDialog("d_step_edit");
			editDialog = Ui.dialog({
				id : "d_step_edit",
				padding : 0,
				title : selectStep.name,
				content : "<div id='addStepDiv' style='width:980px;left:300px;'></div>",
				top : 10,
				init : function() {
					var param = {
						id : Zdsoft.app.g('id'),
						subsystemId : Zdsoft.app.g('subsystemId'),
						businessType : Zdsoft.app.g('businessType'),
						stepOperType : "edit",
						permission : selectStep.permission,
						taskUserId : selectStep.taskUserId,
						step : selectStep.step,
						stepName : selectStep.name,
						easyLevel : Zdsoft.app.g('easyLevel')
					};
					var url = Zdsoft.app.url("jbmp/editor/wf-stepInfo.action",
							param);
					load("#addStepDiv", url);
				},
				ok : function() {
					if (Zdsoft.app.g('businessType').indexOf("17") === 0) {
						saveWfStep(id,index);
						return false;
					} else {
						wfControll.stepEditSave(id, index);
					}
				}
			});
		}
	},
	// 增加步骤， 打开新增对话框
	addStep : function(parallel, left, top, height, width) {
		 addDialog = Ui.dialog({
			id : "d_step_add",
			title : U.lang('WF.ADD_STEP'),
			content : "<div id='addStepDiv' style='width:980px;left:300px;'></div>",
			padding : 0,
			top : 10,
			init : function() {
				var param = {
					id : Zdsoft.app.g('id'),
					subsystemId : Zdsoft.app.g('subsystemId'),
					businessType : Zdsoft.app.g('businessType'),
					stepOperType : "add",
					permission : 0,
					parallel : parallel,
					currentStepId : Zdsoft.app.g('currentStepId'),
					easyLevel : Zdsoft.app.g('easyLevel')
				};
				var url = Zdsoft.app.url("jbmp/editor/wf-stepInfo.action",
						param);
				load("#addStepDiv", url);
			},
			ok : function() {
				if (Zdsoft.app.g('businessType').indexOf("17") === 0) {
					saveWfStep(parallel, left, top, height, width);
					return false;
				} else {
					wfControll.stepAddSave();
				}
			}
		});
	},
	// 增加网关
	addGate : function() {
		var index = processDesign.instance.getMaxIndex() + 1;
		var gate = {
			id : 'ParallelGateway_' + index,
			name : 'gate',
			taskUser : 'gate',
			cls : 'wf-node wf-gate',
			index : index,
			type : 'ParallelGateway'
		};
		var options = {
			maxConnection : -1
		};// 不限连接数
		var $gate = processDesign.add(gate, options);
		// $gate.contextMenu("gate_context_menu", gateContextMenuSettings);
		return $gate;
	},
	// 增加条件节点
	addCondition : function() {
		var index = processDesign.instance.getMaxIndex() + 1;
		var condition = {
			id : 'ExclusiveGateway_' + index,
			name : 'condition',
			taskUser : 'condition',
			cls : 'wf-node wf-condition',
			index : index,
			type : 'ExclusiveGateway'
		};
		var options = {
			maxConnection : -1
		};// 不限连接数
		var $condition = processDesign.add(condition, options);
		$condition.contextMenu("condition_context_menu",
				conditionContextMenuSettings);
	},
	/**
	 * 编辑条件
	 * 
	 * @param {string}
	 *            op 编辑类型
	 * @returns {undefined}
	 */
	editCondition : function(connection, sourceId, targetId, op) {
		var isLoad = false, ins = processDesign.instance, dialog;
		var id = sourceId + targetId;
		var sequenceFlow = ins.getConditionMap(id);
		if (sourceId && targetId) {
			Ui.closeDialog("d_condition_edit");
			dialog = Ui.dialog({
				id : "d_condition_edit",
				padding : 0,
				title : "条件设置",
				content : "<div id='conditionDiv' style='width:650px;left:300px;'></div>",
				top : 100,
				init : function() {
					var param;
					if (sequenceFlow)
						param = {
							id : id,
							stepOperType : "add",
							conditionName : sequenceFlow.name,
							conditions : sequenceFlow.conditionsequenceflow.expression.staticValue
						};
					else
						param = {
							id : id,
							conditionName : "",
							conditions : ""
						};
					var url = Zdsoft.app.url("jbmp/editor/wf-condition.action",
							param);
					load("#conditionDiv", url);
				},
				ok : function() {
					if (!condition_checkDiv()) {
						return false;
					}
					var conditionName = $("#conditionName").val();
					var conditions = formCondition.getConditions();
					if (!sequenceFlow) {
						sequenceFlow = {// 自定义属性
							'name' : conditionName,// 名称
							'documentation' : '',
							'conditionsequenceflow' : {
								'expression' : {
									'type' : "static",
									'staticValue' : conditions
									// 表达式
								}
							},
							'defaultflow' : 'None',
							'conditionalflow' : 'None',
							'showdiamondmarker' : false
						};
					} else {
						sequenceFlow.name = conditionName;
						sequenceFlow.conditionsequenceflow.expression.staticValue = conditions;
					}
					ins.removeConnect(connection);
					ins.putConditionMap(id, sequenceFlow);
					ins.addConnectByStt(sourceId, targetId, "edit");
				}
			});
		}
	},
	// 移除步骤
	removeStep : function(deleteId) {
		var name;
		$("#wf_designer_canvas").animate({
					scrollTop : 0,
					scrollLeft : 0
				}, 0);
		var index = deleteId.substring(deleteId.lastIndexOf('_') + 1);
		if (index && processDesign.instance.hasIndex(index)) {
			if (processDesign.instance.getData(index).type == 'UserTask') {
				name = processDesign.instance.getData(index).name;
				Ui.confirm(U.lang('WF.DELETE_STEP_CONFIRM', {
									name : name
								}), function() {
							// 删除步骤的同时 还要重新连线和位置调整等
							var stepDataList = processDesign.instance
									.getSteps();
							// 根据内容来重新计算位置 居中 如果是并行步骤的话 TODO
							var $container = $("#wf_designer_canvas");
							var beforeParallelGatewayHeight = 0;
							var nextParallelGatewayHeight = 0;
							for (var id in stepDataList) {
								if (stepDataList[id].type == "ParallelGateway") {
									if (beforeParallelGatewayHeight == 0) {
										beforeParallelGatewayHeight = stepDataList[id].top;
									}
									if (stepDataList[id].top > beforeParallelGatewayHeight) {
										nextParallelGatewayHeight = stepDataList[id].top;
									}
								}
							}
							var inParallelGateway = false;
							if (stepDataList[index].top > beforeParallelGatewayHeight
									&& stepDataList[index].top < nextParallelGatewayHeight)
								inParallelGateway = true;
							// 删除步骤的下一步
							var next_step_id = processDesign.instance
									.getSourceAndTargetRelation(deleteId);
							var next_step_index = next_step_id
									.substring(next_step_id.lastIndexOf('_')
											+ 1);
							// 删除步骤的上一步
							var before_step_id = processDesign.instance
									.getTargetAndSourceRelation(deleteId);
							var before_step_index = before_step_id
									.substring(before_step_id.lastIndexOf('_')
											+ 1);
							var totalParallelWidth = 0;
							var parallelSteps = [];
							if (stepDataList[before_step_index].type == "ParallelGateway"
									&& inParallelGateway) {
								// 如果上一步是网关的话 判断与删除元素并行的元素个数 如果删除后只剩下一个的话
								// 则需要删除掉网关
								var parallelNum = 0;
								var parallelId;
								for (var id in stepDataList) {
									var selectedStep = $("#"
											+ stepDataList[id].id);
									if (stepDataList[id].top == stepDataList[index].top
											&& stepDataList[id].id != deleteId) {
										parallelNum++;
										parallelId = stepDataList[id].id;
										totalParallelWidth += selectedStep
												.outerWidth();
										parallelSteps.push(stepDataList[id]);
									}
								}
								if (parallelNum == 1) {
									// 删除本节点后只剩下一个节点 需要删除网关节点
									var _beforeAboveParallelStepId = processDesign.instance
											.getTargetAndSourceRelation(stepDataList[before_step_index].id);

									var _afterBelowParallelStepId = processDesign.instance
											.getSourceAndTargetRelation(stepDataList[next_step_index].id);

									// 调整高度
									for (var id in stepDataList) {
										var _topInterval = 0;
										if (stepDataList[id].top > stepDataList[next_step_index].top) {
											_topInterval = 200;
										} else if (stepDataList[id].top > stepDataList[before_step_index].top) {
											_topInterval = 100;
										} else {
											continue;
										}
										var selectedStep = $("#"
												+ stepDataList[id].id);
										var position_selected_step = {
											left : ($container.outerWidth() - selectedStep
													.outerWidth())
													/ 2,
											top : stepDataList[id].top
													- _topInterval
										};
										selectedStep
												.css(position_selected_step);
										processDesign.instance.updateData(
												stepDataList[id].index,
												position_selected_step);
									}
									// 删除网关节点
									processDesign.instance.removeStep(
											before_step_index, true);
									processDesign.instance.removeStep(
											next_step_index, true);
									processDesign.instance.removeStep(index,
											true);

									processDesign.instance
											.putSourceAndTargetRelation(
													_beforeAboveParallelStepId,
													parallelId);
									processDesign.instance
											.putSourceAndTargetRelation(
													parallelId,
													_afterBelowParallelStepId);

									processDesign.instance
											.putTargetAndSourceRelation(
													parallelId,
													_beforeAboveParallelStepId);
									processDesign.instance
											.putTargetAndSourceRelation(
													_afterBelowParallelStepId,
													parallelId);

								} else {
									var parallelInterval = ($container
											.outerWidth() - totalParallelWidth)
											/ (parallelNum + 1);

									// 并行的节点 重新计算位置
									var _parallelStepLeft = 0;
									var _parallelStepId;
									for (var k = 0; k < parallelSteps.length; k++) {
										_parallelStepId = parallelSteps[k].id;
										_parallelStepLeft += parallelInterval;
										var parallelStep = $("#"
												+ parallelSteps[k].id);
										var position_parallel_step = {
											left : _parallelStepLeft,
											top : parallelSteps[k].top
										};
										parallelStep
												.css(position_parallel_step);
										processDesign.instance.updateData(
												parallelSteps[k].index,
												position_parallel_step);
										_parallelStepLeft += parallelStep
												.outerWidth();
									}

									// 无需删除网关节点
									// 调整前面网关节点的target 删除掉本节点
									var _beforeParallelTargetIds = processDesign.instance
											.getSourceAndTargetRelation(stepDataList[before_step_index].id);
									var _beforeParallelTargetArray = _beforeParallelTargetIds
											.split(",");
									var _beforeParallelTargetId = "";
									for (var i = 0; i < _beforeParallelTargetArray.length; i++) {
										if (deleteId != _beforeParallelTargetArray[i]) {
											_beforeParallelTargetId += _beforeParallelTargetArray[i]
													+ ",";
										}
									}

									// 调整后面网关节点的sourceId 删除掉本节点
									var _afterParallelTargetIds = processDesign.instance
											.getTargetAndSourceRelation(stepDataList[next_step_index].id);
									var _afterParallelTargetArray = _afterParallelTargetIds
											.split(",");
									var _afterParallelTargetId = "";
									for (var i = 0; i < _afterParallelTargetArray.length; i++) {
										if (deleteId != _afterParallelTargetArray[i]) {
											_afterParallelTargetId += _afterParallelTargetArray[i]
													+ ",";
										}
									}
									processDesign.instance.removeStep(index,
											true);
									processDesign.instance
											.putSourceAndTargetRelation(
													stepDataList[before_step_index].id,
													_beforeParallelTargetId);
									processDesign.instance
											.putTargetAndSourceRelation(
													stepDataList[next_step_index].id,
													_afterParallelTargetIds);
								}
							} else {
								// 调整高度
								for (var id in stepDataList) {
									if (stepDataList[id].top > stepDataList[index].top) {
										var selectedStep = $("#"
												+ stepDataList[id].id);
										var position_selected_step = {
											left : stepDataList[id].left,
											top : stepDataList[id].top - 100
										};
										selectedStep
												.css(position_selected_step);
										processDesign.instance.updateData(
												stepDataList[id].index,
												position_selected_step);
									}
								}
								processDesign.instance.removeStep(index, true);
								processDesign.instance
										.putSourceAndTargetRelation(
												stepDataList[before_step_index].id,
												stepDataList[next_step_index].id);
								processDesign.instance
										.putTargetAndSourceRelation(
												stepDataList[next_step_index].id,
												stepDataList[before_step_index].id);
							}

							// 删除所有连线
							processDesign.instance.detachEveryConnection();
							// 全部重新连线
							for (var id in stepDataList) {
								var targetIds = processDesign.instance
										.getSourceAndTargetRelation(stepDataList[id].id)
								if (targetIds) {
									var targetIdArray = targetIds.split(",");
									for (var i = 0; i < targetIdArray.length; i++) {
										if (targetIdArray[i] == "")
											continue;
										processDesign.instance.addConnectByStt(
												stepDataList[id].id,
												targetIdArray[i], "edit");
									}
								}
							}
							Ui.tip(U.lang('WF.DELETE_SUCCESS'), "success");
						});
			} else if (processDesign.instance.getData(index).type == 'ParallelGateway') {
				Ui.confirm(U.lang('WF.DELETE_GATE_CONFIRM'), function() {
							Ui.tip(U.lang('WF.DELETE_GATE_SUCCESS'), "success");
							processDesign.instance.removeStep(index, true);
						});
			} else if (processDesign.instance.getData(index).type == 'ExclusiveGateway') {
				Ui.confirm(U.lang('WF.DELETE_CONDITION_CONFIRM'), function() {
							Ui.tip(U.lang('WF.DELETE_CONDITION_SUCCESS'),
									"success");
							processDesign.instance.removeStep(index, true);
						});
			}
			if (Zdsoft.app.g('operation') === "process"
					|| Zdsoft.app.g('operation') === "modify") {
				// 如果受权限控制 开放出前一步和后一步
				var targetIds = processDesign.instance
						.getSourceAndTargetRelation(deleteId);
				var sourceIds = processDesign.instance
						.getTargetAndSourceRelation(deleteId);
				if (targetIds) {
					var targetIdArray = targetIds.split(",");
					for (var i = 0; i < targetIdArray.length; i++) {
						jsPlumb.setTargetEnabled(targetIdArray[i], true);
					}
				}
				if (sourceIds) {
					var sourceIdArray = sourceIds.split(",");
					for (var i = 0; i < sourceIdArray.length; i++) {
						jsPlumb.setSourceEnabled(sourceIdArray[i], true);
					}
				}
			}
		} else {
			Ui.tip(U.lang('WF.PLEASE_SELECT_A_STEP'), "warning");
		}
	},
	/**
	 * 删除选中步骤
	 * 
	 * @returns {undefined}
	 */
	removeSelectedStep : function() {
		var selectedId = processDesign.instance.getSelect();
		if (selectedId)
			wfControll.removeStep(selectedId);
		else
			Ui.tip(U.lang('WF.PLEASE_SELECT_A_STEP'), "warning");
	},
	/**
	 * 保存链接与视图
	 * 
	 * @param {object}
	 *            data
	 * @param {function}
	 *            callback
	 * @returns {undefined}
	 */
	sendFlow : function() {
		$("#sendButton").attr("class", "abtn-gray");
		if (!saveFlag) {
			saveFlag = true;
		} else {
			return;
		}
		$("#wf_designer_canvas").animate({
					scrollTop : 0,
					scrollLeft : 0
				}, 0);
		var fullData = processDesign.getData();
		if(typeof(fullData) == "undefined") {
			Ui.tip(U.lang('WF.FLOW_CHECK_ERROR'), 'danger');
			$("#sendButton").attr("class", "abtn-blue");
			saveFlag = false;
			return;
		}
		if(!fullData) {
			$("#sendButton").attr("class", "abtn-blue");
			saveFlag = false;
			return;
		}
		var modelData = fullData.model;
		if(Zdsoft.app.g('operation') !== "start"){		
		 	fullData = modelData;
		}
		modelData = JSON.stringify(modelData);
		fullData = JSON.stringify(fullData);
		// edit 和 process 调用的不同
		var url, param;
		url = Zdsoft.app.g('actionUrl'), param = {
			id : Zdsoft.app.g('id'),
			taskHandlerSaveJson : Zdsoft.app.g('taskHandlerSaveJson'),
			jsonResult : modelData
		};
		$.post(url, param, function(result) {
			if (result === "success") {
				Ui.tip(U.lang('WF.FLOW_SAVE_SUCCESS'), 'success');
				wfControll.closeDesinger(Zdsoft.app.g('callBackJs'),Zdsoft.app.g('id'),
						Zdsoft.app.g('easyLevel'), fullData);
			} else {
				Ui.tip(	U.lang('WF.FLOW_SAVE_ERROR') + "(" + result
								+ ")", 'danger');
				$("#sendButton").attr("class", "abtn-blue");
				saveFlag = false;
			}
		});
	},
	/**
	 * 保存链接与视图
	 * 
	 * @param {object}
	 *            data
	 * @param {function}
	 *            callback
	 * @returns {undefined}
	 */
	saveFlow : function() {
		$("#saveButton").attr("class", "abtn-gray");
		if (!saveFlag) {
			saveFlag = true;
		} else {
			return;
		}
		$("#wf_designer_canvas").animate({
					scrollTop : 0,
					scrollLeft : 0
				}, 0);
		var fullData = processDesign.getData();
		if(typeof(fullData) == "undefined") {
			Ui.tip(U.lang('WF.FLOW_CHECK_ERROR'), 'danger');
			$("#saveButton").attr("class", "abtn-blue");
			saveFlag = false;
			return;
		}
		if(!fullData) {
			$("#saveButton").attr("class", "abtn-blue");
			saveFlag = false;
			return;
		}
		var modelData = fullData.model;
		if(Zdsoft.app.g('operation') === "edit"){		
		 	fullData = modelData;
		}
		modelData = JSON.stringify(modelData);
		fullData = JSON.stringify(fullData);
		// edit 和 process 调用的不同
		var url, param;
		if (Zdsoft.app.g('operation') === "edit") {
			url = Zdsoft.app.url("jbmp/editor/wf-saveData.action"), param = {
				id : Zdsoft.app.g('id'),
				actionUrl : Zdsoft.app.g('actionUrl'),
				deploy : Zdsoft.app.g('deploy'),
				jsonResult : modelData
			};
			$.post(url, param, function(result) {
				if (result === "success") {
					Ui.tip(U.lang('WF.FLOW_SAVE_SUCCESS'), 'success');
					wfControll.closeDesinger(Zdsoft.app.g('callBackJs'),Zdsoft.app.g('id'),
							Zdsoft.app.g('easyLevel'), fullData);
				} else {
					Ui.tip(	U.lang('WF.FLOW_SAVE_ERROR') + "(" + result
									+ ")", 'danger');
					$("#saveButton").attr("class", "abtn-blue");
					saveFlag = false;
				}
			});
			
		} else {
			Ui.tip(U.lang('WF.FLOW_SAVE_SUCCESS'), 'success');
			wfControll.closeDesinger(Zdsoft.app.g('callBackJs1'),Zdsoft.app.g('id'),Zdsoft.app.g('easyLevel'), fullData);
		}
		
	},
	// 保存流程
	addFlow : function() {
		Ui.dialog({
			id : "d_flow_save",
			title : U.lang('WF.ADD_FLOW'),
			content : "<div id='addFlowDiv' style='width:400px;left:100px;'></div>",
			padding : 0,
			top : 100,
			init : function() {
				var param = {
					subsystem : Zdsoft.app.g('subsystemId')
				};
				var url = Zdsoft.app.url(
						"component/flowManage/flowManage-choseAdd.action",
						param);
				load("#addFlowDiv", url);
			},
			ok : function() {
				$("#addButton").attr("class", "abtn-gray");
				if (!addFlag) {
					addFlag = true;
				} else {
					return;
				}
				$("#wf_designer_canvas").animate({
							scrollTop : 0,
							scrollLeft : 0
						}, 0);
				var flowName = $("#flowName").val(), description = $("#description")
						.val();
				if(_getLength(description)>120){
					Ui.tip("流程描述超过最大长度120", 'warning');
					$("#addButton").attr("class", "abtn-blue");
					addFlag = false;
					return ;
				}
				if ($.trim(flowName) === "") {
					Ui.tip(L.WF.FLOW_NAME_EMPTY, 'warning');
					$("#addButton").attr("class", "abtn-blue");
					addFlag = false;
					return ;
				}
				var data = processDesign.getData();
				if (typeof(data) == "undefined") {
					Ui.tip(U.lang('WF.FLOW_CHECK_ERROR'), 'danger');
					$("#addButton").attr("class", "abtn-blue");
					addFlag = false;
					return;
				}
				if (!data) {
					$("#addButton").attr("class", "abtn-blue");
					addFlag = false;
					return;
				}
				data =data.model;
				data.name=flowName;
				data = JSON.stringify(data);
				
				var isUnit = "0";
				if ($("#unit1").is(':checked'))
					isUnit = "1";
				var url = Zdsoft.app
						.url("component/flowManage/flowManage-choseSave.action"), param = {
					"flow.flowName" : flowName,
					"flow.description" : description,
					"flow.subsystem" : Zdsoft.app.g('subsystemId'),
					"flow.flowType" : Zdsoft.app.g('businessType'),
					"flow.easyLevel" : Zdsoft.app.g('easyLevel'),
					deploy : Zdsoft.app.g('deploy'),
					isUnit : isUnit,
					jsonResult : data
				};
				$.post(url, param, function(result) {
					if (result === "success") {
						Ui.tip(U.lang('WF.FLOW_SAVE_SUCCESS'),
								'success');
						// 回调
					} else {
						Ui.tip(	U.lang('WF.FLOW_SAVE_ERROR') + "("
										+ result + ")", 'danger');
					}
					$("#addButton").attr("class", "abtn-blue");
					addFlag = false;
				});
			}
		});
	},

	/**
	 * 修改步骤保存
	 * 
	 * @returns {undefined}
	 */
	stepEditSave : function(id, index) {
		if (!step_checkDiv()) {
			return false;
		}
		var steps = [];
		var users = [];
		var taskUser = $("#selTeacherName").val(), taskUserId = $("#selTeacherId")
				.val();
		users.push({
					id : taskUserId,
					name : taskUser
				});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index, name = $("#stepName").val(), step = Zdsoft.app
					.g('businessType');
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id = 'UserTask_' + step + '_' + processId;
				var left = $("#" + _id).position().left;
				var top = $("#" + _id).position().top;
				steps.push({
							id : id,
							left : left,
							top : top,
							index : processId,
							type : 'UserTask',
							name : name,
							step : step,
							permission : 0,
							taskUser : users[i].name,
							taskUserId : users[i].id
						});
				processDesign.instance.updateStep(index, steps[i]);
				$(normalStepSelector + ' i').dblclick(function(e) {
							e.stopPropagation();
						});
			}
		}
	},
	/**
	 * 增加步骤保存
	 * 
	 * @returns {undefined}
	 */
	stepAddSave : function() {
		if (!step_checkDiv()) {
			return false;
		}
		var steps = [];
		var users = [];
		var index = processDesign.instance.getMaxIndex();
		var taskUser = $("#selTeacherName").val(), taskUserId = $("#selTeacherId")
				.val();
		users.push({
					id : taskUserId,
					name : taskUser
				});

		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = ++index, name = $("#stepName").val(), step = Zdsoft.app
					.g('businessType');
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 不允许processId重复
				if (processDesign.instance.hasIndex(processId)) {
					Ui.tip(U.lang('WF.STEP_EXISTS'), "warning");
					return false;
				}
				// 也不允许新增中有processId重复
				for (var i = 0; i < steps.length; i++) {
					if (processId === steps[i].processId) {
						Ui.tip(U.lang('WF.STEP_EXISTS'), "warning");
						return false;
					}
				}

				// 满足所有条件的加入数组
				var _id = 'UserTask_' + step + "_" + processId;
				steps.push({
							id : _id,
							index : processId,
							type : 'UserTask',
							name : name,
							step : step,
							permission : 0,
							taskUser : users[i].name,
							taskUserId : users[i].id
						});
				$steps = processDesign.add(steps[i]);
				// 初始化右键菜单
				$steps
						.contextMenu("step_context_menu",
								stepContextMenuSettings);
				// 双击编辑步骤
				$("#" + _id).addClass("wf-step-next-modify");
				$(normalStepSelector + ' i').dblclick(function(e) {
							e.stopPropagation();
						});
				// $("#"+steps[i].id).trigger("click");
			}
		}
	},
	/**
	 * 修改步骤保存-公文
	 * 
	 * @returns {undefined}
	 */
	stepEdit4officeDocSave : function(id, index) {
		$("#wf_designer_canvas").animate({
					scrollTop : 0,
					scrollLeft : 0
				}, 0);
		if (!step_checkDiv()) {
			return false;
		}
		var steps = [];
		var users = [];
		var taskUser = $("#selTeacherName").val(), taskUserId = $("#selTeacherId")
				.val();
		users.push({
					id : taskUserId,
					name : taskUser
				});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index, name = $("#stepDiv").find(".selected")
					.text(), step = $("#step").val(), permission = -1;
			if ($("#span4").hasClass("ui-checkbox-current")) {
				permission = -1;
			} else {
				if ($("#span1").hasClass("ui-checkbox-current")) {
					permission += 1;
				}
				if ($("#span2").hasClass("ui-checkbox-current")) {
					permission += 2;
				}
				if ($("#span3").hasClass("ui-checkbox-current")) {
					permission += 4;
				}
			}
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id = 'UserTask_' + step + '_' + processId;
				var left = $("#" + _id).position().left;
				var top = $("#" + _id).position().top;
				steps.push({
							id : id,
							left : left,
							top : top,
							index : processId,
							type : 'UserTask',
							name : name,
							step : step,
							permission : permission,
							taskUser : users[i].name,
							taskUserId : users[i].id
						});
				if (Zdsoft.app.g('operation') === "process"
						|| Zdsoft.app.g('operation') === "modify") {
					processDesign.instance.updateStep(index, steps[i]);
					$("#" + _id).addClass("wf-step-next-modify");
					if (processDesign.instance.getStepMap(_id)) {

					} else {
						$("#" + _id).on("dblclick", function() {
									wfControll.editStep(this.id, "base");
								});
					}

				} else {
					processDesign.instance.updateStep(index, steps[i]);
				}
				$(normalStepSelector + ' i').dblclick(function(e) {
							e.stopPropagation();
						});
			}
		}
		editDialog.close();
	},
	/**
	 * 增加步骤保存-公文
	 * 
	 * @returns {undefined}
	 */
	stepAdd4officeDocSave : function(parallel, left, top, height, width) {
		if (!step_checkDiv()) {
			return false;
		}
		var steps = [];
		var users = [];
		var taskUser = $("#selTeacherName").val(), taskUserId = $("#selTeacherId")
				.val();
		// 并行 还是 单一
		var stepType = $("#stepType").find(".current").attr("value");
		if (stepType === "single") {
			users.push({
						id : taskUserId,
						name : taskUser
					});
		} else {
			var singleUsers = taskUser.split(',');
			var singleUserIds = taskUserId.split(',');
			
			if( singleUsers.length > 8){
				Ui.tip(U.lang('并行流程一次最多只能添加10个步骤'), "warning");
				return false;
			}
			
			for (var i = 0; i < singleUsers.length; i++) {
				users.push({
							id : singleUserIds[i],
							name : singleUsers[i]
						});
			};
			
		}
		var $steps, _id, processId;
		for (var ii = 0; ii < users.length; ii++) {
			var stepDataList = processDesign.instance.getSteps();
			// 循环每行获取对应的processId及name值
			var name = $("#stepDiv").find(".selected").text(), step = $("#step")
					.val(), permission = -1;
			var index = processDesign.instance.getMaxIndex();
			processId = ++index;
			if (Zdsoft.app.g('easyLevel') == '1') {
				if ($("#span4").hasClass("ui-checkbox-current")) {
					permission = -1;
				} else {
					if ($("#span1").hasClass("ui-checkbox-current")) {
						permission += 1;
					}
					if ($("#span2").hasClass("ui-checkbox-current")) {
						permission += 2;
					}
					if ($("#span3").hasClass("ui-checkbox-current")) {
						permission += 4;
					}
				}
			}
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 不允许processId重复
				if (processDesign.instance.hasIndex(processId)) {
					Ui.tip(U.lang('WF.STEP_EXISTS'), "warning");
					return false;
				}
				// 也不允许新增中有processId重复
				for (var i = 0; i < steps.length; i++) {
					if (processId === steps[i].processId) {
						Ui.tip(U.lang('WF.STEP_EXISTS'), "warning");
						return false;
					}
				}
				// 满足所有条件的加入数组
				_id = 'UserTask_' + step + "_" + processId;
				steps.push({
							id : _id,
							index : processId,
							type : 'UserTask',
							left : 0,
							top : 0,
							name : name,
							step : step,
							permission : permission,
							taskUser : users[ii].name,
							taskUserId : users[ii].id
						});
				$steps = processDesign.add(steps[ii]);

				// 初始化右键菜单
				$steps
						.contextMenu("step_context_menu",
								stepContextMenuSettings);
				// 双击编辑步骤
				$("#" + _id).addClass("wf-step-next-modify");
				if (Zdsoft.app.g('operation') === "process"
						|| Zdsoft.app.g('operation') === "modify") {
					$("#" + _id).on("dblclick", function() {
								wfControll.editStep(this.id, "base");
							});
				}
				$(normalStepSelector + ' i').dblclick(function(e) {
							e.stopPropagation();
						});
			}

			// 寻找上面的元素 或者并行的元素
			var aboveId = -99, aboveTop = 0, aboveLeft = 0;
			var belowId = -99, belowTop = 99999, belowLeft = 0;
			var isParallelGateway = false;
			var parallelSteps = [];
			var parallelStepsIds = [];
			parallelStepsIds.push(_id);
			// 找到是否有并行步骤
			var parallelHeight = 0;
			for (var id in stepDataList) {
				var selectedStep = $("#" + stepDataList[id].id);
				// 并行的步骤 有交叉的认定为并行步骤
				if (((top - stepDataList[id].top) > -height)
						&& ((top - stepDataList[id].top) < selectedStep
								.outerHeight())
						&& ((-width) < (left - stepDataList[id].left))
						&& ((left - stepDataList[id].left) < selectedStep
								.outerWidth())) {
					isParallelGateway = true;
					parallelHeight = stepDataList[id].top;
				}
			}

			// 用于计算并行步骤的left
			var totalParallelWidth = 0, parallelCount = 0;
			for (var id in stepDataList) {
				var selectedStep = $("#" + stepDataList[id].id);
				// 并行的步骤 有交叉的认定为并行步骤
				if (parallelHeight == stepDataList[id].top) {
					parallelSteps.push(stepDataList[id]);
					parallelStepsIds.push(stepDataList[id].id);
					totalParallelWidth += selectedStep.outerWidth();
					parallelCount++;
				} else {
					if (top - stepDataList[id].top > 0) {
						// 寻找上面的元素
						if (stepDataList[id].top > aboveTop) {
							aboveId = id;
							aboveTop = stepDataList[id].top;
							aboveLeft = stepDataList[id].left;
						}
					} else {
						// 下面的元素
						if (top < stepDataList[id].top) {
							if (stepDataList[id].top < belowTop) {
								belowId = id;
								belowTop = stepDataList[id].top;
								belowLeft = stepDataList[id].left;
							}
						}
					}
				}
			}
			var existsGate = false;
			// 根据内容来重新计算位置 居中 如果是并行步骤的话 TODO
			var $container = $("#wf_designer_canvas");
			// 如果添加的是并行步骤
			if (isParallelGateway) {
				var parallelInterval = ($container.outerWidth()
						- totalParallelWidth - $steps.outerWidth())
						/ (parallelCount + 2);
				if (parallelInterval <= 0)
					parallelInterval = 10;
				// 如果上一步的类型不是网关 则需要添加网关节点 2个
				if (stepDataList[aboveId].type != "ParallelGateway") {
					// 前面网关
					var gate_before = wfControll.addGate();
					var gate_before_div = $("#" + gate_before[0].id);
					var position_gate_before = {
						left : ($container.outerWidth() - gate_before_div
								.outerWidth())
								/ 2,
						top : aboveTop + 100
					};
					gate_before_div.css(position_gate_before);
					processDesign.instance.updateData(gate_before[0].id
									.substring(gate_before[0].id
											.lastIndexOf('_')
											+ 1), position_gate_before);

					// 并行的节点
					var _parallelStepLeft = 0;
					var _parallelStepId;
					for (var k = 0; k < parallelSteps.length; k++) {
						_parallelStepId = parallelSteps[k].id;
						_parallelStepLeft += parallelInterval;
						var parallelStep = $("#" + parallelSteps[k].id);
						var position_parallel_step = {
							left : _parallelStepLeft,
							top : aboveTop + 200
						};
						parallelStep.css(position_parallel_step);
						processDesign.instance.updateData(
								parallelSteps[k].index, position_parallel_step);
						_parallelStepLeft += parallelStep.outerWidth();
					}

					_parallelStepLeft += parallelInterval;
					// 本节点
					var position_add_step = {
						left : _parallelStepLeft,
						top : aboveTop + 200
					};
					$steps.css(position_add_step);
					processDesign.instance.updateData(processId,
							position_add_step);

					// 后面网关
					var gate_after = wfControll.addGate();
					var gate_before_div = $("#" + gate_after[0].id);
					var position_gate_after = {
						left : ($container.outerWidth() - $("#"
								+ gate_after[0].id).outerWidth())
								/ 2,
						top : aboveTop + 300
					};
					gate_before_div.css(position_gate_after);
					processDesign.instance.updateData(gate_after[0].id
									.substring(gate_after[0].id
											.lastIndexOf('_')
											+ 1), position_gate_after);

					// 放置连线信息 source-->target
					processDesign.instance.putSourceAndTargetRelation(
							stepDataList[aboveId].id, gate_before[0].id);
					processDesign.instance.putSourceAndTargetRelation(
							gate_before[0].id, _parallelStepId + "," + _id);
					processDesign.instance.putSourceAndTargetRelation(
							_parallelStepId, gate_after[0].id);
					processDesign.instance.putSourceAndTargetRelation(_id,
							gate_after[0].id);
					processDesign.instance.putSourceAndTargetRelation(
							gate_after[0].id, stepDataList[belowId].id);
					// 放置连线信息 target-->source
					processDesign.instance.putTargetAndSourceRelation(
							gate_before[0].id, stepDataList[aboveId].id);
					processDesign.instance.putTargetAndSourceRelation(
							_parallelStepId, gate_before[0].id);
					processDesign.instance.putTargetAndSourceRelation(_id,
							gate_before[0].id);
					processDesign.instance.putTargetAndSourceRelation(
							gate_after[0].id, _parallelStepId + "," + _id);
					processDesign.instance.putTargetAndSourceRelation(
							stepDataList[belowId].id, gate_after[0].id);
				} else {
					// 上一步节点是网关的
					// 并行的节点
					existsGate = true;
					var _parallelStepLeft = 0;
					for (var k = 0; k < parallelSteps.length; k++) {
						_parallelStepLeft += parallelInterval;
						var parallelStep = $("#" + parallelSteps[k].id);
						var position_parallel_step = {
							left : _parallelStepLeft,
							top : aboveTop + 100
						};
						parallelStep.css(position_parallel_step);
						processDesign.instance.updateData(
								parallelSteps[k].index, position_parallel_step);
						_parallelStepLeft += parallelStep.outerWidth();
					}

					_parallelStepLeft += parallelInterval;
					// 本节点
					var position_add_step = {
						left : _parallelStepLeft,
						top : aboveTop + 100
					};
					$steps.css(position_add_step);
					processDesign.instance.updateData(processId,
							position_add_step);

					// 放置连线信息 source-->target
					var _parallelTargetId = processDesign.instance
							.getSourceAndTargetRelation(stepDataList[aboveId].id);
					_parallelTargetId += "," + _id
					processDesign.instance.putSourceAndTargetRelation(
							stepDataList[aboveId].id, _parallelTargetId);

					processDesign.instance.putSourceAndTargetRelation(_id,
							stepDataList[belowId].id);
					// 放置连线信息 target-->source
					processDesign.instance.putTargetAndSourceRelation(_id,
							stepDataList[aboveId].id);
					var _parallelSourceId = processDesign.instance
							.getTargetAndSourceRelation(stepDataList[belowId].id);
					_parallelSourceId += "," + _id;
					processDesign.instance.putTargetAndSourceRelation(
							stepDataList[belowId].id, _parallelSourceId);
				}
			} else {
				// 本节点
				var position = {
					left : ($container.outerWidth() - $steps.outerWidth()) / 2,
					top : aboveTop + 100
				};
				$steps.css(position);
				processDesign.instance.updateData(processId, position);

				// 放置连线信息 source-->target
				processDesign.instance.putSourceAndTargetRelation(
						stepDataList[aboveId].id, _id);
				processDesign.instance.putSourceAndTargetRelation(_id,
						stepDataList[belowId].id);
				// 放置连线信息 target-->source
				processDesign.instance.putTargetAndSourceRelation(_id,
						stepDataList[aboveId].id);
				processDesign.instance.putTargetAndSourceRelation(
						stepDataList[belowId].id, _id);
			}

			// 将下面所有的节点相应的增加高度
			var top_next_add_height = 100;
			if (!existsGate && isParallelGateway) {
				top_next_add_height = 200;
			}
			for (var id in stepDataList) {
				var selectedStep = $("#" + stepDataList[id].id);
				// 下面的元素
				if (top < stepDataList[id].top) {
					// 排除自己和并行的元素//TODO
					if ($.inArray(stepDataList[id].id, parallelStepsIds) != -1)
						continue;
					if (stepDataList[id].type == "ParallelGateway"
							&& isParallelGateway && !existsGate)
						continue;
					if (isParallelGateway && existsGate) {
						continue;
					}
					// 将下面的所有元素调整位置 固定加100
					var position_next = {
						left : stepDataList[id].left,
						top : stepDataList[id].top + top_next_add_height
					};
					$("#" + stepDataList[id].id).css(position_next);
					processDesign.instance.updateData(stepDataList[id].index,
							position_next);
				}
			}

			// 删除所有连线
			processDesign.instance.detachEveryConnection();
			// 全部重新连线
			for (var id in stepDataList) {
				var targetIds = processDesign.instance
						.getSourceAndTargetRelation(stepDataList[id].id)
				if (targetIds) {
					var targetIdArray = targetIds.split(",");
					for (var i = 0; i < targetIdArray.length; i++) {
						if (targetIdArray[i] != "")
							processDesign.instance.addConnectByStt(
									stepDataList[id].id, targetIdArray[i],
									"edit");
					}
				}

			}
			top = $steps.position().top;
			left = $steps.position().left;
		}
		addDialog.close();
	},
	/**
	 * 清除所有链接
	 * 
	 * @returns {undefined}
	 */
	clearAllConnect : function() {
		Ui.confirm(U.lang('WF.CLEAR_CONNECTION_CONFIRM'), function() {
					processDesign.instance.clearConnect();
				});
	},
	/**
	 * 计算画板的高度
	 * 
	 * @returns {undefined}
	 */
	setCanvasHeight : function() {
		// 工作流操作平台框架
		var sidHeight = $(window).height() - $('.workflow-header').height();
		$('.workflow-canvas,.workflow-sidebar-left').height(sidHeight);
	},
	/**
	 * 关闭设计器
	 */
	closeDesinger : function(callBackJs, id,easyLevel, jsonResult) {
		// setTimeout(function(){$(".wf-boxy-close",window.parent.document).trigger('click');},2000);
		setTimeout(function() {
					parent.closeOfficeWin(callBackJs,id,easyLevel,jsonResult);
				}, 1000);
	},
	closeDesingerWithConfirm : function() {
		
		Ui.confirm('是否保存当前的流程?', function () {
    		wfControll.saveFlow();
		}, function () {
   			 setTimeout(function() {
					parent.closeOfficeWin();
				}, 1000);
		});
		
	},
	/**
	 * 帮助
	 */
	help : function() {
		if (Zdsoft.app.g('businessType').indexOf("17") === 0) {
			window.open(Zdsoft.app.url("jbmp/editor/wf-help-officedoc.action"),
					"流程设计-帮助");
		} else {
			window
					.open(Zdsoft.app.url("jbmp/editor/wf-help.action"),
							"流程设计-帮助");
		}
	}
};

/**
 * 步骤右键菜单配置
 */
var stepContextMenuSettings = {
	bindings : {
		// 编辑-基本信息
		"step_context_basic" : function(elem, b) {
			wfControll.editStep(elem.id, "base");
		},
		// 删除步骤
		"step_context_del" : function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	},
	onContextMenu : function(e) {
		if (e.target.className == 'mouseHover-tips')
			return false;
		else
			return true;
	}
},
/**
 * 网关右键菜单配置对象
 */
gateContextMenuSettings = {
	bindings : {
		// 删除步骤
		"step_context_del" : function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	}
},
/**
 * 条件节点右键菜单配置对象
 */
conditionContextMenuSettings = {
	bindings : {
		// 删除条件
		"condition_context_del" : function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	}
};

$(function() {
	/**
	 * 设计器wrapper
	 * 
	 * @param {object}
	 *            $container
	 */
	var $container = $("#wf_designer_canvas");
	var id = Zdsoft.app.g('id');
	var businessType = Zdsoft.app.g('businessType');
	var operation = Zdsoft.app.g('operation');
	var instanceType = Zdsoft.app.g('instanceType');
	var actionUrl = Zdsoft.app.g('actionUrl');
	var callBackJs = Zdsoft.app.g('callBackJs');
	var currentStepId = Zdsoft.app.g('currentStepId');
	var taskHandlerSaveJson = Zdsoft.app.g('taskHandlerSaveJson');
	var subsystemId = Zdsoft.app.g('subsystemId');
	var jsonResult = Zdsoft.app.g('jsonResult');

	/**
	 * 数据通信对象
	 * 
	 * @param {object}
	 *            wfInter
	 */
	var wfInter = {
		/**
		 * 初始化流程链接
		 * 
		 * @param {function}
		 *            callback
		 * @returns {undefined}
		 */
		init : function() {
			if (jsonResult == "" || jsonResult == null) {
				var url = Zdsoft.app.url("jbmp/editor/wf-getData.action"), param = {
					id : id,
					subsystemId : subsystemId,
					instanceType : instanceType,
					currentStepId : currentStepId
				};
				getJSON(url, param, function(data) {
							wfInter.assembledData(data)
						});
			} else {
				//jsonResult="{'model':"+jsonResult+"}";
				wfInter.assembledData(jsonResult)
			}
		},

		assembledData : function(data) {
			// 生成节点与链接
			processDesign.set(data);
			// 只有编辑的情况下才可以操作下面的
			var stepSelector = normalStepSelector;
			var gateSelector = normalGateSelector;
			var conditionSelector = normalConditionSelector;
			if (operation === 'edit' || operation === 'start') {
				// 公文流程设计的时候屏蔽条件节点
				if (subsystemId != "17") {
					$("#addConditionButton").show();
				}
				// 初始化步骤右键菜单
				$(stepSelector).contextMenu("step_context_menu",
						stepContextMenuSettings);
				// 初始化网关右键菜单
				// $(gateSelector).contextMenu("gate_context_menu",
				// gateContextMenuSettings);
				// 初始化条件节点右键菜单
				$(conditionSelector).contextMenu("condition_context_menu",
						conditionContextMenuSettings);
				// 初始化通用右键菜单
				// $container.contextMenu("common_context_menu",
				// commonContextMenuSettings);

				// 阻止非空白处的右键事件，不出现菜单
				$container.on("contextmenu", "*", function(evt) {
							evt.stopPropagation();
						});
				// 绑定事件点击控制事件
				Zdsoft.events.add(wfControll);
				Zdsoft.events.add(wfOpeator);

				// 双击编辑步骤
				$container.on("dblclick", stepSelector, function() {
							wfControll.editStep(this.id, "base");
						});
			} else if (operation === 'process' || operation === 'modify') {
				// gateSelector = nextGateSelector;
				// $(gateSelector).contextMenu("gate_context_menu",
				// gateContextMenuSettings);
				var _currentStep = processDesign.instance.getCurrentStep();
				if (_currentStep) {
					var permission = _currentStep.properties.permission;
					if (permission == -1) {
						stepSelector = uncompleteStepSelector;
						$(stepSelector).contextMenu("step_context_menu",
								stepContextMenuSettings);
						// 双击编辑步骤
						$container.on("dblclick", stepSelector, function() {
									wfControll.editStep(this.id, "base");
								});

					} else {
						stepSelector = nextRMStepSelector;
						var permissionArray = processDesign.instance
								.tranfserPermission(permission);

						// 双击编辑步骤
						$container.on("dblclick", nextModifyStepSelector,
								function() {
									wfControll.editStep(this.id, "base");
								});
						$(nextModifyStepSelector).contextMenu(
								"step_context_menu_modify",
								stepContextMenuSettings);

						$(nextRemoveStepSelector).contextMenu(
								"step_context_menu_remove",
								stepContextMenuSettings);

						$(stepSelector).contextMenu("step_context_menu",
								stepContextMenuSettings);

					}
				}
				// 阻止非空白处的右键事件，不出现菜单
				$container.on("contextmenu", "*", function(evt) {
							evt.stopPropagation();
						});
				// 绑定事件点击控制事件
				Zdsoft.events.add(wfControll);

			}
			// 读取步骤信息并刷新右栏
			infoBar.reset();
			// 选中步骤并读取步骤信息
			var loadLock;
			$container.on("click", normalStepSelector + ",.wf-step-active",
					function() {
						var id = parseInt(this.id.substring(this.id
										.lastIndexOf('_')
										+ 1), 10);
						processDesign.instance.select(id);
						if (!loadLock) {
							infoBar.loadContent(id);
							loadLock = true;
							setTimeout(function() {
										loadLock = false;
									}, 500)
						}
					});
			// 阻止连线部分 单机和双击事件
			$(normalStepSelector + ' i').click(function(e) {
						e.stopPropagation();
					});
			$(normalStepSelector + ' i').dblclick(function(e) {
						e.stopPropagation();
					});

			wfControll.setCanvasHeight();
		}
	};

	/**
	 * 右侧信息条
	 * 
	 * @param function
	 *            infoBar
	 */
	var infoBar = (function() {
		var $container = $("#wf_sidebar");
		var sidebarTpl = "<div class='workflow-info'>";
		sidebarTpl += "<p class='tit'>基本信息</p><ul><li><span>当前步骤名称:</span><%=name%></li></ul>";
		sidebarTpl += "<p class='tit'>审核人</p><ul><li><span class='full'><%=taskUser%></span></li></ul>";
		// sidebarTpl += "<p class='tit'>权限控制</p><ul><li><span
		// class='full'><%=permissionStr%></span></li></ul></div>";
		if (operation !== 'edit') {
			sidebarTpl += "<p class='pl-20 mt-10'><a id='sendButton' href='javascript:;' class='abtn-blue' data-click='sendFlow'>确定</a></p>";
			sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='closeDesingerWithConfirm'>返回</a></p>";
		}else{
			sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='saveFlow'>确定</a></p>";
			sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='closeDesinger'>返回</a></p>";
		}
		return {
			$container : $container,
			setContent : function(content) {
				$container.html(content);
				var sidHeight = $(window).height()
						- $('.workflow-header').height();
				$('.workflow-sidebar').height(sidHeight);
			},
			/**
			 * 加载步骤信息
			 * 
			 * @param {integer}
			 *            id 步骤ID
			 * @param {function}
			 *            callback 执行回调函数
			 * @returns {undefined}
			 */
			loadContent : function(id) {
				var that = this;
				$container.waiting(U.lang('READ_INFO'));
				var _selectdData = processDesign.instance.getData(id);
				var permissionStr = "";
				if (_selectdData.permission === -1) {
					permissionStr += "可自定义流程";
				} else {
					var permissionArray = processDesign.instance
							.tranfserPermission(_selectdData.permission);
					if (permissionArray[2])
						permissionStr += "可插入步骤 ";
					else
						permissionStr += "不可插入步骤 ";
					if (permissionArray[1])
						permissionStr += "可修改负责人 ";
					else
						permissionStr += "不可修改负责人 ";
					if (permissionArray[0])
						permissionStr += "可略过步骤 ";
					else
						permissionStr += "不可略过步骤 ";
				}

				var _nodes = $.tmpl(sidebarTpl, {
					name : _selectdData.name,
					taskUser : _selectdData.taskUser
						// ,permissionStr : permissionStr
					});
				$container.stopWaiting();
				that.setContent(_nodes);
			},
			reset : function() {
				var sidebarTpl = "<div class='workflow-guide'>工作流引导</div>";
				if (operation !== 'edit') {
					sidebarTpl += "<p class='pl-20 mt-10'><a id='sendButton' href='javascript:;' class='abtn-blue' data-click='sendFlow'>确定</a></p>";
					sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='closeDesingerWithConfirm'>返回</a></p>";
				}else{
					sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='saveFlow'>确定</a></p>";
					sidebarTpl += "<p class='pl-20 mt-10'><a id='returnButton' href='javascript:;' class='abtn-blue' data-click='closeDesinger'>返回</a></p>";
				}
				
				$container.html(sidebarTpl);
				// $container
				// .html("<p class='pl-20 mt-10'><a id='saveButton'
				// href='javascript:;' class='abtn-blue'
				// data-click='saveFlow'>保存流程图链接和视图</a></p>");
				var sidHeight = $(window).height()
						- $('.workflow-header').height();
				$('.workflow-sidebar').height(sidHeight);
			}
		};
	})();

	var wfOpeator = {
		/**
		 * 重新加载视图
		 * 
		 * @returns {undefined}
		 */
		reloadFlow : function() {
			processDesign.instance.clear();
			wfInter.init();
		}
	};
	/**
	 * 通用右键菜单配置对象
	 */
	commonContextMenuSettings = {
		bindings : {
			// 新增步骤
			// @Todo: 是否新增单一步骤
			"common_context_addstep" : wfControll.addStep,
			// 刷新视图
			"common_context_reload" : wfOpeator.reloadFlow,
			// 保存流程
			"common_context_save" : wfControll.saveFlow
		}
	};
	// 执行初始化流程步骤操作
	wfInter.init();

	// 设置左边的栏目内容可以拖动
	$("#workflow-sidebar-left").children().draggable({
		helper : "clone",
		scope : "wf_designer_canvas",
		drag : function(event, ui) {
			ui.helper.addClass("wf-user-move");
			var left = parseInt(ui.offset.left - $(this).offset().left) - 101
					+ $("#wf_designer_canvas").scrollLeft();
			var top = parseInt(ui.offset.top) + 11
					+ $("#wf_designer_canvas").scrollTop();
			var stepDataList = processDesign.instance.getSteps();
			for (var id in stepDataList) {
				var selectedStep = $("#" + stepDataList[id].id);
				if (((top - stepDataList[id].top) > -ui.helper.outerHeight())
						&& ((top - stepDataList[id].top) < selectedStep
								.outerHeight())
						&& ((-ui.helper.outerWidth()) < (left - stepDataList[id].left))
						&& ((left - stepDataList[id].left) < selectedStep
								.outerWidth())) {
					if (stepDataList[id].type == "UserTask") {
						// 增加样式
						if (!selectedStep.hasClass("wf-step-active"))
							selectedStep.addClass("wf-step-active");
					}
				} else {
					// 去掉样式
					if (selectedStep.hasClass("wf-step-active"))
						selectedStep.removeClass("wf-step-active");
				}
			}
		},
		start:function(event, ui) {
			var stepDataList = processDesign.instance.getSteps();
			var beforeParallelGatewayHeight = 0;
			var nextParallelGatewayHeight = 0;
			var currentStepTop = 0;
			var currentStepId = Zdsoft.app.g('currentStepId');
			for (var id in stepDataList) {
				if (currentStepId == stepDataList[id].id) {
					currentStepTop = stepDataList[id].top;
				}
				if (stepDataList[id].type == "ParallelGateway") {
					if (beforeParallelGatewayHeight == 0) {
						beforeParallelGatewayHeight = stepDataList[id].top;
					}
					if (stepDataList[id].top > beforeParallelGatewayHeight) {
						nextParallelGatewayHeight = stepDataList[id].top;
					}
				}
			}
			for (var id in stepDataList) {
				if(stepDataList[id].top >= currentStepTop){
					if (stepDataList[id].type != "EndNoneEvent") {
						if(beforeParallelGatewayHeight==0 ||(beforeParallelGatewayHeight>0 && (stepDataList[id].top<beforeParallelGatewayHeight || stepDataList[id].top>nextParallelGatewayHeight))){
							var top=stepDataList[id].top+48;
							var height=50;
							$("#wf_designer_canvas").append("<p class='wf-over-bg' style='height:"+height+"px;top:"+top+"px;'></p>");
						}
						if(stepDataList[id].top==nextParallelGatewayHeight){
							var top=stepDataList[id].top+39;
							var height=61;
							$("#wf_designer_canvas").append("<p class='wf-over-bg' style='height:"+height+"px;top:"+top+"px;'></p>");
						}
					}
				}
			}
		},
		stop:function(event, ui) {
			$(".wf-over-bg").remove();
		}
	});

	$("#wf_designer_canvas").droppable({
		scope : "wf_designer_canvas",
		tolerance : 'touch',
		drop : function(event, ui) {
			var left = parseInt(ui.offset.left - $(this).offset().left);
			var top = parseInt(ui.offset.top - $(this).offset().top) + 11
					+ $("#wf_designer_canvas").scrollTop();
			var stepDataList = processDesign.instance.getSteps();
			// 寻找上面的元素 或者并行的元素
			var aboveId = -99, aboveTop = 0;
			var belowId = -99, belowTop = 99999;
			var beforeParallelGatewayHeight = 0;
			var nextParallelGatewayHeight = 0;
			var parallel = false;
			var currentStepId = Zdsoft.app.g('currentStepId');
			var currentStepTop = 0;
			// 先有的网关数量 用于判断是否多重网关 目前只能支持2个网关
			var isParallelGateway = false, parallelNum = 0;
			for (var id in stepDataList) {
				if (currentStepId == stepDataList[id].id) {
					currentStepTop = stepDataList[id].top;
				}
				var selectedStep = $("#" + stepDataList[id].id);
				if (stepDataList[id].type == "ParallelGateway") {
					parallelNum++;
					if (beforeParallelGatewayHeight == 0) {
						beforeParallelGatewayHeight = stepDataList[id].top;
					}
					if (stepDataList[id].top > beforeParallelGatewayHeight) {
						nextParallelGatewayHeight = stepDataList[id].top;
					}
				}
				// 并行的步骤 有交叉的认定为并行步骤
				if (((top - stepDataList[id].top) > -ui.helper.outerHeight())
						&& ((top - stepDataList[id].top) < selectedStep
								.outerHeight())
						&& ((-ui.helper.outerWidth()) < (left - stepDataList[id].left))
						&& ((left - stepDataList[id].left) < selectedStep
								.outerWidth())) {

					if (stepDataList[id].type != "UserTask") {
						// 并行节点只能是节点 不能是其他类型
						Ui.tip(U.lang('WF.INVALID_OPERATION'), "warning");
						return;
					}
					isParallelGateway = true;
					parallelHeight = stepDataList[id].top;
				} else {
					// 寻找上面的元素
					if (top - stepDataList[id].top > 0) {
						if (stepDataList[id].top > aboveTop) {
							aboveId = id;
							aboveTop = stepDataList[id].top;
						}
					} else {
						// 下面的元素
						if (top < stepDataList[id].top) {
							if (stepDataList[id].top < belowTop) {
								belowId = id;
								belowTop = stepDataList[id].top;
							}
						}
					}
				}
			}
			var inParallelGateway = false;
			if (top > beforeParallelGatewayHeight
					&& top < nextParallelGatewayHeight)
				inParallelGateway = true;
			// 不能放在开始节点前面
			if (aboveId == -99) {
				Ui.tip(U.lang('WF.INVALID_OPERATION_BEFORE_START'), "warning");
				return;
			}
			// 不能放在结束节点后面
			if (stepDataList[aboveId].type == "EndNoneEvent") {
				Ui.tip(U.lang('WF.INVALID_OPERATION_AFTER_END'), "warning");
				return;
			}
			// 并行步骤内部不支持增加非并行步骤
			if (inParallelGateway && !isParallelGateway) {
				Ui.tip(U.lang('WF.INVALID_OPERATION_INVALID_PARALLEL'),
						"warning");
				return;
			}

			// 多重网关
			if (parallelNum != 0 && isParallelGateway && !inParallelGateway) {
				Ui.tip(U.lang('WF.INVALID_OPERATION_MORE_PARALLEL'), "warning");
				return;
			}
			// 如果没有并行步骤的情况下 或者有并行正好添加并行步骤的时候 开放并行选项
			if (parallelNum == 0 || (isParallelGateway && inParallelGateway)) {
				parallel = true;
			}

			if (Zdsoft.app.g('operation') === "process"
					|| Zdsoft.app.g('operation') === "modify") {
				if (top < currentStepTop) {
					Ui.tip(U.lang('WF.INVALID_OPERATION_MODIFY_STEP'),
							"warning");
					return;
				}
				if ((isParallelGateway && inParallelGateway)
						&& currentStepTop == parallelHeight) {
					Ui.tip(U.lang('WF.INVALID_OPERATION_MODIFY_STEP'),
							"warning");
					return;
				}
			}
			wfControll.addStep(parallel, left, top, ui.helper.outerHeight(),
					ui.helper.outerWidth());
		}
	});
});