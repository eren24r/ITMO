#include <bits/stdc++.h>

using namespace std;

void calc(vector<int> &numbers, int n, int k){
    int minInd = -2;
    int min = numbers[0];
    for (int i = 0; i < n - k + 1; i++) {
        if (minInd < i) {
            int lMin = numbers[i];
            for (int j = i; j < i + k; j++) {
                if (numbers[j] <= lMin) {
                    lMin = numbers[j];
                    min = numbers[j];
                    minInd = j;
                }
            }
        } else {
            if (numbers[minInd] >= numbers[i + k - 1]) {
                min = numbers[i + k - 1];
                minInd = i + k - 1;
            }
        }
        cout << min << " ";

    }
}

int main() {
    int n, k;
    cin >> n >> k;

    vector<int> numbers(n);

    for (int i = 0; i < n; i++) {
        cin >> numbers[i];
    }

    calc(numbers, n , k);

    return 0;
}