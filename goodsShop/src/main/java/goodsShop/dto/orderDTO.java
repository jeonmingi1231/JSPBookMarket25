package goodsShop.dto;

public class orderDTO {
    private int orderId;        // 주문 고유 번호 (PK)
    private String memberId;    // 회원 ID
    private String memberName;  // 회원 이름
    private int orderTotal;     // 전체 결제 금액
    private String orderDate;   // 주문 일시 (DATETIME)
    private String phone;       // 연락처
    private String address;     // 배송 주소
    private String memo;        // 배송 요청사항

    // Getter / Setter
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    private String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId +
               ", memberId=" + memberId +
               ", memberName=" + memberName +
               ", orderTotal=" + orderTotal +
               ", orderDate=" + orderDate +
               ", phone=" + phone +
               ", address=" + address +
               ", memo=" + memo +
               ", paymentMethod=" + paymentMethod + "]";
    }

}
