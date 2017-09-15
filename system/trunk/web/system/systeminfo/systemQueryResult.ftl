<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#include "/common/handlefielderror.ftl">
<@htmlmacro.moduleDiv titleName="平台基础信息设置">
    <input name="resultIndex" id="resultIndex" type="hidden" value="${resultIndex?default('0')}">
    <table border="0" cellspacing="0" cellpadding="0" class="table-form">
       <#if resultPacks?exists>
		    <tr>
		       <td>
		             <table border="0" cellspacing="0" cellpadding="0" class="table-form">
			                <#assign resultLength=resultPacks?size>
					  	  	<tr>
					  	  	   <td>
								   <div class="pub-tab">
										<div class="pub-tab-list"> 	  	  	
								  	  	  <#list 1..resultLength as i>
								  	  	    <li <#if i-1==resultIndex>class="current li-${i}"<#elseif resultIndex==0 && i==1>class="current li-${i}" <#else>class="li-${i}" </#if> onclick="javascript:showResultSet('${i}');">执行结果${i}</li>
								  	  	  </#list>
										</div>
									</div>	  	  	  
					  	  	   </td>
					  	  	</tr>
					  	  	
					  	  	<tr>
  	  	                       <td width="100%" height="100%" valign="top">
  	  	                             <table border="0" cellspacing="1" cellpadding="1" class="table-form" >
  	  	                                    <#assign resultPack=currentResultPack?exists>
										    <#if currentResultPack.resultCode?exists>
										  	  <tr><td>更新了${currentResultPack.resultCode}条记录</td></tr>
										    <#else>
											  <tr>			  	
											  	<#assign columnNames=currentResultPack.columnNames?if_exists>
											  	<#list columnNames?if_exists as columnName>
											  	  <th><strong>${columnName}</strong></th>
											  	</#list>
											  </tr>  
											  <#assign records=currentResultPack.records?if_exists>
											  <#list records?if_exists as record>
											  	<tr>
											  	  <#list record?if_exists as record_info>
											  	  	<td nowrap title="${columnNames[record_info_index]}">${record_info?default('')}</td>
											  	  </#list>
											  	</tr>
											  </#list>
										    </#if>
  	  	                             </table>
  	  	                       </td>
  	  	                    </tr>   
		             </table>
		       </td>
		    </tr>
	   </#if> 
    </table>
<script type="text/javascript">
   function showResultSet(val){
     var contentField = document.getElementById('taskContent');
	　if(!checkElement(contentField,'SQL语句')){
		return ;
	  }
	  document.getElementById('resultIndex').value=val-1;
	  var url = "${request.contextPath}/system/admin/platformInfoAdmin-doExceptioQuery.action"
	  var data = $("#form1").serializeArray();
	  load("#divContext",url,"","","",data);
	}
</script>    
</@htmlmacro.moduleDiv> 