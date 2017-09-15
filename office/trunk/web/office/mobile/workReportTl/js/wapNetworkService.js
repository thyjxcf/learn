var wapNetworkService = {
		
	// 列表信息
	doLoadList : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadList(dataType, data);
	},
	
	doLoadDetail : function(dataType, data,isEdit){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadDetail(dataType, data,isEdit);
	},
	
	doSave : function(data){
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
				var conHtmlStr="";
				for(var i=0;i<cou;i++){
					var obj = array[i];
//					<li>
//	                    <span class="type type-green f-14">已通过</span>
//	                    <p class="tt f-18">出差参加教育储备展会</p>
//	                    <p class="dd f-14">
//	                        <span>公差</span>
//	                        <span>19:30</span>
//	                    </p>
//	                </li>
					var htmlStr = '<li';
					if(dataType == WapConstants.DATA_TYPE_1){
						if(obj.state == WapConstants.STATE_1){
							htmlStr+=' onclick="location.href=\'workReportEdit.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}else if(obj.state == WapConstants.STATE_2){
							htmlStr+=' onclick="location.href=\'workReportDetail.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}
					}else if(dataType == WapConstants.DATA_TYPE_0){
						htmlStr+=' onclick="location.href=\'workReportEdit.html?id='+obj.id+'&dataType='+dataType+'\';"';
					}
					htmlStr+='>';
					if(obj.state == WapConstants.STATE_1){
						htmlStr+='<span class="type type-blue f-14">未提交</span>';
					}else if(obj.state == WapConstants.STATE_2){
						htmlStr+='<span class="type type-green f-14">已提交</span>';
					}
					htmlStr+='<p class="tt f-18">'+obj.content+"</p>";
					htmlStr+='<p class="dd f-14">';
					if(dataType == WapConstants.DATA_TYPE_0){
						htmlStr+='<span>'+obj.createUserName+'</span>';
					}
					//htmlStr+='<span>'+obj.year+"学年"+'</span>';
					//htmlStr+='<span>'+"第"+obj.semester+"学期"+'</span>';
					htmlStr+='<span>'+"第"+obj.week+"周"+'</span>';
					htmlStr+='<span>'+obj.createTime+'</span>';
					htmlStr+='</p></li>';
					conHtmlStr+=htmlStr;
				}
				$('.ui-list').append(conHtmlStr);
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
			    	$('.loading-more').unbind();
			    }
				if(dataType == WapConstants.DATA_TYPE_0){
					$('#list').addClass('ui-list-full');
				}else{
					$('#list').removeClass('ui-list-full');
				}
				$('#list').show();
				$('#empty').hide();
			}else{
				$('#list').hide();
				$('#empty').show();
			}	
	    },
	    
	    _loadDetail : function(dataType, data,isEdit){
	    	var result=data.result_object;
	    	if(isEdit==WapConstants.BOOLEAN_0){
		    	$('#year').html(result.year);
		    	$('#semester').html("第"+result.semester+"学期");
		    	$('#week').html("第"+result.week+"周");
		    	$('#content').html(result.content);
	    	}else if(isEdit==WapConstants.BOOLEAN_1){
	    		var opt = {  
						'default': {
							theme: 'default',
							mode: 'scroller',
							display: 'modal',
							animate: 'fade'
						},
						'select': {
							preset: 'select'
						}
				}
	    		
	    		$("#userName").val(result.userName);
	    		$("#unitName").val(result.unitName);
	    		if(dataType == WapConstants.DATA_TYPE_0){
	    			$(".useShow").show();
	    		}
	    		$("#id").val(result.id);
	    		$("#createUserId").val(result.createUserId);
	    		$("#unitId").val(result.unitId);
	    		$('#year').val(result.year);
	    		if(dataType == WapConstants.DATA_TYPE_1 || !isNotBlank(result.id)){
	    			$("#semDivId").addClass('sel');
	    			var semSelHtml='<span class="dd">';
	    			semSelHtml+='<select id="semester" name="officeWorkReport.semester" class="demo-test-select txt" data-role="none">';
	    			if(result.semester == 1){
	    				semSelHtml+='<option value="1" selected="selected">'+"第1学期"+'</option>';
	    			}else{
	    				semSelHtml+='<option value="1">'+"第1学期"+'</option>';
	    			}
	    			if(result.semester == 2){
	    				semSelHtml+='<option value="2" selected="selected">'+"第2学期"+'</option>';
	    			}else{
	    				semSelHtml+='<option value="2">'+"第2学期"+'</option>';
	    			}
	    			semSelHtml+='</select>';
	    			semSelHtml+='</span>';
	    			$("#semDivId").append(semSelHtml);
	    			$("#semDivId").append('<i class="icon-arrow"></i>');
	    			
	    			$('#semester').scroller($.extend(opt['select'],opt['default']));
	    		}else if(dataType == WapConstants.DATA_TYPE_0){
	    			$("#semDivId").append('<span class="dd"><input type="text" id="semesterStr" class="txt" value="'+"第"+result.semester+"学期"+'" readonly><input type="hidden" id="semester" name="officeWorkReport.semester" class="txt" value="'+result.semester+'" readonly></span>');
	    		}
	    		
		    	//周次
		    	var week = result.week;
	    		var weeks = result.weeks;
	    		$("#week").html('');
	    		if(weeks.length > 0){
	    			var htmlStr = '';
	    			$.each(weeks, function(index, item) {
	    				if(isNotBlank(week) && item == week){
	    					htmlStr += '<option value="'+item+'" selected="selected">'+"第"+item+"周"+'</option>';
	    				}else{
	    					htmlStr += '<option value="'+item+'">'+"第"+item+"周"+'</option>';
	    				}
	    			});
	    			$("#week").html(htmlStr);
	    		}
				$('#week').scroller($.extend(opt['select'],opt['default']));
	    		
	    		if(!isNotBlank(result.id)){
	    			//新增
	    		}else{
	    			$('#content').val(result.content);
	    			
	    			$('#state').val(result.state);
	    			$('#unitClass').val(result.unitClass);
	    			$('#parentUnitId').val(result.parentUnitId);
	    			$('#unitOrderId').val(result.unitOrderId);
	    			$('#teacherOrderId').val(result.teacherOrderId);
	    			$('#createTime').val(result.createTime);
	    			
	    		}
	    	}
	    },
	    
	    _returnSuccess : function(){
	    	closeMsgTip();
	    	viewToast("操作成功",function(){
	    		$('#back').click();
	    	});
	    },
	    
  }
};