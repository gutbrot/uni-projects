#pragma once
#include "character.h"

class Monster : public Character{
    int id;
public:
    Monster(const int type);
    void showName() const { std::cout << name; };
};