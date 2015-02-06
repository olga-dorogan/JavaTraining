package com.custom.metadata;

import java.sql.SQLException;
import java.util.Properties;

public interface DatabaseAnalysis{
    public void createFileWithDatabaseMetadata(String url, Properties props, String outputFile) throws SQLException;
}