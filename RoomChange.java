import java.util.HashMap;
import java.util.Map;

/**
 * 客房更换模块
 * 负责处理客人更换房间相关功能
 */
public class RoomChangeModule {
    // 房间占用状态
    private Map<String, Boolean> roomStatus;
    // 房间与客人的映射
    private Map<String, String> roomGuestMap;

    public RoomChangeModule() {
        roomStatus = new HashMap<>();
        roomGuestMap = new HashMap<>();
        initializeRooms();
    }

    // 初始化房间状态
    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            roomStatus.put("Room" + i, false);
        }
    }

    // 检查房间是否可用
    private boolean isRoomAvailable(String roomNumber) {
        return roomStatus.containsKey(roomNumber) && !roomStatus.get(roomNumber);
    }

    // 更换房间
    public String changeRoom(String currentRoom, String newRoom, String guestName) {
        if (!roomGuestMap.containsKey(currentRoom) || !roomGuestMap.get(currentRoom).equals(guestName)) {
            return "当前房间信息不匹配！";
        }
        if (!isRoomAvailable(newRoom)) {
            return "新房间不可用！";
        }

        // 更新房间状态
        roomStatus.put(currentRoom, false);
        roomStatus.put(newRoom, true);
        roomGuestMap.remove(currentRoom);
        roomGuestMap.put(newRoom, guestName);
        return "房间更换成功！新房间号：" + newRoom;
    }

    // 登记初始入住（模拟与登记模块交互）
    public void registerInitialCheckIn(String roomNumber, String guestName) {
        roomStatus.put(roomNumber, true);
        roomGuestMap.put(roomNumber, guestName);
    }

    // 显示当前房间状态
    public void displayRoomStatus() {
        System.out.println("当前房间状态：");
        for (Map.Entry<String, Boolean> entry : roomStatus.entrySet()) {
            System.out.println(entry.getKey() + " : " + (entry.getValue() ? "已占用" : "空闲"));
        }
    }

    // 主函数，用于测试模块功能
    public static void main(String[] args) {
        RoomChangeModule module = new RoomChangeModule();
        module.registerInitialCheckIn("Room1", "王五");
        module.displayRoomStatus();
        System.out.println(module.changeRoom("Room1", "Room2", "王五"));
        module.displayRoomStatus();
    }

    // 扩展功能
    public void notifyHousekeeping(String roomNumber) {
        System.out.println("通知清洁人员：房间 " + roomNumber + " 需要清理");
    }

    public void logRoomChange(String oldRoom, String newRoom) {
        System.out.println("日志记录：从 " + oldRoom + " 更换至 " + newRoom);
    }

    public boolean validateGuest(String guestName) {
        return guestName != null && !guestName.isEmpty();
    }

    // 占位方法
    public void placeholderMethod1() {
        System.out.println("占位方法1");
    }

    public void placeholderMethod2() {
        System.out.println("占位方法2");
    }

    public interface GuestMissionService {

	Guest getGuestById(int id);

	List<Guest> getGuestByName(String name, int st, int eachPage);

	Guest getGuestByRoomNumber(String s);

	Guest getGuestByContract(String ct);

	int addGuest(Guest t);

	int addHost(Host t);

	int addIntern(Intern t);

	int addGuestBalance(GuestBalance t);

	int addGuestService(GuestService t);
	
	int delGuest(int id);
	
	int getTotal();
	
	List<Guest> getGuestList(int st, int eachPage);
	
	//add by yck
	int getTotalByName_RoomNumber(String name, String roomNumber);
	List<Guest> getGuestByName_RoomNumber(String name, String roomNumber, int st, int eachPage);
	List<Guest> getAllGuestByName_RoomNumber(String name, String roomNumber);

	//查询为租客gid提供的服务项 (item是要查的条目名)
	GuestService getCertainGuestService(Integer gid, String item);
        }
}
