package sample;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller2 {

    @FXML BorderPane BorderId;
    static private HashMap<String, Pane> screenMap = new HashMap<>();
    static private Scene main;
    private Node component;


    public Controller2() {

    }

    public Controller2(Scene main, Stage primaryStage) {
        this.main = main;
        primaryStage.setScene(main);
    }

    protected void addScreen(String name, Pane pane) {

        screenMap.put(name, pane);
    }

    protected void removeScreen(String name) {

        screenMap.remove(name);
    }

    protected void activate(String name) {

        main.setRoot(screenMap.get(name));
    }

    public void test(ActionEvent e) {
        System.out.println("asdf");
    }

    public void changeScene(ActionEvent e) {

        System.out.println("asdfssfa");
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1000), BorderId);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);

        Node src = (Node)e.getSource();
        System.out.println(src);
        String id = src.getId();

        fadeOutTransition.setOnFinished( p -> fadeIn(id));
        fadeOutTransition.play();
    }

    public void fadeIn(String id) {
        try {
            System.out.println(id);
            String targetXML;
            switch (id) {
                case "LogInButtonId":

                    targetXML = "MainMenuView";
                    break;
                case "RegisterButtonId":
                    targetXML = "register";
                    break;
                case "BackButtonId":
                    System.out.println("jestem w back: " + id);
                    targetXML = "LoginView";
                    break;
                default:
                    targetXML = "LoginScreenView";
                    break;
            }

            //activate(targetXML);
            Pane tmp = screenMap.get(targetXML);
            main.setRoot(tmp);
            tmp.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), tmp);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
