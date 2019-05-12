package com.rcsoyer.servicosjuridicos.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=validate")
abstract class RepositoryConfigTest {
    
}
