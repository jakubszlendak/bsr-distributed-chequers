#-------------------------------------------------
#
# Project created by QtCreator 2017-08-10T18:14:37
#
#-------------------------------------------------

QT       += core gui
QT       += network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CheckersClientWiktor
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which as been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0


SOURCES += \
        main.cpp \
        loginwindow.cpp \
    controler.cpp \
    view.cpp \
    roomwindow.cpp \
    communication.cpp \
    fullcommand.cpp \
    player.cpp \
    gamewindow.cpp \
    board.cpp \
    square.cpp \
    piece.cpp \
    userindicator.cpp \
 #   board.cpp \
#    communication.cpp \
#    controler.cpp \
#    fullcommand.cpp \
#    gamewindow.cpp \
#    loginwindow.cpp \
#    main.cpp \
#    piece.cpp \
#    player.cpp \
#    roomwindow.cpp \
#    square.cpp \
#    userindicator.cpp \
#    view.cpp \
   usersgamepanel.cpp \
    usercolor.cpp

HEADERS += \
        loginwindow.h \
    controler.h \
    view.h \
    roomwindow.h \
    communication.h \
    fullcommand.h \
    player.h \
    state.h \
    gamewindow.h \
    board.h \
    square.h \
    piece.h \
    userindicator.h \
  #  board.h \
#    communication.h \
#    controler.h \
#    fullcommand.h \
#    gamewindow.h \
#    loginwindow.h \
#    piece.h \
#    player.h \
#    roomwindow.h \
#    square.h \
#    state.h \
#    userindicator.h \
#    view.h \
    usersgamepanel.h \
   usercolor.h

FORMS += \
        loginwindow.ui \
    roomwindow.ui \
    gamewindow.ui
