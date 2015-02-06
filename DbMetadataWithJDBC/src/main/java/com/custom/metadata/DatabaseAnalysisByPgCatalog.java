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
 * Created by olga on 05.02.15.
 */
public class DatabaseAnalysisByPgCatalog implements DatabaseAnalysis {

    final String
            COLUMN_NAME_COLUMN = "attr_name",
            COLUMN_TYPE_COLUMN = "data_type",
            PK_CONSTR_NAME = "constr_name",
            PK_COLUMNS = "pk_attr",
            FK_CONSTR_NAME = "fk_constr_name",
            FK_COLUMN_NAME = "fk_attr_name",
            FK_REF_TABLE = "fk_ref_table",
            FK_REF_COLUMN = "fk_ref_attr";

    @Override
    public void createFileWithDatabaseMetadata(String url, Properties props, String outputFile) throws SQLException {

        final String sqlColumn = "SELECT pg_attribute.attname AS " + COLUMN_NAME_COLUMN +
                "                      , format_type(pg_attribute.atttypid, null) AS " + COLUMN_TYPE_COLUMN +
                " FROM pg_class INNER JOIN pg_attribute ON pg_attribute.attrelid = pg_class.oid" +
                " WHERE pg_attribute.attnum > 0 AND pg_class.relname = ?";

        final String sqlPK = "SELECT cn.conname AS " + PK_CONSTR_NAME +
                "                   , atr.attname AS " + PK_COLUMNS +
                " FROM pg_class AS cl, pg_constraint AS cn, pg_attribute AS atr" +
                " WHERE cl.oid = cn.conrelid AND cn.contype = 'p' AND atr.attrelid = cl.oid AND " +
                "       atr.attnum = ANY (cn.conkey) AND cl.relname = ?";

        final String sqlFK = "SELECT DISTINCT inittable.constraintname AS " + FK_CONSTR_NAME +
                "                   , inittable.attname AS " + FK_COLUMN_NAME +
                "                   , pg_class.relname AS " + FK_REF_TABLE +
                "                   , pg_attribute.attname AS " + FK_REF_COLUMN +
                " FROM " +
                "   (SELECT cl.relname AS tablename, cn.conname AS constraintname, atr.attname AS attname, " +
                "           cn.confkey, cn.confrelid " +
                "    FROM pg_class AS cl, pg_constraint AS cn, pg_attribute AS atr " +
                "    WHERE cl.oid = cn.conrelid AND cn.contype = 'f' AND atr.attrelid = cl.oid AND " +
                "          atr.attnum = ANY (cn.conkey) AND cl.relname = ?) \n" +
                "    AS inittable," +
                "    pg_class, pg_attribute" +
                " WHERE  inittable.confrelid = pg_class.oid AND pg_attribute.attrelid = pg_class.oid AND " +
                "       pg_attribute.attnum = ANY (inittable.confkey);";

        try (Connection connection = DriverManager.getConnection(url, props);
             PreparedStatement preparedStatementColumns = connection.prepareStatement(sqlColumn);
             PreparedStatement preparedStatementPK = connection.prepareStatement(sqlPK);
             PreparedStatement preparedStatementFK = connection.prepareStatement(sqlFK)) {

            List<String> tables = getTableNames(connection);
            JSONArray jsonArrayTables = new JSONArray();
            for (String table : tables) {
                jsonArrayTables.put(new JSONObject()
                        .put(Key.TABLE_NAME, table)
                        .put(Key.COLUMNS, getTableColumns(preparedStatementColumns, table))
                        .put(Key.PRIMARY_KEY, getTablePK(preparedStatementPK, table))
                        .put(Key.FOREIGN_KEY, getTableFK(preparedStatementFK, table)));
            }

            try (FileWriter writer = new FileWriter(outputFile)) {
                jsonArrayTables.write(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getTableNames(Connection connection) throws SQLException {
        final String TABLE_NAME = "tablename";
        final String sql = "SELECT " + TABLE_NAME + " FROM pg_catalog.pg_tables " +
                "WHERE schemaname NOT IN ('pg_catalog', 'information_schema')";
        List<String> listTables = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSetTables = statement.executeQuery(sql)) {
            while (resultSetTables.next()) {
                listTables.add(resultSetTables.getString(TABLE_NAME));
            }
        }
        return listTables;
    }

    private JSONArray getTableColumns(PreparedStatement preparedStatement, String tableName) throws SQLException {
        JSONArray jsonArrayColumns = new JSONArray();
        preparedStatement.setString(1, tableName);
        try (ResultSet resultSetColumns = preparedStatement.executeQuery()) {
            while (resultSetColumns.next()) {
                jsonArrayColumns.put(new JSONObject()
                        .put(Key.COLUMN_NAME, resultSetColumns.getString(COLUMN_NAME_COLUMN))
                        .put(Key.COLUMN_TYPE, resultSetColumns.getString(COLUMN_TYPE_COLUMN)));
            }
        }
        return jsonArrayColumns;
    }

    private JSONObject getTablePK(PreparedStatement preparedStatement, String tableName) throws SQLException {
        JSONObject jsonObjectPK = null;
        preparedStatement.setString(1, tableName);
        try (ResultSet resultSetPK = preparedStatement.executeQuery()) {
            JSONArray jsonArrayColumns = new JSONArray();
            while (resultSetPK.next()) {
                if (jsonObjectPK == null) {
                    jsonObjectPK = new JSONObject();
                    jsonObjectPK.put(Key.KEY_NAME, resultSetPK.getString(PK_CONSTR_NAME));
                }
                jsonArrayColumns.put(resultSetPK.getString(PK_COLUMNS));
            }
            if (jsonObjectPK != null) {
                jsonObjectPK.put(Key.KEY_COLUMNS, jsonArrayColumns);
            }
        }
        return jsonObjectPK;
    }

    private JSONArray getTableFK(PreparedStatement preparedStatement, String tableName) throws SQLException {
        JSONArray jsonArrayFK = new JSONArray();
        preparedStatement.setString(1, tableName);
        try (ResultSet resultSetFK = preparedStatement.executeQuery()) {
            while (resultSetFK.next()) {
                jsonArrayFK.put(new JSONObject()
                        .put(Key.KEY_NAME, resultSetFK.getString(FK_CONSTR_NAME))
                        .put(Key.KEY_COLUMNS, resultSetFK.getString(FK_COLUMN_NAME))
                        .put(Key.KEY_REFERENCE, new JSONObject()
                                .put(Key.KEY_REFERENCED_TABLE, resultSetFK.getString(FK_REF_TABLE))
                                .put(Key.KEY_REFERENCED_COLUMN, resultSetFK.getString(FK_REF_COLUMN))));
            }
        }
        return jsonArrayFK;
    }
}














