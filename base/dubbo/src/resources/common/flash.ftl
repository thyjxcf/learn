<#macro documentViewer url width="642" height="407" rtmpUrl="" id="documentViewer">
	<#if width=="0">
	<div id="${id}" class="flexpaper_viewer" style="height:${height}px"></div>
	<#else>
	<div id="${id}" class="flexpaper_viewer" style="width:${width}px;height:${height}px"></div>
	</#if>
	<script type="text/javascript" src="${request.contextPath}/static/flexpaper/flexpaper.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/flexpaper/flexpaper_flash.js"></script>
    <script type="text/javascript" src="${request.contextPath}/static/flexpaper/flexpaper_handlers.js"></script>
	<script type="text/javascript" src="${request.contextPath}/static/flexpaper/FlexPaperViewer.js"></script>
	<script language="JavaScript">
		var player = new FlexPaperDocViewer("${id}", "${url}?_t="+Date.parse(new Date()), "${request.contextPath}");
	</script>
</#macro>

<#macro videoPlayer url="" extension="flv" image="" resourceId="" width="715" height="520">
	<script type="text/javascript" src="${request.contextPath}/static/jwplay/jwplayer.js"></script>
	<!--[if gt IE 9]> <script src="${request.contextPath}/static/jwplay/jwplayer.html5.js" type="text/javascript"></script> <![endif]--> 
	<script language="JavaScript" src="${request.contextPath}/static/jwplay/VideoJwPlayer.js"></script>
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	     id="videoPlayer" width="${width}" height="${height}" align="middle"
	     codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	    <param name="movie" value="${request.contextPath}/static/jwplay/jwplayer.flash.swf?_1" />
	    <param name="quality" value="high" />
	    <param name="bgcolor" value="#000000" />
	    <param name="wmode" value="Opaque" />
		<param name="scale" value="showall" />
		<param name="allowfullscreen" value="true" />
	    <param name="allowscriptacces" value="always" />
	    <embed 
	    	type="application/x-shockwave-flash"
	    	pluginspage="http://www.macromedia.com/go/getflashplayer"
	    	quality="high"
	        width="${width}"
			height="${height}"
			name="videoPlayer"
			align="middle"
	        quality="high"
	        scale="showall"
			allowscriptaccess="always" 
			allowfullscreen="true"
	    </embed>
	</object>
	<script>
		var player = new VideoPlayer("videoPlayer", "${url}", "${extension}", "${rtmpUrl! }", "${resourceId! }", "${width}", "${height}", "${image}", "${request.contextPath}");
	</script>
</#macro>
