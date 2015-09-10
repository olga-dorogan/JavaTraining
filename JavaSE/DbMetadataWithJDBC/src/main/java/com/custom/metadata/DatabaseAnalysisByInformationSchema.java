package com.custom.metadata;

import com.custom.metadata.output.Key;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by olga on 04.02.15.
 */
public class DatabaseAnalysisByInformationSchema implements DatabaseAnalysis {

    private PreparedStatement preparedStatementPK;
    private PreparedStatement preparedStatementFK;
    private PreparedStatement preparedStatementColumns;

    final String
            COLUMN_NAME_COLUMN = "column_name",
            COLUMN_TYPE_COLUMN = "data_type",
            CONSTRAINT_NAME_COLUMN = "constraint_name",
            FK_CONSTRAINT_NAME = "fk_constraint_name",
            FK_COLUMN_NAME = "fk_column_name",
            FK_REFERENCED_TABLE_NAME = "referenced_table_name",
            FK_REFERENCED_COLUMN_NAME = "referenced_column_name";

    @Override
    public void createFileWithDatabaseMetadata(String url, Properties props, String outputFile) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, props)) {
            createFile(connection, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(Connection connection, String outputFile) throws IOException, SQLException {
        JSONArray jsonArray = new JSONArray();
        List<String> tableNames = getTableNames(connection);
        try {
            prepareStatements(connection);
            for (String table : tableNames) {
                jsonArray.put(new JSONObject()
                        .put(Key.TABLE_NAME, table)
                        .put(Key.COLUMNS, getColumnNames(table))
                        .put(Key.PRIMARY_KEY, getPrimaryKey(table))
                        .put(Key.FOREIGN_KEY, getForeignKeys(table)));
            }
        } finally {
            closeStatements();
        }

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            jsonArray.write(fileWriter);
        }
    }

    private List<String> getTableNames(Connection connection) throws SQLException {
        final String TABLE_NAME_COLUMN = "table_name";
        final String sql = "SELECT " + TABLE_NAME_COLUMN +
                " FROM information_schema.tables " +
                " WHERE table_schema NOT IN ('pg_catalog', 'information_schema')";
        List<String> tableNames = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSetTables = statement.executeQuery(sql)) {
            while (resultSetTables.next()) {
                tableNames.add(resultSetTables.getString(TABLE_NAME_COLUMN));
            }
        }
        return tableNames;
    }

    private void prepareStatements(Connection connection) throws SQLException {
        final String sqlColumns = "SELECT " + COLUMN_NAME_COLUMN + ", " + COLUMN_TYPE_COLUMN +
                " FROM information_schema.columns " +
                " WHERE table_name = ?";
        final String sqlPK = "SELECT " + COLUMN_NAME_COLUMN + ", " + CONSTRAINT_NAME_COLUMN +
                " FROM information_schema.table_constraints NATURAL INNER JOIN information_schema.key_column_usage " +
                " WHERE constraint_type = 'PRIMARY KEY' AND table_name = ?";
        final String sqlFK = "SELECT  DISTINCT " +
                "     kcu1.constraint_name AS " + FK_CONSTRAINT_NAME +
                "    ,kcu1.table_name AS fk_table_name " +
                "    ,kcu1.column_name AS " + FK_COLUMN_NAME +
                "    ,kcu2.table_name AS " + FK_REFERENCED_TABLE_NAME +
                "    ,kcu2.column_name AS " + FK_REFERENCED_COLUMN_NAME +
                " FROM information_schema.referential_constraints AS rc " +
                "   INNER JOIN information_schema.key_column_usage AS kcu1 " +
                "       ON kcu1.constraint_catalog = rc.constraint_catalog  " +
                "       AND kcu1.constraint_schema = rc.constraint_schema " +
                "       AND kcu1.constraint_name = rc.constraint_name " +
                "   INNER JOIN information_schema.key_column_usage AS kcu2 " +
                "       ON kcu2.constraint_catalog = rc.unique_constraint_catalog  " +
                "        AND kcu2.constraint_schema = rc.unique_constraint_schema " +
                "       AND kcu2.constraint_name = rc.unique_constraint_name " +
                "       AND kcu2.ordinal_position = kcu1.ordinal_position " +
                " WHERE kcu1.table_name = ? AND kcu2.table_name != ?";
        preparedStatementColumns = connection.prepareStatement(sqlColumns);
        preparedStatementPK = connection.prepareStatement(sqlPK);
        preparedStatementFK = connection.prepareStatement(sqlFK);
    }

    private void closeStatements() throws SQLException {
        if (preparedStatementColumns != null) {
            preparedStatementColumns.close();
        }
        if (preparedStatementPK != null) {
            preparedStatementPK.close();
        }
        if (preparedStatementFK != null) {
            preparedStatementFK.close();
        }
    }

    private JSONArray getColumnNames(String tableName) throws SQLException {
        JSONArray jsonArrayColumns = new JSONArray();
        preparedStatementColumns.setString(1, tableName);
        try (ResultSet resultSetColumns = preparedStatementColumns.executeQuery()) {
            while (resultSetColumns.next()) {
                jsonArrayColumns.put(new JSONObject()
                        .put(Key.COLUMN_NAME, resultSetColumns.getString(COLUMN_NAME_COLUMN))
                        .put(Key.COLUMN_TYPE, resultSetColumns.getString(COLUMN_TYPE_COLUMN)));
            }
        }
        return jsonArrayColumns;
    }

    private JSONObject getPrimaryKey(String tableName) throws SQLException {
        JSONArray jsonArrayPKColumns = new JSONArray();
        JSONObject jsonObjectPK = null;
        preparedStatementPK.setString(1, tableName);
        try (ResultSet resultSetColumns = preparedStatementPK.executeQuery()) {
            while (resultSetColumns.next()) {
                if (jsonObjectPK == null) {
                    jsonObjectPK = new JSONObject();
                    jsonObjectPK.put(Key.KEY_NAME, resultSetColumns.getString(CONSTRAINT_NAME_COLUMN));
                }
                jsonArrayPKColumns.put(resultSetColumns.getString(COLUMN_NAME_COLUMN));
            }
            if (jsonObjectPK != null) {
                jsonObjectPK.put(Key.COLUMNS, jsonArrayPKColumns);
            }
        }
        return jsonObjectPK;
    }

    private JSONArray getForeignKeys(String tableName) throws SQLException {
        JSONArray jsonArrayFK = new JSONArray();
        preparedStatementFK.setString(1, tableName);
        preparedStatementFK.setString(2, tableName);
        try (ResultSet resultSetFK = preparedStatementFK.executeQuery()) {
            while (resultSetFK.next()) {
                jsonArrayFK.put(new JSONObject()
                        .put(Key.KEY_NAME, resultSetFK.getString(FK_CONSTRAINT_NAME))
                        .put(Key.KEY_COLUMNS, resultSetFK.getString(FK_COLUMN_NAME))
                        .put(Key.KEY_REFERENCE, new JSONObject()
                                .put(Key.KEY_REFERENCED_COLUMN, resultSetFK.getString(FK_REFERENCED_COLUMN_NAME))
                                .put(Key.KEY_REFERENCED_TABLE, resultSetFK.getString(FK_REFERENCED_TABLE_NAME))));
            }
        }
        return jsonArrayFK;
    }
}
