module cloud.happydev.music.ui {
    requires cloud.happydev.music.entity;
    requires cloud.happydev.music.songpersistence;
    requires cloud.happydev.music.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    uses cloud.happydev.music.persistence.Repository;
    opens cloud.happydev.music.ui to javafx.fxml;
    exports cloud.happydev.music.ui;
}
