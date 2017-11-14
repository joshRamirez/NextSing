package repository;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM ng_users";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List users = new ArrayList();

            while (rs.next()) {
                users.add(toUser(rs));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("No users");
    }

    @Override
    public User getUser(int userId) {
        String sql = "SELECT * FROM ng_users " +
                "WHERE ng_users_id = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("User does not exist");
    }

    @Override
    public User getUser(User user) {
        String sql = "SELECT * FROM ng_users " +
                "WHERE username = ?" +
                "AND password = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, user.getUsername());
            preparedStatement.setObject(2, user.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User toUser(ResultSet rs) {
        User user = new User();

        try {
            user.setUserId(rs.getInt(1));
            user.setUsername(rs.getString(3));
            user.setPassword(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
