package net.zdsoft.eis.base.common.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.cache.PublicCacheConstants;
import net.zdsoft.eis.base.common.dao.CodeRuleBuildDao;
import net.zdsoft.eis.base.common.entity.CodeParam;
import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CodeRuleBuildService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.observe.CodeRuleObserveParam;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.leadin.common.dao.MaxCodeDao;
import net.zdsoft.leadin.common.entity.MaxCodeMetadata;
import net.zdsoft.leadin.common.entity.MaxCodeParam;

/**
 * 号码规则生成 利用JAVA的“观察者设计模式”来实现当学号或学籍号规则发生变化时，生成规则缓冲池中的规则要删除
 */
public class CodeRuleBuildServiceImpl extends DefaultCacheManager implements CodeRuleBuildService {

    private final static Logger log = LoggerFactory.getLogger(CodeRuleBuildServiceImpl.class);

    private final static String CODE_RULE_KEY_PREFIX = BaseCacheConstants.EIS_CODERULE
            + "coderule_";
    private final static String CODE_SEQID_KEY_PREFIX = PublicCacheConstants.CODERULE_SEQ;

    private CodeRuleBuildDao codeRuleBuildDao;
    private MaxCodeDao maxCodeDao;
    private UnitService unitService;
    
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setMaxCodeDao(MaxCodeDao maxCodeDao) {
        this.maxCodeDao = maxCodeDao;
    }

    public void setCodeRuleBuildDao(CodeRuleBuildDao codeRuleBuildDao) {
        this.codeRuleBuildDao = codeRuleBuildDao;
    }

    /**
     * 填充号码
     * 
     * @param codeParts
     * @param maxcode
     * @return
     */
    private String fillCodes(CodeParts codeParts, int maxcode) {
        String codePrefix = codeParts.getPrefix();// 前缀
        int codenumber = codeParts.getSerialLen();// 流水号长度

        // 新的学号的数字的长度
        String code = codePrefix;

        // 流水号不足位数的在前面补上零
        int codelength = String.valueOf(maxcode).length();
        if (codelength > codenumber) {
            // 得到的数字长度大于流水号长度时，则得到""号码
            code = "";
        } else {
            // 得到的数字长度小于流水号长度时，则前面补0；等于流水号长度时，直接组合
            if (codelength < codenumber) {
                for (int i = 0; i < (codenumber - codelength); i++) {
                    code = code + "0";
                }
            }
            code = code + String.valueOf(maxcode);
        }

        return code;
    }

    public synchronized String autoCreateUnitcode(CodeParam codeparam, int codeType,
            MaxCodeMetadata metadata) {
        // 生成本学校ID对应的key，值是学号生成规则明细List
        List<CodeRuleDetail> stucoderulelist = getUnitCodeRuleLists(codeparam.getSchid(), codeType);

        // 如果规则没有维护（流水号是默认添加的，所以stucoderulelist一定不为null）
        if (null == stucoderulelist || stucoderulelist.size() == 0)
            return "";

        // 根据规则取学号的前缀部分，以及流水号的长度（若有流水号）
        CodeParts codeParts = this.getCodePrefix(stucoderulelist, codeparam);

        String codePrefix = codeParts.getPrefix();// 前缀
        int codenumber = codeParts.getSerialLen();// 流水号长度

        // 如果没有设置流水号或流水号的长度为0时
        if (codenumber == 0) {
            return codePrefix;
        }

        /* 根据学号前缀和当前的最大学号生成新的学号 */
        // 取得此前缀学号的流水号
        int maxcode = this.getUnitCodeSequenceId(codeparam.getSchid(), codeParts, codeType,
                metadata);

        return fillCodes(codeParts, maxcode);
    }

    public synchronized String autoCreateCode(CodeParam codeparam, int codeType,
            MaxCodeMetadata metadata) {
        // 生成学籍号对应的key，值是学籍号生成规则明细List
        List<CodeRuleDetail> unitivecoderule = getCodeRuleDetails(codeparam.getSection(), codeType);

        // 如果学籍号规则没有维护（流水号自动添加，所以unitivecoderule一定不为null）
        if (null == unitivecoderule)
            return "";

        // 根据规则取学籍号的前缀部分，以及流水号的长度（若有流水号）
        CodeParts codeParts = this.getCodePrefix(unitivecoderule, codeparam);

        String codePrefix = codeParts.getPrefix();// 前缀
        int codenumber = codeParts.getSerialLen();// 流水号长度

        // 如果没有设置流水号或流水号的长度为0时
        if (codenumber == 0) {
            return codePrefix;
        }

        /* 根据学籍号前缀和当前的最大学号生成新的学籍号 */
        // 取得此前缀学籍号的流水号
        int maxcode = this.getCodeSequenceId(codeparam.getSection(), codeParts, codeType, metadata);

        return fillCodes(codeParts, maxcode);
    }

    /**
     * 取得学号的流水号
     * 
     * @param schoolId 学校id
     * @param metadata
     * @param codeparam 学号前缀和流水号的数组
     * @return 新的流水号
     */
    private int getUnitCodeSequenceId(final String schoolId, final CodeParts codeParts,
            final int codeType, MaxCodeMetadata metadata) {
        // 生成取前缀流水号的key
        String key = buildKey(CODE_SEQID_KEY_PREFIX, codeType, schoolId, null);

        final MaxCodeParam param = new MaxCodeParam(codeParts.getDisposePrefix(), codeParts.getSerialLen())
                .setUnitId(schoolId);

        final String objectKey = key;

        return getSequenceId(param, objectKey, metadata, codeParts);
    }

    /**
     * 取流水号
     * 
     * @param param
     * @param objectKey
     * @param metadata
     * @param codeParts
     * @return
     */
    private int getSequenceId(final MaxCodeParam param, final String objectKey,
            final MaxCodeMetadata metadata, final CodeParts codeParts) {
        int id = incrementInCache(new CacheObjectParam<Integer>() {
            public Integer fetchObject() {
                // 取得id
                return maxCodeDao.getMaxSerialNumber(metadata, param);
            }

            public String fetchKey() {
                return objectKey + codeParts.getDisposePrefix();
            }
        }, 1);

        return id;
    }

    /**
     * 取得学籍号的流水号
     * 
     * @param num 一次生成多少个
     * @param metadata
     * @param unitivecodePrefix 学籍号的前缀
     * 
     * @return
     */
    private int getCodeSequenceId(String section, final CodeParts codeParts, final int codeType,
            MaxCodeMetadata metadata) {
        String unitId = null;
        Unit unit = unitService.getTopEdu();
        if (null != unit) {
            unitId = unit.getId();
        }
        
        String key = buildKey(CODE_SEQID_KEY_PREFIX, codeType, unitId, null);//section
        final MaxCodeParam param = new MaxCodeParam(codeParts.getDisposePrefix(), codeParts.getSerialLen());

        final String objectKey = key;

        return getSequenceId(param, objectKey, metadata, codeParts);
    }

    public String getPrefix(CodeParam codeparam, int codeType) {
        // 目前只支持全局号码规则
        List<CodeRuleDetail> codeRuleDetails = getCodeRuleDetails(codeparam.getSection(), codeType);
        CodeParts codeParts = this.getCodePrefix(codeRuleDetails, codeparam);
        return codeParts.getPrefix();
    }

    /**
     * 根据规则生成号码的前缀，并且得到流水号长度（学号和学籍号共用）
     * 
     * @param codeRuleDetails 规则明细列表
     * @param codeparam 号码生成依据参数 schregion 学校所在地行政区（BasicSchoolinfo.region） schcode
     *            学校编号（BasicSchoolinfo.code） currentacadyear 当前学年（格式为：2005-2006）
     *            enteracadyear 入学学年（格式为：2005-2006） section 所属学段（微代码：DM-RKXD）
     *            stusourcetype 学生来源（微代码：DM-XSLY，StudentInfoex.sourcetype）
     *            stuislocalsource 是否本地生源（StudentInfoex.islocalsource） stusex
     *            性别（微代码：M男，F女） classcode 班级代码（需要是9位）
     * @param global 是否是全局规则
     * @return 0.号码前缀；1.流水号位数
     */
    private CodeParts getCodePrefix(List<CodeRuleDetail> codeRuleDetails, CodeParam codeparam) {
        // 学号和学籍的固定部分,学号和学籍的流水号位数
        CodeParts codeParts = new CodeParts();
        StringBuilder prefixStr = new StringBuilder("");
        StringBuilder disposePrefixStr = new StringBuilder("");
        Iterator<CodeRuleDetail> iterator = codeRuleDetails.iterator();
        while (iterator.hasNext()) {
            CodeRuleDetail detail = iterator.next();
            String datatype = detail.getDataType();// 数据类型，如入学学年enteracadyear
            int rulenumber = detail.getRuleNumber();// 取值位比如9位中的第3位
            String constant = detail.getConstant();// 固定值
            boolean isInSerail = detail.isInSerialNumber();// 是否计入流水号，如果不计入，则值为"_"表示模糊查询

            // 固定位fixedvalue
            if (datatype.equals("fixedvalue")) {
                prefixStr.append(constant);
                disposePrefixStr.append(constant);
                continue;
            }

            // 流水号位数serialno，放到返回的数组里
            if (datatype.equals("serialno")) {
                codeParts.setSerialLen(rulenumber);// 流水号长度
                continue;
            }

            Object obj = null;
            try {
                obj = PropertyUtils.getProperty(codeparam, datatype);
            } catch (IllegalAccessException e) {
                log.equals(e);
            } catch (InvocationTargetException e) {
                log.equals(e);
            } catch (NoSuchMethodException e) {
                log.equals(e);
            }
            if (obj != null) {
                String value = (String) obj;
                if (StringUtils.isNotEmpty(value) && value.length() >= rulenumber) {
                    prefixStr.append(String.valueOf(value.charAt(rulenumber - 1)));
                    if(isInSerail){
                        disposePrefixStr.append(String.valueOf(value.charAt(rulenumber - 1)));
                    }else{
                        disposePrefixStr.append("_");// 模糊查询时使用
                    }                    
                }
            }
        }

        codeParts.setPrefix(prefixStr.toString());
        codeParts.setDisposePrefix(disposePrefixStr.toString());
        return codeParts;
    }

    /**
     * 号码组成部分
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Oct 25, 2010 1:50:54 PM $
     */
    private class CodeParts {
        private String prefix;// 真实前缀
        private String disposePrefix;// 处理过的前缀，如果某字段不计入流水号，则用“_”代替
        private int serialLen;// 流水号长度

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public int getSerialLen() {
            return serialLen;
        }

        public void setSerialLen(int serialLen) {
            this.serialLen = serialLen;
        }

        public String getDisposePrefix() {
            return disposePrefix;
        }

        public void setDisposePrefix(String disposePrefix) {
            this.disposePrefix = disposePrefix;
        }

    }

    /*
     * 观察者（Observer）接收到被观察者的更改通知后，执行此方法
     */
    public void update(Observable obs, Object source) {
        CodeRuleObserveParam param = (CodeRuleObserveParam) source;
        removeCodeRuleCache(param);
    }

    public synchronized void removeCodeMap(String unitId, int codeType) {
        CodeRuleObserveParam param = new CodeRuleObserveParam(codeType, unitId);
        removeCodeRuleCache(param);
    }

    private void removeCodeRuleCache(CodeRuleObserveParam param) {
        String unitId = param.getUnitId();
        String section = param.getSection();

        if (CodeRuleObserveParam.CODETYPE_ALL == param.getCodeType()) {
            clearCache();
        } else {
            String ruleKey = buildKey(CODE_RULE_KEY_PREFIX, param.getCodeType(), unitId, section);
            removeCoderuleMap(ruleKey);

            String seqKey = buildKey(CODE_SEQID_KEY_PREFIX, param.getCodeType(), unitId, section);
            removeCoderuleMap(seqKey);
        }
    }

    /**
     * 创建key
     * 
     * @param prefix
     * @param codeType
     * @param unitId
     * @param section
     * @return
     */
    private String buildKey(String prefix, int codeType, String unitId, String section) {
        String ruleKey = prefix + codeType;
        if (StringUtils.isNotEmpty(unitId)) {
            ruleKey += "_" + unitId;
        }
        if (StringUtils.isNotEmpty(section)) {
            ruleKey += "_" + section;
        }
        return ruleKey;
    }

    // -------------------缓存信息 begin------------------------
    private List<CodeRuleDetail> getUnitCodeRuleLists(final String schoolId, final int codeType) {
        CodeRule rule = getUnitCodeRule(schoolId, codeType);
        if(rule != null){
        	return rule.getCodeRuleDetails();
        }
        return Collections.emptyList();
    }

    public boolean isAutoBuildCode(final int codeType) {
        CodeRule codeRule = getCodeRule(null, codeType);
        if (null == codeRule) {
            return false;
        } else {
            return "1".equals(codeRule.getAutomatism()) ? true : false;
        }
    }

    public CodeRule getCodeRule(final String section, final int codeType) {
        String unitId = null;
        Unit unit = unitService.getTopEdu();
        if (null != unit) {
            unitId = unit.getId();
        }
        
        final String key = buildKey(CODE_RULE_KEY_PREFIX, codeType, unitId, null);//section

        return getEntityFromCache(new CacheEntityParam<CodeRule>() {

            public CodeRule fetchObject() {
                CodeRule rule = null;
                // 暂时不加section
                // rule = codeRuleBuildDao.getCodeRule(section, codeType);
                List<CodeRule> codeRules = codeRuleBuildDao.getCodeRules(codeType);
                if (codeRules.size() > 0) {
                    rule = codeRules.get(0);
                }
                if (null != rule) {
                    rule.setCodeRuleDetails(getCodeRuleDetails(rule.getId()));
                }
                return rule;
            }

            public String fetchKey() {
                return key;
            }
        });
    }

    public CodeRule getUnitCodeRule(final String schoolId, final int codeType) {
        final String key = buildKey(CODE_RULE_KEY_PREFIX, codeType, schoolId, null);

        return getEntityFromCache(new CacheEntityParam<CodeRule>() {

            public CodeRule fetchObject() {
                CodeRule rule = codeRuleBuildDao.getUnitCodeRule(schoolId, codeType);
                if (null != rule) {
                    rule.setCodeRuleDetails(getCodeRuleDetails(rule.getId()));
                }
                return rule;
            }

            public String fetchKey() {
                return key;
            }
        });
    }

    public List<CodeRuleDetail> getCodeRuleDetails(String ruleId) {
        return codeRuleBuildDao.getCodeRuleDetails(ruleId);
    }

    public List<CodeRuleDetail> getCodeRuleDetails(final String section, final int codeType) {
        return getCodeRule(section, codeType).getCodeRuleDetails();
    }

    /**
     * 删除类CoderuleServiceImpl中编号生成规则缓存
     * 
     * @param key 缓存的key
     */
    private void removeCoderuleMap(String key) {
        removeFromCache(key);
    }

    // -------------------缓存信息 end--------------------------
}
