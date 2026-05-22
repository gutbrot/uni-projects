package pac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class MenuFrame extends JFrame {

    private final JComboBox<String> box;
    private final JTextField nameField = new JTextField(20);
    private final JButton startButton = new JButton("Play!");

    private final Model model;
    private final CountDownLatch latch;

    public MenuFrame(Model model, CountDownLatch latch) {
        this.model = model;
        this.latch = latch;

        String[] casts = {"Warrior", "Mage", "Monk"};
        box = new JComboBox<>(casts);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Dungeon Crawler Java Remake");
        setSize(400, 110);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(box);
        panel.add(nameField);
        panel.add(startButton);
        add(panel, BorderLayout.CENTER);

        startButton.addActionListener(new StartButtonActionListener());

        setVisible(true);
        System.out.println("Menu visible");
    }

    class StartButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String cast = (String) box.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(MenuFrame.this,
                        "Please enter a name!",
                        "Missing name",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            switch (cast){
                case "Warrior":
                    model.setPlayer(new Character(name,0,0,20));
                    break;
                case "Mage":
                    model.setPlayer(new Character(name,0,0,15));
                    break;
                case "Monk":
                    model.setPlayer(new Character(name,0,0,18));
                    break;
            }

            try {
                XmlMapLoader.loadIntoModel("res/data.xml", model);

                latch.countDown();

                dispose();
                System.out.println("Menu disposed");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(MenuFrame.this,
                        "Error loading game data: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
