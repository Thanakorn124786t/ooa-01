package PhoneShopSystem;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.List;

public class Member {
    private Database db;
    private Scanner sc;
    private ArrayList<String> purchaseHistory;

    public Member(Database db, Scanner sc) {
        this.db = db;
        this.sc = sc;
        this.purchaseHistory = new ArrayList<>();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== เมนูลูกค้า ===");
            System.out.println("1. ซื้อโทรศัพท์");
            System.out.println("2. แจ้งซ่อมโทรศัพท์");
            System.out.println("3. ติดตามการซ่อมอุปกรณ์");
            System.out.println("4. การแจ้งเตือน");
            System.out.println("5. การจัดส่งสินค้า");
            System.out.println("6. ตรวจสอบสถานะการจัดส่ง");
            System.out.println("7. การคืนสินค้า");
            System.out.println("8. ตรวจสอบการคืนสินค้า");
            System.out.println("9. การเคลมสินค้า");
            System.out.println("10. แสดงประวัติการซื้อ");
            System.out.println("11. กลับไปหน้าหลัก");
            System.out.print("เลือกเมนู: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ กรุณาใส่ตัวเลข");
                continue;
            }

            switch (choice) {
                case 1 -> buyPhone();
                case 2 -> repairPhone();
                case 3 -> trackRepair();
                case 4 -> memberNotifications();
                case 5 -> trackPackage();
                case 6 -> checkDeliveryStatus();
                case 7 -> returnProduct();
                case 8 -> checkReturnStatus();
                case 9 -> claimProduct();
                case 10 -> showPurchaseHistory();
                case 11 -> {
                    return;
                }
                default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
            }
        }
    }

    // 🛒 text mode ซื้อโทรศัพท์
    private void buyPhone() {
        String[] products = { "iPhone 17", "iPhone 17 Pro", "iPhone 17 Pro Max",
                "Samsung S25", "Samsung S25 Plus", "Samsung S25 Ultra" };
        double[] prices = { 32900, 35900, 48900, 29900, 32900, 42900 };

        System.out.println("\n📱===== ซื้อโทรศัพท์ =====");
        System.out.println("เลือกสินค้าที่ต้องการซื้อ:");
        for (int i = 0; i < products.length; i++)
            System.out.printf("%d. %s (%.2f บาท)\n", i + 1, products[i], prices[i]);

        System.out.print("เลือกหมายเลขสินค้า: ");
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("❌ ไม่ถูกต้อง");
            return;
        }

        if (choice < 1 || choice > products.length) {
            System.out.println("❌ สินค้าไม่ถูกต้อง");
            return;
        }

        String product = products[choice - 1];
        double price = prices[choice - 1];

        System.out.print("กรอกชื่อผู้ซื้อ: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อผู้ซื้อ");
            return;
        }

        // วิธีชำระเงิน
        System.out.println("\n💳 เลือกวิธีชำระเงิน:");
        System.out.println("1. เงินสด");
        System.out.println("2. บัตรเครดิต");
        System.out.println("3. โอนเงิน");
        System.out.println("4. ผ่อนชำระ");
        System.out.print("เลือกหมายเลข: ");
        int payChoice;
        try {
            payChoice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("❌ ไม่ถูกต้อง");
            return;
        }

        String payment = switch (payChoice) {
            case 1 -> "เงินสด";
            case 2 -> "บัตรเครดิต";
            case 3 -> "โอนเงิน";
            case 4 -> "ผ่อนชำระ";
            default -> {
                System.out.println("❌ วิธีชำระเงินไม่ถูกต้อง");
                yield null;
            }
        };
        if (payment == null) return;

        System.out.print("กรอกจำนวนเครื่อง: ");
        int qty;
        try {
            qty = Integer.parseInt(sc.nextLine());
            if (qty <= 0) {
                System.out.println("❌ จำนวนต้องมากกว่า 0");
                return;
            }
            if (qty > 10) {
                System.out.println("❌ ไม่สามารถซื้อเกิน 10 เครื่องในครั้งเดียว");
                return;
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
            return;
        }

        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addPurchase(name, product, payment, price, qty, date);
        purchaseHistory.add(String.format("%s | %s | จำนวน: %d | วิธีชำระ: %s | รวม: %.2f บาท", 
            date, product, qty, payment, price * qty));

        System.out.println("✅ บันทึกข้อมูลเรียบร้อย!");
        printPurchaseReceipt(name, product, payment, price, qty);
    }

    // 🧾 ใบเสร็จซื้อสินค้า
    private void printPurchaseReceipt(String name, String product, String payment, double unitPrice, int qty) {
        String staff = "พี่้อ้วน";
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        int orderId = db.purchases.size();
        
        System.out.println("\n========================================");
        System.out.println("           ใบเสร็จการชำระเงิน");
        System.out.println("              ร้านแอปสโตร์");
        System.out.println("ติดต่อ: LINE:@Appstore | Tel: 0932783888");
        System.out.println("Staff: " + staff);
        System.out.println("----------------------------------------");
        System.out.println("เลขที่รายการ: S" + String.format("%03d", orderId));
        System.out.println("วันที่: " + date);
        System.out.println("ชำระโดย: " + payment);
        System.out.println("----------------------------------------");
        System.out.printf("%-25s x%d %,.2f\n", product, qty, unitPrice);
        System.out.println("----------------------------------------");
        System.out.printf("รวมทั้งสิ้น: %,.2f บาท\n", unitPrice * qty);
        System.out.println("*สินค้ารับประกัน 7 วัน*");
        System.out.println("ขอบคุณที่ใช้บริการ ❤️");
        System.out.println("========================================\n");
    }

    // 🔧 text mode การซ่อมอุปกรณ์
    private void repairPhone() {
        System.out.println("\n🔧===== การซ่อมอุปกรณ์ =====");
        System.out.print("กรอกชื่อผู้แจ้งซ่อม: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อผู้แจ้งซ่อม");
            return;
        }
        
        System.out.print("รุ่น: ");
        String model = sc.nextLine().trim();
        if (model.isEmpty()) {
            System.out.println("❌ กรุณากรอกรุ่น");
            return;
        }
        
        System.out.print("ยี่ห้อ: ");
        String brand = sc.nextLine().trim();
        if (brand.isEmpty()) {
            System.out.println("❌ กรุณากรอกยี่ห้อ");
            return;
        }
        
        System.out.print("IMEI: ");
        String imei = sc.nextLine().trim();
        while (imei.length() != 15 || !imei.matches("\\d+")) {
            System.out.println("❌ IMEI ต้องเป็นตัวเลข 15 หลัก");
            System.out.print("กรุณากรอก IMEI ใหม่: ");
            imei = sc.nextLine().trim();
        }
        
        System.out.print("สีเครื่อง: ");
        String color = sc.nextLine().trim();
        
        System.out.print("เบอร์โทร: ");
        String phone = sc.nextLine().trim();
        
        System.out.print("อาการเสีย: ");
        String symptom = sc.nextLine().trim();
        if (symptom.isEmpty()) {
            System.out.println("❌ กรุณากรอกอาการเสีย");
            return;
        }
        
        System.out.print("รายละเอียด: ");
        String detail = sc.nextLine().trim();
        
        System.out.print("ราคาค่าซ่อม (บาท): ");
        double cost;
        try {
            cost = Double.parseDouble(sc.nextLine());
            if (cost < 0) {
                System.out.println("❌ ราคาต้อง >=0");
                return;
            }
        } catch (Exception e) {
            System.out.println("❌ ราคาต้องเป็นตัวเลข");
            return;
        }

        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addRepair(name, symptom, model, brand, imei, color, phone, detail, cost, date);
        System.out.println("✅ บันทึกการซ่อมเรียบร้อย!");
        printRepairReceipt(name, model, brand, imei, color, phone, symptom, detail, cost);
    }

    // 🧾 ใบเสร็จซ่อมสินค้า
    private void printRepairReceipt(String name, String model, String brand, String imei, String color, String phone,
            String symptom, String detail, double cost) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        int repairId = db.repairs.size();
        
        System.out.println("\n========================================");
        System.out.println("           ใบเสร็จซ่อมสินค้า");
        System.out.println("              ร้านแอปสโตร์");
        System.out.println("----------------------------------------");
        System.out.println("เลขที่ใบสั่งซ่อม: R" + String.format("%03d", repairId));
        System.out.println("วันที่: " + date);
        System.out.println("ลูกค้า: " + name);
        System.out.println("รุ่น: " + model + " | ยี่ห้อ: " + brand + " | IMEI: " + imei);
        System.out.println("สี: " + color + " | โทร: " + phone);
        System.out.println("อาการ: " + symptom);
        System.out.println("รายละเอียด: " + detail);
        System.out.println("----------------------------------------");
        System.out.printf("ค่าซ่อม: %,.2f บาท\n", cost);
        System.out.println("*สินค้ารับประกันการซ่อม 7 วัน*");
        System.out.println("ขอบคุณที่ใช้บริการ ❤️");
        System.out.println("========================================\n");
    }

    // 🔍 text mode ติดตามการซ่อมอุปกรณ์
    private void trackRepair() {
        System.out.println("\n🔍===== ติดตามการซ่อมอุปกรณ์ =====");
        System.out.print("กรอกชื่อลูกค้า: ");
        String customerName = sc.nextLine().trim();
        
        if (customerName.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อลูกค้า");
            return;
        }

        List<Database.Repair> repairs = db.findRepairsByCustomer(customerName);
        
        if (repairs.isEmpty()) {
            System.out.println("❌ ไม่พบข้อมูลการซ่อมสำหรับลูกค้า: " + customerName);
            return;
        }

        System.out.println("\nพบ " + repairs.size() + " รายการซ่อม:");
        for (int i = 0; i < repairs.size(); i++) {
            Database.Repair repair = repairs.get(i);
            System.out.println((i + 1) + ". เลขที่ซ่อม: " + repair.id + " | รุ่น: " + repair.model + 
                             " | อาการ: " + repair.symptom + " | สถานะ: " + repair.status);
        }

        System.out.print("\nเลือกรายการที่ต้องการดูรายละเอียด (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= repairs.size()) {
                printRepairDetails(repairs.get(choice - 1));
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🧾 พิมพ์รายละเอียดการซ่อม
    private void printRepairDetails(Database.Repair repair) {
        System.out.println("\n========================================");
        System.out.println("           รายละเอียดการซ่อม");
        System.out.println("========================================");
        System.out.println("เลขที่ซ่อม: " + repair.id);
        System.out.println("ลูกค้า: " + repair.customerName);
        System.out.println("รุ่น: " + repair.model);
        System.out.println("ยี่ห้อ: " + repair.brand);
        System.out.println("IMEI: " + repair.imei);
        System.out.println("อาการ: " + repair.symptom);
        System.out.println("รายละเอียด: " + repair.detail);
        System.out.println("ค่าซ่อม: " + String.format("%,.2f", repair.cost) + " บาท");
        System.out.println("สถานะ: " + repair.status);
        System.out.println("วันที่: " + repair.date);
        System.out.println("========================================\n");
    }

    // 🔔 text mode การแจ้งเตือน(สมาชิก)
    private void memberNotifications() {
        System.out.println("\n🔔===== การแจ้งเตือน (สมาชิก) =====");
        
        List<Database.Notification> notifications = db.findNotificationsByTarget("สมาชิก");
        
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
            } else {
                System.out.println("❌ เลือกการแจ้งเตือนไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🚚 text mode การจัดส่งสินค้า
    private void trackPackage() {
        System.out.println("\n🚚===== การจัดส่งสินค้า =====");
        System.out.println("1. ติดตามด้วยเลขIMEI");
        System.out.println("2. ติดตามด้วยชื่อลูกค้า");
        System.out.println("3. กลับไปเมนูก่อนหน้า");
        System.out.print("เลือกวิธีติดตาม: ");
        
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
            return;
        }

        switch (choice) {
            case 1 -> trackByTrackingNumber();
            case 2 -> trackByCustomerName();
            case 3 -> { return; }
            default -> System.out.println("❌ กรุณาเลือกเมนูให้ถูกต้อง");
        }
    }

    // 🔢 ติดตามด้วยเลข IMEI
    private void trackByTrackingNumber() {
        System.out.print("\nกรุณากรอกเลขIMEI: ");
        String trackingNumber = sc.nextLine().trim();
        
        if (trackingNumber.isEmpty()) {
            System.out.println("❌ กรุณากรอกเลขIMEI");
            return;
        }

        Database.Delivery delivery = db.findDeliveryByTrackingNumber(trackingNumber);
        
        if (delivery == null) {
            System.out.println("❌ ไม่พบข้อมูลการจัดส่งสำหรับเลข IMEI: " + trackingNumber);
            return;
        }

        printDeliveryDetails(delivery);
    }

    // 👤 ติดตามด้วยชื่อลูกค้า
    private void trackByCustomerName() {
        System.out.print("\nกรุณากรอกชื่อลูกค้า: ");
        String customerName = sc.nextLine().trim();
        
        if (customerName.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อลูกค้า");
            return;
        }

        List<Database.Delivery> deliveries = db.findDeliveriesByCustomer(customerName);
        
        if (deliveries.isEmpty()) {
            System.out.println("❌ ไม่พบข้อมูลการจัดส่งสำหรับลูกค้า: " + customerName);
            return;
        }

        System.out.println("\nพบ " + deliveries.size() + " รายการจัดส่ง:");
        for (int i = 0; i < deliveries.size(); i++) {
            System.out.println((i + 1) + ". " + deliveries.get(i));
        }

        System.out.print("\nเลือกรายการที่ต้องการดูรายละเอียด (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= deliveries.size()) {
                printDeliveryDetails(deliveries.get(choice - 1));
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 📊 text mode การจัดส่งสินค้า(การตรวจสอบสถานะการจัดส่ง)
    private void checkDeliveryStatus() {
        System.out.println("\n📊===== ตรวจสอบสถานะการจัดส่ง =====");
        // ใช้ฟังก์ชัน trackPackage เดียวกัน
        trackPackage();
    }

    // 🧾 พิมพ์รายละเอียดการจัดส่ง
    private void printDeliveryDetails(Database.Delivery delivery) {
        System.out.println("\n========================================");
        System.out.println("           ข้อมูลการติดตามพัสดุ");
        System.out.println("========================================");
        System.out.println("เลขที่คำสั่งซื้อ: " + delivery.purchaseId);
        System.out.println("เลข IMEI : " + delivery.trackingNumber);
        System.out.println("ลูกค้า: " + delivery.customerName);
        System.out.println("ที่อยู่จัดส่ง: " + delivery.address);
        System.out.println("เบอร์โทร: " + delivery.phone);
        System.out.println("บริษัทขนส่ง: " + delivery.deliveryCompany);
        System.out.println("สถานะปัจจุบัน: " + getStatusWithIcon(delivery.status));
        System.out.println("วันที่สร้างรายการ: " + delivery.date);
        System.out.println("อัพเดทล่าสุด: " + delivery.lastUpdate);
        
        System.out.println("\n📋 ประวัติการอัพเดท:");
        if (delivery.updates.isEmpty()) {
            System.out.println("   - ยังไม่มีประวัติการอัพเดท");
        } else {
            for (String update : delivery.updates) {
                System.out.println("   📌 " + update);
            }
        }
        
        printDeliveryCompanyContact(delivery.deliveryCompany);
        System.out.println("========================================\n");
    }

    // 📞 แสดงข้อมูลติดต่อบริษัทขนส่ง
    private void printDeliveryCompanyContact(String company) {
        System.out.println("\n📞 ข้อมูลติดต่อ " + company + ":");
        switch (company) {
            case "Kerry Express" -> {
                System.out.println("   ☎️ โทร: 1217");
                System.out.println("   🌐 เว็บไซต์: www.kerryexpress.com");
                System.out.println("   📱 แอป: Kerry Express");
            }
            case "Flash Express" -> {
                System.out.println("   ☎️ โทร: 1770");
                System.out.println("   🌐 เว็บไซต์: www.flashexpress.com");
                System.out.println("   📱 แอป: Flash Express");
            }
            case "J&T Express" -> {
                System.out.println("   ☎️ โทร: 02-026-4444");
                System.out.println("   🌐 เว็บไซต์: www.jtexpress.co.th");
                System.out.println("   📱 แอป: J&T Express");
            }
            case "Thailand Post" -> {
                System.out.println("   ☎️ โทร: 1545");
                System.out.println("   🌐 เว็บไซต์: www.thailandpost.co.th");
                System.out.println("   📱 แอป: Thailand Post");
            }
            case "DHL" -> {
                System.out.println("   ☎️ โทร: 02-345-5000");
                System.out.println("   🌐 เว็บไซต์: www.dhl.co.th");
                System.out.println("   📱 แอป: DHL Express");
            }
            default -> System.out.println("   ℹ️ กรุณาติดต่อร้านค้าเพื่อข้อมูลเพิ่มเติม");
        }
    }

    // ↩️ text mode การคืนสินค้า (แจ้งการคืนสินค้า(สมาชิก))
    private void returnProduct() {
        System.out.println("\n↩️===== การคืนสินค้า =====");
        System.out.print("กรอกชื่อผู้ขอคืน: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อผู้ขอคืน");
            return;
        }
        
        System.out.print("กรอกรายการสินค้าที่ต้องการคืน: ");
        String product = sc.nextLine().trim();
        if (product.isEmpty()) {
            System.out.println("❌ กรุณากรอกรายการสินค้า");
            return;
        }
        
        System.out.print("เหตุผลการคืนสินค้า: ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) {
            System.out.println("❌ กรุณากรอกเหตุผลการคืนสินค้า");
            return;
        }

        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addReturn(name, product, reason, date);

        System.out.println("\n✅ แจ้งคืนสินค้าเรียบร้อย!");
        System.out.println("สถานะ: รอดำเนินการ");
        System.out.println("พนักงานจะติดต่อคุณกลับภายใน 24 ชั่วโมง");
        
        printReturnReceipt(name, product, reason);
    }

    // 🧾 ใบเสร็จคืนสินค้า
    private void printReturnReceipt(String name, String product, String reason) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        
        System.out.println("\n========================================");
        System.out.println("           ใบแจ้งคืนสินค้า");
        System.out.println("              ร้านแอปสโตร์");
        System.out.println("----------------------------------------");
        System.out.println("ลูกค้า: " + name);
        System.out.println("สินค้า: " + product);
        System.out.println("เหตุผล: " + reason);
        System.out.println("สถานะ: รอดำเนินการ");
        System.out.println("วันที่: " + date);
        System.out.println("----------------------------------------");
        System.out.println("**หมายเหตุ:**");
        System.out.println("- พนักงานจะตรวจสอบและติดต่อกลับภายใน 24 ชั่วโมง");
        System.out.println("- กรุณาเก็บสินค้าและกล่องเดิมให้ครบถ้วน");
        System.out.println("ขอบคุณที่ใช้บริการ ❤️");
        System.out.println("========================================\n");
    }

    // 🔍 text mode การคืนสินค้า(ตรวจสอบการคืนสินค้า)
    private void checkReturnStatus() {
        System.out.println("\n🔍===== ตรวจสอบการคืนสินค้า =====");
        System.out.print("กรอกชื่อลูกค้า: ");
        String customerName = sc.nextLine().trim();
        
        if (customerName.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อลูกค้า");
            return;
        }

        List<Database.Return> returns = db.findReturnsByCustomer(customerName);
        
        if (returns.isEmpty()) {
            System.out.println("❌ ไม่พบข้อมูลการคืนสินค้าสำหรับลูกค้า: " + customerName);
            return;
        }

        System.out.println("\nพบ " + returns.size() + " รายการคืนสินค้า:");
        for (int i = 0; i < returns.size(); i++) {
            Database.Return returnItem = returns.get(i);
            System.out.println((i + 1) + ". เลขที่คืน: " + returnItem.id + " | สินค้า: " + returnItem.product + 
                             " | สถานะ: " + returnItem.status);
        }

        System.out.print("\nเลือกรายการที่ต้องการดูรายละเอียด (0 เพื่อย้อนกลับ): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) return;
            if (choice > 0 && choice <= returns.size()) {
                printReturnDetails(returns.get(choice - 1));
            } else {
                System.out.println("❌ เลือกรายการไม่ถูกต้อง");
            }
        } catch (Exception e) {
            System.out.println("❌ กรุณาใส่ตัวเลข");
        }
    }

    // 🧾 พิมพ์รายละเอียดการคืนสินค้า
    private void printReturnDetails(Database.Return returnItem) {
        System.out.println("\n========================================");
        System.out.println("           รายละเอียดการคืนสินค้า");
        System.out.println("========================================");
        System.out.println("เลขที่คืน: " + returnItem.id);
        System.out.println("เลขที่ซื้อ: " + returnItem.purchaseId);
        System.out.println("ลูกค้า: " + returnItem.customerName);
        System.out.println("สินค้า: " + returnItem.product);
        System.out.println("เหตุผล: " + returnItem.reason);
        System.out.println("สถานะ: " + returnItem.status);
        System.out.println("วันที่: " + returnItem.date);
        if (!returnItem.employeeResponse.isEmpty()) {
            System.out.println("คำตอบจากพนักงาน: " + returnItem.employeeResponse);
        }
        System.out.println("========================================\n");
    }

    // ⚠️ text mode การเคลมสินค้า
    private void claimProduct() {
        System.out.println("\n⚠️===== การเคลมสินค้า =====");
        System.out.print("กรอกชื่อผู้ขอเคลม: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ กรุณากรอกชื่อผู้ขอเคลม");
            return;
        }
        
        System.out.print("กรอกรายการสินค้าที่ต้องการเคลม: ");
        String product = sc.nextLine().trim();
        if (product.isEmpty()) {
            System.out.println("❌ กรุณากรอกรายการสินค้า");
            return;
        }
        
        System.out.print("เหตุผลการเคลมสินค้า: ");
        String reason = sc.nextLine().trim();
        if (reason.isEmpty()) {
            System.out.println("❌ กรุณากรอกเหตุผลการเคลมสินค้า");
            return;
        }

        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        db.addClaim(name, product, reason, date);

        System.out.println("\n✅ แจ้งเคลมสินค้าเรียบร้อย!");
        System.out.println("สถานะ: รอดำเนินการ");
        System.out.println("พนักงานจะติดต่อคุณกลับภายใน 24 ชั่วโมง");
        
        printClaimReceipt(name, product, reason);
    }

    // 🧾 ใบเสร็จเคลมสินค้า
    private void printClaimReceipt(String name, String product, String reason) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        
        System.out.println("\n========================================");
        System.out.println("           ใบแจ้งเคลมสินค้า");
        System.out.println("              ร้านแอปสโตร์");
        System.out.println("----------------------------------------");
        System.out.println("ลูกค้า: " + name);
        System.out.println("สินค้า: " + product);
        System.out.println("เหตุผล: " + reason);
        System.out.println("สถานะ: รอดำเนินการ");
        System.out.println("วันที่: " + date);
        System.out.println("----------------------------------------");
        System.out.println("**หมายเหตุ:**");
        System.out.println("- พนักงานจะตรวจสอบและติดต่อกลับภายใน 24 ชั่วโมง");
        System.out.println("- กรุณาเตรียมหลักฐานการซื้อและสินค้าให้พร้อม");
        System.out.println("ขอบคุณที่ใช้บริการ ❤️");
        System.out.println("========================================\n");
    }

    // 🧾 text mode แสดงประวัติการซื้อ
    private void showPurchaseHistory() {
        if (purchaseHistory.isEmpty()) {
            System.out.println("❌ ยังไม่มีรายการซื้อสินค้า");
            return;
        }
        
        System.out.println("\n🧾===== ประวัติการซื้อทั้งหมด (" + purchaseHistory.size() + " รายการ) =====");
        for (int i = 0; i < purchaseHistory.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + purchaseHistory.get(i));
        }
        System.out.println("========================================\n");
    }

    // 🎯 แสดงสถานะพร้อมไอคอน
    private String getStatusWithIcon(String status) {
        return switch (status) {
            case "รับออเดอร์" -> "📦 " + status;
            case "เตรียมพัสดุ" -> "📋 " + status;
            case "รับเข้าระบบ" -> "🏢 " + status;
            case "อยู่ระหว่างขนส่ง" -> "🚚 " + status;
            case "กำลังจัดส่ง" -> "🛵 " + status;
            case "จัดส่งสำเร็จ" -> "✅ " + status;
            case "จัดส่งไม่สำเร็จ" -> "❌ " + status;
            case "รอดำเนินการ" -> "⏳ " + status;
            case "อนุมัติ" -> "✅ " + status;
            case "ปฏิเสธ" -> "❌ " + status;
            default -> "📄 " + status;
        };
    }
}