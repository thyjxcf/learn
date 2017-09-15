package net.zdsoft.eis.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class ClassAction extends RemoteBaseAction {
    private BasicClassService basicClassService;
    private TeacherService teacherService;

    public String getSchoolClass() throws Exception {
        // 获取单位id
        String unitId = getParamValue("unitId");
        if (StringUtils.isBlank(unitId)) {
            sendResult(RemoteCallUtils.convertError("参数不对！").toString());
            return SUCCESS;
        }
        List<BasicClass> classes = basicClassService.getClasses(unitId);
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 50; i++) {
            map.put("id", "111");
            map.put("teacherid", "teacherid");
            map.put("viceTeacherId", "viceTeacherId");
            map.put("name", "计算机科学与技术" + i + "班");
            lists.add(map);
            map = new HashMap<String, String>();
            map.put("id", "ClassId" + i);
            map.put("teacherid", "teacherid");
            map.put("viceTeacherId", "viceTeacherId");
            map.put("name", "Class 2");
            lists.add(map);
        }
        sendResult(RemoteCallUtils.convertJsons(lists).toString());
        return SUCCESS;
    }

    public String getClassCheckCommit() throws Exception {
        JSONObject json = getJsonParam();
        String classId = json.getString("classId");
        String opeType = json.getString("opeType");
        JSONArray results = json.getJSONArray("mcodeResult");
        System.out.println("Class " + classId + " OpeType " + opeType + " result:");
        for (int i = 0; i < results.size(); i++) {
            JSONObject js = results.getJSONObject(i);
            String checkItem = js.getString("checkItem");
            String selected = "";
            if (js.containsKey("selected"))
                selected = js.getString("selected");

            System.out.println(checkItem + " = " + selected);
        }
        // sendResult(RemoteCallUtils.convertError("失败").toString());
        sendResult(RemoteCallUtils.convertJson("").toString());
        return SUCCESS;
    }

    public String getClassCheck() throws Exception {
        String className = getParamValue("className");
        JSONArray a = new JSONArray();
        for (int k = 0; k < 4; k++) {
            JSONObject o = new JSONObject();
            if (k != 3)
                o.put("teacherName", className + "班老师" + k);
            o.put("itemName", "考核 - " + k);
            o.put("itemId", "itemId" + k);
            JSONArray a2 = new JSONArray();
            int r = RandomUtils.nextInt(6) + 1;
            o.put("selected", "mcodeId" + RandomUtils.nextInt(r));
            for (int i = 0; i < r; i++) {
                JSONObject mo = new JSONObject();
                mo.put("name", "微代码" + i);
                mo.put("value", "mcodeId" + i);
                a2.add(mo);
            }
            o.put("mcode", a2);
            a.add(o);
        }
        System.out.println(a.toString());
        sendResult(RemoteCallUtils.convertJson(a.toString()).toString());
        return SUCCESS;
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

}
