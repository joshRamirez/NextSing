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
