package cloud.happydev.music.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.Desktop.getDesktop;
import static java.lang.String.join;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("HappyMusic App");

        Label label = new Label("Remove all duplicated mp3 files. A file is considered duplicated if it has the same file name.\n" +
                "1. Case insensitive.\n" +
                "2. Spaces is not considered.\n" +
                "3. Final part with parenthesis with number inside is not considered.\n" +
                "\n" +
                "So, 'a test.mp3', 'atest.mp3', 'aTest.mp3', 'a Test (1).mp3' are considered all the same file.");

        Button button = new Button("Select Directory");

        TextArea textArea = new TextArea();

        VBox vBox = new VBox(label, button, textArea);
        Scene scene = new Scene(vBox, 960, 600);

        stage.setScene(scene);
        stage.show();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        button.setOnAction(e -> {
            textArea.setText("");
            File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                textArea.setText(textArea.getText() + "Duplicated in <" + selectedDirectory.getAbsolutePath() + ">\n" +
                        "Moved to trash.\n\n");
                List<String> removed = duplicatedNames(selectedDirectory);
                if (removed.isEmpty()) {
                    textArea.setText(textArea.getText() + "No file removed.");
                } else {
                    textArea.setText(textArea.getText() + join("\n", removed));
                }
            } else {
                textArea.setText(textArea.getText() + "No directory chosen.");
            }
        });
    }

    public List<String> duplicatedNames(File dir) {
        File[] firstLevelFiles = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".mp3"));
        List<String> removed = new ArrayList<>();
        Map<String, File> checkedFiles = new HashMap<>();
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                final String cleanName = aFile.getName()
                        .toLowerCase()
                        .replace(".mp3", "")
                        .replace(" ", "")
                        .replaceAll("[(]\\d+[)]", "");

                if (checkedFiles.containsKey(cleanName)) {
                    if (aFile.length() > checkedFiles.get(cleanName).length()) {
                        final File oldFile = checkedFiles.get(cleanName);

                        if (deleteFile(oldFile)) {
                            removed.add(oldFile.getName());
                        }
                        checkedFiles.put(cleanName, aFile);
                    } else {
                        if (deleteFile(aFile)) {
                            removed.add(aFile.getName());
                        }
                    }
                } else {
                    checkedFiles.put(cleanName, aFile);
                }
            }
        }
        return removed;
    }

    public static boolean deleteFile(File file) {
        boolean success = false;
        Desktop desktop = getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.MOVE_TO_TRASH)) {
            try {
                success = desktop.moveToTrash(file);
            } catch (Exception e) {
                success = false; // We'll just do a hard delete
            }
        }
        if (!success) {
            success = file.delete();
        }
        return success;
    }

    static void setRoot(String fxml) throws IOException {
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
