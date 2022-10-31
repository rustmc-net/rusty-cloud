package net.rustmc.cloud.base.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 31.10.2022
 */
public class ZipHelper {

    public static void zipFoldersAndFiles(Path sourcesFolderPath, Path zipPath){
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));

            Files.walkFileTree(sourcesFolderPath, new SimpleFileVisitor<>() {

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    zipOutputStream.putNextEntry(new ZipEntry(sourcesFolderPath.relativize(file).toString()));
                    zipOutputStream.setLevel(ZipOutputStream.STORED);
                    Files.copy(file, zipOutputStream);
                    zipOutputStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
            zipOutputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
