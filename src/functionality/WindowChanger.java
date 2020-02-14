/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionality;

import javafx.stage.Stage;

/**
 *
 * @author Juho
 */
public interface WindowChanger {
    public void changeWindow(String[] windowParams);
    public Stage getStage();

    
}
