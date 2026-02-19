public class Main {
    static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        System.out.println(dataRetriever.findInvoiceTotals());
        System.out.println("=====================");
        System.out.println(dataRetriever.findConfirmedAndPaidInvoiceTotals());
        System.out.println("=====================");
        System.out.println(dataRetriever.computeStatusTotals());
        System.out.println("=====================");
        System.out.println(dataRetriever.computeWeightedTurnover());
        System.out.println("=====================");
        System.out.println(dataRetriever.findInvoiceTaxSummaries());
        System.out.println("=====================");
        System.out.println(dataRetriever.computeWeightedTurnoverTtc());
    }
}
