import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        ArrayList<Fils> fils = new ArrayList<>();
        for (int i = 1; i <= 5; i++) fils.add(new Fils("fill: " + i, i));
        Fils.hello();

        for(int i = 0; i < 5; i++){
            Thread s = new Thread(fils.get(i));
            s.start();
        }

    }
}

class Fils implements Runnable{
    int id;
    String name;
    Boolean rightFork = false;
    Boolean leftFork = false;
    int leftId = 0;

    public static ArrayList<Boolean> forks = new ArrayList<>(6);
    public static void hello(){
        forks.add(false);
    }

    public Fils(String name, int id){
        forks.add(false);
        this.id = id;
        this.name = name;
    }

    String think(){
        if(!leftFork || !rightFork) {
            return this.name + " thinking!";
        }else{
            return null;
        }
    }

    String eat(){
        synchronized (forks.getClass()) {
            if (leftFork && rightFork) {
                forks.set(id - 1, false);
                forks.set(id, false);
                leftFork = false;
                rightFork = false;
                System.out.println(forks);
                return this.name + " eating!";
            } else {
                return this.name + " can't eating!";
            }
        }
    }

    String giveRight(){
        synchronized (forks.getClass()) {
            if (!rightFork) {
                forks.set(id, true);
                rightFork = true;
                System.out.println(forks);
                return this.name + " give right fork!";
            } else {
                return null;
            }
        }
    }

    String giveLeft(){
        synchronized (forks.getClass()) {
            if (!leftFork) {
                forks.set(id - 1, true);
                leftFork = true;
                System.out.println(forks);
                return this.name + " give left fork!";
            } else {
                return null;
            }
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println(think());
                Thread.sleep(800);
                System.out.println(giveRight());
                Thread.sleep(800);
                System.out.println(giveLeft());
                Thread.sleep(800);
                System.out.println(eat());
                Thread.sleep(800);
            }catch (InterruptedException e){
                continue;
            }
        }
    }
}