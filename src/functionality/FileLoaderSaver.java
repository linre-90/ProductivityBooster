package functionality;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import productivitybooster.ProductivityBooster;


public class FileLoaderSaver {
    final private File file =  new File(System.getProperty("user.home") + "\\ProductivityBooster\\profiles.txt");
    
    public void saveProfiles(ArrayList<Profile>profiles){
        removeFile();
        try {
            FileOutputStream out = new FileOutputStream(this.file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(ProductivityBooster.returnHandler().getProfiles());
            out.close();
            objOut.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException ex) {
            Logger.getLogger(FileLoaderSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void removeFile(){
        this.file.delete();
    }
    public void loadProfiles(){
            try {
                FileInputStream in = new FileInputStream(this.file);
                ObjectInputStream objIn = new ObjectInputStream(in);
                ArrayList<Profile> loadedProfiles = (ArrayList<Profile>)objIn.readObject();
                for (Profile loadedProfile : loadedProfiles) {
                    ProductivityBooster.returnHandler().addProfileToList(new Profile(loadedProfile.getName(), loadedProfile.getFilepaths()));
                }
                in.close();
                objIn.close();
            } catch (IOException e) {
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FileLoaderSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}