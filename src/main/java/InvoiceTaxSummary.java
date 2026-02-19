public class InvoiceTaxSummary {
    private Integer invoice_id;
    private Double HT;
    private Double TVA;
    private Double TTC;

    @Override
    public String toString() {
        return "InvoiceTaxSummary{" +
                "invoice_id=" + invoice_id +
                ", HT=" + HT +
                ", TVA=" + TVA +
                ", TTC=" + TTC +
                '}';
    }

    public InvoiceTaxSummary(Integer invoice_id, Double HT, Double TVA, Double TTC) {
        this.invoice_id = invoice_id;
        this.HT = HT;
        this.TVA = TVA;
        this.TTC = TTC;
    }
}
