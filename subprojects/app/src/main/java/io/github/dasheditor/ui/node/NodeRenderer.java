package io.github.dasheditor.ui.node;

import io.github.dasheditor.model.EventNode;
import io.github.dasheditor.model.Graph;
import io.github.dasheditor.model.Node;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class NodeRenderer {

    private final Graph graph;

    private static final Font BOLD_FONT = new Font("Open Sans", Font.BOLD, 12);
    private static final Font BOLDITALIC_FONT = new Font("Open Sans", Font.BOLD | Font.ITALIC, 12);

    public NodeRenderer(Graph graph) {
        this.graph = graph;
    }

    public void renderNode(Graphics2D g, Node node, boolean selected) {
        // TODO: Calculate the node layout (i.e., width, height)
        RoundRectangle2D nodeBounds = new RoundRectangle2D.Double(
                Math.round(node.getPositionX() / 20.0f) * 20, Math.round(node.getPositionY() / 20.0f) * 20,
                200, 140, 20, 20);
        g.setClip(nodeBounds); // Set the base clip.

        // Draw the background and border.
        g.setColor(new Color(15, 15, 15));
        g.fill(nodeBounds);
        if (!selected) {
            g.setColor(Color.BLACK);
            g.draw(nodeBounds);
        }

        // Draw header background.
        g.clipRect((int) nodeBounds.getX(), (int) nodeBounds.getY(), (int) nodeBounds.getWidth(), 40);
        g.setPaint(new GradientPaint(
                (float) nodeBounds.getX(), (float) nodeBounds.getCenterY(), new Color(125, 17, 17),
                (float) nodeBounds.getMaxX(), (float) nodeBounds.getCenterY(), new Color(118, 55, 50, 100)));
        g.fill(g.getClip());
        g.setPaint(new GradientPaint(
                (float) nodeBounds.getX(), (float) nodeBounds.getY(), new Color(255, 255, 255, 100),
                (float) nodeBounds.getMaxX(), 40, new Color(255, 255, 255, 0)));
        g.draw(g.getClip());

        // Reset clip.
        g.setClip(null);

        if (selected) {
            g.setColor(Color.GRAY);
            g.draw(nodeBounds);
        }

        g.setFont(BOLD_FONT);
        g.setColor(Color.WHITE);
        g.drawString(node.getTitle(), (int) nodeBounds.getX() + 10, (int) nodeBounds.getY() + (35 / 2));

        g.setFont(BOLDITALIC_FONT);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(getNodeSubtitle(node), (int) nodeBounds.getX() + 10, (int) nodeBounds.getY() + (35 / 2) + g.getFontMetrics().getHeight() - 2);
    }

    public Node getNodeAt(int x, int y) {
        for (Node node : graph.getNodes()) {
            // TODO: Replace with method to calculate node layout.
            LayoutData layout = new LayoutData(200, 140);
            boolean isContained = x > node.getPositionX() && y > node.getPositionY()
                    && x < (node.getPositionX() + layout.width()) && y < (node.getPositionY() + layout.height());
            if (!isContained)
                continue;
            return node;
        }
        return null;
    }

    private String getNodeSubtitle(Node node) {
        if (node instanceof EventNode) {
            return "Event";
        }
        return null;
    }

    public record LayoutData(int width, int height) {

    }
}
