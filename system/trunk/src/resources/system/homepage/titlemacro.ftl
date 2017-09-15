<#--
*  宏名：titleDiv
*  功能：实现页头显示
-->
<#macro titleDiv>
<link href="${request.contextPath}/static/system/homepage/css/smalltitle.css" type="text/css" rel="stylesheet">
<table width="100%"  border="0" cellspacing="0" cellpadding="0"><tr><td>
    <div class="headerBg">
	<div class="Indexlogo"><img src="${request.contextPath}/common/logoFile.action?filePath=system/homepage/images/logoLeft.jpg" alt="Logo" /></div>
    <div class="rightBanner">
    	<div class="topLvOne">
        	<div class="rightLink">
            	<div class="rightLinkInner">
                    <span><img src="${request.contextPath}/static/system/homepage/images/head_icon1.gif" alt="Home" align="top" />
			<#if subSystem?exists>
				<a href="nav.action?appId=${subSystem.id?default(10)}" target="mainframe">
			<#else>
				<a href="nav.action?appId=10" target="mainframe">
			</#if>
			导航</a></span>
                    <span><img src="${request.contextPath}/static/system/homepage/images/head_icon3.gif" alt="AboutMe" align="top" /><a href="#" onclick="javascript:window.open('${request.contextPath}/${action.getText('eis.about.postfix')}?subSystem=${appId}','_blank','height=340,width=450,top=100,left=250')" >关于我们</a></span>
                    <span><img src="${request.contextPath}/static/system/homepage/images/head_icon4.gif" alt="Help" align="top" /><a href="#" onclick="javascript:onClickHelp(this);" target="_blank">帮助</a></span>
                    <span><img src="${request.contextPath}/static/system/homepage/images/head_icon5.gif" alt="Return" align="top" /><a href="${request.contextPath}\fpf\homepage\back.action">返回中心</a></span>
                </div>
                <img src="${request.contextPath}/static/system/homepage/images/logoRight.jpg" alt="rightLogo" />
            </div>
            <h1>${loginInfo.unitName}</h1>
        </div>
        <div class="topLvTwo">
        	<div class="rightWelcome">
            	<span class="teacherName">${loginInfo.user.realname?default("")} 老师</span>
                <span class="date"><#if currentSemester?exists>当前是：${currentSemester.acadyear}学年　${appsetting.getMcode("DM-XQ").get(currentSemester.semester)}
                    <#else>
    	                还没有设置当前学年学期，请设置！
                    </#if></span>
            </div>
            <div class="rightLogout">
            	<span class="time">当前日期：${currentDate?string("yyyy-MM-dd")}</span>
                <a class="logoutLink" href="${request.contextPath}/${action.getText('eis.logout.postfix')}">退出</a>
            </div>
        </div>
    </div>
	</div></td>
   </tr>
</table>
</#macro>