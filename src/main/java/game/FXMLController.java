package game;

import database.gamer.Gamer;
import database.jpa.DBTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FXMLController {

    @FXML
    private Button winButton;
    @FXML
    private ImageView b00;
    @FXML
    private ImageView b01;
    @FXML
    private ImageView b02;
    @FXML
    private  ImageView b10;
    @FXML
    private  ImageView b11;
    @FXML
    private ImageView b12;
    @FXML
    private ImageView b20;
    @FXML
    private ImageView b21;
    @FXML
    private ImageView b22;
    @FXML
    private AnchorPane start;
    @FXML
    private AnchorPane gamepane;
    @FXML
    private AnchorPane preppane;
    @FXML
    private AnchorPane leaderboardPane;
    @FXML
    private TextField gamer1;
    @FXML
    private Label nameLabel;
    @FXML
    private Label messageout;
    @FXML
    private Label stepLabel;
    @FXML
    private TableView leaderboard;
    @FXML
    private TableColumn<Gamer, String> id;
    @FXML
    private TableColumn<Gamer, String> name;
    @FXML
    private TableColumn<Gamer, String> winscore;


    public static ImageView[][] btns = new ImageView[3][3];
    private static byte[][] ground = new byte[3][3];
    private static String player1;
    private static Image blackblack;
    private static Image whiteblack;
    private static Image whitewhite;
    private static Image blackwhite;
    private static Image black;
    private static Image white;

    private boolean IsFirstClick = true;
    private boolean ColorSelection = true;
    private int[] FirstClickNumber = new int[2];
    private int StepCounter=0;

    private static Logger logger = LoggerFactory.getLogger(FXMLController.class);

    private Gamer gamer = new Gamer();
    DBTools conn = new DBTools();

    @FXML
    private void B_startClick(ActionEvent event) {
        preppane.setVisible(true);
        start.setVisible(false);
    }
    @FXML
    private void B_gamebeginClick(ActionEvent event) {
        preppane.setVisible(false);
        gamepane.setVisible(true);

        gamer.setName(gamer1.getText());

        conn.addGamer(gamer);

        gamebegin();
    }
    @FXML
    private void winButtonClick(ActionEvent event) {
        start.setVisible(true);
        gamepane.setVisible(false);
        conn.updateGamer(gamer);
        resetGame();
    }
    @FXML
    private void leadbShow(ActionEvent event) {
        start.setVisible(false);
        leaderboardPane.setVisible(true);

        logger.info("Leaderboard lekérése.");

        ObservableList<Gamer> top = FXCollections.observableList(conn.getLeaderboard());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        winscore.setCellValueFactory(new PropertyValueFactory<>("score"));
        leaderboard.setItems(top);
    }
    @FXML
    private void leadBackButtonClick(ActionEvent event) {
        leaderboardPane.setVisible(false);
        start.setVisible(true);
    }

    @FXML
    private void b00Click() { step(0,0); }
    @FXML
    private void b01Click() { step(0,1); }
    @FXML
    private void b02Click() { step(0,2); }
    @FXML
    private void b10Click() { step(1,0); }
    @FXML
    private void b11Click() { step(1,1); }
    @FXML
    private void b12Click() { step(1,2); }
    @FXML
    private void b20Click() { step(2,0); }
    @FXML
    private void b21Click() { step(2,1); }
    @FXML
    private void b22Click() { step(2,2); }

    public void initialize() {

        logger.info("Játék elindítva.");

        start.setVisible(true);
        gamepane.setVisible(false);
        preppane.setVisible(false);
        leaderboardPane.setVisible(false);

        btns[0][0] = b00;
        btns[0][1] = b01;
        btns[0][2] = b02;
        btns[1][0] = b10;
        btns[1][1] = b11;
        btns[1][2] = b12;
        btns[2][0] = b20;
        btns[2][1] = b21;
        btns[2][2] = b22;

        resetGame();

        blackblack = new Image(getClass().getResource("blackblack.png").toString());
        whiteblack = new Image(getClass().getResource("whiteblack.png").toString());
        whitewhite = new Image(getClass().getResource("whitewhite.png").toString());
        blackwhite = new Image(getClass().getResource("blackwhite.png").toString());
        black = new Image(getClass().getResource("black.png").toString());
        white = new Image(getClass().getResource("white.png").toString());

        stepLabel.setText(StepCounter + " . lépés");
        paint();
    }

    public void gamebegin()
    {
        player1=gamer1.getText();
        nameLabel.setText(player1);
    }

    private void resetGame()
    {
        for(byte i=0;i<3;i++)
            ground[0][i] = 1;
        for(byte i=0;i<3;i++)
            ground[1][i] = 0;
        for(byte i=0;i<3;i++)
            ground[2][i] = 2;

        winButton.setVisible(false);
        StepCounter = 0;
        IsFirstClick = true;
        paint();
    }

    private static void paint()
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                if (i==0 && j==0 || i== 0 && j==2 || i==1 && j==1 || i==2 && j==0 || i==2 && j==2 /*i == j || abs(i + j) == 2*/)
                {
                    switch (ground[i][j])
                    {
                        case 0:
                            btns[i][j].setImage(black);
                            break;
                        case 1:
                            btns[i][j].setImage(blackwhite);
                            break;
                        case 2:
                            btns[i][j].setImage(blackblack);
                    }
                }
                else
                {
                    switch (ground[i][j]) {
                        case 0:
                            btns[i][j].setImage(white);
                            break;
                        case 1:
                            btns[i][j].setImage(whitewhite);
                            break;
                        case 2:
                            btns[i][j].setImage(whiteblack);
                    }
                }
            }
    }

    public void step(int a,int b)
    {
        if(IsFirstClick)
        {
            if(ground[a][b] == 0)
            {
                messageout.setText("Rossz választás.");
                logger.warn("A semmivel nem lehet lépni.");
            }
            else
            {
                if(ColorSelection && ground[a][b]==2 || !ColorSelection && ground[a][b]==1)
                {
                    messageout.setText("Rossz színt választottál.");
                    logger.warn("Rossz szín választva.");
                }
                else if(ColorSelection && ground[a][b]==1 || !ColorSelection && ground[a][b]==2)
                {
                    FirstClickNumber[0] = a;
                    FirstClickNumber[1] = b;
                    IsFirstClick = false;
                    messageout.setText("Kiválasztva.");
                    logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + " kiválasztva.");
                }
            }
        }
        else
        {
            if (a==FirstClickNumber[0] && b==FirstClickNumber[1])
            {
                messageout.setText("Ugyan oda nem léphetsz.");
                logger.warn("Ugyan oda nem léphet.");
            }

            else
            {
                if(ground[a][b]==0 && ground[FirstClickNumber[0]][FirstClickNumber[1]]==1 && ColorSelection)
                {
                    if(FirstClickNumber[0]<a)
                    {
                        if(ground[a][b]==0)
                        {
                            ground[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                            ground[a][b]=1;

                            paint();
                            IsFirstClick=true;
                            ColorSelection=false;
                            logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + a + "," + b + "-re lépett.");
                        }
                    }
                    else
                    {
                        messageout.setText("Vissza nem léphetsz.");
                        logger.warn("Vissza nem tud lépni.");
                    }
                }
                else if(ground[a][b]==0 && ground[FirstClickNumber[0]][FirstClickNumber[1]]==2 && !ColorSelection)
                {
                    if(FirstClickNumber[0]>a)
                    {
                        if(ground[a][b]==0)
                        {
                            ground[FirstClickNumber[0]][FirstClickNumber[1]] =0;
                            ground[a][b]=2;

                            paint();
                            IsFirstClick=true;
                            ColorSelection=true;
                            logger.info(FirstClickNumber[0] + "," + FirstClickNumber[1] + "-ről " + a + "," + b + "-re lépett.");
                        }

                    }
                }
            }
            StepCounter++;
            stepLabel.setText(StepCounter + " . lépés");
            logger.info( StepCounter + ". lépés.");
            chechwin();
        }
    }

    public void chechwin()
    {
        int goodness=0;
        for (int i=0;i<3;i++)
        {
            if(ground[0][i]==2)
                goodness++;
            if(ground[2][i]==1)
                goodness++;
        }
        logger.info(goodness + " bábu van a helyén.");
        if(goodness==6)
            playwin();
    }
    public void playwin()
    {
        logger.info(player1 + " megnyerte a játékot " + StepCounter + " lépéssel.");
        messageout.setText("Megnyerted a játékot.");
        winButton.setVisible(true);
    }
}