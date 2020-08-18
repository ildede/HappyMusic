package cloud.happydev.music.ui;

import cloud.happydev.music.entity.Song;
import cloud.happydev.music.songpersistence.SongRepository;

import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Map<String, Song> songs = new HashMap<>();
        songs.put("test", new Song("test"));
        songs.put("other", new Song("other"));
        final SongRepository songRepository = new SongRepository(songs);
        songRepository.findAll().forEach(System.out::println);
    }
}
