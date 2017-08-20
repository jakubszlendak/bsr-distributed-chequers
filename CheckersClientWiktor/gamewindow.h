#ifndef GAMEWINDOW_H
#define GAMEWINDOW_H

#include <QDialog>
#include "board.h"
#include "usersgamepanel.h"
namespace Ui {
class GameWindow;
}

class GameWindow : public QDialog
{
    Q_OBJECT

public:
    explicit GameWindow(QWidget *parent = 0);
    ~GameWindow();
     Board* board;
     UsersGamePanel* uGPanel;
     QPushButton* giveUpButton;
     void currentPlayer(bool crrPlr);
private slots:
     void on_giveUpButton_Clicked();
signals:
     void giveUp();
private:
    Ui::GameWindow *ui;


};

#endif // GAMEWINDOW_H
