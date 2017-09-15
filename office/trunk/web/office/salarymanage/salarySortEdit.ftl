<#import "/common/htmlcomponent.ftl" as htmlmacro>
<#import "/common/commonmacro.ftl" as commonmacro>
<@htmlmacro.moduleDiv titleName="工资列项编辑">
<p class="tt"><a href="javascript:void(0);" class="close">关闭</a><span>工资列项编辑</span></p>
	<div class="wrap pa-25">
	<form name="salaryForm" id="salaryForm" method="post">
	<div class="typical-table-wrap pt-15" <#if officeSalaryImport.salary51?default("")!=""> style="height:580px;overflow-y:auto;"</#if>>
	<input type="hidden" id="salaryId" name="officeSalarySort.id" value="${officeSalarySort.id!}">
	<table border="0" cellspacing="0" cellpadding="0" class="table-form">
		<@htmlmacro.tableDetail divClass="table-form">
			<tr>
				<#if officeSalaryImport.salary1?default("")!="">
				<th style="width:10%">列项1：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary1 style="width:50px;" maxlength="25" name="officeSalarySort.sort1" value="${officeSalarySort.sort1!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary2?default("")!="">
				<th style="width:10%">列项2：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary2 style="width:50px;" maxlength="25" name="officeSalarySort.sort2" value="${officeSalarySort.sort2!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary3?default("")!="">
				<th style="width:10%">列项3：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary3 style="width:50px;" maxlength="25" name="officeSalarySort.sort3" value="${officeSalarySort.sort3!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary4?default("")!="">
				<th style="width:10%">列项4：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary4 style="width:50px;" maxlength="25" name="officeSalarySort.sort4" value="${officeSalarySort.sort4!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary5?default("")!="">
				<th style="width:10%">列项5：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary5 style="width:50px;" maxlength="25" name="officeSalarySort.sort5" value="${officeSalarySort.sort5!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary6?default("")!="">
				<th style="width:10%">列项6：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary6 style="width:50px;" maxlength="25" name="officeSalarySort.sort6" value="${officeSalarySort.sort6!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary7?default("")!="">
				<th style="width:10%">列项7：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary7 style="width:50px;" maxlength="25" name="officeSalarySort.sort7" value="${officeSalarySort.sort7!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary8?default("")!="">
				<th style="width:10%">列项8：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary8 style="width:50px;" maxlength="25" name="officeSalarySort.sort8" value="${officeSalarySort.sort8!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary9?default("")!="">
				<th style="width:10%">列项9：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary9 style="width:50px;" maxlength="25" name="officeSalarySort.sort9" value="${officeSalarySort.sort9!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary10?default("")!="">
				<th style="width:10%">列项10：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary10 style="width:50px;" maxlength="25" name="officeSalarySort.sort10" value="${officeSalarySort.sort10!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary11?default("")!="">
				<th style="width:10%">列项11：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary11 style="width:50px;" maxlength="25" name="officeSalarySort.sort11" value="${officeSalarySort.sort11!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary12?default("")!="">
				<th style="width:10%">列项12：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary12 style="width:50px;" maxlength="25" name="officeSalarySort.sort12" value="${officeSalarySort.sort12!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary13?default("")!="">
				<th style="width:10%">列项13：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary13 style="width:50px;" maxlength="25" name="officeSalarySort.sort13" value="${officeSalarySort.sort13!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary14?default("")!="">
				<th style="width:10%">列项14：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary14 style="width:50px;" maxlength="25" name="officeSalarySort.sort14" value="${officeSalarySort.sort14!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary15?default("")!="">
				<th style="width:10%">列项15：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary15 style="width:50px;" maxlength="25" name="officeSalarySort.sort15" value="${officeSalarySort.sort15!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary16?default("")!="">
				<th style="width:10%">列项16：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary16 style="width:50px;" maxlength="25" name="officeSalarySort.sort16" value="${officeSalarySort.sort16!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary17?default("")!="">
				<th style="width:10%">列项17：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary17 style="width:50px;" maxlength="25" name="officeSalarySort.sort17" value="${officeSalarySort.sort17!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary18?default("")!="">
				<th style="width:10%">列项18：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary18 style="width:50px;" maxlength="25" name="officeSalarySort.sort18" value="${officeSalarySort.sort18!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary19?default("")!="">
				<th style="width:10%">列项19：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary19 style="width:50px;" maxlength="25" name="officeSalarySort.sort19" value="${officeSalarySort.sort19!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary20?default("")!="">
				<th style="width:10%">列项20：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary20 style="width:50px;" maxlength="25" name="officeSalarySort.sort20" value="${officeSalarySort.sort20!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary21?default("")!="">
				<th style="width:10%">列项21：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary21 style="width:50px;" maxlength="25" name="officeSalarySort.sort21" value="${officeSalarySort.sort21!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary22?default("")!="">
				<th style="width:10%">列项22：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary22 style="width:50px;" maxlength="25" name="officeSalarySort.sort22" value="${officeSalarySort.sort22!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary23?default("")!="">
				<th style="width:10%">列项23：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary23 style="width:50px;" maxlength="25" name="officeSalarySort.sort23" value="${officeSalarySort.sort23!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary24?default("")!="">
				<th style="width:10%">列项24：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary24 style="width:50px;" maxlength="25" name="officeSalarySort.sort24" value="${officeSalarySort.sort24!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary25?default("")!="">
				<th style="width:10%">列项25：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary25 style="width:50px;" maxlength="25" name="officeSalarySort.sort25" value="${officeSalarySort.sort25!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary26?default("")!="">
				<th style="width:10%">列项26：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary26 style="width:50px;" maxlength="25" name="officeSalarySort.sort26" value="${officeSalarySort.sort26!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary27?default("")!="">
				<th style="width:10%">列项27：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary27 style="width:50px;" maxlength="25" name="officeSalarySort.sort27" value="${officeSalarySort.sort27!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary28?default("")!="">
				<th style="width:10%">列项28：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary28 style="width:50px;" maxlength="25" name="officeSalarySort.sort28" value="${officeSalarySort.sort28!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary29?default("")!="">
				<th style="width:10%">列项29：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary29 style="width:50px;" maxlength="25" name="officeSalarySort.sort29" value="${officeSalarySort.sort29!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary30?default("")!="">
				<th style="width:10%">列项30：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary30 style="width:50px;" maxlength="25" name="officeSalarySort.sort30" value="${officeSalarySort.sort30!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary31?default("")!="">
				<th style="width:10%">列项31：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary31 style="width:50px;" maxlength="25" name="officeSalarySort.sort31" value="${officeSalarySort.sort31!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary32?default("")!="">
				<th style="width:10%">列项32：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary32 style="width:50px;" maxlength="25" name="officeSalarySort.sort32" value="${officeSalarySort.sort32!}">
				</td>
				</#if>
			</tr>	
			<tr>	
				<#if officeSalaryImport.salary33?default("")!="">
				<th style="width:10%">列项33：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary33 style="width:50px;" maxlength="25" name="officeSalarySort.sort33" value="${officeSalarySort.sort33!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary34?default("")!="">
				<th style="width:10%">列项34：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary34 style="width:50px;" maxlength="25" name="officeSalarySort.sort34" value="${officeSalarySort.sort34!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary35?default("")!="">
				<th style="width:10%">列项35：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary35 style="width:50px;" maxlength="25" name="officeSalarySort.sort35" value="${officeSalarySort.sort35!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary36?default("")!="">
				<th style="width:10%">列项36：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary36 style="width:50px;" maxlength="25" name="officeSalarySort.sort36" value="${officeSalarySort.sort36!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary37?default("")!="">
				<th style="width:10%">列项37：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary37 style="width:50px;" maxlength="25" name="officeSalarySort.sort37" value="${officeSalarySort.sort37!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary38?default("")!="">
				<th style="width:10%">列项38：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary38 style="width:50px;" maxlength="25" name="officeSalarySort.sort38" value="${officeSalarySort.sort38!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary39?default("")!="">
				<th style="width:10%">列项39：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary39 style="width:50px;" maxlength="25" name="officeSalarySort.sort39" value="${officeSalarySort.sort39!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary40?default("")!="">
				<th style="width:10%">列项40：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary40 style="width:50px;" maxlength="25" name="officeSalarySort.sort40" value="${officeSalarySort.sort40!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary41?default("")!="">
				<th style="width:10%">列项41：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary41 style="width:50px;" maxlength="25" name="officeSalarySort.sort41" value="${officeSalarySort.sort41!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary42?default("")!="">
				<th style="width:10%">列项42：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary42 style="width:50px;" maxlength="25" name="officeSalarySort.sort42" value="${officeSalarySort.sort42!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary43?default("")!="">
				<th style="width:10%">列项43：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary43 style="width:50px;" maxlength="25" name="officeSalarySort.sort43" value="${officeSalarySort.sort43!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary44?default("")!="">
				<th style="width:10%">列项44：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary44 style="width:50px;" maxlength="25" name="officeSalarySort.sort44" value="${officeSalarySort.sort44!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary45?default("")!="">
				<th style="width:10%">列项45：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary45 style="width:50px;" maxlength="25" name="officeSalarySort.sort45" value="${officeSalarySort.sort45!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary46?default("")!="">
				<th style="width:10%">列项46：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary46 style="width:50px;" maxlength="25" name="officeSalarySort.sort46" value="${officeSalarySort.sort46!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary47?default("")!="">
				<th style="width:10%">列项47：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary47 style="width:50px;" maxlength="25" name="officeSalarySort.sort47" value="${officeSalarySort.sort47!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary48?default("")!="">
				<th style="width:10%">列项48：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary48 style="width:50px;" maxlength="25" name="officeSalarySort.sort48" value="${officeSalarySort.sort48!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary49?default("")!="">
				<th style="width:10%">列项49：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary49 style="width:50px;" maxlength="25" name="officeSalarySort.sort49" value="${officeSalarySort.sort49!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary50?default("")!="">
				<th style="width:10%">列项50：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary50 style="width:50px;" maxlength="25" name="officeSalarySort.sort50" value="${officeSalarySort.sort50!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary51?default("")!="">
				<th style="width:10%">列项51：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary51 style="width:50px;" maxlength="25" name="officeSalarySort.sort51" value="${officeSalarySort.sort51!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary52?default("")!="">
				<th style="width:10%">列项52：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary52 style="width:50px;" maxlength="25" name="officeSalarySort.sort52" value="${officeSalarySort.sort52!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary53?default("")!="">
				<th style="width:10%">列项53：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary53 style="width:50px;" maxlength="25" name="officeSalarySort.sort53" value="${officeSalarySort.sort53!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary54?default("")!="">
				<th style="width:10%">列项54：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary54 style="width:50px;" maxlength="25" name="officeSalarySort.sort54" value="${officeSalarySort.sort54!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary55?default("")!="">
				<th style="width:10%">列项55：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary55 style="width:50px;" maxlength="25" name="officeSalarySort.sort55" value="${officeSalarySort.sort55!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary56?default("")!="">
				<th style="width:10%">列项56：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary56 style="width:50px;" maxlength="25" name="officeSalarySort.sort56" value="${officeSalarySort.sort56!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary57?default("")!="">
				<th style="width:10%">列项57：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary57 style="width:50px;" maxlength="25" name="officeSalarySort.sort57" value="${officeSalarySort.sort57!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary58?default("")!="">
				<th style="width:10%">列项58：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary58 style="width:50px;" maxlength="25" name="officeSalarySort.sort58" value="${officeSalarySort.sort58!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary59?default("")!="">
				<th style="width:10%">列项59：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary59 style="width:50px;" maxlength="25" name="officeSalarySort.sort59" value="${officeSalarySort.sort59!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary60?default("")!="">
				<th style="width:10%">列项60：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary60 style="width:50px;" maxlength="25" name="officeSalarySort.sort60" value="${officeSalarySort.sort60!}">
				</td>
				</#if>
			</tr>
			<tr>	
				<#if officeSalaryImport.salary61?default("")!="">
				<th style="width:10%">列项61：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary61 style="width:50px;" maxlength="25" name="officeSalarySort.sort61" value="${officeSalarySort.sort61!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary62?default("")!="">
				<th style="width:10%">列项62：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary62 style="width:50px;" maxlength="25" name="officeSalarySort.sort62" value="${officeSalarySort.sort62!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary63?default("")!="">
				<th style="width:10%">列项63：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary63 style="width:50px;" maxlength="25" name="officeSalarySort.sort63" value="${officeSalarySort.sort63!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary64?default("")!="">
				<th style="width:10%">列项64：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary64 style="width:50px;" maxlength="25" name="officeSalarySort.sort64" value="${officeSalarySort.sort64!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary65?default("")!="">
				<th style="width:10%">列项65：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary65 style="width:50px;" maxlength="25" name="officeSalarySort.sort65" value="${officeSalarySort.sort65!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary66?default("")!="">
				<th style="width:10%">列项66：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary66 style="width:50px;" maxlength="25" name="officeSalarySort.sort66" value="${officeSalarySort.sort66!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary67?default("")!="">
				<th style="width:10%">列项67：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary67 style="width:50px;" maxlength="25" name="officeSalarySort.sort67" value="${officeSalarySort.sort67!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary68?default("")!="">
				<th style="width:10%">列项68：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary68 style="width:50px;" maxlength="25" name="officeSalarySort.sort68" value="${officeSalarySort.sort68!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary69?default("")!="">
				<th style="width:10%">列项69：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary69 style="width:50px;" maxlength="25" name="officeSalarySort.sort69" value="${officeSalarySort.sort69!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary70?default("")!="">
				<th style="width:10%">列项70：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary70 style="width:50px;" maxlength="25" name="officeSalarySort.sort70" value="${officeSalarySort.sort70!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary71?default("")!="">
				<th style="width:10%">列项71：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary71 style="width:50px;" maxlength="25" name="officeSalarySort.sort71" value="${officeSalarySort.sort71!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary72?default("")!="">
				<th style="width:10%">列项72：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary72 style="width:50px;" maxlength="25" name="officeSalarySort.sort72" value="${officeSalarySort.sort72!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary73?default("")!="">
				<th style="width:10%">列项73：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary73 style="width:50px;" maxlength="25" name="officeSalarySort.sort73" value="${officeSalarySort.sort73!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary74?default("")!="">
				<th style="width:10%">列项74：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary74 style="width:50px;" maxlength="25" name="officeSalarySort.sort74" value="${officeSalarySort.sort74!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary75?default("")!="">
				<th style="width:10%">列项75：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary75 style="width:50px;" maxlength="25" name="officeSalarySort.sort75" value="${officeSalarySort.sort75!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary76?default("")!="">
				<th style="width:10%">列项76：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary76 style="width:50px;" maxlength="25" name="officeSalarySort.sort76" value="${officeSalarySort.sort76!}">
				</td>
				</#if>
			</tr>
			<tr>	
				<#if officeSalaryImport.salary77?default("")!="">
				<th style="width:10%">列项77：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary77 style="width:50px;" maxlength="25" name="officeSalarySort.sort77" value="${officeSalarySort.sort77!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary78?default("")!="">
				<th style="width:10%">列项78：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary78 style="width:50px;" maxlength="25" name="officeSalarySort.sort78" value="${officeSalarySort.sort78!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary79?default("")!="">
				<th style="width:10%">列项79：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary79 style="width:50px;" maxlength="25" name="officeSalarySort.sort79" value="${officeSalarySort.sort79!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary80?default("")!="">
				<th style="width:10%">列项80：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary80 style="width:50px;" maxlength="25" name="officeSalarySort.sort80" value="${officeSalarySort.sort80!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary81?default("")!="">
				<th style="width:10%">列项81：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary81 style="width:50px;" maxlength="25" name="officeSalarySort.sort81" value="${officeSalarySort.sort81!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary82?default("")!="">
				<th style="width:10%">列项82：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary82 style="width:50px;" maxlength="25" name="officeSalarySort.sort82" value="${officeSalarySort.sort82!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary83?default("")!="">
				<th style="width:10%">列项83：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary83 style="width:50px;" maxlength="25" name="officeSalarySort.sort83" value="${officeSalarySort.sort83!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary84?default("")!="">
				<th style="width:10%">列项84：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary84 style="width:50px;" maxlength="25" name="officeSalarySort.sort84" value="${officeSalarySort.sort84!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary85?default("")!="">
				<th style="width:10%">列项85：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary85 style="width:50px;" maxlength="25" name="officeSalarySort.sort85" value="${officeSalarySort.sort85!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary86?default("")!="">
				<th style="width:10%">列项86：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary86 style="width:50px;" maxlength="25" name="officeSalarySort.sort86" value="${officeSalarySort.sort86!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary87?default("")!="">
				<th style="width:10%">列项87：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary87 style="width:50px;" maxlength="25" name="officeSalarySort.sort87" value="${officeSalarySort.sort87!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary88?default("")!="">
				<th style="width:10%">列项88：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary88 style="width:50px;" maxlength="25" name="officeSalarySort.sort88" value="${officeSalarySort.sort88!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary89?default("")!="">
				<th style="width:10%">列项89：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary89 style="width:50px;" maxlength="25" name="officeSalarySort.sort89" value="${officeSalarySort.sort89!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary90?default("")!="">
				<th style="width:10%">列项90：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary90 style="width:50px;" maxlength="25" name="officeSalarySort.sort90" value="${officeSalarySort.sort90!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary91?default("")!="">
				<th style="width:10%">列项91：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary91 style="width:50px;" maxlength="25" name="officeSalarySort.sort91" value="${officeSalarySort.sort91!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary92?default("")!="">
				<th style="width:10%">列项92：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary92 style="width:50px;" maxlength="25" name="officeSalarySort.sort92" value="${officeSalarySort.sort92!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary93?default("")!="">
				<th style="width:10%">列项93：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary93 style="width:50px;" maxlength="25" name="officeSalarySort.sort93" value="${officeSalarySort.sort93!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary94?default("")!="">
				<th style="width:10%">列项94：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary94 style="width:50px;" maxlength="25" name="officeSalarySort.sort94" value="${officeSalarySort.sort94!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary95?default("")!="">
				<th style="width:10%">列项95：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary95 style="width:50px;" maxlength="25" name="officeSalarySort.sort95" value="${officeSalarySort.sort95!}">
				</td>
				</#if>
				<#if officeSalaryImport.salary96?default("")!="">
				<th style="width:10%">列项96：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary96 style="width:50px;" maxlength="25" name="officeSalarySort.sort96" value="${officeSalarySort.sort96!}">
				</td>
				</#if>
			</tr>
			<tr>
				<#if officeSalaryImport.salary97?default("")!="">
				<th style="width:10%">列项97：</th>
				<td style="width:15%">
				<input type="text" class="input-txt fn-left" id="salary97 style="width:50px;" maxlength="25" name="officeSalarySort.sort97" value="${officeSalarySort.sort97!}">
				</td>
				</#if>
				<td colspan="3">
				</td>
		   </tr>
	
		    <tr>
		    	<td colspan="8" class="td-opt">
		    	    <a class="abtn-blue" href="javascript:void(0);" onclick="doSave('${officeSalarySort.id!}');" id="btnSave">保存</a>
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
        url: "${request.contextPath}/office/salarymanage/salarymanage-salarySortSave.action",
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