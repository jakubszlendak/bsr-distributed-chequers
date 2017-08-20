#include "roomwindow.h"
#include "ui_roomwindow.h"
#include <QMessageBox>
RoomWindow::RoomWindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::RoomWindow)
{
    ui->setupUi(this);
    ui->waitingLabel->setVisible(false);
    ui->listWidget->setSelectionMode(QAbstractItemView::SingleSelection);
    waitingLabelStandardText = "Oczekiwanie na odpowiedź użytkownika ";
}

RoomWindow::~RoomWindow()
{
    delete ui;
}

void RoomWindow::setUser(QString user)
{
    ui->userLabel->setText(user);
}

void RoomWindow::refreshPlayersList(std::list<Player> playersList)
{
    ui->listWidget->clear();
    for(auto i :playersList)
    {
        QListWidgetItem * item = new QListWidgetItem(i.name, ui->listWidget);
        if(!i.free)
        {
         item->setFlags(Qt::ItemIsSelectable);
        }
    }

}

void RoomWindow::waitingForPlayerResponse(bool yesOrNo, QString user)
{
    if(yesOrNo)
    {
        ui->waitingLabel->setText(waitingLabelStandardText + user);
        ui->waitingLabel->setVisible(true);
        ui->invitePushButton->setEnabled(false);
        ui->logoutPushButton->setEnabled(false);
        ui->refreshPushButton->setEnabled(false);
    }
    else
    {
        ui->waitingLabel->setVisible(false);
        ui->invitePushButton->setEnabled(true);
        ui->logoutPushButton->setEnabled(true);
        ui->refreshPushButton->setEnabled(true);
    }
}

void RoomWindow::on_refreshPushButton_clicked()
{
    emit refreshButtonSignal();
}

void RoomWindow::on_logoutPushButton_clicked()
{
    emit logout();
}

void RoomWindow::on_invitePushButton_clicked()
{
    auto a =  ui->listWidget->currentItem();
    if (ui->listWidget->currentItem()!=NULL)
    {
    emit invite( ui->listWidget->currentItem()->text());
    }
    else
    {
        QMessageBox msgBox;
        msgBox.setWindowTitle("Warcaby");
        msgBox.setText("Aby zaprosić przeciwnika do rozgrywki, najpierw wybierz go z listy");
        msgBox.exec();

    }

}

void RoomWindow::showEvent(QShowEvent *event)
{
    QWidget::showEvent( event );
    emit refreshButtonSignal();

}
