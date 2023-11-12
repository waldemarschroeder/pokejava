import java.util.Random;
import java.util.stream.DoubleStream;
import java.lang.Math;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

class Position {
    int x;
    int y;

    Position (int x, int y) {
        this.x = x;
        this.y = y;
    }

    void randomPos (int min, int max) {
        if (min > max){
            System.out.println("Error: min cannot be > max");
        }
        else {
            Random randx = new Random();
            // nextInt as provided by Random is exclusive of the top value so you need to add 1 
            this.x = randx.nextInt((max - min) + 0) + min;
            Random randy = new Random();
            this.y = randy.nextInt((max - min) + 0) + min;
        }  
    }
}

class Trainer {
    Position p;

    public Trainer (Position p) {
        this.p = p;
    }

    void moving (Map map1, Position targetPos) {
        if (targetPos.x >= 0 && targetPos.y >= 0 && targetPos.x < map1.sizeX && targetPos.y < map1.sizeY) {
            if (map1.matrix [targetPos.y][targetPos.x].isAccessable) {
                map1.rmTrainerInMap(p);
                this.p = targetPos;
                map1.mvTrainerInMap(p);
            } else {System.out.println("\nBLOCKED moving");}
        } else {System.out.println("\nBLOCKED moving");}
        map1.printMap();
        if (map1.matrix [p.y][p.x].symbol.contains("P")) {
            Battle.main(null);
            map1.printMap();
        }
    }
    void moveLeft (Map map1) {
        Position targetPos = new Position(p.x-1,p.y);
        this.moving(map1,targetPos);
    }
    void moveRight (Map map1) {
        Position targetPos = new Position(p.x+1,p.y);
        this.moving(map1,targetPos);
    }
    void moveUp (Map map1) {
        Position targetPos = new Position(p.x,p.y-1);
        this.moving(map1,targetPos);
    }
    void moveDown (Map map1) {
        Position targetPos = new Position(p.x,p.y+1);
        this.moving(map1,targetPos);
    }
}

class Field {
    //Position position;
    Random random = new Random();
    String type;
    boolean isTherePoke = false;
    boolean isAccessable = true;
    boolean isThereTrainer = false;
    String symbol;

    // possible field types
    public static String[] types = {"street","grass", "blocked"};
    double[] probs = {25, 40, 20}; // 25% Street, 40% Grass, 20% Blocked
    double totalProbs = DoubleStream.of(probs).sum();
    int index = random.nextInt(types.length);
    public String randomType() {
        double x = random.nextDouble() * totalProbs;
        for (int i = 0; i < probs.length; ++i) {
            x -= probs[i];
            if (x <= 0) {
                return types[i];
            }
        }
        return types[types.length - 1];
    }

    public Field () {
        //this.position = p;
        this.type = randomType();
        
        if (this.type == "street") {
            this.symbol = "S ";
        }
        if (this.type == "grass") {
            this.symbol = "G";
            if (random.nextFloat() < 0.3) {
                this.isTherePoke = true;
                this.symbol += "P";
            } else {
                this.isTherePoke = false;
                this.symbol += " ";
            }
        }
        if (this.type == "blocked") {
            this.symbol = "B ";
            this.isAccessable = false;
        }
        if (isThereTrainer) {
            this.symbol += "T";
        } else {this.symbol += " ";}

    }

    void updateField (boolean isThereTrainer) {
        this.isThereTrainer = isThereTrainer;
        this.symbol = this.symbol.substring(0, this.symbol.length() - 1);
        if (isThereTrainer) {
            this.symbol += "T";
        } else {this.symbol += " ";}
    }

}

class Map {
    int sizeX;
    int sizeY;
    Field[][] matrix;

    public Map (int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        matrix = new Field[sizeY][sizeX];
        // Remember that everything in the matrix is initialized to null so
        // you must initialize everything
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Field();
            }
        }
    }

    void printMap () {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j].symbol + " ");
            }
            System.out.println();
        }
    }

    void rmTrainerInMap (Position trainerPos) {
        this.matrix[trainerPos.y][trainerPos.x].updateField(false);
    }
    void mvTrainerInMap (Position trainerPos) {
        this.matrix[trainerPos.y][trainerPos.x].updateField(true);
    }

}

public class World {
    public static void main(String[] args) {
        Map route1 = new Map(10, 5);
        route1.printMap();
        Position userPos = new Position(0, 0);
        userPos.randomPos(0, Math.min(route1.sizeX, route1.sizeY));
        while (!route1.matrix[userPos.y][userPos.x].isAccessable) {
            userPos.randomPos(0, Math.min(route1.sizeX, route1.sizeY));
        }
        Trainer user = new Trainer(userPos);
        route1.mvTrainerInMap(user.p);
        route1.printMap();
        
        // running through map
        JFrame myJFrame = new JFrame();
        myJFrame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) { user.moveUp(route1);}
                else if (keyCode == KeyEvent.VK_DOWN) { user.moveDown(route1);}
                else if (keyCode == KeyEvent.VK_LEFT) { user.moveLeft(route1);}
                else if (keyCode == KeyEvent.VK_RIGHT) { user.moveRight(route1);}
            }
        });
        myJFrame.setVisible(true);
    }
}