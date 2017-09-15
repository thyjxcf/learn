var wapNetwork={
		
		doGetClassRank:function(studentId,queryDateStr){
			var url = "/common/open/health/health-getClassRank.action";
			showLoading("正在加载数据……");
			officeAjax.doAjax(url,{'studentId':studentId,'queryDateStr':queryDateStr}, function(data) {
				closeMsgTip();
		    	if(data.result==Constants.SUCCESS){
		    		wapNetworkService.doGetClassRank(data,studentId);
		    	}else{
		    		if(data.msg!=''){
		    			viewToast(data.msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			}, 'json').error(function(data) {
		    	closeMsgTip();
		    	viewToast("连接出错");
		    });
		},
		
		doGetDetail : function(ownerId,studentId,dateType,addOrLess,queryDateStr){
			var url = "/common/open/health/health-getDetail.action";
			showLoading("正在加载数据……");
			officeAjax.doAjax(url,{"ownerId":ownerId,'studentId':studentId,'dateType':dateType,'addOrLess':addOrLess,'queryDateStr':queryDateStr}, function(data) {
				closeMsgTip();
		    	if(data.result==Constants.SUCCESS){
		    		$(".centerContainer").show();
		    		wapNetworkService.doGetDetail(data,ownerId,studentId,dateType);
		    	}else{
		    		$(".centerContainer").hide();
		    		if(data.msg!=''){
		    			viewToast(data.msg);
		    		}else{
		    			viewToast('操作失败');
		    		}
		    	}
			}, 'json').error(function(data) {
		    	closeMsgTip();
		    	viewToast("连接出错");
		    });
		},
		
		
};