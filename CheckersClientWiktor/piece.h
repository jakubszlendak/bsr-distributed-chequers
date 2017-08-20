#ifndef PIECE_H
#define PIECE_H

#include <QWidget>
enum PieceState{
    black,
    white,
    black_King,
    white_King,
    not_exists
};

class Piece : public QWidget
{
    Q_OBJECT
public:
    explicit Piece(QWidget *parent = nullptr,PieceState pS = not_exists);
    PieceState pieceState;
    bool side; //0 - Opponent, 1 - client player;

    void changeState(PieceState PS);
protected:
    void paintEvent(QPaintEvent *event);
signals:


private:



};

#endif // PIECE_H
