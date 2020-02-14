
package functionality;

import java.util.ArrayList;
import popUps.InformationPopUp;

public class ProfileHandler {
    private final ArrayList<Profile>profiles = new ArrayList<>();
    private final String infoMessage = "Too many profiles";

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }
    public void addProfileToList(Profile profile){
        if(profiles.size()<6){
            profiles.add(profile);
        }else{
            InformationPopUp info = new InformationPopUp();
            info.createInfoPopUp(this.infoMessage);
        }
        
    }  

     public boolean hasProfiles(){
        if(profiles.size()>=0){
            for (Profile profile : profiles) {
                if(profile instanceof Profile){
                    return true;
                }
            }
        }
        return false;
     }
}
