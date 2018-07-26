package example;

import com.zlefin.emnun.NetworkUtil;
import com.zlefin.emnun.Node;
import java.util.ArrayList;
import java.util.Random;

/**
 * A small, runnable command-line setup to display functionality
 *
 * @author Zachary Lefin
 * @since 7/26/18
 */
public class Main {

    public static void main(String[] args) {

        Node ano = new Node();
        Node bno = new Node();
        Node cno = new Node();
        Node dno = new Node();
        Node eno = new Node();

        String fwd = "forward";
        String bck = "backward";

        // unwanted self connect, if verbose on will inform in console
        NetworkUtil.getAllChainedNodes(ano).forEach(node -> node.setVerbose(true));
        ano.connect(ano, fwd, bck);

        /**

        The following network layout is formed by the connect function calls.

          -b-
         / | \
        a--c--e
         \ | /
          -d-

        Different strings can be used to indicate layers, one-way value passing, etc.
        In this case, I've used "forward" and "backward" to indicate some sort of "flow" in the network

         */
        ano.connect(bno, fwd, bck);
        ano.connect(cno, fwd, bck);
        ano.connect(dno, fwd, bck);
        bno.connect(cno, fwd, bck);
        bno.connect(eno, fwd, bck);
        cno.connect(dno, fwd, bck);
        cno.connect(eno, fwd, bck);
        dno.connect(eno, fwd, bck);

        cno.getAllConnections().forEach(connection -> System.out.println(connection));


        dno.disconnect(eno, fwd, bck);

        System.out.println(NetworkUtil.getAllChainedConnections(dno).size());

        ArrayList<Node> allNodes = NetworkUtil.getAllChainedNodes(ano);
        Random random = new Random();
        allNodes.forEach(node -> node.getFields().put("value", random.nextDouble()));
        NetworkUtil.printAllChainNodeFields(ano, "value");
    }
}
