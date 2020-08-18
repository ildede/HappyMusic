package cloud.happydev.music.songpersistence;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongRepositoryTest {

    @Test
    public void DEFAULT_REPOSITORY_HAS_TWO_SONG() {
        final SongRepository songRepository = new SongRepository();
        assertEquals(2, songRepository.findAll().size());
    }

    @Test
    public void WRONG_TITLE_RETURN_EMPTY_OPTIONAL() {
        final SongRepository songRepository = new SongRepository();
        assertEquals(Optional.empty(), songRepository.findByTitle("any"));
    }
}
