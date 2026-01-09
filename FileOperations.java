package com.example.aidmanagerprojectgui2026;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileOperations {

    void saveToTextFile(String filename) throws IOException;

    void loadFromTextFile(String filename) throws IOException, FileNotFoundException;

    void saveToBinaryFile(String filename) throws IOException;

    void loadFromBinaryFile(String filename) throws IOException, FileNotFoundException;

}
