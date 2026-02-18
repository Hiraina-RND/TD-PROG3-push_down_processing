public class InvoiceTotal {
    private Integer id;
    private StatusEnum status;
    private String customer_name;
    private Double total;

    public InvoiceTotal(Integer id, StatusEnum status, String customer_name, Double total) {
        this.id = id;
        this.status = status;
        this.customer_name = customer_name;
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvoiceTotal{" +
                "id=" + id +
                ", status=" + status +
                ", customer_name='" + customer_name + '\'' +
                ", total=" + total +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public Double getTotal() {
        return total;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
