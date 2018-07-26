package com.zlefin.emnun;

import java.util.ArrayList;

/**
 * Various methods to apply operations to a group of
 * connected Nodes, individual Nodes, or Connections.
 * Primarily all static methods.
 *
 * @author Zachary Lefin
 * @since 7/26/18
 */
public class NetworkUtil {


    /**
     * Creates an ArrayList of all Nodes with Connections to
     * startNode, and prints the Nodes' field value if they
     * have the given string in their fields HashMap.
     *
     * @param startNode
     * @param fieldName
     */
    public static void printAllChainNodeFields(Node startNode, String fieldName) {
        ArrayList<Node> allNodes = getAllChainedNodes(startNode);
        for (int i = 0; i < allNodes.size(); i++) {
            if(!allNodes.get(i).getFields().containsKey(fieldName)) continue;
            System.out.println(i + ": " + allNodes.get(i).getFields().get(fieldName));
        }
    }

    /**
     * Returns an ArrayList of all Nodes that are linked to the startNode
     *
     * @param startNode
     * @return
     */
    public static ArrayList<Node> getAllChainedNodes(Node startNode) {
        ArrayList<Node> out = new ArrayList<>();
        out.add(startNode);

        for (Connection connection : getAllChainedConnections(startNode)) {
            Node xNode = connection.getxNode();
            Node yNode = connection.getyNode();

            if (!out.contains(xNode)) {
                out.add(xNode);
            }
            if (!out.contains(yNode)) {
                out.add(yNode);
            }
        }

        return out;
    }

    /**
     * Returns an ArrayList of all Connections that are linked to the startNode
     *
     * @param startNode
     * @return
     */
    public static ArrayList<Connection> getAllChainedConnections(Node startNode) {
        ArrayList<Connection> init = new ArrayList<>();
        return getAllChainedConnections(startNode, init);
    }

    private static ArrayList<Connection> getAllChainedConnections(Node startNode, ArrayList<Connection> out) {
        Node node = startNode;
        ArrayList<Connection> owned = node.getAllConnections();


        for (Connection connection : owned) {
            if (!out.contains(connection)) {
                out.add(connection);
                node = connection.getxNode().equals(node) ? connection.getyNode() : connection.getxNode();
                getAllChainedConnections(node, out);
            }
        }

        return out;

    }

}
