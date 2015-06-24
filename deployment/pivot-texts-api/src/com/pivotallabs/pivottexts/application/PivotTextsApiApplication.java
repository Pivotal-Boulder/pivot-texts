package com.pivotallabs.pivottexts.application;

import com.google.common.cache.CacheBuilder;
import com.pivotallabs.pivottexts.pivotsconnector.Pivot;
import com.pivotallabs.pivottexts.pivotsconnector.PivotsConnector;
import com.pivotallabs.pivottexts.pivotsconnector.PivotsSource;
import com.pivotallabs.pivottexts.pivottextsservice.PivotTextsService;
import com.pivotallabs.pivottexts.textsdatastorage.PivotTextsDataGateway;
import com.pivotallabs.pivottexts.textsdatastorage.postgres.PivotTextsPostgresDataGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan("com.pivotallabs")
@EnableSwagger2
@EnableCaching
public class PivotTextsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PivotTextsApiApplication.class, args);
    }

    @Bean
    public PivotTextsDataGateway pivotTextsDataGateway(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        return new PivotTextsPostgresDataGateway(namedParameterJdbcTemplate);
    }

    @Bean
    public PivotTextsService pivotTextsService(
            PivotTextsDataGateway pivotTextsDataGateway,
            PivotsSource pivotsSource
    ) {
        return new PivotTextsService(pivotTextsDataGateway, pivotsSource);
    }

    @Bean
    public PivotsSource pivotsSource(
            @Value("${pivots.url}") String pivotsUrl,
            @Value("${pivots.email}") String pivotsEmail,
            @Value("${pivots.authToken}") String pivotsAuthToken
    ) {
        PivotsConnector pivotsConnector = new PivotsConnector(
                new RestTemplate(), pivotsUrl, pivotsEmail, pivotsAuthToken
        );

        return new PivotsSource() {
            @Override
            @Cacheable("pivots")
            public List<Pivot> getPivots() {
                return pivotsConnector.getPivots();
            }
        };
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(name,
                        CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build().asMap(), false);
            }
        };
    }
}
