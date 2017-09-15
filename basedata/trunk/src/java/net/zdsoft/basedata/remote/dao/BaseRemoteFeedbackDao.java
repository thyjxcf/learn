package net.zdsoft.basedata.remote.dao;

import net.zdsoft.eis.base.common.entity.Feedback;

public interface BaseRemoteFeedbackDao {
    void addFeedback(Feedback... fb);
}
