var wap = {
		init : function(contextPath, unitId, userId){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/goodsManager/goodsManagerList.html";
		},
		
		//列表页
		initList : function(){
			wap._listService.init();
		},
		
		//列表页详细
		initListDetail : function(){
			wap._listDetailService.init();
		},
		
		//领用物品列表页
		initApplyList : function(){
			wap._appplyListService.init();
		},
		
		//领用物品申请
		initAppplyListDetail : function(){
			wap._appplyListDetailService.init();
		},
		
		
		//列表页面方法	
		_listService : {
			
			init : function(){
				if(storage.get(WapConstants.AUDIT_MODE)==WapConstants.BOOLEAN_1)
					$("#more-layer").show();
				var Request = new UrlSearch();
				var isBack = Request.isBack;//是否查看返回
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._listService.doRemoveCache();
				}
				var searchType = storage.get(WapConstants.SEARCH_TYPE);
				if(!isNotBlank(searchType)){
					searchType='';
				}
				var applyType = storage.get(WapConstants.APPLY_TYPE);
				if(!isNotBlank(applyType)){
					applyType='';
					$(".filter-layer .goodsState").find("li[dataval='']").addClass("current");
				}else{
					$(".filter-layer .goodsState").find("li[dataval='"+applyType+"']").addClass("current");
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
				}
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				
				//加载筛选项
				wapNetwork.doGetSelectList(userId, unitId, searchType, applyType);
				
				//初始化数据
				wapNetwork.doGetList(userId, unitId, searchType, searchName, applyType, dataType);
				
				//搜索按钮
				$('#search-btn').click(function(){
					wap._listService.bindClickSearch(userId, unitId);
				});
				//筛选确认查询
				$('.filter-layer .opt .submit').click(function(e){
					wap._listService.bindClickSearchByCon(userId, unitId);
				});
				//筛选重置
				$('.filter-layer .opt .reset').click(function(e){
					$('.filter-layer li').removeClass('current');
					$('.condition-all').addClass('current');
				});
				
				//tab页
				$('#more-layer li').on('touchstart', function(){
					wap._listService.doRemoveCache();
					var dataTypeCurr = $(this).attr("dataValue");
					storage.set(WapConstants.DATA_TYPE,dataTypeCurr);
					$('#searchName').attr('placeholder', '请输入物品名称');
					$('#searchName').val('');
					wap._listService.loadListData(userId, unitId, "", "", "", dataTypeCurr);
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
				
				//领用申请
				$('#getApply').click(function(){
					wap._listService.doRemoveCache();
			    	location.href="goodsManagerApplyList.html";
				});
				
				$('#back').click(function(){
					try{
						mobile.back();
					}catch(e){
						location.href = "../officeMain.html";
					}
				});
				
			},
			
			//加载第一页数据
			loadListData : function(userId, unitId, searchType, searchName, applyType, dataType){
				$('#list').hide();
				$('#empty').hide();
				$('.ui-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, unitId);
				});
				wapNetwork.doGetList(userId, unitId, searchType, searchName, applyType, dataType);
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
				var applyType = storage.get(WapConstants.APPLY_TYPE);
				if(!isNotBlank(applyType)){
					applyType='';
				}
				var searchName = $('#searchName').val();
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
			    wapNetwork.doMoreList(userId, unitId, searchType, searchName, applyType, dataType, ++WapPage.pageIndex);
			},
			
			//搜索查询
			bindClickSearch : function(userId, unitId){
				var searchType = storage.get(WapConstants.SEARCH_TYPE);
				if(!isNotBlank(searchType)){
					searchType='';
				}
				var applyType = storage.get(WapConstants.APPLY_TYPE);
				if(!isNotBlank(applyType)){
					applyType='';
				}
				var searchName = $('#searchName').val();
				storage.set(WapConstants.SEARCH_NAME,searchName);
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
				wap._listService.loadListData(userId, unitId, searchType, searchName, applyType, dataType);
				$("#searchName").blur();
			},
			
			//通过筛选查询数据
			bindClickSearchByCon : function(userId, unitId){
				var searchType = $(".goodsTypes .current").attr("id");
				storage.set(WapConstants.SEARCH_TYPE,searchType);
				var applyType = $(".goodsState .current").attr("dataval");
				storage.set(WapConstants.APPLY_TYPE,applyType);
				
				var searchName = $('#searchName').val();
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
				wap._listService.loadListData(userId, unitId, searchType, searchName, applyType, dataType);
			},
			
			//清楚筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_TYPE);
				storage.remove(WapConstants.APPLY_TYPE);
				storage.remove(WapConstants.SEARCH_NAME);
				storage.remove(WapConstants.DATA_TYPE);
				$('.filter-layer li').removeClass('current');
				$('.condition-all').addClass('current');
			},
			
		},
		_appplyListService : {
			
			init : function(){
				if(storage.get(WapConstants.AUDIT_MODE)==WapConstants.BOOLEAN_1)
					$("#more-layer").show();
				var Request = new UrlSearch();
				var isBack = Request.isBack;//是否查看返回
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._appplyListService.doRemoveCache();
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
				wapNetwork.doGetSelectList(userId, unitId, searchType, "");
				
				//初始化数据
				wapNetwork.doGetList(userId, unitId, searchType, searchName, "", WapConstants.DATA_TYPE_2);
				
				//搜索按钮
				$('#search-btn').click(function(){
					wap._appplyListService.bindClickSearch(userId, unitId);
				});
				//筛选确认查询
				$('.filter-layer .opt .submit').click(function(e){
					wap._appplyListService.bindClickSearchByCon(userId, unitId);
				});
				//筛选重置
				$('.filter-layer .opt .reset').click(function(e){
					$('.filter-layer li').removeClass('current');
					$('.condition-all').addClass('current');
				});
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					wap._appplyListService.bindClickSearch(userId, unitId);
					return false;
				});
				
				//更多
				$('.loading-more').bind('click', function(){
					wap._appplyListService.bindClickMore(userId, unitId);
				});
				
				$('#back').click(function(){
					location.href = "goodsManagerList.html";
				});
				
			},
			
			//加载第一页数据
			loadListData : function(userId, unitId, searchType, searchName, applyType, dataType){
				$('#list').hide();
				$('#empty').hide();
				$('.ui-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._appplyListService.bindClickMore(userId, unitId);
				});
				wapNetwork.doGetList(userId, unitId, searchType, searchName, "", WapConstants.DATA_TYPE_2);
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
			    wapNetwork.doMoreList(userId, unitId, searchType, searchName, "", WapConstants.DATA_TYPE_2, ++WapPage.pageIndex);
			},
			
			//搜索查询
			bindClickSearch : function(userId, unitId){
				var searchType = storage.get(WapConstants.SEARCH_TYPE);
				if(!isNotBlank(searchType)){
					searchType='';
				}
				var searchName = $('#searchName').val();
				storage.set(WapConstants.SEARCH_NAME,searchName);
				wap._appplyListService.loadListData(userId, unitId, searchType, searchName, "", WapConstants.DATA_TYPE_2);
				$("#searchName").blur();
			},
			
			//通过筛选查询数据
			bindClickSearchByCon : function(userId, unitId){
				var searchType = $(".goodsTypes .current").attr("id");
				storage.set(WapConstants.SEARCH_TYPE,searchType);
				
				var searchName = $('#searchName').val();
				wap._appplyListService.loadListData(userId, unitId, searchType, searchName, "", WapConstants.DATA_TYPE_2);
			},
			
			//清楚筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_TYPE);
				storage.remove(WapConstants.SEARCH_NAME);
				$('.filter-layer li').removeClass('current');
			},
			
		},
		_listDetailService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				var unitId = storage.get(Constants.UNIT_ID);
				$('#back').click(function(){
					location.href = "goodsManagerList.html?isBack=1";
				});
				
				wapNetwork.doGetDetail(unitId,dataType,id);
			},
		},
		_appplyListDetailService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
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
						viewToast('申请数量不能为空');
						return;
					}else if (!validateInteger(applyAmount)){	
						viewToast("申请数量只能输入正整数");
						return;
					}
					if(getStringLen(applyRemark) > 1000){
						viewToast('申请说明不能超过1000字符');
						return;
					}
//					showMsg("确定提交？",function(){
						wapNetwork.doSave(unitId,userId,id,applyAmount,applyRemark);
//					});
				});
				wapNetwork.doGetApplyDetail(unitId,dataType,id);
			},
		},
		
};

