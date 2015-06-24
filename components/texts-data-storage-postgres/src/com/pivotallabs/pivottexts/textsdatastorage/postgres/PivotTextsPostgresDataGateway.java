package com.pivotallabs.pivottexts.textsdatastorage.postgres;

import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import com.pivotallabs.pivottexts.textsdatastorage.PivotTextsDataGateway;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class PivotTextsPostgresDataGateway implements PivotTextsDataGateway {

    NamedParameterJdbcTemplate jdbcTemplate;

    public PivotTextsPostgresDataGateway(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PivotText pivotText) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pivot_id", pivotText.getPivotId());
        paramMap.put("pivot_first_name", pivotText.getPivotFirstName());
        paramMap.put("pivot_last_name", pivotText.getPivotLastName());
        paramMap.put("pivot_location", pivotText.getPivotLocation());
        paramMap.put("message", pivotText.getMessage());

        jdbcTemplate.update(
                "INSERT INTO pivot_texts " +
                        "(received_at, pivot_id, pivot_first_name, pivot_last_name, pivot_location, message) " +
                        "VALUES (now(), :pivot_id, :pivot_first_name, :pivot_last_name, :pivot_location, :message);",
                paramMap
        );
    }

    @Override
    public List<PivotText> forToday() {
        return jdbcTemplate.query(
                "SELECT pivot_id, pivot_first_name, pivot_last_name, pivot_location, message, EXTRACT(EPOCH FROM received_at) as received_at " +
                "FROM pivot_texts " +
                "WHERE date_trunc('day', now()) = date_trunc('day', received_at);",
                (rs, rowNum) -> {
                    PivotText pivotText = new PivotText();
                    pivotText.setPivotId(rs.getInt("pivot_id"));
                    pivotText.setPivotFirstName(rs.getString("pivot_first_name"));
                    pivotText.setPivotLastName(rs.getString("pivot_last_name"));
                    pivotText.setPivotLocation(rs.getString("pivot_location"));
                    pivotText.setMessage(rs.getString("message"));
                    pivotText.setReceivedAt(rs.getLong("received_at"));

                    return pivotText;
                }
        );
    }
}
