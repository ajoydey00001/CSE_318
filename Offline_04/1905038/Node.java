import java.util.ArrayList;
import java.util.List;

public class Node {
        String value; // unacc, acc , good , vgood
        int number_attribute;
        List<Node> childlist;

        public Node(){
                childlist = new ArrayList<>();

        }

}
