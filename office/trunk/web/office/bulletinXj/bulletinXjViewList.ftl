<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj.action?bulletinType=${bulletinType!}";
	load("#container", url);
}

function toManage(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj-manageList.action?bulletinType=${bulletinType!}&startTime=${defaultStartWeekDay!}&endTime=${defaultEndWeekDay}";
	load("#container", url);
}

function toAudit(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditList.action?bulletinType=${bulletinType!}";
	load("#container", url);
}
function toAuditSet(){
	var url="${request.contextPath}/office/bulletinXj/bulletinXj-auditSet.action?bulletinType=${bulletinType!}";
	load("#container", url);
}
function viewBulletin(obj,id){
	$(obj).css("font-weight","normal");
	window.open("${request.contextPath}/office/bulletinXj/bulletinXj-viewDetail.action?bulletinType=${bulletinType!}&bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
}
function sear(){
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
	var searName =  document.getElementById("searName").value;
	searName = encodeURI(searName);
	publishName=encodeURI(publishName);
	var searchAreaId = document.getElementById("searchAreaId").value;
	var url="${request.contextPath}/office/bulletinXj/bulletinXj.action?bulletinType=${bulletinType!}"+"&searName="+searName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName+"&searchAreaId="+searchAreaId;
	load("#container",url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="toView();">单位${bulletinName!}</li>
	<#if megAdmin>
	<li onclick="toManage();">${bulletinName!}管理</li>
	</#if>
	<#if shheAdmin && needAudit>
	<li onclick="toAudit();">${bulletinName!}审核</li>
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
    			<div class="query-tt" <#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>style="display:none;"</#if>>
    				<span class="fn-left">校区：</span>
    			</div>
    			<#if teachAreaList?size lt 2 || bulletinType == stack.findValue('@net.zdsoft.office.bulletin.entity.OfficeBulletinType@ZCWJ')>
	    			<@common.select style="width:100px;float:left;display:none;" valName="searchAreaId" valId="searchAreaId" notNull="true" myfunchange="sear">
						<a val="">全部</a>
						<#if teachAreaList?exists && teachAreaList?size gt 0>
	                		<#list teachAreaList as area>
	                			<a val="${area.id!}" <#if searchAreaId?default('') == area.id>class="selected"</#if>>${area.areaName!}</a>
	                		</#list>
	                	</#if>
					</@common.select>
				<#else>
					<@common.select style="width:100px;float:left;" valName="searchAreaId" valId="searchAreaId" notNull="true" myfunchange="sear">
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
    			&nbsp;结束日期：<@common.datepicker class="input-txt" style="width:100px;" id="endTime" 
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
				&nbsp;<a href="javascript:void(0)" onclick="sear();" class="abtn-blue">查找</a>
			</div>
    	</div>
    </div>
</div>
<@common.tableList id="tablelist">
	<tr>
		<th width="6%">序号</th>
		<th width="64%">标题</th>
		<th width="13%">创建时间</th>
		<th width="17%">创建人(所属部门)</th>
	</tr>
	<#if officeBulletinXjList?exists && officeBulletinXjList?size gt 0>
	<#list officeBulletinXjList as bulletin>
		<tr>
			<td>${bulletin_index+1}</td>
			<td>
				<span style="cursor:pointer;font-weight:<#if bulletin.isRead>normal<#else>bold</#if>;" onclick="viewBulletin(this,'${bulletin.id!}');">
					<#if  teachAreaList?size gt 1 && loginInfo.unitClass == 2>
						【${bulletin.areaName!}】
	        		</#if>
					<@common.cutOff4List str="${bulletin.title!}" length=50 />
					<#if bulletin.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
					<#if bulletin.orderId gt 0>
						<img src="${request.contextPath}/static/images/icon/top.png">
					</#if>
				</span>
			</td>
			<td>
				${(bulletin.createTime?string('yy-MM-dd'))?if_exists}(${bulletin.weekDay!})
			</td>
			<td>
				${bulletin.createUserName!}
				<#if bulletin.deptName?exists && bulletin.deptName != ''>
					(${bulletin.deptName!})
				</#if>
			</td>
		</tr>
	</#list>
<#else>
	<tr>
        <td colspan="4"><p class="no-data mt-50 mb-50">还没有数据哦！</p></td>
   	</tr>
</#if>
</@common.tableList>

<@common.Toolbar container="#container"/>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
<script type="text/javascript">
$(function(){
	$('.notice-item .dd .more').click(function(e){
		e.preventDefault();
		$('.notice-item .dd').show().next('.dd-all').hide();
		$(this).parent('.dd').hide().next('.dd-all').show();
	});
});
</script>
</@common.moduleDiv>