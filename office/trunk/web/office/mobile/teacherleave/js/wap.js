var wap = {
		init : function(contextPath, unitId, userId, userRealName){
			storage.set(Constants.UNIT_ID, unitId);
			storage.set(Constants.USER_ID, userId);
			storage.set(Constants.MOBILE_CONTEXT_PATH, contextPath);
			storage.set(Constants.USER_REALNAME, userRealName);
			location.href = contextPath + "/office/mobile/teacherleave/leaveList.html";
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
					if(dataType == WapConstants.DATA_TYPE_2){
						$("#queryText").attr('placeholder', '请输入汇报内容');
					}else{
						$("#queryText").attr('placeholder', '请输入汇报人姓名');
					}
					$("#queryText").val('');
					
					if(isBack==WapConstants.BACK_LIST_GLAG_1){
						//返回第一次刷新列表还原值
						isBack = WapConstants.BACK_LIST_GLAG_0;
					}else{
						//之后刷新列表刷新搜索条件
						storage.remove(WapConstants.LEAVE_TYPE_SEL);
						storage.remove(WapConstants.AUDIT_STATE_SEL);
					}
					var leaveType = storage.get(WapConstants.LEAVE_TYPE_SEL);
					var applyStatus = storage.get(WapConstants.AUDIT_STATE_SEL);
//					if(!isNotBlank(leaveType)){
//						leaveType='';
//					}
//					
					if(!isNotBlank(applyStatus)){
						applyStatus=0;
					}
					
					wap._listService.loadListData(unitId, userId, dataType, searchStr, leaveType, applyStatus);
				});
				
				//更多
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
					location.href = "leaveApplyEdit.html?dataType="+dataType;
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
					wap._listService.loadListData(unitId, userId, dataType, searchStr, leaveType, applyStatus);
				});
				
				//加载数据
				$('.tab-wrap ul li[value="'+dataType+'"]').addClass('current').siblings('li').removeClass('current');
				$('.tab-wrap ul li[value="'+dataType+'"]').trigger('touchstart');
				//wapNetwork.doGetList(unitId, userId, dataType, searchStr);
			},
			
			initQuerys : function(dataType){
				//请假
				var mcodes = JSON.parse(storage.get(WapConstants.LEAVE_TYPE_MCODES));
				var leaveType = storage.get(WapConstants.LEAVE_TYPE_SEL);
				var state = storage.get(WapConstants.AUDIT_STATE_SEL);
				var leaveTypeHtmlStr = '';
				
				if(!isNotBlank(leaveType)){
					leaveType='';
				}
				
				if(!isNotBlank(state)){
					state=0;
				}
				
				$.each(mcodes, function(index, json) {
					leaveTypeHtmlStr += '<li  value="'+json.thisId+'"><span>'+json.content+'<i class="icon-img icon-current"></i></span></li>';
				});
				$("#leaveType").html(leaveTypeHtmlStr);
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
				$('#leaveType li[value="'+leaveType+'"]').addClass('current');
				$('#auditStatus li[value="'+state+'"]').addClass('current');
			},
			
			//返回时加载数据
			backList:function(dataType){
				location.href="leaveList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			//加载第一页数据
			loadListData : function(unitId, userId, dataType, searchStr, leaveType, applyStatus){
				$('#listDiv').hide();
				$('#empty').hide();
				$("#list").html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(unitId, userId, dataType, searchStr);
				});
				//var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(unitId, userId, dataType, searchStr, leaveType, applyStatus);
			},
			
			//加载更多
			bindClickMore : function(unitId, userId, dataType, searchStr, applyStatus){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchStr = storage.get(WapConstants.SEARCH_STR);
			    wapNetwork.doMoreList(unitId, userId, dataType, searchStr, applyStatus, ++WapPage.pageIndex);
			},
		},
		
		_editService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var type = Request.type;
				var id = Request.id;
				if(dataType=='' || typeof(dataType)=='undefined'){
					dataType = WapConstants.DATA_TYPE_1;
				}
				
				wap._editService.initBind();//绑定
				wap._editService.initData(id, type);//初始化数据
				
				$("#cancelId").click(function(){
					wap._editService.clearCache();
					wap._listService.backList(dataType);
				});
				
				$("#save").click(function(){
					wap._editService.save(dataType);
				});
				
				//申请人
				$("#applyUserDiv").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var userids = storage.get(WapConstants.ADDRESS_USERIDS_1);
					var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
//					var returnurl = _contextPath + "/office/mobile/teacherleave/leaveApplyEdit.html";
//					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
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
					
					var returnurl = _contextPath + "/office/mobile/teacherleave/leaveApplyEdit.html?type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_2);//单选
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap/redirect.html?unitId="+_unitId;
					location.href = requesturl;
					
					//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
//					$("#page").hide();
//					$("#addressIframe").attr("src",requesturl);
//					$("#addressIframe").show();
					
					//参数
					storage.set(WapConstants.ADDRESS_TYPE, WapConstants.ADDRESS_TYPE_1);
				});
				//通知人员
				$("#noticeAddress").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
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
					
					var returnurl = _contextPath + "/office/mobile/teacherleave/leaveApplyEdit.html?type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_2);//本单位
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);//单选
					//storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.addressBackFunction");//通讯录回调函数
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap/redirect.html?unitId="+_unitId;
					location.href = requesturl;
//					$("#page").hide();
//					$("#addressIframe").attr("src",requesturl);
//					$("#addressIframe").show();
					
					//参数
					storage.set(WapConstants.ADDRESS_TYPE, WapConstants.ADDRESS_TYPE_2);
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
					wap._editService.addLeaveDaysDeatil(startDate,endDate);
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
					wap._editService.addLeaveDaysDeatil(startDate,endDate);
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
						htmlStr += '<span class="dd"><input type="number" id="detailTimeName'+ trIndex +'" tipName="'+dy+"年"+dm+"月"+'" onchange="wap._editService.changeDetailDays('+trIndex+')" class="txt" maxlength="2" value="" placeholder="请输入明细天数（必填）"></span>';
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
						htmlStr += '<span class="tt"><input type="hidden" id="detailTimeId'+ trIndex +'" name="detailTime" value="'+desTime+'_'+(parseInt(num)+1)+'"></span>';
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
				
				wap._editService.setUser(type, userIds, userNames);
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
//							htmlStr += '</span>';
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
						viewToast(tipName+"请假时间明细不能为空");
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
					viewToast('请假天数不能为空');
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
				wapNetwork.doSave(dataType);
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
					
					wap._editService.setUser(type, userIds, userNames);
					if(WapConstants.ADDRESS_TYPE_2==type){//通知人员
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_1);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_1);
						wap._editService.setUser(WapConstants.ADDRESS_TYPE_1, userids, usernames);
					}else{
						var userids = storage.get(WapConstants.ADDRESS_USERIDS_2);
						var usernames = storage.get(WapConstants.ADDRESS_USERNAMES_2);
						wap._editService.setUser(WapConstants.ADDRESS_TYPE_2, userids, usernames);
					}
					
					//获取缓存中之前已维护的数据
					var id = storage.get(WapConstants.EDIT_ID);
					var userId = storage.get(WapConstants.EDIT_USERID);
					var unitId = storage.get(WapConstants.EDIT_UNITID);
					var deptId = storage.get(WapConstants.EDIT_DEPTID);
					var startDate = storage.get(WapConstants.EDIT_START_DATE);
					var endDate = storage.get(WapConstants.EDIT_END_DATE);
					var leaveDays = storage.get(WapConstants.EDIT_LEAVE_DAYS);
					var leaveType = storage.get(WapConstants.EDIT_LEAVE_TYPE);
					var remark = storage.get(WapConstants.EDIT_REMARK);
					var leaveFlow = storage.get(WapConstants.EDIT_LEAVEFLOW);

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
					
					wap._editService.initBind();
				}else{
					//清缓存
					wap._editService.clearCache();
					
					var unitId = storage.get(Constants.UNIT_ID);
					var userId = storage.get(Constants.USER_ID);
					wapNetwork.doGetApplyDetail(unitId, userId, id);
				}
				
				var fileIndex=1;
				//附件
				$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="*/*">');
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
				});
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

				$('#startDate').scroller($.extend(opt['dateYMD'],opt['default']));
				$('#endDate').scroller($.extend(opt['dateYMD'],opt['default']));
				$('#leaveType').scroller($.extend(opt['select'],opt['default']));			
				$('#leaveFlow').scroller($.extend(opt['select'],opt['default']));			
				
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
				
				storage.set(WapConstants.EDIT_ID, id);
				storage.set(WapConstants.EDIT_USERID, userId);
				storage.set(WapConstants.EDIT_UNITID, unitId);
				storage.set(WapConstants.EDIT_DEPTID, deptId);
				storage.set(WapConstants.EDIT_START_DATE, startDate);
				storage.set(WapConstants.EDIT_END_DATE, endDate);
				storage.set(WapConstants.EDIT_LEAVE_DAYS, leaveDays);
				storage.set(WapConstants.EDIT_LEAVE_TYPE, leaveType);
				storage.set(WapConstants.EDIT_REMARK, remark);
				storage.set(WapConstants.EDIT_LEAVEFLOW, leaveFlow);
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
				
				storage.remove(WapConstants.EDIT_ID);
				storage.remove(WapConstants.EDIT_USERID);
				storage.remove(WapConstants.EDIT_UNITID);
				storage.remove(WapConstants.EDIT_DEPTID);
				storage.remove(WapConstants.EDIT_START_DATE);
				storage.remove(WapConstants.EDIT_END_DATE);
				storage.remove(WapConstants.EDIT_LEAVE_DAYS);
				storage.remove(WapConstants.EDIT_LEAVE_TYPE);
				storage.remove(WapConstants.EDIT_REMARK);
				storage.remove(WapConstants.EDIT_LEAVEFLOW);
			},
			
			//附件选择
			fileChange : function(objId,fileIndex){
				$("#"+objId).hide();
				var file = $("#"+objId).get(0).files[0];
				var fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
				var htmlStr='<li class="fn-clear new-file">';
				var laIn=file.name.lastIndexOf('.');
				if(laIn!=-1){
					htmlStr+=getFilePic(file.name.substring(laIn+1 ));
				}else{
					htmlStr+=getFilePic("");
				}
				htmlStr+='<a href="javascript:void(0)" class="icon-img icon-del2" onclick="wap._editService.attDel(this,\'upFile'+fileIndex+'\')"></a>';
				htmlStr+='<p class="acc-tt f-16">'+file.name+'</p>';
				htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
				htmlStr+='</li>';
				
				$("#accFooter").append(htmlStr);
				
				fileIndex++;
				$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="image/*">');
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
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