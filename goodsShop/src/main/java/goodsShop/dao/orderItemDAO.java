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

    // DB 연결
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

    /** 주문 상세 추가 (장바구니 → 주문) */
    public int insertOrderItem(int orderId, cartDTO cart) {
        String sql = "INSERT INTO order_items "
                   + "(order_id, i_id, i_name, i_unitPrice, i_size, i_quantity, item_total) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setString(2, cart.getIId());
            ps.setString(3, cart.getIName());
            ps.setInt(4, cart.getIUnitPrice());
            ps.setString(5, cart.getISize());
            ps.setInt(6, cart.getIQuantity());
            ps.setInt(7, cart.getCTotal());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 특정 주문의 상세 항목 조회 */
    public List<orderItemDTO> getOrderItemsByOrderId(int orderId) {
        List<orderItemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
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
}
