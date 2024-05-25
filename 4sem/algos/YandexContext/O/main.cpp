#include <bits/stdc++.h>

using namespace std;

bool bipartiteCheck(int node, vector<list<int>>& adjacencyList, vector<int>& color, int currentColor) {
    color[node] = currentColor;
    for (int neighbor : adjacencyList[node]) {
        if (color[neighbor] == 0) {
            if (!bipartiteCheck(neighbor, adjacencyList, color, (currentColor == 2) ? 1 : 2)) return false;
        } else if (color[neighbor] == currentColor) {
            return false;
        }
    }
    return true;
}

void initializeGraph(int nodes, int edges, vector<list<int>>& adjacencyList, vector<int>& color) {
    int u, v;
    for (int i = 0; i < nodes; i++) {
        color[i] = 0;
    }

    for (int i = 0; i < edges; i++) {
        cin >> u >> v;
        u--; v--;
        adjacencyList[u].push_back(v);
        adjacencyList[v].push_back(u);
    }
}

int main() {
    int nodes, edges;
    cin >> nodes >> edges;

    vector<list<int>> adjacencyList(nodes);
    vector<int> color(nodes);

    initializeGraph(nodes, edges, adjacencyList, color);

    bool isBipartite = true;
    for (int i = 0; i < nodes; i++) {
        if (!isBipartite) break;
        if (color[i] == 0) {
            isBipartite = bipartiteCheck(i, adjacencyList, color, 1);
        }
    }

    if (isBipartite) {
        cout << "YES";
    } else {
        cout << "NO";
    }

    return 0;
}
