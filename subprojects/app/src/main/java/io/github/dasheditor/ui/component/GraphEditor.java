package io.github.dasheditor.ui.component;

import io.github.dasheditor.model.Graph;
import io.github.dasheditor.model.Node;
import io.github.dasheditor.ui.node.NodeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class GraphEditor extends JPanel {

    private VolatileImage offscreenBuffer;
    private final Graph graph;
    private final NodeRenderer nodeRenderer;
    private final BufferedImage gridImage = getGridImage();
    private final Point cameraPosition = new Point(0, 0);
    private Point lastMousePosition;
    private int lastMouseButton;
    private int selectedNodeIndex = -1;

    private static final Color BACKGROUND_COLOR = new Color(35, 35, 35);
    private static final Color PRIMARY_COLOR = new Color(25, 25, 25);
    private static final Color SECONDARY_COLOR = new Color(45, 45, 45);

    public GraphEditor(Graph graph) {
        this.graph = graph;
        this.nodeRenderer = new NodeRenderer(graph);

        GraphMouseListener mouseListener = new GraphMouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
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

            for (Node node : graph.getNodes()) {
                boolean selected = selectedNodeIndex >= 0 && graph.getNodes().get(selectedNodeIndex).equals(node);
                nodeRenderer.renderNode(g2, node, selected);
            }

            g2.dispose();
            g.drawImage(offscreenBuffer, 0, 0, null);
        } while (offscreenBuffer.contentsLost());
    }

    private void createOffscreenBuffer() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration conf = env.getDefaultScreenDevice().getDefaultConfiguration();
        offscreenBuffer = conf.createCompatibleVolatileImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
    }

    private BufferedImage getGridImage() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color c;
                if (x < 1 || y < 1) {
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
                selectedNodeIndex = -1;
                for (int i = 0; i < graph.getNodes().size(); i++) {
                    Node node = nodeRenderer.getNodeAt(e.getX() - cameraPosition.x, e.getY() - cameraPosition.y);
                    if (node == null)
                        continue;

                    selectedNodeIndex = i;
                    repaint();
                    return;
                }
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (lastMouseButton == MouseEvent.BUTTON1) {
                if (selectedNodeIndex < 0)
                    return;

                Node node = nodeRenderer.getNodeAt(e.getX() - cameraPosition.x, e.getY() - cameraPosition.y);
                if (node == null)
                    return;

                node.setPositionX(node.getPositionX() + (e.getX() - lastMousePosition.x));
                node.setPositionY(node.getPositionY() + (e.getY() - lastMousePosition.y));

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
