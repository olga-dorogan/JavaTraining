package com.custom.metadata;

import com.custom.metadata.output.Key;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by olga on 04.02.15.
 */
public class DatabaseAnalysisByConnectionMetadata implements DatabaseAnalysis {
    private static int
            TABLE_NAME_POSITION = 3,
            PK_COLUMN_NAME_POSITION = 4,
            PK_NAME_POSITION = 6,
            FK_COLUMN_NAME_POSITION = 8,
            FK_NAME_POSITION = 12,
            FK_REFERENCED_TABLE_NAME_POSITION = 3,
            FK_REFERENCED_COLUMN_POSITION = 4;
    private static int
            COLUMN_NAME_POSITION = 4,
            COLUMN_TYPE_POSITION = 6;

    @Override
    public void createFileWithDatabaseMetadata(String url, Properties props, String outputFile) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            DatabaseMetaData metaData = connection.getMetaData();
            createFile(metaData, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(DatabaseMetaData metaData, String outputFile) throws SQLException, IOException {
        JSONArray jsonArray = new JSONArray();
        String tableName;
        try (ResultSet metaDataTables = metaData.getTables(null, null, null, new String[]{"TABLE"})) {
            while (metaDataTables.next()) {
                tableName = metaDataTables.getString(TABLE_NAME_POSITION);
                jsonArray.put(new JSONObject()
                        .put(Key.TABLE_NAME, tableName)
                        .put(Key.PRIMARY_KEY, getPrimaryKeys(metaData, tableName))
                        .put(Key.FOREIGN_KEY, getForeignKeys(metaData, tableName))
                        .put(Key.COLUMNS, getColumns(metaData, tableName)));
            }
        }
        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            jsonArray.write(fileWriter);
        }
    }

    private JSONObject getPrimaryKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        JSONObject jsonObjectPK = new JSONObject();
        JSONArray jsonArrayColumns = new JSONArray();
        try (ResultSet resultSetPK = metaData.getPrimaryKeys(null, null, tableName)) {
            while (resultSetPK.next()) {
                jsonArrayColumns.put(resultSetPK.getString(PK_COLUMN_NAME_POSITION));
            }
            if (resultSetPK.previous()) {
                jsonObjectPK.put(Key.KEY_NAME, resultSetPK.getString(PK_NAME_POSITION));
            }
            jsonObjectPK.put(Key.KEY_COLUMNS, jsonArrayColumns);
        }
        return jsonObjectPK;
    }

    private JSONArray getForeignKeys(DatabaseMetaData metaData, String tableName) throws SQLException {
        JSONArray jsonArrayFK = new JSONArray();
        JSONObject jsonObjectKey;
        JSONObject jsonObjectRef;
        try (ResultSet resultSetFK = metaData.getImportedKeys(null, null, tableName)) {
            while (resultSetFK.next()) {
                jsonObjectKey = new JSONObject();
                jsonObjectRef = new JSONObject();
                jsonObjectKey.put(Key.KEY_NAME, resultSetFK.getString(FK_NAME_POSITION));
                jsonObjectKey.put(Key.KEY_COLUMNS, resultSetFK.getString(FK_COLUMN_NAME_POSITION));
                jsonObjectRef.put(Key.KEY_REFERENCED_TABLE, resultSetFK.getString(FK_REFERENCED_TABLE_NAME_POSITION));
                jsonObjectRef.put(Key.KEY_REFERENCED_COLUMN, resultSetFK.getString(FK_REFERENCED_COLUMN_POSITION));
                jsonObjectKey.put(Key.KEY_REFERENCE, jsonObjectRef);
                jsonArrayFK.put(jsonObjectKey);
            }
        }
        return jsonArrayFK;
    }

    private JSONArray getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        JSONArray jsonArrayColumns = new JSONArray();
        try (ResultSet resultSetColumns = metaData.getColumns(null, null, tableName, null)) {
            while (resultSetColumns.next()) {
                jsonArrayColumns.put(new JSONObject()
                        .put(Key.COLUMN_NAME, resultSetColumns.getString(COLUMN_NAME_POSITION))
                        .put(Key.COLUMN_TYPE, resultSetColumns.getString(COLUMN_TYPE_POSITION)));
            }
        }
        return jsonArrayColumns;
    }
}
