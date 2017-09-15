/* 
 * @(#)XLSFileUtils.java    Created on 2007-3-21
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: XLSFileUtils.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.bag.HashBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.ObjectUtils;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;

/**
 * Microsoft Excel工作簿格式文件工具类. 包含了解析文件获取结果的一些方法.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public class XLSFileUtils {

    private static final Logger log = LoggerFactory.getLogger(XLSFileUtils.class);

    /**
     * 获取要导入的字段的id(即值对象的属性名称).
     * 
     * @param fieldIds
     *            要导入的字段id数组
     * @param 所有字段对象数组
     * @return 合法的字段id数组以及必须导入的字段id的一个数组
     */
    public static String[] getValidFieldIds(String[] fieldIds,
            XLSFileField[] fields) {
        if (Validators.isEmpty(fields)) {
            return new String[0];
        }

        Set<String> propertySet = new LinkedHashSet<String>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isRequired()) {
                propertySet.add(fields[i].getId());
            }
        }

        if (!Validators.isEmpty(fieldIds)) {
            for (int i = 0; i < fieldIds.length; i++) {
                for (int j = 0; j < fields.length; j++) {
                    if (fields[j].getId().equals(fieldIds[i])) {
                        propertySet.add(fields[j].getId());
                        break;
                    }
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Import property names: " + propertySet);
        }

        return (String[]) propertySet.toArray(new String[0]);
    }

    /**
     * 根据提供的XLS文件字段id和XLS解析模板来获取有效的字段id以及模板中属性为必须的字段id.
     * 
     * @param template
     *            解析模板对象
     * @param fieldIds
     *            字段id数组
     * @return 有效的字段id和模板中属性为必须的字段id.
     */
    public static String[] getValidFieldIds(XLSFileTemplate template,
            String... fieldIds) {
        if (template == null || template.getAllFields().isEmpty()) {
            return new String[0];
        }

        Set<String> allFieldIds = new HashSet<String>();
        Set<String> validFieldIds = new LinkedHashSet<String>();
        Collection<XLSFileField> fields = template.getAllFields();
        for (XLSFileField field : fields) {
            allFieldIds.add(field.getId());
            if (field.isRequired()) {
                validFieldIds.add(field.getId());
            }
        }

        if (fieldIds != null) {
            for (String fieldId : fieldIds) {
                if (allFieldIds.contains(fieldId)) {
                    validFieldIds.add(fieldId);
                }
            }
        }

        return (String[]) validFieldIds
                .toArray(new String[validFieldIds.size()]);
    }

    public static String[] getValidFieldNames(XLSFileField[] fields,
            String... fieldIds) {
        List<String> nameList = new ArrayList<String>();
        String[] validFieldIds = getValidFieldIds(fieldIds, fields);

        if (validFieldIds != null || validFieldIds.length != 0) {
            List<String> idList = Arrays.asList(validFieldIds);
            for (int i = 0; i < fields.length; i++) {
                XLSFileField field = fields[i];
                if (idList.contains(field.getId())) {
                    nameList.add(field.getName());
                }
            }
        }

        return nameList.toArray(new String[nameList.size()]);
    }

    /**
     * 根据提供的XLS文件字段id和XLS解析模板来获取有效的字段名称以及模板中属性为必须的字段名称.
     * 
     * @param template
     *            解析模板对象
     * @param fieldIds
     *            字段id数组
     * @return 有效的字段名称和模板中属性为必须的字段名称.
     */
    public static String[] getValidFieldNames(XLSFileTemplate template,
            String... fieldIds) {
        return (String[]) getValidFieldNameSet(template, fieldIds).toArray(
                new String[0]);
    }

    /**
     * 解析XLS文件, 并返回解析结果对象.
     * 
     * @param workbook
     *            workbook对象
     * @param fieldIds
     *            要导入的字段id数组
     * @param fields
     *            所有可以导入的字段对象数组
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当导入文件中的字段名称非法时, 抛出异常.
     */
    public static ParseResult parseXLSFile(Workbook workbook,
            String[] fieldIds, XLSFileField[] fields) throws ParseException {
        ParseResult result = new ParseResult();
        if (workbook == null || fields == null) {
            return result;
        }

        Set<String> importFieldNameSet = new LinkedHashSet<String>(); // 要导入的字段名称
        Set<String> allFieldNameSet = new LinkedHashSet<String>(); // 所有的字段名称
        // key: fieldName; value: field
        Map<String, XLSFileField> allFieldName2FieldMap = new HashMap<String, XLSFileField>();

        // 设置以上三个变量的值
        fieldInit(fieldIds, fields, importFieldNameSet, allFieldNameSet,
                allFieldName2FieldMap);

        if (log.isDebugEnabled()) {
            log.debug("importFieldNameSet: " + importFieldNameSet);
            log.debug("allFieldName2FieldMap: " + allFieldNameSet);
        }

        for (int i = 0, n = workbook.getNumberOfSheets(); i < n; i++) {
            Sheet sheet = workbook.getSheet(i); // 工作表对象
            String sheetName = sheet.getName();

            Cell[] firstRow = sheet.getRow(0); // 首行: 信息字段标题行
            if (Validators.isEmpty(firstRow)) {
                continue;
            }

            int columnNum = firstRow.length; // 字段列数
            log.debug("columnNum: " + columnNum);

            // 需导入的字段的索引和字段映射
            Map<Integer, XLSFileField> fieldColumnIndex2FieldMap = checkImportFieldNames(
                    result, importFieldNameSet, allFieldNameSet,
                    allFieldName2FieldMap, sheetName, firstRow);
            if (log.isDebugEnabled()) {
                log.debug("fieldColumnIndex2FieldMap: "
                        + fieldColumnIndex2FieldMap);
            }

            // 将工作表中的每行要导入信息以: 字段对象-字段值 的映射关系记录到map中,
            // 并将此map记录到list
            List<Map<String, Object>> fieldMapList = extractSheetRecords(
                    sheetName, sheet, columnNum, fieldColumnIndex2FieldMap,
                    result);

            if (!fieldMapList.isEmpty()) {
                // 将记录列表存放到解析结果对象中
                result.addRecordMapList(sheetName, fieldMapList);
            }
            log.debug("recordMapList size: " + fieldMapList.size());
        }

        return result;
    }

    /**
     * 解析XLS文件, 并返回解析结果对象.
     * 
     * @param workbook
     *            workbook对象(Workbook)
     * @param fieldIds
     *            要导入的字段id数组(页面传入)
     * @param fields
     *            所有可以导入的字段对象数组(与数据库对应)
     * @param clazz
     *            工作表记录对应的实体类
     * @return 包含了解析提示信息和值对象集合的解析结果对象.
     * @throws ParseException
     *             当导入文件中的字段名称非法时, 抛出异常.
     */
    public static <T> ParseResult parseXLSFile(Workbook workbook,
            String[] fieldIds, XLSFileField[] fields, Class<T> clazz)
            throws ParseException {
        ParseResult result = new ParseResult();
        if (workbook == null || fields == null) {
            return result;
        }

        Set<String> importFieldNameSet = new LinkedHashSet<String>(); // 要导入的字段名称
        Set<String> allFieldNameSet = new LinkedHashSet<String>(); // 所有的字段名称
        // key: fieldName; value: field
        Map<String, XLSFileField> allFieldName2FieldMap = new HashMap<String, XLSFileField>();

        // 设置以上三个变量的值(为以上三个变量赋值)
        fieldInit(fieldIds, fields, importFieldNameSet, allFieldNameSet,
                allFieldName2FieldMap);

        if (log.isDebugEnabled()) {
            log.debug("importFieldNameSet: " + importFieldNameSet);
            log.debug("allFieldName2FieldMap: " + allFieldNameSet);
        }

        for (int i = 0, n = workbook.getNumberOfSheets(); i < n; i++) {
            Sheet sheet = workbook.getSheet(i); // 工作表对象
            String sheetName = sheet.getName(); // 工作表名称

            Cell[] firstRow = sheet.getRow(0); // 首行: 信息字段标题行
            if (firstRow == null) {
                continue;
            }

            int columnCount = firstRow.length; // 字段列数
            log.debug("columnCount: " + columnCount);

            // 需导入的字段的索引和字段映射
            Map<Integer, XLSFileField> fieldColumnIndex2FieldMap = checkImportFieldNames(
                    result, importFieldNameSet, allFieldNameSet,
                    allFieldName2FieldMap, sheetName, firstRow);
            if (log.isDebugEnabled()) {
                log.debug("fieldColumnIndex2FieldMap: "
                        + fieldColumnIndex2FieldMap);
            }

            // 将工作表中的每行要导入信息以: 字段id-字段值 的映射关系记录到map中,
            // 并将此map记录到list
            List<Map<String, Object>> fieldMapList = extractSheetRecords(
                    sheetName, sheet, columnCount, fieldColumnIndex2FieldMap,
                    result);

            // 生成工作表的记录值对象
            List<Object> recordList = createDataObjects(clazz, fieldMapList);
            if (!recordList.isEmpty()) {
                // 将记录列表存放到解析结果对象中
                result.addRecordList(sheetName, recordList);
            }
            log.debug("recordList size: " + recordList.size());
        }

        return result;
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段名称来确定解析获取那些字段列的值.
     * 
     * @param workbook
     *            workbook对象.
     * @param template
     *            解析文件的模板, 包含了所有合法的字段名.
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名存在重名或者缺少模板中属性为必须的字段名时, 抛出此异常.
     */
    public static ParseResult parseXLSFile(Workbook workbook,
            XLSFileTemplate template) throws ParseException {
        if (workbook == null || template == null) {
            throw new IllegalArgumentException(
                    "Workbook or template can't be null!'");
        }

        // 文件中必须存在的字段名称
        Set<String> requiredFieldNames = template.getRequiredFieldNames();
        return parseXLSFile(workbook, template, requiredFieldNames,
                new ParseCallback() {
                    public void execute(ParseResult result, String sheetName,
                            List<Map<String, Object>> fieldMaps) {
                        // 将记录列表存放到解析结果对象中
                        if (!fieldMaps.isEmpty()) {
                            result.addRecordMapList(sheetName, fieldMaps);
                        }
                        log.debug("recordMapList size: " + fieldMaps.size());
                    }
                });
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段名称来确定解析获取那些字段列的值.
     * 
     * @param workbook
     *            workbook对象.
     * @param template
     *            解析文件的模板, 包含了所有合法的字段名.
     * @param clazz
     *            每行记录所对应的实体对象
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名存在重名或者缺少模板中属性为必须的字段名时, 抛出此异常.
     */
    public static <T> ParseResult parseXLSFile(Workbook workbook,
            XLSFileTemplate template, Class<T> clazz) throws ParseException {
        if (workbook == null || template == null || clazz == null) {
            throw new IllegalArgumentException(
                    "Workbook or template or clazz can't be null!'");
        }

        final Class<T> _clazz = clazz;
        // 文件中必须存在的字段名称
        Set<String> requiredFieldNames = template.getRequiredFieldNames();
        return parseXLSFile(workbook, template, requiredFieldNames,
                new ParseCallback() {
                    public void execute(ParseResult result, String sheetName,
                            List<Map<String, Object>> fieldMaps) {
                        // 生成工作表的记录值对象
                        List<Object> recordList = createDataObjects(_clazz,
                                fieldMaps);
                        // 将记录列表存放到解析结果对象中
                        if (!recordList.isEmpty()) {
                            result.addRecordList(sheetName, recordList);
                        }
                        log.debug("recordList size: " + recordList.size());
                    }
                });
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段名称来确定解析获取那些字段列的值.
     * 
     * @param workbook
     *            workbook对象.
     * @param template
     *            解析文件的模板, 包含了所有合法的字段名.
     * @param fieldIds
     *            需要获取值的字段id
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名存在重名或者缺少模板中属性为必须的字段名时, 抛出此异常.
     */
    public static ParseResult parseXLSFile(Workbook workbook,
            XLSFileTemplate template, String[] fieldIds) throws ParseException {
        if (workbook == null || template == null) {
            throw new IllegalArgumentException(
                    "Workbook or template can't be null!'");
        }

        // 文件中必须存在的字段名称
        Set<String> requiredFieldNames = getValidFieldNameSet(template,
                fieldIds);
        return parseXLSFile(workbook, template, requiredFieldNames,
                new ParseCallback() {
                    public void execute(ParseResult result, String sheetName,
                            List<Map<String, Object>> fieldMaps) {
                        // 将记录列表存放到解析结果对象中
                        if (!fieldMaps.isEmpty()) {
                            result.addRecordMapList(sheetName, fieldMaps);
                        }
                        log.debug("recordMapList size: " + fieldMaps.size());
                    }
                });
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段名称来确定解析获取那些字段列的值.
     * 
     * @param workbook
     *            workbook对象.
     * @param template
     *            解析文件的模板, 包含了所有合法的字段名.
     * @param mapper
     *            映射每行记录的对象
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名存在重名或者缺少模板中属性为必须的字段名时, 抛出此异常.
     */
    public static ParseResult parseXLSFile(Workbook workbook,
            XLSFileTemplate template, XLSFileRowMapper<?> mapper)
            throws ParseException {
        if (workbook == null || template == null) {
            throw new IllegalArgumentException(
                    "Workbook or template can't be null!'");
        }

        final XLSFileRowMapper<?> _mapper = mapper;
        // 文件中必须存在的字段名称
        Set<String> requiredFieldNames = template.getRequiredFieldNames();
        return parseXLSFile(workbook, template, requiredFieldNames,
                new ParseCallback() {
                    public void execute(ParseResult result, String sheetName,
                            List<Map<String, Object>> fieldMaps) {
                        // 生成工作表的记录值对象
                        List<Object> recordList = createDataObjects(_mapper,
                                fieldMaps);
                        // 将记录列表存放到解析结果对象中
                        if (!recordList.isEmpty()) {
                            result.addRecordList(sheetName, recordList);
                        }
                        log.debug("recordList size: " + recordList.size());
                    }
                });
    }

    /**
     * 解析XLS文件, 并返回解析结果对象.
     * 
     * @param in
     *            文件输入流
     * @param fieldIds
     *            要导入的字段id数组
     * @param fields
     *            所有可以导入的字段对象数组
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当导入文件不存在或格式不正确时, 抛出异常.
     */
    public static ParseResult parseXLSFile(InputStream in, String[] fieldIds,
            XLSFileField[] fields) throws ParseException {
        if (in == null) {
            throw new IllegalArgumentException("InputStream can't be null!");
        }

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);
        }
        catch (Exception e) {
            throw new ParseException("读取导入文件出错！您指定的导入文件不是XLS格式文件。", e);
        }
        finally {
            FileUtils.close(in); // Do not forget to close the stream!
        }

        return parseXLSFile(workbook, fieldIds, fields);
    }

    /**
     * 解析XLS文件, 并返回解析结果对象.
     * 
     * @param in
     *            文件输入流
     * @param fieldIds
     *            要导入的字段id数组(页面传入)
     * @param fields
     *            所有可以导入的字段对象数组(与数据库对应)
     * @param clazz
     *            工作表记录对应的实体类
     * @return 包含了解析提示信息和值对象集合的解析结果对象.
     * @throws ParseException
     *             当导入文件不存在或格式不正确时, 抛出异常.
     */
    public static <T> ParseResult parseXLSFile(InputStream in, String[] fieldIds,
            XLSFileField[] fields, Class<T> clazz) throws ParseException {
        if (in == null) {
            throw new IllegalArgumentException("InputStream can't be null!");
        }

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);
        }
        catch (Exception e) {
            throw new ParseException("读取导入文件出错！您指定的导入文件不是XLS格式文件。", e);
        }
        finally {
            FileUtils.close(in); // Do not forget to close the stream!
        }

        return parseXLSFile(workbook, fieldIds, fields, clazz);
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段标题来确定解析出那些字段.
     * 
     * @param in
     *            输入流
     * @param template
     *            解析文件的模板, 包含了模板文件中的所有标题字段.
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名称非法时, 抛出此异常.
     */
    public static ParseResult parseXLSFile(InputStream in,
            XLSFileTemplate template) throws ParseException {
        if (in == null || template == null) {
            throw new IllegalArgumentException(
                    "InputStream or template can't be null!");
        }

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);
        }
        catch (Exception e) {
            throw new ParseException("读取导入文件出错！您指定的导入文件不是XLS格式文件。", e);
        }
        finally {
            FileUtils.close(in); // Do not forget to close the stream!
        }

        return parseXLSFile(workbook, template);
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段标题来确定解析出那些字段.
     * 
     * @param in
     *            输入流
     * @param template
     *            解析文件的模板, 包含了模板文件中的所有标题字段.
     * @param mapper
     *            映射每行记录的对象
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名称非法时, 抛出此异常.
     */
    public static ParseResult parseXLSFile(InputStream in,
            XLSFileTemplate template, XLSFileRowMapper<?> mapper)
            throws ParseException {
        if (in == null || template == null || mapper == null) {
            throw new IllegalArgumentException(
                    "InputStream or template or mapper can't be null!");
        }

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(in);
        }
        catch (Exception e) {
            throw new ParseException("读取导入文件出错！您指定的导入文件不是XLS格式文件。", e);
        }
        finally {
            FileUtils.close(in); // Do not forget to close the stream!
        }

        return parseXLSFile(workbook, template, mapper);
    }

    /**
     * 检查导入文件的字段名称.
     * 
     * @param result
     *            parseResult
     * @param importFieldNames
     *            需导入的字段名
     * @param allFieldNames
     *            所有能导入的字段名(初始话的字段)
     * @param allFieldMap
     *            所有能导入的字段名，<String,XLSFileField>
     * @param sheetName
     *            工作表对象
     * @param fieldNameRow
     *            首行: 信息字段标题行
     * @return 返回需导入的字段的 索引-字段对象 map
     * @throws ParseException
     */
    private static Map<Integer, XLSFileField> checkImportFieldNames(
            ParseResult result, Set<String> importFieldNames,
            Set<String> allFieldNames, Map<String, XLSFileField> allFieldMap,
            String sheetName, Cell[] fieldNameRow) throws ParseException {
        // 需导入的字段的索引和字段映射
        Map<Integer, XLSFileField> fieldIndexMap = new LinkedHashMap<Integer, XLSFileField>();
        List<String> invalidFieldNames = new ArrayList<String>(); // 非法的字段名称
        Bag fieldNamesBag = new HashBag(); // 需导入的字段名称

        int columnNum = fieldNameRow.length;
        for (int j = 0; j < columnNum; j++) {
            String fieldName = getCellValue(fieldNameRow[j]);
            log.debug("fieldName: " + fieldName);
            if (Validators.isEmpty(fieldName)) {
                continue;
            }

            fieldName = fieldName.trim();
            if (allFieldNames.contains(fieldName)) {
                fieldNamesBag.add(fieldName); // 记录合法的字段名称
            }
            else {
                invalidFieldNames.add(fieldName); // 记录非法的字段名称
            }

            // 如果字段名称是要导入的字段名称之一,
            // 则记录此字段的列和该字段对象的映射关系
            if (importFieldNames.contains(fieldName)) {
                fieldIndexMap.put(j, allFieldMap.get(fieldName));
            }
        }

        // 判断要导入的字段名称是否重复
        Set<String> duplicatFieldNames = new LinkedHashSet<String>(); // 重复的字段名称
        for (Iterator<?> iter = fieldNamesBag.iterator(); iter.hasNext();) {
            String fieldName = (String) iter.next();
            if (fieldNamesBag.getCount(fieldName) > 1) {
                duplicatFieldNames.add(fieldName);
            }
        }
        if (!duplicatFieldNames.isEmpty()) {
            throw new ParseException("工作表[" + sheetName + "]中存在重复的字段列："
                    + duplicatFieldNames);
        }

        if (!importFieldNames.isEmpty()
                && importFieldNames.size() > fieldIndexMap.size()) {
            Set<String> names = new HashSet<String>();
            Collection<XLSFileField> c = fieldIndexMap.values();
            for (Iterator<XLSFileField> itr = c.iterator(); itr.hasNext();) {
                XLSFileField data = (XLSFileField) itr.next();
                names.add(data.getName());
            }
            importFieldNames.removeAll(names);

            throw new ParseException("工作表[" + sheetName + "]中缺少以下字段列："
                    + importFieldNames);
        }

        return fieldIndexMap;
    }

    // 创建数据对象
    private static <T> List<Object> createDataObjects(Class<T> clazz,
            List<Map<String, Object>> fieldMapList) {
        List<Object> dataList = new ArrayList<Object>(); // 导入数据值对象列表
        for (Map<String, Object> fieldMap : fieldMapList) {
            Object obj = ObjectUtils.createInstance(clazz);
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                ObjectUtils.setProperty(obj, name, value);
            }
            dataList.add(obj);
        }

        return dataList;
    }

    // 创建数据对象
    private static List<Object> createDataObjects(XLSFileRowMapper<?> mapper,
            List<Map<String, Object>> fieldMapList) {
        List<Object> dataList = new ArrayList<Object>(); // 导入数据值对象列表
        for (Map<String, Object> fieldMap : fieldMapList) {
            dataList.add(mapper.mapRow(fieldMap));
        }

        return dataList;
    }

    /**
     * 提取工作表中每行有效的记录, 并以(字段标识-字段值)的映射关系记录到map中, 将此map记录到list中.
     * 
     * @param sheetName
     *            工作表名
     * @param sheet
     *            工作表对象
     * @param columnCount
     *            字段列数
     * @param fieldColumn2FieldMap
     *            字段标识-字段值map
     * @param result
     *            ParseResult, 解析结果对象
     * @return 存放 字段标识-字段值 的map的list.
     */
    private static List<Map<String, Object>> extractSheetRecords(
            String sheetName, Sheet sheet, int columnCount,
            Map<Integer, XLSFileField> fieldColumn2FieldMap, ParseResult result) {
        List<Map<String, Object>> fieldMapList = new ArrayList<Map<String, Object>>();
        int rowNum = sheet.getRows();
        log.debug("rowNum: " + rowNum);

        for (int i = 1; i < rowNum; i++) {
            Cell[] row = sheet.getRow(i);
            if (Validators.isEmpty(row) || isRowEmpty(row, columnCount)) {
                continue;
            }

            boolean isRowInvalid = false; // 一行中的各列字段的值是否存在非法

            // key: field的id; value: field的值
            Map<String, Object> fieldId2FieldValueMap = new HashMap<String, Object>();
            for (Map.Entry<Integer, XLSFileField> entry : fieldColumn2FieldMap
                    .entrySet()) {
                int fieldIndex = entry.getKey();
                int currentRow = i + 1; // 当前行号
                int currentColumn = fieldIndex + 1; // 当前列号

                String fieldValue = fieldIndex < row.length ? getCellValue(row[fieldIndex])
                        : null;

                XLSFileField field = (XLSFileField) entry.getValue();

                fieldValue = Validators.isEmpty(fieldValue) ? field
                        .getDefaultValue() : fieldValue.trim();

                if (log.isDebugEnabled()) {
                    log.debug("fieldValue(" + currentRow + ", " + currentColumn
                            + "): " + fieldValue);
                }

                if (field.isRequired() && Validators.isEmpty(fieldValue)) {
                    result.addError("工作表[" + sheetName + "]的第" + currentRow
                            + "行第" + currentColumn + "列中[" + field.getName()
                            + "]字段的值不能为空");
                    isRowInvalid = true;
                    continue;
                }

                if (field.isText() && !Validators.isEmpty(fieldValue)) {
                    int minLen = field.getMinLength();
                    int maxLen = field.getMaxLength();
                    if (maxLen < minLen) {
                        throw new IllegalArgumentException(
                                "Must be true: maxLength >= minLength !");
                    }

                    if (minLen != 0
                            && StringUtils.getRealLength(fieldValue) < minLen) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值[" + fieldValue
                                + "]的长度小于最小允许值");
                        isRowInvalid = true;
                        continue;
                    }
                    if (maxLen != 0
                            && StringUtils.getRealLength(fieldValue) > maxLen) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值[" + fieldValue
                                + "]的长度大于最大允许值");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isEmail() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isEmail(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值格式有误");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isIdCard() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isIdCardNumber(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值格式有误");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isMobile() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isMobile(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值格式有误");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isNumber() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isNumber(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值必须为整数");
                        isRowInvalid = true;
                        continue;
                    }
                    else {
                        int minLen = field.getMinLength();
                        int maxLen = field.getMaxLength();
                        if (maxLen < minLen) {
                            throw new IllegalArgumentException(
                                    "Must be true: maxLength >= minLength !");
                        }

                        if (minLen != 0
                                && StringUtils.getRealLength(fieldValue) < minLen) {
                            result.addError("工作表[" + sheetName + "]的第"
                                    + currentRow + "行第" + currentColumn + "列中["
                                    + field.getName() + "]字段的值[" + fieldValue
                                    + "]的长度小于最小允许值");
                            isRowInvalid = true;
                            continue;
                        }
                        if (maxLen != 0
                                && StringUtils.getRealLength(fieldValue) > maxLen) {
                            result.addError("工作表[" + sheetName + "]的第"
                                    + currentRow + "行第" + currentColumn + "列中["
                                    + field.getName() + "]字段的值[" + fieldValue
                                    + "]的长度大于最大允许值");
                            isRowInvalid = true;
                            continue;
                        }
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isFloat() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isNumeric(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值必须为浮点数");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isPhone() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isPhoneNumber(fieldValue)) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值格式有误");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isPostcode() && !Validators.isEmpty(fieldValue)) {
                    if (!Validators.isNumber(fieldValue)
                            || fieldValue.length() != 6) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值格式有误");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
                else if (field.isDate()) {
                    if (Validators.isEmpty(fieldValue)) {
                        fieldId2FieldValueMap.put(field.getId(), null);
                    }
                    else {
                        Cell cell = row[fieldIndex];
                        CellType cellType = cell.getType();
                        if (CellType.DATE.equals(cellType)) {
                            DateCell dateCell = (DateCell) cell;

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dateCell.getDate());
                            cal.add(Calendar.MILLISECOND, -cal
                                    .get(Calendar.ZONE_OFFSET));

                            fieldId2FieldValueMap.put(field.getId(), cal
                                    .getTime());
                        }
                        else if (Validators.isDate(fieldValue)) {
                            fieldId2FieldValueMap.put(field.getId(), DateUtils
                                    .string2Date(fieldValue));
                        }
                        else {
                            result.addError("工作表[" + sheetName + "]的第"
                                    + currentRow + "行第" + currentColumn + "列中["
                                    + field.getName() + "]字段的值格式有误");
                            isRowInvalid = true;
                            continue;
                        }
                    }
                }
                else if (field.isOther() && !Validators.isEmpty(fieldValue)) {
                    // 获取名称对应的代码
                    Object value = field.getRange().get(fieldValue);
                    if (value == null) {
                        result.addError("工作表[" + sheetName + "]的第" + currentRow
                                + "行第" + currentColumn + "列中["
                                + field.getName() + "]字段的值[" + fieldValue
                                + "]不合法");
                        isRowInvalid = true;
                        continue;
                    }

                    fieldValue = value.toString();
                    fieldId2FieldValueMap.put(field.getId(), fieldValue);

                }
                else {
                    fieldId2FieldValueMap.put(field.getId(), fieldValue);
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("fieldId2FieldValueMap: " + fieldId2FieldValueMap);
            }

            // 如果一行中存在一个字段的值不合法, 则忽略此行
            if (!isRowInvalid) {
                fieldMapList.add(fieldId2FieldValueMap);
            }
        }

        return fieldMapList;
    }

    /**
     * 设置要导入的字段名称, 所有的字段名称等信息.
     * 
     * @param fieldIds
     *            需导入的字段(页面传入)
     * @param fields
     *            所有可以导入的字段对象数组(与数据库对应)
     * @param importFieldNames
     *            需导入的字段
     * @param allFieldNames
     *            所有的字段
     * @param allFieldName2FieldMap
     *            <String,Field> 所有字段对应的MAP
     */
    private static void fieldInit(String[] fieldIds, XLSFileField[] fields,
            Set<String> importFieldNames, Set<String> allFieldNames,
            Map<String, XLSFileField> allFieldName2FieldMap) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isRequired()) {
                // 默认必须导入的字段名称
                importFieldNames.add(fields[i].getName());
            }

            allFieldNames.add(fields[i].getName());
            allFieldName2FieldMap.put(fields[i].getName(), fields[i]);
        }

        if (!Validators.isEmpty(fieldIds)) {
            if (log.isDebugEnabled()) {
                log.debug("fieldIds: " + Arrays.asList(fieldIds));
            }

            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fieldIds.length; j++) {
                    if (fieldIds[j].equals(fields[i].getId())) {
                        importFieldNames.add(fields[i].getName());
                        break;
                    }
                }
            }
        }
    }

    // 获取单元格的文本值(String)
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        return Validators.isEmpty(cell.getContents()) ? "" : cell.getContents();
    }

    private static Set<String> getValidFieldNameSet(XLSFileTemplate template,
            String... fieldIds) {
        if (template == null || template.getAllFields().isEmpty()) {
            return Collections.emptySet();
        }

        Map<String, String> allFieldIds = new HashMap<String, String>();
        Set<String> validFieldNames = new LinkedHashSet<String>();
        Collection<XLSFileField> fields = template.getAllFields();
        for (XLSFileField field : fields) {
            allFieldIds.put(field.getId(), field.getName());
            if (field.isRequired()) {
                validFieldNames.add(field.getName());
            }
        }

        if (fieldIds != null) {
            for (String fieldId : fieldIds) {
                String name = allFieldIds.get(fieldId);
                if (name != null) {
                    validFieldNames.add(name);
                }
            }
        }

        return validFieldNames;
    }

    // 判断工作表中是否某行为空, 即所有单元格的值为空
    private static boolean isRowEmpty(Cell[] row, int columnNum) {
        if (row == null) {
            return true;
        }

        for (int i = 0; i < columnNum && i < row.length; i++) {
            if (!Validators.isEmpty(getCellValue(row[i]))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 解析XLS文件, 并返回解析结果对象. 根据模板中的字段名称来确定解析获取那些字段列的值.
     * 
     * @param workbook
     *            workbook对象.
     * @param template
     *            解析文件的模板, 包含了所有合法的字段名.
     * @param requiredFieldNames
     *            xls文件中必须存在的字段列名
     * @param callback
     *            回调对象
     * @return 包含了解析提示信息和解析结果对象.
     * @throws ParseException
     *             当文件中的字段名存在重名或者缺少模板中属性为必须的字段名时, 抛出此异常.
     */
    private static ParseResult parseXLSFile(Workbook workbook,
            XLSFileTemplate template, Set<String> requiredFieldNames,
            ParseCallback callback) throws ParseException {
        if (workbook == null || template == null || requiredFieldNames == null
                || callback == null) {
            throw new IllegalArgumentException("Arguments can't be null!'");
        }

        ParseResult result = new ParseResult();
        for (int i = 0, n = workbook.getNumberOfSheets(); i < n; i++) {
            Sheet sheet = workbook.getSheet(i); // 工作表对象
            String sheetName = sheet.getName(); // 工作表名称

            Cell[] firstRow = sheet.getRow(0); // 首行: 信息字段标题行
            if (firstRow == null) {
                continue;
            }

            int columnCount = firstRow.length; // 字段列数
            log.debug("columnCount: " + columnCount);

            // 需导入的字段的索引和字段映射
            Map<Integer, XLSFileField> column2FieldMap = new HashMap<Integer, XLSFileField>();
            Bag fieldNameBag = new HashBag();
            for (int j = 0; j < columnCount; j++) {
                String fieldName = getCellValue(firstRow[j]);
                log.debug("fieldName: " + fieldName);
                if (Validators.isEmpty(fieldName)) {
                    continue;
                }

                fieldName = fieldName.trim();
                if (template.getAllFieldNames().contains(fieldName)) {
                    fieldNameBag.add(fieldName); // 记录合法的字段名称
                    column2FieldMap.put(j, template.getField(fieldName));
                }
            }

            // 判断要导入的字段名称是否重复
            Set<String> validFieldNames = new HashSet<String>();
            Set<String> duplicatFieldNames = new LinkedHashSet<String>(); // 重复的字段名称
            for (Iterator<?> iter = fieldNameBag.iterator(); iter.hasNext();) {
                String fieldName = (String) iter.next();
                if (fieldNameBag.getCount(fieldName) > 1) {
                    duplicatFieldNames.add(fieldName);
                }
                else {
                    validFieldNames.add(fieldName);
                }
            }

            if (!duplicatFieldNames.isEmpty()) {
                throw new ParseException("工作表[" + sheetName + "]中存在重复的字段列："
                        + duplicatFieldNames);
            }

            if (validFieldNames.size() < requiredFieldNames.size()) {
                requiredFieldNames.removeAll(validFieldNames);
                throw new ParseException("工作表[" + sheetName + "]中缺少以下字段列："
                        + requiredFieldNames);
            }

            // 将工作表中的每行要导入信息以: 字段id-字段值 来记录, 并记录到list
            List<Map<String, Object>> fieldMaps = extractSheetRecords(
                    sheetName, sheet, columnCount, column2FieldMap, result);
            callback.execute(result, sheetName, fieldMaps);
        }

        return result;
    }

    private XLSFileUtils() {
    }

    // 解析后处理结果的回调接口
    private interface ParseCallback {
        void execute(ParseResult result, String sheetName,
                List<Map<String, Object>> fieldMaps);
    }

}
