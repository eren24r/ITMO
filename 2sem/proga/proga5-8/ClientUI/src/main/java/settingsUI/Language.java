package settingsUI;

import java.util.Locale;
import java.util.ResourceBundle;


public class Language {
    public ResourceBundle setLanguage(String language) {
        ResourceBundle resourceBundle;
        switch (language) {
            case "German" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("gr"));
            case "Latvian" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("lt"));
            case "Spanish" -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("sp"));
            default -> resourceBundle = ResourceBundle.getBundle("strings", new Locale("ru"));
        }
        return resourceBundle;
    }
}