package divine.client.ui;

import divine.client.game.GamePack;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author: Typically
 * Project: divine
 * Date: 1/29/2019
 */
public class ClientAppletStub extends JPanel implements AppletStub {

    public static final long serialVersionUID = 1L;
    public static final Dimension GAME_DIMENSION = new Dimension(765, 503);

    private GamePack gamePack;
    private Class<?> client;

    private Applet applet;

    public ClientAppletStub(GamePack gamePack) throws ClassNotFoundException {
        this.gamePack = gamePack;
    }

    public void run() throws Exception {
        client = gamePack.getClassLoader().loadClass(gamePack.getConfig().getCode());
        applet = (Applet) client.newInstance();

        construct();
    }

    private void construct() {
        this.setLayout(new BorderLayout(0, 0));
        applet.setStub(this);
        applet.setSize(GAME_DIMENSION);
        applet.setMinimumSize(GAME_DIMENSION);
        applet.init();
        applet.start();
        this.add(applet, BorderLayout.CENTER);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        try {
            return gamePack.getJarUrl();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public URL getCodeBase() {
        try {
            return gamePack.getJarUrl();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getParameter(String name) {
        return gamePack.getConfig().getParameters().get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return applet.getAppletContext();
    }

    @Override
    public void appletResize(int width, int height) {
        applet.setSize(width, height);
    }

    public GamePack getGamePack() {
        return gamePack;
    }
}
