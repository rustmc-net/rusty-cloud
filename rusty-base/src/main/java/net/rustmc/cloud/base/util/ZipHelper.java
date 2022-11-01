package net.rustmc.cloud.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
