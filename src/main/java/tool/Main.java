package tool;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {

    @FXML
    private ChoiceBox<String> cbType;
    @FXML
    private TextField tfHash;

    @FXML
    public void handleButtonAction(ActionEvent e) {
        String type = cbType.getValue();
        String hash = tfHash.getText();
        if (hash == null || !hash.matches("[a-zA-Z0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please input a valid hash");
            alert.show();
        } else {
            String header;
            switch (type) {
                case "MD5":
                    header = "urn:md5:";
                    break;
                case "SHA-1":
                    header = "urn:sha1:";
                    break;
                case "eD2k":
                    header = "urn:ed2k:";
                    break;
                default:
                    header = "urn:btih:";
            }
            String magnetLink = "magnet:?xt=" + header + hash;

            // copy magnet link to system clipboard
            // https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
            StringSelection stringSelection = new StringSelection(magnetLink);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            tfHash.setText(null);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Magnetie: A Magnet Link Generator");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("icon.png"))));
        GridPane gridPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main.fxml")));
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
