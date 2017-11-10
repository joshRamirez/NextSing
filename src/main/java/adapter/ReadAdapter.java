package adapter;

import model.Album;
import repository.AlbumRepository;

import java.util.List;

public class ReadAdapter extends Adapter {
    public ReadAdapter(AlbumRepository albumRepository) {
        super(albumRepository);
    }

    public Album getAlbum(int albumId) {
        return albumRepository.getAlbum(albumId);
    }

    public List<Album> getAlbums() {
        return albumRepository.getAlbums();
    }

    public Album getAlbumByName(String name) {
        return albumRepository.getAlbumByName(name);
    }


}
