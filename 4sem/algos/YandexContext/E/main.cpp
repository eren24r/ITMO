#include <bits/stdc++.h>
using namespace std;

bool check(vector<int> &arr, int k, int ans){
    int tmp = arr[0] + ans;
    int tmpK = 1;
    for(int i = 1; i < arr.size(); i++){
        if(arr[i] >= tmp){
            tmpK++;
            tmp = arr[i] + ans;
        }
    }

    if(tmpK >= k){
        return true;
    }else{
        return false;
    }
}

int searchBin(vector<int> &arr, int k){
    //int l = 0, r = arr[arr.size() - 1] - arr[0];
    int l = 0, r = arr[arr.size() - 1];

    while(l + 1 < r){
        int tmp = l + (r - l) / 2;

        if(check(arr, k, tmp)){
            l = tmp;
        }else{
            r = tmp;
        }
    }

    return l;
}

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> arr(n);

    for(int i = 0; i < n; i++){
        cin >> arr[i];
    }

    cout << searchBin(arr, k);
    return 0;
}
