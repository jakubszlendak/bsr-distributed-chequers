#ifndef USERCOLOR_H
#define USERCOLOR_H

#include <QWidget>

class UserColor : public QWidget
{
    Q_OBJECT
public:
    explicit UserColor(QWidget *parent = nullptr, bool color=0);
     bool color;
signals:

public slots:
protected:
    void paintEvent(QPaintEvent *event)override;
private:

};

#endif // USERCOLOR_H
