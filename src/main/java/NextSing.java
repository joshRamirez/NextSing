import adapter.MiscAdapter;
import adapter.ReadAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Util;
import model.Album;
import model.Singer;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class NextSing {
    public static void main(String[] args) {
        AlbumRepository albumRepository = new AlbumRepositoryImpl();
        SingerRepository singerRepository = new SingerRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        SessionRepository sessionRepository = new SessionRepositoryImpl();

        ReadAdapter databaseRead = new ReadAdapter(albumRepository, singerRepository, userRepository, sessionRepository);
        MiscAdapter databaseMisc = new MiscAdapter(albumRepository, singerRepository, userRepository, sessionRepository);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (isDatabaseSetUp(databaseRead)) {
            initializeDatabase();
        }

        databaseRead.initializeSingers();

        get("/", (req, res) -> {
            return getHtml("target/classes/public/index.html");
        });

        get("/login/", (req, res) -> {
            return getHtml("target/classes/public/index.html");
        });

        get("/search/", (req, res) -> {
            return getHtml("target/classes/public/search.html");
        });

        get("/result/albums/", (req, res) -> {
            return getHtml("target/classes/public/result.html");
        });

        get("/result/singers/", (req, res) -> {
            return getHtml("target/classes/public/result.html");
        });

        get("/albums/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(databaseRead.getAlbums());
        });

        get("/albums.html", (req, res) -> {
            return albumsToHtmlTable(databaseRead.getAlbums());
        });

        get("/albums/names.html", (req, res) -> {
            return albumsToHtml(databaseRead.getAlbums());
        });

        get("/albums/singers/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(databaseRead.getAlbumWithSinger());
        });

        get("/albums/singers/:albumId", (req, res) -> {
            int albumId = Integer.parseInt(req.params("albumId"));
            res.type("application/json");
            return gson.toJson(databaseRead.getAlbumWithSingerByAlbum(albumId));
        });

        get("/singers/albums/:singerId", (req, res) -> {
            int singerId = Integer.parseInt(req.params("singerId"));
            res.type("application/json");
            return gson.toJson(databaseRead.getAlbumWithSingerBySinger(singerId));
        });

        get("/albums/:albumId", (req, res) -> {
            int albumId = Integer.parseInt(req.params("albumId"));
            res.type("application/json");
            return gson.toJson(databaseRead.getAlbum(albumId));
        });

        get("/singers/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(databaseRead.getSingers());
        });

        get("/singers.html", (req, res) -> {
            return singersToHtmlTable(databaseRead.getSingers());
        });

        get("/singers/names.html", (req, res) -> {
            return singersToHtml(databaseRead.getSingers());
        });

        get("/singers/:singerId", (req, res) -> {
            int singerId = Integer.parseInt(req.params("singerId"));
            res.type("application/json");
            return gson.toJson(databaseRead.getSinger(singerId));
        });

        get("/users/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(databaseRead.getUsers());
        });

        get("/users/:userId", (req, res) -> {
            int userId = Integer.parseInt(req.params("userId"));
            res.type("application/json");
            return gson.toJson(databaseRead.getUser(userId));
        });

        post("/login/", (req, res) -> {
            User user = toUser(req.body());

            if (databaseRead.isUser(user)) {
                databaseMisc.signInUser(user, req.session().id());

                res.redirect("/search/");
            } else {
                res.redirect("/login/");
            }

            return "Try Again";
        });

        post("/albums/", (req, res) -> {
            return "success";
        });

        before((req, res) -> {
            System.out.println(req.pathInfo());
        });

        before((req, res) -> {
            if (!(req.pathInfo().equals("/login/") ^ req.pathInfo().equals("/"))) {
                if (!databaseMisc.validateUser(req.session().id())) {
                    res.redirect("/login/");
                    halt();
                }
            }
        });
    }

    private static String singersToHtmlTable(List<Singer> singers) {
        List<String> singerHtml = new ArrayList<>();
        singerHtml.add("<tr><th>Name</th><th>Date of Birth</th><th>Sex</th></tr>");
        for (Singer singer : singers) {
            singerHtml.add("<tr><th>" + singer.getName() + "</th><th>" + singer.getDateOfBirth() + "</th><th>" + singer.getSex() + "</th></tr>");
        }
        return String.join("", singerHtml);
    }

    private static String albumsToHtmlTable(List<Album> albums) {
        List<String> albumHtml = new ArrayList<>();
        albumHtml.add("<tr><th>Name</th><th>Record Company</th><th>Release Year</th></tr>");
        for (Album album : albums) {
            albumHtml.add("<tr><th>" + album.getAlbumName() + "</th><th>" + album.getRecordCompany() + "</th><th>" + album.getReleaseYear() + "</th></tr>");
        }
        return String.join("", albumHtml);
    }

    private static String singersToHtml(List<Singer> singers) {
        List<String> singerHtml = new ArrayList<>();
        for (Singer singer : singers) {
            singerHtml.add("<option value=\"" + singer.getSingerId() + "\">" + singer.getName() + "</option>");
        }
        return String.join("", singerHtml);
    }

    private static String albumsToHtml(List<Album> albums) {
        List<String> albumHtml = new ArrayList<>();
        for (Album album : albums) {
            albumHtml.add("<option value=\"" + album.getAlbumId() + "\">" + album.getAlbumName() + "</option>");
        }
        return String.join("", albumHtml);
    }

    private static String getHtml(String htmlFile) {
        List<String> fileLines = new ArrayList<>();
        try {
            fileLines = Files.readAllLines(Paths.get(htmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.join("", fileLines);
    }

    private static User toUser(String body) {
        String[] credentialList = body.split("&");
        User user = new User();
        user.setUsername(credentialList[0].split("=")[1]);
        user.setPassword(DigestUtils.sha256Hex(credentialList[1].split("=")[1]));

        return user;
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
