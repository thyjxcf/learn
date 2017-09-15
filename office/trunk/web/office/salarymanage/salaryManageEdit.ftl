<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="工资编辑">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>工资编辑</span></p>
	<div class="wrap pa-10">
	<form name="salaryForm" id="salaryForm" method="post">
	<div class="typical-table-wrap pt-15" <#if officeSalaryImport.salary51?default("")!=""> style="height:580px;overflow-y:auto;"</#if>>
	<input type="hidden" id="salaryId" name="officeSalary.id" value="${officeSalary.id!}">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		        <th style="width:15%">${officeSalaryImport.realname!}：</th>
		        <td style="width:10%">
		        	${officeSalary.realname!}
		        </td>
		        <th style="width:15%">${officeSalaryImport.cardnumber!}：</th>
		        <td style="width:10%">
		        	${officeSalary.cardnumber!}
		        </td>
		        <#if officeSalaryImport.salary1?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary1!}：</th>
		        <td style="width:5%">
		        	<input type="text" class="input-txt fn-left" id="salary1" style="width:50px;" maxlength="10" name="officeSalary.salary1" value="${officeSalary.salary1!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary2?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary2!}：</th>
		        <td style="width:5%">
		        	<input type="text" class="input-txt fn-left" id="salary2" style="width:50px;" maxlength="10" name="officeSalary.salary2" value="${officeSalary.salary2!}">
		        </td>
		        </#if>
		    </tr>
		    <tr>
		    	<#if officeSalaryImport.salary3?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary3!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary3" style="width:50px;" maxlength="10" name="officeSalary.salary3" value="${officeSalary.salary3!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary4?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary4!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary4" style="width:50px;" maxlength="10" name="officeSalary.salary4" value="${officeSalary.salary4!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary5?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary5!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary5" style="width:50px;" maxlength="10" name="officeSalary.salary5" value="${officeSalary.salary5!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary6?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary6!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary6" style="width:50px;" maxlength="10" name="officeSalary.salary6" value="${officeSalary.salary6!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary7?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary7!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary7" style="width:50px;" maxlength="10" name="officeSalary.salary7" value="${officeSalary.salary7!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary8?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary8!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary8" style="width:50px;" maxlength="10" name="officeSalary.salary8" value="${officeSalary.salary8!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary9?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary9!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary9" style="width:50px;" maxlength="10" name="officeSalary.salary9" value="${officeSalary.salary9!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary10?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary10!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary10" style="width:50px;" maxlength="10" name="officeSalary.salary10" value="${officeSalary.salary10!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary11?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary11!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary11" style="width:50px;" maxlength="10" name="officeSalary.salary11" value="${officeSalary.salary11!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary12?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary12!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary12" style="width:50px;" maxlength="10" name="officeSalary.salary12" value="${officeSalary.salary12!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary13?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary13!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary13" style="width:50px;" maxlength="10" name="officeSalary.salary13" value="${officeSalary.salary13!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary14?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary14!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary14" style="width:50px;" maxlength="10" name="officeSalary.salary14" value="${officeSalary.salary14!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary15?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary15!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary15" style="width:50px;" maxlength="10" name="officeSalary.salary15" value="${officeSalary.salary15!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary16?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary16!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary16" style="width:50px;" maxlength="10" name="officeSalary.salary16" value="${officeSalary.salary16!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary17?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary17!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary17" style="width:50px;" maxlength="10" name="officeSalary.salary17" value="${officeSalary.salary17!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary18?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary18!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary18" style="width:50px;" maxlength="10" name="officeSalary.salary18" value="${officeSalary.salary18!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary19?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary19!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary19" style="width:50px;" maxlength="10" name="officeSalary.salary19" value="${officeSalary.salary19!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary20?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary20!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary20" style="width:50px;" maxlength="10" name="officeSalary.salary20" value="${officeSalary.salary20!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary21?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary21!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary21" style="width:50px;" maxlength="10" name="officeSalary.salary21" value="${officeSalary.salary21!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary22?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary22!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary22" style="width:50px;" maxlength="10" name="officeSalary.salary22" value="${officeSalary.salary22!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary23?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary23!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary23" style="width:50px;" maxlength="10" name="officeSalary.salary23" value="${officeSalary.salary23!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary24?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary24!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary24" style="width:50px;" maxlength="10" name="officeSalary.salary24" value="${officeSalary.salary24!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary25?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary25!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary25" style="width:50px;" maxlength="10" name="officeSalary.salary25" value="${officeSalary.salary25!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary26?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary26!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary26" style="width:50px;" maxlength="10" name="officeSalary.salary26" value="${officeSalary.salary26!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary27?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary27!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary27" style="width:50px;" maxlength="10" name="officeSalary.salary27" value="${officeSalary.salary27!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary28?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary28!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary28" style="width:50px;" maxlength="10" name="officeSalary.salary28" value="${officeSalary.salary28!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary29?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary29!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary29" style="width:50px;" maxlength="10" name="officeSalary.salary29" value="${officeSalary.salary29!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary30?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary30!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary30" style="width:50px;" maxlength="10" name="officeSalary.salary30" value="${officeSalary.salary30!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary31?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary31!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary31" style="width:50px;" maxlength="10" name="officeSalary.salary31" value="${officeSalary.salary31!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary32?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary32!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary32" style="width:50px;" maxlength="10" name="officeSalary.salary32" value="${officeSalary.salary32!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary33?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary33!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary33" style="width:50px;" maxlength="10" name="officeSalary.salary33" value="${officeSalary.salary33!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary34?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary34!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary34" style="width:50px;" maxlength="10" name="officeSalary.salary34" value="${officeSalary.salary34!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary35?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary35!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary35" style="width:50px;" maxlength="10" name="officeSalary.salary35" value="${officeSalary.salary35!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary36?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary36!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary36" style="width:50px;" maxlength="10" name="officeSalary.salary36" value="${officeSalary.salary36!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary37?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary37!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary37" style="width:50px;" maxlength="10" name="officeSalary.salary37" value="${officeSalary.salary37!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary38?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary38!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary38" style="width:50px;" maxlength="10" name="officeSalary.salary38" value="${officeSalary.salary38!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary39?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary39!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary39" style="width:50px;" maxlength="10" name="officeSalary.salary39" value="${officeSalary.salary39!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary40?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary40!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary40" style="width:50px;" maxlength="10" name="officeSalary.salary40" value="${officeSalary.salary40!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary41?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary41!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary41" style="width:50px;" maxlength="10" name="officeSalary.salary41" value="${officeSalary.salary41!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary42?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary42!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary42" style="width:50px;" maxlength="10" name="officeSalary.salary42" value="${officeSalary.salary42!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary43?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary43!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary43" style="width:50px;" maxlength="10" name="officeSalary.salary43" value="${officeSalary.salary43!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary44?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary44!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary44" style="width:50px;" maxlength="10" name="officeSalary.salary44" value="${officeSalary.salary44!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary45?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary45!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary45" style="width:50px;" maxlength="10" name="officeSalary.salary45" value="${officeSalary.salary45!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary46?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary46!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary46" style="width:50px;" maxlength="10" name="officeSalary.salary46" value="${officeSalary.salary46!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary47?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary47!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary47" style="width:50px;" maxlength="10" name="officeSalary.salary47" value="${officeSalary.salary47!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary48?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary48!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary48" style="width:50px;" maxlength="10" name="officeSalary.salary48" value="${officeSalary.salary48!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary49?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary49!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary49" style="width:50px;" maxlength="10" name="officeSalary.salary49" value="${officeSalary.salary49!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary50?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary50!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary50" style="width:50px;" maxlength="10" name="officeSalary.salary50" value="${officeSalary.salary50!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary51?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary51!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary51" style="width:50px;" maxlength="10" name="officeSalary.salary51" value="${officeSalary.salary51!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary52?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary52!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary52" style="width:50px;" maxlength="10" name="officeSalary.salary52" value="${officeSalary.salary52!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary53?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary53!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary53" style="width:50px;" maxlength="10" name="officeSalary.salary53" value="${officeSalary.salary53!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary54?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary54!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary54" style="width:50px;" maxlength="10" name="officeSalary.salary54" value="${officeSalary.salary54!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary55?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary55!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary55" style="width:50px;" maxlength="10" name="officeSalary.salary55" value="${officeSalary.salary55!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary56?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary56!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary56" style="width:50px;" maxlength="10" name="officeSalary.salary56" value="${officeSalary.salary56!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary57?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary57!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary57" style="width:50px;" maxlength="10" name="officeSalary.salary57" value="${officeSalary.salary57!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary58?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary58!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary58" style="width:50px;" maxlength="10" name="officeSalary.salary58" value="${officeSalary.salary58!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary59?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary59!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary59" style="width:50px;" maxlength="10" name="officeSalary.salary59" value="${officeSalary.salary59!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary60?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary60!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary60" style="width:50px;" maxlength="10" name="officeSalary.salary60" value="${officeSalary.salary60!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary61?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary61!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary61" style="width:50px;" maxlength="10" name="officeSalary.salary61" value="${officeSalary.salary61!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary62?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary62!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary62" style="width:50px;" maxlength="10" name="officeSalary.salary62" value="${officeSalary.salary62!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary63?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary63!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary63" style="width:50px;" maxlength="10" name="officeSalary.salary63" value="${officeSalary.salary63!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary64?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary64!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary64" style="width:50px;" maxlength="10" name="officeSalary.salary64" value="${officeSalary.salary64!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary65?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary65!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary65" style="width:50px;" maxlength="10" name="officeSalary.salary65" value="${officeSalary.salary65!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary66?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary66!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary66" style="width:50px;" maxlength="10" name="officeSalary.salary66" value="${officeSalary.salary66!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary67?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary67!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary67" style="width:50px;" maxlength="10" name="officeSalary.salary67" value="${officeSalary.salary67!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary68?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary68!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary68" style="width:50px;" maxlength="10" name="officeSalary.salary68" value="${officeSalary.salary68!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary69?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary69!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary69" style="width:50px;" maxlength="10" name="officeSalary.salary69" value="${officeSalary.salary69!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary70?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary70!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary70" style="width:50px;" maxlength="10" name="officeSalary.salary70" value="${officeSalary.salary70!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary71?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary71!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary71" style="width:50px;" maxlength="10" name="officeSalary.salary71" value="${officeSalary.salary71!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary72?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary72!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary72" style="width:50px;" maxlength="10" name="officeSalary.salary72" value="${officeSalary.salary72!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary73?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary73!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary73" style="width:50px;" maxlength="10" name="officeSalary.salary73" value="${officeSalary.salary73!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary74?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary74!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary74" style="width:50px;" maxlength="10" name="officeSalary.salary74" value="${officeSalary.salary74!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary75?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary75!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary75" style="width:50px;" maxlength="10" name="officeSalary.salary75" value="${officeSalary.salary75!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary76?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary76!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary76" style="width:50px;" maxlength="10" name="officeSalary.salary76" value="${officeSalary.salary76!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary77?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary77!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary77" style="width:50px;" maxlength="10" name="officeSalary.salary77" value="${officeSalary.salary77!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary78?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary78!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary78" style="width:50px;" maxlength="10" name="officeSalary.salary78" value="${officeSalary.salary78!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary79?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary79!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary79" style="width:50px;" maxlength="10" name="officeSalary.salary79" value="${officeSalary.salary79!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary80?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary80!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary80" style="width:50px;" maxlength="10" name="officeSalary.salary80" value="${officeSalary.salary80!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary81?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary81!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary81" style="width:50px;" maxlength="10" name="officeSalary.salary81" value="${officeSalary.salary81!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary82?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary82!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary82" style="width:50px;" maxlength="10" name="officeSalary.salary82" value="${officeSalary.salary82!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary83?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary83!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary83" style="width:50px;" maxlength="10" name="officeSalary.salary83" value="${officeSalary.salary83!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary84?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary84!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary84" style="width:50px;" maxlength="10" name="officeSalary.salary84" value="${officeSalary.salary84!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary85?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary85!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary85" style="width:50px;" maxlength="10" name="officeSalary.salary85" value="${officeSalary.salary85!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary86?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary86!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary86" style="width:50px;" maxlength="10" name="officeSalary.salary86" value="${officeSalary.salary86!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary87?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary87!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary87" style="width:50px;" maxlength="10" name="officeSalary.salary87" value="${officeSalary.salary87!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary88?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary88!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary88" style="width:50px;" maxlength="10" name="officeSalary.salary88" value="${officeSalary.salary88!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary89?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary89!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary89" style="width:50px;" maxlength="10" name="officeSalary.salary89" value="${officeSalary.salary89!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary90?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary90!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary90" style="width:50px;" maxlength="10" name="officeSalary.salary90" value="${officeSalary.salary90!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary91?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary91!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary91" style="width:50px;" maxlength="10" name="officeSalary.salary91" value="${officeSalary.salary91!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary92?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary92!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary92" style="width:50px;" maxlength="10" name="officeSalary.salary92" value="${officeSalary.salary92!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary93?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary93!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary93" style="width:50px;" maxlength="10" name="officeSalary.salary93" value="${officeSalary.salary93!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary94?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary94!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary94" style="width:50px;" maxlength="10" name="officeSalary.salary94" value="${officeSalary.salary94!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary95?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary95!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary95" style="width:50px;" maxlength="10" name="officeSalary.salary95" value="${officeSalary.salary95!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary96?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary96!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary96" style="width:50px;" maxlength="10" name="officeSalary.salary96" value="${officeSalary.salary96!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary97?default("")!="">
		        <th style="width:15%">${officeSalaryImport.salary97!}：</th>
		        <td style="width:10%">
		        	<input type="text" class="input-txt fn-left" id="salary97" style="width:50px;" maxlength="10" name="officeSalary.salary97" value="${officeSalary.salary97!}">
		        </td>
		        </#if>
		        <td colspan="2">
		        </td>
		    </tr> 
	
		    <tr>
		    	<td colspan="8" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('${officeSalaryImport.id!}');" id="btnSave">保存</a>
		    	    <a class="abtn-blue reset ml-5" href="javascript:void(0);">返回</a>
		        </td>
		    </tr>
		</@htmlmacro.tableDetail>
		</table>
		</div>
	</form>
	</div>
</@htmlmacro.moduleDiv >
<script>

var isSubmit =false;
function doSave(thisId){
	var options = {
        url: "${request.contextPath}/office/salarymanage/salarymanage-salaryManageSave.action",
        success: showReply,
        dataType: 'json',
        clearForm: false,
        resetForm: false,
        type: 'post'
    };
    isSubmit = true;
    $('#salaryForm').ajaxSubmit(options);
}

function showReply(data){
	if(!data.operateSuccess){
	   if(data.errorMessage!=null&&data.errorMessage!=""){
		   showMsgError(data.errorMessage);
		   isSubmit = false;
		   return;
	   }
	}else{
		showMsgSuccess(data.promptMessage,"",function(){
			closeDiv("#sealAddLayer");
		  	searchOrder();
		});
		return;
	}

}
</script>