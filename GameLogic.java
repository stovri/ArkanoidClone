package com.github.grhscompsci2.java2DGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GameLogic {

  public GameLogic() {
    initComponents();
  }

  final GamePanel gp = new GamePanel(500, 500);

  private void initComponents() {

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Entity entity = new Entity("Player", 100, 100, 100, 100);

    gp.addEntity(entity);

    setGamePanelKeyBindings(gp, entity);

    frame.add(gp);

    frame.pack();
    frame.setVisible(true);

    // start the game loop which will repaint the screen
    runGameLoop();
  }
  // Starts a new thread and runs the game loop in it.

  public void runGameLoop() {
    Thread loop = new Thread(new Runnable() {
      @Override
      public void run() {
        gp.running = true;
        gp.gameLoop();
      }
    });
    loop.start();
  }

  private void setGamePanelKeyBindings(GamePanel gp, final Entity entity) {
    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "D pressed");
    gp.getActionMap().put("D pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.RIGHT = true;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "A pressed");
    gp.getActionMap().put("A pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.LEFT = true;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "W pressed");
    gp.getActionMap().put("W pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.UP = true;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "S pressed");
    gp.getActionMap().put("S pressed", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.DOWN = true;
      }
    });
    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "D released");
    gp.getActionMap().put("D released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.RIGHT = false;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "A released");
    gp.getActionMap().put("A released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.LEFT = false;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "W released");
    gp.getActionMap().put("W released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.UP = false;
      }
    });

    gp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "S released");
    gp.getActionMap().put("S released", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        entity.DOWN = false;
      }
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new GameLogic();
      }
    });
  }
}

class Entity extends JPanel {

  private int width = 50, height = 50;
  private int speed = 5;
  public boolean UP = false, DOWN = false, LEFT = false, RIGHT = false;

  public Entity(String text, int x, int y, int width, int height) {
    this.width = width;
    this.height = height;
    add(new JLabel(text));
    setBorder(new LineBorder(Color.BLACK));
    setBounds(x, y, width, height);
  }

  public void move() {
    if (UP) {
      setLocation(getX(), getY() - speed);
    }
    if (DOWN) {
      setLocation(getX(), getY() + speed);
    }
    if (LEFT) {
      setLocation(getX() - speed, getY());
    }
    if (RIGHT) {
      setLocation(getX() + speed, getY());
    }

  }

  @Override
  public void setBounds(int x, int y, int w, int h) {
    super.setBounds(x, y, width, height);
  }

  @Override
  protected void paintComponent(Graphics grphcs) {
    super.paintComponent(grphcs);
    grphcs.setColor(Color.CYAN);
    grphcs.fillRect(0, 0, getWidth(), getHeight());
  }
}

class GamePanel extends JPanel {

  private int width, height;
  private int frameCount = 0;
  private int fps = 0;
  public static boolean running = false, paused = false;
  final ArrayList<Entity> entities = new ArrayList<>();

  GamePanel(int w, int h) {
    setLayout(null);
    width = w;
    height = h;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  public void addEntity(Entity e) {
    add(e);
    entities.add(e);
  }

  @Override
  protected void paintComponent(Graphics grphcs) {
    super.paintComponent(grphcs);

    grphcs.setColor(Color.GREEN);
    grphcs.fillRect(0, 0, getWidth(), getHeight());

    grphcs.setColor(Color.BLACK);
    grphcs.drawString("FPS: " + fps, 5, 10);

    frameCount++;
  }

  // Only run this in another Thread!
  public void gameLoop() {
    // This value would probably be stored elsewhere.
    final double GAME_HERTZ = 30.0;
    // Calculate how many ns each frame should take for our target game hertz.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    // At the very most we will update the game this many times before a new render.
    // If you're worried about visual hitches more than perfect timing, set this to
    // 1.
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    // We will need the last update time.
    double lastUpdateTime = System.nanoTime();
    // Store the last time we rendered.
    double lastRenderTime = System.nanoTime();

    // If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

    // Simple way of finding FPS.
    int lastSecondTime = (int) (lastUpdateTime / 1000000000);

    while (running) {
      double now = System.nanoTime();
      int updateCount = 0;

      if (!paused) {
        // Do as many game updates as we need to, potentially playing catchup.
        while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
          updateGame();
          lastUpdateTime += TIME_BETWEEN_UPDATES;
          updateCount++;
        }

        // If for some reason an update takes forever, we don't want to do an insane
        // number of catchups.
        // If you were doing some sort of game that needed to keep EXACT time, you would
        // get rid of this.
        if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
          lastUpdateTime = now - TIME_BETWEEN_UPDATES;
        }

        drawGame();
        lastRenderTime = now;

        // Update the frames we got.
        int thisSecond = (int) (lastUpdateTime / 1000000000);
        if (thisSecond > lastSecondTime) {
          System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
          fps = frameCount;
          frameCount = 0;
          lastSecondTime = thisSecond;
        }

        // Yield until it has been at least the target time between renders. This saves
        // the CPU from hogging.
        while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
          // allow the threading system to play threads that are waiting to run.
          Thread.yield();

          // This stops the app from consuming all your CPU. It makes this slightly less
          // accurate, but is worth it.
          // You can remove this line and it will still work (better), your CPU just
          // climbs on certain OSes.
          // FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a
          // look at different peoples' solutions to this.
          // On my OS it does not unpuase the game if i take this away
          try {
            Thread.sleep(1);
          } catch (Exception e) {
          }

          now = System.nanoTime();
        }
      }
    }
  }

  private void updateGame() {
    for (Entity e : entities) {
      e.move();
    }
  }

  private void drawGame() {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        repaint();
      }
    });
  }
}