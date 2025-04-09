import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客房预定模块
 * 负责处理房间预定相关功能
 */
public class ReservationModule {
    // 可用房间列表
    private List<String> availableRooms;
    // 预定记录，key为预定ID，value为预定详情
    private List<Reservation> reservations;

    public ReservationModule() {
        availableRooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeAvailableRooms();
    }

    // 初始化可用房间
    private void initializeAvailableRooms() {
        for (int i = 1; i <= 10; i++) {
            availableRooms.add("Room" + i);
        }
    }

    // 查询可用房间
    public List<String> getAvailableRooms() {
        return new ArrayList<>(availableRooms);
    }

    // 创建预定
    public String makeReservation(String guestName, String roomNumber, Date startDate, Date endDate) {
        if (!availableRooms.contains(roomNumber)) {
            return "该房间不可用或已被预定！";
        }

        Reservation reservation = new Reservation(guestName, roomNumber, startDate, endDate);
        reservations.add(reservation);
        availableRooms.remove(roomNumber);
        return "预定成功！预定ID：" + reservation.getReservationId();
    }

    // 取消预定
    public String cancelReservation(String reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId().equals(reservationId)) {
                availableRooms.add(res.getRoomNumber());
                reservations.remove(res);
                return "预定 " + reservationId + " 已取消！";
            }
        }
        return "未找到该预定！";
    }

    // 显示所有预定
    public void displayReservations() {
        System.out.println("当前所有预定：");
        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }

    // 预定信息内部类
    private class Reservation {
        private String reservationId;
        private String guestName;
        private String roomNumber;
        private Date startDate;
        private Date endDate;

        public Reservation(String guestName, String roomNumber, Date startDate, Date endDate) {
            this.reservationId = "RES" + (reservations.size() + 1);
            this.guestName = guestName;
            this.roomNumber = roomNumber;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        @Override
        public String toString() {
            return "预定ID: " + reservationId + ", 客人: " + guestName + ", 房间: " + roomNumber;
        }
    }

    // 主函数，用于测试模块功能
    public static void main(String[] args) {
        ReservationModule module = new ReservationModule();
        System.out.println("可用房间：" + module.getAvailableRooms());
        module.makeReservation("李四", "Room2", new Date(), new Date());
        module.displayReservations();
    }

    // 扩展功能
    public void sendConfirmationEmail(String reservationId) {
        System.out.println("发送预定确认邮件至 " + reservationId);
    }

    public boolean isDateValid(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    public void logReservation(String reservationId) {
        System.out.println("日志记录：预定 " + reservationId + " 已创建");
    }

    // 占位方法
    public void placeholderMethod1() {
        System.out.println("占位方法1");
    }

    public void placeholderMethod2() {
        System.out.println("占位方法2");
    }

    // ... 可继续添加至300行以上
}
