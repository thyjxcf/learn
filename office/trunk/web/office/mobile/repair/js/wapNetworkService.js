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
	
	doLoadEdit : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadEdit(dataType, data);
	},
	doLoadAudit : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadAudit(dataType, data);
	},
	doLoadDetail : function(dataType, data){
		if (this._service._doError(data)) return;
		wapNetworkService._service._loadDetail(dataType, data);
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
					
					if(dataType == WapConstants.DATA_TYPE_0){
						if(obj.state == WapConstants.REPAIR_STATE_1){
							htmlStr+=' onclick="location.href=\'repairEdit.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}else{
							htmlStr+=' onclick="location.href=\'repairDetail.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}
					}else if(dataType == WapConstants.DATA_TYPE_1){
						if(obj.state == WapConstants.REPAIR_STATE_1 || obj.state == WapConstants.REPAIR_STATE_2){
							htmlStr+=' onclick="location.href=\'repairAudit.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}else{
							htmlStr+=' onclick="location.href=\'repairDetail.html?id='+obj.id+'&dataType='+dataType+'\';"';
						}
					}
					htmlStr+='>';
					if(obj.state=="1"){
						htmlStr+='<span class="type type-red f-14">' + '未维修';
					}else if(obj.state=="2"){
						htmlStr+='<span class="type type-blue f-14">' + '维修中';
					}else if(obj.state=="3"){
						htmlStr+='<span class="type type-green f-14">' + '已维修';
					}
					htmlStr+= '</span>';
						
					htmlStr+= '<p class="tt f-18">' + obj.goodsName +"&nbsp;"+ obj.goodsPlace ;
					htmlStr+= '</p>';
					
					htmlStr+= '<p class="dd f-14">';
					htmlStr+= '<span>' + obj.type +'</span>';
					htmlStr+= '<span>&nbsp;'+ obj.detailTime +'</span>';
					if(dataType == WapConstants.DATA_TYPE_1){
						htmlStr+= '<span>&nbsp;'+ obj.userName +'</span>';
					}
					if(obj.state=="3" && obj.isFeedback){
						htmlStr+= '<span>&nbsp;已反馈</span>';
					}else if(obj.state=="3" && !obj.isFeedback){
						htmlStr+= '<span>&nbsp;未反馈</span>';
					}
					htmlStr+='</p>';
					
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
    		$(".diffTypes").html(containerHtml);
	    },
	    
	    _loadEdit : function(dataType, data){
	    	var result=data.result_object;
	    	var opt = {  
				'default': {
					theme: 'default',
					mode: 'scroller',
					display: 'modal',
					animate: 'fade'
				},
				'select': {
					preset: 'select'
				},
			}
	    	$('#id').val(result.id);
	    	$('#userId').val(result.userId);
	    	$('#unitId').val(result.unitId);
	    	$('#unitClass').val(result.unitClass);
	    	$('#userName').val(result.userName);
	    	$('#phone').val(result.phone);
	    	$('#detailTime').val(result.detailTime);
	    	
	    	if(!isNotBlank(result.id)){
	    		//新增
	    		
	    	}else{
	    		//修改
	    		$('#goodsPlace').val(result.goodsPlace);
	    		$('#goodsName').val(result.goodsName);
	    		$('#remark').val(result.remark);
	    		//附件
	    		var attachmentArray = result.attachmentArray;
	    		if(attachmentArray.length > 0){
	    			var fileName = '';
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				if(json.fileExist){
	    					fileName = json.fileName;
	    					$("#fileName").val(fileName);
	    					return;
		    			}
	    			});
	    		}
	    	}
	    	
	    	var teachAreaId = result.teachAreaId;
	    	var teachAreaIdList = result.teachAreaIdList;
	    	var htmlStr = '<span class="dd">';
	    	htmlStr+='<select id="teachAreaId" name="officeRepaire.teachAreaId" class="demo-test-select txt" data-role="none">';
	    	htmlStr += '<option value="">请选择（必填）</option>';
    		if(teachAreaIdList.length > 0 && result.unitClass == WapConstants.UNIT_CLASS_2){
    			$.each(teachAreaIdList, function(index, item) {
    				if(isNotBlank(teachAreaId) && item.id == teachAreaId){
    					htmlStr += '<option value="'+item.id+'" selected="selected">'+item.name+'</option>';
    				}else{
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
    			htmlStr+='</select>';
    			htmlStr+='</span>';
    			htmlStr+='<i class="icon-arrow"></i>';
    			$("#areaDivId").append(htmlStr);
    			$('#teachAreaId').scroller($.extend(opt['select'],opt['default']));
    			$("#areaDivId").show();
    		}else{
    			$("#areaDivId").append('<input type="hidden" name="officeRepaire.teachAreaId" value="00000000000000000000000000000000">');
    		}
    		
    		var equipmentType = result.equipmentType;
	    	var equipmentTypeList = result.equipmentTypeList;
	    	$("#equipmentType").html('');
	    	var htmlStr = '<option value="">请选择</option>';
    		if(equipmentTypeList.length > 0){
    			$.each(equipmentTypeList, function(index, item) {
    				if(isNotBlank(equipmentType) && item.id == equipmentType){
    					htmlStr += '<option value="'+item.id+'" selected="selected">'+item.name+'</option>';
    				}else{
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
    		}
    		$("#equipmentType").html(htmlStr);
    		$('#equipmentType').scroller($.extend(opt['select'],opt['default']));
    		$("#equipmentType").on('change',function(e){
    			if(($("#equipmentType").val()==1 || $("#equipmentType").val()==2) && result.unitClass == WapConstants.UNIT_CLASS_2){
    				$("#classDivId").show();
    			}else{
    				$("#classDivId").hide();
    			}
    		});
			
    		var classId = result.classId;
	    	var classIdList = result.classIdList;
	    	var htmlStr = '<span class="dd">';
	    	htmlStr+='<select id="classId" name="officeRepaire.classId" class="demo-test-select txt" data-role="none">';
	    	htmlStr += '<option value="">请选择（必填）</option>';
    		if(classIdList.length > 0){
    			$.each(classIdList, function(index, item) {
    				if(isNotBlank(classId) && item.id == classId){
    					htmlStr += '<option value="'+item.id+'" selected="selected">'+item.name+'</option>';
    				}else{
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
    			htmlStr+='</select>';
    			htmlStr+='</span>';
    			htmlStr+='<i class="icon-arrow"></i>';
    		}
    		if(result.unitClass == WapConstants.UNIT_CLASS_2){
    			$("#classDivId").append(htmlStr);
    			$('#classId').scroller($.extend(opt['select'],opt['default']));
    			if($("#equipmentType").val()==1 || $("#equipmentType").val()==2){
    				$("#classDivId").show();
    			}
    		}
    		
    		var type = result.type;
	    	var typeList = result.typeList;
	    	$("#type").html('');
	    	var htmlStr = '<option value="">请选择（必填）</option>';
    		if(typeList.length > 0){
    			$.each(typeList, function(index, item) {
    				if(isNotBlank(type) && item.id == type){
    					htmlStr += '<option value="'+item.id+'" selected="selected">'+item.name+'</option>';
    				}else{
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
    		}
    		$("#type").html(htmlStr);
    		$('#type').scroller($.extend(opt['select'],opt['default']));
    		var officeRepaireTypeList = result.officeRepaireTypeList;
    		$("#type").on('change',function(e){
    			var typeVal=$("#type").val();
    			$("#repaireTypeId").html('');
    	    	var htmlStr = '<option value="">请选择</option>';
    			$.each(officeRepaireTypeList, function(index, item) {
    				if(typeVal == item.key){
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
        		$("#repaireTypeId").html(htmlStr);
        		$('#repaireTypeId').scroller($.extend(opt['select'],opt['default']));
    		});
    		
    		var repaireTypeId = result.repaireTypeId;
	    	var repaireTypeIdList = result.repaireTypeIdList;
	    	$("#repaireTypeId").html('');
	    	var htmlStr = '<option value="">请选择</option>';
    		if(repaireTypeIdList.length > 0){
    			$.each(repaireTypeIdList, function(index, item) {
    				if(isNotBlank(repaireTypeId) && item.id == repaireTypeId){
    					htmlStr += '<option value="'+item.id+'" selected="selected">'+item.name+'</option>';
    				}else{
    					htmlStr += '<option value="'+item.id+'">'+item.name+'</option>';
    				}
    			});
    		}
    		$("#repaireTypeId").html(htmlStr);
    		$('#repaireTypeId').scroller($.extend(opt['select'],opt['default']));
	    	
	    	wap._editService.initBind();
	    },
	    
	    _loadAudit : function(dataType, data){
	    	var result=data.result_object;
	    	$('#id').val(result.id);
	    	$('#userId').val(result.userId);
	    	$('#unitId').val(result.unitId);
	    	$('#unitClass').val(result.unitClass);
	    	var unitClass=result.unitClass;
	    	$("#userName").html(result.userName);
	    	if(unitClass == WapConstants.UNIT_CLASS_2 && isNotBlank(result.teachAreaId)){
	    		$("#teachAreaId").html(result.teachAreaId);
	    		$("#areaDivId").show();
	    	}
	    	$("#equipmentType").html(result.equipmentType);
	    	if(unitClass == WapConstants.UNIT_CLASS_2 && isNotBlank(result.classId)){
	    		$("#classId").html(result.classId);
	    		$("#classDivId").show();
	    	}
	    	$("#goodsPlace").html(result.goodsPlace);
	    	$("#phone").html(result.phone);
	    	$("#goodsName").html(result.goodsName);
	    	$("#type").html(result.type);
	    	$("#repaireTypeId").html(result.repaireTypeId);
	    	$("#detailTime").html(result.detailTime);
	    	$("#remark").html(result.remark);
	    	
	    	$("#repaireTime").val(result.repaireTime);
	    	if(result.state == WapConstants.REPAIR_STATE_2){
	    		$("#state2").attr("checked",true);
	    	}else if(result.state == WapConstants.REPAIR_STATE_3){
	    		$("#state3").attr("checked",true);
	    	}else{
	    		$("#state2").attr("checked",true);
	    	}
	    	$("#repaireRemark").val(result.repaireRemark);
	    	//附件
    		var attachmentArray = result.attachmentArray;
    		if(attachmentArray.length > 0){
    			var htmlStr = ''; 
    			$.each(attachmentArray, function(index, json){
    				var fileSize = getFileSize(json.fileSize);
    				htmlStr += '<li class="fn-clear">';
    				htmlStr += getFilePic(json.extName);
    				
    				if(json.fileExist){
	    				htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    			}else{
	    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
	    			}
//    				htmlStr += '<a href="#" class="icon-img icon-download"></a>';
    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
    				htmlStr += '</li>';
    			});
    			$("#fileDiv").html(htmlStr);
    		}
	    	wap._auditService.initBind();
	    },
	    
	    _loadDetail : function(dataType, data){
	    	var result=data.result_object;
	    	$('#id').val(result.id);
	    	$('#userId').val(result.userId);
	    	$('#unitId').val(result.unitId);
	    	$('#unitClass').val(result.unitClass);
	    	var unitClass=result.unitClass;
	    	$("#userName").html(result.userName);
	    	if(unitClass == WapConstants.UNIT_CLASS_2 && isNotBlank(result.teachAreaId)){
	    		$("#teachAreaId").html(result.teachAreaId);
	    		$("#areaDivId").show();
	    	}
	    	$("#equipmentType").html(result.equipmentType);
	    	if(unitClass == WapConstants.UNIT_CLASS_2 && isNotBlank(result.classId)){
	    		$("#classId").html(result.classId);
	    		$("#classDivId").show();
	    	}
	    	$("#goodsPlace").html(result.goodsPlace);
	    	$("#phone").html(result.phone);
	    	$("#goodsName").html(result.goodsName);
	    	$("#type").html(result.type);
	    	$("#repaireTypeId").html(result.repaireTypeId);
	    	$("#detailTime").html(result.detailTime);
	    	$("#remark").html(result.remark);
	    	$("#repaireTime").html(result.repaireTime);
	    	$("#repaireRemark").html(result.repaireRemark);
	    	if(result.state == WapConstants.REPAIR_STATE_3){
	    		$("#repShow").show();
		    	if(result.isFeedback){
		    		$("#feedbackShow").html(result.feedback);
		    		//$("#feedbackShowDiv").show();
		    		$("#feedback").val(result.feedback);
		    		var mark = result.mark;
		    		if(isNotBlank(mark)){
		    			if("1"==mark){
		    				$(".rating-star").html('<a href="javascript:void(0);" class="star1 current" dataValue="1"></a>');
		    			}else if("2"==mark){
		    				$(".rating-star").html('<a href="javascript:void(0);" class="star2 current" dataValue="2"></a>');
		    			}else if("3"==mark){
		    				$(".rating-star").html('<a href="javascript:void(0);" class="star3 current" dataValue="3"></a>');
		    			}else if("4"==mark){
		    				$(".rating-star").html('<a href="javascript:void(0);" class="star4 current" dataValue="4"></a>');
		    			}else if("5"==mark){
		    				$(".rating-star").html('<a href="javascript:void(0);" class="star5 current" dataValue="5"></a>');
		    			}else{
		    				$(".rating-star").html('<a href="javascript:void(0);" class="" dataValue="1"></a>');
		    			}
		    		}else{
		    			$(".rating-star").html('<a href="javascript:void(0);" class="" dataValue="1"></a>');
		    		}
		    		//$('.rating-star a').unbind();
		    		//$("#feedbackStarDiv").hide();
		    		$("#feedbackDiv").hide();
		    		$("#feedbackDetailDiv").show();
		    	}else if(dataType == WapConstants.DATA_TYPE_0){
		    		$("#feedback").val(result.feedback);
		    		$("#save").show();
		    		$("#feedbackDiv").show();
		    		$("#feedbackDetailDiv").hide();
		    	}
	    	}
	    	//附件
    		var attachmentArray = result.attachmentArray;
    		if(attachmentArray.length > 0){
    			var htmlStr = ''; 
    			$.each(attachmentArray, function(index, json){
    				var fileSize = getFileSize(json.fileSize);
    				htmlStr += '<li class="fn-clear">';
    				htmlStr += getFilePic(json.extName);
    				
    				if(json.fileExist){
	    				htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    			}else{
	    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
	    			}
//    				htmlStr += '<a href="#" class="icon-img icon-download"></a>';
    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
    				htmlStr += '</li>';
    			});
    			$("#fileDiv").html(htmlStr);
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