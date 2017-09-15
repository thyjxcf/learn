var wap = {
		init : function(contextPath, unitId, userId, userRealName){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			
			location.href = contextPath + "/office/mobile/workreport/workreportList.html";
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
		
		//清楚缓存
		clearCache : function(){
			storage.remove(WapConstants.WR_SEARCH_STR);
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
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					var dataType = $('.tab-wrap ul').children('.current').val();
					var searchStr = $("#queryText").val();
					wap._listService.loadListData(unitId, userId, dataType, searchStr);
					return false;
				});
				
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					var dataType = $(this).val();
					if(dataType == WapConstants.DATA_TYPE_2){
						$("#queryText").attr('placeholder', '请输入汇报人姓名');
					}else{
						$("#queryText").attr('placeholder', '请输入汇报内容');
					}
					
					if(isBack==WapConstants.BACK_LIST_GLAG_1){
						//返回第一次刷新列表还原值
						isBack = WapConstants.BACK_LIST_GLAG_0;
						//返回列表页默认搜索值
						searchStr = storage.get(WapConstants.WR_SEARCH_STR);
						if(isNotBlank(searchStr)){
							$('#queryText').val(searchStr);
						}else{
							searchStr = '';
						}
						
					}else{
						$("#queryText").val('');
						searchStr = '';
					}
					
					wap._listService.loadListData(unitId, userId, dataType, searchStr);
				});
				
				//更多
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					var searchStr = storage.get(WapConstants.WR_SEARCH_STR);
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr, applyStatus);
				});
				
				//新增
				$("#apply").click(function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					location.href = "workreportEdit.html?dataType="+dataType;
				});
				
				//取消
				$("#cancelId").click(function(){
					wap.clearCache();
					try{
						mobile.back();
					}catch(e){
						location.href = "../officeMain.html";
					}
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
				//wapNetwork.doGetList(unitId, userId, dataType, searchStr);
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="workreportList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, searchStr){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr);
				});
				//var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(unitId, userId, dataType, searchStr);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId, dataType, searchStr){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
			    wapNetwork.doMoreList(unitId, userId, dataType, searchStr, ++WapPage.pageIndex);
			},
		},
		
		_editService : {
			
			init : function(){
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var type = Request.type;
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				wap._editService.initBind();
				wap._editService.initData(id, type);//初始化数据
				
				$("#cancelId").click(function(){
					wap._editService.clearCache();
					wap._listService.backList(dataType);
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
				
				//接收人
				$("#address").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var userids = storage.get(WapConstants.ADDRESS_USERIDS_1);
					var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
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
					
					var returnurl = _contextPath + "/office/mobile/workreport/workreportEdit.html?type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_1);//
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//
//					storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
//					$("#page").hide();
//					$("#addressIframe").attr("src",requesturl);
//					$("#addressIframe").show();
					
					location.href = requesturl;
				});
			},
			
			save : function(dataType){
				if(!isActionable()){
					return false;
				}
				
				var receiveIds = $("#receiveIds").val();
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var content = $("#content").val();
				
				if(!isNotBlank(receiveIds)){
					viewToast('请选择接收人');
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
				
				if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
					viewToast('开始时间不能大于结束时间');
					return;
				}
				
				if(!isNotBlank(content)){
					viewToast('汇报内容不能为空');
					return;
				}else{
					if(getStringLen(content) > 500){
						viewToast('汇报内容不能超过500字符');
						return;
					}
				}
				
				var userId = storage.get(Constants.USER_ID);
				$("#userId").val(userId);
				wapNetwork.doSave(dataType);
			},
			
			initData : function(id, type){
				if("2" == type){
					//获取缓存中已选中的人员
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS,false);
					var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES,false);
					if(isNotBlank(userIds) && isNotBlank(userNames)){
						wap._editService.setUser(userIds, userNames);
					}
					//获取缓存中之前已维护的数据
					var reportType = storage.get(WapConstants.EDIT_REPORT_TYPE);
					var startTime = storage.get(WapConstants.EDIT_START_TIME);
					var endTime = storage.get(WapConstants.EDIT_END_TIME);
					var content = storage.get(WapConstants.EDIT_CONTENT);
					if(isNotBlank(reportType)){
						$("#reportType").val(reportType);
					}
					if(isNotBlank(startTime)){
						$('#startDate').val(startTime);
					}
					if(isNotBlank(startTime)){
						$('#endDate').val(endTime);
					}
					if(isNotBlank(content)){
						$('#content').val(content);
					}
					
					wap._editService.initBind();
				}else{
					//第一次进新增页面清缓存
					wap._editService.clearCache();
					
					if(isNotBlank(id)){
						wapNetwork.doGetApplyDetail(id);
					}else{
						//获取上一次选中的人员
						var userIds = storageLocal.get(WapConstants.LAST_RECEIVE_IDS, false);
						var userNames = storageLocal.get(WapConstants.LAST_RECEIVE_NAMES, false);
						if(isNotBlank(userIds) && isNotBlank(userNames)){
							wap._editService.setUser(userIds, userNames);
							//赋值给通讯录
							storage.set(WapConstants.ADDRESS_USERIDS_1, userIds);
							storage.set(WapConstants.ADDRESS_USERNAMES_1, userNames);
						}else{
							storage.set(WapConstants.ADDRESS_USERIDS_1, "");
							storage.set(WapConstants.ADDRESS_USERNAMES_1, "");
						}
					}
				}
			},
			
			initBind : function(){
				var opt = {  
					'default': {
						theme: 'default',
						mode: 'scroller',
						display: 'modal',
						animate: 'fade'
					},
					'dateYMD': {
						preset: 'date',
						dateFormat: 'yyyy-mm-dd',
						defaultValue: new Date(new Date()),
						//invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] }
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
				$('#reportType').scroller($.extend(opt['select'],opt['default']));	
				
				//删除接收人
				$('.dd-scroll').on('click','span',function(e){
					e.preventDefault();
					$(this).remove();
					wap._editService.deleteUser($(this).find(".my-userid-class").val());
				});		
			},
			
			
			//通讯录回调函数
			addressBackFunction:function(){
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				
				wap._editService.setUser(userIds, userNames);
				$("#page").show();
				$("#addressIframe").hide();
			},
			
			//
			setUser : function(userIds, userNames){
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
//							htmlStr += '</span>';
						$('#receiveNames').html(htmlStr);
						$('#receiveIds').val(ids);
						storage.set(WapConstants.ADDRESS_USERIDS_1, userIds);
						storage.set(WapConstants.ADDRESS_USERNAMES_1, userNames);
					}
				}else{
					$('#receiveNames').html('');
					$('#receiveIds').val('');
					storage.set(WapConstants.ADDRESS_USERIDS_1, '');
					storage.set(WapConstants.ADDRESS_USERNAMES_1, '');
				} 
			},
			
			//删除用户
			deleteUser : function(userId){
				var userIds = storage.get(WapConstants.ADDRESS_USERIDS_1);
				var userNames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
				var ids = userIds.split(',');
				var names = userNames.split(',');
				var index = ids.indexOf(userId);
				if(index > -1){
					ids.splice(index, 1);
					names.splice(index, 1);
				}

				$("#receiveIds").val(ids.join(","));
				storage.set(WapConstants.ADDRESS_USERIDS_1, ids.join(","));
				storage.set(WapConstants.ADDRESS_USERNAMES_1, names.join(","));
			},
			
			//数据缓存起来
			saveDataCache : function(){
				var reportType = $("#reportType").val();
				var startTime = $('#startDate').val();
				var endTime = $('#endDate').val();
				var content = $('#content').val();
				storage.set(WapConstants.EDIT_REPORT_TYPE, reportType);
				storage.set(WapConstants.EDIT_START_TIME, startTime);
				storage.set(WapConstants.EDIT_END_TIME, endTime);
				storage.set(WapConstants.EDIT_CONTENT, content);
			},
			
			//清除缓存
			clearCache : function(){
				//清楚缓存
				storage.remove(Constants.ADDRESS_RETURN_URL);
				storage.remove(Constants.CONTEXTPATH);
				
				storage.remove(WapConstants.EDIT_REPORT_TYPE);
				storage.remove(WapConstants.EDIT_START_TIME);
				storage.remove(WapConstants.EDIT_END_TIME);
				storage.remove(WapConstants.EDIT_CONTENT);
				
				storage.remove(Constants.ADDRESS_SELECTED_USERIDS);
				storage.remove(Constants.ADDRESS_SELECTED_USERNAMES);
				
				storage.remove(WapConstants.ADDRESS_USERIDS_1);
				storage.remove(WapConstants.ADDRESS_USERNAMES_1);
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
			
		},
		
};  