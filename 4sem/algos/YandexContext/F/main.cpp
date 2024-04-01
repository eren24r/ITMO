#include <bits/stdc++.h>

using namespace std;

void sort(string arr[100], int size){
    for (int i = 0; i < size; i++) {
        int j = i;
        string tmp = arr[i];
        while (j >= 0 && tmp + arr[j] <= arr[j] + tmp) {
            arr[j] = arr[j - 1];
            j--;
        }
        arr[j + 1] = tmp;
    }
}

void printArrayStrsAsNumber(string arr[100], int size){
    while (size >= 0) {
        cout << arr[size];
        size--;
    }
}

int main() {
    string str;
    string arrStrs[100];
    int size = 0;

    while (cin >> str) {
        if (str.empty() || str == "end") {
            break;
        }
        arrStrs[size] = str;
        size++;
    }

    sort(arrStrs, size);
    printArrayStrsAsNumber(arrStrs, size);

    return 0;
}
