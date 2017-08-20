#include "userindicator.h"
#include "qpainter.h"
UserIndicator::UserIndicator(QWidget *parent) : QWidget(parent)
{
    QPalette palette(UserIndicator::palette());
    palette.setColor(backgroundRole(),Qt::white);
    setPalette(palette);
    setFixedSize(6,6);
    setContentsMargins(0,0,0,0);
}
void UserIndicator::paintEvent(QPaintEvent *event)
{
    if(nowMe)
    {
        QPainter painter(this);
        painter.setRenderHint(QPainter::Antialiasing);
        painter.setBrush(QBrush(Qt::black));
        //painter.setPen(QPen(Qt::black));
        painter.drawEllipse(0,0,5,5);
    }
}
