import adapter.ReadAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Album;
import repository.*;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class NextSing {
    public static void main(String[] args) {
        AlbumRepository albumRepository = new AlbumRepositoryImpl();
        SingerRepository singerRepository = new SingerRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();

        ReadAdapter databaseRead = new ReadAdapter(albumRepository, singerRepository, userRepository);

        if (isDatabaseSetUp(databaseRead)) {
            initializeDatabase();
        }

        get("/albums/", (req, res) -> {
            return new Gson().toJson(databaseRead.getAlbums());
        });

        post("/albums/", (req, res) -> {
            List<Album> test = new Gson().fromJson("[{\"albumId\":187,\"singerId\":102,\"albumName\":\"BAD\",\"releaseYear\":\"1980\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":188,\"singerId\":103,\"albumName\":\"BROWNIE\",\"releaseYear\":\"1956\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":189,\"singerId\":104,\"albumName\":\"A MAJOR\",\"releaseYear\":\"2001\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":190,\"singerId\":105,\"albumName\":\"FIRST TIME\",\"releaseYear\":\"1999\",\"recordCompany\":\"WARNER BROS. RECORDS\"},{\"albumId\":191,\"singerId\":106,\"albumName\":\"MY HEART\",\"releaseYear\":\"1997\",\"recordCompany\":\"EPIC RECORDS\"},{\"albumId\":192,\"singerId\":107,\"albumName\":\"DO IT LIKE\",\"releaseYear\":\"1985\",\"recordCompany\":\"CAPITOL RECORDS\"},{\"albumId\":193,\"singerId\":108,\"albumName\":\"I WONDER\",\"releaseYear\":\"1978\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":194,\"singerId\":109,\"albumName\":\"AMERICA\",\"releaseYear\":\"2005\",\"recordCompany\":\"COLUMBIA RECORDS\"},{\"albumId\":195,\"singerId\":110,\"albumName\":\"REPLACE\",\"releaseYear\":\"2010\",\"recordCompany\":\"ATLANTIC RECORDS\"},{\"albumId\":196,\"singerId\":111,\"albumName\":\"WILSON\",\"releaseYear\":\"2000\",\"recordCompany\":\"INTERSCOPE RECORDS\"},{\"albumId\":197,\"singerId\":102,\"albumName\":\"BEAT IT\",\"releaseYear\":\"1982\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":198,\"singerId\":103,\"albumName\":\"JIMMY\",\"releaseYear\":\"1960\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":199,\"singerId\":104,\"albumName\":\"B MAJOR\",\"releaseYear\":\"2002\",\"recordCompany\":\"COLUMBIA RECORDS\"},{\"albumId\":200,\"singerId\":105,\"albumName\":\"ONE MORE\",\"releaseYear\":\"2003\",\"recordCompany\":\"WARNER BROS. RECORDS\"},{\"albumId\":201,\"singerId\":106,\"albumName\":\"WILL GO ON\",\"releaseYear\":\"1998\",\"recordCompany\":\"UNIVERSAL RECORDS\"},{\"albumId\":202,\"singerId\":107,\"albumName\":\"MICKIE\",\"releaseYear\":\"1989\",\"recordCompany\":\"EPIC RECORDS\"},{\"albumId\":203,\"singerId\":108,\"albumName\":\"STEPHEN\",\"releaseYear\":\"1979\",\"recordCompany\":\"MOTOWN RECORDS CORP\"},{\"albumId\":204,\"singerId\":109,\"albumName\":\"GOD BLESS\",\"releaseYear\":\"2007\",\"recordCompany\":\"CAPITAL RECORDS\"},{\"albumId\":205,\"singerId\":110,\"albumName\":\"IRREPALCE\",\"releaseYear\":\"2004\",\"recordCompany\":\"COLUMBIA RECORDS\"},{\"albumId\":206,\"singerId\":111,\"albumName\":\"HIGH SCHOOL\",\"releaseYear\":\"2002\",\"recordCompany\":\"ATLANTIC RECORDS\"},{\"albumId\":207,\"singerId\":102,\"albumName\":\"BLACK OR WHITE\",\"releaseYear\":\"1998\",\"recordCompany\":\"INTERSCOPE RECORDS\"},{\"albumId\":208,\"singerId\":103,\"albumName\":\"JAMES BROWN\",\"releaseYear\":\"1970\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":209,\"singerId\":104,\"albumName\":\"C MAJOR\",\"releaseYear\":\"2005\",\"recordCompany\":\"MOTOWN RECORD CORP\"},{\"albumId\":210,\"singerId\":105,\"albumName\":\"I\\u0027M BACK\",\"releaseYear\":\"2005\",\"recordCompany\":\"COLUMBIA RECORDS\"},{\"albumId\":211,\"singerId\":106,\"albumName\":\"PRAYER\",\"releaseYear\":\"1998\",\"recordCompany\":\"WARNER BROS. RECORDS\"},{\"albumId\":212,\"singerId\":107,\"albumName\":\"MICK JAGGER\",\"releaseYear\":\"1990\",\"recordCompany\":\"UNIVERSAL RECORDS\"},{\"albumId\":213,\"singerId\":108,\"albumName\":\"WONDERFUL\",\"releaseYear\":\"1984\",\"recordCompany\":\"EPIC RECORDS\"},{\"albumId\":214,\"singerId\":109,\"albumName\":\"U.S.A.\",\"releaseYear\":\"2009\",\"recordCompany\":\"MOTOWN RECORDS CORP\"},{\"albumId\":215,\"singerId\":110,\"albumName\":\"TO THE LEFT\",\"releaseYear\":\"2010\",\"recordCompany\":\"CAPITAL RECORDS\"},{\"albumId\":216,\"singerId\":111,\"albumName\":\"WILDCATS\",\"releaseYear\":\"2011\",\"recordCompany\":\"MOTOWN RECORDS CORP\"}]",
                    new TypeToken<List<Album>>() {
                    }.getType());
            return "success";
        });

        System.out.println(databaseRead.getAlbums().toString());
        System.out.println(databaseRead.getAlbumByName("ONE MORE"));

    }

    private static boolean isDatabaseSetUp(ReadAdapter databaseRead) {
        return databaseRead.getAlbums().size() == 0 && databaseRead.getSingers().size() == 0 &&
                databaseRead.getUsers().size() == 0;
    }

    private static void initializeDatabase() {
        Util.loadData("target/classes/ng_singers.txt");
        Util.loadData("target/classes/ng_albums.txt");
        Util.loadData("target/classes/ng_users.txt");
    }
}
