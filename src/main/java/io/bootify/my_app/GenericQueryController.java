package io.bootify.my_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.bootify.my_app.bean.FilterDTO;
import io.bootify.my_app.bean.QueryFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GenericQueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*
    curl -X POST   -H "Content-Type: application/json"   -d '{
	"page": 0,
    "limit": 3,
    "sortField": "cognome",
    "sortOrder": "asc",
    "filters": [
      {"field": "cognome", "value": "Williams"}
    ]
  }'   http://localhost:8080/query?tableName=rubrica
     */
    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> getData(
            @RequestParam String tableName,
            @RequestBody QueryFilter tutorialRequest
    ) {
        int page = tutorialRequest.getPage();
        int limit = tutorialRequest.getLimit();
        String sortField = tutorialRequest.getSortField();
        String sortOrder = tutorialRequest.getSortOrder();
        List<FilterDTO> filters = tutorialRequest.getFilters();

        // Calcola l'offset per la pagina richiesta
        int offset = (page - 1) * limit;

        // Costruzione della query con filtri e ordinamento dinamici
        List<Object> queryParams = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(*) FROM ")
                .append(tableName);

        if (!filters.isEmpty()) {
            queryBuilder.append(" WHERE");

            for (int i = 0; i < filters.size(); i++) {
                FilterDTO filter = filters.get(i);
                String field = filter.getField();
                String value = filter.getValue();

                queryBuilder.append(" ")
                        .append(field)
                        .append(" = ?");

                //queryParams.add("%" + value + "%");
                queryParams.add(value);

                if (i < filters.size() - 1) {
                    queryBuilder.append(" AND");
                }
            }
        }

        //int totalElements = jdbcTemplate.queryForObject(queryBuilder.toString(), queryParams.toArray(), Integer.class);
        int totalElements =  jdbcTemplate.queryForObject(
            queryBuilder.toString(), Integer.class, queryParams.toArray());
        //queryBuilder = new StringBuilder("select count (*) from rubrica");
        //int totalElements =  jdbcTemplate.queryForObject(
        //    queryBuilder.toString(), Integer.class);


        queryParams.clear();
        queryBuilder = new StringBuilder("SELECT * FROM ")
                .append(tableName);

        if (!filters.isEmpty()) {
            queryBuilder.append(" WHERE");

            for (int i = 0; i < filters.size(); i++) {
                FilterDTO filter = filters.get(i);
                String field = filter.getField();
                String value = filter.getValue();

                queryBuilder.append(" ")
                        .append(field)
                        .append(" LIKE ?");

                queryParams.add("%" + value + "%");

                if (i < filters.size() - 1) {
                    queryBuilder.append(" AND");
                }
            }
        }

        queryBuilder.append(" ORDER BY ")
                .append(sortField)
                .append(" ")
                .append(sortOrder)
                .append("  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
               

        queryParams.add(offset);
        queryParams.add(limit);

        List<Map<String, Object>> results = jdbcTemplate.queryForList(
                queryBuilder.toString(),
                queryParams.toArray()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("totalElements", totalElements);
        response.put("data", results);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
