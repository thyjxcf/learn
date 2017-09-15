var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId,dataType,date) {
		var url = "/common/open/officemobile/schedule-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'dataType':dataType,'calendar.calendarTime':date,'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(dataType,data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--schedule-list error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId,dataType,date,pageIndex) {
		var url = "/common/open/officemobile/schedule-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'dataType':dataType,'calendar.calendarTime':date,'pageLastDate':storage.get(WapConstants.PAGE_LAST_DATE), 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(dataType,data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--schedule-list error.');
	    });
	},
	//获取更多信息
	doGetCalendar : function(unitId, userId,date,backAndForth,dataType) {
		var url = "/common/open/officemobile/schedule-calendar.action";
		var lastChoiseDay = storage.get(WapConstants.CHOISE_DAY);
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'calDto.calendarTime':date,'backAndForth':backAndForth,'lastChoiseDay':lastChoiseDay,'dataType':dataType}, function(data) {
			wapNetworkService.doLoadCalendar(data);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--schedule-calendar error.');
		});
	},
	
	//申请
	doGetApplyDetail : function(unitId, userId,id){
		url = "/common/open/officemobile/schedule-apply.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,"id":id}, function(data) {
			wapNetworkService.doLoadApplyDetail(id,data);
		}, 'json').error(function(data) {
			console.log('[error] wapNetwork--schedule-detail error.');
		});
	},
	//详情
	doGetDetail : function(unitId, userId,dataType,id){
		url = "/common/open/officemobile/schedule-detail.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'dataType':dataType,"id":id}, function(data) {
	    	wapNetworkService.doLoadDetail(data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--schedule-detail error.');
	    });
	},
	//提交申请
	doSave : function(dataType){
		showLoading("正在处理……");
		//ajax表单提交
		var options = {
			url : storage.get(Constants.MOBILE_CONTEXT_PATH)+"/common/open/officemobile/schedule-save.action",
			success : function(data){
				closeMsgTip();

				if(data.result==Constants.SUCCESS){
					viewToast('操作成功', function(){
						wap._listService.backList(dataType);
					});
		    	}else{
		    		var msg = data.msg;
		    		if(isNotBlank(msg)){
		    			viewToast(msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			},
			dataType : 'json',
			clearForm : false,
			resetForm : false,
			type : 'post',
			error:function(XMLHttpRequest, textStatus, errorThrown){
				closeMsgTip();
				viewToast(XMLHttpRequest.status);
			}//请求出错 
		};
	  	$("#dataForm").ajaxSubmit(options);
	},
};