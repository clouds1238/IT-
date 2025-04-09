## 客房登记模块 

### 功能目标
客房登记模块旨在为酒店前台提供一个高效的入住登记工具，帮助工作人员快速分配房间并记录客人信息，确保房间状态实时更新，避免重复分配或管理混乱。

### 主要功能实现
1. **登记客人入住 (checkInGuest)**  
   - **描述**：为到达酒店的客人分配指定房间，记录其姓名、身份证号和入住时间。  
   - **输入**：客人姓名 (`guestName`)、身份证号 (`idNumber`)、房间号 (`roomNumber`)。  
   - **实现细节**：  
     - 检查房间号是否有效（是否存在于系统）。  
     - 验证房间是否空闲，若已被占用则拒绝登记。  
     - 创建 `Guest` 对象存储客人信息，更新房间状态为“已占用”。  
   - **输出**：返回入住成功的提示信息，如“入住成功！房间号：Room1，客人姓名：张三”。  
   - **使用场景**：客人到达前台办理入住手续时使用。

2. **查询房间状态 (displayRoomStatus)**  
   - **描述**：显示系统中所有房间的当前状态（占用或空闲）。  
   - **实现细节**：遍历房间状态映射表，打印每个房间的占用情况。  
   - **输出**：控制台输出，如“Room1 : 已占用, Room2 : 空闲”。  
   - **使用场景**：工作人员需快速了解当前房间使用情况。

3. **获取客人信息 (getGuestInfo)**  
   - **描述**：根据房间号查询入住客人的详细信息。  
   - **输入**：房间号 (`roomNumber`)。  
   - **实现细节**：从客人记录映射表中获取对应房间的 `Guest` 对象，若无记录返回空值。  
   - **输出**：返回 `Guest` 对象，包含姓名、身份证号和入住时间。  
   - **使用场景**：验证某房间的入住者身份。
