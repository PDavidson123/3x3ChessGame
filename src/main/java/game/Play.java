package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Play
{

    private static Logger logger = LoggerFactory.getLogger(FXMLController.class);

    public static boolean IsFirstClick = true;
    private static boolean ColorSelection = true;
    private static int[] FirstClickNumber = new int[2];

    /**
     * A megtenni kívánt lépéseket ellenőrzi, teszi meg.
     * @param Height
     * @param Width
     */
    public void step(int Height,int Width)
    {
        if(IsFirstClick) {
            stepIsNull(Height,Width);
        }
        else {
            if (Height==FirstClickNumber[0] && Width==FirstClickNumber[1]) {
                logger.warn("Ugyan oda nem léphet.");
                FXMLController.getInstance().setMessageOutText("Ugyan oda nem léphetsz.");
            }

            else {
                if(FXMLController.ground[Height][Width]==0 && FXMLController.ground[FirstClickNumber[0]][FirstClickNumber[1]]==1 && ColorSelection) {
                    if(FXMLController.ground[Height][Width]==0 && (FirstClickNumber[0]+1 == Height && FirstClickNumber[1]+2 == Width) || (FirstClickNumber[0]+1 == Height && FirstClickNumber[1]-2 == Width) || (FirstClickNumber[0]+2 == Height && FirstClickNumber[1]+1 == Width) || (FirstClickNumber[0]+2 == Height && FirstClickNumber[1]-1 == Width)) {
                        FXMLController.ground[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                        FXMLController.ground[Height][Width]=1;

                        FXMLController.paint();
                        IsFirstClick=true;
                        ColorSelection=false;
                        FXMLController.StepCounter++;
                        logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + Height + "," + Width + "-re lépett.");
                        checkwin();
                        FXMLController.getInstance().setMessageOutText(FXMLController.StepCounter + ". lépés");
                    }
                }
                else if(FXMLController.ground[Height][Width]==0 && FXMLController.ground[FirstClickNumber[0]][FirstClickNumber[1]]==2 && !ColorSelection) {
                    if(FXMLController.ground[Height][Width]==0 && (FirstClickNumber[0]-1 == Height && FirstClickNumber[1]-2 == Width) || (FirstClickNumber[0]-1 == Height && FirstClickNumber[1]+2 == Width) || (FirstClickNumber[0]-2 == Height && FirstClickNumber[1]-1 == Width) || (FirstClickNumber[0]-2 == Height && FirstClickNumber[1]+1 == Width)) {
                        FXMLController.ground[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                        FXMLController.ground[Height][Width]=2;

                        FXMLController.paint();
                        IsFirstClick=true;
                        ColorSelection=true;
                        FXMLController.StepCounter++;
                        logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + Height + "," + Width + "-re lépett.");
                        checkwin();
                        FXMLController.getInstance().setMessageOutText(FXMLController.StepCounter + ". lépés");
                    }
                }
            }
            logger.info( FXMLController.StepCounter + ". lépés.");
        }
    }

    /**
     * Ledönti, hogy a játékos a jó színt választotta-e.
     * @param Height
     * @param Width
     */
    public static void stepColorSelect(int Height,int Width) {
        if(ColorSelection && FXMLController.ground[Height][Width]==2 || !ColorSelection && FXMLController.ground[Height][Width]==1) {
            logger.warn("Rossz szín választva.");
        }
        else if(ColorSelection && FXMLController.ground[Height][Width]==1 || !ColorSelection && FXMLController.ground[Height][Width]==2) {
            FirstClickNumber[0] = Height;
            FirstClickNumber[1] = Width;
            IsFirstClick = false;
            logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + " kiválasztva.");
        }
    }

    /**
     * Ellenőrzi, hogy bábut választott-e a játékos.
     * @param Height
     * @param Width
     */
    public static void stepIsNull(int Height, int Width) {
        if(FXMLController.ground[Height][Width] == 0) {
            logger.warn("A semmivel nem lehet lépni.");
        }
        else
            stepColorSelect(Height,Width);
    }

    /**
     * Ellenőrzi, hogy a játékos nyert-e.
     */
    public void checkwin()
    {
        int goodness=0;
        for (int i=0;i<3;i++)
        {
            if(FXMLController.ground[0][i]==2)
                goodness++;
            if(FXMLController.ground[2][i]==1)
                goodness++;
        }
        logger.info(goodness + " bábu van a helyén.");
        if(checkGoodness(goodness))
            FXMLController.getInstance().playwin();
    }
    public boolean checkGoodness(int goodness) {
        if(goodness == 6)
            return true;
        else
            return false;
    }


}
