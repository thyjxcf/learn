<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toManage(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj-manageList.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toAudit(){
	var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
	load("#container", url);
}

function toAuditSet(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditSet.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function query3(id){
	$('#show').val(id);
	doqueryno3();
}

function doquery3(){
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
	var publishName= document.getElementById("publishName").value;
    var show =  document.getElementById("show").value;
    var searName =  document.getElementById("searName").value;
	searName = encodeURI(searName);
	publishName=encodeURI(publishName);
    var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show+"&searName="+searName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName;
    load("#container",url);
}
function doqueryno3(){
    var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
    load("#container",url);
}
</script>
<!--div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div-->
<div class="popUp-layer" id="bulletinLayer" style="display:none;width:700px;580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li onclick="toView();">单位${bulletinName!}</li>
	<#if megAdmin>
	<li onclick="toManage();">${bulletinName!}管理</li>
	</#if>
	<#if shheAdmin>
	<li class="current" onclick="toAudit();">${bulletinName!}审核</li>
	</#if>
	<#if megAdmin&&bulletinType=='12'>
	<li onclick="toAuditSet();">审核权限设置</li>
	</#if>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
    	<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">  
    		<div class="query-part">
    			<div class="query-tt" style="margin-top:-3px;">
    				<input type="hidden" name="show" id="show" value="${show!}">
	                <span class="user-sList user-sList-radio"><!--多选：user-sList-checkbox；单选：user-sList-radio-->
	                	  <span <#if show?default("")=="5">class="current"</#if> data-select="0" onclick="query3('5')">全部</span>
					      <span <#if show?default("")=="2">class="current"</#if> data-select="2" onclick="query3('2')">未审核</span>
					      <span <#if show?default("")=="3">class="current"</#if> data-select="3" onclick="query3('3')">通过</span>
					      <span <#if show?default("")=="4">class="current"</#if> data-select="4" onclick="query3('4')">不通过</span>
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
					<input id="searName" type="text" class="input-txt" style="width:200px;" value="${searName!}">
				</div>
				<div class="query-tt ml-10">
					<span class="fn-left">创建人：</span>
				</div>
				<div class="fn-left">
					<input id="publishName" type="text" class="input-txt" style="width:100px;" value="${publishName!}">
				</div>
				&nbsp;<a href="javascript:void(0)" onclick="doquery3();" class="abtn-blue">查找</a>
        	</div>
    	</div>
	</div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="53%">标题</th>
		<th width="8%">创建时间</th>
		<th width="8%">截止时间</th>
		<th width="14%">创建人(所属部门)</th>
		<th width="17%">操作</th>
	</tr>
	 <#if officeBulletinXjList?exists && officeBulletinXjList?size gt 0>
		<#list officeBulletinXjList as bulletin>
		<tr>
			<td>
        		<span style="cursor:pointer;" onclick="viewBulletin('${bulletin.id!}');">
        		<#if  teachAreaList?size gt 1 && loginInfo.unitClass == 2>
					【${bulletin.areaName!}】
        		</#if>
    			<@common.cutOff4List str="${bulletin.title!}" length=40 />
        		</span>
        	</td>
        	<td>
				${(bulletin.createTime?string('yy-MM-dd'))?if_exists}
			</td>
        	<td>
        		${appsetting.getMcodeName("DM-JZSJ",bulletin.endType?default("2"))?default("一年")}
			</td>
        	<td>
    			${bulletin.createUserName!}
				<#if bulletin.deptName?exists && bulletin.deptName != ''>
					(${bulletin.deptName!})
				</#if>
			</td>
        	<td>
                <#if bulletin.state == "2">
                	<span class="state-wrap" style="z-index:999;">
                		<a href="javascript:void(0)" class="btn-fail">不通过</a>
                    	<a href="javascript:void(0)" onclick="publishBulletin('${bulletin.id!}')">通过并发布</a>
                        <div class="state-layer">
                            <p><textarea id="${bulletin.id!}"></textarea></p>
                            <p>
                                <a href="javascript:void(0);" onclick="publishUnBulletin('${bulletin.id!}')" class="abtn-submit">确定</a>
                                <a href="javascript:void(0);" class="abtn-reset">取消</a>
                            </p>
            		    </div>
                	</span>
                <#elseif bulletin.state == "3">
                		<#if bulletin.isTop>
	                		<a href="javascript:void(0)" style="display:none;" onclick="qxtop('${bulletin.id}')">取消置顶</a>
	                	<#else>
	                		<a href="javascript:void(0)" style="display:none;" onclick="topHead('${bulletin.id}')">置顶</a>
	                	</#if>
	                	 <img src="${request.contextPath}/static/images/icon/right.png">&nbsp;已通过
                <#elseif bulletin.state == "4">
	                <span title="${bulletin.idea!}"><img src="${request.contextPath}/static/images/icon/wrong.png">&nbsp;未通过</span>
                </#if>
        	</td>
    </tr>
	</#list>
    <#else>
    <tr>
    	<td colspan="5">
    		<p class="no-data mt-50 mb-50">还没有数据哦！</p>
    	</td>
    </tr>
    </#if>	
</@common.tableList>
<@common.Toolbar container="#container"/>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script>
vselect();

$(function(){
	$('.notice-item .dd .more').click(function(e){
		e.preventDefault();
		$('.notice-item .dd').show().next('.dd-all').hide();
		$(this).parent('.dd').hide().next('.dd-all').show();
	});
	
	$('.btn-fail').click(function(e){
		e.preventDefault();
		$('.state-layer').hide();
		$(this).siblings('.state-layer').show();
	});
	$('.state-layer .abtn-submit,.state-layer .abtn-reset').click(function(e){
		e.preventDefault();
		$(this).parents('.state-layer').hide();
	});
});

function viewBulletin(id){
	window.open("${request.contextPath}/office/bulletinXj/bulletinXj-onlyViewDetail.action?bulletinType=${bulletinType!}&bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}

function publishBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletinXj/bulletinXj-publish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("发布成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function publishUnBulletin(id){
	var show =  document.getElementById("show").value;
	var idea =  document.getElementById(id).value;
	if(idea.length > 100){
		showMsgError('原因不能超过200个字符');
		return false;
	}
	idea = encodeURI(idea);
	if(confirm("确定要不通过吗？")){
	    $.getJSON(_contextPath + "/office/bulletinXj/bulletinXj-unpublish.action?bulletinId="+id+"&idea="+idea, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("操作成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function topHead(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定置顶吗？")){
	    $.getJSON(_contextPath + "/office/bulletinXj/bulletinXj-top.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 置顶失败，显示失败信息
	        showMsgError(data);
	      } else {//置顶成功
	      	showMsgSuccess("置顶成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}

function qxtop(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定取消置顶吗？")){
	    $.getJSON(_contextPath + "/office/bulletinXj/bulletinXj-qxtop.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 取消置顶失败，显示失败信息
	        showMsgError(data);
	      } else {//取消置顶成功
	      	showMsgSuccess("取消置顶成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
</script>
</@common.moduleDiv>