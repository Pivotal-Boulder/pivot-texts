package com.pivotallabs.pivottexts.textsdatastorage.postgres;

import com.pivotallabs.pivottexts.dbtestinglibrary.TestDataSourceFactory;
import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class PivotTextsPostgresDataGatewayTest {

    private PivotTextsPostgresDataGateway dataGateway;
    private TestDataSourceFactory testDataSourceFactory;

    @Before
    public void setup() {
        testDataSourceFactory = new TestDataSourceFactory("pivot-texts-test");
        dataGateway = new PivotTextsPostgresDataGateway(testDataSourceFactory.getNamedParameterJdbcTemplate());
    }

    @Test
    public void testSave() {
        PivotText pivotText = new PivotText();
        pivotText.setPivotId(32);
        pivotText.setPivotLocation("Boulder");
        pivotText.setPivotFirstName("Johnny");
        pivotText.setPivotLastName("Doe++");
        pivotText.setMessage("Chicken Pox AGAIN!!!");

        dataGateway.save(pivotText);

        Integer matchingCount = testDataSourceFactory.getJdbcTemplate().queryForObject(
            "SELECT count(*) FROM pivot_texts " +
                "WHERE pivot_id = 32 AND " +
                "pivot_first_name = 'Johnny' AND " +
                "pivot_last_name = 'Doe++' AND " +
                "pivot_location = 'Boulder' AND " +
                "message = 'Chicken Pox AGAIN!!!';",
            Integer.class);

        assertThat(matchingCount, equalTo(1));
    }

    @Test
    public void testForToday() {
        testDataSourceFactory.getJdbcTemplate().update(
            "INSERT INTO pivot_texts " +
                "(received_at, pivot_id, pivot_first_name, pivot_last_name, pivot_location, message) " +
                "VALUES " +
                "(now() - INTERVAL '1 day', 10, 'Johnny', 'Doe', 'Boulder', 'Yesterday I was late')," +
                "(now() + INTERVAL '1 day', 10, 'Johnny', 'Doe', 'Boulder', 'Tomorrow I will be sick.')," +
                "(now(),                    10, 'Johnny', 'Doe', 'Boulder', 'Hey you.');");

        List<PivotText> pivotTexts = dataGateway.forToday();

        assertThat(pivotTexts.size(), equalTo(1));

        PivotText pivotText = pivotTexts.get(0);
        assertThat(pivotText.getPivotId(), equalTo(10));
        assertThat(pivotText.getPivotFirstName(), equalTo("Johnny"));
        assertThat(pivotText.getPivotLastName(), equalTo("Doe"));
        assertThat(pivotText.getPivotLocation(), equalTo("Boulder"));
        assertThat(pivotText.getMessage(), equalTo("Hey you."));
        assertThat(pivotText.getReceivedAt(), notNullValue());
    }
}
