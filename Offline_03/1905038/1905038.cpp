#include <bits/stdc++.h>
#define inf 10000000.777
#define no_part 0
#define s_part 1
#define t_part 2

using namespace std;

vector<int> g[3001];
vector<int> cost[3001];
int benchmark[24][2];

int node = 0;
int edge = 0;
int MAX = -2147483647;
int MIN = 2147483647;
int t1, t2;
vector<int> state;
vector<int> spart;
vector<int> tpart;
vector<int> sigma_s_weight, sigma_t_weight;
int num_s_part = 0;
int num_t_part = 0;

int max_edge_v1, max_edge_v2;

void benchmark_initialize()
{

    benchmark[0][0] = 1;
    benchmark[0][1] = 12078;
    benchmark[1][0] = 2;
    benchmark[1][1] = 12084;
    benchmark[2][0] = 3;
    benchmark[2][1] = 12077;
    benchmark[3][0] = 11;
    benchmark[3][1] = 627;
    benchmark[4][0] = 12;
    benchmark[4][1] = 621;
    benchmark[5][0] = 13;
    benchmark[5][1] = 645;
    benchmark[6][0] = 14;
    benchmark[6][1] = 3187;
    benchmark[7][0] = 15;
    benchmark[7][1] = 3169;
    benchmark[8][0] = 16;
    benchmark[8][1] = 3172;
    benchmark[9][0] = 22;
    benchmark[9][1] = 14123;
    benchmark[10][0] = 23;
    benchmark[10][1] = 14129;
    benchmark[11][0] = 24;
    benchmark[11][1] = 14131;
    benchmark[12][0] = 32;
    benchmark[12][1] = 1560;
    benchmark[13][0] = 33;
    benchmark[13][1] = 1537;
    benchmark[14][0] = 34;
    benchmark[14][1] = 1541;
    benchmark[15][0] = 35;
    benchmark[15][1] = 8000;
    benchmark[16][0] = 36;
    benchmark[16][1] = 7996;
    benchmark[17][0] = 37;
    benchmark[17][1] = 8009;
    benchmark[18][0] = 43;
    benchmark[18][1] = 7027;
    benchmark[19][0] = 44;
    benchmark[19][1] = 7022;
    benchmark[20][0] = 45;
    benchmark[20][1] = 7020;
    benchmark[21][0] = 48;
    benchmark[21][1] = 6000;
    benchmark[22][0] = 49;
    benchmark[22][1] = 6000;
    benchmark[23][0] = 50;
    benchmark[23][1] = 5988;
}

void semi_greedy_maxcut()
{

    for (int i = 0; i <= node; i++)
    {
        state[i] = no_part;
    }
    spart.clear();
    tpart.clear();
    num_s_part = 0;
    num_t_part = 0;
    MAX = t1;
    MIN = t2;

    float alpha = (float)rand() / RAND_MAX;
    int mui = (int)(MIN + alpha * (MAX - MIN));

    vector<pair<int, int>> rcl_edge;

    for (int i = 1; i <= node; i++)
    {

        for (int j = 0; j < g[i].size(); j++)
        {
            if (cost[i][j] >= mui && i > j)
            {
                rcl_edge.push_back(make_pair(i, g[i][j]));
            }
        }
    }

    

   
    int random_edge = rand() % rcl_edge.size();
    
    spart.push_back(rcl_edge[random_edge].first);
    tpart.push_back(rcl_edge[random_edge].second);
    num_s_part++;
    num_t_part++;
    state[rcl_edge[random_edge].first] = s_part;
    state[rcl_edge[random_edge].second] = t_part;

   

    while (num_s_part + num_t_part != node)
    {
        MAX = -2147483647;
        MIN = 2147483647;
        for (int i = 1; i <= node; i++)
        {
            if (state[i] == no_part)
            {
                sigma_s_weight[i] = 0;
                sigma_t_weight[i] = 0;

                for (int j = 0; j < g[i].size(); j++)
                {
                    int temp = g[i][j];
                    if (state[temp] == t_part)
                    {
                        sigma_s_weight[i] += cost[i][j];
                    }
                    else if (state[temp] == s_part)
                    {
                        sigma_t_weight[i] += cost[i][j];
                    }
                }

                MAX = MAX > sigma_s_weight[i] ? MAX : sigma_s_weight[i];
                MAX = MAX > sigma_t_weight[i] ? MAX : sigma_t_weight[i];
                MIN = MIN < sigma_s_weight[i] ? MIN : sigma_s_weight[i];
                MIN = MIN < sigma_t_weight[i] ? MIN : sigma_t_weight[i];
            }
        }

        mui = (int)(MIN + alpha * (MAX - MIN));
        vector<int> rcl_node;

        for (int i = 1; i <= node; i++)
        {
            if (state[i] == no_part)
            {
                int temp = sigma_s_weight[i] > sigma_t_weight[i] ? sigma_s_weight[i] : sigma_t_weight[i];
                if (temp >= mui)
                {
                    rcl_node.push_back(i);
                }
            }
        }

       

        int random_node = rand() % rcl_node.size();
     
        random_node = rcl_node[random_node];
        if (sigma_s_weight[random_node] > sigma_t_weight[random_node])
        {
            state[random_node] = s_part;
            num_s_part++;
            spart.push_back(random_node);
        }
        else
        {
            state[random_node] = t_part;
            num_t_part++;
            tpart.push_back(random_node);
        }
    }

    
}
int cal_sig_s(int id)
{
    int sum = 0;
    for (int i = 0; i < g[id].size(); i++)
    {
        int temp = g[id][i];
        if (state[temp] == t_part)
        {
            sum += cost[id][i];
        }
    }
    return sum;
}
int cal_sig_t(int id)
{
    int sum = 0;
    for (int i = 0; i < g[id].size(); i++)
    {
        int temp = g[id][i];
        if (state[temp] == s_part)
        {
            sum += cost[id][i];
        }
    }
    return sum;
}

int local_search()
{
    int count = 0;
    bool change = true;
    while (change)
    {
        count++;
        /* code */
        change = false;
        int i = 1;
        while ((i <= node) && !change)
        {
            /* code */
            if (state[i] == s_part && (cal_sig_t(i) - cal_sig_s(i) > 0))
            {
                state[i] = t_part;
                num_s_part--;
                int l = -1;
                for (int j = 0; j < spart.size(); j++)
                {
                    if (spart[j] == i)
                    {
                        l = j;
                    }
                }
                spart.erase(spart.begin() + l);
                tpart.push_back(i);
                num_t_part++;
                change = true;
            }
            else if (state[i] == t_part && (cal_sig_s(i) - cal_sig_t(i) > 0))
            {
                state[i] = s_part;
                num_t_part--;
                int l = -1;
                for (int j = 0; j < tpart.size(); j++)
                {
                    if (tpart[j] == i)
                    {
                        l = j;
                    }
                }
                tpart.erase(tpart.begin() + l);
                spart.push_back(i);
                num_s_part++;
                change = true;
            }

            i++;
        }
    }
    return count;
}

int objective_function()
{
    int sum = 0;
    for (int i = 0; i < spart.size(); i++)
    {
        for (int j = 0; j < g[spart[i]].size(); j++)
        {
            if (state[g[spart[i]][j]] == t_part)
            {
                sum += cost[spart[i]][j];
            }
        }
    }
    return sum;
}

void simple_greedy_maxcut()
{

    for (int i = 0; i <= node; i++)
    {
        state[i] = no_part;
    }
    spart.clear();
    tpart.clear();
    num_s_part = 0;
    num_t_part = 0;

    spart.push_back(max_edge_v1);
    tpart.push_back(max_edge_v2);
    num_s_part++;
    num_t_part++;
    state[max_edge_v1] = s_part;
    state[max_edge_v2] = t_part;

    while (num_s_part + num_t_part != node)
    {
        /* code */

        MAX = -2147483647;
        int partial_max_vertex = -1;

        for (int i = 1; i <= node; i++)
        {
            if (state[i] == no_part)
            {
                sigma_s_weight[i] = 0;
                sigma_t_weight[i] = 0;

                for (int j = 0; j < g[i].size(); j++)
                {
                    int temp = g[i][j];
                    if (state[temp] == t_part)
                    {
                        sigma_s_weight[i] += cost[i][j];
                    }
                    else if (state[temp] == s_part)
                    {
                        sigma_t_weight[i] += cost[i][j];
                    }
                }

                MAX = MAX > sigma_s_weight[i] ? MAX : sigma_s_weight[i];
                if (MAX == sigma_s_weight[i])
                {
                    partial_max_vertex = i;
                }
                MAX = MAX > sigma_t_weight[i] ? MAX : sigma_t_weight[i];

                if (MAX == sigma_t_weight[i])
                {
                    partial_max_vertex = i;
                }
            }
        }

        if (sigma_s_weight[partial_max_vertex] > sigma_t_weight[partial_max_vertex])
        {

            state[partial_max_vertex] = s_part;
            num_s_part++;
            spart.push_back(partial_max_vertex);
        }
        else
        {

            state[partial_max_vertex] = t_part;
            num_t_part++;
            tpart.push_back(partial_max_vertex);
        }
    }

   
}

void simple_randomized_maxcut()
{

    spart.clear();
    tpart.clear();
    num_s_part = 0;
    num_t_part = 0;

    for (int i = 1; i <= node; i++)
    {
        int toss = rand() % 2;
        if (toss == 0)
        {
            state[i] = s_part;
            spart.push_back(i);
            num_s_part++;
        }
        else
        {
            state[i] = t_part;
            tpart.push_back(i);
            num_t_part++;
        }
    }

   
}



int main()
{

    benchmark_initialize();

   
     ofstream out("Output.csv");
    out<<"Problem"<<",,,,"<<"Constructive Algorithm"<<",,,"<<"Local Search"<<",,,,,,"<<"Grasp"<<",,"<<"Known Best Solution (upper bound)"<<endl;
    out<<"Name"<<","<<"No. of vertex"<<","<<"No. of edges"<<","<<"Weight_Range"<<","<<"Randomized-1"<<","<<"Greedy-1"<<","<<"Semi Greedy"<<","<<"random-local-search"<<",,"<<"greedy-local-search"<<",,"<<"semi-greedy-local-search"<<",,"<<"Grasp-1"<<",,,"<<endl;
    out<<",,,,,,,"<<"No. of iteration"<<","<<"Best_value"<<","<<"No. of iteration"<<","<<"Best_value"<<","<<"No. of iteration"<<","<<"Best_value"<<","<<"No. of iteration"<<","<<"Best_value"<<","<<endl;
    
    srand(time(0));
    int ls_iteration = 0;
    int grasp_iteration = 50;

    for (int w = 0; w < 24; w++)
    {
         MAX = -2147483647;
         MIN = 2147483647;
        string s = "g" + to_string(benchmark[w][0]) + ".rud";
        ifstream in(s);
        in >> node >> edge;
        for(int i=0;i<3001;i++){
            g[i].clear();
            cost[i].clear();
        }

        for (int i = 0; i < edge; i++)
        {
            int u, v;
            int w;
            in >> u >> v >> w; // 1 index based

            g[u].push_back(v);
            g[v].push_back(u);
            cost[u].push_back(w);
            cost[v].push_back(w);
            MAX = MAX > w ? MAX : w;
            if (MAX == w)
            {
                max_edge_v1 = u;
                max_edge_v2 = v;
            }
            MIN = MIN < w ? MIN : w;
        }
        t1 = MAX;
        t2 = MIN;
        state.clear();
        sigma_s_weight.clear();
        sigma_t_weight.clear();

        for (int i = 0; i <= node; i++)
        {
            state.push_back(no_part);
            sigma_s_weight.push_back(0);
            sigma_t_weight.push_back(0);
        }
        out<<"G"<<to_string(benchmark[w][0])<<","<<node<<","<<edge<<","<<MIN<<" to "<<MAX<<",";

        int sum = 0,sum_2 =0;
        ls_iteration = 0;
        for(int i=0;i<grasp_iteration;i++){
            simple_randomized_maxcut();
            sum = sum + objective_function();
            ls_iteration +=local_search();
            sum_2 +=objective_function();
        }
        int riter = (ls_iteration / grasp_iteration);
        int ravr = (sum_2 / grasp_iteration);
        out<<(sum/grasp_iteration)<<",";
        simple_greedy_maxcut();
        out<<objective_function()<<",";
        int giter = local_search();
        int gavr = objective_function();
        sum = 0;
        sum_2 = 0;
        ls_iteration = 0;
        int best_solution = -2147483647;
        for(int i=0;i<grasp_iteration;i++){
            semi_greedy_maxcut();
            sum = sum + objective_function();
            ls_iteration = ls_iteration + local_search();
            int temp = objective_function();
            sum_2 = sum_2 + temp;
            best_solution = best_solution > temp ? best_solution : temp;

        }
        out<<(sum / grasp_iteration)<<","<<riter<<","<<ravr<<","<<giter<<","<<gavr<<","<<(ls_iteration / grasp_iteration) << ","<<(sum_2 / grasp_iteration)<<","<<grasp_iteration<<","<<best_solution<<","<<benchmark[w][1]<<endl;
    
        in.close();
    }

    

    return 0;
}