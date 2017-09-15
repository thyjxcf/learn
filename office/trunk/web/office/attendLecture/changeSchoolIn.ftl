<#import "/common/htmlcomponent.ftl" as htmlmacro />
<th style="width:20%"><span class="c-orange mr-5">*</span>年级：</th>
			<td style="width:30%">
				<@htmlmacro.select style="width:83%;" valName="officeAttendLecture.gradeId" valId="gradeId" notNull="true" myfunchange="changeGrade" msgName="年级">
				<a val="" ><span>请选择</span></a>
					<#if gradesList?exists && gradesList?size gt 0>
						<#list gradesList as item>
							<a val="${item.id!}"<#if officeAttendLecture.gradeId?default('') == item.id>class="selected"</#if>  ><span>${item.gradename!}</span></a>
						</#list>
					</#if>
				</@htmlmacro.select>
			</td>
	        <th style="width:20%"><span class="c-orange mr-5">*</span>班级：</th>
	        <td style="width:30%">
	        	<@htmlmacro.select style="width:83%;" valName="officeAttendLecture.classId" valId="classId" txtId="classTxt" notNull="true" optionDivName="itemClassIdOptionDiv" msgName="班级">
					<a val="" ><span>请选择</span></a>
				</@htmlmacro.select>
	        </td>