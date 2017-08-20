#ifndef PLAYER_H
#define PLAYER_H
#include <QString>

class Player
{
public:
    Player();
    QString name;
    bool free;
    bool operator <(const Player& l);
};

#endif // PLAYER_H
