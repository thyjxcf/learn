var wapNetworkService = {
		
	// 列表信息
	doLoadList : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadList(dataType, data);
	},
	
	// 筛选信息
	doLoadSelectList : function(searchType, applyType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadSelectList(searchType, applyType, data);
	},
	
	doLoadDetail : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadDetail(dataType, data);
	},
	
	doLoadApplyDetail : function(unitId,userId,data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadApplyDetail(unitId,userId,data);
	},
	
	doSave : function(data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._returnSuccess();
	},
	doAudit : function(data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._returnSuccess();
	},
	doGiveBack : function(data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._returnSuccess();
	},
	
	//私有方法
	_service : {
		_doError : function(data) {
			if(typeof(data) == 'undefined' && data == 'undefined' && data == null && data == ''){
				closeMsgTip();
				viewToast("未获取到数据");
	        	return true;
	        }else if (data.result == Constants.FAILURE) {
	        	closeMsgTip();
	        	viewToast(data.msg);
	        	return true;
	        }
	        return false;
	      },
		_loadList : function(dataType, data) {
			WapPage = data.page;
			var array = data.result_array;
			var cou=array.length;
			if(cou>0){
				var conHtmlStr="";
				for(var i=0;i<cou;i++){
					var obj = array[i];
//                    	<li>
//	                    <span class="type type-orange f-14">待审核</span>
//	                    <p class="tt f-18">申请笔记本，数量2</p>
//	                    <p class="dd f-14">
//	                        <span>王青龙</span>
//	                        <span>办公用品</span>
//	                        <span>19:30</span>
//	                    </p>
//                		</li>
					var htmlStr = '<li';
					
					if(dataType == WapConstants.DATA_TYPE_0 || dataType == WapConstants.DATA_TYPE_1){
						htmlStr+=' onclick="location.href=\'goodsManagerDetail.html?id='+obj.id+'&dataType='+dataType+'\';"';
					}else if(dataType == WapConstants.DATA_TYPE_2){
						htmlStr+=' onclick="location.href=\'goodsManagerDetailApply.html?id='+obj.id+'\';"';
					}
					htmlStr+='>';
					if(dataType == WapConstants.DATA_TYPE_0 || dataType == WapConstants.DATA_TYPE_1){
						if(obj.state=="0"){
							htmlStr+='<span class="type type-gray f-14">' + '待审核';
						}else if(obj.state=="1"){
							htmlStr+='<span class="type type-green f-14">' + '已通过';
						}else if(obj.state=="2"){
							htmlStr+='<span class="type type-red f-14">' + '未通过';
						}else{
							htmlStr+='<span class="type type-green f-14">' + '已归还';
						}
						htmlStr+= '</span>';
						
						htmlStr+= '<p class="tt f-18">' + obj.name ;
						htmlStr+= '&nbsp;&nbsp;数量' + obj.amount;
						htmlStr+= '</p>';
						
						htmlStr+= '<p class="dd f-14">';
						if(dataType == WapConstants.DATA_TYPE_0){
							htmlStr+= '<span>' + obj.reqUserName +'&nbsp;</span>';
						}
						htmlStr+= '<span>' + obj.typeName +'</span>';
						htmlStr+= '<span>&nbsp;'+ obj.creationTime +'</span>';
						htmlStr+='</p>';
					}else if(dataType == WapConstants.DATA_TYPE_2){
						htmlStr+= '<p class="tt f-18">' + obj.name ;
						htmlStr+= '</p>';
						
						htmlStr+= '<p class="dd f-14">';
						htmlStr+= '<span>' + obj.typeName +'</span>';
						htmlStr+='</p>';
					}
					
					htmlStr+='</li>';
					conHtmlStr+=htmlStr;
				}
				$('.ui-list').append(conHtmlStr);
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
			    	$('.loading-more').unbind();
			    }
				$('#list').show();
				$('#empty').hide();
			}else{
				$('#list').hide();
				$('#empty').show();
			}	
	    },
	    
	    _loadSelectList : function(searchType, applyType, data) {
    		var result=data.result_array;
    		var containerHtml="";
//	    		<li class="current"><span>公差<i class="icon-img icon-current"></i></span></li>
//	    		<li><span>事假<i class="icon-img icon-current"></i></span></li>
    		if(!isNotBlank(searchType)){
    			containerHtml+='<li class="condition-all current" id=""><span>全部<i class="icon-img icon-current"></i></span></li>';
    		}else{
    			containerHtml+='<li class="condition-all" id=""><span>全部<i class="icon-img icon-current"></i></span></li>';
    		}
    		
    		if(result.length>0){
    			for(var i=0;i<result.length;i++){
    				var obj = result[i];
    				containerHtml += '<li';
    				if(searchType==obj.typeId){
    					containerHtml += ' class="current"';
    				}
    				containerHtml += ' id="'+obj.typeId+'">';
    				containerHtml += '<span>'+obj.typeName+'<i class="icon-img icon-current"></i></span></li>';
    			}
    		}
    		$(".goodsTypes").html(containerHtml);
	    },
	    
	    _loadDetail : function(dataType, data){
	    	var result=data.result_object;
	    	$('#creationTime').html(result.creationTime);
	    	$('#title').html(result.reqUserName+"申请"+result.name);
	    	$('#name').html(result.name);
	    	$('#typeName').html(result.typeName);
	    	$('#price').html(result.price);
	    	$('#model').html(result.model);
	    	$('#amount').html(result.amount);
	    	$('#reqRemark').html(result.reqRemark);
	    	$('#remark').html(result.remark);
	    	if(result.state == WapConstants.GOODS_AUDIT_PASS){
	    		$('.icon-state-yse').show();
	    	}else if(result.state == WapConstants.GOODS_AUDIT_NOT_PASS){
	    		$('.icon-state-no').show();
//	    		<ul class="ui-detail mt-20 f-14">
//	                <li class="fn-clear">
//	                    <span class="tt">审核意见：</span>
//	                    <span class="dd"></span>
//	                </li>
//	            </ul>
	    		var htmlStr='<ul class="ui-detail mt-20 f-14"><li class="fn-clear"><span class="tt">审核意见：</span>';
	    		htmlStr+='<span class="dd">'+result.advice+'</span>';
	    		htmlStr+='</li></ul>';
	    		$("#container").append(htmlStr);
	    	}
	    	if(dataType == WapConstants.DATA_TYPE_1 && result.state == WapConstants.GOODS_AUDIT_PASS && !result.isReturned){
	    		$("#giveBackDivId").show();
	    		$("#giveBack").click(function(){
	    			if(!isActionable()){
						return false;
					}
	    			showMsg("确定归还？",function(){
						wapNetwork.doGiveBack(storage.get(Constants.USER_ID),result.id);
					});
	    		});
	    	}else if(dataType == WapConstants.DATA_TYPE_0  && result.state == WapConstants.GOODS_NOT_AUDIT){
	    		$("#auditDivId").show();
	    		$("#yPass").click(function(){
	    			if(!isActionable()){
						return false;
					}
	    			wapNetwork.doAudit(storage.get(Constants.USER_ID),result.id,WapConstants.GOODS_AUDIT_PASS);
	    		});
	    		$("#nPassSubmit").on('touchstart',function(){
	    			if(!isActionable()){
						return false;
					}
	    			var nPassContent=$("#nPassContent").val();
	    			if(nPassContent==''){
	    				viewToast('审核意见不能为空');
						return;
					}
					if(getStringLen(nPassContent) > 1000){
						viewToast('审核意见不能超过1000字符');
						return;
					}
					wapNetwork.doAudit(storage.get(Constants.USER_ID),result.id,WapConstants.GOODS_AUDIT_NOT_PASS,nPassContent);
	    		});
	    	}
	    },
	    
	    _loadApplyDetail : function(unitId,userId,data){
	    	var result=data.result_object;
	    	$('#name').val(result.name);
	    	$('#typeName').val(result.typeName);
	    	$('#price').val(result.price);
	    	$('#model').val(result.model);
	    	$('#goodsUnit').val(result.goodsUnit);
	    	$('#amount').html(result.amount);
	    	$('#remark').val(result.remark);
	    },
	    
	    _returnSuccess : function(){
	    	closeMsgTip();
	    	viewToast("操作成功",function(){
	    		$('#back').click();
	    	});
	    },
	    
  }
};