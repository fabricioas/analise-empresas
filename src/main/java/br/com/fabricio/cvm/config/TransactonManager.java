package br.com.fabricio.cvm.config;

import java.io.File;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

public class TransactonManager {
	private CacheManager cacheManager = null;

	public void begin() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			    .with(CacheManagerBuilder.persistence(new File("/temp/ehcache/store", "data"))) 
			    .build(true);
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void end() {
		cacheManager.close();
	}
	
}
