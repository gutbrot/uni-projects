#include "map.h"

using std::cout;
using std::endl;

void Map::loadMap(const std::string& filename) {            
        std::ifstream file(filename);
        if (!file) {
            std::cerr << "File open error" << endl;
            return;
        }
        file >> rows >> cols;        
        map = new Tile * [rows];
        for (int i = 0; i < rows; ++i)
            map[i] = new Tile[cols];

        int value;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (!(file >> value)) return;
                map[i][j].type = value;
                if(value == -2){
                    map[i][j].isCurrent = true;
                    current = { i,j };
                }
                map[i][j].isGoal = (value == -3);

                if (value > 0){
                    map[i][j].monster = new Monster(value);
                    map[i][j].isMonster = true;
                }
                else
                    map[i][j].monster = nullptr;
            }
        }
    }

void Map::displayMap() const {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (map[i][j].isCurrent) cout << "P ";
                else if (map[i][j].isGoal) cout << "X ";
                else if (map[i][j].type == -1) cout << "# ";
                else if (map[i][j].type > 0) cout << "M ";
                else cout << "  ";
            }
            cout << endl;
        }
    }

void Map::move() {
        std::cout << "up 0, right 1, down 2, left 3" << std::endl;
        int dir = 0;
        std::cin >> dir;
        int dx[] = { -1, 0, 1, 0 }; 
        int dy[] = { 0, 1, 0, -1 };

        int newRow = current.first + dx[dir];
        int newCol = current.second + dy[dir];

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            // impossible?
            cout << "Nem lehet odalépni: falon kívül" << endl;
            return;
        }

        if (map[newRow][newCol].type == -1) {
            cout << "There is a wall!" << endl;
            return;
        }

        map[current.first][current.second].isCurrent = false;
        current = { newRow, newCol };
        map[newRow][newCol].isCurrent = true;

        cout << "Move successful: (" << newRow << ", " << newCol << ")" << endl;
    }

bool Map::reachedGoal() const{
        if (map[current.first][current.second].isGoal)
            return true;
        else
            return false;
    }

bool Map::encounteredMonster() const{
    if (map[current.first][current.second].isMonster)
        return true;
    else
        return false;
}

Monster& Map::getMonster(){
    return *map[current.first][current.second].monster;
}

void Map::defeatedMonster(){
    delete map[current.first][current.second].monster;
    map[current.first][current.second].isMonster = false;
    map[current.first][current.second].type = 0;
}

void Map::clearMap(){
    for (size_t i = 0; i < rows; ++i) {
        delete map[i];
    }
}

Map::~Map(){
    clearMap();
}
