package goodsShop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import goodsShop.dto.orderDTO;

public class orderDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public orderDAO() {
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

    /** 주문 저장 후 생성된 orderId 반환 */
    public int insertOrder(orderDTO dto) {
        int orderId = 0;
        String sql = "INSERT INTO orders (m_id, m_name, phone, address, memo, order_total, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?)";



        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dto.getMemberId());        // m_id
            ps.setString(2, dto.getMemberName());      // m_name
            ps.setString(3, dto.getPhone());           // phone
            ps.setString(4, dto.getAddress());         // address
            ps.setString(5, dto.getMemo());            // memo
            ps.setInt(6, dto.getOrderTotal());         // order_total
            ps.setString(7, dto.getPaymentMethod());   // payment_method



            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    orderId = keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    /** 주문 ID로 주문 1건 조회 */
    public orderDTO getOrderById(int orderId) {
        orderDTO dto = null;
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new orderDTO();
                    dto.setOrderId(rs.getInt("order_id"));
                    dto.setMemberId(rs.getString("m_id"));
                    dto.setMemberName(rs.getString("m_name"));
                    dto.setOrderTotal(rs.getInt("order_total"));
                    dto.setOrderDate(rs.getString("order_date"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setMemo(rs.getString("memo"));
                    dto.setPaymentMethod(rs.getString("payment_method"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    /** 특정 회원의 주문 내역 리스트 */
    public List<orderDTO> getOrderList(String memberId) {
        List<orderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE m_id = ? ORDER BY order_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orderDTO dto = new orderDTO();
                    dto.setOrderId(rs.getInt("order_id"));
                    dto.setMemberId(rs.getString("m_id"));
                    dto.setMemberName(rs.getString("m_name"));
                    dto.setOrderTotal(rs.getInt("order_total"));
                    dto.setOrderDate(rs.getString("order_date"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setAddress(rs.getString("address"));
                    dto.setMemo(rs.getString("memo"));
                    dto.setPaymentMethod(rs.getString("payment_method")); 

                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** 자원 해제 */
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    /** 주문 정보 수정 처리 */
    public boolean updateOrder(orderDTO dto) {
        String sql = "UPDATE orders SET phone = ?, address = ?, memo = ?, payment_method = ? WHERE order_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);

            // 수정할 항목 설정
            pstmt.setString(1, dto.getPhone());            // 연락처
            pstmt.setString(2, dto.getAddress());          // 배송 주소
            pstmt.setString(3, dto.getMemo());             // 요청 사항
            pstmt.setString(4, dto.getPaymentMethod());    // 결제 방법
            pstmt.setInt(5, dto.getOrderId());             // 주문 ID (조건)

            int result = pstmt.executeUpdate(); // 실행 후 결과 반환 (영향 받은 행 수)
            return result > 0; // 수정 성공 여부 반환
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 출력
        }
        return false; // 실패 시 false 반환
    }

}
