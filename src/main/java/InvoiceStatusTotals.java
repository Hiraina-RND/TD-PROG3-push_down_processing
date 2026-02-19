public class InvoiceStatusTotals {
    private Double total_paid;
    private Double total_confirmed;
    private Double total_draft;

    @Override
    public String toString() {
        return "InvoiceStatusTotals{" +
                "total_paid=" + total_paid +
                ", total_confirmed=" + total_confirmed +
                ", total_draft=" + total_draft +
                '}';
    }

    public InvoiceStatusTotals(Double total_paid, Double total_confirmed, Double total_draft) {
        this.total_paid = total_paid;
        this.total_confirmed = total_confirmed;
        this.total_draft = total_draft;
    }

    public InvoiceStatusTotals() {

    }

    public Double getTotal_paid() {
        return total_paid;
    }

    public Double getTotal_confirmed() {
        return total_confirmed;
    }

    public Double getTotal_draft() {
        return total_draft;
    }

    public void setTotal_paid(Double total_paid) {
        this.total_paid = total_paid;
    }

    public void setTotal_confirmed(Double total_confirmed) {
        this.total_confirmed = total_confirmed;
    }

    public void setTotal_draft(Double total_draft) {
        this.total_draft = total_draft;
    }
}
