<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletinTl/bulletinTl.action";
	load("#container", url);
}

function toManage(){
	var show = document.getElementById("show").value;
	var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	load("#container", url);
}

function addBulletin(){
	var publishName = document.getElementById("publishName").value;
	var searchName = document.getElementById("searchName").value;
    var show = document.getElementById("show").value;
    searchName = encodeURI(searchName);
	publishName = encodeURI(publishName);
	var url="${request.contextPath}/office/bulletinTl/bulletinTl-add.action?show="+show+"&searchName="+searchName+"&publishName="+publishName;
    load("#container",url);
}

function updateBulletin(id){
	var publishName = document.getElementById("publishName").value;
	var searchName = document.getElementById("searchName").value;
    var show = document.getElementById("show").value;
    searchName = encodeURI(searchName);
	publishName = encodeURI(publishName);
	var url="${request.contextPath}/office/bulletinTl/bulletinTl-edit.action?bulletinId="+id+"&show="+show+"&searchName="+searchName+"&publishName="+publishName;
    load("#container",url);
}

function deleteBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要删除吗？")){
	    $.getJSON(_contextPath + "/office/bulletinTl/bulletinTl-delete.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 删除失败，显示失败信息
	        showMsgError(data);
	      } else {//删除成功
	      	showMsgSuccess("删除成功！", "提示", function(){
				var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function publishBulletins(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletinTl/bulletinTl-publish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("发布成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
function qxPublishBulletins(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要取消发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletinTl/bulletinTl-qxPublish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("取消成功！", "提示", function(){
				var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
function viewBulletin(id){
	window.open("${request.contextPath}/office/bulletinTl/bulletinTl-onlyViewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}

function topHead(id,topState){
	var show =  document.getElementById("show").value;
	if(confirm("确定置顶吗？")){
	    $.getJSON(_contextPath + "/office/bulletinTl/bulletinTl-top.action?bulletinId="+id, {"topState":topState}, 
	    function(data) {
	      if (data!=null && data != '') {// 置顶失败，显示失败信息
	        showMsgError(data);
	      } else {//置顶成功
	      	showMsgSuccess("置顶成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function query2(show){
	$('#show').val(show);
	doqueryno2();
}

function doquery2(){
	var startTime= document.getElementById("startTime").value;
	var endTime= document.getElementById("endTime").value;
	if(startTime!=''&&endTime!=''){
		var re = compareDate(startTime,endTime);
		if(re==1){
			addFieldError(document.getElementById("endTime"),"结束时间不能小于开始时间！");
			isSubmit=false;
			return false;
		}
	}
	var publishName = document.getElementById("publishName").value;
	var searchName = document.getElementById("searchName").value;
    var show = document.getElementById("show").value;
    searchName = encodeURI(searchName);
	publishName=encodeURI(publishName);
    var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show+"&searchName="+searchName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName;
    load("#container",url);
}
function doqueryno2(){
    var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
    load("#container",url);
}

function remindBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要对未读通知人员发送短信提醒吗？")){
	    $.getJSON(_contextPath + "/office/bulletinTl/bulletinTl-remind.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发送失败，显示失败信息
	        showMsgError(data);
	      } else {//发送成功
	      	showMsgSuccess("短信发送成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="toView();">单位通知</li>
	<li class="current" onclick="toManage();">通知管理</li>
	</ul>
</div>
<form id="dataform" name="dataform">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt" style="margin-top:-3px;">
    				<input type="hidden" name="show" id="show" value="${show!}">
			    	<span class="user-sList user-sList-radio fn-left">
			    		<span <#if show?default("")=="0">class="current"</#if> data-select="0" onclick="query2('0')">全部</span>
			    		<span <#if show?default("")=="1">class="current"</#if> data-select="1" onclick="query2('1')">未发布</span>
			    		<span <#if show?default("")=="3">class="current"</#if> data-select="3" onclick="query2('3')">已发布</span>
			    	</span>
    			</div>
				<span style="display:none;">
    			开始日期：<@common.datepicker class="input-txt" style="width:100px;" id="startTime" 
				   value="${startTime!}"/>
    			结束日期：<@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
				   value="${endTime!}"/>
				</span>
				<div class="query-tt ml-10">
					<span class="fn-left">标题：</span>
				</div>
				<div class="fn-left">
					<input id="searchName" type="text" class="input-txt" style="width:200px;" value="${searchName!}">
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">创建人：</span>
				</div>
				<div class="fn-left">
					<input id="publishName" type="text" class="input-txt" style="width:100px;" value="${publishName!}">
				</div>
				&nbsp;<a href="javascript:void(0)" onclick="doquery2();" class="abtn-blue">查找</a>
			<div>
	    	<div class="mt-15 fn-rel">
			    <a href="javascript:doDeleted();" class="abtn-blue" style="position:absolute;top:-90px;right:370px;">删除</a>
		    	<a href="javascript:saveOrder();" class="abtn-blue" style="position:absolute;top:-90px;right:270px;">保存排序</a>
		    	<a href="javascript:doPublish();" class="abtn-blue" style="position:absolute;top:-90px;right:200px;">发布</a>
	            <a href="javascript:void(0);" class="abtn-orange-new" style="position:absolute;top:-95px;right:0;" onclick="addBulletin()">新建通知</a>
	        </div>
		</div>
	</div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="3%"><span class="ui-checkbox ui-checkbox-all" style="width:15px;padding-left:5px;" data-all="no"><input type="checkbox" class="chk"></span></th>
		<th width="34%">标题</th>
		<th width="14%">创建人(所属部门)</th>
		<th width="8%">创建时间</th>
		<th width="8%">截止时间</th>
		<th width="6%">点击量</th>
		<th width="7%">排序号</th>
		<th width="20%" class="t-center">操作</th>
	</tr>
	<#if officeBulletinTlList?exists && officeBulletinTlList?size gt 0>
		<#list officeBulletinTlList as bulletinTl>
		<tr>
			<td>
				<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${bulletinTl.id?default('')}"></span>
			</td>
	        <td>
				<span style="cursor:pointer;" onclick="viewBulletin('${bulletinTl.id!}');">
					<@common.cutOff4List str="${bulletinTl.title!}" length=32 />
				</span>
			</td>
			<td>
				${bulletinTl.createUserName!}
				<#if bulletinTl.deptName?exists && bulletinTl.deptName != ''>
					(${bulletinTl.deptName!})
				</#if>
			</td>
	        <td>
        		${(bulletinTl.createTime?string('yy-MM-dd'))?if_exists}
            </td>
	        <td>
        		${appsetting.getMcodeName("DM-JZSJ",bulletinTl.endType?default("2"))?default("一年")}
    		</td>
	        <td>
        		${bulletinTl.clickNum!}
    		</td>
	        <td>
	        	<input type="text" class="input-txt" style="width:50px;" id="${bulletinTl.id!}_orderId" name="orderId" value="${bulletinTl.orderId!}" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
	        	<input type="hidden" id="${bulletinTl.id!}_bulletinIds" name="bulletinId" value="${bulletinTl.id!}"/>
	        </td>
	        <td class="t-center">
            	<a href="javascript:void(0);" onclick="updateBulletin('${bulletinTl.id!}');" class="mt-5">编辑</a>
            	<a href="javascript:void(0);" onclick="deleteBulletin('${bulletinTl.id!}');" class="mt-5">删除</a>
	        	<#if bulletinTl.state == "1">
	            	<a href="javascript:void(0)" onclick="publishBulletins('${bulletinTl.id!}')" class="mt-5">发布</a>
	          	<#elseif bulletinTl.state == "3">
	            	<a href="javascript:void(0)" onclick="qxPublishBulletins('${bulletinTl.id!}')" class="mt-5">取消发布</a>
	            </#if>
	            <a href="javascript:void(0);" onclick="remindBulletin('${bulletinTl.id!}');">短信提醒</a>
        	</td>
        </tr>
        </#list>
    <#else>
    <tr>
    	<td colspan="8">
        	<p class="no-data mt-50 mb-50">还没有数据哦！</p>
        </td>
    </tr>
    </#if>
</@common.tableList>
<@common.Toolbar container="#container">
<span class="ui-checkbox ui-checkbox-all" data-all="no" style="margin-left:-10px;"><input type="checkbox" class="chk">全选</span>
    <a href="javascript:doDeleted();" class="abtn-blue">删除</a>
	<a href="javascript:saveOrder();" class="abtn-blue">保存排序</a>
	<a href="javascript:doPublish();" class="abtn-blue">发布</a>
	<a href="javascript:doRemind();" class="abtn-blue">短信提醒</a>
</@common.Toolbar>
</form>               
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(function(){
	$('.notice-item .dd .more').click(function(e){
		e.preventDefault();
		$('.notice-item .dd').show().next('.dd-all').hide();
		$(this).parent('.dd').hide().next('.dd-all').show();
	});
	
	$('.btn-fail').click(function(e){
		e.preventDefault();
		$(this).siblings('.state-layer').show();
	});
	$('.state-layer .abtn-submit,.state-layer .abtn-reset').click(function(e){
		e.preventDefault();
		$(this).parents('.state-layer').hide();
	});
	
});

function doDeleted(){
	var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要删除吗？")){
		return;
	}
	var show =  document.getElementById("show").value;
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletinTl/bulletinTl-deleteIds.action",
		data: $.param( {bulletinIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("删除成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
		    		load("#container",url);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
function doPublish(){
	var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要发布吗？")){
		return;
	}
	var show =  document.getElementById("show").value;
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletinTl/bulletinTl-publishIds.action",
		data: $.param( {bulletinIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("发布成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
		    		load("#container",url);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function saveOrder(){
	var show =  document.getElementById("show").value;
	var orderIds_ = document.getElementsByName("orderId");
	if(orderIds_.length == 0){
		showMsgWarn("没有数据需要保存");
		return;
	}
	var bulletinIds_ = document.getElementsByName("bulletinId");
	var oIds = [];
	var bIds = [];
	var flag = false;
	for(var i=0;i<orderIds_.length;i++){
		if(orderIds_[i].value==''){
			flag = true;
			break;
		}
		oIds[i] = orderIds_[i].value;
		bIds[i] = bulletinIds_[i].value;
	}
	if(flag){
		showMsgWarn("排序不能为空");
		return;
	}
    $.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletinTl/bulletinTl-saveOrder.action",
		data: $.param( {bulletinIds:bIds,orderIds:oIds},true),
		success: function(data){
			if(data != null && data!=''){//保存排序失败，显示失败信息
				showMsgError(data);
				return;
			}else{//保存排序成功
				showMsgSuccess("保存排序成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
		    		load("#container",url);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}

function doRemind(){
	var ids = [];
	if(isCheckBoxSelect($("[name='checkid']")) == false){
		showMsgWarn("请先选择想要进行操作的数据！");
		return;
	}
	if(!confirm("确定要对未读通知人员发送短信提醒吗？")){
		return;
	}
	var show =  document.getElementById("show").value;
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletinTl/bulletinTl-remindIds.action",
		data: $.param( {bulletinIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("短信发送成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action?show="+show;
		    		load("#container",url);
				});
				return;
			}
		},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){alert(XMLHttpRequest.status);}
	});
}
</script>
</@common.moduleDiv>