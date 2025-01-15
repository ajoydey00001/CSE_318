public class State {

    int[] arr;
    int[] reverse_arr;
    int stones_captured_player_1=0;
    int stones_captured_player_2 = 0;
    int additional_move_earned_player_1 = 0;
    int additional_move_earned_player_2 = 0;

    public State(int[] a,int p1_a, int p1_s, int p2_a, int p2_s){
        arr = a;
        additional_move_earned_player_1 = p1_a;
        stones_captured_player_1 = p1_s;
        additional_move_earned_player_2 = p2_a;
        stones_captured_player_2 = p2_s;
        reverse_arr = new int[14];
        reverse_arr[0]= 12;
        reverse_arr[1] = 11;
        reverse_arr[2] = 10;
        reverse_arr[3] = 9;
        reverse_arr[4] = 8;
        reverse_arr[5] = 7;
        reverse_arr[6] = -1;
        reverse_arr[7] = 5;
        reverse_arr[8] = 4;
        reverse_arr[9] = 3;
        reverse_arr[10] = 2;
        reverse_arr[11] = 1;
        reverse_arr[12] = 0;
        reverse_arr[13] =-1;
    }
    public State(int[] a){
        arr = a;
        reverse_arr = new int[14];
        reverse_arr[0]= 12;
        reverse_arr[1] = 11;
        reverse_arr[2] = 10;
        reverse_arr[3] = 9;
        reverse_arr[4] = 8;
        reverse_arr[5] = 7;
        reverse_arr[6] = -1;
        reverse_arr[7] = 5;
        reverse_arr[8] = 4;
        reverse_arr[9] = 3;
        reverse_arr[10] = 2;
        reverse_arr[11] = 1;
        reverse_arr[12] = 0;
        reverse_arr[13] =-1;
    }
    public State(){
        arr = new int[14];
        reverse_arr = new int[14];
        reverse_arr[0]= 12;
        reverse_arr[1] = 11;
        reverse_arr[2] = 10;
        reverse_arr[3] = 9;
        reverse_arr[4] = 8;
        reverse_arr[5] = 7;
        reverse_arr[6] = -1;
        reverse_arr[7] = 5;
        reverse_arr[8] = 4;
        reverse_arr[9] = 3;
        reverse_arr[10] = 2;
        reverse_arr[11] = 1;
        reverse_arr[12] = 0;
        reverse_arr[13] =-1;
    }

    public State state_copy(){
        State copy = new State();
        for (int i=0;i<14;i++){
            copy.arr[i] = arr[i];
        }
        copy.stones_captured_player_1 = stones_captured_player_1;
        copy.stones_captured_player_2 = stones_captured_player_2;
        copy.additional_move_earned_player_1 = additional_move_earned_player_1;
        copy.additional_move_earned_player_2 = additional_move_earned_player_2;
        return copy;

    }
// 0th index based
    public boolean moveHelp(int index , int p){
        if(p==1){
            return move(index,p);
        }
        else {
            return move(12-index , p);
        }
    }

    public boolean move(int index , int player){
        boolean flag ;
        if(player == 1 ){
            flag = true;
        }
        else {
            flag = false;
        }

        int n = arr[index];
        arr[index] =0;
        int l = index;
        if(n>1){
            n--;

            for(int i=0;i<n;i++){
                l = (l+1) %14;
                if(flag && l==13){
                    l = (l+1) %14;
                }
                if((!flag) && l==6){
                    l = (l+1) %14;
                }
                arr[l]++;

            }

        }
        l = (l+1) %14;

        if(flag && l==13){
            l = (l+1) %14;
        }
        if((!flag) && l==6){
            l = (l+1) %14;
        }

        if(flag && l==6){
            arr[l]++;
            if(!is_Game_Over(1))
            {
                additional_move_earned_player_1 ++;
                return true;
            }
            else {
                return true;
            }

        }
        if((!flag) && l==13){
            arr[l]++;
            if(!is_Game_Over(2))
            {
                additional_move_earned_player_2 ++;
                return true;
            }
            else {
                return true;
            }

        }

        int k = reverse_arr[l];
        if(((arr[l] ==0 && arr[k]!=0) && (0 <= l && l<= 5)) && flag){

            int s = arr[k] +1;
            arr[k] = 0;
             arr[6] += s;
            stones_captured_player_1 +=s;

        }
        else if(((arr[l] ==0 && arr[k] !=0) && (7 <= l && l<= 12)) && !flag){

            int s = arr[k] +1;
            arr[k] = 0;
            arr[13] += s;
            stones_captured_player_2 +=s;
        }
        else{
            arr[l]++;
        }
        return false;



    }

    public boolean is_Game_Over(int player){

        if(player == 1){
            boolean flag = true;
            for(int i=0;i<6;i++){
                if(arr[i] != 0){
                    flag = false;
                    break;
                }
            }
            return flag;
        }
        else {
            boolean flag = true;
            for(int i=7;i<13;i++){
                if(arr[i] != 0){
                    flag = false;
                    break;
                }
            }
            return flag;
        }
    }

    public void print(){


        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Player 2 :");
        System.out.println("(Storage bin)        (1st bin)   (2nd bin)   (3rd bin)   (4th bin)   (5th bin)   (6th bin)       ");
        System.out.println("-----------          ---------    --------    -------     -------     -------     -------         ");
        System.out.println();
        System.out.println("    "+arr[13] +"                    "+arr[12]+"          "+arr[11]+"          "+arr[10]+"             "+arr[9]+"            "+arr[8]+"           "+ arr[7]+"                ");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Player 1:");
        System.out.println("                    (1st bin)   (2nd bin)   (3rd bin)   (4th bin)   (5th bin)   (6th bin)       (Storage bin)");
        System.out.println("                    ---------    --------    -------     -------     -------     -------         -----------");
        System.out.println();
        System.out.println("                        "+arr[0]+"          "+arr[1]+"          "+arr[2]+"             "+arr[3]+"            "+arr[4]+"           "+ arr[5]+"                " + arr[6]);

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

    }



    public int stone_in_my_storage(int i){
        if (i==1){
            return arr[6];
        }
        else{
            return arr[13];
        }
    }

    public int stone_in_other_storage(int i){
        if(i==1){
            return  arr[13];
        }
        else {
            return arr[6];
        }

    }
    public int stone_in_my_side(int player){
        int s=0;
        if(player == 1){
            for(int i=0;i<6;i++){
                s+=arr[i];
            }
        }
        else {
            for(int i=7;i<13;i++){
                s+=arr[i];
            }
        }
        return s;
    }
    public int stone_in_other_side(int player){
        int s=0;
        if(player == 2){
            for(int i=0;i<6;i++){
                s+=arr[i];
            }
        }
        else {
            for(int i=7;i<13;i++){
                s+=arr[i];
            }
        }
        return s;
    }
    public int additional_move_earned(int player){
        if(player == 1){
            return additional_move_earned_player_1;
        }
        else {
            return additional_move_earned_player_2;
        }
    }

    public int stone_captured(int player){
        if(player == 1){
            return stones_captured_player_1;
        }
        else {
            return stones_captured_player_2;
        }
    }
    // 0th index based
    public boolean legal_move(int index, int player){
      if(player == 1){
          if(arr[index] != 0) {
              return true;
          }
          else {
              return false;
          }
      }
      else {
          if(arr[12 - index] != 0){
              return true;
          }
          else {
              return false;
          }
      }


    }




}
