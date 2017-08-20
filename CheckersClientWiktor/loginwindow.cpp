#include "loginwindow.h"
#include "ui_loginwindow.h"
#include <QMessageBox>

LoginWindow::LoginWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::LoginWindow)
{
    ui->setupUi(this);
    this->move(100,100);
    re= new QRegularExpression("[a-zA-Z0-9]*");
    v= new QRegularExpressionValidator(*re, 0);
   // ui->loginLineEdit->setValidator(v);
    //ui->passwordLineEdit->setValidator(v);

  //  QDesktopWidget qdw = ;
//    int scrWdth=qdw.screen()->width();
//    int scrHght=qdw.screen()->height();
//    int width=this->frameGeometry().width();
//    int height=this->frameGeometry().height();
//    this->setGeometry(((scrWdth/2)-(width/2)),((scrHght/2)-(height/2)),width,height);
}

LoginWindow::~LoginWindow()
{
    delete ui;
}

void LoginWindow::clearFields()
{
    ui->loginLineEdit->setText("");
    ui->passwordLineEdit->setText("");
}



void LoginWindow::on_loginButton_clicked()
{
    QString a = ui->loginLineEdit->text();
    QString b = ui->passwordLineEdit->text();
    int pos =0;
    if(v->validate(a,pos)!=QValidator::Invalid &
            v->validate(b,pos)!=QValidator::Invalid)
    {

        emit login(id,password);
    }
    else
    {
        QMessageBox msgBox;
        msgBox.setWindowTitle("Warcaby");
        msgBox.setText("Login oraz hasło mogą składać się tylko ze znaków alfanumerycznych bez znaków diaktrycznych");
        msgBox.exec();

    }
}

void LoginWindow::on_loginLineEdit_textChanged(const QString &arg1)
{
    id = ui->loginLineEdit->text();

}

void LoginWindow::on_passwordLineEdit_textChanged(const QString &arg1)
{
    password = ui->passwordLineEdit->text();
}

void LoginWindow::on_registerButton_clicked()
{
    QString a = ui->loginLineEdit->text();
    QString b = ui->passwordLineEdit->text();
    int pos =0;
    if(v->validate(a,pos)!=QValidator::Invalid &
            v->validate(b,pos)!=QValidator::Invalid)
    {

        emit regist(id,password);
    }
    else
    {
        QMessageBox msgBox;
        msgBox.setWindowTitle("Warcaby");
        msgBox.setText("Login oraz hasło mogą składać się tylko ze znaków alfanumerycznych bez znaków diaktrycznych");
        msgBox.exec();

    }
}
