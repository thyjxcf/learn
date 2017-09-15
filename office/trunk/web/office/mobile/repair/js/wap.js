var wap = {
		init : function(contextPath, unitId, userId){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			location.href = contextPath + "/office/mobile/repair/repairList.html";
		},
		
		//列表页
		initList : function(){
			wap._listService.init();
		},
		
		initEdit : function(id){
			wap._editService.init();
		},
		
		initAudit : function(id){
			wap._auditService.init();
		},
		
		initDetail : function(){
			wap._detailService.init();
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
					$(".filter-layer .diffState").find("li[dataval='']").addClass("current");
				}else{
					$(".filter-layer .diffState").find("li[dataval='"+applyType+"']").addClass("current");
				}
				var dataType = storage.get(WapConstants.DATA_TYPE);
				if(!isNotBlank(dataType)){
					dataType=WapConstants.DATA_TYPE_0;
					$(".tab-wrap").find("li[datavalue='"+WapConstants.DATA_TYPE_0+"']").addClass("current");
				}else{
					$(".tab-wrap").find("li[datavalue='"+dataType+"']").addClass("current");
				}
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				
				//加载筛选项
				wapNetwork.doGetSelectList(userId, unitId, searchType, applyType);
				
				//初始化数据
				wapNetwork.doGetList(userId, unitId, searchType, applyType, dataType);
				
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
					wap._listService.loadListData(userId, unitId, "", "", dataTypeCurr);
				});
				
				//更多
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, unitId);
				});
				
				$('#repairAdd').click(function(){
			    	location.href="repairEdit.html";
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
			loadListData : function(userId, unitId, searchType, applyType, dataType){
				$('#list').hide();
				$('#empty').hide();
				$('.ui-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, unitId);
				});
				wapNetwork.doGetList(userId, unitId, searchType, applyType, dataType);
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
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
			    wapNetwork.doMoreList(userId, unitId, searchType, applyType, dataType, ++WapPage.pageIndex);
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
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
				wap._listService.loadListData(userId, unitId, searchType, applyType, dataType);
			},
			
			//通过筛选查询数据
			bindClickSearchByCon : function(userId, unitId){
				var searchType = $(".diffTypes .current").attr("id");
				storage.set(WapConstants.SEARCH_TYPE,searchType);
				var applyType = $(".diffState .current").attr("dataval");
				storage.set(WapConstants.APPLY_TYPE,applyType);
				
				var dataType = $('.fn-flex-auto .current').attr("dataValue");
				wap._listService.loadListData(userId, unitId, searchType, applyType, dataType);
			},
			
			//清楚筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.SEARCH_TYPE);
				storage.remove(WapConstants.APPLY_TYPE);
				storage.remove(WapConstants.DATA_TYPE);
				$('.filter-layer li').removeClass('current');
				$('.condition-all').addClass('current');
			},
			
		},
		_editService : {
			
			init : function(){
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType = '';
				}
				
				if(id==null || typeof(id)=='undefined'){
					id = '';
				}
				
				wap._editService.initData(id,dataType);//初始化数据
				
				$('#back').click(function(){
					location.href = "repairList.html?isBack=1";
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
				$("#upFile").on('change',function(){
					var file = $(this).get(0).files[0];
					$("#fileName").val(file.name);
				});
			},
			
			save : function(dataType){
				
				if(!isActionable()){
					return false;
				}
				
				var unitClass = $("#unitClass").val();
				var equipmentType = $("#equipmentType").val();
				var goodsPlace = $("#goodsPlace").val();
				var phone = $("#phone").val();
				var goodsName = $("#goodsName").val();
				var type = $("#type").val();
				var detailTime = $("#detailTime").val();
				var remark = $("#remark").val();
				
				if($("#areaDivId").find('select').size() > 0){
					var teachAreaId = $("#teachAreaId").val();
					if(!isNotBlank(teachAreaId)){
						viewToast('校区不能为空');
						return;
					}
				}
				
				if((equipmentType == 1 || equipmentType == 2) && unitClass == WapConstants.UNIT_CLASS_2){
					var classId = $("#classId").val();
					if(!isNotBlank(classId)){
						viewToast('班级不能为空');
						return;
					}
				}
				
				if(!isNotBlank(goodsPlace)){
					viewToast('设备地点不能为空');
					return;
				}else{
					if(getStringLen(goodsPlace) > 100){
						viewToast('设备地点不能超过100字符');
						return;
					}
				}
				
				if(!isNotBlank(phone)){
					viewToast('联系电话不能为空');
					return;
				}else{
					if(getStringLen(phone) > 20 || !validateInteger(phone)){
						viewToast('请输入正确的联系电话（不大于20位数字）');
						return;
					}
				}
				
				if(!isNotBlank(goodsName)){
					viewToast('设备名称不能为空');
					return;
				}else{
					if(getStringLen(goodsName) > 60){
						viewToast('设备名称不能超过60字符');
						return;
					}
				}
				
				if(!isNotBlank(type)){
					viewToast('类别不能为空');
					return;
				}
				
				if(!isNotBlank(detailTime)){
					viewToast('报修时间不能为空');
					return;
				}else{
					$("#detailTimeVal").val(detailTime+":00");
				}
				
				if(!isNotBlank(remark)){
					viewToast('故障详情不能为空');
					return;
				}else{
					if(getStringLen(remark) > 200){
						viewToast('故障详情不能超过200字符');
						return;
					}
				}
				
				wapNetwork.doSave(storage.get(Constants.UNIT_ID),storage.get(Constants.USER_ID),dataType);
			},
			
			initData : function(id,dataType){
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetEdit(unitId, userId, id,dataType);
			},
			//用于绑定插件
			initBind : function(){
				var curr = new Date().getFullYear();
				var opt = {  
					'default': {
						theme: 'default',
						mode: 'scroller',
						display: 'modal',
						animate: 'fade'
					},
					'dateYMDHI': {
						preset: 'datetime',
						dateFormat: 'yyyy-mm-dd',
						defaultValue: new Date(new Date()),
						//invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] }
					},
					'select': {
						preset: 'select'
					},
				}

				$('#detailTime').scroller($.extend(opt['dateYMDHI'],opt['default']));
				$('#equipmentType').scroller($.extend(opt['select'],opt['default']));			
				$('#type').scroller($.extend(opt['select'],opt['default']));			
				$('#repaireTypeId').scroller($.extend(opt['select'],opt['default']));			
				
			},
		},
		_auditService : {
			
			init : function(){
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType = '';
				}
				
				if(id==null || typeof(id)=='undefined'){
					id = '';
				}
				
				wap._auditService.initData(id,dataType);//初始化数据
				
				$('#back').click(function(){
					location.href = "repairList.html?isBack=1";
				});
				
				$("#save").click(function(){
					wap._auditService.save(dataType);
				});
				
			},
			
			save : function(dataType){
				
				if(!isActionable()){
					return false;
				}
				
				var repaireTime = $("#repaireTime").val();
				var repaireRemark = $("#repaireRemark").val();
				
				if(!isNotBlank(repaireTime)){
					viewToast('维修时间不能为空');
					return;
				}else{
					$("#repaireTimeVal").val(repaireTime+":00");
				}
				if(getStringLen(repaireRemark) > 200){
					viewToast('维修备注不能超过200字符');
					return;
				}
				
				wapNetwork.doSave(storage.get(Constants.UNIT_ID),storage.get(Constants.USER_ID),dataType);
			},
			
			initData : function(id,dataType){
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetAudit(unitId, userId, id,dataType);
			},
			//用于绑定插件
			initBind : function(){
				var curr = new Date().getFullYear();
				var opt = {  
						'default': {
							theme: 'default',
							mode: 'scroller',
							display: 'modal',
							animate: 'fade'
						},
						'dateYMDHI': {
							preset: 'datetime',
							dateFormat: 'yyyy-mm-dd',
							defaultValue: new Date(new Date()),
							//invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] }
						},
						'select': {
							preset: 'select'
						},
				}
				
				$('#repaireTime').scroller($.extend(opt['dateYMDHI'],opt['default']));
				
			},
		},
		_detailService : {
			init : function(){
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType = '';
				}
				
				if(id==null || typeof(id)=='undefined'){
					id = '';
				}
				
				wap._detailService.initData(id,dataType);//初始化数据
				
				$('#back').click(function(){
					location.href = "repairList.html?isBack=1";
				});
				
				$("#save").click(function(){
					wap._detailService.save(dataType);
				});
				
				//评分
				$('.rating-star a').click(function(){
					var myIndex=$(this).index()+1;
					switch (myIndex){
					case 1:
						myText='失望';
						break;
					case 2:
						myText='不满';
						break;
					case 3:
						myText='一般';
						break;
					case 4:
						myText='满意';
						break;
					case 5:
						myText='惊喜';
						break;
					}
					$("#feedback").val(myText);
				});
			},
			
			save : function(dataType){
				
				if(!isActionable()){
					return false;
				}
				
				var feedbackStar = $('.rating-star .current').attr('dataValue');
				var feedback = $("#feedback").val();
				if(!isNotBlank(feedbackStar)){
					viewToast('请先进行评价打分');
					return;
				}else{
					$("#mark").val(feedbackStar);
				}
				if(!isNotBlank(feedback)){
					viewToast('反馈信息不能为空');
					return;
				}
				
				wapNetwork.doSave(storage.get(Constants.UNIT_ID),storage.get(Constants.USER_ID),WapConstants.DATA_TYPE_1);
			},
			
			initData : function(id,dataType){
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(unitId, userId, id,dataType);
			}
		},
		
};

