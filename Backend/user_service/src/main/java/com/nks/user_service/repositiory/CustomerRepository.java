package com.nks.user_service.repositiory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nks.user_service.model.Customer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class CustomerRepository {

    private static final String INDEX = "users";

    private final RestHighLevelClient client;

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerRepository(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void save(Customer customer) throws IOException {
        IndexRequest request = new IndexRequest(INDEX)
                .id(customer.getUserId())
                .source(objectMapper.convertValue(customer, Map.class));

        client.index(request, RequestOptions.DEFAULT);
    }

    public Optional<Customer> findById(String key) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("userid", key))
                .size(1);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        if(Objects.requireNonNull(response.getHits().getTotalHits()).value > 0) {
            SearchHit hit = response.getHits().getHits()[0];
            return Optional.of(objectMapper.readValue(hit.getSourceAsString(), Customer.class));
        }
        return Optional.empty();
    }

    public boolean duplicateUser(String email, String phone) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("email", email))
                .should(QueryBuilders.termQuery("phone", phone))
                .minimumShouldMatch(1); // if any match, it's duplicate

        SearchSourceBuilder builder = new SearchSourceBuilder().query(boolQuery);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return Objects.requireNonNull(response.getHits().getTotalHits()).value > 0;
    }

    public Optional<Customer> findByEmail(String email) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("email", email))
                .size(1);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        if(Objects.requireNonNull(response.getHits().getTotalHits()).value > 0) {
            SearchHit hit = response.getHits().getHits()[0];
            return Optional.of(objectMapper.readValue(hit.getSourceAsString(), Customer.class));
        }
        return Optional.empty();
    }

    public Optional<Customer> findByPhone(String phone) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .query(QueryBuilders.termQuery("phone", phone))
                .size(1);
        searchRequest.source(builder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        if(Objects.requireNonNull(response.getHits().getTotalHits()).value > 0) {
            SearchHit hit = response.getHits().getHits()[0];
            return Optional.of(objectMapper.readValue(hit.getSourceAsString(), Customer.class));
        }
        return Optional.empty();
    }

    public void ensureIndexExists() throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX);
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

        if (!exists) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX);

            Customer customer = new Customer("dummy231", "eca.2016@gmai.com", "Dummy", "+912345678909",
                    "MALE", true);
            // Convert POJO to mapping (basic types inferred)
            Map<String, Object> jsonMap = objectMapper.convertValue(customer, Map.class);
            StringBuilder mappingBuilder = new StringBuilder("{ \"properties\": {");

            for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                mappingBuilder.append("\"")
                        .append(entry.getKey())
                        .append("\": { \"type\": \"keyword\" },"); // you can change to "text", "long", etc.
            }

            // remove trailing comma and close
            int lastComma = mappingBuilder.lastIndexOf(",");
            if (lastComma > 0) {
                mappingBuilder.deleteCharAt(lastComma);
            }

            mappingBuilder.append("}}");

            createIndexRequest.mapping(mappingBuilder.toString(), XContentType.JSON);

            CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println("Index created: " + response.index());
        } else {
            System.out.println("Index already exists: " + INDEX);
        }
    }

}
