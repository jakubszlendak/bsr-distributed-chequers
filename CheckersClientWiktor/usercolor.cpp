#include "usercolor.h"
#include "qpainter.h"
UserColor::UserColor(QWidget *parent, bool color) : QWidget(parent)
{
    this->color=color;
    QPalette palette(UserColor::palette());
    palette.setColor(backgroundRole(),Qt::white);
    setPalette(palette);
    setFixedSize(10,10);
    setContentsMargins(0,0,0,0);
}
void UserColor::paintEvent(QPaintEvent *event)
{
    QPainter painter(this);
        painter.setRenderHint(QPainter::Antialiasing);
        if(color){
//            QPen qpen(Qt::red);
//            qpen.setWidth(4);
//            painter.setPen(qpen);
            painter.setBrush(QBrush(Qt::white));
        }else
        {
            painter.setBrush(QBrush(Qt::black));
        }

        painter.drawRect(0, 0, 10, 10);
}
