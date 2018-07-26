package com.zlefin.emnun;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The object used to describe central anchor points to create
 * an interconnected web.
 *
 * @author Zachary Lefin
 * @since 7/26/18
 */
public class Node {

    public Node(boolean verbose) {
        this.verbose = verbose;
    }
    public Node(){

    }

    /**
     * Should a given Node say anything?
     */
    private boolean verbose = false;

    private HashMap<String, Object> fields = new HashMap<>();
    private HashMap<String, ArrayList<Connection>> connectionGroups = new HashMap<>();


    /**
     * Create a connection to another Node.
     * Must give connectionGroup name in both Nodes
     * to indicate under which circumstances it will connect.
     *
     * @param yNode
     * @param xGroupName
     * @param yGroupName
     */
    public void connect(Node yNode, String xGroupName, String yGroupName) {
        Node xNode = this;

        // check if same
        if (xNode.equals(yNode)) {
            if(verbose) System.out.println("Error: can't connect node to itself");
            return;
        }
        // check if groups exist
        if (!xNode.connectionGroups.containsKey(xGroupName)) {
            xNode.connectionGroups.put(xGroupName, new ArrayList<>());
        }
        if (!yNode.connectionGroups.containsKey(yGroupName)) {
            yNode.connectionGroups.put(yGroupName, new ArrayList<>());
        }
        Connection connection = new Connection(this, yNode);
        xNode.connectionGroups.get(xGroupName).add(connection);
        yNode.connectionGroups.get(yGroupName).add(connection);
    }

    /**
     * Removes a connection between two Nodes.
     *
     * @param yNode
     * @param xGroupName
     * @param yGroupName
     */
    public void disconnect(Node yNode, String xGroupName, String yGroupName) {
        Node xNode = this;

        // check both actually own the connection
        if (xNode.isConnectedTo(yNode, xGroupName) && yNode.isConnectedTo(xNode, yGroupName)) {

            xNode.disconnect(yNode, xGroupName);
            yNode.disconnect(xNode, yGroupName);
        }else{
            if(verbose) System.out.println("Error: one or both nodes missing connection");
        }

    }

    private void disconnect(Node destNode, String groupName) {
        if (!connectionGroups.containsKey(groupName)) {
            if(verbose) System.out.println("Error: groupname '" + groupName + "' not in node " + this.toString());
            return;
        }

        ArrayList<Connection> connections = connectionGroups.get(groupName);

        if (connections.contains(destNode)) {
            connections.remove(destNode);
        } else {
            if(verbose) System.out.println("Error: no node found to remove");
        }


    }

    /**
     * Check if this Node is connected to that Node via
     * a given connection group.
     *
     * @param node
     * @param groupName
     * @return
     */
    public boolean isConnectedTo(Node node, String groupName) {
        if (!connectionGroups.containsKey(groupName)) {
            if(verbose) System.out.println("Warning: no group in node");
            return false;
        }
        for (Connection connection : connectionGroups.get(groupName)) {
            if (connection.getxNode().equals(node) || connection.getyNode().equals(node)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an ArrayList of Connections that are all connected
     * to this Node by any group.
     *
     * @return
     */
    public ArrayList<Connection> getAllConnections() {
        ArrayList<Connection> out = new ArrayList<>();
        for (ArrayList<Connection> connections : connectionGroups.values()) {
            for (Connection connection : connections) {
                out.add(connection);
            }
        }
        return out;
    }

    // Moved this function to static Network Util class
    @Deprecated
    private ArrayList<Connection> getAllNetworkConnections() {
        ArrayList<Connection> last = new ArrayList<>();
        return getAllNetworkConnections(last);
    }

    @Deprecated
    private ArrayList<Connection> getAllNetworkConnections(ArrayList<Connection> last) {
        ArrayList<Connection> curr = last;
        ArrayList<Connection> owned = getAllConnections();

        for (Connection connection : owned) {
            if (!curr.contains(connection)) {
                curr.add(connection);
                Node otherNode = connection.getxNode().equals(this) ? connection.getyNode() : connection.getxNode();
                otherNode.getAllNetworkConnections(curr);
            }
        }

        return curr;

    }



    public HashMap<String, Object> getFields() {
        return fields;
    }

    public HashMap<String, ArrayList<Connection>> getConnectionGroups() {
        return connectionGroups;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
