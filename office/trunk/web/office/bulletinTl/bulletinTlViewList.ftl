<#import "/common/htmlcomponent.ftl" as common />
<@common.moduleDiv titleName="">
<script>
function toView(){
	var url="${request.contextPath}/office/bulletinTl/bulletinTl.action";
	load("#container", url);
}

function toManage(){
	var url="${request.contextPath}/office/bulletinTl/bulletinTl-manageList.action";
	load("#container", url);
}

function viewBulletin(obj,id){
	$(obj).css("font-weight","normal");
	window.open("${request.contextPath}/office/bulletinTl/bulletinTl-viewDetail.action?bulletinId="+id,'','fullscreen,scrollbars,resizable=yes,toolbar=no');
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
	var searchName =  document.getElementById("searchName").value;
	searchName = encodeURI(searchName);
	publishName=encodeURI(publishName);
	var url="${request.contextPath}/office/bulletinTl/bulletinTl.action?searchName="+searchName+"&startTime="+startTime+"&endTime="+endTime+"&publishName="+publishName;
	load("#container",url);
}
</script>
<div class="popUp-layer" id="classLayer3" style="display:none;width:500px;"></div>
<div class="popUp-layer" id="bulletinLayer" style="display:none;top:100px;left:300px;width:700px;height:580px;"></div>
<div class="pub-tab">
	<ul class="pub-tab-list">
	<li class="current" onclick="toView();">单位通知</li>
	<#if manageAdmin>
	<li onclick="toManage();">通知管理</li>
	</#if>
	</ul>
</div>
<div class="pub-table-wrap">
	<div class="pub-table-inner">
		<div class="query-builder-nobg mt-15" style="padding:0 0 5px 0;">
    		<div class="query-part">
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
					<input id="searchName" type="text" class="input-txt" style="width:200px;" value="${searchName!}">
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
		<th width="17%">创建人(单位)</th>
	</tr>
	<#if officeBulletinTlList?exists && officeBulletinTlList?size gt 0>
	<#list officeBulletinTlList as bulletinTl>
		<tr>
			<td>${bulletinTl_index+1}</td>
			<td>
				<span style="cursor:pointer;font-weight:<#if bulletinTl.isRead>normal<#else>bold</#if>;" onclick="viewBulletin(this,'${bulletinTl.id!}');">
					<@common.cutOff4List str="${bulletinTl.title!}" length=50 />
					<#if bulletinTl.isNew>
						<img src="${request.contextPath}/static/images/icon/new.png">
					</#if>
					<#if bulletinTl.orderId gt 0>
						<img src="${request.contextPath}/static/images/icon/top.png">
					</#if>
				</span>
			</td>
			<td>
				${(bulletinTl.createTime?string('yy-MM-dd'))?if_exists}(${bulletinTl.weekDay!})
			</td>
			<td>
				${bulletinTl.createUserName!}(${bulletinTl.unitName!})
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
</@common.moduleDiv>