var wapNetworkService = {
		
	// 工作汇报列表信息
	doLoadList : function(dataType, searchStr,reportType, sendTime,collect,data){
		wapNetworkService._service._loadList(dataType, searchStr,reportType,sendTime,collect, data);
	},
	
	// 工作汇报详情信息
	doLoadDetail : function(dataType, data){
		wapNetworkService._service._doLoadDetail(dataType, data);
	},
	// 工作汇报编辑
	doLoadEdit:function(dataType,id,data){
		wapNetworkService._service._doLoadEdit(dataType,id,data);
	},
	
	//私有方法
	_service : {
		_doLoadEdit :function(dataType,id,data){
			if(data.result==Constants.SUCCESS){
				var obj = data.result_object;
				if(isNotBlank(id)){//编辑
					var reportType=obj.reportType;
					$("#reportType").val(reportType);
					if(reportType==2){
						$("#reportType option:nth-child(2)").attr('selected', 'selected');
					}
					
					$("#id").val(id);
					$("#startDate").val(obj.beginTime);
					$("#endDate").val(obj.endTime);
					$("#content").val(obj.content);
					//接收人
					wap._editService.setUser(obj.receiveUserIds, obj.receiveUserNames,obj.userPhotos);
					//附件
		    		var attachmentArray = obj.attachmentArray;
		    		if(attachmentArray.length > 0){
		    			var fileName = '';
		    			$.each(attachmentArray, function(index, json){
		    				//var fileSize = getFileSize(json.fileSize);
		    				if(json.fileExist){
		    					fileName = json.fileName;
		    					$("#fileName").val(fileName);
		    					$("#everAttachId").val(json.id);
		    					return;
			    			}
		    			});
		    		}
				}
				wap._editService.initBind();
			}
		},
		_loadList : function(dataType, searchStr,reportType,sendTime,collect,data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				storage.set(WapConstants.WR_SEARCH_STR, searchStr);
				var array = data.result_array;
				if(dataType == WapConstants.DATA_TYPE_1){
					var receiveNum = data.receiveNum;
					if(receiveNum>99){
						receiveNum = '99+';
					}
					$("#wait-dis").html("("+receiveNum+")");
					storage.set(WapConstants.RECEIVE_NUMBER, receiveNum);
				}else{
					$("#wait-dis").html("("+storage.get(WapConstants.RECEIVE_NUMBER)+")");
				}
				if(array.length > 0){
					var listHtmlStr = '';
					//改变列表样式
					if(dataType == WapConstants.DATA_TYPE_3){
						$("#list").attr('class', 'group f-17');
						$.each(array, function(index, json) {
						var	htmlInfo = 'location.href=\'workreportList.html?sendTime='+json.time+'&dataType=1&collect=1';
						if(reportType==WapConstants.REPORT_TYPE_2){
							htmlInfo += '&reportType='+WapConstants.REPORT_TYPE_2;
						}else{
							htmlInfo += '&reportType='+WapConstants.REPORT_TYPE_1;
						}
						htmlInfo += '\''
						listHtmlStr += '<li onclick="'+htmlInfo+'">';
						listHtmlStr += '<i class="icon-img icon-arrow"></i>'
						listHtmlStr += '<span class="fn-right mr-60 f-15 c-999">'+json.count+'人</span>'
						var time = json.time.substring(5, 7)+"月"+json.time.substring(8, 10)+"日"
						if(reportType==WapConstants.REPORT_TYPE_2){
							listHtmlStr +=  '<span>月报（'+time+'）</span>'
						}else{
							listHtmlStr +=  '<span>周报（'+time+'）</span>'
						}
						listHtmlStr += '</li>';
						});
					}else{
						$("#list").attr('class', 'report-list');
						//列表
						var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
						$.each(array, function(index, json) {
							var typeStr = '';
							if(WapConstants.REPORT_TYPE_1 == json.reportType){//周报
								typeStr = '周';
	                        }else if(WapConstants.REPORT_TYPE_2 == json.reportType){//月报
	                        	typeStr = '月';
	                        }
							var htmlInfo = "";
							if(json.state==2){
								htmlInfo = 'location.href=\'workreportDetail.html?id='+json.id+'&dataType='+dataType+
								'&reportType='+reportType+'&sendTime='+sendTime+'&collect='+collect+'\'';
							}else{
								htmlInfo = 'location.href=\'workreportEdit.html?id='+json.id+'&dataType='+dataType+
								'&reportType='+reportType+'&sendTime='+sendTime+'&collect='+collect+'\'';
							}
							
							listHtmlStr += '<li onclick="'+htmlInfo+'">';
	                        listHtmlStr += '<p class="tt">';
	                        var path = json.sendUserImg;
	                        if(path==''||path==null){
	                        	listHtmlStr += '<span class="place-avatar" style="background:'+getBackgroundColor(json.sendUserName)+';">'+getLast2Str(json.sendUserName)+'</span>'
							}else{
								listHtmlStr += '<img src="'+path+'">'
							}
	                        listHtmlStr += '<span class="name f-16">'+json.sendUserName+'</span>'
	                        listHtmlStr += '<span class="time f-11">'+json.sendTime+'</span>'
	                        if(collect==1){
	                        	if(WapConstants.READ_0 == json.read){//未读
	                        		listHtmlStr += '<span class="read f-16"><span>未读</span></span>'
	                        	}else {
	                        		listHtmlStr += '<span class="read f-16">已读</span>'
	                        	}
	                        }else{
	                        	if(WapConstants.REPORT_TYPE_1 == json.reportType){//周报
	                        		listHtmlStr += '<span class="type type-week f-14">周</span>'
	                        	}else if(WapConstants.REPORT_TYPE_2 == json.reportType){//月报
	                        		listHtmlStr += '<span class="type type-mon f-14">月</span>'
	                        	}
	                        }
	                        if(WapConstants.DATA_TYPE_2 == dataType){
	                        	if(json.state==2){
		                        	if(json.readNum==json.sendNum){
		                        		listHtmlStr += '<span class="read f-16">全部已读</span>'
		                        	}else{
		                        		listHtmlStr += '<span class="read f-16"><span>'+json.readNum+'</span>/'+json.sendNum+'已读</span>'
		                        	}
	                        	}else if(json.state==8){
	                        		listHtmlStr += '<span class="read f-16">已撤回</span>';
	                        	}else{
	                        		listHtmlStr += '<span class="read f-16">未提交</span>';
	                        	}
	                        }
	                        listHtmlStr += '</p>'
	                        listHtmlStr += '<p class="dd f-15">'+json.content+'</p>'
	                        listHtmlStr += '</li>';
						});
					}
					$("#list").append(listHtmlStr);
					
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
//				    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
				    	$('.loading-more').html('');
				    	$('.loading-more').unbind();
				    }
					
					$("#listDiv").show();
					$('#empty').hide();
				}else{
					//内容为空
					$('#listDiv').hide();
					$('#empty').show();
				}
			}
	    },
	    
	    //详情
	    _doLoadDetail : function(dataType, data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var replyArray = obj.reply;
	    		var readUserArray = obj.readUserArray;
	    		var unReadUserArray = obj.unReadUserArray;
	    		var attachmentArray = obj.attachmentArray;
	    		var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
				
				if(dataType == WapConstants.DATA_TYPE_2){
					$("#sendDiv").hide();
					$("#receiveDiv").show();
				}else{
					$("#sendDiv").show();
					$("#receiveDiv").hide();
				}
				var path = obj.sendUserImg;
				var htmlStr='';
                if(path==''||path==null){
                	//path=_contextPath+'/static/html/images/icon/img_default_photo.png';
                	htmlStr += '<span class="place-avatar" style="background:'+getBackgroundColor(obj.sendUserName)+';">'+getLast2Str(obj.sendUserName)+'</span>';
					
				}else{
					htmlStr += '<img src="'+path+'">';
				}
				$("#detailUl").html('<li class="avatar avatar2 fn-clear">'+htmlStr+'<span class="pt-20 f-17">'+obj.sendUserName+'<br><font class="f-12 c-999">'+obj.sendTime+'</font></span></li>'+
	                    '<li class="f-15 c-333"><p class="pt-20">'+obj.content+'</p></li>');
				
				//附件
	    		if(attachmentArray.length > 0){
	    			var htmlStr = ''; 
	    			$.each(attachmentArray, function(index, json){
	    				var fileSize = getFileSize(json.fileSize);
	    				htmlStr += '<li class="fn-clear">';
	    				htmlStr += getFilePic(json.extName);
	    				if(json.fileExist){
	    					if(isAndroid()&&isWeiXin()){
	    						var downloadPath = _contextPath+"/common/open/officeh5/downloadAtt.action?path="+json.downloadPath;
	    						htmlStr+='<a href="'+downloadPath+'" class="icon-img icon-download"></a>';
	    					}else{
	    						htmlStr+='<a href="'+json.downloadPath+'" class="icon-img icon-download"></a>';
	    					}
		    			}else{
		    				htmlStr+='<a href="javascript:viewToast('+"'附件不存在'"+');" class="icon-img icon-download"></a>';
		    			}
	    				htmlStr += '<p class="acc-tt f-16">'+json.fileName+'</p>';
	    				htmlStr += '<p class="acc-dd">'+fileSize+'</p>';
	    				htmlStr += '</li>';
	    			});
	    			$("#fileDiv").html(htmlStr);
	    		}
				//已读
				if(readUserArray.length > 0){
					$("#readNum").html(readUserArray.length+"人");
					var	htmlInfo  = '<p class="list fn-clear f-14 c-333">';
					$.each(readUserArray, function(index, json) {
						var	nameAndPath = json.split('==');
						var htmlStr = ''; 
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlStr += '<span><span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
//							nameAndPath[1]=_contextPath+'/static/html/images/icon/img_default_photo.png';
						}else{
							htmlStr += '<span><img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += htmlStr+wap.subStrName(nameAndPath[0])+'</span>';
					});
					htmlInfo += '</p>';
					$("#readUserLi").append(htmlInfo);
				}else{
					$("#readUserLi").hide();
				}
				//未读
				if(unReadUserArray.length > 0){
					$("#unReadNum").html(unReadUserArray.length+"人");
					var	htmlInfo = '<p class="list fn-clear f-14 c-333">';
					$.each(unReadUserArray, function(index, json) {
						var	nameAndPath = json.split('==');
						var htmlStr = ''; 
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlStr += '<span><span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
//							nameAndPath[1]=_contextPath+'/static/html/images/icon/img_default_photo.png';
						}else{
							htmlStr += '<span><img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += htmlStr+wap.subStrName(nameAndPath[0])+'</span>';
					});
					htmlInfo += '</p>';
					$("#unReadUserLi").append(htmlInfo);
				}else{
					$("#unReadUserLi").hide();
				}
				if(replyArray.length > 0){
					var	htmlInfo ='<p class="pt-30 f-15 c-666">共有'+replyArray.length+'条回复</p>';
					htmlInfo += '<div class="reply-list">';
					$.each(replyArray, function(index, json) {
						var	nameAndPath = json.userName.split('==');
						
						if(replyArray.length-1==index){
							htmlInfo += '<div class="reply-item fn-clear last">';
						}else{
							htmlInfo += '<div class="reply-item fn-clear">';
						}
						if(nameAndPath[1]==''||nameAndPath[1]==null){
							htmlInfo += '<span class="place-avatar" style="background:'+getBackgroundColor(nameAndPath[0])+';">'+getLast2Str(nameAndPath[0])+'</span>'
						}else{
							htmlInfo += '<img src="'+nameAndPath[1]+'">';
						}
						htmlInfo += '<p class="f-14">';
						htmlInfo += '<span class="c-999"><span class="fn-right f-10">'+json.createTime+'</span>'+nameAndPath[0]+'</span>';
						htmlInfo += '<span class="pt-10 c-333">'+json.content+'</span>';
						htmlInfo += '</p>';
						htmlInfo += '</div>';
					});
					htmlInfo += '</div>';
					$("#replyLi").html(htmlInfo);
				}else{
					$("#replyLi").hide();
				}
	    	}
	    },
	}   
};