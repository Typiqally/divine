package divine.client.core;

import divine.client.game.GamePack;
import divine.client.ui.ClientAppletStub;
import divine.client.ui.ClientFrame;

/**
 * Author: Typically
 * Project: divine
 * Date: 1/28/2019
 */
public class Divine {

    private final GamePack gamePack;

    private ClientAppletStub appletStub;
    private ClientFrame clientFrame;

    public Divine(GamePack gamePack) throws Exception {
        this.gamePack = gamePack;
    }

    public void init() throws Exception {
        gamePack.build();

        this.appletStub = new ClientAppletStub(gamePack);
        appletStub.run();

        this.clientFrame = new ClientFrame(appletStub);
        clientFrame.init();
    }

}
