package pac;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class GameEngine {

    public static final Model model = new Model();

    public static void main(String[] args) throws Exception {

        CountDownLatch menuLatch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            new MenuFrame(model, menuLatch);
        });

        menuLatch.await();

        CountDownLatch gameLatch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            new GameFrame(model, gameLatch);
        });

        gameLatch.await();

        SwingUtilities.invokeLater(() -> {
            new GameCompletedFrame();
        });
    }

}
