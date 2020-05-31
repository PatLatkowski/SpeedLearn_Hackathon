package sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

public class RegisterView implements Initializable {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;

    private Stage primaryStage;

    @FXML
    void logIn(ActionEvent event) throws Exception
    {
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
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    contr.loadCourses();
                    stage.show();
                    //primaryStage.hide();
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

    @FXML
    public void register(ActionEvent e) throws Exception
    {
        System.out.println(password.getText());
        System.out.println(password2.getText());
        if(password.getText().equals(password2.getText()))
        {
            System.out.println(password2.getText());
            DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
                //Choice depends on type of method you will be invoking.
                HttpPost postRequest = new HttpPost("http://192.168.62.26:9090/users");

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
                /*HttpEntity httpEntity = response.getEntity();
                String apiOutput = EntityUtils.toString(httpEntity);

                Gson g = new Gson();
                User user = g.fromJson(apiOutput, User.class);*/
                //Verify the populated object


                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreenView.fxml"));
                        Parent root = (Parent) loader.load();
                        //Stage stage = new Stage();
                        //stage.setScene(new Scene(root));
                        //stage.show();
                        LoginScreenView contr = loader.getController();

                        Main.animate(e, loader, contr, root);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    //menu.showMainMenu(primaryStage);

            } finally {
                //Important: Close the connect
                httpClient.getConnectionManager().shutdown();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
