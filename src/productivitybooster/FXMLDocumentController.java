package productivitybooster;

import functionality.FileLoaderSaver;
import functionality.Profile;
import functionality.ProfileHandler;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import popUps.*;
import functionality.WindowChanger;
import functionality.ProfileHandlerInterface;
import javafx.stage.Stage;

// FXML controll class
public class FXMLDocumentController implements Initializable, WindowChanger, ProfileHandlerInterface{
    final private String errorLaunchingExe = "Error when trying to file, Check the file paths!";
    final private String savedProfilesOnexitPopUpText = "Profiles saved!!";
    final private String[] launchUserGuide = {"UserGuide.fxml", ProductivityBooster.USER_GUIDE_TITLE};
    final private String[] createProfileFXMLAddres = {"CreateProfile.fxml", ProductivityBooster.CREATE_PROFILE_TITLE};
    final private String[] modifyProfileFXMLAddress = {"EditProfile.fxml", ProductivityBooster.MODIFY_PROFILE_TITLE};
    final private String[] exitWithoutsaveInstructions = {"Warning, Closing without saving profiles", "Click ok to close, cancel to go back!"};
    final private String[] deleteAllProfilesInstructions = {"Warning, you are about to delete all profiles! This action is not reversible!!", "Click ok to delete, cancel to go back!"};
    private HashMap<Button,Profile> finalBtnProfileMap;  
    private boolean errorLaunching = false;
    
    @FXML private Label noSavedProfilesText;
    @FXML private Button profileBtn0;
    @FXML private Button profileBtn1;
    @FXML private Button profileBtn2;
    @FXML private Button profileBtn3;
    @FXML private Button profileBtn4;
    @FXML private Button profileBtn5;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!getProfileHandler().hasProfiles()){
            noSavedProfilesText.setVisible(true);
        }
        finalBtnProfileMap = populateProfileButtons(profileButtonListMaker());
    }
    
    @FXML private void aboutBtn(ActionEvent event){
        AboutPopUp about = new AboutPopUp();
        about.createAboutPopUp();
    }
    
    @FXML private void userGuide(ActionEvent event){
        changeWindow(launchUserGuide);           
    }
 
    @FXML private void fileMenu(ActionEvent event){
        final MenuItem source = (MenuItem)event.getSource();
        switch(source.getId()){
            case "closeAndSaveMenuItem":
                FileLoaderSaver fileLoaderSaver = new FileLoaderSaver();
                fileLoaderSaver.saveProfiles(getProfiles());
                InformationPopUp informationPopUp = new InformationPopUp();
                informationPopUp.createInfoPopUp(this.savedProfilesOnexitPopUpText);
                System.exit(0);
                break;
            case "closeDontSaveMenuItem":
                ConfirmationPopUp exitWithoutSave = new ConfirmationPopUp();
                boolean closeWithoutSaving = exitWithoutSave.createPopUp(this.exitWithoutsaveInstructions);
                if(closeWithoutSaving){
                    System.exit(0);
                }
        }
    }
    
    @FXML private void editMenu(ActionEvent event){
        final MenuItem source = (MenuItem)event.getSource();
        final  FileLoaderSaver fileLoaderSaver = new FileLoaderSaver();
        switch(source.getId()){
            case "createNewProfileMenuItem":
                    changeWindow(this.createProfileFXMLAddres);
                break;
            case "modifyProfileMenuItem":
                    changeWindow(this.modifyProfileFXMLAddress);
                break;

            case "saveAllMenuItem":
                fileLoaderSaver.saveProfiles(getProfiles());
                InformationPopUp informationPopUp = new InformationPopUp();
                informationPopUp.createInfoPopUp(this.savedProfilesOnexitPopUpText);
                break;
            case "deleteAllMenuItem":
                ConfirmationPopUp deleteALL = new ConfirmationPopUp();
                boolean closeWithoutSaving = deleteALL.createPopUp(this.deleteAllProfilesInstructions);
                if(closeWithoutSaving){
                    fileLoaderSaver.removeFile();
                    getProfiles().clear();
                    for (Button button : profileButtonListMaker()) {
                        button.setVisible(false);
                    }
                    initialize(null, null);
                }
                break;
        }
    }
    @FXML private void profileBtnListener(ActionEvent e){
        Node node = (Node)e.getSource();
        String pressedBtn = node.getId();
        for (Button button : finalBtnProfileMap.keySet()) {
            if(button.getId().equals(pressedBtn)){
                getStage().setIconified(true);
                LaunchingPopUp l = new LaunchingPopUp();            
                launchExe(finalBtnProfileMap.get(button).getFilepaths());
                if(!errorLaunching){
                    l.launchPopup("All done!!:) PATIENSE WHILE WAITING PROGRAMS TO START!");
                }if(errorLaunching){
                    InformationPopUp errorPopUp = new InformationPopUp();
                    errorPopUp.createInfoPopUp(errorLaunchingExe);
                }
            }
        }
    }
    
    private void launchExe(ArrayList<String> launchTheseFiles){
        Thread t1 = new Thread(() -> {
            for (String launchTheseFile : launchTheseFiles) {
                try {
                    if(launchTheseFile.startsWith("htt")){
                        // Web page
                           Desktop.getDesktop().browse(URI.create(launchTheseFile));                       
                    }
                    if(launchTheseFile.endsWith("exe")){
                        // exe
                         Process process  = new ProcessBuilder(launchTheseFile).start();
                    }if(new File(launchTheseFile).isDirectory()){
                        // launch folder
                        Desktop.getDesktop().open(new File(launchTheseFile));                      
                    }
                } catch (IOException ex) {
                    errorLaunching = true;
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
        // removed t1.join here
    }
    
    private List<Button> profileButtonListMaker(){
        List<Button>profileButtons = new ArrayList<>();
        profileButtons.add(profileBtn0);
        profileButtons.add(profileBtn1);
        profileButtons.add(profileBtn2);
        profileButtons.add(profileBtn3);
        profileButtons.add(profileBtn4);
        profileButtons.add(profileBtn5); 
        return profileButtons;
    }
    
    private HashMap<Button,Profile> populateProfileButtons(List<Button>profileButtons){
        HashMap<Button,Profile>buttonToProfile = new HashMap();
        for (int i = 0; i < getProfiles().size(); i++) {
            buttonToProfile.put(profileButtons.get(i), getProfiles().get(i));
            profileButtons.get(i).setText(getProfiles().get(i).getName());
            profileButtons.get(i).setVisible(true);
        }
        return buttonToProfile;
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
        return ProductivityBooster.returnHandler();
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        return ProductivityBooster.returnHandler().getProfiles();
    }
}
