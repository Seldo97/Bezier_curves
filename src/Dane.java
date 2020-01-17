import static java.lang.Math.*;

public class Dane
{
    public static int wspolX[] = new int[100];
    public static int wspolY[] = new int[100];
    public static int tempX[] = new int[100];
    public static int tempY[] = new int[100];
    public static int bezierX[] = new int[1000];
    public static int bezierY[] = new int[1000];
    private static int punkty = 0;
    public static int pktBez = 0;
    PanelGraficzny panel;
    //private int n;

    public Dane(){}

    public void dodajPunktyKrzywej(int x, int y){
        bezierX[pktBez] = x;
        bezierY[pktBez] = y;

        pktBez++;
    }

    public void dodajPunkty(int x, int y){
        wspolX[punkty] = x;
        wspolY[punkty] = y;

        punkty++;
    }

    public void wyczysc(){
        if(punkty > 0){
            //for(int i = 0; i < punkty; i++)
            //    wspolY = wspolX = null;

            punkty = 0;
            pktBez = 0;
        }
    }

    public void bezier(double u){
        int n = punkty - 1;
        int m = n; //stopien wielomianu

        for(double t=0; t <= 1; t+=u){
            double x=0, y=0;
            for(int i=0; i<=n; i++){
                if(!panel.flagaM){
                    x += berenstein(m ,i , t)*(double)getX(i);
                    y += berenstein(m ,i , t)*(double)getY(i);
                }else{
                    x += berenstein(m ,i , t)*(double)getXt(i);
                    y += berenstein(m ,i , t)*(double)getYt(i);
                }

            }
            //System.out.println((int)x+ " " + (int) y);
            dodajPunktyKrzywej((int) x, (int) y);
        }

    }

    public double berenstein(int n, int i, double t){
        double wynikB = 0;
        wynikB = symbolNewtona(n, i) * Math.pow(1-t, n-i) * Math.pow(t, i);

        return wynikB;
    }

    public double symbolNewtona(int n, int k)
    {
        double wynik = 1;
        for(int i=1; i<=k; i++)
            wynik = wynik * (n-i+1)/i;

        return wynik;
    }

    public void setXt(int i, int p){ tempX[i] = p;}

    public void setYt(int i, int p){ tempY[i] = p;}

    public int getXt(int i){ return tempX[i];}

    public int getYt(int i){ return tempY[i];}

    public void setX(int i, int p){ wspolX[i] = p;}

    public void setY(int i, int p){ wspolY[i] = p;}

    public int getX(int i){ return wspolX[i];}

    public int getY(int i){ return wspolY[i];}

    public int getbX(int i){ return bezierX[i];}

    public int getbY(int i){ return bezierY[i];}

    public int getIlePunktow(){ return punkty;}

    public int getIlePunktowKrzywej(){ return pktBez;}
}
