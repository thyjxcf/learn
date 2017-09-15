var wap = {
		init : function(contextPath, unitId, userId,hasUnitAuth){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(WapConstants.HAS_UNIT_AUTH, hasUnitAuth);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/schedule/scheduleList.html";
		},
		
		initList : function(){
			wap._listService.init();
		},
		
		initEdit : function(){
			wap._editService.init();
		},
		initDetail : function(){
			wap._detailService.init();
		},
		
		//list
		_listService : {
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var isBack = Request.isBack;
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType=WapConstants.DATA_TYPE_1;
				}
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				
				$('.ui-footer span a').removeClass('current');
				if(dataType == WapConstants.DATA_TYPE_3){
					$("#myTab3 a").addClass('current');
				}else if(dataType == WapConstants.DATA_TYPE_2){
					$("#myTab2 a").addClass('current');
				}else{
					$("#myTab1 a").addClass('current');
				}
				
				//取消
				$("#cancelId").click(function(){
					location.href="../workMain.html";
				});
				//新建
				$("#apply").click(function(){
					var dataType = $('.ui-footer span a.current').attr("dataValue");
					location.href = "scheduleEdit.html?dataType="+dataType;
				});
				//上一月
				$("#prevMon").click(function(){
					var date = $("#date").val();
					var dataType = $('.ui-footer span a.current').attr("dataValue");
					wapNetwork.doGetCalendar(unitId, userId,date,0,dataType);
				});
				//下一月
				$("#nextMon").click(function(){
					var date = $("#date").val();
					var dataType = $('.ui-footer span a.current').attr("dataValue");
					wapNetwork.doGetCalendar(unitId, userId,date,1,dataType);
				});
				//tab页
				$('.ui-footer span a').on('touchstart', function(){
					$(this).addClass('current').parent('span').siblings('span').find('a').removeClass('current');;
					var date = '';
					if(storage.get(WapConstants.CHOISE_DAY)){
						date =storage.get(WapConstants.CHOISE_DAY);
					}else{
						date = $("#date").val();
					}
					var dataType = $('.ui-footer span a.current').attr("dataValue");
					wapNetwork.doGetCalendar(unitId,userId,date,'',dataType);
					wap._listService.toData();
				});
				var date = '';
				if(isBack==WapConstants.BACK_LIST_GLAG_1){
					date =storage.get(WapConstants.CHOISE_DAY);
				}else{
					date = $("#date").val();
					storage.remove(WapConstants.CHOISE_DAY);
				}
				wapNetwork.doGetCalendar(unitId,userId,date,'',dataType);
				wap._listService.toData();
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="scheduleList.html?isBack="+WapConstants.BACK_LIST_GLAG_1+"&dataType="+dataType;
			},
			toData:function(){
				var date = storage.get(WapConstants.CHOISE_DAY);
				var dataType = $('.ui-footer span a.current').attr("dataValue");
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wap._listService.loadListData(unitId, userId,dataType,date);
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId,dataType,date){
				$('#empty').hide();
				$("#list").html('');
				storage.remove(WapConstants.PAGE_LAST_DATE);
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId,dataType,date);
				});
				wapNetwork.doGetList(unitId,userId,dataType,date);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
			    wapNetwork.doMoreList(unitId,userId,dataType,++WapPage.pageIndex);
			},
		},
		
		
		_editService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType=WapConstants.DATA_TYPE_1;
				}
				
				//取消
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				$('#startDate').change(function(){
					var startDate = $(this).val();
					$("#startDate").val(startDate+":00");
				});
				
				$('#endDate').change(function(){
					var endDate = $(this).val();
					$("#endDate").val(endDate+":00");
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
				$("#upFile").on('change',function(){
					var file = $(this).get(0).files[0];
					$("#fileName").val(file.name);
				});
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetApplyDetail(unitId, userId,id);
			},
			
			initBind : function(){
				var now = new Date();
				$('#startDate').mobiscroll().datetime({
					theme: 'mobiscroll',
					lang: 'zh',
					display: 'bottom',
					dateFormat: 'yy-mm-dd',
					timeFormat: 'HH:ii',
//					min: new Date(now.getFullYear(), now.getMonth(), now.getDate()),
//					invalid: ['w0', 'w6', '5/1', '12/24', '12/25'],
					dateOrder: 'Mddyy',
					timeWheels: 'HHii'
				});
				
				$('#endDate').mobiscroll().datetime({
					theme: 'mobiscroll',
					lang: 'zh',
					display: 'bottom',
					dateFormat: 'yy-mm-dd',
					timeFormat: 'HH:ii',
//					invalid: ['w0', 'w6', '5/1', '12/24', '12/25'],
					dateOrder: 'Mddyy',
					timeWheels: 'HHii'
				});

				$('#period').mobiscroll().select({
			        theme: 'mobiscroll',
			        lang: 'zh',
			        display: 'bottom',
			        minWidth: 200,
			    });	
			},
			
			save : function(dataType){
				if(!isActionable()){
					return false;
				}
				
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var place = $("#place").val();
				var period = $("#period").val();
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
				if(!isNotBlank(period)){
					viewToast('请选择时间段');
					return;
				}
				
				if(!validateDatetime(document.getElementById('startDate'), document.getElementById('endDate'))){
					viewToast('开始时间不能大于结束时间');
					return;
				}
				
				if(getStringLen(place) > 50){
					viewToast('地点不能超过50字符');
					return;
				}
				if(!isNotBlank(content)){
					viewToast('日志内容不能为空');
					return;
				}
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				$("#unitId").val(unitId);
				$("#userId").val(userId);
				$("#dataType").val(dataType);
				
				wapNetwork.doSave(dataType);
			},
			
		},
		_detailService : {
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType=WapConstants.DATA_TYPE_1;
				}
				
				//取消
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				//暂无附件
				$("#attachment").hide();
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(unitId, userId,dataType,id);
			},
			
		},
		
};  