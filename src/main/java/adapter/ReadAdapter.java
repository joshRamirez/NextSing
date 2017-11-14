package adapter;

import common.SingerCache;
import model.Album;
import model.AlbumWithSinger;
import model.Singer;
import model.User;
import repository.AlbumRepository;
import repository.SessionRepository;
import repository.SingerRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ReadAdapter extends Adapter {
    SingerCache singerCache = new SingerCache();

    public ReadAdapter(AlbumRepository albumRepository, SingerRepository singerRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        super(albumRepository, singerRepository, userRepository, sessionRepository);
    }

    public void initializeSingers() {
        singerRepository.getSingers().forEach((singer) -> singerCache.getSingerCache().put(singer.getSingerId(), singer));
        singerCache.setCached(true);
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

    public Singer getSinger(int singerId) {
        return singerRepository.getSinger(singerId);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(int userId) {
        return userRepository.getUser(userId);
    }

    public boolean isUser(User user) {
        return userRepository.getUser(user) != null;
    }

    public List<AlbumWithSinger> getAlbumWithSinger() {
        List<Album> albums = albumRepository.getAlbums();
        List<AlbumWithSinger> albumSinger = new ArrayList<>();

        if (!singerCache.isCached()) {
            initializeSingers();
        }

        for (Album album : albums) {
            AlbumWithSinger albumWithSinger = new AlbumWithSinger();
            albumWithSinger.setAlbum(album);
            albumWithSinger.setSinger(singerCache.getSingerCache().get(album.getSingerId()));
            albumSinger.add(albumWithSinger);
        }

        return albumSinger;
    }

    public List<AlbumWithSinger> getAlbumWithSingerByAlbum(int albumId) {
        Album album = albumRepository.getAlbum(albumId);
        Singer singer;
        List albumWithSinger = new ArrayList();
        if (singerCache.isCached()) {
            singer = singerCache.getSingerCache().get(album.getSingerId());
        } else {
            singer = singerRepository.getSinger(album.getSingerId());
        }
        albumWithSinger.add(new AlbumWithSinger(album, singer));

        return albumWithSinger;
    }

    public List<AlbumWithSinger> getAlbumWithSingerBySinger(int singerId) {
        Singer singer = singerRepository.getSinger(singerId);
        List<Album> albums = albumRepository.getAlbumsBySinger(singerId);
        List albumWithSinger = new ArrayList();
        albumWithSinger.add(new AlbumWithSinger(albums, singer));

        return albumWithSinger;
    }
}
