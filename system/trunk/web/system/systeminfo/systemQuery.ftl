<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
  <form id="form1" action="" name="form1">
    <p class="table-dt">请输入SQL查询语句：</p>
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
        <tr>
		  <td> 
		    <textarea name="taskContent" id="taskContent" class="text-area my-5 fn-left" style="width:1150px;height:120px;">${taskContent?default('')}</textarea>
		  </td>
	    </tr>
	    <tr>
		  <td> 
			  <a href="javascript:void(0)" class="abtn-blue ml-30 fn-left" onclick="javascript:doQuery();">查找</a>
		  	  <span id="msgSpan" style="display:none" class="actiontip_common"></span>
		  </td>	  	  
	    <tr>
    </table>
    
    <div id="divContext" style="overflow-y:auto;height:400px">
    
    </div>
  </form>  

<script type="text/javascript">
    function doQuery(){
	  var contentField = document.getElementById('taskContent');
	　if(!checkElement(contentField,'SQL语句')){
		return ;
	  }
	  document.getElementById("msgSpan").style.display="";  
	  //document.queryFrame.doQuery();
	  var url = "${request.contextPath}/system/admin/platformInfoAdmin-doExceptioQuery.action"
	  var data = $("#form1").serializeArray();
	  load("#divContext",url,"","","",data);
	}
</script>    
</@htmlmacro.moduleDiv>    