package common;

import org.apache.commons.codec.digest.DigestUtils;
import repository.NextSingDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Util {
    public static void loadData(String path) {
        //"/Users/josh/IdeaProjects/nextsing/src/main/resources/ng_albums.txt"
        File file = new File(path);
        Scanner in = null;

        try {
            in = new Scanner(file);
            in.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String sql;

        if (path.contains("ng_albums")) {
            sql = "INSERT INTO ng_albums (ng_singers_id, album_name, release_year, record_company) VALUES (" +
                    "(SELECT ng_singers_id FROM ng_singers WHERE \"name\" = ?), ?, ?, ?)";
        } else if (path.contains("ng_singers")) {
            sql = "INSERT INTO ng_singers (\"name\", dob, sex) VALUES (?, CAST(? as DATE), ?)";
        } else if (path.contains("ng_users")) {
            sql = "INSERT INTO ng_users (username, password) VALUES (?, ?)";
        } else {
            throw new RuntimeException("Invalid file");
        }

        while (in.hasNextLine()) {
            String rawText = in.nextLine();
            String[] albumData = rawText.split("\\|");

            try {
                PreparedStatement preparedStatement = NextSingDatabase.getConn().prepareStatement(sql);
                for (int i = 0; i < albumData.length; i++) {
                    if (path.contains("ng_users") && i == 1) {
                        preparedStatement.setObject(i + 1, DigestUtils.sha256Hex(albumData[i].trim()));
                    } else {
                        preparedStatement.setObject(i + 1, albumData[i].trim());
                    }
                }

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
