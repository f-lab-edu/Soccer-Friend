package soccerfriend.service;

import java.io.File;

public interface FileManageService {

    public void upload(File file);

    public void download(String fileName, String path);

    public void delete(String fileName);
}
