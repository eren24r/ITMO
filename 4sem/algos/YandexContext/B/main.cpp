#include<bits/stdc++.h>

using namespace std;

int main() {
    string input;
    cin >> input;

    int n = input.length() / 2 + 1;

    stack<char> tmpChar;
    stack<int> animalStack;
    stack<int> trap;
    int ind[n];

    int indCntAnimals = 0;
    int indCntTraps = 0;

    for (int i = 0; i < input.length(); i++) {
        char currentChar = input[i];

        if (islower(currentChar)) {
            indCntAnimals++;
            animalStack.push(indCntAnimals);
        }else{
            indCntTraps++;
            trap.push(indCntTraps);
        }

        if(!tmpChar.empty()) {
            char tmpInd = tmpChar.top();

            if ((tolower(currentChar) == tolower(tmpInd)) && (currentChar != tmpInd)) {
                ind[trap.top()] = animalStack.top();
                trap.pop();
                animalStack.pop();
                tmpChar.pop();
            } else {
                tmpChar.push(currentChar);
            }
        }else{
            tmpChar.push(currentChar);
        }
    }

    if (tmpChar.empty()) {
        cout << "Possible\n";
        for (int i = 1; i < n; i++) {
            cout << ind[i] << " ";
        }
    } else {
        cout << "Impossible\n";
    }

    return 0;
}
