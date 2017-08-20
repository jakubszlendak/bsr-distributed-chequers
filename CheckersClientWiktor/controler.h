#ifndef CONTROLER_H
#define CONTROLER_H
#include "loginwindow.h"
#include "roomwindow.h"
#include "communication.h"
#include "state.h"
#include "player.h"
#include "gamewindow.h"
#include <QObject>
#include <QDebug>
#include <list>
class Controler : public QObject
{
    Q_OBJECT
public:
    explicit Controler(QObject *parent = 0);
    ~Controler();
    void show();
    bool getMySide();

public slots:
    void login(QString login, QString password);
    void regist (QString login, QString password);
    void commandReceived(fullCommand fllCmmnd);
    void refreshButtonSlot();
    void logout();
    void invite(QString user);
    void squareClickedWithMouse(Square *square);
    void giveUp();

private:
    QString user;
    QString opponent;
    bool currentPlayer; //1 - me, 0 - other player
    LoginWindow loginWindow;
    RoomWindow roomWindow;
    GameWindow gameWindow;
    Communication* comm;
    state crrState = NLG;
    state prvState = NLG;

    void changeState(state nextState);
    void LGN_WFRcommandAnalyser(fullCommand fllCmmnd);
    void NLGcommandAnalyser(fullCommand fllCmmnd);
    void CRA_WFRcommandAnalyser(fullCommand fllCmmnd);
    void ROOMcommandAnalyser(fullCommand fllCmmnd);
    void LSP_WFRcommandAnalyser(fullCommand fllCmmnd);
    void RFP_WFRcommandAnalyser(fullCommand fllCmmnd);
    void WAITRcommandAnalyser(fullCommand fllCmmnd);
    void WAITGcommandAnalyser(fullCommand fllCmmnd);
    void GAMEcommandAnalyser(fullCommand fllCmmnd);
    void MOV_WFRcommandAnalyser(fullCommand fllCmmnd);
    void unexpectedCommand(fullCommand fllCmmnd);
    void unexpectedCommand();
    void protocolError(fullCommand fllCmmnd);
    void openRoomWindow();
    void refreshPlayersList();
    void clearSelection();
    bool mySide; //1 - Biały, 0 - Czarny
    bool isFirstClicked;
    int xClicked,yClicked;

     std::list<Player> playersList;
    PieceState piecesTable[64];
    //QNetworkSession *networkSession;

    //QDataStream in;
};

#endif // CONTROLER_H
