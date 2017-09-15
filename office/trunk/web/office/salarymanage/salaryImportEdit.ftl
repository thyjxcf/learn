<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="工资列项编辑">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>工资列项编辑</span></p>
	<div class="wrap pa-25">
	<form name="salaryForm" id="salaryForm" method="post">
	<div class="typical-table-wrap pt-15" <#if officeSalaryImport.salary51?default("")!=""> style="height:580px;overflow-y:auto;"</#if>>
	<input type="hidden" id="salaryId" name="officeSalaryImport.id" value="${officeSalaryImport.id!}">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<@htmlmacro.tableDetail divClass="table-form">
		    <tr>
		        <th style="width:10%">列项2：</th>
		        <td style="width:15%">
		        	${officeSalaryImport.realname!}
		        </td>
		        <th style="width:10%">列项3：</th>
		        <td style="width:15%">
		        	${officeSalaryImport.cardnumber!}
		        </td>
		        <#if officeSalaryImport.salary1?default("")!="">
		        <th style="width:10%">列项4：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary1" style="width:50px;" maxlength="25" name="officeSalaryImport.salary1" value="${officeSalaryImport.salary1!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary2?default("")!="">
		        <th style="width:10%">列项5：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary2" style="width:50px;" maxlength="25" name="officeSalaryImport.salary2" value="${officeSalaryImport.salary2!}">
		        </td>
		        </#if>
		    </tr>
		    <tr>
		    	<#if officeSalaryImport.salary3?default("")!="">
		        <th style="width:10%">列项6：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary3" style="width:50px;" maxlength="25" name="officeSalaryImport.salary3" value="${officeSalaryImport.salary3!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary4?default("")!="">
		        <th style="width:10%">列项7：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary4" style="width:50px;" maxlength="25" name="officeSalaryImport.salary4" value="${officeSalaryImport.salary4!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary5?default("")!="">
		        <th style="width:10%">列项8：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary5" style="width:50px;" maxlength="25" name="officeSalaryImport.salary5" value="${officeSalaryImport.salary5!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary6?default("")!="">
		        <th style="width:10%">列项9：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary6" style="width:50px;" maxlength="25" name="officeSalaryImport.salary6" value="${officeSalaryImport.salary6!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary7?default("")!="">
		        <th style="width:10%">列项10：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary7" style="width:50px;" maxlength="25" name="officeSalaryImport.salary7" value="${officeSalaryImport.salary7!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary8?default("")!="">
		        <th style="width:10%">列项11：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary8" style="width:50px;" maxlength="25" name="officeSalaryImport.salary8" value="${officeSalaryImport.salary8!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary9?default("")!="">
		        <th style="width:10%">列项12：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary9" style="width:50px;" maxlength="25" name="officeSalaryImport.salary9" value="${officeSalaryImport.salary9!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary10?default("")!="">
		        <th style="width:10%">列项13：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary25" style="width:50px;" maxlength="25" name="officeSalaryImport.salary10" value="${officeSalaryImport.salary10!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary11?default("")!="">
		        <th style="width:10%">列项14：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary11" style="width:50px;" maxlength="25" name="officeSalaryImport.salary11" value="${officeSalaryImport.salary11!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary12?default("")!="">
		        <th style="width:10%">列项15：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary12" style="width:50px;" maxlength="25" name="officeSalaryImport.salary12" value="${officeSalaryImport.salary12!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary13?default("")!="">
		        <th style="width:10%">列项16：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary13" style="width:50px;" maxlength="25" name="officeSalaryImport.salary13" value="${officeSalaryImport.salary13!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary14?default("")!="">
		        <th style="width:10%">列项17：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary14" style="width:50px;" maxlength="25" name="officeSalaryImport.salary14" value="${officeSalaryImport.salary14!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary15?default("")!="">
		        <th style="width:10%">列项18：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary15" style="width:50px;" maxlength="25" name="officeSalaryImport.salary15" value="${officeSalaryImport.salary15!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary16?default("")!="">
		        <th style="width:10%">列项19：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary16" style="width:50px;" maxlength="25" name="officeSalaryImport.salary16" value="${officeSalaryImport.salary16!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary17?default("")!="">
		        <th style="width:10%">列项20：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary17" style="width:50px;" maxlength="25" name="officeSalaryImport.salary17" value="${officeSalaryImport.salary17!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary18?default("")!="">
		        <th style="width:10%">列项21：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary18" style="width:50px;" maxlength="25" name="officeSalaryImport.salary18" value="${officeSalaryImport.salary18!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary19?default("")!="">
		        <th style="width:10%">列项22：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary19" style="width:50px;" maxlength="25" name="officeSalaryImport.salary19" value="${officeSalaryImport.salary19!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary20?default("")!="">
		        <th style="width:10%">列项23：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary20" style="width:50px;" maxlength="25" name="officeSalaryImport.salary20" value="${officeSalaryImport.salary20!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary21?default("")!="">
		        <th style="width:10%">列项24：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary21" style="width:50px;" maxlength="25" name="officeSalaryImport.salary21" value="${officeSalaryImport.salary21!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary22?default("")!="">
		        <th style="width:10%">列项25：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary22" style="width:50px;" maxlength="25" name="officeSalaryImport.salary22" value="${officeSalaryImport.salary22!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary23?default("")!="">
		        <th style="width:10%">列项26：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary23" style="width:50px;" maxlength="25" name="officeSalaryImport.salary23" value="${officeSalaryImport.salary23!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary24?default("")!="">
		        <th style="width:10%">列项27：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary24" style="width:50px;" maxlength="25" name="officeSalaryImport.salary24" value="${officeSalaryImport.salary24!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary25?default("")!="">
		        <th style="width:10%">列项28：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary25" style="width:50px;" maxlength="25" name="officeSalaryImport.salary25" value="${officeSalaryImport.salary25!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary26?default("")!="">
		        <th style="width:10%">列项29：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary26" style="width:50px;" maxlength="25" name="officeSalaryImport.salary26" value="${officeSalaryImport.salary26!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary27?default("")!="">
		        <th style="width:10%">列项30：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary27" style="width:50px;" maxlength="25" name="officeSalaryImport.salary27" value="${officeSalaryImport.salary27!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary28?default("")!="">
		        <th style="width:10%">列项31：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary28" style="width:50px;" maxlength="25" name="officeSalaryImport.salary28" value="${officeSalaryImport.salary28!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary29?default("")!="">
		        <th style="width:10%">列项32：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary29" style="width:50px;" maxlength="25" name="officeSalaryImport.salary29" value="${officeSalaryImport.salary29!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary30?default("")!="">
		        <th style="width:10%">列项33：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary30" style="width:50px;" maxlength="25" name="officeSalaryImport.salary30" value="${officeSalaryImport.salary30!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary31?default("")!="">
		        <th style="width:10%">列项34：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary31" style="width:50px;" maxlength="25" name="officeSalaryImport.salary31" value="${officeSalaryImport.salary31!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary32?default("")!="">
		        <th style="width:10%">列项35：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary32" style="width:50px;" maxlength="25" name="officeSalaryImport.salary32" value="${officeSalaryImport.salary32!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary33?default("")!="">
		        <th style="width:10%">列项36：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary33" style="width:50px;" maxlength="25" name="officeSalaryImport.salary33" value="${officeSalaryImport.salary33!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary34?default("")!="">
		        <th style="width:10%">列项37：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary34" style="width:50px;" maxlength="25" name="officeSalaryImport.salary34" value="${officeSalaryImport.salary34!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary35?default("")!="">
		        <th style="width:10%">列项38：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary35" style="width:50px;" maxlength="25" name="officeSalaryImport.salary35" value="${officeSalaryImport.salary35!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary36?default("")!="">
		        <th style="width:10%">列项39：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary36" style="width:50px;" maxlength="25" name="officeSalaryImport.salary36" value="${officeSalaryImport.salary36!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary37?default("")!="">
		        <th style="width:10%">列项40：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary37" style="width:50px;" maxlength="25" name="officeSalaryImport.salary37" value="${officeSalaryImport.salary37!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary38?default("")!="">
		        <th style="width:10%">列项41：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary38" style="width:50px;" maxlength="25" name="officeSalaryImport.salary38" value="${officeSalaryImport.salary38!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary39?default("")!="">
		        <th style="width:10%">列项42：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary39" style="width:50px;" maxlength="25" name="officeSalaryImport.salary39" value="${officeSalaryImport.salary39!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary40?default("")!="">
		        <th style="width:10%">列项43：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary40" style="width:50px;" maxlength="25" name="officeSalaryImport.salary40" value="${officeSalaryImport.salary40!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary41?default("")!="">
		        <th style="width:10%">列项44：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary41" style="width:50px;" maxlength="25" name="officeSalaryImport.salary41" value="${officeSalaryImport.salary41!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary42?default("")!="">
		        <th style="width:10%">列项45：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary42" style="width:50px;" maxlength="25" name="officeSalaryImport.salary42" value="${officeSalaryImport.salary42!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary43?default("")!="">
		        <th style="width:10%">列项46：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary43" style="width:50px;" maxlength="25" name="officeSalaryImport.salary43" value="${officeSalaryImport.salary43!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary44?default("")!="">
		        <th style="width:10%">列项47：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary44" style="width:50px;" maxlength="25" name="officeSalaryImport.salary44" value="${officeSalaryImport.salary44!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary45?default("")!="">
		        <th style="width:10%">列项48：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary45" style="width:50px;" maxlength="25" name="officeSalaryImport.salary45" value="${officeSalaryImport.salary45!}">
		        </td>
		        </#if>
		        <#if officeSalaryImport.salary46?default("")!="">
		        <th style="width:10%">列项49：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary46" style="width:50px;" maxlength="25" name="officeSalaryImport.salary46" value="${officeSalaryImport.salary46!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary47?default("")!="">
		        <th style="width:10%">列项50：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary47" style="width:50px;" maxlength="25" name="officeSalaryImport.salary47" value="${officeSalaryImport.salary47!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary48?default("")!="">
		        <th style="width:10%">列项51：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary48" style="width:50px;" maxlength="25" name="officeSalaryImport.salary48" value="${officeSalaryImport.salary48!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary49?default("")!="">
		        <th style="width:10%">列项52：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary49" style="width:50px;" maxlength="25" name="officeSalaryImport.salary49" value="${officeSalaryImport.salary49!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary50?default("")!="">
		        <th style="width:10%">列项53：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary50" style="width:50px;" maxlength="25" name="officeSalaryImport.salary50" value="${officeSalaryImport.salary50!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary51?default("")!="">
		        <th style="width:10%">列项54：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary51" style="width:50px;" maxlength="25" name="officeSalaryImport.salary51" value="${officeSalaryImport.salary51!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary52?default("")!="">
		        <th style="width:10%">列项55：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary52" style="width:50px;" maxlength="25" name="officeSalaryImport.salary52" value="${officeSalaryImport.salary52!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary53?default("")!="">
		        <th style="width:10%">列项56：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary53" style="width:50px;" maxlength="25" name="officeSalaryImport.salary53" value="${officeSalaryImport.salary53!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary54?default("")!="">
		        <th style="width:10%">列项57：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary54" style="width:50px;" maxlength="25" name="officeSalaryImport.salary54" value="${officeSalaryImport.salary54!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary55?default("")!="">
		        <th style="width:10%">列项58：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary55" style="width:50px;" maxlength="25" name="officeSalaryImport.salary55" value="${officeSalaryImport.salary55!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary56?default("")!="">
		        <th style="width:10%">列项59：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary56" style="width:50px;" maxlength="25" name="officeSalaryImport.salary56" value="${officeSalaryImport.salary56!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary57?default("")!="">
		        <th style="width:10%">列项60：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary57" style="width:50px;" maxlength="25" name="officeSalaryImport.salary57" value="${officeSalaryImport.salary57!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary58?default("")!="">
		        <th style="width:10%">列项61：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary58" style="width:50px;" maxlength="25" name="officeSalaryImport.salary58" value="${officeSalaryImport.salary58!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary59?default("")!="">
		        <th style="width:10%">列项62：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary59" style="width:50px;" maxlength="25" name="officeSalaryImport.salary59" value="${officeSalaryImport.salary59!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary60?default("")!="">
		        <th style="width:10%">列项63：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary60" style="width:50px;" maxlength="25" name="officeSalaryImport.salary60" value="${officeSalaryImport.salary60!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary61?default("")!="">
		        <th style="width:10%">列项64：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary61" style="width:50px;" maxlength="25" name="officeSalaryImport.salary61" value="${officeSalaryImport.salary61!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary62?default("")!="">
		        <th style="width:10%">列项65：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary62" style="width:50px;" maxlength="25" name="officeSalaryImport.salary62" value="${officeSalaryImport.salary62!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary63?default("")!="">
		        <th style="width:10%">列项66：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary63" style="width:50px;" maxlength="25" name="officeSalaryImport.salary63" value="${officeSalaryImport.salary63!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary64?default("")!="">
		        <th style="width:10%">列项67：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary64" style="width:50px;" maxlength="25" name="officeSalaryImport.salary64" value="${officeSalaryImport.salary64!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary65?default("")!="">
		        <th style="width:10%">列项68：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary65" style="width:50px;" maxlength="25" name="officeSalaryImport.salary65" value="${officeSalaryImport.salary65!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary66?default("")!="">
		        <th style="width:10%">列项69：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary66" style="width:50px;" maxlength="25" name="officeSalaryImport.salary66" value="${officeSalaryImport.salary66!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary67?default("")!="">
		        <th style="width:10%">列项70：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary67" style="width:50px;" maxlength="25" name="officeSalaryImport.salary67" value="${officeSalaryImport.salary67!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary68?default("")!="">
		        <th style="width:10%">列项71：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary68" style="width:50px;" maxlength="25" name="officeSalaryImport.salary68" value="${officeSalaryImport.salary68!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary69?default("")!="">
		        <th style="width:10%">列项72：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary69" style="width:50px;" maxlength="25" name="officeSalaryImport.salary69" value="${officeSalaryImport.salary69!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary70?default("")!="">
		        <th style="width:10%">列项73：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary70" style="width:50px;" maxlength="25" name="officeSalaryImport.salary70" value="${officeSalaryImport.salary70!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary71?default("")!="">
		        <th style="width:10%">列项74：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary71" style="width:50px;" maxlength="25" name="officeSalaryImport.salary71" value="${officeSalaryImport.salary71!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary72?default("")!="">
		        <th style="width:10%">列项75：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary72" style="width:50px;" maxlength="25" name="officeSalaryImport.salary72" value="${officeSalaryImport.salary72!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary73?default("")!="">
		        <th style="width:10%">列项76：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary73" style="width:50px;" maxlength="25" name="officeSalaryImport.salary73" value="${officeSalaryImport.salary73!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary74?default("")!="">
		        <th style="width:10%">列项77：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary74" style="width:50px;" maxlength="25" name="officeSalaryImport.salary74" value="${officeSalaryImport.salary74!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary75?default("")!="">
		        <th style="width:10%">列项78：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary75" style="width:50px;" maxlength="25" name="officeSalaryImport.salary75" value="${officeSalaryImport.salary75!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary76?default("")!="">
		        <th style="width:10%">列项79：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary76" style="width:50px;" maxlength="25" name="officeSalaryImport.salary76" value="${officeSalaryImport.salary76!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary77?default("")!="">
		        <th style="width:10%">列项80：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary77" style="width:50px;" maxlength="25" name="officeSalaryImport.salary77" value="${officeSalaryImport.salary77!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary78?default("")!="">
		        <th style="width:10%">列项81：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary78" style="width:50px;" maxlength="25" name="officeSalaryImport.salary78" value="${officeSalaryImport.salary78!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary79?default("")!="">
		        <th style="width:10%">列项82：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary79" style="width:50px;" maxlength="25" name="officeSalaryImport.salary79" value="${officeSalaryImport.salary79!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary80?default("")!="">
		        <th style="width:10%">列项83：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary80" style="width:50px;" maxlength="25" name="officeSalaryImport.salary80" value="${officeSalaryImport.salary80!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary81?default("")!="">
		        <th style="width:10%">列项84：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary81" style="width:50px;" maxlength="25" name="officeSalaryImport.salary81" value="${officeSalaryImport.salary81!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary82?default("")!="">
		        <th style="width:10%">列项85：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary82" style="width:50px;" maxlength="25" name="officeSalaryImport.salary82" value="${officeSalaryImport.salary82!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary83?default("")!="">
		        <th style="width:10%">列项86：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary83" style="width:50px;" maxlength="25" name="officeSalaryImport.salary83" value="${officeSalaryImport.salary83!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary84?default("")!="">
		        <th style="width:10%">列项87：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary84" style="width:50px;" maxlength="25" name="officeSalaryImport.salary84" value="${officeSalaryImport.salary84!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary85?default("")!="">
		        <th style="width:10%">列项88：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary85" style="width:50px;" maxlength="25" name="officeSalaryImport.salary85" value="${officeSalaryImport.salary85!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary86?default("")!="">
		        <th style="width:10%">列项89：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary86" style="width:50px;" maxlength="25" name="officeSalaryImport.salary86" value="${officeSalaryImport.salary86!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary87?default("")!="">
		        <th style="width:10%">列项90：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary87" style="width:50px;" maxlength="25" name="officeSalaryImport.salary87" value="${officeSalaryImport.salary87!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary88?default("")!="">
		        <th style="width:10%">列项91：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary88" style="width:50px;" maxlength="25" name="officeSalaryImport.salary88" value="${officeSalaryImport.salary88!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary89?default("")!="">
		        <th style="width:10%">列项92：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary89" style="width:50px;" maxlength="25" name="officeSalaryImport.salary89" value="${officeSalaryImport.salary89!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary90?default("")!="">
		        <th style="width:10%">列项93：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary90" style="width:50px;" maxlength="25" name="officeSalaryImport.salary90" value="${officeSalaryImport.salary90!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary91?default("")!="">
		        <th style="width:10%">列项94：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary91" style="width:50px;" maxlength="25" name="officeSalaryImport.salary91" value="${officeSalaryImport.salary91!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary92?default("")!="">
		        <th style="width:10%">列项95：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary92" style="width:50px;" maxlength="25" name="officeSalaryImport.salary92" value="${officeSalaryImport.salary92!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary93?default("")!="">
		        <th style="width:10%">列项96：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary93" style="width:50px;" maxlength="25" name="officeSalaryImport.salary93" value="${officeSalaryImport.salary93!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary94?default("")!="">
		        <th style="width:10%">列项97：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary94" style="width:50px;" maxlength="25" name="officeSalaryImport.salary94" value="${officeSalaryImport.salary94!}">
		        </td>
		        </#if>
		    </tr> 
		    <tr>
		    	<#if officeSalaryImport.salary95?default("")!="">
		        <th style="width:10%">列项98：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary95" style="width:50px;" maxlength="25" name="officeSalaryImport.salary95" value="${officeSalaryImport.salary95!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary96?default("")!="">
		        <th style="width:10%">列项99：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary96" style="width:50px;" maxlength="25" name="officeSalaryImport.salary96" value="${officeSalaryImport.salary96!}">
		        </td>
		        </#if>
		    	<#if officeSalaryImport.salary97?default("")!="">
		        <th style="width:10%">列项100：</th>
		        <td style="width:15%">
		        	<input type="text" class="input-txt fn-left" id="salary97" style="width:50px;" maxlength="25" name="officeSalaryImport.salary97" value="${officeSalaryImport.salary97!}">
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
        url: "${request.contextPath}/office/salarymanage/salarymanage-salaryImportSave.action",
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