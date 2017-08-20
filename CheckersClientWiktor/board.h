#ifndef BOARD_H
#define BOARD_H

#include <QWidget>
#include "square.h"
#include "piece.h"
#include <algorithm>
#include <memory>
#include <vector>
#include <QGridLayout>
class Board : public QWidget
{
    Q_OBJECT
public:
    explicit Board(QWidget *parent = nullptr);
    void getPiecesTable(PieceState gotTable[]);
    void clearSelection();
    void currentPlayer(bool crrPlr);
    bool crrPlayer;
    bool mySide; // 0 black,1 white
    bool isFirstClicked;
signals:
    void squareClickedWithMouseSignal(Square* that);
private slots:
    void squareClickedWithMouse(Square* that);
private:
    //Square *square;
    //std::vector<std::vector<Square*>>* squaresTable;//(8,vector<point>(8));
    Square **squareTable;
    Piece **piecesTable;
    //bool myColor; // 0 - black, 1 - white

    //Matrix ma;
    //Square squaresTable [8][8];
};

#endif // BOARD_H
