#include <iostream>
#include <stdio.h>
#include <algorithm>
#include <vector>
#include <stdlib.h>
#include <ctime>
#include <queue>

using namespace std;

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

int main()
{
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

    vector<int> pic = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int round = 1;
    srand (time(NULL));
    while(true) {

        int start;
        while(true) {
            start = rand() % 15 + 1;
            if(pic[start-1] == 0) break;
        }

        pic[start-1] = round;

        vector<int> ans = bfs(start,adj);

        for(int i = 0 ; i < 15 ; i++) {
            cout << ans[i] << " ";
        }
        cout << endl;

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

        pic[stop-1] = round;

        cout << start << " " << stop << " = " << ans[stop-1]<< endl;
        round++;
        if(round > 7) break;

        cout << endl << endl;
        for(int i = 0 ; i < 15 ; i++) {
            cout << pic[i] << " ";
        }
        cout << endl;
    }

    cout << endl << endl;
    for(int i = 0 ; i < 15 ; i++) {
        cout << pic[i] << " ";
    }
    cout << endl;

}
