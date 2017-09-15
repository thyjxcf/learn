<#import "/common/commonmacro.ftl" as eisCommonmacro>

<#macro selectMoreStudentTreeByPopedom idObjectId nameObjectId otherParam="" width=650 height=420 callback="">
	<@selectStudentTreeByPopedom useCheckbox=true idObjectId=idObjectId nameObjectId=nameObjectId otherParam=otherParam width=width height=height callback=callback>
		<#nested />
	</@selectStudentTreeByPopedom>
</#macro>

<#macro selectOneStudentTreeByPopedom idObjectId nameObjectId otherParam="" width=650 height=420 callback="">
	<@selectStudentTreeByPopedom useCheckbox=false idObjectId=idObjectId nameObjectId=nameObjectId otherParam=otherParam width=width height=height callback=callback>
		<#nested />
	</@selectStudentTreeByPopedom>
</#macro>

<#macro selectStudentTreeByPopedom useCheckbox idObjectId nameObjectId url="${request.contextPath}/common/eisu/showStudentTreeDivByPopedom.action" otherParam="" width=650 height=420 callback="">
	<@eisCommonmacro.selectObjectTree useCheckbox=useCheckbox idObjectId=idObjectId nameObjectId=nameObjectId url=url otherParam=otherParam width=width height=height callback=callback>
		<#nested />
	</@eisCommonmacro.selectObjectTree>
</#macro>
