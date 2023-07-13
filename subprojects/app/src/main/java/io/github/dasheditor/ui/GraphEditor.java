package io.github.dasheditor.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

public final class GraphEditor extends JPanel {

    private VolatileImage offscreenBuffer;
    private final BufferedImage gridImage;
    private final Point cameraPosition;
    private Point lastMousePosition;
    private int lastMouseButton;
    private int selectedNodeIndex = -1;
    private final ArrayList<RoundRectangle2D> nodeBounds = new ArrayList<>();

    public GraphEditor() {
        gridImage = getGridImage();
        cameraPosition = new Point(0, 0);

        GraphMouseListener mouseListener = new GraphMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        nodeBounds.add(new RoundRectangle2D.Float(0, 0, 200, 125, 20, 20));
        nodeBounds.add(new RoundRectangle2D.Float(250, 50, 200, 125, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (offscreenBuffer == null || offscreenBuffer.getWidth() != getWidth() || offscreenBuffer.getHeight() != getHeight())
            createOffscreenBuffer();

        do {
            if (offscreenBuffer.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
                createOffscreenBuffer();
            }

            Graphics2D g2 = offscreenBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(new TexturePaint(gridImage, new Rectangle2D.Float(cameraPosition.x, cameraPosition.y, 100, 100)));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.translate(cameraPosition.x, cameraPosition.y);

            for (RoundRectangle2D node : nodeBounds) {
                drawNode(g2, node);
            }

            g2.dispose();
            g.drawImage(offscreenBuffer, 0, 0, null);
        } while (offscreenBuffer.contentsLost());
    }

    private void drawNode(Graphics2D g2, RoundRectangle2D bounds) {
        g2.setClip(null);

        g2.setPaint(new GradientPaint(
                (float) bounds.getX(), (float) bounds.getMaxY(), new Color(15, 15, 15),
                (float) bounds.getMaxX(), (float) bounds.getY(), new Color(15, 15, 15, 100)));
        g2.fill(bounds);

        if (selectedNodeIndex < 0) {
            g2.setColor(Color.BLACK);
            g2.draw(bounds);
        }

        g2.setClip(bounds);
        g2.setPaint(new GradientPaint(
                (float) bounds.getX(), (float) bounds.getCenterY(), new Color(125, 17, 17),
                (float) bounds.getMaxX(), (float) bounds.getCenterY(), new Color(118, 55, 50, 100)));
        g2.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getMaxX(), 40);
        g2.setPaint(new GradientPaint(
                (float) bounds.getX(), (float) bounds.getY(), new Color(255, 255, 255, 100),
                (float) bounds.getMaxX(), 40, new Color(255, 255, 255, 0)));
        g2.setClip(new Rectangle2D.Float((float) bounds.getX(), (float) bounds.getY(), (float) bounds.getWidth(), 40));
        g2.draw(new RoundRectangle2D.Float((float) bounds.getX(), (float) bounds.getY(), (float) bounds.getWidth(), 50, 20, 20));

        g2.setClip(null);

        if (selectedNodeIndex >= 0 && nodeBounds.get(selectedNodeIndex).equals(bounds)) {
            g2.setColor(Color.WHITE);
            g2.draw(bounds);
        }

        g2.setFont(new Font("Open Sans", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString("On Enable", (int) bounds.getX() + 10, (int) bounds.getY() + (35 / 2));

        g2.setFont(new Font("Open Sans", Font.BOLD | Font.ITALIC, 12));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Event", (int) bounds.getX() + 10, (int) bounds.getY() + (35 / 2) + g2.getFontMetrics().getHeight() - 2);
    }

    private void createOffscreenBuffer() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration conf = env.getDefaultScreenDevice().getDefaultConfiguration();
        offscreenBuffer = conf.createCompatibleVolatileImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
    }

    private static final Color BACKGROUND_COLOR = new Color(35, 35, 35);
    private static final Color PRIMARY_COLOR = new Color(25, 25, 25);
    private static final Color SECONDARY_COLOR = new Color(45, 45, 45);

    private BufferedImage getGridImage() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color c;
                if (x <= 1 || y <= 1) {
                    c = PRIMARY_COLOR;
                } else if (x % (image.getWidth() / 5) == 0 || y % (image.getHeight() / 5) == 0) {
                    c = SECONDARY_COLOR;
                } else {
                    c = BACKGROUND_COLOR;
                }
                image.setRGB(x, y, c.getRGB());
            }
        }
        return image;
    }

    private class GraphMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            lastMouseButton = e.getButton();
            lastMousePosition = e.getPoint();

            if (lastMouseButton == MouseEvent.BUTTON1) {
                for (int i = 0; i < nodeBounds.size(); i++) {
                    int transformedX = e.getX() - cameraPosition.x;
                    int transformedY = e.getY() - cameraPosition.y;
                    if (!nodeBounds.get(i).contains(transformedX, transformedY))
                        continue;

                    selectedNodeIndex = i;
                    repaint();
                    return;
                }

                selectedNodeIndex = -1;
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (lastMouseButton == MouseEvent.BUTTON1 && selectedNodeIndex >= 0) {
                RoundRectangle2D bounds = nodeBounds.get(selectedNodeIndex);
                bounds.setFrame(bounds.getX() + e.getX() - lastMousePosition.x, bounds.getY() + e.getY() - lastMousePosition.y, bounds.getWidth(), bounds.getHeight());

                lastMousePosition.setLocation(e.getPoint());
                repaint();
            } else if (lastMouseButton == MouseEvent.BUTTON3) {
                cameraPosition.translate(e.getX() - lastMousePosition.x, e.getY() - lastMousePosition.y);
                lastMousePosition.setLocation(e.getPoint());
                repaint();
            }
        }
    }
}
