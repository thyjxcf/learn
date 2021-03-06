var L=L||{};
L.WF=$.extend(L.WF,
		{
			START_NAME:"开始",
			END_NAME:"结束",
			IN_SUBMIT:"提交中...",
			MAX_CONNECTION:"每个步骤只能有一条连接线",
			PROCESS_NOT_DONE:"流程尚未完成",
			PROCESS_RUNNING_WELL:"流程运行良好",
			PROCESS_ERROR:"流程运行出错",
			DELETE_FLOW_CONFIRM:"确认要删除选中流程吗？这将删除：<br/>1:流程描述与步骤设置 <br/> 2:依托于该流程的所有工作",
			CONDITION_INVAILD:"值中不能含有'号",
			CONDITION_REPEAT:"条件重复",
			CONDITION_INCOMPLETE:"请补充完整条件",
			CONDITION_NEED:"请先添加条件",
			CONDITION_TIPS:"请先维护条件提示",
			CONDITION_CANNOT_EDIT:"无法编辑已经存在关系的条件",
			CONDITION_FORMAT_ERROR:"条件表达式书写错误,请检查括号匹配",
			TPLNAME_CANNOT_BE_EMPTY:"模板名称不能为空",
			PLEASE_SELECT_PROCESS:"请选择流程",
			SAVE_AND_DESIGN:"保存并设计",
			UNSAVE_FLOW_UNLOAD_TIP:"尚未保存流程，如果离开您的数据会丢失",
			TYPE_FORM_CHANGE_TIP:"尚未保存流程，如果离开您的数据会丢失！",
			CLEAR_CONNECTION_CONFIRM:"确认要清除所有链接吗？",
			PLEASE_SELECT_A_STEP:"请选择要删除的步骤",
			DELETE_STEP_CONFIRM:"确认要删除“<%=name%>”步骤吗？",
			DELETE_SUCCESS:"成功删除步骤",
			FLOW_SAVE_ERROR:"保存流程失败",
			FLOW_NAME_EMPTY:"流程名称不能为空",
			ADD_FLOW:"添加流程",
			FLOW_SAVE_SUCCESS:"保存流程成功",
			FLOW_CHECK_ERROR:"流程校验失败",
			DELETE_GATE_CONFIRM:"确认要删除并行节点吗？",
			DELETE_GATE_SUCCESS:"成功删除并行节点",
			DELETE_CONDITION_CONFIRM:"确认要删除条件节点吗？",
			DELETE_CONDITION_SUCCESS:"成功删除条件节点",
			EXISTING_CORRESPONDENCE:"该对应关系已存在",
			ADD_STEP:"新建步骤",
			LOADING_PROCESS:"正在加载步骤信息",
			REPEAT_STEP:"步骤重复",
			STEP_EXISTS:"步骤序号已存在",
			STEP_NAME_EMPTY:"步骤名称不能为空",
			NOT_FROM_START_TO_END:"连接不能从开始步骤直接指向结束步骤",
			NOT_FROM_GATE_TO_GATE:"连接不能从并行节点直接指向并行节点",
			NOT_FROM_START_TO_GATE:"连接不能从开始步骤直接指向并行节点",
			NOT_FROM_GATE_TO_END:"连接不能从并行节点直接指向结束步骤",
			NOT_TO_START:"连接不能指向开始",
			EMPTY_STEP_NAME:"请填写步骤名称",
			INVALID_OPERATION:"无效操作",
			INVALID_OPERATION_BEFORE_START:"无效操作(节点不能先于开始节点)",
			INVALID_OPERATION_AFTER_END:"无效操作(节点不能晚于结束节点)",
			INVALID_OPERATION_MORE_PARALLEL:"无效操作(不支持多重并行步骤)",
			INVALID_OPERATION_INVALID_PARALLEL:"无效操作(并行步骤内部不支持增加非并行步骤)",
			INVALID_OPERATION_MODIFY_STEP:"无效操作(只能操作未处理的步骤)",
			CLEAR_CONNECTION_CONFIRM:"确认要清除所有链接吗？"
	});