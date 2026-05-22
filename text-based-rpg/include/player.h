#pragma once
#include "Character.h"
class Player : public Character
{
private:
    int xpNext;
    size_t level;
    int mana;
    int manaMax;
public:
    Player();
    void selectClass(int);
    int attackBasic() { return str; }
    void showStats() const;
    // void select attack
    void setName();

};

