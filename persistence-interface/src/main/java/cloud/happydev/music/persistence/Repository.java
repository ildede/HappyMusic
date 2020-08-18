package cloud.happydev.music.persistence;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> findByTitle(String title);
    List<T> findAll();
}
