package com.rcsoyer.servicosjuridicos.config;

import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;

@Configuration
@EnableCaching
public class CacheConfiguration {

  private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

  public CacheConfiguration(JHipsterProperties jHipsterProperties) {
    BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
    JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

    jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder
        .newCacheConfigurationBuilder(Object.class, Object.class,
            ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
        .withExpiry(ExpiryPolicyBuilder
            .timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
        .build());
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> {
      cm.createCache(com.rcsoyer.servicosjuridicos.repository.UserRepository.USERS_BY_LOGIN_CACHE,
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.repository.UserRepository.USERS_BY_EMAIL_CACHE,
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.User.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.Authority.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.User.class.getName() + ".authorities",
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.Assunto.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica.class.getName(),
          jcacheConfiguration);
      cm.createCache(
          com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica.class.getName() + ".dgAdvogados",
          jcacheConfiguration);
      cm.createCache(
          com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica.class.getName() + ".assuntos",
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.Advogado.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.Advogado.class.getName() + ".processos",
          jcacheConfiguration);
      cm.createCache(
          com.rcsoyer.servicosjuridicos.domain.Advogado.class.getName() + ".feriasLicencas",
          jcacheConfiguration);
      cm.createCache(
          com.rcsoyer.servicosjuridicos.domain.Advogado.class.getName() + ".dgCoordenacoes",
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.Modalidade.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.FeriasLicenca.class.getName(),
          jcacheConfiguration);
      cm.createCache(com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao.class.getName(),
          jcacheConfiguration);
      // jhipster-needle-ehcache-add-entry
    };
  }
}
