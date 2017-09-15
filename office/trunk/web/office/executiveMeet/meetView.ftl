<p class="tt"><a href="#" class="close">关闭</a><span>会议查看</span></p>
<table border="0" cellspacing="0" cellpadding="0" class="table-form">
    <tr>
        <th width="30%"><span class="c-orange mr-5">*</span>会议名称：</th>
        <td width="70%" style="word-break:break-all; word-wrap:break-word;">
        	${officeExecutiveMeet.name!}
        </td>
    </tr>
    <tr>
        <th width="30%"><span class="c-orange mr-5">*</span>会议时间：</th>
        <td width="70%">
    		${(officeExecutiveMeet.meetDate?string('yyyy-MM-dd HH:mm:00'))?if_exists}
        </td>
    </tr>
    <tr>
    	<th width="30%"><span class="c-orange mr-5">*</span>会议地点：</th>
        <td width="70%" style="word-break:break-all; word-wrap:break-word;">
        	${officeExecutiveMeet.place!}
        </td>
	</tr>
</table>
<p class="dd">
	<a class="abtn-blue reset ml-5" href="#"><span>关闭</span></a>
</p>
