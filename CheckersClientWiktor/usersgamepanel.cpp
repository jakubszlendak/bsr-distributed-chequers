#include "usersgamepanel.h"
#include <QGridLayout>
UsersGamePanel::UsersGamePanel(QWidget *parent) : QWidget(parent)
{
    QGridLayout* grid = new QGridLayout;
    //grid->setMargin(0);
    label1=new QLabel(player1Login,this);
    label2=new QLabel(player2Login,this);
    label1->setParent(this);
    label1->setStyleSheet("QLabel { color : #004cc6; }");
    label2->setParent(this);
    userIndicator1=new UserIndicator(this);
    userIndicator2=new UserIndicator(this);
    userColor1= new UserColor(this);
    userColor2= new UserColor(this);
    grid->setAlignment(Qt::AlignCenter);
    setContentsMargins(0,0,0,0);
    this->setLayout(grid);
    grid->addWidget(userIndicator1,0,0);
    grid->addWidget(userIndicator2,1,0);
    grid->addWidget(label1,0,1);
    grid->addWidget(label2,1,1);
    grid->addWidget(userColor1,0,2);
    grid->addWidget(userColor2,1,2);
}

void UsersGamePanel::currentPlayer(bool crrPlr)
{
    if(crrPlr)
    {
        userIndicator1->nowMe=true;
        userIndicator2->nowMe=false;

    }else
    {
         userIndicator1->nowMe=false;
         userIndicator2->nowMe=true;
    }
    update();
}

void UsersGamePanel::widgetsActualization()
{
    label1->setText(player1Login);
    label2->setText(player2Login);
    userColor1->color=player1color;
    userColor2->color=player2color;
    update();
}
