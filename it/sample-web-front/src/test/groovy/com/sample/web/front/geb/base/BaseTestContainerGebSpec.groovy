package com.sample.web.front.geb.base;

import geb.spock.GebSpec
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers

@Testcontainers
class BaseTestContainerGebSpec extends GebSpec {

    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8")

    static final GenericContainer<?> MAILHOG_CONTAINER =
            new GenericContainer<>("mailhog/mailhog")
                    .withExposedPorts(1025, 8025)
                    .waitingFor(Wait.forHttp("/").forPort(8025));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        MYSQL_CONTAINER.start()
        MAILHOG_CONTAINER.start()

        registry.add(
                "spring.datasource.url",
                () ->
                        "jdbc:mysql://%s:%d/%s"
                                .formatted(
                                        MYSQL_CONTAINER.getHost(),
                                        MYSQL_CONTAINER.getFirstMappedPort(),
                                        MYSQL_CONTAINER.getDatabaseName()))
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername)
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword)
        registry.add("spring.flyway.url", MYSQL_CONTAINER::getJdbcUrl)
        registry.add("spring.flyway.user", MYSQL_CONTAINER::getUsername)
        registry.add("spring.flyway.password", MYSQL_CONTAINER::getPassword)

        registry.add("spring.mail.host", MAILHOG_CONTAINER::getHost)
        registry.add("spring.mail.port", MAILHOG_CONTAINER::getFirstMappedPort)
    }
}
