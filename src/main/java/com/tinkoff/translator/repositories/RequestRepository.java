package com.tinkoff.translator.repositories;

import com.tinkoff.translator.entities.RequestEntity;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class RequestRepository {

    private static final int NUMBER_OF_ID_COLUMN = 1;
    private static final int NUMBER_OF_REQUEST_TIME_COLUMN = 1;
    private static final int NUMBER_OF_CLIENT_IP_COLUMN = 2;
    private static final String PS_INSERT_REQUEST = "INSERT INTO request" +
            "(request_time,client_ip) VALUES (?,?)";
    private final Connection connection;

    public boolean save(RequestEntity entity) {
        try (PreparedStatement statement = connection.prepareStatement(PS_INSERT_REQUEST,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(NUMBER_OF_REQUEST_TIME_COLUMN, entity.getRequestTime().format(DateTimeFormatter.ISO_DATE_TIME));
            statement.setString(NUMBER_OF_CLIENT_IP_COLUMN, entity.getClientIp());
            if (statement.executeUpdate() > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getLong(NUMBER_OF_ID_COLUMN));
                return true;
            }
            return false;
        } catch (SQLException exception) {
            return false;
        }
    }
}
