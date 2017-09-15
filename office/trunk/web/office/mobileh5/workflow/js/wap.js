var wap = {
		
		backList : function(){
			var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
			if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
				$(".html-window-close").click();
			}else{
				location.href="workflowMain.html";
			}
		},
		//审核列表
		initAuditList : function(){
			wap.initMainTab();
			wap._auditListService.init();
		},
		//我发起的列表
		initApplyList : function(){
			wap.initMainTab();
			wap._applyListService.init();
		},
		//审核查看详情
		initDetail : function(){
			wap._detailService.init();
		},
		//审核审批页面
		initAudit : function(){
			wap._detailService.initAudit();
		},
		
		//物品查看详情
		initGoodsDetail : function(){
			wap._goodsService._detailGoodsService.init();
		},
		
		//外出申请
		initGooutApply : function(){
			wap._applyService._gooutService.init();
		},
		//集体外出申请
		initGooutJtApply : function(){
			wap._applyService._gooutJtService.init();
		},
		//出差申请
		initEvectionApply : function(){
			wap._applyService._evectionService.init();
		},
		//报销
		initExpenseApply : function(){
			wap._applyService._expenseService.init();
		},
		//请假
		initLeaveApply : function(){
			wap._applyService._LeaveService.init();
		},
		//听课申请
		initAttendLectureApply : function(){
			wap._applyService._attendLectureService.init();
		},
		//物品领用list
		initGoodsApplyList : function(){
			wap._goodsService._appplyListService.init();
		},
		//物品领用申请
		initGoodsAppply : function(){
			wap._goodsService._appplyListDetailService.init();
		},
		//首页模块权限
		initMainTab : function(){
			var unitId = storage.get(Constants.UNIT_ID);
//			storage.remove(WapConstants.OFFICE_MAIN_MODEL);
//			storage.remove(WapConstants.OFFICE_CLASS_ARRAY);
			wapNetwork.initMainTab(unitId);
		},
		
		_auditListService : {
			
			init : function(){
				var Request = new UrlSearch();
				var isBack = Request.isBack;
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._auditListService.doRemoveCache();
				}
				var dataType = storage.get(WapConstants.SEARCH_DATA_TYPE);
				if(!isNotBlank(dataType)){
					dataType = WapConstants.DATA_TYPE_1;
				}
				$(".tab-wrap").find("li[value='"+dataType+"']").addClass("current");
				
				var type = storage.get(WapConstants.SEARCH_TYPE);
				if(!isNotBlank(type)){
					type=WapConstants.OFFICE_ALL;
				}
				var text = $('.show-all-layer ul li[value="'+type+'"]').addClass("current").text();
				$(".show-all").html('<font>'+text+'</font><span class="icon-img icon-filter"></span>');
				
				var searchStr = storage.get(WapConstants.SEARCH_NAME);
				if(!isNotBlank(searchStr)){
					searchStr=''
				}else{
					$("#queryText").val(searchStr);
				}
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wap._auditListService.initBind();
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					wap._auditListService.doRemoveCache();
					var dataType = $(this).val();
					$("#queryText").val('');
					
					if(isBack==WapConstants.BACK_LIST_GLAG_1){
						//返回第一次刷新列表还原值
						isBack = WapConstants.BACK_LIST_GLAG_0;
					}else{
						//重置搜索条件
						var defaultElem = $('.show-all-layer ul li[value="0"]');
						defaultElem.addClass('current').siblings('li').removeClass('current');
						$('.show-all-layer').hide();
						$('.show-all font').text(defaultElem.text());
					}
					
					var type = $('.show-all-layer ul').children('.current').val();
					wap._auditListService.loadListData(unitId, userId, dataType, type,searchStr);
				});
				
				//更多
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					var type = $('.show-all-layer ul').children('.current').val();
					wap._auditListService.bindClickMore(unitId, userId, dataType, type,searchStr);
				});
				
				$("#queryBtn").click(function(){
					if($("#showAllLayer").is(':visible')){
						closeShowAll();
					}
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					var searchStr = $("#queryText").val();
					var dataType = $('.tab-wrap ul').children('.current').val();
					var type = $('.show-all-layer ul').children('.current').val();
					wap._auditListService.loadListData(unitId, userId, dataType, type,searchStr);
				});
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					$("#queryBtn").click();
					return false;
				});

				$("#cancelId").click(function(){
					wap.backList();
				});
				//加载数据
//				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
//				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart'); 
				
				$('.show-all-layer ul li[value="'+type+'"]').addClass('current').siblings('li').removeClass('current');
				$('.show-all-layer ul li[value="'+type+'"]').trigger('click'); 
			},
			//清除筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_DATA_TYPE);
				storage.remove(WapConstants.SEARCH_TYPE);
				storage.remove(WapConstants.SEARCH_NAME);
			},
			//返回时加载数据
			backList:function(dataType){
				var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
				if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
					if($(".html-window-back").length > 0){//存在  则说明是列表页进来的  不需要返回的工作界面  否则需要调用$(".html-window-close").click();
						location.href="workflowAuditList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					}else{
						$(".html-window-close").click();
					}
				}else{
					location.href="workflowAuditList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
				}
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, type,searchStr){
				
				storage.set(WapConstants.SEARCH_DATA_TYPE,dataType);
				storage.set(WapConstants.SEARCH_TYPE,type);
				storage.set(WapConstants.SEARCH_NAME,searchStr);
				
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._auditListService.bindClickMore(unitId, userId, dataType,type, searchStr);
				});
				wapNetwork.doGetAuditList(unitId, userId, dataType, type,searchStr, 1);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId, dataType,type, searchStr){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchStr = storage.get(WapConstants.SEARCH_STR);
			    wapNetwork.doGetAuditList(unitId, userId, dataType, type,searchStr, ++WapPage.pageIndex);
			},
			
			initBind : function(){
				$('.show-all-layer ul li').click(function(){
					
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.tab-wrap ul').children('.current').val();
					var type = $(this).val();
					var searchStr = $("#queryText").val();
					wap._auditListService.loadListData(unitId, userId, dataType, type,searchStr);	
				});
			},
			
		},
	
		_applyListService : {
			
			init : function(){
				var Request = new UrlSearch();
				var isBack = Request.isBack;
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._applyListService.doRemoveCache();
				}
				
				var type = storage.get(WapConstants.SEARCH_TYPE);
				if(!isNotBlank(type)){
					type=WapConstants.OFFICE_ALL;
				}
				var text = $('.show-all-layer ul li[value="'+type+'"]').addClass("current").text();
				$(".show-all").html('<font>'+text+'</font><span class="icon-img icon-filter"></span>');
				
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				var searchStr = "";
				
				wap._applyListService.initBind();
				
				//更多
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					var type = $('.show-all-layer ul').children('.current').val();
					wap._applyListService.bindClickMore(unitId, userId, dataType, type,searchStr);
				});

				$("#cancelId").click(function(){
					wap.backList();
				});
				
				$("#queryBtn").click(function(){
					if($("#showAllLayer").is(':visible')){
						closeShowAll();
					}
					
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					var searchStr = $("#queryText").val();
					var type = $('.show-all-layer ul').children('.current').val();
					wap._auditListService.loadListData(userId, searchStr);
				});
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					$("#queryBtn").click();
					return false;
				});
				
				//加载数据
//				wap._applyListService.loadListData(userId, searchStr);
				$('.show-all-layer ul li[value="'+type+'"]').addClass('current').siblings('li').removeClass('current');
				$('.show-all-layer ul li[value="'+type+'"]').trigger('click'); 
			},
			//清除筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_TYPE);
			},
			//返回时加载数据
			backList:function(dataType){
				var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
				if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
					if($(".html-window-back").length > 0){//存在  则说明是列表页进来的  不需要返回的工作界面  否则需要调用$(".html-window-close").click();
						location.href="workflowApplyList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					}else{
						$(".html-window-close").click();
					}
				}else{
					location.href="workflowApplyList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
				}
			},
			
			//加载第一页数据
			loadListData : function(userId, searchStr){
				var businessType = $('.show-all-layer ul').children('.current').val();
				storage.set(WapConstants.SEARCH_TYPE,businessType);
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._applyListService.bindClickMore(userId, businessType,searchStr);
				});
				wapNetwork.doGetApplyList(userId, businessType,searchStr, 1);
			},
			
			//加载更多
			bindClickMore : function(userId, businessType,searchStr){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchStr = storage.get(WapConstants.SEARCH_STR);
			    wapNetwork.doGetApplyList(userId, businessType,searchStr, ++WapPage.pageIndex);
			},
			
			initBind : function(){
				$('.show-all-layer ul li').click(function(){
					$(this).addClass('current').siblings('li').removeClass('current');
					$('.show-all-layer').hide();
					$('.show-all font').text($(this).text());
					
					var userId = storage.get(Constants.USER_ID);
					var searchStr = $("#queryText").val();
					wap._applyListService.loadListData(userId, searchStr);	
				});
			},
		},
		
		_detailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var businessType = Request.businessType;
				var id = Request.id;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_0;
				}
				
				if(businessType=='' || typeof(businessType)=='undefined'){
					businessType = WapConstants.OFFICE_TEACHER_LEAVE;
				}
				
				//取消
				$("#cancelId").click(function(){
					if(WapConstants.DATA_TYPE_0 == dataType){
						wap._applyListService.backList(dataType);
					}else{
						wap._auditListService.backList(dataType);
					}
				});
				//删除
				$("#delete").click(function(){
					wapNetwork.doDeleteApply(dataType, id, businessType);					
				});
				wapNetwork.doGetDetail(dataType, id, businessType);
			},
			
			//审核操作
			initAudit : function(type){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var businessType = Request.businessType;
				var id = Request.id;
				var taskId = Request.taskId;
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				if(businessType=='' || typeof(businessType)=='undefined'){
					businessType = WapConstants.OFFICE_TEACHER_LEAVE;
				}
				//取消
				$("#cancelId").click(function(){
					wap._auditListService.backList(dataType);
				});
				
				//通过
				$("#passAudit").on('touchstart',function(){
					var reason = $("#passReason").val();
//					if(!isNotBlank(reason)){
//						viewToast('审核意见不能为空');
//						return;
//					}
					$("#pass").val('true');
					wap._detailService.saveAudit(Constants.APPLY_STATUS_3, dataType, businessType)
				});
				
				//不通过
				$("#failAudit").on('touchstart',function(){
//					$("#reasonDiv").hide();
					var reason = $("#failReason").val();
					if(!isNotBlank(reason)){
						viewToast('审核意见不能为空');
						return;
					}
					
					$("#pass").val('false');
					wap._detailService.saveAudit(Constants.APPLY_STATUS_4, dataType, businessType)
				});
				
				wapNetwork.doGetAuditDetail(dataType, id, unitId, userId, taskId, businessType);
			},
			
			saveAudit : function(type, dataType, businessType){
				if(!isActionable()){
					return false;
				}
				var taskHandlerSaveJson = JSON.parse($("#taskHandlerSaveJson").val());
				taskHandlerSaveJson.comment.commentType = 1;
				if(Constants.APPLY_STATUS_4 == type){
					taskHandlerSaveJson.comment.textComment = $("#failReason").val();
				}else{
					taskHandlerSaveJson.comment.textComment = $("#passReason").val();
				}
				$("#taskHandlerSaveJson").val(JSON.stringify(taskHandlerSaveJson));
				
				wapNetwork.doAuditSave(dataType, businessType);
			},
			
		},

		_goodsService : {
			_detailGoodsService : {
				init : function(){
					var Request = new UrlSearch();
					var id = Request.id;
					var dataType = Request.dataType;
					var businessType = Request.businessType;
					var unitId = storage.get(Constants.UNIT_ID);
					$('#back').click(function(){
						if(WapConstants.DATA_TYPE_0 == dataType){
							wap._applyListService.backList(dataType);
						}else{
							wap._auditListService.backList(dataType);
						}
					});
					
					wapNetwork.doGetDetail(dataType, id, businessType, unitId);
				},
			},
			
			_appplyListService : {
				
				init : function(){
					if(storage.get(WapConstants.GOOGS_AUDIT_MODE)==WapConstants.BOOLEAN_1)
						$("#more-layer").show();
					var Request = new UrlSearch();
					var isBack = Request.isBack;//是否查看返回
					if(isBack==null || typeof(isBack)=='undefined'){
						wap._goodsService._appplyListService.doRemoveCache();
					}
					var searchType = storage.get(WapConstants.SEARCH_TYPE);
					if(!isNotBlank(searchType)){
						searchType='';
					}
					var searchName = storage.get(WapConstants.SEARCH_NAME);
					if(!isNotBlank(searchName)){
						searchName='';
					}else{
						$("#searchName").val(searchName);
					}
					
					var userId = storage.get(Constants.USER_ID);
					var unitId = storage.get(Constants.UNIT_ID);
					
					//加载筛选项
					wapNetwork.doGetGoodsSelectList(userId, unitId, searchType, "");
					
					//初始化数据
					wapNetwork.doGetGoodsApplyList(userId, unitId, searchType, searchName);
					
					//搜索按钮
					$('#search-btn').click(function(){
						wap._goodsService._appplyListService.bindClickSearch(userId, unitId);
					});
					//筛选确认查询
					$('.filter-layer .opt .submit').click(function(e){
						wap._goodsService._appplyListService.bindClickSearchByCon(userId, unitId);
					});
					//筛选重置
					$('.filter-layer .opt .reset').click(function(e){
						$('.filter-layer li').removeClass('current');
						$('.condition-all').addClass('current');
					});
					
					//搜索事件监听
					$('.my-search-form').submit(function () { 
						wap._goodsService._appplyListService.bindClickSearch(userId, unitId);
						return false;
					});
					
					//更多
					$('.loading-more').bind('click', function(){
						wap._goodsService._appplyListService.bindClickMore(userId, unitId);
					});
					
					$('#cancelId').click(function(){
						wap.backList();
					});
					
				},
				
				//加载第一页数据
				loadListData : function(userId, unitId, searchType, searchName){
					$('#list').hide();
					$('#empty').hide();
					$('.ui-list').html('');
					$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
					$('.loading-more').bind('click', function(){
						wap._goodsService._appplyListService.bindClickMore(userId, unitId);
					});
					wapNetwork.doGetGoodsApplyList(userId, unitId, searchType, searchName, 1);
				},
				
				//加载更多
				bindClickMore : function(userId, unitId){
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
				    	return;
				    }
					var searchType = storage.get(WapConstants.SEARCH_TYPE);
					if(!isNotBlank(searchType)){
						searchType='';
					}
					var searchName = $('#searchName').val();
				    wapNetwork.doGetGoodsApplyList(userId, unitId, searchType, searchName, ++WapPage.pageIndex);
				},
				
				//搜索查询
				bindClickSearch : function(userId, unitId){
					var searchType = storage.get(WapConstants.SEARCH_TYPE);
					if(!isNotBlank(searchType)){
						searchType='';
					}
					var searchName = $('#searchName').val();
					storage.set(WapConstants.SEARCH_NAME,searchName);
					wap._goodsService._appplyListService.loadListData(userId, unitId, searchType, searchName);
					$("#searchName").blur();
				},
				
				//通过筛选查询数据
				bindClickSearchByCon : function(userId, unitId){
					var searchType = $(".goodsTypes .current").attr("id");
					storage.set(WapConstants.SEARCH_TYPE,searchType);
					
					var searchName = $('#searchName').val();
					wap._goodsService._appplyListService.loadListData(userId, unitId, searchType, searchName);
				},
				
				//清楚筛选缓存数据，恢复原样
				doRemoveCache : function(){
					storage.remove(WapConstants.SEARCH_TYPE);
					storage.remove(WapConstants.SEARCH_NAME);
					$('.filter-layer li').removeClass('current');
				},
				
			},
			
			_appplyListDetailService : {
				init : function(){
					var Request = new UrlSearch();
					var id = Request.id;
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					$('#back').click(function(){
						location.href = "goodsManagerApplyList.html?isBack=1";
					});
					$("#submit").click(function(){
						if(!isActionable()){
							return false;
						}
						var applyAmount=$("#applyAmount").val();
						var applyRemark=$("#applyRemark").val();
						if(!isNotBlank(applyAmount)){
							viewToast('申请数量不能为空或非数字类型');
							return;
						}else if (!validateInteger(applyAmount)){	
							viewToast("申请数量只能输入正整数");
							return;
						}
						if(getStringLen(applyAmount) > 8){
							viewToast('申请数量不能超过8位');
							return;
						}
						if(getStringLen(applyRemark) > 1000){
							viewToast('申请说明不能超过1000字符');
							return;
						}
//						showMsg("确定提交？",function(){
							wapNetwork.doGoodsSave(unitId,userId,id,applyAmount,applyRemark);
//						});
					});
					wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_GOODS);
				},
			},
		},
		_applyService : {
			_jumpDetail:function(id,businessType){
				var url="";
				if(WapConstants.OFFICE_TEACHER_LEAVE==businessType){//请假管理
					url="leaveDetail.html";
				}else if(WapConstants.OFFICE_GO_OUT == businessType){//外出管理
					url="goOutDetail.html";
				}else if(WapConstants.OFFICE_GO_OUT_JT == businessType){//集体外出管理
					url="goOutjtDetail.html";
				}else if(WapConstants.OFFICE_EVECTION == businessType){//出差管理
					url = "evectionDetail.html";
				}else if(WapConstants.OFFICE_EXPENSE == businessType){//报销管理
					url = "expenseDetail.html";
				}else if(WapConstants.OFFICE_ATTEND_LECTURE == businessType){//听课管理
					url = "attendLectureDetail.html";
				}else{
					wap.backList();
					return;
				}
				url+='?id='+id+'&businessType='+businessType+'&dataType=0';
				location.href=url;
			},
			_gooutService : {
				init : function(){
					
					var Request = new UrlSearch();
					var id = Request.id;
					//wap._applyService._gooutService.initBind();//绑定
					$("#startDate").val(new Date().Format("yyyy-MM-dd hh:mm"));
					wap._applyService._gooutService.initData(id);//初始化数据
					
					$("#leaveFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#startDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('H',startDate,endDate);
							if(num-parseInt(num)>0.5){
								$("#goOutDays").val(parseInt(num)+1+'.0');
							}else if(num-parseInt(num)>0){
								$("#goOutDays").val(parseInt(num)+0.5);
							}else{
								$("#goOutDays").val(parseInt(num)+'.0');
							}
						}
					});
					
					$("#endDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('H',startDate,endDate);
							if(num-parseInt(num)>0.5){
								$("#goOutDays").val(parseInt(num)+1+'.0');
							}else if(num-parseInt(num)>0){
								$("#goOutDays").val(parseInt(num)+0.5);
							}else{
								$("#goOutDays").val(parseInt(num)+'.0');
							}
						}
					});
					
					$("#cancelId").click(function(){
						wap.backList();
					});
					
					$("#save").click(function(){
						wap._applyService._gooutService.save();
					});
					
				},
				
				save : function(){
					if(!isActionable()){
						return false;
					}
					var startDate = $("#startDate").val();
					var endDate = $("#endDate").val();
					var goOutDays = $("#goOutDays").val();
					var remark = $("#remark").val();
					if(!isNotBlank(startDate)){
						viewToast('请选择开始时间');
						return;
					}else{
						$("#startDateVal").val(startDate+":00");
					}
					if(!isNotBlank(endDate)){
						viewToast('请选择结束时间');
						return;
					}else{
						$("#endDateVal").val(endDate+":00");
					}
					
					if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
						viewToast('开始时间不能大于结束时间');
						return;
					}
					
					if(!isNotBlank(goOutDays)){
						viewToast('外出时间不能为空或非数字类型');
						return;
					}else{
						if(goOutDays<=0){
							viewToast('外出时间必须为正数');
							return;
						}
						if(!validateFloat(goOutDays)){
							viewToast('外出时间只能包含一位小数');
							return;
						}
						var hoursTwo=goOutDays*2;
						if(hoursTwo-parseInt(hoursTwo)!=0){
							viewToast('外出时间小数位只可为0或5');
							return false;
						}
					}
					
					if(!isNotBlank(remark)){
						viewToast('外出事由不能为空');
						return;
					}else{
						if(getStringLen(remark) > 255){
							viewToast('外出事由不能超过255字符');
							return;
						}
					}
					
					var leaveFlow = $("#leaveFlow").val();
					var goOutType = $("#goOutType").val();
					
					if(!isNotBlank(leaveFlow)){
						viewToast('请选择外出流程');
						return;
					}
					if(!isNotBlank(goOutType)){
						viewToast('请选择外出类型');
						return;
					}
					var userId = storage.get(Constants.USER_ID);
					$("#userId").val(userId);
					wapNetwork.doSave(WapConstants.OFFICE_GO_OUT);
				},
				
				initData : function(id){
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_GO_OUT);
					
					var fileIndex=1;
					//附件
					if(isWeiXin()){
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
					}else{
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
					}
					$("#upFile"+fileIndex).on('change',function(){
						wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
					});
				},
				
				initBind : function(){
					var now = new Date();
					$('#startDate').mobiscroll().datetime({
						theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
					});
					
					$('#endDate').mobiscroll().datetime({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
				    });

					$('#goOutType,#leaveFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });		
					
					autoLayer('.abtn-send','#autoLayer',0,'.close','');		
					
					$("#upFile").on('change',function(){
						var file = $(this).get(0).files[0];
						$("#fileName").val(file.name);
					});
					
					//流程默认显示下一步处理人
					var flowId = $("#leaveFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
			},
			
			_evectionService : {
				
				init : function(){
					var Request = new UrlSearch();
					var id = Request.id;
					
//					wap._applyService._evectionService.initBind();//绑定
					$("#startDate").val(new Date().Format("yyyy-MM-dd"));
					wap._applyService._evectionService.initData(id);//初始化数据
					
					$("#leaveFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#startDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(validateDate(document.getElementById('startDate'), document.getElementById('endDate'))>0){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('D',startDate,endDate);
							$("#goOutDays").val(parseInt(num)+1);
						}
					});
					
					$("#endDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(validateDate(document.getElementById('startDate'), document.getElementById('endDate'))>0){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('D',startDate,endDate);
							$("#goOutDays").val(parseInt(num)+1);
						}
					});
					
					$("#cancelId").click(function(){
						wap.backList();
					});
					
					$("#save").click(function(){
						wap._applyService._evectionService.save();
					});
					
				},
				
				save : function(){
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
						viewToast('出差天数不能为空或非数字类型');
						return;
					}else{
						if(!validateFloat(goOutDays) || goOutDays<=0){
							viewToast('出差天数只能输入正数,且不超过一位小数');
							return;
						}
					}
					var num = countTimeLength('D',startDate,endDate);
					if(goOutDays>parseInt(num)+1){
						viewToast('出差天数不能超过开始结束的时间差');
						return;
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
					wapNetwork.doSave(WapConstants.OFFICE_EVECTION);
				},
				
				initData : function(id){
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_EVECTION);
					
					var fileIndex=1;
					//附件
					if(isWeiXin()){
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
					}else{
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
					}
					$("#upFile"+fileIndex).on('change',function(){
						wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
					});
				},
				
				initBind : function(){
					$('#startDate').mobiscroll().date({
						theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
					});
					
					$('#endDate').mobiscroll().date({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
				    });

					$('#leaveType,#leaveFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });		
					
					autoLayer('.abtn-send','#autoLayer',0,'.close','');		
					
					$("#upFile").on('change',function(){
						var file = $(this).get(0).files[0];
						$("#fileName").val(file.name);
					});
					
					//流程默认显示下一步处理人
					var flowId = $("#leaveFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
			},
			
			_expenseService : {
				init : function(){
					var Request = new UrlSearch();
					var id = Request.id;
					
					wap._applyService._expenseService.initBind();//绑定
					wap._applyService._expenseService.initData(id);//初始化数据
					
					$("#expenseFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#cancelId").click(function(){
						wap.backList();
					});
					
					$("#save").click(function(){
						wap._applyService._expenseService.save();
					});
					
				},
				
				save : function(){
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
					var ret=/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
					if(!isNotBlank(expenseMoney)){
						viewToast('报销金额不能为空或非数字类型');
						return;
					}else if(!ret.test(expenseMoney)){
						viewToast("报销金额只能输入正数,且不超过两位小数");
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
					
					
					
//					showMsg("确定提交？",function(){
						var userId = storage.get(Constants.USER_ID);
						$("#userId").val(userId);
						wapNetwork.doSave(WapConstants.OFFICE_EXPENSE);
//					});
				},
				
				initData : function(id){
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_EXPENSE);
					
					var fileIndex=1;
					//附件
					if(isWeiXin()){
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
					}else{
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
					}
					$("#upFile"+fileIndex).on('change',function(){
						wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
					});
				},
				
				initBind : function(){
					
					$('#expenseFlow,#leaveFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });	
					
					$("#upFile").on('change',function(){
						var file = $(this).get(0).files[0];
						$("#fileName").val(file.name);
					});
					
					//流程默认显示下一步处理人
					var flowId = $("#expenseFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
			},
			
			_gooutJtService:{
				
				init:function(){
					var Request=new UrlSearch();
					var id = Request.id;
					var type=Request.type;
					
					$("#cancelId").click(function(){
						wap.backList();
					});
					
					$("#gooutJtFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#goOutJtType").change(function(){
						var type = $(this).val();
						if("2"==type){
							$("#studentEx").hide();
							$("#teacherEx").show();
						}else{
							$("#studentEx").show();
							$("#teacherEx").hide();
						}
					});
					
					$("#isOrganization").change(function(){
						var type = $(this).val();
						if("true"==type){
							$("#organizationDiv").show();
						}else{
							$("#organizationDiv").hide();
						}
					});
					
					$("#startDate").val(new Date().Format("yyyy-MM-dd hh:mm"));
					wap._applyService._gooutJtService.initData(id,type);//初始化数据
					
					$("#startDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
								viewToast('开始时间不能大于结束时间');
								return;
							}
						}
					});
					$("#endDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
								viewToast('开始时间不能大于结束时间');
								return;
							}
						}
					});
					$("#save").click(function(){
						wap._applyService._gooutJtService.save();
					});
					
					//活动负责人
					$("#activityleaderDiv").click(function(){
						//已维护的数据缓存起来
						wap._applyService._gooutJtService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_1);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_1);

						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/goOutjtApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_2);//单选
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
						
						//参数
						storage.set(WapConstants.ADDRESS_TYPE_JT, WapConstants.ADDRESS_TYPE_JT_1);
					});
					//带队老师
					$("#leaderGroupDiv").click(function(){
						//已维护的数据缓存起来
						wap._applyService._gooutJtService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_2);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_2);

						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/goOutjtApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_2);//单选
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
						
						//参数
						storage.set(WapConstants.ADDRESS_TYPE_JT, WapConstants.ADDRESS_TYPE_JT_2);
					});
					//其他老师
					$("#otherTeacher").click(function(){
						//已维护的数据缓存起来
						wap._applyService._gooutJtService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_3);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_3);
						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/goOutjtApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//多选
						//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
						//参数
						storage.set(WapConstants.ADDRESS_TYPE_JT, WapConstants.ADDRESS_TYPE_JT_3);
					});
					//参与人员
					$("#partakePerson").click(function(){
						//已维护的数据缓存起来
						wap._applyService._gooutJtService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_4);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_4);
						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/goOutjtApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//多选
						//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
						//参数
						storage.set(WapConstants.ADDRESS_TYPE_JT, WapConstants.ADDRESS_TYPE_JT_4);
					});
					
				},
				//数据缓存起来
				saveDataCache : function(){
					var id = $("#id").val();
					var userId = $('#userId').val();
					var unitId = $('#unitId').val();
					var applyUserId = $('#applyUserId').val();
					var createTime = $('#createTime').val();
					var startDate = $('#startDate').val();
					var endDate = $('#endDate').val();
					var goOutJtType = $('#goOutJtType').val();
					var organize  = $('#organize').val();
					var activityNumber= $('#activityNumber').val();
					var place = $('#place').val();
					var scontent  = $('#scontent').val();
					var vehicle  = $('#vehicle').val();
					var isDrivinglicence = $('#isDrivinglicence').val();
					var isOrganization = $('#isOrganization').val();
					var traveUnit = $('#traveUnit').val();
					var travelinkPerson = $('#travelinkPerson').val();
					var travelinkPhone  = $('#travelinkPhone').val();
					var isInsurance = $('#isInsurance').val();
					var activityleaderId  = $('#activityleaderId').val();
					var activityleaderName = $('#activityleaderName').val();
					var activityleaderPhone  = $('#activityleaderPhone').val();
					var leadGroupId= $('#leadGroupId').val();
					var leadGroupName= $('#leadGroupName').val();
					var leadGroupPhone= $('#leadGroupPhone').val();
					var otherTeacherId= $('#otherTeacherId').val();
					var otherTeacherNames= $('#otherTeacherNames').html();
					var activityIdeal= $('#activityIdeal').val();
					var saftIdeal= $('#saftIdeal').val();
					var tcontent= $('#tcontent').val();
					var partakePersonId= $('#partakePersonId').val();
					var partakePersonNames= $('#partakePersonNames').html();
					var gooutJtFlow= $('#gooutJtFlow').val();
					
					storage.set(WapConstants.LEAVE_EDIT_ID, id);
					storage.set(WapConstants.LEAVE_EDIT_USERID, userId);
					storage.set(WapConstants.LEAVE_EDIT_UNITID, unitId);
					storage.set(WapConstants.LEAVE_EDIT_CREATETIME, applyUserId);
					storage.set(WapConstants.LEAVE_EDIT_APPLYUSERID, createTime);
					storage.set(WapConstants.LEAVE_EDIT_START_DATE, startDate);
					storage.set(WapConstants.LEAVE_EDIT_END_DATE, endDate);
					
					storage.set(WapConstants.LEAVE_EDIT_END_GOOUTJTTYPE, goOutJtType);
					storage.set(WapConstants.LEAVE_EDIT_END_ORGANIZE, organize);
					storage.set(WapConstants.LEAVE_EDIT_END_ACTIVITYNUMBER, activityNumber);
					storage.set(WapConstants.LEAVE_EDIT_END_PLACE, place);
					storage.set(WapConstants.LEAVE_EDIT_END_SCONTENT, scontent);
					storage.set(WapConstants.LEAVE_EDIT_END_VEHICLE, vehicle);
					storage.set(WapConstants.LEAVE_EDIT_END_ISDRIVINGLICENCE, isDrivinglicence);
					storage.set(WapConstants.LEAVE_EDIT_END_ISORGANIZATION, isOrganization);
					storage.set(WapConstants.LEAVE_EDIT_END_TRAVEUNIT, traveUnit);
					storage.set(WapConstants.LEAVE_EDIT_END_TRAVELINKPERSON, travelinkPerson);
					storage.set(WapConstants.LEAVE_EDIT_END_TRAVELINKPHONE, travelinkPhone);
					storage.set(WapConstants.LEAVE_EDIT_END_ISINSURANCE, isInsurance);
					storage.set(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERID, activityleaderId);
					storage.set(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERNAME, activityleaderName);
					storage.set(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERPHONE, activityleaderPhone);
					storage.set(WapConstants.LEAVE_EDIT_END_LEADGROUPID, leadGroupId);
					storage.set(WapConstants.LEAVE_EDIT_END_LEADGROUPNAME, leadGroupName);
					storage.set(WapConstants.LEAVE_EDIT_END_LEADGROUPPHONE, leadGroupPhone);
					storage.set(WapConstants.LEAVE_EDIT_END_OTHERTEACHERID, otherTeacherId);
					storage.set(WapConstants.LEAVE_EDIT_END_OTHERTEACHERNAMES, otherTeacherNames);
					storage.set(WapConstants.LEAVE_EDIT_END_ACTIVITYIDEAL, activityIdeal);
					storage.set(WapConstants.LEAVE_EDIT_END_SAFTIDEAL, saftIdeal);
					storage.set(WapConstants.LEAVE_EDIT_END_TCONTENT, tcontent);
					storage.set(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONID, partakePersonId);
					storage.set(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONNAMES, partakePersonNames);
					storage.set(WapConstants.LEAVE_EDIT_END_GOOUTJTFLOW, gooutJtFlow);
				},
				//清除缓存
				clearCache : function(){
					storage.remove(Constants.ADDRESS_SELECTED_USERIDS);
					storage.remove(Constants.ADDRESS_SELECTED_USERNAMES);
					
					storage.remove(WapConstants.ADDRESS_TYPE_JT);
					storage.remove(WapConstants.ADDRESS_TYPE_JT_1);
					storage.remove(WapConstants.ADDRESS_TYPE_JT_2);
					storage.remove(WapConstants.ADDRESS_TYPE_JT_3);
					storage.remove(WapConstants.ADDRESS_TYPE_JT_4);
					storage.remove(WapConstants.ADDRESS_USERIDS_JT_1);
					storage.remove(WapConstants.ADDRESS_USERNAMES_JT_1);
					storage.remove(WapConstants.ADDRESS_USERIDS_JT_2);
					storage.remove(WapConstants.ADDRESS_USERNAMES_JT_2);
					storage.remove(WapConstants.ADDRESS_USERIDS_JT_3);
					storage.remove(WapConstants.ADDRESS_USERNAMES_JT_3);
					storage.remove(WapConstants.ADDRESS_USERIDS_JT_4);
					storage.remove(WapConstants.ADDRESS_USERNAMES_JT_4);
					
					storage.remove(Constants.LEAVE_TYPES);
					storage.remove(Constants.LEAVE_FLOWS);
					
					storage.remove(WapConstants.LEAVE_EDIT_ID);
					storage.remove(WapConstants.LEAVE_EDIT_USERID);
					storage.remove(WapConstants.LEAVE_EDIT_UNITID);
					storage.remove(WapConstants.LEAVE_EDIT_CREATETIME);
					storage.remove(WapConstants.LEAVE_EDIT_APPLYUSERID);
					storage.remove(WapConstants.LEAVE_EDIT_START_DATE);
					storage.remove(WapConstants.LEAVE_EDIT_END_DATE);
					storage.remove(WapConstants.LEAVE_EDIT_END_GOOUTJTTYPE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ORGANIZE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ACTIVITYNUMBER);
					storage.remove(WapConstants.LEAVE_EDIT_END_PLACE);
					storage.remove(WapConstants.LEAVE_EDIT_END_SCONTENT);
					storage.remove(WapConstants.LEAVE_EDIT_END_VEHICLE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ISDRIVINGLICENCE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ISORGANIZATION);
					storage.remove(WapConstants.LEAVE_EDIT_END_TRAVEUNIT);
					storage.remove(WapConstants.LEAVE_EDIT_END_TRAVELINKPERSON);
					storage.remove(WapConstants.LEAVE_EDIT_END_TRAVELINKPHONE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ISINSURANCE);
					storage.remove(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERID);
					storage.remove(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERNAME);
					storage.remove(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERPHONE);
					storage.remove(WapConstants.LEAVE_EDIT_END_LEADGROUPID);
					storage.remove(WapConstants.LEAVE_EDIT_END_LEADGROUPNAME);
					storage.remove(WapConstants.LEAVE_EDIT_END_LEADGROUPPHONE);
					storage.remove(WapConstants.LEAVE_EDIT_END_OTHERTEACHERID);
					storage.remove(WapConstants.LEAVE_EDIT_END_OTHERTEACHERNAMES);
					storage.remove(WapConstants.LEAVE_EDIT_END_ACTIVITYIDEAL);
					storage.remove(WapConstants.LEAVE_EDIT_END_SAFTIDEAL);
					storage.remove(WapConstants.LEAVE_EDIT_END_TCONTENT);
					storage.remove(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONID);
					storage.remove(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONNAMES);
					storage.remove(WapConstants.LEAVE_EDIT_END_GOOUTJTFLOW);
				},
				
				save : function(){
					if(!isActionable()){
						return false;
					}
					var startDate = $("#startDate").val();
					var endDate = $("#endDate").val();
					var goOutJtType=$("#goOutJtType").val();
					if(!isNotBlank(startDate)){
						viewToast('请选择开始时间');
						return;
					}else{
						$("#startDateVal").val(startDate+":00");
					}
					if(!isNotBlank(endDate)){
						viewToast('请选择结束时间');
						return;
					}else{
						$("#endDateVal").val(endDate+":00");
					}
					
					if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
						viewToast('开始时间不能大于结束时间');
						return;
					}
					if(isNotBlank(goOutJtType)){
						if(goOutJtType=="1"){
							var organize=$("#organize").val();
							var activityNumber=$("#activityNumber").val();
							var place=$("#place").val();
							var scontent=$("#scontent").val();
							var vehicle=$("#vehicle").val();
							//var isDrivinglicence=$("isDrivinglicence").val();
							var isOrganization=$("#isOrganization").val();
							var traveUnit=$("#traveUnit").val();
							var travelinkPerson=$("#travelinkPerson").val();
							var travelinkPhone=$("#travelinkPhone").val();
							//var isInsurance=$("isInsurance").val();
							var activityleaderId=$("#activityleaderId").val();
							var activityleaderPhone=$("#activityleaderPhone").val();
							var leadGroupId=$("#leadGroupId").val();
							var leadGroupPhone=$("#leadGroupPhone").val();
							//var activityIdeal=$("activityIdeal").val();
							//var saftIdeal=$("saftIdeal").val();
							if(!isNotBlank(organize)){
								viewToast('组织活动的年级或班级不能为空');
								return;
							}else{
								if(getStringLen(organize) > 20){
									viewToast('组织活动的年级或班级不能超过20字符');
									return;
								}
							}
							if(!isNotBlank(activityNumber)){
								viewToast('活动人数不能为空或非数字类型');
								return;
							}else{
								if(getStringLen(activityNumber) >4){
									viewToast('活动人数不能超过9999人');
									return;
								}
							}
							if(!isNotBlank(place)){
								viewToast('活动地点不能为空');
								return;
							}else{
								if(getStringLen(place) > 50){
									viewToast('活动地点不能超过50字符');
									return;
								}
							}
							if(!isNotBlank(scontent)){
								viewToast('活动内容不能为空');
								return;
							}else{
								if(getStringLen(scontent) > 200){
									viewToast('活动内容不能超过200字符');
									return;
								}
							}
							if(!isNotBlank(vehicle)){
								viewToast('交通工具不能为空');
								return;
							}else{
								if(getStringLen(vehicle) > 50){
									viewToast('交通工具不能超过50字符');
									return;
								}
							}
							
							if(isNotBlank(isOrganization) && isOrganization == 'true'){
								if(!isNotBlank(traveUnit)){
									viewToast('旅行社单位不能为空');
									return;
								}else{
									if(getStringLen(traveUnit) > 20){
										viewToast('旅行社单位不能超过20字符');
										return;
									}
								}
								if(!isNotBlank(travelinkPerson)){
									viewToast('旅行社联系人不能为空');
									return;
								}else{
									if(getStringLen(travelinkPerson) > 20){
										viewToast('旅行社联系人不能超过20字符');
										return;
									}
								}
								if(!isNotBlank(travelinkPhone)){
									viewToast('旅行社联系人手机号不正确，请重新维护');
									return;
								}else{
									if(getStringLen(travelinkPhone) > 20){
										viewToast('旅行社联系人手机号不能超过20数字');
										return;
									}
								}
							}
							if(!isNotBlank(activityleaderId)){
								viewToast('活动负责人不能为空');
								return;
							}
							if(!isNotBlank(activityleaderPhone)){
								viewToast('活动负责人手机号不正确，请重新维护');
								return;
							}else{
								if(getStringLen(activityleaderPhone) > 20){
									viewToast('活动负责人手机号不能超过20数字');
									return;
								}
							}
							if(!isNotBlank(leadGroupId)){
								viewToast('带队老师不能为空');
								return;
							}
							if(!isNotBlank(leadGroupPhone)){
								viewToast('带队老师手机号不正确，请重新维护');
								return;
							}else{
								if(getStringLen(leadGroupPhone) > 20){
									viewToast('带队老师手机号不能超过20数字');
									return;
								}
							}
							
						}else if(goOutJtType=="2"){
							var tcontent=$("#tcontent").val();
							if(!isNotBlank(tcontent)){
								viewToast('外出内容不能为空');
								return;
							}else{
								if(getStringLen(tcontent) > 200){
									viewToast('外出内容不能超过200字符');
									return;
								}
							}
						}
					}
					
					var gooutJtFlow = $("#gooutJtFlow").val();
					
					if(!isNotBlank(gooutJtFlow)){
						viewToast('请选择外出流程');
						return;
					}
					var userId = storage.get(Constants.USER_ID);
					$("#userId").val(userId);
					wapNetwork.doSave(WapConstants.OFFICE_GO_OUT_JT);
				},
				initData : function(id, type){
					if("2" == type){
						var jtTypeMcodes = storage.get(WapConstants.JT_TYPES);
						if(isNotBlank(jtTypeMcodes)){
							jtTypeMcodes = JSON.parse(jtTypeMcodes);
							$("#goOutJtType").html('');
				    		if(jtTypeMcodes.length > 0){
				    			var htmlStr = '';
				    			$.each(jtTypeMcodes, function(index, json) {
			    					htmlStr += '<option value="'+json.type+'">'+json.typeName+'</option>';
				    			});
				    			$("#goOutJtType").html(htmlStr);
				    		}
						}else{
							$("#goOutJtType").html(htmlStr);
						}
						
			    		$("#isDrivinglicence").html('<option value="false">否</option> <option value="true">是</option>');
			    		$("#isOrganization").html('<option value="false">否</option> <option value="true">是</option>');
			    		$("#isInsurance").html('<option value="false">否</option> <option value="true">是</option>');
			    		$("#activityIdeal").html('<option value="false">否</option> <option value="true">是</option>');
			    		$("#saftIdeal").html('<option value="false">否</option> <option value="true">是</option>');
			    		//请假流程
			    		var flowArray = storage.get(WapConstants.JT_FLOWS);
			    		if(isNotBlank(flowArray)){
			    			flowArray = JSON.parse(flowArray);
			    			$("#gooutJtFlow").html('');
				    		if(flowArray.length > 0){
				    			var htmlStr = '';
				    			$.each(flowArray, function(index, json) {
				    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
				    			});
				    			$("#gooutJtFlow").html(htmlStr);
				    		}
						}else{
							$("#gooutJtFlow").html('');
						}
			    		
						
						
						//获取缓存中之前已维护的数据
						var id = storage.get(WapConstants.LEAVE_EDIT_ID);
						var userId = storage.get(WapConstants.LEAVE_EDIT_USERID);
						var unitId = storage.get(WapConstants.LEAVE_EDIT_UNITID);
						var createTime =storage.get(WapConstants.LEAVE_EDIT_CREATETIME);
						var applyUserId =storage.get(WapConstants.LEAVE_EDIT_APPLYUSERID);
						var startDate =storage.get(WapConstants.LEAVE_EDIT_START_DATE);
						var endDate =storage.get(WapConstants.LEAVE_EDIT_END_DATE);
						var gooOutJtType =storage.get(WapConstants.LEAVE_EDIT_END_GOOUTJTTYPE);
						var organize =storage.get(WapConstants.LEAVE_EDIT_END_ORGANIZE);
						var activityNumber =storage.get(WapConstants.LEAVE_EDIT_END_ACTIVITYNUMBER);
						var place =storage.get(WapConstants.LEAVE_EDIT_END_PLACE);
						var scontent =storage.get(WapConstants.LEAVE_EDIT_END_SCONTENT);
						var vehicle =storage.get(WapConstants.LEAVE_EDIT_END_VEHICLE);
						var isDrivinglicence =storage.get(WapConstants.LEAVE_EDIT_END_ISDRIVINGLICENCE);
						var isOrganization =storage.get(WapConstants.LEAVE_EDIT_END_ISORGANIZATION);
						var traveUnit =storage.get(WapConstants.LEAVE_EDIT_END_TRAVEUNIT);
						var travelinkPerson =storage.get(WapConstants.LEAVE_EDIT_END_TRAVELINKPERSON);
						var travelinkPhone =storage.get(WapConstants.LEAVE_EDIT_END_TRAVELINKPHONE);
						var isInsurance =storage.get(WapConstants.LEAVE_EDIT_END_ISINSURANCE);
						var activityleaderId =storage.get(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERID);
						var activityleaderName =storage.get(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERNAME);
						var activityleaderPhone =storage.get(WapConstants.LEAVE_EDIT_END_ACTIVITYLEADERPHONE);
						var leadGroupId =storage.get(WapConstants.LEAVE_EDIT_END_LEADGROUPID);
						var leadGroupName =storage.get(WapConstants.LEAVE_EDIT_END_LEADGROUPNAME);
						var leadGroupPhone =storage.get(WapConstants.LEAVE_EDIT_END_LEADGROUPPHONE);
						var otherTeacherId =storage.get(WapConstants.LEAVE_EDIT_END_OTHERTEACHERID);
						var otherTeacherNames =storage.get(WapConstants.LEAVE_EDIT_END_OTHERTEACHERNAMES);
						var activityIdeal =storage.get(WapConstants.LEAVE_EDIT_END_ACTIVITYIDEAL);
						var saftIdeal =storage.get(WapConstants.LEAVE_EDIT_END_SAFTIDEAL);
						var tcontent =storage.get(WapConstants.LEAVE_EDIT_END_TCONTENT);
						var partakePersonId =storage.get(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONID);
						var partakePersonNames =storage.get(WapConstants.LEAVE_EDIT_END_PARTAKEPERSONNAMES);
						var gooutJtFlow =storage.get(WapConstants.LEAVE_EDIT_END_GOOUTJTFLOW);
						
						if(isNotBlank(id)){
							$("#id").val(id);
						}
						if(isNotBlank(userId)){
							$("#userId").val(userId);
						}
						if(isNotBlank(unitId)){
							$("#unitId").val(unitId);
						}
						if(isNotBlank(createTime)){
							$("#createTime").val(createTime);
						}
						if(isNotBlank(applyUserId)){
							$("#applyUserId").val(applyUserId);
						}
						if(isNotBlank(startDate)){
							$("#startDate").val(startDate);
						}
						if(isNotBlank(endDate)){
							$("#endDate").val(endDate);
						}
						if(isNotBlank(gooOutJtType)){
							$("#goOutJtType").val(gooOutJtType);
						}
						if(isNotBlank(organize)){
							$("#organize").val(organize);
						}
						if(isNotBlank(activityNumber)){
							$("#activityNumber").val(activityNumber);
						}
						if(isNotBlank(place)){
							$("#place").val(place);
						}
						if(isNotBlank(scontent)){
							$("#scontent").val(scontent);
						}
						if(isNotBlank(vehicle)){
							$("#vehicle").val(vehicle);
						}
						if(isNotBlank(isDrivinglicence)){
							$("#isDrivinglicence").val(isDrivinglicence);
						}
						if(isNotBlank(isOrganization)){
							$("#isOrganization").val(isOrganization);
						}
						if(isNotBlank(traveUnit)){
							$("#traveUnit").val(traveUnit);
						}
						if(isNotBlank(travelinkPerson)){
							$("#travelinkPerson").val(travelinkPerson);
						}
						if(isNotBlank(travelinkPhone)){
							$("#travelinkPhone").val(travelinkPhone);
						}
						if(isNotBlank(isInsurance)){
							$("#isInsurance").val(isInsurance);
						}
						if(isNotBlank(activityleaderId)){
							$("#activityleaderId").val(activityleaderId);
						}
						if(isNotBlank(activityleaderName)){
							$("#activityleaderName").val(activityleaderName);
						}
						if(isNotBlank(activityleaderPhone)){
							$("#activityleaderPhone").val(activityleaderPhone);
						}
						if(isNotBlank(leadGroupId)){
							$("#leadGroupId").val(leadGroupId);
						}
						if(isNotBlank(leadGroupName)){
							$("#leadGroupName").val(leadGroupName);
						}
						if(isNotBlank(leadGroupPhone)){
							$("#leadGroupPhone").val(leadGroupPhone);
						}
						if(isNotBlank(otherTeacherId)){
							$("#otherTeacherId").val(otherTeacherId);
						}
						if(isNotBlank(otherTeacherNames)){
							$("#otherTeacherNames").html(otherTeacherNames);
						}
						if(isNotBlank(activityIdeal)){
							$("#activityIdeal").val(activityIdeal);
						}
						if(isNotBlank(saftIdeal)){
							$("#saftIdeal").val(saftIdeal);
						}
						if(isNotBlank(tcontent)){
							$("#tcontent").val(tcontent);
						}
						
						if(isNotBlank(partakePersonId)){
							$("#partakePersonId").val(partakePersonId);
						}
						if(isNotBlank(partakePersonNames)){
							$("#partakePersonNames").html(partakePersonNames);
						}
						if(isNotBlank(gooutJtFlow)){
							$("#gooutJtFlow").val(gooutJtFlow);
						}
						
						//获取缓存中已选中的人员
						var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
						var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
						var type = storage.get(WapConstants.ADDRESS_TYPE_JT);
						if(WapConstants.ADDRESS_TYPE_JT_1==type){//活动负责人
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_1);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_1);
							wap._applyService._gooutJtService.setUser(WapConstants.ADDRESS_TYPE_JT_1, userIds, userNames);
						}
						else if(WapConstants.ADDRESS_TYPE_JT_2==type){//带队老师
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_2);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_2);
							wap._applyService._gooutJtService.setUser(WapConstants.ADDRESS_TYPE_JT_2, userIds, userNames);
						}
						else if(WapConstants.ADDRESS_TYPE_JT_3==type){//其他老师
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_3);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_3);
							wap._applyService._gooutJtService.setUser(WapConstants.ADDRESS_TYPE_JT_3, userIds, userNames);
						}
						else if(WapConstants.ADDRESS_TYPE_JT_4==type){//参与人员
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_JT_4);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_4);
							wap._applyService._gooutJtService.setUser(WapConstants.ADDRESS_TYPE_JT_4, userIds, userNames);
						}
						
						wap._applyService._gooutJtService.initBind();
						
						if("2"==gooOutJtType){
							$("#studentEx").hide();
							$("#teacherEx").show();
						}else{
							$("#studentEx").show();
							$("#teacherEx").hide();
						}
					}else{
						//清缓存
						wap._applyService._gooutJtService.clearCache();
						
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_GO_OUT_JT);
					}
					
					var fileIndex=1;
					//附件
					if(isWeiXin()){
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
					}else{
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
					}
					$("#upFile"+fileIndex).on('change',function(){
						wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
					});
				},
				
				setUser : function(type, userIds, userNames){
					if(WapConstants.ADDRESS_TYPE_JT_3==type){//其他老师
						if(isNotBlank(userIds)){
							$('#otherTeacherNames').html('');
							$('#otherTeacherId').val('');
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								var htmlStr = '';
								for(index in names){
									if(index < names.length){
										htmlStr += '<span>'+names[index]+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>\n';
									}
								}
								$('#otherTeacherNames').html(htmlStr);
								$('#otherTeacherId').val(ids);
								storage.set(WapConstants.ADDRESS_USERIDS_JT_3, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_JT_3, userNames);
							}
						}else{
							$('#otherTeacherNames').html('');
							$('#otherTeacherId').val('');
							storage.set(WapConstants.ADDRESS_USERIDS_JT_2, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_JT_2, '');
						} 
					}
					else if(WapConstants.ADDRESS_TYPE_JT_4==type){//参与人员
						if(isNotBlank(userIds)){
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								var htmlStr = '';
								for(index in names){
									if(index < names.length){
										htmlStr += '<span>'+names[index]+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>\n';
									}
								}
//								htmlStr += '</span>';
								$('#partakePersonNames').html(htmlStr);
								$('#partakePersonId').val(ids);
								storage.set(WapConstants.ADDRESS_USERIDS_JT_4, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_JT_4, userNames);
							}
						}else{
							$('#partakePersonNames').html('');
							$('#partakePersonId').val('');
							storage.set(WapConstants.ADDRESS_USERIDS_JT_2, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_JT_2, '');
						} 
					}
					else if(WapConstants.ADDRESS_TYPE_JT_1==type){//活动负责人
						if(isNotBlank(userIds)){
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								$("#activityleaderId").val(ids[0]);
								$("#activityleaderName").val(names[0]);
								storage.set(WapConstants.ADDRESS_USERIDS_JT_1, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_JT_1, userNames);
							}
						}else{
							$("#activityleaderId").val('');
							$("#activityleaderName").val('');
							storage.set(WapConstants.ADDRESS_USERIDS_JT_1, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_JT_1, '');
						} 
					}
					else if(WapConstants.ADDRESS_TYPE_JT_2==type){//带队老师
						if(isNotBlank(userIds)){
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								$("#leadGroupId").val(ids[0]);
								$("#leadGroupName").val(names[0]);
								storage.set(WapConstants.ADDRESS_USERIDS_JT_1, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_JT_1, userNames);
							}
						}else{
							$("#leadGroupId").val('');
							$("#leadGroupName").val('');
							storage.set(WapConstants.ADDRESS_USERIDS_JT_1, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_JT_1, '');
						} 
					}
				},
				
				//删除用户
				deleteUser : function(userId){
					//
					var userIds = storage.get(WapConstants.ADDRESS_USERIDS_JT_3);
					var userNames = storage.get(WapConstants.ADDRESS_USERNAMES_JT_3);
					var ids = userIds.split(',');
					var names = userNames.split(',');
					var index = ids.indexOf(userId);
					if(index > -1){
						ids.splice(index, 1);
						names.splice(index, 1);
					}
					
					$("#otherTeacherId").val(ids.join(","));
					storage.set(WapConstants.ADDRESS_USERIDS_JT_3, ids.join(","));
					storage.set(WapConstants.ADDRESS_USERNAMES_JT_3, names.join(","));
				},
				
				initBind : function(){
					var now = new Date();
					$('#startDate').mobiscroll().datetime({
						theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
					});
					
					$('#endDate').mobiscroll().datetime({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
				    });

					$('#goOutJtType,#isDrivinglicence,#isOrganization,#isInsurance,#activityIdeal,#saftIdeal,#gooutJtFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });		
					
					var isOrganization = $("#isOrganization").val();
					if("true"==isOrganization){
						$("#organizationDiv").show();
					}else{
						$("#organizationDiv").hide();
					}
					
					
					autoLayer('.abtn-send','#autoLayer',0,'.close','');		
					
					$('.dd-scroll').on('click','span',function(e){
						e.preventDefault();
						$(this).remove();
						
						wap._applyService._gooutJtService.deleteUser($(this).find(".my-userid-class").val());
					});
					
					$("#upFile").on('change',function(){
						var file = $(this).get(0).files[0];
						$("#fileName").val(file.name);
					});
					
					//流程默认显示下一步处理人
					var flowId = $("#gooutJtFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
				
			},
			
			
			_LeaveService : {
				
				init : function(){
					var Request = new UrlSearch();
					var type = Request.type;
					var id = Request.id;
					
					$("#startDate").val(new Date().Format("yyyy-MM-dd"));
					wap._applyService._LeaveService.initData(id, type);//初始化数据
					
					$("#cancelId").click(function(){
						wap._applyService._LeaveService.clearCache();
						wap.backList();
					});
					
					
					$("#leaveFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#save").click(function(){
						wap._applyService._LeaveService.save();
					});
					
					$("#startDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(validateDate(document.getElementById('startDate'), document.getElementById('endDate'))>0){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('D',startDate,endDate);
							$("#leaveDays").val(parseInt(num)+1);
						}
						wap._applyService._LeaveService.addLeaveDaysDeatil(startDate,endDate);
					});
					
					$("#endDate").on('change', function(){
						var startDate = $("#startDate").val();
						var endDate = $("#endDate").val();
						if(isNotBlank(startDate) && isNotBlank(endDate)){
							if(validateDate(document.getElementById('startDate'), document.getElementById('endDate'))>0){
								viewToast('开始时间不能大于结束时间');
								return;
							}
							var num = countTimeLength('D',startDate,endDate);
							$("#leaveDays").val(parseInt(num)+1);
						}
						wap._applyService._LeaveService.addLeaveDaysDeatil(startDate,endDate);
					});
					
					//申请人
					$("#applyUserDiv").click(function(){
						//已维护的数据缓存起来
						wap._applyService._LeaveService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_1);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
//						var returnurl = _contextPath + "/office/mobile/teacherleave/leaveApplyEdit.html";
//						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/leaveApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_2);//单选
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
						
						//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
//						$("#page").hide();
//						$("#addressIframe").attr("src",requesturl);
//						$("#addressIframe").show();
						
						//参数
						storage.set(WapConstants.ADDRESS_TYPE, WapConstants.ADDRESS_TYPE_1);
					});
					//通知人员
					$("#noticeAddress").click(function(){
						//已维护的数据缓存起来
						wap._applyService._LeaveService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_2);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_2);
						if(isNotBlank(userids)){
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, userids);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERIDS, '');
						}
						
						if(isNotBlank(usernames)){
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, usernames);
						}else{
							storage.set(Constants.ADDRESS_SELECTED_USERNAMES, '');
						}
						
						var returnurl = _contextPath + "/office/mobileh5/workflow/leaveApplyEdit.html?type=2";
						storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
						storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//单选
						//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
						//请求通讯录
						var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
						location.href = requesturl;
//						$("#page").hide();
//						$("#addressIframe").attr("src",requesturl);
//						$("#addressIframe").show();
						
						//参数
						storage.set(WapConstants.ADDRESS_TYPE, WapConstants.ADDRESS_TYPE_2);
					});
				},
				
				//添加请假时间明细
				addLeaveDaysDeatil : function(startDate,endDate){
					var dt1 = new Date(startDate.replace(/-/g, "/"));  
			    	var dt2 = new Date(endDate.replace(/-/g, "/"));
			    	if(dt1.getMonth() != dt2.getMonth()){
			    		var trIndex=0;
						var c =getDateYearMouth(startDate,endDate);
						var htmlStr = '';
						for(i=0;i<c.length;i++){
							var desTime=c[i];
							var dy=desTime.substr(0,4);
							var dm=+desTime.substr(4,2);
							
							htmlStr += '<li>';
							htmlStr += '<span class="tt">'+dy+"年"+dm+"月"+'</span>';
							htmlStr += '<span class="tt"><input type="hidden" id="desTimeId'+ trIndex +'" value="'+desTime+'"></span>';
							htmlStr += '<span class="tt"><input type="hidden" id="detailTimeId'+ trIndex +'" name="detailTime" value="'+desTime+'"></span>';
							htmlStr += '<span class="dd"><input type="number" id="detailTimeName'+ trIndex +'" tipName="'+dy+"年"+dm+"月"+'" onchange="wap._applyService._LeaveService.changeDetailDays('+trIndex+')" class="txt" maxlength="2" value="" placeholder="请输入明细天数（必填）"></span>';
							htmlStr += '</li>';
							trIndex++;
						}
						$("#trIndex").val(trIndex);
						$("#leaveDaysDetail").html(htmlStr);
						$("#leaveDaysDetail").show();
			    	}else{
			    		var trIndex=0;
			    		var num = countTimeLength('D',startDate,endDate);
			    		var c =getDateYearMouth(startDate,endDate);
						var htmlStr = '';
						for(i=0;i<c.length;i++){
							var desTime=c[i];
							var dy=desTime.substr(0,4);
							var dm=+desTime.substr(4,2);
							htmlStr += '<li>';
							htmlStr += '<span class="tt">'+dy+"年"+dm+"月"+'</span>';
							htmlStr += '<span class="tt"><input type="hidden" id="desTimeId'+ trIndex +'" value="'+desTime+'"></span>';
							htmlStr += '<span class="tt"><input type="hidden" id="detailTimeId'+ trIndex +'" tipName="'+dy+"年"+dm+"月"+'" name="detailTime" value="'+desTime+'_'+(parseInt(num)+1)+'"></span>';
							htmlStr += '</li>';
						}
						$("#trIndex").val(trIndex);
						$("#leaveDaysDetail").html(htmlStr);
			    		$("#leaveDaysDetail").hide();
			    	}
				},
				
				changeDetailDays : function(index){
					var desTimeId = $("#desTimeId"+index).val();
					var detailTimeName = $("#detailTimeName"+index).val();
					var detailTimeId = desTimeId+"_"+detailTimeName;
					$("#detailTimeId"+index).val(detailTimeId);
				},
				
				//详情页添加请假时间明细
				addLeaveDaysDetailDeatil : function(startDate,endDate){
					var dt1 = new Date(startDate.replace(/-/g, "/"));  
			    	var dt2 = new Date(endDate.replace(/-/g, "/"));
			    	var array = new Array();
			    	if(dt1.getMonth() != dt2.getMonth()){
			    		var trIndex=0;
						var c =getDateYearMouth(startDate,endDate);
						var htmlStr = '';
						for(i=0;i<c.length;i++){
							var desTime=c[i];
							var dy=desTime.substr(0,4);
							var dm=+desTime.substr(4,2);
							array[i]=dy+"年"+dm+"月";
						}
			    	}
			    	return array;
				},
				
				//通讯录回调函数
				addressBackFunction:function(){
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
					var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
					
					var type = storage.get(WapConstants.ADDRESS_TYPE);
					
					wap._applyService._LeaveService.setUser(type, userIds, userNames);
					$("#page").show();
					$("#addressIframe").hide();
				},
				
				
				//
				setUser : function(type, userIds, userNames){
					if(WapConstants.ADDRESS_TYPE_2==type){//通知人员
						if(isNotBlank(userIds)){
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								var htmlStr = '';
								for(index in names){
									if(index < names.length){
										htmlStr += '<span>'+names[index]+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>\n';
									}
								}
//								htmlStr += '</span>';
								$('.dd-scroll').html(htmlStr);
								$('#noticePersonIds').val(ids);
								storage.set(WapConstants.ADDRESS_USERIDS_2, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_2, userNames);
							}
						}else{
							$('#noticePersonNames').html('');
							$('#noticePersonIds').val('');
							storage.set(WapConstants.ADDRESS_USERIDS_2, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_2, '');
						} 
					}else{//申请人
						if(isNotBlank(userIds)){
							var ids = userIds.split(',');
							if(isNotBlank(userNames)){
								var names = userNames.split(',');
								$("#applyUserId").val(ids[0]);
								$("#applyUserName").val(names[0]);
								storage.set(WapConstants.ADDRESS_USERIDS_1, userIds);
								storage.set(WapConstants.ADDRESS_USERNAMES_1, userNames);
							}
						}else{
							$("#applyUserId").val('');
							$("#applyUserName").val('');
							storage.set(WapConstants.ADDRESS_USERIDS_1, '');
							storage.set(WapConstants.ADDRESS_USERNAMES_1, '');
						} 
					}
				},
				
				//删除用户
				deleteUser : function(userId){
					var userIds = storage.get(WapConstants.ADDRESS_USERIDS_2);
					var userNames = storage.get(WapConstants.ADDRESS_USERNAMES_2);
					var ids = userIds.split(',');
					var names = userNames.split(',');
					var index = ids.indexOf(userId);
					if(index > -1){
						ids.splice(index, 1);
						names.splice(index, 1);
					}
					
					$("#noticePersonIds").val(ids.join(","));
					storage.set(WapConstants.ADDRESS_USERIDS_2, ids.join(","));
					storage.set(WapConstants.ADDRESS_USERNAMES_2, names.join(","));
				},
				
				save : function(dataType){
					if(!isActionable()){
						return false;
					}
					
					var applyUserId = $("#applyUserId").val();
					var startDate = $("#startDate").val();
					var endDate = $("#endDate").val();
					var leaveDays = $("#leaveDays").val();
					var leaveType = $("#leaveType").val();
					var remark = $("#remark").val();
					
					if(!isNotBlank(applyUserId)){
						viewToast('请选择申请人');
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
					var detailFlag = false;
					var trIndex = $("#trIndex").val();
					var desTimeCount = 0;
					for(var i = 0; i<trIndex; i++){
						var detailTimeName = $("#detailTimeName"+i).val();
						var tipName = $("#detailTimeName"+i).attr("tipName");
						if(!isNotBlank(detailTimeName)){
							viewToast(tipName+"请假时间明细不能为空或非数字类型");
							detailFlag = true;
							return;
						}else{
							if(!validateFloat(detailTimeName) || detailTimeName<=0){
								viewToast(tipName+'请假时间明细只能输入正数,且不超过一位小数');
								detailFlag = true;
								return;
							}
							if(parseFloat(detailTimeName)>31){
								viewToast(tipName+"请假时间明细不能超过31天");
								detailFlag = true;
								return;
							}
							desTimeCount += parseFloat(detailTimeName);
						}
					}
					if(detailFlag){
						return;
					}
					if(!isNotBlank(leaveDays)){
						viewToast('请假天数不能为空或非数字类型');
						return;
					}else{
						if(!validateFloat(leaveDays) || leaveDays<=0){
							viewToast('请假天数只能输入正数,且不超过一位小数');
							return;
						}
					}
					if(desTimeCount > leaveDays){
						viewToast('请假详细时间总数不能大于请假天数');
						return;
					}
					var num = countTimeLength('D',startDate,endDate);
					if(leaveDays>parseInt(num)+1){
						viewToast('请假天数不能超过开始结束的时间差');
						return;
					}
					
					if(!isNotBlank(remark)){
						viewToast('请假原因不能为空');
						return;
					}else{
						if(getStringLen(remark) > 100){
							viewToast('请假原因不能超过100字符');
							return;
						}
					}
					
					var leaveFlow = $("#leaveFlow").val();
					
					if(!isNotBlank(leaveFlow)){
						viewToast('请选择请假流程');
						return;
					}
					
					var userId = storage.get(Constants.USER_ID);
					$("#userId").val(userId);
					wapNetwork.doSave(WapConstants.OFFICE_TEACHER_LEAVE);
				},
				
				initData : function(id, type){
					if("2" == type){
						var leaveTypeMcodes = storage.get(WapConstants.LEAVE_TYPES);
						if(isNotBlank(leaveTypeMcodes)){
							leaveTypeMcodes = JSON.parse(leaveTypeMcodes);
						}
						$("#leaveType").html('');
			    		if(leaveTypeMcodes.length > 0){
			    			var htmlStr = '';
			    			$.each(leaveTypeMcodes, function(index, json) {
		    					htmlStr += '<option value="'+json.thisId+'">'+json.content+'</option>';
			    			});
			    			$("#leaveType").html(htmlStr);
			    		}
			    		
			    		//请假流程
			    		var flowArray = storage.get(WapConstants.LEAVE_FLOWS);
			    		if(isNotBlank(flowArray)){
			    			flowArray = JSON.parse(flowArray);
			    			$("#leaveFlow").html('');
				    		if(flowArray.length > 0){
				    			var htmlStr = '';
				    			$.each(flowArray, function(index, json) {
				    					htmlStr += '<option value="'+json.flowId+'">'+json.flowName+'</option>';
				    			});
				    			$("#leaveFlow").html(htmlStr);
				    		}
						}else{
							$("#leaveFlow").html('');
						}
			    		
						
						//获取缓存中已选中的人员
						var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
						var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
						var type = storage.get(WapConstants.ADDRESS_TYPE);
						
						wap._applyService._LeaveService.setUser(type, userIds, userNames);
						if(WapConstants.ADDRESS_TYPE_2==type){//通知人员
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_1);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
							wap._applyService._LeaveService.setUser(WapConstants.ADDRESS_TYPE_1, userids, usernames);
						}else{
							var userids = storage.get(WapConstants.ADDRESS_USERIDS_2);
							var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_2);
							wap._applyService._LeaveService.setUser(WapConstants.ADDRESS_TYPE_2, userids, usernames);
						}
						
						//获取缓存中之前已维护的数据
						var id = storage.get(WapConstants.LEAVE_EDIT_ID);
						var userId = storage.get(WapConstants.LEAVE_EDIT_USERID);
						var unitId = storage.get(WapConstants.LEAVE_EDIT_UNITID);
						var deptId = storage.get(WapConstants.LEAVE_EDIT_DEPTID);
						var startDate = storage.get(WapConstants.LEAVE_EDIT_START_DATE);
						var endDate = storage.get(WapConstants.LEAVE_EDIT_END_DATE);
						var leaveDays = storage.get(WapConstants.LEAVE_EDIT_LEAVE_DAYS);
						var leaveType = storage.get(WapConstants.LEAVE_EDIT_LEAVE_TYPE);
						var remark = storage.get(WapConstants.LEAVE_EDIT_REMARK);
						var leaveFlow = storage.get(WapConstants.LEAVE_EDIT_LEAVEFLOW);
						if(isNotBlank(id)){
							$("#id").val(id);
						}
						if(isNotBlank(userId)){
							$("#userId").val(userId);
						}
						if(isNotBlank(unitId)){
							$("#unitId").val(unitId);
						}
						if(isNotBlank(deptId)){
							$("#deptId").val(deptId);
						}
						if(isNotBlank(startDate)){
							$("#startDate").val(startDate);
						}
						if(isNotBlank(endDate)){
							$("#endDate").val(endDate);
						}
						if(isNotBlank(leaveDays)){
							$("#leaveDays").val(leaveDays);
						}
						if(isNotBlank(leaveType)){
							$("#leaveType").val(leaveType);
						}
						if(isNotBlank(remark)){
							$("#remark").val(remark);
						}
						if(isNotBlank(leaveFlow)){
							$("#leaveFlow").val(leaveFlow);
						}
						
						wap._applyService._LeaveService.initBind();
					}else{
						//清缓存
						wap._applyService._LeaveService.clearCache();
						
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_TEACHER_LEAVE);
					}
					
					var fileIndex=1;
					//附件
					if(isWeiXin()){
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
					}else{
						$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
					}
					$("#upFile"+fileIndex).on('change',function(){
						wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
					});
				},
				
				initBind : function(){
					$('#startDate').mobiscroll().date({
						theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
					});
					
					$('#endDate').mobiscroll().date({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
				    });

					$('#leaveType,#leaveFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });		
					
					autoLayer('.abtn-send','#autoLayer',0,'.close','');	
					
					$('.dd-scroll').on('click','span',function(e){
						e.preventDefault();
						$(this).remove();
						
						wap._applyService._LeaveService.deleteUser($(this).find(".my-userid-class").val());
					});
					
					$("#upFile").on('change',function(){
						var file = $(this).get(0).files[0];
						$("#fileName").val(file.name);
					});
					
					//流程默认显示下一步处理人
					var flowId = $("#leaveFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
				
				//数据缓存起来
				saveDataCache : function(){
					var id = $("#id").val();
					var userId = $('#userId').val();
					var unitId = $('#unitId').val();
					var deptId = $('#deptId').val();
					var startDate = $('#startDate').val();
					var endDate = $('#endDate').val();
					var leaveDays = $('#leaveDays').val();
					var leaveType = $('#leaveType').val();
					var remark = $('#remark').val();
					var leaveFlow = $('#leaveFlow').val();
					
					storage.set(WapConstants.LEAVE_EDIT_ID, id);
					storage.set(WapConstants.LEAVE_EDIT_USERID, userId);
					storage.set(WapConstants.LEAVE_EDIT_UNITID, unitId);
					storage.set(WapConstants.LEAVE_EDIT_DEPTID, deptId);
					storage.set(WapConstants.LEAVE_EDIT_START_DATE, startDate);
					storage.set(WapConstants.LEAVE_EDIT_END_DATE, endDate);
					storage.set(WapConstants.LEAVE_EDIT_LEAVE_DAYS, leaveDays);
					storage.set(WapConstants.LEAVE_EDIT_LEAVE_TYPE, leaveType);
					storage.set(WapConstants.LEAVE_EDIT_REMARK, remark);
					storage.set(WapConstants.LEAVE_EDIT_LEAVEFLOW, leaveFlow);
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
					
					storage.remove(Constants.LEAVE_TYPES);
					storage.remove(Constants.LEAVE_FLOWS);
					
					storage.remove(WapConstants.LEAVE_EDIT_ID);
					storage.remove(WapConstants.LEAVE_EDIT_USERID);
					storage.remove(WapConstants.LEAVE_EDIT_UNITID);
					storage.remove(WapConstants.LEAVE_EDIT_DEPTID);
					storage.remove(WapConstants.LEAVE_EDIT_START_DATE);
					storage.remove(WapConstants.LEAVE_EDIT_END_DATE);
					storage.remove(WapConstants.LEAVE_EDIT_LEAVE_DAYS);
					storage.remove(WapConstants.LEAVE_EDIT_LEAVE_TYPE);
					storage.remove(WapConstants.LEAVE_EDIT_REMARK);
					storage.remove(WapConstants.LEAVE_EDIT_LEAVEFLOW);
				},
			},
			
			_attendLectureService : {
				init : function(){
					
					var Request = new UrlSearch();
					var id = Request.id;
					$("#attendDate").val(new Date().Format("yyyy-MM-dd"));
					//wap._applyService._attendLectureService.initBind();
					wap._applyService._attendLectureService.initData(id);//初始化数据
					
					$("#attendLectureFlow").change(function(){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						var flowId = $(this).val();
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					});
					
					$("#cancelId").click(function(){
						wap.backList();
					});
					
					$("#save").click(function(){
						wap._applyService._attendLectureService.save();
					});
					
				},
				
				save : function(){
					if(!isActionable()){
						return false;
					}
					var attendDate = $("#attendDate").val();
					var attendPeriod = $("#attendPeriod").val();
					var attendPeriodNum = $("#attendPeriodNum").val();
					var gradeId = $("#gradeId").val();
					var classId = $("#classId").val();
					var subjectName = $("#subjectName").val();
					var projectName = $("#projectName").val();
					var teacherName = $("#teacherName").val();
					var projectContent = $("#projectContent").val();
					var projectOpinion = $("#projectOpinion").val();
					var attendLectureFlow = $("#attendLectureFlow").val();
					
					if(!isNotBlank(attendDate)){
						viewToast('请选择听课时间');
						return;
					}else{
						$("#attendDateVal").val(attendDate);
					}
					if(!isNotBlank(attendPeriod)){
						viewToast('请选择课次');
						return;
					}
					if(!isNotBlank(attendPeriodNum)){
						viewToast('请选择课次');
						return;
					}
					if(!isNotBlank(gradeId)){
						viewToast('请选择年级');
						return;
					}
					if(!isNotBlank(classId)){
						viewToast('请选择班级');
						return;
					}
					
					if(!isNotBlank(subjectName)){
						viewToast('学科不能为空');
						return;
					}else{
						if(getStringLen(subjectName) > 100){
							viewToast('学科不能超过100字符');
							return;
						}
					}
					if(!isNotBlank(projectName)){
						viewToast('课题名称不能为空');
						return;
					}else{
						if(getStringLen(projectName) > 100){
							viewToast('课题名称不能超过100字符');
							return;
						}
					}
					if(!isNotBlank(teacherName)){
						viewToast('授课老师不能为空');
						return;
					}else{
						if(getStringLen(teacherName) > 50){
							viewToast('授课老师不能超过50字符');
							return;
						}
					}
					if(!isNotBlank(projectContent)){
						viewToast('听课内容不能为空');
						return;
					}else{
						if(getStringLen(projectContent) > 1000){
							viewToast('听课内容不能超过1000字符');
							return;
						}
					}
					
					if(!isNotBlank(attendLectureFlow)){
						viewToast('请选择听课流程');
						return;
					}
					var userId = storage.get(Constants.USER_ID);
					$("#userId").val(userId);
					wapNetwork.doSave(WapConstants.OFFICE_ATTEND_LECTURE);
				},
				
				initData : function(id){
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					wapNetwork.doGetApplyDetail(unitId, userId, id, WapConstants.OFFICE_ATTEND_LECTURE);
				},
				
				initClassInfo : function(){
					var classArray = storage.get(WapConstants.OFFICE_CLASS_ARRAY);
					if(isNotBlank(classArray)){
						classArray = JSON.parse(classArray);
					}
					$("#classId").html('');
		    		if(classArray.length > 0){
		    			var gradeId = $("#gradeId").val();
		    			var classes = new Array();;
		    			$.each(classArray, function(index, json) {
		    				if(isNotBlank(gradeId) && gradeId == json.gradeId){
		    					classes.push(json);
		    				}
		    				
		    			});
		    			var htmlStr = '';
		    			$.each(classes, function(index, json) {
	    					htmlStr += '<option value="'+json.classId+'">'+json.className+'</option>';
		    			});
		    			$("#classId").html(htmlStr);
		    		}
		    		$('#classId').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        placeholder: '请选择',
				        minWidth: 200
				    });	
					
				},
				
				initBind : function(){
					var now = new Date();
					$('#attendDate').mobiscroll().date({
						theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        dateFormat: 'yy-mm-dd',
					});
					$('#attendPeriod,#attendPeriodNum,#classId,#attendLectureFlow').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        placeholder: '请选择',
				        minWidth: 200
				    });		
					$('#gradeId').mobiscroll().select({
						theme: 'mobiscroll',
						lang: 'zh',
						display: 'bottom',
						placeholder: '请选择',
						minWidth: 200,
						onSet: function (event, inst) {//根据年级联动班级
							wap._applyService._attendLectureService.initClassInfo();
					    }
					});		
					
					autoLayer('.abtn-send','#autoLayer',0,'.close','');		
					
					//流程默认显示下一步处理人
					var flowId = $("#attendLectureFlow").val();
					if(isNotBlank(flowId)){
						var unitId = storage.get(Constants.UNIT_ID);
						var userId = storage.get(Constants.USER_ID);
						wapNetwork.getAuditUser(unitId, userId, 0, flowId, null);
					}
				},
			},
			
			//附件选择
			fileChange : function(objId,fileIndex){
				$("#"+objId).hide();
				var file = $("#"+objId).get(0).files[0];
				var fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
				var htmlStr='<li class="fn-clear">';
				var laIn=file.name.lastIndexOf('.');
				if(laIn!=-1){
					htmlStr+=getFilePic(file.name.substring(laIn+1 ));
				}else{
					htmlStr+=getFilePic("");
				}
				htmlStr+='<a href="javascript:void(0)" class="icon-img icon-del2" onclick="wap._applyService.attDel(this,\'upFile'+fileIndex+'\')"></a>';
				htmlStr+='<p class="acc-tt f-16">'+file.name+'</p>';
				htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
				htmlStr+='</li>';
				
				$("#accFooter").append(htmlStr);
				
				fileIndex++;
				if(isWeiXin()){
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="MIME_type">');
				}else{
					$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
				}
				$("#upFile"+fileIndex).on('change',function(){
					wap._applyService.fileChange("upFile"+fileIndex,fileIndex);
				});
			},
			
			//删除附件
			attDel : function(obj,fileIndexId,attId){
				if(attId){
					var attDelIds=$("#attDelIds").val();
					if(attDelIds==''){
						$("#attDelIds").val(attId);
					}else{
						$("#attDelIds").val(attDelIds+=","+attId);
					}
					$(obj).parent('li').remove();
				}else{
					$(obj).parent('li').remove();
					$("#"+fileIndexId).remove();
				}
			},
		},
		//判断是否有emoji表情符
		isEmojiCharacter:function(substring) { 
		    for ( var i = 0; i < substring.length; i++) {  
		        var hs = substring.charCodeAt(i);  
		        if (0xd800 <= hs && hs <= 0xdbff) {  
		            if (substring.length > 1) {  
		                var ls = substring.charCodeAt(i + 1);  
		                var uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;  
		                if (0x1d000 <= uc && uc <= 0x1f77f) {  
		                    return true;  
		                }  
		            }  
		        } else if (substring.length > 1) {  
		            var ls = substring.charCodeAt(i + 1);  
		            if (ls == 0x20e3) {  
		                return true;  
		            }  
		        } else {  
		            if (0x2100 <= hs && hs <= 0x27ff) {  
		                return true;  
		            } else if (0x2B05 <= hs && hs <= 0x2b07) {  
		                return true;  
		            } else if (0x2934 <= hs && hs <= 0x2935) {  
		                return true;  
		            } else if (0x3297 <= hs && hs <= 0x3299) {  
		                return true;  
		            } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030  
		                    || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b  
		                    || hs == 0x2b50) {  
		                return true;  
		            }  
		        }  
		    }  
		},
};  