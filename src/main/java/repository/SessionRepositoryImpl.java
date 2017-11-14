package repository;

import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRepositoryImpl implements SessionRepository {
    @Override
    public Session getSession(String sessionId) {
        String sql = "SELECT * FROM ng_sessions " +
                "WHERE session_id = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, sessionId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toSession(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createSession(Session session) {
        String sql = "INSERT INTO ng_sessions (ng_users_id, session_id, is_logged_in) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, session.getUserId());
            preparedStatement.setObject(2, session.getSessionId());
            preparedStatement.setObject(3, session.getLoggedIn());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Session toSession(ResultSet rs) {
        Session session = new Session();

        try {
            session.setAuthId(rs.getInt(1));
            session.setUserId(rs.getInt(2));
            session.setSessionId(rs.getString(3));
            session.setLoggedIn(rs.getBoolean(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return session;
    }
}
