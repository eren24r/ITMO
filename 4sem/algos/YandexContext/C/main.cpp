#include <bits/stdc++.h>

using namespace std;

struct VariableData {
    int previousValue;
    int currentValue;
};

int main() {
    map<string, int> vals;
    vector<map<string, VariableData>> blocks;
    map<string, VariableData> curBlock;

    string line;
    while (cin >> line) {
        if (line == "{") {
            blocks.push_back(curBlock);
            curBlock.clear();
        } else if (line != "}") {
            int separator = line.find('=');
            string key = line.substr(0, separator);
            string value = line.substr(separator + 1, line.length() - 1);

            if (curBlock.count(key) == 0) {
                curBlock[key].previousValue = vals[key];
            }

            int val;
            if (isdigit(static_cast<unsigned char>(value[1])) || isdigit(static_cast<unsigned char>(value[0]))) {
                val = stoi(value);
            } else {
                val = vals[value];
                cout << val << endl;
            }

            curBlock[key].currentValue = val;

            vals[key] = val;
        } else if (line == "}") {
            for (auto &it : curBlock) {
                vals[it.first] = it.second.previousValue;
            }
            curBlock = blocks.back();
            blocks.pop_back();
        }
    }

    return 0;
}
