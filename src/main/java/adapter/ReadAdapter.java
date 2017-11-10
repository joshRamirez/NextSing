package adapter;

import model.Album;
import model.Singer;
import model.User;
import repository.AlbumRepository;
import repository.SingerRepository;
import repository.UserRepository;

import java.util.List;

public class ReadAdapter extends Adapter {
    public ReadAdapter(AlbumRepository albumRepository, SingerRepository singerRepository, UserRepository userRepository) {
        super(albumRepository, singerRepository, userRepository);
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

    public List<Singer> getSingers() {
        return singerRepository.getSingers();
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

}
