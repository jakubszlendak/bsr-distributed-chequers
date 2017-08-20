#include "piece.h"
#include "qpainter.h"
Piece::Piece(QWidget *parent, PieceState pS) : QWidget(parent)
{
    pieceState=pS;
    QPalette palette(Piece::palette());
    palette.setColor(backgroundRole(),Qt::white);
    setPalette(palette);
    setFixedSize(40,40);
    setContentsMargins(5,5,5,5);


}

void Piece::changeState(PieceState PS)
{
    pieceState=PS;

    update();

}

void Piece::paintEvent(QPaintEvent *event)
{
    QPainter painter(this);
        painter.setRenderHint(QPainter::Antialiasing);
        switch (pieceState)
        {
            case black:
            {
                painter.setBrush(QBrush(Qt::black));
                painter.setPen(QPen(Qt::white,2));
                painter.drawEllipse(0,0,40,40);
                break;
            }
            case white:
            {
                painter.setBrush(QBrush(Qt::white));
                //painter.setPen(QPen(Qt::black));
                painter.drawEllipse(0,0,40,40);
                break;
            }
            case black_King:
            {
                painter.setBrush(QBrush(Qt::black));
                painter.setPen(QPen(Qt::white));
                painter.drawEllipse(0,0,40,40);
                painter.setPen(QPen(Qt::white, 6, Qt::SolidLine, Qt::RoundCap));
                painter.drawLine(5, 20, 35, 20);
                painter.drawLine(20, 5, 20, 35);
                break;
            }
            case white_King:
            {
                painter.setBrush(QBrush(Qt::white));
                painter.setPen(QPen(Qt::black));
                painter.drawEllipse(0,0,40,40);
                painter.setPen(QPen(Qt::black, 6, Qt::SolidLine, Qt::RoundCap));
                painter.drawLine(5, 20, 35, 20);
                painter.drawLine(20, 5, 20, 35);
                break;
            }
        case not_exists:
            break;
        }

}
