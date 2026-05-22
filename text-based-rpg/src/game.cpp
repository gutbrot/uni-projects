#include "game.h"

Game::Game(){
    state = MENU;
    map = new Map;
    player = new Player();

}

void Game::nextState(State s){
    state = s;
}

void Game::run(){
    while (state != GAME_OVER) {
        switch (state) {
        case MENU:
            std::cout << "Current state: Menu" << std::endl;
            mainMenu();
            break;
        case CHARACTER_CREATION:
            std::cout << "Current state: cc" << std::endl;
            createCharacter();
            break;
        case MAP_INITIALIZATION:
            std::cout << "Current state: mi" << std::endl;
            initializeMap();
            break;
        case EXPLORATION:
            exploration();
            break;
        case COMBAT:
            combat();
            break;
        default:
            break;
        }
    }
}

void Game::mainMenu(){
    std::cout << "play 0, quit 1" << std::endl;
    int choice;
    std::cin >> choice;
    if (!choice)
        nextState(CHARACTER_CREATION);
    else
        nextState(GAME_OVER);

}

void Game::createCharacter(){
    std::cout << "knight 0, mage 1" << std::endl;
    int choice;
    std::cin >> choice;
    player->selectClass(choice);
    player->showStats();
    nextState(MAP_INITIALIZATION);
}

void Game::initializeMap(){
    // error handling: map = nullptr
    map->loadMap("map.txt");
    map->displayMap();
    nextState(EXPLORATION);
}

void Game::exploration(){
    // what if current && isGoal==true
    while(!map->encounteredMonster()) {
        map->move();
        map->displayMap();
    }
    nextState(COMBAT);
}

void Game::combat(){
    Monster monster = map->getMonster();
    size_t round = 1;
    while (true){
        if (player->getHp() <= 0) {
            nextState(GAME_OVER);
            break;
        }
        if (monster.getHp() <= 0) {
            nextState(EXPLORATION);
            map->defeatedMonster();
            break;
        }

        std::cout << "Youve encountered a(n) "; monster.showName();
        std::cout << "Round: " << round << std::endl;
        round++;

        if (round >= 3)
            monster.setHp(10);
    }
}

void Game::clearGame(){
    map->clearMap();
    delete player;
}

Game::~Game()
{
    clearGame();
}
