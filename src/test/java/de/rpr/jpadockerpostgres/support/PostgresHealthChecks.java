/*
 * Copyright 2017 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rpr.jpadockerpostgres.support;

import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Michael J. Simons, 2017-11-06
 */
public final class PostgresHealthChecks {

  public static SuccessOrFailure canConnectTo(
      final Container container, final String dbUser, final String dbPassword, final String dbName) {
    SuccessOrFailure rv;

    try (
        Connection connection = DriverManager.getConnection(container.port(5432)
            .inFormat("jdbc:postgresql://$HOST:$EXTERNAL_PORT/" + dbName + "?loggerLevel=OFF"), dbUser, dbPassword)
    ) {
      rv = SuccessOrFailure.success();
    } catch (SQLException e) {
      rv = SuccessOrFailure.failureWithCondensedException("Connection not yet ready", e);
    }
    return rv;
  }
}
