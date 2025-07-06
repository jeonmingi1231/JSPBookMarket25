<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="goodsShop.dao.orderDAO, goodsShop.dto.orderDTO"%>
<%
    request.setCharacterEncoding("UTF-8");

    String orderIdStr = request.getParameter("orderId");

    // 오류 방지: orderId가 없을 경우
    if (orderIdStr == null || orderIdStr.trim().equals("")) {
        out.println("<script>alert('주문번호가 없습니다.'); history.back();</script>");
        return;
    }

    int orderId = Integer.parseInt(orderIdStr);

    orderDAO dao = new orderDAO();
    orderDTO dto = dao.getOrderById(orderId);
    dao.close();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>주문 완료</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center text-success">✅ 주문이 완료되었습니다!</h2>
        <div class="mt-4">
            <p>
                <strong><%=dto.getMemberName()%></strong> 님의 주문이 정상 처리되었습니다.
            </p>
            <ul class="list-group">
                <li class="list-group-item">🧾 주문번호: <strong><%=dto.getOrderId()%></strong></li>
                <li class="list-group-item">📦 배송지: <%=dto.getAddress()%></li>
                <li class="list-group-item">📱 연락처: <%=dto.getPhone()%></li>
                <li class="list-group-item">💬 요청사항: <%=dto.getMemo()%></li>
                <li class="list-group-item">💰 총 결제 금액: <strong><%=dto.getOrderTotal()%> 원</strong></li>
                <li class="list-group-item">💳 결제 방법: <strong><%= dto.getPaymentMethod() != null ? dto.getPaymentMethod() : "없음" %></strong></li>
            </ul>

            <!-- ✅ 주문 수정 버튼 -->
            <div class="text-center mt-3">
                <a href="updateOrder.jsp?orderId=<%=dto.getOrderId()%>" class="btn btn-warning">
                    ✏️ 주문 정보 수정
                </a>
            </div>
        </div>

        <div class="text-center mt-4">
            <a href="index.jsp" class="btn btn-primary">🏠 홈으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
