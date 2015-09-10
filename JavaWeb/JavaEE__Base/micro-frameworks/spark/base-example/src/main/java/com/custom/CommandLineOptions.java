package com.custom;

import com.beust.jcommander.Parameter;

/**
 * Created by olga on 13.07.15.
 */
class CommandLineOptions {

    @Parameter(names = "--debug")
    boolean debug = false;

    @Parameter(names = {"--service-port"})
    Integer servicePort = 4567;

    @Parameter(names = {"--database"})
    String database = "blog";

    @Parameter(names = {"--db-host"})
    String dbHost = "localhost";

    @Parameter(names = {"--db-username"})
    String dbUsername = "root";

    @Parameter(names = {"--db-password"})
    String dbPassword = "root";

    @Parameter(names = {"--db-port"})
    Integer dbPort = 3306;


}