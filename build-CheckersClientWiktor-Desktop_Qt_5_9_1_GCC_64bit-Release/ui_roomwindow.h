/********************************************************************************
** Form generated from reading UI file 'roomwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.9.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_ROOMWINDOW_H
#define UI_ROOMWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QVBoxLayout>

QT_BEGIN_NAMESPACE

class Ui_RoomWindow
{
public:
    QGridLayout *gridLayout;
    QVBoxLayout *verticalLayout;
    QGridLayout *gridLayout_2;
    QLabel *label;
    QPushButton *logoutPushButton;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QPushButton *refreshPushButton;
    QSpacerItem *horizontalSpacer_2;
    QVBoxLayout *verticalLayout_2;
    QPushButton *invitePushButton;
    QSpacerItem *verticalSpacer_2;
    QLabel *waitingLabel;
    QSpacerItem *verticalSpacer;
    QLabel *label_2;
    QLabel *userLabel;
    QSpacerItem *verticalSpacer_3;
    QListWidget *listWidget;

    void setupUi(QDialog *RoomWindow)
    {
        if (RoomWindow->objectName().isEmpty())
            RoomWindow->setObjectName(QStringLiteral("RoomWindow"));
        RoomWindow->resize(422, 301);
        gridLayout = new QGridLayout(RoomWindow);
        gridLayout->setObjectName(QStringLiteral("gridLayout"));
        verticalLayout = new QVBoxLayout();
        verticalLayout->setObjectName(QStringLiteral("verticalLayout"));
        gridLayout_2 = new QGridLayout();
        gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        label = new QLabel(RoomWindow);
        label->setObjectName(QStringLiteral("label"));

        gridLayout_2->addWidget(label, 0, 1, 1, 1);

        logoutPushButton = new QPushButton(RoomWindow);
        logoutPushButton->setObjectName(QStringLiteral("logoutPushButton"));

        gridLayout_2->addWidget(logoutPushButton, 3, 2, 1, 1);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        refreshPushButton = new QPushButton(RoomWindow);
        refreshPushButton->setObjectName(QStringLiteral("refreshPushButton"));

        horizontalLayout->addWidget(refreshPushButton);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);


        gridLayout_2->addLayout(horizontalLayout, 3, 1, 1, 1);

        verticalLayout_2 = new QVBoxLayout();
        verticalLayout_2->setObjectName(QStringLiteral("verticalLayout_2"));
        invitePushButton = new QPushButton(RoomWindow);
        invitePushButton->setObjectName(QStringLiteral("invitePushButton"));

        verticalLayout_2->addWidget(invitePushButton);

        verticalSpacer_2 = new QSpacerItem(10, 10, QSizePolicy::Minimum, QSizePolicy::Minimum);

        verticalLayout_2->addItem(verticalSpacer_2);

        waitingLabel = new QLabel(RoomWindow);
        waitingLabel->setObjectName(QStringLiteral("waitingLabel"));
        waitingLabel->setEnabled(true);
        QFont font;
        font.setPointSize(10);
        waitingLabel->setFont(font);
        waitingLabel->setAlignment(Qt::AlignCenter);
        waitingLabel->setWordWrap(true);

        verticalLayout_2->addWidget(waitingLabel);

        verticalSpacer = new QSpacerItem(20, 40, QSizePolicy::Minimum, QSizePolicy::Expanding);

        verticalLayout_2->addItem(verticalSpacer);

        label_2 = new QLabel(RoomWindow);
        label_2->setObjectName(QStringLiteral("label_2"));

        verticalLayout_2->addWidget(label_2);

        userLabel = new QLabel(RoomWindow);
        userLabel->setObjectName(QStringLiteral("userLabel"));

        verticalLayout_2->addWidget(userLabel);

        verticalSpacer_3 = new QSpacerItem(20, 5, QSizePolicy::Minimum, QSizePolicy::Minimum);

        verticalLayout_2->addItem(verticalSpacer_3);


        gridLayout_2->addLayout(verticalLayout_2, 1, 2, 1, 1);

        listWidget = new QListWidget(RoomWindow);
        listWidget->setObjectName(QStringLiteral("listWidget"));

        gridLayout_2->addWidget(listWidget, 1, 1, 1, 1);


        verticalLayout->addLayout(gridLayout_2);


        gridLayout->addLayout(verticalLayout, 0, 0, 1, 1);


        retranslateUi(RoomWindow);

        QMetaObject::connectSlotsByName(RoomWindow);
    } // setupUi

    void retranslateUi(QDialog *RoomWindow)
    {
        RoomWindow->setWindowTitle(QApplication::translate("RoomWindow", "Warcaby", Q_NULLPTR));
        label->setText(QApplication::translate("RoomWindow", "Lista graczy", Q_NULLPTR));
        logoutPushButton->setText(QApplication::translate("RoomWindow", "Wyloguj", Q_NULLPTR));
        refreshPushButton->setText(QApplication::translate("RoomWindow", "Od\305\233wie\305\274 list\304\231", Q_NULLPTR));
        invitePushButton->setText(QApplication::translate("RoomWindow", "Zapro\305\233 do gry", Q_NULLPTR));
        waitingLabel->setText(QApplication::translate("RoomWindow", "Oczekiwanie na odpowied\305\272 u\305\274ytkownika", Q_NULLPTR));
        label_2->setText(QApplication::translate("RoomWindow", "Tw\303\263j login:", Q_NULLPTR));
        userLabel->setText(QApplication::translate("RoomWindow", "user", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class RoomWindow: public Ui_RoomWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_ROOMWINDOW_H
