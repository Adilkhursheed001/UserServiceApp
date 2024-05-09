package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Panel implements ActionListener, KeyListener {
    int width ;
    int height;

    public class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Timer timer;
    public Game(int height,int width){
        this.height = height;
        this.width = width;
        setBackground(Color.black);
        setPreferredSize(new Dimension(this.width,this.height));
         timer = new Timer(100,this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);


    }

    int tileSize = 25;
    Tile snake = new Tile(5,5);
    Tile food = new Tile(10,10);
    Boolean GameOver = false;

    ArrayList<Tile> snakeBody = new ArrayList<>();

    public void paint(Graphics g){
        super.paint(g);
        draw(g);
    }

    int snakeSpeedX = 0;
    int snakeSpeedY = 0;

    // collision logic
   public boolean collision(Tile tile1,Tile tile2){
       return tile1.x == tile2.x && tile1.y == tile2.y;
   }

   public void move(){
       if(collision(snake,food)){
           snakeBody.add(new Tile(food.x, food.y));
           placefood();
       }
       for (int i = snakeBody.size()-1; i >= 0; i--) {
           Tile new_snakePart = snakeBody.get(i);
           if (i == 0) { //right before the head
               new_snakePart.x = snake.x;
               new_snakePart.y = snake.y;
           }
           else {
               Tile prev_SnakePart = snakeBody.get(i-1);
               new_snakePart.x = prev_SnakePart.x;
               new_snakePart.y = prev_SnakePart.y;
           }
       }

       snake.x = snake.x + snakeSpeedX;
       snake.y = snake.y + snakeSpeedY;

       for(int i = 0;i < snakeBody.size();i++){
           Tile snakePart = snakeBody.get(i);
           if(collision(snakePart,snake)){
               GameOver = true;
           }
       }
       if(snake.x * tileSize > width || snake.x * tileSize < 0 || snake.y * tileSize > height
          || snake.y * tileSize < 0){
           GameOver = true;
       }
   }

   public void placefood(){
       Random random = new Random();
       food.x = random.nextInt(this.width/tileSize);
       food.y = random.nextInt(this.height/tileSize);
   }

    public void draw(Graphics g) {

//        for (int i = 0; i <= this.width / tileSize; i++) {
//            g.drawLine(i * tileSize, 0, i * tileSize, this.height);
//            g.drawLine(0, i * tileSize, this.width, i * tileSize);
//        }

        //painting Snake Position
        g.setColor(Color.green);
        g.fill3DRect(snake.x * tileSize, snake.y * tileSize, tileSize, tileSize,true);
        g.setColor(Color.WHITE);
        g.fillOval(snake.x * tileSize,snake.y * tileSize+5, tileSize - 13,tileSize - 13);
        g.setColor(Color.black);
        g.fillOval(snake.x * tileSize+2,snake.y * tileSize+6, tileSize - 17,tileSize - 17);
        g.setColor(Color.white);
        g.fillOval(snake.x * tileSize+12,snake.y * tileSize+5, tileSize - 13,tileSize - 13);
        g.setColor(Color.black);
        g.fillOval(snake.x * tileSize+14,snake.y * tileSize+6, tileSize - 17,tileSize - 17);

        //painting food position
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize,true);

        //painting snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snake = snakeBody.get(i);
            g.setColor(Color.green);
            g.fill3DRect(snake.x * tileSize, snake.y * tileSize, tileSize, tileSize,true);
        }

        //Score
        g.setFont(new Font("Aerial", Font.PLAIN, 20));
        if (GameOver) {
            g.setColor(Color.black);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setFont(new Font("Aerial",Font.BOLD,50));
            g.setColor(Color.red);
            g.drawString("GAME OVER: " + snakeBody.size(), width/5, height/2);

        } else {
            g.setColor(Color.green);
            g.drawString("Score - " + snakeBody.size(), tileSize, tileSize);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

       move();
       repaint();
       if(GameOver){
           timer.stop();
       }
    }


    @Override
    public void keyPressed(KeyEvent e) {
     if(e.getKeyCode() == KeyEvent.VK_UP && snakeSpeedY != 1){
         snakeSpeedY = -1;
         snakeSpeedX = 0;
     }
     else if(e.getKeyCode() == KeyEvent.VK_DOWN && snakeSpeedY != -1){
            snakeSpeedY = 1;
            snakeSpeedX = 0;
        }
     else if(e.getKeyCode() == KeyEvent.VK_LEFT && snakeSpeedX != 1){
         snakeSpeedY = 0;
         snakeSpeedX = -1;
     }
     else if(e.getKeyCode() == KeyEvent.VK_RIGHT && snakeSpeedX != -1){
         snakeSpeedY = 0;
         snakeSpeedX = 1;
     }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
