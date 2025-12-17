// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    static int[][][] profitData = new int[MONTHS][DAYS][COMMS];

    private static int getCommodityIndex(String commodity) {
        for (int i = 0; i < commodities.length; i++) {
            if (commodity.equals(commodities[i])) {
                return i;
            }
        }
        return -1;
    }


    public static void loadData() {
        for (int month = 0; month < MONTHS; month++) {
            String FileName = "Data_Files/" + months[month] + ".txt";
            File file = new File(FileName);

            try {
                Scanner sc = new Scanner(file);

                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");

                    if (parts.length >= 3) {
                        int day = Integer.parseInt(parts[0].trim());
                        String commodityName = parts[1].trim();
                        int profit = Integer.parseInt(parts[2].trim());

                        int commodityIndex = getCommodityIndex(commodityName);

                        if (commodityIndex != -1 && day >= -1 && day <= DAYS) {

                            profitData[month][day - 1][commodityIndex] = profit;
                        }
                    }
                }
                sc.close();
            } catch (FileNotFoundException e) {
            } catch (NumberFormatException e) {

            }
        }
    }


    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "Invalid month!";
        }

        long maxProfit = Long.MIN_VALUE;
        int bestCommodityIndex = -1;

        for (int commodityIndex = 0; commodityIndex < COMMS; commodityIndex++) {
            long currentProfit = 0;
            for (int day = 0; day < DAYS; day++) {
                currentProfit += profitData[month][day][commodityIndex];
            }
            if (currentProfit > maxProfit) {
                maxProfit = currentProfit;
                bestCommodityIndex = commodityIndex;
            }
        }
        if (bestCommodityIndex == -1) return "Invalid month!";

            return commodities[bestCommodityIndex] + maxProfit;
        }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }
        int totalProfit = 0;
        for (int commodityIndex = 0; commodityIndex < COMMS; commodityIndex++) {
            totalProfit += profitData[month][day-1][commodityIndex];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}