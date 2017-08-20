#include "gamewindow.h"
#include "ui_gamewindow.h"
#include <QPushButton>
#include <QGraphicsScene>
#include <QGraphicsProxyWidget>
#include <QGraphicsView>
GameWindow::GameWindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::GameWindow)
{
    ui->setupUi(this);
    board = new Board(this);
    uGPanel = new UsersGamePanel(this);
    giveUpButton = new QPushButton("Poddaję się",this);

//    QGraphicsScene *scene = new QGraphicsScene(this);
//    QGraphicsProxyWidget *w = scene->addWidget(giveUpButton);
//    w->setPos(50, 50);
//    //w->setRotation(180);
//    //QGraphicsView* graphicsView = new QGraphicsView(this);
//    ui->graphicsView->setScene(scene);

   ui->horizontalLayout->addWidget(board);
   QVBoxLayout *vLayout = new QVBoxLayout;
    ui->horizontalLayout->addItem(vLayout);
    vLayout->addWidget(uGPanel);
    vLayout->addWidget(giveUpButton);
    QSpacerItem *spacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Expanding);
    vLayout->addItem(spacer);
    connect(giveUpButton,SIGNAL(clicked()),this,SLOT(on_giveUpButton_Clicked()));
}

GameWindow::~GameWindow()
{
    delete ui;
}

void GameWindow::currentPlayer(bool crrPlr)
{
    uGPanel->currentPlayer(crrPlr);
    board->currentPlayer(crrPlr);
    update();
}

void GameWindow::on_giveUpButton_Clicked()
{
    emit(giveUp());
}
