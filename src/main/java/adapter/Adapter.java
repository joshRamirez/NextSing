package adapter;

import repository.AlbumRepository;

public abstract class Adapter {
    AlbumRepository albumRepository;

    public Adapter(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
    }
}
