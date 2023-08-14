package ui;

import model.Coord;
import model.Mapble;
import service.FlyFigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements Runnable, Mapble {
    int Counter = 0;
    JLabel label = new JLabel("0");
    JButton gameOver = new JButton("Game Over");
    MenuWindow menuWindow = new MenuWindow();

    TimeAdapter timeAdapter = new TimeAdapter();
    Timer timer = new Timer(200, timeAdapter);

    private Box[][] boxes;
    private FlyFigure fly;

    public Window() {
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        initForm();
        initComponent();
        initBoxes();
        addKeyListener(new KeyAdapter());
        timer.start();
        ButtonGameOverAction();
    }

    private void initForm() {
        setSize(Config.WIDTH * Config.SIZE + 15, Config.HEIGHT * Config.SIZE + 39);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Tetris");
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponent(){
        label.setForeground(Color.white);

        label.setBounds(Config.WIDTH+250, Config.HEIGHT, 100,10);
        gameOver.setBounds(Config.WIDTH*10/2, Config.HEIGHT*12, 200, 50);

        gameOver.setVisible(false);

        add(label);
        add(gameOver);
    }

    public void ButtonGameOverAction() {
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                menuWindow.initWindow();
                menuWindow.initComponent();
            }
        };
        gameOver.addActionListener(actionListener1);
    }

    public void addFigure() {
        fly = new FlyFigure(this);
        if (fly.canPlaceFigure()) //Если не можем разместить фигуру заканчиваем игру
            showFigure();
        else {
            timer.stop();
            gameOver.setVisible(true);
        }
    }

    private void initBoxes() {
        for (int x = 0; x < Config.WIDTH; x++) {
            for (int y = 0; y < Config.HEIGHT; y++) {
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);
            }
        }
    }

    @Override
    public void run() {
        repaint();
    }

    private void showFigure() {
        showFigure(1);
    }

    private void hideFigure() {
        showFigure(0);
    }

    private void showFigure(int color) {
        for (Coord dot : fly.getFigure().dot)
            setBoxColor(fly.getCoord().x + dot.x, fly.getCoord().y + dot.y, color);
    }

    private void setBoxColor(int x, int y, int color) {
        if (x < 0 || x >= Config.WIDTH) return;
        if (y < 0 || y >= Config.HEIGHT) return;
        boxes[x][y].setColor(color);
    }

    public int getBoxColor(int x,int y){
        if (x < 0 || x >= Config.WIDTH) return -1;
        if (y < 0 || y >= Config.HEIGHT) return -1;
        return boxes[x][y].getColor();
    }

    private void moveFly(int sx, int sy){
        hideFigure();
        fly.moveFigure(sx,sy);
        showFigure();
    }

    private void turnFly(int direction){
        hideFigure();
        fly.turnFigure(direction);
        showFigure();
    }

    class KeyAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveFly(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveFly(+1, 0);
                    break;
                case KeyEvent.VK_UP:
                    turnFly(1);
                    break;
                case KeyEvent.VK_DOWN:
                    turnFly(2);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private void stripLiners(){
        for (int y = Config.HEIGHT - 1; y >= 0; y--)
            while (isFullLine(y))
                dropLine(y);
    }

    private void dropLine(int y ){
        for (int moveY = y - 1; moveY >= 0; moveY--)
            for (int x = 0; x < Config.WIDTH; x++)
                setBoxColor(x,moveY + 1,getBoxColor(x,moveY));
        for (int x = 0; x < Config.WIDTH; x++)
            setBoxColor(x,0,0);
    }

    private boolean isFullLine(int y){
        for (int x = 0; x < Config.WIDTH; x++)
            if (getBoxColor(x,y) != 2)
                return false;
        Counter += 10;
        label.setText(Integer.toString(Counter));
        return true;
    }

    class TimeAdapter implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            moveFly(0,1);
            if (fly.isLanded()){
                showFigure(2);
                stripLiners();
                addFigure();
            }
        }
    }
}
