import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客房遗物管理模块
 * 负责处理客人在酒店遗失或找到的物品的管理功能
 */
public class PEManagement {
    // 存储遗物信息的列表
    private List<LostItem> lostItems;
    // 房间号与遗物的映射，方便按房间查询
    private Map<String, List<LostItem>> roomToItemsMap;
    // 遗物ID计数器
    private int itemIdCounter;

    // 构造函数，初始化模块
    public LostAndFoundModule() {
        lostItems = new ArrayList<>();
        roomToItemsMap = new HashMap<>();
        itemIdCounter = 1000; // 初始遗物ID从1000开始
    }

    // 登记遗失物品
    public String registerLostItem(String roomNumber, String itemDescription, String reportedBy, Date foundDate) {
        LostItem item = new LostItem(generateItemId(), roomNumber, itemDescription, reportedBy, foundDate);
        lostItems.add(item);

        // 更新房间号与遗物的映射
        roomToItemsMap.computeIfAbsent(roomNumber, k -> new ArrayList<>()).add(item);
        logLostItemRegistration(item.getItemId());
        return "遗物登记成功！物品ID：" + item.getItemId();
    }

    // 生成唯一的遗物ID
    private String generateItemId() {
        return "ITEM" + itemIdCounter++;
    }

    // 查询某房间的遗失物品
    public List<LostItem> queryItemsByRoom(String roomNumber) {
        return roomToItemsMap.getOrDefault(roomNumber, new ArrayList<>());
    }

    // 查询所有未领取的遗失物品
    public List<LostItem> queryUnclaimedItems() {
        List<LostItem> unclaimed = new ArrayList<>();
        for (LostItem item : lostItems) {
            if (!item.isClaimed()) {
                unclaimed.add(item);
            }
        }
        return unclaimed;
    }

    // 领取遗失物品
    public String claimItem(String itemId, String claimantName, String claimantIdNumber) {
        for (LostItem item : lostItems) {
            if (item.getItemId().equals(itemId)) {
                if (item.isClaimed()) {
                    return "该物品已被领取！";
                }
                item.setClaimed(true);
                item.setClaimantName(claimantName);
                item.setClaimantIdNumber(claimantIdNumber);
                item.setClaimDate(new Date());
                logItemClaimed(itemId);
                notifyStaff(itemId);
                return "物品 " + itemId + " 领取成功！领取人：" + claimantName;
            }
        }
        return "未找到物品ID：" + itemId;
    }

    // 显示所有遗失物品
    public void displayAllLostItems() {
        System.out.println("所有遗失物品记录：");
        for (LostItem item : lostItems) {
            System.out.println(item);
        }
    }

    // 遗失物品内部类
    private class LostItem {
        private String itemId;          // 物品唯一ID
        private String roomNumber;     // 发现物品的房间号
        private String description;    // 物品描述
        private String reportedBy;     // 报告人
        private Date foundDate;        // 发现日期
        private boolean claimed;       // 是否被领取
        private String claimantName;   // 领取人姓名
        private String claimantIdNumber; // 领取人身份证号
        private Date claimDate;        // 领取日期

        public LostItem(String itemId, String roomNumber, String description, String reportedBy, Date foundDate) {
            this.itemId = itemId;
            this.roomNumber = roomNumber;
            this.description = description;
            this.reportedBy = reportedBy;
            this.foundDate = foundDate;
            this.claimed = false;
            this.claimantName = null;
            this.claimantIdNumber = null;
            this.claimDate = null;
        }

        public String getItemId() {
            return itemId;
        }

        public boolean isClaimed() {
            return claimed;
        }

        public void setClaimed(boolean claimed) {
            this.claimed = claimed;
        }

        public void setClaimantName(String claimantName) {
            this.claimantName = claimantName;
        }

        public void setClaimantIdNumber(String claimantIdNumber) {
            this.claimantIdNumber = claimantIdNumber;
        }

        public void setClaimDate(Date claimDate) {
            this.claimDate = claimDate;
        }

        @Override
        public String toString() {
            String status = claimed ? "已领取 (领取人: " + claimantName + ")" : "未领取";
            return "物品ID: " + itemId + ", 房间: " + roomNumber + ", 描述: " + description + ", 状态: " + status;
        }
    }

    // 日志记录遗物登记
    private void logLostItemRegistration(String itemId) {
        System.out.println("日志记录：物品 " + itemId + " 已登记为遗失物品");
    }

    // 日志记录物品领取
    private void logItemClaimed(String itemId) {
        System.out.println("日志记录：物品 " + itemId + " 已被领取");
    }

    // 通知工作人员
    private void notifyStaff(String itemId) {
        System.out.println("通知工作人员：物品 " + itemId + " 已被领取，请核查");
    }

    // 验证领取人身份（模拟接口）
    private boolean validateClaimant(String idNumber) {
        return idNumber != null && idNumber.length() >= 9; // 简单验证规则
    }

    // 主函数，用于测试模块功能
    public static void main(String[] args) {
        LostAndFoundModule module = new LostAndFoundModule();
        module.registerLostItem("Room1", "黑色钱包", "张三", new Date());
        module.registerLostItem("Room2", "银色手表", "李四", new Date());
        module.displayAllLostItems();
        System.out.println(module.claimItem("ITEM1000", "王五", "123456789"));
        module.displayAllLostItems();
        System.out.println("Room1的遗物：" + module.queryItemsByRoom("Room1"));
    }

    // 扩展功能：统计遗物数量
    public int getTotalLostItems() {
        return lostItems.size();
    }

    // 扩展功能：统计未领取物品数量
    public int getUnclaimedItemCount() {
        return queryUnclaimedItems().size();
    }

    // 扩展功能：按日期范围查询遗物
    public List<LostItem> queryItemsByDateRange(Date startDate, Date endDate) {
        List<LostItem> result = new ArrayList<>();
        for (LostItem item : lostItems) {
            if (!item.foundDate.before(startDate) && !item.foundDate.after(endDate)) {
                result.add(item);
            }
        }
        return result;
    }

    // 扩展功能：清理过期未领取物品（假设30天未领取清理）
    public void cleanExpiredItems(int daysThreshold) {
        Date now = new Date();
        List<LostItem> toRemove = new ArrayList<>();
        for (LostItem item : lostItems) {
            long diffInMillies = now.getTime() - item.foundDate.getTime();
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
            if (!item.isClaimed() && diffInDays > daysThreshold) {
                toRemove.add(item);
            }
        }
        lostItems.removeAll(toRemove);
        System.out.println("已清理 " + toRemove.size() + " 个过期未领取物品");
    }
  
}
