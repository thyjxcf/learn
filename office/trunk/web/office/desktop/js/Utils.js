/**
 * Utils.js 一些常用工具函数
 */
var currDT;
var aryDay = new Array("日", "一", "二", "三", "四", "五", "六");
var aryMonth = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十 ", "十一", "十二");
var countMonth = new Array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10 ", "11", "12");
var text_content = new Array("全部", "最近三天", "最近一个月", "最近六个月", "六个月之后");
$("#monthLabel").attr("value", eval(new Date().getMonth() + 1));
var Utils = {
  // 打开新窗口
  openFullWindow : function(url) {
    window.open(url, "", "width=" + screen.width + ",height=" + screen.height
        + ",top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes");
  },
  // 打开本地新窗口
  openLocaWindow : function(url) {
    window.location = url;
  },

  // 弹出框
  showWindow : function(objTag, closeTag, fromTag) { // 用class或者是ID绑定事件

    jQuery(objTag).jWindowOpen({ // 弹出层的id
      modal : true,
      center : true,
      // drag : ".title",
      close : closeTag, // 关闭层按钮，可以id也可以class
      closeHoverClass : "hover",
      transfererFrom : fromTag,
      transfererClass : "transferer" // 弹开效果
    // transfererClass:"" //直接显示
    });
    // jQuery("#_______overlayer").css("display","none"); //背景遮罩效果，默认有遮罩效果，若是不要遮罩请去掉此行的注释
  },
  // 弹出框
  showWindowQuickly : function(objTag, closeTag, fromTag) { // 用class或者是ID绑定事件

    jQuery(objTag).jWindowOpen({ // 弹出层的id
      modal : true,
      center : true,
      close : closeTag, // 关闭层按钮，可以id也可以class
      closeHoverClass : "hover",
      transfererFrom : fromTag,
      transfererClass : "" // 直接显示
    });
  },

  // 弹出框(背景无遮罩效果)
  showWindowNo : function(objTag, closeTag, fromTag) { // 用class或者是ID绑定事件
    jQuery(objTag).jWindowOpen({ // 弹出层的id
      modal : true,
      center : true,
      // drag : ".title",
      close : closeTag, // 关闭层按钮，可以id也可以class
      closeHoverClass : "hover",
      transfererFrom : fromTag,
      transfererClass : "transferer" // 弹开效果
    // transfererClass:"" //直接显示
    });
    jQuery("#_______overlayer").css("display", "none"); // 背景遮罩效果，默认有遮罩效果，若是不要遮罩请去掉此行的注释
  },
  // 隐藏弹出
  hideWindow : function(objTag) {
    var tag = objTag || '.skin-management';
    jQuery(tag).hide();
    jQuery("#_______overlayer").css("display", "none");
  }
};
// 删除所有数组元素
Array.prototype.clear = function() {
  this.length = 0;
};
// 删除某个数组元素
Array.prototype.remove = function(dx) {
  if (isNaN(dx) || dx > this.length) {
    return false;
  }
  this.splice(dx, 1);
};

/**
 * 上一周 或 下一周
 */
var DateUtils = {

  showDate : function(dateNow) {

    if (null != dateNow) {
      currDT = dateNow;
    }
    var dw = currDT.getDay();

    var tdDT;
    // 确定周一是那天
    if (dw == 0) {
      tdDT = currDT;
    } else {
      tdDT = DateUtils.addDate(currDT, (-dw));
    }
    // 在表格中显示一周的日期
    var weekStart = "";
    var weekEnd = "";

    weekStart = SimpleDateFormat(tdDT, "-");

    $('#weekmemo').attr("startDate", weekStart);

    tdDT = DateUtils.addDate(tdDT, 6); // 下一天

    weekEnd = SimpleDateFormat(tdDT, "-");
    $('#weekmemo').attr("endDate", weekEnd);
    weekStart += "——";
    weekStart += weekEnd;

    return weekStart; // 显示日期
  },

  // 增加或减少若干天，由 num 的正负决定，正为加，负为减
  addDate : function(dt, num) {
    var ope = "+";
    if (num < 0) {
      ope = "-";
    }

    var reDT = dt;
    for ( var i = 0; i < Math.abs(num); i++) {
      reDT = DateUtils.addOneDay(reDT, ope);
    }

    return reDT;
  },

  // 增加或减少一天，由ope决定, + 为加，- 为减，否则不动
  addOneDay : function(dt, ope) {
    var num = 0;
    if (ope == "-") {
      num = -1;
    } else if (ope == "+") {
      num = 1;
    }

    var y = dt.getFullYear();
    var m = dt.getMonth();
    var lastDay = DateUtils.getLastDay(y, m);

    var d = dt.getDate();
    d += num;
    if (d < 1) {
      m--;
      if (m < 0) {
        y--;
        m = 11;
      }
      d = DateUtils.getLastDay(y, m);
    } else if (d > lastDay) {
      m++;
      if (m > 11) {
        y++;
        m = 0;
      }
      d = 1;
    }

    var reDT = new Date();
    reDT.setYear(y);
    reDT.setMonth(m,d);
//    reDT.setMonth(m);
//    reDT.setDate(d);

    return reDT;
  },

  // 是否为闰年
  isLeapYear : function(y) {
    var isLeap = false;
    if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
      isLeap = true;
    }
    return isLeap;
  },

  // 每月最后一天
  getLastDay : function(y, m) {
    var lastDay = 28;
    m++;
    if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
      lastDay = 31;
    } else if (m == 4 || m == 6 || m == 9 || m == 11) {
      lastDay = 30;
    } else if (DateUtils.isLeapYear(y) == true) {
      lastDay = 29;
    }
    return lastDay;
  },

  /**
   * 上一月 或 下一月
   * 
   * @param currYear
   * @param currMonth
   * @param next
   */

  addmonth : function(currdate, monthId) {
    var currDTStr = formatDate(currDT);// yyyy-mm-dd
    if (currdate != undefined) {
      var date = currdate;
    } else {
      var date = new Date();
    }
    var year = date.getFullYear();
    var month = date.getMonth();
    var week = date.getDay();// 星期几
    date.setMonth(date.getMonth() + 1);
    var lastDate = new Date(date - 3600000 * 24);
    var end = lastDate.getDate();// 本月最后一天

    var num = 1;
    var weekstrat = DateUtils.getMonthWeek(year, month, 1);// 本月第一天的周数
    var weekEnd = DateUtils.getMonthWeek(year, month, end);// 本月最后一天的周数
    var testTbl = monthId;
    DateUtils.undisplayTableRow(monthId);
    var tdContent = "";

    /* S=初始化每日的记录条数为0 */
    countArray = new Array();
    calendarStrs = new Array();
    for ( var i = 0; i <= end; i++) {
      countArray[i] = 0;
      calendarStrs[i] = '';
    }
    /* E=初始化每日的记录条数为0 */

    // 根据 年月 得到该月所有的日期和记录数的关系
    var startDate = year + "-" + (month + 1) + "-1";
    var endDate = year + "-" + (month + 1) + "-" + end;
//    var schedule = jQuery("#schedule");
//    var scheduleValue = "";
//    if(schedule.attr("checked")){
//    	var scheduleValue = schedule.val();
//    }
//    var workweek = jQuery("#workweek");
//    var workweekValue = "";
//    if(workweek.attr("checked")){
//    	var workweekValue = workweek.val();
//    }
   // jQuery("#schedule").attr("happen","1");
    //jQuery("#workweek").attr("happen","1");
    $.post(_contextPath + "/office/desktop/memo/memo-memoCount.action", {
      "startDate" : startDate,
//      "schedule" : scheduleValue,
      "endDate" : endDate,
//      "workweek" : workweekValue,
      "type" : "month"
    }, function(json) {
      if (json != null) {
        jQuery.each(json, function(i, item) { // 待办条数
          countArray[item.index] = item.count;
          calendarStrs[item.index] = item.calendarStr;
        });
      }
      for ( var i = weekstrat; i <= weekEnd; i++) {// 一个月的周数
        if (!testTbl.rows[i - 1]) {
          return;
        }
        testTbl.rows[i - 1].style.display = "table-row"; // currDT突出显示
        for ( var x = 0; x < 7; x++) {// 星期
          var tempDate = new Date(year, month, num);
          var tempDateStr = formatDate(tempDate);
          var tempClassName = "";
          if (tempDateStr < currDTStr) {
            tempClassName = "_blank";
          }
          testTbl.rows[i - 1].cells[x].style.color = "";
          if (year == currDT.getFullYear() && month == currDT.getMonth() && num == currDT.getDate()) {
            testTbl.rows[i - 1].cells[x].style.color = "red"; // currDT突出显示
          }
          var tempmon = parseInt(month + 1) < 10 ? "0" + parseInt(month + 1) : parseInt(month + 1);
          var ymtemp = year + "-" + tempmon + "-";
          if (num == 1 && week == x) {
            if (countArray[num] != 0) {
              tdContent = "<p value='" + ymtemp + num + "' class=\"have " + tempClassName + " \" >" + countArray[num]
                  + "</p>" + num+"<br/><span style='white-space:nowrap;'>"+calendarStrs[num]+"</span>";
            } else {
              tdContent = "<p value='" + ymtemp + num + "' class=\"" + tempClassName + " \" >&nbsp;</p>" + num+"<br/><span style='white-space:nowrap;'>"+calendarStrs[num]+"</span>";
            }
            num++;
          } else if (num > 1 && num <= end) {
            if (countArray[num] != 0) {
              tdContent = "<p value='" + ymtemp + num + "'class=\"have " + tempClassName + " \" >" + countArray[num]
                  + "</p>" + num+"<br/><span style='white-space:nowrap;'>"+calendarStrs[num]+"</span>";
            } else {
              tdContent = "<p value='" + ymtemp + num + "' class=\"" + tempClassName + " \" >&nbsp;</p>" + num+"<br/><span style='white-space:nowrap;'>"+calendarStrs[num]+"</span>";
            }
            num++;
          } else {
            tdContent = "<p class=\"_blank\">&nbsp;</p>";
          }
          testTbl.rows[i - 1].cells[x].innerHTML = tdContent;
        }
      }
      /* 为有备忘记录的日期单元格加点击图标查看详细内容的click事件 */
      $(".table-kb-mon td").mouseover(function() {

        $(".table-kb td").removeClass("hover cur hover_1")
        if ($(this).find("p").hasClass("_blank")) {
          $(this).addClass("hover_1");
        } else {
          $(this).addClass("hover");
        }
      });
      $(".table-kb-mon td").click(function(e) {
        $(".table-kb-mon td").removeClass("hover cur hover_1")
        $(this).addClass("hover_1 cur");
        $("#addMon,#modelLayer").show();
        var date_now = $(this).find("p").attr("value");
        // alert("YY=_contextPath="+_contextPath);
        $("#addMon").load(_contextPath + "/office/desktop/memo/memo-addMon.action?date=" + date_now);
        e.stopPropagation();
      }).mouseout(function() {
        $(".table-kb td").removeClass("hover cur hover_1")
      });
      $(".table-kb-mon td .have").click(function(e) {
        var date_now = $(this).attr("value");
        $(".table-kb-mon td").removeClass("hover cur hover_1")
        $(this).parents("td").addClass("hover_1 cur");
        $("#listMon,#modelLayer").show();
        // alert("YY=_contextPath="+_contextPath);
//        var schedule = jQuery("#schedule");
//        var scheduleValue = "" ;
//        if(schedule.attr("checked")){
//        	scheduleValue = schedule.val();
//        }
//        var workweek = jQuery("#workweek");
//        var workweekValue = "";
//        if(workweek.attr("checked")){
//        	workweekValue = workweek.val();
//        }
//        +"&schedule="+scheduleValue+"&workweek="+workweekValue
        $("#listMon").load(_contextPath + "/office/desktop/memo/memo-listMon.action?date=" + date_now);
        e.stopPropagation();
      });
      $("._blank").each(function(e, i) {
        $(this).parents("td").removeClass("hover cur hover_1").unbind("click");
        // $(this).parents("td").addClass("_blank");
      });
    }, 'json');
  },
  undisplayLebel : function(ym, num) {
    if (num != undefined) {
      num = num < 10 ? "0" + num : num;
    }
    var temp = "<span style=\"display:none\">";// 
    temp += ym + num;
    temp += "</span>";
    return temp;
  },

  // 隐藏所有行
  undisplayTableRow : function(elementId) {
    var mytable = elementId;
    var rowlen = mytable.rows.length;
    for ( var i = rowlen - 1; i >= 0; i--) {
      mytable.rows[i].style.display = "none"; // currDT突出显示tyle.display = "block"; // currDT突出显示
    }
  },

  getYearWeek : function(a, b, c) {// 得到年的周数（不准）
    var d1 = new Date(a, b, c), d2 = new Date(a, 0, 1), d = Math.round((d1 - d2) / 86400000);
    return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7);
  },

  getMonthWeek : function(a, b, c) {// 得到月的周数
    /*
     * a = d = 当前日期 b = 6 - w = 当前周的还有几天过完（不算今天） a + b 的和在除以7 就是当天是当前月份的第几周
     */
    var date = new Date(a, parseInt(b), c), w = date.getDay(), d = date.getDate();
    return Math.ceil((d + 6 - w) / 7);
  }
};
/* 通用的日期格式化方法 */
function formatDate(date){
  var year = date.getYear();
  var month = date.getMonth();
  month = month <9 ? "0"+(month+1) : (month+1);
  var day = date.getDate();
  day = day <10 ? "0"+day : day;
  return year+""+month+""+day;
}