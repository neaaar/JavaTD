package com.mfarioli.JavaTD.Helpers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    AudioInputStream audioInputStream;

    AudioFormat audioFormat;

    DataLine.Info info;

    Clip audioClip;

    File audioFile;

    public AudioPlayer(File audioFile) {
        this.audioFile = audioFile;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            audioFormat = audioInputStream.getFormat();
            info = new DataLine.Info(Clip.class, audioFormat);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioInputStream);
            this.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void start() {
        audioClip.setMicrosecondPosition(0);
        audioClip.loop(10);
        audioClip.start();
    }

    public void stop() {
        audioClip.stop();
    }
}
