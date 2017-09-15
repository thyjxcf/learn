var wap = {
//		init : function(contextPath, unitId, userId){
//			storage.set(Constants.UNIT_ID, unitId);
//			storage.set(Constants.USER_ID, userId);
//			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
//			location.href = contextPath + "/office/mobile/bulletin/bulletinList.html";
//		},
		
		initList : function(){
			wap._listService.init();
		},
		
		initDetail : function(){
			wap._detailService.init();
		},
		
		//list
		_listService : {
			init : function(){
				var Request = new UrlSearch();
				var isBack = Request.isBack;
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				var searchStr = "";
				
				//取消
				$("#cancelId").click(function(){
						location.href="../workMain.html";
				});
				
				wap._listService.loadListData(unitId, userId);
			},
			
			//返回时加载数据
			backList:function(){
				location.href="bulletinList.html?isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId){
				$('#empty').hide();
				$("#list").html('');
				storage.remove(WapConstants.PAGE_LAST_DATE);
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId);
				});
				//var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(unitId,userId);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
			    wapNetwork.doMoreList(unitId,userId,++WapPage.pageIndex);
			},
		},
		
		
		_detailService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				
				//取消
				$("#cancelId").click(function(){
					wap._listService.backList();
				});
				//暂无附件
				$("#attachment").hide();
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(unitId, userId,id);
			},
			
		},
		
};  