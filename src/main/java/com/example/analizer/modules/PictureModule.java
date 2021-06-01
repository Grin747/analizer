package com.example.analizer.modules;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.example.analizer.interfaces.Module;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Component
public class PictureModule implements Module {
    @Override
    public boolean isAcceptableFormat(String format) {
        return format.equals("jpeg") ||
                format.equals("jpg") ||
                format.equals("png");
    }

    @Override
    public String[] getFunctions() {
        return new String[] {
                "1 - Show image resolution",
                "2 - Show exif info",
                "3 - Count image size"
        };
    }

    @Override
    public String exec(int func, File file) {
        if (func > 3 || func < 1) return exception;

        try {
            var result = PictureModule.class.getMethod("op" + func, File.class).invoke(this, file);
            return (String) result;
        } catch (Exception e) {
            return exception;
        }
    }

    public String op1(File file) throws IOException {
        var img = ImageIO.read(file);
        return img.getWidth() + " x " + img.getHeight();
    }

    public String op2(File file) throws ImageProcessingException, IOException {
        var meta = ImageMetadataReader.readMetadata(file);
        var result = new StringBuilder();

        for (Directory dir : meta.getDirectories())
            for (Tag tag : dir.getTags())
                result.append(tag).append("\n");

        return result.toString();
    }

    public String op3(File file) {
        return file.length() / 1024 + " KB";
    }
}
