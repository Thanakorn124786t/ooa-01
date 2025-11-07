package PhoneShopSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    public ArrayList<Purchase> purchases;
    public ArrayList<Repair> repairs;
    public ArrayList<Return> returns;
    public ArrayList<Delivery> deliveries;
    public ArrayList<Claim> claims;
    public ArrayList<Notification> notifications;

    public Database() {
        purchases = new ArrayList<>();
        repairs = new ArrayList<>();
        returns = new ArrayList<>();
        deliveries = new ArrayList<>();
        claims = new ArrayList<>();
        notifications = new ArrayList<>();
        
        initializeDeliveryCompanies();
        initializeSampleData(); // ข้อมูลตัวอย่างครบทุกฟังก์ชัน
    }

    // 🏢 ข้อมูลบริษัทขนส่ง
    private void initializeDeliveryCompanies() {
        // ข้อมูลบริษัทขนส่งจะถูกใช้ในระบบ
    }

    // 📦 ข้อมูลตัวอย่างครบทุกฟังก์ชัน (3 ตัวอย่างต่อฟังก์ชัน)
    private void initializeSampleData() {
        // 🛒 ตัวอย่างข้อมูลการซื้อ (3 ตัวอย่าง)
        initializePurchaseSamples();
        
        // 🔧 ตัวอย่างข้อมูลการซ่อม (3 ตัวอย่าง)
        initializeRepairSamples();
        
        // ↩️ ตัวอย่างข้อมูลการคืนสินค้า (3 ตัวอย่าง)
        initializeReturnSamples();
        
        // ⚠️ ตัวอย่างข้อมูลการเคลมสินค้า (3 ตัวอย่าง)
        initializeClaimSamples();
        
        // 🚚 ตัวอย่างข้อมูลการจัดส่ง (3 ตัวอย่าง)
        initializeDeliverySamples();
        
        // 🔔 ตัวอย่างข้อมูลการแจ้งเตือน (6 ตัวอย่าง)
        initializeNotificationSamples();
    }

    // 🛒 ตัวอย่างข้อมูลการซื้อ
    private void initializePurchaseSamples() {
        purchases.add(new Purchase("S001", "สมชาย ใจดี", "iPhone 17 Pro", "เงินสด", 35900, 1, 35900, "01/11/2024"));
        purchases.add(new Purchase("S002", "สมหญิง กรุณา", "Samsung S25 Ultra", "บัตรเครดิต", 42900, 1, 42900, "02/11/2024"));
        purchases.add(new Purchase("S003", "นายวีรศักดิ์ มั่นคง", "iPhone 17 Pro Max", "โอนเงิน", 48900, 1, 48900, "03/11/2024"));
        
        // สร้างการจัดส่งอัตโนมัติสำหรับตัวอย่าง
        addDelivery("S001", "สมชาย ใจดี", "123 ถนนสุขุมวิท กรุงเทพ", "0812345678", "Kerry Express");
        addDelivery("S002", "สมหญิง กรุณา", "456 ถนนสีลม กรุงเทพ", "0898765432", "Flash Express");
        addDelivery("S003", "นายวีรศักดิ์ มั่นคง", "789 ถนนรัชดา กรุงเทพ", "0825554444", "J&T Express");
    }

    // 🔧 ตัวอย่างข้อมูลการซ่อม
    private void initializeRepairSamples() {
        repairs.add(new Repair("R001", "สมชาย ใจดี", "หน้าจอแตก", "iPhone 15 Pro", "Apple", "123456789012345", "ดำ", "0812345678", "หน้าจอแตกต้องเปลี่ยนใหม่", 2500, "01/11/2024"));
        repairs.add(new Repair("R002", "สมหญิง กรุณา", "แบตเตอรี่เสื่อม", "Samsung S24 Ultra", "Samsung", "234567890123456", "ขาว", "0898765432", "แบตเตอรี่หมดเร็ว ต้องเปลี่ยนใหม่", 1500, "02/11/2024"));
        repairs.add(new Repair("R003", "นายวีรศักดิ์ มั่นคง", "เสียบชาร์จไม่เข้า", "iPhone 14 Pro", "Apple", "345678901234567", "ทอง", "0825554444", "พอร์ตชาร์จเสีย ต้องซ่อม", 1200, "03/11/2024"));
        
        // อัพเดทสถานะการซ่อมตัวอย่าง
        updateRepairStatus("R001", "ซ่อมเสร็จแล้ว");
        updateRepairStatus("R002", "กำลังซ่อม");
        updateRepairStatus("R003", "รออะไหล่");
    }

    // ↩️ ตัวอย่างข้อมูลการคืนสินค้า
    private void initializeReturnSamples() {
        returns.add(new Return("RT001", "S001", "สมชาย ใจดี", "iPhone 17 Pro", "สินค้ามีตำหนิ", "อนุมัติ", "01/11/2024", "อนุมัติการคืนเนื่องจากสินค้ามีตำหนิจากโรงงาน"));
        returns.add(new Return("RT002", "S002", "สมหญิง กรุณา", "Samsung S25 Ultra", "ไม่พอใจสินค้า", "รอดำเนินการ", "02/11/2024", ""));
        returns.add(new Return("RT003", "S003", "นายวีรศักดิ์ มั่นคง", "iPhone 17 Pro Max", "สั่งสินค้าผิด", "ปฏิเสธ", "03/11/2024", "ปฏิเสธเนื่องจากเกิน 7 วัน"));
    }

    // ⚠️ ตัวอย่างข้อมูลการเคลมสินค้า
    private void initializeClaimSamples() {
        claims.add(new Claim("CL001", "สมชาย ใจดี", "iPhone 17 Pro", "เครื่องเสียภายใน 7 วัน", "อนุมัติ", "01/11/2024", "อนุมัติเคลม เปลี่ยนเครื่องใหม่ให้"));
        claims.add(new Claim("CL002", "สมหญิง กรุณา", "Samsung S25 Ultra", "หน้าจอมีจุดดำ", "รอดำเนินการ", "02/11/2024", ""));
        claims.add(new Claim("CL003", "นายวีรศักดิ์ มั่นคง", "iPhone 17 Pro Max", "เครื่องร้อนผิดปกติ", "ปฏิเสธ", "03/11/2024", "ปฏิเสธเนื่องจากเป็นความเสียหายจากผู้ใช้"));
    }

    // 🚚 ตัวอย่างข้อมูลการจัดส่ง
    private void initializeDeliverySamples() {
        deliveries.add(new Delivery("S001", "KRY0000001", "สมชาย ใจดี", "123 ถนนสุขุมวิท กรุงเทพ", "0812345678", "Kerry Express", "จัดส่งสำเร็จ", "01/11/2024"));
        deliveries.add(new Delivery("S002", "FLS0000002", "สมหญิง กรุณา", "456 ถนนสีลม กรุงเทพ", "0898765432", "Flash Express", "อยู่ระหว่างขนส่ง", "02/11/2024"));
        deliveries.add(new Delivery("S003", "JNT0000003", "นายวีรศักดิ์ มั่นคง", "789 ถนนรัชดา กรุงเทพ", "0825554444", "J&T Express", "กำลังจัดส่ง", "03/11/2024"));
        
        // เพิ่มประวัติการอัพเดทสถานะ
        deliveries.get(0).updates.add("01/11/2024 10:00 - อัพเดทสถานะเป็น: จัดส่งสำเร็จ");
        deliveries.get(1).updates.add("02/11/2024 14:30 - อัพเดทสถานะเป็น: อยู่ระหว่างขนส่ง");
        deliveries.get(2).updates.add("03/11/2024 09:15 - อัพเดทสถานะเป็น: กำลังจัดส่ง");
    }

    // 🔔 ตัวอย่างข้อมูลการแจ้งเตือน
    private void initializeNotificationSamples() {
        // แจ้งเตือนพนักงาน
        notifications.add(new Notification("N001", "พนักงาน", "มีการคืนสินค้ารอดำเนินการจาก สมชาย ใจดี", "01/11/2024", "ยังไม่อ่าน"));
        notifications.add(new Notification("N002", "พนักงาน", "มีคำสั่งซื้อใหม่จาก สมหญิง กรุณา", "02/11/2024", "ยังไม่อ่าน"));
        notifications.add(new Notification("N003", "พนักงาน", "มีการเคลมสินค้ารอดำเนินการจาก นายวีรศักดิ์ มั่นคง", "03/11/2024", "ยังไม่อ่าน"));
        
        // แจ้งเตือนสมาชิก
        notifications.add(new Notification("N004", "สมาชิก", "การซ่อมของคุณพร้อมรับแล้ว", "01/11/2024", "ยังไม่อ่าน"));
        notifications.add(new Notification("N005", "สมาชิก", "คำสั่งซื้อของคุณกำลังจัดส่ง", "02/11/2024", "ยังไม่อ่าน"));
        notifications.add(new Notification("N006", "สมาชิก", "การคืนสินค้าของคุณได้รับการอนุมัติ", "03/11/2024", "ยังไม่อ่าน"));
    }

    // 🛒 เพิ่มการซื้อสินค้า
    public void addPurchase(String customerName, String product, String payment, double unitPrice, int qty, String date) {
        String id = String.format("S%03d", purchases.size() + 1);
        double total = unitPrice * qty;
        Purchase purchase = new Purchase(id, customerName, product, payment, unitPrice, qty, total, date);
        purchases.add(purchase);
        
        // สร้างการจัดส่งอัตโนมัติเมื่อมีการซื้อ (ยกเว้นรับสินค้าที่ร้าน)
        if (!payment.equals("รับสินค้าที่ร้าน")) {
            String[] companies = {"Kerry Express", "Flash Express", "J&T Express", "Thailand Post", "DHL"};
            String randomCompany = companies[(int) (Math.random() * companies.length)];
            addDelivery(id, customerName, "ที่อยู่จัดส่งตามที่แจ้ง", "ตามที่แจ้ง", randomCompany);
        }
        
        // สร้างแจ้งเตือน
        addNotification("พนักงาน", "มีคำสั่งซื้อใหม่จาก " + customerName + " - " + product, date);
    }

    // 🔧 เพิ่มรายการซ่อม
    public void addRepair(String customerName, String symptom, String model, String brand, String imei, String color,
            String phone, String detail, double cost, String date) {
        String id = String.format("R%03d", repairs.size() + 1);
        repairs.add(new Repair(id, customerName, symptom, model, brand, imei, color, phone, detail, cost, date));
        
        // สร้างแจ้งเตือน
        addNotification("พนักงาน", "มีรายการซ่อมใหม่จาก " + customerName + " - " + model, date);
        addNotification("สมาชิก", "รายการซ่อม " + model + " ของคุณได้รับการบันทึกแล้ว (เลขที่: " + id + ")", date);
    }

    // ↩️ เพิ่มการคืนสินค้า
    public void addReturn(String customerName, String product, String reason, String date) {
        String id = String.format("RT%03d", returns.size() + 1);
        String purchaseId = findPurchaseId(customerName, product);
        returns.add(new Return(id, purchaseId, customerName, product, reason, "รอดำเนินการ", date, ""));
        
        // สร้างแจ้งเตือนพนักงาน
        addNotification("พนักงาน", "มีการคืนสินค้ารอดำเนินการจาก " + customerName + " - " + product, date);
        addNotification("สมาชิก", "การคืนสินค้า " + product + " ของคุณได้รับการบันทึกแล้ว (เลขที่: " + id + ")", date);
    }

    // 🚚 เพิ่มข้อมูลการจัดส่ง
    public void addDelivery(String purchaseId, String customerName, String address, String phone, String deliveryCompany) {
        String trackingNumber = generateTrackingNumber(deliveryCompany);
        String status = "รับออเดอร์";
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
        deliveries.add(new Delivery(purchaseId, trackingNumber, customerName, address, phone, deliveryCompany, status, date));
        
        // สร้างแจ้งเตือน
        addNotification("สมาชิก", "คำสั่งซื้อของคุณกำลังจัดส่ง เลขติดตาม: " + trackingNumber, date);
    }

    // ⚠️ เพิ่มการเคลมสินค้า
    public void addClaim(String customerName, String product, String reason, String date) {
        String id = String.format("CL%03d", claims.size() + 1);
        String purchaseId = findPurchaseId(customerName, product);
        claims.add(new Claim(id, customerName, product, reason, "รอดำเนินการ", date, ""));
        
        // สร้างแจ้งเตือนพนักงาน
        addNotification("พนักงาน", "มีการเคลมสินค้ารอดำเนินการจาก " + customerName + " - " + product, date);
        addNotification("สมาชิก", "การเคลมสินค้า " + product + " ของคุณได้รับการบันทึกแล้ว (เลขที่: " + id + ")", date);
    }

    // 🔔 เพิ่มการแจ้งเตือน
    public void addNotification(String target, String message, String date) {
        String id = String.format("N%03d", notifications.size() + 1);
        notifications.add(new Notification(id, target, message, date, "ยังไม่อ่าน"));
    }

    // 🔢 สร้างเลขIMEIอัตโนมัติ (ระบบหลัก)
    private String generateTrackingNumber(String company) {
        String prefix = switch (company) {
            case "Kerry Express" -> "KRY";
            case "Flash Express" -> "FLS";
            case "J&T Express" -> "JNT";
            case "Thailand Post" -> "THP";
            case "DHL" -> "DHL";
            default -> "APP"; // สำหรับร้านค้าเอง
        };
        // สร้างเลข 7 หลัก โดยนับจากจำนวนการจัดส่งทั้งหมด + 1
        return prefix + String.format("%07d", deliveries.size() + 1);
    }

    // 🔍 ค้นหา Purchase ID
    private String findPurchaseId(String customerName, String product) {
        for (Purchase p : purchases) {
            if (p.customerName.equals(customerName) && p.product.equals(product)) {
                return p.id;
            }
        }
        return "ไม่พบข้อมูล";
    }

    // ฟังก์ชันอื่นๆ ที่เหลืออยู่เดิม...
    // 🔍 ค้นหาการจัดส่งโดยเลขIMEI
    public Delivery findDeliveryByTrackingNumber(String trackingNumber) {
        for (Delivery delivery : deliveries) {
            if (delivery.trackingNumber.equalsIgnoreCase(trackingNumber)) {
                return delivery;
            }
        }
        return null;
    }

    // 🔍 ค้นหาการจัดส่งโดยชื่อลูกค้า
    public List<Delivery> findDeliveriesByCustomer(String customerName) {
        List<Delivery> results = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            if (delivery.customerName.toLowerCase().contains(customerName.toLowerCase())) {
                results.add(delivery);
            }
        }
        return results;
    }

    // 🔄 อัพเดทสถานะการจัดส่ง
    public boolean updateDeliveryStatus(String trackingNumber, String newStatus) {
        Delivery delivery = findDeliveryByTrackingNumber(trackingNumber);
        if (delivery != null) {
            delivery.status = newStatus;
            delivery.lastUpdate = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
            delivery.updates.add(delivery.lastUpdate + " - อัพเดทสถานะเป็น: " + newStatus);
            return true;
        }
        return false;
    }

    // 🔄 อัพเดทสถานะการคืนสินค้า
    public boolean updateReturnStatus(String returnId, String newStatus, String employeeResponse) {
        for (Return returnItem : returns) {
            if (returnItem.id.equals(returnId)) {
                returnItem.status = newStatus;
                returnItem.employeeResponse = employeeResponse;
                return true;
            }
        }
        return false;
    }

    // 🔄 อัพเดทสถานะการเคลมสินค้า
    public boolean updateClaimStatus(String claimId, String newStatus, String employeeResponse) {
        for (Claim claim : claims) {
            if (claim.id.equals(claimId)) {
                claim.status = newStatus;
                claim.employeeResponse = employeeResponse;
                return true;
            }
        }
        return false;
    }

    // 🔄 อัพเดทสถานะการซ่อม
    public boolean updateRepairStatus(String repairId, String newStatus) {
        for (Repair repair : repairs) {
            if (repair.id.equals(repairId)) {
                repair.status = newStatus;
                return true;
            }
        }
        return false;
    }

    // 🔔 อัพเดทสถานะการแจ้งเตือน
    public void markNotificationAsRead(String notificationId) {
        for (Notification notification : notifications) {
            if (notification.id.equals(notificationId)) {
                notification.status = "อ่านแล้ว";
            }
        }
    }

    // 🔍 ค้นหาการซ่อมโดยชื่อลูกค้า
    public List<Repair> findRepairsByCustomer(String customerName) {
        List<Repair> results = new ArrayList<>();
        for (Repair repair : repairs) {
            if (repair.customerName.toLowerCase().contains(customerName.toLowerCase())) {
                results.add(repair);
            }
        }
        return results;
    }

    // 🔍 ค้นหาการคืนสินค้าโดยชื่อลูกค้า
    public List<Return> findReturnsByCustomer(String customerName) {
        List<Return> results = new ArrayList<>();
        for (Return returnItem : returns) {
            if (returnItem.customerName.toLowerCase().contains(customerName.toLowerCase())) {
                results.add(returnItem);
            }
        }
        return results;
    }

    // 🔍 ค้นหาการเคลมโดยชื่อลูกค้า
    public List<Claim> findClaimsByCustomer(String customerName) {
        List<Claim> results = new ArrayList<>();
        for (Claim claim : claims) {
            if (claim.customerName.toLowerCase().contains(customerName.toLowerCase())) {
                results.add(claim);
            }
        }
        return results;
    }

    // 🔍 ค้นหาแจ้งเตือนโดยกลุ่มเป้าหมาย
    public List<Notification> findNotificationsByTarget(String target) {
        List<Notification> results = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.target.equals(target)) {
                results.add(notification);
            }
        }
        return results;
    }

    // 📊 ฟังก์ชันรายงานต่างๆ
    public String getBestSellingProduct() {
        if (purchases.isEmpty()) return null;
        HashMap<String, Integer> countMap = new HashMap<>();
        for (Purchase p : purchases) {
            countMap.put(p.product, countMap.getOrDefault(p.product, 0) + p.qty);
        }
        String best = null;
        int max = 0;
        for (String key : countMap.keySet()) {
            if (countMap.get(key) > max) {
                max = countMap.get(key);
                best = key;
            }
        }
        return best;
    }

    public int getProductCount(String product) {
        int sum = 0;
        for (Purchase p : purchases) {
            if (p.product.equals(product))
                sum += p.qty;
        }
        return sum;
    }

    public double getTotalSalesByProduct(String product) {
        double sum = 0;
        for (Purchase p : purchases) {
            if (p.product.equals(product))
                sum += p.total;
        }
        return sum;
    }

    public String getMostCommonRepairModel() {
        if (repairs.isEmpty()) return null;
        HashMap<String, Integer> countMap = new HashMap<>();
        for (Repair r : repairs) {
            countMap.put(r.model, countMap.getOrDefault(r.model, 0) + 1);
        }
        String best = null;
        int max = 0;
        for (String key : countMap.keySet()) {
            if (countMap.get(key) > max) {
                max = countMap.get(key);
                best = key;
            }
        }
        return best;
    }

    public int getRepairCountByModel(String model) {
        int sum = 0;
        for (Repair r : repairs) {
            if (r.model.equals(model))
                sum++;
        }
        return sum;
    }

    public double getTotalSales() {
        double total = 0;
        for (Purchase p : purchases) {
            total += p.total;
        }
        return total;
    }

    public int getPendingReturnsCount() {
        int count = 0;
        for (Return returnItem : returns) {
            if (returnItem.status.equals("รอดำเนินการ")) {
                count++;
            }
        }
        return count;
    }

    public int getPendingClaimsCount() {
        int count = 0;
        for (Claim claim : claims) {
            if (claim.status.equals("รอดำเนินการ")) {
                count++;
            }
        }
        return count;
    }

    // 📦 Class สำหรับเก็บข้อมูลการซื้อ
    public static class Purchase {
        public String id;
        public String customerName;
        public String product;
        public String payment;
        public double unitPrice;
        public int qty;
        public double total;
        public String date;

        public Purchase(String id, String customerName, String product, String payment, double unitPrice, int qty,
                double total, String date) {
            this.id = id;
            this.customerName = customerName;
            this.product = product;
            this.payment = payment;
            this.unitPrice = unitPrice;
            this.qty = qty;
            this.total = total;
            this.date = date;
        }

        @Override
        public String toString() {
            return String.format("เลขที่: %s | ลูกค้า: %s | สินค้า: %s | จำนวน: %d | รวม: %,.2f บาท", 
                id, customerName, product, qty, total);
        }
    }

    // 🔧 Class สำหรับเก็บข้อมูลการซ่อม
    public static class Repair {
        public String id;
        public String customerName;
        public String symptom;
        public String model;
        public String brand;
        public String imei;
        public String color;
        public String phone;
        public String detail;
        public double cost;
        public String date;
        public String status;

        public Repair(String id, String customerName, String symptom, String model, String brand, String imei,
                String color, String phone, String detail, double cost, String date) {
            this.id = id;
            this.customerName = customerName;
            this.symptom = symptom;
            this.model = model;
            this.brand = brand;
            this.imei = imei;
            this.color = color;
            this.phone = phone;
            this.detail = detail;
            this.cost = cost;
            this.date = date;
            this.status = "รับเครื่องแล้ว";
        }

        @Override
        public String toString() {
            return String.format("เลขที่: %s | ลูกค้า: %s | รุ่น: %s | อาการ: %s | สถานะ: %s", 
                id, customerName, model, symptom, status);
        }
    }

    // ↩️ Class สำหรับเก็บข้อมูลการคืนสินค้า
    public static class Return {
        public String id;
        public String purchaseId;
        public String customerName;
        public String product;
        public String reason;
        public String status;
        public String date;
        public String employeeResponse;

        public Return(String id, String purchaseId, String customerName, String product, String reason, 
                     String status, String date, String employeeResponse) {
            this.id = id;
            this.purchaseId = purchaseId;
            this.customerName = customerName;
            this.product = product;
            this.reason = reason;
            this.status = status;
            this.date = date;
            this.employeeResponse = employeeResponse;
        }

        @Override
        public String toString() {
            return String.format("เลขที่: %s | ลูกค้า: %s | สินค้า: %s | สถานะ: %s", 
                id, customerName, product, getStatusWithIcon(status));
        }

        private String getStatusWithIcon(String status) {
            return switch (status) {
                case "รอดำเนินการ" -> "⏳ " + status;
                case "อนุมัติ" -> "✅ " + status;
                case "ปฏิเสธ" -> "❌ " + status;
                default -> "📄 " + status;
            };
        }
    }

    // 🚚 Class สำหรับเก็บข้อมูลการจัดส่ง
    public static class Delivery {
        public String purchaseId;
        public String trackingNumber;
        public String customerName;
        public String address;
        public String phone;
        public String deliveryCompany;
        public String status;
        public String date;
        public String lastUpdate;
        public ArrayList<String> updates;

        public Delivery(String purchaseId, String trackingNumber, String customerName, String address, 
                       String phone, String deliveryCompany, String status, String date) {
            this.purchaseId = purchaseId;
            this.trackingNumber = trackingNumber;
            this.customerName = customerName;
            this.address = address;
            this.phone = phone;
            this.deliveryCompany = deliveryCompany;
            this.status = status;
            this.date = date;
            this.lastUpdate = date;
            this.updates = new ArrayList<>();
            this.updates.add(date + " - สร้างรายการจัดส่ง - " + status);
        }

        @Override
        public String toString() {
            return String.format("เลข IMEI : %s | ลูกค้า: %s | บริษัท: %s | สถานะ: %s", 
                trackingNumber, customerName, deliveryCompany, getStatusWithIcon(status));
        }

        private String getStatusWithIcon(String status) {
            return switch (status) {
                case "รับออเดอร์" -> "📦 " + status;
                case "เตรียมพัสดุ" -> "📋 " + status;
                case "รับเข้าระบบ" -> "🏢 " + status;
                case "อยู่ระหว่างขนส่ง" -> "🚚 " + status;
                case "กำลังจัดส่ง" -> "🛵 " + status;
                case "จัดส่งสำเร็จ" -> "✅ " + status;
                case "จัดส่งไม่สำเร็จ" -> "❌ " + status;
                default -> "📄 " + status;
            };
        }
    }

    // ⚠️ Class สำหรับเก็บข้อมูลการเคลมสินค้า
    public static class Claim {
        public String id;
        public String customerName;
        public String product;
        public String reason;
        public String status;
        public String date;
        public String employeeResponse;

        public Claim(String id, String customerName, String product, String reason, 
                    String status, String date, String employeeResponse) {
            this.id = id;
            this.customerName = customerName;
            this.product = product;
            this.reason = reason;
            this.status = status;
            this.date = date;
            this.employeeResponse = employeeResponse;
        }

        @Override
        public String toString() {
            return String.format("เลขที่: %s | ลูกค้า: %s | สินค้า: %s | สถานะ: %s", 
                id, customerName, product, getStatusWithIcon(status));
        }

        private String getStatusWithIcon(String status) {
            return switch (status) {
                case "รอดำเนินการ" -> "⏳ " + status;
                case "อนุมัติ" -> "✅ " + status;
                case "ปฏิเสธ" -> "❌ " + status;
                default -> "📄 " + status;
            };
        }
    }

    // 🔔 Class สำหรับเก็บข้อมูลการแจ้งเตือน
    public static class Notification {
        public String id;
        public String target;
        public String message;
        public String date;
        public String status;

        public Notification(String id, String target, String message, String date, String status) {
            this.id = id;
            this.target = target;
            this.message = message;
            this.date = date;
            this.status = status;
        }

        @Override
        public String toString() {
            String statusIcon = status.equals("ยังไม่อ่าน") ? "🔴" : "✅";
            return String.format("%s %s | %s | %s", statusIcon, message, target, date);
        }
    }
}