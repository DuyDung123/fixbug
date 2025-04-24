package com.example.fixbug.study;

import java.util.*;

public class JobAbc extends AbstractClasGenA {
    public JobAbc(String a, String b) {
        super(a, b);
    }

    @Override
    public void start() {
        this.d();
        System.out.println(this.getA() + this.getB());
        System.out.println("khởi động 1 cái gì đó ở đây");
        Map<String, Object> hashMap = new HashMap<>(); //thứ tự lộn xộn
        Map<String, Object> linkedHashMap = new LinkedHashMap<>(); //duy trì thu tự
        Map<String, Object> treeMap = new TreeMap<>();
        Set<String> stringSet = new HashSet<>(); // không cho phép trùng với class cần Override lại equals và hasCode
        ArrayList<String>  stringListArrayList = new ArrayList<>(); //truy cập nhanh với chỉ số. mất thời gian cấp phát bộ nhớ khi đầy
        LinkedList<String>  stringList2 = new LinkedList<>(); // nhanh với thêm và xóa vào đầu. chậm với lấy chỉ số
        Queue<String> stringQueue = new LinkedList<>();
        stringQueue.add("");stringQueue.poll();
        stringList2.get(1); stringList2.remove(2);stringList2.add(1, "s"); stringList2.addLast("d");
        stringListArrayList.get(1);
    }

    @Override
    public void startJob() {
        this.start();
    }
}
