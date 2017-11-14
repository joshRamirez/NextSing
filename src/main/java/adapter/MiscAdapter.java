package adapter;

import model.Session;
import model.User;
import repository.AlbumRepository;
import repository.SessionRepository;
import repository.SingerRepository;
import repository.UserRepository;

public class MiscAdapter extends Adapter {

    public MiscAdapter(AlbumRepository albumRepository, SingerRepository singerRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        super(albumRepository, singerRepository, userRepository, sessionRepository);
    }

    public boolean validateUser(String sessionId) {
        if(sessionRepository.getSession(sessionId) == null) {
            return false;
        }

        return sessionRepository.getSession(sessionId).getLoggedIn();
    }

    public void signInUser(User user, String sessionId) {
        Session session = getSession(sessionId);
        user = userRepository.getUser(user);

        if(session == null) {
            session = new Session();
            session.setLoggedIn(true);
            session.setUserId(user.getUserId());
            session.setSessionId(sessionId);
            sessionRepository.createSession(session);
        } else {
            if (session.getLoggedIn() && session.getUserId() != user.getUserId()) {
                //delete all sessions with this user id
                session.setLoggedIn(true);
                sessionRepository.createSession(session);
            } else if (!session.getLoggedIn()) {
                session.setLoggedIn(true);
                sessionRepository.createSession(session);
            }
        }
    }

    private Session getSession(String sessionId) {
        return sessionRepository.getSession(sessionId);
    }
}
