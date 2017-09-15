<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="">
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
    <script type="text/javascript"> 
    vselect();
      function doSave() {
      <#if !eduUnit>
        var ids = jQuery("input[type='hidden'][name='id']");
        if (ids.length == 0 )  {
          showMsgWarn("没有当前学期的日期信息,请先维护当前学期数据");
          return;
        }
       </#if> 
		var dateInfoList = new Array();
		var index = 0;
		jQuery("[name='dateInfo']").each(function(){
				var id = jQuery(this).find("[name='id']").val();	
				var flag = jQuery(this).find("[name='isfeast']");
				if(flag.attr('checked')=="checked"){
					var isfeast = "Y";
				}else{
					var isfeast = "N";
				}
			    var feastname = jQuery(this).find("[name='feastname']").val();
			    var week = jQuery(this).find("[name='week']").val();  
			    dateInfoList.push(new dateInfo(id,isfeast,feastname,week));
			    index++;
		});
		var listString = JSON.stringify(dateInfoList);
		listString.replace(new RegExp("null","gm"),"");
		jQuery.ajax({
			url:"${request.contextPath}/basedata/holiday/baseDateAdmin-remote.action",
			type:"POST",
			dataType:"JSON",
			data:{"listString":listString},
			async:false,
			success:function(data){
				if(data.operateSuccess){
					showMsgSuccess("保存成功");
				}else{
					showMsgError(data.errorSuccess);
				}
			}
		});
	}

	function dateInfo(id,isfeast,feastname,week){
		this.id = id;
		this.isfeast = isfeast;
		this.feastname = feastname;
		this.week = week;
	}

    </script> 
    <form name="form1" id="dataform" action="" method="post">
   	 <@htmlmacro.tableList id="dataTable"> 
	      <tr id="titleRow"> 
	        <th>日期</th>
	        <#if !eduUnit>
	        <th>周次</th>
	        </#if>
	        <th>星期</th>
	        <th>是否教休日</th>
	        <th class="t-center">节假日名称</th>
	      </tr> 
	      <#if dateInfoList?exists && (dateInfoList?size>0)>
          <#list dateInfoList as dateInfo>
          <tr name="dateInfo">
            <td class="t-left">
            <input type="hidden" name="id" id="id" value="${dateInfo.id?default('')}" >
            ${dateInfo.date?string("yyyy-MM-dd")}
            </td>
            <#if !eduUnit>
            <td class="t-left">${dateInfo.week?default('')}</td>
            </#if>
            <td class="t-left">${dateInfo.weekday?default('')}
            <input type="hidden" name="week" id="week" value="${dateInfo.week?default('')}">
            </td>
            <td class="t-center">
              <#if dateInfo.isfeast == "Y" >
               <p><span class="ui-checkbox ui-checkbox-current"><input type="checkbox" class="chk" name="isfeast" checked/></span></p>
              <#else>
                <p><span class="ui-checkbox"><input name="isfeast" class="chk" type="checkbox"/></span></p>
              </#if>
            </td>
            <td class="t-center">
            <input type="text" name="feastname" class="input-txt" style="width:150px;" 
                size="30" value="${dateInfo.feastname?default('')}" maxlength="50">
            </td>
          </tr>
          </#list>
          <#else>
		  	<tr>
	           <td colspan=8> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td>
	    	</tr>
	   </#if>
      </@htmlmacro.tableList>
    </form> 
      <div class="t-center mt-10">
	   	 <a class="abtn-blue-big" onclick="javascript:doSave();" href="javascript:void(0);">保存</a>
		</div>
</@htmlmacro.moduleDiv>
