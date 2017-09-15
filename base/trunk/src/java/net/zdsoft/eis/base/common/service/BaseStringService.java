package net.zdsoft.eis.base.common.service;

import net.zdsoft.leadin.cache.CacheManager;

public interface BaseStringService extends CacheManager {
    String getValue(String code);
    String getValue(String code, String defaultValue);
}
