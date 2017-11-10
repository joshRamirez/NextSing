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

    private Singer toSinger(ResultSet rs) {
        Singer singer = new Singer();

        try {
            singer.setSingerId(rs.getInt(1));
            singer.setDateOfBirth(rs.getString(2));
            singer.setName(rs.getString(3));
            singer.setSex(rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return singer;
    }
}
