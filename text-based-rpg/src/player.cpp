#include "Player.h"
using std::cout;
using std::endl;

Player::Player(){
	level = 0;
	xpNext = 0;
	hpMax = hp = 0;
	str = def = agi = arcane = 0;
	manaMax = mana = 0;
}

void Player::selectClass(int value){
	// expand and balance classes
	level = 1;
	xpNext = 100;
	if (value == 0) {
		hpMax = hp = 10;
		str = def = agi = arcane = 5;
		manaMax = mana = 5;
	}
	else {
		hpMax = hp = 5;
		str = def = agi = arcane = 10;
		manaMax = mana = 10;
	}
}

void Player::setName()
{
	std::cout << "Type a name: ";
	std::cin >> name;
}


void Player::showStats() const{
	cout << "Name: " << name << endl;
	cout << "Hp: " << hp << "/" << hpMax << " Mana:" << mana << "/" << manaMax << endl;
	cout << "Str: " << str << " Def: " << def << " Agi: " << agi << " Arcane: " << arcane << endl;
}
