package repository;

import model.Singer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SingerRepositoryImpl implements SingerRepository {
    @Override
    public List<Singer> getSingers() {
        String sql = "SELECT * FROM ng_singers";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            List singers = new ArrayList();

            while (rs.next()) {
                singers.add(toSinger(rs));
            }

            return singers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("No singers");
    }

    @Override
    public Singer getSinger(int singerId) {
        String sql = "SELECT * FROM ng_singers " +
                "WHERE ng_singers_id = ?";

        try {
            PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
            preparedStatement.setObject(1, singerId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return toSinger(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Singer does not exist");
    }

    private Singer toSinger(ResultSet rs) {
        Singer singer = new Singer();

        try {
            singer.setSingerId(rs.getInt(1));
            singer.setName(rs.getString(2));
            singer.setDateOfBirth(rs.getString(3));
            singer.setSex(rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return singer;
    }
}
