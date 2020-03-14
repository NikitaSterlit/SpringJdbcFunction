package com.example.spring.service;


import com.example.spring.model.Cats;
import com.example.spring.model.Response;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private JdbcTemplate jdbcTemplate;

//    @Value("${funс-name}")
//    private String funcName;

    public DataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cats> callFunc() throws SQLException {

        List<Cats> listCats = new ArrayList<>();

        SimpleJdbcCall jdbc = new SimpleJdbcCall(jdbcTemplate).withFunctionName("my_test_func")
                .declareParameters(new SqlOutParameter("response_type", OracleTypes.STRUCT, "RESPONSE"))
                .declareParameters(new SqlParameter("value1", OracleTypes.NUMBER))
                .declareParameters(new SqlParameter("value2", OracleTypes.VARCHAR))
                .declareParameters(new SqlParameter("value21", OracleTypes.NUMBER))
                .declareParameters(new SqlParameter("value22", OracleTypes.VARCHAR));

        SqlParameterSource input = new MapSqlParameterSource()
                .addValue("value1", 1995)
                .addValue("value2", "Nikita")
                .addValue("value21", 1987)
                .addValue("value22", "Igor");

        Struct struct = null;
        Response response = new Response();

        struct = jdbc.executeFunction(Struct.class, input);

        Object[] result = struct.getAttributes();
        response.setId((BigDecimal) result[0]);
        Array array = (Array)result[1];
        Object[] result2 = (Object[])array.getArray();
        listCats = catBuilder(result2);
        return listCats;
    }

    public List<Cats> catBuilder (Object [] objects) throws SQLException {
        List<Cats> listCats = new ArrayList<>();
        for(Object ob : objects){
            Struct struct = (Struct) ob;
            Object[] values = struct.getAttributes();
            Cats cats = new Cats();
            cats.setName((String) values[0]);
            cats.setColor((String) values[1]);
            listCats.add(cats);
        }
        return listCats;
    }
}
