#ifndef USERSGAMEPANEL_H
#define USERSGAMEPANEL_H

#include <QWidget>
#include <QLabel>
#include "usercolor.h"
#include "userindicator.h"

class UsersGamePanel : public QWidget
{
    Q_OBJECT
public:
    explicit UsersGamePanel(QWidget *parent = nullptr);
    QString player1Login,player2Login;
    bool player1color, player2color; //0 - black, 1 -white
    void currentPlayer(bool crrPlr);
    void widgetsActualization();
signals:

public slots:
private:
    UserIndicator *userIndicator1,*userIndicator2;
    UserColor *userColor1,*userColor2;
    QLabel* label1, *label2;
    int _currentPlayer=0;
};

#endif // USERSGAMEPANEL_H
