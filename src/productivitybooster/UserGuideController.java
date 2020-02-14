
package productivitybooster;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import functionality.WindowChanger;
import javafx.stage.Stage;


// FXML controll class
public class UserGuideController implements Initializable, WindowChanger {
    final private String[] goBack = {"FXMLDocument.fxml", ProductivityBooster.MAIN_TITLE};
    private Map<String,String> topicsAndText;
    @FXML Text guideText;
    @FXML ListView topicsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createMapTopics();
    } 
    
    @FXML private void goBack(ActionEvent event){
        changeWindow(goBack);
    }
    
    @FXML private void setGuideText(MouseEvent e){
        guideText.setText(topicsAndText.get((String)topicsList.getSelectionModel().getSelectedItem()));
    }
    
    
    
    private void createMapTopics(){
       topicsAndText = new HashMap<>();
       // top menu
       topicsAndText.put("Main Screen", "This is where profiles exists if they are set. From menu above you can choose different actions. You can have six different profiles.");
       topicsAndText.put("FILE drop down menu", "Exit from program. You can choose if you want to save or not");
       topicsAndText.put("EDIT drop down menu", "Choose to create and edit profiles. There is option to also save all and delete all. NOTE if you delete all you cant get them back.");
       topicsAndText.put("HELP drop down menu", "Contains about page and User guide");
       // create profile page
       topicsAndText.put("Create new profile", "Here you can create a profile. Note that there are some limitation."+ "\n"+ "1. Maximum name lenght is 20 characters."+"\n"+"2. You can only add file path to .exe ending file or to folder, others won't work."+"\n"+"3. Web addres must begin with http." +"\n"+"4. Remove path by right click mouse over path and delete."+"\n"+ "Browse file path from browse path.. button. This opens file-explorer. Write or paste web address to same field. You can also copy and paste path or web address to field. Then press Add path to add path. When you are done click Create."+"\n"+"NOTE IT IS HIGHLY PREFERRED THAT YOU SAVE YOU PROFILES AFTER CREATING FROM EDIT -> SAVE ALL!!");     
       // mofify profile
       topicsAndText.put("Modify profile", "Select profile to modify from left hand side list called Profiles. To replace name write new name to field and press set name. Adding and removing paths works same as in create profile page. REMEMBER to press save button. Remove button removes selected profile.");     
       
       ObservableList<String> helpHeadlines = FXCollections.observableArrayList();
        for (String string : topicsAndText.keySet()) {
            helpHeadlines.add(string);
        }
        Collections.sort(helpHeadlines);
        topicsList.setItems(helpHeadlines);

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
