#include <bits/stdc++.h>

using namespace std;

void depthFirstSearch(int node, vector<pair<list<int>, int>>& graph, int& cycleCount) {
    graph[node].second = 1;
    for (int adjacent : graph[node].first) {
        if (graph[adjacent].second == 0) {
            depthFirstSearch(adjacent, graph, cycleCount);
        } else if (graph[adjacent].second == 1) {
            cycleCount++;
        }
    }
    graph[node].second = 2;
}

void initializeGraph(int n, vector<pair<list<int>, int>>& graph) {
    int key;
    for (int i = 0; i < n; i++) {
        cin >> key;
        graph[key - 1].first.push_back(i);
    }
}

int main() {
    int n, cycleCount = 0;
    cin >> n;

    vector<pair<list<int>, int>> graph(n);
    initializeGraph(n, graph);

    for (int i = 0; i < n; i++) {
        if (graph[i].second == 0) {
            depthFirstSearch(i, graph, cycleCount);
        }
    }

    cout << cycleCount << endl;

    return 0;
}
