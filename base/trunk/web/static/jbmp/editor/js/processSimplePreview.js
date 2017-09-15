(function(){
	var dashstyle="0";
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
		dashstyle="-1";
	}
	var jsPlumbDefaults = {
		
		Endpoint: "Blank",
		
		//Connector: "Flowchart",
		Connector : "Straight",
		ConnectionOverlays : [
			[ "Arrow", { 
				location: 1,
				id: "arrow",
	            length: 14,
	            foldback: 0.6
			} ]
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
			for(var i = 0; i < targetIds.length; i++) {
				//判断是否是连线 如果是，需要找到连线的outgoing
				var _resourceId=targetIds[i].resourceId;
				var sequenceFlow=that.instance.getSequenceFlow(_resourceId);
				var outgoing_resourceId =(sequenceFlow.outgoing)[0].resourceId;
				if(!outgoing_resourceId)
					outgoing_resourceId=_resourceId;
				//根据target判断连线状态 edit/uncomplete/complete
				var _connectType="uncomplete";//默认是可编辑的
					if(that.instance.getStepStateMap(sourceId)){
						if(that.instance.getStepStateMap(sourceId))
							_connectType =that.instance.getPreviewConnectType(that.instance.getStepStateMap(sourceId));
					}
				ret.connects.push(sourceId + "," + outgoing_resourceId+","+_connectType);
				that.instance.putSourceAndTargetRelation(sourceId,outgoing_resourceId);
				//保存条件信息
				if(sourceStep.stencil.id === "ExclusiveGateway"){
					that.instance.putConditionMap(sourceId+outgoing_resourceId,sequenceFlow.properties);
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
		
		//StartNoneEvent(开始节点),UserTask(步骤节点),ParallelGateway(网关节点),SequenceFlow(连线节点),EndNoneEvent(结束节点)
		//先将连线的信息放入map
		$.each(model.childShapes, function(i, item) {
			that.instance.setStepMap(item);
			if(item.stencil.id =="SequenceFlow"){
				that.instance.putSequenceFlow(item);
				return true;//$.each() continue return true; break return false;
			}
		});
		if(model && model.childShapes){
			$.each(model.childShapes, function(i, item) {
				if(item.stencil.id =="SequenceFlow"){
					return true;
				}
				var _id=item.resourceId;
				//将当前步骤存入，供后面权限判断使用
				if(currentStepId && currentStepId === _id){
					that.instance.setCurrentStep(item);
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
				}else if(d.type === "ParallelGateway"){
					d.cls = "wf-node wf-gate";
				} else if(d.type === "UserTask"){
					//根据状态获取样式
					if(that.instance.getStepStateMap(d.id)){
						d.cls=that.instance.getStateClass(that.instance.getStepStateMap(d.id));
					}else{
						//没有状态意味着是模型 全部设置为未完成
						d.cls=that.instance.getStateClass('1');
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
				$.each(steps, function(index, step){
					//预览的话 一概控制成不可拖出和拖进
					jsPlumb.setTargetEnabled(step.id,false);
					jsPlumb.setSourceEnabled(step.id,false);
				});
			};
		var wfData=eval("("+data+")"); //包数据解析为json 格式 
		$("#workflow_name").html(wfData.name);
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
	init:function(tips){
		$("#wf_designer_canvas").html("<p class='no-workflow'>"+tips+"</p>");
	}
};


$(function() {
	/**
	 * 设计器wrapper
	 * @param {object} $container  
	 */
	var $container = $("#wf_designer_canvas");
	/**
	 * 当前流程ID
	 * @param {integer} id 
	 */
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
	 * @param {object} wfInter
	 */
	var wfInter = {
		/**
		 * 初始化流程链接
		 * @param {function} callback
		 * @returns {undefined}
		 */
		init: function() {
			if (jsonResult == "" || jsonResult == null) {
				 var url = Zdsoft.app.url("jbmp/editor/wf-getPreviewData.action"),param = {id: id,subsystemId:subsystemId,instanceType:instanceType,currentStepId:currentStepId};
				 getJSON(url,param,function(data){
					wfInter.assembledData(data);
				});
			}else{
				 setTimeout(function(){wfInter.assembledData(jsonResult);},500);
				//jsonResult="{'model':"+jsonResult+"}";
				
			}
		},
		assembledData : function(data) {
			// 生成节点与链接
			if(data !==""){
				processDesign.set(data);
			}else{
				if(id == ""){
					processDesign.init("请选择一种流程");
				}else{
					processDesign.init("相关流程不存在哦");
				}
			}
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
			sidebarTpl +="<p class='tit'>经办角色</p><ul><li><span>审核人:</span><%=taskUser%></li></ul>" ;
			sidebarTpl +="<p class='tit'>权限控制</p><ul><li><span>不可插入步骤、不可修改负责人、不可略过步骤</span></li></ul></div>" ;
			sidebarTpl +="<p class='pl-20 mt-10'><a href='javascript:;' class='abtn-blue' data-click='saveFlow'>保存流程图链接和视图</a></p>";
		return {
			$container: $container,
			setContent: function(content) {
				$container.html(content);
				var sidHeight=$(window).height()-$('.workflow-header').height();
				var conHeight=sidHeight-$('.workflow-toolbar').height()-$('.workflow-content .tips').height()-32;
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
				var _nodes=$.tmpl(sidebarTpl, {name:_selectdData.name,taskUser:_selectdData.taskUser});
				$container.stopWaiting();
				that.setContent(_nodes);
			},
			reset: function() {
				$container.html("<div class='workflow-guide'>工作流引导</div><p class='pl-20 mt-10'><a href='javascript:;' class='abtn-blue' data-click='saveFlow'>保存流程图链接和视图</a></p>");
				var sidHeight=$(window).height()-$('.workflow-header').height();
				var conHeight=sidHeight-$('.workflow-toolbar').height()-$('.workflow-content .tips').height()-32;
				$('.workflow-sidebar').height(sidHeight);
			}
		};
	})();

	// 执行初始化流程步骤操作
	wfInter.init();
});