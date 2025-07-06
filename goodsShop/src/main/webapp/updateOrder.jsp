<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="goodsShop.dao.orderDAO, goodsShop.dto.orderDTO" %>
<%
    request.setCharacterEncoding("UTF-8");
    String orderIdStr = request.getParameter("orderId");
    int orderId = Integer.parseInt(orderIdStr);

    orderDAO dao = new orderDAO();
    orderDTO dto = dao.getOrderById(orderId);  // 기존 주문 정보 조회
    dao.close();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>주문 정보 수정</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center text-warning">✏️ 주문 정보 수정</h2>
        <form method="post" action="updateOrderProc.jsp">
            <input type="hidden" name="orderId" value="<%= dto.getOrderId() %>">

            <div class="mb-3">
                <label class="form-label">연락처</label>
                <input type="text" name="phone" class="form-control" value="<%= dto.getPhone() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">주소</label>
                <input type="text" name="address" class="form-control" value="<%= dto.getAddress() %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">배송 요청사항</label>
                <textarea name="memo" class="form-control" rows="3"><%= dto.getMemo() %></textarea>
            </div>

            <div class="mb-3">
                <label class="form-label">결제 방법</label>
                <input type="text" name="paymentMethod" class="form-control" value="<%= dto.getPaymentMethod() %>" required>
            </div>

            <div class="text-center">
                <button type="submit" class="btn btn-primary">✅ 수정 완료</button>
                <a href="orderItem.jsp?orderId=<%=dto.getOrderId()%>" class="btn btn-secondary">← 돌아가기</a>
            </div>
        </form>
    </div>
</body>
</html>
