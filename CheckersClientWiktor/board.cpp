#include "board.h"
#include "piece.h"

Board::Board(QWidget *parent) : QWidget(parent)
{
   //   std::vector<std::vector<Square>> sqrtbl(8,std::vector<Square>(8));
     // squaresTable=sqrtbl;
    //squaresTable->push_back(std::vector<Square*>());
    //square = new Square(this);
    //resize(800,800);
    setFixedSize(400,400);
    QGridLayout* grid = new QGridLayout;
    //grid->setMargin(0);
    grid->setAlignment(Qt::AlignCenter);
    setContentsMargins(0,0,0,0);
    this->setLayout(grid);
    squareTable = new Square*[64];
    piecesTable = new Piece*[64];
    for (int i = 0;i<8;i++)
    {
       // std::vector<Square*> sqRow = new std::vector<Square*>();
        for(int j = 0; j<8;j++)
        {

            Qt::GlobalColor squareColor;
            if((i+j)%2==0){
                squareColor=Qt::white;
            }else{
                squareColor=Qt::black;
            }
            squareTable[i*8+j]=new Square(this,squareColor,j,i);
            grid->addWidget(squareTable[i*8+j],i,j);
            QGridLayout* gridSquare = new QGridLayout;
            gridSquare->setAlignment(Qt::AlignCenter);
            gridSquare->setContentsMargins(0,0,0,0);
            squareTable[i*8+j]->setLayout(gridSquare);
            piecesTable[i*8+j]=new Piece(squareTable[i*8+j],not_exists);
            squareTable[i*8+j]->setMyChildPiece(piecesTable[i*8+j]);
             connect((squareTable[i*8+j]),SIGNAL(squareClickedWithMouse(Square*)),this,SLOT(squareClickedWithMouse(Square*)));
            gridSquare->addWidget(piecesTable[i*8+j]);
            //Square sq(this,Qt::white);
            //sqRow->push_back(new Square(this,squareColor));
        }
       // squaresTable->push_back(sqRow);
    }
//    for (int i = 0;i<8;i++)
//    {
//        for(int j = 0; j<8;j++)
//        {

//            Qt::GlobalColor squareColor;
//            if(i+j%2==0){
//                squareColor=Qt::white;
//            }else{
//                squareColor=Qt::black;
//            }
//            Square sq(this,squareColor);
//            squaresTable[i][j]=sq;

//            //sqRow.push_back(sq);
//        }
//    }


}

void Board::getPiecesTable(PieceState gotTable[])
{
    for(int i = 0; i<64;i++)
    {
        piecesTable[i]->changeState(gotTable[i]);
        squareTable[i]->selected=false;
    }
    update();
}

void Board::clearSelection()
{
    for(int i = 0; i<64;i++)
    {
        squareTable[i]->selected=false;
    }
    update();
}

void Board::currentPlayer(bool crrPlr)
{
    crrPlayer=crrPlr;
}

void Board::squareClickedWithMouse(Square *that)
{
    emit squareClickedWithMouseSignal(that);
}
