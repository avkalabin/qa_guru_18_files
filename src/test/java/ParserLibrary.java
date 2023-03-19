import java.util.Date;
import java.util.List;

public class ParserLibrary {
    public String library;
    public String version;
    public String license;
    public List<String> tags;
    public Developer developer;

    public static class Developer {
        public String name;
        public String email;
        public String devId;
        public List<String> roles;
        public String organisation;
    }


}
