#include <bits/stdc++.h>

using namespace std;

void depthFirstSearch(int node, const vector<uint32_t>& graph, vector<int>& colors, int N, int& componentCount, uint32_t limit, int type) {
    colors[node] += 1;
    for (int i = 0; i < N; i++) {
        uint32_t id = type ? (i * N + node) : (node * N + i);
        if (i != node && colors[i] == type && graph[id] <= limit) {
            depthFirstSearch(i, graph, colors, N, componentCount, limit, type);
        }
    }
    componentCount++;
}

void initializeGraph(int N, vector<uint32_t>& graph, uint32_t& minWeight, uint32_t& maxWeight) {
    minWeight = numeric_limits<uint32_t>::max();
    maxWeight = 0;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            uint32_t weight;
            cin >> weight;
            graph[i * N + j] = weight;
            if (i != j) {
                if (weight < minWeight) minWeight = weight;
                if (weight > maxWeight) maxWeight = weight;
            }
        }
    }
}

bool checkComponents(int N, const vector<uint32_t>& graph, vector<int>& colors, uint32_t limit) {
    fill(colors.begin(), colors.end(), 0);
    int componentCount = 0;
    if (N > 1) {
        depthFirstSearch(0, graph, colors, N, componentCount, limit, 0);
        depthFirstSearch(0, graph, colors, N, componentCount, limit, 1);
    }
    return componentCount == 2 * N;
}

int findMinimumLimit(int N, const vector<uint32_t>& graph, uint32_t minWeight, uint32_t maxWeight) {
    vector<int> colors(N, 0);
    uint32_t left = minWeight, right = maxWeight;
    while (left < right) {
        uint32_t middle = (left + right) / 2;
        if (checkComponents(N, graph, colors, middle)) {
            right = middle;
        } else {
            left = middle + 1;
        }
    }
    return (checkComponents(N, graph, colors, left) ? left : 0);
}

int main() {
    int N;
    cin >> N;

    vector<uint32_t> graph(N * N);
    uint32_t minWeight, maxWeight;
    initializeGraph(N, graph, minWeight, maxWeight);

    int result = findMinimumLimit(N, graph, minWeight, maxWeight);
    cout << result;

    return 0;
}
