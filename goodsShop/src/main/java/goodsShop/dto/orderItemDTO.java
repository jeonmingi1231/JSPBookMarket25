package goodsShop.dto;

public class orderItemDTO {
    private int orderItemId;
    private int orderId;
    private String iId;
    private String iName;
    private int iUnitPrice;
    private String iSize;
    private int iQuantity;
    private int itemTotal;

    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getIId() { return iId; }
    public void setIId(String iId) { this.iId = iId; }

    public String getIName() { return iName; }
    public void setIName(String iName) { this.iName = iName; }

    public int getIUnitPrice() { return iUnitPrice; }
    public void setIUnitPrice(int iUnitPrice) { this.iUnitPrice = iUnitPrice; }

    public String getISize() { return iSize; }
    public void setISize(String iSize) { this.iSize = iSize; }

    public int getIQuantity() { return iQuantity; }
    public void setIQuantity(int iQuantity) { this.iQuantity = iQuantity; }

    public int getItemTotal() { return itemTotal; }
    public void setItemTotal(int itemTotal) { this.itemTotal = itemTotal; }
}
