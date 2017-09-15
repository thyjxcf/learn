package net.zdsoft.eis.system.frame.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.system.frame.dao.ExceptionDataDao;
import net.zdsoft.eis.system.frame.dto.ResultPack;
import net.zdsoft.eis.system.frame.service.ExceptionDataService;
import net.zdsoft.keel.util.Validators;

public class ExceptionDataServiceImpl implements ExceptionDataService {
    private ExceptionDataDao exceptionDataDao;
    public static final char TASK_SEPARATOR = ';';

    public void setExceptionDataDao(ExceptionDataDao exceptionDataDao) {
        this.exceptionDataDao = exceptionDataDao;
    }

    public ResultPack[] saveExecuteQuery(String taskContent, int pageNum) throws IOException,
            SQLException {
        if (Validators.isEmpty(taskContent)) {
            return null;
        }

        String[] sqls;
        try {
            // 以令牌化方式处理特殊字符
            sqls = splitSql(taskContent, true);
            if (null == sqls || sqls.length == 0) {
                throw new IOException("没有解析到有效的SQL语句");
            }
            List<String> sqlList = new ArrayList<String>();
            // 去掉空行
            for (String sql : sqls) {
                if (StringUtils.isBlank(sql))
                    continue;
                sql = sql.trim();
                if (!Validators.isEmpty(sql)) {
                    if (sql.indexOf("--") == 0)
                        continue;
                    sqlList.add(sql);
                }
            }
            sqls = sqlList.toArray(new String[0]);
        } catch (IOException e) {
            throw e;
        }

        ResultPack[] packs = new ResultPack[sqls.length];
        if (pageNum < sqls.length) {
            if (sqls[pageNum].length() >= 6) {
                String tmp = sqls[pageNum].substring(0, 6);
                if ("select".equals(tmp.toLowerCase())) {
                    packs[pageNum] = exceptionDataDao.executeQuery(sqls[pageNum]);
                } else {
                    int i = exceptionDataDao.executeUpdate(sqls[pageNum]);
                    packs[pageNum] = new ResultPack();
                    packs[pageNum].setResultCode(i);
                }
            } else {
                throw new IOException("没有解析到有效的SQL语句");
            }
        }
        return packs;
    }

    private String[] splitSql(String sqlStatement, boolean slashComments) throws IOException {
        if (!sqlStatement.endsWith(";")) {
            sqlStatement += ";";
        }
        Reader r = new StringReader(sqlStatement);
        StreamTokenizer token = new StreamTokenizer(r);
        token.resetSyntax();
        token.slashStarComments(slashComments);
        token.quoteChar('\'');

        List<String> sqlList = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();

        int flag = StreamTokenizer.TT_EOF;
        while (StreamTokenizer.TT_EOF != (flag = token.nextToken())) {
            switch (flag) {
            case StreamTokenizer.TT_NUMBER:
                sb.append(token.nval);
                break;
            case StreamTokenizer.TT_WORD:
                sb.append(token.sval);
                break;
            case '\'':
                sb.append('\'').append(token.sval).append('\'');
                break;
            case TASK_SEPARATOR:
                if ((!sb.toString().trim().startsWith("--") || !slashComments)
                        && !Validators.isBlank(sb.toString().trim())) {
                    sqlList.add(sb.toString());
                }
                sb = new StringBuffer();
                break;
            default:
                sb.append((char) token.ttype);
                break;
            }
        }
        return sqlList.toArray(new String[0]);
    }

    public Integer[] updateExecute(String taskContent) throws Exception {
        String[] sqls;
        try {
            sqls = splitSql(taskContent, true);
            List<String> sqlList = new ArrayList<String>();
            for (String sql : sqls) {
                if (!Validators.isEmpty(sql)) {
                    sqlList.add(sql);
                }
            }
            sqls = sqlList.toArray(new String[0]);
        } catch (Exception e) {
            throw new Exception("SQL 语句解析异常");
        }

        Integer[] result = new Integer[sqls.length];
        for (int i = 0; i < sqls.length; i++) {
            result[i] = exceptionDataDao.executeUpdate(sqls[i]);
        }
        return result;
    }

}
