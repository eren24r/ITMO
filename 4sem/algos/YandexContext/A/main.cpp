#include <bits/stdc++.h>

using namespace std;

int main() {
    int n;
    cin >> n;
    int cnt = 0;
    int firstInd = 0;
    int mx = 0;
    int ansInd = 0;
    int tmpFirst;
    int tmp;

    cin >> tmpFirst;

    for(int i = 1; i < n; i++){
        cin >> tmp;

        if (tmpFirst != tmp) {
            cnt = 0;
        }

        if(tmpFirst == tmp){
            cnt = cnt + 1;

            if(cnt == 2) {
                if(i - firstInd > mx) {
                    ansInd = firstInd;
                    mx = i - firstInd;
                }
                firstInd = i - 1;
                cnt = 1;
            }
        }

        tmpFirst = tmp;
    }

    if(n - firstInd > mx){
        ansInd = firstInd;
        mx = n - firstInd;
    }

    cout << ansInd + 1 << " " << ansInd + mx;

    return 0;
}
