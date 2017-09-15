	//文件夹下的js文件 
	//	├── Uploader.swf                      // SWF文件，当使用Flash运行时需要引入。
	//	├── webuploader.js                    // 完全版本。
	//	├── webuploader.min.js                // min版本
	//	├── webuploader.withoutimage.js       // 去除图片处理的版本，包括HTML5和FLASH.
	//	└── webuploader.withoutimage.min.js   // min版本


	//下面是一个使用的例子
	//页面文件<div id="uploader" class="wu-example">
	//<!-用来存放文件信息->
	//<div id="thelist" class="uploader-list"></div>
	//<div class="btns">
	//<div id="contentUploader">选择文件</div>
	//<button id="ctlBtn" class="btn btn-default">开始上传</button>
	//</div>
	//</div> 
	


	//api详情参考 http://fex.baidu.com/webuploader/doc/index.html
	var uploader ;
	$(function(){
		vselect();
	})
	
	//初始化webuploader
	uploader = WebUploader.create({
        // 不压缩image
        resize: false,
        // swf文件路径
        swf: '${request.contextPath}/static/webuploader/Uploader.swf',
        // 文件接收服务端。 保存文件的action地址 例:${request.contextPath}/officedoc/common/remote-saveAttachment.action
        server: '',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash. webuploader会根据浏览器识别创建上传对象
        pick: {
        	id:'#contentUploader', //选择文件按钮的选择器
        	multiple :true 		//是否开起同时选择多个文件能力。
        },
        fileVal :'uploadContentFile',//设置文件上传域的name
        fileNumLimit :1,			//验证文件总数量, 超出则不允许加入队列 
        fileSizeLimit:104857600,  //验证文件总大小是否超出限制, 超出则不允许加入队列。 单位kb
        fileSingleSizeLimit:104857600, //验证单个文件大小是否超出限制, 超出则不允许加入队列。单位kb
        //需要传到action其它表单的数据
        formData :{
        	contentId:'${archDto.id!}'
        },							
        auto :true 	//设置为 true 后，不需要手动调用上传，有文件选择即开始上传
    });
    
    // 当有文件添加进来之前触发该方法   该方法将在其它验证之前执行  返回false则文件不被添加
    uploader.on( 'beforeFileQueued', function( file ) {
		return true;
    });
    
    //该方法用来处理错误事件 用来监听错误事件
    // Q_EXCEED_NUM_LIMIT 总文件个数超过限制大小 设置fileNumLimit推送的错误信息
    //Q_EXCEED_SIZE_LIMIT 总文件超过限制大小 设置fileSizeLimit推送的错误信息
    //F_EXCEED_SIZE 单个文件超过限制大小 设置fileSingleSizeLimit推送的错误信息
    uploader.on('error', function(errorStr,max,file){
    	if(errorStr=='Q_EXCEED_NUM_LIMIT'){
    		showMsgError("文件个数不能超过"+max);
    	}else if(errorStr=='Q_EXCEED_SIZE_LIMIT'){
    	}else if(errorStr=='F_EXCEED_SIZE'){
    		showMsgError("上传的文件大小不能超过"+max/1024/1024+"M");
    	}
    });
    
    
    // 当有文件添加进来的时候 方法用来处理已经加入上传队列的对象
    uploader.on( 'fileQueued', function( file ) {
    	//例:
    	//var innerHTML = '<li  id="'+file.id+'"><img src="'+_contextPath+'/static/images/icon/file/'+getFileType(file.ext)+'">';
    	//innerHTML+='<span class="name" title="'+file.name+'">'+fileName+'</span>';            
		//innerHTML+='<span class="fr"></span></li>';
        //$('#attachmentP').html($('#attachmentP').html()+innerHTML);     
    });
    
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        closeTip();
        showTip("文件上传中....当前进度"+Math.floor(percentage*100) + "%");
        if(Math.floor(percentage*100)==100){
        	closeTip();
        	showTip("文件保存中...");
        }
    });
	
	//上传成功 data是服务器端返回的数据 支持json格式
    uploader.on( 'uploadSuccess', function( file ,data) {
    	closeTip();
    	if(data.operateSuccess){
    		showMsgSuccess("文件保存完成");
			if(data.businessValue){
				var returnValue = data.businessValue.split("*");
			    var innerHtml =  '<a href="javascript:void(0);" class="att_download" dataValue="'+returnValue[1]+'"';
			    	innerHtml += 'onclick="doDownload(\''+returnValue[1]+'\');" >下载</a>';
			        innerHtml += '<a href="javascript:void(0);" class="att_show" dataId="'+returnValue[0]+'" onclick="viewAttachment(\''+returnValue[0]+'\')">预览</a>';
			        innerHtml += '<a href="javascript:void(0);" class="att_delete"  onclick="doDeleteContentAtt(\'\',\''+returnValue[0]+'\',\''+file.id+'\')">删除</a> ';       		
				$("#"+file.id+" .fr").html(innerHtml);
			}
    	}else{
    		showMsgError("文件保存出错");
    	}
    });
	
	//上传出错 该上传错误是upload本身抛出 例如断网或者分片上传失败
    uploader.on( 'uploadError', function( file ,data) {
    	closeTip();
    	showMsgError("文件上传出错");
    });
	
	//无论上传是否成功 都会在上传完成时执行该事件
    uploader.on( 'uploadComplete', function( file ) {
        
    });
	
	//所有的事件执行都会触发这个事件 用来监听整个服务
    uploader.on( 'all', function( type ) {
    	if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });
    
    //注 $btn 代表上传按钮 不是选择文件的按钮
    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();  //暂停上传
        } else {
            uploader.upload(); //开始上传
        }
    });
    
    //其它以下方法
    //用来计算文件的mod5的值 断点续传使用 未研究
    //uploader.md5File( file )
    //把文件从上传队列中删除
    //uploader.removeFile(file)
    //返回指定状态的文件集合，不传参数将返回所有状态的文件。
    //uploader.getFiles()
    //uploader.getFiles('error')
    //其它方法见api
    