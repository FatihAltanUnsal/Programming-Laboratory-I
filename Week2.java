import java.util.Scanner;

public class Week2 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. Enter Data (Veri Girisi)
            System.out.print("Guzergahta kac durak var? ");
            int numStops = scanner.nextInt();

            System.out.print("Otobusun koltuk kapasitesini girin: ");
            int seatingCapacity = scanner.nextInt();
            scanner.nextLine();

            // Dizilerin (arrays) tanimlanmasi
            String[] stopNames = new String[numStops];
            int[] boarding = new int[numStops];
            int[] alighting = new int[numStops];
            int[] occupancy = new int[numStops];

            System.out.println("\n--- Durak Bilgilerini Girin ---");
            for (int i = 0; i < numStops; i++) {
                System.out.println("\nDurak " + (i + 1) + ":");
                System.out.print("  Durak adi: ");
                stopNames[i] = scanner.nextLine();

                System.out.print("  Binen yolcu sayisi: ");
                boarding[i] = scanner.nextInt();

                System.out.print("  Inen yolcu sayisi: ");
                alighting[i] = scanner.nextInt();
                scanner.nextLine();
            }

            // 2. Occupancy Calculation & 3. Capacity Check
            int currentPassengers = 0;
            int overCapacityCount = 0;

            System.out.println("\n--- Yolculuk Takibi ve Uyarilar ---");
            for (int i = 0; i < numStops; i++) {
                currentPassengers += (boarding[i] - alighting[i]);

                // Negatif yolcu kontrolu
                if (currentPassengers < 0) {
                    System.out.println("Data error at [" + stopNames[i] + "]: cannot have more passengers alighting than are currently on the bus. Occupancy set to 0.");
                    currentPassengers = 0;
                }

                occupancy[i] = currentPassengers;
                System.out.println("[" + stopNames[i] + "] duragindan sonra otobusteki yolcu sayisi: " + currentPassengers);

                // Kapasite asim kontrolu
                if (currentPassengers > seatingCapacity) {
                    System.out.println("Warning: Bus is over capacity at [" + stopNames[i] + "]!");
                    overCapacityCount++;
                }
            }

            // 1. Display All (Tum Duraklari Listeleme)
            System.out.println("\n============================================================");
            System.out.printf("%-18s | %-8s | %-8s | %-15s%n", "Durak Adi", "Binen", "Inen", "Mevcut Doluluk");
            System.out.println("------------------------------------------------------------");
            for (int i = 0; i < numStops; i++) {
                System.out.printf("%-18s | %-8d | %-8d | %-15d%n",
                        stopNames[i], boarding[i], alighting[i], occupancy[i]);
            }
            System.out.println("============================================================");

            // 2. Statistics (Istatistikler)
            System.out.println("\n--- Istatistikler ---");

            // En yogun durak (en cok binen yolcuya gore)
            int maxBoarding = boarding[0];
            int busiestIndex = 0;
            int totalOccupancy = 0;

            for (int i = 0; i < numStops; i++) {
                if (boarding[i] > maxBoarding) {
                    maxBoarding = boarding[i];
                    busiestIndex = i;
                }
                totalOccupancy += occupancy[i];
            }

            System.out.println("En yogun durak (en cok binis): " + stopNames[busiestIndex] + " (" + maxBoarding + " yolcu)");

            // Ortalama doluluk hesabi
            double avgOccupancy = (double) totalOccupancy / numStops;
            System.out.printf("Duraklar arasi ortalama doluluk: %.2f%n", avgOccupancy);

            // Kapasitenin asildigi durak sayisi
            System.out.println("Kapasitenin asildigi durak sayisi: " + overCapacityCount);

            // Son durak kontrolu
            int finalOccupancy = occupancy[numStops - 1];
            if (finalOccupancy != 0) {
                System.out.println("Warning: " + finalOccupancy + " passengers still on the bus after the final stop -- please check your data.");
            } else {
                System.out.println("Tum yolcular son durakta indi (Nihai doluluk: 0).");
            }
        }
    }
}
