var wap = {
		init : function(contextPath, unitId, userId){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/evection/evectionList.html";
		},
		
		initList : function(){
			wap._listService.init();
		},
		
		initEdit : function(id){
			wap._editService.init();
		},
		
		initDetail : function(){
			wap._detailService.init();
		},
		
		initAudit : function(){
			wap._detailService.initAudit();
		},
		
		//list
		_listService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var isBack = Request.isBack;
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				var searchStr = "";
				
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					var dataType = $(this).val();
					if(dataType == WapConstants.DATA_TYPE_1){
						$("#queryText").attr('placeholder', '请输入汇报内容');
					}else{
						$("#queryText").attr('placeholder', '请输入汇报人姓名');
					}
					$("#queryText").val('');
					
					if(isBack==WapConstants.BACK_LIST_GLAG_1){
						//返回第一次刷新列表还原值
						isBack = WapConstants.BACK_LIST_GLAG_0;
					}else{
						//之后刷新列表刷新搜索条件
						storage.remove(WapConstants.LEAVE_TYPE_SEL);
						storage.remove(WapConstants.AUDIT_STATE_SEL);
					}
					var leaveType = storage.get(WapConstants.LEAVE_TYPE_SEL);
					var applyStatus = storage.get(WapConstants.AUDIT_STATE_SEL);
//					if(!isNotBlank(leaveType)){
//						leaveType='';
//					}
//					
					if(!isNotBlank(applyStatus)){
						applyStatus=0;
					}
					
					wap._listService.loadListData(unitId, userId, dataType, searchStr, leaveType, applyStatus);
				});
				
				//更多
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					var applyStatus = storage.get(WapConstants.AUDIT_STATE_SEL);
					
					if(!isNotBlank(applyStatus)){
						applyStatus=0;
					}
					
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr, applyStatus);
				});
				
				//新增
				$("#apply").click(function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					location.href = "evectionApplyEdit.html?dataType="+dataType;
				});
				
				//取消
				$("#cancelId").click(function(){
					try{
						mobile.back();
					}catch(e){
						location.href = "../officeMain.html";
					}
				});
				
				//筛选确认
				$("#querySubmit").click(function(){
					var leaveType = $("#leaveType .current").val();
					var applyStatus = $("#auditStatus .current").val();
					var dataType = $('.tab-wrap ul').children('.current').val();
					
					storage.set(WapConstants.LEAVE_TYPE_SEL, leaveType);
					storage.set(WapConstants.AUDIT_STATE_SEL, applyStatus);
					wap._listService.loadListData(unitId, userId, dataType, searchStr, leaveType, applyStatus);
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
				//wapNetwork.doGetList(unitId, userId, dataType, searchStr);
			},
			
			initQuerys : function(dataType){
				//出差
				var state = storage.get(WapConstants.AUDIT_STATE_SEL);
				if(!isNotBlank(state)){
					state=0;
				}
				//审核状态
		    	var auditHtmlStr ='';
				if(WapConstants.DATA_TYPE_2 == dataType){//审核
					auditHtmlStr += '<li class="default-current" value="0"><span>待审核<i class="icon-img icon-current"></i></span></li>';
					auditHtmlStr += '<li value="99"><span>已审核<i class="icon-img icon-current"></i></span></li>';
				}else{//申请
					auditHtmlStr += '<li value="'+Constants.APPLY_STATUS_0+'" class="default-current"><span>全部<i class="icon-img icon-current"></i></span></li>';
					auditHtmlStr += '<li value="'+Constants.APPLY_STATUS_1+'"><span>未提交<i class="icon-img icon-current"></i></span></li>';
					auditHtmlStr += '<li value="'+Constants.APPLY_STATUS_2+'"><span>审核中<i class="icon-img icon-current"></i></span></li>';
					auditHtmlStr += '<li value="'+Constants.APPLY_STATUS_3+'"><span>通过<i class="icon-img icon-current"></i></span></li>';
					auditHtmlStr += '<li value="'+Constants.APPLY_STATUS_4+'"><span>不通过<i class="icon-img icon-current"></i></span></li>';
				}
				$("#auditStatus").html(auditHtmlStr);
				
				//默认选中值
				$('#auditStatus li[value="'+state+'"]').addClass('current');
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="evectionList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, searchStr, leaveType, applyStatus){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr);
				});
				//var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(unitId, userId, dataType, searchStr, leaveType, applyStatus);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId, dataType, searchStr, applyStatus){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
//				var searchStr = storage.get(WapConstants.SEARCH_STR);
				var searchStr = '';
			    wapNetwork.doMoreList(unitId, userId, dataType, searchStr, applyStatus, ++WapPage.pageIndex);
			},
		},
		
		_editService : {
			
			init : function(){
				
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				wap._editService.initBind();//绑定
				wap._editService.initData(id);//初始化数据
				
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
			},
			
			save : function(dataType){
				if(!isActionable()){
					return false;
				}
				
				var place=$("#place").val();
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var goOutDays = $("#goOutDays").val();
				var remark = $("#remark").val();
				
				if(!isNotBlank(place)){
					viewToast('请输入出差地点');
					return;
				}
				if(!isNotBlank(startDate)){
					viewToast('请选择开始时间');
					return;
				}
				
				if(!isNotBlank(endDate)){
					viewToast('请选择结束时间');
					return;
				}
				
				if(validateDate(document.getElementById('startDate'), document.getElementById('endDate'))>0){
					viewToast('开始时间不能大于结束时间');
					return;
				}
				
				if(!isNotBlank(goOutDays)){
					viewToast('出差时间不能为空');
					return;
				}else{
					if(!validateFloat(goOutDays) || goOutDays<=0){
						viewToast('出差时间只能输入正数,且不超过一位小数');
						return;
					}
				}
				
				if(!isNotBlank(remark)){
					viewToast('出差事由不能为空');
					return;
				}else{
					if(getStringLen(remark) > 255){
						viewToast('出差事由不能超过255字符');
						return;
					}
				}
				
				var leaveFlow = $("#leaveFlow").val();
				
				if(!isNotBlank(leaveFlow)){
					viewToast('请选择出差流程');
					return;
				}
				
				var userId = storage.get(Constants.USER_ID);
				$("#userId").val(userId);
				wapNetwork.doSave(dataType);
			},
			
			initData : function(id){
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetApplyDetail(unitId, userId, id);
			},
			
			initBind : function(){
				var curr = new Date().getFullYear();
				var opt = {  
					'default': {
						theme: 'default',
						mode: 'scroller',
						display: 'modal',
						animate: 'fade'
					},
					'dateY': {
						preset: 'date',
						dateFormat: 'yyyy',
						defaultValue: new Date(new Date()),		
						//invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] },	
						onBeforeShow: function (inst) { 
							if(inst.settings.wheels[0].length>1)
							{
								inst.settings.wheels[0].pop();
								inst.settings.wheels[0].pop();
							}else{
								null
							}
						}
					},			
					'dateYM': {
						preset: 'date',
						dateFormat: 'yyyy-mm',
						defaultValue: new Date(new Date()),					
						onBeforeShow: function (inst) { 
							if(inst.settings.wheels[0].length>2)
							{
								inst.settings.wheels[0].pop();
							}else{
								null
							}
						}
					},
					'dateYMD': {
						preset: 'date',
						dateFormat: 'yyyy-mm-dd',
						defaultValue: new Date(new Date()),
						//invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] }
					},
					'datetime': {
						preset: 'datetime',
						minDate: new Date(2012, 3, 10, 9, 22),
						maxDate: new Date(2014, 7, 30, 15, 44),
						stepMinute: 5
					},
					'time': {
						preset: 'time'
					},
					'select': {
						preset: 'select'
					},
					'select-opt': {
						preset: 'select',
						group: true,
						width: 50
					}
				}

				$('#startDate').scroller($.extend(opt['dateYMD'],opt['default']));
				$('#endDate').scroller($.extend(opt['dateYMD'],opt['default']));
				$('#leaveFlow').scroller($.extend(opt['select'],opt['default']));			
				
				$("#upFile").on('change',function(){
					var file = $(this).get(0).files[0];
					$("#fileName").val(file.name);
				});
			},
			
			//数据缓存起来
			saveDataCache : function(){
				
			},
			
			
		},
		
		_detailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				//取消
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				wapNetwork.doGetDetail(dataType, id);
			},
			
			//审核操作
			initAudit : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var taskId = Request.taskId;
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				//取消
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				//通过
				$("#passAudit").click(function(){
					$("#pass").val('true');
					wap._detailService.saveAudit(Constants.APPLY_STATUS_3, dataType)
				});
				
				//不通过
				$("#failAudit").on('touchstart',function(){
//					$("#reasonDiv").hide();
					var reason = $("#reason").val();
					if(!isNotBlank(reason)){
						viewToast('审核意见不能为空');
						return;
					}
					
					$("#pass").val('false');
					wap._detailService.saveAudit(Constants.APPLY_STATUS_4, dataType)
				});
				
				wapNetwork.doGetAuditDetail(dataType, id, unitId, userId, taskId);
			},
			
			saveAudit : function(type, dataType){
				if(!isActionable()){
					return false;
				}
				
				var taskHandlerSaveJson = JSON.parse($("#taskHandlerSaveJson").val());
				
				taskHandlerSaveJson.comment.commentType = 1;
				if(Constants.APPLY_STATUS_4 == type){
					taskHandlerSaveJson.comment.textComment = $("#reason").val();
				}
				$("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSaveJson));
				
				wapNetwork.doAuditSave(dataType);
			},
			
		},
		
};  