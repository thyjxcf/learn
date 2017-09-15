<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/jquery.tablednd_0_5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
function saveOrder(){
	jQuery.ajax({
			type: "POST",
			url: "${request.contextPath}/basedata/teacher/teacherAdmin-order.action?unitId=${unitId?default('')}&&deptId=${deptId?default('')}",
			data:jQuery('#listform').serialize(),
			success:function(data){
   			if(data.operateSuccess){
   					showMsgSuccess(data.promptMessage,"",function(){
   					load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?deptidnow=${deptidnow?default()}&queryTchId=${queryTchId?default('')}&queryTchName=${queryTchName?default('')}&queryTchCard=${queryTchCard?default('')}");
   				});
   			}else{
   				showMsgError(data.errorMessage);
   			}
   		}
	});
}

function cancelOrder(){
	load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?deptidnow=${deptidnow?default()}&queryTchId=${queryTchId?default('')}&queryTchName=${queryTchName?default('')}&queryTchCard=${queryTchCard?default('')}");
	
}

</script>
<#assign htmlaction=request.contextPath+"/basedata/teacher/teacherAdmin-list.action?deptidnow=${deptidnow?default()}&queryTchId=${queryTchId?default('')}&queryTchName=${queryTchName?default('')}&queryTchCard=${queryTchCard?default('')}" />
<form id="listform" method="post" name="listform">
<input name="deptidnow" type="hidden" value="${deptidnow?default('')}">
<@common.tableList id="listTable" name="listTable" class="public-table table-list table-dragSort">
<thead>
<tr>
<th width="50" class="t-center">拖动栏</th>
<th>排序号</th>
<th>编号</th>
<th>姓名</th>
<th>性别</th>
</tr>
</thead>
<tbody>
<#if teacherList?exists &&  (teacherList?size>0)>
	<#list teacherList as x>
	<tr>
		<td class="t-center drag-sort-td">拖动排序<input type="hidden" name="teacherids" value="${x.id?default("")}"></td>
	  	<td>${x_index+1}</td>
	  	<td>${x.tchId?default("")}</td>
	  	<td>${x.name?default("")}</td>
	  	<td>${appsetting.getMcode("DM-XB").get(x.sex?default(-1))}</td>
	</tr>
	</#list>
<#else>
	<tr>
		<td colspan="5"> <p class="no-data mt-20">还没有任何记录哦！</p></td>
	</tr>
</#if>
<tbody>
</@common.tableList>
<div class="explain-text">
            <p class="b">号码排序说明：</p>
            <p>1、鼠标移至拖动栏处变为十字形；</p>
            <p>2、按住左键不放，拖动该行至目标处；</p>
            <p>3、放开鼠标左键，该行跟目标行的位置即可交换。</p>
        </div>
 <#if teacherList?exists &&  (teacherList?size>0)>
	<p style="text-align:center;padding:50px 0;">
		<a href="javascript:void(0);" onclick="saveOrder();" class="abtn-blue">保存排序</a> 
		<a href="javascript:void(0);" onclick="cancelOrder();" class="abtn-blue">取消排序</a> 
	</p>
</#if>
<script>
vselect();

$(".table-dragSort").tableDnD({ 
	  onDrop:function(table,row){
			var rows = table.tBodies[0].rows;
			for(i=0;i<rows.length;i++){
				if(i%2==0){
					$(".public-table tbody tr:even").removeClass(); 
				}
				else{
						$(".public-table tbody tr:odd").removeClass(); 
						$(".public-table tbody tr:odd").addClass("odd"); 
				}
			}
		}
	});

</script>
</form>
</@common.moduleDiv>