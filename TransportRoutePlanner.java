import java.util.Random;

public class TransportRoutePlanner {
    // Mahalle isimleri
    private static final String[] MAHALLELER = {"Merkez", "Sanayi", "Üniversite"};
    
    // Sentetik veri üretimi için rastgele sayı üretici
    private static Random random = new Random();
    
    public static void main(String[] args) {
        double[][] kriterler = new double[3][5]; // 3 mahalle, 5 kriter
        
        // Sentetik veri üretimi (örnek değer aralıkları)
        for (int i = 0; i < 3; i++) {
            kriterler[i][0] = random.nextInt(1000) + 500;   // Nüfus yoğunluğu (500-1500)
            kriterler[i][1] = random.nextInt(10) + 1;       // Mevcut ulaşım altyapısı (1-10)
            kriterler[i][2] = random.nextInt(500) + 100;    // Maliyet analizi (100-600)
            kriterler[i][3] = random.nextInt(100) + 10;     // Çevresel etki (10-110)
            kriterler[i][4] = random.nextInt(50) + 5;       // Sosyal fayda (5-55)
        }
        
        
        // Softmax algoritması ile kriterlerin ağırlıklarını hesapla
        double[][] agirliklar = applySoftmax(kriterler);
        
        // En uygun mahalleyi seç
        int enUygunMahalle = selectBestNeighborhood(agirliklar);
        
        // Sonuçları yazdır
        printResults(kriterler, agirliklar, enUygunMahalle);
    }
    
    // Softmax algoritması
    private static double[][] applySoftmax(double[][] kriterler) {
        double[][] agirliklar = new double[3][5];
        
        for (int j = 0; j < 5; j++) { // 5 kriter
            double sumExp = 0;
            double[] expValues = new double[3];
            
            for (int i = 0; i < 3; i++) {
                expValues[i] = Math.exp(kriterler[i][j]);
                sumExp += expValues[i];
            }
            
            for (int i = 0; i < 3; i++) {
                agirliklar[i][j] = expValues[i] / sumExp;
            }
        }
        
        return agirliklar;
    }
    
    // En uygun mahalleyi seçme (Toplam ağırlığı en yüksek olan mahalleyi seç)
    private static int selectBestNeighborhood(double[][] agirliklar) {
        double[] toplamAgirlik = new double[3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                toplamAgirlik[i] += agirliklar[i][j];
            }
        }
        
        int enUygunIndex = 0;
        double maxAgirlik = toplamAgirlik[0];
        
        for (int i = 1; i < 3; i++) {
            if (toplamAgirlik[i] > maxAgirlik) {
                maxAgirlik = toplamAgirlik[i];
                enUygunIndex = i;
            }
        }
        
        return enUygunIndex;
    }
    
    // Sonuçları ekrana yazdırma
    private static void printResults(double[][] kriterler, double[][] agirliklar, int enUygunMahalle) {
        System.out.println("Mahalle Kriterleri ve Ağırlıkları:");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-12s %-12s %-12s %-12s %-12s %-12s\n", "Mahalle", "Nüfus", "Ulaşım", "Maliyet", "Çevresel", "Sosyal");
        
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-12s", MAHALLELER[i]);
            for (int j = 0; j < 5; j++) {
                System.out.printf("%-12.2f", kriterler[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\nSoftmax Ağırlıkları:");
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-12s", MAHALLELER[i]);
            for (int j = 0; j < 5; j++) {
                System.out.printf("%-12.4f", agirliklar[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\nEn uygun mahalle: " + MAHALLELER[enUygunMahalle]);
    }
}

