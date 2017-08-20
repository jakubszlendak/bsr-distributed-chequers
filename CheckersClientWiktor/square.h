#ifndef SQUARE_H
#define SQUARE_H
#include "piece.h"
#include <QWidget>
class Board;
class Square : public QWidget
{
    Q_OBJECT
public:
    explicit Square(QWidget *parent = nullptr, Qt::GlobalColor color = Qt::white,int x=0,int y=0);
    bool selected;
     int x,y;
     void setMyChildPiece(Piece* mChldPc);
protected:
    void paintEvent(QPaintEvent *event);
    void mouseReleaseEvent(QMouseEvent *event)override;
signals:
     void squareClickedWithMouse(Square* that);
public slots:

private:

    Qt::GlobalColor color;
    Piece* myChildPiece;
    Board* myParentBoard;


};

#endif // SQUARE_H
