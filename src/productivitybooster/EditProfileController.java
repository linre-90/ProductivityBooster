
package productivitybooster;

import functionality.FileExplorer;
import functionality.FileLoaderSaver;
import functionality.Profile;
import functionality.ProfileHandler;
import java.io.IOException;
import java.net.URL;
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
import popUps.ConfirmationPopUp;
import popUps.InformationPopUp;
import functionality.WindowChanger;
import functionality.ProfileHandlerInterface;
import java.util.ArrayList;
import javafx.stage.Stage;

public class EditProfileController implements Initializable, WindowChanger, ProfileHandlerInterface {
    final private String tooLongProfileNameMsg = "Too long Profile name!\n (Max. "+ ProductivityBooster.PROFILE_NAME_LENGTH +" characters)";
    final private String savedInfoMessage = "Profiles saved!";
    final private String noDuplicatesInfoPopUpMessage = "No duplicate paths allowed! And path cant be empty!";
    final private String[] removingWholeProfilePopUpHeaderContextMsg = {"Warning! tou are about to remove whole profile witch cannot be reverted!", "Pres ok to continue cancel to back!"};
    final private String[] changeWindowParameters = {"FXMLDocument.fxml",ProductivityBooster.MAIN_TITLE};
    private int profileIndex = -1;
    private int pathIndex = -1;
  
    @FXML private TextField profileNameTextFieldInEdit;
    @FXML private TextField pathTextFieldInEdit;
    @FXML private ListView<String> pathsListViewInEdit;
    @FXML private ListView<String> selectProfileToModifyListView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> startUpList = FXCollections.observableArrayList();
        for (Profile profile : getProfiles()) {
            startUpList.add(profile.toString());
        }
        selectProfileToModifyListView.setItems(startUpList);
    }

    @FXML private void openFileExplorer(ActionEvent e){
        FileExplorer fileExplorer = new FileExplorer();
        String path = fileExplorer.launchFileExplorer();
        if(!path.equals("-")){
            pathTextFieldInEdit.setText(path);
        }
    }
    @FXML private void addNewPathBtn(ActionEvent e){
        if(!FXCollections.observableArrayList(getProfiles().get(profileIndex).getFilepaths()).contains(pathTextFieldInEdit.getText()) && !pathTextFieldInEdit.getText().isEmpty()){
            getProfiles().get(profileIndex).addPath(pathTextFieldInEdit.getText());
            pathTextFieldInEdit.clear();
            pathsListViewInEdit.setItems(FXCollections.observableArrayList(getProfiles().get(profileIndex).getFilepaths()));
        }
        else{
            InformationPopUp noDuplicates = new InformationPopUp();
            noDuplicates.createInfoPopUp(noDuplicatesInfoPopUpMessage);
        }
    }
    
    @FXML private void profileIndexUpdate(MouseEvent e){
        profileIndex = selectProfileToModifyListView.getSelectionModel().getSelectedIndex();
        profileNameTextFieldInEdit.setText(getProfiles().get(profileIndex).getName());
        pathsListViewInEdit.setItems(FXCollections.observableArrayList(getProfiles().get(profileIndex).getFilepaths()));
       
    }
    
    @FXML private void pathIndexUpdate(MouseEvent e){
        pathIndex = pathsListViewInEdit.getSelectionModel().getSelectedIndex();
    }
    
    @FXML private void setProfileName(ActionEvent e){
        if(profileNameTextFieldInEdit.getText().length() > ProductivityBooster.PROFILE_NAME_LENGTH){
            InformationPopUp tooLongNameinfo = new InformationPopUp();
            tooLongNameinfo.createInfoPopUp(tooLongProfileNameMsg);
        }
        else{
            getProfiles().get(profileIndex).setName(profileNameTextFieldInEdit.getText());
            initialize(null, null);
        }
    }
    
    @FXML private void saveBtnPressed(ActionEvent e){
        FileLoaderSaver fls = new FileLoaderSaver();
        fls.saveProfiles(getProfiles());
        InformationPopUp info = new InformationPopUp();
        info.createInfoPopUp(savedInfoMessage);
    }
    
    @FXML private void closeWindow(ActionEvent e){
        changeWindow(changeWindowParameters);
    }
    
    @FXML private void deletePathContextMenuEdit(ActionEvent e){
            getProfiles().get(profileIndex).removePathFromList(pathsListViewInEdit.getItems().get(pathIndex));
            pathsListViewInEdit.getItems().remove(pathIndex);
    }
    
    @FXML private void removeBtnPressed(ActionEvent e){
        ConfirmationPopUp sureToRemove = new ConfirmationPopUp();
        boolean remove = sureToRemove.createPopUp(removingWholeProfilePopUpHeaderContextMsg);
        if(remove){
            getProfiles().remove(profileIndex);
            profileIndex = -1;
            profileNameTextFieldInEdit.clear();
            initialize(null, null);
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

    @Override
    public ProfileHandler getProfileHandler() {
       return null;
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        return ProductivityBooster.returnHandler().getProfiles();
    }
    
}
