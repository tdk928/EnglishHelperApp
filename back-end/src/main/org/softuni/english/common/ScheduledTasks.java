//package org.softuni.english.common;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScheduledTasks {
//    private final WatchService watchService;
//
//    private static final int SCHEDULED_TASKS_FIXED_RATE = 5000;
//
//    public ScheduledTasks(WatchService watchService) {
//        this.watchService = watchService;
//    }
//
//    @Scheduled(cron = "0 0/53 17 * * *")
//    public void promotionsClearTask() {
//        if(this.watchService.clearAllPromotions()) {
//            System.out.println("Success");
//        }
//    }
//
////    @Scheduled(fixedRate = 1000)
////    public void regularLogTask() {
////        System.out.println("Regular Log [" + LocalDate.now() + "]: Everything okey!");
////    }
//}
