#include "player.h"

Player::Player()
{

}

bool Player::operator <(const Player &l)
{
    if(free & !l.free)
    {
        return true;
    }
    else if(!free & l.free)
    {
        return false;
    }
    else
        return name<l.name;
}
