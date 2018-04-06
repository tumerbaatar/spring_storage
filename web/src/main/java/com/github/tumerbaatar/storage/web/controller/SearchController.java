package com.github.tumerbaatar.storage.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import com.github.tumerbaatar.storage.model.Part;
import com.github.tumerbaatar.storage.service.PartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class SearchController {
    private PartService partService;
    private RestHighLevelClient client;

    @GetMapping(value = "/search/parts")
    public List<Part> searchParts(
            @RequestParam(value = "storage", required = false) String storageSlug,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "results_on_page", required = false, defaultValue = "10") int resultsOnPage
    ) throws IOException {
        log.info("Search in storage: " + storageSlug);
        log.info("Search query: " + query);
        log.info("Search results page: " + page);
        log.info("Search results on page: " + page);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.multiMatchQuery(
                        query,
                        "partNumber",
                        "name",
                        "description",
                        "manufacturerPartNumber"
                )
                        .fuzziness(Fuzziness.AUTO)
                        .prefixLength(2)
                        .maxExpansions(10)
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)

        );

        SearchRequest searchRequest = new SearchRequest(storageSlug);
        searchRequest.types("part");
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest);

        List<Part> foundParts = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            long foundPartId = Long.valueOf(searchHit.getId());
            Part foundPart = partService.findPart(foundPartId);
            if (foundPart != null) {
                foundParts.add(foundPart);
            }
        }
        return foundParts;
    }

}
