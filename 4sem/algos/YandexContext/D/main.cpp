#include <bits/stdc++.h>

using namespace std;

int main() {
    int a, b, c, d, k;
    cin >> a >> b >> c >> d >> k;
    int tmpA = a;
    int i;

    for (i = 0; i < k; ++i) {
        a = a * b - c;

        if (a == tmpA) {
            break;
        }

        if (a <= 0) {
            a = 0;
            break;
        }

        if (a >= d) {
            a = d;
            break;
        }
    }

    cout << a << endl;

    return 0;
}
