#include<bits/stdc++.h>

using namespace std;

int calc(int n, int p, int k, vector<int> &history){
    int res = 0;
    list<int> tmp[n];

    for (int i = 0; i < p; i++) {
        tmp[--history[i]].push_back(i);
    }

    unordered_set<int> cars;
    priority_queue<pair<int, int>> ids;

    for (int i = 0; i < p; i++){
        int curr;
        curr = history[i];
        tmp[curr].pop_front();

        if (cars.find(curr) == cars.end()){
            if (cars.size() >= k) {
                cars.erase(ids.top().second);
                ids.pop();
            }
            res++;
            cars.insert(curr);
        }
        if (tmp[curr].empty()) ids.push({INT_MAX, curr});
        else ids.push({tmp[curr].front(), curr});
    }

    return res;
}

int main() {
    int n, k, p, cnt = 0;
    cin >> n >> k >> p;


    vector<int> his(p);

    for (int i = 0; i < p; i++) {
        cin >> his[i];
    }

    cout << calc(n, p, k, his) << endl;

    return 0;
}