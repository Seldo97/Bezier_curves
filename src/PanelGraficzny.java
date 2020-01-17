import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.applet.Applet;

public class PanelGraficzny extends JPanel
{
    //obiekt do przechowywania grafiki
    BufferedImage plotno;
    Dane dane;
    Graphics2D g; // (Graphics2D) plotno.getGraphics();
    Graphics2D g2;
    Macierze M;
    public static boolean flaga = false;
    public static boolean flagaM = false;
    public PanelGraficzny(int szer, int wys)
    {
        super();
        dane = new Dane();
        ustawRozmiar(new Dimension(szer,wys));
        wyczysc();
        //pack();
    }

    public void ustawRozmiar(Dimension r)
    {
        //przygotowanie płótna
        plotno = new BufferedImage((int)r.getWidth(), (int)r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
    }

    public void wyczysc()
    {
        //wyrysowanie białego tła

        dane.wyczysc();
        //M.czysc();
        g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.black));
        rysujSrodek();
        repaint();
        flaga = false;
        flagaM = false;
    }

    public void rysujSrodek(){
        g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.lightGray);

        g.drawLine(0, 300, 600, 300);
        g.drawLine(300, 0, 300, 600);
        g.drawString("y", 305, 11);
        g.drawString("x", 587, 310);

        repaint();
    }

    public void usunKrzywa(){
        //M.defJedn();
        g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.black));
        repaint();
        rysujSrodek();

        for(int i=0; i<dane.getIlePunktow(); i++){
            dane.wspolX[i] = dane.tempX[i];
            dane.wspolY[i] = dane.tempY[i];
        }

        rysujPunkty(Color.black);

        flaga = false;
        flagaM = false;
    }

    public void rysujPunkty(Color color){
        g.setColor(color);
        for(int i=0; i<dane.getIlePunktow(); i++){
            Rectangle2D rectangle = new Rectangle2D.Double(dane.getX(i), dane.getY(i), 3, 3);
            g.fill(rectangle);
            g.draw(rectangle);
            g.drawString("P{"+(i+1)+"}", dane.getX(i)+10, dane.getY(i)+10);
            repaint();
        }
    }

    public void rysujPunktyTemp(Color color){
        g.setColor(color);
        flagaM = true;
        for(int i=0; i<dane.getIlePunktow(); i++){
            Rectangle2D rectangle = new Rectangle2D.Double(dane.getXt(i), dane.getYt(i), 3, 3);
            g.fill(rectangle);
            g.draw(rectangle);
            g.drawString("P{"+(i+1)+"}", dane.getXt(i)+10, dane.getYt(i)+10);
            repaint();
        }
    }

    public void zaznaczMiejsce(int x, int y)
    {
        g = (Graphics2D) plotno.getGraphics();

        g.setColor(Color.black);
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, 3, 3);
        g.fill(rectangle);
        g.draw(rectangle);
        g.drawString("P{"+dane.getIlePunktow()+"}", x+10, y+10);
        repaint();
    }

    public void punktOdniesienia(int x, int y){
        g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.green);
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, 5, 5);
        g.fill(rectangle);
        g.draw(rectangle);
        repaint();
    }

    public void rysujProste(){
        //JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
        //System.out.println("twoj stary");

        //g = (Graphics2D) plotno.getGraphics();
        if(!flaga)
            g.setColor(Color.black);
        else
            g.setColor(Color.red);

        float[] dashingPattern2 = {3f, 6f};
        Stroke stroke2 = new BasicStroke(1.2f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1.0f, dashingPattern2, 0.0f);

        g.setStroke(stroke2);

        if(!flagaM)
            for(int i = 0; i<dane.getIlePunktow()-1; i++)
                g.drawLine(dane.getX(i), dane.getY(i), dane.getX(i+1), dane.getY(i+1));
        else
            for(int i = 0; i<dane.getIlePunktow()-1; i++)
                g.drawLine(dane.getXt(i), dane.getYt(i), dane.getXt(i+1), dane.getYt(i+1));

        repaint();

    }

    public void rysujKrzywa(double t){
        //JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
        //System.out.println("twoj stary");

        dane.bezier(t);
        g = (Graphics2D) plotno.getGraphics();
        if(!flaga)
            g.setColor(Color.blue);
        else
            g.setColor(Color.magenta);

        for(int i = 0; i<dane.getIlePunktowKrzywej()-1; i++)
            g.drawLine(dane.getbX(i), dane.getbY(i), dane.getbX(i+1), dane.getbY(i+1));

        repaint();
    }

    //przesłonięta metoda paintComponent z klasy JPanel do rysowania
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu 
        g2d.drawImage(plotno, 0, 0, this);
    }
}