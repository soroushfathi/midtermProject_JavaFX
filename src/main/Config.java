package main;

public class Config {
    public static int TILE_SIZE = 70;
    public static int WIDTH = 10;
    public static int HEIGHT = 10;

    public static String FIRST_COLOR = "#ba68c8";
    public static String SECOND_COLOR = "#ce93d8";

    public static String SAFE_COLOR = "#00c853";
    public static double SAFE_OPACITY = 0.5;

    public static boolean PREPARE = true;

    public static int SETTING_WIDTH = 215;

    public static int LIMIT =WIDTH;

    public static PlayType PLAY_TYPE=PlayType.LOCAL;

    public static int SERVER_RESPONSE_TIMEOUT=20;

    public static boolean YOUR_TURN =false;
    public static int MY_ID ;
    public static boolean MOVED=false ;
    public static String LAST_MOVE="" ;
    public static String FROM;
    public static String TO;
    public static boolean GAME_IS_STARTED=true;

}