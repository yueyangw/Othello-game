package Othello;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


//图片加载
public class ImageValue {
    public static BufferedImage whiteImage = null;//白棋图片
    public static BufferedImage blackImage = null;//黑棋图片
    public static BufferedImage background = null;//背景图片
    public static BufferedImage InitialBackGround = null;//初始界面背景图片
    public static BufferedImage Play = null;//
    public static BufferedImage Cheating = null;//
    public static BufferedImage Computer = null;//
    public static BufferedImage Helper = null;//
    public static BufferedImage ChooseBackground = null;//
    public static BufferedImage ReadFile = null;//
    public static BufferedImage yes = null;//
    public static BufferedImage GamePanelback = null;//
    public static BufferedImage fanhui = null;//
    public static BufferedImage huiqi =  null;//
    public static BufferedImage cundang = null;//
    public static BufferedImage chongxinkaishi = null;//
    public static BufferedImage reminder = null;
    public static BufferedImage renjichoose = null;
//    public static BufferedImage huifang = null;


    private static String path = "/images/";

    public static void init() {
        try{
            background = ImageIO.read(ImageValue.class.getResource(path + "background.jpg"));
            whiteImage = ImageIO.read(ImageValue.class.getResource(path + "w6.png"));
            blackImage = ImageIO.read(ImageValue.class.getResource(path + "b8.png"));
            InitialBackGround = ImageIO.read(ImageValue.class.getResource(path + "InitialBackGround.jpg"));
            Play = ImageIO.read(ImageValue.class.getResource(path + "开始游戏.png"));
            Cheating = ImageIO.read(ImageValue.class.getResource(path + "上帝模式.png"));
            Computer = ImageIO.read(ImageValue.class.getResource(path + "人机对战.png"));
            Helper = ImageIO.read(ImageValue.class.getResource(path + "帮助说明.png"));
            ChooseBackground = ImageIO.read(ImageValue.class.getResource(path + "选择界面背景.JPG"));
            ReadFile = ImageIO.read(ImageValue.class.getResource(path + "读取存档.png"));
            yes = ImageIO.read(ImageValue.class.getResource(path + "确定.png"));
            GamePanelback = ImageIO.read(ImageValue.class.getResource(path + "GamePanelback.jpg"));
            fanhui = ImageIO.read(ImageValue.class.getResource(path + "返回至主菜单.png"));
            huiqi = ImageIO.read(ImageValue.class.getResource(path + "悔棋.png"));
            cundang = ImageIO.read(ImageValue.class.getResource(path + "存档.png"));
            chongxinkaishi = ImageIO.read(ImageValue.class.getResource(path + "重新开始.png"));
            reminder = ImageIO.read(ImageValue.class.getResource(path + "img.png"));
            renjichoose = ImageIO.read(ImageValue.class.getResource(path + "人机选择.jpg"));
//            huifang = ImageIO.read(ImageValue.class.getResource(path + "回放.png"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
