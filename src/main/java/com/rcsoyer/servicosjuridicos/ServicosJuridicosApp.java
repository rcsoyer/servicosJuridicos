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
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class ServicosJuridicosApp {
    
    private final Environment env;
    private static final Logger log = LoggerFactory.getLogger(ServicosJuridicosApp.class);
    
    public ServicosJuridicosApp(Environment env) {
        this.env = env;
    }
    
    /**
     * Initializes servicosJuridicos.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a
     * href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        List<String> activeProfiles = asList(env.getActiveProfiles());
        boolean hasSpringProfileDev = activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT);
        boolean hasSpringProfileProd = activeProfiles.contains(SPRING_PROFILE_PRODUCTION);
        boolean hasSpringProfileCloud = activeProfiles.contains(SPRING_PROFILE_CLOUD);
        
        if (hasSpringProfileDev && hasSpringProfileProd) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        
        if (hasSpringProfileDev && hasSpringProfileCloud) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }
    
    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServicosJuridicosApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }
    
    private static void logApplicationStartup(Environment env) {
        var protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                               .map(keyStore -> "https")
                               .orElse("http");
        var serverPort = env.getProperty("server.port");
        var contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                                  .orElse("/");
        var hostAddress = Try.of(InetAddress::getLocalHost)
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
}

