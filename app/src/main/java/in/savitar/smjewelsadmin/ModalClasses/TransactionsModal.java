package in.savitar.smjewelsadmin.ModalClasses;

public class TransactionsModal {

    private String Comments;
    private String Date;
    private String Amount;
    private String UserID;

    public TransactionsModal() {
    }

    public TransactionsModal(String comments, String date, String amount, String userID) {
        Comments = comments;
        Date = date;
        Amount = amount;
        UserID = userID;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
