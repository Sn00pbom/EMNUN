package com.zlefin.emnun;

import java.util.HashMap;

/**
 * The Connection object's primary purpose is to describe a bond between
 * two "connected" Nodes. Connection objects can hold their own variables, enabling
 * modulation of data when "passing through" between two connected Nodes.
 *
 * @author Zachary Lefin
 * @since 7/26/18
 */
public class Connection {
    public Connection(Node xNode, Node yNode) {
        this.xNode = xNode;
        this.yNode = yNode;
    }


    private Node xNode;
    private Node yNode;

    /**
     * A boolean to indicate whether a Connection is allowed to "flow" or not.
     */
    private boolean open = true;

    private HashMap<String, Object> fields = new HashMap<>();



    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Node getxNode() {
        return xNode;
    }

    public Node getyNode() {
        return yNode;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

}
