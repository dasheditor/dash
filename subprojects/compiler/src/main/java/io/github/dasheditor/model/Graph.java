package io.github.dasheditor.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final ArrayList<Node> nodes = new ArrayList<>();

    public Graph() {}

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
