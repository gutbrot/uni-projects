package pac;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class GameFrame extends JFrame {
    private JButton upButton, downButton, rightButton, leftButton;
    private Canvas canvas;
    private final Model model;
    private  CountDownLatch latch;

    public GameFrame(Model model, CountDownLatch latch) {
        this.model = model;
        setTitle("Dungeon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvas = new GameCanvas(model.getWorld(), model.getPlayer());
        add(canvas, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 3));

        upButton = new JButton("↑");
        downButton = new JButton("↓");
        leftButton = new JButton("←");
        rightButton = new JButton("→");

        controlPanel.add(new JLabel());
        controlPanel.add(upButton);
        controlPanel.add(new JLabel());
        controlPanel.add(leftButton);
        controlPanel.add(downButton);
        controlPanel.add(rightButton);

        add(controlPanel, BorderLayout.SOUTH);

        CombatStatusPanel combatPanel = new CombatStatusPanel(model);
        add(combatPanel, BorderLayout.NORTH);

        System.out.println("player x: " + model.getPlayer().getX() + " y : " + model.getPlayer().getY());
        upButton.addActionListener(e -> {
            MoveResult result = model.getWorld().movePlayer(model.getPlayer(),0, -1);
            if (result == MoveResult.VALID) {
                canvas.repaint();
                combatPanel.refresh();
                System.out.println("player x: " + model.getPlayer().getX() + " y: " + model.getPlayer().getY());
            } else if (result == MoveResult.GAME_COMPLETED) {
                canvas.repaint();
                latch.countDown();
                dispose();
                return;
            }
        });

        downButton.addActionListener(e -> {
            MoveResult result = model.getWorld().movePlayer(model.getPlayer(),0, 1);
            if (result == MoveResult.VALID) {
                canvas.repaint();
                combatPanel.refresh();
                System.out.println("player x: " + model.getPlayer().getX() + " y: " + model.getPlayer().getY());
            } else if (result == MoveResult.GAME_COMPLETED) {
                canvas.repaint();
                latch.countDown();
                dispose();
                return;
            }
        });

        leftButton.addActionListener(e -> {
            MoveResult result = model.getWorld().movePlayer(model.getPlayer(),-1, 0);
            if (result == MoveResult.VALID) {
                canvas.repaint();
                combatPanel.refresh();
                System.out.println("player x: " + model.getPlayer().getX() + " y: " + model.getPlayer().getY());
            } else if (result == MoveResult.GAME_COMPLETED) {
                canvas.repaint();
                latch.countDown();
                dispose();
                return;
            }
        });

        rightButton.addActionListener(e -> {
            MoveResult result = model.getWorld().movePlayer(model.getPlayer(),1, 0);
            if (result == MoveResult.VALID) {
                canvas.repaint();
                combatPanel.refresh();
                System.out.println("player x: " + model.getPlayer().getX() + " y: " + model.getPlayer().getY());
            } else if (result == MoveResult.GAME_COMPLETED) {
                canvas.repaint();
                latch.countDown();
                dispose();
                return;
            }
        });
        SkillPanel skillPanel = new SkillPanel(model);
        add(skillPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}