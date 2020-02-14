
package functionality;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileExplorer {

    public String launchFileExplorer(){
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter("Only \".exe files, web addres or folder allowed.\"", "exe"));
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int i = fc.showOpenDialog(fc);
        if(i == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            return file.getAbsolutePath();
        }
        else{
            return "-";
        }
    }
}

