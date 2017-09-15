// 常量
var WapConstants = {
	//列表搜索类型
	DATA_TYPE_1 : '1',//草稿箱
	DATA_TYPE_2 : '2',//发件箱
	DATA_TYPE_3 : '3',//收件箱
	
	//修改页面类型
	EDIT_TYPE_1 : 'send',//新增(发送)
	EDIT_TYPE_2 : 'reply',//回复
	EDIT_TYPE_3 : 'replyAll',//回复全部
	EDIT_TYPE_4 : 'forwarding',//转发
	EDIT_TYPE_5 : 'reSend',//再次编辑（重发）
	
	//缓存kye
	SEARCH_STR : 'search_str',//搜索内容key
	EDIT_TITLE:'edit_title',
	EDIT_CONTENT:'edit_content',
	EDIT_EXIST_ATT:'edit_exist_att',
	EDIT_DEL_ATT:'edit_del_att',
	
	//是否返回操作
	BACK_LIST_GLAG_0 : '0',
	BACK_LIST_GLAG_1 : '1',
	
	//设置1 草稿箱/2 发件箱/3 收件箱/4废件箱/5 自定义文件夹
	STATE_TYPE_1 : '1',
	STATE_TYPE_2 : '2',
	STATE_TYPE_3 : '3',
	STATE_TYPE_4 : '4',
	STATE_TYPE_5 : '5',
	
	//每页显示几行
	PATE_SIZE : 10,
	
};

//page
var WapPage = {
  curRowNum : -1,
  desc : false,
  id : "",
  maxPageIndex : 1,
  maxRowCount : 1,
  pageIndex : 1,
  pageSize : WapConstants.PATE_SIZE,
  sort : "",
  useCursor : false
};
