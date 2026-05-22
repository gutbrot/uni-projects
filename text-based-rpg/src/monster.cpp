#include "monster.h"

Monster::Monster(const int type){
	name = "goblin";
	id = type;
	hpMax = hp = 5;
	str = def = agi = arcane = 0;
}


