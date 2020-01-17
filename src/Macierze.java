import static java.lang.Math.*;

public class Macierze
{
    private static double[][] jednostkowa = new double[3][3];
    private double[][] jednostkowaSt = new double[3][3];
    private double[] wektor = new double[3];
    Dane dane = new Dane();

    public Macierze(){
        defJedn();
        defWek();
    }

    void czysc(){
        defJedn();
    }

    public void defJedn(){
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                jednostkowa[i][j] = 0;

        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                jednostkowaSt[i][j] = 0;

        jednostkowa[0][0] = 1; jednostkowa[1][1] = 1; jednostkowa[2][2] = 1;
        jednostkowaSt[0][0] = 1; jednostkowaSt[1][1] = 1; jednostkowaSt[2][2] = 1;
    }

    void defWek(){
        wektor[0] = 0; wektor[1] = 0; wektor[2] = 1;
    }

    void translacja(int e, int f){
        int suma = 0;
        int[] wynik = {0,0,0};
        double przesuniecia[][] = new double[3][3];
        double finalna[][] = new double[3][3];

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                przesuniecia[i][j] = jednostkowaSt[i][j];
            }
        }

        przesuniecia[2][0] = e;
        przesuniecia[2][1] = f;

        for(int k=0; k<3; k++){
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    suma += przesuniecia[k][j]*jednostkowa[j][i];
                }
                finalna[k][i] = suma;
                suma = 0;
            }
        }
        suma = 0;
        for(int i=0; i<dane.getIlePunktow(); i++){
            wektor[0] = dane.getX(i);
            wektor[1] = dane.getY(i);
            ///

            for(int j=0; j<3; j++){
                suma = 0;
                for(int k=0; k<3; k++)
                    suma += wektor[k]*finalna[k][j];
                //System.out.print(suma+" ");
                wynik[j] = suma;
                //System.out.print(wynik[j]+" ");
            }

            dane.setXt(i,wynik[0]);
            dane.setYt(i,wynik[1]);
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                jednostkowa[i][j] = finalna[i][j];
            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(jednostkowa[i][j] + " ");
            }
            System.out.println();
        }
        defWek();
        System.out.print("");
    }

    void obrot(int fi){
        double obrotowa[][] = new double[3][3];
        double finalna[][] = new double[3][3];
        double suma = 0;

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                obrotowa[i][j] = jednostkowaSt[i][j];
            }
        }

        obrotowa[0][0] = Math.cos(Math.toRadians(fi));
        obrotowa[0][1] = Math.sin(Math.toRadians(fi));
        obrotowa[1][0] = -Math.sin(Math.toRadians(fi));
        obrotowa[1][1] = Math.cos(Math.toRadians(fi));

        for(int k=0; k<3; k++){
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    suma += obrotowa[k][j]*jednostkowa[j][i];
                }
                finalna[k][i] = suma;
                suma = 0;
            }
        }

        suma = 0;
        int[] wynik = {0,0,0};
        for(int i=0; i<dane.getIlePunktow(); i++){
            wektor[0] = dane.getX(i);
            wektor[1] = dane.getY(i);
            ///
            for(int j=0; j<3; j++){
                suma = 0;
                for(int k=0; k<3; k++)
                    suma += wektor[k]*finalna[k][j];
                //System.out.print(suma+" ");
                wynik[j] = (int)suma;
                //System.out.print(wynik[j]+" ");
            }

            dane.setXt(i,wynik[0]);
            dane.setYt(i,wynik[1]);
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                jednostkowa[i][j] = finalna[i][j];
            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(jednostkowa[i][j] + " ");
            }
            System.out.println();
        }
        //defJedn();
        defWek();
        System.out.print("");
    }


    void skalowanie(double a, double d){
        double skalowania[][] = new double[3][3];
        double finalna[][] = new double[3][3];
        double suma = 0;

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                skalowania[i][j] = jednostkowaSt[i][j];
            }
        }

        skalowania[0][0] = a;
        skalowania[1][1] = d;

        for(int k=0; k<3; k++){
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    suma += skalowania[k][j]*jednostkowa[j][i];
                }
                finalna[k][i] = suma;
                suma = 0;
            }
        }

        suma = 0;
        double[] wynik = {0,0,0};
        for(int i=0; i<dane.getIlePunktow(); i++){
            wektor[0] = dane.getX(i);
            wektor[1] = dane.getY(i);
            ///
            for(int j=0; j<3; j++){
                suma = 0;
                for(int k=0; k<3; k++)
                    suma += wektor[k]*finalna[k][j];
                //System.out.print(suma+" ");
                wynik[j] = suma;
                //System.out.print(wynik[j]+" ");
            }

            dane.setXt(i,(int)wynik[0]);
            dane.setYt(i,(int)wynik[1]);
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                jednostkowa[i][j] = finalna[i][j];
            }
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(jednostkowa[i][j] + " ");
            }
            System.out.println();
        }
        //defJedn();
        //defWek();
        System.out.print("");
    }
}
