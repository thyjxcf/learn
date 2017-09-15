
<html>
<head>
<title>${webAppTitle}--学校综合信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${request.contextPath}/static/css/layout.css"	type="text/css">
<script language="javascript">
	
	//查询
	function search(){
			
		var searchType = formSearch.searchType.value;
		if(searchType == "schoolInfo")
			formSearch.action="schoolGeneralInfoQuery-queryBasicSchool.action";
		else if(searchType == "semesterInfo")
			formSearch.action="schoolGeneralInfoQuery-queryBasicSemester.action";
		else
			formSearch.action="schoolGeneralInfoQuery-queryBasicClass.action";
		formSearch.submit();
	}
	
	function onKeyDown(){
		if(event.keyCode!=13 ){
			return false;
		}
		else{
			search();
		}
    }
	
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="YecSpec" height="100%">
	<form name="formSearch" id="formSearch"   method="post">
	
	<tr>
		<td height="45" class="padding_left">
			<table width="99%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td>				
						<table width="100%" border="0">
							<tr>
								<td width="20">
								</td>
								<td width="100" >
									请选择查询条件:
								</td>
								<td width="120">
									<select name="searchType" size="1" id="searchName">
									  <option value="schoolInfo" <#if searchType="schoolInfo">selected</#if>>学校基本信息</option>
									  <option value="semesterInfo" <#if searchType="semesterInfo">selected</#if>>学校学年学期信息</option>
									  <option value="classInfo" <#if searchType="classInfo">selected</#if>>学校班级信息</option>
							      </select>
								</td>
								<td width="100" align="right">
									请输入学校名称:
								</td>
								<td width="100">
									<input name="name" type="text" class="input" size="20" onKeyDown="onKeyDown();" />
								</td>
								<td width="80">
									<div class="comm_button21"
										onMouseover="this.className = 'comm_button21';"
										onMousedown="this.className = 'comm_button22';"
										onMouseout="this.className = 'comm_button21';"
										onClick="search();">
										查询
									</div>
								</td>
								<td>
								</td>								
							</tr>
						</table>
					
				  </td>
					
				</tr>
			</table>
		</td>
	</tr>
 <tr>
  
  </tr>
  </form>
	  <tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	  <tr><td bgcolor="#ffffff" height="1"></td></tr>	  
	<tr><td bgcolor="#C2CDF7" height="1"></td></tr>
	
</table>
</body>
</html>
