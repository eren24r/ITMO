#include<bits/stdc++.h>

using namespace std;

void doo(deque<int> &frst, deque<int> &scnd){
	if (frst.size() > scnd.size()){
        scnd.push_back(frst.front());
        frst.pop_front();
    }
    string tmp;
    cin >> tmp;
    if (tmp == "-") {
        cout << scnd.front() << endl;
        scnd.pop_front();
    } else{
        if (tmp == "*") {
            int n;
            cin >> n;
            frst.push_front(n);
        } else if (tmp == "+") {
            int n;
            cin>> n;
            frst.push_back(n);
        }
    }
}

int main() {
    int n;
    cin >> n;
    deque<int> frst;
    deque<int> scnd;

    while (n != 0) {
        doo(frst, scnd);
        n--;
    }
    return 0;
}
