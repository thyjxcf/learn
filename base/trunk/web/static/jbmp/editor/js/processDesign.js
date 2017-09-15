(function(){
	var dashstyle="0";
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
		dashstyle="-1";
	}
	var jsPlumbDefaults = {
		Endpoint: "Blank",
		
		Connector: "Flowchart",
		//Connector : "Straight",
		ConnectionOverlays : [
			[ "Arrow", { 
				location: 1,
				id: "arrow",
	            length: 14,
	            foldback: 0.6
			} ],
			[ "Label", { 
				location:0.1,
				id:"label",
				cssClass:"aLabel"
			}]
		],

		ConnectionsDetachable: false,
		ReattachConnections: false,

		//LogEnabled: true, // 调试模式
		// 锚点自动调整位置
		Anchor: "Continuous",
		// Anchor: [[0, 0.5, 1, 0], [1, 0.5, 1, 0]],
		// 连接器样式
		PaintStyle: { 
			strokeStyle:"#EE8C0C",
			lineWidth: 3,
			dashstyle: dashstyle,
			outlineWidth: 4,
			outlineColor: "transparent"
		}
	};

	// 判断连接器是否指向了自己
	var isConnectToSelf = function(param) {
			return param.sourceId === param.targetId
		},
		isFromStartToEnd = function(param){
			return param.sourceId === "StartNoneEvent_-1" && param.targetId === "EndNoneEvent_0"
		},
		// 判断连接步骤是否重复
		stepIsRepeat = function(param){
			// 获取同一scope下的链接器，判断当前链接是否有重复
			var cnts = jsPlumb.getConnections(param.scope);
			if(cnts.length > 0) {
				for(var i = 0, len = cnts.length; i < len; i++) {
					// 如果有链接器 sourceId 及 targetId 都相等，那大概也许可能就相等了。
					if(param.sourceId === cnts[i].sourceId && param.targetId === cnts[i].targetId) {
						return true;
					}
				}
			}
			return false;
		};

	// 连接前的事件，
	var beforeDropHandler = function(param) {
		if(isConnectToSelf(param)){
			return false;
		}
		if(stepIsRepeat(param)){
			Ui.tip(U.lang('WF.REPEAT_STEP'), "warning");
			return false;
		}
		if(isFromStartToEnd(param)) {
			Ui.tip(U.lang('WF.NOT_FROM_START_TO_END'), 'warning');
			return false;
		}
		if(isFromGateToGate(param)) {
			Ui.tip(U.lang('WF.NOT_FROM_GATE_TO_GATE'), 'warning');
			return false;
		}
		return true;
	};

	// 连接时, 判断步骤是否重复
	jsPlumb.bind("beforeDrop", beforeDropHandler);
	
	//注册连线类型
	jsPlumb.registerConnectionTypes({
		edit:{paintStyle: {strokeStyle:"#EE8C0C",lineWidth: 3,dashstyle: dashstyle,outlineWidth: 4,outlineColor: "transparent"},hoverPaintStyle: {strokeStyle:"#B2C0D1",lineWidth:3,dashstyle:"4 1"},overlays:[["Arrow",{location:1,id:"arrow",length:14,foldback:.6}]]},
		complete:{paintStyle:{strokeStyle:"#84b955",lineWidth:3,dashstyle: dashstyle,outlineWidth:4,outlineColor:"transparent"},overlays:[["Arrow",{location:1,id:"arrow",length:14,foldback:.6}]]},
		uncomplete:{paintStyle:{strokeStyle:"#B2C0D1",lineWidth:3,outlineWidth:4,outlineColor:"transparent",dashstyle:"4 1"},overlays:[["Arrow",{location:1,id:"arrow",length:14,foldback:.6}]]}
	});
	jsPlumb.importDefaults(jsPlumbDefaults);
	
})();

var processDesign = {
	instance: new wfDesigner($("#wf_designer_canvas"),jsPlumb),
	//解析数据
	parseData: function(data){
		var currentStepId,
			model=data.model,//模型定义数据
			modelExtend=data.modelExtend;//模型业务数据
		var that=this;
		
		var taskUserMap=new Map();//操作人map
		var ret = {
				steps: [],
				connects: []
			};
		var pushConnect = function(sourceId, targetIds) {
			var d;
			if(typeof sourceId === "undefined" || targetIds.length === 0) {
				return false;
			}
			var sourceStep = that.instance.getStepMap(sourceId);
			//根据target判断连线状态 edit/uncomplete/complete
			var _connectType="edit";//默认是可编辑的
			if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify" ){//非编辑才控制
				var permission=0;
				var currentStep =that.instance.getCurrentStep();
				if(currentStep)
					permission=currentStep.properties.permission;
				for(var i = 0; i < targetIds.length; i++) {
					//判断是否是连线 如果是，需要找到连线的outgoing
					var _resourceId=targetIds[i].resourceId;
					var sequenceFlow=that.instance.getSequenceFlow(_resourceId);
					var outgoing_resourceId =(sequenceFlow.outgoing)[0].resourceId;
					if(!outgoing_resourceId)
						outgoing_resourceId=_resourceId;
					if(permission===-1){
						if(that.instance.getStepStateMap(sourceId)){
							_connectType =that.instance.getPermissionConnectType(that.instance.getStepStateMap(sourceId));
						}
					}else{
		 				var permissionArray = that.instance.tranfserPermission(permission);
		 				if(permissionArray[2]){//insert
		 					if(that.instance.getStepStateMap(sourceId)){
		 						//对于当前节点 特殊处理
		 						if(currentStep.resourceId !==sourceId){
		 							_connectType =that.instance.getConnectType(that.instance.getStepStateMap(sourceId));
		 						}
							}
		 				}else{
		 					if(that.instance.getStepStateMap(sourceId)){
		 						_connectType =that.instance.getConnectType(that.instance.getStepStateMap(sourceId));
							}
		 				}
					}
					ret.connects.push(sourceId + "," + outgoing_resourceId+","+_connectType);
					that.instance.putConnectionMap(sourceId+outgoing_resourceId,_connectType);
					//保存条件信息
					if(sourceStep.stencil.id === "ExclusiveGateway"){
						that.instance.putConditionMap(sourceId+outgoing_resourceId,sequenceFlow.properties);
					}
					var _targetId="";
					if(that.instance.getSourceAndTargetRelation(sourceId)){
						_targetId=that.instance.getSourceAndTargetRelation(sourceId);
						_targetId+=","+outgoing_resourceId
					}else{
						_targetId=outgoing_resourceId;
					}
					that.instance.putSourceAndTargetRelation(sourceId,_targetId);
					var _sourceId="";
					if(that.instance.getTargetAndSourceRelation(outgoing_resourceId)){
						_sourceId=that.instance.getTargetAndSourceRelation(outgoing_resourceId);
						_sourceId+=","+sourceId
					}else{
						_sourceId=sourceId;
					}
					that.instance.putTargetAndSourceRelation(outgoing_resourceId,_sourceId);
				}	
			}else{
				for(var i = 0; i < targetIds.length; i++) {
					//判断是否是连线 如果是，需要找到连线的outgoing
					var _resourceId=targetIds[i].resourceId;
					var sequenceFlow=that.instance.getSequenceFlow(_resourceId);
					var outgoing_resourceId =(sequenceFlow.outgoing)[0].resourceId;
					if(!outgoing_resourceId)
						outgoing_resourceId=_resourceId;
					ret.connects.push(sourceId + "," + outgoing_resourceId+","+_connectType);
					//保存条件信息
					if(sourceStep.stencil.id === "ExclusiveGateway"){
						that.instance.putConditionMap(sourceId+outgoing_resourceId,sequenceFlow.properties);
					}
					var _targetId="";
					if(that.instance.getSourceAndTargetRelation(sourceId)){
						_targetId=that.instance.getSourceAndTargetRelation(sourceId);
						_targetId+=","+outgoing_resourceId
					}else{
						_targetId=outgoing_resourceId;
					}
					that.instance.putSourceAndTargetRelation(sourceId,_targetId);
					var _sourceId="";
					if(that.instance.getTargetAndSourceRelation(outgoing_resourceId)){
						_sourceId=that.instance.getTargetAndSourceRelation(outgoing_resourceId);
						_sourceId+=","+sourceId
					}else{
						_sourceId=sourceId;
					}
					that.instance.putTargetAndSourceRelation(outgoing_resourceId,_sourceId);
				}	
			}
		};
		//业务数据组装
		if(modelExtend && modelExtend.childShapes){
			$.each(modelExtend.childShapes, function(i, item) {
				if(Zdsoft.app.g('currentStepId') ===""){
					if(item.status && item.status===2){
						currentStepId=item.id;
					}
					that.instance.putStepStateMap(item.id,item.status);
				}else{
					if(item.status && item.status===2){
						if(item.id === Zdsoft.app.g('currentStepId')){
							currentStepId=item.id;
							that.instance.putStepStateMap(item.id,item.status);
						}else{
							//并行 设置其它的为4 特殊
							that.instance.putStepStateMap(item.id,4);
						}
					}else{
						that.instance.putStepStateMap(item.id,item.status);
					}
				}
				if(item.usertaskprincipalusernames)
					taskUserMap.put(item.id,item.usertaskprincipalusernames);
			});
		}
		
		Zdsoft.app.s("currentStepId", currentStepId);
		/* 
           if(Zdsoft.app.g('operation') ==="start"){
			if(model && model.childShapes){
				$.each(model.childShapes, function(i, item) {
					that.instance.putStepStateMap(item.resourceId,1);
				});
			}
			currentStepId="StartNoneEvent_-1";
		}                   
		*/
		if(model && model.childShapes){
			//StartNoneEvent(开始节点),UserTask(步骤节点),ParallelGateway(网关节点),SequenceFlow(连线节点),EndNoneEvent(结束节点)
			
			//先将连线的信息放入map
			$.each(model.childShapes, function(i, item) {
				//将当前步骤存入，供后面权限判断使用
				if(currentStepId && currentStepId === item.resourceId){
					/*
					if(Zdsoft.app.g('operation') ==="start"){
						item.properties.permission=0;
					}
					*/
					//修改赋予当前步骤自由流的权限
					if(Zdsoft.app.g('operation') ==="modify"){
						item.properties.permission=-1;
					}
					that.instance.setCurrentStep(item);
				}
				if(item.stencil.id =="SequenceFlow"){
					that.instance.putSequenceFlow(item);
				}
			});

			$.each(model.childShapes, function(i, item) {
				var _id=item.resourceId;
				//将步骤存入Map
				that.instance.setStepMap(item);
				if(item.stencil.id =="SequenceFlow"){
					return true;
				}
				
				var _index=parseInt(_id.substring(_id.lastIndexOf('_')+1),10);
				
				var taskUser="",taskUserId="";
				if(item.stencil.id === "UserTask"){
					 taskUser=taskUserMap.getValue(_id)?taskUserMap.getValue(_id):"";
					 if(item.properties.customidentitylinks)
						 taskUserId=item.properties.customidentitylinks.items[0].identitylinkexpr;
				}
				
				// 只接收需要的属性
				d = {
					id: _id,
					index: _index,
					type:item.stencil.id,
					taskUser:taskUser,
					taskUserId:taskUserId,
					top: item.bounds.upperLeft.y,
					left: item.bounds.upperLeft.x,
					name: item.properties.name,
					permission:item.properties.permission,
					to: item.outgoing,
					step:item.properties.step
				};
				// 特殊步骤处理, (开始结束)
				if(d.type === "StartNoneEvent"){
					d.cls = "wf-step wf-step-start";
				} else if(d.type === "EndNoneEvent"){
					d.cls = "wf-step wf-step-end";
				} else if(d.type === "ExclusiveGateway"){
					d.cls = "wf-node wf-condition";
				} else if(d.type === "ParallelGateway"){
					d.cls = "wf-node wf-gate";
				} else if(d.type === "UserTask"){
					//根据状态获取样式
					if(Zdsoft.app.g('operation') === "process" ||Zdsoft.app.g('operation') ==="modify"){
						if(that.instance.getStepStateMap(d.id)){
							d.cls=that.instance.getStateClass(that.instance.getStepStateMap(d.id));
						}
					}else{
						d.cls=that.instance.getStateClass('0');
					}
				}
				ret.steps.push(d);
				if(d.to) {
					pushConnect(d.id, d.to);
				}
				
			});
		}
		return ret;
	},
	set: function(data){
		var that = this,
			_data;
			addStartStep = function(){
				that.instance.addStep({
					id: 'StartNoneEvent_-1',
					name: U.lang('WF.START_NAME'),//L.START,
					taskUser:'',
					cls: "wf-step wf-step-start",
					index: -1,
					type:'StartNoneEvent'
				});
				
			},
			addEndStep = function(){
				var $step = that.instance.addStep({
					id: 'EndNoneEvent_0',
					name:U.lang('WF.END_NAME'),//L.END,
					taskUser:'',
					cls: "wf-step wf-step-end",
					index: 0,
					type:'EndNoneEvent'
				});
				var $container = $("#wf_designer_canvas");
				var position = {
					left: $container.outerWidth() - $step.outerWidth() - 80,
					top: $container.outerHeight() - $step.outerHeight() - 80
				};

				$step.css(position);
				that.instance.updateData('0', position);
			};
			sourceAndTargetSet= function(steps) {
				if(Zdsoft.app.g('operation') === "process" || Zdsoft.app.g('operation') ==="modify"){//过程性修改才按照状态去控制
					var permission=0;
					var currentStep=that.instance.getCurrentStep();
					if(currentStep)
						permission=currentStep.properties.permission;
					
					if(permission===-1){
						$.each(steps, function(index, step){
							//当前步骤 可以拖出 不可以拖进；已完成步骤 不可以拖出 不可以拖进；未完成步骤 可以拖进 不可以拖出
							//还要根据当前步骤的权限来判断
							if(that.instance.getStepStateMap(step.id)){
								var _state =that.instance.getStepStateMap(step.id);
								if(_state === 1){
									if(step.type ==='ParallelGateway'){
										$("#"+step.id).addClass("wf-gate-next");
									}
								}
								jsPlumb.setTargetEnabled(step.id, that.instance.getTargetEnabledMap(_state));
								jsPlumb.setSourceEnabled(step.id, that.instance.getSourceEnabledMap(_state));
							}
						});
					}else{
		 				$.each(steps, function(index, step){
							//当前步骤 可以拖出 不可以拖进；已完成步骤 不可以拖出 不可以拖进；未完成步骤 可以拖进 不可以拖出
							//还要根据当前步骤的权限来判断
		 					jsPlumb.setTargetEnabled(step.id, false);
							jsPlumb.setSourceEnabled(step.id, false);
						});
		 				var permissionArray = that.instance.tranfserPermission(permission);
		 				var _nextStepIds = that.instance.getSourceAndTargetRelation(currentStep.resourceId);
	 					if(_nextStepIds){
	 						var _nextStepIdArray=_nextStepIds.split(",");
	 						for (var i = 0; i < _nextStepIdArray.length; i++) {
	 							var step=that.instance.getStepMap(_nextStepIdArray[i]);
	 							//如果下一步是网关 继续找步骤 增加网关和步骤特殊样式
	 							if(step.stencil.id === "ParallelGateway"){
	 								$("#"+step.resourceId).addClass("wf-gate-next");
	 								var __nextStepIds = that.instance.getSourceAndTargetRelation(step.resourceId);
	 								var __nextStepIdArray=__nextStepIds.split(",");
	 								for (var j = 0; j < __nextStepIdArray.length; j++) {
	 									var _step=that.instance.getStepMap(__nextStepIdArray[j]);
	 									if(_step.stencil.id === "EndNoneEvent_0"){
	 										return true;
	 									}
	 									var _permission=_step.properties.permission;
	 					 				var _permissionArray = that.instance.tranfserPermission(_permission);
	 					 				if(_permissionArray[0])//remove
	 					 					$("#"+_step.resourceId).addClass("wf-step-next-remove");
	 					 				if(_permissionArray[1])//modify
	 					 					$("#"+_step.resourceId).addClass("wf-step-next-modify");	
	 					 				if(_permissionArray[0] && _permissionArray[1])
	 					 					$("#"+_step.resourceId).addClass("wf-step-next-remove-modify");	
	 								}
	 							}else if(step.stencil.id !== "EndNoneEvent_0"){
	 								var _permission=step.properties.permission;
 					 				var _permissionArray = that.instance.tranfserPermission(_permission);
 					 				if(_permissionArray[0])//remove
 					 					$("#"+step.resourceId).addClass("wf-step-next-remove");
 					 				if(_permissionArray[1])//modify
 					 					$("#"+step.resourceId).addClass("wf-step-next-modify");
 					 				if(_permissionArray[0] && _permissionArray[1])
 					 					$("#"+step.resourceId).addClass("wf-step-next-remove-modify");	
	 							}
	 							if(permissionArray[2]){//insert
	 								jsPlumb.setTargetEnabled(_nextStepIdArray[i], true);
	 							}
	 						}
	 					}
	 					if(permissionArray[2]){//insert
		 					jsPlumb.setSourceEnabled(currentStep.resourceId, true);
	 					}
					}
				}
			};
		var wfData=eval("("+data+")"); //包数据解析为json 格式 
		$("#workflow_name").val(wfData.name);
		parent.setWfTitle("流程设计器-"+wfData.name);
		$("#key").val(wfData.key);
		if(wfData.model.childShapes){
				_data = this.parseData(wfData);
				that.instance.addSteps(_data.steps);
				// 在步骤都加载完成后才连线
				that.instance.addConnects(_data.connects);
				//控制是否可以作为source和target
				sourceAndTargetSet(_data.steps);
		} else {
			// 增加开始和结束步骤
			addEndStep();
			addStartStep();
				
		}
		//开始和结束节点的一些限制
		jsPlumb.setTargetEnabled("StartNoneEvent_-1", false);
		jsPlumb.setSourceEnabled("EndNoneEvent_0", false);
	},
	
	add: function(data,options){
		return this.instance.addSteps(data, options, true);
	},

	getData: function(){
		var steps = [],
			sequenceFlowMap=new Map(),
			connectionTargetMap=new Map(),
			connectionSourceMap=new Map(),
			stepMap=new Map(),
			stepData = this.instance.getSteps(),
			connectData = this.instance.getConnects();
		
		for(var i in stepData){
			if(stepData.hasOwnProperty(i)){
				stepMap.put(stepData[i].id,stepData[i]);
			}
		}
		// 解析链接数据 一条线就相当于 source节点－链接－target节点
		for(var i in connectData){
			if(connectData.hasOwnProperty(i)){
				var sequenceFlowId = 'SequenceFlow_'+i;
				var _connects = connectData[i].split(",");
				//将该虚拟节点作为source节点的target 同时将target节点作为虚拟节点的target
				var _targetIds =sequenceFlowMap.getValue(_connects[0]);
				connectionSourceMap.put(_connects[0],_connects[1]);
				connectionTargetMap.put(_connects[1],_connects[0]);
				if(!(_targetIds)){
					_targetIds=[];
				}
				_targetIds.push({
                    'resourceId': sequenceFlowId
                });
				sequenceFlowMap.put(_connects[0],_targetIds);
				
				var sourceStep=stepMap.getValue(_connects[0]);
				var targetStep=stepMap.getValue(_connects[1]);
				//构造出一个SequenceFlow
				var sequenceProperties;
				//如果sourceStep为条件结点
				if(sourceStep.type ==="ExclusiveGateway"){
					var sequenceProperties= this.instance.getConditionMap(_connects[0]+_connects[1])
					if(sequenceProperties){
						sequenceProperties.overrideid=sequenceFlowId;
					}else{
						Ui.tip(L.WF.CONDITION_NEED, "warning");
						return false;
					}
				}else{
					sequenceProperties={//自定义属性 
						'overrideid': sequenceFlowId,
	                    'name': '',
	                    'documentation': '',
	                    'conditionsequenceflow': '',
	                    'defaultflow': 'None',
	                    'conditionalflow': 'None',
	                    'showdiamondmarker': false
                    };
				}
				var sequenceFlowData={
	                'resourceId': sequenceFlowId,
	                'stencil': {'id': 'SequenceFlow'},
	                'childShapes': [],
	                'outgoing': [{'resourceId': _connects[1]}],
	                'bounds': {
	                    'lowerRight': {
	                        'x': $("#"+targetStep.id).position().left,
	                        'y': $("#"+targetStep.id).position().top+(targetStep.type == 'ParallelGateway'?20:0)
	                    },
	                    'upperLeft': {
	                        'x': $("#"+sourceStep.id).position().left,
	                        'y': $("#"+sourceStep.id).position().top+(sourceStep.type == 'ParallelGateway'?20:0)
	                    }
	                },
	                'dockers': [
						{
						    'x': $("#"+sourceStep.id).width()/2,
						    'y': $("#"+sourceStep.id).height()/2
						},
						{
	                        'x': $("#"+targetStep.id).width()/2,
	                        'y': $("#"+targetStep.id).height()/2
	                    }
	                ],
	                'target': {
	                    'resourceId': _connects[1]
	                }
		        };  
		        steps.push(sequenceFlowData);
			}
			sequenceFlowData.properties =sequenceProperties;
		}
		// 解析步骤数据
		for(var i in stepData){
			if(stepData.hasOwnProperty(i)){
				if(stepData[i].type !=='EndNoneEvent'){
					if(!connectionSourceMap.containsKey(stepData[i].id)){
						return ;
					}
				}
				if(stepData[i].type !=='StartNoneEvent'){
					if(!connectionTargetMap.containsKey(stepData[i].id)){
						return ;
					}
				}
				//StartNoneEvent(开始节点),UserTask(步骤节点),ParallelGateway(网关节点),SequenceFlow(连线节点),EndNoneEvent(结束节点)
				var _properties={};
				if(stepData[i].type =='StartNoneEvent'){
					_properties={
						'overrideid': stepData[i].id,
				        'name': stepData[i].name
			        };
				}else if(stepData[i].type =='UserTask'){
					_properties={
						'overrideid': stepData[i].id,
				        'name': stepData[i].name,
	                    'step':stepData[i].step,//自定义属性 步骤
	                    'permission':stepData[i].permission//自定义属性 权限控制 -1 不控制；1、可插入步骤；2、可修改负责人；4、可删除步骤
	                };
					//如果没有指定人 就不加载该属性
					if(stepData[i].taskUserId !=""){
						var customidentitylinks={//自定义属性 人员
	                        "items": [
	                            {
	                                "identity_type": "user",
	                                "identity_link_type": "principal",
	                                "identitylinkexpr": stepData[i].taskUserId//stepData[i].taskUserId
	                            }
	                        ]
	                    };
	                    _properties.customidentitylinks =customidentitylinks;
					}
				}else if(stepData[i].type =='ParallelGateway' || stepData[i].type =='ExclusiveGateway'){
					_properties={
						'overrideid': stepData[i].id,
				        'name': stepData[i].name
	                };
				}else if(stepData[i].type =='EndNoneEvent'){
					_properties={
						'overrideid': stepData[i].id,
				        'name': stepData[i].name
	                };
				}
				
				var _outgoing =sequenceFlowMap.getValue(stepData[i].id);
				if(!_outgoing){
					_outgoing=[];
				}
				var _stepData=
				{
				    'resourceId': stepData[i].id,
				    'properties': _properties,
				    'stencil': {'id': stepData[i].type},
				    'childShapes': [],
				    'outgoing': _outgoing,
				    'bounds': {
				        'lowerRight': {
				            'x': $("#"+stepData[i].id).position().left+($("#"+stepData[i].id).width()),
				            'y': $("#"+stepData[i].id).position().top+($("#"+stepData[i].id).height())
				        },
				        'upperLeft': {
				            'x': $("#"+stepData[i].id).position().left,
				            'y': $("#"+stepData[i].id).position().top
				        }
				    },
				    'dockers': []
				};
				steps.push(_stepData);
			}
		}
		var wfdata={
			'resourceId': 'canvas',
	        'properties': {
	            'name': $("#workflow_name").val(),
	            'process_id': $("#key").val()//需要保存相对应的值
	        },
	        'stencil': {
	            'id': 'BPMNDiagram'
	        },
	        'childShapes':steps,
	        'bounds': {
	            'lowerRight': {
	                'x': 1000,
	                'y': 500
	            },
	            'upperLeft': {
	                'x': 0,
	                'y': 0
	            }
	        },
	        'stencilset': {
	            'namespace': 'http://b3mn.org/stencilset/bpmn2.0#',
	            'url': '../editor/stencilsets/bpmn2.0/bpmn2.0.json'
	        }
		}
		return wfdata;
	}
};

/**
 * 设计器容器内普通步骤选择器
 * @param {String} normalStepSelector 
 */
var normalStepSelector = ".wf-step:not(.wf-step-start,.wf-step-end,.wf-tips)";

var normalGateSelector = ".wf-gate";

var normalConditionSelector = ".wf-condition";

var nextGateSelector = ".wf-gate-next";

var nextRemoveStepSelector = ".wf-step-next-remove";
var nextModifyStepSelector = ".wf-step-next-modify";
var nextRMStepSelector = ".wf-step-next-remove-modify";
var uncompleteStepSelector = ".wf-step-uncomplete";
var flag=false;
var addDialog,editDialog;
var flowStepInfoMap = {};

/**
 * 控制器
 * @param object wfControll
 */	
var wfControll = {
	/**
	 * 编辑步骤
	 * @param {integer} processId 步骤ID
	 * @param {string} op 编辑类型
	 * @returns {undefined}
	 */
	editStep: function(id, op) {
		var isLoad = false,ins = processDesign.instance,dialog;
		if (id) {
			var index = id.substring(id.lastIndexOf('_')+1);
			var selectStep = ins.getData(index);
			Ui.closeDialog("d_step_edit");
			editDialog = Ui.dialog({
				id: "d_step_edit",
				padding: 0,
				title: selectStep.name,
				content: "<div id='addStepDiv' style='width:980px;left:300px;'></div>",
				top:10,
				init: function() {
					var param = {
						id:Zdsoft.app.g('id'),
						subsystemId:Zdsoft.app.g('subsystemId'),
						businessType:Zdsoft.app.g('businessType'),
						stepOperType:"edit",
						permission:selectStep.permission,
						taskUserId:selectStep.taskUserId,
						step:selectStep.step,
						stepName:selectStep.name,
						easyLevel:Zdsoft.app.g('easyLevel'),
						operation:Zdsoft.app.g('operation'),
						selectStepId:selectStep.id,
						selectStepInfo:flowStepInfoMap[id]
					};
					var url= Zdsoft.app.url("jbmp/editor/wf-stepInfo.action",param);
					load("#addStepDiv",url);
				},
				ok: function() {
					if(Zdsoft.app.g('businessType').indexOf("17") ===0
							|| (Zdsoft.app.g('businessType').indexOf("70") ===0 && Zdsoft.app.g('businessType') != "7009")){
						//wfControll.stepEdit4officeDocSave(id,index);
						saveWfStep(id,index);
						return false;
					}else{
						wfControll.stepEditSave(id,index);
					}
				}
			});
		}
	},
	// 增加步骤， 打开新增对话框
	addStep: function() {
		addDialog=Ui.dialog({
			id: "d_step_add",
			title:U.lang('WF.ADD_STEP'),
			content: "<div id='addStepDiv' style='width:980px;left:300px;'></div>",
			padding: 0,
			top:10,
			init: function() {
				var param = {
					id:Zdsoft.app.g('id'),
					subsystemId:Zdsoft.app.g('subsystemId'),
					businessType:Zdsoft.app.g('businessType'),
					stepOperType:"add",
					permission:0,
					currentStepId : Zdsoft.app.g('currentStepId'),
					easyLevel:Zdsoft.app.g('easyLevel'),
					operation:Zdsoft.app.g('operation')
				};
				var url= Zdsoft.app.url("jbmp/editor/wf-stepInfo.action",param);
				load("#addStepDiv",url);
			},
			ok: function() {
				if(Zdsoft.app.g('businessType').indexOf("17") ===0
						|| (Zdsoft.app.g('businessType').indexOf("70") ===0 && Zdsoft.app.g('businessType') != "7009")){
					saveWfStep();
					return false;
					//wfControll.stepAdd4officeDocSave();
				}else{
					wfControll.stepAddSave();
				}
			}
		});
	},
	//增加网关
	addGate: function() {	
		var index = processDesign.instance.getMaxIndex()+1;
		var gate ={
			id: 'ParallelGateway_'+index,
			name: 'gate',
			taskUser:'gate',
			cls:'wf-node wf-gate',
			index: index,
			type:'ParallelGateway'
		};
		var options={maxConnection:-1};//不限连接数
		var $gate=processDesign.add(gate,options);
		$gate.contextMenu("gate_context_menu", gateContextMenuSettings);
	},
	//增加条件节点
	addCondition: function() {	
		var index = processDesign.instance.getMaxIndex()+1;
		var condition ={
			id: 'ExclusiveGateway_'+index,
			name: 'condition',
			taskUser:'condition',
			cls:'wf-node wf-condition',
			index: index,
			type:'ExclusiveGateway'
		};
		var options={maxConnection:-1};//不限连接数
		var $condition=processDesign.add(condition,options);
		$condition.contextMenu("condition_context_menu", conditionContextMenuSettings);
	},
	/**
	 * 编辑条件
	 * @param {string} op 编辑类型
	 * @returns {undefined}
	 */
	editCondition: function(connection,sourceId,targetId,op) {
		var isLoad = false,ins = processDesign.instance,dialog;
		var id=sourceId+targetId;
		var sequenceFlow = ins.getConditionMap(id);
		if (sourceId && targetId) {
			Ui.closeDialog("d_condition_edit");
			dialog = Ui.dialog({
				id: "d_condition_edit",
				padding: 0,
				title: "条件设置",
				content: "<div id='conditionDiv' style='width:650px;left:300px;'></div>",
				top:100,
				init: function() {
					var param ;
					if(sequenceFlow)
						param = {id:id,stepOperType:"add",conditionName:sequenceFlow.name,conditions:sequenceFlow.conditionsequenceflow.expression.staticValue};
					else
						param = {id:id,conditionName:"",conditions:""};
					var url= Zdsoft.app.url("jbmp/editor/wf-condition.action",param);
					load("#conditionDiv",url);
				},
				ok: function() {
					if(!condition_checkDiv()){
						return false;
					}
					var conditionName=$("#conditionName").val();
					var conditions=formCondition.getConditions();
					if(!sequenceFlow){
						sequenceFlow={//自定义属性 
			                    'name': conditionName,//名称
			                    'documentation': '',
			                    'conditionsequenceflow': {
			                    	 'expression': {
			                         'type': "static",
			                         'staticValue': conditions//表达式
			                    	 }
			                    },
			                    'defaultflow': 'None',
			                    'conditionalflow': 'None',
			                    'showdiamondmarker': false
		                    };
					}else{
						sequenceFlow.name=conditionName;
						sequenceFlow.conditionsequenceflow.expression.staticValue=conditions;
					}
					ins.removeConnect(connection);
					ins.putConditionMap(id,sequenceFlow);
					ins.addConnectByStt(sourceId,targetId,"edit");
				}
			});
		}
	},
	// 移除步骤
	removeStep: function(id) {
		var name;
		var index=id.substring(id.lastIndexOf('_')+1);
		
		if (index && processDesign.instance.hasIndex(index)) {
			if(processDesign.instance.getData(index).type =='UserTask'){
				name = processDesign.instance.getData(index).name;
				Ui.confirm(U.lang('WF.DELETE_STEP_CONFIRM',{name:name}), function() {
					Ui.tip(U.lang('WF.DELETE_SUCCESS'), "success");
					processDesign.instance.removeStep(index,true);
					if(Zdsoft.app.g('operation') ==="edit" && Zdsoft.app.g('subsystemId') ==="70"){
						flowStepInfoMap[id] = "remove-"+id;
					}
				});
			}else if(processDesign.instance.getData(index).type =='ParallelGateway'){
				Ui.confirm(U.lang('WF.DELETE_GATE_CONFIRM'), function() {
					Ui.tip(U.lang('WF.DELETE_GATE_SUCCESS'), "success");
					processDesign.instance.removeStep(index,true);
					if(Zdsoft.app.g('operation') ==="edit" && Zdsoft.app.g('subsystemId') ==="70"){
						flowStepInfoMap[id] = "remove-"+id;
					}
				});
			}else if(processDesign.instance.getData(index).type =='ExclusiveGateway'){
				Ui.confirm(U.lang('WF.DELETE_CONDITION_CONFIRM'), function() {
					Ui.tip(U.lang('WF.DELETE_CONDITION_SUCCESS'), "success");
					processDesign.instance.removeStep(index,true);
					if(Zdsoft.app.g('operation') ==="edit" && Zdsoft.app.g('subsystemId') ==="70"){
						flowStepInfoMap[id] = "remove-"+id;
					}
				});
			}
			if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify"){
				//如果受权限控制 开放出前一步和后一步
				var targetIds =processDesign.instance.getSourceAndTargetRelation(id);
				var sourceIds =processDesign.instance.getTargetAndSourceRelation(id);
				if(targetIds){
					var targetIdArray=targetIds.split(",");
					for (var i = 0; i < targetIdArray.length; i++) {
						jsPlumb.setTargetEnabled(targetIdArray[i], true);
					}
				}
				if(sourceIds){
					var sourceIdArray=sourceIds.split(",");
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
	 * @returns {undefined}
	 */
	removeSelectedStep: function() {
		var selectedId = processDesign.instance.getSelect();
		if(selectedId)
			wfControll.removeStep(selectedId);
		else
			Ui.tip(U.lang('WF.PLEASE_SELECT_A_STEP'), "warning");
	},
	/**
	 * 保存链接与视图
	 * @param {object} data
	 * @param {function} callback
	 * @returns {undefined}
	 */
	saveFlow: function() {
		$("#saveButton").attr("class","abtn-gray");
		if(!flag){
			flag=true;
		}else{
			return;
		}
		$("#wf_designer_canvas").animate({scrollTop:0},0);
		var data = processDesign.getData();
		data =JSON.stringify(data);
		if(typeof(data) == "undefined"){
			Ui.tip(U.lang('WF.FLOW_CHECK_ERROR'), 'danger');
			$("#saveButton").attr("class","abtn-blue");
			flag=false;
			return;
		}
		//edit 和 process 调用的不同
		var url,param;
		if(Zdsoft.app.g('operation') ==="edit"){
			var flowStepInfo = "";
			for(var stepKey in flowStepInfoMap){
				if(flowStepInfo == ""){
					flowStepInfo = flowStepInfoMap[stepKey];
				}
				else{
					flowStepInfo = flowStepInfo + "+" + flowStepInfoMap[stepKey];
				}
			}
			
			 url = Zdsoft.app.url("jbmp/editor/wf-saveData.action"),
		 	 param = {
				id:Zdsoft.app.g('id'),
				actionUrl: Zdsoft.app.g('actionUrl'),
				deploy : Zdsoft.app.g('deploy'),
				businessType: Zdsoft.app.g('businessType'),
				flowStepInfo:flowStepInfo,
		 		jsonResult: data
		 	    
		 	 };
		}else{
			 url = Zdsoft.app.g('actionUrl'),
		 	 param = {
		 		id: Zdsoft.app.g('id'),
		 		taskHandlerSaveJson:Zdsoft.app.g('taskHandlerSaveJson'),
		 		jsonResult: data
		 	 };
		}
		$.post(url,param,function(result){
			if(result ==="success"){
				Ui.tip(U.lang('WF.FLOW_SAVE_SUCCESS'),'success');
				wfControll.closeDesinger(Zdsoft.app.g('callBackJs'),Zdsoft.app.g('easyLevel'),data);
			}else{
				Ui.tip(U.lang('WF.FLOW_SAVE_ERROR')+"("+result+")", 'danger');
				$("#saveButton").attr("class","abtn-blue");
				flag=false;
			}
		});
	},
	/**
	 * 修改步骤保存
	 * @returns {undefined}
	 */
	stepEditSave:function(id,index){
		if(!step_checkDiv()){
			return false;
		}
		var steps = [];
		var users=[];
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index,
				name =$("#stepName").val(),
				step =Zdsoft.app.g('businessType');
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id='UserTask_'+step+'_'+processId;
				var left = $("#"+_id).position().left;
				var top = $("#"+_id).position().top;
				steps.push({id: id, left:left,top:top,index:processId,type:'UserTask',name:name,step:step,permission:0,taskUser:users[i].name,taskUserId:users[i].id});
				processDesign.instance.updateStep(index, steps[i]);
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
			}
		}
	},
	/**
	 * 增加步骤保存
	 * @returns {undefined}
	 */
	stepAddSave:function(){
		if(!step_checkDiv()){
			return false;
		}
		var steps =[];
		var users=[];
		var index = processDesign.instance.getMaxIndex();
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = ++index,
				name =$("#stepName").val(),
				step =Zdsoft.app.g('businessType');
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
				var _id='UserTask_'+step+"_"+processId;
				steps.push({id: _id, index:processId,type:'UserTask',name:name,step:step,permission:0,taskUser:users[i].name,taskUserId:users[i].id});
				$steps = processDesign.add(steps[i]);
				// 初始化右键菜单
				$steps.contextMenu("step_context_menu", stepContextMenuSettings);
				// 双击编辑步骤
				$("#"+_id).addClass("wf-step-next-modify");
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
				//$("#"+steps[i].id).trigger("click");
			}
		}
	},
	/**
	 * 修改步骤保存-公文
	 * @returns {undefined}
	 */
	stepEdit4officeDocSave:function(id,index){

		if(!step_checkDiv()){
			return false;
		}
		var steps = [];
		var users=[];
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index,
				name =$("#stepDiv").find(".selected").text(),
				step =$("#step").val(),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
					}
				}
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id='UserTask_'+step+'_'+processId;
				var left = $("#"+_id).position().left;
				var top = $("#"+_id).position().top;
				steps.push({id: id, left:left,top:top,index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				if(Zdsoft.app.g('operation') ==="process" || Zdsoft.app.g('operation') ==="modify"){
					processDesign.instance.updateStep(index, steps[i]);
					$("#"+_id).addClass("wf-step-next-modify");
					if(processDesign.instance.getStepMap(_id)){
						
					}else{
						$("#"+_id).on("dblclick", function() {
							wfControll.editStep(this.id, "base");
						});
					}
					
				}else{
					processDesign.instance.updateStep(index, steps[i]);
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
			}
		}
		editDialog.close();
	},
	/**
	 * 增加步骤保存-公文
	 * @returns {undefined}
	 */
	stepAdd4officeDocSave:function(){
		if(!step_checkDiv()){
			return false;
		}
		var steps =[];
		var users=[];
		var index = processDesign.instance.getMaxIndex();
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		//并行 还是 单一
		var stepType= $("#stepType").find(".current").attr("value");
		if(stepType ==="single"){
			users.push({id: taskUserId, name: taskUser});
		}else{
			var singleUsers =taskUser.split(',');
			var singleUserIds =taskUserId.split(',');
			for (var i = 0; i < singleUsers.length; i++) {
				users.push({id: singleUserIds[i], name: singleUsers[i]});
			}
		}
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = ++index,
				name =$("#stepDiv").find(".selected").text(),
				step =$("#step").val(),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
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
				var _id='UserTask_'+step+"_"+processId;
				steps.push({id: _id, index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				$steps = processDesign.add(steps[i]);
				// 初始化右键菜单
				$steps.contextMenu("step_context_menu", stepContextMenuSettings);
				// 双击编辑步骤
				$("#"+_id).addClass("wf-step-next-modify");
				if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify"){
					$("#"+_id).on("dblclick", function() {
						wfControll.editStep(this.id, "base");
					});
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
				
				//$("#"+steps[i].id).trigger("click");
			}
		}
		addDialog.close();
	},
	/**
	 * 修改步骤保存-办公
	 * @returns {undefined}
	 */
	stepEditOfficeSave:function(id,index){

		if(!step_checkDiv()){
			return false;
		}
		var steps = [];
		var users=[];
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index,
				name =$("#stepName").val(),
				step =Zdsoft.app.g('businessType'),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
					}
				}
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id='UserTask_'+step+'_'+processId;
				
				//为办公流程步骤填充步骤信息:type-id-taskUserId-stepUserType+type-id-taskUserId-stepUserType
				if(Zdsoft.app.g('operation') ==="edit"){
					var stepUserIds = $("#stepUserIds").val();
					var stepUserType = $('input[name="stepUserType"]:checked').val();
					flowStepInfoMap[_id] = "edit-"+_id+"-"+stepUserIds+"-"+stepUserType;
				}
				
				var left = $("#"+_id).position().left;
				var top = $("#"+_id).position().top;
				steps.push({id: id, left:left,top:top,index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				if(Zdsoft.app.g('operation') ==="process" || Zdsoft.app.g('operation') ==="modify"){
					processDesign.instance.updateStep(index, steps[i]);
					$("#"+_id).addClass("wf-step-next-modify");
					if(processDesign.instance.getStepMap(_id)){
						
					}else{
						$("#"+_id).on("dblclick", function() {
							wfControll.editStep(this.id, "base");
						});
					}
					
				}else{
					processDesign.instance.updateStep(index, steps[i]);
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
			}
		}
		editDialog.close();
	},
	/**
	 * 修改步骤保存-办公 带微代码
	 * @returns {undefined}
	 */
	stepEditOfficeWithMcodeSave:function(id,index){

		if(!step_checkDiv()){
			return false;
		}
		var steps = [];
		var users=[];
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = index,
				name =$("#stepName").val(),
				step =$("#step").val(),
				//step =Zdsoft.app.g('businessType'),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
					}
				}
			// processId及name都不为空时，处理数据
			if (!isNaN(processId) && name !== "") {
				// 满足所有条件的加入数组
				var _id='UserTask_'+step+'_'+processId;
				
				//为办公流程步骤填充步骤信息:type-id-taskUserId-stepUserType+type-id-taskUserId-stepUserType
				if(Zdsoft.app.g('operation') ==="edit"){
					var stepUserIds = $("#stepUserIds").val();
					var stepUserType = $('input[name="stepUserType"]:checked').val();
					flowStepInfoMap[_id] = "edit-"+_id+"-"+stepUserIds+"-"+stepUserType;
				}
				
				var left = $("#"+_id).position().left;
				var top = $("#"+_id).position().top;
				steps.push({id: id, left:left,top:top,index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				if(Zdsoft.app.g('operation') ==="process" || Zdsoft.app.g('operation') ==="modify"){
					processDesign.instance.updateStep(index, steps[i]);
					$("#"+_id).addClass("wf-step-next-modify");
					if(processDesign.instance.getStepMap(_id)){
						
					}else{
						$("#"+_id).on("dblclick", function() {
							wfControll.editStep(this.id, "base");
						});
					}
					
				}else{
					processDesign.instance.updateStep(index, steps[i]);
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
			}
		}
		editDialog.close();
	},
	/**
	 * 增加步骤保存-办公
	 * @returns {undefined}
	 */
	stepAddOfficeSave:function(){
		if(!step_checkDiv()){
			return false;
		}
		var steps =[];
		var users=[];
		var index = processDesign.instance.getMaxIndex();
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = ++index,
				name =$("#stepName").val(),
				step =Zdsoft.app.g('businessType'),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
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
				var _id='UserTask_'+step+"_"+processId;
				
				//为办公流程步骤填充步骤信息:type-id-taskUserId-stepUserType+type-id-taskUserId-stepUserType
				if(Zdsoft.app.g('operation') ==="edit"){
					var stepUserIds = $("#stepUserIds").val();
					var stepUserType = $('input[name="stepUserType"]:checked').val();
					flowStepInfoMap[_id] = "add-"+_id+"-"+stepUserIds+"-"+stepUserType;
				}
				
				steps.push({id: _id, index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				$steps = processDesign.add(steps[i]);
				// 初始化右键菜单
				$steps.contextMenu("step_context_menu", stepContextMenuSettings);
				// 双击编辑步骤
				$("#"+_id).addClass("wf-step-next-modify");
				if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify"){
					$("#"+_id).on("dblclick", function() {
						wfControll.editStep(this.id, "base");
					});
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
				
				//$("#"+steps[i].id).trigger("click");
			}
		}
		addDialog.close();
	},
	/**
	 * 增加步骤保存-办公 带微代码
	 * @returns {undefined}
	 */
	stepAddOfficeWithMcodeSave:function(){
		if(!step_checkDiv()){
			return false;
		}
		var steps =[];
		var users=[];
		var index = processDesign.instance.getMaxIndex();
		var taskUser=$("#selTeacherName").val(),
			taskUserId=$("#selTeacherId").val();
		users.push({id: taskUserId, name: taskUser});
		var $steps;
		for (var i = 0; i < users.length; i++) {
			// 循环每行获取对应的processId及name值
			var processId = ++index,
				name =$("#stepName").val(),
				step =$("#step").val(),
				permission=0;
				if($("#span4").hasClass("ui-checkbox-current")){
					permission=-1;
				}else{
					if($("#span1").hasClass("ui-checkbox-current")){
						permission+=1;
					}
					if($("#span2").hasClass("ui-checkbox-current")){
						permission+=2;
					}
					if($("#span3").hasClass("ui-checkbox-current")){
						permission+=4;
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
				var _id='UserTask_'+step+"_"+processId;
				
				//为办公流程步骤填充步骤信息:type-id-taskUserId-stepUserType+type-id-taskUserId-stepUserType
				if(Zdsoft.app.g('operation') ==="edit"){
					var stepUserIds = $("#stepUserIds").val();
					var stepUserType = $('input[name="stepUserType"]:checked').val();
					flowStepInfoMap[_id] = "add-"+_id+"-"+stepUserIds+"-"+stepUserType;
				}
				
				steps.push({id: _id, index:processId,type:'UserTask',name:name,step:step,permission:permission,taskUser:users[i].name,taskUserId:users[i].id});
				$steps = processDesign.add(steps[i]);
				// 初始化右键菜单
				$steps.contextMenu("step_context_menu", stepContextMenuSettings);
				// 双击编辑步骤
				$("#"+_id).addClass("wf-step-next-modify");
				if(Zdsoft.app.g('operation') ==="process" ||Zdsoft.app.g('operation') ==="modify"){
					$("#"+_id).on("dblclick", function() {
						wfControll.editStep(this.id, "base");
					});
				}
				$(normalStepSelector+' i').dblclick(function(e){
				   e.stopPropagation();
				});
				
				//$("#"+steps[i].id).trigger("click");
			}
		}
		addDialog.close();
	},
	
	/**
	 * 清除所有链接
	 * @returns {undefined}
	 */
	clearAllConnect: function() {
		Ui.confirm(U.lang('WF.CLEAR_CONNECTION_CONFIRM'), function() {
			processDesign.instance.clearConnect();
		});
	},
	/**
	 * 计算画板的高度
	 * @returns {undefined}
	 */
	setCanvasHeight:function(){
		//工作流操作平台框架
		var sidHeight=$(window).height()-$('.workflow-header').height();
		var conHeight=sidHeight-$('.workflow-toolbar').height()-$('.workflow-content .tips').height();
		$('.workflow-canvas').height(conHeight-32);
	},
	/**
	 * 关闭设计器
	 */
	closeDesinger: function(callBackJs,easyLevel,jsonResult) {
		//setTimeout(function(){$(".wf-boxy-close",window.parent.document).trigger('click');},2000);
		setTimeout(function(){parent.closeOfficeWin(callBackJs,easyLevel,jsonResult);},2000);
	},
	/**
	 * 帮助
	 */
	help: function() {
		if(Zdsoft.app.g('businessType').indexOf("17") ===0){
			window.open(Zdsoft.app.url("jbmp/editor/wf-help-officedoc.action"),"流程设计-帮助");
		}else{
			window.open(Zdsoft.app.url("jbmp/editor/wf-help.action"),"流程设计-帮助");
		}
	}
};

/**
 * 步骤右键菜单配置
 */
var stepContextMenuSettings = {
	bindings: {
		// 编辑-基本信息
		"step_context_basic": function(elem, b) {
			wfControll.editStep(elem.id, "base");
		},
		// 删除步骤
		"step_context_del": function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	},
	onContextMenu:function(e){
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
	bindings: {
		// 删除步骤
		"step_context_del": function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	}
},
/**
 * 条件节点右键菜单配置对象
 */
conditionContextMenuSettings = {
	bindings: {
		// 删除条件
		"condition_context_del": function(elem, b) {
			wfControll.removeStep(elem.id);
		}
	}
};

$(function() {
	/**
	 * 设计器wrapper
	 * @param {object} $container  
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

	/**
	 * 数据通信对象
	 * @param {object} wfInter
	 */
	var wfInter = {
		/**
		 * 初始化流程链接
		 * @param {function} callback
		 * @returns {undefined}
		 */
		init: function(callback) {
			flowStepInfoMap = {};
			 var url = Zdsoft.app.url("jbmp/editor/wf-getData.action"),param = {id:id,subsystemId:subsystemId,instanceType:instanceType,currentStepId:currentStepId,operation:operation};
			 getJSON(url,param,function(data){
					// 生成节点与链接
			 		processDesign.set(data);
		 			// 只有编辑的情况下才可以操作下面的
					var stepSelector=normalStepSelector;
					var gateSelector=normalGateSelector;
					var conditionSelector=normalConditionSelector;
		 			if(operation === 'edit' || operation === 'start'){
		 				$("#buttonDiv").show();
		 				$("#addStepButton").show();
	 					$("#addGateButton").show();
	 					//公文流程设计的时候屏蔽条件节点
	 					if(subsystemId != "17"){
	 						$("#addConditionButton").show();
	 					}
	 					$("#removeStepButton").show();
	 					$("#moreOperationButton").show();
			 			// 初始化步骤右键菜单
						$(stepSelector).contextMenu("step_context_menu", stepContextMenuSettings);
						// 初始化网关右键菜单
						$(gateSelector).contextMenu("gate_context_menu", gateContextMenuSettings);
						// 初始化条件节点右键菜单
						$(conditionSelector).contextMenu("condition_context_menu", conditionContextMenuSettings);
						// 初始化通用右键菜单
						$container.contextMenu("common_context_menu", commonContextMenuSettings);
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
			 		}else if (operation ==='process' || operation ==='modify' ){
			 			gateSelector=nextGateSelector;
			 			$(gateSelector).contextMenu("gate_context_menu", gateContextMenuSettings);
			 			var _currentStep=processDesign.instance.getCurrentStep();
			 			if(_currentStep){
			 				var permission = _currentStep.properties.permission;
			 				if(permission == -1){
			 					stepSelector=uncompleteStepSelector;
			 					$(stepSelector).contextMenu("step_context_menu", stepContextMenuSettings);
			 					// 双击编辑步骤
								$container.on("dblclick", stepSelector, function() {
									wfControll.editStep(this.id, "base");
								});
								$("#buttonDiv").show();
			 					$("#addStepButton").show();
			 					$("#addGateButton").show();
			 					
			 					//办公修改
			 					if(subsystemId != "17"){
			 						$("#addConditionButton").show();
			 						$("#removeStepButton").show();
			 						$("#moreOperationButton").show();
			 					}
			 				}else{
			 					stepSelector=nextRMStepSelector;
				 				var permissionArray = processDesign.instance.tranfserPermission(permission);
				 				if(permissionArray[0] && subsystemId != "17"){//remove
				 					$("#buttonDiv").show();
				 					$("#removeStepButton").show();
				 				}
				 				if(permissionArray[2]){//insert
				 					$("#buttonDiv").show();
				 					$("#addStepButton").show();
				 					$("#addGateButton").show();
				 				}
			 					// 双击编辑步骤
								$container.on("dblclick", nextModifyStepSelector, function() {
									wfControll.editStep(this.id, "base");
								});
			 					$(nextModifyStepSelector).contextMenu("step_context_menu_modify", stepContextMenuSettings);

			 					$(nextRemoveStepSelector).contextMenu("step_context_menu_remove", stepContextMenuSettings);

			 					$(stepSelector).contextMenu("step_context_menu", stepContextMenuSettings);

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
					$container.on("click", normalStepSelector + ",.wf-step-active", function() {
						var id = parseInt(this.id.substring(this.id.lastIndexOf('_')+1), 10);
						processDesign.instance.select(id);
						if(!loadLock) {
							infoBar.loadContent(id);
							loadLock = true;
							setTimeout(function(){
								loadLock = false;
							}, 500)
						}
					});
					// 阻止连线部分 单机和双击事件
					$(normalStepSelector+' i').click(function(e){
					   e.stopPropagation();
					});
					$(normalStepSelector+' i').dblclick(function(e){
					   e.stopPropagation();
					});
					
					wfControll.setCanvasHeight();	
				 	callback && callback();
			});
		}
	};
	
	/**
	 * 右侧信息条
	 * @param function infoBar
	 */
	var infoBar = (function() {
		var $container = $("#wf_sidebar");
		var sidebarTpl="<div class='workflow-info'>";
			sidebarTpl +="<p class='tit'>基本信息</p><ul><li><span>当前步骤名称:</span><%=name%></li></ul>";
			sidebarTpl +="<p class='tit'>审核人</p><ul><li><span class='full'><%=taskUser%></span></li></ul>" ;
			sidebarTpl +="<p class='tit'>权限控制</p><ul><li><span class='full'><%=permissionStr%></span></li></ul></div>" ;
			sidebarTpl +="<p class='pl-20 mt-10'><a id='saveButton' href='javascript:;' class='abtn-blue' data-click='saveFlow'>保存流程图链接和视图</a></p>";
		return {
			$container: $container,
			setContent: function(content) {
				$container.html(content);
				var sidHeight=$(window).height()-$('.workflow-header').height();
				$('.workflow-sidebar').height(sidHeight);
			},
			/**
			 * 加载步骤信息
			 * @param {integer} id 步骤ID
			 * @param {function} callback 执行回调函数
			 * @returns {undefined}
			 */
			loadContent: function(id){
				var that = this;
				$container.waiting(U.lang('READ_INFO'));
				var _selectdData = processDesign.instance.getData(id);
				var permissionStr="";
				if(_selectdData.permission === -1){
					permissionStr+="可自定义流程";
				}else{
					var permissionArray =processDesign.instance.tranfserPermission(_selectdData.permission);
					if(permissionArray[2])
						permissionStr+="可插入步骤 ";
					else
						permissionStr+="不可插入步骤 ";
					if(permissionArray[1])
						permissionStr+="可修改负责人 ";
					else
						permissionStr+="不可修改负责人 ";
					if(permissionArray[0])
						permissionStr+="可略过步骤 ";
					else
						permissionStr+="不可略过步骤 ";
				}
				var _nodes=$.tmpl(sidebarTpl, {name:_selectdData.name,taskUser:_selectdData.taskUser,permissionStr:permissionStr});
				$container.stopWaiting();
				that.setContent(_nodes);
			},
			reset: function() {
				$container.html("<div class='workflow-guide'>工作流引导</div><p class='pl-20 mt-10'><a id='saveButton' href='javascript:;' class='abtn-blue' data-click='saveFlow'>保存流程图链接和视图</a></p>");		
				var sidHeight=$(window).height()-$('.workflow-header').height();
				$('.workflow-sidebar').height(sidHeight);
			}
		};
	})();
	
	var wfOpeator ={
		/**
		 * 重新加载视图
		 * @returns {undefined}
		 */
		reloadFlow: function() {
			processDesign.instance.clear();
			wfInter.init();
		}
	};
	/**
	 * 通用右键菜单配置对象
	 */
	commonContextMenuSettings = {
		bindings: {
			// 新增步骤
			// @Todo: 是否新增单一步骤
			"common_context_addstep": wfControll.addStep,
			// 刷新视图
			"common_context_reload": wfOpeator.reloadFlow,
			// 保存流程
			"common_context_save": wfControll.saveFlow
		}
	};
	// 执行初始化流程步骤操作
	wfInter.init();
});