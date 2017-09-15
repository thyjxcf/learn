<#import "/common/htmlcomponent.ftl" as common>
<@common.moduleDiv titleName="">
<script language="JavaScript" src="${request.contextPath}/static/js/buffalo.js"></script>
<script type="text/javascript">
  var isSubmitting =false;
  function doSave() {
  	clearMessages();
    if (isSubmitting) {
      return;
    }
    var beginTime1 = "";
    var beginTime2 = "";
    var endTime1 = "";
    var endTime2 = "";
    for(var i=1; i<=${totalSection};i++){
    	beginTime1 = document.getElementById("beginTime"+i);
    	endTime1 = document.getElementById("endTime"+i);
    	beginTime2 = document.getElementById("beginTime"+(i+1));
    	endTime2 = document.getElementById("endTime"+(i+1));
    	if(isBlank(beginTime1.value)){
    		addFieldError(beginTime1,"第"+i+"节开始时间不能为空");
			return;
    	}
    	if(isBlank(endTime1.value)){
    		addFieldError(endTime1,"第"+i+"节结束时间不能为空");
			return;
    	}
    	if(compareTimes(beginTime1,endTime1)>-1){
			addFieldError(beginTime1,"第"+i+"节开始时间要小于结束时间");
			return;
		}
    	if(i<${totalSection}){
			if(isBlank(beginTime2.value)){
	    		addFieldError(beginTime2,"第"+(i+1)+"节开始时间不能为空");
				return;
	    	}
	    	if(isBlank(endTime2.value)){
	    		addFieldError(endTime2,"第"+(i+1)+"节结束时间不能为空");
				return;
	    	}
			if(compareTimes(endTime1,beginTime2)>0){
				addFieldError(endTime1,"第"+i+"节结束时间不能大于第"+(i+1)+"节开始时间");
				return;
			}
    	}
    }
    isSubmitting = true;
    jQuery.ajax({
    	url:"${request.contextPath}/basedata/section/sectionAdmin-saveSection.action",
    	dataType:"JSON",
    	type:"POST",
    	data:jQuery("#dataform").serialize(),
    	async:false,
    	success:function(data){
    		if(data.operateSuccess){
    			var i = 0;
      			showMsgSuccess(data.promptMessage,"",function(){
					if(i==0){      			
   						load("#container","${request.contextPath}/basedata/section/sectionAdmin.action");
   					}
   					i++;
   				});
	      		}else{
	      			showMsgError(data.errorMessage);
	      		}
    	}
    });
  }
  function compareTimes(elem1, elem2) {
    if (elem1.value != "" && elem2.value != "") {
      var date1;
      var date2;
      try {
        date1 = elem1.value.split(':');
        date2 = elem2.value.split(':');
      } catch (e) {
        date1 = elem1.split(':');
        date2 = elem2.split(':');
      }
      if (eval(date1[0]) > eval(date2[0])) {
        return 1;
      } else if (eval(date1[0]) == eval(date2[0])) {
        if (eval(date1[1]) > eval(date2[1])) {
          return 1;
        } else if (eval(date1[1]) == eval(date2[1])) {
            return 0;
        } else {
          return -1;
        }
      } else {
        return -1;
      }
    }
  }
  
</script> 
    <form name="form1" id="dataform" action="" method="post">
   	 <@common.tableList id="dataTable">
	      <tr id="titleRow"> 
	        <th>节次</th>
	        <th>开始时间</th>
	        <th>结束时间</th>
	      </tr> 
	      <#if totalSection gt 0>
          <#list 1..totalSection as x>
          <tr>
            <td>第${x}节</td>
        	<#if stusysSectionTimeSetList?exists && stusysSectionTimeSetList[x-1]?exists>
	        	<td><@common.datepicker class="input-txt input-readonly" style="width:150px" id="beginTime${x}" name="beginTime" dateFmt="HH:mm" value="${stusysSectionTimeSetList[x-1].beginTime!}"/></td>
	            <td><@common.datepicker class="input-txt input-readonly" style="width:150px" id="endTime${x}" name="endTime" dateFmt="HH:mm" value="${stusysSectionTimeSetList[x-1].endTime!}" /></td>
          	<#else>
	          	<td><@common.datepicker class="input-txt input-readonly" style="width:150px" id="beginTime${x}" name="beginTime" dateFmt="HH:mm" value=""/></td>
	            <td><@common.datepicker class="input-txt input-readonly" style="width:150px" id="endTime${x}" name="endTime" dateFmt="HH:mm" value="" /></td>
          	</#if>
          </tr>
          </#list>
          <#else>
          <tr><td colspan="7"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
          </#if>
      </@common.tableList>
       <#if totalSection gt 0>
      <div class="t-center mt-10">
	   	 <a class="abtn-blue-big" onclick="javascript:doSave();" href="javascript:void(0);">保存</a>
	</div>
	</#if>
    </form> 
<script>
jQuery(document).ready(function(){
	$t_c_width=jQuery(".table-content").width();
	$t_c_width=$t_c_width-16;
	jQuery(".table-content").height(jQuery(".mainFrame", window.parent.document).height() 
		 - jQuery('.table1-bt').height()-5);
	jQuery(".table-header").width($t_c_width);
})

</script>  
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script> 
</@common.moduleDiv>