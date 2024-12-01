package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.common.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    private final static String BASE_PATH = System.getProperty("java.io.tmpdir");

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromRunnable( () -> readAsString(fileName));
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable( () -> writeAsString(fileName, content));
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable( () -> deleteByFilename(fileName));
    }

    private String readAsString(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Paths.get(BASE_PATH, fileName).toAbsolutePath().toString()));
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            logger.info("read content:" + sb.toString());
            br.close();
            return sb.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void writeAsString(String fileName, String content) {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(Paths.get(BASE_PATH, fileName).toAbsolutePath().toString()));
            wr.write(content);
            logger.info("written content:" + content);
            wr.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteByFilename(String fileName) {
        File file = new File(Paths.get(BASE_PATH, fileName).toAbsolutePath().toString());
        logger.info("deleted file :" + file.getAbsolutePath());
        file.delete();
    }
}
