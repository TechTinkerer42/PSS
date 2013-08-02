package org.ets.ereg.common.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.service.CountryService;
import org.ets.ereg.common.business.service.ReferenceService;
import org.ets.ereg.common.shared.util.ReferenceTypeCriteria;
import org.ets.ereg.domain.interfaces.model.common.ETSCountry;
import org.ets.ereg.domain.interfaces.model.common.PhoneType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ets-applicationContext-test.xml",
		"classpath:application-context-common-business.xml" })
public class TestReferenceData {
	private static Logger log = LoggerFactory.getLogger(TestReferenceData.class);
	
	@Resource(name = "referenceEntityService")
	ReferenceService referenceService;
	@Resource(name = "blCacheManager")
	CacheManager cacheManager;
	@Resource(name = "blCountryService")
	CountryService countryService;
	
	@Ignore @Test
	public void testPhoneTypeRefData(){
		printAllCacheNames();
		enableCacheStatistics("etsStandardElements");
		enableCacheStatistics("org.hibernate.cache.StandardQueryCache");
		printCacheHitsStats("etsStandardElements","before load");
		int cacheSize = getCacheSize("etsStandardElements");
		
		printCacheSize("org.hibernate.cache.StandardQueryCache","before load");
		printCacheHitsStats("org.hibernate.cache.StandardQueryCache","before load");
		
		//Cache has not yet been hit, count must be zero
		assertEquals(0, getHitCount("etsStandardElements"));
		
		//loading first time, must hit DB, cache hit count must be zero
		List<PhoneType> list= referenceService.getAll(PhoneType.class,new ReferenceTypeCriteria(),false);
		assertNotNull(list);
		assertEquals(2,list.size());
		assertEquals(0, getHitCount("etsStandardElements"));
		printCacheHitsStats("etsStandardElements","after initial load");
		printCacheSize("org.hibernate.cache.StandardQueryCache","after initial load");
		printCacheHitsStats("org.hibernate.cache.StandardQueryCache","after initial load");
		
		//this time, must be loaded from cache, cahe hit count must be 2 because there are 2 records for phone type
		List<PhoneType> cachedList= referenceService.getAll(PhoneType.class,new ReferenceTypeCriteria(),false);
		assertEquals(2, getHitCount("etsStandardElements"));
		assertNotNull(cachedList);
		assertEquals(2,cachedList.size());
		assertEquals(cachedList.size(),list.size());
		
		printCacheHitsStats("etsStandardElements","after cached list");
		printCacheSize("org.hibernate.cache.StandardQueryCache","after cached list");
		printCacheHitsStats("org.hibernate.cache.StandardQueryCache","after cached list");
		printCacheContents("org.hibernate.cache.StandardQueryCache","after cached list");
		
		//access single object by type and key, must come from cache so hit count is incremented
		PhoneType pt = referenceService.getEntityByPrimaryKey(PhoneType.class,"M");
		assertEquals("M",pt.getCode());
		assertEquals(3, getHitCount("etsStandardElements"));
		
		printCacheSize("org.hibernate.cache.StandardQueryCache","after single object access");
		printCacheHitsStats("org.hibernate.cache.StandardQueryCache","after single object access");
		
		//access it again
		pt = referenceService.getEntityByPrimaryKey(PhoneType.class,"M");
		assertEquals(4, getHitCount("etsStandardElements"));
		printCacheHitsStats("etsStandardElements","after individual element load");
		printCacheSize("org.hibernate.cache.StandardQueryCache","after individual element load");
		printCacheHitsStats("org.hibernate.cache.StandardQueryCache","after individual element load");
		printCacheContents("org.hibernate.cache.StandardQueryCache","after individual element load");
		
		//access list again
		list= referenceService.getAll(PhoneType.class,new ReferenceTypeCriteria(),false);
		assertEquals(6, getHitCount("etsStandardElements"));
		printCacheHitsStats("etsStandardElements","after load 3");
		assertEquals((cacheSize+2),getCacheSize("etsStandardElements"));
	}
	
	@Ignore @Test
	public void getCountries(){
		printCacheContents("blStandardElements");
		List<Country> countries = countryService.findCountries();
		printCacheContents("blStandardElements");
		countries = countryService.findCountries();
//		getETSCountries();
		
	}
	
	@Ignore @Test
	public void getETSCountries(){
		printCacheContents("blStandardElements");
		ETSCountry country = (ETSCountry)countryService.findCountryByAbbreviation("US");
		log.info("1isd:"+country.getIsdCode());
		printCacheContents("blStandardElements");
		country = (ETSCountry)countryService.findCountryByAbbreviation("US");
		log.info("2isd:"+country.getIsdCode());
		country = (ETSCountry)countryService.findCountryByAbbreviation("US");
		log.info("3isd:"+country.getIsdCode());
		country = (ETSCountry)countryService.findCountryByAbbreviation("US");
		log.info("4isd:"+country.getIsdCode());
		printCacheSize("blStandardElements");
		country = (ETSCountry)countryService.findCountryByAbbreviation("testcountry3");
		log.info("5isd:"+country.getIsdCode());
		printCacheSize("blStandardElements");
	}
	
	private void printStats(String cacheName,String when){
		log.info("-----printStats-----"+when);
		net.sf.ehcache.Cache cache = cacheManager.getCache(cacheName);
		cache.setStatisticsEnabled(true);
		log.info(cacheName+" - "+ cache.getStatistics().toString());
		log.info("-----printStats end-----\n");
	}
	
	private void printStats(String cacheName){
		printStats(cacheName,"");
	}
	
	private void printCacheHitsStats(String cacheName,String when){
		log.info("-----printStats-----"+when);
		net.sf.ehcache.Cache cache = cacheManager.getCache(cacheName);
		log.info(cacheName+" - hits:"+ getHitCount(cacheName));
		log.info(cacheName+" - live cache hits:"+ getLiveHitCount(cacheName));
		log.info("-----printStats end-----\n");
	}
	
	private void printCacheHitsStats(String cacheName){
		printStats(cacheName,"");
	}
	
	private void printAllCacheNames(){
		log.info("-----printAllCacheNames-----");
	    for (String cacheName : cacheManager.getCacheNames()) {
	    	log.info(cacheName);
	    }
	    log.info("-----printAllCacheNames end-----");
	}
	
	private void printCacheContents(String cacheName, String when){
		log.info("-----printCacheContents-----"+when);
		net.sf.ehcache.Cache cache = cacheManager.getCache(cacheName);
		printCacheSize(cacheName);
		List all = cache.getKeys();
		for (int i=0;i<all.size();i++){
			log.info(cache.get(all.get(i)).toString());
		}
		log.info("-----printCacheContents end-----");
	}
	
	private void printCacheContents(String cacheName){
		printCacheContents(cacheName,"");
	}
	
	private void printCacheSize(String cacheName){
		printCacheSize(cacheName,"");
	}
	
	private void printCacheSize(String cacheName,String when){
		when = StringUtils.isEmpty(when)?"":when+", ";
		log.info(when +"There are "+getCacheSize(cacheName)+" elements in "+cacheName+"\n");
	}
	
	private int getCacheSize(String cacheName){
		return cacheManager.getCache(cacheName).getSize();
	}
	
	private Cache getCacheObject(String cacheName){
		return cacheManager.getCache(cacheName);
	}
	
	private void enableCacheStatistics(String cacheName){
		net.sf.ehcache.Cache cache = cacheManager.getCache(cacheName);
		log.info(cacheName+"---CacheStatistics enabled :"+cache.isStatisticsEnabled());
		cache.setStatisticsEnabled(true);
	}
	
	private long getHitCount(String cacheName){
		return cacheManager.getCache(cacheName).getStatistics().getCacheHits();
	}
	
	private long getLiveHitCount(String cacheName){
		return cacheManager.getCache(cacheName).getLiveCacheStatistics().getCacheHitCount();
	}
}
