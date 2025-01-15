import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class main {


    public static boolean checkSolvable(int[][] mtx, int p, int q,int n){
        int[] arr = new int[n*n];
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                arr[k] = mtx[i][j];
                k++;
            }
        }
        int sum=0;
        for(int i=0;i<n*n-1;i++){
            for(int j=i+1;j<n*n;j++){
                if(arr[i]!=0 && arr[j]!=0){
                    if(arr[i]>arr[j]){
                        sum++;
                    }
                }
            }
        }
        //System.out.println("inversion is  "+sum);
        if(n%2==1){
            if(sum%2==0){
                return true;
            }
            else {
                return false;
            }
        }
        else{
                int t = (n-p)+1;
            //System.out.println("Position is  "+t);
                if(sum%2==0 && t%2==1){
                    return true;
                }
                else if(sum%2==1 && t%2==0){
                    return true;
                }
                else {
                    return false;
                }

        }


    }

    public static int manhattan_distance_priority_function(int[][] mtx, int n){
        int p,q;
        int[][] arr = new int[n*n+1][2];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){

                int temp = i*n + j + 1;
                arr[temp][0]=i;
                arr[temp][1]=j;
            }
        }
        int sum=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(mtx[i][j]!=0){
                    int temp = mtx[i][j];
                    p = arr[temp][0] - i;
                    p = p>0 ? p : (-1) * p;
                    sum +=p;
                    q= arr[temp][1] - j;
                    q = q>0? q : (-1)*q;
                    sum +=q;

                }
            }
        }
        return sum;

    }

    public static void A_Algo_using_manhattan_distance(int[][] mtx,int blankX, int blankY, int n){
        int number =-1;
        Node parent = new Node(mtx,n);
        parent.g_n = 0;
        parent.h_n = manhattan_distance_priority_function(parent.matrix, parent.size);
        if(parent.h_n==0){
            System.out.println("Minimum number of moves 0");
            parent.print();
            return;
        }
        blankX--;
        blankY--;
        parent.blankX = blankX;
        parent.blankY = blankY;
        parent.p_index = -1;

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        pq.add(parent);
        List<Node> closedlist = new ArrayList<>();
        int[] X = {-1,1,0,0};
        int[] Y = {0,0,-1,1};


        while (!pq.isEmpty()){
            Node node = pq.poll();
           // System.out.println();
            //node.print();
            //System.out.println("g_n is "+node.g_n);
            //System.out.println("h_n is "+node.h_n);
            //System.out.println("blankX is "+node.blankX);
            //System.out.println("blankY is "+node.blankY);
            closedlist.add(node);
            number++;
            //System.out.println();
            //System.out.println("Neighbours are ");
            for(int i=0;i<4;i++){

                Node neighbour = createNeighbour(node.matrix,node.blankX,node.blankY,n,X[i],Y[i],number);
                if(neighbour != null){

                    boolean flag = checkSimilarity(neighbour,closedlist,n);
                    if(!flag){
                        neighbour.g_n = node.g_n+1;
                        neighbour.h_n = manhattan_distance_priority_function(neighbour.matrix,n);
                       // neighbour.print();
                        if(neighbour.h_n==0){
                            closedlist.add(neighbour);
                            System.out.println("Minimum number of moves "+ neighbour.g_n);
                            int t1 = closedlist.size()-1;
                            Stack<Node> stack = new Stack<>();
                            while (t1!=-1){
                                stack.push(closedlist.get(t1));
                                t1 = closedlist.get(t1).p_index;
                            }
                            while (!stack.isEmpty()){
                                Node nud = stack.pop();
                                nud.print();
                            }

                            return;
                        }


                        pq.add(neighbour);
                    }

                }


            }


        }



    }


    public static int hamming_distance_priority_function(int[][] mtx,int n){
        int[][] temp = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                temp[i][j] = i*n + j + 1;
            }
        }
        temp[n-1][n-1] = 0;
        int sum=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(mtx[i][j]!=0){
                    if(temp[i][j] != mtx[i][j]){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public static Node createNeighbour(int[][] parent, int blankX, int blankY, int n, int p,int q, int index){
        if((blankX+p==-1) || (blankX+p ==n)){
            return null;
        }
        if((blankY + q == -1) || (blankY + q == n)){
            return null;
        }

        int[][] temp = new int[n][n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(parent[i][j]==0)
                    continue;
                if(i==blankX+p && j==blankY+q)
                    continue;
                temp[i][j] = parent[i][j];


            }
        }
        temp[blankX][blankY] = parent[blankX+p][blankY+q];
        temp[blankX+p][blankY+q] = 0;
        Node neighbour = new Node(temp,n);
        neighbour.blankY= blankY+q;
        neighbour.blankX = blankX + p;
        neighbour.p_index = index;
        return neighbour;

    }

    public static boolean checkSimilarity(Node node, List<Node> list, int n){

        for(int i=0;i<list.size();i++){
            int[][] p = node.matrix;
            int[][] q = list.get(i).matrix;
            boolean flag = true;

            for(int j=0;j<n;j++){
                for(int k=0;k<n;k++){
                    if(p[j][k] != q[j][k]){
                        flag = false;
                    }
                }
            }
            if(flag)
            {
                return true;
            }

        }
        return false;

    }

    public static void A_Algo_using_hamming_distance(int[][] mtx,int blankX, int blankY, int n){
        int number =-1;
        Node parent = new Node(mtx,n);
        parent.g_n = 0;
        parent.h_n = hamming_distance_priority_function(parent.matrix, parent.size);
        if(parent.h_n==0){
            System.out.println("Minimum number of moves 0");
            parent.print();
            return;
        }
        blankX--;
        blankY--;
        parent.blankX = blankX;
        parent.blankY = blankY;
        parent.p_index = -1;

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        pq.add(parent);
        List<Node> closedlist = new ArrayList<>();
        int[] X = {-1,1,0,0};
        int[] Y = {0,0,-1,1};


        while (!pq.isEmpty()){
            Node node = pq.poll();
            //System.out.println();
            //node.print();
           // System.out.println("g_n is "+node.g_n);
           // System.out.println("h_n is "+node.h_n);
           // System.out.println("blankX is "+node.blankX);
            //System.out.println("blankY is "+node.blankY);
            closedlist.add(node);
            number++;
           // System.out.println();
           // System.out.println("Neighbours are ");
            for(int i=0;i<4;i++){

                    Node neighbour = createNeighbour(node.matrix,node.blankX,node.blankY,n,X[i],Y[i],number);
                    if(neighbour != null){

                        boolean flag = checkSimilarity(neighbour,closedlist,n);
                        if(!flag){
                            neighbour.g_n = node.g_n+1;
                            neighbour.h_n = hamming_distance_priority_function(neighbour.matrix,n);
                            //neighbour.print();
                            if(neighbour.h_n==0){
                                closedlist.add(neighbour);
                                System.out.println("Minimum number of moves "+ neighbour.g_n);
                                int t1 = closedlist.size()-1;
                                Stack<Node> stack = new Stack<>();
                                while (t1!=-1){
                                    stack.push(closedlist.get(t1));
                                    t1 = closedlist.get(t1).p_index;
                                }
                                while (!stack.isEmpty()){
                                    Node nud = stack.pop();
                                    nud.print();
                                }

                                return;
                            }


                            pq.add(neighbour);
                        }

                    }


            }


        }



    }


    public static void main(String[] arg) throws IOException {

        int n,i,j,k,l,p=-1,q=-1;
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        int[][] matrix1 = new int[n][n];
        for(i=0;i<n;i++){
            for(j=0;j<n;j++){
                k = Integer.parseInt(br.readLine());
                if(k==0) {
                    p=i+1;
                    q=j+1;
                }
                matrix1[i][j]=k;
            }
        }

        if(checkSolvable(matrix1,p,q,n)){
            System.out.println("It is a solvable puzzle");

            System.out.println();
            System.out.println("By using Hamming distance priority function :");
            System.out.println();
            A_Algo_using_hamming_distance(matrix1,p,q,n);
            System.out.println();
            System.out.println();
            System.out.println("By using Manhattan distance priority function :");
            System.out.println();
            A_Algo_using_manhattan_distance(matrix1,p,q,n);


        }
        else {
            System.out.println("Unsolvable puzzle");
        }

    }

}
