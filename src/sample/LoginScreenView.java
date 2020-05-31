package sample;

import com.google.gson.Gson;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import sample.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenView implements Initializable {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    private Stage primaryStage;

    @FXML
    void logIn(ActionEvent event) throws Exception
    {
        final URL resource = getClass().getResource("audio.mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
            //Choice depends on type of method you will be invoking.
            HttpPost postRequest = new HttpPost("http://192.168.62.26:9090/users/login");

            //Set the API media type in http accept header
            postRequest.addHeader("accept", "application/json");
            postRequest.addHeader("content-type", "application/json");

            PasswordEncoder encoder = new PasswordEncoder();
            String hashedPassword = encoder.hashPassword(password.getText());
            String json = "{\"username\":\"" + login.getText() +"\",\"password\":\"" + hashedPassword + "\"}";
            StringEntity entity = new StringEntity(json);
            postRequest.setEntity(entity);

            //Send the request; It will immediately return the response in HttpResponse object
            HttpResponse response = httpClient.execute(postRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            //Now pull back the response object
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);

           Gson g = new Gson();
           User user = g.fromJson(apiOutput, User.class);
            //Verify the populated object

            if(!(user == null))
            {
                /*System.out.println(user.getId());
                System.out.println(user.getUsername());*/
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
                    Parent root = (Parent) loader.load();
                    MainMenuViewController contr = loader.getController();
                    contr.setUser(user);

                    Main.animate(event, loader, contr, root);

                    contr.loadCourses();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //menu.showMainMenu(primaryStage);
            }
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }


    public void setStage(Stage stage)
    {
        this.primaryStage = stage;
    }
    void showLoginScreen(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void toRegister(ActionEvent e) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = (Parent) loader.load();
            //MainMenuViewController contr = loader.getController();
            RegisterView contr = loader.getController();

            Main.animate(e, loader, contr, root);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
