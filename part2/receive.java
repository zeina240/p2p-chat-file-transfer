package part2;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



public class receive implements Runnable {

	part2.Client c;
    boolean exit;
    public void stop()
    {
        exit = true;
    }

    receive(part2.Client aThis) {
        c = aThis;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            c.receive();
        }
    }
}


