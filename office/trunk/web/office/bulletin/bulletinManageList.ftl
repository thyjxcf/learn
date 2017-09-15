<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletin/bulletin.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toManage(){
	var show =  document.getElementById("show").value;
	var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	load("#container", url);
}

function toAudit(){
	var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toAuditSet(){
	var url="${request.contextPath}/office/bulletin/bulletin-auditSet.action?bulletinType=${bulletinType!}";
	load("#container", url);
}
function addBulletin(){
	var publishName=document.getElementById("publishName").value;
	var searName =  document.getElementById("searName").value;
    var show =  document.getElementById("show").value;
    searName = encodeURI(searName);
	publishName=encodeURI(publishName);
	var searchAreaId = document.getElementById("searchAreaId").value;
	var url="${request.contextPath}/office/bulletin/bulletin-add.action?bulletinType=${bulletinType!}&show="+show+"&searName="+searName+"&publishName="+publishName+"&searchAreaId="+searchAreaId;
    load("#container",url);
}

function updateBulletin(id){
	var publishName=document.getElementById("publishName").value;
	var searName =  document.getElementById("searName").value;
    var show =  document.getElementById("show").value;
    searName = encodeURI(searName);
	publishName=encodeURI(publishName);
	var searchAreaId = document.getElementById("searchAreaId").value;
	var url="${request.contextPath}/office/bulletin/bulletin-edit.action?bulletinType=${bulletinType!}&bulletinId="+id+"&show="+show+"&searName="+searName+"&publishName="+publishName+"&searchAreaId="+searchAreaId;
    load("#container",url);
}

function deleteBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要删除吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-delete.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 删除失败，显示失败信息
	        showMsgError(data);
	      } else {//删除成功
	      	showMsgSuccess("删除成功！", "提示", function(){
				var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function publishBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要提交吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-submit.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("提交成功！", "提示", function(){
				var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function publishBulletins(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-publish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("发布成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
function qxPublishBulletins(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要取消发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-qxPublish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {
	        showMsgError(data);
	      } else {
	      	showMsgSuccess("取消成功！", "提示", function(){
				var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
function viewBulletin(id){
	window.open("${request.contextPath}/office/bulletin/bulletin-onlyViewDetail.action?bulletinType=${bulletinType!}&bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}

function topHead(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定置顶吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-top.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 置顶失败，显示失败信息
	        showMsgError(data);
	      } else {//置顶成功
	      	showMsgSuccess("置顶成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function qxtop(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定取消置顶吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-qxtop.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 取消置顶失败，显示失败信息
	        showMsgError(data);
	      } else {//取消置顶成功
	      	showMsgSuccess("取消置顶成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function query2(id){
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
	$('#type').val(id);
	<#else>
	$('#show').val(id);
	</#if>
	doquery2();
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
	
	var type;
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		type=$("#type").val();
	</#if>
	if(type==null||type==""){
		type="";
	}
	
	
	var publishName=document.getElementById("publishName").value;
	var searName =  document.getElementById("searName").value;
    var show =  document.getElementById("show").value;
    searName = encodeURI(searName);
	publishName=encodeURI(publishName);
	var searchAreaId = document.getElementById("searchAreaId").value;
    var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show+"&searName="+searName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName+"&searchAreaId="+searchAreaId+"&type="+type;
    load("#container",url);
}
function doqueryno2(){
    var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
    load("#container",url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		<li onclick="toView();">单位通知公告</li>
		<#if megAdmin>
		<li class="current" onclick="toManage();">通知公告管理</li>
		</#if>
		<#if shheAdmin && needAudit>
		<li onclick="toAudit();">通知公告审核</li>
		</#if>
	<#else>
		<li onclick="toView();">单位${bulletinName!}</li>
		<#if megAdmin>
		<li class="current" onclick="toManage();">${bulletinName!}管理</li>
		</#if>
		<#if shheAdmin && needAudit>
		<li onclick="toAudit();">${bulletinName!}审核</li>
		</#if>
		<#if megAdmin&&bulletinType=='2'&&xinJiangDeploy>
		<li onclick="toAuditSet();">审核权限设置</li>
		</#if>
	</#if>
	</ul>
</div>
<form id="dataform" name="dataform">
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt" style="margin-top:-3px;">
    				<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
    				<input type="hidden" name="type" id="type" value="${type!}">
    				<#else>
    				<input type="hidden" name="show" id="show" value="${show!}">
    				</#if>
			    	<span class="user-sList user-sList-radio fn-left">
			    	
			    	<#if bulletinType!="1"||xinJiangDeploy||jiAnLiuZhongDeploy>
				    	<#if needAudit>
				        <span <#if show?default("")=="0">class="current"</#if> data-select="0" onclick="query2('0')">全部</span>
				        <span <#if show?default("")=="1">class="current"</#if> data-select="1" onclick="query2('1')">未提交</span>
				        <span <#if show?default("")=="2">class="current"</#if> data-select="2" onclick="query2('2')">未审核</span>
				        <span <#if show?default("")=="3">class="current"</#if> data-select="3" onclick="query2('3')">已通过</span>
				        <span <#if show?default("")=="4">class="current"</#if> data-select="4" onclick="query2('4')">未通过</span>
				    	<#else>
			    		<span <#if show?default("")=="0">class="current"</#if> data-select="0" onclick="query2('0')">全部</span>
			    		<span <#if show?default("")=="1">class="current"</#if> data-select="1" onclick="query2('1')">未发布</span>
			    		<span <#if show?default("")=="3">class="current"</#if> data-select="3" onclick="query2('3')">已发布</span>
				    	</#if>
				    <#else>
				    	<span <#if type?default("")=="">class="current"</#if> data-select="" onclick="query2('')">全部</span>
			    		<span <#if type?default("")=="1">class="current"</#if> data-select="1" onclick="query2('1')">通知</span>
			    		<span <#if type?default("")=="3">class="current"</#if> data-select="3" onclick="query2('3')">公告</span>
			    	</#if>
			    	</span>
    			</div>
    			
    			<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		    	<div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
		    	<div class="ui-select-box fn-left" style="width:120px;">
		            <input type="text" class="ui-select-txt" value=""  readonly/>
		            <input name="show" id="show" type="hidden" value=""  class="ui-select-value" />
		            <a class="ui-select-close"></a>
		            <div class="ui-option" myfunchange="doquery2">
		        		<div class="a-wrap">
		            	<a val="0"><span>全部</span></a>
		            	<#if needAudit>
		            		<a val="1" <#if show?default("") == "1">class="selected"</#if>><span>未提交</span></a>
		                	<a val="2" <#if show?default("") == "2">class="selected"</#if>><span>未审核</span></a>
		                	<a val="3" <#if show?default("") == "3">class="selected"</#if>><span>已通过</span></a>
		                	<a val="4" <#if show?default("") == "4">class="selected"</#if>><span>未通过</span></a>
		                <#else>
		                	<a val="1" <#if show?default("") == "1">class="selected"</#if>><span>未发布</span></a>
		                	<a val="3" <#if show?default("") == "3">class="selected"</#if>><span>已发布</span></a>
		                </#if>
		                </div>
		            </div>
			    </div>
			    </#if>
    			
    			<div class="query-tt" <#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
    				<span class="fn-left">&nbsp;校区：</span>
    			</div>
    			<#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	    			<@common.select style="width:100px;float:left;display:none;" valName="searchAreaId" valId="searchAreaId" notNull="true" myfunchange="doquery2">
						<a val="">全部</a>
						<#if teachAreaList?exists && teachAreaList?size gt 0>
	                		<#list teachAreaList as area>
	                			<a val="${area.id!}" <#if searchAreaId?default('') == area.id>class="selected"</#if>>${area.areaName!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
				<#else>
					<@common.select style="width:100px;float:left;" valName="searchAreaId" valId="searchAreaId" notNull="true" myfunchange="doquery2">
					<a val="">全部</a>
					<#if teachAreaList?exists && teachAreaList?size gt 0>
                		<#list teachAreaList as area>
                			<a val="${area.id!}" <#if searchAreaId?default('') == area.id>class="selected"</#if>>${area.areaName!}</a>
                		</#list>
                	</#if>
				</@common.select>
    			</#if>
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
					<input id="searName" type="text" class="input-txt" style="width:200px;" value="${searName!}">
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
			    <#if !needAudit>
			    	<a href="javascript:doPublish();" class="abtn-blue" style="position:absolute;top:-90px;right:200px;">发布</a>
				</#if>
				<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
	            <a href="javascript:void(0);" class="abtn-orange-new" style="position:absolute;top:-95px;right:0;" onclick="addBulletin()">新建通知公告</a>
	            <#else>
	            <a href="javascript:void(0);" class="abtn-orange-new" style="position:absolute;top:-95px;right:0;" onclick="addBulletin()">新建${bulletinName!}</a>
	            </#if>
	        </div>
		</div>
	</div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="3%"><span class="ui-checkbox ui-checkbox-all" style="width:15px;padding-left:5px;" data-all="no"><input type="checkbox" class="chk"></span></th>
		<th width="39%">标题</th>
		<th width="14%">创建人(所属部门)</th>
		<th width="8%">创建时间</th>
		<th width="8%">截止时间</th>
		<th width="6%">点击量</th>
		<th width="7%">排序号</th>
		<th width="15%">操作</th>
	</tr>
	<#if officeBulletinList?exists && officeBulletinList?size gt 0>
    		<#list officeBulletinList as bulletin>
    		<tr>
			<td>
				<span class="ui-checkbox"><input type="checkbox" class="chk" name="checkid" value="${bulletin.id?default('')}"></span>
			</td>
	        <td>
				<span style="cursor:pointer;" onclick="viewBulletin('${bulletin.id!}');">
					
						<#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
		        			【${appsetting.getMcode("DM-ZCWJ").get(bulletin.scope?default('1')?string!)}】
		        		<#else>
		        		<#if  teachAreaList?size gt 1>
		        			<#if loginInfo.unitClass == 2>
								【${bulletin.areaName!}】
							</#if>
		        		</#if>
	        		</#if>
					<@common.cutOff4List str="${bulletin.title!}" length=32 />
				</span>
			</td>
			<td>
				${bulletin.createUserName!}
				<#if bulletin.deptName?exists && bulletin.deptName != ''>
					(${bulletin.deptName!})
				</#if>
			</td>
	        <td>
        		${(bulletin.createTime?string('yy-MM-dd'))?if_exists}
            </td>
	        <td>
        		${appsetting.getMcodeName("DM-JZSJ",bulletin.endType?default("2"))?default("一年")}
    		</td>
	        <td>
        		${bulletin.clickNum!}
    		</td>
	        <td>
	        	<input type="text" class="input-txt" style="width:50px;" id="${bulletin.id!}_orderId" name="orderId" value="${bulletin.orderId!}" maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
	        	<input type="hidden" id="${bulletin.id!}_bulletinIds" name="bulletinId" value="${bulletin.id!}"/>
	        </td>
	        <td>
            	<#if !(needAudit && bulletin.state == "2")>
	            	<a href="javascript:void(0);" onclick="updateBulletin('${bulletin.id!}');" class="mt-5">编辑</a>
	            	<a href="javascript:void(0);" onclick="deleteBulletin('${bulletin.id!}');" class="mt-5">删除</a>
            	</#if>
                <#if bulletin.state == '1'>
	                <#if needAudit>
	                	<a href="javascript:void(0);" onclick="publishBulletin('${bulletin.id!}');">提交</a>
	                <#else>
	                	<a href="javascript:void(0)" onclick="publishBulletins('${bulletin.id!}')">发布</a>
	                </#if>
                <#elseif bulletin.state == "2">
               		<#if needAudit>
	                	<img src="${request.contextPath}/static/images/icon/right.png">&nbsp;已提交
	               	<#else>
	               		<a href="javascript:void(0)" onclick="publishBulletins('${bulletin.id!}')" >发布</a>
	               	</#if>
	          <#elseif bulletin.state == "3">
	           <#if needAudit>
	                <img src="${request.contextPath}/static/images/icon/right.png">&nbsp;已通过
	            <#else>
	            	<#if bulletin.isTop>
                		<a href="javascript:void(0)" onclick="qxtop('${bulletin.id}')" style="display:none;">取消置顶</a>
                	<#else>
                		<a href="javascript:void(0)" onclick="topHead('${bulletin.id}')" style="display:none;">置顶</a>
                	</#if>
	            	<a href="javascript:void(0)" onclick="qxPublishBulletins('${bulletin.id!}')">取消发布</a>
	            </#if>
	          <#elseif bulletin.state == "4">
	                <span title="${bulletin.idea!}"><img src="${request.contextPath}/static/images/icon/wrong.png" alt="">&nbsp;未通过</span>
                </#if>
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
    <#if !needAudit>
    	<a href="javascript:doPublish();" class="abtn-blue">发布</a>
	</#if>
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
		url: "${request.contextPath}/office/bulletin/bulletin-deleteIds.action",
		data: $.param( {bulletinIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("删除成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
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
	<#if needAudit>
	if(!confirm("确定要发布吗？(注：审核未通过的无法发布)")){
		return;
	}
	<#else>
	if(!confirm("确定要发布吗？")){
		return;
	}
	</#if>
	var show =  document.getElementById("show").value;
	var i = 0;
	$("input[name='checkid'][checked='checked']").each(function(){
		ids[i] = $(this).val();
		i++;
	});
	$.ajax({
		type: "POST",
		url: "${request.contextPath}/office/bulletin/bulletin-publishIds.action",
		data: $.param( {bulletinIds:ids},true),
		success: function(data){
			if(data != null && data!=''){
				showMsgError(data);
				return;
			}else{
				showMsgSuccess("发布成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
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
		url: "${request.contextPath}/office/bulletin/bulletin-saveOrder.action?bulletinType=${bulletinType!}",
		data: $.param( {bulletinIds:bIds,orderIds:oIds},true),
		success: function(data){
			if(data != null && data!=''){//保存排序失败，显示失败信息
				showMsgError(data);
				return;
			}else{//保存排序成功
				showMsgSuccess("保存排序成功！", "提示", function(){
					var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}&show="+show;
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