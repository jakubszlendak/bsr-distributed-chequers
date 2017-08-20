#include "controler.h"
#include <QApplication>
#include <QMessageBox>
#include <QDebug>
Controler::Controler(QObject *parent):

    QObject(parent)
    //, networkSession(Q_NULLPTR)
{
 //   in.setDevice(socket);
   // in.setVersion(QDataStream::Qt_4_0);
    loginWindow.show();
    comm = new Communication();
    connect(&loginWindow, SIGNAL(login(QString,QString)), this, SLOT(login(QString,QString)));
    connect(&loginWindow, SIGNAL(regist(QString,QString)), this, SLOT(regist(QString,QString)));
    connect(comm, SIGNAL(commandReceived(fullCommand)),this,SLOT(commandReceived(fullCommand)));
    connect(&roomWindow,SIGNAL(refreshButtonSignal()),this,SLOT(refreshButtonSlot()));
    connect(&roomWindow,SIGNAL(logout()),this,SLOT(logout()));
    connect(&roomWindow,SIGNAL(invite(QString)),this,SLOT(invite(QString)));
    connect((gameWindow.board),SIGNAL(squareClickedWithMouseSignal(Square*)),this,SLOT(squareClickedWithMouse(Square*)));
    connect(&gameWindow,SIGNAL(giveUp()),this,SLOT(giveUp()));
}

Controler::~Controler()
{
    delete comm;
}

bool Controler::getMySide()
{
    return mySide;
}





void Controler::login(QString login, QString password)
{
    comm->login(login,password);
    changeState(LGN_WFR);
    user=login;
}

void Controler::regist(QString login, QString password)
{
    comm->regist(login,password);
    changeState(CRA_WFR);
}

void Controler::commandReceived(fullCommand fllCmmnd)
{
    switch (crrState) {
    case LGN_WFR:
        LGN_WFRcommandAnalyser(fllCmmnd);
        break;
    case CRA_WFR:
        CRA_WFRcommandAnalyser(fllCmmnd);
        break;
    case NLG:
        NLGcommandAnalyser(fllCmmnd);
        break;
    case ROOM   :
        ROOMcommandAnalyser(fllCmmnd);
        break;
    case LSP_WFR:
        LSP_WFRcommandAnalyser(fllCmmnd);
        break;
    case RFP_WFR:
        RFP_WFRcommandAnalyser(fllCmmnd);
        break;
    case WAITR  :
        WAITRcommandAnalyser(fllCmmnd);
        break;
    case WAITG  :
        WAITGcommandAnalyser(fllCmmnd);
        break;
    case GAME   :
        GAMEcommandAnalyser(fllCmmnd);
        break;
    case MOV_WFR:
        MOV_WFRcommandAnalyser(fllCmmnd);
        break;
    default:
        break;
    }
    qDebug()<<"Got command:";
    qDebug()<<fllCmmnd.com;
    qDebug()<<fllCmmnd.parameters;
}

void Controler::refreshButtonSlot()
{
    refreshPlayersList();
}

void Controler::logout()
{
    comm->sendCommand(LGO);
    changeState(NLG);
    roomWindow.close();
    loginWindow.show();
}

void Controler::invite(QString user)
{
    comm->sendCommand(RFP,user);
    changeState(RFP_WFR);
    opponent=user;
}

void Controler::squareClickedWithMouse(Square *square)
{
    if(mySide)
    {
        qDebug()<<"Clicked square: x= "<<square->x<<" y= "<<square->y;
        if(!isFirstClicked)
        {
    //        xClicked= square->x;
    //        yClicked=square->y;
            xClicked=square->x;
            yClicked=square->y;
            isFirstClicked=true;
            gameWindow.board->isFirstClicked=isFirstClicked;
        }
        else
        {

            if(xClicked==square->x & yClicked==square->y)
            {
                isFirstClicked=false;
                gameWindow.board->isFirstClicked=isFirstClicked;
            }
            else
            {
                isFirstClicked=false;
                gameWindow.board->isFirstClicked=isFirstClicked;
                comm->sendCommand(MOV,QString::number(xClicked),QString::number(yClicked),QString::number(square->x),QString::number(square->y));
                changeState(MOV_WFR);
            }
        }
    }else
    {

        qDebug()<<"Clicked square: x= "<<7-square->x<<" y= "<<7-square->y;
        if(!isFirstClicked)
        {
    //        xClicked= square->x;
    //        yClicked=square->y;
            xClicked=7- square->x;
            yClicked=7-square->y;
            isFirstClicked=true;
            gameWindow.board->isFirstClicked=isFirstClicked;
        }
        else
        {

            if(xClicked==7-square->x & yClicked==7-square->y)
            {
                isFirstClicked=false;
                gameWindow.board->isFirstClicked=isFirstClicked;
            }
            else
            {
                isFirstClicked=false;
                gameWindow.board->isFirstClicked=isFirstClicked;
                comm->sendCommand(MOV,QString::number(xClicked),QString::number(yClicked),QString::number(7-square->x),QString::number(7-square->y));
                changeState(MOV_WFR);
            }
        }

    }

}

void Controler::giveUp()
{
    comm->sendCommand(GVU);
}

void Controler::changeState(state nextState)
{
    switch (nextState){
        case NLG    :
            qDebug()<<"state: NLG";
            break;
        case LGN_WFR:
            qDebug()<<"state: LGN_WFR";
            break;
        case CRA_WFR:
            qDebug()<<"state: CRA_WFR";
            break;
        case ROOM   :
            qDebug()<<"state: ROOM";
            break;
        case LSP_WFR:
            qDebug()<<"state: LSP_WFR";
            break;
        case WAITR  :
            qDebug()<<"state: WAITR";
            break;
        case WAITG  :
            qDebug()<<"state: WAITG";
            roomWindow.waitingForPlayerResponse(false);
            roomWindow.close();
            gameWindow.show();

            break;
        case GAME   :
            qDebug()<<"state: GAME";

            break;
    case MOV_WFR   :
        qDebug()<<"state: MOV_WFR";

        break;
    }
    prvState=crrState;
    crrState=nextState;

}

void Controler::LGN_WFRcommandAnalyser(fullCommand fllCmmnd)
{
    switch(fllCmmnd.com)
    {
    case LGN:


        if(fllCmmnd.par1()=="1")
        {
            changeState(ROOM);
            openRoomWindow();
            loginWindow.close();
            loginWindow.clearFields();
        }
        else if(fllCmmnd.par1()=="0")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Niepoprawne nazwa użytkownika lub hasło");
            msgBox.exec();
            changeState(NLG);
        }
        break;
    case ERR:
        if(fllCmmnd.par1()=="not enough parameters")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Przed zalogowaniem wypełnij pola 'login' i 'hasło'");
            msgBox.exec();
            changeState(NLG);

        }else
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Błąd!");
            msgBox.setInformativeText("Niewłaściwa liczba parametrów");
            msgBox.exec();
            changeState(NLG);
        }
        break;
    default:
        unexpectedCommand();
        changeState(NLG);
        break;
    }
}

void Controler::NLGcommandAnalyser(fullCommand fllCmmnd)
{

}

void Controler::CRA_WFRcommandAnalyser(fullCommand fllCmmnd)
{
    switch (fllCmmnd.com)
    {
    case CRA:
        if(fllCmmnd.par1()=="1")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Zarejestrowano użytkownika.");
            msgBox.setInformativeText("Zaloguj się, aby zagrać.");
            msgBox.exec();
            changeState(NLG);

        }
        else if(fllCmmnd.par1()=="0")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Rejestracja nie powiodła się.");
            msgBox.setInformativeText("Nazwa użytkownika jest zajęta");
            msgBox.exec();
            changeState(NLG);
        }
        else{

        }
        break;
    case ERR:
        if(fllCmmnd.par1()=="not enough parameters")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Przed zarejestrowaniem wypełnij pola 'login' i 'hasło'");
            msgBox.exec();
            changeState(NLG);

        }else
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Błąd!");
            msgBox.setInformativeText("Niewłaściwa liczba parametrów");
            msgBox.exec();
            changeState(NLG);
        }
        break;
    default:
        unexpectedCommand();
        changeState(NLG);
        break;
    }


}

void Controler::ROOMcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = ROOM;
    switch (fllCmmnd.com)
    {
    case RP1:
        {
            QString question = "Otrzymałeś zaproszenie do gry od użytkownika '";
            question += fllCmmnd.par1();
            question += "'. Czy chcesz z nim zagrać?";
            QMessageBox::StandardButton reply;
            reply = QMessageBox::question(&roomWindow, "Zaproszenie do gry", question ,QMessageBox::No|QMessageBox::Yes);

            if (reply == QMessageBox::Yes) {
                comm->sendCommand(RP1,"1");
                changeState(WAITG);
                opponent=fllCmmnd.par1();

            } else {
                comm->sendCommand(RP1,"0");
            }
        }
        break;

    case ERR:
    {
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);
        changeState(defaultState);
    }
        break;
    case ERS:
        break;
    default:
    {
        unexpectedCommand();
        changeState(defaultState);
    }
        break;
    }
}

void Controler::LSP_WFRcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = ROOM;
    switch (fllCmmnd.com)
    {
    case LSP:
    {
        int parNumber = fllCmmnd.parameters.length();
        Player tempPlayer;
        playersList.clear();
        for(int i =0;i<parNumber;i++)
        {

            if(i%2==0){

                tempPlayer.name=fllCmmnd.parameters[i];

            }
            else
            {
                if(fllCmmnd.parameters[i]=="A")
                {
                    tempPlayer.free=true;
                }
                else if(fllCmmnd.parameters[i]=="B")
                {
                    tempPlayer.free=false;
                }
                else{
//                    QMessageBox msgBox;
//                    msgBox.setWindowTitle("Warcaby");
//                    msgBox.setText("Błąd protokołu!");
//                    msgBox.exec();
                    protocolError(fllCmmnd);
                    changeState(defaultState);
                }
                playersList.push_back(tempPlayer);
            }
        }
        for (std::list<Player>::iterator a = playersList.begin(), end =playersList.end() ; a !=end; ++a )
        {

          //  qDebug()<<end->name;// <<" "<<end;
            if(a->name==user)
            {
                a=playersList.erase(a);
            }
        }
        playersList.sort();

        roomWindow.refreshPlayersList(playersList);
        changeState(defaultState);
    }
    break;
    case ERR:
    {
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);
        changeState(defaultState);
    }
        break;
    case ERS:
        break;
    default:
    {
        unexpectedCommand();
        changeState(defaultState);
    }
        break;
    }
}

void Controler::RFP_WFRcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = ROOM;
    switch (fllCmmnd.com)
    {
    case RFP:
        {
            if(fllCmmnd.par1()=="1")
            {
                changeState(WAITR);
                roomWindow.waitingForPlayerResponse(true,opponent);

            }
            else if(fllCmmnd.par1()=="0")
            {
                QMessageBox msgBox;
                msgBox.setWindowTitle("Warcaby");
                msgBox.setText("Nie można zaprosić użytkownika do gry.");
                msgBox.exec();
                changeState(defaultState);
                refreshPlayersList();
            }
            else{
//                QMessageBox msgBox;
//                msgBox.setWindowTitle("Warcaby");
//                msgBox.setText("Błąd protokołu!");
//                msgBox.setInformativeText("Otrzymano nieprawidłowy parametr");
//                msgBox.exec();
                protocolError(fllCmmnd);
                changeState(defaultState);
            }
        }
        break;

    case ERR:
    {
        QMessageBox msgBox;
        msgBox.setWindowTitle("Warcaby");
        msgBox.setText("Nie można zaprosić użytkownika do gry.");
        msgBox.exec();
        changeState(defaultState);
        refreshPlayersList();
    }
        break;
    case ERS:
        break;
    default:
    {
        unexpectedCommand();
        changeState(defaultState);
    }
        break;
    }
}

void Controler::WAITRcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = WAITR;
    switch (fllCmmnd.com)
    {
    case RP2:
        {
            if(fllCmmnd.par1()=="1")
            {
                changeState(WAITG);


            }
            else if(fllCmmnd.par1()=="0")
            {

                QMessageBox msgBox;
                msgBox.setWindowTitle("Warcaby");
                msgBox.setText("Użytkownik "+user+" odrzucił zaproszenie do gry");
                msgBox.exec();
                changeState(ROOM);
                roomWindow.waitingForPlayerResponse(false);
                //refreshPlayersList();
            }
            else{
//                QMessageBox msgBox;
//                msgBox.setWindowTitle("Warcaby");
//                msgBox.setText("Błąd protokołu!");
//                msgBox.setInformativeText("Otrzymano nieprawidłowy parametr");
//                msgBox.exec();
                protocolError(fllCmmnd);
                changeState(defaultState);
            }
        }
        break;


    case ERR:
    {
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);
        changeState(defaultState);
    }
        break;
    case ERS:
        break;
    default:
    {
        unexpectedCommand();
        changeState(defaultState);
    }
        break;
    }
}

void Controler::WAITGcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = WAITG;
    switch (fllCmmnd.com)
    {
    case INI:
        {
            if(fllCmmnd.par1()=="B")
            {
                mySide=true;
                changeState(GAME);
                gameWindow.uGPanel->player1color=mySide;
                gameWindow.uGPanel->player2color= !mySide;
                gameWindow.uGPanel->player1Login=user;
                gameWindow.uGPanel->player2Login=opponent;
                gameWindow.board->mySide=mySide;
                gameWindow.uGPanel->widgetsActualization();
                gameWindow.uGPanel->currentPlayer(0);

            }
            else if(fllCmmnd.par1()=="C")
            {

               mySide=false;
               changeState(GAME);
               gameWindow.uGPanel->player1color=mySide;
               gameWindow.uGPanel->player2color= !mySide;
               gameWindow.uGPanel->player1Login=user;
               gameWindow.uGPanel->player2Login=opponent;
               gameWindow.uGPanel->widgetsActualization();
               gameWindow.uGPanel->currentPlayer(0);
            }
            else{
//                QMessageBox msgBox;
//                msgBox.setWindowTitle("Warcaby");
//                msgBox.setText("Błąd protokołu!");
//                msgBox.setInformativeText("Otrzymano nieprawidłowy parametr");
//                msgBox.exec();
                protocolError(fllCmmnd);
                changeState(defaultState);
            }
        }
        break;


    case ERR:
    {
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);
        changeState(defaultState);
    }
        break;
    case ERS:
        break;
    default:
    {
        unexpectedCommand();
        changeState(defaultState);
    }
        break;
    }
}

void Controler::GAMEcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = GAME;
    switch (fllCmmnd.com)
    {
    case CHB:
        {
            if(fllCmmnd.par1().length()==64)
            {
                QString gotBoard=fllCmmnd.par1();
                for(int i = 0;i<64;i++)
                {
                    if(gotBoard[i]== QLatin1Char('B'))
                    {
                        piecesTable[i]=white;
                    }else
                        if(gotBoard[i]== QLatin1Char('C'))
                        {
                            piecesTable[i]=black;
                        }else
                            if(gotBoard[i]== QLatin1Char('D'))
                            {
                                piecesTable[i]=white_King;
                            }else
                                if(gotBoard[i]== QLatin1Char('E'))
                                {
                                    piecesTable[i]=black_King;
                                }else
                                    if(gotBoard[i]== QLatin1Char('O'))
                                    {
                                        piecesTable[i]=not_exists;
                                    }
                                    else
                                    {
                                        QMessageBox msgBox;
                                        msgBox.setWindowTitle("Warcaby");
                                        msgBox.setText("Błąd protokołu!");
                                        msgBox.exec();
                                        clearSelection();
                                    }

                }
                if(mySide)
                {
                    gameWindow.board->getPiecesTable(piecesTable);
                }
                else
                {
                    PieceState piecesTableRotated[64];
                    for(int i = 0;i<64;i++)
                    {
                        piecesTableRotated[i]=piecesTable[63-i];
                    }
                    gameWindow.board->getPiecesTable(piecesTableRotated);

                }
            }else
            {
                QMessageBox msgBox;
                msgBox.setWindowTitle("Warcaby");
                msgBox.setText("Błąd protokołu!");
                msgBox.exec();
                clearSelection();
            }

        }
        break;

    case YMV:
    {
        gameWindow.currentPlayer(1);
         clearSelection();
    }
        break;
    case  EOG:
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            if(fllCmmnd.par1()==user)
            {
                msgBox.setText("Gratulacje, wygrałeś!");
            }
            else if(fllCmmnd.par1()==opponent)
            {
                msgBox.setText("Koniec gry, przegrałeś.");
            }
            else{
                msgBox.setText("Koniec gry");
            }
            msgBox.exec();
            gameWindow.hide();
            changeState(ROOM);
            openRoomWindow();

        }
    break;
    case ERR:
    {
        if(fllCmmnd.par1()=="wait for your turn!")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Ruch niemożliwy, poczekaj na swoją turę.");
            msgBox.exec();
            //clearSelection();
        }
        else{
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);


        //changeState(defaultState);
        }
        clearSelection();
    }
        break;
    case ERS:
        clearSelection();
        break;
    default:
    {
        unexpectedCommand(fllCmmnd);
        changeState(defaultState);
        clearSelection();
    }
        break;
    }

}

void Controler::MOV_WFRcommandAnalyser(fullCommand fllCmmnd)
{
    state defaultState = GAME;
    switch (fllCmmnd.com)
    {
    case MOV:
        {
            if(fllCmmnd.par1()=="1")
            {
                gameWindow.currentPlayer(0);
                changeState(GAME);
                 //gameWindow.board->clearSelection();
            }else
            {
                QMessageBox msgBox;
                msgBox.setWindowTitle("Warcaby");
                msgBox.setText("Ruch niemożliwy do wykonania");
                msgBox.exec();
                clearSelection();
                changeState(GAME);
            }

        }
        break;

    case ERR:
    {
        if(fllCmmnd.par1()=="wait for your turn!")
        {
            QMessageBox msgBox;
            msgBox.setWindowTitle("Warcaby");
            msgBox.setText("Ruch niemożliwy, poczekaj na swoją turę.");
            msgBox.exec();
            changeState(defaultState);
        }else
        {
//        QMessageBox msgBox;
//        msgBox.setWindowTitle("Warcaby");
//        msgBox.setText("Błąd protokołu!");
//        msgBox.exec();
        protocolError(fllCmmnd);
        changeState(defaultState);
        //clearSelection();
        }
        clearSelection();
    }
        break;
    case ERS:
         clearSelection();
         changeState(defaultState);
        break;
    default:
    {
        unexpectedCommand(fllCmmnd);
        changeState(defaultState);
         clearSelection();
    }
        break;
    }
}

void Controler::unexpectedCommand(fullCommand fllCmmnd)
{
    QMessageBox msgBox;
    msgBox.setText("Otrzymano od serwera nieoczekiwaną komendę");
    msgBox.exec();
    qDebug()<<"unexpected command:";
    qDebug()<<fllCmmnd.com<<fllCmmnd.parameters;

}
void Controler::unexpectedCommand()
{
    QMessageBox msgBox;
    msgBox.setText("Otrzymano od serwera nieoczekiwaną komendę");
    msgBox.exec();

}

void Controler::protocolError(fullCommand fllCmmnd)
{
    QMessageBox msgBox;
    msgBox.setWindowTitle("Warcaby");
    msgBox.setText("Błąd protokołu!");
    msgBox.exec();
    qDebug()<<"Protocol Error: "<<fllCmmnd.com<<fllCmmnd.parameters;
}
void Controler::openRoomWindow()
{

    roomWindow.setUser(user);
    roomWindow.show();
   // refreshPlayersList();
}

void Controler::refreshPlayersList()
{
    changeState(LSP_WFR);
    comm->sendCommand(LSP);

}

void Controler::clearSelection()
{
    isFirstClicked=false;
    gameWindow.board->isFirstClicked=isFirstClicked;
     gameWindow.board->clearSelection();
}
