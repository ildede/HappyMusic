package cloud.happydev.music.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SongTest {

    @Test
    void A_SONG_HAS_A_TITLE() {
        final Song song = new Song("aTitle");

        assertEquals("aTitle", song.getTitle());
    }
}
