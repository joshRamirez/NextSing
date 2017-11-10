package repository;

import model.Album;

import java.util.List;

public interface AlbumRepository {
    public Album getAlbum(int albumId);

    public List<Album> getAlbums();

    public Album getAlbumByName(String name);
}
