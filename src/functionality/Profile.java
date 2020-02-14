package functionality;

import java.io.Serializable;
import java.util.ArrayList;


public class Profile implements Serializable{
    private String name;
    private ArrayList<String> filepaths = new ArrayList<>();

    public Profile(String name) {
        this.name = name;
    }
    public Profile(String name, ArrayList<String>filePathsFromMemory) {
        this.name = name;
        this.filepaths = filePathsFromMemory;
    }

    public void setFilepaths(ArrayList<String> filepaths) {
        this.filepaths = filepaths;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getFilepaths() {
        return filepaths;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addPath(String path){
        if(!filepaths.contains(path)){
            filepaths.add(path);
        }
    }
    public String removePathFromList(String path){
        // returns string for popup message
        boolean isInList = false;
        for (String filepath : this.filepaths) {
            if(filepath.equals(path)){
                isInList = true;
            }
        }
        if(isInList){
            this.filepaths.remove(path);
            return "Removed: " + path;
        }
        if(!isInList){
            return path+ " not on this profiles list!";
        }
        else{
            return "Something else went wrong, try again!";
        }
    } 
}
