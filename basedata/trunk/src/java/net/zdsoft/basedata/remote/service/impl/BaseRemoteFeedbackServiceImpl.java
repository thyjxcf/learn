package net.zdsoft.basedata.remote.service.impl;

import javax.annotation.Resource;

import net.zdsoft.basedata.remote.dao.BaseRemoteFeedbackDao;
import net.zdsoft.basedata.remote.service.BaseRemoteFeedbackService;
import net.zdsoft.eis.base.common.entity.Feedback;

import org.springframework.stereotype.Service;

@Service
public class BaseRemoteFeedbackServiceImpl implements BaseRemoteFeedbackService {

    @Resource
    private BaseRemoteFeedbackDao baseRemoteFeedbackDao;
    
    @Override
    public void addFeedback(Feedback... fb) {
        baseRemoteFeedbackDao.addFeedback(fb);
    }

}
