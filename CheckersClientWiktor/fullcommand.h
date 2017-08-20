#ifndef COMMAND_H
#define COMMAND_H

#include <QStringList>

enum command{
    LGN,
    CRA,
    LSP,
    RFP,
    RP1,
    RP2,
    INI,
    CHB,
    YMV,
    GVU,
    EOG,
    LGO,
    ERR,
    ERS,
    MOV,
    INTERNAL_ERROR

};
class fullCommand{
public:
    fullCommand();
    command com;
    QStringList parameters;
    QString par1();
    QString par2();
    QString par3();
    QString par4();
};
//class command
//{
//public:
//    command();
//};

#endif // COMMAND_H
