module cloud.happydev.music.ui {
    requires cloud.happydev.music.entity;
    requires cloud.happydev.music.songpersistence;
    requires cloud.happydev.music.persistence;
    requires javafx.controls;
    requires javafx.fxml;

    uses cloud.happydev.music.persistence.Repository;
    opens cloud.happydev.music.ui to javafx.fxml;
    exports cloud.happydev.music.ui;
}
