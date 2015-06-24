package com.pivotallabs.pivottexts.dbtestinglibrary;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class TestDataSourceFactory {

    private final PGPoolingDataSource dataSource;

    public TestDataSourceFactory(String dbName) {
        dataSource = createDataSource(dbName);
        truncateAll();
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private PGPoolingDataSource createDataSource(String dbName) {
        PGPoolingDataSource dataSource;
        dataSource = new PGPoolingDataSource();
        dataSource.setUser("admin");
        dataSource.setPassword("admin");
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName(dbName);
        return dataSource;
    }

    private void truncateAll() {
        getJdbcTemplate().execute("DO\n" +
                "$do$\n" +
                "DECLARE\n" +
                "  table_record RECORD;\n" +
                "  table_name   VARCHAR(50);\n" +
                "BEGIN\n" +
                "  FOR table_record IN (SELECT tablename\n" +
                "                       FROM pg_tables\n" +
                "                       WHERE schemaname = 'public' AND tablename != 'schema_version')\n" +
                "  LOOP\n" +
                "    table_name := table_record.tablename;\n" +
                "    EXECUTE 'TRUNCATE TABLE ' || quote_ident(table_name) || ' CASCADE;';\n" +
                "  END LOOP;\n" +
                "END\n" +
                "$do$;\n");
    }
}
