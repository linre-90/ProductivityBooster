
package popUps;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class LaunchingPopUp {
    public void launchPopup(String msg){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Launching programs!");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }
    
}
