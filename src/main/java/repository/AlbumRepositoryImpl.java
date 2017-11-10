package repository;

import model.Album;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepository {
    public Album getAlbum(int albumId) {
        String sql = "SELECT * FROM ng_albums " +
                "WHERE ng_albums_id = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, albumId);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return toAlbum(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Album> getAlbums() {
        String sql = "SELECT * FROM ng_albums";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List albums = new ArrayList();

            while (rs.next()) {
                albums.add(toAlbum(rs));
            }

            return albums;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Album getAlbumByName(String name) {
        String sql = "SELECT * FROM ng_albums " +
                "WHERE album_name = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, name);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return toAlbum(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
