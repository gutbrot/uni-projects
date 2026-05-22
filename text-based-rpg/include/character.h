#pragma once
#include <string>
#include <iostream>
class Character{
protected:
	std::string name;
	int hp;
	int hpMax;
	int str;
	int def;
	int agi;
	int arcane;
public:
	int getHp() const { return hp; }
	void setHp(int dmg) { hp -= dmg; }
};
