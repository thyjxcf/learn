<#import "/common/htmlcomponent.ftl" as htmlmacro>
<@htmlmacro.showPrompt/>
<@htmlmacro.moduleDiv titleName="">

<div class="pub-table-inner" style="margin-top:15px;">
<div class="pub-table-wrap" id="myWorkReportdiv" style="width:1200px;">
<form name="form1" action="" method="post">
<div class="typical-table-wrap pt-15" <#if officeSalaryImport.salary9! !="">style="width:1200px;overflow-x:auto;"</#if>>
	<@htmlmacro.tableList id="tablelist" style="table-layout:fixed">
	<#if registOff>
		<#if officeSalarySort.id?default("")!="">
	<tr>
		
		<#if officeSalaryImport.salary9?default("")=="">
		<#if officeSalaryImport.salary1?default("")=="">
		<th width="100%" colspan="3" style="word-break:break-all; word-wrap:break-word;"></th>
		<#elseif officeSalaryImport.salary2?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*25!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="25%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary3?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*20!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*20!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary4?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*16!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*16!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary5?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*13!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*15!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary6?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*12!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*13!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary7?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*9!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*12!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>			
			</#list>
			</#if>
		<#elseif officeSalaryImport.salary8?default("")=="">
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*9!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*10!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		<#else>
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*9!}%" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${cols*10!}%" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		</#if>
		
		<#else>
			<#if times?exists && (times?size>0)>
			<#list times as time>
			<#assign cols=colsMap.get(time)>
				<#if time?default("")=="1">
					<th width="${cols*133!}px" colspan="${cols!}" style="word-break:break-all; word-wrap:break-word;"></th>
				<#else>
					<th colspan="${cols!}" width="${100*cols!}px;" style="word-break:break-all; word-wrap:break-word;text-align:center;">${time!}</th>
				</#if>
			</#list>
			</#if>
		</#if>
	</tr>
	</#if>
	</#if>
	
	<#if officeSalaryImport.id?default("")!="">
	<tr>
		<#if officeSalaryImport.salary9?default("")=="">
		<#if officeSalaryImport.salary1?default("")=="">
		<th width="33%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="33%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="34%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<#elseif officeSalaryImport.salary2?default("")=="">
		<th width="25%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="25%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="25%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="25%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<#elseif officeSalaryImport.salary3?default("")=="">
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<#elseif officeSalaryImport.salary4?default("")=="">
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="20%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="18%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="16%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="16%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<#elseif officeSalaryImport.salary5?default("")=="">
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		<#elseif officeSalaryImport.salary6?default("")=="">
		<th width="5%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="15%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="14%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="14%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="13%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="13%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<th width="13%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		<th width="13%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary5!}</th>
		<#elseif officeSalaryImport.salary7?default("")=="">
		<th width="4%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary5!}</th>
		<th width="12%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary6!}</th>
		<#elseif officeSalaryImport.salary8?default("")=="">
		<th width="4%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<th width="11%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary5!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary6!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary7!}</th>
		<#else>
		<th width="4%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="9%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<th width="9%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		<th width="9%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		<th width="9%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary5!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary6!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary7!}</th>
		<th width="10%" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary8!}</th>
		</#if>
		
		<#else>
		
		<th width="50px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.serialNumbers!}</th>
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.realname!}</th>
		<th width="120px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.cardnumber!}</th>
		<#if officeSalaryImport.salary1?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary1!}</th>
		</#if>
		<#if officeSalaryImport.salary2?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary2!}</th>
		</#if>
		<#if officeSalaryImport.salary3?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary3!}</th>
		</#if>
		<#if officeSalaryImport.salary4?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary4!}</th>
		</#if>
		<#if officeSalaryImport.salary5?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary5!}</th>
		</#if>
		<#if officeSalaryImport.salary6?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary6!}</th>
		</#if>
		<#if officeSalaryImport.salary7?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary7!}</th>
		</#if>
		<#if officeSalaryImport.salary8?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary8!}</th>
		</#if>
		<#if officeSalaryImport.salary9?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary9!}</th>
		</#if>
		</#if>
		
		<#if officeSalaryImport.salary10?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary10!}</th>
		</#if>
		<#if officeSalaryImport.salary11?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary11!}</th>
		</#if>
		<#if officeSalaryImport.salary12?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary12!}</th>
		</#if>
		<#if officeSalaryImport.salary13?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary13!}</th>
		</#if>
		<#if officeSalaryImport.salary14?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary14!}</th>
		</#if>
		<#if officeSalaryImport.salary15?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary15!}</th>
		</#if>
		<#if officeSalaryImport.salary16?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary16!}</th>
		</#if>
		<#if officeSalaryImport.salary17?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary17!}</th>
		</#if>
		<#if officeSalaryImport.salary18?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary18!}</th>
		</#if>
		<#if officeSalaryImport.salary19?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary19!}</th>
		</#if>
		<#if officeSalaryImport.salary20?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary20!}</th>
		</#if>
		<#if officeSalaryImport.salary21?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary21!}</th>
		</#if>
		<#if officeSalaryImport.salary22?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary22!}</th>
		</#if>
		<#if officeSalaryImport.salary23?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary23!}</th>
		</#if>
		<#if officeSalaryImport.salary24?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary24!}</th>
		</#if>
		<#if officeSalaryImport.salary25?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary25!}</th>
		</#if>
		<#if officeSalaryImport.salary26?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary26!}</th>
		</#if>
		<#if officeSalaryImport.salary27?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary27!}</th>
		</#if>
		<#if officeSalaryImport.salary28?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary28!}</th>
		</#if>
		<#if officeSalaryImport.salary29?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary29!}</th>
		</#if>
		<#if officeSalaryImport.salary30?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary30!}</th>
		</#if>
		<#if officeSalaryImport.salary31?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary31!}</th>
		</#if>
		<#if officeSalaryImport.salary32?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary32!}</th>
		</#if>
		<#if officeSalaryImport.salary33?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary33!}</th>
		</#if>
		<#if officeSalaryImport.salary34?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary34!}</th>
		</#if>
		<#if officeSalaryImport.salary35?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary35!}</th>
		</#if>
		<#if officeSalaryImport.salary36?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary36!}</th>
		</#if>
		<#if officeSalaryImport.salary37?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary37!}</th>
		</#if>
		<#if officeSalaryImport.salary38?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary38!}</th>
		</#if>
		<#if officeSalaryImport.salary39?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary39!}</th>
		</#if>
		<#if officeSalaryImport.salary40?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary40!}</th>
		</#if>
		<#if officeSalaryImport.salary41?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary41!}</th>
		</#if>
		<#if officeSalaryImport.salary42?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary42!}</th>
		</#if>
		<#if officeSalaryImport.salary43?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary43!}</th>
		</#if>
		<#if officeSalaryImport.salary44?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary44!}</th>
		</#if>
		<#if officeSalaryImport.salary45?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary45!}</th>
		</#if>
		<#if officeSalaryImport.salary46?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary46!}</th>
		</#if>
		<#if officeSalaryImport.salary47?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary47!}</th>
		</#if>
		
		<#if officeSalaryImport.salary48?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary48!}</th>
		</#if>
		<#if officeSalaryImport.salary49?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary49!}</th>
		</#if>
		<#if officeSalaryImport.salary50?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary50!}</th>
		</#if>
		<#if officeSalaryImport.salary51?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary51!}</th>
		</#if>
		<#if officeSalaryImport.salary52?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary52!}</th>
		</#if>
		<#if officeSalaryImport.salary53?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary53!}</th>
		</#if>
		<#if officeSalaryImport.salary54?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary54!}</th>
		</#if>
		<#if officeSalaryImport.salary55?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary55!}</th>
		</#if>
		<#if officeSalaryImport.salary56?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary56!}</th>
		</#if>
		<#if officeSalaryImport.salary57?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary57!}</th>
		</#if>
		<#if officeSalaryImport.salary58?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary58!}</th>
		</#if>
		<#if officeSalaryImport.salary59?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary59!}</th>
		</#if>
		<#if officeSalaryImport.salary60?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary60!}</th>
		</#if>
		<#if officeSalaryImport.salary61?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary61!}</th>
		</#if>
		<#if officeSalaryImport.salary62?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary62!}</th>
		</#if>
		<#if officeSalaryImport.salary63?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary63!}</th>
		</#if>
		<#if officeSalaryImport.salary64?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary64!}</th>
		</#if>
		<#if officeSalaryImport.salary65?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary65!}</th>
		</#if>
		<#if officeSalaryImport.salary66?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary66!}</th>
		</#if>
		<#if officeSalaryImport.salary67?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary67!}</th>
		</#if>
		<#if officeSalaryImport.salary68?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary68!}</th>
		</#if>
		<#if officeSalaryImport.salary69?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary69!}</th>
		</#if>
		<#if officeSalaryImport.salary70?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary70!}</th>
		</#if>
		<#if officeSalaryImport.salary71?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary71!}</th>
		</#if>
		<#if officeSalaryImport.salary72?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary72!}</th>
		</#if>
		<#if officeSalaryImport.salary73?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary73!}</th>
		</#if>
		<#if officeSalaryImport.salary74?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary74!}</th>
		</#if>
		<#if officeSalaryImport.salary75?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary75!}</th>
		</#if>
		<#if officeSalaryImport.salary76?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary76!}</th>
		</#if>
		<#if officeSalaryImport.salary77?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary77!}</th>
		</#if>
		<#if officeSalaryImport.salary78?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary78!}</th>
		</#if>
		<#if officeSalaryImport.salary79?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary79!}</th>
		</#if>
		<#if officeSalaryImport.salary80?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary80!}</th>
		</#if>
		<#if officeSalaryImport.salary81?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary81!}</th>
		</#if>
		<#if officeSalaryImport.salary82?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary82!}</th>
		</#if>
		<#if officeSalaryImport.salary83?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary83!}</th>
		</#if>
		<#if officeSalaryImport.salary84?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary84!}</th>
		</#if>
		<#if officeSalaryImport.salary85?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary85!}</th>
		</#if>
		<#if officeSalaryImport.salary86?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary86!}</th>
		</#if>
		<#if officeSalaryImport.salary87?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary87!}</th>
		</#if>
		<#if officeSalaryImport.salary88?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary88!}</th>
		</#if>
		<#if officeSalaryImport.salary89?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary89!}</th>
		</#if>
		<#if officeSalaryImport.salary90?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary90!}</th>
		</#if>
		<#if officeSalaryImport.salary91?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary91!}</th>
		</#if>
		<#if officeSalaryImport.salary92?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary92!}</th>
		</#if>
		<#if officeSalaryImport.salary93?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary93!}</th>
		</#if>
		<#if officeSalaryImport.salary94?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary94!}</th>
		</#if>
		<#if officeSalaryImport.salary95?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary95!}</th>
		</#if>
		<#if officeSalaryImport.salary96?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary96!}</th>
		</#if>
		<#if officeSalaryImport.salary97?default("")!="">
		<th width="100px" style="word-break:break-all; word-wrap:break-word;">${officeSalaryImport.salary97!}</th>
		</#if>

	</tr>
	</#if>
	<#if officeSalaryList?exists && (officeSalaryList?size>0)>
		<#list officeSalaryList as officeSalary>
		    <tr>
                <td style="word-break:break-all; word-wrap:break-word;">${officeSalary.serialNumbers!}</td>
                <td style="word-break:break-all; word-wrap:break-word;">${officeSalary.realname!}</td>
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.cardnumber!}</td>
				<#if officeSalaryImport.salary1?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary1!}</td>
				</#if>
				<#if officeSalaryImport.salary2?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary2!}</td>
				</#if>
				<#if officeSalaryImport.salary3?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary3!}</td>
				</#if>
				<#if officeSalaryImport.salary4?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary4!}</td>
				</#if>
				<#if officeSalaryImport.salary5?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary5!}</td>
				</#if>
				<#if officeSalaryImport.salary6?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary6!}</td>
				</#if>
				<#if officeSalaryImport.salary7?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary7!}</td>
				</#if>
				<#if officeSalaryImport.salary8?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary8!}</td>
				</#if>
				<#if officeSalaryImport.salary9?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary9!}</td>
				</#if>
				<#if officeSalaryImport.salary10?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary10!}</td>
				</#if>
				<#if officeSalaryImport.salary11?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary11!}</td>
				</#if>
				<#if officeSalaryImport.salary12?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary12!}</td>
				</#if>
				<#if officeSalaryImport.salary13?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary13!}</td>
				</#if>
				<#if officeSalaryImport.salary14?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary14!}</td>
				</#if>
				<#if officeSalaryImport.salary15?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary15!}</td>
				</#if>
				<#if officeSalaryImport.salary16?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary16!}</td>
				</#if>
				<#if officeSalaryImport.salary17?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary17!}</td>
				</#if>
				<#if officeSalaryImport.salary18?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary18!}</td>
				</#if>
				<#if officeSalaryImport.salary19?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary19!}</td>
				</#if>
				<#if officeSalaryImport.salary20?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary20!}</td>
				</#if>
				<#if officeSalaryImport.salary21?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary21!}</td>
				</#if>
				<#if officeSalaryImport.salary22?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary22!}</td>
				</#if>
				<#if officeSalaryImport.salary23?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary23!}</td>
				</#if>
				<#if officeSalaryImport.salary24?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary24!}</td>
				</#if>
				<#if officeSalaryImport.salary25?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary25!}</td>
				</#if>
				<#if officeSalaryImport.salary26?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary26!}</td>
				</#if>
				<#if officeSalaryImport.salary27?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary27!}</td>
				</#if>
				<#if officeSalaryImport.salary28?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary28!}</td>
				</#if>
				<#if officeSalaryImport.salary29?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary29!}</td>
				</#if>
				<#if officeSalaryImport.salary30?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary30!}</td>
				</#if>
				<#if officeSalaryImport.salary31?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary31!}</td>
				</#if>
				<#if officeSalaryImport.salary32?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary32!}</td>
				</#if>
				<#if officeSalaryImport.salary33?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary33!}</td>
				</#if>
				<#if officeSalaryImport.salary34?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary34!}</td>
				</#if>
				<#if officeSalaryImport.salary35?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary35!}</td>
				</#if>
				<#if officeSalaryImport.salary36?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary36!}</td>
				</#if>
				<#if officeSalaryImport.salary37?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary37!}</td>
				</#if>
				<#if officeSalaryImport.salary38?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary38!}</td>
				</#if>
				<#if officeSalaryImport.salary39?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary39!}</td>
				</#if>
				<#if officeSalaryImport.salary40?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary40!}</td>
				</#if>
				<#if officeSalaryImport.salary41?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary41!}</td>
				</#if>
				<#if officeSalaryImport.salary42?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary42!}</td>
				</#if>
				<#if officeSalaryImport.salary43?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary43!}</td>
				</#if>
				<#if officeSalaryImport.salary44?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary44!}</td>
				</#if>
				<#if officeSalaryImport.salary45?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary45!}</td>
				</#if>
				<#if officeSalaryImport.salary46?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary46!}</td>
				</#if>
				<#if officeSalaryImport.salary47?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary47!}</td>
				</#if>
				
				<#if officeSalaryImport.salary48?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary48!}</td>
				</#if>
				<#if officeSalaryImport.salary49?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary49!}</td>
				</#if>
				<#if officeSalaryImport.salary50?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary50!}</td>
				</#if>
				<#if officeSalaryImport.salary51?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary51!}</td>
				</#if>
				<#if officeSalaryImport.salary52?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary52!}</td>
				</#if>
				<#if officeSalaryImport.salary53?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary53!}</td>
				</#if>
				<#if officeSalaryImport.salary54?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary54!}</td>
				</#if>
				<#if officeSalaryImport.salary55?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary55!}</td>
				</#if>
				<#if officeSalaryImport.salary56?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary56!}</td>
				</#if>
				<#if officeSalaryImport.salary57?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary57!}</td>
				</#if>
				<#if officeSalaryImport.salary58?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary58!}</td>
				</#if>
				<#if officeSalaryImport.salary59?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary59!}</td>
				</#if>
				<#if officeSalaryImport.salary60?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary60!}</td>
				</#if>
				<#if officeSalaryImport.salary61?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary61!}</td>
				</#if>
				<#if officeSalaryImport.salary62?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary62!}</td>
				</#if>
				<#if officeSalaryImport.salary63?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary63!}</td>
				</#if>
				<#if officeSalaryImport.salary64?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary64!}</td>
				</#if>
				<#if officeSalaryImport.salary65?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary65!}</td>
				</#if>
				<#if officeSalaryImport.salary66?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary66!}</td>
				</#if>
				<#if officeSalaryImport.salary67?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary67!}</td>
				</#if>
				<#if officeSalaryImport.salary68?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary68!}</td>
				</#if>
				<#if officeSalaryImport.salary69?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary69!}</td>
				</#if>
				<#if officeSalaryImport.salary70?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary70!}</td>
				</#if>
				<#if officeSalaryImport.salary71?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary71!}</td>
				</#if>
				<#if officeSalaryImport.salary72?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary72!}</td>
				</#if>
				<#if officeSalaryImport.salary73?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary73!}</td>
				</#if>
				<#if officeSalaryImport.salary74?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary74!}</td>
				</#if>
				<#if officeSalaryImport.salary75?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary75!}</td>
				</#if>
				<#if officeSalaryImport.salary76?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary76!}</td>
				</#if>
				<#if officeSalaryImport.salary77?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary77!}</td>
				</#if>
				<#if officeSalaryImport.salary78?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary78!}</td>
				</#if>
				<#if officeSalaryImport.salary79?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary79!}</td>
				</#if>
				<#if officeSalaryImport.salary80?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary80!}</td>
				</#if>
				<#if officeSalaryImport.salary81?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary81!}</td>
				</#if>
				<#if officeSalaryImport.salary82?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary82!}</td>
				</#if>
				<#if officeSalaryImport.salary83?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary83!}</td>
				</#if>
				<#if officeSalaryImport.salary84?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary84!}</td>
				</#if>
				<#if officeSalaryImport.salary85?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary85!}</td>
				</#if>
				<#if officeSalaryImport.salary86?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary86!}</td>
				</#if>
				<#if officeSalaryImport.salary87?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary87!}</td>
				</#if>
				<#if officeSalaryImport.salary88?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary88!}</td>
				</#if>
				<#if officeSalaryImport.salary89?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary89!}</td>
				</#if>
				<#if officeSalaryImport.salary90?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary90!}</td>
				</#if>
				<#if officeSalaryImport.salary91?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary91!}</td>
				</#if>
				<#if officeSalaryImport.salary92?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary92!}</td>
				</#if>
				<#if officeSalaryImport.salary93?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary93!}</td>
				</#if>
				<#if officeSalaryImport.salary94?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary94!}</td>
				</#if>
				<#if officeSalaryImport.salary95?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary95!}</td>
				</#if>
				<#if officeSalaryImport.salary96?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary96!}</td>
				</#if>
				<#if officeSalaryImport.salary97?default("")!="">
				<td style="word-break:break-all; word-wrap:break-word;">${officeSalary.salary97!}</td>
				</#if>
			</tr>
		</#list>
		<#else>
	   		<tr><td colspan="50"> <p class="no-data mt-50 mb-50">还没有任何记录哦！</p></td></tr>
		</#if>
	
	</@htmlmacro.tableList>	
</div>
	<@htmlmacro.Toolbar container="#myWorkReportdiv"></@htmlmacro.Toolbar>
</form>
</div>
</div>
<script type="text/javascript" src="${request.contextPath}/static/js/myscript.js"></script>
</@htmlmacro.moduleDiv>