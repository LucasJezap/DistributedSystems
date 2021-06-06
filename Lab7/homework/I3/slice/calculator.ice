
#ifndef CALC_ICE
#define CALC_ICE

module Demo
{
  struct Line
  {
    float x1;
    float y1;
    float x2;
    float y2;
  }
	
  sequence <float> Seq;
  sequence <float> SquareSeq;

  dictionary <string, int> LetterCount;

  interface Calc
  {
    float lineLength(Line l);
    SquareSeq squareSequence(Seq s);
    LetterCount countLetters(string sentence);
  };

};

#endif
