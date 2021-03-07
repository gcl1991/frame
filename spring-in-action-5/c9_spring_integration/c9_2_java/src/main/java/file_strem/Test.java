package file_strem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "integration",produces = "application/json")
@CrossOrigin(origins = "*")
public class Test {
    private FileWriterGateway fileWriterGateway;

    @Autowired
    public Test(FileWriterGateway fileWriterGateway){
        this.fileWriterGateway = fileWriterGateway;
    }

    @GetMapping("/file")
    public String test() {
        fileWriterGateway.writeToFile("test.log","String-java");
        return "ok i get";
    }
}
