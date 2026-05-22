#pragma once
#include <iostream>
#include <fstream>
#include <sstream>

#include "monster.h"

struct Tile {
    int type; 
    Monster* monster = nullptr;
    bool isCurrent = false;
    bool isGoal = false;
    bool isMonster = false;
};

class Map {
private:
    Tile** map;
    int rows = 0, cols = 0;
    std::pair<int, int> current;

public:
    void loadMap(const std::string& filename);
    void displayMap() const;
    void move();
    bool reachedGoal() const;
    bool encounteredMonster() const;
    Monster& getMonster();
    void defeatedMonster();
    void clearMap();
    ~Map();
};