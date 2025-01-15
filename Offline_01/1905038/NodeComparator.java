import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    public int compare(Node n1, Node n2){
        if((n1.g_n + n1.h_n) > (n2.g_n + n2.h_n) ){
            return 1;
        }
        else if((n1.g_n + n1.h_n) < (n2.g_n + n2.h_n) ){
            return -1;
        }
        return 0;
    }

}
