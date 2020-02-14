
package productivitybooster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import functionality.ProfileHandler;
import functionality.FileLoaderSaver;
import java.io.File;


public class ProductivityBooster extends Application {
    private static ProfileHandler PROFILEHANDLER;
    private static Stage PS_STAGE;
    final public static String MAIN_TITLE = "Productivity Boosting";
    final public static String CREATE_PROFILE_TITLE = "Create new boosting profile!";
    final public static String MODIFY_PROFILE_TITLE = "Modify boosting profile!";
    final public static String USER_GUIDE_TITLE = "Learn about boosting!";
    final public static int PROFILE_NAME_LENGTH = 100;
    
    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        PS_STAGE = stage;
        PS_STAGE.setTitle(MAIN_TITLE);
        PS_STAGE.setScene(scene);
        PS_STAGE.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PROFILEHANDLER = new ProfileHandler();
        FileLoaderSaver fileLoaderSaver = new FileLoaderSaver();
        fileLoaderSaver.loadProfiles();
        new File(System.getProperty("user.home") + "\\ProductivityBooster").mkdirs();
        launch(args);
    }
    public static ProfileHandler returnHandler(){
        return PROFILEHANDLER;
    }

    public static Stage getPsStage() {
        return PS_STAGE;
    }
}
