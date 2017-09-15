var wap = {
		init : function(contextPath, unitId, userId){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/workReportTl/workReportList.html";
		},
		
		//列表页
		initList : function(){
			wap._listService.init();
		},
		
		//详细
		initListDetail : function(){
			wap._listDetailService.init();
		},
		
		//详细
		initListEdit : function(){
			wap._listEditService.init();
		},
		
		//列表页面方法	
		_listService : {
			
			init : function(){
				if(storage.get(WapConstants.MANAGE_MODE)==WapConstants.BOOLEAN_1){
					$("#more-layer").show();
				}
				var Request = new UrlSearch();
				var isBack = Request.isBack;//是否查看返回
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._listService.doRemoveCache();
				}
				var searchName = storage.get(WapConstants.SEARCH_NAME);
				if(!isNotBlank(searchName)){
					searchName='';
				}else{
					$("#searchName").val(searchName);
				}
				var dataType = storage.get(WapConstants.DATA_TYPE);
				if(!isNotBlank(dataType)){
					dataType=WapConstants.DATA_TYPE_1;
					$(".tab-wrap").find("li[datavalue='"+WapConstants.DATA_TYPE_1+"']").addClass("current");
				}else{
					$(".tab-wrap").find("li[datavalue='"+dataType+"']").addClass("current");
					if(dataType==WapConstants.DATA_TYPE_0){
						$('#searchName').attr('placeholder', '请输入汇报人姓名');
					}
				}
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				
				//初始化数据
				wapNetwork.doGetList(userId, unitId, searchName, dataType);
				
				//tab页
				$('#more-layer li').on('touchstart', function(){
					wap._listService.doRemoveCache();
					var dataTypeCurr = $(this).attr("dataValue");
					storage.set(WapConstants.DATA_TYPE,dataTypeCurr);
					if(dataTypeCurr==WapConstants.DATA_TYPE_1){
						$('#searchName').attr('placeholder', '请输入汇报内容');
					}else if(dataTypeCurr==WapConstants.DATA_TYPE_0){
						$('#searchName').attr('placeholder', '请输入汇报人姓名');
					}
					$('#searchName').val('');
					wap._listService.loadListData(userId, unitId, "", dataTypeCurr);
				});
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					wap._listService.bindClickSearch(userId, unitId);
					return false;
				});
				
				//更多
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, unitId);
				});
				
				$('#back').click(function(){
					try{
						mobile.back();
					}catch(e){
						location.href = "../officeMain.html";
					}
				});
				
				//新增
				$("#apply").click(function(){
					location.href = "workReportEdit.html";
				});
			},
			
			//加载第一页数据
			loadListData : function(userId, unitId, searchName, dataType){
				$('#list').hide();
				$('#empty').hide();
				$('.ui-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, unitId);
				});
				wapNetwork.doGetList(userId, unitId, searchName, dataType);
			},
			
			//加载更多
			bindClickMore : function(userId, unitId){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchName = $('#searchName').val();
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
			    wapNetwork.doMoreList(userId, unitId, searchName, dataType, ++WapPage.pageIndex);
			},
			
			//搜索查询
			bindClickSearch : function(userId, unitId){
				var searchName = $('#searchName').val();
				storage.set(WapConstants.SEARCH_NAME,searchName);
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
				wap._listService.loadListData(userId, unitId, searchName, dataType);
				$("#searchName").blur();
			},
			
			//清楚筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_NAME);
				storage.remove(WapConstants.DATA_TYPE);
				$('.filter-layer li').removeClass('current');
			},
			
		},
		_listDetailService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				$('#back').click(function(){
					location.href = "workReportList.html?isBack=1";
				});
				
				wapNetwork.doGetDetail(unitId,userId,dataType,id,WapConstants.BOOLEAN_0);
			},
		},
		_listEditService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				if(!isNotBlank(id)){
					id='';
				}
				if(!isNotBlank(dataType)){
					dataType='';
				}
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				
				wapNetwork.doGetDetail(unitId,userId,dataType,id,WapConstants.BOOLEAN_1);
				
				$('#back').click(function(){
					location.href = "workReportList.html?isBack=1";
				});
				
				$('#save').click(function(){
					if(!isActionable()){
						return false;
					}
					var content=$("#content").val();
					if(content==''){
						viewToast('汇报内容不能为空');
						return;
					}else if(getStringLen(content) > 500){
						viewToast('汇报内容不能超过500字符');
						return;
					}
					wapNetwork.doSave(unitId,userId,dataType);
				});
				
			},
		},
		
};

