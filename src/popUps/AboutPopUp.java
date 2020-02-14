
package popUps;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import productivitybooster.ProductivityBooster;


public class AboutPopUp {
    public void createAboutPopUp(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("This app is for convinient of my own..:)\nSave up to 6 launcher profiles.\n"
                + "Modify profiles and set them custom names(max. "+ProductivityBooster.PROFILE_NAME_LENGTH+ " characters).\n"
                + "Remember allways hit save button when modifying!\n"
                + "Webaddress must begin with http.\n"
                + "Folders are too selectable.\n"
                + "Exe filepath has to point .exe file.\n"
                + "Enjoy!!");

        alert.showAndWait();
    }
}
