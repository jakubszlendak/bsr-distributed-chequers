#include "square.h"
#include "qpainter.h"
#include "board.h"
Square::Square(QWidget *parent, Qt::GlobalColor color, int x, int y) : QWidget(parent)
{
    QPalette palette(Square::palette());
    palette.setColor(backgroundRole(),Qt::white);
    setPalette(palette);
    setFixedSize(50,50);
    setContentsMargins(0,0,0,0);
    this->x=x;
    this->y=y;
    this->color=color;
    selected=false;
    myParentBoard=dynamic_cast<Board*>(parent);
    // myChildPiece=dynamic_cast<Piece*>
}

void Square::setMyChildPiece(Piece *mChldPc)
{
    myChildPiece=mChldPc;
}

void Square::paintEvent(QPaintEvent *event)
{
    QPainter painter(this);
        painter.setRenderHint(QPainter::Antialiasing);
        if(selected){
            QPen qpen(Qt::red);
            qpen.setWidth(4);
            painter.setPen(qpen);
        }
        painter.setBrush(QBrush(color));
        painter.drawRect(0, 0, 49, 49);
}
void Square::mouseReleaseEvent(QMouseEvent *event)
{
//    if(
//            (
//                (
//                    (myParentBoard->mySide && (myChildPiece->pieceState==white ||myChildPiece->pieceState==white_King))
//                    ||
//                    ((!myParentBoard->mySide)&& (myChildPiece->pieceState==black ||myChildPiece->pieceState==black_King))
//                )
//                &&
//                myParentBoard->crrPlayer
//                &&
//                myParentBoard->isFirstClicked
//            )
//            ||
//            (
//                !myParentBoard->isFirstClicked && myChildPiece->pieceState==not_exists
//            )

//       )
//    {
        selected= !selected;
        update();
        emit squareClickedWithMouse(this);
//    }
}
