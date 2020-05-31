package sample;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import sample.model.Course;
import sample.model.User;
import sample.model.UsersCourse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuViewController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button startLearning;
    @FXML
    private TableView coursesList;
    @FXML
    TableColumn<UsersCourse, String> imageNameColumn;
    @FXML
    TableColumn<UsersCourse, Double> progressColumn;
    @FXML
    private Label nick;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Hello World!");
    }

    ObservableList<UsersCourse> courses;

    UsersCourse clickedCourse;

    static User user;

    static Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coursesList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            clickedCourse = courses.get((Integer)newValue);

        });
        user = new User();
        imageNameColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().getName()));
        /*progressColumn.setCellFactory(//wykorzystanie paska postępu
                ProgressBarTableCell.<Course>forTableColumn());*/
        //progressColumn.setCellValueFactory(//postęp przetwarzania
               // p -> p.getValue().progressProperty().asObject());
        courses = FXCollections.observableList(new ArrayList<>());
        coursesList.setItems(courses);
    }

    @FXML
    public void startLearning(ActionEvent event) throws Exception {
        for(UsersCourse course : courses)
        {
            if(clickedCourse.getName().equals(course.getName()))
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CurseView.fxml"));
                    Parent root = (Parent) loader.load();
                    CurseViewController contr = loader.getController();
                    contr.setCourse(clickedCourse);
                    contr.setUser(user);
                    System.out.println(clickedCourse.getIdcourse());

                    try {
                        contr.loadLessons();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.animate(event, loader, contr, root);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @FXML
    public void search(ActionEvent event) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            //Define a HttpGet request; You can choose between HttpPost, HttpDelete or HttpPut also.
            //Choice depends on type of method you will be invoking.
            HttpGet getRequest = new HttpGet("http://192.168.62.26:9090/userscourses");

            //Set the API media type in http accept header
            getRequest.addHeader("accept", "application/json");
            //Send the request; It will immediately return the response in HttpResponse object
            HttpResponse response = httpClient.execute(getRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            //Now pull back the response object
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);

            Gson g = new Gson();

            ArrayList<LinkedTreeMap> coursesArray =  new ArrayList<>();
            coursesArray = g.fromJson(apiOutput, ArrayList.class);
            //courses = FXCollections.observableArrayList(coursesArray);

            for (LinkedTreeMap map: coursesArray)
            {
                UsersCourse a = new UsersCourse(map);
                System.out.println(a.getName());
                courses.add(a);
            }

            //System.out.println(coursesArray.get(0).get("lessons"));


            coursesList.setItems(courses);

            if(!(user == null))
        {
                /*System.out.println(.getId());
                System.out.println(user.getUsername());


                MainMenuViewController menu = new MainMenuViewController();
                menu.setUser(user);
                menu.showMainMenu(primaryStage);*/
        }

        //Lets see what we got from API
        System.out.println(apiOutput); //<user id="10"><firstName>demo</firstName><lastName>user</lastName></user>
    } finally {
        //Important: Close the connect
        httpClient.getConnectionManager().shutdown();
    }
    }

    public void loadCourses()
    {

        ArrayList<LinkedTreeMap> coursesArray =  user.getUserscourses();;
        //coursesArray = g.fromJson(apiOutput, ArrayList.class);
        //courses = FXCollections.observableArrayList(coursesArray);
        System.out.println(coursesArray);
        for (LinkedTreeMap map: coursesArray)
        {
            UsersCourse a = new UsersCourse(map);
            System.out.println(a.getName());
            courses.add(a);
        }
    }

    public void setUser(User user)
    {
        this.user = user;
        nick.setText(user.getUsername() + " Exp:" + user.getExp().toString());
    }
    public void setHandler(Stage stage)
    {
        this.primaryStage = stage;
    }
}
