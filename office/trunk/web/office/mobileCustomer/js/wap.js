var wap = {
		
		initList : function(){//所有列表
			wap._listService.init();
		},
		
		initEdit : function(id){//编辑页
			wap._editService.init();
		},
		
		initCanReadDetail : function(){//抄送人我的客户中非本人客户---地区--本部--所有客户详情
			wap._canReaddetailService.init();
		},
		
		initFollowDetail : function(id){//跟踪记录详情
			wap._followDetailService.init();
		},
		
		initZYKDetail : function(id){//资源库详情
			wap._zykDetailService.init();
		},
		
		initAuditDetail : function(id){//审核详情
			wap._auditDetailService.init();
		},
		
		initFollowEdit : function(id){//跟踪记录编辑页
			wap._followEditService.init();
		},
		
		_listService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;//列表类型
				var isBack = Request.isBack;//是否查看返回
				if(isBack==null || typeof(isBack)=='undefined'){
					wap._listService.doRemoveCache();
				}
				
				var unitId = storage.get(Constants.UNIT_ID);
				var userId = storage.get(Constants.USER_ID);
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				
				var customerType = storage.get(WapConstants.CUSTOMER_TYPE);//客户类型
				var customerRegion = storage.get(WapConstants.CUSTOMER_REGION);//所在地区码
				var customerStatus = storage.get(WapConstants.CUSTOMER_STATUS);//客户进展状态or申请审核状态
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					if(roleType==WapConstants.ROLE_TYPE_4){
						dataType = WapConstants.DATA_TYPE_6;
					}else{
						dataType = WapConstants.DATA_TYPE_1;
					}
				}
				if(!isNotBlank(customerType)){
					customerType='';
				}
				if(!isNotBlank(customerRegion)){
					customerRegion='';
				}
				if(!isNotBlank(customerStatus)){
					customerStatus='';
				}
				var searchStr = "";//搜索字符（客户名称）
				//不同角色，底部tab不同
				if(roleType==WapConstants.ROLE_TYPE_1){
					$('footer .abtn-dq,.abtn-bd,.abtn-sh,.abtn-sy').hide();
				}else if(roleType==WapConstants.ROLE_TYPE_2){
					$('footer .abtn-bd,.abtn-sh,.abtn-sy').hide();
				}else if(roleType==WapConstants.ROLE_TYPE_3){
					$('footer .abtn-dq,.abtn-sy').hide();
				}else{
					$('footer .abtn-dq,.abtn-bd,.abtn-wd,.abtn-sq').hide();
				}
				
				if(dataType == WapConstants.DATA_TYPE_1||dataType == WapConstants.DATA_TYPE_2){
					initFooter('1');
					$('#list_tab_1').html("我的客户").val('1');
					$('#list_tab_2').html("延期申请").val('2');
					$('#list_tab_3').hide();
				}else if(dataType == WapConstants.DATA_TYPE_3||dataType == WapConstants.DATA_TYPE_4){
					initFooter('2');
					$('title').html("客户申请");
					if(roleType==WapConstants.ROLE_TYPE_1){
						$('#list_tab_1').html("我的申请").val('3');
						$('.tab-wrap').hide();
					}else{
						$('#list_tab_1').html("我的申请").val('3');
						$('#list_tab_2').html("分派申请").val('4');
						$('#list_tab_3').hide();
					}
					$("#queryText").attr('placeholder', '请输入客户名称');
					//wap._listService.loadListData(unitId, userId, dataType);
					$('footer .abtn-sq').addClass('active').siblings('a').removeClass('active');
				}else if(dataType == WapConstants.DATA_TYPE_5){
					initFooter('3');
					$('title').html("客户资源库");
					$('#list_tab_1').val('5');
					$('.tab-wrap').hide();
					$('#stateChoise').hide();
					$('#stateChoiseName').hide();
					$("#queryText").attr('placeholder', '请输入客户名称');
					//wap._listService.loadListData(unitId, userId, dataType);
					$('footer .abtn-zyk').addClass('active').siblings('a').removeClass('active');
				}else if(dataType == WapConstants.DATA_TYPE_6){
					if(roleType==WapConstants.ROLE_TYPE_2){
						initFooter('4');
						$('title').html("地区客户");
						$('footer .abtn-dq').addClass('active').siblings('a').removeClass('active');
					}else if(roleType==WapConstants.ROLE_TYPE_3){
						initFooter('5');
						$('title').html("本部客户");
						$('footer .abtn-bd').addClass('active').siblings('a').removeClass('active');
					}else{
						initFooter('6');
						$('title').html("所有客户");
						$('footer .abtn-sy').addClass('active').siblings('a').removeClass('active');
					}
					$('#list_tab_1').val('6');
					$('.tab-wrap').hide();
					$("#queryText").attr('placeholder', '请输入客户名称');
					//wap._listService.loadListData(unitId, userId, dataType);
				}else{
					initFooter('7');
					$('title').html("客户审核");
					$('#list_tab_1').html("申请审核").val('7');
					$('#list_tab_2').html("延期审核").val('8');
					$('#list_tab_3').html("分派审核").val('9');
					$("#queryText").attr('placeholder', '请输入客户名称');
					//wap._listService.loadListData(unitId, userId, dataType);
					$('footer .abtn-sh').addClass('active').siblings('a').removeClass('active');
				}
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					var searchStr = $("#queryText").val();
					wap._listService.bindClickSearchByCon(unitId, userId, dataType,searchStr);
					return false;
				});
				
				//搜索事件
				$('.ui-search-opt .btn,#confirm').click(function () { 
					var searchStr = $("#queryText").val();
					wap._listService.bindClickSearchByCon(unitId, userId, dataType,searchStr);
					return false;
				});
				
				//tab事件
				$('.tab-wrap ul li').bind('touchstart', function(){
					dataType = $(this).val();
					$("#queryText").attr('placeholder', '请输入客户名称');
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
						wap._listService.loadListData(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus);
					}else{
						$("#queryText").val('');
						searchStr = '';
						wap._listService.doRemoveCache();
						wap._listService.bindClickSearchByCon(unitId, userId, dataType,searchStr);
					}
				});
				
				//添加
				$("#apply").click(function(){
					var dataTypeStr = $('.tab-wrap ul').children('.current').val();
					if(dataTypeStr==0){
						dataTypeStr=dataType;
					}
					location.href = "customerApplyEdit.html?dataType="+dataTypeStr;
				});
				
				//取消
				$("#cancelId").click(function(){
					$(".html-window-close").click();
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
			},
			
			//通过筛选查询数据
			bindClickSearchByCon : function(userId, unitId,dataType,searchStr){
				var customerType = $("#customerTypeUl .current").attr("dataval");
				storage.set(WapConstants.CUSTOMER_TYPE, customerType);
				var val1 = $('#regionTxt .province').attr('dataValue');
				var val2 = $('#regionTxt .city').attr('dataValue');
				if(val2=='00')
					val2=''
				var val3 = $('#regionTxt .district').attr('dataValue');
				if(val3=='00')
					val3=''
				if(!isNotBlank(val1)){
					customerRegion='';
				}else{
					var customerRegion = val1+val2+val3;
				}
				storage.set(WapConstants.CUSTOMER_REGION, customerRegion);
				var customerStatus = $("#stateChoise .current").attr("dataval");
				storage.set(WapConstants.CUSTOMER_STATUS, customerStatus);
				wap._listService.loadListData(userId, unitId,dataType,searchStr,customerType,customerRegion,customerStatus);
			},
			//清除筛选缓存数据，恢复原样
			doRemoveCache : function(){
				storage.remove(WapConstants.WR_SEARCH_STR);
				storage.remove(WapConstants.CUSTOMER_TYPE);
				storage.remove(WapConstants.CUSTOMER_REGION);
				storage.remove(WapConstants.CUSTOMER_STATUS);
				$('.filter-layer li').removeClass('current');
				$('#regionTxt .province').attr('dataValue','');
				$('#region').html('');
//				$('.condition-all').addClass('current');
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="myCustomerList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').unbind();
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus);
				});
				wapNetwork.doGetList(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus);
			},
			//加载更多
			bindClickMore : function(unitId, userId, dataType, searchStr,customerType,customerRegion,customerStatus){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
			    wapNetwork.doMoreList(unitId, userId, dataType,searchStr,customerType,customerRegion,customerStatus,++WapPage.pageIndex);
			},
//			changeType : function(unitId, userId, dataType, searchStr,reportType,sendTime,collect){
//				var searchStr = $("#queryText").val();
//				if(collect==1){
//				}else{
//					 dataType = $('.tab-wrap ul').children('.current').val();
//					 reportType = $(".show-all").val();
//				}
//				if(sendTime==null || typeof(sendTime)=='undefined'){
//					sendTime = '';
//				}
//				wap._listService.loadListData(unitId, userId, dataType, searchStr,reportType,sendTime,collect);
//				return false;
//			}
			
		},
		
		_editService : {
			
			init : function(){
				
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var type = Request.type;
				var isBack = Request.isBack;
				
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				
				//wap._editService.initBind();
				wap._editService.initData(id, type,isBack);//初始化数据
				
				if(roleType!=WapConstants.ROLE_TYPE_4){
					$("#followUserLi").hide();
				}else{
					$("#save").html('提交');
					$('#followUserName').attr('placeholder','请选择（选填）');
				}
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				$("#save").click(function(){
					wap._editService.save(id,dataType);
				});
				
				//跟进人
				$("#followUserLi").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var _userId = storage.get(Constants.USER_ID);
					
					var returnurl = _contextPath + "/office/mobileCustomer/customerApplyEdit.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					storage.set(WapConstants.CUSTOMER_ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					//请求通讯录
					var requesturl = _contextPath + "/office/mobileCustomer/addressDetail.html?unitId="+_unitId+'&userId='+_userId+'&type=1';
					location.href = requesturl;
				});
				
			},
			
			initData : function(id,type,isBack){
				wapNetwork.doGetApplyDetail(id,type,isBack);
			},
			
			saveDataCache : function(){
				var name = $("#name").val();
				var nickName = $("#nickName").val();
				var region = $("#showRegionVal").val();
				var regionName = $("#showRegionName").val();
				var type = $("#type").val();
				var typeTxt = $("#typeTxt").val();//不可修改的类型
				var infoSource = $("#infoSource").val();
				var backgroundInfo = $("#backgroundInfo").val();
				var progressState = $("#progressState").val();
				var product = $("#product").val();
				var contact = $("#contact").val();
				var phone = $("#phone").val();
				var contactInfo = $("#contactInfo").val();
				var followUser = $("#followUser").val();
				var followUserName = $("#followUserName").val();
				
				storage.set(WapConstants.CUSTOMER_EDIT_NAME, name);
				storage.set(WapConstants.CUSTOMER_EDIT_NICKNAME, nickName);
				storage.set(WapConstants.CUSTOMER_EDIT_REGION, region);
				storage.set(WapConstants.CUSTOMER_EDIT_REGIONNAME, regionName);
				storage.set(WapConstants.CUSTOMER_EDIT_TYPE, type);
				storage.set(WapConstants.CUSTOMER_EDIT_TYPETXT, typeTxt);
				storage.set(WapConstants.CUSTOMER_EDIT_INFOSOURCE, infoSource);
				storage.set(WapConstants.CUSTOMER_EDIT_BACKGROUND_INFO, backgroundInfo);
				storage.set(WapConstants.CUSTOMER_EDIT_PROGRESS_STATE, progressState);
				storage.set(WapConstants.CUSTOMER_EDIT_PRODUCT, product);
				storage.set(WapConstants.CUSTOMER_EDIT_CONTACT, contact);
				storage.set(WapConstants.CUSTOMER_EDIT_PHONE, phone);
				storage.set(WapConstants.CUSTOMER_EDIT_CONTACT_INFO, contactInfo);
				
				storage.set(WapConstants.CUSTOMER_FOLLOW_USER_ID , followUser);
				storage.set(WapConstants.CUSTOMER_FOLLOW_USER_NAME , followUserName);
			},
			
			//清除缓存
			clearCache : function(){
				storage.remove(WapConstants.CUSTOMER_ADDRESS_RETURN_URL);
				storage.remove(WapConstants.CUSTOMER_FOLLOW_USER_ID);
				storage.remove(WapConstants.CUSTOMER_FOLLOW_USER_NAME);
				
				storage.remove(WapConstants.CUSTOMER_EDIT_NAME);
				storage.remove(WapConstants.CUSTOMER_EDIT_NICKNAME);
				storage.remove(WapConstants.CUSTOMER_EDIT_REGION);
				storage.remove(WapConstants.CUSTOMER_EDIT_REGIONNAME);
				storage.remove(WapConstants.CUSTOMER_EDIT_TYPE);
				storage.remove(WapConstants.CUSTOMER_EDIT_TYPETXT);
				storage.remove(WapConstants.CUSTOMER_EDIT_INFOSOURCE);
				
				storage.remove(WapConstants.CUSTOMER_EDIT_BACKGROUND_INFO);
				storage.remove(WapConstants.CUSTOMER_EDIT_PROGRESS_STATE);
				storage.remove(WapConstants.CUSTOMER_EDIT_PRODUCT);
				storage.remove(WapConstants.CUSTOMER_EDIT_CONTACT);
				storage.remove(WapConstants.CUSTOMER_EDIT_PHONE);
				storage.remove(WapConstants.CUSTOMER_EDIT_CONTACT_INFO);
			},
			
			save : function(id,dataType){
				if(!isActionable()){
					return false;
				}
				
				var name = $("#name").val();
				var nickName = $("#nickName").val();
				var region = $("#showRegionVal").val();
				var type = $("#type").val();
				var typeTxt = $("#typeTxt").val();
				var infoSource = $("#infoSource").val();
				var backgroundInfo = $("#backgroundInfo").val();
				var progressState = $("#progressState").val();
				var product = $("#product").val();
				var contact = $("#contact").val();
				var phone = $("#phone").val();
				var contactInfo = $("#contactInfo").val();
				var followUser = $("#followUser").val();
				
				
				if(!isNotBlank(name)){
					viewToast('官方名称不能为空');
					return;
				}else{
					if(getStringLen(name) > 100){
						viewToast('官方名称不能超过100字符');
						return;
					}
				}
				
				if(getStringLen(nickName) > 100){
					viewToast('地方名称不能超过100字符');
					return;
				}
				
				if(!isNotBlank(region)||region=='NaN'){
					viewToast('请选择客户所在地');
					return;
				}
				
				if(!isNotBlank(typeTxt)){
					if(!isNotBlank(type)){
						viewToast('请选择客户类别');
						return;
					}
				}
				
				if(!isNotBlank(infoSource)){
					viewToast('客户来源不能为空');
					return;
				}else{
					if(getStringLen(infoSource) > 200){
						viewToast('客户来源不能超过200字符');
						return;
					}
				}
				
				if(!isNotBlank(backgroundInfo)){
					viewToast('客户背景不能为空');
					return;
				}else{
					if(getStringLen(backgroundInfo) > 200){
						viewToast('客户背景不能超过200字符');
						return;
					}
				}
				
				if(!isNotBlank(progressState)){
					viewToast('请选择客户状态');
					return;
				}
				
				if(!isNotBlank(product)){
					viewToast('请选择意向合作产品');
					return;
				}
				
				if(!isNotBlank(contact)){
					viewToast('联系人不能为空');
					return;
				}else{
					if(getStringLen(contact) > 100){
						viewToast('联系人不能超过100字符');
						return;
					}
				}
				
				if(!isNotBlank(phone)||phone.indexOf('.')>0){
						viewToast('联系方式1不能为空且只能为数字');
					return;
				}else{
					if(getStringLen(phone) > 30){
						viewToast('联系方式1不能超过30字符');
						return;
					}
				}
				
				if(getStringLen(contactInfo) > 100){
					viewToast('联系方式2不能超过100字符');
					return;
				}
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				$("#userId").val(userId);
				$("#unitId").val(unitId);
				$("#id").val(id);
				wapNetwork.doSave(dataType);
			},
			
			initBind : function(){
				$('#progressState,#type,#product').mobiscroll().select({
			        theme: 'mobiscroll',
			        lang: 'zh',
			        display: 'bottom',
			        minWidth: 200
			    });	
//				$('#product').mobiscroll().select({
//					theme: 'mobiscroll',
//					lang: 'zh',
//					display: 'bottom',
//					select: 'multiple',
//					minWidth: 200
//				});	
			},
		},
		
		_canReaddetailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var isBack = Request.isBack;
				var isOver = Request.isOver;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				
				if(roleType==WapConstants.ROLE_TYPE_1){
					$('title').html("小组客户详情");
				}else if(roleType==WapConstants.ROLE_TYPE_2){
					$('title').html("地区客户详情");
				}else if(roleType==WapConstants.ROLE_TYPE_3){
					$('title').html("本部客户详情");
				}else{
					$('title').html("所有客户详情");
				}
				//返回
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				//改跟进人提交
				$("#saveFollowMan").click(function(){
					wap._canReaddetailService.saveFollowMan(id,roleType,dataType);
				});
				//tab切换
				$('.tab-wrap ul li').click(function(){
					var readDataType = $(this).val();
					if(readDataType==2){
						$("#customerInfo").hide();
						$("#followInfo").show();
					}else{
						$("#followInfo").hide();
						$("#customerInfo").show();
						
					}
				});
				//跟进人
				$("#followUserLi").click(function(){
					//已维护的数据缓存起来
					wap._canReaddetailService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var _userId = storage.get(Constants.USER_ID);
					
					var returnurl = _contextPath + "/office/mobileCustomer/customerIcanReadDetail.html?dataType="+dataType+'&id='+id+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					storage.set(WapConstants.CUSTOMER_ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					//请求通讯录
					var requesturl = _contextPath + "/office/mobileCustomer/addressDetail.html?unitId="+_unitId+'&userId='+_userId+'&type=1';
					location.href = requesturl;
				});
				
				wapNetwork.doGetCanReadDetail(dataType,id,isBack);
				if(isOver=='1'){
					$("#followUserLi").unbind('click');
					$("#followUserTxt").next('.icon-arrow').hide();
					$("#saveFollowMan").hide();
				}
			},
			
			saveFollowMan : function(id,roleType,dataType){
				if(!isActionable()){
					return false;
				}
				var followUser = $("#followUser").val();
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				if(roleType!=WapConstants.ROLE_TYPE_1){
					if(!isNotBlank(followUser)){
						viewToast('跟进人不能为空');
						return;
					}
				}
				
				wapNetwork.doSaveFollowMan(id,roleType,dataType);
			},
			saveDataCache : function(){
				var followUser = $("#followUser").val();
				var followUserName = $("#followUserName").val();
				
				storage.set(WapConstants.CUSTOMER_FOLLOW_USER_ID , followUser);
				storage.set(WapConstants.CUSTOMER_FOLLOW_USER_NAME , followUserName);
			},
			
		},
		
		_zykDetailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var isBack = Request.isBack;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_5;
				}
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				//返回
				if(roleType==WapConstants.ROLE_TYPE_1){
					$("#dataForm").hide();	
				}
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				//提交跟进人
				$("#saveFollowMan").click(function(){
					wap._canReaddetailService.saveFollowMan(id,roleType,dataType);
				});
				if(roleType==WapConstants.ROLE_TYPE_4){
					$("#saveFollowMan a").html('确定');
				}
				//跟进人
				$("#followUserLi").click(function(){
					//已维护的数据缓存起来
					wap._canReaddetailService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var _userId = storage.get(Constants.USER_ID);
					
					var returnurl = _contextPath + "/office/mobileCustomer/customerLibrary.html?dataType="+dataType+'&id='+id+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					storage.set(WapConstants.CUSTOMER_ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					//请求通讯录
					var requesturl = _contextPath + "/office/mobileCustomer/addressDetail.html?unitId="+_unitId+'&userId='+_userId+'&type=1';
					location.href = requesturl;
				});
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				$("#userId").val(userId);
				$("#unitId").val(unitId);
				$("#id").val(id);
				wapNetwork.doZYKDetail(dataType,id,isBack);
			},
			
			initBind : function(){
				$('#progressState').mobiscroll().select({
			        theme: 'mobiscroll',
			        lang: 'zh',
			        display: 'bottom',
			        minWidth: 200
			    });	
			},
			
		},
		
		_auditDetailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var applyId = Request.applyId;
				var auditId = Request.auditId;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_3;
				}
				if(applyId=='undefined'){
					applyId = '';
				}
				if(auditId=='undefined'){
					auditId = '';
				}
				if(dataType==WapConstants.DATA_TYPE_2||dataType==WapConstants.DATA_TYPE_3||dataType==WapConstants.DATA_TYPE_4){
					$('title').html("申请详情");
				}else{
					layer('.no-pass','.ui-layer','.close','不通过的原因');
				}
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				//返回
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				$("#saveReason").on('touchstart',function(){
					$("#auditStatus").val(3);
					var reason = $("#reason").val();
					if(!isNotBlank(reason)){
						viewToast('审核意见不能为空');
						return;
					}else{
						if(getStringLen(reason) > 250){
							viewToast('审核意见不能超过250字符');
							return;
						}
					}
					wapNetwork.doAudit(dataType,id);
				});
				
				$(".ui-footer .submit").click(function(){
					if(dataType==WapConstants.DATA_TYPE_2||dataType==WapConstants.DATA_TYPE_3||dataType==WapConstants.DATA_TYPE_4){
						location.href = "customerApplyEdit.html?dataType="+dataType+'&id='+id;
					}else{
						//通过
						$("#auditStatus").val(2);
						wapNetwork.doAudit(dataType,id);
					}
				});
				
				$(".ui-footer .no-pass").click(function(){
					if(dataType==WapConstants.DATA_TYPE_2||dataType==WapConstants.DATA_TYPE_3||dataType==WapConstants.DATA_TYPE_4){
						//放弃申请
						//wapNetwork.doGiveUpApply(dataType,id);
						//删除
						wapNetwork.doDelete(dataType,id);
					}else{
						//不通过
					}
				});
				var userId = storage.get(Constants.USER_ID);
				var unitId = storage.get(Constants.UNIT_ID);
				$("#userId").val(userId);
				$("#unitId").val(unitId);
				$("#id").val(id);
				wapNetwork.doAuditDetail(dataType,id,applyId,auditId);
			},
			
		},
		
		_followDetailService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var id = Request.id;
				var isBack = Request.isBack;
				var expiryDate = Request.expiryDate;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
				if(expiryDate=='undefined' || typeof(expiryDate)=='undefined'){
					expiryDate = "1";
				}
				if(Number(expiryDate)>=0){
					$("#delayLi").hide();
				}
				if(isBack==WapConstants.BACK_LIST_GLAG_1){
					$("#followInfo").hide();
					$("#delayInfo").hide();
					$("#customerInfoForm").show();
					$("#customerInfoLi").addClass('current').siblings('li').removeClass('current');;
				}else{
					$("#customerInfoForm").hide();
				}
				//返回
				$("#cancelId").click(function(){
					wap._listService.backList(dataType);
				});
				
				//修改我的客户信息
				$("#saveCusInfo").click(function(){
					wap._editService.save(id,dataType);
				});
				
				//保存延期信息
				$("#saveDelayInfo").click(function(){
					wap._followDetailService.saveDelayInfo(id,dataType);
				});
				
				//添加跟进记录
				$("#addFollowRecord").click(function(){
					location.href = "myCustomerFollowEdit.html?dataType="+dataType+'&id='+id+'&expiryDate='+expiryDate;
				});
				
				//跟进人
				$("#followUserLi").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var _userId = storage.get(Constants.USER_ID);

					var returnurl = _contextPath + '/office/mobileCustomer/myCustomerFollow.html?dataType='+dataType+'&id='+id+'&expiryDate='+expiryDate+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
					storage.set(WapConstants.CUSTOMER_ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					//请求通讯录
					var requesturl = _contextPath + "/office/mobileCustomer/addressDetail.html?unitId="+_unitId+'&userId='+_userId+'&type=1';
					location.href = requesturl;
				});
				
				//tab切换
				$('.tab-wrap ul li').click(function(){
					var readDataType = $(this).val();
					if(readDataType==1){
						$("#followInfo").hide();
						$("#delayInfo").hide();
						$("#customerInfoForm").show();
					}else if(readDataType==2){
						$("#delayInfo").hide();
						$("#customerInfoForm").hide();
						$("#followInfo").show();
					}else{
						$("#customerInfoForm").hide();
						$("#followInfo").hide();
						$("#delayInfo").show();
					}
				});
				
				wap._editService.initData(id,dataType,isBack);
				$("#type").hide();
				if(roleType==WapConstants.ROLE_TYPE_1){
					$("#followUserLi").hide();
				}
				wapNetwork.doGetFollowDetail(dataType,id);
			},
			
			saveDelayInfo : function(id,dataType){
				if(!isActionable()){
					return false;
				}
				var delayApllyInfo = $("#delayApllyInfo").val();
				
				if(!isNotBlank(delayApllyInfo)){
					viewToast('延期申请信息不能为空');
					return;
				}else{
					if(getStringLen(delayApllyInfo) > 500){
						viewToast('延期申请信息不能超过500字符');
						return;
					}
				}
				$("#applyId").val(id);
				var userId = storage.get(Constants.USER_ID);
				$("#delayUserId").val(userId);
				wapNetwork.doSaveDelayInfo(id,dataType);
			},
		},
			
			_followEditService : {
				
				init : function(){
					
					var Request = new UrlSearch();
					var dataType = Request.dataType;
					var id = Request.id;
					var isBack = Request.isBack;
					var expiryDate = Request.expiryDate;
					
					if(dataType=='' || typeof(dataType)=='undefined'){
						dataType = WapConstants.DATA_TYPE_1;
					}
					var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
					
					//wap._followEditService.initBind();//初始化数据
					wap._followEditService.initData(id,dataType,isBack);//初始化数据
					
					if(roleType!=WapConstants.ROLE_TYPE_1){
						$("#copyToLi").hide();	
					}
					$("#cancelId").click(function(){
						location.href='myCustomerFollow.html?dataType='+dataType+'&id='+id+'&expiryDate='+expiryDate;
					});
					
					$("#save").click(function(){
						wap._followEditService.save(id,dataType,expiryDate);
					});
					
					//抄送人
					$("#copyToLi").click(function(){
						//已维护的数据缓存起来
						wap._followEditService.saveDataCache();
						
						//通讯录需求的几个参数
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						var _unitId = storage.get(Constants.UNIT_ID);
						var _userId = storage.get(Constants.USER_ID);
						
						var returnurl = _contextPath + "/office/mobileCustomer/myCustomerFollowEdit.html?dataType="+dataType+'&id='+id+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
						storage.set(WapConstants.CUSTOMER_ADDRESS_RETURN_URL, returnurl);//绝对路径
						
						//请求通讯录
						var requesturl = _contextPath + "/office/mobileCustomer/addressDetail.html?unitId="+_unitId+'&userId='+_userId+'&type=2';
						location.href = requesturl;
					});
					
				},
				
				initData : function(id,dataType,isBack){
					wapNetwork.doFollowEdit(id,dataType,isBack);
				},
				
				saveDataCache : function(){
					var copyTo = $("#copyTo").val();
					var copyToName = $("#copyToName").val();
					var followUp = $("#followUp").val();
					var followInfo = $("#followInfo").val();
					
					storage.set(WapConstants.CUSTOMER_FOLLOW_USER_ID , copyTo);
					storage.set(WapConstants.CUSTOMER_FOLLOW_USER_NAME , copyToName);
					storage.set(WapConstants.CUSTOMER_FOLLOW_UP , followUp);
					storage.set(WapConstants.CUSTOMER_FOLLOW_INFO , followInfo);
				},
				
				//清除缓存
				clearCache : function(){
					storage.remove(WapConstants.CUSTOMER_ADDRESS_RETURN_URL);
					storage.remove(WapConstants.CUSTOMER_FOLLOW_USER_ID);
					storage.remove(WapConstants.CUSTOMER_FOLLOW_USER_NAME);
					
					storage.remove(WapConstants.CUSTOMER_FOLLOW_UP);
					storage.remove(WapConstants.CUSTOMER_FOLLOW_INFO);
				},
				
				save : function(id,dataType,expiryDate){
					if(!isActionable()){
						return false;
					}
					
					var copyTo = $("#copyTo").val();
					var followUp = $("#followUp").val();
					var followInfo = $("#followInfo").val();
					
					var roleType = storage.get(Constants.ROLE_TYPE);//用户角色
					if(roleType==WapConstants.ROLE_TYPE_1){
						if(!isNotBlank(copyTo)){
							viewToast('请选择抄送人');
							return;
						}
					}else{
						$("#copyTo").val('');
					}
					
					if(!isNotBlank(followUp)){
						viewToast('请选择跟进状态');
						return;
					}
					
					if(!isNotBlank(followInfo)){
						viewToast('跟进信息不能为空');
						return;
					}else{
						if(getStringLen(followInfo) > 1000){
							viewToast('跟进信息不能超过1000字符');
							return;
						}
					}
					
					$("#applyId").val(id);
					wapNetwork.doSaveFollowInfo(id,dataType,expiryDate,followUp);
				},
				
				initBind : function(){
					$('#followUp').mobiscroll().select({
				        theme: 'mobiscroll',
				        lang: 'zh',
				        display: 'bottom',
				        minWidth: 200
				    });		
				},
			},
		
};  