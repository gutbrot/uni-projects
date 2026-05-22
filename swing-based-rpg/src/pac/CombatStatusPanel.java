package pac;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CombatStatusPanel extends JPanel {

    private final Model model;

    private JLabel playerHpLabel;
    private DefaultListModel<String> monsterListModel;
    private JList<String> monsterList;

    private JComboBox<Monster> targetBox;
    private JComboBox<Ability> abilityBox;
    private JButton attackButton;
    private JButton useAbilityButton;

    public CombatStatusPanel(Model model) {
        this.model = model;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(260, 400));

        playerHpLabel = new JLabel("Player HP: ", SwingConstants.CENTER);
        add(playerHpLabel, BorderLayout.NORTH);

        monsterListModel = new DefaultListModel<>();
        monsterList = new JList<>(monsterListModel);
        add(new JScrollPane(monsterList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));

        targetBox = new JComboBox<>();
        bottomPanel.add(targetBox);

        attackButton = new JButton("Attack");
        attackButton.setEnabled(false);
        attackButton.addActionListener(e -> basicAttack());
        bottomPanel.add(attackButton);

        JPanel abilityPanel = new JPanel(new BorderLayout());
        abilityBox = new JComboBox<>();
        useAbilityButton = new JButton("Use ability");

        useAbilityButton.addActionListener(e -> abilityAttack());

        abilityPanel.add(abilityBox, BorderLayout.CENTER);
        abilityPanel.add(useAbilityButton, BorderLayout.EAST);

        bottomPanel.add(abilityPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {

        Character p = model.getPlayer();
        playerHpLabel.setText("Player HP: " + p.getHp());

        List<Monster> monsters = model.getWorld().getMonstersAt(p.getX(), p.getY());

        monsterListModel.clear();
        targetBox.removeAllItems();

        if (monsters.isEmpty()) {
            monsterListModel.addElement("No monsters here");
            attackButton.setEnabled(false);
            useAbilityButton.setEnabled(false);
        } else {
            for (Monster m : monsters) {
                monsterListModel.addElement(m.getName() + " - HP: " + m.getHp());
                targetBox.addItem(m);
            }
            attackButton.setEnabled(true);
        }

        abilityBox.removeAllItems();
        for (Ability a : p.getUnlockedAbilities()) {
            abilityBox.addItem(a);
        }

        useAbilityButton.setEnabled(
                !monsters.isEmpty() && abilityBox.getItemCount() > 0
        );

        revalidate();
        repaint();
    }

    private void basicAttack() {

        Monster target = (Monster) targetBox.getSelectedItem();
        if (target == null) return;

        Character p = model.getPlayer();

        int dmgPlayer = 5;
        target.setHp(target.getHp() - dmgPlayer);

        if (target.getHp() <= 0) {
            target.setHp(0);
            model.getWorld().removeEntity(target);
        } else {
            int dmgMonster = 3;
            p.setHp(p.getHp() - dmgMonster);
            if (p.getHp() <= 0) {
                p.setHp(0);
                JOptionPane.showMessageDialog(this, "You died!");
                System.exit(0);
            }
        }

        refresh();
    }

    private void abilityAttack() {

        Monster target = (Monster) targetBox.getSelectedItem();
        Ability ability = (Ability) abilityBox.getSelectedItem();
        if (target == null || ability == null) return;

        Character p = model.getPlayer();

        int dmgPlayer = ability.getDamage();
        target.setHp(target.getHp() - dmgPlayer);

        if (target.getHp() <= 0) {
            target.setHp(0);
            model.getWorld().removeEntity(target);
        } else {
            int dmgMonster = 3;
            p.setHp(p.getHp() - dmgMonster);
            if (p.getHp() <= 0) {
                p.setHp(0);
                JOptionPane.showMessageDialog(this, "You died!");
                System.exit(0);
            }
        }
        refresh();
    }
}
