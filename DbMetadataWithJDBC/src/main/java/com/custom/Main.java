package com.custom;

import com.custom.metadata.DatabaseAnalysisByConnectionMetadata;
import com.custom.metadata.DatabaseAnalysisByInformationSchema;
import com.custom.metadata.DatabaseAnalysisByPgCatalog;

import java.sql.*;
import java.util.Properties;

/**
 * Created by olga on 04.02.15.
 */
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/tested_shop";
        Properties properties = new Properties();
        properties.put("username", "olga");
        properties.put("password", "olga");
        String outputFile;
        try {
            outputFile = "test_pg_catalog.txt";
            new DatabaseAnalysisByPgCatalog().createFileWithDatabaseMetadata(url, properties, outputFile);
            outputFile = "test_information_schema.txt";
            new DatabaseAnalysisByInformationSchema().createFileWithDatabaseMetadata(url, properties, outputFile);
            outputFile = "test_conn_metadata.txt";
            new DatabaseAnalysisByConnectionMetadata().createFileWithDatabaseMetadata(url, properties, outputFile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
