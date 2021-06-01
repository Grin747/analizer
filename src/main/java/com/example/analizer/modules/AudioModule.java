package com.example.analizer.modules;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.mp3.Mp3MetadataReader;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Directory;
import com.example.analizer.interfaces.Module;
import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class AudioModule implements Module {
    @Override
    public boolean isAcceptableFormat(String format) {
        return format.equals("mp3");
    }

    @Override
    public String[] getFunctions() {
        return new String[] {
                "1 - Get duration",
                "2 - Get name",
                "3 - Get author"
        };
    }

    @Override
    public String exec(int func, File file) {
        if (func > 3 || func < 1) return exception;

        try {
            var result = AudioModule.class.getMethod("op" + func, File.class).invoke(this, file);
            return (String) result;
        } catch (Exception e) {
            return exception;
        }
    }

    public String op1(File file) throws UnsupportedAudioFileException, IOException {
        var fileFormat = AudioSystem.getAudioFileFormat(file);

        if (fileFormat instanceof TAudioFileFormat) {
            var props = fileFormat.properties();
            var duration = (Long) props.get("duration");
            return duration / 1000000 + " seconds";
        }

        return exception;
    }

    public String op2(File file) throws IOException, UnsupportedAudioFileException {
        var fileFormat = AudioSystem.getAudioFileFormat(file);

        if (fileFormat instanceof TAudioFileFormat) {
            var props = fileFormat.properties();
            return (String) props.get("title");
        }

        return exception;
    }

    public String op3(File file) throws UnsupportedAudioFileException, IOException {
        var fileFormat = AudioSystem.getAudioFileFormat(file);

        if (fileFormat instanceof TAudioFileFormat) {
            var props = fileFormat.properties();
            return (String) props.get("author");
        }

        return exception;
    }
}
