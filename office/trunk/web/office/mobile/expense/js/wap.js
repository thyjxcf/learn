var wap = {
		init : function(contextPath, unitId, userId, userRealName){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/expense/expenseList.html";
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
//					if(dataType == WapConstants.DATA_TYPE_2){
//						$("#queryText").attr('placeholder', '请输入汇报内容');
//					}else{
//						$("#queryText").attr('placeholder', '请输入汇报人姓名');
//					}
//					$("#queryText").val('');
					
					if(isBack==WapConstants.BACK_LIST_GLAG_1){
						//返回第一次刷新列表还原值
						isBack = WapConstants.BACK_LIST_GLAG_0;
					}else{
						//之后刷新列表刷新搜索条件
//						storage.remove(WapConstants.LEAVE_TYPE_SEL);
						storage.remove(WapConstants.AUDIT_STATE_SEL);
					}
//					var leaveType = storage.get(WapConstants.LEAVE_TYPE_SEL);
					var applyStatus = storage.get(WapConstants.AUDIT_STATE_SEL);
//					if(!isNotBlank(leaveType)){
//						leaveType='';
//					}
//					
					if(!isNotBlank(applyStatus)){
						applyStatus=0;
					}
					
					wap._listService.loadListData(unitId, userId, dataType, searchStr, applyStatus);
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
					location.href = "expenseApplyEdit.html?dataType="+dataType;
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
					wap._listService.loadListData(unitId, userId, dataType, searchStr, applyStatus);
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
				//wapNetwork.doGetList(unitId, userId, dataType, searchStr);
			},
			
			initQuerys : function(dataType){
				//请假
//				var mcodes = JSON.parse(storage.get(WapConstants.LEAVE_TYPE_MCODES));
//				var leaveType = storage.get(WapConstants.LEAVE_TYPE_SEL);
				var state = storage.get(WapConstants.AUDIT_STATE_SEL);
//				var leaveTypeHtmlStr = '';
				
//				if(!isNotBlank(leaveType)){
//					leaveType='';
//				}
				
				if(!isNotBlank(state)){
					state=0;
				}
				
//				$.each(mcodes, function(index, json) {
//					leaveTypeHtmlStr += '<li  value="'+json.thisId+'"><span>'+json.content+'<i class="icon-img icon-current"></i></span></li>';
//				});
//				$("#leaveType").html(leaveTypeHtmlStr);
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
//				$('#leaveType li[value="'+leaveType+'"]').addClass('current');
				$('#auditStatus li[value="'+state+'"]').addClass('current');
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="expenseList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, searchStr, applyStatus){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr, applyStatus);
				});
				//var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(unitId, userId, dataType, searchStr, applyStatus);
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
				//清缓存
				wap._editService.clearCache();
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				wap._editService.initBind();//绑定
				wap._editService.initData(id);//初始化数据
				
				$("#cancelId").click(function(){
					wap._editService.clearCache();
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
				var expenseMoney = $("#expenseMoney").val();
				var expenseType = $("#expenseType").val();
				var detail = $("#detail").val();
				var expenseFlow = $("#expenseFlow").val();
				
				if(!isNotBlank(expenseFlow)){
					viewToast('请选择报销流程');
					return;
				}
				if(!isNotBlank(expenseMoney)){
					viewToast('报销金额不能为空');
					return;
				}else if(!validateInteger(expenseMoney)){
					viewToast("报销金额只能输入正整数");
					return;
				}
				
				if(!isNotBlank(expenseType)){
					viewToast('报销类别不能为空');
					return;
				}else if(getStringLen(expenseType) > 30){
					viewToast('报销类别不能超过30字符');
					return;
				}
				
				if(!isNotBlank(detail)){
					viewToast('费用明细不能为空');
					return;
				}else if(getStringLen(detail) > 250){
					viewToast('费用明细不能超过250字符');
					return;
				}
				
				
				
//				showMsg("确定提交？",function(){
					var userId = storage.get(Constants.USER_ID);
					$("#userId").val(userId);
					wapNetwork.doSave(dataType);
//				});
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

				$('#expenseFlow').scroller($.extend(opt['select'],opt['default']));			
				
				$('.dd-scroll').on('click','span',function(e){
					e.preventDefault();
					$(this).remove();
					
					wap._editService.deleteUser($(this).find(".my-userid-class").val());
				});
				
				$("#upFile").on('change',function(){
					var file = $(this).get(0).files[0];
					$("#fileName").val(file.name);
				});
			},
			
			//数据缓存起来
			saveDataCache : function(){
				
			},
			
			//清除缓存
			clearCache : function(){
				storage.remove(Constants.ADDRESS_SELECTED_USERIDS);
				storage.remove(Constants.ADDRESS_SELECTED_USERNAMES);
				
				storage.remove(WapConstants.ADDRESS_TYPE);
				storage.remove(WapConstants.ADDRESS_TYPE_1);
				storage.remove(WapConstants.ADDRESS_TYPE_2);
				storage.remove(WapConstants.ADDRESS_USERIDS_1);
				storage.remove(WapConstants.ADDRESS_USERNAMES_1);
				storage.remove(WapConstants.ADDRESS_USERIDS_2);
				storage.remove(WapConstants.ADDRESS_USERNAMES_2);
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