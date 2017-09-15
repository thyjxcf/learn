var wap = {
		init : function(){
			storage.remove(WapConstants.SEARCH_STR);
			var contextPath=storage.get(Constants.MOBILE_CONTEXT_PATH);
			location.href = contextPath + '/office/mobile/message/messageList.html?dataType='+WapConstants.DATA_TYPE_3
			+'&isBack='+WapConstants.BACK_LIST_GLAG_0;
		},
		
		//列表页
		initList : function(){
			wap._listService.init();
		},
		
		//详情页
		initDetail : function(){
			wap._detailService.init();
		},
		
		detailBack : function(){
			wap._detailService.abtn-back();
		},
		
		//编辑页
		initEdit : function(){
			wap._editService.init();
		},
		
		editBack : function(){
			wap._editService.abtn-back();
		},
		
		//列表页面方法	
		_listService : {
			
			init : function(){
				var Request = new UrlSearch();
				var dataType = Request.dataType;
				var isBack = Request.isBack;
				if(dataType==null || typeof(dataType)=='undefined'){
					dataType=WapConstants.DATA_TYPE_3;
				}
				if(isBack==null || typeof(isBack)=='undefined'){
					isBack=WapConstants.BACK_LIST_GLAG_0;
				}
				
				var userId = storage.get(Constants.USER_ID);
				
				//搜索按钮
				$('header .search').click(function(){
					if($(this).hasClass('search-current')){
						$(this).removeClass('search-current');
						$('.list-search-wrap').children('.txt').val('');
						$('.list-search-wrap').hide();
					}else{
						$(this).addClass('search-current');
						$('.list-search-wrap').children('.txt').attr('placeholder', '请输入标题');
						$('.list-search-wrap').show();
					}
				});
				
				//tab页
				$('.more-layer p').on('touchstart', function(){
					var dataType = $(this).attr("dataValue");
					var userId = storage.get(Constants.USER_ID);
					$('.list-search-wrap').children('.txt').attr('placeholder', '请输入标题');
					$('.list-search-wrap').children('.txt').val('');
					wap._listService.loadListData(userId, dataType);
				});
				
				//搜索事件监听
				$('.my-search-form').submit(function () { 
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.more-layer .current').attr("dataValue");
					wap._listService.loadListData(userId, dataType);
					$("#searchMsg").blur();
					return false;
				});
				
				//更多
				$('.loading-more').bind('click', function(){
					var userId = storage.get(Constants.USER_ID);
					var dataType = $('.more-layer .current').attr("dataValue");
					wap._listService.bindClickMore(userId, dataType);
				});
				
				//新增
				$('header .edit').click(function(){
					var dataType = $('.more-layer .current').attr("dataValue");
			    	location.href="messageEdit.html?editType="+WapConstants.EDIT_TYPE_1+"&dataType="+dataType;
				});
				
				//加载默认条件
				var searchStr = '';
				if(isBack == WapConstants.BACK_LIST_GLAG_1){
					//返回列表页默认搜索值
					searchStr = storage.get(WapConstants.SEARCH_STR);
					if(isNotBlank(searchStr)){
						$('.list-search-wrap').children('.txt').val(searchStr);
						$('.list-search-wrap').show();
					}
				}
				$('.more-layer p').removeClass('current');
				if(dataType == WapConstants.DATA_TYPE_1){
					$("#myTab1").addClass('current');
					$("#tabTxt").html($("#myTab1").html());
				}else if(dataType == WapConstants.DATA_TYPE_2){
					$("#myTab2").addClass('current');
					$("#tabTxt").html($("#myTab2").html());
				}else{
					$("#myTab3").addClass('current');
					$("#tabTxt").html($("#myTab3").html());
				}
				
				$('.abtn-edit').click(function() {
					var dataType = $('.more-layer .current').attr("dataValue");
			    	location.href="messageEdit.html?dataType="+dataType+"&editType="+WapConstants.EDIT_TYPE_1;
			    });
				
				wapNetwork.doGetList(userId, dataType, searchStr);
			},
			
			//加载第一页数据
			loadListData : function(userId, dataType){
				$('#list').hide();
				$('#empty').hide();
				$('.mail-list').html('');
				$('.loading-more').html('<a href="javascript:void(0)">加载更多</a>');
				$('.loading-more').bind('click', function(){
					wap._listService.bindClickMore(userId, dataType);
				});
				
				var searchStr = $('.list-search-wrap').children('.txt').val();
				wapNetwork.doGetList(userId, dataType,searchStr);
			},
			
			//加载更多
			bindClickMore : function(userId, dataType){
				if (WapPage.pageIndex >= WapPage.maxPageIndex) {
			    	return;
			    }
				var searchStr = storage.get(WapConstants.SEARCH_STR);
			    wapNetwork.doMoreList(userId, dataType,searchStr, ++WapPage.pageIndex);
			},
			
		},
		
		//详情页面方法
		_detailService : {
			back : function(){
				$('.abtn-back').click();
			},
			del : function(dataType,id){
				showMsg("确认删除？",function(){
					wapNetwork.doDelete(dataType,id);
				});
			},
			edit : function(dataType,id,replyMsgId,editType){
				location.href="messageEdit.html?id="+id+"&replyMsgId="+replyMsgId+"&dataType="+dataType+"&editType="+editType;
			},
			
			init : function(){
				var Request = new UrlSearch();
				var id = Request.id;
				var dataType = Request.dataType;
				var replyMsgId = Request.replyMsgId;
				var userId = storage.get(Constants.USER_ID);
				wapNetwork.doGetDetail(userId,dataType, id,replyMsgId);
				//返回按钮
			    $('.abtn-back').click(function() {
			    	location.href="messageList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			    });
			},
		},
		
		//编辑页面方法
		_editService : {
			
			back : function(){
				$('#cancelId').click();
			},
			
			backList:function(dataType){
				location.href="messageList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
			},
			
			init : function(){
				
				var Request = new UrlSearch();
//				var type = Request.type;//1:其他、2：通讯录返回
				var dataType = Request.dataType;
				var editType = Request.editType;
				var id = Request.id;
				var replyMsgId = Request.replyMsgId;
				//初始化数据
				wap._editService.initData();
				
				//通讯录
				$("#address").click(function(){
					//已维护的数据缓存起来
					wap._editService.saveDataCache();
					
					//通讯录需求的几个参数
					var _contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
					var _unitId = storage.get(Constants.UNIT_ID);
					var returnurl = _contextPath + "/office/mobile/message/messageEdit.html?id="+id+"&replyMsgId="+replyMsgId+"&dataType="+dataType+"&editType="+editType+"&type=2";
					storage.set(Constants.ADDRESS_RETURN_URL, returnurl);//绝对路径
					
//					storage.set(Constants.ADDRESS_RETURN_FUNCTION, "parent.wap._editService.redirectBackFunction");//通讯录回调函数
					storage.set(Constants.CONTEXTPATH, _contextPath);
					storage.set(Constants.ADDRESS_TYPE, Constants.TYPE_1);
					storage.set(Constants.ADDRESS_SELECT_TYPE, Constants.TYPE_1);
					//请求通讯录
					var requesturl = _contextPath + "/component/addressbookwap/redirect.html?unitId="+_unitId;
//					$("#page").hide();
//					$("#redirectDivId").attr("src",requesturl);
//					$("#redirectDivId").show();
					location.href = requesturl;
				});
				
				//取消
				$('#cancelId').click(function(){
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS, false);
					var usernames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES, false);
					var title = $('#msgTitle').val();
					var content = $('#msgCon').val();
					var attNumber=$('#attNumber').html();
					var state=$('#state').val();
					if(state==''&&(isNotBlank(userIds)||isNotBlank(title)||isNotBlank(content)||isNotBlank(attNumber))){
						showMsg("是否保存草稿？",function(){
							wap._editService.save(dataType,editType,WapConstants.STATE_TYPE_1);
						},function(){
							if(editType==WapConstants.EDIT_TYPE_1){
								location.href="messageList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
							}else{
								if(dataType == WapConstants.DATA_TYPE_3){
									location.href="messageReceivedDetail.html?replyMsgId="+replyMsgId+"&id="+id+"&dataType="+dataType;
								}else{
									location.href="messageGeneralDetail.html?replyMsgId="+replyMsgId+"&id="+id+"&dataType="+dataType;
								}
							}
							//清除缓存
							wap._editService.clearCache();
						});
					}else{
						if(editType==WapConstants.EDIT_TYPE_1){
							location.href="messageList.html?dataType="+dataType+"&isBack="+WapConstants.BACK_LIST_GLAG_1;
						}else{
							if(dataType == WapConstants.DATA_TYPE_3){
								location.href="messageReceivedDetail.html?replyMsgId="+replyMsgId+"&id="+id+"&dataType="+dataType;
							}else{
								location.href="messageGeneralDetail.html?replyMsgId="+replyMsgId+"&id="+id+"&dataType="+dataType;
							}
						}
						//清除缓存
						wap._editService.clearCache();
					}
				});
				
				//提交
				$('#submitBtn').click(function(){
					wap._editService.save(dataType,editType,WapConstants.STATE_TYPE_2);
				});
			},
			
			//提交
			save : function(dataType,editType,state){
				var userId = storage.get(Constants.USER_ID);
				var sendUserName = storage.get(Constants.USER_REALNAME);
				var unitId = storage.get(Constants.UNIT_ID);
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var usernames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				var title = $('#msgTitle').val();
				var content = $('#msgCon').val();
				
				if(!isNotBlank(userIds)){
					showMsgTip('收件人不能为空');
					return;
				}
				
				if(!isNotBlank(title)){
					showMsgTip('主题不能为空');
					return;
				}else{
					if(getStringLen(title) > 200){
						showMsgTip('主题不能超过200字符');
						return;
					}
				}
				
				if(!isNotBlank(content)){
					showMsgTip('内容不能为空');
					return;
				}else{
//					if(getStringLen(content) > 500){
//						alert('内容不能超过500字符');
//						return;
//					}
				}
				wapNetwork.doSave(dataType,editType,userIds,userId,sendUserName,unitId,state);
				//清除缓存
				wap._editService.clearCache();
			},
			//通讯录回调函数
			redirectBackFunction:function(){
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				wap._editService.setUser(userIds, userNames);
				$("#page").show();
				$("#redirectDivId").hide();
			},
			//初始化数据
			initData : function(){
				var Request = new UrlSearch();
				var type = Request.type;//1:其他、2：通讯录返回
				var dataType = Request.dataType;
				var editType = Request.editType;
				var fileIndex=1;
				var id = Request.id;
				var replyMsgId = Request.replyMsgId;
				if(editType==WapConstants.EDIT_TYPE_2){
					$("#msgUse").html("回复");
				}else if(editType==WapConstants.EDIT_TYPE_3){
					$("#msgUse").html("回复全部");
				}else if(editType==WapConstants.EDIT_TYPE_4){
					$("#msgUse").html("转发");
				}else if(editType==WapConstants.EDIT_TYPE_5){
					$("#msgUse").html("编辑");
				}else{
					$("#msgUse").html("写消息");
				}
				if("2" == type){
					//获取缓存中已选中的人员
					var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
					var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
					if(isNotBlank(userIds) && isNotBlank(userNames)){
						wap._editService.setUser(userIds, userNames);
					}
					//获取缓存中之前已维护的数据
					var title = storage.get(WapConstants.EDIT_TITLE);
					var content = storage.get(WapConstants.EDIT_CONTENT);
					var existAtt = storage.get(WapConstants.EDIT_EXIST_ATT);
					var delAtt = storage.get(WapConstants.EDIT_DEL_ATT);
					if(isNotBlank(title)){
						$('#msgTitle').val(title);
					}
					if(isNotBlank(content)){
						$('#msgCon').val(content);
					}
					if(isNotBlank(id)){
						$("#msgId").val(id);
					}
					if(isNotBlank(replyMsgId)){
						$("#replyMsgId").val(replyMsgId);
					}
					if(isNotBlank(delAtt)){
						$("#attDelIds").val(delAtt);
					}
					if(isNotBlank(existAtt)){
						$("#accFooter").html(existAtt);
						wap._editService.attNumber();
					}
				}else{
					//清除缓存
					wap._editService.clearCache();
					
					if(editType==WapConstants.EDIT_TYPE_1){
						//新增--不做处理
						$("#msgUse").html("写消息");
					}else{
						var Request = new UrlSearch();
						var id = Request.id;
						var dataType = Request.dataType;
						var editType = Request.editType;
						wapNetwork.doEdit(dataType,id,editType);
					}
				}
				
				$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="*/*">');
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
				});
				wap._editService.attNumber();
				
				wap._editService.auto();
				
				//$(window).resize(function(){auto1();});
				
//				$('.acc-wrap li .icon-del2').on('touchstart', function(e){
//					//e.preventDefault();
//					$(this).parent('li').remove();
//					if(!$('.acc-wrap li').length){
//						$('#accFooter').hide();
//					};
//					wap._editService.auto();
//				});
				
//				$('.dd-scroll span').click(function(e){
//					e.stopPropagation();
//					$(this).remove();
//					wap._editService.deleteUser($(this).find(".my-userid-class").val());
//				});
			},
			
			//重新计算上部高度
			auto : function(){
				var all_h=parseInt($('.scroll-wrap').height());
				var list_h=parseInt($('.msgsnd-list').height());
				var text_h=all_h-list_h-25;
				$('#container').height(all_h);
				$('#msgCon').height(text_h);
				//layer('.icon-del2','.ui-layer','.close','标题1');
			},
			
			//通讯录赋值
			setUser : function(userIds, userNames){
				if(""!=userIds && userIds!=null && typeof(userIds)!='undefined'){
					var ids = userIds.split(',');
					if(""!=userNames && userNames!=null && typeof(userNames)!='undefined'){
						var names = userNames.split(',');
						var htmlStr = '';
						for(index in names){
							if(index < ids.length){
								htmlStr += '<span>'+names[index]+'<input type="hidden" class="my-userid-class" value="'+ids[index]+'"/></span>\n';
							}
						}
						$('.dd-scroll').html(htmlStr);
					}
				}
				$('.dd-scroll span').unbind();
				$('.dd-scroll span').click(function(e){
					e.stopPropagation();
					$(this).remove();
					wap._editService.deleteUser($(this).find(".my-userid-class").val());
				});
				
			},
			
			//删除用户
			deleteUser : function(userId){
				var userIds = storage.get(Constants.ADDRESS_SELECTED_USERIDS);
				var userNames = storage.get(Constants.ADDRESS_SELECTED_USERNAMES);
				var ids = userIds.split(',');
				var names = userNames.split(',');
				var index = ids.indexOf(userId);
				if(index > -1){
					ids.splice(index, 1);
					names.splice(index, 1);
				}
				
				storage.set(Constants.ADDRESS_SELECTED_USERIDS, ids.join(","));
				storage.set(Constants.ADDRESS_SELECTED_USERNAMES, names.join(","));
			},
			
			//数据缓存起来
			saveDataCache : function(){
				var title = $('#msgTitle').val();
				var content = $('#msgCon').val();
				var attDelIds = $('#attDelIds').val();
				$("#accFooter .new-file").remove();
				storage.set(WapConstants.EDIT_TITLE, title);
				storage.set(WapConstants.EDIT_CONTENT, content);
				storage.set(WapConstants.EDIT_DEL_ATT, attDelIds);
				storage.set(WapConstants.EDIT_EXIST_ATT, $("#accFooter").html());
			},
			
			//清除缓存
			clearCache : function(){
				storage.remove(Constants.ADDRESS_RETURN_URL);
				storage.remove(Constants.CONTEXTPATH);
				
				storage.remove(Constants.ADDRESS_SELECTED_USERIDS);
				storage.remove(Constants.ADDRESS_SELECTED_USERNAMES);
				storage.remove(Constants.ADDRESS_RETURN_FUNCTION);
				
				storage.remove(WapConstants.EDIT_TITLE);
				storage.remove(WapConstants.EDIT_CONTENT);
				storage.remove(WapConstants.EDIT_EXIST_ATT);
				storage.remove(WapConstants.EDIT_DEL_ATT);
			},
			
			//计算附件数量
			attNumber : function(){
				var num=$("#accFooter li").length;
				if(num!=0){
					$("#attNumber").html(num);
				}else{
					$("#attNumber").html("");
				}
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
				wap._editService.attNumber();
					
			},
			//附件选择
			fileChange : function(objId,fileIndex){
				$("#"+objId).hide();
				var file = $("#"+objId).get(0).files[0];
				var fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
				
//				<li class="fn-clear">
//	                <span class="icon-img icon-class icon-word"></span>
//	                <a href="javascript:void(0)" class="icon-img icon-del2"></a>
//	                <p class="acc-tt f-16">关于帽子戏法的...百度解释.docx</p>
//	                <p class="acc-dd">805.2KB</p>
//	            </li>
				
				var htmlStr='<li class="fn-clear new-file">';
				var laIn=file.name.lastIndexOf('.');
				if(laIn!=-1){
					htmlStr+=getFilePic(file.name.substring( laIn+1 ));
				}else{
					htmlStr+=getFilePic("");
				}
				htmlStr+='<a href="javascript:void(0)" class="icon-img icon-del2" onclick="wap._editService.attDel(this,\'upFile'+fileIndex+'\')"></a>';
				htmlStr+='<p class="acc-tt f-16">'+file.name+'</p>';
				htmlStr+='<p class="acc-dd">'+fileSize+'</p>';
				htmlStr+='</li>';
				$("#accFooter").append(htmlStr);
				wap._editService.attNumber();
				wap._editService.auto();
				fileIndex++;
				$('.ui-file').append('<input type="file" name="file" id="upFile'+fileIndex+'" accept="*/*">');
				$("#upFile"+fileIndex).on('change',function(){
					wap._editService.fileChange("upFile"+fileIndex,fileIndex);
				});
			}
		}
};

