public class Node {

    int size, g_n, h_n, blankX, blankY,p_index;
    int[][] matrix;

    public Node(int[][] m,int n){
        matrix = m;
        size = n;
        h_n = blankX = blankY = -1;
        g_n=0;
        p_index = -1;
    }

    public void print(){
        for(int i=0;i<size;i++){
            System.out.println();
            for(int j=0;j<size;j++){
                System.out.print(matrix[i][j]+" ");
            }
        }
        System.out.println();
    }


}
