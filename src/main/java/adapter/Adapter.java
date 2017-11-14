package adapter;

import repository.AlbumRepository;
import repository.SessionRepository;
import repository.SingerRepository;
import repository.UserRepository;

public abstract class Adapter {
    AlbumRepository albumRepository;
    SingerRepository singerRepository;
    UserRepository userRepository;
    SessionRepository sessionRepository;

    public Adapter(AlbumRepository albumRepository, SingerRepository singerRepository, UserRepository userRepository, SessionRepository sessionRepository){
        this.albumRepository = albumRepository;
        this.singerRepository = singerRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }
}
