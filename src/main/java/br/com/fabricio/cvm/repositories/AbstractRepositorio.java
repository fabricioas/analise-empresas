package br.com.fabricio.cvm.repositories;

import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import br.com.fabricio.cvm.config.TransactonManager;

public abstract class AbstractRepositorio<T> {
	private Cache<String, T> cache;

	public AbstractRepositorio(TransactonManager tm, String cacheName, Integer sizeMB) {
		super();
		cache = tm.getCacheManager().createCache(cacheName,
				CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, getClassRepos(),
						ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES)
								.offheap(1, MemoryUnit.MB).disk(sizeMB, MemoryUnit.MB, true)));
	}

	protected abstract Class<T> getClassRepos();

	public Cache<String, T> getCache() {
		return cache;
	}

	public void setCache(Cache<String, T> cache) {
		this.cache = cache;
	}

	public T getById(String id) {
		return cache.get(id);
	}

	public abstract void save(T value);

}
