var wap = {
		init : function(contextPath, unitId, userId, userRealName){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			
			location.href = contextPath + "/office/mobileh5/workreport/workreportList.html";
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
		
		//清除缓存
		clearCache : function(){
			storage.remove(WapConstants.WR_SEARCH_STR);
			storage.remove(WapConstants.RECEIVE_NUMBER);
		},
		
		//list
		_listService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var sendTime = Request.sendTime;
				var reportType = Request.reportType;
				var isBack = Request.isBack;
				var collect = Request.collect;//1汇总进入列表，2列表返回汇总页
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				var searchStr = "";
				
				//汇总页面隐藏
				if(collect==1){
					$('#typeDiv').hide();
					$('#showAllTab').hide();
					$('#headDiv').hide();
					$('#apply').hide();
					//$('footer').hide();
					var headStr = '';
					if(reportType==WapConstants.REPORT_TYPE_1){
						headStr+='周报';
					}else{
						headStr+='月报';
					}
					if(sendTime!=null && typeof(sendTime)!='undefined'){
						headStr+="("+sendTime.substring(5, 7)+"月"+sendTime.substring(8, 10)+"日)";
					}
					$('title').html(headStr);
					if(!$("#cancelId").hasClass('html-window-back')){
						$("#cancelId").addClass('html-window-back');
					}
				}else if(collect==2){
					//$('footer').hide().siblings('.ui-footer').show();
					$('.ui-footer').show();
					if(reportType==WapConstants.REPORT_TYPE_1){
						$('.ui-footer .submit').addClass('active').parent('span').siblings('span').find('a').removeClass('active');
					}else{
						$('.ui-footer .reset').addClass('active').parent('span').siblings('span').find('a').removeClass('active');
					}
					$('.ui-search,.report-add').hide();
				}else{
					initWieikeGoBack();
				}
				
				if(isBack==WapConstants.BACK_LIST_GLAG_1){
					if(reportType==WapConstants.REPORT_TYPE_1){
						$('.show-all font').text('周报');
						$('.show-all').val(WapConstants.REPORT_TYPE_1);
					}else if(reportType==WapConstants.REPORT_TYPE_2){
						$('.show-all font').text('月报');
						$('.show-all').val(WapConstants.REPORT_TYPE_2);
					}else{
						$('.show-all font').text('全部');
						$('.show-all').val('');
					}
				}
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					if($("#showAllLayer").is(':visible')){
						closeShowAll();
					}
					var searchStr = $("#queryText").val();
					if(collect==1){
					}else{
						 dataType = $('.tab-wrap ul').children('.current').val();
						 reportType = $(".show-all").val();
					}
					if(sendTime==null || typeof(sendTime)=='undefined'){
						sendTime = '';
					}
					wap._listService.loadListData(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
					return false;
				});
				
				//搜索事件
				$('.ui-search-opt .btn').click(function () { 
					if($("#showAllLayer").is(':visible')){
						closeShowAll();
					}
					var searchStr = $("#queryText").val();
					if(collect==1){
					}else{
						 dataType = $('.tab-wrap ul').children('.current').val();
						 reportType = $(".show-all").val();
					}
					if(sendTime==null || typeof(sendTime)=='undefined'){
						sendTime = '';
					}
					wap._listService.loadListData(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
					return false;
				});
				
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					$('.show-all-layer').hide();
					if(collect==1){
					}else{
						if($(this).hasClass('i-can')){
							//$('footer').hide().siblings('.ui-footer').show();
							$('.ui-footer').show();
							$('.ui-footer .submit').addClass('active').parent('span').siblings('span').find('a').removeClass('active');
							$('.ui-search,.report-add').hide();
							if(reportType==WapConstants.REPORT_TYPE_2){
								$('.ui-footer .reset').addClass('active').parent('span').siblings('span').find('a').removeClass('active');
							}else{
								$('.ui-footer .submit').addClass('active').parent('span').siblings('span').find('a').removeClass('active');
							}
						}else{
							//$('footer').show().siblings('.ui-footer').hide();
							$('.ui-footer').hide();
							$('.ui-search,.report-add').show();
						};
					}
					var dataType = $(this).val();
					if(dataType == WapConstants.DATA_TYPE_1){
						$("#queryText").attr('placeholder', '请输入汇报人姓名');
					}else{
						$("#queryText").attr('placeholder', '请输入汇报内容');
					}
					
					if(dataType == WapConstants.DATA_TYPE_3){
						if(collect==2){
							if(reportType==null || typeof(reportType)=='undefined'){
								reportType = WapConstants.REPORT_TYPE_1;
							}
							collect='';
						}else{
							reportType = WapConstants.REPORT_TYPE_1;
						}
					}else{
						if(collect==1){
							
						}else{
							if(isBack!=WapConstants.BACK_LIST_GLAG_1){
								$('.show-all font').text('全部');
								$('.show-all').val('');
							}
							reportType = $(".show-all").val();
							$('.show-all-layer ul li').each(function() {
								if($(this).val()==reportType)
								$(this).addClass('current').siblings('li').removeClass('current');
							});
						}
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
					if(sendTime==null || typeof(sendTime)=='undefined'){
						sendTime = '';
					}
					wap._listService.loadListData(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
				});
				
				//周报月报Tab
				$('#footerTab span').bind('click', function(){
					if($(this).find('a').hasClass('submit')){
						reportType = WapConstants.REPORT_TYPE_1;
					}else{
						reportType = WapConstants.REPORT_TYPE_2;
					}
					wap._listService.loadListData(unitId, userId, WapConstants.DATA_TYPE_3, searchStr,reportType,sendTime);
				});
				
				//编辑页面
				$("#apply").click(function(){
					var dataType = $('.tab-wrap ul').children('.current').val();
					location.href = "workreportEdit.html?dataType="+dataType;
				});
				
				//取消
				$("#cancelId").click(function(){
					if(collect==1){
						wap._listService.backList(WapConstants.DATA_TYPE_3,'',reportType,2);
					}else{
						wap.clearCache();
						var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
						if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
							if($(".html-window-back").length > 0){//存在  则说明是列表页进来的  不需要返回的工作界面  否则需要调用$(".html-window-close").click();
								location.href="../work/workMain.html";
							}else{
								$(".html-window-close").click();
							}
						}else{
							location.href="../work/workMain.html";
						}
					}
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
				//汇报类型切换
				$('.show-all-layer ul li').click(function(e){
					e.preventDefault();
					$(this).addClass('current').siblings('li').removeClass('current');
					$('.show-all-layer').hide();
					$('.show-all font').text($(this).text());
					$('.show-all').val($(this).val());
					wap._listService.changeType(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
				});
				
			},
			
			//返回时加载数据
			backList:function(dataType,sendTime,reportType,collect){
				var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
				if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
					$(".html-window-close").click();
				}else{
					location.href="workreportList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1+
							"&sendTime="+sendTime+"&reportType="+reportType+"&collect="+collect;
				}
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
				});
				wapNetwork.doGetList(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
			},
			//加载更多
			bindClickMore : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
			    wapNetwork.doMoreList(unitId, userId, dataType, searchStr,reportType,sendTime,collect, ++WapPage.pageIndex);
			},
			changeType : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect){
				var searchStr = $("#queryText").val();
				if(collect==1){
				}else{
					 dataType = $('.tab-wrap ul').children('.current').val();
					 reportType = $(".show-all").val();
				}
				if(sendTime==null || typeof(sendTime)=='undefined'){
					sendTime = '';
				}
				wap._listService.loadListData(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
				return false;
			}
			
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
				if(isNotBlank(id)){
					wapNetwork.getReportById(id,dataType);
				}else{
					wap._editService.initData(id, type);//初始化数据
				}
				wap._editService.initBind();
				
				$("#cancelId").click(function(){
					wap._editService.clearCache();
					wap._listService.backList(dataType,'','','');
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
				$("#upFile").on('change',function(){
					var file = $(this).get(0).files[0];
					$("#fileName").val(file.name);
					$("#everAttachId").val("");
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
					var userphotos = storage.get(WapConstants.ADDRESS_USERPHOTOS_1);
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
					if(isNotBlank(userphotos)){
						storage.set(Constants.ADDRESS_SELECTED_USERPHOTOS, userphotos);
					}else{
						storage.set(Constants.ADDRESS_SELECTED_USERPHOTOS, '');
					}
					
					var returnurl = _contextPath + "/office/mobileh5/workreport/workreportEdit.html?type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_1);//
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
					
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
				var filename = $("#fileName").val();
				
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
				
				if(!isNotBlank(receiveIds)){
					viewToast('请选择接收人');
					return;
				}
				var ids = receiveIds.split(',');
				if(ids.length>=30){
					viewToast('接收人不能多于30人');
					return;
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
					var userPhotos = storage.get(Constants.ADDRESS_SELECTED_USERPHOTOS,false);
					if(isNotBlank(userIds) && isNotBlank(userNames)){
						wap._editService.setUser(userIds, userNames,userPhotos);
					}
					//获取缓存中之前已维护的数据
					var reportType = storage.get(WapConstants.EDIT_REPORT_TYPE);
					var startTime = storage.get(WapConstants.EDIT_START_TIME);
					var endTime = storage.get(WapConstants.EDIT_END_TIME);
					var content = storage.get(WapConstants.EDIT_CONTENT);
					var id = storage.get(WapConstants.EDIT_ID);
					if(isNotBlank(reportType)){
						$("#reportType").val(reportType);
						if(reportType==2){
							$("#reportType option:nth-child(2)").attr('selected', 'selected');
						}
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
					if(isNotBlank(id)){
						$('#id').val(id);
					}
					wap._editService.initBind();
				}else{
					//第一次进新增页面清缓存
					wap._editService.clearCache();
					
					//获取上一次选中的人员
//					var userIds = storageLocal.get(WapConstants.LAST_RECEIVE_IDS, false);
//					var userNames = storageLocal.get(WapConstants.LAST_RECEIVE_NAMES, false);
//					if(isNotBlank(userIds) && isNotBlank(userNames)){
//						wap._editService.setUser(userIds, userNames);
//						//赋值给通讯录
//						storage.set(WapConstants.ADDRESS_USERIDS_1, userIds);
//						storage.set(WapConstants.ADDRESS_USERNAMES_1, userNames);
//					}else{
//						storage.set(WapConstants.ADDRESS_USERIDS_1, "");
//						storage.set(WapConstants.ADDRESS_USERNAMES_1, "");
//					}
				}
			},
			
			initBind : function(){
				var now = new Date();
				$('#startDate').mobiscroll().date({
					theme: 'mobiscroll',
					lang: 'zh',
					display: 'bottom',
					dateFormat: 'yy-mm-dd',
//					invalid: ['w0', 'w6', '5/1', '12/24', '12/25'],限制选择的时间：周末---五一---圣诞
					dateOrder: 'Mddyy',
					timeWheels: 'HHii'
				});
				
				$('#endDate').mobiscroll().date({
					theme: 'mobiscroll',
					lang: 'zh',
					display: 'bottom',
					dateFormat: 'yy-mm-dd',
//					invalid: ['w0', 'w6', '5/1', '12/24', '12/25'],
					dateOrder: 'Mddyy',
					timeWheels: 'HHii'
				});

				$('#reportType,#leaveFlow').mobiscroll().select({
			        theme: 'mobiscroll',
			        lang: 'zh',
			        display: 'bottom',
			        minWidth: 200,
			    });	
				//删除接收人
				$('#receiveNames').on('click','span:not(.receive-address)',function(e){
					e.preventDefault();
					$(this).remove();
					wap._editService.deleteUser($(this).find(".my-userid-class").val());
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
					var userphotos = storage.get(WapConstants.ADDRESS_USERPHOTOS_1);
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
					if(isNotBlank(userphotos)){
						storage.set(Constants.ADDRESS_SELECTED_USERPHOTOS, userphotos);
					}else{
						storage.set(Constants.ADDRESS_SELECTED_USERPHOTOS, '');
					}
					
					var returnurl = _contextPath + "/office/mobileh5/workreport/workreportEdit.html?type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_1);//
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap2/redirect.html?unitId="+_unitId;
					
					location.href = requesturl;
				});
			},
			
			//
			setUser : function(userIds, userNames,userPhotos){
				//阿斯顿..，
				var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
				if(isNotBlank(userIds)){
					var ids = userIds.split(',');
					if(isNotBlank(userNames)){
						var names = userNames.split(',');
						var photos = userPhotos.split(',');
//						for(index in photos){
//							if(photos[index]==''||photos[index]==null){
//								photos[index]=_contextPath+'/static/html/images/icon/img_default_photo.png';
//							}
//						}
						var htmlStr = '';
						for(index in names){
							var photoStr = '';
							if(index < names.length){
								if(!isNotBlank(photos[index])){
									photoStr += '<span class="place-avatar" style="background:'+getBackgroundColor(names[index])+';">'+getLast2Str(names[index])+'</span>'
								}else{
									photoStr += '<img src="'+photos[index]+'">';
								}
								htmlStr += '<span>'+photoStr+wap.subStrName(names[index])+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>';
							}
						}
							htmlStr += '<span class="receive-address"><a href="#" id="address" class="add"></a></span>';
						$('#receiveNames').html(htmlStr);
						$('#receiveIds').val(ids);
						storage.set(WapConstants.ADDRESS_USERIDS_1, userIds);
						storage.set(WapConstants.ADDRESS_USERNAMES_1, userNames);
						storage.set(WapConstants.ADDRESS_USERPHOTOS_1, userPhotos);
					}
				}else{
					$('#receiveNames').html('');
					$('#receiveIds').val('');
					storage.set(WapConstants.ADDRESS_USERIDS_1, '');
					storage.set(WapConstants.ADDRESS_USERNAMES_1, '');
					storage.set(WapConstants.ADDRESS_USERPHOTOS_1, '');
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
				var id = $('#id').val();
				storage.set(WapConstants.EDIT_REPORT_TYPE, reportType);
				storage.set(WapConstants.EDIT_START_TIME, startTime);
				storage.set(WapConstants.EDIT_END_TIME, endTime);
				storage.set(WapConstants.EDIT_CONTENT, content);
				storage.set(WapConstants.EDIT_ID, id);
			},
			
			//清除缓存
			clearCache : function(){
				//清除缓存
				storage.remove(Constants.ADDRESS_RETURN_URL);
				storage.remove(Constants.CONTEXTPATH);
				
				storage.remove(WapConstants.EDIT_REPORT_TYPE);
				storage.remove(WapConstants.EDIT_START_TIME);
				storage.remove(WapConstants.EDIT_END_TIME);
				storage.remove(WapConstants.EDIT_CONTENT);
				storage.remove(WapConstants.EDIT_ID);
				storage.remove(WapConstants.EDIT_FILE);
				storage.remove(WapConstants.EDIT_FILE_NAME);
				
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
				var sendTime = Request.sendTime;
				var reportType = Request.reportType;
				var collect = Request.collect;//是否汇总
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				var userId = '';
				if(dataType == WapConstants.DATA_TYPE_1){
					userId = storage.get(Constants.USER_ID);
				}
				if(dataType == WapConstants.DATA_TYPE_2){
					$("#cancelReport").show();
				}
				$("#cancelReport").click(function(){
					wapNetwork.cancelReport(id,storage.get(Constants.USER_ID),dataType,sendTime,reportType,collect);
				});
				//返回
				$("#cancelId").click(function(){
						wap._listService.backList(dataType,sendTime,reportType,collect);
				});
				//触发手机键盘done/go
				$('#dataForm').submit(function () { 
					wap._detailService.saveReply(id,dataType,sendTime,reportType,collect);
				});
				//评论
				$("#saveReply").click(function(){
					wap._detailService.saveReply(id,dataType,sendTime,reportType,collect);
				});
				wapNetwork.doGetDetail(dataType,id,userId);
			},
			
			//评论时加载数据
			backDetail : function(id,dataType,sendTime,reportType,collect){
				var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
				if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
					if($(".html-window-back").length > 0){//存在  则说明是列表页进来的  不需要返回的工作界面  否则需要调用$(".html-window-close").click();
						location.href="workreportDetail.html?dataType="+dataType+"&id="+id+
								"&sendTime="+sendTime+"&reportType="+reportType+"&collect="+collect;
					}else{
						$(".html-window-close").click();
					}
				}else{
					location.href="workreportDetail.html?dataType="+dataType+"&id="+id+
							"&sendTime="+sendTime+"&reportType="+reportType+"&collect="+collect;
				}
			},
			
			saveReply : function(id,dataType,sendTime,reportType,collect){
				if(!isActionable()){
					return false;
				}
				var content = $("#content").val();
				
				if(!isNotBlank(content)){
					return;
				}else{
					if(getStringLen(content) > 500){
						viewToast('评论内容不能超过500字符');
						return;
					}
				}
				
				var userId = storage.get(Constants.USER_ID);
				$("#userId").val(userId);
				$("#reportId").val(id);
				wapNetwork.doSaveReply(id,dataType,sendTime,reportType,collect);
			},
			
		},
		
		subStrName : function(name){
			if(name.length>4){
				name = name.substring(0,4);
			}
			return name;
		},
};  