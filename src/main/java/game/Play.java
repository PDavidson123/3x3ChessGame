package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Play
{

    private static Logger logger = LoggerFactory.getLogger(Play.class);

    public static boolean FirstClick = true;
    private static boolean ColorSelection = true;
    public static int[] FirstClickNumberO = new int[2];

    public static int[][] groundO = new int[3][3];

    public static int StepCounter=0;

    /**
     * A megtenni kívánt lépéseket ellenőrzi, teszi meg.
     * @param Height a választott bábu Y koordinátája
     * @param Width a választott bábu X koordinátája
     */
    public void step(int Height,int Width, int[][] ground,int[] FirstClickNumber, boolean IsFirstClick)
    {
        if(IsFirstClick) {
            stepIsNull(Height,Width, ground);
        }
        else {
            if (Height==FirstClickNumber[0] && Width==FirstClickNumber[1]) {
                logger.warn("Ugyan oda nem léphet.");
                FXMLController.getInstance().setMessageOutText("Ugyan oda nem léphetsz.");
            }

            else {
                if(ground[Height][Width]==0 && ground[FirstClickNumber[0]][FirstClickNumber[1]]==1 && ColorSelection) {
                    if(canWhiteMove(Height,Width,ground, FirstClickNumber)) {
                        groundO[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                        groundO[Height][Width]=1;

                        FirstClick=true;
                        ColorSelection=false;
                        StepCounter++;
                        logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + Height + "," + Width + "-re lépett.");
                        checkwin();
                        FXMLController.getInstance().setMessageOutText(StepCounter + ". lépés");
                    }
                }
                else if(ground[Height][Width]==0 && ground[FirstClickNumber[0]][FirstClickNumber[1]]==2 && !ColorSelection) {
                    if(canBlackMove(Height,Width,ground, FirstClickNumber)) {
                        groundO[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                        groundO[Height][Width]=2;

                        FirstClick=true;
                        ColorSelection=true;
                        StepCounter++;
                        logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + Height + "," + Width + "-re lépett.");
                        checkwin();
                        FXMLController.getInstance().setMessageOutText(StepCounter + ". lépés");
                    }
                }
            }
            logger.info(StepCounter + ". lépés.");
        }
    }

    /**
     * Azt állapítja meg, hogy a fehér hova léphet.
     * @param Height a választott bábu Y koordinátája
     * @param Width a választott bábu X koordinátája
     * @param ground a játéktér mátrixa
     * @param FirstClickNumber 1. lépés helye
     * @return
     */
    public boolean canWhiteMove(int Height, int Width, int[][] ground, int[] FirstClickNumber)
    {
        if(ground[Height][Width]==0 && (FirstClickNumber[0]+1 == Height && FirstClickNumber[1]+2 == Width) || (FirstClickNumber[0]+1 == Height && FirstClickNumber[1]-2 == Width) || (FirstClickNumber[0]+2 == Height && FirstClickNumber[1]+1 == Width) || (FirstClickNumber[0]+2 == Height && FirstClickNumber[1]-1 == Width))
            return true;
        else
            return false;
    }

    /**
     * Azt állapítja meg, hogy a fekete hova léphet.
     * @param Height a választott bábu Y koordinátája
     * @param Width a választott bábu X koordinátája
     * @param ground a játéktér mátrixa
     * @param FirstClickNumber 1. lépés helye
     * @return
     */
    public boolean canBlackMove(int Height, int Width, int[][] ground, int[] FirstClickNumber)
    {
        if(ground[Height][Width]==0 && (FirstClickNumber[0]-1 == Height && FirstClickNumber[1]-2 == Width) || (FirstClickNumber[0]-1 == Height && FirstClickNumber[1]+2 == Width) || (FirstClickNumber[0]-2 == Height && FirstClickNumber[1]-1 == Width) || (FirstClickNumber[0]-2 == Height && FirstClickNumber[1]+1 == Width))
            return true;
        else
            return false;
    }

    /**
     * Ellenőrzi, hogy bábut választott-e a játékos.
     * @param Height a választott mező Y koordinátája
     * @param Width A választott mező X koordinátája
     */
    public static void stepIsNull(int Height, int Width, int[][] ground) {
        if(ground[Height][Width] == 0) {
            logger.warn("A semmivel nem lehet lépni.");
        }
        else
            stepColorSelect(Height,Width, ground);
    }

    /**
     * Eldönti, hogy a játékos a jó színt választotta-e.
     * @param Height a választott bábu Y koordinátája
     * @param Width a választott bábu X koordinátája
     */
    public static void stepColorSelect(int Height,int Width, int[][] ground) {
        if(ColorSelection && ground[Height][Width]==2 || !ColorSelection && ground[Height][Width]==1) {
            logger.warn("Rossz szín választva.");
        }
        else if(ColorSelection && ground[Height][Width]==1 || !ColorSelection && ground[Height][Width]==2) {
            FirstClickNumberO[0] = Height;
            FirstClickNumberO[1] = Width;
            FirstClick = false;
            logger.info(FirstClickNumberO[0] + "," + FirstClickNumberO[1] + " kiválasztva.");
        }
    }

    /**
     * Ellenőrzi, hogy a játékos nyert-e.
     */
    public void checkwin()
    {
        int goodness=0;
        for (int i=0;i<3;i++)
        {
            if(groundO[0][i]==2)
                goodness++;
            if(groundO[2][i]==1)
                goodness++;
        }
        logger.info(goodness + " bábu van a helyén.");
        if(checkGoodness(goodness))
            FXMLController.getInstance().playwin();
    }

    /**
     * @param goodness a már célban levő bábuk száma
     * @return true-val, hogyha nyert
     */
    public boolean checkGoodness(int goodness) {
        if(goodness == 6)
            return true;
        else
            return false;
    }
}
