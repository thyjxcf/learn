/**
 * 备忘录
 */
var memoUrlw = "";
var memoUrl = "";
var memo = {
  init_add : function() {
    
  },
  init_memoDate : function() {
//    /*默认初始化时查询所有的备忘信息，timeType=0,表示查询全部*/
	  var t = $("#datememo").attr("memoType");
    if(!t){
      t = 0 ;
    }
    var listUrl='/office/desktop/memo/memo-listMemos.action?timeType='+t;
//    var schedule = jQuery("#schedule");
//    if(schedule.attr("checked")){
//    	listUrl+="&schedule="+schedule.val();
//    }
//    var workweek = jQuery("#workweek");
//    if(workweek.attr("checked")){
//    	listUrl+="&workweek="+workweek.val();
//    }
//    var schedule = jQuery("#schedule");
//    var scheduleValue = "" ;
//    if(schedule.attr("checked")){
//    	scheduleValue = schedule.val();
//    }
//    var workweek = jQuery("#workweek");
//    var workweekValue = "";
//    if(workweek.attr("checked")){
//    	workweekValue = workweek.val();
//    }
    //jQuery("#schedule").attr("happen","3");
   // jQuery("#workweek").attr("happen","3");
//    +"&schedule="+scheduleValue+"&workweek="+workweekValue
    $('.schedule-content').load(_contextPath + listUrl,function(){
      $(".schedule-content .dt").html(text_content[t]);
    });
    /*备忘录-日程-右侧菜单：0.全部、1.最近三天、2.最近一个月、3.最近六个月、4.六个月之后*/
    $('.schedule-slider li').click(function() {
      var type = $(this).attr("value");
      var text = $(this).text();
      $(this).addClass("current").siblings("li").removeClass("current");
      var listUrl2= '/office/desktop/memo/memo-listMemos.action?timeType='+type;
//      var schedule = jQuery("#schedule");
//      if(schedule.attr("checked")){
//      	listUrl2+="&schedule="+schedule.val();
//      }
//      var workweek = jQuery("#workweek");
//      if(workweek.attr("checked")){
//      	listUrl2+="&workweek="+workweek.val();
//      }
     // jQuery("#schedule").attr("happen","3");
     // jQuery("#workweek").attr("happen","3");
      $('.schedule-content').load(_contextPath +listUrl2,function(){
        $("#datememo").attr("memoType",type);
        $(".schedule-content .dt").html(text_content[type]);
      });
    });
    
  },

  init_month : function() {
    currDT = new Date();
    memo_month.getmonth();
  },
  init_week : function() {
    currDT = new Date();
    var dateNow = new Date();
    var dateStr = $('#weekmemo').attr("startDate");
    if(dateStr!=""){
      dateNow = SimpleDateParse(dateStr,'-');
    }
    var lab = DateUtils.showDate(dateNow);
    
    $("#span1").html(lab);
    // 周视图新建
  }

};
var memo_month = {
  // 上一月 或 下一月
  getmonth : function(currYear, currMonth, next) {
    $(".table-scroll td").each(function(e,i){
      $(this).unbind();
    });
    var date = new Date();
    date.setDate(1);
    if (currYear != undefined && next != '') {// 上一个月或下一个月
      var nextMonth = eval(currMonth - next);
      date.setYear(currYear);
      date.setMonth(nextMonth);
    }else{
     if($("#monthmemo").attr("year")){
      date.setYear($("#monthmemo").attr("year"));
     }
     if($("#monthmemo").attr("month")){
       date.setMonth($("#monthmemo").attr("month")-1);
     }
    }
    var temp = eval(date.getMonth() + 1);
    
    //$("#monthLabel").text(aryMonth[temp-1]);//显示中文月份
    $("#monthLabel").text(countMonth[temp-1]);//显示标准的数字月份
    //$("#monthLabel").text(temp);//显示数字月份
    $("#monthLabel").attr("value",temp);
    $("#yearLabel").text(date.getFullYear());
    $("#monthmemo").attr("year",date.getFullYear());
    $("#monthmemo").attr("month",temp);
    var monthId = document.getElementById("monthNote");
    DateUtils.addmonth(date, monthId);
  },
  // 上一周 或 下一周

  addWeek : function(ope) {
    var num = 0;
    if (ope == "-") {
      num = -7;
    } else if (ope == "+") {
      num = 7;
    }
    currDT = DateUtils.addDate(currDT, num);
    var weekth = document.getElementById("weekth");
    var weeklabel = DateUtils.showDate(weekth);
    $("#span1").html(weeklabel);

  },

  // 备忘录日期弹出框标题显示
  // 备忘录日期弹出框标题显示
  showTitle : function(hang, lie, date) {
    var titleLabel = "";
    var half = "";
    var minu = ":00:00";
    var datetime = "";
    if (hang % 2) {
      lie = Number(lie) + 1;
      hang = Number(hang) - 1;
      half = "半";
      minu = ":30:00";
    }
    var wId = document.getElementById("weekth");
    if (wId != null) {
      titleLabel = wId.rows[0].cells[lie].title;
      datetime = titleLabel.substring(2, titleLabel.length);
      var waId = document.getElementById("weekall");
      titleLabel += " " + waId.rows[hang].cells[0].innerHTML;
      var hour = waId.rows[hang].cells[0].innerHTML;
      datetime += " " + hour.substring(2, hour.indexOf("点")) + minu;
      $("#timeStringw").val(datetime);
      $("#weektop").html(titleLabel + half);
    }
    $("#memoDatem").val(date);

  }

}
