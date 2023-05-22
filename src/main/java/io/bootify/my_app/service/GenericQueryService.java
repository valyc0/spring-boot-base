package io.bootify.my_app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.bootify.my_app.bean.FilterDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenericQueryService {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public GenericQueryService(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public String executeQuery(String tableName, int page, int pageSize, List<FilterDTO> filters, String sortField, String sortOrder) throws JsonProcessingException {
        int offset = (page - 1) * pageSize;

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ").append(tableName).append(" WHERE 1=1");

        for (FilterDTO filterDTO : filters) {
            queryBuilder.append(" AND ").append(filterDTO.getField()).append(" like '%?%' ");
        }

        
        queryBuilder.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder).append(" LIMIT ? OFFSET ?");

        Object[] parameters = new Object[filters.size() ];
        int index = 0;
        for (FilterDTO filterDTO : filters) {
            parameters[index] = filterDTO.getValue();
            index++;
        }
        
        parameters[index] = pageSize;
        index++;
        parameters[index] = offset;

        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(queryBuilder.toString(), parameters);

        return objectMapper.writeValueAsString(queryResult);
    }
}
