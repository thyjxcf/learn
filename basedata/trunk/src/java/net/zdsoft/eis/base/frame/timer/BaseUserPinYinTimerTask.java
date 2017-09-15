package net.zdsoft.eis.base.frame.timer;

import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.util.PinYinUtil;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BaseUserPinYinTimerTask extends QuartzJobBean {
 Logger log = LoggerFactory.getLogger(BaseUserPinYinTimerTask.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		UserService userService = (UserService) ContainerManager
				.getComponent("userService");
		BaseUserService baseUserService = (BaseUserService) ContainerManager
				.getComponent("baseUserService");

		/**
		 * 为所有用户都设置拼音（包括简拼，全拼，混拼，如果这些总长度超过2000，
		 * 那么只设置一种全拼和简拼，姓名也包括在内，方便使用）
		 */
		log.info("It's time to set PinYin Begin----------");
		System.out.println("---------开始转换拼音---------------");
		List<User> userList = userService.getUsersWithOutPinYin();
		if(CollectionUtils.isNotEmpty(userList)){
			for(User user:userList){
				String pinyin = user.getRealname()+","+PinYinUtil.getPinYinSet(user.getRealname());
				if(StringUtils.length(pinyin)<=2000){
					user.setPinyinAll(pinyin);
				}else{
					String[] pinyins = PinYinUtil.getPinYin(user.getRealname());
					//只存一种全拼简拼
					user.setPinyinAll(user.getRealname()+","+pinyins[0]+","+pinyins[1]);
				}
			}
			baseUserService.updateUsersPinYin(userList);
		}
		System.out.println("---------拼音转换结束---------------");
		log.info("-----------set PinYin end.");
	}

}
