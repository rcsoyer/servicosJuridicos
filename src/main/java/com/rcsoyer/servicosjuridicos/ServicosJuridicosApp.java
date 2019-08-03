package com.rcsoyer.servicosjuridicos;

import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_CLOUD;
import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_DEVELOPMENT;
import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_PRODUCTION;
import static java.util.Arrays.asList;

import com.rcsoyer.servicosjuridicos.config.ApplicationProperties;
import com.rcsoyer.servicosjuridicos.config.DefaultProfileUtil;
import io.vavr.control.Try;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class ServicosJuridicosApp {
    
    private final Environment env;
    
    public ServicosJuridicosApp(final Environment env) {
        this.env = env;
    }
    
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(ServicosJuridicosApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        final Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }
    
    private static void logApplicationStartup(final Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                                  .map(keyStore -> "https")
                                  .orElse("http");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                                     .orElse("/");
        String serverPort = env.getProperty("server.port");
        String hostAddress = Try.of(InetAddress::getLocalHost)
                                .map(InetAddress::getHostAddress)
                                .getOrElse(() -> {
                                    log.warn("The host name could not be determined, using `localhost` as fallback");
                                    return "localhost";
                                });
        log.info("\n----------------------------------------------------------\n\t" +
                     "Application '{}' is running! Access URLs:\n\t" +
                     "Local: \t\t{}://localhost:{}{}\n\t" +
                     "External: \t{}://{}:{}{}\n\t" +
                     "Profile(s): \t{}\n----------------------------------------------------------",
                 env.getProperty("spring.application.name"),
                 protocol,
                 serverPort,
                 contextPath,
                 protocol,
                 hostAddress,
                 serverPort,
                 contextPath,
                 env.getActiveProfiles());
    }
    
    @PostConstruct
    private void checkActiveProfiles() {
        final List<String> activeProfiles = asList(env.getActiveProfiles());
        Predicate<List<String>> hasProfileDev = profiles -> profiles.contains(SPRING_PROFILE_DEVELOPMENT);
        Predicate<List<String>> hasProfileProd = profiles -> profiles.contains(SPRING_PROFILE_PRODUCTION);
        Predicate<List<String>> hasProfileCloud = profiles -> profiles.contains(SPRING_PROFILE_CLOUD);
        
        Optional.of(activeProfiles)
                .filter(hasProfileDev.and(hasProfileProd))
                .ifPresent(profiles -> log.error("The application might be misconfigured! It should not run " +
                                                     "with both the 'dev' and 'prod' profiles at the same time."));
        
        Optional.of(activeProfiles)
                .filter(hasProfileDev.and(hasProfileCloud))
                .ifPresent(profiles -> log.error("The application might be misconfigured! It should not " +
                                                     "run with both the 'dev' and 'cloud' profiles at the same time."));
    }
}

