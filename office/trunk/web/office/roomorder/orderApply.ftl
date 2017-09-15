<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function myOrder(){
	var url="${request.contextPath}/office/roomorder/roomorder.action";
	load("#container", url);
}

function orderApply(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderApply.action";
	load("#container", url);
}

function orderAudit(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderAudit.action";
	load("#container", url);
}

function timeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-timeSet.action";
	load("#container", url);
}

function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}

function labTypeSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-labTypeSet.action";
	load("#container", url);
}

function labApplyCount(){
	var url="${request.contextPath}/office/roomorder/roomorder-labApplyCount.action";
	load("#container", url);
}

function getWeek(todayDate)
{
  var msg = '';
  switch (todayDate.getDay())
  {
    case 0:
      msg += "星期日";
      break;
    case 1:
      msg += "星期一";
      break;
    case 2:
      msg += "星期二";
      break;
    case 3:
      msg += "星期三";
      break;
    case 4:
      msg += "星期四";
      break;
    case 5:
      msg += "星期五";
      break;
    case 6:
      msg += "星期六";
      break;
    default:
      break;
  }
  return msg;
}


function clock_pulse(date){
  var obj = document.getElementById('main_clock');
  if(date && date!=''){
	  if (obj) obj.innerHTML = getWeek(date);
  }else{
  	if (obj) obj.innerHTML = '';
  }
}

function searchOrder(){
	var roomType=$("#roomType").val();
	var currentTime = document.getElementById("currentTime").value;
	if(roomType!='' && currentTime!=''){
		var url="${request.contextPath}/office/roomorder/roomorder-orderApplyList.action";
		url += "?roomType="+roomType;
		url += "&currentTime="+currentTime;
		load("#orderApplyListDiv", url);
	}else{
		load('#orderApplyListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择类型！');
	}
	var date;
	if(currentTime == ''){
		date = new Date();
	}else{
		date = new Date(currentTime);
	}
	clock_pulse(date);
}

function changeDay(value){
	var currentTime = document.getElementById("currentTime").value;
	var date;
	if(currentTime == ''){
		date = new Date();
	}else{
		var oDate1 = currentTime.split("-"); 
		date = new Date(oDate1[0], oDate1[1]-1, oDate1[2]); 
		//date = new Date(currentTime);
	}
	var day = addDate(date,value);
	var str = day.getFullYear();
	if(day.getMonth()<9){
		str += "-0" + (day.getMonth()+1);
	}else{
		str += "-" + (day.getMonth()+1);
	}
	if(day.getDate()<10){
		str += "-0" + (day.getDate());
	}else{
		str += "-" + (day.getDate());
	}
	document.getElementById("currentTime").value = str;
	searchOrder();
}
function orderSet(){
	var url="${request.contextPath}/office/roomorder/roomorder-orderSet.action";
	load("#container", url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="myOrder();">我的预约</li>
	<li class="current" onclick="orderApply();">预约申请</li>
	<#if auditAdmin>
	<li onclick="orderAudit();">预约审核</li>
	<li onclick="timeSet();">时段设置</li>
	<li onclick="orderSet();">类型信息设置</li>
	<#if !edu>
	<li onclick="labTypeSet();">实验种类设置</li>
	</#if>
	</#if>
	<#if !edu>
	<li onclick="labApplyCount();">实验申请统计</li>
	</#if>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
				<div class="query-tt ml-10">
					<span class="fn-left">类型：</span>
				</div>
				<div class="fn-left">
					<@common.select style="width:170px;" valName="roomType" valId="roomType" myfunchange="searchOrder">
						<a val="">请选择</a>
						<#list officeRoomOrderSetList as item>
						<a val="${item.thisId}" <#if officeRoomOrderSet?exists&&officeRoomOrderSet.thisId==item.thisId> class="selected" </#if>>${appsetting.getMcode('DM-CDLX').get(item.thisId?default(''))}</a>
						</#list>
					</@common.select>
				</div>
			</div>
    	</div>
    </div>
</div>
<div id="container">
	<div class="fn-clear">
		
    	<p class="typical-tt fn-left">
    	    <span>&nbsp;&nbsp;&nbsp;</span>
    		<span class="block green"></span>
    		<span>本人已申请</span>
    		<span class="block gray ml-20"></span>
    		<span>他人已抢占</span>
    		<span class="block orange ml-20"></span>
    		<span>审核中</span>
    		<span class="block red ml-20"></span>
    		<span>未通过</span>
    		<span class="c-red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本周及下周可申请</span>
    	</p>
        <p class="typical-time fn-right">
        	<a href="javascript:void(0);" onclick="changeDay(-1);" class="prev"></a>
        	<@common.datepicker class="input-txt time" style="width:100px;" id="currentTime" value="${currentTime?string('yyyy-MM-dd')}" onpicked="function(){searchOrder();}" oncleared="function(){$('#orderApplyListDiv').html('');clock_pulse('');}"/>
        	<a href="javascript:void(0);" onclick="changeDay(1);" class="next"></a>
        </p>
        
    	<div class="fn-right mr-10" id="main_clock">
			
		</div>
    </div>
	<div id="orderApplyListDiv"></div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
function addDate(dd,dadd){
	var a = dd;
	a = a.valueOf();
	a = a + dadd * 24 * 60 * 60 * 1000;
	a = new Date(a);
	return a;
}
$(document).ready(function(){
<#if officeRoomOrderSet?exists>
	$("#roomType").val('${officeRoomOrderSet.thisId!}');
	searchOrder();
<#else>
	load('#orderApplyListDiv','${request.contextPath}/common/tipMsg.action?msg=请选择类型！');
</#if>
});
</script>
</@common.moduleDiv>