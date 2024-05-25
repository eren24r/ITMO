#include<bits/stdc++.h>

using namespace std;

multimap<int, int> blcs_size;
map<int, int> blcs;

void ins(const pair<int, int>& pair){
    blcs.insert(pair);
    blcs_size.insert({pair.second, pair.first});
}

int main() {
    int n, m, k, ind, size;
    cin >> n >> m;
    pair<int, int> his[m];
    ins({1, n});

    for (int i = 0; i < m; his[i] = {k, ind}, i++){
        cin >> k;
        if (k > 0) {
            auto it = blcs_size.lower_bound(k);
            if (it == blcs_size.end()) ind = -1;
            else {
                ind = it->second;
                size = it->first - k;
                blcs.erase(it->second);
                blcs_size.erase(it);
                if (size > 0) ins({ind + k, size});
            }
            cout << ind << endl;
        } else {
            int index_x = his[abs(k) - 1].second;
            int size_x = his[abs(k) - 1].first;
            if (index_x == -1) continue;

            auto it_r = blcs.lower_bound(index_x);
            auto it_l = (it_r != blcs.begin()) ? prev(it_r) : blcs.end();

            if (it_r != blcs.end() && it_r->first == index_x + size_x){
                if (it_l != blcs.end() && it_l->first + it_l->second == index_x) {
                    ind = it_l->first;
                    size = it_l->second + it_r->second;
                    auto it_d1 = blcs_size.find(it_l->second);
                    while (it_d1->second != it_l->first) it_d1++;
                    blcs_size.erase(it_d1);
                    blcs.erase(it_l);

                    auto it_d2 = blcs_size.find(it_r->second);
                    while (it_d2->second != it_r->first) it_d2++;
                    blcs_size.erase(it_d2);
                    blcs.erase(it_r);

                    ins({ind, size + size_x});
                } else {
                    size = it_r->second;

                    auto it_d = blcs_size.find(it_r->second);
                    while (it_d->second != it_r->first) it_d++;
                    blcs_size.erase(it_d);
                    blcs.erase(it_r);

                    ins({index_x, size + size_x});
                }
            } else {
                if (it_l != blcs.end() && it_l->first + it_l->second == index_x) {
                    ind = it_l->first;
                    size = it_l->second;

                    auto it_d = blcs_size.find(it_l->second);
                    while (it_d->second != it_l->first) it_d++;
                    blcs_size.erase(it_d);
                    blcs.erase(it_l);

                    ins({ind, size + size_x});
                } else ins({index_x, size_x});
            }

            ind = 0;
        }
    }
    return 0;
}