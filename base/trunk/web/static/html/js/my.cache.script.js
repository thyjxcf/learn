/**
 * 动态加载js/css（js缓存版本号更新时远程获取新的js内容并加载，版本号无更新时则从缓存中获取）
 * 会重新加载isVersion="true"的js文件
 * 需先加载jquery.js 且一般放在所有js的最后面
 * callback 加载js完成后执行的方法
 */
function initScript(callback){
	var version = storage.get('version_time_key');
//	var scripts = document.getElementsByTagName('script');
	if(""==version)
		return;
	var scripts = $('script[isVersion="true"]');
	var csss = $('link[isVersion="true"]');

	if(csss.length>0){
		loadCss(csss, 0, version);
	}
	
	if(scripts.length>0){
		loadScript(scripts, 0, version,callback);
	}else{
		if(callback){
			callback();
		}
	}
}

function loadScript(scripts, index, version,callback){
	if(index < scripts.length){
		var _src = scripts[index].src;
		//跳过my.cache.script.js
		if(_src.indexOf('my.cache.script.js')==-1){
			var _newSrc = '';
			if(_src.indexOf('\?')>0){
				_newSrc = _src + '&version_time=' + version;
			}else{
				_newSrc = _src + '?version_time=' + version;
			}
			getJscriptCache(_newSrc, function(){
				index++;
				loadScript(scripts, index, version,callback);
			},true,true);
		}else{
			index++;
			loadScript(scripts, index, version,callback);
		}
	}else{
		if(callback){
			callback();
		}
	}
}

function loadCss(csss, index, version){
	if(index < csss.length){
		var _href = csss[index].href;
		//跳过my.cache.script.js
		var _newHref = '';
		if(_href.indexOf('\?')>0){
			_newHref = _href + '&version_time=' + version;
		}else{
			_newHref = _href + '?version_time=' + version;
		}
		getJscriptCache(_newHref, function(){
			index++;
			loadCss(csss, index, version);
		},true,true);
	}
}

//远程获取js
function getJscriptCache(url,callback,async,cache){
	if(async== undefined){
		async = true;
	}
	if(cache== undefined){
		cache = true;
	}
	$.ajax({
		  url: url,
		  async: async,
		  cache: cache,
//		  dataType: "script",
		  success: callback
	});
};