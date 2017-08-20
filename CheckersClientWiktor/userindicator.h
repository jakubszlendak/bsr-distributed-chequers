#ifndef USERINDICATOR_H
#define USERINDICATOR_H

#include <QWidget>

class UserIndicator : public QWidget
{
    Q_OBJECT
public:
    explicit UserIndicator(QWidget *parent = nullptr);
    bool nowMe;
protected:
    void paintEvent(QPaintEvent *event)override;
signals:

public slots:
};

#endif // USERINDICATOR_H
