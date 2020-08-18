package cloud.happydev.music.songpersistence;

import cloud.happydev.music.entity.Song;
import cloud.happydev.music.persistence.Repository;

import java.util.*;

public class SongRepository implements Repository<Song> {

    private final Map<String, Song> songs;

    public SongRepository(Map<String, Song> songs) {
        this.songs = songs;
    }

    public SongRepository() {
        this.songs = new HashMap<>();
        this.songs.put("primary", new Song("Primary song"));
        this.songs.put("secondary", new Song("Secondary song"));
    }

    @Override
    public Optional<Song> findByTitle(String title) {
        return Optional.ofNullable(songs.getOrDefault(title, null));
    }

    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songs.values());
    }
}
