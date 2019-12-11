package pl.springnauka.tasktreemanager.tasks.boundary;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface StorageService {

    void saveFile(Long id, MultipartFile file) throws IOException;

    Resource loadFile(Long id, String filename) throws MalformedURLException;
}
