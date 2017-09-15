package net.zdsoft.basedata.remote.action;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.basedata.remote.service.BaseRemoteFeedbackService;
import net.zdsoft.eis.base.common.entity.Feedback;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.keel.util.ServletUtils;

import org.apache.commons.lang3.StringUtils;

public class RemoteFeedbackAction extends RemoteBaseAction {

    @Resource
    private BaseRemoteFeedbackService baseRemoteFeedbackService;

    public String addFeedback() {
        JSONObject json = new JSONObject();
        String feedbacksJson = getParamValue("feedbacks");
        if (StringUtils.isBlank(feedbacksJson)) {
            return printError(json);
        }
        JSONArray jsonArray = JSONArray.fromObject(feedbacksJson);
        Feedback[] feedbacks = (Feedback[]) JSONArray.toArray(jsonArray, Feedback.class);
        baseRemoteFeedbackService.addFeedback(feedbacks);
        return printSuccess(json);
    }
    
    public String validatecheck() {
        try {
            ServletUtils.print(getResponse(), "1");
        }
        catch (IOException e) {
        }
        return NONE;
    }
}
