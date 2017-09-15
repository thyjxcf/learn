<#include "/leadin/dataimport/dataimport.ftl"/> 
<#-- 参数信息 -->
<#macro displayInfoMacro>
<script>
function validateTemplate(){	
	return true;	
}

function validateImport(){	
	return true;	
}
</script> 
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls，*.xlsx)<br />
2、导入文件内容全部为实际显示内容，如性别为：男或者女<br />
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
    <a class="abtn-blue-big" onclick="goback();">返回</a>
	<script>
	function goback(){
		load("#container","${request.contextPath}/basedata/teacher/teacherAdmin.action");
	}
	</script>
</#macro>

<#macro remark>
  <table>
  	<tr><td><font color='blue'>1、教职工编号将自动生成；<br>
		 2、本次导入的教职工都将视为新增操作，不覆盖原有数据；<br>
     </td></tr>
  </table>
</#macro>

