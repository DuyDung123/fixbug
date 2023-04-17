package com.example.fixbug.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class EsService {

    /**
     * @param client createClient();
     * @param document table
     * @param id record: If you don't pass the id, it will automatically generate, it is recommended to pass the id to support the update
     * @param value: data save
     * @throws IOException
     */
    public static void insertDocument(RestHighLevelClient client, String document, String id, Map<String, Object> value) throws IOException {
        IndexRequest request = new IndexRequest(document);
        request.source(value, XContentType.JSON);
        request.id(id);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        if (response.status() != RestStatus.CREATED) {
            throw new RuntimeException("Failed to insert document into Elasticsearch");
        }
    }

    public static RestHighLevelClient createClient(String host, int port, String username, String password) {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

    /**
     * @param client createClient();
     * @param doc table
     * @param id : id record;
     * @param document: date reccord
     * @throws IOException
     */
    public static void updateDocument(RestHighLevelClient client, String doc, String id, Map<String, Object> document) throws IOException {
        UpdateRequest request = new UpdateRequest(doc, id);
        request.doc(document);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        if (response.status() != RestStatus.OK) {
            throw new RuntimeException("Failed to update document in Elasticsearch");
        }
    }

    public static void searchDocuments(RestHighLevelClient client, String doc, String field, String value) throws IOException {
        SearchRequest request = new SearchRequest(doc);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(field, value));
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap.toString());
            // Process each document source here
        }
    }
}
