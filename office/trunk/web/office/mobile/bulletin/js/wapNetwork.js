var wapNetwork = {
		
	// 获取列表信息
	doGetList : function(unitId, userId) {
		var url = "/common/open/officemobile/bulletin-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'pageSize':Constants.PATE_SIZE,'pageIndex':1}, function(data) {
	    	wapNetworkService.doLoadList(data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--bulletin-list error.');
	    });
	},
	
	//获取更多信息
	doMoreList : function(unitId, userId,pageIndex) {
		var url = "/common/open/officemobile/bulletin-list.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,'pageLastDate':storage.get(WapConstants.PAGE_LAST_DATE), 'pageSize':Constants.PATE_SIZE,'pageIndex':pageIndex}, function(data) {
	    	wapNetworkService.doLoadList(data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--bulletin-list error.');
	    });
	},
	
	//详情
	doGetDetail : function(unitId, userId,id){
		url = "/common/open/officemobile/bulletin-detail.action";
		officeAjax.doAjax(url, {'unitId':unitId, 'userId':userId,"id":id}, function(data) {
	    	wapNetworkService.doLoadDetail(data);
	    }, 'json').error(function(data) {
		   console.log('[error] wapNetwork--bulletin-detail error.');
	    });
	},
};