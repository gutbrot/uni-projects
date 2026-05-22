package pac;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class SkillPanel extends JPanel {

    private JComboBox<Ability> unlockableBox;

    private JButton unlockButton;

    private final Model model;

    public SkillPanel(Model model) {
        this.model = model;

        setLayout(new GridLayout(1, 2));

        add(createUnlockablePanel());

        refresh();
    }

    private JPanel createUnlockablePanel() {

        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("Unlockable Abilities:"), BorderLayout.NORTH);

        unlockableBox = new JComboBox<>();
        p.add(unlockableBox, BorderLayout.CENTER);

        unlockButton = new JButton("Unlock");
        unlockButton.addActionListener(e -> {

            Ability a = (Ability) unlockableBox.getSelectedItem();
            Character player = model.getPlayer();

            if (a == null) return;

            player.unlockAbility(a);

            a.setCost(0);

            System.out.println("Unlocked: " + a.getName());

            refresh();
        });

        p.add(unlockButton, BorderLayout.SOUTH);

        return p;
    }

    public void refresh() {

        Character player = model.getPlayer();

        Set<Ability> unlocked = player.getUnlockedAbilities();
        Set<Ability> unlockable = model.getGraph().getUnlockable(unlocked);

        unlockableBox.removeAllItems();
        for (Ability a : unlockable) {
            unlockableBox.addItem(a);
        }

        updateButtonState();
        revalidate();
        repaint();
    }

    private void updateButtonState() {

        Character player = model.getPlayer();
        Ability selectedUnlockable = (Ability) unlockableBox.getSelectedItem();

        if (selectedUnlockable == null) {
            unlockButton.setEnabled(false);
            return;
        }

        boolean enoughXp = player.getXp() >= selectedUnlockable.getCost();

        unlockButton.setEnabled(enoughXp);
    }
}