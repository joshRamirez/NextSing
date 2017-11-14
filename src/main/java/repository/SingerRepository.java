package repository;

import model.Singer;

import java.util.List;

public interface SingerRepository {
    List<Singer> getSingers();

    Singer getSinger(int singerId);
}
