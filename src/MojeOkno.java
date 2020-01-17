
import java.awt.*;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MojeOkno extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
    PanelGraficzny panel;
    Dane dane = new Dane();
    Macierze macierze = new Macierze();
    JTextArea wspolXY = new JTextArea("", 3, 12);

    JScrollPane scrollPane = new JScrollPane( wspolXY,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

    JPanel przyciski = new JPanel(new GridLayout(20, 1));

    JButton wyczysc = new JButton("Clear All");
    JButton proste = new JButton("Connect points");
    JButton krzywa = new JButton("Draw curve [t]");
    JButton usunKrz = new JButton("Refresh");
    JButton trans = new JButton("Move [e, f]");
    JButton obrot = new JButton("Rotate [φ]");
    JButton skaluj = new JButton("Scale [a, d]");
    JButton zaznacz = new JButton("+");

    JTextField podajT = new JTextField("0.01");
    JTextField podajFi = new JTextField("");
    JTextField podajX = new JTextField("");
    JTextField podajY = new JTextField("");
    JPanel podajXY = new JPanel(new GridLayout(1, 2));
    JTextField podajA = new JTextField("");
    JTextField podajD = new JTextField("");
    JPanel podajAD = new JPanel(new GridLayout(1, 2));
    JTextField wzgX = new JTextField("");
    JTextField wzgY = new JTextField("");
    JPanel wzgXY = new JPanel(new GridLayout(1, 3));

    JLabel xy = new JLabel("");
    JLabel mac = new JLabel("Transformation matrixes:");
    JLabel wzgledem = new JLabel("Operacje względem [x, y]:");

    public MojeOkno() {
        //wywolanie konstruktora klasy nadrzednej (JFrame)
        super("Olek Marcin Bezier v0.3 beta");
        //ustawienie standardowej akcji po naciśnięciu przycisku zamkniecia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(740, 600);
        //setPreferredSize(new Dimension(870, 640));
        panel = new PanelGraficzny(600, 600);
        wspolXY.setEditable(false);
        scrollPane.setMaximumSize( new Dimension(10, 10));
        wspolXY.setLineWrap(true);
        przyciski.setPreferredSize(new Dimension(150, 1));
        //wspolXY.setMargin( new Insets(50,50,50,50) );
        podajX.setHorizontalAlignment(JTextField.CENTER);
        podajY.setHorizontalAlignment(JTextField.CENTER);
        podajA.setHorizontalAlignment(JTextField.CENTER);
        podajD.setHorizontalAlignment(JTextField.CENTER);
        podajT.setHorizontalAlignment(JTextField.CENTER);
        podajFi.setHorizontalAlignment(JTextField.CENTER);
        wzgX.setHorizontalAlignment(JTextField.CENTER);
        wzgY.setHorizontalAlignment(JTextField.CENTER);
        xy.setHorizontalAlignment(SwingConstants.CENTER);
        xy.setVerticalAlignment(SwingConstants.CENTER);
        mac.setHorizontalAlignment(SwingConstants.CENTER);
        mac.setVerticalAlignment(SwingConstants.CENTER);
        //wzgledem.setHorizontalAlignment(SwingConstants.CENTER);
        wzgledem.setVerticalAlignment(SwingConstants.CENTER);
        //rozmieszczenie elementow - menadzer rozkladu
        //FlowLayout ustawia elementy jeden za drugim
        //w tym przypadku dodatkowo wysrodkowane na ekranie, z odstępem w pionie i poziomie
        //setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));     
        //add(panel);
        podajXY.add(podajX);
        podajXY.add(podajY);
        podajAD.add(podajA);
        podajAD.add(podajD);
        wzgXY.add(wzgX);
        wzgXY.add(wzgY);
        wzgXY.add(zaznacz);
        przyciski.add(xy);
        przyciski.add(wyczysc);
        przyciski.add(proste);
        przyciski.add(krzywa);
        przyciski.add(podajT);
        przyciski.add(usunKrz);
        przyciski.add(mac);
        przyciski.add(trans);
        przyciski.add(podajXY);
        przyciski.add(obrot);
        przyciski.add(podajFi);
        przyciski.add(skaluj);
        przyciski.add(podajAD);
        przyciski.add(wzgledem);
        przyciski.add(wzgXY);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.EAST);
        add(przyciski, BorderLayout.WEST);

        //nasłuch zdarzeń
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        //dopasowanie rozmiaru okna do zawartości
        pack();
        //wysrodkowanie okna na ekranie
        setLocationRelativeTo(null);
        //wyswietlenie naszej ramki
        setVisible(true);
        nasluchuj();
    }

    public void nasluchuj(){
        proste.addActionListener(this);
        krzywa.addActionListener(this);
        wyczysc.addActionListener(this);
        usunKrz.addActionListener(this);
        trans.addActionListener(this);
        obrot.addActionListener(this);
        skaluj.addActionListener(this);
        zaznacz.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object zrodlo = e.getSource();
        if(zrodlo == proste){
            panel.rysujProste();
        }
        if(zrodlo == krzywa){
            //dane.bezier(0.02);
            panel.rysujKrzywa(Double.parseDouble(podajT.getText()));
            dane.pktBez = 0;
        }
        if(zrodlo == usunKrz){
            //dane.bezier(0.02);
            panel.usunKrzywa();
            wspolXY.setText("");
            for(int i=0; i<dane.getIlePunktow(); i++){
                wspolXY.append("  P{"+(i+1)+"} = ["+dane.getX(i)+";"+dane.getY(i)+"]\n");
            }
            //dane.pktBez = 0;
        }
        if(zrodlo == wyczysc){
            panel.wyczysc();
            wspolXY.setText(null);
            podajX.setText(null);
            podajY.setText(null);
            podajA.setText(null);
            podajD.setText(null);
            podajFi.setText(null);
            wzgX.setText(null);
            wzgY.setText(null);
        }
        if(zrodlo == trans){
            macierze.translacja(Integer.parseInt(podajX.getText()),
                    Integer.parseInt(podajY.getText()));
            panel.rysujPunktyTemp(Color.red);
            wspolXY.append("------------------------------\n");
            for(int i=0; i<dane.getIlePunktow(); i++){
                wspolXY.append("  P{"+(i+1)+"} = ["+dane.getX(i)+";"+dane.getY(i)+"]\n");
            }
            wspolXY.append("------------------------------\n");
            panel.flaga = true;
        }
        if(zrodlo == obrot){
            if(wzgX.getText().equals("") && wzgY.getText().equals(""))
                macierze.obrot(-Integer.parseInt(podajFi.getText()));

            //macierze.obrot(-Integer.parseInt(podajFi.getText()));
            panel.rysujPunktyTemp(Color.red);
            wspolXY.append("------------------------------\n");
            for(int i=0; i<dane.getIlePunktow(); i++){
                wspolXY.append("  P{"+(i+1)+"} = ["+dane.getX(i)+";"+dane.getY(i)+"]\n");
            }
            wspolXY.append("------------------------------\n");
            panel.flaga = true;
        }
        if(zrodlo == skaluj){
            if(wzgX.getText().equals("") && wzgY.getText().equals(""))
                macierze.skalowanie(Double.parseDouble(podajA.getText()),
                        Double.parseDouble(podajD.getText()));

            System.out.println(Double.parseDouble(podajA.getText()));
            System.out.println(Double.parseDouble(podajD.getText()));
            panel.rysujPunktyTemp(Color.red);
            wspolXY.append("------------------------------\n");
            for(int i=0; i<dane.getIlePunktow(); i++){
                wspolXY.append("  P{"+(i+1)+"} = ["+dane.getX(i)+";"+dane.getY(i)+"]\n");
            }
            wspolXY.append("------------------------------\n");
            panel.flaga = true;
        }
        if(zrodlo == zaznacz){
            panel.punktOdniesienia(Integer.parseInt(wzgX.getText()), Integer.parseInt(wzgY.getText()));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //panel.wyczysc();
    }

    //klik myszką na prawy panel
    @Override
    public void mousePressed(MouseEvent e) {
        //kliknięcie na prawy panel powoduje zaznaczenie tej pozycji
        wspolXY.setFont(wspolXY.getFont().deriveFont(Font.BOLD, wspolXY.getFont().getSize()));
        //wspolXY.setForeground(Color.RED);
        if(dane.getIlePunktow() < 99){
            dane.dodajPunkty(e.getX(), e.getY());
            wspolXY.append("  P{"+dane.getIlePunktow()+"} = ["+e.getX()+";"+e.getY()+"]\n");
            panel.zaznaczMiejsce(e.getX(),e.getY());
            pack();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xy.setText("X:"+e.getX() + "  " +"Y:"+ e.getY());
    }

}