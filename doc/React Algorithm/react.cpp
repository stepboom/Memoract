#include <iostream>
#include <stdio.h>
#include <algorithm>
#include <vector>
#include <stdlib.h>
#include <ctime>
#include <queue>

using namespace std;


vector<int> pic = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
int adj[15][15] =  {
        0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,
        0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 ,
        0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 , 0 ,
        0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 0 ,
        0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 0 , 0 ,
        0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 ,
        0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 , 0 , 1 , 0 ,
        0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 , 1 ,
        0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 1 , 0 ,
        0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 , 1 ,
        0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 1 , 0 };

vector<int> bfs (int s,int adj[15][15])
{
    vector<int> ans;
    for (int i=0 ; i<15 ; i++) {
        ans.push_back(-1);
    }

    queue<int> q;
    q.push(s);
    ans[s-1] = 0;
    int u;
    while(!q.empty())
    {
        u = q.front();
        q.pop();
        for(int i = 0 ; i < 15 ; i++)
        {
            if(adj[u-1][i] == 1)
            {
                if(ans[i] == -1)
                {
                    ans[i] = ans[u-1] + 1;
                    q.push(i+1);
                }
            }
        }
    }
    return ans;
}

int setPos(vector<int> ans, int start) //find end 1 step...
{
    srand (time(NULL));
    int stop;
    int j = 0;
    int len = 3;
    while(true) {
        stop = rand() % 15 + 1;
        if(stop != start && ans[stop-1] >= len && pic[stop-1] == 0) break;
        j++;
        if(j > 30) {
            len--;
            j = 0;
            cout << "---------- Decrese Length ----------\n";
        }
    }
    pic[stop-1] = 1;
    return stop;
}

void find5Pos(int start) // find 4 step by 1 start...
{
    int stop;
    vector<int> ans = bfs(start,adj);
    pic[start-1] = 1;
    for(int i = 0; i < 4; i++) {
        stop = setPos(ans,start);
        start = stop;
    }
}

void resetAll() // reset pic...
{
    for(int i = 0 ; i< 15;i++) {
        pic[i] = 0;
    }
    for(int i = 0 ; i < 15 ; i++) {
        cout << pic[i] << " ";
    }
    cout << endl;
}

int main()
{
    int input;
    int start;
    srand (time(NULL));
    start = rand() % 15 + 1;
    find5Pos(start);
    while(true)
    {
        for(int i = 0 ; i < 15 ; i++) {
            cout << pic[i] << " ";
        }
        cout << endl;

        cout << "INPUT : ";
        cin >> input;
        cout << endl;
        switch(input)
        {
            case 0 : // exit...
                exit(0);
            case 1 : // click 1 correct...
                while(true)
                {
                    int i = rand() % 15;
                    if(pic[i] == 1) {
                        pic[i] = 0;
                        vector<int> ans = bfs(i+1,adj);
                        int non = setPos(ans,i+1);
                        break;
                    }
                }
                break;
            case 2 : // fault
                while(true)
                {
                    int i = rand() % 15;
                    if(pic[i] == 0) {
                        resetAll();
                        find5Pos(i+1);
                        break;
                    }
                }
            default : continue;
        }
    }
}
