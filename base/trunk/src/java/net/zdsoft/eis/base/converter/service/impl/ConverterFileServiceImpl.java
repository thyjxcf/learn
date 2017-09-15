package net.zdsoft.eis.base.converter.service.impl;import java.io.File;import java.util.Collections;import java.util.Date;import java.util.HashMap;import java.util.List;import java.util.Map;import net.zdsoft.eis.base.common.service.StorageDirService;import net.zdsoft.eis.base.converter.dao.ConverterFileDao;import net.zdsoft.eis.base.converter.entity.ConverterFile;import net.zdsoft.eis.base.converter.service.ConverterFileService;import net.zdsoft.keel.util.Pagination;import org.apache.commons.lang.StringUtils;public class ConverterFileServiceImpl implements ConverterFileService {	private Map<String, ConverterFileDao> converterFileDaoMap = new HashMap<String, ConverterFileDao>();	private StorageDirService storageDirService;	public void setConverterFileDaoMap(Map<String, ConverterFileDao> converterFileDaoMap) {		this.converterFileDaoMap.putAll(converterFileDaoMap);	}		public void register(String name, ConverterFileDao entity) {		this.converterFileDaoMap.put(name, entity);	}		public void setStorageDirService(StorageDirService storageDirService) {		this.storageDirService = storageDirService;	}	@Override	public void updateJobInHand2NoHand(int resetTime) {		for (ConverterFileDao converterFileDao : converterFileDaoMap.values()) {			converterFileDao.updateJobInHand2NoHand(resetTime);		}	}	@Override	public Integer updateJobFinished(ConverterFile job) {		ConverterFileDao converterFileDao = converterFileDaoMap.get(job.getProperties().getTableName());		return converterFileDao.updateJobFinished(job);	}	@Override	public Integer updateRunTime(int status, Date jobRunTime, ConverterFile job) {		ConverterFileDao converterFileDao = converterFileDaoMap.get(job.getProperties().getTableName());		return converterFileDao.updateRunTime(status, jobRunTime, job.getId());	}	@SuppressWarnings("unchecked")	@Override	public List<ConverterFile> findNextNoHandJobs(Pagination page, String[] extNames, Integer status) {		for (ConverterFileDao converterFileDao : converterFileDaoMap.values()) {			List<ConverterFile> list = converterFileDao.findNextNoHandJobs(page, extNames, status);			if (list.size() > 0) {				for (ConverterFile file : list) {										String dirId = file.getDirId();					if (StringUtils.isNotBlank(dirId)) {						String dirPath = storageDirService.getDir(dirId);						file.setFilePath(dirPath + File.separator + file.getFilePath());					}				}				return list;			}		}		return Collections.EMPTY_LIST;	}}