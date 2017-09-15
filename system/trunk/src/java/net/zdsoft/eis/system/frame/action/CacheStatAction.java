/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.frame.action;

import net.zdsoft.eis.frame.action.BaseAction;

import org.hibernate.SessionFactory;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;

public class CacheStatAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;
    private String queryName;
    private String entityName;
    private String collectionName;
    private String secondLevelCacheName;
    
    private String fullStatistics;//总体情况
    private String queryStatistics;
    private String entityStatistics;
    private String collectionStatistics;
    private String secondLevelCacheStatistics;
    
    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() throws Exception {
        Statistics stats = sessionFactory.getStatistics();
        String[] array = stats.getSecondLevelCacheRegionNames();
        fullStatistics=stats.toString();
        for(int i=0;i<array.length;i++){
            fullStatistics=array[i]+"<br/>"+fullStatistics;
        }
        
        if(queryName!=null && queryName.trim().length()>0){
            QueryStatistics queryStats = stats.getQueryStatistics(queryName);
            queryStatistics = queryStats.toString();
        }
        
        if(entityName!=null && entityName.trim().length()>0){
            EntityStatistics entityStats = stats.getEntityStatistics(entityName);
            entityStatistics = entityStats.toString();
        }
        
        if(collectionName!=null && collectionName.trim().length()>0){
            CollectionStatistics collectionStats = stats.getCollectionStatistics(collectionName);
            collectionStatistics = collectionStats.toString();
        }
        
        if(secondLevelCacheName!=null && secondLevelCacheName.trim().length()>0){
            SecondLevelCacheStatistics cacheStats = stats.getSecondLevelCacheStatistics(secondLevelCacheName);
            if(cacheStats!=null){
                secondLevelCacheStatistics = cacheStats.toString();
            }
        }
        
        return SUCCESS;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the collectionName
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * @param collectionName the collectionName to set
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the queryName
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * @param queryName the queryName to set
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * @return the secondLevelCacheName
     */
    public String getSecondLevelCacheName() {
        return secondLevelCacheName;
    }

    /**
     * @param secondLevelCacheName the secondLevelCacheName to set
     */
    public void setSecondLevelCacheName(String secondLevelCacheName) {
        this.secondLevelCacheName = secondLevelCacheName;
    }

    /**
     * @return the fullStatistics
     */
    public String getFullStatistics() {
        return fullStatistics;
    }

    /**
     * @return the queryStatistics
     */
    public String getQueryStatistics() {
        return queryStatistics;
    }

    /**
     * @return the collectionStatistics
     */
    public String getCollectionStatistics() {
        return collectionStatistics;
    }

    /**
     * @return the entityStatistics
     */
    public String getEntityStatistics() {
        return entityStatistics;
    }

    /**
     * @return the secondLevelCacheStatistics
     */
    public String getSecondLevelCacheStatistics() {
        return secondLevelCacheStatistics;
    }
    

}
