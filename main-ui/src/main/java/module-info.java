module cloud.happydev.music.ui {
    requires cloud.happydev.music.entity;
    requires cloud.happydev.music.songpersistence;
    requires cloud.happydev.music.persistence;
    uses cloud.happydev.music.persistence.Repository;
}
