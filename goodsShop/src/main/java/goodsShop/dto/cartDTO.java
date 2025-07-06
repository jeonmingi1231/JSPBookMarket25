package goodsShop.dto;

public class cartDTO {
    private int cartId;
    private String mId;
    private String mName;
    private String iId;
    private String iName;
    private int iUnitPrice;
    private String iCategory;
    private String iSize;
    private int iQuantity;
    private int cTotal;

    // getter/setter도 camelCase로 수정
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public String getMId() { return mId; }
    public void setMId(String mId) { this.mId = mId; }

    public String getMName() { return mName; }
    public void setMName(String mName) { this.mName = mName; }

    public String getIId() { return iId; }
    public void setIId(String iId) { this.iId = iId; }

    public String getIName() { return iName; }
    public void setIName(String iName) { this.iName = iName; }

    public int getIUnitPrice() { return iUnitPrice; }
    public void setIUnitPrice(int iUnitPrice) { this.iUnitPrice = iUnitPrice; }

    public String getICategory() { return iCategory; }
    public void setICategory(String iCategory) { this.iCategory = iCategory; }

    public String getISize() { return iSize; }
    public void setISize(String iSize) { this.iSize = iSize; }

    public int getIQuantity() { return iQuantity; }
    public void setIQuantity(int iQuantity) { this.iQuantity = iQuantity; }

    public int getCTotal() { return cTotal; }
    public void setCTotal(int cTotal) { this.cTotal = cTotal; }
}


