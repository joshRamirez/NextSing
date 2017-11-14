package repository;

import model.Album;

import java.util.List;

public interface AlbumRepository {
    Album getAlbum(int albumId);

    List<Album> getAlbums();

    Album getAlbumByName(String name);

    List<Album> getAlbumsBySinger(int singerId);
}
