package PhoneShopSystem;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList; // ✅ เพิ่ม import นี้

public class Employee {
    private Database db;
    private Scanner sc;

    public Employee(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== เมนูพนักงาน ===");
            System.out.println("1. การรายงาน");
            System.out.println("2. การแจ้งเตือน(พนักงาน)");
            System.out.println("3. จัดการการคืนสินค้า");
            System.out.println("4. จัดการการเคลมสินค้า");
            System.out.println("5. จัดการการซ่อมอุปกรณ์");
            System.out.println("6. จัดการการจัดส่งสินค้า");
            System.out.println("7. ออกจากระบบ");
            System.out.print("เลือกเมนู: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ กรุณาใส่ตัวเลขเท่านั้น");
                continue;
            }

            switch (choice) {
                case 1 -> showReports();
                case 2 -> employeeNotifications();
                case 3 -> manageReturns();
                case 4 -> manageClaims();
                case 5 -> manageRepairs();
                case 6 -> manageDeliveries();
                case 7 -> {
                    System.out.println("ออกจากระบบพนักงาน...");
                    return;
                }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📊 text mode การรายงาน
    private void showReports() {
        while (true) {
            System.out.println("\n📊===== การรายงาน =====");
            System.out.println("1. รายงานยอดขายทั้งหมด");
            System.out.println("2. รายงานการซ่อมทั้งหมด");
            System.out.println("3. สรุปสินค้าขายดีที่สุด");
            System.out.println("4. สรุปรุ่นที่ซ่อมบ่อยที่สุด");
            System.out.println("5. สถิติการคืนสินค้า");
            System.out.println("6. สถิติการเคลมสินค้า");
            System.out.println("7. กลับไปเมนูก่อนหน้า");
            System.out.print("เลือกรายงาน: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> showMonthlySales();
                case 2 -> showMonthlyRepairs();
                case 3 -> showBestSellingProduct();
                case 4 -> showMostCommonRepair();
                case 5 -> showReturnStatistics();
                case 6 -> showClaimStatistics();
                case 7 -> { return; }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📦 แสดงยอดขายทั้งหมด
    private void showMonthlySales() {
        if (db.purchases.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการขาย");
            return;
        }

        double totalAll = 0;
        System.out.println("\n========================================");
        System.out.println("          รายงานยอดขายทั้งหมด");
        System.out.println("========================================");

        for (Database.Purchase p : db.purchases) {
            totalAll += p.total;
            printReceipt(p);
        }
        System.out.println("----------------------------------------");
        System.out.printf("รวมยอดขายทั้งหมด: %,.2f บาท\n", totalAll);
        System.out.println("========================================\n");
    }

    private void printReceipt(Database.Purchase p) {
        System.out.println("\n----------------------------------------");
        System.out.println("เลขที่รายการ: " + p.id);
        System.out.println("วันที่: " + p.date);
        System.out.println("ลูกค้า: " + p.customerName);
        System.out.println("สินค้า: " + p.product);
        System.out.println("จำนวน: " + p.qty + " เครื่อง");
        System.out.printf("ราคาต่อหน่วย: %,.2f บาท\n", p.unitPrice);
        System.out.printf("รวม: %,.2f บาท\n", p.total);
        System.out.println("ชำระโดย: " + p.payment);
        System.out.println("----------------------------------------");
    }

    // 🔧 แสดงรายการซ่อมทั้งหมด
    private void showMonthlyRepairs() {
        if (db.repairs.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการซ่อม");
            return;
        }

        double totalCost = 0;
        System.out.println("\n========================================");
        System.out.println("          รายงานการซ่อมทั้งหมด");
        System.out.println("========================================");

        for (Database.Repair r : db.repairs) {
            totalCost += r.cost;
            printRepairReceipt(r);
        }
        System.out.println("----------------------------------------");
        System.out.printf("รวมค่าซ่อมทั้งหมด: %,.2f บาท\n", totalCost);
        System.out.println("========================================\n");
    }

    private void printRepairReceipt(Database.Repair r) {
        System.out.println("\n----------------------------------------");
        System.out.println("เลขที่ใบสั่งซ่อม: " + r.id);
        System.out.println("วันที่: " + r.date);
        System.out.println("ลูกค้า: " + r.customerName);
        System.out.println("รุ่น: " + r.model);
        System.out.println("ยี่ห้อ: " + r.brand);
        System.out.println("อาการ: " + r.symptom);
        System.out.println("รายละเอียด: " + r.detail);
        System.out.println("ค่าใช้จ่าย: " + String.format("%,.2f", r.cost) + " บาท");
        System.out.println("สถานะ: " + r.status);
        System.out.println("----------------------------------------");
    }

    // 📈 แสดงสินค้าขายดีที่สุด
    private void showBestSellingProduct() {
        String product = db.getBestSellingProduct();
        if (product == null) {
            System.out.println("❌ ยังไม่มีข้อมูลการขาย");
            return;
        }
        int qty = db.getProductCount(product);
        double total = db.getTotalSalesByProduct(product);

        System.out.println("\n========================================");
        System.out.println("           สรุปสินค้าขายดีที่สุด");
        System.out.println("========================================");
        System.out.println("สินค้า: " + product);
        System.out.println("จำนวนขาย: " + qty + " เครื่อง");
        System.out.printf("ยอดขายรวม: %,.2f บาท\n", total);
        System.out.println("========================================\n");
    }

    // 🔧 แสดงรุ่นที่ซ่อมบ่อยที่สุด
    private void showMostCommonRepair() {
        String model = db.getMostCommonRepairModel();
        if (model == null) {
            System.out.println("❌ ยังไม่มีข้อมูลการซ่อม");
            return;
        }
        int count = db.getRepairCountByModel(model);

        System.out.println("\n========================================");
        System.out.println("           สรุปรุ่นที่ซ่อมบ่อยที่สุด");
        System.out.println("========================================");
        System.out.println("รุ่น: " + model);
        System.out.println("จำนวนครั้ง: " + count + " เคส");
        System.out.println("========================================\n");
    }

    // 📊 สถิติการคืนสินค้า
    private void showReturnStatistics() {
        int pendingCount = db.getPendingReturnsCount();
        int totalCount = db.returns.size();
        int approvedCount = 0;
        int rejectedCount = 0;

        for (Database.Return returnItem : db.returns) {
            if (returnItem.status.equals("อนุมัติ")) approvedCount++;
            if (returnItem.status.equals("ปฏิเสธ")) rejectedCount++;
        }

        System.out.println("\n========================================");
        System.out.println("           สถิติการคืนสินค้า");
        System.out.println("========================================");
        System.out.println("ทั้งหมด: " + totalCount + " รายการ");
        System.out.println("รอดำเนินการ: " + pendingCount + " รายการ");
        System.out.println("อนุมัติ: " + approvedCount + " รายการ");
        System.out.println("ปฏิเสธ: " + rejectedCount + " รายการ");
        System.out.println("========================================\n");
    }

    // 📊 สถิติการเคลมสินค้า
    private void showClaimStatistics() {
        int pendingCount = db.getPendingClaimsCount();
        int totalCount = db.claims.size();
        int approvedCount = 0;
        int rejectedCount = 0;

        for (Database.Claim claim : db.claims) {
            if (claim.status.equals("อนุมัติ")) approvedCount++;
            if (claim.status.equals("ปฏิเสธ")) rejectedCount++;
        }

        System.out.println("\n========================================");
        System.out.println("           สถิติการเคลมสินค้า");
        System.out.println("========================================");
        System.out.println("ทั้งหมด: " + totalCount + " รายการ");
        System.out.println("รอดำเนินการ: " + pendingCount + " รายการ");
        System.out.println("อนุมัติ: " + approvedCount + " รายการ");
        System.out.println("ปฏิเสธ: " + rejectedCount + " รายการ");
        System.out.println("========================================\n");
    }

    // 🔔 text mode การแจ้งเตือน(พนักงาน)
    private void employeeNotifications() {
        System.out.println("\n🔔===== การแจ้งเตือน (พนักงาน) =====");
        
        List<Database.Notification> notifications = db.findNotificationsByTarget("พนักงาน");
        
        if (notifications.isEmpty()) {
            System.out.println("❌ ยังไม่มีการแจ้งเตือน");
            return;
        }

        int unreadCount = 0;
        for (Database.Notification notification : notifications) {
            if (notification.status.equals("ยังไม่อ่าน")) {
                unreadCount++;
            }
        }

        System.out.println("คุณมี " + unreadCount + " การแจ้งเตือนที่ยังไม่ได้อ่าน");
        
        for (int i = 0; i < notifications.size(); i++) {
            Database.Notification notification = notifications.get(i);
            String statusIcon = notification.status.equals("ยังไม่อ่าน") ? "🔴" : "✅";
            System.out.println((i + 1) + ". " + statusIcon + " " + notification.message + 
                             " (" + notification.date + ")");
        }

        System.out.print("\nเลือกการแจ้งเตือนที่ต้องการอ่าน (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= notifications.size()) {
                Database.Notification notification = notifications.get(choice - 1);
                db.markNotificationAsRead(notification.id);
                System.out.println("✅ ทำเครื่องหมายว่าอ่านแล้ว");
                
                // ถ้าเป็นการแจ้งเตือนเกี่ยวกับการคืนสินค้า ให้แนะนำไปที่เมนูจัดการการคืน
                if (notification.message.contains("คืนสินค้า")) {
                    System.out.println("💡 ต้องการไปที่เมนูจัดการการคืนสินค้าหรือไม่? (Y/N)");
                    String response = sc.nextLine().trim();
                    if (response.equalsIgnoreCase("Y")) {
                        manageReturns();
                    }
                }
            } else {
                System.out.println("❌ เลือกการแจ้งเตือนไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // ↩️ text mode การคืนสินค้า(ตรวจสอบการคืนสินค้า)
    private void manageReturns() {
        while (true) {
            System.out.println("\n↩️===== จัดการการคืนสินค้า =====");
            System.out.println("1. แสดงรายการคืนสินค้าทั้งหมด");
            System.out.println("2. แสดงรายการรอดำเนินการ");
            System.out.println("3. อนุมัติ/ปฏิเสธการคืนสินค้า");
            System.out.println("4. กลับไปเมนูก่อนหน้า");
            System.out.print("เลือกเมนู: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> showAllReturns();
                case 2 -> showPendingReturns();
                case 3 -> approveRejectReturn();
                case 4 -> { return; }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📋 แสดงรายการคืนสินค้าทั้งหมด
    private void showAllReturns() {
        if (db.returns.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการคืนสินค้า");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          รายการคืนสินค้าทั้งหมด");
        System.out.println("========================================");

        for (int i = 0; i < db.returns.size(); i++) {
            Database.Return returnItem = db.returns.get(i);
            String statusIcon = getReturnStatusIcon(returnItem.status);
            System.out.printf("%d. %s | %s | %s | %s\n", 
                i + 1, returnItem.id, returnItem.customerName, 
                returnItem.product, statusIcon + returnItem.status);
        }
        System.out.println("========================================\n");
    }

    // ⏳ แสดงรายการรอดำเนินการ
    private void showPendingReturns() {
        List<Database.Return> pendingReturns = new ArrayList<>(); // ✅ แก้ไขแล้ว
        for (Database.Return returnItem : db.returns) {
            if (returnItem.status.equals("รอดำเนินการ")) {
                pendingReturns.add(returnItem);
            }
        }

        if (pendingReturns.isEmpty()) {
            System.out.println("✅ ไม่มีรายการคืนสินค้ารอดำเนินการ");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("       รายการคืนสินค้ารอดำเนินการ");
        System.out.println("========================================");

        for (int i = 0; i < pendingReturns.size(); i++) {
            Database.Return returnItem = pendingReturns.get(i);
            System.out.printf("%d. %s | %s | %s | %s\n", 
                i + 1, returnItem.id, returnItem.customerName, 
                returnItem.product, returnItem.reason);
        }
        System.out.println("========================================\n");
    }

    // ✅❌ อนุมัติ/ปฏิเสธการคืนสินค้า
    private void approveRejectReturn() {
        // แสดงเฉพาะรายการที่รอดำเนินการ
        List<Database.Return> pendingReturns = new ArrayList<>(); // ✅ แก้ไขแล้ว
        for (Database.Return returnItem : db.returns) {
            if (returnItem.status.equals("รอดำเนินการ")) {
                pendingReturns.add(returnItem);
            }
        }

        if (pendingReturns.isEmpty()) {
            System.out.println("✅ ไม่มีรายการคืนสินค้ารอดำเนินการ");
            return;
        }

        System.out.println("\nรายการคืนสินค้ารอดำเนินการ:");
        for (int i = 0; i < pendingReturns.size(); i++) {
            Database.Return returnItem = pendingReturns.get(i);
            System.out.println((i + 1) + ". " + returnItem.id + " | " + returnItem.customerName + 
                             " | " + returnItem.product + " | " + returnItem.reason);
        }

        System.out.print("\nเลือกรายการที่ต้องการจัดการ (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= pendingReturns.size()) {
                Database.Return selectedReturn = pendingReturns.get(choice - 1);
                processReturn(selectedReturn);
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🔍 ตรวจสอบและดำเนินการคืนสินค้า
    private void processReturn(Database.Return returnItem) {
        System.out.println("\n========================================");
        System.out.println("       ตรวจสอบการคืนสินค้า");
        System.out.println("========================================");
        System.out.println("เลขที่คืน: " + returnItem.id);
        System.out.println("ลูกค้า: " + returnItem.customerName);
        System.out.println("สินค้า: " + returnItem.product);
        System.out.println("เหตุผล: " + returnItem.reason);
        System.out.println("วันที่: " + returnItem.date);
        
        // ตรวจสอบโปรโมชั่นและประกัน
        System.out.println("\n🔍 ตรวจสอบโปรโมชั่นและประกัน:");
        boolean hasWarranty = checkWarranty(returnItem);
        boolean hasPromotion = checkPromotion(returnItem);
        
        System.out.println("✅ ประกันเหลือ: " + (hasWarranty ? "มี" : "ไม่มี"));
        System.out.println("🎁 โปรโมชั่น: " + (hasPromotion ? "มี" : "ไม่มี"));
        
        System.out.println("\nเลือกการดำเนินการ:");
        System.out.println("1. อนุมัติการคืน");
        System.out.println("2. ปฏิเสธการคืน");
        System.out.print("เลือกหมายเลข: ");
        
        try {
            int action = Integer.parseInt(sc.nextLine());
            if (action == 1) {
                approveReturn(returnItem);
            } else if (action == 2) {
                rejectReturn(returnItem);
            } else {
                System.out.println("❌ เลือกการดำเนินการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // ✅ อนุมัติการคืน
    private void approveReturn(Database.Return returnItem) {
        System.out.print("กรอกเหตุผลการอนุมัติ: ");
        String response = sc.nextLine().trim();
        
        if (response.isEmpty()) {
            response = "อนุมัติตามเงื่อนไขการคืนสินค้า";
        }
        
        db.updateReturnStatus(returnItem.id, "อนุมัติ", response);
        
        // สร้างแจ้งเตือนให้ลูกค้า
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addNotification("สมาชิก", "การคืนสินค้าของคุณได้รับการอนุมัติ: " + response, date);
        
        System.out.println("✅ อนุมัติการคืนสินค้าเรียบร้อย!");
    }

    // ❌ ปฏิเสธการคืน
    private void rejectReturn(Database.Return returnItem) {
        System.out.print("กรอกเหตุผลการปฏิเสธ: ");
        String response = sc.nextLine().trim();
        
        if (response.isEmpty()) {
            response = "ไม่เป็นไปตามเงื่อนไขการคืนสินค้า";
        }
        
        db.updateReturnStatus(returnItem.id, "ปฏิเสธ", response);
        
        // สร้างแจ้งเตือนให้ลูกค้า
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addNotification("สมาชิก", "การคืนสินค้าของคุณถูกปฏิเสธ: " + response, date);
        
        System.out.println("❌ ปฏิเสธการคืนสินค้าเรียบร้อย!");
    }

    // 🔍 ตรวจสอบประกัน
    private boolean checkWarranty(Database.Return returnItem) {
        // ตรวจสอบจากวันที่ซื้อ (สมมติว่าประกัน 7 วัน)
        // ในระบบจริงควรดึงข้อมูลการซื้อจาก purchaseId
        return true; // สมมติว่ามีประกันเหลือ
    }

    // 🎁 ตรวจสอบโปรโมชั่น
    private boolean checkPromotion(Database.Return returnItem) {
        // ตรวจสอบโปรโมชั่นจากสินค้า
        // ในระบบจริงควรดึงจากฐานข้อมูลโปรโมชั่น
        return false; // สมมติว่าไม่มีโปรโมชั่น
    }

    // ⚠️ จัดการการเคลมสินค้า
    private void manageClaims() {
        while (true) {
            System.out.println("\n⚠️===== จัดการการเคลมสินค้า =====");
            System.out.println("1. แสดงรายการเคลมสินค้าทั้งหมด");
            System.out.println("2. แสดงรายการรอดำเนินการ");
            System.out.println("3. อนุมัติ/ปฏิเสธการเคลมสินค้า");
            System.out.println("4. กลับไปเมนูก่อนหน้า");
            System.out.print("เลือกเมนู: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> showAllClaims();
                case 2 -> showPendingClaims();
                case 3 -> approveRejectClaim();
                case 4 -> { return; }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📋 แสดงรายการเคลมสินค้าทั้งหมด
    private void showAllClaims() {
        if (db.claims.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการเคลมสินค้า");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          รายการเคลมสินค้าทั้งหมด");
        System.out.println("========================================");

        for (int i = 0; i < db.claims.size(); i++) {
            Database.Claim claim = db.claims.get(i);
            String statusIcon = getReturnStatusIcon(claim.status);
            System.out.printf("%d. %s | %s | %s | %s\n", 
                i + 1, claim.id, claim.customerName, 
                claim.product, statusIcon + claim.status);
        }
        System.out.println("========================================\n");
    }

    // ⏳ แสดงรายการรอดำเนินการ
    private void showPendingClaims() {
        List<Database.Claim> pendingClaims = new ArrayList<>(); // ✅ แก้ไขแล้ว
        for (Database.Claim claim : db.claims) {
            if (claim.status.equals("รอดำเนินการ")) {
                pendingClaims.add(claim);
            }
        }

        if (pendingClaims.isEmpty()) {
            System.out.println("✅ ไม่มีรายการเคลมสินค้ารอดำเนินการ");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("       รายการเคลมสินค้ารอดำเนินการ");
        System.out.println("========================================");

        for (int i = 0; i < pendingClaims.size(); i++) {
            Database.Claim claim = pendingClaims.get(i);
            System.out.printf("%d. %s | %s | %s | %s\n", 
                i + 1, claim.id, claim.customerName, 
                claim.product, claim.reason);
        }
        System.out.println("========================================\n");
    }

    // ✅❌ อนุมัติ/ปฏิเสธการเคลมสินค้า
    private void approveRejectClaim() {
        List<Database.Claim> pendingClaims = new ArrayList<>(); // ✅ แก้ไขแล้ว
        for (Database.Claim claim : db.claims) {
            if (claim.status.equals("รอดำเนินการ")) {
                pendingClaims.add(claim);
            }
        }

        if (pendingClaims.isEmpty()) {
            System.out.println("✅ ไม่มีรายการเคลมสินค้ารอดำเนินการ");
            return;
        }

        System.out.println("\nรายการเคลมสินค้ารอดำเนินการ:");
        for (int i = 0; i < pendingClaims.size(); i++) {
            Database.Claim claim = pendingClaims.get(i);
            System.out.println((i + 1) + ". " + claim.id + " | " + claim.customerName + 
                             " | " + claim.product + " | " + claim.reason);
        }

        System.out.print("\nเลือกรายการที่ต้องการจัดการ (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= pendingClaims.size()) {
                Database.Claim selectedClaim = pendingClaims.get(choice - 1);
                processClaim(selectedClaim);
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🔍 ตรวจสอบและดำเนินการเคลมสินค้า
    private void processClaim(Database.Claim claim) {
        System.out.println("\n========================================");
        System.out.println("       ตรวจสอบการเคลมสินค้า");
        System.out.println("========================================");
        System.out.println("เลขที่เคลม: " + claim.id);
        System.out.println("ลูกค้า: " + claim.customerName);
        System.out.println("สินค้า: " + claim.product);
        System.out.println("เหตุผล: " + claim.reason);
        System.out.println("วันที่: " + claim.date);
        
        // ตรวจสอบประกัน
        System.out.println("\n🔍 ตรวจสอบประกัน:");
        boolean hasWarranty = checkClaimWarranty(claim);
        System.out.println("✅ ประกันเหลือ: " + (hasWarranty ? "มี" : "ไม่มี"));
        
        System.out.println("\nเลือกการดำเนินการ:");
        System.out.println("1. อนุมัติการเคลม");
        System.out.println("2. ปฏิเสธการเคลม");
        System.out.print("เลือกหมายเลข: ");
        
        try {
            int action = Integer.parseInt(sc.nextLine());
            if (action == 1) {
                approveClaim(claim);
            } else if (action == 2) {
                rejectClaim(claim);
            } else {
                System.out.println("❌ เลือกการดำเนินการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // ✅ อนุมัติการเคลม
    private void approveClaim(Database.Claim claim) {
        System.out.print("กรอกเหตุผลการอนุมัติ: ");
        String response = sc.nextLine().trim();
        
        if (response.isEmpty()) {
            response = "อนุมัติตามเงื่อนไขการเคลมสินค้า";
        }
        
        db.updateClaimStatus(claim.id, "อนุมัติ", response);
        
        // สร้างแจ้งเตือนให้ลูกค้า
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addNotification("สมาชิก", "การเคลมสินค้าของคุณได้รับการอนุมัติ: " + response, date);
        
        System.out.println("✅ อนุมัติการเคลมสินค้าเรียบร้อย!");
    }

    // ❌ ปฏิเสธการเคลม
    private void rejectClaim(Database.Claim claim) {
        System.out.print("กรอกเหตุผลการปฏิเสธ: ");
        String response = sc.nextLine().trim();
        
        if (response.isEmpty()) {
            response = "ไม่เป็นไปตามเงื่อนไขการเคลมสินค้า";
        }
        
        db.updateClaimStatus(claim.id, "ปฏิเสธ", response);
        
        // สร้างแจ้งเตือนให้ลูกค้า
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addNotification("สมาชิก", "การเคลมสินค้าของคุณถูกปฏิเสธ: " + response, date);
        
        System.out.println("❌ ปฏิเสธการเคลมสินค้าเรียบร้อย!");
    }

    // 🔍 ตรวจสอบประกันการเคลม
    private boolean checkClaimWarranty(Database.Claim claim) {
        // ตรวจสอบจากวันที่ซื้อ (สมมติว่าประกัน 7 วัน)
        return true; // สมมติว่ามีประกันเหลือ
    }

    // 🔧 จัดการการซ่อมอุปกรณ์
    private void manageRepairs() {
        while (true) {
            System.out.println("\n🔧===== จัดการการซ่อมอุปกรณ์ =====");
            System.out.println("1. แสดงรายการซ่อมทั้งหมด");
            System.out.println("2. อัพเดทสถานะการซ่อม");
            System.out.println("3. กลับไปเมนูก่อนหน้า");
            System.out.print("เลือกเมนู: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> showAllRepairs();
                case 2 -> updateRepairStatus();
                case 3 -> { return; }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📋 แสดงรายการซ่อมทั้งหมด
    private void showAllRepairs() {
        if (db.repairs.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการซ่อม");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          รายการซ่อมทั้งหมด");
        System.out.println("========================================");

        for (int i = 0; i < db.repairs.size(); i++) {
            Database.Repair repair = db.repairs.get(i);
            System.out.printf("%d. %s | %s | %s | %s | %s\n", 
                i + 1, repair.id, repair.customerName, 
                repair.model, repair.symptom, repair.status);
        }
        System.out.println("========================================\n");
    }

    // 🔄 อัพเดทสถานะการซ่อม
    private void updateRepairStatus() {
        System.out.println("\nรายการซ่อมทั้งหมด:");
        for (int i = 0; i < db.repairs.size(); i++) {
            Database.Repair repair = db.repairs.get(i);
            System.out.println((i + 1) + ". " + repair.id + " | " + repair.customerName + 
                             " | " + repair.model + " | " + repair.status);
        }

        System.out.print("\nเลือกรายการที่ต้องการอัพเดทสถานะ (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= db.repairs.size()) {
                Database.Repair selectedRepair = db.repairs.get(choice - 1);
                updateRepairStatusProcess(selectedRepair);
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🔄 กระบวนการอัพเดทสถานะการซ่อม
    private void updateRepairStatusProcess(Database.Repair repair) {
        System.out.println("\nสถานะปัจจุบัน: " + repair.status);
        System.out.println("\nเลือกสถานะใหม่:");
        System.out.println("1. รับเครื่องแล้ว");
        System.out.println("2. กำลังซ่อม");
        System.out.println("3. รออะไหล่");
        System.out.println("4. ซ่อมเสร็จแล้ว");
        System.out.println("5. พร้อมรับเครื่อง");
        System.out.print("เลือกหมายเลข: ");
        
        try {
            int statusChoice = Integer.parseInt(sc.nextLine());
            String newStatus = switch (statusChoice) {
                case 1 -> "รับเครื่องแล้ว";
                case 2 -> "กำลังซ่อม";
                case 3 -> "รออะไหล่";
                case 4 -> "ซ่อมเสร็จแล้ว";
                case 5 -> "พร้อมรับเครื่อง";
                default -> {
                    System.out.println("❌ เลือกสถานะไม่ถูกต้อง");
                    yield null;
                }
            };
            
            if (newStatus != null) {
                if (db.updateRepairStatus(repair.id, newStatus)) {
                    // สร้างแจ้งเตือนให้ลูกค้า
                    String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                    db.addNotification("สมาชิก", "สถานะการซ่อมของคุณถูกอัพเดทเป็น: " + newStatus, date);
                    
                    System.out.println("✅ อัพเดทสถานะเรียบร้อยแล้ว");
                    System.out.println("สถานะใหม่: " + newStatus);
                } else {
                    System.out.println("❌ ไม่สามารถอัพเดทสถานะได้");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🚚 จัดการการจัดส่งสินค้า
    private void manageDeliveries() {
        while (true) {
            System.out.println("\n🚚===== จัดการการจัดส่งสินค้า =====");
            System.out.println("1. แสดงรายการจัดส่งทั้งหมด");
            System.out.println("2. อัพเดทสถานะการจัดส่ง");
            System.out.println("3. ค้นหาการจัดส่ง");
            System.out.println("4. กลับไปเมนูก่อนหน้า");
            System.out.print("เลือกเมนู: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> showAllDeliveries();
                case 2 -> updateDeliveryStatus();
                case 3 -> searchDelivery();
                case 4 -> { return; }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 📦 แสดงรายการจัดส่งทั้งหมด
    private void showAllDeliveries() {
        if (db.deliveries.isEmpty()) {
            System.out.println("❌ ยังไม่มีข้อมูลการจัดส่ง");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("          รายการจัดส่งทั้งหมด");
        System.out.println("========================================");

        for (int i = 0; i < db.deliveries.size(); i++) {
            Database.Delivery delivery = db.deliveries.get(i);
            System.out.printf("%d. %s | %s | %s | %s\n", 
                i + 1, delivery.trackingNumber, delivery.customerName, 
                delivery.deliveryCompany, delivery.status);
        }
        System.out.println("========================================\n");
    }

    // 🔄 อัพเดทสถานะการจัดส่ง
    private void updateDeliveryStatus() {
        System.out.print("กรอกเลขIMEIที่ต้องการอัพเดท: ");
        String trackingNumber = sc.nextLine().trim();
        
        if (trackingNumber.isEmpty()) {
            System.out.println("❌ กรุณากรอกเลขIMEI");
            return;
        }

        Database.Delivery delivery = db.findDeliveryByTrackingNumber(trackingNumber);
        if (delivery == null) {
            System.out.println("❌ ไม่พบข้อมูลการจัดส่ง");
            return;
        }

        System.out.println("\nสถานะปัจจุบัน: " + delivery.status);
        System.out.println("\nเลือกสถานะใหม่:");
        System.out.println("1. รับออเดอร์");
        System.out.println("2. เตรียมพัสดุ");
        System.out.println("3. รับเข้าระบบ");
        System.out.println("4. อยู่ระหว่างขนส่ง");
        System.out.println("5. กำลังจัดส่ง");
        System.out.println("6. จัดส่งสำเร็จ");
        System.out.println("7. จัดส่งไม่สำเร็จ");
        System.out.print("เลือกหมายเลข: ");
        
        try {
            int statusChoice = Integer.parseInt(sc.nextLine());
            String newStatus = switch (statusChoice) {
                case 1 -> "รับออเดอร์";
                case 2 -> "เตรียมพัสดุ";
                case 3 -> "รับเข้าระบบ";
                case 4 -> "อยู่ระหว่างขนส่ง";
                case 5 -> "กำลังจัดส่ง";
                case 6 -> "จัดส่งสำเร็จ";
                case 7 -> "จัดส่งไม่สำเร็จ";
                default -> {
                    System.out.println("❌ เลือกสถานะไม่ถูกต้อง");
                    yield null;
                }
            };
            
            if (newStatus != null) {
                if (db.updateDeliveryStatus(trackingNumber, newStatus)) {
                    // สร้างแจ้งเตือนให้ลูกค้า
                    String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                    db.addNotification("สมาชิก", "สถานะการจัดส่งของคุณถูกอัพเดทเป็น: " + newStatus, date);
                    
                    System.out.println("✅ อัพเดทสถานะเรียบร้อยแล้ว");
                    System.out.println("สถานะใหม่: " + newStatus);
                } else {
                    System.out.println("❌ ไม่สามารถอัพเดทสถานะได้");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🔍 ค้นหาการจัดส่ง
    private void searchDelivery() {
        System.out.print("กรอกเลข IMEI หรือชื่อลูกค้า: ");
        String keyword = sc.nextLine().trim();
        
        if (keyword.isEmpty()) {
            System.out.println("❌ กรุณากรอกคำค้นหา");
            return;
        }

        // ค้นหาด้วยเลข IMEI
        Database.Delivery delivery = db.findDeliveryByTrackingNumber(keyword);
        if (delivery != null) {
            printDeliveryDetails(delivery);
            return;
        }

        // ค้นหาด้วยชื่อลูกค้า
        List<Database.Delivery> deliveries = db.findDeliveriesByCustomer(keyword);
        if (!deliveries.isEmpty()) {
            System.out.println("\nพบ " + deliveries.size() + " รายการจัดส่ง:");
            for (int i = 0; i < deliveries.size(); i++) {
                Database.Delivery d = deliveries.get(i);
                System.out.println((i + 1) + ". " + d.trackingNumber + " | " + d.customerName + 
                                 " | " + d.deliveryCompany + " | " + d.status);
            }
        } else {
            System.out.println("❌ ไม่พบข้อมูลการจัดส่ง");
        }
    }

    // 🧾 พิมพ์รายละเอียดการจัดส่ง
    private void printDeliveryDetails(Database.Delivery delivery) {
        System.out.println("\n========================================");
        System.out.println("           รายละเอียดการจัดส่ง");
        System.out.println("========================================");
        System.out.println("เลขที่คำสั่งซื้อ: " + delivery.purchaseId);
        System.out.println("เลข IMEI: " + delivery.trackingNumber);
        System.out.println("ลูกค้า: " + delivery.customerName);
        System.out.println("ที่อยู่จัดส่ง: " + delivery.address);
        System.out.println("เบอร์โทร: " + delivery.phone);
        System.out.println("บริษัทขนส่ง: " + delivery.deliveryCompany);
        System.out.println("สถานะ: " + delivery.status);
        System.out.println("วันที่สร้าง: " + delivery.date);
        System.out.println("อัพเดทล่าสุด: " + delivery.lastUpdate);
        
        System.out.println("\n📋 ประวัติการอัพเดท:");
        if (delivery.updates.isEmpty()) {
            System.out.println("   - ยังไม่มีประวัติการอัพเดท");
        } else {
            for (String update : delivery.updates) {
                System.out.println("   📌 " + update);
            }
        }
        System.out.println("========================================\n");
    }

    // 🎯 ไอคอนสถานะการคืน
    private String getReturnStatusIcon(String status) {
        return switch (status) {
            case "รอดำเนินการ" -> "⏳ ";
            case "อนุมัติ" -> "✅ ";
            case "ปฏิเสธ" -> "❌ ";
            default -> "📄 ";
        };
    }
}