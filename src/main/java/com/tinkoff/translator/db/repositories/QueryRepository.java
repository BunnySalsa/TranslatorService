package com.tinkoff.translator.db.repositories;

import com.tinkoff.translator.db.entities.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class QueryRepository {

    private static final int NUMBER_OF_ANSWER_TIME_COLUMN = 1;
    private static final int NUMBER_OF_CLIENT_IP_COLUMN = 2;
    private static final String PS_INSERT_REQUEST = "INSERT INTO translator_scheme.request" +
            "(answer_time,client_ip) VALUES (?,?)";
    private Connection connection;

    public QueryRepository(@Autowired Connection connection) {
        this.connection = connection;
    }


    public boolean save(RequestEntity entity) {
        try (PreparedStatement statement = connection.prepareStatement(PS_INSERT_REQUEST,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(NUMBER_OF_ANSWER_TIME_COLUMN, entity.getAnswerTime());
            statement.setString(NUMBER_OF_CLIENT_IP_COLUMN, entity.getClientIp());
            if (statement.executeUpdate() > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getLong(1));
                return true;
            }
            return false;
        } catch (SQLException exception) {
            return false;
        }
    }
}
