package adapter;

import repository.AlbumRepository;
import repository.SingerRepository;
import repository.UserRepository;

public abstract class Adapter {
    AlbumRepository albumRepository;
    SingerRepository singerRepository;
    UserRepository userRepository;

    public Adapter(AlbumRepository albumRepository, SingerRepository singerRepository, UserRepository userRepository){
        this.albumRepository = albumRepository;
        this.singerRepository = singerRepository;
        this.userRepository = userRepository;
    }
}
