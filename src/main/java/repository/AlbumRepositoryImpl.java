package repository;

import model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static repository.NextSingDatabase.getConn;

public class AlbumRepositoryImpl implements AlbumRepository {
    public List<Album> getAlbums() {
        String sql = "SELECT * FROM ng_albums";

        try (Connection conn = getConn()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List albums = new ArrayList();

            while (rs.next()) {
                albums.add(toAlbum(rs));
            }

            return albums;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("No albums");
    }

    public Album getAlbum(int albumId) {
        String sql = "SELECT * FROM ng_albums " +
                "WHERE ng_albums_id = ?";

        try {
            PreparedStatement preparedStatement = getConn().prepareStatement(sql);
            preparedStatement.setObject(1, albumId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toAlbum(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Album does not exist");
    }

    public Album getAlbumByName(String name) {
        String sql = "SELECT * FROM ng_albums " +
                "WHERE album_name = ?";

        try {
            PreparedStatement preparedStatement = getConn().prepareStatement(sql);
            preparedStatement.setObject(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toAlbum(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Album does not exist");
    }

    @Override
    public List<Album> getAlbumsBySinger(int singerId) {
        String sql = "SELECT * FROM ng_albums " +
                "WHERE ng_singers_id = ?";

        List albums = new ArrayList();
        try {
            PreparedStatement preparedStatement = getConn().prepareStatement(sql);
            preparedStatement.setObject(1, singerId);

            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                albums.add(toAlbum(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return albums;
    }

    private Album toAlbum(ResultSet rs) {
        Album album = new Album();

        try {
            album.setAlbumId(rs.getInt(1));
            album.setSingerId(rs.getInt(2));
            album.setAlbumName(rs.getString(3));
            album.setReleaseYear(rs.getString(4));
            album.setRecordCompany(rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return album;
    }
}
