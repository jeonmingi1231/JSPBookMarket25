<%@ page import="goodsShop.dao.cartDAO" %>
<%
    request.setCharacterEncoding("UTF-8");
    String cartIdStr = request.getParameter("cartId");
    if (cartIdStr != null && !cartIdStr.equals("")) {
        int cartId = Integer.parseInt(cartIdStr);
        cartDAO dao = new cartDAO();
        dao.deleteCartById(cartId);  // ← 장바구니 항목 삭제
        dao.close();
    }
    response.sendRedirect("cart.jsp");  // 삭제 후 장바구니 화면으로 이동
%>
