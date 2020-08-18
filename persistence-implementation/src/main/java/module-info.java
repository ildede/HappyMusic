module cloud.happydev.music.songpersistence {
    requires cloud.happydev.music.entity;
    requires cloud.happydev.music.persistence;
    provides cloud.happydev.music.persistence.Repository with cloud.happydev.music.songpersistence.SongRepository;
    exports cloud.happydev.music.songpersistence;
}
