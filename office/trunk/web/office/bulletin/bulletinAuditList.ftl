<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletin/bulletin.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toManage(){
	var url="${request.contextPath}/office/bulletin/bulletin-manageList.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toAudit(){
	var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
	load("#container", url);
}

function toAuditSet(){
	var url="${request.contextPath}/office/bulletin/bulletin-auditSet.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function query3(id){
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
	$('#type').val(id);
	<#else>
	$('#show').val(id);
	</#if>
	doquery3();
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
    
    var type;
    <#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		type=$("#type").val();
	</#if>
	if(type==null||type==""){
		type="";
	}
    
    var searName =  document.getElementById("searName").value;
	searName = encodeURI(searName);
	publishName=encodeURI(publishName);
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
    var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show+"&searName="+searName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName+"&type="+type;
    <#else>
    var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show+"&searName="+searName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName;
    </#if>
    load("#container",url);
}
function doqueryno3(){
    var show =  document.getElementById("show").value;
    var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
    load("#container",url);
}
</script>
<!--div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div-->
<div class="popUp-layer" id="bulletinLayer" style="display:none;width:700px;580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		<li onclick="toView();">单位通知公告</li>
		<#if megAdmin>
		<li onclick="toManage();">通知公告管理</li>
		</#if>
		<#if shheAdmin && needAudit>
		<li class="current" onclick="toAudit();">通知公告审核</li>
		</#if>
	<#else>
		<li onclick="toView();">单位${bulletinName!}</li>
		<#if megAdmin>
		<li onclick="toManage();">${bulletinName!}管理</li>
		</#if>
		<#if shheAdmin && needAudit>
		<li class="current" onclick="toAudit();">${bulletinName!}审核</li>
		</#if>
		<#if megAdmin&&bulletinType=='2'&&xinJiangDeploy>
		<li onclick="toAuditSet();">审核权限设置</li>
		</#if>
	</#if>
	</ul>
</div>
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
				    	<!--多选：user-sList-checkbox；单选：user-sList-radio-->
		                	  <span <#if show?default("")=="5">class="current"</#if> data-select="0" onclick="query3('5')">全部</span>
						      <span <#if show?default("")=="2">class="current"</#if> data-select="2" onclick="query3('2')">未审核</span>
						      <span <#if show?default("")=="3">class="current"</#if> data-select="3" onclick="query3('3')">通过</span>
						      <span <#if show?default("")=="4">class="current"</#if> data-select="4" onclick="query3('4')">不通过</span>
	               		 
				    <#else>
				    	<span <#if type?default("")=="">class="current"</#if> data-select="" onclick="query3('')">全部</span>
			    		<span <#if type?default("")=="1">class="current"</#if> data-select="1" onclick="query3('1')">通知</span>
			    		<span <#if type?default("")=="3">class="current"</#if> data-select="3" onclick="query3('3')">公告</span>
			    	</#if>
			    	</span>
    			
    			</div>
    			
    			<#if bulletinType=="1"&&!xinJiangDeploy&&!jiAnLiuZhongDeploy>
		    	<div class="query-tt ml-10"><span class="fn-left">状态：</span></div>
		    	<div class="ui-select-box fn-left" style="width:120px;">
		            <input type="text" class="ui-select-txt" value=""  readonly/>
		            <input name="show" id="show" type="hidden" value=""  class="ui-select-value" />
		            <a class="ui-select-close"></a>
		            <div class="ui-option" myfunchange="doquery3">
		        		<div class="a-wrap">
		            	<a val="5"><span>全部</span></a>
		            	<#if needAudit>
		                	<a val="2" <#if show?default("") == "2">class="selected"</#if>><span>未审核</span></a>
		                	<a val="3" <#if show?default("") == "3">class="selected"</#if>><span>通过</span></a>
		                	<a val="4" <#if show?default("") == "4">class="selected"</#if>><span>不通过</span></a>
		                <#else>
		                	<a val="1" <#if show?default("") == "1">class="selected"</#if>><span>未发布</span></a>
		                	<a val="3" <#if show?default("") == "3">class="selected"</#if>><span>已发布</span></a>
		                </#if>
		                </div>
		            </div>
			    </div>
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
	 <#if officeBulletinList?exists && officeBulletinList?size gt 0>
		<#list officeBulletinList as bulletin>
		<tr>
			<td>
        		<span style="cursor:pointer;" onclick="viewBulletin('${bulletin.id!}');">
        		<#if  teachAreaList?size gt 1>
	        		<#if bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	        			【${appsetting.getMcode("DM-ZCWJ").get(bulletin.scope?default('1')?string!)}】
	        		<#else>
		        		【${bulletin.areaName!}】
	        		</#if>
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
	window.open("${request.contextPath}/office/bulletin/bulletin-onlyViewDetail.action?bulletinType=${bulletinType!}&bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}

function publishBulletin(id){
	var show =  document.getElementById("show").value;
	if(confirm("确定要发布吗？")){
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-publish.action?bulletinId="+id, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("发布成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
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
	    $.getJSON(_contextPath + "/office/bulletin/bulletin-unpublish.action?bulletinId="+id+"&idea="+idea, null, 
	    function(data) {
	      if (data!=null && data != '') {// 发布失败，显示失败信息
	        showMsgError(data);
	      } else {//发布成功
	      	showMsgSuccess("操作成功！", "提示", function(){
			var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
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
			var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
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
			var url="${request.contextPath}/office/bulletin/bulletin-auditList.action?bulletinType=${bulletinType!}&show="+show;
	    		load("#container",url);
			});
	      }
	    });
	}
}
</script>
</@common.moduleDiv>