#pragma once
#include "map.h"
#include "character.h"
#include "player.h"

enum State {
	MENU,
	CHARACTER_CREATION,
	MAP_INITIALIZATION,
	EXPLORATION,
	COMBAT,
	GAME_OVER
};

class Game {
	State state;
	Player* player;
	Map* map;
public:
	Game();
	void nextState(State);
	void run();
	void mainMenu();
	void createCharacter();
	void initializeMap();
	void exploration();
	void combat();
	void clearGame();
	~Game();
};