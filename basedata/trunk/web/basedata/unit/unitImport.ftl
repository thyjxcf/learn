<#include "/leadin/dataimport/dataimport.ftl"/> 
<#-- 参数信息 -->
<#macro displayInfoMacro>
<script>
function validateTemplate(){	
	return true;	
}

function validateImport(){	
	if(!checkAllValidate()){
		return false;
	}
	return true;	
}
</script> 
</#macro>

<#-- 说明信息 -->
<#macro importDescriptionMacro>
1、导入文件只支持Excel文件(*.xls，*.xlsx)<br />
</#macro>

<#-- 按钮栏 -->
<#macro buttonBar>
    <a href="javascript:void(0);" class="abtn-blue-big" onclick="goback();">返回</a>
	<script>
	function goback(){
		load("#container", "${request.contextPath}/basedata/unit/unitAdmin.action");
	}
	</script>
</#macro>

<#macro remark>
<tr><td height="10"></td></tr>	
 		<td>
			1、根据单位名称做唯一性校验，只支持新增操作，单位如果已经存在则无法继续操作<br>
			2、只支持学校信息导入，如单位类型为"托管中小学"<!--或"幼儿园学校"<br>
			3、单位类型是托管幼儿园时，学校代码无需输入，否则必填<br>-->
		 	<font color='red'></font><br>
	</td>	 					
</#macro>