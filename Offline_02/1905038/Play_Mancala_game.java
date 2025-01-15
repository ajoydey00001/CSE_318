import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Play_Mancala_game {




    public static Best_move Evalution(State pos,int player ,int k){
        Best_move bm = new Best_move();

        if(k == 1){
            bm.eval =  pos.stone_in_my_storage(player) - pos.stone_in_other_storage(player);

        }
        else if(k == 2){
            bm.eval =  40*(pos.stone_in_my_storage(player) - pos.stone_in_other_storage(player)) + 20*(pos.stone_in_my_side(player) - pos.stone_in_other_side(player));
        }
        else if(k == 3){
            bm.eval =  40*(pos.stone_in_my_storage(player) - pos.stone_in_other_storage(player)) + 20*(pos.stone_in_my_side(player) - pos.stone_in_other_side(player)) + 10*pos.additional_move_earned(player);
        }
        else {
            bm.eval =  40*(pos.stone_in_my_storage(player) - pos.stone_in_other_storage(player)) + 20*(pos.stone_in_my_side(player) - pos.stone_in_other_side(player)) + 10*pos.additional_move_earned(player) + 5*pos.stone_captured(player);
        }
        bm.index = -1;
        return bm;

    }

    public static Best_move function_minimax(State position, int depth, int alpha , int beta, boolean flag, int player, int heuristic){

        if((depth == 0) || (position.is_Game_Over(player))){

            return Evalution(position,player,heuristic);
        }


        if(flag){
            int maximum_eval = -3700;
            int g = -1;
            for(int i=0;i<6;i++){
                if(position.legal_move(i,player)){
                    State duplicate = position.state_copy();
                    boolean move_flag = duplicate.moveHelp(i,player);

                    int eval;
                    Best_move lbm;
                    if(move_flag){
                        if(player == 1){
                            lbm = function_minimax(duplicate,depth-1,alpha,beta,true,1,heuristic);
                        }
                        else {
                            lbm = function_minimax(duplicate,depth - 1,alpha,beta,true,2,heuristic);
                        }
                    }
                    else if(player == 1){
                        lbm = function_minimax(duplicate,depth-1,alpha,beta,false,2,heuristic);
                    }
                    else {
                        lbm = function_minimax(duplicate,depth-1,alpha,beta,false,1,heuristic);
                    }
                    eval = lbm.eval;
                    maximum_eval = Math.max(maximum_eval,eval);
                    if(maximum_eval == eval){
                        g = i;
                    }
                    alpha = Math.max(alpha,eval);

                    if(beta <= alpha){
                        break;
                    }

                }
            }

            Best_move bm = new Best_move();
            bm.eval = maximum_eval;
            bm.index = g;
            return bm;

        }
        else {
            int minimum_eval = 3700;
            int g = -1;
            for(int i=0;i<6;i++){
                if(position.legal_move(i,player)){
                    State duplicate = position.state_copy();
                    boolean move_flag = duplicate.moveHelp(i,player);


                    Best_move lbm;
                    int eval;

                    if(move_flag){
                        if(player == 1){
                            lbm = function_minimax(duplicate,depth-1,alpha,beta,false,1,heuristic);
                        }
                        else {
                            lbm = function_minimax(duplicate,depth - 1,alpha,beta,false,2,heuristic);
                        }
                    }
                    else if(player == 1){
                        lbm = function_minimax(duplicate,depth -1,alpha,beta,true,2,heuristic);
                    }
                    else {
                        lbm = function_minimax(duplicate,depth - 1,alpha,beta,true,1,heuristic);
                    }
                    eval = lbm.eval;
                    minimum_eval = Math.min(minimum_eval,eval);
                    if(minimum_eval == eval){
                        g = i;
                    }

                    beta = Math.min(beta,eval);
                    if(beta <= alpha){
                        break;
                    }

                }
            }
            Best_move bm = new Best_move();
            bm.eval = minimum_eval;
            bm.index = g;
            return bm;

        }




    }






        public static void main(String[] args) throws IOException {


            int[] arr = new int[14];
            for(int i=0;i<6;i++) arr[i]=4;
            arr[6] = arr[13] = 0;
            for(int i=7;i<13;i++) arr[i] = 4;

            State s1 = new State(arr);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            int depth, heuristic_1, heuristic_2;

            System.out.println("This game can be played in 3 types:");
            System.out.println("1. Player 1: User vs Player 2: Computer");
            System.out.println("2. Player 1: Computer vs Player 2: Computer");
            System.out.println("3. User vs User");
            int choice = Integer.parseInt(br.readLine());
            if(choice == 1){
                System.out.println("Which heuristic do you want for Computer");
                heuristic_2 = Integer.parseInt(br.readLine());
                System.out.println("Enter the depth ");
                depth = Integer.parseInt(br.readLine());
                s1.print();
                while (true){
                    if(s1.is_Game_Over(1)){
                        break;
                    }
                    boolean can_play = true;
                    System.out.println("Please enter your choice : ");
                    int l = Integer.parseInt(br.readLine());

                    while (s1.moveHelp(l-1,1)){
                        System.out.println("You gain additional move");
                        s1.print();
                        if(s1.is_Game_Over(1)){
                            can_play = false;
                            break;
                        }
                        System.out.println("Please enter your choice:");
                        l = Integer.parseInt(br.readLine());
                    }
                    if(!can_play){
                        break;
                    }

                    s1.print();


                    while (true){
                        if(s1.is_Game_Over(2)){
                            can_play = false;
                            break;
                        }

                        Best_move bm = function_minimax(s1,depth,-3700, 3700,true,2,heuristic_2);
                        System.out.println("Computer choose "+(bm.index + 1)+"th bin");
                        if(s1.moveHelp(bm.index,2)){
                            System.out.println("Computer gain additional movie");
                            s1.print();
                        }
                        else {
                            s1.print();
                            break;
                        }

                    }
                    if(!can_play){
                        break;
                    }

                }

                System.out.println("User gets "+(s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) + " points");
                System.out.println("Computer gets "+ (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2)) + " points");
                if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) > (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))){
                    System.out.println("User win in the game");
                }
                else if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) < (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))) {
                    System.out.println("Computer wins in the game");
                }
                else {
                    System.out.println(" The game is draw");
                }
            }
            else if(choice == 2){
                System.out.println("Which heuristic do you want for player 1");
                heuristic_1 = Integer.parseInt(br.readLine());
                System.out.println("Which heuristic do you want for player 2");
                heuristic_2 = Integer.parseInt(br.readLine());
                System.out.println("Enter the depth");
                depth = Integer.parseInt(br.readLine());

                s1.print();
                while (true){
                    boolean can_play = true;


                    while (true){
                        if(s1.is_Game_Over(1)){
                            can_play = false;
                            break;
                        }
                        Best_move bm = function_minimax(s1,depth,-3700, 3700,true,1,heuristic_1);
                        System.out.println("Computer 1 chooses "+(bm.index + 1)+"th bin");
                        if(s1.moveHelp(bm.index,1)){
                            System.out.println("Computer 1 gain additional movie");
                            s1.print();
                        }
                        else {
                            s1.print();
                            break;
                        }

                    }


                    if(!can_play){
                        break;
                    }



                    while (true){
                        if(s1.is_Game_Over(2)){
                            can_play = false;
                            break;
                        }

                        Best_move bm = function_minimax(s1,depth,-3700, 3700,true,2,heuristic_2);
                        System.out.println("Computer 2 chooses "+(bm.index + 1)+"th bin");
                        if(s1.moveHelp(bm.index,2)){
                            System.out.println("Computer 2 gain additional movie");
                            s1.print();
                        }
                        else {
                            s1.print();
                            break;
                        }

                    }

                    if(!can_play){
                        break;
                    }

                }

                System.out.println("Computer 1 gets "+(s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) + " points");
                System.out.println("Computer 2 gets "+ (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2)) + " points");
                if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) > (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))){
                    System.out.println("Computer 1 wins in the game");
                }
                else if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) < (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))) {
                    System.out.println("Computer 2 wins in the game");
                }
                else {
                    System.out.println(" The game is draw");
                }


            }
            else {


                s1.print();
                while (true){
                    if(s1.is_Game_Over(1)){
                        break;
                    }
                    boolean can_play = true;
                    System.out.println("User 1 , Please enter your choice : ");
                    int l = Integer.parseInt(br.readLine());

                    while (s1.moveHelp(l-1,1)){
                        System.out.println("You gain additional move");
                        s1.print();
                        if(s1.is_Game_Over(1)){
                            can_play = false;
                            break;
                        }
                        System.out.println("User 1 , Please enter your choice:");
                        l = Integer.parseInt(br.readLine());
                    }
                    if(!can_play){
                        break;
                    }

                    s1.print();
                    if(s1.is_Game_Over(2)){
                        break;
                    }

                    System.out.println("User 2, Please enter your choice : ");
                     l = Integer.parseInt(br.readLine());

                    while (s1.moveHelp(l-1,2)){
                        System.out.println("You gain additional move");
                        s1.print();
                        if(s1.is_Game_Over(2)){
                            can_play = false;
                            break;
                        }
                        System.out.println("User 2 , Please enter your choice:");
                        l = Integer.parseInt(br.readLine());
                    }

                    s1.print();
                    if(!can_play){
                        break;
                    }

                }

                System.out.println("User 1 gets "+(s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) + " points");
                System.out.println("User 2 gets "+ (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2)) + " points");
                if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) > (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))){
                    System.out.println("User 1 wins in the game");
                }
                else if((s1.stone_in_my_storage(1) + s1.stone_in_my_side(1)) < (s1.stone_in_my_storage(2) + s1.stone_in_my_side(2))) {
                    System.out.println("User 2 wins in the game");
                }
                else {
                    System.out.println(" The game is draw");
                }

            }


        }




}
