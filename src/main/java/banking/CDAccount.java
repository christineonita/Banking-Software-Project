package banking;

public class CDAccount extends Account {
    private String cdApr;
    private String cdAmount;

    public CDAccount(String cdApr, String cdAmount) {
        super(Double.parseDouble(cdAmount), Double.parseDouble(cdApr), 0);
        this.cdApr = cdApr;
        this.cdAmount = cdAmount;
    }

    public String getCdApr() {
        return cdApr;
    }

    public String getCdInitialAmount() {
        return cdAmount;
    }
}