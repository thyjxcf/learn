package net.zdsoft.leadin.dataimport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.leadin.dataimport.common.DataImportConstants;
import net.zdsoft.leadin.dataimport.entity.ImportDataJob;
import net.zdsoft.leadin.dataimport.entity.ImportDataJobDetail;
import net.zdsoft.leadin.util.UUIDGenerator;

public class ImportDataJobDaoImpl extends BasicDAO implements ImportDataJobDao {
    private static final Integer IMPORT_JOB_TYPE_CY = 2;// 城域

    private static final String SQL_INSERT_JOB = "INSERT INTO sys_import_job (id, object_name, import_depend_on, init_file, section, "
            + "file_path, is_deleted, name, status, unit_id, updatestamp, user_id, result_msg, job_start_time, job_end_time,server_type_id) "
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_DELETE_JOB_BY_IDS = "delete from sys_import_job  where id IN";
    private static final String SQL_UPDATE_RUN_TIME = "UPDATE sys_import_job set status = ?,job_run_time = ? where id = ?";
    private static final String SQL_UPDATE_JOB = "UPDATE sys_import_job set status = ?,job_end_time = ?,result_msg = ?,error_file = ? where id = ?";
    private static final String SQL_UPDATE_JOB_NO_HAND = "UPDATE sys_import_job set status = ? where (status = ? or status = ? ) AND server_type_id = ? and is_deleted = '0' and ROUND(TO_NUMBER(sysdate - job_run_time) * 24 * 60) >= ?";

    private static final String SQL_SELECT_EXISTS_JOB = "select count(1) from sys_import_job "
            + "where init_file = ? and object_name = ? and user_id = ? and name = ? and is_deleted = '0' and status IN (0,1) ";

    private static final String SQL_SELECT_JOB_BY_ID = "SELECT * FROM sys_import_job WHERE id = ? AND is_deleted = '0'";
    private static final String SQL_SELECT_JOB_BY_IDS = "SELECT * FROM sys_import_job WHERE is_deleted = '0' AND id IN";
    private static final String SQL_SELECT_JOB_BY_STATUS = "select * from sys_import_job where status = ? AND server_type_id = ? and is_deleted = '0' order by updatestamp";
    private static final String SQL_SELECT_JOB_BY_UNIT_ID_OBJECT = "SELECT * FROM sys_import_job WHERE UNIT_ID = ? AND object_name = ? AND server_type_id = ? and is_deleted = '0' order by updatestamp desc";
    private static final String SQL_SELECT_JOB_BY_JOBSTARTTIME = "select * from sys_import_job where server_type_id = "
            + IMPORT_JOB_TYPE_CY + " and status in (2,3) AND job_start_time < ? and rownum <= ? ";
    private static final String SQL_SELECT_UNDISPOSED_JOBS = "select id from sys_import_job where status = 0 and is_deleted = '0' order by updatestamp";
    
    // ==================任务明细===================
    private static final String SQL_INSERT_JOB_DETAIL = "INSERT INTO sys_import_job_detail(ID, JOB_ID, NAME, VALUE) VALUES(?,?,?,?)";
    private static final String SQL_DELETE_JOB_DETAIL_BY_IDS = "DELETE sys_import_job_detail where job_id IN";
    private static final String SQL_SELECT_JOB_DETAIL_BY_JOB_ID = "SELECT * FROM sys_import_job_detail WHERE job_id = ?";

    private ImportDataJobDetail rsToJobDetail(ResultSet rs) throws SQLException {
        ImportDataJobDetail dataJob = new ImportDataJobDetail();
        dataJob.setId(rs.getString("id"));
        dataJob.setJobId(rs.getString("job_id"));
        dataJob.setName(rs.getString("name"));
        dataJob.setValue(rs.getString("value"));
        return dataJob;
    }

    public int[] addJobDetails(List<ImportDataJobDetail> jobDetails) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (ImportDataJobDetail job : jobDetails) {
            if (job.getId() == null) {
                job.setId(UUIDGenerator.getUUID());
            }
            String[] ss = { job.getId(), job.getJobId(), job.getName(), job.getValue() };
            listOfArgs.add(ss);
        }
        int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
        return batchUpdate(SQL_INSERT_JOB_DETAIL, listOfArgs, argTypes);
    }

    public void deleteJobDetails(String[] ids) {
        updateForInSQL(SQL_DELETE_JOB_DETAIL_BY_IDS, new Object[] {}, ids);
    }

    public List<ImportDataJobDetail> findJobDetails(String jobId) {
        return query(SQL_SELECT_JOB_DETAIL_BY_JOB_ID, jobId,
                new MultiRowMapper<ImportDataJobDetail>() {
                    public ImportDataJobDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rsToJobDetail(rs);
                    }
                });
    }

    // ==================任务===================
    private ImportDataJob toEntity(ResultSet rs) throws SQLException {
        ImportDataJob job = new ImportDataJob();
        job.setFilePath(rs.getString("file_path"));
        job.setId(rs.getString("id"));
        job.setIsDeleted(rs.getString("is_deleted"));
        job.setName(rs.getString("name"));
        job.setStatus(rs.getInt("status"));
        job.setUnitId(rs.getString("unit_id"));
        job.setUpdatestamp(rs.getTimestamp("updatestamp"));
        job.setUserId(rs.getString("user_id"));
        job.setSection(rs.getString("section"));
        job.setInitFile(rs.getString("init_file"));
        job.setImportDependOn(rs.getString("import_depend_on"));
        job.setObjectName(rs.getString("object_name"));
        job.setResultMsg(rs.getString("result_msg"));
        job.setJobStartTime(rs.getTimestamp("job_start_time"));
        job.setJobRunTime(rs.getTimestamp("job_run_time"));
        job.setJobEndTime(rs.getTimestamp("job_end_time"));
        job.setErrorFile(rs.getString("error_file"));

        return job;
    }

    public int insertImportJob(ImportDataJob job) {
        return update(SQL_INSERT_JOB, new Object[] { job.getId(), job.getObjectName(),
                job.getImportDependOn(), job.getInitFile(), job.getSection(), job.getFilePath(),
                job.getIsDeleted(), job.getName(), job.getStatus(), job.getUnitId(),
                job.getUpdatestamp(), job.getUserId(), job.getResultMsg(), job.getJobStartTime(),
                job.getJobEndTime(), IMPORT_JOB_TYPE_CY });
    }

    public void deleteImportJobs(String[] ids) {
        updateForInSQL(SQL_DELETE_JOB_BY_IDS, new Object[] {}, ids);
    }

    public void updateJobFinished(ImportDataJob job) {
    	String msg = job.getResultMsg();
    	if (net.zdsoft.keel.util.StringUtils.getRealLength(msg) > 500) {
	      msg = msg.substring(0, 250);
	   }
        update(SQL_UPDATE_JOB, new Object[] { job.getStatus(), job.getJobEndTime(),
        		msg, job.getErrorFile(), job.getId() });
    }

    public void updateJobNoHand(int resetTime) {
		update(SQL_UPDATE_JOB_NO_HAND, new Object[] {
				DataImportConstants.TASK_STATUS_NO_HAND,
				DataImportConstants.TASK_STATUS_PRE_HAND,
				DataImportConstants.TASK_STATUS_IN_HAND, IMPORT_JOB_TYPE_CY,
				Integer.valueOf(resetTime) });
    }

    public void updateRunTime(int status, Date jobRunTime, String id) {
        update(SQL_UPDATE_RUN_TIME, new Object[] { status, jobRunTime, id });
    }

    public boolean isExistsJob(String initFile, String objectName, String userId,
            String uploadFileName) {
        int rtn = queryForInt(SQL_SELECT_EXISTS_JOB, new Object[] { initFile, objectName, userId,
                uploadFileName });
        if (rtn == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ImportDataJob findImportJob(String jobId) {
        return (ImportDataJob) query(SQL_SELECT_JOB_BY_ID, jobId,
                new SingleRowMapper<ImportDataJob>() {

                    public ImportDataJob mapRow(ResultSet rs) throws SQLException {
                        return toEntity(rs);
                    }
                });
    }

    public List<ImportDataJob> findImportJobs(String[] jobIds) {
        return queryForInSQL(SQL_SELECT_JOB_BY_IDS, new Object[] {}, jobIds,
                new MultiRowMapper<ImportDataJob>() {
                    public ImportDataJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return toEntity(rs);
                    }
                });
    }

    public List<ImportDataJob> findImportJobs(String unitId, String objectName) {
        return query(SQL_SELECT_JOB_BY_UNIT_ID_OBJECT, new Object[] { unitId, objectName,
                IMPORT_JOB_TYPE_CY }, new MultiRowMapper<ImportDataJob>() {

            public ImportDataJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                return toEntity(rs);
            }
        });
    }

    public Map<String, Integer> getJobPosMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> list = query(SQL_SELECT_UNDISPOSED_JOBS, new MultiRowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("id");
            }
        });
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i);
            map.put(id, i + 1);
        }
        return map;
    }

    public List<ImportDataJob> findNextNoHandJobs(int n) {
        List<ImportDataJob> jobList = new ArrayList<ImportDataJob>();

        List<ImportDataJob> list = query(SQL_SELECT_JOB_BY_STATUS, new Object[] { 0,
                IMPORT_JOB_TYPE_CY }, new MultiRowMapper<ImportDataJob>() {
            public ImportDataJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                return toEntity(rs);
            }
        });

        // 一个单位在一次并发中只执行一个任务������
        Set<String> unitIdSet = new HashSet<String>();
        for (ImportDataJob job : list) {
            if (jobList.size() >= n)
                break;

            if (unitIdSet.contains(job.getUnitId()))
                continue;

            unitIdSet.add(job.getUnitId());
            jobList.add(job);
        }

        return jobList;
    }

    public List<ImportDataJob> findImportJobs(int status) {
        return query(SQL_SELECT_JOB_BY_STATUS, new Object[] { status, IMPORT_JOB_TYPE_CY },
                new MultiRowMapper<ImportDataJob>() {
                    public ImportDataJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return toEntity(rs);
                    }
                });
    }

    public List<ImportDataJob> findImportJobs(Date jobStartTime, int num) {
        return query(SQL_SELECT_JOB_BY_JOBSTARTTIME, new Object[] { jobStartTime,Integer.valueOf(num) },
                new MultiRowMapper<ImportDataJob>() {
                    public ImportDataJob mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return toEntity(rs);
                    }
                });
    }

}
