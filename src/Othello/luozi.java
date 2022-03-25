package Othello;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class luozi extends Thread{
    @Override
    public void run() {
        super.run();
        File f;
        URI uri;
        URL url;
        try {
            f = new File("resourse/luozi.wav" );
            uri = f.toURI();
            url = uri.toURL();  //解析地址
            AudioClip aau;
            aau = Applet.newAudioClip(url);
            aau.play();  //循环播放
        } catch (Exception E)
        { E.printStackTrace();
        }

    }
}
