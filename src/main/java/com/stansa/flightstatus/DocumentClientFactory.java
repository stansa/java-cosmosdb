package com.stansa.flightstatus;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;

public class DocumentClientFactory {
    private static final String HOST = "https://cdb-poc.documents.azure.com:443/";
    private static final String MASTER_KEY = "xxxxxx";

    private static DocumentClient documentClient = new DocumentClient(HOST, MASTER_KEY,
            ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);

    public static DocumentClient getDocumentClient() {
        return documentClient;
    }

}