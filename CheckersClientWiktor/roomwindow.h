#ifndef ROOMWINDOW_H
#define ROOMWINDOW_H

#include <QDialog>
#include <player.h>
#include <list>
namespace Ui {
class RoomWindow;
}

class RoomWindow : public QDialog
{
    Q_OBJECT

public:
    explicit RoomWindow(QWidget *parent = 0);
    ~RoomWindow();
     void setUser(QString user);
    void refreshPlayersList(std::list<Player> playersList);
    void waitingForPlayerResponse(bool yesOrNo,QString user = "");
private slots:
    void on_refreshPushButton_clicked();
    void on_logoutPushButton_clicked();

    void on_invitePushButton_clicked();

signals:
    void refreshButtonSignal();
    void invite(QString user);
    void logout();
private:
    void showEvent(QShowEvent* event)override;
    QString waitingLabelStandardText;
    Ui::RoomWindow *ui;
};

#endif // ROOMWINDOW_H
