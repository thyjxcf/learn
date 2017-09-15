var wapNetworkService = {
		
	// 通知公告列表信息
	doLoadList : function(data){
		wapNetworkService._service._loadList(data);
	},
	//通知公告详情
	doLoadDetail : function(data){
		wapNetworkService._service._doLoadDetail(data);
	},
	
	//私有方法
	_service : {
		_loadList : function(data) {
			if(data.result==Constants.SUCCESS){
				WapPage = data.page;
				var array = data.result_array;
				var firstDay = data.firstDay;
				var contextPath = storage.get(Constants.MOBILE_CONTEXT_PATH);
				if(array.length > 0){
					//列表
					var listHtmlStr = $("#list").html();
					if(isNotBlank(listHtmlStr)){
						listHtmlStr = listHtmlStr.substr(0,listHtmlStr.lastIndexOf('</ul>'));
					}
//					var listHtmlStr = '<p class="time"><span class="f-11">'+firstDay+'</span></p>';
//					listHtmlStr += '<ul class="f-15">';
					$.each(array, function(index, json) {
						if(index==0&&json.first=='1'){
							listHtmlStr += '</ul>';
                        	listHtmlStr += '</div>';
						}
					    if(json.first=='1'){
                        	listHtmlStr += '<div class="notice-item">';
                        	listHtmlStr += '<p class="time"><span class="f-11">'+json.date+'</span></p>';
        					listHtmlStr += '<ul class="f-15">';
                        }
						var htmlInfo = 'location.href=\'bulletinDetail.html?id='+json.id+'\'';
						listHtmlStr += '<li onclick="'+htmlInfo+'"'
						 if(json.last=='1'){
	                        	listHtmlStr += 'class="last"';
	                        }
						listHtmlStr +='>';
						var imgSrc = '';
						if(json.type=='1'){
							imgSrc = contextPath+'/static/html5/images/icon/notice_2.png';
						}else{
							imgSrc = contextPath+'/static/html5/images/icon/notice_1.png';
						}
                        listHtmlStr += '<a href="#" class="fn-clear"><img src="'+imgSrc+'" /><span>'+json.title+'</span></a>';
                        listHtmlStr += '</li>';
                        if(json.last=='1'||index==Constants.PATE_SIZE-1){
                        	listHtmlStr += '</ul>';
                        	listHtmlStr += '</div>';
                        }
                        if(index==Constants.PATE_SIZE-1){
                        	storage.set(WapConstants.PAGE_LAST_DATE, json.date);
                        }
					});
					$("#list").html(listHtmlStr);
					
					if (WapPage.pageIndex >= WapPage.maxPageIndex) {
				    	$('.loading-more').html('<a href="javascript:void(0)">暂时没有更多的记录哦</a>');
				    	$('.loading-more').unbind();
				    }
					
					$("#listDiv").show();
					$('#empty').hide();
				}else{
					//内容为空
					$('#listDiv').hide();
					$('.loading-more').hide();
					$('#empty').show();
				}
			}
	    },
	    
	    //申请详情
	    _doLoadDetail : function(data){
	    	if(data.result==Constants.SUCCESS){
	    		var obj = data.result_object;
	    		var typeStr = '';
	    		
	    		$("#detailDiv h1").html(obj.title);
	    		$("#nameAndDept").html(obj.nameAndDept);
	    		$("#createTime").html(obj.createTime);
	    		$("#content").html(obj.content);
	    		$("img").each(function(){
	    			if($(this).width()==0)
	    			$(this).attr("style","max-width:85%;height:auto;"); 
    		    });
	    		var browser = {
	                    versions: function() {
	                        var u = navigator.userAgent,
	                        app = navigator.appVersion;
	                        return {
	                            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
	                            iPhone: u.indexOf('iPhone') > -1 ,                      
	                            iPad: u.indexOf('iPad') > -1,
	                            iPod: u.indexOf('iPod') > -1,
	                        };
	                    } (),
	                    language: (navigator.browserLanguage || navigator.language).toLowerCase()
	                }
	                if (browser.versions.iPhone||browser.versions.iPad||browser.versions.iPod)
	                {
			    		$("p[style='line-height: 16px;']").each(function(){
			    			$(this).find("img").attr("style","height:30px;padding-top:1px;");
			    			$(this).find("a").attr("style","font-size:32px;");
			    			if(!$(this).has("img").length){
			    				//$(this).removeAttr("style","line-height: 16px;");
			    				$(this).find("span").attr("style","");
			    			}
			    			$(this).attr("style","padding-bottom:5px;");
			    		});
	               }
	    		if (browser.versions.android)
	    		{
	    			$("p[style='line-height: 16px;']").each(function(){
	    				$(this).find("img").attr("style","height:16px;padding-top:1px;");
	    				$(this).find("a").attr("style","font-size:16px;");
	    				$(this).attr("style","line-height: 16px;padding-bottom:5px;");
	    			});
	    		}
	    	}
	    },
	}   
};