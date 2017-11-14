package repository;

import model.Session;

public interface SessionRepository {
    Session getSession(String sessionId);

    void createSession(Session session);
}
