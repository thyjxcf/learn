package net.zdsoft.eis.base.data.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: CodeRuleConstant.java,v 1.10 2006/11/14 06:59:18 dongzk Exp $
 */
public final class CodeRuleUtils {

    public final static int REGION_LENGTH = 6;// 行政区划长度
    
    /*
     * 说明： 在学号规则中，流水号也存于表base_code_rule_detail中，其中ruleposition="99"，即表示位置编号
     * 排在最后面，rulenumber的值表示流水号的长度。 注意：同一个编号规则中（即同一个ruleid）只能有一个流水号。
     * 
     * 在学籍号规则中，流水号也存于表base_code_rule中，其中ruleposition="99"，即表示位置编号
     * 排在最后面，rulenumber的值表示流水号的长度。 注意：同一个编号规则中（即同一个ruleid）只能有一个流水号。
     */

    /**
     * 默认值
     */
    public final static String DEFAULT_CODE_RULE_PARAM = "fixedvalue";

    /**
     * 号码规则中的字段
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Oct 15, 2010 3:26:04 PM $
     */
    private static class CodeRuleColumn {
        private String col;
        private String name;
        private String len;// ""表示无限长或不定长

        public CodeRuleColumn(String col, String name, String len) {
            super();
            this.col = col;
            this.name = name;
            this.len = len;
        }

        public String getCol() {
            return col;
        }

        public String getName() {
            return name;
        }

        public String getLen() {
            return len;
        }

    }

    private final static List<CodeRuleColumn> codeRuleColumns;
    static {
        CodeRuleColumn[] columns = { new CodeRuleColumn(DEFAULT_CODE_RULE_PARAM, "固定代码", "0"),
                new CodeRuleColumn("schregion", "学校所在地行政区", "6"),
                new CodeRuleColumn("schcode", "学校代码", "12"),
                new CodeRuleColumn("currentacadyear", "当前学年", "9"),
                new CodeRuleColumn("enteracadyear", "入学学年", "9"),
                new CodeRuleColumn("graduateyear", "毕业学年", "9"),
                new CodeRuleColumn("section", "所属学段", "1"),
                new CodeRuleColumn("stusourcetype", "学生来源", "1"),
                new CodeRuleColumn("stuislocalsource", "是否本地生源", "1"),
                new CodeRuleColumn("stusex", "性别", "1"),
                new CodeRuleColumn("serialno", "流水号", "1"),
                new CodeRuleColumn("runschtype", "学校办别", "1"),
                new CodeRuleColumn("specialtyCode", "专业代码", "7"),
//                new CodeRuleColumn("identitycard", "身份证", "18"),
                new CodeRuleColumn("credentialcode", "证件号", "18"),
        		new CodeRuleColumn("classcode", "班级", "10"),
        		new CodeRuleColumn("classorderid", "班内编号", "3")
        };

        codeRuleColumns = Arrays.asList(columns);
    }

    /**
     * 数据类型的个数
     */
    private final static int length = codeRuleColumns.size();

//    /**
//     * 得到数据类型和类型名称的页面选择List（包含流水号）
//     * 
//     * @return List
//     */
//    public final static List<String[]> getCoderuleParamList() {
//        List<String[]> coderuleParamsList = new ArrayList<String[]>(length);
//        for (int i = 0; i < length; i++) {
//            CodeRuleColumn column = codeRuleColumns.get(i);
//            coderuleParamsList.add(new String[] { column.getCol(), column.getName() });
//        }
//
//        return coderuleParamsList;
//    }
//
//    /**
//     * 得到数据类型和类型名称的页面选择List（不包含流水号）
//     * 
//     * @return List
//     */
//    public final static List<String[]> getCoderuleParamListNoSerialno() {
//        List<String[]> coderuleParamsList = new ArrayList<String[]>(length - 1);
//        for (int i = 0; i < length; i++) {
//            CodeRuleColumn column = codeRuleColumns.get(i);
//
//            if (!(column.getCol().equals("serialno"))) {
//                coderuleParamsList.add(new String[] { column.getCol(), column.getName() });
//            }
//        }
//
//        return coderuleParamsList;
//    }

    /**
     * 得到数据类型和类型名称的页面选择List（只有流水号）
     * 
     * @return List
     */
    public final static List<String[]> getCoderuleParamListSerialno() {
        List<String[]> coderuleParamsList = new ArrayList<String[]>(1);
        for (int i = 0; i < length; i++) {
            CodeRuleColumn column = codeRuleColumns.get(i);
            if (column.getCol().equals("serialno")) {
                coderuleParamsList.add(new String[] { column.getCol(), column.getName() });
                break;
            }
        }

        return coderuleParamsList;
    }

    /**
     * 根据数据类型得到类型的名称
     * 
     * @param StucoderuleParam
     * @return String
     */
    public final static String getCoderuleParamName(String coderuleParam) {
        String name = null;
        for (int i = 0; i < length; i++) {
            CodeRuleColumn column = codeRuleColumns.get(i);
            if (coderuleParam.equals(column.getCol())) {
                name = column.getName();
                break;
            }
        }

        return name;
    }

    /**
     * 根据数据类型得到类型最大长度
     * 
     * @param coderuleParam
     * @return String
     */
    public final static String getCoderuleParamLength(String coderuleParam) {
        String len = null;
        for (int i = 0; i < length; i++) {
            CodeRuleColumn column = codeRuleColumns.get(i);
            if (coderuleParam.equals(column.getCol())) {
                len = column.getLen();
                break;
            }
        }

        return len;
    }

    /**
     * 得到数据类型最大长度的Map
     * 
     * @return Map
     */
    public final static Map<String, String> getCoderuleParamLengthMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < length; i++) {
            CodeRuleColumn column = codeRuleColumns.get(i);
            map.put(column.getCol(), column.getLen());
        }

        return map;
    }
    

    private static final int CODETYPE_UNITIVECODE = 11; // 学籍号规则
    private static final int CODETYPE_MEETEXAMCODE = 12; // 高中会考证号规则

    /**
     * 学号
     */
    public final static int CODETYPE_STUDENT_CODE = 21;
    /**
     * 毕业证号
     */
    private final static int CODETYPE_GRADUATE = 22;
    
    public final static Map<String, String> codeTypeRangeMap;
    static{
    	codeTypeRangeMap=new HashMap<String, String>();
    	codeTypeRangeMap.put(CODETYPE_UNITIVECODE+"", "global");//全局规则
    	codeTypeRangeMap.put(CODETYPE_MEETEXAMCODE+"", "global");
    	codeTypeRangeMap.put(CODETYPE_STUDENT_CODE+"", "school");//学校自定义规则
    	codeTypeRangeMap.put(CODETYPE_GRADUATE+"", "school");
    	
    }
    
}
