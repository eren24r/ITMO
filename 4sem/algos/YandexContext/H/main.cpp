#include <bits/stdc++.h>
using namespace std;

int calc(vector<int> a, int k){
    int ans = 0;
    for(int i = 0; i < a.size(); i++){
        if((i + 1) % k == 0){
            ans = ans + a[i];
        }
    }

    return ans;
}

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> a(n);

    int tmpSum = 0;

    for(int i = 0; i < n; i++){
        cin >> a[i];
        tmpSum = tmpSum + a[i];
    }

    sort(a.begin(), a.end());
    reverse(a.begin(), a.end());

    cout << tmpSum - calc(a, k);
}
