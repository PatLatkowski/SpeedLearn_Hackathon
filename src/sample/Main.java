package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*LoginScreenView login = new LoginScreenView();
        login.showLoginScreen(primaryStage);*/
        /*MainMenuViewController menu = new MainMenuViewController();
        menu.showMainMenu(primaryStage);*/
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 1000, 600));
        //pStage = primaryStage;
        Controller2 cont = new Controller2(new Scene(root, 1000, 600), primaryStage);
        cont.addScreen("LoginView", FXMLLoader.load(getClass().getResource( "LoginView.fxml" )));
        cont.addScreen("register", FXMLLoader.load(getClass().getResource( "register.fxml" )));
        cont.activate("LoginView");
        primaryStage.show();
    }

    public static void animate(Event event, FXMLLoader loader, Initializable contr, Parent root) {
        Button but = (Button)event.getSource();
        Stage sc = (Stage) but.getScene().getWindow();
        Scene scOld = but.getScene();
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1000), scOld.getRoot());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished( p -> {
            sc.setScene(new Scene(root, 1000, 600));
            root.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), root);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        });
        fadeOutTransition.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
