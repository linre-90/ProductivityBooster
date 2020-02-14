
package popUps;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class InformationPopUp {
    public void createInfoPopUp(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
