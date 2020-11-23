package com.wildlife.fody.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.wildlife.fody.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.wildlife.fody.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.wildlife.fody.domain.User.class.getName());
            createCache(cm, com.wildlife.fody.domain.Authority.class.getName());
            createCache(cm, com.wildlife.fody.domain.User.class.getName() + ".authorities");
            createCache(cm, com.wildlife.fody.domain.Country.class.getName());
            createCache(cm, com.wildlife.fody.domain.Country.class.getName() + ".species");
            createCache(cm, com.wildlife.fody.domain.Employee.class.getName());
            createCache(cm, com.wildlife.fody.domain.Sighting.class.getName());
            createCache(cm, com.wildlife.fody.domain.Sighting.class.getName() + ".photos");
            createCache(cm, com.wildlife.fody.domain.Sighting.class.getName() + ".competitors");
            createCache(cm, com.wildlife.fody.domain.BirdsIdentified.class.getName());
            createCache(cm, com.wildlife.fody.domain.BirdsIdentified.class.getName() + ".birdBehaviours");
            createCache(cm, com.wildlife.fody.domain.BirdBehaviour.class.getName());
            createCache(cm, com.wildlife.fody.domain.BirdBehaviour.class.getName() + ".birdsIdentifieds");
            createCache(cm, com.wildlife.fody.domain.EggsAndChick.class.getName());
            createCache(cm, com.wildlife.fody.domain.EggsAndChick.class.getName() + ".eggs");
            createCache(cm, com.wildlife.fody.domain.EggsAndChick.class.getName() + ".chicks");
            createCache(cm, com.wildlife.fody.domain.Egg.class.getName());
            createCache(cm, com.wildlife.fody.domain.Chick.class.getName());
            createCache(cm, com.wildlife.fody.domain.Competitors.class.getName());
            createCache(cm, com.wildlife.fody.domain.Competitors.class.getName() + ".competitorImpactOnMks");
            createCache(cm, com.wildlife.fody.domain.Competitors.class.getName() + ".competitorActions");
            createCache(cm, com.wildlife.fody.domain.CompetitorImpactOnMk.class.getName());
            createCache(cm, com.wildlife.fody.domain.CompetitorImpactOnMk.class.getName() + ".competitors");
            createCache(cm, com.wildlife.fody.domain.CompetitorAction.class.getName());
            createCache(cm, com.wildlife.fody.domain.CompetitorAction.class.getName() + ".competitors");
            createCache(cm, com.wildlife.fody.domain.FeedingObservation.class.getName());
            createCache(cm, com.wildlife.fody.domain.NestSiteOverview.class.getName());
            createCache(cm, com.wildlife.fody.domain.Maintenance.class.getName());
            createCache(cm, com.wildlife.fody.domain.RingingMorphs.class.getName());
            createCache(cm, com.wildlife.fody.domain.SpeciesCategory.class.getName());
            createCache(cm, com.wildlife.fody.domain.Species.class.getName());
            createCache(cm, com.wildlife.fody.domain.Species.class.getName() + ".taggedAnimals");
            createCache(cm, com.wildlife.fody.domain.Species.class.getName() + ".countries");
            createCache(cm, com.wildlife.fody.domain.TaggedAnimal.class.getName());
            createCache(cm, com.wildlife.fody.domain.Photo.class.getName());
            createCache(cm, com.wildlife.fody.domain.ObservationLocation.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
