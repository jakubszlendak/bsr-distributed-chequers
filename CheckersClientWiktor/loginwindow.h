#ifndef LOGINWINDOW_H
#define LOGINWINDOW_H

#include <QMainWindow>
#include <QRegularExpressionValidator>
//#include <QString>
namespace Ui {
class LoginWindow;
}

class LoginWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = 0);
    ~LoginWindow();
    void clearFields();
private slots:
    void on_loginButton_clicked();

    void on_loginLineEdit_textChanged(const QString &arg1);

    void on_passwordLineEdit_textChanged(const QString &arg1);

    void on_registerButton_clicked();

signals:
    void login(QString login, QString password);
    void regist(QString login, QString password);

private:
    Ui::LoginWindow *ui;
    QString id;
    QString password;
    QRegularExpression *re;
    QRegularExpressionValidator* v;

};

#endif // LOGINWINDOW_H
