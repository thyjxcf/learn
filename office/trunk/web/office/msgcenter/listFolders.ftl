<p class="tt tt-my">我的信息夹</p>
<div class="msg-sidebar-custom">
    <#list officeMsgFolders as x>
    <p>
        <span class="initial">
            <a href="javascript:void(0);" class="name" onclick="loadMsgDiv(5, '${x.id!}');">${x.folderName!}</a>
            <i class="edit"></i>
            <i class="del"></i>
        </span>
        <span class="finish">
            <input type="text" class="input-txt" maxlength="25"><br/>
            <input type="hidden" class="input-hiddenId" value="${x.id!}"/>
            <a href="javascript:void(0);" class="abtn-blue submit">确定</a>
            <a href="javascript:void(0);" class="cancel ml-5">取消</a>
        </span>
    </p>
    </#list>
</div>
<p class="add"><a href="javascript:void(0);">新建信息夹</a></p>
<script>

$(document).ready(function(){
	//----------自定义侧边栏 start----------
	//鼠标滑过背景变化效果
	$('.msg-sidebar-wrap').on('mouseover','p',function(){
		$(this).addClass('hover');
	});
	$('.msg-sidebar-wrap').on('mouseout','p',function(){
		$(this).removeClass('hover');
	});
	//点击选中当前栏目
	$('.msg-sidebar-wrap>p').click(function(e){
		e.preventDefault();
		$('.msg-sidebar-wrap p').removeClass('current');
		$(this).addClass('current');
	});
	$('.msg-sidebar-custom p a.name').click(function(e){
		e.preventDefault();
		$('.msg-sidebar-wrap p').removeClass('current');
		$(this).parents('p').addClass('current');
	});
	//删除自定义分类
	$('.msg-sidebar-custom').on('click','p .initial .del',function(){
		if(confirm("确定要删除吗？")){
			var id = $(this).parents('p').find('.finish').find('.input-hiddenId').val();
			$.getJSON("${request.contextPath}/office/msgcenter/msgcenter-deleteFolder.action", {
		          "folderId":id
		        }, function(data) {
					if (data!=null && data != '') {//删除失败
				        showMsgError(data);
				    } else {//删除成功
				      	showMsgSuccess("删除成功！","提示",function(){
							load("#msgFloderDiv", "${request.contextPath}/office/msgcenter/msgcenter-listFolders.action");
							$(".move-wrap .move-inner").each(function(){
								var type=$(this).attr("id");
					      		load("#"+type, "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action?type="+type);
							});
					      	//load("#folder1", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
					      	//load("#folder2", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
					      	//load("#folder3", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
					      	//load("#folder4", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder5", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangess.action");
				      		//load("#folder6", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangesss.action");
				      		//load("#folder7", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder8", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder9", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder10", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      	});
				    }
			});
		}
	});
	//编辑自定义分类
	$('.msg-sidebar-custom').on('click','p .initial .edit',function(){
		if($('.msg-sidebar-custom p.new').hasClass('edit')){
			showMsgWarn('请先保存！');
		}else{
			var txt=$(this).siblings('.name').text();
			$('.msg-sidebar-custom p').removeClass('edit');
			$(this).parents('p').addClass('edit').find('.finish').show().find('.input-txt').val(txt);
		};
	});
	//编辑自定义分类---提交
	$('.msg-sidebar-custom').on('click','p .finish .submit',function(e){
		e.preventDefault();
		var txt=$(this).siblings('.input-txt').val();
		if(txt==''){
			showMsgWarn('内容不能为空！');
			return;
		};
		//如果id为空，那么为新增情况，id不为空，为修改情况
		var id = $(this).siblings('.input-hiddenId').val();
		if(id && id!=''){
			$.getJSON("${request.contextPath}/office/msgcenter/msgcenter-saveFolder.action", {
		          "officeMsgFolder.id":id,"officeMsgFolder.folderName":txt
		        }, function(data) {
					if (data!=null && data != '') {//修改失败
				        showMsgError(data);
				    } else {//修改成功
				      	showMsgSuccess("修改成功！","提示",function(){
							load("#msgFloderDiv", "${request.contextPath}/office/msgcenter/msgcenter-listFolders.action");
							$(".move-wrap .move-inner").each(function(){
								var type=$(this).attr("id");
					      		load("#"+type, "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action?type="+type);
							});
				      		//load("#folder1", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder2", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder3", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder4", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder5", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangess.action");
				      		//load("#folder6", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangesss.action");
				      		//load("#folder7", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder8", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder9", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder10", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      	});
				    }
			});
		}else{
			$.getJSON("${request.contextPath}/office/msgcenter/msgcenter-saveFolder.action", {
	          "officeMsgFolder.folderName":txt
		        }, function(data) {
					if (data!=null && data != '') {//新增失败
				        showMsgError(data);
				    } else {//新增成功
				      	showMsgSuccess("新增成功！","提示",function(){
							load("#msgFloderDiv", "${request.contextPath}/office/msgcenter/msgcenter-listFolders.action");
							$(".move-wrap .move-inner").each(function(){
								var type=$(this).attr("id");
					      		load("#"+type, "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action?type="+type);
							});
				      		//load("#folder1", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder2", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder3", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder4", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder5", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangess.action");
				      		//load("#folder6", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChangesss.action");
				      		//load("#folder7", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder8", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      		//load("#folder9", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChange.action");
				      		//load("#folder10", "${request.contextPath}/office/msgcenter/msgcenter-listFoldersChanges.action");
				      	});
				    }
			});
		}
	});
	//编辑自定义分类---取消
	$('.msg-sidebar-custom').on('click','p .finish .cancel',function(e){
		e.preventDefault();
		var txt=$(this).siblings('.input-txt').val();
		var id = $(this).siblings('.input-hiddenId').val();
		if(id && id!=''){
			$(this).parents('p').removeClass('current edit').children('.finish').hide().siblings('.initial').show();
		}else{
			$(this).parents('p').remove();
		}
	});
	//新建自定义分类
	$('.msg-sidebar-wrap p.add a').click(function(e){
		e.preventDefault();
		if($('.msg-sidebar-custom p.edit').length>0){
			showMsgWarn('请先保存！');
		}else{
			var newLen=$('.msg-sidebar-custom').find('p.new').length;
			if(newLen==0){
				var $chr='<p class="current edit new"><span class="initial"><a href="javascript:void(0);" class="name"></a><i class="edit"></i><i class="del"></i></span><span class="finish"><input type="text" class="input-txt" maxlength="25"><br /><a href="javascript:void(0);" class="abtn-blue submit">确定</a><a href="javascript:void(0);" class="cancel ml-5">取消</a></span></p>';
				$($chr).appendTo('.msg-sidebar-custom');
			}else{
				showMsgWarn('新建分类已存在！');
			};
		};
	});
});
</script>