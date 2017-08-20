//#include "loginwindow.h"
#include "roomwindow.h"
#include "controler.h"
#include <QApplication>
#include<time.h>
#include <QTranslator>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Controler controler;// = new Controler();
    QTranslator qtTranslator;
        if (qtTranslator.load(QLocale::system(),
                    "qt", "_",
                    QLibraryInfo::location(QLibraryInfo::TranslationsPath)))
        {
            qDebug() << "qtTranslator ok";
            a.installTranslator(&qtTranslator);
        }

        QTranslator qtBaseTranslator;
        if (qtBaseTranslator.load("qtbase_" + QLocale::system().name(),
                    QLibraryInfo::location(QLibraryInfo::TranslationsPath)))
        {
            qDebug() << "qtBaseTranslator ok";
            a.installTranslator(&qtBaseTranslator);
        }
//    QDesktopWidget *qdw = a.desktop();
//    int scrWdth=qdw.screen()->width();
//    int scrHght=qdw.screen()->height();
//    int width=this->frameGeometry().width();
//    int height=this->frameGeometry().height();
//    this->setGeometry(((scrWdth/2)-(width/2)),((scrHght/2)-(height/2)),width,height);
    //QDesktopWidget qdw;
    //controler.startSession();
    //RoomWindow rmwndw;
    //rmwndw.show();

    return a.exec();
}
