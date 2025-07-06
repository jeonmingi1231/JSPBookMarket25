<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="goodsShop.dao.orderDAO, goodsShop.dto.orderDTO" %>
<%
    request.setCharacterEncoding("UTF-8");

    // 파라미터 수신
    int orderId = Integer.parseInt(request.getParameter("orderId"));
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String memo = request.getParameter("memo");
    String paymentMethod = request.getParameter("paymentMethod");

    // DTO 생성 및 값 설정
    orderDTO dto = new orderDTO();
    dto.setOrderId(orderId);
    dto.setPhone(phone);
    dto.setAddress(address);
    dto.setMemo(memo);
    dto.setPaymentMethod(paymentMethod);

    // DB 수정 처리
    orderDAO dao = new orderDAO();
    boolean success = dao.updateOrder(dto);
    dao.close();

    // 결과 처리
    if (success) {
        response.sendRedirect("orderItem.jsp?orderId=" + orderId);
    } else {
        out.println("<script>alert('수정 실패'); history.back();</script>");
    }
%>
