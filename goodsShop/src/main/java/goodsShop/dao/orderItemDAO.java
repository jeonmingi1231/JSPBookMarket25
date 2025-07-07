package goodsShop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import goodsShop.dto.cartDTO;
import goodsShop.dto.orderItemDTO;

public class orderItemDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // ================================
    // 생성자: 데이터베이스 연결 설정
    // JDBC 드라이버를 로딩하고 goodsDB에 접속합니다.
    // ================================
    public orderItemDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.111.101:3306/goodsDB",
                "goods",
                "1234"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================================
    // [CREATE] 주문 상세 항목 추가 (INSERT)
    // 장바구니(cartDTO)에 담긴 상품 정보를 기반으로
    // order_items 테이블에 주문 상세 항목을 저장합니다.
    // orderId는 orders 테이블의 주문 번호입니다.
    // ================================
    public int insertOrderItem(int orderId, cartDTO cart) {
        String sql = "INSERT INTO order_items "
                   + "(order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);                      // 어떤 주문에 속한 항목인지 설정
            ps.setString(2, cart.getIId());             // 상품 ID
            ps.setString(3, cart.getIName());           // 상품 이름
            ps.setInt(4, cart.getIUnitPrice());         // 단가
            ps.setString(5, cart.getISize());           // 사이즈
            ps.setInt(6, cart.getIQuantity());          // 수량
            ps.setInt(7, cart.getCTotal());             // 항목별 총액
            return ps.executeUpdate();                  // 실행 성공 시 1 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;                                   // 실패 시 0 반환
        }
    }

    // ================================
    // [READ] 주문 상세 항목 목록 조회
    // 특정 주문(orderId)에 대한 상세 항목들을 조회합니다.
    // 즉, 한 주문에 포함된 개별 상품들을 불러옵니다.
    // SELECT 결과는 orderItemDTO 목록으로 반환됩니다.
    // ================================
    public List<orderItemDTO> getOrderItemsByOrderId(int orderId) {
        List<orderItemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId); // 조회할 주문번호 지정
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderItemDTO item = new orderItemDTO();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setIId(rs.getString("i_id"));
                    item.setIName(rs.getString("i_name"));
                    item.setIUnitPrice(rs.getInt("i_unitPrice"));
                    item.setISize(rs.getString("i_size"));
                    item.setIQuantity(rs.getInt("i_quantity"));
                    item.setItemTotal(rs.getInt("item_total"));
                    list.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================================
    // [UTILITY] DB 연결 자원 해제
    // rs, pstmt, conn 등 데이터베이스 객체를 안전하게 닫습니다.
    // 누수 방지 및 안전한 종료를 위한 필수 처리입니다.
    // ================================
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
