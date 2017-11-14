package model;

import java.util.ArrayList;
import java.util.List;

public class AlbumWithSinger {
    List<Album> album;
    Singer singer;

    public AlbumWithSinger() {
        this.album = new ArrayList<>();
    }

    public AlbumWithSinger(Album album, Singer singer) {
        this.album = new ArrayList<>();
        this.album.add(album);
        this.singer = singer;
    }

    public AlbumWithSinger(List<Album> album, Singer singer) {
        this.album = album;
        this.singer = singer;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album.add(album);
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }
}
