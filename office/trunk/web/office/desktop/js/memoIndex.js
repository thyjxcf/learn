$(function() {	
	var myWidth=$(window).width();
	//if(myWidth>=1200){myCount=4;}else{myCount=3;};
	var myCount=3;
	var memoLiW=$('.memo-list ul li').width()+52;
	var memoLen=$('.memo-list ul li').length;
	var memoGroup=Math.ceil(memoLen/myCount);
	$('.memo-list ul').css('left',0).attr('data-store','0');
	$('.memo-list .prev').hide();
	$('.memo-list .next').show();
	if(memoLen<=myCount){
		$('.memo-list .next').hide();
	}
	$('.memo-list .next').click(function(e){
		var dataStore=parseInt($('.memo-list ul').attr('data-store'));
		dataStore=dataStore+1;
		scWidth=dataStore*myCount*memoLiW;
		if(dataStore<memoGroup){
			$('.memo-list ul').attr('data-store',dataStore);
			$('.memo-list ul').animate({'left':'-'+scWidth+'px'},500);
			$('.memo-list .prev').show();
		}
		if(dataStore==memoGroup-1){
			$('.memo-list .next').hide();
		}
	});
	$('.memo-list .prev').click(function(e){
		var dataStore=parseInt($('.memo-list ul').attr('data-store'));
		dataStore=dataStore-1;
		scWidth=dataStore*myCount*memoLiW;
		if(dataStore>=0){
			$('.memo-list ul').attr('data-store',dataStore);
			$('.memo-list ul').animate({'left':'-'+scWidth+'px'},500);
			$('.memo-list .next').show();
		}
		if(dataStore==0){
			$('.memo-list .prev').hide();
		}
	});
	$('.memo-list ul li').not('.add-new,.more').hover(
		function(){
			$(this).addClass('hover');
			var liIndex=$(this).index();
			if(liIndex==1 || liIndex==4 || liIndex==7){
				if(myWidth>=1200){
					$(this).find('.memo-wrap').css({'left':'-130px'});
				}else{
					$(this).find('.memo-wrap').css({'left':'-105px'});
				}
			}else if(liIndex==2 || liIndex==5 || liIndex==8){
				$(this).find('.memo-wrap').css({'left':'auto','right':'-1px'});
			}
		},function(){
			$(this).removeClass('hover');
		}
	);
	$('.more-memo').click(function(){
//		$('#memorandum').show();
		$('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandum.action', function() {
			memo.init_memoDate();// 初始化显示日程安排tab页内的数据
			$('#memorandum').jWindowOpen({
		    	modal:true,
		    	center:true,
		    	close:'#memorandum .close'
		    });
	    });
	    $("#datememo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
	    
	})
	// weekmemo
	$('#weekmemo').click(function() {// 周视图
	    $("#memo_menu_tab").val("weekmemo");
	    $(this).attr("startDate", SimpleDateFormat(new Date()));// 清除保存的周信息
	    $('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandumWeek.action', function() {
	      memo.init_week();
	      var startDate = $("#weekmemo").attr("startdate");
	      var endDate = $("#weekmemo").attr("endDate");
	//      var schedule = jQuery("#schedule");
	//      var scheduleValue = "" ;
	//      if(schedule.attr("checked")){
	//      	scheduleValue = schedule.val();
	//      }
	//      var workweek = jQuery("#workweek");
	//      var workweekValue = "";
	//      if(workweek.attr("checked")){
	//      	workweekValue = workweek.val();
	//      }
	    //  jQuery("#schedule").attr("happen","2");
	    //  jQuery("#workweek").attr("happen","2");
	//      +"&schedule="+scheduleValue+"&workweek="+workweekValue
	      $("#weekListContent").load(_contextPath + "/office/desktop/memo/memo-week.action?startDate=" + startDate + "&endDate=" + endDate);
	    });
	    $("#datememo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
	});
	$('#monthmemo').click(function() {// 月视图
	    $("#memo_menu_tab").val("monthmemo");
	    var date = new Date();
	    $(this).attr("year", date.getFullYear());
	    $(this).attr("month", date.getMonth() + 1);
	    $('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandumMonth.action', function() {
	      memo.init_month();
	    });
	    $("#datememo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
	});
	// 备忘录-日程
	$('#datememo').click(function() {
		$(this).attr("type", 1);
		$("#memo_menu_tab").val("datememo");
		$('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandum.action', function() {
		  memo.init_memoDate();
		});
		$("#datememo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
	});
	// 备忘录-新建备忘
	$('.add-bw').click(function() {
	    $('#memoLayer').load(_contextPath + '/office/desktop/memo/memo-newMemo.action');
	});
	
	//备忘录弹出层
	$('.memorandum ul.dt li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.memorandum .grid:eq('+$(this).index()+')').show().siblings('.grid').hide();
	})
	$('.re-memorandum-tab li input:radio').click(function(){
		var $index=$(this).attr('index');
		$index=parseInt($index);
		$('.re-memorandum-grid:eq('+$index+')').show().siblings('.re-memorandum-grid').hide();
	});
	$('.table-kb-wek td,.table-kb-mon td').mouseover(function(){
		$('.table-kb td').removeClass('hover cur')
		$(this).addClass('hover');
	})
	$('.table-kb-wek td').click(function(){
		$('.table-kb-wek td').removeClass('hover cur')
		$(this).addClass('hover cur');
		$('#addWek,#modelLayer').show();
	})
	$('.table-kb-mon td').click(function(){
		$('.table-kb-mon td').removeClass('hover cur')
		$(this).addClass('hover cur');
		$('#addMon,#modelLayer').show();
	})
	$('.table-kb-wek td .more').click(function(e){
		e.stopPropagation();
		$('.table-kb-wek td').removeClass('hover cur')
		$(this).parents('td').addClass('hover cur');
		$('#listWek,#modelLayer').show();
	})
	$('.table-kb-mon td .have').click(function(e){
		e.stopPropagation();
		$('.table-kb-mon td').removeClass('hover cur')
		$(this).parents('td').addClass('hover cur');
		$('#listMon,#modelLayer').show();
	})
	$('#closeAddMon,#closeListMon,#closeAddWek,#closeListWek').click(function(){
		$('#modelLayer').hide();
	})
	$('#listWekTable tr,#listMonTable tr').mouseover(function(){
		$(this).addClass('current').siblings('tr').removeClass('current');
	})
	$('.add-img').click(function(){
		$('#addMon,#modelLayer').show();
		$('#listMon').hide();
	})
	$('.close-top').click(function(){
		$(this).parents('.top-layer').hide();
		$('.table-kb td').removeClass('current')
	})
	$('.schedule-slider li').click(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
		$('.grid .schedule-content:eq('+$(this).index()+')').show().siblings('.schedule-content').hide();
		$('.re-memorandum-grid .schedule-content:eq('+$(this).index()+')').show().siblings('.schedule-content').hide();
	})
	$('.schedule-content ul li').hover(function(){
		$(this).addClass('current').siblings('li').removeClass('current');
	})
	$('.add-bw').click(function(){
		$('#memorandum').hide();
		$('#memoLayer').show().addClass('new-memoLayer');
	})
	$('body').on('click','.new-memoLayer .close,.new-memoLayer .submit,.new-memoLayer .reset',function(){
		$('#memoLayer').removeClass('new-memoLayer');
		$('#memorandum').show();
		$('#memoLayer').hide();
	})
});

/* 通用的备忘录刷新方法 */
function frushMyMemo(showList,date,size){
//	  var schedule = jQuery("#schedule");
//      var scheduleValue = "" ;
//      if(schedule.attr("checked")){
//      	scheduleValue = schedule.val();
//      }
//      var workweek = jQuery("#workweek");
//      var workweekValue = "";
//      if(workweek.attr("checked")){
//      	workweekValue = workweek.val();
//      }
	
  $("#addMon,#listMon,#listWek,#addWek").hide();
  $("#modelLayer").hide();
  
  $("#memo_menu_tab").find("li").removeClass("current");
  var tabType = $("#memo_menu_tab").val();
  var innerType = $("#"+tabType).attr("type");
  if(tabType==null || tabType == ""){
	  tabType = "datememo";
  }
  if(tabType=="monthmemo"){
    $("#monthmemo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
    $('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandumMonth.action', function() {
      memo.init_month();
    });
  }
  if(tabType=="datememo"){
    $("#datememo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
    $('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandum.action', function() {
      memo.init_memoDate();
    });
  }
  if(tabType=="weekmemo"){
    $("#weekmemo").addClass("current").siblings("li").removeClass("current");// 显示原来的tab页
    $('#memo-box').load(_contextPath + '/office/desktop/memo/memo-memorandumWeek.action', function() {
      memo.init_week();
      var startDate = $("#weekmemo").attr("startdate");
      var endDate = $("#weekmemo").attr("endDate");
      //addby liuy
//      var schedule = jQuery("#schedule");
//      var scheduleValue = "" ;
//      if(schedule.attr("checked")){
//      	scheduleValue = schedule.val();
//      }
//      var workweek = jQuery("#workweek");
//      var workweekValue = "";
//      if(workweek.attr("checked")){
//      	workweekValue = workweek.val();
//      }
 //     jQuery("#schedule").attr("happen","2");
  //    jQuery("#workweek").attr("happen","2");
//      +"&schedule="+scheduleValue+"&workweek="+workweekValue
      $("#weekListContent").load(_contextPath+"/office/desktop/memo/memo-week.action?startDate="+startDate+"&endDate="+endDate);
    });
  }
  if(showList=="showList"&&size>0){
    if(tabType=="monthmemo"){
//    	?schedule="+scheduleValue+"&workweek="+workweekValue
      $.get(_contextPath+"/office/desktop/memo/memo-listMon.action", { date: date, t: new Date().getTime() },
          function(data){
            $("#listMon").html(data);
            $("#listMon").show();
            $("#modelLayer").show();
     });
    }
    if(tabType=="weekmemo"){
//    	?schedule="+scheduleValue+"&workweek="+workweekValue
      $.get(_contextPath+"/office/desktop/memo/memo-listMon.action", { date: date, t: new Date().getTime() },
          function(data){
            $("#listWek").html(data);
            $("#modelLayer").show();
            $("#listWek").show();
     });
    }
  }
}
function SimpleDateFormat(date,split){
  var year = date.getFullYear();
  var month = date.getMonth();
  month = month <9 ? "0"+(month+1) : (month+1);
  var day = date.getDate();
  day = day <10 ? "0"+day : day;
  if(null==split){
    split ="-";
  }
  return year+split+month+split+day;
}
function SimpleDateParse(dateStr,split){
  var dateNow = new Date();
  var dateArr = dateStr.split(split);
  dateNow.setFullYear(parseInt(dateArr[0],10));
  dateNow.setMonth(parseInt(dateArr[1],10)-1);
  dateNow.setDate(parseInt(dateArr[2],10));
  return dateNow;
}