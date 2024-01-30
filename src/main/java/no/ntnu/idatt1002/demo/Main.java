package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.view.MyWindow;

/**
 * Use this class to start the application
 */
public class Main {

    /**
     * Main method for my application
     */
    public static void main(String[] args) throws Exception {
        MyWindow window = new MyWindow(Globals.APP_NAME);
        window.setVisible(true);
    }
}
