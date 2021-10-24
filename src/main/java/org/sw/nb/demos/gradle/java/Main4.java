package org.sw.nb.demos.gradle.java;

import java.util.*;
import java.util.concurrent.*;

public class Main4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService readThreadPool = Executors.newScheduledThreadPool(1);
        List<ScheduledFuture> futureList = new ArrayList<>();
        //用objects模拟任务列表，任务的time代表耗费的时间
        List<Map<String, Object>> objects = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> object = new HashMap<>();
            object.put("name", "任务" + i);
            object.put("time", 1000);
            objects.add(object);
        }
        //第6个任务sleep15s，第6个任务是任务5
        objects.get(0).put("time", 15000);
        System.out.println(objects);
        for (Map<String, Object> object : objects) {
            //任务在0s后开始，任务间隔是5s
            ScheduledFuture future = readThreadPool.scheduleWithFixedDelay(() -> {
                System.out.println(sec() + " " + object.toString());
                try {
                    //暂停指定时间
                    Thread.sleep((int) object.get("time"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.SECONDS);
            futureList.add(future);
        }
        for (ScheduledFuture future : futureList) {
            future.get();
        }
    }

    public static int sec() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
}
