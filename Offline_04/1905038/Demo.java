import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Demo {

    public static List<List<String>> attributes_list;
    public static Node tree_head;

    public static Random rand;
    public static void attribute_initialize(){
        List<String> temp =  new ArrayList<>();
        temp.add("vhigh");
        temp.add("high");
        temp.add("med");
        temp.add("low");
        attributes_list.add(temp);
        temp = new ArrayList<>();
        temp.add("vhigh");
        temp.add("high");
        temp.add("med");
        temp.add("low");
        attributes_list.add(temp);
        temp = new ArrayList<>();
        temp.add("2");
        temp.add("3");
        temp.add("4");
        temp.add("5more");
        attributes_list.add(temp);
        temp = new ArrayList<>();
        temp.add("2");
        temp.add("4");
        temp.add("more");
        attributes_list.add(temp);
        temp = new ArrayList<>();
        temp.add("small");
        temp.add("med");
        temp.add("big");
        attributes_list.add(temp);
        temp = new ArrayList<>();
        temp.add("low");
        temp.add("med");
        temp.add("high");
        attributes_list.add(temp);


    }

    public static String Plurality_Value(List<Example> list){

        int a1,a2,a3,a4;
        a1=a2=a3=a4=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).Value_class.equalsIgnoreCase("unacc")){
                a1++;
            }
            else if(list.get(i).Value_class.equalsIgnoreCase("acc")){
                a2++;
            }
            else if(list.get(i).Value_class.equalsIgnoreCase("good")){
                a3++;
            }
            else if(list.get(i).Value_class.equalsIgnoreCase("vgood")){
                a4++;
            }
        }

        if(a1>=a2 && a1>=a3 && a1>=a4){

            return "unacc";
        }
        if(a2>=a1 && a2>=a3 && a2>=a4){

            return "acc";
        }
        if(a3>=a1 && a3>=a2 && a3>=a4){

            return "good";
        }

        return "vgood";

    }
    public static boolean Is_all_example_have_same_classification(List<Example> list){
        if(list.size()==1) return true;
        for(int i=1;i<list.size();i++){
            if(!list.get(i).Value_class.equalsIgnoreCase(list.get(0).Value_class)){
                return false;
            }
        }
        return true;
    }

    public static double B_function(List<Example> e_list){

        int a1,a2,a3,a4;
        a1=a2=a3=a4=0;
        for(int i=0;i<e_list.size();i++){
            if(e_list.get(i).Value_class.equalsIgnoreCase("unacc")){
                a1++;
            }
            else if(e_list.get(i).Value_class.equalsIgnoreCase("acc")) {
                a2++;
            }
            else if(e_list.get(i).Value_class.equalsIgnoreCase("good")){
                a3++;
            }
            else if(e_list.get(i).Value_class.equalsIgnoreCase("vgood")) {
                a4++;
            }
        }

        double result=0.0;
        if(a1 != 0 && a1 != e_list.size()){
            result = result + ((a1*1.0)/e_list.size())*(Math.log((e_list.size()*1.0)/a1) / Math.log(2));
        }
        if(a2 != 0 && a2 != e_list.size()){
            result += ((a2*1.0)/e_list.size())*(Math.log((e_list.size()*1.0) / a2) / Math.log(2));
        }
        if(a3 != 0 && a3 != e_list.size()){
            result += ((a3*1.0)/e_list.size())*(Math.log((e_list.size()*1.0)/a3) / Math.log(2));
        }
        if(a4 != 0 && a4 != e_list.size()) {
            result += ((a4*1.0)/e_list.size())*(Math.log((e_list.size()*1.0)/a4) / Math.log(2));
        }

        return result;
    }
    public static Example get_duplicate(Example e){
        Example temp = new Example();
        temp.Value_class = e.Value_class;
        for(int i=0;i<e.attributes.size();i++){
            temp.attributes.add(e.attributes.get(i));
        }
        return temp;
    }

    public static int Importance(List<Integer> a_list, List<Example> e_list){
            if(a_list.size()==1) return a_list.get(0);
            int max_attribute = -1;
            double max_gain = 0.0;
            double source_entropy = B_function(e_list);
            double information_gain=0.0;
            double Remainder=0.0;
            for(int i=0;i<a_list.size();i++){
                if(a_list.get(i)==1){
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();
                    List<Example> l4 = new ArrayList<>();
                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(0).equalsIgnoreCase("vhigh")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(0).equalsIgnoreCase("high")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(0).equalsIgnoreCase("med")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(0).equalsIgnoreCase("low")){
                            l4.add(get_duplicate(e_list.get(j)));
                        }


                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    Remainder += ((l4.size()*1.0) / e_list.size()) * B_function(l4);
                    information_gain = source_entropy - Remainder;
                    max_gain = Math.max(max_gain,information_gain);


                }
                else if(a_list.get(i)==2){
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();
                    List<Example> l4 = new ArrayList<>();
                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(1).equalsIgnoreCase("vhigh")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(1).equalsIgnoreCase("high")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(1).equalsIgnoreCase("med")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(1).equalsIgnoreCase("low")) {
                            l4.add(get_duplicate(e_list.get(j)));
                        }


                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    Remainder += ((l4.size()*1.0) / e_list.size()) * B_function(l4);
                    information_gain = source_entropy - Remainder;


                }
                else if(a_list.get(i)==3){
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();
                    List<Example> l4 = new ArrayList<>();
                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(2).equalsIgnoreCase("2")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(2).equalsIgnoreCase("3")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(2).equalsIgnoreCase("4")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(2).equalsIgnoreCase("5more")) {
                            l4.add(get_duplicate(e_list.get(j)));
                        }


                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    Remainder += ((l4.size()*1.0) / e_list.size()) * B_function(l4);
                    information_gain = source_entropy - Remainder;


                }
                else if(a_list.get(i)==4){
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();

                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(3).equalsIgnoreCase("2")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(3).equalsIgnoreCase("4")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(3).equalsIgnoreCase("more")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }



                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    information_gain = source_entropy - Remainder;


                }
                else if(a_list.get(i)==5){
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();

                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(4).equalsIgnoreCase("small")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(4).equalsIgnoreCase("big")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(4).equalsIgnoreCase("med")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }



                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    information_gain = source_entropy - Remainder;

                }
                else {
                    List<Example> l1 = new ArrayList<>();
                    List<Example> l2 = new ArrayList<>();
                    List<Example> l3 = new ArrayList<>();

                    for (int j=0;j<e_list.size();j++){
                        if(e_list.get(j).attributes.get(5).equalsIgnoreCase("low")){
                            l1.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(5).equalsIgnoreCase("high")){
                            l2.add(get_duplicate(e_list.get(j)));
                        }
                        else if(e_list.get(j).attributes.get(5).equalsIgnoreCase("med")){
                            l3.add(get_duplicate(e_list.get(j)));
                        }



                    }

                    Remainder= ((l1.size() *1.0) / e_list.size()) * B_function(l1);

                    Remainder += ((l2.size() * 1.0) / e_list.size()) * B_function(l2);

                    Remainder += ((l3.size() * 1.0) / e_list.size()) * B_function(l3);

                    information_gain = source_entropy - Remainder;

                }

               if(i==0){
                   max_gain = information_gain;
               }
               else{
                  max_gain = Math.max(max_gain,information_gain);
               }

                if(max_gain == information_gain){
                    max_attribute = a_list.get(i);

                }


            }

            return max_attribute;


    }
    public static Node Decision_tree_learning(List<Example> examples,List<Integer>attributes, List<Example> p_examples){

        Node new_node = new Node();
        if(examples.size()==0){
            new_node.value= Plurality_Value(p_examples);
            return new_node;
        }
        else if(Is_all_example_have_same_classification(examples)){
            new_node.value = examples.get(0).Value_class;
            return new_node;
        }
        else if(attributes.size()==0){
            new_node.value = Plurality_Value(examples);
            return new_node;
        }
        else {
            int attribute_num = Importance(attributes,examples);
            List<Integer>new_att = new ArrayList<>(attributes);
            new_att.remove(new Integer(attribute_num));

            attribute_num--;
            new_node.number_attribute = attribute_num;

            Node childNode;

            for(int i=0;i<attributes_list.get(attribute_num).size();i++){
                List<Example> list = new ArrayList<>();

                for (int j=0;j<examples.size();j++){
                    if(examples.get(j).attributes.get(attribute_num).equalsIgnoreCase(attributes_list.get(attribute_num).get(i))){
                        list.add(get_duplicate(examples.get(j)));
                    }
                }

                childNode = Decision_tree_learning(list,new_att,examples);
                new_node.childlist.add(childNode);
            }


            return new_node;


        }

    }

    public static String get_result_from_tree(Example ex , Node root){
        if(root.value != null){

            return root.value;
        }

        int attribute_number = root.number_attribute;
        int l=-1;
        for (int i=0;i<attributes_list.get(attribute_number).size();i++){
            if(attributes_list.get(attribute_number).get(i).equalsIgnoreCase(ex.attributes.get(attribute_number))){
                l=i;
            }
        }

        Node temp = root.childlist.get(l);
        return get_result_from_tree(ex,temp);


    }

    public static void main(String[] args) throws IOException {
        rand = new Random();
        attributes_list = new ArrayList<>();
        attribute_initialize();

        String line;

        List<Example> examples = new ArrayList<>();
        double sum =0.0;
        double standard_deviance = 0.0;
        int test_size = 0;
        List<Double> result_list = new ArrayList<>();

        for (int q=0;q<20;q++){
            BufferedReader reader = new BufferedReader(new FileReader("1905038/car.data"));
            while (true){
                line = reader.readLine();
                if(line == null) break;

                String[] tokens = line.split(",");
                Example ex = new Example();
                for(int i=0;i<tokens.length - 1;i++){
                    ex.attributes.add(tokens[i]);

                }
                ex.Value_class = tokens[tokens.length-1];
                examples.add(ex);
            }

            reader.close();
            List<Integer> sample_attribute= new ArrayList<>();
            sample_attribute.add(1);
            sample_attribute.add(2);
            sample_attribute.add(3);
            sample_attribute.add(4);
            sample_attribute.add(5);
            sample_attribute.add(6);

            List<Example> test_example = new ArrayList<>();
            List<Example> training_example = new ArrayList<>();
            test_size = (examples.size() * 20)/100;


            Collections.shuffle(examples);

            while (true){
                if(test_example.size() == test_size){
                    while (examples.size() != 0){
                        Example ex = examples.get(0);
                        examples.remove(0);
                        training_example.add(ex);
                    }
                    break;
                }
                int i = rand.nextInt(examples.size());

                test_example.add(examples.get(i));
                examples.remove(i);


            }

            tree_head = Decision_tree_learning(training_example,sample_attribute,training_example);

            int accurate_answer = 0;
            for(int i=0;i<test_example.size();i++){

                String result = get_result_from_tree(test_example.get(i),tree_head);
                if(result.equalsIgnoreCase(test_example.get(i).Value_class)) {
                    accurate_answer++;
                }

            }
           double temp = (accurate_answer * 1.0) / test_size;
            result_list.add(temp);
            sum +=temp;

        }
        double average = sum / 20;
        System.out.println("Average is : "+average);
        for (int j=0;j<result_list.size();j++){
            double temp = result_list.get(j) - average;
            standard_deviance += temp * temp;
        }
        standard_deviance /= 20;
        standard_deviance = Math.sqrt(standard_deviance);
        System.out.println("Standard Deviation is : "+standard_deviance);

    }
}
