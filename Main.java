package pkg8vezirproblemi;

import java.util.Random;

public class Main {
    private int matris[][];   //Sınıf elemanları
    private Random rand;    //tanımları
    public Main() {
        matris = new int[8][8]; //Yapıcı matrisi oluşturuyor
        rand = new Random();    //Random elemanı değer üretmek için hazır
    }
    public void create() {      //Tüm karelere sıfır yerleştiriliyor
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                matris[i][j] = 0;   
            }
        }
        for(int j=0;j<8;j++){   // Her sütunun rastgele satırına vezirler bırakılıyor.
            int i = rand.nextInt(8);
            matris[i][j] = 1;
        }
    }
    public void matrisYaz() {   //Matrisi ekranda gösterme fonksiyonu
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                System.out.print("["+matris[i][j]+"]");
            }System.out.println("");
        }
    }
    public int test() { //Matris üzerindeki tüm 1 (vezir) 'lerin tehdit değerlerinin toplamı hesaplanıyor.
        int cakisma = 0;
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(matris[i][j] == 1)
                cakisma+=tehditSay(i, j);   
                //Fonksiyona vezirlerin parametleri yollanıp tehdit değerleri alınıyor.
            }
        }
        
        return cakisma;
    }
    public void generate() {
        int [][] matrisYedek = new int [8][8];
        matrisKopyala(matrisYedek, matris);     //Başlangıçta matris yedeği alınıyor.
        int oncekiTestDegeri = this.test();     //ve test değeri alınıyor. Kıyaslama için...
        do {
            matrisKopyala(matris, matrisYedek);     //Döngü içerisinde matris sürekli değişecektir.
        int j = rand.nextInt(8);                    //Fakat kıyaslama ilk matris durumuyla yapılacağından
        int i = 0;                                  //Döngü girişinde ilk matris yedekten alınıyor.
        while(matris[i][j] == 0) {  //Herhangi bir vezir seçiliyor.
            i++;
        }
        matris[i][j] = 0;   // Vezirin konumu boşaltılıyor.
        i = rand.nextInt(8);    
        matris[i][j] = 1;   //ve o sütunda kalmak şartıyla yeniden rastgele konumlandırılıyor.
        }while(this.test() > oncekiTestDegeri-1);   
        //Tehdit sayısının -ilk duruma göre- düşmesi şartı için zorlanıyor.
    }
    
    public int tehditSay(int i,int j) {
        int x,y;
        int cakisma = 0;
        for(int a=0;a<8;a++) {
            if(matris[a][j] == 1) cakisma++;    //Düşey konumda tehdit kontrol
            if(matris[i][a] == 1) cakisma++;    //Yatay konumda tehdit kontrol
        }
        x = i;y = j;
        while (x>=0 && y>=0) {  //Sol üst çapraz kontrolü
            if(matris[x][y] == 1) cakisma++;
            x--;y--;
        }
        x = i;y = j;
        while (x>=0 && y<8) {   //Sol alt çapraz kontrolü
            if(matris[x][y] == 1) cakisma++;
            x--;y++;
        }
        x = i;y = j;
        while (x<8 && y>=0) {   //Sağ üst çapraz kontrolü
            //matris[x][y] = 2;
            if(matris[x][y] == 1) cakisma++;
            x++;y--;
        }
        x = i;y = j;
        while (x<8 && y<8) {    //Sağ alt çapraz kontrolü
            //matris[x][y] = 2;
            if(matris[x][y] == 1) cakisma++;
            x++;y++;
        }
        //matris[i][j] = 1
        //Kendi konumundaki 1 den dolayı cakisma sayısı 6 defa fazla sayılıyor.
        return cakisma-6;
    }
    public void vezirKoy(int i,int j) {
        matris[i][j] = 1;
    }
    public static void main(String[] args) {
        Main nesne = new Main();
        nesne.create();
        nesne.matrisYaz();
        System.out.println("Tehdit Sayısı: " + nesne.test());
        
        nesne.generate();
        nesne.matrisYaz();
        System.out.println("Generate sonrasi tehdit sayısı: "+nesne.test());
    }

    private void matrisKopyala(int[][] yeniMatris, int[][] eskiMatris) {
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                yeniMatris[i][j] = eskiMatris[i][j];
            }
        }
    }
    
}
