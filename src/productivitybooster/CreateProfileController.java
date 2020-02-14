
package productivitybooster;
import functionality.FileExplorer;
import functionality.Profile;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import popUps.InformationPopUp;
import functionality.WindowChanger;
import javafx.stage.Stage;


public class CreateProfileController implements Initializable, WindowChanger {
    final private ObservableList<String> paths = FXCollections.observableArrayList();
    final private String noDuplicatesInfoPopUpMessage = "No duplicate paths allowed! And path cant be empty!";
    final private String nameTooBig = "Name is too long, only" + ProductivityBooster.PROFILE_NAME_LENGTH+ " characters allowed!";
    final private String[] goBack = {"FXMLDocument.fxml", ProductivityBooster.MAIN_TITLE};
    private int selectedIndex = 0;
    
    @FXML private TextField pathTextField;
    @FXML private ListView <String> pathsList;
    @FXML private TextField profileNameTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}   
    
    @FXML private void browsePath(ActionEvent event){
        FileExplorer fileExplorer = new FileExplorer();
        String path = fileExplorer.launchFileExplorer();
        if(!path.equals("-")){
            pathTextField.setText(path);
        }
    }
    
    @FXML private void addPathBtn(ActionEvent e){
        if(!paths.contains(pathTextField.getText()) && !pathTextField.getText().isEmpty()){
            paths.add(pathTextField.getText());
            pathTextField.clear();
            pathsList.setItems(paths);
        }
        else{
            InformationPopUp noDuplicates = new InformationPopUp();
            noDuplicates.createInfoPopUp(noDuplicatesInfoPopUpMessage);
        }
    }
    
    @FXML private void cancelBtn(ActionEvent event){
        paths.clear();
        changeWindow(goBack);
    }
    
    @FXML private void deleteContextMenu(ActionEvent event){
        if(paths.size()>0 & selectedIndex<=paths.size()){
            paths.remove(selectedIndex);
        }
    }
    
    @FXML private void getSelectedIndex(MouseEvent e){
        selectedIndex = pathsList.getSelectionModel().getSelectedIndex();
    }
    
    @FXML private void saveNewProfileBtn(ActionEvent e){
        if(profileNameTextField.getText().length() <= ProductivityBooster.PROFILE_NAME_LENGTH){
            ArrayList<String> temp = new ArrayList<>();
            for (String path : paths) {
                temp.add(path);
            }
            ProductivityBooster.returnHandler().addProfileToList(new Profile(profileNameTextField.getText(), temp));
            paths.clear();
            changeWindow(goBack);
        }else{
            InformationPopUp nameTooLong = new InformationPopUp();
            nameTooLong.createInfoPopUp(nameTooBig);
        }
    }
    
    @Override
    public void changeWindow(String[]parameters){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(parameters[0]));
            try {
                Parent root = (Parent)loader.load();
                getStage().setScene(new Scene(root));
                getStage().setTitle(parameters[1]);
                getStage().show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public Stage getStage() {
        return ProductivityBooster.getPsStage();
    }
}
