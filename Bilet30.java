import java.io.*;
import java.awt.*;
import java.net.*;

class Bilet30 extends Frame{
    String dir;
    URL fileURL;

    Toolkit tool;
    Image im;
    int ww,hh;
    Graphics graphics;

    Bilet30(){
        dir = System.getProperty("user.dir");
        File startFile = new File(dir + "/inputFile.txt");

        LoadStream(startFile);
    }

    public static void main(String args[]) {
        new Bilet30();
    }

    void LoadStream(File file){
        if(file.isFile()){
            try {
                fileURL = new URL("file:/" + file.getPath());
            } catch (MalformedURLException e) {
    
            }
    
            System.out.println(fileURL);
    
            try {
                if (fileURL != null) {
                    InputStream is = fileURL.openStream();
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
    
                    String line;
                    int[] x = new int[3], y = new int[3];
    
                    int lineNr = 0;
                    while ((line = dis.readLine()) != null) {              
                        if (line.contains("x=")) {
                            x[lineNr] =  Integer.parseInt(line.substring(line.lastIndexOf("x=") + 2, line.indexOf(",")));  
                        }
                        if (line.contains("y=")) {
                            y[lineNr] =  Integer.parseInt(line.substring(line.lastIndexOf("y=") + 2));  
                        }

                        lineNr++;
                    }

                    is.close();

                    DesenareTriunghi(x,y);
                }
    
            } catch (IOException e) {
    
            }
        }
        else{
            System.out.println("Invalid file");
        }
    }


    void DesenareTriunghi(int[] pointsX, int[] pointsY){
        tool = getToolkit();
        Dimension res = tool.getScreenSize();
        ww = res.width;
        hh = res.height;
        setResizable(false);
        setTitle("Triangle Drawer");
        setBackground(Color.gray);
        setLayout(null);

        resize(ww, hh);
        move(0, 0);
        setVisible(true);

        im = createImage(ww, hh);
        graphics = im.getGraphics();

        Desenare(pointsX, pointsY);
    }

    void Desenare(int[] pointsX, int[] pointsY){
        graphics.setColor(Color.red);
        int w = im.getWidth(this);
        int h = im.getHeight(this);
        
        //pt a avea reper cartezian
        graphics.drawLine(pointsX[0] + w/2, h/2 - pointsY[0], pointsX[1] + w/2, h/2 - pointsY[1]);
        graphics.drawLine(pointsX[1] + w/2, h/2 - pointsY[1], pointsX[2] + w/2, h/2 - pointsY[2]);
        graphics.drawLine(pointsX[2] + w/2, h/2 - pointsY[2], pointsX[0] + w/2, h/2 - pointsY[0]);


        //centru de greutate
        int gX = (int)((pointsX[0] + pointsX[1] + pointsX[2])/3.0);
        int gY = (int)((pointsY[0] + pointsY[1] + pointsY[2])/3.0);

        //punct de 6 px diametru
        graphics.fillOval(gX + ww/2 - 3, (hh/2 - gY) - 3, 3, 3);

        repaint();
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {
        g.drawImage(im, 0, 0, this);
    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            System.exit(0);
        }

        return false;
    }
}