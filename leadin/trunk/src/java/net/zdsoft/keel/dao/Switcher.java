/*
 * Created on 2004-10-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package net.zdsoft.keel.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * 用于把一个集合的对象中只包含id的属性填充成有完整属性对象的工具类，使用示例如下： Switcher switcher = new Switcher();
 * for (int i = 0; i < teacherRequiredList.size(); i++) { TeacherRequired
 * teacherRequired = (TeacherRequired) teacherRequiredList .get(i);
 * switcher.add(teacherRequired.getTeacher().getId()); } Map teachers = new
 * HashMap(); if (!switcher.isEmpty()) { teachers =
 * teacherDAO.findTeachersByIds(schoolId, switcher .toArray()); } for (int i =
 * 0; i < teacherRequiredList.size(); i++) { TeacherRequired teacherRequired =
 * (TeacherRequired) teacherRequiredList .get(i); Teacher teacher = (Teacher)
 * switcher.getValue(i, teachers); if (teacher != null) {
 * teacherRequired.setTeacher(teacher); } }
 * 
 * @author liangxiao
 * @version $Revision: 1.5 $, $Date: 2007/01/11 09:15:14 $
 */
public class Switcher {

    private ArrayList<String> keys = null;
    private Object[] attachArgs = null;

    /**
     * 构造方法
     */
    public Switcher() {
        keys = new ArrayList<String>();
    }

    /**
     * 取得附加参数
     * 
     * @return
     */
    public Object[] getAttachArgs() {
        return attachArgs;
    }

    /**
     * 设置附加参数
     * 
     * @param attachArgs
     */
    public void setAttachArgs(Object[] attachArgs) {
        this.attachArgs = attachArgs;
    }

    /**
     * 判断是否为空
     * 
     * @return
     */
    public boolean isEmpty() {
        if (keys == null || keys.isEmpty()) {
            return true;
        }

        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 增加一个键
     * 
     * @param key
     */
    public void add(String key) {
        keys.add(key);
    }

    /**
     * 取出指定位置的键
     * 
     * @param index
     * @return
     */
    public String getKey(int index) {
        return (String) keys.get(index);
    }

    /**
     * 把键的集合转换成键的数组
     * 
     * @return
     */
    public String[] toArray() {
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < keys.size(); i++) {
            String entry = keys.get(i);
            if (entry != null) {
                set.add(entry);
            }
        }
        return (String[]) set.toArray(new String[0]);
    }

    /**
     * 取出指定位置的值对象
     * 
     * @param index
     * @param values
     * @return
     */
    public <V> V getValue(int index, Map<String,V> values) {
        String key = getKey(index);

        if (key == null) {
            return null;
        }

        return values.get(key);
    }

}