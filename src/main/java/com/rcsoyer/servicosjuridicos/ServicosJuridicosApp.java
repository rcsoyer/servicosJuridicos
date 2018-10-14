package com.rcsoyer.servicosjuridicos;

import com.rcsoyer.servicosjuridicos.config.ApplicationProperties;
import com.rcsoyer.servicosjuridicos.config.DefaultProfileUtil;
import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_CLOUD;
import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_DEVELOPMENT;
import static io.github.jhipster.config.JHipsterConstants.SPRING_PROFILE_PRODUCTION;
import io.vavr.control.Try;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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
    private static final Logger LOG = LoggerFactory.getLogger(ServicosJuridicosApp.class);
    
    public ServicosJuridicosApp(Environment env) {
        this.env = env;
    }
    
    /**
     * Initializes servicosJuridicos.
     * <p>
     * Spring profiles can be configured with a program arguments
     * --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on
     * <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        logErrMisconfigApp(activeProfiles);
    }
    
    private void logErrMisconfigApp(List<String> activeProfiles) {
        Consumer<Boolean> checkHasProdOrCloud = hasIt -> {
            boolean hasProfileProd = activeProfiles.contains(SPRING_PROFILE_PRODUCTION);
            boolean hasProfileCloud = activeProfiles.contains(SPRING_PROFILE_CLOUD);
            Consumer<Boolean> logErrProfileProd =
                error -> LOG.error("You have misconfigured your application! It should not run with both the 'dev' and 'prod' profiles at the samee time.");
            Consumer<Boolean> logErrProfileCloud =
                error -> LOG.error("You have misconfigured your application! It should not run with both the 'dev' and 'cloud' profiles at the same time.");
            Optional.of(hasProfileProd)
                    .filter(Boolean.TRUE::equals)
                    .ifPresent(logErrProfileProd);
            Optional.of(hasProfileCloud)
                    .filter(Boolean.TRUE::equals)
                    .ifPresent(logErrProfileCloud);
        };
        boolean hasProfileDev = activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT);
        Optional.of(hasProfileDev)
                .filter(Boolean.TRUE::equals)
                .ifPresent(checkHasProdOrCloud);
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
        String protocol = protocol(env);
        String hostAddress = hostAdressOrDefault();
        String format = format();
        String appName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String[] activeProfiles = env.getActiveProfiles();
        LOG.info(format, appName, protocol, serverPort, protocol, hostAddress, serverPort, activeProfiles);
    }
    
    private static String format() {
        return "\n----------------------------------------------------------\n\t"
                   + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
                   + "External: \t{}://{}:{}\n\t"
                   + "Profile(s): \t{}\n----------------------------------------------------------";
    }
    
    private static String protocol(Environment env) {
        return Optional.ofNullable(env.getProperty("server.ssl.key-store"))
                       .map(keyStore -> keyStore)
                       .orElseGet(() -> "http");
    }
    
    private static String hostAdressOrDefault() {
        return Try.of(() -> InetAddress.getLocalHost().getHostAddress())
                  .getOrElse(() -> {
                      LOG.warn("The host name could not be determined, using `localhost` as fallback");
                      return "localhost";
                  });
    }
}
