#include <bits/stdc++.h>

using namespace std;

struct Chr {
    char symbol;
    int value;
};

bool comp(Chr l, Chr r) {
    return l.value > r.value;
}

string getResByBinSearch(string str, vector<Chr> chrPair){
    int l = 0;
    int r = str.size() - 1;

    for (Chr pair: chrPair) {
        int posL = str.find(pair.symbol);
        if (str[l] != pair.symbol) swap(str[l], str[posL]);
        int posR = str.substr(l + 1).find(pair.symbol) + l + 1;
        if (str[r] != pair.symbol) swap(str[r], str[posR]);
        l++;
        r--;
    }

    return str;
}

int main() {
    string str;
    cin >> str;
    char symbol = 'a';
    vector<Chr> all;

    while (symbol <= 'z') {
        Chr pair;
        cin >> pair.value;
        pair.symbol = symbol;
        all.push_back(pair);
        symbol++;
    }

    sort(all.begin(), all.end(), comp);

    vector<Chr> chrPair;
    for (Chr pair: all) {
        if (count(str.begin(), str.end(), pair.symbol) >= 2) {
            chrPair.push_back(pair);
        }
    }

    cout << getResByBinSearch(str, chrPair) << endl;
    return 0;
}
