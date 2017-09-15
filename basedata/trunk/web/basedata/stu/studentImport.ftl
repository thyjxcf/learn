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

<#if systemDeploySchool?default('') =='sxrrt'>
$('[name="covered"]:input').parent().hide();
</#if>
</script> 
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls或者*.xlsx)<br />
2、导入文件内容全部为实际显示内容，如性别为：男或者女<br />
3、日期类型支持三种格式(年月日、年-月-日、年/月/日)，其中【年月日】这种格式必须满足8位数，即月份和日期不足两位的，前面要补0，如20060102。<br />
<font color='red'><Strong>注意：</strong>日期数据时，如果不是采用【年月日】这种格式时，需要把Excel的单元格格式设置为常规或者文本，否则导入时，格式验证无法通过！</font><br>                          
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>

</#macro>

<#macro remark>
  <table>
  	<td>
  	<#if systemDeploySchool?default('') =='sxrrt'>
  	<font color='red'>注意：此导入只新增学生，不对学生信息进行更新。判断学生是否存在的依据是姓名、账号。<br>
  	<#else>
  	1、不选择“覆盖原有数据”时，只增加系统里没有的学生信息；系统中已有的学生信息，内容不变；<br>
	 	2、选择“覆盖原有数据”时，增加系统里没有的学生信息，同时更新系统里已有的学生信息<br>
		  &nbsp;&nbsp;（只更新导入文件中有的列内容【姓名、学号除外】，导入文件中没有的列，则保持系统中的内容不变）<br>
       	<font color='red'> 注意：选择“覆盖原有数据”时，更新学生信息的依据是姓名、学号。<br>
       	</font>
  	</#if>
     </td>
  </table>
</#macro>
