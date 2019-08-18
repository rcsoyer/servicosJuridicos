package com.rcsoyer.servicosjuridicos.config;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.Authority;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.domain.User;
import com.rcsoyer.servicosjuridicos.repository.UserRepository;
import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory
            .setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder
                    .timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            String usersByLoginCache = UserRepository.USERS_BY_LOGIN_CACHE;
            String usersByEmailCache = UserRepository.USERS_BY_EMAIL_CACHE;
            String nameUser = User.class.getName();
            String nameAuthority = Authority.class.getName();
            String nameUserAutorities = nameUser + ".authorities";
            String nameAssunto = Assunto.class.getName();
            String nameCoordenacao = CoordenacaoJuridica.class.getName();
            String nameCoordenacaoDgAdvogados = nameCoordenacao + ".dgAdvogados";
            String nameCoordenacaoAssuntos = nameCoordenacao + ".assuntos";
            String nameAdvogado = Advogado.class.getName();
            String nameAdvogadoProcessos = nameAdvogado + ".processos";
            String nameAdvogadoFeriasLicenca = nameAdvogado + ".feriasLicencas";
            String nameAdvogadoDgCoordenacoes = nameAdvogado + ".dgCoordenacoes";
            String nameProcessoJudicial = ProcessoJudicial.class.getName();
            String nameModalidade = Modalidade.class.getName();
            String nameFeriasLicenca = FeriasLicenca.class.getName();
            String nameAdvogadoDgCoordenacao = AdvogadoDgCoordenacao.class.getName();
            cacheManager.createCache(usersByLoginCache, jcacheConfiguration);
            cacheManager.createCache(usersByEmailCache, jcacheConfiguration);
            cacheManager.createCache(nameUser, jcacheConfiguration);
            cacheManager.createCache(nameAuthority, jcacheConfiguration);
            cacheManager.createCache(nameUserAutorities, jcacheConfiguration);
            cacheManager.createCache(nameAssunto, jcacheConfiguration);
            cacheManager.createCache(nameCoordenacao, jcacheConfiguration);
            cacheManager.createCache(nameCoordenacaoDgAdvogados, jcacheConfiguration);
            cacheManager.createCache(nameCoordenacaoAssuntos, jcacheConfiguration);
            cacheManager.createCache(nameAdvogado, jcacheConfiguration);
            cacheManager.createCache(nameAdvogadoProcessos, jcacheConfiguration);
            cacheManager.createCache(nameAdvogadoFeriasLicenca, jcacheConfiguration);
            cacheManager.createCache(nameAdvogadoDgCoordenacoes, jcacheConfiguration);
            cacheManager.createCache(nameProcessoJudicial, jcacheConfiguration);
            cacheManager.createCache(nameModalidade, jcacheConfiguration);
            cacheManager.createCache(nameFeriasLicenca, jcacheConfiguration);
            cacheManager.createCache(nameAdvogadoDgCoordenacao, jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
