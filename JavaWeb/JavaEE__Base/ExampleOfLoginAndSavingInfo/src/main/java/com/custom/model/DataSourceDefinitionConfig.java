package com.custom.model;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

/**
 * Created by olga on 15.05.15.
 */
@DataSourceDefinition(
        name = "java:app/MyApp/MyDS",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:mem:test")
@Stateless
public class DataSourceDefinitionConfig {
}
