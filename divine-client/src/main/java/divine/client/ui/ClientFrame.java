package divine.client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Typically
 * Project: divine
 * Date: 1/29/2019
 */
public class ClientFrame extends JFrame {

    public static final Dimension WINDOWS_DIMENSION = new Dimension(780, 542);

    private ClientAppletStub appletStub;

    public ClientFrame(ClientAppletStub appletStub) {
        this.appletStub = appletStub;
    }

    public void init() {
        setSize(WINDOWS_DIMENSION);
        setMinimumSize(WINDOWS_DIMENSION);

        setTitle("Divine " + appletStub.getGamePack().getFile().getName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(appletStub);
        validate();
        setVisible(true);
    }

}
