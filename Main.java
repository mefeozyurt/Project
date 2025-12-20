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

                        if (commodityIndex != -1 && day >= 1 && day <= DAYS) {

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
            return "INVALID_MONTH";
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
        if (bestCommodityIndex == -1) {
            return "INVALID_MONTH";
        }

            return commodities[bestCommodityIndex] + " " + maxProfit;
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
        int commodityIndex = getCommodityIndex(commodity);
        if (commodityIndex == -1 || from < 1 || to > DAYS || from > to) {
            return -99999;
        }
        int totalProfit = 0;
        for (int month = 0; month < MONTHS; month++) {
            for (int day = from - 1; day < to; day++) {
                totalProfit += profitData[month][day][commodityIndex];
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) { 
        if (month < 0 || month >= MONTHS) {
            return -1;
        }

        long maxDailyProfit = Long.MIN_VALUE;
        int bestDay = -1;
        for (int day = 0; day < DAYS; day++) {
            long dailyTotal = 0;
            for (int commodity = 0; commodity < COMMS; commodity++) {
                dailyTotal += profitData[month][day][commodity];
            }
            if (dailyTotal > maxDailyProfit) {
                maxDailyProfit = dailyTotal;
                bestDay = day + 1;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) { 
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return "INVALID_COMMODITY";
        }

        long maxProfit = Long.MIN_VALUE;
        int bestMonthIndex = -1;
        for (int month = 0; month < MONTHS; month++) {
            long monthTotal = 0;
            for (int day = 0; day < DAYS; day++) {
                monthTotal += profitData[month][day][commodityIndex];
            }
            if (monthTotal > maxProfit) {
                maxProfit = monthTotal;
                bestMonthIndex = month;
            }
        }

        if (bestMonthIndex == -1) {
            return "INVALID_COMMODITY";
        }   return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) { 
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return -1;
        }

        int maxStreak = 0;
        int currentStreak = 0;

        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                int profit =  profitData[month][day][commodityIndex];

                if (profit < 0) {
                    currentStreak++;
                } else {
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0;
                }
            }
        }
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }

        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return -1;
        }

        int count = 0;
        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                if (profitData[month][day][commodityIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -99999;
        }

        int maxSwing = 0;

        for (int day = 0; day < DAYS - 1; day++) {
            long totalDay1 = 0;
            long totalDay2 = 0;

            for (int commodity = 0; commodity < COMMS; commodity++) {
                totalDay1 += profitData[month][day][commodity];
                totalDay2 += profitData[month][day + 1][commodity];
            }

            int swing = (int) Math.abs(totalDay2 - totalDay1);
            if (swing > maxSwing) {
                maxSwing = swing;
            }
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        int index1 = getCommodityIndex(c1);
        int index2 = getCommodityIndex(c2);

        if (index1 == -1 || index2 == -1) {
            return "INVALID_COMMODITY";
        }

        long totalProfit1 = 0;
        long totalProfit2 = 0;

        for (int month = 0; month < MONTHS; month++) {
            for (int day = 0; day < DAYS; day++) {
                totalProfit1 += profitData[month][day][index1];
                totalProfit2 += profitData[month][day][index2];
            }
        }
        if (totalProfit1 > totalProfit2) {
            return c1 + " is better by " + (totalProfit1 - totalProfit2);
        } else if (totalProfit1 < totalProfit2) {
            return c2 + " is better by " + (totalProfit2 - totalProfit1);
        } else  {
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) { 
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        long maxWeekProfit = Long.MIN_VALUE;
        int bestWeekProfit = -1;

        for (int week = 0; week < 4; week++) {
            long currentWeekProfit = 0;
            int startDay = week * 7;

            for (int i = 0;i < 7;i++) {
                int dayIndex = startDay + i;

                for (int commodity = 0; commodity < COMMS; commodity++) {
                    currentWeekProfit += profitData[month][dayIndex][commodity];
                }
            }

            if (currentWeekProfit > maxWeekProfit) {
                maxWeekProfit = currentWeekProfit;
                bestWeekProfit = week + 1;
            }
        }
        return "Week " + bestWeekProfit;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}