package PhoneShopSystem;

// การดูหมายเลข IMEI ของอุปกรณ์ กดหมายเลข *#06#
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Scanner sc = new Scanner(System.in);

        System.out.println("🛠️ กำลังเริ่มระบบร้านขายโทรศัพท์...");
        System.out.println("========================================");
        System.out.println("           ร้านแอปสโตร์");
        System.out.println("     โทรศัพท์มือถือและอุปกรณ์ไอที");
        System.out.println("========================================");

        try {
            while (true) {
                System.out.println("\n=== ระบบร้านขายโทรศัพท์ ===");
                System.out.println("1. เข้าระบบลูกค้า");
                System.out.println("2. เข้าระบบพนักงาน");
                System.out.println("3. แสดงข้อมูลระบบ");
                System.out.println("4. ออกจากระบบ");
                System.out.print("เลือกเมนู: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("❌ กรุณาใส่ตัวเลขเท่านั้น");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        Member member = new Member(db, sc);
                        member.showMenu();
                    }
                    case 2 -> {
                        Employee employee = new Employee(db, sc);
                        employee.showMenu();
                    }
                    case 3 -> showSystemInfo(db);
                    case 4 -> {
                        System.out.println("👋 ออกจากระบบแล้ว ขอบคุณที่ใช้บริการ!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("❌ กรุณาเลือกเมนู 1-4 เท่านั้น");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ เกิดข้อผิดพลาดในระบบ: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void showSystemInfo(Database db) {
        System.out.println("\n📊===== สถิติระบบร้านขายโทรศัพท์ =====");
        System.out.println("========================================");
        System.out.println("จำนวนการซื้อสินค้า: " + db.purchases.size() + " รายการ");
        System.out.println("จำนวนการซ่อมสินค้า: " + db.repairs.size() + " รายการ");
        System.out.println("จำนวนการคืนสินค้า: " + db.returns.size() + " รายการ");
        System.out.println("จำนวนการเคลมสินค้า: " + db.claims.size() + " รายการ");
        System.out.println("จำนวนการจัดส่ง: " + db.deliveries.size() + " รายการ");

        if (!db.purchases.isEmpty()) {
            double totalSales = db.getTotalSales();
            System.out.printf("ยอดขายรวมทั้งหมด: %,.2f บาท\n", totalSales);

            String bestSeller = db.getBestSellingProduct();
            if (bestSeller != null) {
                int qty = db.getProductCount(bestSeller);
                System.out.println("สินค้าขายดีที่สุด: " + bestSeller + " (ขายได้ " + qty + " เครื่อง)");
            }
        }

        int pendingReturns = db.getPendingReturnsCount();
        int pendingClaims = db.getPendingClaimsCount();

        System.out.println("การคืนสินค้ารอดำเนินการ: " + pendingReturns + " รายการ");
        System.out.println("การเคลมสินค้ารอดำเนินการ: " + pendingClaims + " รายการ");

        if (pendingReturns > 0 || pendingClaims > 0) {
            System.out.println("\n⚠️  มีรายการที่ต้องดำเนินการ:");
            if (pendingReturns > 0) {
                System.out.println("   - การคืนสินค้า: " + pendingReturns + " รายการ");
            }
            if (pendingClaims > 0) {
                System.out.println("   - การเคลมสินค้า: " + pendingClaims + " รายการ");
            }
        }

        System.out.println("========================================\n");
    }
}