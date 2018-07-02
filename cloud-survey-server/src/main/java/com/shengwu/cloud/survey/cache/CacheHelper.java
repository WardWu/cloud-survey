package com.shengwu.cloud.survey.cache;

import com.shengwu.cloud.survey.common.ConstantType;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


/**
 * Ehcache 缓存。
 * Created by shengwu on 2017/8/25.
 */
@Repository
public class CacheHelper implements Serializable {

    @Autowired
    EhCacheCacheManager ehCacheCacheManager;

    /**
     * 保存key-value 缓存。
     *
     * @param key   String
     * @param value Object
     */

    public void setCache(String key, Object value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            try {
                Cache cache = ehCacheCacheManager.getCacheManager().getCache(ConstantType.USER_CACHE);
                Element element = new Element(key, value);
                cache.put(element);
            } catch (Exception e) {
                System.out.println("putCache.error");
            }
        }
    }

    /**
     * 获取缓存。
     *
     * @param key String
     * @return value String
     */
    public String getCache(String key)  {
        String value = null;
        if (StringUtils.isNotBlank(key)) {
            Cache cache = ehCacheCacheManager.getCacheManager().getCache(ConstantType.USER_CACHE);
            try {
                Element element = cache.getQuiet(key);
                if (element != null && element.getObjectValue() != null) {
                    value = (String) element.getObjectValue();
                }
            } catch (ClassCastException e) {
                System.out.println("getCache.error");
            }
        }
        return value;
    }

    /**
     * 清空所有缓存数据。
     */
    public void removeAllCaches(String cacheName) {
        ehCacheCacheManager.getCacheManager().getCache(cacheName).removeAll();
    }


    /**
     * 清空指定缓存。
     *
     * @param cacheName 缓存名称
     * @param key       缓存key String
     */
    public void removeCache(String cacheName, String key) {
        try {
            ehCacheCacheManager.getCacheManager().getCache(cacheName).remove(key);
        } catch (ClassCastException e) {
            System.out.println("removeCache.error");
        }
    }
}
