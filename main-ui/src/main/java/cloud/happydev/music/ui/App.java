package cloud.happydev.music.ui;

import cloud.happydev.music.songpersistence.SongRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class App extends Application {

    private static Scene scene;
    private static SongRepository songRepository;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("HappyMusic App");

        Button button = new Button("Select Directory");

        TextArea textArea = new TextArea();
        System.setOut(new PrintStream(System.out) {
            @Override
            public void write(byte[] buf, int off, int len) {
                super.write(buf, off, len);
                String msg = new String(buf, off, len);
                textArea.setText(textArea.getText() + msg);
            }
        });

        VBox vBox = new VBox(button, textArea);
        Scene scene = new Scene(vBox, 960, 600);

        stage.setScene(scene);
        stage.show();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        button.setOnAction(e -> {
            textArea.setText("");
            File selectedDirectory = directoryChooser.showDialog(stage);

            if (selectedDirectory != null) {
                textArea.setText(textArea.getText() + "List file in: " + selectedDirectory.getAbsolutePath());
                listDirectory(selectedDirectory, 0, textArea);
            } else {
                textArea.setText(textArea.getText() + "No directory chosen.");
            }
        });
    }

    public void listDirectory(File dir, int level, TextArea textArea) {
        File[] firstLevelFiles = dir.listFiles((file, name) ->
                !file.isHidden()
                && !(name.startsWith(".") || name.startsWith("_"))
                && (file.isDirectory() || name.toLowerCase().endsWith(".mp3"))
        );
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                for (int i = 0; i < level; i++) {
                    textArea.setText(textArea.getText() + "\t");
                }
                if (aFile.isDirectory()) {
                    textArea.setText(textArea.getText() + "[" + aFile.getName() + "]\n");
                    if (level < 5) {
                        listDirectory(aFile, level + 1, textArea);
                    }
                } else {
                    textArea.setText(textArea.getText() + aFile.getName() + "\n");
                }
            }
        }
    }

    static void setRoot(String fxml) throws IOException {
        System.out.println("Song = " + songRepository.findByTitle(fxml));
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
