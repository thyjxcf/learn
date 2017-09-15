<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script language="javascript">
  function gBack(){
  	load("#teacherList","${backUrl}");
  }
  
  function doSave(){
  var flag = false;
	var adjustIds = "";
	var length = mainform.elements.length;
	for(i=0;i<length;i++){
		var elem = mainform.elements[i];
		if(elem.name == "disstuid" ){	//必选项也都返回，再设置 && !(elem.disabled)
			flag = true;
			if(adjustIds == ""){
				adjustIds = elem.value;
			}else{
				adjustIds = adjustIds + "$" + elem.value;
			}
		}
	}
	mainform.adjustIds.value = adjustIds;
	
	jQuery.ajax({
	   		url:"${request.contextPath}/basedata/common/colsdisplay/colsDisplaySave.action",
	   		type:"POST",
	   		data:jQuery('#mainform').serialize(),
	   		dataType:"JSON",
	   		async:false,
	   		success:function(data){
	   			if(data.operateSuccess){
	   					showMsgSuccess(data.promptMessage,"",function(){
	   					load("#teacherList","${request.contextPath}/basedata/teacher/teacherAdmin-list.action?modID=${modID?default('')}&deptidnow=00000000000000000000000000000000");
	   				});
	   			}else{
	   				showMsgError(data.errorMessage);
	   			}
	   		}
 		});
}
//调入选的行
function doAdjust(path){
	//是否有选择
	var flag = false;
	var length = mainform.elements.length;
	for(i=0;i<length;i++){
		if(mainform.elements[i].name == 'srcstuid' && mainform.elements[i].checked){
			flag = true;
			break;
		}
	}
	if(!flag){
		alert("请先在左边列表中选择要启用的具体字段！");
		return;
	}
	
	moveDate("srctable","distable","disstuid",path);
}

//取消调入的行
function doAdjustCancel(path){
	//是否有选择
	var flag = false;
	var length = mainform.elements.length;
	for(i=0;i<length;i++){
		if(mainform.elements[i].name == 'disstuid' && mainform.elements[i].checked && !(mainform.elements[i].disabled)){
			flag = true;
			break;
		}
	}
	if(!flag){
		alert("请先在右边列表中选择要取消启用(不再启用)的具体字段！");
		return;
	}
	
	moveDate("distable","srctable","srcstuid",path);
}

//移动数据
function moveDate(srctableid,distableid,checkid,path){
	var checkbox;
	var stuid;
	var stuname;
	//var stusex;
	//var stucode;
	var srctable = document.getElementById(srctableid);
	var distable = document.getElementById(distableid);
	var srcrows = srctable.rows.length;
	var disrows;
	for (var i=1; i<=srcrows -1; i++) {
		checkbox = srctable.rows.item(i).cells.item(0).children.item(0).children.item(0).children.item(0);
		var isdisabledsize =srctable.rows.item(i).cells.item(0).children.item(0).children.item(0).className.indexOf('ui-checkbox-disabled');
		if(checkbox.type == "checkbox" && checkbox.checked == true && !(isdisabledsize>=0)){
			//取得数据
			stuid = checkbox.value;
			stuname = srctable.rows.item(i).cells.item(1).innerHTML;

			//在目标table中插入数据
			disrows = distable.rows.length;
			distable.insertRow(disrows);
			distable.rows.item(disrows).insertCell(0);
			distable.rows.item(disrows).cells.item(0).className='t-center';
			distable.rows.item(disrows).cells.item(0).innerHTML = '<p><span class="ui-checkbox ui-checkbox-current"> <input name="' + checkid + '" type="checkbox" value="'
				+ stuid +'" checked="checked" class="chk" /> </span></p>';
			
			distable.rows.item(disrows).insertCell(1);
			distable.rows.item(disrows).cells.item(1).innerText = stuname;
	
			srctable.deleteRow(i);
			i --;
			srcrows --;
		}
	}
	vselect();
	return;
}
</script>

<#--srcstuid 	adjust-Stuid	-->
<form action="" name="mainform" id="mainform" method="post">
<input type="hidden" name="backUrl" value="${backUrl}">
<input type="hidden" name="adjustIds" value=""/>
<input type="hidden" name="type" value="${type?default("student")}"/>
<input type="hidden" name="classid" value="${classid?default("")}"/>
<input type="hidden" name="deptidnow" value="${deptidnow?default("")}"/>
<input type="hidden" name="schGUID" value="${schGUID?default("")}"/>

<div class="wrap">
    	<div class="pub-table-wrap">
    	<div class="pub-table-inner">
            <div id="userDiv" class="fn-left" style="width:50%;">
            <p class="permission-tt">详细信息显示--可启用字段：<span class="ui-checkboxl left-all" data-all="no"><input type="checkbox" name="selectAllSrc" class="chk" name="srcstuid"  id="srcstuid" value="checkbox" /></span>全选
             <a href="javascript:void(0);" class="abtn-blue" onclick="doAdjust('${request.contextPath}');">启用</a>		  		
            </p>
		 			<div style="overflow-y:auto;height:320px;">
		 			<@common.tableList id="srctable" name="srctable">
					<tr>
						<th width="40">选择</th>
						<th>字段名</th>
					</tr>
			          <#if hideList?exists>
			          <#list hideList as item>
					    <tr  id="srcrow">
						  	<td class="t-center">
							  	<p><span class="ui-checkbox">
							  		<input type="checkbox" class="chk" name="srcstuid"  id="srcstuid" value="${item.id?if_exists}" />
							  	</span></p>
						  	</td>
							<td class="padding_left">${item.colsName?if_exists}</td>
			          	</tr>
			          </#list>
			          </#if>
					</@common.tableList>
				</div>		
			</div>
            <div class="fn-right treDiv" style="width:50%;">
            <p class="permission-tt">详细信息显示--已启用字段：<span class="ui-checkboxr right-all" data-all="no"><input type="checkbox" name="selectAllDis" 
            class="chk" name="srcstuid"  id="srcstuid" value="checkbox" /></span>全选
            <a href="javascript:void(0);" class="abtn-blue" onclick="doAdjustCancel('${request.contextPath}');">取消</a></p>
	            <div style="overflow-y:auto;height:320px;">
	          		  <@common.tableList id="distable" name="distable">
						<tr>
							<th width="40">选择</th>
							<th>字段名</th>
						</tr>
			          <#if DisplayList?exists>
			          <#list DisplayList as item>                          
					    <tr id="disrow">
						    <td class="t-center">
							    <p><span class="ui-checkbox <#if item.colsConstraint == 1  || item.colsConstraint == 2 >ui-checkbox-disabled</#if>">
							    <input type="checkbox" class="chk" name="disstuid" id="disstuid" value="${item.id?if_exists}"/>
							    </span></p>
							  </td>
							<td>${item.colsName?if_exists}</td>
			            </tr>
			          </#list>
			          </#if>
					</@common.tableList>
				</div>	
          	</div>
           	<div class="fn-clear"></div>
        </div>
    </div>
   </div>
<p style="text-align:center;padding:50px 0;">
        <a href="javascript:void(0);" class="abtn-blue submit" onclick="doSave();">保存</a>
        <a href="javascript:void(0);" class="abtn-blue ml-5" onclick="gBack();">返回</a>
</p>
</form>
<script>
vselect();

$('.left-all').click(function(){
		var chkAll=$(this).attr('data-all');
		if(chkAll=="no"){
			$(this).attr('data-all','yes');
			$('.fn-left').find('.ui-checkbox').addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		}else{
			$(this).attr('data-all','no');
			$('.fn-left').find('.ui-checkbox').removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		};
	});
	
$('.right-all').click(function(){
		var chkAll=$(this).attr('data-all');
		if(chkAll=="no"){
			$(this).attr('data-all','yes');
			$('.fn-right').find('.ui-checkbox').addClass('ui-checkbox-current').find('.chk').attr('checked','checked');
		}else{
			$(this).attr('data-all','no');
			$('.fn-right').find('.ui-checkbox').removeClass('ui-checkbox-current').find('.chk').removeAttr('checked');
		};
	});
	
</script>
</@common.moduleDiv>
