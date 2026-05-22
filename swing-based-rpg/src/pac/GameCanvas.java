package pac;

import java.awt.*;

public class GameCanvas extends Canvas {

    private final World map;
    Character player;
    public GameCanvas(World map, Character player) {
        this.map = map;
        this.player = player;
        setSize(map.cols * 32, map.rows * 32);
    }

    @Override
    public void paint(Graphics g) {

        for (int r = 0; r < map.rows; r++) {
            for (int c = 0; c < map.cols; c++) {

                Tile t = map.getTiles()[r][c];

                Color color = switch (t.getType()) {
                    case 0 -> Color.LIGHT_GRAY;
                    case 1 -> Color.DARK_GRAY;
                    case 2 -> Color.RED;
                    default -> Color.BLACK;
                };

                g.setColor(color);
                g.fillRect(t.x, t.y, t.width, t.height);

                if (t.entityCount() > 0) {
                    g.setColor(Color.GREEN);
                    g.drawString("E", t.x + 12, t.y + 20);
                }

                if (player != null &&
                        r == player.getY() &&
                        c == player.getX()) {

                    g.setColor(Color.BLUE);
                    g.drawString("P", t.x + 12, t.y + 20);
                }
            }
        }
    }
}