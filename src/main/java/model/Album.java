package model;

public class Album {
    int albumId;
    int singerId;
    String albumName;
    String releaseYear;
    String recordCompany;

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public int getSingerId() {
        return singerId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRecordCompany() {
        return recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    @Override
    public String toString() {
        return String.format("Album(%s, %s, %s, %s, %s)", getAlbumId(), getSingerId(), getAlbumName(), getReleaseYear(), getRecordCompany());
    }
}
