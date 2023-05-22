package io.bootify.my_app;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.bootify.my_app.bean.FilterDTO;
import io.bootify.my_app.bean.QueryFilter;
import io.bootify.my_app.service.GenericQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    private final GenericQueryService genericQueryService;

    @Autowired
    public QueryController(GenericQueryService genericQueryService) {
        this.genericQueryService = genericQueryService;
    }

    @GetMapping("/aa")
    @ResponseBody
    public QueryFilter getJ() {
        List<FilterDTO> filterDTOs = new ArrayList<>();
        FilterDTO filterDTO = new FilterDTO();
        filterDTO.setValue("nome");
        filterDTO.setField("nome");
        filterDTOs.add(0, filterDTO);

        QueryFilter queryFilter= new QueryFilter();
        queryFilter.setFilters(filterDTOs);
        return queryFilter;
        
    }


    @PostMapping("/{tableName}")
    public ResponseEntity<String> executeQuery(
            @PathVariable String tableName,
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @RequestBody QueryFilter queryFilter
    ) {
        try {
            List<FilterDTO> filters = queryFilter.getFilters();
         
            String result = genericQueryService.executeQuery(tableName, page, pageSize, filters, sortField, sortOrder);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing query result");
        }
    }
}
