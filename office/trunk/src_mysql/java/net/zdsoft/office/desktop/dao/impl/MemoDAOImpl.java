package net.zdsoft.office.desktop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SQLCreater;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.office.desktop.dao.MemoDAO;
import net.zdsoft.office.desktop.entity.Memo;
import net.zdsoft.office.util.EntityUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-3-13 下午01:28:36 $
 */
@SuppressWarnings("unchecked")
public class MemoDAOImpl extends BasicDAO implements MemoDAO {

    private static final String SQL_INSERT_MEMO = "INSERT INTO office_memo"
            + "(content,creation_time,id,user_id,is_sms_alarm,sms_alarm_time)VALUES(?,?,?,?,?,?)";

    private static final String SQL_FIND_MEMOS_CPUNT = "select count(1) count,to_char( creation_time,'yyyy-mm-dd') datestr from OFFICE_MEMO ";
    private static final String SQL_FIND_MEMOS_CPUNT_IN_DATE = "select count(*) count from OFFICE_MEMO ";

    private static final String SQL_DELETE_MEMO_BY_IDS = "DELETE FROM office_memo om WHERE om.id IN";

    private static final String SQL_FIND_MEMO_BY_ID = "SELECT * FROM office_memo om WHERE om.id=?";

    private static final String SQL_FIND_MEMO_BY_USERID = "SELECT * FROM office_memo om"
            + " WHERE om.user_id=? ORDER BY creation_time DESC";

    private static final String SQL_UPDATE_MEMO = "UPDATE office_memo SET content=?, "
            + "creation_time=?,is_sms_alarm=?,sms_alarm_time=? WHERE id=?";

    private static final String SQL_FIND_CURRENT_ALARM_MEMO = "select * from office_memo where version=?";

    private static final String SQL_UPDATE_MEMO_VERSION = "update office_memo set version=? where "
            + "version=? and is_sms_alarm = ? and sms_alarm_time <= ?";

    private static final String SQL_FIND_MEMO = "SELECT * FROM office_memo om";
    
    private static final String SQL_FIND_MEMO_LIST_BY_USERID ="select * from OFFICE_MEMO where user_id=? and creation_time-str_to_date(date_format(sysdate, '%Y-%m-%d'), '%Y-%m-%d')>=0 order by creation_time";

    @Override
    public void deleteMemos(String[] ids) {
        if (Validators.isEmpty(ids)) {
            return;
        }
        this.updateForInSQL(SQL_DELETE_MEMO_BY_IDS, null, ids);
    }

    @Override
    public void updateMemo(Memo memo) {
        if (memo.getIsSmsAlarm() == 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(memo.getSmsAlarmTime());
            c.add(Calendar.SECOND, 1);
            memo.setSmsAlarmTime(c.getTime());
        }
        this.update(
                SQL_UPDATE_MEMO,
                new Object[] { memo.getContent(), memo.getTime(), memo.getIsSmsAlarm(), memo.getSmsAlarmTime(),
                        memo.getId() });
    }

    @Override
    public Memo findMemo(String id) {
        return (Memo) query(SQL_FIND_MEMO_BY_ID, new Object[] { id }, new MemoSingleRowMapper());
    }

    @Override
    public List<Memo> findMemos(String userId, Pagination page) {
    	if(page!=null){
    		return query(SQL_FIND_MEMO_BY_USERID, new Object[] { userId }, new MemoMultiRowMapper(), page);
    	}else{
    		return query(SQL_FIND_MEMO_BY_USERID, new Object[] { userId }, new MemoMultiRowMapper());

    	}
    }

    @Override
    public List<Memo> findMemosByTime(String userId, Date startDate, Date endDate, Pagination page, boolean isDesc) {
        SQLCreater sql = new SQLCreater(SQL_FIND_MEMO, false);
        sql.and("om.user_id=?", userId, !Validators.isBlank(userId));
        sql.and("om.creation_time<=?", endDate, null != endDate);
        sql.and("om.creation_time>=?", startDate, null != startDate);
        // sql.orderByDesc("om.creation_time");
        sql.orderBy("om.creation_time", isDesc);

        if (null != page) {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper(), page);
        }
        else {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper());
        }
    }

    @Override
    public void insertMemo(Memo memo) {
        if (StringUtils.isBlank(memo.getId())) {
            memo.setId(EntityUtils.generateUUID());
        }
        if (memo.getIsSmsAlarm() == 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(memo.getSmsAlarmTime());
            c.add(Calendar.SECOND, 1);
            memo.setSmsAlarmTime(c.getTime());
        }
        update(SQL_INSERT_MEMO,
                new Object[] { memo.getContent(), memo.getTime(), memo.getId(), memo.getUserId(), memo.getIsSmsAlarm(),
                        memo.getSmsAlarmTime() }, new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.CHAR, Types.CHAR,
                        Types.INTEGER, Types.TIMESTAMP });
    }

    @Override
    public void insertMemos(Memo... memos) {
        if (memos == null || memos.length == 0) {
            return;
        }
        for (Memo memo : memos) {
            if (StringUtils.isBlank(memo.getId())) {
                memo.setId(EntityUtils.generateUUID());
            }
        }
        List<Object[]> argsList = new ArrayList<Object[]>();
        for (Memo memo : memos) {
            Object[] args = new Object[] { memo.getContent(), memo.getTime(), memo.getId(), memo.getUserId(),
                    memo.getIsSmsAlarm(), memo.getSmsAlarmTime() };
            argsList.add(args);
        }

        batchUpdate(SQL_INSERT_MEMO, argsList, new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.CHAR, Types.CHAR,
                Types.INTEGER, Types.TIMESTAMP });
    }

    private static class MemoSingleRowMapper implements SingleRowMapper {
        @Override
        public Object mapRow(ResultSet rs) throws SQLException {
            return new MemoMultiRowMapper().mapRow(rs, 1);
        }
    }

    private static class MemoMultiRowMapper implements MultiRowMapper {
        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Memo memo = new Memo();
            memo.setContent(rs.getString("content"));
            memo.setTime(rs.getTimestamp("creation_time"));
            memo.setId(rs.getString("id"));
            memo.setUserId(rs.getString("user_id"));
            memo.setIsSmsAlarm(rs.getInt("is_sms_alarm"));
            memo.setSmsAlarmTime(rs.getTimestamp("sms_alarm_time"));

            return memo;
        }
    }

    @Override
    public List<Memo> findMemosByDayDesc(String userId, String[] dateStrs, Pagination page) {

        SQLCreater sql = new SQLCreater(SQL_FIND_MEMO, false);
        sql.andIn("to_char(creation_time,'yyyy-mm-dd')", dateStrs, Types.VARCHAR, true);
        sql.and("user_id= ?", userId, Types.VARCHAR, true);
        sql.orderBy("creation_time", true);
        if (null != page) {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper(), page);
        }
        else {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper());
        }
    }

    @Override
    public List<Memo> findMemosByDayAsc(String userId, String[] dateStrs, Pagination page) {

        SQLCreater sql = new SQLCreater(SQL_FIND_MEMO, false);
        sql.andIn("to_char(creation_time,'yyyy-mm-dd')", dateStrs, Types.VARCHAR, true);
        sql.and("user_id= ?", userId, Types.VARCHAR, true);
        sql.orderBy("creation_time", false);
        if (null != page) {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper(), page);
        }
        else {
            return query(sql.getSQL(), sql.getArgs(), new MemoMultiRowMapper());
        }

    }

    @Override
    public Map<String, Integer> findMenosCount(String userId, String[] dateStrs) {
        SQLCreater sql = new SQLCreater(SQL_FIND_MEMOS_CPUNT, false);
        sql.andIn("to_char(creation_time,'yyyy-mm-dd')", dateStrs, Types.VARCHAR, true);
        sql.and("user_id= ?", userId, Types.VARCHAR, true);
        String sqlStr = sql.getSQL() + " group by to_char( creation_time,'yyyy-mm-dd')";
        return queryForMap(sqlStr, sql.getArgs(), new MapRowMapper() {
            @Override
            public Integer mapRowValue(ResultSet arg0, int arg1) throws SQLException {
                return arg0.getInt("count");
            }

            @Override
            public String mapRowKey(ResultSet arg0, int arg1) throws SQLException {
                return arg0.getString("datestr");
            }
        });
    }

    @Override
    public Map<String, Integer> findMenosCount(String userId, Date startDate, Date endDate) {
        SQLCreater sql = new SQLCreater(SQL_FIND_MEMOS_CPUNT, false);
        sql.and("user_id=?", userId, !Validators.isBlank(userId));
        sql.and("creation_time<=?", endDate, true);
        sql.and("creation_time>=?", startDate, true);
        String sqlStr = sql.getSQL() + " group by to_char( creation_time,'yyyy-mm-dd')";

        return queryForMap(sqlStr, sql.getArgs(), new MapRowMapper() {
            @Override
            public Integer mapRowValue(ResultSet arg0, int arg1) throws SQLException {
                return arg0.getInt("count");
            }

            @Override
            public String mapRowKey(ResultSet arg0, int arg1) throws SQLException {
                return arg0.getString("datestr");
            }
        });
    }

    @Override
    public int findMemoCount(String userId, Date date) {
        SQLCreater sql = new SQLCreater(SQL_FIND_MEMOS_CPUNT_IN_DATE, false);
        sql.and("user_id=?", userId, true);
        sql.and("creation_time<=?", DateUtils.getEndDate(date), true);
        sql.and("creation_time>=?", DateUtils.getStartDate(date), true);
        return queryForInt(sql.getSQL(), sql.getArgs());
    }
    
    @Override
    public int findTotalMemoCountByDate(String userId, Date date) {
        SQLCreater sql = new SQLCreater(SQL_FIND_MEMOS_CPUNT_IN_DATE, false);
        sql.and("user_id=?", userId, true);
        sql.and("creation_time>=?", DateUtils.getStartDate(date), true);
        return queryForInt(sql.getSQL(), sql.getArgs());
    }

    public List<Memo> getMemoListByUserId(String userId){
    	return query(SQL_FIND_MEMO_LIST_BY_USERID, userId, new MemoMultiRowMapper());
    }
}
