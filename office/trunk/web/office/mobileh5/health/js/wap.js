var wap = {
		
	backList : function(studentId){
		var isNewWeikeFlag = storage.get(WeikeConstants.WEIKE_FLAG_KEY);
		if(WeikeConstants.WEIKE_FLAG_VALUE_TYPE_2 == isNewWeikeFlag){//如果是跟新版微课对接
			$(".html-window-close").click();
		}else{
			var ownerId=storage.get(WapConstants.OWNER_ID);
			location.href="healthDetail.html?studentId="+studentId+"&ownerId="+ownerId;
		}
	},
	//查看详情
	initDetail : function(){
		wap._detailService.init();
	},
	initClassRank:function(){
		wap._classRankService.init();
	},
	_classRankService:{
		init:function(){
			var Request = new UrlSearch();
			var studentId=Request.studentId;
			var queryDateStr=Request.queryDateStr;
			wapNetwork.doGetClassRank(studentId,queryDateStr);
			
			$("#cancelId").click(function(){
				wap.backList($("#studentId").val());
			});
		},
	},
	_detailService : {
		
		init : function(){
			var Request = new UrlSearch();
			var ownerId = Request.ownerId;
			var studentIdInit=Request.studentId;
			var studentId="";
			var queryDateStr="";
			
			wapNetwork.doGetDetail(ownerId,studentIdInit,1,0,queryDateStr);//doGetDetail : function(ownerId,studentId,dateType,addOrLess,queryDateStr)
			//日期减一
			$(".arrow-l").click(function(){
				studentId=$(".studentId.active").attr("id");
				if(studentId==null) return;
				queryDateStr=$("#queryDateStr").val();
				wapNetwork.doGetDetail("",studentId,1,-1,queryDateStr);
			});
			//日期加一
			$(".arrow-r").click(function(){
				studentId=$(".studentId.active").attr("id");
				if(studentId==null) return;
				queryDateStr=$("#queryDateStr").val();
				var nowDate=new Date();
				var mon = nowDate.getMonth() + 1;
				var day = nowDate.getDate();
				var mydate = nowDate.getFullYear() + "-" + (mon<10?"0"+mon:mon) + "-" +(day<10?"0"+day:day);
				//当天 不能再看后面的时间
				if(queryDateStr==mydate) return;
				wapNetwork.doGetDetail("",studentId,1,1,queryDateStr);
			});
			
			//secondtab切换
			var $div_li3=$(".secondtab_menu > ul > li");
			$div_li3.click(function(){
				$(this).addClass("active").siblings().removeClass("active");
				var index=$div_li3.index(this);
				var dateType="";
				studentId=$(".studentId.active").attr("id");
				if(index==0){
					$(".arrow-l").show();
					$(".arrow-r").show();
					dateType=1;
					queryDateStr=$("#queryDateStr").val();
				}else if(index==1){
					$(".arrow-l").hide();
					$(".arrow-r").hide();
					dateType=2;
				}else if(index==2){
					$(".arrow-l").hide();
					$(".arrow-r").hide();
					dateType=3;
				}
				wapNetwork.doGetDetail("",studentId,dateType,0,queryDateStr);
				
			});
		},
	
		band:function(){
			//maintab切换
			var $div_li2=$(".maintab_menu > ul > li");
			$div_li2.unbind();
			$div_li2.click(function(){
				$(this).addClass("active").siblings().removeClass("active");
				
				var studentId=$(".studentId.active").attr("id");
				$(".secondtab_menu > ul > li").removeClass("active");
				$("#today").addClass("active");
				$(".arrow-l").show();
				$(".arrow-r").show();
				wapNetwork.doGetDetail("",studentId,1,0,"");
			});
		}
	}
};