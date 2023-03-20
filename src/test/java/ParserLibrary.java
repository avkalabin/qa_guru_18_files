import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ParserLibrary {
    public String library;
    public String version;

    public String license;
    public List<String> tags;
    @JsonFormat(pattern = "dd-MM-yyyy")
    public Date date;
    public List<Developer> developers;

    public static class Developer {
        public String name;
        public String email;
        public String devId;
        public List<String> roles;
        public String organisation;
    }


}
